package com.app.cryptography.service;

import com.app.cryptography.dto.FileModelDTO;
import com.app.cryptography.model.CryptoComponents;

public interface SecretService {
    void saveCryptoComponents(CryptoComponents cryptoComponents);
}
