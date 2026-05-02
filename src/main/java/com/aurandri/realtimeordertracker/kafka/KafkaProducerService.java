package com.aurandri.realtimeordertracker.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${app.kafka.topic.order-events}")
    private String topic;

    public void publishOrderEvent(OrderEvent event) {
        String key = event.getOrderId().toString();

        CompletableFuture<SendResult<String, OrderEvent>> future =
                kafkaTemplate.send(topic, key, event);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Failed to publish event for orderId={}, status={}, error={}",
                        event.getOrderId(), event.getCurrentStatus(), ex.getMessage());
            } else {
                log.info("Published event orderId={}, status={}, partition={}, offset={}",
                        event.getOrderId(),
                        event.getCurrentStatus(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            }
        });
    }
}