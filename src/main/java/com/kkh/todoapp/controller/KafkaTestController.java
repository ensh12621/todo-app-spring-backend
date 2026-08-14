package com.kkh.todoapp.controller;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kkh.todoapp.service.MyKafkaProducer;

@RequestMapping("/kafka")
@Controller
public class KafkaTestController {
    
    private MyKafkaProducer kafkaProducer;

    private static final Logger logger = LoggerFactory.getLogger(KafkaTestController.class);

    public KafkaTestController(MyKafkaProducer kafkaProducer){
        this.kafkaProducer = kafkaProducer;
    }

    @GetMapping("/test/{param1}")
    public void test1(@PathVariable String param1){
        kafkaProducer.sendMessage(param1);
    }
}
