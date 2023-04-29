package com.app.cryptography.service;

import com.app.cryptography.dto.FileToEncryptDTO;
import com.app.cryptography.model.DecryptedFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    void saveFileToEncrypt(MultipartFile file, FileToEncryptDTO fileToEncryptDTO) throws IOException;

    void updateFile(FileToEncryptDTO fileToEncryptDTO);
}
