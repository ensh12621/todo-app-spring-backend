package com.kkh.todoapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MyKafkaProducerImpl implements MyKafkaProducer{
    private static final String TOPIC = "topic1";
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger logger = LoggerFactory.getLogger(MyKafkaProducerImpl.class);

    public MyKafkaProducerImpl(KafkaTemplate<String, String> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(String message){
        kafkaTemplate.send(TOPIC, message);
        logger.info("Produced message: [{}]", message);
    }
}
