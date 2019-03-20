package com.liyu.redis.example;

import com.liyu.redis.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liyu.guan
 * @Date: 2019/3/20 上午11:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisList {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Test
    public void rpush(){
        List list = new ArrayList();
        list.add("ccc");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("d");
        list.add("g");
        String rpushlist = "rpushlist";
        int aaaa = 1234;
        System.out.println(redisTemplate.opsForList().leftPush(aaaa, rpushlist));
    }
}
