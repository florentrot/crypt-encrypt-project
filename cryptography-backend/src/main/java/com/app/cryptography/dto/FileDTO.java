package com.app.cryptography.dto;

import lombok.Data;

@Data
public abstract class FileDTO {

    private String fileId;
    private String fileName;
    private String fileExtension;
    private String fileSize;
    private String fileType;
    private String recipientsEmail;

}
