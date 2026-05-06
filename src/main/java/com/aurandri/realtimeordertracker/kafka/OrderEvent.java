package com.aurandri.realtimeordertracker.kafka;

import java.time.LocalDateTime;

import com.aurandri.realtimeordertracker.entities.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private Long orderId;
    private OrderStatus previousStatus;
    private OrderStatus currentStatus;
    private LocalDateTime timestamp;

    public static OrderEvent of(Long orderId, OrderStatus previousStatus, OrderStatus currentStatus) {
        return new OrderEvent(orderId, previousStatus, currentStatus, LocalDateTime.now());
    }
}