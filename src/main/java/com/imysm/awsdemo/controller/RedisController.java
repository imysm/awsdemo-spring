package com.imysm.awsdemo.controller;

import com.imysm.awsdemo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spring-awsdemo/redis")
public class RedisController {
    private final RedisService redisService;

    @Autowired
    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

//    @PutMapping("/{key}")
//    public void put(@PathVariable String key, @RequestBody String value) {
//        redisService.setKeyValue(key, value);
//    }

    @GetMapping("/{key}")
    public String get(@PathVariable String key) {
        return redisService.getValue(key);
    }
}
