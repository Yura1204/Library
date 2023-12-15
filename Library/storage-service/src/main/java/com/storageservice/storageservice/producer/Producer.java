package com.storageservice.storageservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.storageservice.storageservice.model.AuthorInput;
import com.storageservice.storageservice.model.BookInput;
import com.storageservice.storageservice.service.KafkaMessagingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Producer {

    private final KafkaMessagingService kafkaMessagingService;
    private final ModelMapper modelMapper;


    public BookInput sendBookInputEvent(BookInput bookInput) throws JsonProcessingException {
        kafkaMessagingService.sendBookInput(modelMapper.map(bookInput, BookInput.class));
        log.info("Send order from producer {}", bookInput);
        return bookInput;
    }

    public AuthorInput sendAuthorInputEvent(AuthorInput authorInput) throws JsonProcessingException {
        kafkaMessagingService.sendAuthorInput(modelMapper.map(authorInput, AuthorInput.class));
        log.info("Send order from producer {}", authorInput);
        return authorInput;
    }

    public void sendBookDeletionEvent(BookInput bookInput) throws JsonProcessingException {
        kafkaMessagingService.sendDeletedBook(bookInput);
        log.info("Send book deletion event from producer: {}", bookInput);
    }

    public void sendAuthorDeletionEvent(AuthorInput authorInput) throws JsonProcessingException {
        kafkaMessagingService.sendDeletedAuthor(authorInput);
        log.info("Send author deletion event from producer: {}", authorInput);
    }
}