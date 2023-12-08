package com.storageservice.storageservice.controller;

import com.storageservice.storageservice.model.AuthorInput;
import com.storageservice.storageservice.producer.Producer;
import com.storageservice.storageservice.repository.AuthorInputRepository;
import com.storageservice.storageservice.service.AuthorInputService;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
}
