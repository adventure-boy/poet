package com.adventureboy.system.controller;

import cn.hutool.core.util.RandomUtil;
import com.adventureboy.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    RedisUtil redisUtil;
    @GetMapping("/getVerificationCode")
    public String getVerificationCode() {
        String verificationCode = RandomUtil.randomString(4);
        redisUtil.set(verificationCode,verificationCode,100);
        return verificationCode;
    }

    @GetMapping("/test")
    public String test(String hello) {
        return hello;
    }
}
