package com.aurandri.realtimeordertracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aurandri.realtimeordertracker.dto.CreateCustomerDTO;
import com.aurandri.realtimeordertracker.entities.CustomerEntity;
import com.aurandri.realtimeordertracker.repositories.CustomerRepository;

@Service
public class CustomerService {
    @Autowired private CustomerRepository customerRepository;

    public CustomerEntity createCustomer(CreateCustomerDTO dto) {

        Boolean isExist = customerRepository.existsByEmail(dto.getEmail());

        if (Boolean.TRUE.equals(isExist)) {
            return null;
        }

        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerName(dto.getCustomerName());
        customer.setEmail(dto.getEmail());

        return customerRepository.save(customer);
    }
}
