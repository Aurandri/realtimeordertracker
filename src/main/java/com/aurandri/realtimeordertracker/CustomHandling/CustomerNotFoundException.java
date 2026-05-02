package com.aurandri.realtimeordertracker.CustomHandling;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(Long id) {
        super("Could not find costumer with id: " + id);
    }
}
