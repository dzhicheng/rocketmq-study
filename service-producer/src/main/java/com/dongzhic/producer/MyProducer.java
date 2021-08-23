package com.dongzhic.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 生产消息：同步
 * @Author dongzhic
 * @Date 2021/8/10 15:50
 */
public class MyProducer {

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        // 1.实例化生产者的同时，指定生产组名称
        DefaultMQProducer producer = new DefaultMQProducer("myproducer_grp_01");

        // 2.指定NameServer的地址
//        producer.setNamesrvAddr("47.93.56.20:9876");
        producer.setNamesrvAddr("60.60.1.61:9876");

        producer.setSendMsgTimeout(10000);

        // 3.客户端初始化:对生产者进行初始化
        producer.start();

        // 4.创建消息，topic：主题名称，body：消息内容
        Message message = new Message("tp_demo_01", "hello world !".getBytes());

        // 5.发送消息（同步）
        final SendResult result = producer.send(message);
        System.out.println(result);

        // 6.关闭生产者
        producer.shutdown();

    }
}
