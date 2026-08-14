package com.kkh.todoapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class MyKafkaConsumerImpl implements MyKafkaConsumer{

    
    private static final Logger logger = LoggerFactory.getLogger(MyKafkaConsumerImpl.class);

    @KafkaListener(topics="topic1", groupId = "foo")
    @Override
    public void consume(String message) {
        logger.info("consumed message: [{}]", message);
        
    }
}
