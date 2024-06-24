package com.tumtech.authservice.repository;

import com.tumtech.authservice.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail (String email);
    Page<Users> getAllUsers(Pageable pageable);

}
