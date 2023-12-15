package com.storageservice.storageservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.storageservice.storageservice.model.BookInput;
import com.storageservice.storageservice.producer.Producer;
import com.storageservice.storageservice.repository.BookInputRepository;
import com.storageservice.storageservice.service.BookInputService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/books")
public class BookProcessingController {
    private BookInputRepository bookInputRepository;
    private final BookInputService bookInputService;
    private BookInput bookInput;
    private final Producer producer;

    public BookProcessingController(BookInputRepository bookInputRepository, BookInputService bookInputService, BookInput bookInput, Producer producer) {
        this.bookInputRepository = bookInputRepository;
        this.bookInputService = bookInputService;
        this.bookInput = bookInput;
        this.producer = producer;
    }

    @Autowired
    public BookProcessingController(BookInputService bookInputService, Producer producer) {
        this.bookInputService = bookInputService;
        this.producer = producer;
    }

    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("bookInput", new BookInput());
        return "addBookForm";
    }


    @SneakyThrows
    @PostMapping("/processBookInput")
    public ResponseEntity<String> processBookInput(
            @RequestParam("title") String title,
            @RequestParam("authorname") String authorname,
            @RequestParam("description") String description,
            @RequestParam("genre") String genre,
            @RequestParam("publisher") String publisher,
            @RequestParam("year_published") int year_published,
            @RequestPart("book_content") MultipartFile book_content
    ) {
        // Создать объект BookInput с полученными данными
        BookInput bookInput = new BookInput();
        bookInput.setTitle(title);
        bookInput.setAuthorname(authorname);
        bookInput.setDescription(description);
        bookInput.setGenre(genre);
        bookInput.setPublisher(publisher);
        bookInput.setYearPublished(year_published);
        bookInput.setBookContent(book_content.getBytes());

        boolean success = bookInputService.processBookInput(bookInput);

        if (success) {
            producer.sendBookInputEvent(bookInput);
            System.out.println("Send order to kafka");
            return ResponseEntity.ok("Book input processed successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to process book input");
        }
    }

    @DeleteMapping("/deleteBookById")
    public ResponseEntity<String> deleteBookById(
            @RequestParam Long id,
            @RequestParam("title") String title,
            @RequestParam("authorname") String authorname,
            @RequestParam("description") String description,
            @RequestParam("genre") String genre,
            @RequestParam("publisher") String publisher,
            @RequestParam("year_published") int year_published
    ) throws JsonProcessingException {
        // Попытаться удалить книгу
        boolean success = bookInputService.deleteBookById(id);

        if (success) {
            // Создать объект BookInput с информацией об удаленной книге
            BookInput deletedBook = new BookInput();
            deletedBook.setBook_input_id(id);
            deletedBook.setTitle(title);
            deletedBook.setAuthorname(authorname);
            deletedBook.setDescription(description);
            deletedBook.setGenre(genre);
            deletedBook.setPublisher(publisher);
            deletedBook.setYearPublished(year_published);

            // Отправить информацию об удаленной книге в Kafka
            producer.sendBookDeletionEvent(deletedBook);
            System.out.println("Send a delete order to kafka");
            return ResponseEntity.ok("Book deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete book");
        }
    }

}