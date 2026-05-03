package com.aurandri.realtimeordertracker.services;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurandri.realtimeordertracker.CustomHandling.CustomerNotFoundException;
import com.aurandri.realtimeordertracker.CustomHandling.InvalidStatusTransitionException;
import com.aurandri.realtimeordertracker.CustomHandling.OrderNotFoundException;
import com.aurandri.realtimeordertracker.dto.CreateOrderDTO;
import com.aurandri.realtimeordertracker.dto.UpdateOrderDTO;
import com.aurandri.realtimeordertracker.entities.CustomerEntity;
import com.aurandri.realtimeordertracker.entities.OrderEntity;
import com.aurandri.realtimeordertracker.entities.OrderStatus;
import com.aurandri.realtimeordertracker.kafka.KafkaProducerService;
import com.aurandri.realtimeordertracker.kafka.OrderEvent;
import com.aurandri.realtimeordertracker.repositories.CustomerRepository;
import com.aurandri.realtimeordertracker.repositories.OrderRepository;

import tools.jackson.databind.ObjectMapper;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    ObjectMapper objectMapper;

    // Get All Order data
    @Transactional(readOnly = true)
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get All Order by Status
    @Transactional(readOnly = true)
    public List<OrderEntity> getOrderByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public OrderEntity getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    // Create
    @Transactional
    public OrderEntity createOrder(CreateOrderDTO createOrderDTO) {
        CustomerEntity customer = customerRepository.findByEmail(createOrderDTO.getCustomerEmail())
        .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + createOrderDTO.getCustomerEmail()));

        OrderEntity order = new OrderEntity();
        order.setCustomer(customer);
        order.setItems(createOrderDTO.getItems().toString());
        order.setStatus(OrderStatus.PENDING);

        OrderEntity saved = orderRepository.save(order);
        // Publish setelah DB commit
        kafkaProducerService.publishOrderEvent(
                OrderEvent.of(saved.getId(), null, OrderStatus.PENDING)
        );

        return saved;
    }

    // Update ORDER
    // Set rule transisition
    private static final Map<OrderStatus, Set<OrderStatus>> VALID_TRANSITIONS = Map.of(
            OrderStatus.PENDING,    Set.of(OrderStatus.PROCESSING),
            OrderStatus.PROCESSING, Set.of(OrderStatus.SHIPPED),
            OrderStatus.SHIPPED,    Set.of(OrderStatus.DELIVERED)
    );

    private void validateTransition(OrderStatus current, OrderStatus next) {
        if (!VALID_TRANSITIONS.getOrDefault(current, Set.of()).contains(next)) {
            throw new InvalidStatusTransitionException(current, next);
        }
    }

    @Transactional
    public OrderEntity updateOrder(UpdateOrderDTO updateOrderDTO) {
        OrderEntity order = orderRepository.findById(updateOrderDTO.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException(updateOrderDTO.getOrderId()));

        validateTransition(order.getStatus(), updateOrderDTO.getStatus());

        OrderStatus previousStatus = order.getStatus();
        order.setStatus(updateOrderDTO.getStatus());
        OrderEntity saved = orderRepository.save(order);

        // Publish setelah DB commit
        kafkaProducerService.publishOrderEvent(
                OrderEvent.of(saved.getId(), previousStatus, updateOrderDTO.getStatus())
        );

        return saved;
    }

}
