package com.tumtech.authservice.repository;

import com.tumtech.authservice.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageDataRepository extends JpaRepository<FileData, Long> {
    Optional<FileData> findByName (String name);
}
