package com.storageservice.storageservice.repository;

import com.storageservice.storageservice.model.StoredAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoredAuthorRepository extends JpaRepository<StoredAuthor, Long> {
    Optional<StoredAuthor> findByAuthorname(String authorname);
}

