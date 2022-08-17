package com.dongzhic.controller;

import com.dongzhic.producer.MQProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/rocket")
public class RocketController {

    @Autowired
    private MQProducer mqProducer;

    @RequestMapping(value = "/send")
    public String sendRocket(@RequestParam(required = false) String data,
                            @RequestParam(required = false) String tag) {
        try {
            log.info("rocket的消息={}", data);
            mqProducer.sendMessage(data,"TopicTest", tag, null);
            return "发送rocket成功";
        } catch (Exception e) {
            log.error("发送rocket异常：", e);
            return "发送rocket失败";
        }
    }

}
