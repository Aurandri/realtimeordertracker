package com.aurandri.realtimeordertracker.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(name = "customer", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class CustomerEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String customerName;

    @Column(length = 100) 
    // @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(gmail\\.com|yahoo\\.com|outlook\\.com)$")
    private String email;
}

