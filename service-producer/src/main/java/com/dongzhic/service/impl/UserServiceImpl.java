package com.dongzhic.service.impl;

import com.dongzhic.service.IUserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * @Author dongzhic
 * @Date 2022/8/22 12:40
 */
@Service
@DubboService(interfaceClass = IUserService.class)
public class UserServiceImpl implements IUserService {

    @Override
    public String sayHello(String name) {
        return "hello, " + name;
    }
}
