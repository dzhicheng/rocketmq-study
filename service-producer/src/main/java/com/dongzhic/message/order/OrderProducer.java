package com.dongzhic.message.order;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 顺序消息生产者
 * @Author dongzhic
 * @Date 2022/8/18 16:30
 */
public class OrderProducer {

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {

        // 1.创建消息生产者producer，并指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");

        // 2.指定NameServer地址
        producer.setNamesrvAddr("60.60.1.31:9876;60.60.1.32:9876");

        // 3.启动producer
        producer.start();

        List<OrderStep> orderList = OrderStep.buildOrders();
        for (int i = 0; i < orderList.size(); i++) {

            OrderStep order = orderList.get(i);

            // 消息内容
            String body = order + "";
            Message message = new Message("OrderTopic", "OrderTag", "order_" + i, body.getBytes());
            /**
             * Broker中队列选择器
             * 参数一：消息对象
             * 参数二：消息队列的选择器
             * 参数三：选择队列的业务标识（订单号）
             */
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                /**
                 * @param mqs 队列集合
                 * @param msg 消息对象
                 * @param arg 业务标识参数
                 * @return
                 */
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    long orderId = (long) arg;
                    long index = orderId % mqs.size();
                    return mqs.get((int) index);
                }
            }, order.getOrderId());

            System.out.println("返回结果：" + sendResult);
        }

        // 6.关闭生产者producer
        producer.shutdown();

    }
}
