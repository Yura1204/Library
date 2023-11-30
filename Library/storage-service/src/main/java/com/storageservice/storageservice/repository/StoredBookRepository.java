package com.storageservice.storageservice.repository;

import com.storageservice.storageservice.model.StoredBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoredBookRepository extends JpaRepository<StoredBook, Long> {
    StoredBook save(StoredBook storedBook);
}

