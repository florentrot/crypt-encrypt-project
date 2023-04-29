package com.app.cryptography.controller;

import com.app.cryptography.dto.FileToEncryptDTO;
import com.app.cryptography.model.CryptoComponents;
import com.app.cryptography.model.FileToEncrypt;
import com.app.cryptography.service.EncryptService;
import com.app.cryptography.service.SecretService;
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
public class EncryptController {

    @Autowired
    FileServiceImpl fileService;

    @Autowired
    EncryptService encryptService;

    @Autowired
    SecretService secretService;

    @PostMapping("/upload")
    public void uploadFile(@RequestParam("file_upload") MultipartFile file_upload,
                           @RequestParam("file_model") String encryptMessage) throws IOException {
        FileToEncryptDTO fileToEncryptDTO = (FileToEncryptDTO) JsonRequestService.fromJsonMethod(encryptMessage, FileToEncryptDTO.class);
        this.fileService.saveFileToEncrypt(file_upload, fileToEncryptDTO);
    }

    @PostMapping("/encrypt")
    public void encryptFile(@RequestBody FileToEncryptDTO fileToEncryptDTO) throws InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        CryptoComponents cryptoComponents = this.encryptService.encrypt("src/main/resources/clear-files/" + fileToEncryptDTO.getFileId() + fileToEncryptDTO.getFileExtension(), fileToEncryptDTO);
        //save details for decrypt
        this.secretService.saveCryptoComponents(cryptoComponents);
        //update file status
        this.fileService.updateFile(fileToEncryptDTO);
    }

    @GetMapping("/encryptedFiles")
    public List<FileToEncrypt> getAllFileModel() {
        return this.fileService.getAllFileModel();
    }


}
