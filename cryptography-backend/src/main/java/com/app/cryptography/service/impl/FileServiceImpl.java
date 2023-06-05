package com.app.cryptography.service.impl;

import com.app.cryptography.dto.FileDTO;
import com.app.cryptography.dto.FileEncryptDTO;
import com.app.cryptography.model.DecryptedFile;
import com.app.cryptography.model.EncryptedFile;
import com.app.cryptography.repository.DecryptedFileRepository;
import com.app.cryptography.repository.EncryptedFileRepository;
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

    @Value("${files.to.decrypt.path}")
    public String filesToDecryptPath;

    EncryptedFileRepository encryptedFileRepository;

    DecryptedFileRepository decryptedFileRepository;

    FileServiceImpl(EncryptedFileRepository encryptedFileRepository, DecryptedFileRepository decryptedFileRepository) {
        this.encryptedFileRepository = encryptedFileRepository;
        this.decryptedFileRepository = decryptedFileRepository;
    }

    @Override
    public void saveFile(MultipartFile file, FileDTO fileDTO) throws IOException {
        if (fileDTO instanceof FileEncryptDTO) {
         File savedFile = new File(filesToEncryptPath + fileDTO.getFileId() + fileDTO.getFileExtension());

            //save file
            FileOutputStream fos = new FileOutputStream(savedFile);
            fos.write(file.getBytes());
            fos.close();

            //convert
            EncryptedFile encryptedFile = mapToEntity(fileDTO);
            encryptedFile.setFileStatus("Saved");

            //save in database
            this.encryptedFileRepository.save(encryptedFile);

        } else {
          File savedFile = new File(filesToDecryptPath + fileDTO.getFileId() + ".enc"); // fileDTO.getFileExtension() after solve frontend

            //save file
            FileOutputStream fos = new FileOutputStream(savedFile);
            fos.write(file.getBytes());
            fos.close();

        }
    }


    @Override
    public void updateFile(FileEncryptDTO fileEncryptDTO) {
        EncryptedFile fileToUpdate = this.encryptedFileRepository.findById(fileEncryptDTO.getFileId()).get();
        fileToUpdate.setFileStatus("Encrypted");
        this.encryptedFileRepository.save(fileToUpdate);
    }

    @Override
    public List<EncryptedFile> getAllFileModel() {
        return this.encryptedFileRepository.findAll();
    }

    @Override
    public List<DecryptedFile> getAllDecryptedFiles() {
        return this.decryptedFileRepository.findAll();
    }

    public static EncryptedFile mapToEntity(FileDTO fileToEncryptDTO) {
        EncryptedFile encryptedFile = new EncryptedFile();
        encryptedFile.setFileId(fileToEncryptDTO.getFileId());
        encryptedFile.setFileExtension(fileToEncryptDTO.getFileExtension());
        encryptedFile.setFileType(fileToEncryptDTO.getFileType());
        encryptedFile.setFileSize(fileToEncryptDTO.getFileSize());
        encryptedFile.setFileName(fileToEncryptDTO.getFileName());
        encryptedFile.setRecipientsEmail(fileToEncryptDTO.getRecipientsEmail());
        return encryptedFile;
    }


}
