package com.example.restapi.producer;

import com.example.restapi.dtos.MessageDTO;

public interface Producer {
    void produce(String rabbitQueue, MessageDTO messageDTO);
}
