package com.app.cryptography.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Data
@Document
public class DecryptedFile {

    @Id
    private String fileId;
    private String fileName;
    private String fileExtension;
    private String fileSize;
    private String fileStatus;
    private File file;

    public DecryptedFile() {
    }



    public DecryptedFile(File file) {
        this.file = file;
        this.fileName = file.getName();
    }


}
