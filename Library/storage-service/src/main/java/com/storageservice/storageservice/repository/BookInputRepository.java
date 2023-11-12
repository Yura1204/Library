package com.storageservice.storageservice.repository;

import com.storageservice.storageservice.model.BookInput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookInputRepository extends JpaRepository<BookInput, Long> {
    BookInput save(BookInput bookInput);
}