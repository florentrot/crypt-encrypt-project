package com.app.cryptography.service;

import com.app.cryptography.dto.FileToEncryptDTO;
import com.app.cryptography.model.CryptoComponents;

import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface CryptoService {

    CryptoComponents encrypt(String filePath, FileToEncryptDTO fileToEncryptDTO) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException;
}
