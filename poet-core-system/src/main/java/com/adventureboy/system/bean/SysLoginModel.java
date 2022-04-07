package com.adventureboy.system.bean;

import lombok.Data;

@Data
public class SysLoginModel {
    private String username;//用户名
    private String password;//密码
    private String verificationCode;//验证码
}
