package com.tumtech.authservice.util;

import com.tumtech.authservice.serviceImpl.AuthServiceImplementation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
private JwtUtils utils;
private AuthServiceImplementation authServiceImplementation;
@Autowired
public JwtAuthenticationFilter (JwtUtils utils, @Lazy AuthServiceImplementation authServiceImplementation){
    this.authServiceImplementation =authServiceImplementation;
    this.utils = utils;
}




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String authenticationHeader = null;
        String username = null;
        UserDetails userDetails = null;
        authenticationHeader = request.getHeader("Authorization");
        if (authenticationHeader!=null&&authenticationHeader.startsWith("Bearer ")){
            token = authenticationHeader.substring(7);
            username =utils.extractUsername.apply(token);
        }
        if (username!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
            userDetails = authServiceImplementation.loadUserByUsername(username);
            if (userDetails!=null&&utils.isTokenValid.apply(token, userDetails.getUsername())){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);


    }
    }

