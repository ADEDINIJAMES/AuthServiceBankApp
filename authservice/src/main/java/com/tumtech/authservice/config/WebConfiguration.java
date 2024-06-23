package com.tumtech.authservice.config;

import com.tumtech.authservice.serviceImpl.AuthServiceImplementation;
import com.tumtech.authservice.util.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebConfiguration {
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    private AuthServiceImplementation authServiceImplementation;

    public WebConfiguration (JwtAuthenticationFilter jwtAuthenticationFilter, @Lazy AuthServiceImplementation authServiceImplementation){
this.jwtAuthenticationFilter =jwtAuthenticationFilter;
this.authServiceImplementation =authServiceImplementation;
    }
@Bean
    CorsConfigurationSource corsConfigurationSource (){
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
    corsConfiguration.setAllowedMethods(Arrays.asList("*"));
    corsConfiguration.setAllowedHeaders(Arrays.asList("*"));

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
return source;
    }


    @Bean
    public PasswordEncoder passwordEncoder (){return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider (){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
  daoAuthenticationProvider.setUserDetailsService(username -> authServiceImplementation.loadUserByUsername(username));
  return daoAuthenticationProvider;
    }
 @Bean
 public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(httpRequests -> httpRequests
                                .requestMatchers("http").permitAll()
                                .requestMatchers("http").authenticated())
                .logout(logout-> logout.deleteCookies("remove").invalidateHttpSession(true)

                ).
    sessionManagement(sessionManagement ->
            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
