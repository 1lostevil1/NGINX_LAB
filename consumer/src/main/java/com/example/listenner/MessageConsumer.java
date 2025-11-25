package com.example.listenner;

import com.example.entity.Message;
import com.example.entity.MessageEntity;
import com.example.repository.MessageRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageConsumer {

    @Autowired
    private MessageRepository messageRepository;

    @Value("${app.instance.id}")
    private String instanceId;

    @RabbitListener(queues = "messages.queue")
    public void receiveMessage(Message message) {
        System.out.println("🔵 Consumer " + instanceId + " received message: " + message.getId());

        // Сохраняем в базу данных
        MessageEntity entity = new MessageEntity();
        entity.setId(message.getId());
        entity.setContent(message.getContent());
        entity.setType(message.getType());
        entity.setProducerInstance(message.getProducerInstance());
        entity.setConsumerInstance(instanceId);
        entity.setCreatedAt(LocalDateTime.parse(message.getCreatedAt()));
        entity.setProcessedAt(LocalDateTime.now());

        messageRepository.save(entity);

        System.out.println("🟢 Consumer " + instanceId + " saved message: " + message.getId());
    }
}
