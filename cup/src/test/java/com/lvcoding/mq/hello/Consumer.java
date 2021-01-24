package com.lvcoding.mq.hello;

import com.rabbitmq.client.*;

import java.io.IOException;


public class Consumer {

    public static final String QUEUE_NAME = "hello.queue";

    public static void main(String[] args) throws Exception {
        // 获取mq连接
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();

        // 获取channel
        Channel channel = connection.createChannel();

        // 消费消息
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费了消息 + " + new String(body));
            }
        });
    }
}
