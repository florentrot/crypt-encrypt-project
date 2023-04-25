package com.app.cryptography.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Data
@Document
@NoArgsConstructor
public class CryptoComponents {

    @Id
    private String fileId;
    private String keyNo;
    private String IV;

}
