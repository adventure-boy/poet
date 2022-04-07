package com.adventureboy.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * String类型设置带时间有效时间的值,单位为秒
     */
    public void set(String key, String value, long time) {
        ValueOperations vo = redisTemplate.opsForValue();
        vo.set(key,value,time, TimeUnit.SECONDS);
    }

    public String get(String key) {
        ValueOperations vo = redisTemplate.opsForValue();
        String value = vo.get(key).toString();
        return value;
    }

}
