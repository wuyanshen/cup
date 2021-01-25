package com.lvcoding.mq.deadletter;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 使用RabbitMQ的 ”死信队列“ 来实现 “延时队列”
 */
public class Consumer1 {

    private static final String DEAD_LETTER_QUEUE = "lvcoding.dead.letter.queue";
    private static final String DEAD_LETTER_EXCHANGE = "lvcoding.dead.letter.exchange";
    private static final String DEAD_LETTER_ROUTING_KEY = "lvcoding.dead.letter.routing.key";

    public static void main(String[] args) throws Exception {
        final ConnectionFactory factory = new ConnectionFactory();
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();


        // 死信配置
        boolean durable = true; // 是否持久化
        channel.exchangeDeclare(DEAD_LETTER_EXCHANGE, "direct", durable);
        channel.queueDeclare(DEAD_LETTER_QUEUE, durable, false, false, null);
        channel.queueBind(DEAD_LETTER_QUEUE, DEAD_LETTER_EXCHANGE, DEAD_LETTER_ROUTING_KEY);

        // 消费消息
        channel.basicConsume(DEAD_LETTER_QUEUE, durable, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费了消息 " + new String(body, "UTF-8") + " " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }
        });
    }
}
