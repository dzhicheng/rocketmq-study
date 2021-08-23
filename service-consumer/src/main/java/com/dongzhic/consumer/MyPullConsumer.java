package com.dongzhic.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

/**
 * 消费消息：pull方式
 * @Author dongzhic
 * @Date 2021/8/10 16:29
 */
public class MyPullConsumer {

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException, UnsupportedEncodingException {
        // 1.拉取消息的消费者实例，同时指定消费组名称
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("grp01");
        // 设置nameserver的地址
        consumer.setNamespace("60.60.1.61:9876");

        // 对消费者进行初始化
        consumer.start();

        // 获取指定主题的消息队列集合
        final Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues("tp_demo_01");

        /**
         * 遍历该主题的各个消息队列，进行消费
         * PullResult pull(final MessageQueue mq,
         *                 final String subExpression,
         *                 final long offset,
         *                 final int maxNums)
         *  mq：MessageQueue对象，代表当前主题的一个消息队列
         *  subExpression：表达式，对接受的消息按照tag进行过滤
         *      支持 "tag1 || tag2 || tag3" 或者 "*" 类型的写法，null或者*表示不对消息进行tag过滤
         *  offset：消息偏移量，从这里开始消费
         *  maxNums：每次最多拉去多少条消息
         *
         *
         */
        for (MessageQueue messageQueue : messageQueues) {
            System.out.println("message**************queue**************: " + messageQueue);

            final PullResult result = consumer.pull(messageQueue, "", 0, 10);
            // 获取从指定消息队列拉取到的消息
            final List<MessageExt> msgFoundList = result.getMsgFoundList();

            if (msgFoundList != null && msgFoundList.size() > 0) {
                for (MessageExt messageExt : msgFoundList) {
                    System.out.println(messageExt);
                    // 将字节数组反序列化
                    System.out.println(new String(messageExt.getBody(), "utf-8"));
                }
            }

        }

        // 关闭消费者
        consumer.shutdown();

    }
}
