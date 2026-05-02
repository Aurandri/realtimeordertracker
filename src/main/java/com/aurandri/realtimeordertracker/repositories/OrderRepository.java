package com.aurandri.realtimeordertracker.repositories;

import com.aurandri.realtimeordertracker.entities.OrderEntity;
import com.aurandri.realtimeordertracker.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByStatus(OrderStatus orderStatus);
}
