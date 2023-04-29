package com.app.cryptography.controller;

import com.app.cryptography.dto.DecryptComponentsDTO;
import com.app.cryptography.dto.FileToEncryptDTO;
import com.app.cryptography.service.common.JsonRequestService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("")
public class DecryptController {

    @PostMapping("/decrypt")
    public void decryptFile(@RequestParam("file_upload") MultipartFile file_upload,
                           @RequestParam("decrypt_model") String decryptMessage) throws IOException {
        DecryptComponentsDTO decryptComponentsDTO = (DecryptComponentsDTO) JsonRequestService.fromJsonMethod(decryptMessage, DecryptComponentsDTO.class);
      //  this.decryptService.decrypt(file_upload, decryptComponentsDTO);
    }

    @GetMapping("/decryptedFiles")
    public List<String> getAllFileModel() {
        return new ArrayList<>();
    }
}
