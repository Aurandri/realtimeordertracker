package com.aurandri.realtimeordertracker.CustomHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Menangkap error parsing JSON (termasuk enum tidak valid)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Resp<Object>> handleJsonParseError(HttpMessageNotReadableException ex) {
        Resp<Object> response = new Resp<>();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage("something went wrong");
        response.setData(null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Menangkap error @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Resp<Object>> handleValidationError(MethodArgumentNotValidException ex) {
        Resp<Object> response = new Resp<>();
        response.setCode(HttpStatus.BAD_REQUEST.value());
        response.setMessage("something went wrong");
        response.setData(null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Fallback — semua exception lain
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Resp<Object>> handleGeneral(Exception ex) {
        Resp<Object> response = new Resp<>();
        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("something went wrong");
        response.setData(null);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}