package com.liyu.redis.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * redis过期时间，除了String 类型有原子的过期时间设置 其他都需要expire 设置超时间
 * @Author: liyu.guan
 * @Date: 2019/4/9 下午6:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisExpire {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 设置过期时间
     * Redis Documentation: EXPIRE
     */
    @Test
    public  void  expire(){
        redisTemplate.opsForSet().add("a", "s", "c");
        redisTemplate.expire("a", 1, TimeUnit.MINUTES);
    }


    /**
     * 查看过期时间
     *  Redis Documentation: TTL
     */
    @Test
    public void ttl(){
        redisTemplate.opsForSet().add("ttl", "s", "c");
        redisTemplate.expire("ttl", 1, TimeUnit.MINUTES);
        System.out.println(redisTemplate.getExpire("ttl"));
    }

    /**
     *
     * 移除建的过期时间
     * Redis Documentation: PERSIST
     */
    @Test
    public void persist(){
        redisTemplate.opsForSet().add("persist", "s", "c");
        redisTemplate.expire("persist", 1, TimeUnit.MINUTES);
        System.out.println(redisTemplate.persist("persist"));

    }

    /**
     * 设定过期时间 以时间戳 方式设置
     * Redis Documentation: expireAt
     */
    @Test
    public void expireat() throws ParseException {
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time="2019-04-09 19:00:00";
        redisTemplate.opsForSet().add("expireat", "s", "c");
        redisTemplate.expireAt("expireat", format.parse(time));
    }


    /**
     * 查询过期时间还剩多少 timeUnit
     * Redis Documentation: PTTL
     */
    @Test
    public void pttl(){
        redisTemplate.opsForSet().add("pttl", "s", "c");
        redisTemplate.expire("pttl",5,TimeUnit.MINUTES);
        System.out.println(redisTemplate.getExpire("pttl",TimeUnit.SECONDS));
    }




    public static void main(String[] args) {

        System.out.println(new Date().getTime());
    }
}
