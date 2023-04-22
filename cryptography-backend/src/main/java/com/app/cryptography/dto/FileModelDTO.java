package com.app.cryptography.dto;

import lombok.Data;

@Data
public class FileModelDTO {

    private String fileId;;
    private String fileName;
    private String appFileName;
    private String fileExtension;
    private String fileType;
    private String fileSize;
    private String fileStatus;
    private String recipientsEmail;

}
