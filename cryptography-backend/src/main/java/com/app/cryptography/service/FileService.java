package com.app.cryptography.service;

import com.app.cryptography.dto.FileDTO;
import com.app.cryptography.dto.FileEncryptDTO;
import com.app.cryptography.model.DecryptedFile;
import com.app.cryptography.model.EncryptedFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    void saveFile(MultipartFile file, FileDTO fileDTO) throws IOException;

    void updateFile(FileEncryptDTO fileEncryptDTO);

    List<DecryptedFile> getAllDecryptedFiles();

    List<EncryptedFile> getAllFileModel();
}
