package com.aurandri.realtimeordertracker.services;

import com.aurandri.realtimeordertracker.dto.CreateOrderDTO;
import com.aurandri.realtimeordertracker.dto.UpdateOrderDTO;
import com.aurandri.realtimeordertracker.entities.CustomerEntity;
import com.aurandri.realtimeordertracker.entities.OrderEntity;
import com.aurandri.realtimeordertracker.entities.OrderStatus;
import com.aurandri.realtimeordertracker.repositories.CustomerRepository;
import com.aurandri.realtimeordertracker.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // Get All Order data
    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    // Create
    public OrderEntity createOrder(CreateOrderDTO createOrderDTO) {
        CustomerEntity customer = customerRepository.findById(createOrderDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        OrderEntity order = new OrderEntity();
        order.setCustomer(customer);
        order.setItems(createOrderDTO.getItems().toString());
        order.setStatus(OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    // Update
    public OrderEntity updateOrder(UpdateOrderDTO updateOrderDTO) {
        OrderEntity orderId = orderRepository.findById(updateOrderDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderEntity order = orderRepository.getReferenceById(updateOrderDTO.getCustomerId());
        order.setStatus(updateOrderDTO.getStatus());

        return orderRepository.save(order);
    }


}
