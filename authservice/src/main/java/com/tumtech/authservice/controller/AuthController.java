package com.tumtech.authservice.controller;

import com.tumtech.authservice.dto.ApiResponse;
import com.tumtech.authservice.dto.LoginRequest;
import com.tumtech.authservice.dto.UserDto;
import com.tumtech.authservice.model.Users;
import com.tumtech.authservice.service.AuthService;
import com.tumtech.authservice.serviceImpl.AuthServiceImplementation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private AuthService authService;
    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping(value = "/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse register(@ModelAttribute UserDto userDto,
                                @RequestParam("file") MultipartFile file) throws IOException {
        return authService.register(userDto, file);
    }
    @GetMapping("/")
    public ResponseEntity<Page<Users>> allUsers (
            @RequestParam (name = "pageNo", required = false, defaultValue = "0") int pageNo,
            @RequestParam(name="pageSize", required = false, defaultValue = "10") int pageSize,
            @RequestParam (name = "sortParam", required = false, defaultValue = "createdAt,dsc") String sortParam

    ){
        return ResponseEntity.ok(authService.getAllUsers(pageSize,pageNo,sortParam));
    }

    @GetMapping("/{id}")
    public  ResponseEntity<UserDto> getUser (@PathVariable Long id) throws IOException {
        return ResponseEntity.ok(authService.getUser(id));

    }
@PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.Login(loginRequest));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout (HttpServletRequest request){
        return ResponseEntity.ok(authService.logout(request));
    }
}
