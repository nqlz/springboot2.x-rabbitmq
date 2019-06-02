package com.nqlz.consumer.config;

import com.nqlz.repository.entity.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class RabbitRecerver {


   /* @RabbitHandler
    public void onMessage(Message message, Channel channel) throws IOException {
        System.err.println("消费端：----" + message.getPayload());

        Long tag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);

        channel.basicAck(tag, false);
    }*/
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = myQueue.name, durable = myQueue.durable, autoDelete = myQueue.autoDelete),
            exchange = @Exchange(value = MyExchange.name, type = MyExchange.type, ignoreDeclarationExceptions = MyExchange.ignoreDeclarationExceptions),
            key = "spring.*"
    ))
    @RabbitHandler
    public void onOrderMessage(@Payload Order order, @Headers Map headers,Channel channel) throws IOException {
        System.err.println("消费端order：----" + order.getId()+"---"+order.getName());
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(tag, false);
    }

}
