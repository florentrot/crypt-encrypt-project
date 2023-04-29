package com.app.cryptography.controller;

import com.app.cryptography.dto.DecryptComponentsDTO;
import com.app.cryptography.service.DecryptService;
import com.app.cryptography.service.FileService;
import com.app.cryptography.service.common.JsonRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("")
public class DecryptController {

    @Autowired
    DecryptService decryptService;

    @Autowired
    FileService fileService;


    @PostMapping("/decrypt")
    public void decryptFile(@RequestParam("file_upload") MultipartFile file_upload,
                            @RequestParam("decrypt_model") String decryptMessage) throws InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        DecryptComponentsDTO decryptComponentsDTO = (DecryptComponentsDTO) JsonRequestService.fromJsonMethod(decryptMessage, DecryptComponentsDTO.class);
        this.decryptService.decrypt(file_upload, "src/main/resources/enc-files/" + decryptComponentsDTO.getFileId() + ".enc", decryptComponentsDTO);
       // this.fileService.updateFile(fileToEncryptDTO);
    }

    @GetMapping("/decryptedFiles")
    public List<String> getAllFileModel() {
        return new ArrayList<>();
    }
}
