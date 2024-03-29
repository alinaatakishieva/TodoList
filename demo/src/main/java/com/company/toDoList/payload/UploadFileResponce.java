package com.company.toDoList.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileResponce {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;
}

