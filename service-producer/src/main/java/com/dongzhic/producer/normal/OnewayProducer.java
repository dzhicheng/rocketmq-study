package com.dongzhic.producer.normal;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 消息发送-单向发送
 */
public class OnewayProducer {
    public static void main(String[] args) throws Exception{
        // 1.生产者实例化
        DefaultMQProducer producer = new DefaultMQProducer("oneway");
        // 2.指定rocket服务器地址
        producer.setNamesrvAddr("60.60.1.61:9876");

        // 3.启动实例
        producer.start();
        for (int i = 0; i < 10; i++) {
            // 4.创建一个消息实例，指定topic、tag和消息体，tags二级消息类型
            Message msg = new Message("TopicTest",
                    "TagA",
                    ("Hello RocketMQ " +
                            i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            //发送消息
            producer.sendOneway(msg);
            System.out.printf("%s%n",  new String(msg.getBody()));
        }

        // 5.生产者实例不再使用时关闭.
        producer.shutdown();
    }
}
