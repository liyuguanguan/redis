package com.liyu.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: liyu.guan
 * @Date: 2019/4/12 下午2:19
 */
@RestController("redis")
public class RedisTestController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("test")
    public String haha(){
        stringRedisTemplate.opsForValue().set("vv","ss");
        return "helloredis";
    }
}
