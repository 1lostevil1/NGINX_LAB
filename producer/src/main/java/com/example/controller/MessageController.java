package com.example.controller;

import com.example.entity.Message;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Value("${app.instance.id}")
    private String instanceId;

    @PostMapping
    public Map<String, Object> sendMessage(@RequestBody Map<String, String> request) {
        String content = request.get("content");
        String type = request.get("type");

        if (content == null || type == null) {
            return Map.of("error", "Content and type are required");
        }

        Message message = messageService.sendMessage(content, type);

        return Map.of(
                "status", "Message sent to RabbitMQ",
                "producerInstance", instanceId,
                "message", Map.of(
                        "id", message.getId(),
                        "content", message.getContent(),
                        "type", message.getType(),
                        "createdAt", message.getCreatedAt()
                )
        );
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
                "status", "UP",
                "service", "producer",
                "instanceId", instanceId
        );
    }

    @GetMapping("/info")
    public Map<String, String> info() {
        return Map.of(
                "service", "producer",
                "instanceId", instanceId,
                "message", "Handled by producer instance " + instanceId
        );
    }
}
