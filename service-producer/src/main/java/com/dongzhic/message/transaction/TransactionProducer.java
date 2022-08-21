package com.dongzhic.message.transaction;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * @Author dongzhic
 * @Date 2022/8/19 15:41
 */
public class TransactionProducer {

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {

        // 1.创建消息生产者producer，并指定生产者组名
        TransactionMQProducer producer = new TransactionMQProducer("transaction_group");

        // 2.指定NameServer地址
        producer.setNamesrvAddr("60.60.1.31:9876;60.60.1.32:9876");

        String[] tags = {"TagA", "TagB", "TagC"};

        // 添加事务监听器
        producer.setTransactionListener(new TransactionListener() {
            /**
             * 在该方法中执行本地事务
             * @param msg
             * @param arg
             * @return
             */
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                if (tags[0].equals(msg.getTags())) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if (tags[1].equals(msg.getTags())) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else if (tags[2].equals(msg.getTags())) {
                    return LocalTransactionState.UNKNOW;
                }
                return LocalTransactionState.UNKNOW;
            }
            /**
             * 该方法是MQ对消息事务状态回查
             * @param msg
             * @return
             */
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                System.out.println("消息的Tag：" + msg.getTags());
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });

        // 3.启动producer
        producer.start();

        for (int i = 0; i < 3; i++) {
            /**
             * 4.创建消息对象，指定主题Topic、Tag和消息体
             *  参数一：Topic
             *  参数二：Tag
             *  参数三：消息内容
             */
            Message msg = new Message("TransactionTopic", tags[i], ("Hello World，同步消息" + i).getBytes());

            // 5.发送消息
            SendResult result = producer.sendMessageInTransaction(msg, null);
            System.out.println("消息返回内容：" + result);

            // 模拟消息产生过程
            TimeUnit.SECONDS.sleep(1);
        }

        // 6.关闭生产者producer
//        producer.shutdown();


    }

}
