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
public class FileToEncrypt {
    @Id
    private String fileId;
    private String fileName;
    private String filePath;
    private String fileExtension;
    private String fileType;
    private String fileSize;
    private String fileStatus;
    private String recipientsEmail;
    private File file;

    public FileToEncrypt() {
    }

    public FileToEncrypt(String fileName, String filePath, String fileExtension, String fileType, String size, String fileStatus, String recipientsEmail) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileExtension = fileExtension;
        this.fileType = fileType;
        this.fileSize = size;
        this.fileStatus = fileStatus;
        this.recipientsEmail = recipientsEmail;
    }



    public FileToEncrypt(File file) {
        this.file = file;
        this.fileName = file.getName();
        this.filePath = file.getPath();
        this.fileExtension = extension();
        this.fileSize = printFileSize();
    }

    public String extension() {
        String extension="";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i + 1);
        }
        return extension;
    }

    @Override
    public String toString() {
        return "FileModel{" +
                "fileName='" + fileName + '\'' +
                ", extension='" + fileExtension + '\'' +
                ", size='" + fileSize + '\'' +
                '}';
    }

    public String printFileSize() {

        Path path = Paths.get(this.filePath);

        try {
            long size = Files.size(path);
            if (size < 1024) {
                return String.format("%,d bytes", size);
            } else {
                return String.format("%,d kilobytes", size / 1024);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
