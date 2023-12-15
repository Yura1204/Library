package com.catalogservice.catalogservice.config;

import com.catalogservice.catalogservice.model.CatalogAuthorInput;
import com.catalogservice.catalogservice.model.CatalogBookInput;
import com.catalogservice.catalogservice.service.AuthorService;
import com.catalogservice.catalogservice.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaConsumer {
    private final AuthorService authorService;
    private final BookService bookService;
    public KafkaConsumer(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @KafkaListener(topics = "${topic.send-order}", groupId = "${spring.kafka.consumer.group-id-book}")
    public void listenBook(ConsumerRecord<String, String> record) {
        String inputJson = record.value();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CatalogBookInput catalogBookInput = objectMapper.readValue(inputJson, CatalogBookInput.class);
            if (authorService.authorExists(catalogBookInput.getAuthorname())) {
                bookService.addBookToDatabase(catalogBookInput);
            } else {
                System.out.println("Error: Author does not exist.");
            }
        } catch (Exception e) {
            System.out.println("Error processing BookInput from Kafka: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "${topic.send-order}", groupId = "${spring.kafka.consumer.group-id-author}")
    public void listenAuthor(ConsumerRecord<String, String> record) {
        String inputJson = record.value();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CatalogAuthorInput catalogAuthorInput = objectMapper.readValue(inputJson, CatalogAuthorInput.class);
            if (authorService.authorExists(catalogAuthorInput.getAuthorname())) {
                System.out.println("Error: An author with this name already exists.");
            } else {
                authorService.addAuthorToDatabase(catalogAuthorInput);
            }
        } catch (Exception e) {
            System.out.println("Error processing AuthorInput from Kafka: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "${topic.delete-order}", groupId = "${spring.kafka.consumer.group-id-delete-book}")
    public void listenDeleteBookEvent(ConsumerRecord<String, String> record) {
        try {
            String inputJson = record.value();
            ObjectMapper objectMapper = new ObjectMapper();
            CatalogBookInput deleteEvent = objectMapper.readValue(inputJson, CatalogBookInput.class);

            bookService.deleteBookByName(deleteEvent.getTitle());
            System.out.println("Book deleted");
        } catch (Exception e) {
            System.out.println("Error processing DeleteEvent from Kafka: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "${topic.delete-order}", groupId = "${spring.kafka.consumer.group-id-delete-author}")
    public void listenDeleteAuthorEvent(ConsumerRecord<String, String> record) {
        try {
            String inputJson = record.value();
            ObjectMapper objectMapper = new ObjectMapper();
            CatalogAuthorInput deleteEvent = objectMapper.readValue(inputJson, CatalogAuthorInput.class);

            authorService.deleteAuthorByName(deleteEvent.getAuthorname());
            System.out.println("Author deleted");
        } catch (Exception e) {
            System.out.println("Error processing DeleteEvent from Kafka: " + e.getMessage());
        }
    }

}
