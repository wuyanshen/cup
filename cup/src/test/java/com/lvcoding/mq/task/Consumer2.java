package com.lvcoding.mq.task;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer2 {

    public static final String QUEUE_NAME = "task.queue";

    public static void main(String[] args) throws Exception{
        // 获取mq连接
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();

        // 获取channel
        Channel channel = connection.createChannel();
        // 一次只接受一个未确认任务
        channel.basicQos(1);



        // 消费消息
        boolean autoAck = false; // 将自动确认消息关闭
        channel.basicConsume(QUEUE_NAME, autoAck, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    doWork(new String(body));
                } finally {
                    System.out.println("消费了消息 + " + new String(body));
                    // 手动确认消息
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        });
    }

    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
