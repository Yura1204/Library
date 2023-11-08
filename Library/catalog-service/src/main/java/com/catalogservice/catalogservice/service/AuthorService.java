package com.catalogservice.catalogservice.service;

import com.catalogservice.catalogservice.dto.AuthorDTO;
import com.catalogservice.catalogservice.model.Author;
import com.catalogservice.catalogservice.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(Long authorId) {
        Author author = authorRepository.findById(authorId).orElse(null);
        return (author != null) ? convertToDTO(author) : null;
    }

    public List<AuthorDTO> searchAuthorsByName(String authorname) {
        List<Author> authors = authorRepository.findByAuthorname(authorname);
        return authors.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AuthorDTO convertToDTO(Author author) {
        return AuthorDTO.builder()
                .authorId(author.getAuthor_id())
                .authorname(author.getAuthorname())
                .biography(author.getBiography())
                .build();
    }

    private Author convertToEntity(AuthorDTO authorDTO) {
        return Author.builder()
                .author_id(authorDTO.getAuthorId())
                .authorname(authorDTO.getAuthorname())
                .biography(authorDTO.getBiography())
                .build();
    }
}

