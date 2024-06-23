package com.tumtech.authservice.dto;

import com.tumtech.authservice.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Roles userRoles;
    private LocalDateTime createdAt;
    private LocalDateTime updated;
    private String firstName;
    private String email;
    private String lastName;
    private String phone;
    private LocalDate dob;
    private String password;
    private String confirmPassword;
    private String gender;
    private byte profilepic;
    private String profileName;
}
