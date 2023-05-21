package com.app.cryptography.repository;

import com.app.cryptography.model.EncryptedFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncryptedFileRepository extends MongoRepository<EncryptedFile, String> {
}
