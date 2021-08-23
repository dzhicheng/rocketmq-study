package com.dongzhic.producer.normal;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 消息发送-同步发送
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("sync");
        //producer.setRetryTimesWhenSendFailed(2);
        producer.setNamesrvAddr("47.93.56.20:9876");
        // 发送失败时，0不重试，1重试一次
        producer.setRetryTimesWhenSendAsyncFailed(0);

        producer.start();
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("TopicTest" ,
                    "TagB" ,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n%n%n", sendResult.getSendStatus()+":(MsgId):"
                    +sendResult.getMsgId()+":(queueId):"
                    +sendResult.getMessageQueue().getQueueId()
                    +"(value):"+ new String(msg.getBody()));
        }
        producer.shutdown();
    }
}

