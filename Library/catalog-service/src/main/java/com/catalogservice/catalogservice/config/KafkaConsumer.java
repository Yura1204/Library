package com.catalogservice.catalogservice.config;

import com.catalogservice.catalogservice.model.CatalogBookInput;
import com.catalogservice.catalogservice.service.AuthorService;
import com.catalogservice.catalogservice.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private final AuthorService authorService;
    private final BookService bookService;
    public KafkaConsumer(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @KafkaListener(topics = "${topic.send-order}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(ConsumerRecord<String, String> record) {
        String bookInputJson = record.value();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CatalogBookInput catalogBookInput = objectMapper.readValue(bookInputJson, CatalogBookInput.class);

            if (authorService.authorExists(catalogBookInput.getAuthorname())) {
                bookService.addBookToDatabase(catalogBookInput);
            } else {
                System.out.println("Error: Author does not exist.");
            }
        } catch (Exception e) {
            System.out.println("Error processing BookInput from Kafka: " + e.getMessage());
        }
    }
}
