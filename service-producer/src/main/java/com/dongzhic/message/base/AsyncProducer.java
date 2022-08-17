package com.dongzhic.message.base;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * 异步消息
 * @Author dongzhic
 * @Date 2022/8/18 00:14
 */
public class AsyncProducer {

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {

        // 1.创建消息生产者producer，并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");

        // 2.指定NameServer地址
        producer.setNamesrvAddr("60.60.1.31:9876;60.60.1.32:9876");

        // 3.启动producer
        producer.start();

        for (int i = 0; i < 10; i++) {
            /**
             * 4.创建消息对象，指定主题Topic、Tag和消息体
             *  参数一：Topic
             *  参数二：Tag
             *  参数三：消息内容
             */
            Message msg = new Message("base", "Tag2", ("Hello World，异步消息" + i).getBytes());

            // 5.发送异步消息
            producer.send(msg, new SendCallback() {

                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("消息结果：" + sendResult);
                }

                @Override
                public void onException(Throwable e) {
                    System.out.println("消息异常：" + e.getMessage());
                }
            });

            // 模拟消息产生过程
            TimeUnit.SECONDS.sleep(1);
        }

        // 6.关闭生产者producer
        producer.shutdown();


    }
}
