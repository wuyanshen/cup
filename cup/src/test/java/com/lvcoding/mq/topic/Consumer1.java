package com.lvcoding.mq.topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Consumer1 {

    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        // 获取mq连接
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 创建交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        // 创建队列 (名称随机生成，断开连接后队列自动删除)
        String queue = channel.queueDeclare().getQueue();

        // 绑定交换机和队列
        List<String> strings = Arrays.asList("*.orange.*");
        for (String severity : strings) {
            // 此时severity是队列键
            channel.queueBind(queue, EXCHANGE_NAME, severity);
        }

        System.out.println("等待消息中...，退出请 CTRL+C");

        // 消费消息
        boolean autoAck = true; // 自动确认消息
        channel.basicConsume(queue, autoAck, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费了消息 " + new String(body, "UTF-8"));
            }
        });
    }
}
