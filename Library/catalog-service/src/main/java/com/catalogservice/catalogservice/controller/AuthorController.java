package com.catalogservice.catalogservice.controller;

import com.catalogservice.catalogservice.dto.AuthorDTO;
import com.catalogservice.catalogservice.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.getAllAuthors();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long authorId) {
        AuthorDTO author = authorService.getAuthorById(authorId);
        if (author != null) {
            return new ResponseEntity<>(author, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/searchByName/{name}")
    public ResponseEntity<List<AuthorDTO>> searchAuthorsByName(@PathVariable String authorname) {
        List<AuthorDTO> authors = authorService.searchAuthorsByName(authorname);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }
}

