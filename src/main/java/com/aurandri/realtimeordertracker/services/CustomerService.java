package com.aurandri.realtimeordertracker.services;

import com.aurandri.realtimeordertracker.dto.CreateCustomerDTO;
import com.aurandri.realtimeordertracker.entities.CustomerEntity;
import com.aurandri.realtimeordertracker.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired private CustomerRepository customerRepository;

    // Create
    public CustomerEntity createCustomer(CreateCustomerDTO createCustomerDTO) {
        CustomerEntity customer = new CustomerEntity();
        customer.setCustomerName(createCustomerDTO.getCustomerName());

        return customerRepository.save(customer);
    }
}
