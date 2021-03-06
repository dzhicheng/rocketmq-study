package com.dongzhic.producer.normal;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 消息发送-异步发送
 */
public class AsyncProducer {
    public static void main(
            String[] args) throws MQClientException, InterruptedException, UnsupportedEncodingException {
        //生产者实例化
        DefaultMQProducer producer = new DefaultMQProducer("normal_group");
        //指定rocket服务器地址
        producer.setNamesrvAddr("60.60.1.61:9876");
        //启动实例
        producer.start();
        //发送异步失败时的重试次数(这里不重试)
        producer.setRetryTimesWhenSendAsyncFailed(0);

        int messageCount = 10;
        final CountDownLatch countDownLatch = new CountDownLatch(messageCount);
        for (int i = 0; i < messageCount; i++) {
            try {
                final int index = i;
                Message msg = new Message("TopicAsync",
                        "tagA",
                        "OrderID"+index,
                        ("Hello world "+index).getBytes(RemotingHelper.DEFAULT_CHARSET));
                //生产者异步发送
                producer.send(msg, new SendCallback() {
                    /**
                     * 发送成功监听
                     * @param sendResult
                     */
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        countDownLatch.countDown();
                        System.out.printf("%-10d OK %s %n", index, new String(msg.getBody()));
                    }

                    /**
                     * 发送异常监听
                     * @param e
                     */
                    @Override
                    public void onException(Throwable e) {
                        countDownLatch.countDown();
                        System.out.printf("%-10d Exception %s %n", index, e);
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //Thread.sleep(1000);
        countDownLatch.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }
}
