package com.tumtech.authservice.service;

import com.tumtech.authservice.dto.UserDto;
import com.tumtech.authservice.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AuthService extends UserDetailsService {
    String register (UserDto userDto, MultipartFile file) throws IOException;
    UserDto getUser (Long id);
    Page<Users> getAllUsers (Pageable pageable);

}
