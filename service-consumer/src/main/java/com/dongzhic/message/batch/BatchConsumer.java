package com.dongzhic.message.batch;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @Author dongzhic
 * @Date 2022/8/18 23:52
 */
public class BatchConsumer {

    public static void main(String[] args) throws MQClientException {

        // 1.创建消费者Consumer，指定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");

        // 2.指定nameServer地址
        consumer.setNamesrvAddr("60.60.1.31:9876;60.60.1.32:9876");

        // 3.订阅主题Topic和Tag
        consumer.subscribe("BatchTopic", "BatchTag");

        // 设置消费模式：负载均衡|广播模式，默认负载均衡
        consumer.setMessageModel(MessageModel.BROADCASTING);

        // 4.设置回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//                System.out.println(msgs);
                for (MessageExt msg : msgs) {
                    System.out.println(new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        // 5.启动消费者Consumer
        consumer.start();
        System.out.println("消费者启动");
    }

}
