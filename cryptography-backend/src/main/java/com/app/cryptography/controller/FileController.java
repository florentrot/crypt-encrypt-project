package com.app.cryptography.controller;

import com.app.cryptography.dto.EncryptDetailsDTO;
import com.app.cryptography.dto.FileModelDTO;
import com.app.cryptography.model.FileModel;
import com.app.cryptography.service.CryptoService;
import com.app.cryptography.service.common.JsonRequestService;
import com.app.cryptography.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("")
public class FileController {

    @Autowired
    FileServiceImpl fileService;

    @Autowired
    CryptoService cryptoService;

    @PostMapping("/upload")
    public void uploadFile(@RequestParam("file_upload") MultipartFile file,
                           @RequestParam("file_model") String object) throws IOException {
       FileModelDTO fileModelDTO = (FileModelDTO) JsonRequestService.fromJsonMethod(object, FileModelDTO.class);
       this.fileService.saveFile(file, fileModelDTO);
    }

    @PostMapping("/encrypt")
    public void encryptFile(@RequestBody FileModelDTO fileModelDTO) throws InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        this.cryptoService.encrypt("src/main/resources/files/"+ fileModelDTO.getFileId()+fileModelDTO.getFileExtension());
        this.fileService.updateFile(fileModelDTO);
    }

    @GetMapping
    public List<FileModel> getAllFileModel() {
        return this.fileService.getAllFileModel();
    }



}
