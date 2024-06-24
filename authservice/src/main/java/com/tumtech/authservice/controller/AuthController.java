package com.tumtech.authservice.controller;

import com.tumtech.authservice.dto.UserDto;
import com.tumtech.authservice.service.AuthService;
import com.tumtech.authservice.serviceImpl.AuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class AuthController {
    private AuthService authService;
    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/")
    public ResponseEntity<String> register (@RequestBody UserDto userDto,
                                            @RequestParam(name = "profile", required = false)
                                                    MultipartFile file
                                            ) throws IOException {
        return ResponseEntity.ok(authService.register(userDto,file));

    }
//    @GetMapping("/")
//    public ResponseEntity<UserDto> allUsers (){
//        ResponseEntity.ok()
//    }

}
