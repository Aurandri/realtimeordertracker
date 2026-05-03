package com.aurandri.realtimeordertracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurandri.realtimeordertracker.CustomHandling.Resp;
import com.aurandri.realtimeordertracker.dto.CreateCustomerDTO;
import com.aurandri.realtimeordertracker.entities.CustomerEntity;
import com.aurandri.realtimeordertracker.services.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer")
public class CostumerController {

    @Autowired private CustomerService costumerService;

    // Create Customer
    @PostMapping("/create-customer")
    public ResponseEntity<Resp<?>> createCustomer(@RequestBody @Valid CreateCustomerDTO dto) {

        CustomerEntity data = costumerService.createCustomer(dto);

        Resp<Object> response = new Resp<>();

        if (data == null) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setData("Email already registered");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response.setCode(HttpStatus.OK.value());
        response.setData(data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
