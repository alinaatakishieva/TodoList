package com.company.toDoList.controllers;

import com.company.toDoList.service.FileStorageService;
import com.company.toDoList.uploadFile.UploadFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileStorageService fileStorageService;

    @Autowired
    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

//    @PostMapping("/uploadFiles")
//    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
//        String fileName = fileStorageService.storeFile(file);
//
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/file/")
//                .path(fileName)
//                .toUriString();
//
//        return new UploadFileResponse(fileName, fileDownloadUri,
//                file.getContentType(), file.getSize());
//    }

    @PostMapping("/uploadFiles")
    @Async
    public void uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) throws IOException {

        for (MultipartFile file : files) {
            String fileName = fileStorageService.storeFile(file);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/file/")
                    .path(fileName)
                    .toUriString();

            new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        }
    }

    @GetMapping("/downloadFile/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }


//    @GetMapping("/getAllFiles")
//    public ResponseEntity<List<ResponseFile>> getAllFiles() {
//        List<ResponseFile> files = fileStorageService.getAllFiles().map(fileEntity -> {
//            String fileDownloadUri = ServletUriComponentsBuilder
//                    .fromCurrentContextPath()
//                    .path("/files/")
//                    .path(String.valueOf(fileEntity.getId()))
//                    .toUriString();
//
//            return new ResponseFile(
//                    fileEntity.getName(),
//                    fileDownloadUri,
//                    fileEntity.getExtension(),
//                    fileEntity.getData().length);
//        }).collect(Collectors.toList());
//
//        return ResponseEntity.status(HttpStatus.OK).body(files);
//    }
//
//    @GetMapping("/{fileId}")
//    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
//        FileEntity file = fileStorageService.getFile(Long.valueOf(id));
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
//                .body(file.getData());
//    }

}
