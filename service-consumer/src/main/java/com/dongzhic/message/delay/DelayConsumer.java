package com.dongzhic.message.delay;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * 消费延迟消息
 * @Author dongzhic
 * @Date 2022/8/18 23:32
 */
public class DelayConsumer {

    public static void main(String[] args) throws MQClientException {

        // 1.创建消费者Consumer，指定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");

        // 2.指定nameServer地址
        consumer.setNamesrvAddr("60.60.1.31:9876;60.60.1.32:9876");

        // 3.订阅主题Topic和Tag
        consumer.subscribe("DelayTopic", "*");

        // 4.设置回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println("消息Id" + msg.getMsgId() + "延迟消息事件：" + (System.currentTimeMillis() - msg.getStoreTimestamp()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 5.启动消费者Consumer
        consumer.start();
        System.out.println("消费者启动成功");
    }


}
