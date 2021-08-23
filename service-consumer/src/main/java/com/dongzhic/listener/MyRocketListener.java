package com.dongzhic.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 接收broker推送的消息
 * @Author dongzhic
 * @Date 2021/8/11 14:42
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "tp_springboot_01", consumerGroup = "consumer_grp_03")
public class MyRocketListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        // 处理broker推送出来的消息
        log.info(message);

    }
}
