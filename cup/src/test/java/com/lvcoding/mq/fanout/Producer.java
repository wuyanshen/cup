package com.lvcoding.mq.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {

    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception{
        // 获取mq连接
        ConnectionFactory factory = new ConnectionFactory();
        // 注意我们可以使用try-with-resources语句，因为Connection和Channel都实现了java.io.Closeable。这样，我们无需在代码中显式关闭它们
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()){

            // 创建交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

            for (int i = 0; i < 5; i++) {
                // 生产消息
                String msg = "this is a log info ... " + i;
                channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes("UTF-8"));
                System.out.println("生产了消息 " + msg);
            }
        }
    }
}
