package com.app.cryptography.dto;

import lombok.Data;

@Data
public class FileEncryptDTO extends FileDTO{

    private String appFileName;
    private String fileStatus;

}
