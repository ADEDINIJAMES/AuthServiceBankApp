package com.tumtech.authservice.serviceImpl;

import com.tumtech.authservice.model.FileData;
import com.tumtech.authservice.model.ImageData;
import com.tumtech.authservice.repository.FileDataRespository;
import com.tumtech.authservice.repository.ImageDataRepository;
import com.tumtech.authservice.util.PictureUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class FileDataService {
    private final ImageDataRepository imageDataRepository;
  private FileDataRespository fileDataRespository;
    private final String FOLDER_PATH = "fileUploadStorage/";
    @Autowired
    public FileDataService(ImageDataRepository imageDataRepository, FileDataRespository fileDataRespository){
        this.imageDataRepository = imageDataRepository;
        this.fileDataRespository = fileDataRespository;
    }

    public FileData upload (MultipartFile file) throws IOException {
     FileData fileData = new FileData();
     fileData.setName(file.getOriginalFilename());
     fileData.setType(file.getContentType());
     fileData.setImageData(PictureUpload.compressfile(file.getBytes()));
    FileData savedFileData = imageDataRepository.save(fileData);
     if(savedFileData != null){
         return fileData;
     }
return null;
    }

    public byte [] downloadFile (Long id){
        FileData fileDB = imageDataRepository.findById(id).orElseThrow(()-> new RuntimeException("not found"));
        return PictureUpload.decompressImage(fileDB.getImageData());
    }

    public ImageData uploadImageToFileSystem (MultipartFile file) throws IOException {
        if (file != null) {
            String filePath = FOLDER_PATH + file.getOriginalFilename();
            ImageData fileData = new ImageData();
            fileData.setFilePath(filePath);
            fileData.setType(file.getContentType());
            fileData.setName(file.getOriginalFilename());
            file.transferTo(new File(filePath));

            return fileDataRespository.save(fileData);

        }
        return null;
    }

    public byte[] downloadFileSystem (String fileName) throws IOException {
        ImageData imageData = fileDataRespository.findByName(fileName).orElseThrow(()-> new RuntimeException("not found"));
        String filePath = imageData.getFilePath();
        return Files.readAllBytes(new File (filePath).toPath());
    }

    }







