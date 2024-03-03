package com.imysm.awsdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void putKey(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public String getKey(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void setKeyValue(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public String getValue(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void deleteKey(String key) {
        stringRedisTemplate.delete(key);
    }

    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    public void leftPush(String listKey, String value) {
        stringRedisTemplate.opsForList().leftPush(listKey, value);
    }

    public String leftPop(String listKey) {
        return stringRedisTemplate.opsForList().leftPop(listKey);
    }

    public void rightPush(String listKey, String value) {
        stringRedisTemplate.opsForList().rightPush(listKey, value);
    }

    public String rightPop(String listKey) {
        return stringRedisTemplate.opsForList().rightPop(listKey);
    }
}