package com.adventureboy.system.controller;

import cn.hutool.core.util.RandomUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @GetMapping("/getVerificationCode")
    public String getVerificationCode() {
        String verificationCode = RandomUtil.randomString(4);
        return verificationCode;
    }
}
