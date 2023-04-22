package com.app.cryptography.repository;

import com.app.cryptography.model.FileModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends MongoRepository<FileModel, String> {
}
