package com.aurandri.realtimeordertracker.dto;

import com.aurandri.realtimeordertracker.entities.OrderStatus;
import lombok.Data;

@Data
public class OrderRequestDTO {
    private OrderStatus orderStatus;
}
