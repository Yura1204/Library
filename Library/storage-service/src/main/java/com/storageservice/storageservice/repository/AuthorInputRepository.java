package com.storageservice.storageservice.repository;

import com.storageservice.storageservice.model.AuthorInput;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorInputRepository extends JpaRepository<AuthorInput, Long> {
    AuthorInput save(AuthorInput authorInput);
}
