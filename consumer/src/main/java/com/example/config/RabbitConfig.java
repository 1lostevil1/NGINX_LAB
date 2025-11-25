package com.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public Queue messagesQueue() {
        return new Queue("messages.queue", true);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter(objectMapper);
    }


    @Bean
    public DirectExchange messagesExchange() {
        return new DirectExchange("messages.exchange");
    }

    @Bean
    public Binding binding(Queue messagesQueue, DirectExchange messagesExchange) {
        return BindingBuilder.bind(messagesQueue)
                .to(messagesExchange)
                .with("messages.key");
    }

    // Отключаем падение при отсутствии очереди
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setMissingQueuesFatal(false);
        factory.setAutoStartup(true);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate (ConnectionFactory connectionFactory, MessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}