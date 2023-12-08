package com.catalogservice.catalogservice.repository;

import com.catalogservice.catalogservice.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    boolean existsByAuthorname(String authorname);
    List<Author> findByAuthorname(String authorname);
}
