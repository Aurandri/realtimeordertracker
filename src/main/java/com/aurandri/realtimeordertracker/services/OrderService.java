package com.aurandri.realtimeordertracker.services;

import com.aurandri.realtimeordertracker.CustomHandling.CustomerNotFoundException;
import com.aurandri.realtimeordertracker.CustomHandling.InvalidStatusTransitionException;
import com.aurandri.realtimeordertracker.CustomHandling.OrderNotFoundException;
import com.aurandri.realtimeordertracker.dto.CreateOrderDTO;
import com.aurandri.realtimeordertracker.dto.OrderRequestDTO;
import com.aurandri.realtimeordertracker.dto.UpdateOrderDTO;
import com.aurandri.realtimeordertracker.entities.CustomerEntity;
import com.aurandri.realtimeordertracker.entities.OrderEntity;
import com.aurandri.realtimeordertracker.entities.OrderStatus;
import com.aurandri.realtimeordertracker.repositories.CustomerRepository;
import com.aurandri.realtimeordertracker.repositories.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    ObjectMapper objectMapper;

    // Get All Order data
    @Transactional(readOnly = true)
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get All Order by Status
    @Transactional(readOnly = true)
    public List<OrderEntity> getOrderByStatus(OrderRequestDTO orderRequestDTO){
        return  orderRepository.findByStatus(orderRequestDTO.getOrderStatus());
    }

    // Create
    @Transactional
    public OrderEntity createOrder(CreateOrderDTO createOrderDTO) {
        CustomerEntity customer = customerRepository.findById(createOrderDTO.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(createOrderDTO.getCustomerId()));

        OrderEntity order = new OrderEntity();
        order.setCustomer(customer);
        order.setItems(createOrderDTO.getItems().toString());
        order.setStatus(OrderStatus.PENDING);

        return orderRepository.save(order);
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

        // check user input
        validateTransition(order.getStatus(), updateOrderDTO.getStatus());

        order.setStatus(updateOrderDTO.getStatus());
        return orderRepository.save(order);
    }

}
