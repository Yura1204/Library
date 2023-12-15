package com.storageservice.storageservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.storageservice.storageservice.model.AuthorInput;
import com.storageservice.storageservice.producer.Producer;
import com.storageservice.storageservice.repository.AuthorInputRepository;
import com.storageservice.storageservice.service.AuthorInputService;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/authors")
public class AuthorProcessingController {
    private AuthorInputRepository authorInputRepository;
    private AuthorInputService authorInputService;
    private final Producer producer;

    public AuthorProcessingController(AuthorInputService authorInputService, Producer producer) {
        this.authorInputService = authorInputService;
        this.producer = producer;
    }

    @SneakyThrows
    @PostMapping("/processAuthorInput")
    public ResponseEntity<String> processAuthorInput(
            @RequestParam("authorname") String authorname,
            @RequestParam("biography") String biography
    ) {
        AuthorInput authorInput = new AuthorInput();
        authorInput.setAuthorname(authorname);
        authorInput.setBiography(biography);

        boolean success = authorInputService.processAuthorInput(authorInput);

        if (success) {
            producer.sendAuthorInputEvent(authorInput);
            System.out.println("Send order to kafka");
            return ResponseEntity.ok("Author input processed successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to process author input");
        }
    }

    @DeleteMapping("/deleteAuthor")
    public ResponseEntity<String> deleteAuthor(
            @RequestParam Long id,
            @RequestParam("authorname") String authorname,
            @RequestParam("biography") String biography
    ) throws JsonProcessingException {
        boolean success = authorInputService.deleteAuthorById(id);

        if(success) {
            AuthorInput deletedAuthor = new AuthorInput();
            deletedAuthor.setAuthor_id(id);
            deletedAuthor.setAuthorname(authorname);
            deletedAuthor.setBiography(biography);

            producer.sendAuthorDeletionEvent(deletedAuthor);
            System.out.println("Send a delete order to kafka");
            return ResponseEntity.ok("Author deleted successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to delete author");
        }
    }
}
