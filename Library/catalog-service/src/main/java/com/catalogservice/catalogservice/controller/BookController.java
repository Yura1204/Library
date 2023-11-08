package com.catalogservice.catalogservice.controller;

import com.catalogservice.catalogservice.dto.BookDTO;
import com.catalogservice.catalogservice.model.Book;
import com.catalogservice.catalogservice.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        if (book != null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/searchByTitle/{title}")
    public ResponseEntity<List<BookDTO>> searchBooksByTitle(@PathVariable String title) {
        List<BookDTO> books = bookService.searchBooksByTitle(title);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/searchByAuthor/{authorname}")
    public ResponseEntity<List<BookDTO>> searchBooksByAuthor(@PathVariable String authorname) {
        List<BookDTO> books = bookService.searchBooksByAuthor(authorname);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
