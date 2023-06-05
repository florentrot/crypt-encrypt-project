package com.app.cryptography.service.impl;

import com.app.cryptography.dto.FileDecryptDTO;
import com.app.cryptography.model.CryptoComponents;
import com.app.cryptography.model.DecryptedFile;
import com.app.cryptography.repository.CryptoComponentsRepository;
import com.app.cryptography.repository.DecryptedFileRepository;
import com.app.cryptography.repository.EncryptedFileRepository;
import com.app.cryptography.service.DecryptService;
import com.app.cryptography.service.FileService;
import com.app.cryptography.service.common.ConvertHexAndBytesService;
import com.app.cryptography.service.common.SecureRandomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
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
public class DecryptServiceImpl implements DecryptService {

    @Autowired
    CryptoComponentsRepository cryptoComponentsRepository;

    @Autowired
    EncryptedFileRepository encryptedFileRepository;

    @Autowired
    DecryptedFileRepository decryptedFileRepository;

    @Autowired
    FileService fileService;

    @Value("${encrypted.path}")
    private String encryptPath;

    @Value("${decrypted.path}")
    private String decryptPath;

    private static final String ALGORITHM = "AES";
    private static final String CIPHER = "AES/CBC/PKCS5PADDING";


    public DecryptedFile decrypt(String filePath, FileDecryptDTO fileDecryptDTO) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IOException {
        DecryptedFile decryptedFile = new DecryptedFile();

        //1. create NewSecureRandom object
        SecureRandomService nsr = new SecureRandomService();
        nsr.generateSecureRandom();

        //2. get key and iv
        String keyString = fileDecryptDTO.getKeyNo();
        String fileId = fileDecryptDTO.getFileId();
        String ivString = "";


        CryptoComponents cc = this.cryptoComponentsRepository.findById(fileId).get();
        if (!cc.getKeyNo().equals(keyString)) {
            decryptedFile = null;
        } else {
            ivString = cc.getIV();
        }

        if (decryptedFile != null) {
            //3. hex to byte[]
            byte[] byteKey = new ConvertHexAndBytesService(keyString).hexToBytes();
            byte[] byteIv = new ConvertHexAndBytesService(ivString).hexToBytes();

            //4. add values to nsr object
            nsr.getCryptoComponents().put("iv", byteIv);
            nsr.getCryptoComponents().put("key", byteKey);

            //5. create IvParameterSpec and SecretKeySpec objects
            IvParameterSpec iv = new IvParameterSpec(nsr.getCryptoComponents().get("iv"));
            SecretKeySpec skeySpec = new SecretKeySpec(nsr.getCryptoComponents().get("key"), ALGORITHM);

            //3. define the path
            File file = new File(filePath);
            decryptedFile = new DecryptedFile(file);


            //3.1 get file ext and file name
            String ext = this.encryptedFileRepository.findById(fileDecryptDTO.getFileId()).get().getFileExtension();
            String fileName = fileDecryptDTO.getFileId();

            //6. create cipher
            Cipher cipher = Cipher.getInstance(CIPHER);
            try {
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            } catch (Exception e) {
                decryptedFile = null;
            }

            //6. define path
            final Path decryptDir = Paths.get(decryptPath);
            final Path encryptDir = Paths.get(encryptPath);

            //7. decrypt
            final Path encryptedFilePath = encryptDir.resolve(decryptedFile.getFileName());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            final Path decryptedPath = decryptDir.resolve(fileName + ext);
            try (InputStream encryptedData = Files.newInputStream(encryptedFilePath);
                 CipherInputStream decryptStream = new CipherInputStream(encryptedData, cipher);
                 OutputStream decryptedOut = Files.newOutputStream(decryptedPath)) {

                final byte[] bytes = new byte[1024];
                for (int length = decryptStream.read(bytes); length != -1; length = decryptStream.read(bytes)) {
                    decryptedOut.write(bytes, 0, length);
                }
            } catch (Exception ex) {
                System.out.println("Ops!");
            }
            this.decryptedFileRepository.save(this.convertForDB(decryptedFile));
            return decryptedFile;
        } else {
            return null;
        }
    }

    public DecryptedFile convertForDB(DecryptedFile decryptedFile) {
        DecryptedFile decryptedFileForDB = new DecryptedFile();

        int dotIndex = decryptedFile.getFileName().lastIndexOf('.');
        decryptedFileForDB.setFileId(decryptedFile.getFileName().substring(0, dotIndex));
        String realFileName = this.encryptedFileRepository.findById(decryptedFileForDB.getFileId()).get().getFileName();
        decryptedFileForDB.setFileName(realFileName);
        decryptedFileForDB.setFileStatus("DECRYPTED");
        return decryptedFileForDB;
    }
}
