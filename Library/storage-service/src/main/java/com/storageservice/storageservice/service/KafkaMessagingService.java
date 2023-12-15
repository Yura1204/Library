package com.storageservice.storageservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.storageservice.storageservice.model.AuthorInput;
import org.springframework.beans.factory.annotation.Value;
import com.storageservice.storageservice.model.BookInput;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMessagingService {
    private final ObjectMapper objectMapper;

    @Value("${topic.send-order}")
    private String sendClientTopic;

    @Value("${topic.delete-order}")
    private String deleteClientTopic;

    private final KafkaTemplate<String, BookInput> bookInputKafkaTemplate;
    private final KafkaTemplate<String, AuthorInput> authorInputKafkaTemplate;

    public void sendBookInput(BookInput bookInput) throws JsonProcessingException {
        String serializedData = objectMapper.writeValueAsString(bookInput);
        bookInputKafkaTemplate.send(sendClientTopic, String.valueOf(bookInput.getBook_input_id()), bookInput);
    }

    public void sendAuthorInput(AuthorInput authorInput) throws JsonProcessingException {
        String serializedData = objectMapper.writeValueAsString(authorInput);
        authorInputKafkaTemplate.send(sendClientTopic, String.valueOf(authorInput.getAuthor_id()), authorInput);
    }

    public void sendDeletedBook(BookInput deletedBook) {
        bookInputKafkaTemplate.send(deleteClientTopic, String.valueOf(deletedBook.getBook_input_id()), deletedBook);
    }

    public void sendDeletedAuthor(AuthorInput deletedAuthor) {
        authorInputKafkaTemplate.send(deleteClientTopic, String.valueOf(deletedAuthor.getAuthor_id()), deletedAuthor);
    }
}
