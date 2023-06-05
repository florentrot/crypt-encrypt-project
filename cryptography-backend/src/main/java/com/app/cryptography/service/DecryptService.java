package com.app.cryptography.service;

import com.app.cryptography.dto.FileDecryptDTO;
import com.app.cryptography.model.DecryptedFile;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface DecryptService {

     DecryptedFile decrypt(String filePath, FileDecryptDTO fileDecryptDTO) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException;
}
