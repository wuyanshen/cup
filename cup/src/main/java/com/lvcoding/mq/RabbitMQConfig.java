/*
 *
 *
 *        Copyright (c) 2018-2021, wuyanshen All rights reserved.
 *
 *    Redistribution and use in source and binary forms, with or without
 *    modification, are permitted provided that the following conditions are met:
 *
 *    Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *    Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *    Neither the name of the lvcoding.com developer nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *    Author: wuyanshen
 *
 *
 */

package com.lvcoding.mq;

// import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
public class RabbitMQConfig {

    // /**
    //  * 声明交换机
    //  */
    // @Bean
    // public Exchange topicExchange() {
    //     return ExchangeBuilder.topicExchange("email_exchange").durable(true).build();
    // }
    //
    // /**
    //  * 声明队列
    //  */
    // @Bean
    // public Queue topicQueue() {
    //     return new Queue("email_queue");
    // }
    //
    // /**
    //  * 绑定交换机和队列
    //  */
    // @Bean
    // public Binding exchangeBindQueue(@Qualifier("topicQueue") Queue queue, @Qualifier("topicExchange") Exchange exchange) {
    //     return BindingBuilder.bind(queue).to(exchange).with("info.#.email.#").noargs();
    // }
}
