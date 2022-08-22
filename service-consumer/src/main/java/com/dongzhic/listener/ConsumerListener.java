package com.dongzhic.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author dongzhic
 * @Date 2022/8/21 15:49
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "springboot-rocketmq-topic", consumerGroup = "${rocketmq.producer.group}")
public class ConsumerListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        log.info("接收到消息为：" + s);
    }
}
