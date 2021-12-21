package com.company.toDoList.service;

import com.company.toDoList.entities.FileEntity;
import com.company.toDoList.exceptions.FileStorageException;
import com.company.toDoList.exceptions.MyFileNotFoundException;
import com.company.toDoList.payload.UploadFileResponce;
import com.company.toDoList.properties.FileStorageProperties;
import com.company.toDoList.repository.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    private final FileRepo fileRepo;

    private FileStorageProperties fileStorageProperties;

    @Autowired
    public FileStorageService(FileRepo fileRepo) {
        this.fileRepo = fileRepo;
    }

    public String storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        if (fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }

        Path targetLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize().resolve(fileName);

        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return fileName;

        //FileEntity storedFile = new FileEntity(fileName, file.getContentType(), file.getBytes());

        //return fileRepo.save(storedFile);
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize().resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName);
        }
    }
//
//    public FileEntity getFile(Long id) {
//        return fileRepo.findById(id).get();
//    }
//
//    public Stream<FileEntity> getAllFiles() {
//        return fileRepo.findAll().stream();
//    }


}