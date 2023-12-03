package com.storageservice.storageservice.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
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
}