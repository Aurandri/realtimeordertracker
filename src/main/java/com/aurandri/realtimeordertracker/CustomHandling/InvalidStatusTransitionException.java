package com.aurandri.realtimeordertracker.CustomHandling;

import com.aurandri.realtimeordertracker.entities.OrderStatus;

public class InvalidStatusTransitionException extends RuntimeException {
    public InvalidStatusTransitionException(OrderStatus current, OrderStatus next) {
        super("Cannot transition from " + current + " to " + next);
    }
}