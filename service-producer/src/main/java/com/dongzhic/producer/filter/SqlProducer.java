package com.dongzhic.producer.filter;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class SqlProducer {

    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("60.60.1.61:9876");
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
            return;
        }

        for (int i = 0; i < 10; i++) {
            try {
                String tag;
                int div = i % 3;
                if (div == 0) {
                    tag = "TagA";
                } else if (div == 1) {
                    tag = "TagB";
                } else {
                    tag = "TagC";
                }
                Message msg = new Message("SQL",
                    tag,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
                );
                msg.putUserProperty("a", String.valueOf(i));

                SendResult sendResult = producer.send(msg);
                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        producer.shutdown();
    }
}
