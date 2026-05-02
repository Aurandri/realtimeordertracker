package com.aurandri.realtimeordertracker.CustomHandling;

import lombok.Data;

@Data
public class CustomException extends Exception {
    private Integer code;
    private String message;

    // Default Constructor
    public CustomException() {
    }
    // Constructor
    public CustomException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
