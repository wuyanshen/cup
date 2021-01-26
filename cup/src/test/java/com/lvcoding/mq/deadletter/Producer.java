package com.lvcoding.mq.deadletter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Producer {

    private static final String BUSINESS_EXCHANGE_NAME = "lvcoding.business.exchange";
    private static final String BUSINESS_QUEUE = "lvcoding.business.queue";
    private static final String DEAD_LETTER_EXCHANGE = "lvcoding.dead.letter.exchange";
    private static final String DEAD_LETTER_ROUTING_KEY = "lvcoding.dead.letter.routing.key";

    public static void main(String[] args) throws Exception{
        // 获取mq连接
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 创建业务交换机
        channel.exchangeDeclare(BUSINESS_EXCHANGE_NAME, "direct", true);
        // 创建业务队列
        Map<String, Object> map = new HashMap<>();
        map.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        map.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY);
        channel.queueDeclare(BUSINESS_QUEUE, true, false, false ,map);
        // 业务绑定
        channel.queueBind(BUSINESS_QUEUE, BUSINESS_EXCHANGE_NAME, "");

        // 发布消息
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .contentType("text/plain")
                .priority(0) // 优先级默认是0，数字越大优先级越高，优先级越大的消息越是被优先处理
                .deliveryMode(2) // 2 代表持久化消息
                .expiration("20000")
                .build();
        // json字符串消息
        Map<String, Object> msgs = new HashMap<>();
        msgs.put("name", "xiaohong ");
        msgs.put("age", 22);
        msgs.put("address", "北京海淀");
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(msgs);

        channel.basicPublish(BUSINESS_EXCHANGE_NAME, "", true, properties, result.getBytes());
        System.out.println("发布了一条消息 " + result + " "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        // 关闭资源
        channel.close();
        connection.close();
    }
}
