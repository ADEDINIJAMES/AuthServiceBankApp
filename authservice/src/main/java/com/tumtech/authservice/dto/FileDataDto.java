package com.tumtech.authservice.dto;

import lombok.Data;

@Data
public class FileDataDto {
    private byte [] image;

    public FileDataDto(byte[] image) {
        this.image = image;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
