package com.catalogservice.catalogservice.service;

import com.catalogservice.catalogservice.dto.BookDTO;
import com.catalogservice.catalogservice.model.Book;
import com.catalogservice.catalogservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id).orElse(null);
        return (book != null) ? convertToDTO(book) : null;
    }

    public List<BookDTO> searchBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitleContaining(title);
        return books.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<BookDTO> searchBooksByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthor_NameContaining(author);
        return books.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Методы преобразования с использованием @Builder
    public BookDTO convertToDTO(Book book) {
        return BookDTO.builder()
                .id(book.getBook_id())
                .title(book.getTitle())
                .description(book.getDescription())
                .genre(book.getGenre())
                .publisher(book.getPublisher())
                .year_published(book.getYear_published())
                .fileUrl(book.getFileUrl())
                .build();
    }

    public Book convertToEntity(BookDTO bookDTO) {
        return Book.builder()
                .book_id(bookDTO.getId())
                .title(bookDTO.getTitle())
                .description(bookDTO.getDescription())
                .genre(bookDTO.getGenre())
                .publisher(bookDTO.getPublisher())
                .year_published(bookDTO.getYear_published())
                .fileUrl(bookDTO.getFileUrl())
                .build();
    }

    public void updateEntityFromDTO(Book book, BookDTO bookDTO) {
        book.setTitle(bookDTO.getTitle());
        book.setDescription(bookDTO.getDescription());
        book.setGenre(bookDTO.getGenre());
        book.setPublisher(bookDTO.getPublisher());
        book.setYear_published(bookDTO.getYear_published());
        book.setFileUrl(bookDTO.getFileUrl());
    }

}
