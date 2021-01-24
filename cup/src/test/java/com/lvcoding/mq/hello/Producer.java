package com.lvcoding.mq.hello;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class Producer {

    public static final String QUEUE_NAME = "hello.queue";

    public static void main(String[] args) throws Exception {
        // 获取mq连接
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();

        // 获取channel
        Channel channel = connection.createChannel();

        // 新建queue
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 生产消息
        String msg = "hello world";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        System.out.println("生产了消息 " + msg);

        // 关闭资源
        channel.close();
        connection.close();
    }
}
