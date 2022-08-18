package com.dongzhic.message.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @Author dongzhic
 * @Date 2022/8/18 18:08
 */
public class OrderConsumer {

    public static void main(String[] args) throws MQClientException {

        // 1.创建消费者Consumer，指定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");

        // 2.指定nameServer地址
        consumer.setNamesrvAddr("60.60.1.31:9876;60.60.1.32:9876");

        // 3.订阅主题Topic和Tag
        consumer.subscribe("OrderTopic", "*");

        // 4.注册消息监听器
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                for (MessageExt message : msgs) {
                    System.out.println("线程名称【" + Thread.currentThread().getName() + "】, 消费消息：" + new String(message.getBody()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        // 5.启动消费者
        consumer.start();
        System.out.println("消费者启动完成！");
    }
}
