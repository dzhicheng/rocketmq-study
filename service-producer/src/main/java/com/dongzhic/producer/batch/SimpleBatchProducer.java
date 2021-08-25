package com.dongzhic.producer.batch;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量生产消息
 */
public class SimpleBatchProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("batch_producer_group");
        producer.setNamesrvAddr("60.60.1.61:9876");
        producer.start();

        //如果一次只发送不超过4M的消息，那么批处理很容易使用
        //同一批的消息应该有：相同的主题，相同的waitstoremsgok，不支持调度
        String topic = "TopicBatch";
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(topic, "TagB", "OrderID001", "Hello world 0".getBytes()));
        messages.add(new Message(topic, "TagB", "OrderID002", "Hello world 1".getBytes()));
        messages.add(new Message(topic, "TagB", "OrderID003", "Hello world 2".getBytes()));

        producer.send(messages);
        System.out.printf("Batch over");
        producer.shutdown();
    }
}
