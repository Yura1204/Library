package com.catalogservice.catalogservice.service;

import com.catalogservice.catalogservice.dto.BookDTO;
import com.catalogservice.catalogservice.model.Author;
import com.catalogservice.catalogservice.model.Book;
import com.catalogservice.catalogservice.model.CatalogBookInput;
import com.catalogservice.catalogservice.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
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

    @Transactional
    public void addBookToDatabase(CatalogBookInput catalogBookInput) {
        // Логика добавления книги в базу данных
        Author author = authorService.getAuthor(catalogBookInput.getAuthorname());

        // Проверка, существует ли автор в базе данных
        if (author != null) {
            Book book = new Book();
            book.setTitle(catalogBookInput.getTitle());
            book.setDescription(catalogBookInput.getDescription());
            book.setGenre(catalogBookInput.getGenre());
            book.setPublisher(catalogBookInput.getPublisher());
            book.setYear_published(catalogBookInput.getYearPublished());
            book.setBookContent(catalogBookInput.getBookContent());
            book.setAuthor(author);
            bookRepository.save(book);
            System.out.println("Book added to the database.");
        } else {
            // Обработка случая, когда автор не найден
            System.out.println("Author not found.");
        }
    }

    // Метод для удаления книги по имени
    public void deleteBookByName(String bookName) {
        List<Book> booksToDelete = bookRepository.findByTitleContaining(bookName);

        if (!booksToDelete.isEmpty()) {
            Book bookToDelete = booksToDelete.get(0);
            bookRepository.delete(bookToDelete);
            System.out.println("Book deleted by name: " + bookName);
        } else {
            System.out.println("Book not found by name: " + bookName);
        }
    }



    public byte[] getFileContent(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));

        // Получаем массив байтов из bookContent
        return book.getBookContent();
    }

    // Методы преобразования с использованием @Builder
    public BookDTO convertToDTO(Book book) {
        return BookDTO.builder()
                .id(book.getBook_id())
                .title(book.getTitle())
                .authorName(book.getAuthor_id().getAuthorname())
                .description(book.getDescription())
                .genre(book.getGenre())
                .publisher(book.getPublisher())
                .year_published(book.getYear_published())
                .fileUrl(book.getFileUrl())
                .author_id(book.getAuthor_id().getAuthor_id())
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
