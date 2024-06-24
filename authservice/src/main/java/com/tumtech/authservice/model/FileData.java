package com.tumtech.authservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Builder
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    @Lob
    @Column(name = "imageData", length = 1000)
    private  byte[] imageData;

    public static String Error_Messsage = "ERROR IN FILE UPLOAD";

    public static String getError_Messsage() {
        return Error_Messsage;
    }

    public static void setError_Messsage(String error_Messsage) {
        Error_Messsage = error_Messsage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public FileData(Long id, String name, String type, byte[] imageData) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.imageData = imageData;
    }

    public FileData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
