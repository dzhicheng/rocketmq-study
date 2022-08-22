package com.dongzhic.controller;

import com.dongzhic.service.IUserService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author dongzhic
 * @Date 2022/8/22 15:47
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @DubboReference(interfaceClass = IUserService.class)
    private IUserService userService;

    @RequestMapping("/sayHello")
    public String sayHello (String name) {
        return userService.sayHello(name);
    }
}
