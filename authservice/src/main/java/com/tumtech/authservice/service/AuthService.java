package com.tumtech.authservice.service;

import com.tumtech.authservice.dto.ApiResponse;
import com.tumtech.authservice.dto.LoginRequest;
import com.tumtech.authservice.dto.UserDto;
import com.tumtech.authservice.model.Users;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AuthService extends UserDetailsService {
    ApiResponse register (UserDto userDto) throws IOException;
    UserDto getUser (Long id);
    Page<Users> getAllUsers(int pageSize, int pageNo, String sortParam);
     ApiResponse Login (LoginRequest loginRequest);
      ApiResponse logout (HttpServletRequest request );

    }
