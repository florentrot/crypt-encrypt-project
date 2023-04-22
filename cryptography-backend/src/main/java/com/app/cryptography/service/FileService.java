package com.app.cryptography.service;

import com.app.cryptography.dto.FileModelDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {

    void saveFile(MultipartFile file, FileModelDTO fileModelDTO) throws IOException;

    void updateFile(FileModelDTO fileModelDTO);
}
