package com.app.cryptography.service;

import com.app.cryptography.dto.FileEncryptDTO;
import com.app.cryptography.model.CryptoComponents;

import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface EncryptService {

    CryptoComponents encrypt(String filePath, FileEncryptDTO fileEncryptDTO) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException;
}
