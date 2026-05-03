package com.aurandri.realtimeordertracker.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aurandri.realtimeordertracker.entities.CustomerEntity;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    // Logic Insert new cust
    Boolean existsByEmail(String email);

    // check in before order
    Optional<CustomerEntity> findByEmail(String email);
}
