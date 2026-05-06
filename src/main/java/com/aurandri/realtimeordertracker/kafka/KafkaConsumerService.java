package com.aurandri.realtimeordertracker.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurandri.realtimeordertracker.controller.SseController;
import com.aurandri.realtimeordertracker.entities.OrderEntity;
import com.aurandri.realtimeordertracker.entities.OrderStatus;
import com.aurandri.realtimeordertracker.repositories.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final OrderRepository orderRepository;
    private final KafkaProducerService kafkaProducerService;
    private final SseController sseController;

    @KafkaListener(
            topics = "${app.kafka.topic.order-events}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    @Transactional
    public void consume(
            @Payload OrderEvent event,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset
    ) {
        log.info("Consumed event orderId={}, status={}, partition={}, offset={}",
                event.getOrderId(), event.getCurrentStatus(), partition, offset);

        try {
            processEvent(event);
        } catch (Exception ex) {
            // No silent failures - Giving back any log/info
            log.error("Failed to process event orderId={}, status={}, partition={}, offset={}, error={}",
                    event.getOrderId(), event.getCurrentStatus(), partition, offset, ex.getMessage(), ex);
        }
    }

    private void processEvent(OrderEvent event) {
        OrderEntity order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found: " + event.getOrderId()));

        OrderStatus nextStatus = getNextStatus(event.getCurrentStatus());

        if (nextStatus == null) {
            log.info("Order {} already at final status: {}", event.getOrderId(), event.getCurrentStatus());
            return;
        }

        if (!order.getStatus().equals(event.getCurrentStatus())) {
            log.warn("Order {} status mismatch. DB={}, Event={}. Skipping.",
                    event.getOrderId(), order.getStatus(), event.getCurrentStatus());
            return;
        }

        // [Debug] Jeda antar transisi
        try {
            log.info("Order {} waiting 5s before transitioning from {}...", event.getOrderId(), event.getCurrentStatus());
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Sleep interrupted for orderId={}", event.getOrderId());
        }

        order.setStatus(nextStatus);
        orderRepository.save(order);
        log.info("Order {} transitioned: {} -> {}", event.getOrderId(), event.getCurrentStatus(), nextStatus);

        // Push ke SSE
        sseController.pushStatusUpdate(order.getId(), nextStatus.name());

        kafkaProducerService.publishOrderEvent(
                OrderEvent.of(order.getId(), event.getCurrentStatus(), nextStatus)
        );
    }

    private OrderStatus getNextStatus(OrderStatus current) {
        return switch (current) {
            case PENDING -> OrderStatus.PROCESSING;
            case PROCESSING -> OrderStatus.SHIPPED;
            case SHIPPED -> OrderStatus.DELIVERED;
            case DELIVERED -> null; // final state
        };
    }
}