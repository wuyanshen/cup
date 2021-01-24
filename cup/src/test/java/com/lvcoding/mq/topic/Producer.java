package com.lvcoding.mq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Arrays;
import java.util.List;

public class Producer {

    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception{
        // 获取mq连接
        ConnectionFactory factory = new ConnectionFactory();
        // 注意我们可以使用try-with-resources语句，因为Connection和Channel都实现了java.io.Closeable。这样，我们无需在代码中显式关闭它们
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()){

            // 创建交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            // 生产消息
            List<String> strings = Arrays.asList("quick.orange.rabbit", "lazy.orange.elephant", "quick.orange.fox", "lazy.brown.fox", "lazy.pink.rabbit", "quick.brown.fox", "quick.orange.male.rabbit", "lazy.orange.male.rabbit");
            for (String severity : strings) {
                String msg = "this is a log 【" + severity + "】 ... ";
                // 此时severity是绑定键
                channel.basicPublish(EXCHANGE_NAME, severity, null, msg.getBytes("UTF-8"));
                System.out.println("生产了消息 " + msg);
            }
        }
    }
}
