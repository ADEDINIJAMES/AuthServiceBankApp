package com.tumtech.authservice.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Builder
@Data
public class ApiResponse{
    private String message;
    private String responseCode;
    private String data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ApiResponse(String message, String responseCode, String data) {
        this.message = message;
        this.responseCode = responseCode;
        this.data = data;
    }
}