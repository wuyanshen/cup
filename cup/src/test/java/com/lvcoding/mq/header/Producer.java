package com.lvcoding.mq.header;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.*;

public class Producer {

    public static final String EXCHANGE_NAME = "header";

    public static void main(String[] args) throws Exception{
        // 获取mq连接
        ConnectionFactory factory = new ConnectionFactory();
        // 注意我们可以使用try-with-resources语句，因为Connection和Channel都实现了java.io.Closeable。这样，我们无需在代码中显式关闭它们
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()){

            // 创建交换机
            channel.exchangeDeclare(EXCHANGE_NAME, "headers");

            // 生产消息
            for (int i = 0; i < 3; i++) {
                String msg = "this is a msg ... " + i;
                Map<String, Object> headers = new Hashtable<>();
                headers.put("info_email", "email");
                headers.put("info_sms", "sms");
                AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties.Builder();
                properties.headers(headers);
                // 此时severity是绑定键
                channel.basicPublish(EXCHANGE_NAME, "", properties.build(), msg.getBytes("UTF-8"));
                System.out.println("生产了消息 " + msg);
            }
        }
    }
}
