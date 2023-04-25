package com.app.cryptography.repository;

import com.app.cryptography.model.CryptoComponents;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoComponentsRepository extends MongoRepository<CryptoComponents, String> {
}
