package com.company.toDoList.service;

import com.company.toDoList.entities.FileEntity;
import com.company.toDoList.exceptions.FileStorageException;
import com.company.toDoList.exceptions.MyFileNotFoundException;
import com.company.toDoList.properties.FileStorageProperties;
import com.company.toDoList.repository.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileStorageService {

    private final FileRepo fileRepo;

    @Autowired
    public FileStorageService(FileRepo fileRepo) {
        this.fileRepo = fileRepo;
    }

    public FileEntity storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        FileEntity storedFile = new FileEntity(fileName, file.getContentType(), file.getBytes());

        return fileRepo.save(storedFile);
    }

    public FileEntity getFile(Long id){
        return fileRepo.findById(id).get();
    }

    public Stream<FileEntity> getAllFiles() {
        return fileRepo.findAll().stream();
    }



}