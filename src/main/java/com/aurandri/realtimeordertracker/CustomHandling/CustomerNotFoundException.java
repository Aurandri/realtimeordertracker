package com.aurandri.realtimeordertracker.CustomHandling;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String email) {
        super("Could not find costumer with email: " + email);
    }
}
