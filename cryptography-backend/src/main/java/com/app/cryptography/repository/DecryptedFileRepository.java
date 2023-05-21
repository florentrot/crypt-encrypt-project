package com.app.cryptography.repository;

import com.app.cryptography.model.DecryptedFile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecryptedFileRepository extends MongoRepository<DecryptedFile, String> {
}
