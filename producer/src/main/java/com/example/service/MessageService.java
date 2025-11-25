package com.example.service;

import com.example.entity.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MessageService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${app.instance.id}")
    private String instanceId;

    public Message sendMessage(String content, String type) {
        Message message = new Message(content, type, "producer-" + instanceId);
        message.setId(UUID.randomUUID().toString());
        message.setCreatedAt(LocalDateTime.now().toString());

        System.out.println("🟡 Producer " + instanceId + " sending message: " + message.getId());

        rabbitTemplate.convertAndSend("messages.exchange", "messages.key", message);

        return message;
    }
}
