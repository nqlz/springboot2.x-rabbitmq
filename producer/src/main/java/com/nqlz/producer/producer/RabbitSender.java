package com.nqlz.producer.producer;

import com.nqlz.repository.entity.Order;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.logging.Logger;

@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate template;
    private Logger logger;

    final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
        System.out.println("----" + correlationData);
        System.out.println("----" + ack);
        if (!ack) {
            System.out.println("----不确认异常处理。。。。");
        }
    };

    final RabbitTemplate.ReturnCallback returnCallback = (message, replyCode, replyText, exchange, routingKey) -> {
        System.err.println("------return:  exchange:--" + exchange + ",routingKey:---" + routingKey);
        System.out.println("没发出去消息:--" + new String(message.getBody()));
    };

    public void send(Object message, Map properties) throws Exception {
        MessageHeaders mhs = new MessageHeaders(properties);
        Message<Object> targetMessage = MessageBuilder.createMessage(message, mhs);
        template.setConfirmCallback(confirmCallback);
        template.setReturnCallback(returnCallback);
        CorrelationData correlationData=new CorrelationData("1234567890");
        template.convertAndSend("topic001", "spring.hello", targetMessage,correlationData);
    }

    public void sendOrder(Order order) throws Exception {
        template.setConfirmCallback(confirmCallback);
        template.setReturnCallback(returnCallback);
        CorrelationData correlationData=new CorrelationData("1234567890");
        template.convertAndSend("topic001", "spring.hello", order,correlationData);
    }

}
