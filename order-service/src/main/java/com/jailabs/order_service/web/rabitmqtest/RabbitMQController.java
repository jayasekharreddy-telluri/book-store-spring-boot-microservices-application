package com.jailabs.order_service.web.rabitmqtest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class RabbitMQController {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/publish")
    public String publishMessage(@RequestParam String routingKey, @RequestBody String message) {

        // Replace 'orders-exchange' with your exchange name
        rabbitTemplate.convertAndSend("orders-exchange", routingKey, message);
        return "Message sent to RabbitMQ with routing key: " + routingKey;
    }
}
