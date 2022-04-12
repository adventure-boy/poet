package com.adventureboy.system.utils;

import cn.hutool.json.JSONObject;
import com.adventureboy.utils.RedisUtil;
import com.adventureboy.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VerificationCodeUtil {
    @Autowired
    RedisUtil redisUtil;
    public  boolean checkCode(String verificationCode) {
        //验证码为空
        if (verificationCode == null) {
            return false;
        }
        String redisCode = redisUtil.get(verificationCode);
        //redis中包含验证码
        if (redisCode != null && redisCode.equals(verificationCode.trim())) {
            return true;
        }
        return false;
    }
}
