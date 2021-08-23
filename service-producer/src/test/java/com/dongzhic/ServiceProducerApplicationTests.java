package com.dongzhic;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceProducerApplication.class)
class ServiceProducerApplicationTests {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void testSendMessage() {

        for (int i = 0; i < 100; i++) {
            // topic名称
            String destination = "tp_springboot_01";
            // 消息内容
            String payload = "springboot: hello world, " + i;
            rocketMQTemplate.convertAndSend(destination, payload);
        }
    }

}
