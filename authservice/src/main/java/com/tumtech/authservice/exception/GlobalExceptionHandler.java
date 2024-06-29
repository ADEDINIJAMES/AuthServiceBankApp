package com.tumtech.authservice.exception;

import com.tumtech.authservice.dto.ApiResponse;
import org.apache.http.annotation.Contract;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler  {
    @ExceptionHandler(UserNameNotFoundException.class)
    public ResponseEntity<?> usernameNotExceptionHandler(UserNameNotFoundException ex){
        return ResponseEntity.status(404).body(new ApiResponse(ex.getMessage(), "404",ex.getUsername()));
    }



}
