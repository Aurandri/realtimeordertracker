package com.aurandri.realtimeordertracker.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateOrderDTO {
    @NotBlank
    private String customerEmail;

    @NotEmpty
    private List<Object> items;
}
