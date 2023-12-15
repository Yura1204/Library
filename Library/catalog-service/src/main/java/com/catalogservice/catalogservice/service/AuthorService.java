package com.catalogservice.catalogservice.service;

import com.catalogservice.catalogservice.dto.AuthorDTO;
import com.catalogservice.catalogservice.model.Author;
import com.catalogservice.catalogservice.model.CatalogAuthorInput;
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

    public boolean authorExists(String authorname) {
        // Логика проверки наличия автора в базе данных
        return authorRepository.existsByAuthorname(authorname);
    }

    public Author getAuthor(String authorname) {
        List<Author> authors = authorRepository.findByAuthorname(authorname);
        if(authors.isEmpty()) {
            throw new RuntimeException("Error: Author not found - " + authorname);
        }
        return authors.get(0);
    }

    public void addAuthorToDatabase(CatalogAuthorInput catalogAuthorInput) {
        Author author = new Author();
        author.setAuthorname(catalogAuthorInput.getAuthorname());
        author.setBiography(catalogAuthorInput.getBiography());
        authorRepository.save(author);
        System.out.println("Author added to the database");
    }

    public void deleteAuthorByName(String authorname) {
        List<Author> authorsToDelete = authorRepository.findByAuthorname(authorname);

        if (!authorsToDelete.isEmpty()) {
            Author authorToDelete = authorsToDelete.get(0);
            authorRepository.delete(authorToDelete);
            System.out.println("Author deleted by name: " + authorname);
        } else {
            System.out.println("Author not found by name: " + authorname);
        }
    }

    private Author convertToEntity(AuthorDTO authorDTO) {
        return Author.builder()
                .author_id(authorDTO.getAuthorId())
                .authorname(authorDTO.getAuthorname())
                .biography(authorDTO.getBiography())
                .build();
    }
}

