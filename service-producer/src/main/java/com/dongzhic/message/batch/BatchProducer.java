package com.dongzhic.message.batch;

import com.dongzhic.util.ListSplitter;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author dongzhic
 * @Date 2022/8/18 23:52
 */
public class BatchProducer {

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {

        // 1.创建消息生产者producer，并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");

        // 2.指定NameServer地址
        producer.setNamesrvAddr("60.60.1.31:9876;60.60.1.32:9876");

        // 3.启动producer
        producer.start();

        // 单次消息发送不能超过4M
        List<Message> messages = new ArrayList<>();
        /**
         * 4.创建消息对象，指定主题Topic、Tag和消息体
         *  参数一：Topic
         *  参数二：Tag
         *  参数三：消息内容
         */
        Message msg1 = new Message("BatchTopic", "BatchTag", ("Hello World，同步消息" + 0).getBytes());
        Message msg2 = new Message("BatchTopic", "BatchTag", ("Hello World，同步消息" + 1).getBytes());
        Message msg3 = new Message("BatchTopic", "BatchTag", ("Hello World，同步消息" + 2).getBytes());
        Message msg4 = new Message("BatchTopic", "BatchTag", ("Hello World，同步消息" + 3).getBytes());

        messages.add(msg1);
        messages.add(msg2);
        messages.add(msg3);
        messages.add(msg4);

        //把大的消息分裂成若干个小的消息
//        ListSplitter splitter = new ListSplitter(messages);
//        while (splitter.hasNext()) {
//            try {
//                List<Message>  listItem = splitter.next();
//                producer.send(listItem);
//            } catch (Exception e) {
//                e.printStackTrace();
//                //处理error
//            }
//        }

        // 5.发送消息
        SendResult result = producer.send(messages);
        System.out.println("消息返回内容：" + result);

        // 模拟消息产生过程
        TimeUnit.SECONDS.sleep(1);

        // 6.关闭生产者producer
        producer.shutdown();


    }


}
