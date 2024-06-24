package com.tumtech.authservice.dto;

import com.tumtech.authservice.enums.Roles;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserResponse {
    private Roles userRoles;
    private LocalDateTime createdAt;
    private LocalDateTime updated;
    private String firstName;
    private String email;
    private String lastName;
    private String phone;
    private LocalDate dob;
}
