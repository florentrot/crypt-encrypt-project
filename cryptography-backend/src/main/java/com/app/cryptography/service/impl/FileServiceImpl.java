package com.app.cryptography.service.impl;

import com.app.cryptography.dto.FileToEncryptDTO;
import com.app.cryptography.model.FilesToEncrypt;
import com.app.cryptography.repository.FileRepository;
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

    @Value("${files.path}")
    public String filesPath;

    FileRepository fileRepository;

    FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public void saveFile(MultipartFile file, FileToEncryptDTO fileToEncryptDTO) throws IOException {
      //  File savedFile = new File(filesPath + file.getOriginalFilename());
        File savedFile = new File(filesPath + fileToEncryptDTO.getFileId() + fileToEncryptDTO.getFileExtension());

        //save file
        FileOutputStream fos = new FileOutputStream(savedFile);
        fos.write(file.getBytes());
        fos.close();

        //convert
        FilesToEncrypt filesToEncrypt = mapToEntity(fileToEncryptDTO);
        filesToEncrypt.setFileStatus("Saved");

        //save in database
        this.fileRepository.save(filesToEncrypt);

    }

    @Override
    public void updateFile(FileToEncryptDTO fileToEncryptDTO) {
       FilesToEncrypt fileToUpdate = this.fileRepository.findById(fileToEncryptDTO.getFileId()).get();
        fileToUpdate.setFileStatus("Encrypted");
        this.fileRepository.save(fileToUpdate);
    }

    public List<FilesToEncrypt> getAllFileModel() {
        return this.fileRepository.findAll();
    }

    public static FilesToEncrypt mapToEntity(FileToEncryptDTO fileToEncryptDTO) {
        FilesToEncrypt filesToEncrypt = new FilesToEncrypt();
        filesToEncrypt.setFileId(fileToEncryptDTO.getFileId());
        filesToEncrypt.setFileExtension(fileToEncryptDTO.getFileExtension());
        filesToEncrypt.setFileType(fileToEncryptDTO.getFileType());
        filesToEncrypt.setFileSize(fileToEncryptDTO.getFileSize());
        filesToEncrypt.setFileName(fileToEncryptDTO.getFileName());
        filesToEncrypt.setRecipientsEmail(fileToEncryptDTO.getRecipientsEmail());
        return filesToEncrypt;
    }



}
