package com.tumtech.authservice.repository;

import com.tumtech.authservice.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByEmail (String email);
}
