package com.aurandri.realtimeordertracker.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCustomerDTO {
    @NotBlank
    private String customerName;
}
