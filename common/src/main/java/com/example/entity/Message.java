package com.example.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Message {
    private String id;
    private String content;
    private String type;
    private String producerInstance;
    private String createdAt;
    private String consumerInstance;

    public Message() {
    }

    public Message(String content, String type, String createdBy ){
        this.content = content;
        this.type = type;
        this.producerInstance = createdBy;
    }
}
