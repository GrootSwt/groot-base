package com.groot.base.log.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitConfig {

    @Value(value = "${groot.rabbit-mq.queue-name}")
    private String queueName;

    @Value(value = "${groot.rabbit-mq.exchange-name}")
    private String exchangeName;

    @Value(value = "${groot.rabbit-mq.routing-key}")
    private String routingKey;

    @Bean
    public Queue getDirectQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public DirectExchange getDirectExchange() {
        return new DirectExchange(exchangeName, true, false);
    }

    @Bean
    public Binding getBinding() {
        return BindingBuilder.bind(getDirectQueue()).to(getDirectExchange()).with(routingKey);
    }
}
