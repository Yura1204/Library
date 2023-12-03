package com.storageservice.storageservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final KafkaTemplate<String , BookInput> kafkaTemplate;

    public void sendBookInput(BookInput bookInput) throws JsonProcessingException {
        String serializedData = objectMapper.writeValueAsString(bookInput);
        kafkaTemplate.send(sendClientTopic, String.valueOf(bookInput.getBook_input_id()), bookInput);
    }

}
