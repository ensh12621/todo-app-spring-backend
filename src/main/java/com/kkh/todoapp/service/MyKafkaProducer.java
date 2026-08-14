package com.kkh.todoapp.service;

public interface MyKafkaProducer {
    void sendMessage(String msg);
}
