package com.aurandri.realtimeordertracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private String customerName;
    private List<String> items;
    private String status;
    private LocalDateTime createdAt;
}
