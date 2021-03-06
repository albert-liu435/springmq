package com.rookie.bigdata.sample;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import com.rookie.bigdata.until.ConnectionUtil;

/**
 * @ClassName PSReceiveLogs
 * @Description PSReceiveLogs
 * @Author
 * @Date 2020/1/15 16:07
 * @Version 1.0
 */
public class PSReceiveLogs2 {

    private static final String Exchange_name = "logs";

    public static void main(String[] argv) throws Exception {

        //获取连接
        Connection connection = ConnectionUtil.getConnection("localhost", 5672, "/", "guest", "guest");

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(Exchange_name, "fanout");
        //随机定义一个队列名称，也可以自己定义一个队列名称
        String queueName = channel.queueDeclare().getQueue();

        //绑定队列
        channel.queueBind(queueName, Exchange_name, "");
        DeliverCallback deliverCallback = ((consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        });

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });

    }
}
