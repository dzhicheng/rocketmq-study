package com.dongzhic.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 消费消息：push方式
 * @Author dongzhic
 * @Date 2021/8/10 16:29
 */
public class MyPushConsumer {

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException, UnsupportedEncodingException {
        // 1.实例化推送消息消费者的对象，同时指定消费组名称
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer_grp_02");

        // 2.设置nameserver的地址
        consumer.setNamespace("60.60.1.61:9876");

        // 3.订阅主题
        consumer.subscribe("tp_demo_02", "*");

        // 4.回调函数：添加消息监听器，一旦有消息过来,就进行消费
        consumer.setMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                final MessageQueue messageQueue = context.getMessageQueue();
                System.out.println(messageQueue);

                for (MessageExt msg : msgs) {
                    try {
                        System.out.println(new String(msg.getBody(), "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                // 消息消费失败，稍后再试
//                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                // 消息消费成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 对消费者进行初始化
        consumer.start();

        // 此处只是示例，生产中是长服务，除非运维关闭
        TimeUnit.SECONDS.sleep(3);

        // 关闭消费者
        consumer.shutdown();

    }
}
