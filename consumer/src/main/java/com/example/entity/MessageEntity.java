package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
public class MessageEntity {
    @Id
    private String id;

    @Column(length = 1000)
    private String content;

    private String type;
    private String producerInstance;
    private String consumerInstance;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
}
