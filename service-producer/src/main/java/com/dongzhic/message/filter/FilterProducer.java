package com.dongzhic.message.filter;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * @Author dongzhic
 * @Date 2022/8/19 10:57
 */
public class FilterProducer {

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
//            Message msg = new Message("FilterTopic", "FilterTag2", ("Hello World，1同步消息" + i).getBytes());
            Message msg = new Message("FilterSQLTopic", "Tag", ("Hello World，2同步消息" + i).getBytes());
            msg.putUserProperty("i", String.valueOf(i));

            // 5.发送消息
            SendResult result = producer.send(msg);
            System.out.println("消息返回内容：" + result);

            // 模拟消息产生过程
            TimeUnit.SECONDS.sleep(1);
        }

        // 6.关闭生产者producer
        producer.shutdown();


    }

}
