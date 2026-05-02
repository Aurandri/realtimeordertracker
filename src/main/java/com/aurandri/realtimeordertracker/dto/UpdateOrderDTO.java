package com.aurandri.realtimeordertracker.dto;

import com.aurandri.realtimeordertracker.entities.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateOrderDTO {
    @NotNull
    private Long customerId;

    @NotNull
    private Long orderId;

    @NotNull
    private OrderStatus status;
}
