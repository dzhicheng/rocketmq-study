package com.dongzhic.cluster;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * 集群模式下：同步发送
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("sync");
        producer.setNamesrvAddr("60.60.1.61:9876;60.60.1.62:9876");

        producer.start();
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("clusterMsg" ,
                    "TagB" ,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            SendResult sendResult = producer.send(msg);


            System.out.printf("%s%s%n%n%n", sendResult.getRegionId(),sendResult.getSendStatus()+":(MsgId):"
                    +sendResult.getMsgId()+":(queueId):"
                    +sendResult.getMessageQueue().getQueueId()
                    +"(value):"+ new String(msg.getBody()));
        }
        producer.shutdown();
    }
}

