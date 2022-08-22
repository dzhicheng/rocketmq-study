package com.dongzhic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ServiceProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceProducerApplication.class, args);
        log.info("消息发送成功！");
    }

}
