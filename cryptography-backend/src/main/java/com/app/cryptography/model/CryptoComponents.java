package com.app.cryptography.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class CryptoComponents {
    @Id
    private String id;
    private String keyNo;
    private String IV;
    private String fileId;

    public CryptoComponents(String keyNo, String iv) {
        this.keyNo = keyNo;
        this.IV = iv;
    }

    public CryptoComponents(){}

}
