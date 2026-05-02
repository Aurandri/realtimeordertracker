package com.aurandri.realtimeordertracker.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "orders")
@Data
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private CustomerEntity customer;

    @Column(columnDefinition = "Text")
    private String items;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @CreationTimestamp
    @JsonFormat(pattern = "hh:mm a dd/MMM/yyyy", timezone = "Asia/Jakarta")
    private Date createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern = "hh:mm a dd/MMM/yyyy", timezone = "Asia/Jakarta")
    private Date updatedAt;


}