package com.liyu.redis;

import com.liyu.redis.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class RedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private RedisUtil redisUtil;

//    @Test
//    public void contextLoads() {
//        redisUtil.set("dsa", "1");
//        redisUtil.incr("dsa", 1);
////        redisTemplate.opsForValue().set("sa","d");
////        stringRedisTemplate.opsForValue().set("dsa","ssss");
//        System.out.println(redisTemplate.opsForValue().get("sa"));;
//    }

}
