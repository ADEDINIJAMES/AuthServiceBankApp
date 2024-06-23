package com.tumtech.authservice.repository;

import com.tumtech.authservice.model.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileDataRespository extends JpaRepository<ImageData, Long> {
    Optional<ImageData> findByName(String fileName);
}
