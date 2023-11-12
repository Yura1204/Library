package com.storageservice.storageservice.service;

import com.storageservice.storageservice.model.BookInput;
import com.storageservice.storageservice.repository.BookInputRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class BookInputService {
    private BookInputRepository bookInputRepository;

    public BookInputService(BookInputRepository bookInputRepository) {
        this.bookInputRepository = bookInputRepository;
    }

    @Transactional
    public boolean processBookInput(BookInput bookInput) {
        try {
            byte[] fileContent = bookInput.getBookContent();

            // Создать объект BookInput с полученными данными
            BookInput savedBookInput = new BookInput();
            savedBookInput.setTitle(bookInput.getTitle());
            savedBookInput.setAuthorname(bookInput.getAuthorname());
            savedBookInput.setDescription(bookInput.getDescription());
            savedBookInput.setGenre(bookInput.getGenre());
            savedBookInput.setPublisher(bookInput.getPublisher());
            savedBookInput.setYearPublished(bookInput.getYearPublished());
            savedBookInput.setBookContent(fileContent);

            // Добавить запись в book_input
            bookInputRepository.save(savedBookInput);
            return true;
        } catch (Exception e) {
            // Логгирование ошибки
            e.printStackTrace();
            return false;
        }
    }
}