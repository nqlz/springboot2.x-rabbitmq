package com.nqlz.consumer.config;

public interface MyExchange {
    String name = "topic001";
    String durable = "true";
    String autoDelete = "false";
    String ignoreDeclarationExceptions = "true";
    String type = "topic";
}
