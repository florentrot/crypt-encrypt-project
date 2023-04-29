package com.app.cryptography.service.impl;

import com.app.cryptography.dto.FileToEncryptDTO;
import com.app.cryptography.model.DecryptedFile;
import com.app.cryptography.model.FileToEncrypt;
import com.app.cryptography.repository.FileToEncryptRepository;
import com.app.cryptography.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Value("${files.to.encrypt.path}")
    public String filesToEncryptPath;

    FileToEncryptRepository fileToEncryptRepository;

    FileServiceImpl(FileToEncryptRepository fileToEncryptRepository) {
        this.fileToEncryptRepository = fileToEncryptRepository;
    }

    @Override
    public void saveFileToEncrypt(MultipartFile file, FileToEncryptDTO fileToEncryptDTO) throws IOException {

            File savedFile = new File(filesToEncryptPath + fileToEncryptDTO.getFileId() + fileToEncryptDTO.getFileExtension());

            //save file
            FileOutputStream fos = new FileOutputStream(savedFile);
            fos.write(file.getBytes());
            fos.close();

            //convert
            FileToEncrypt fileToEncrypt = mapToEntity(fileToEncryptDTO);
            fileToEncrypt.setFileStatus("Saved");

            //save in database
            this.fileToEncryptRepository.save(fileToEncrypt);

    }

    @Override
    public void updateFile(FileToEncryptDTO fileToEncryptDTO) {
        FileToEncrypt fileToUpdate = this.fileToEncryptRepository.findById(fileToEncryptDTO.getFileId()).get();
        fileToUpdate.setFileStatus("Encrypted");
        this.fileToEncryptRepository.save(fileToUpdate);
    }

    public List<FileToEncrypt> getAllFileModel() {
        return this.fileToEncryptRepository.findAll();
    }

    public static FileToEncrypt mapToEntity(FileToEncryptDTO fileToEncryptDTO) {
        FileToEncrypt fileToEncrypt = new FileToEncrypt();
        fileToEncrypt.setFileId(fileToEncryptDTO.getFileId());
        fileToEncrypt.setFileExtension(fileToEncryptDTO.getFileExtension());
        fileToEncrypt.setFileType(fileToEncryptDTO.getFileType());
        fileToEncrypt.setFileSize(fileToEncryptDTO.getFileSize());
        fileToEncrypt.setFileName(fileToEncryptDTO.getFileName());
        fileToEncrypt.setRecipientsEmail(fileToEncryptDTO.getRecipientsEmail());
        return fileToEncrypt;
    }


}
