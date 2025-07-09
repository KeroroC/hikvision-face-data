package com.huntall.web.controller;

import com.huntall.common.constant.RabbitConstant;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wangpeng
 * @since 2025年06月23日 15:49
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private final RabbitTemplate rabbitTemplate;

    public TestController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/alive")
    public String alive() {
        return "yes";
    }

    @GetMapping("/send")
    public String sendDirectMessage() {
        String id = String.valueOf(UUID.randomUUID());
        String data = "hello rabbitmq";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", id);
        map.put("messageData", data);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend(RabbitConstant.HIK_EXCHANGE_NAME, RabbitConstant.HIK_ID_CARD_ROUTE_KEY, map);
        return "send success";
    }

}
