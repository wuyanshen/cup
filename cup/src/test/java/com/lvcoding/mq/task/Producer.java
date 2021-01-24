package com.lvcoding.mq.task;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class Producer {

    public static final String QUEUE_NAME = "task.queue";

    public static void main(String[] args) throws Exception {
        // 获取mq连接
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();

        // 获取channel
        Channel channel = connection.createChannel();

        // 新建queue
        boolean durable = true; // 是否持久化消息,rabbitmq宕机后重启消息不丢失
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);


        // 生产消息
        for (int i = 0; i < 5; i++) {
            String msg = String.join(" ", args);
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes("UTF-8"));
            System.out.println("生产了消息 " + msg);
        }

        // 关闭资源
        channel.close();
        connection.close();
    }
}
