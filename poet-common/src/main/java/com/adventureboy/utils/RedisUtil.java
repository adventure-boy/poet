package com.adventureboy.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;
    public boolean set(String key, String value, long time) {
        redisTemplate.set
        return
    }
}
