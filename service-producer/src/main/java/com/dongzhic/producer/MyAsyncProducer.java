package com.dongzhic.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

/**
 * 生产消息：异步
 * @Author dongzhic
 * @Date 2021/8/10 15:50
 */
public class MyAsyncProducer {

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException, UnsupportedEncodingException {
        // 1.实例化生产者的同时，指定生产组名称
        DefaultMQProducer producer = new DefaultMQProducer("myproducer_grp_02");

        // 2.指定NameServer的地址
        producer.setNamesrvAddr("60.60.1.61:9876");

        // 3.客户端初始化:对生产者进行初始化
        producer.start();

        for (int i = 0; i < 10; i++) {
            // 4.创建消息，topic：主题名称，body：消息内容
            Message message = new Message("tp_demo_02", ("hello world !, + " + i).getBytes("utf-8"));

            // 5.发送消息（异步）
            producer.send(message, new SendCallback() {
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送成功:" + sendResult);
                }

                public void onException(Throwable throwable) {
                    System.out.println("发送失败:" + throwable.getMessage());
                }
            });
        }

        // 由于是异步发送消息，上面循环结束后，消息可能没收到broker的相应，因此需要sleep几秒
        TimeUnit.SECONDS.sleep(10);

        // 6.关闭生产者
        producer.shutdown();

    }
}
