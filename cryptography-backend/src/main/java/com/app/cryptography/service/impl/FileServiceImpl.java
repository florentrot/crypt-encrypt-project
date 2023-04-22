package com.app.cryptography.service.impl;

import com.app.cryptography.dto.FileModelDTO;
import com.app.cryptography.model.FileModel;
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
    public void saveFile(MultipartFile file, FileModelDTO fileModelDTO) throws IOException {
      //  File savedFile = new File(filesPath + file.getOriginalFilename());
        File savedFile = new File(filesPath + fileModelDTO.getFileId() + fileModelDTO.getFileExtension());

        //save file
        FileOutputStream fos = new FileOutputStream(savedFile);
        fos.write(file.getBytes());
        fos.close();

        //convert
        FileModel fileModel = mapToEntity(fileModelDTO);
        fileModel.setFileStatus("Saved");

        //save in database
        this.fileRepository.save(fileModel);

    }

    @Override
    public void updateFile(FileModelDTO fileModelDTO) {
       FileModel fileToUpdate = this.fileRepository.findById(fileModelDTO.getFileId()).get();
        fileToUpdate.setFileStatus("Encrypted");
        this.fileRepository.save(fileToUpdate);
    }

    public List<FileModel> getAllFileModel() {
        return this.fileRepository.findAll();
    }

    public static FileModel mapToEntity(FileModelDTO fileModelDTO) {
        FileModel fileModel = new FileModel();
        fileModel.setFileId(fileModelDTO.getFileId());
        fileModel.setFileExtension(fileModelDTO.getFileExtension());
        fileModel.setFileType(fileModelDTO.getFileType());
        fileModel.setFileSize(fileModelDTO.getFileSize());
        fileModel.setFileName(fileModelDTO.getFileName());
        fileModel.setRecipientsEmail(fileModelDTO.getRecipientsEmail());
        return fileModel;
    }



}