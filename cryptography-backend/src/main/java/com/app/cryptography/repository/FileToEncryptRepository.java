package com.app.cryptography.repository;

import com.app.cryptography.model.FileToEncrypt;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileToEncryptRepository extends MongoRepository<FileToEncrypt, String> {
}
