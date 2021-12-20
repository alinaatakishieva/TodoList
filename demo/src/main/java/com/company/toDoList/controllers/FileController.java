package com.company.toDoList.controllers;

import com.company.toDoList.entities.FileEntity;
import com.company.toDoList.payload.UploadFileResponce;
import com.company.toDoList.properties.ResponseFile;
import com.company.toDoList.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/file")
public class FileController {

    private final  FileStorageService fileStorageService;

    @Autowired
    public FileController(FileStorageService fileStorageService){
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/uploadFile")
    public FileEntity uploadFile (@RequestParam("file") MultipartFile file) {
        try {
            return fileStorageService.storeFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/getAllFiles")
    public ResponseEntity<List<ResponseFile>> getAllFiles(){
        List<ResponseFile> files = fileStorageService.getAllFiles().map(fileEntity -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(String.valueOf(fileEntity.getId()))
                    .toUriString();

            return new ResponseFile(
                    fileEntity.getName(),
                    fileDownloadUri,
                    fileEntity.getExtension(),
                    fileEntity.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        FileEntity file = fileStorageService.getFile(Long.valueOf(id));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getData());
    }

}
