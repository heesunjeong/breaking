package com.dkt.breaking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> template;

    public void setRedis(String key, String value) {
        template.opsForValue().set(key, value, 2, TimeUnit.MINUTES);
    }

    public String getRedis(String key) {
        return template.opsForValue().get(key);
    }
}
