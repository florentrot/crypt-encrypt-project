package com.app.cryptography.service;

import com.app.cryptography.dto.DecryptComponentsDTO;
import com.app.cryptography.model.DecryptedFile;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface DecryptService {

     DecryptedFile decrypt(MultipartFile multipartFile, String filePath, DecryptComponentsDTO decryptComponentsDTO) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException;

}
