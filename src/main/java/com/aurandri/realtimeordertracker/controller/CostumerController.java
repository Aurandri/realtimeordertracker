package com.aurandri.realtimeordertracker.controller;

import com.aurandri.realtimeordertracker.CustomHandling.Resp;
import com.aurandri.realtimeordertracker.dto.CreateCustomerDTO;
import com.aurandri.realtimeordertracker.entities.CustomerEntity;
import com.aurandri.realtimeordertracker.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CostumerController {

    @Autowired private CustomerService costumerService;

    // Create Customer
    @PostMapping("/create-customer")
    public ResponseEntity<Resp<CustomerEntity>> createCustomer(@RequestBody @Valid CreateCustomerDTO createCustomerDTO) {
        try {
            CustomerEntity data = costumerService.createCustomer(createCustomerDTO);

            Resp<CustomerEntity> response = new Resp<>();
            response.setCode(HttpStatus.OK.value());
            response.setData(data);

            return ResponseEntity.ok(response);
        } catch (Exception e){
            Resp<CustomerEntity> response = new Resp<>();
            response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setData(null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
