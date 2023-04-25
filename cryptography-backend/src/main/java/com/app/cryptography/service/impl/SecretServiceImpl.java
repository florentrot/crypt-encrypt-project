package com.app.cryptography.service.impl;

import com.app.cryptography.model.CryptoComponents;
import com.app.cryptography.repository.CryptoComponentsRepository;
import com.app.cryptography.service.SecretService;
import org.springframework.stereotype.Service;

@Service
public class SecretServiceImpl implements SecretService {

    CryptoComponentsRepository cryptoComponentsRepository;

    SecretServiceImpl(CryptoComponentsRepository cryptoComponentsRepository) {
        this.cryptoComponentsRepository = cryptoComponentsRepository;
    }

    @Override
    public void saveCryptoComponents(CryptoComponents cryptoComponents) {
        cryptoComponentsRepository.save(cryptoComponents);
    }
}
