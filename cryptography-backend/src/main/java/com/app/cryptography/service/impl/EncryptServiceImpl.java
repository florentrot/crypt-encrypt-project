package com.app.cryptography.service.impl;

import com.app.cryptography.dto.FileToEncryptDTO;
import com.app.cryptography.model.CryptoComponents;
import com.app.cryptography.model.FileToEncrypt;
import com.app.cryptography.service.EncryptService;
import com.app.cryptography.service.common.ConvertHexAndBytesService;
import com.app.cryptography.service.common.NewSecureRandomService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class EncryptServiceImpl implements EncryptService {


    @Value("${encrypted.path}")
    private String encryptPath;
    private static final String ALGORITHM = "AES";
    private static final String CIPHER = "AES/CBC/PKCS5PADDING";


    public CryptoComponents encrypt(String filePath, FileToEncryptDTO fileToEncryptDTO) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {

        //1. create NewSecureRandom object
        NewSecureRandomService nsr = new NewSecureRandomService();
        nsr.generateSecureRandom();

        //2. get iv and key hexa
        CryptoComponents ec = new CryptoComponents();
        ec.setFileId(fileToEncryptDTO.getFileId());
        ec.setKeyNo(new ConvertHexAndBytesService(nsr.getCryptoComponents().get("key")).bytesToHex());
        ec.setIV(new ConvertHexAndBytesService(nsr.getCryptoComponents().get("iv")).bytesToHex());

        //3. path to file
        File file = new File(filePath);
        FileToEncrypt fileToEncrypt = new FileToEncrypt(file);


        //4. create IvParameterSpec and SecretKeySpec
        IvParameterSpec iv = new IvParameterSpec(nsr.getCryptoComponents().get("iv"));
        SecretKeySpec skeySpec = new SecretKeySpec(nsr.getCryptoComponents().get("key"), ALGORITHM);


        //5. create cipher
        Cipher cipher = Cipher.getInstance(CIPHER);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);


        final Path criptDir = Paths.get(encryptPath);

        //7. encrypt
        final Path encryptedFilePath = criptDir.resolve(fileToEncrypt.getFileName().substring(0, fileToEncrypt.getFileName().indexOf(".")) + ".enc");
        try (InputStream fin = Files.newInputStream(Paths.get(fileToEncrypt.getFilePath()));
             OutputStream fout = Files.newOutputStream(encryptedFilePath);
             CipherOutputStream cipherOut = new CipherOutputStream(fout, cipher) {
             }) {
            final byte[] bytes = new byte[1024];
            for (int length = fin.read(bytes); length != -1; length = fin.read(bytes)) {
                cipherOut.write(bytes, 0, length);
            }
        } catch (IOException e) {
            System.out.println("error" + e);
        }
        return ec;
    }

}
