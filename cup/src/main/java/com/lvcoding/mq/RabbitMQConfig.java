package com.lvcoding.mq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    /**
     * 声明交换机
     */
    @Bean
    public Exchange topicExchange() {
        return ExchangeBuilder.topicExchange("email_exchange").durable(true).build();
    }

    /**
     * 声明队列
     */
    @Bean
    public Queue topicQueue() {
        return new Queue("email_queue");
    }

    /**
     * 绑定交换机和队列
     */
    @Bean
    public Binding exchangeBindQueue(@Qualifier("topicQueue") Queue queue, @Qualifier("topicExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("info.#.email.#").noargs();
    }
}
