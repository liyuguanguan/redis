package com.liyu.redis.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: liyu.guan
 * @Date: 2019/4/2 下午8:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisHash {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 设置值
     *Redis Documentation: HMSET
     */
    @Test
    public void hmset(){
        redisTemplate.opsForHash().put("hmset","key","value");
    }

    /**
     * 获取值
     * Redis Documentation: HMGET
     */
    @Test
    public void hmget(){
        redisTemplate.opsForHash().put("hgset","key","value");
        System.out.println(redisTemplate.opsForHash().get("hgset", "key"));
    }

    /**
     * 删除值
     * Redis Documentation: HDEL
     */
    @Test
    public void hdel(){
        redisTemplate.opsForHash().put("hdel","key","value");
        System.out.println(redisTemplate.opsForHash().delete("hdel","key"));
    }

    /**
     * 获取键值对的数量
     *Redis Documentation: HLEN
     */
    @Test
    public void hlen(){
        Map map = new HashMap();
        map.put("a1","1");
        map.put("a2","2");
        map.put("a3","3");
        redisTemplate.opsForHash().putAll("hlen",map);
        System.out.println(redisTemplate.opsForHash().size("hlen"));
    }


    /**
     * 检查键是否存在
     * Redis Documentation: HEXISTS
     */
    @Test
    public void hexists(){
        redisTemplate.opsForHash().put("hexists","key","value");
        System.out.println(redisTemplate.opsForHash().hasKey("hexists", "key"));
    }

    /**
     * 获取所有的键
     * Redis Documentation: HKEYS
     */
    @Test
    public void hkeys(){
        Map map = new HashMap();
        map.put("a1","1");
        map.put("a2","2");
        map.put("a3","3");
        redisTemplate.opsForHash().putAll("hkeys",map);
        System.out.println(redisTemplate.opsForHash().keys("hkeys"));
    }


    /**
     * 获取所有值
     * Redis Documentation: HVALS
     */
    @Test
    public void hvals(){
        Map map = new HashMap();
        map.put("a1","1");
        map.put("a2","2");
        map.put("a3","3");
        redisTemplate.opsForHash().putAll("hvals",map);
        System.out.println(redisTemplate.opsForHash().values("hvals"));
    }

    /**
     * 获取散列包含的所有键值对
     * 如果散列包含的值非常大，那么可以先使用 HKEYS获取到所有的key 在使用HGET一个接一个的取出键值从而避免因为一次获取锁哥大体积的值而导致服务器阻塞
     * Redis Documentation: HGETALL
     */
    @Test
    public void hgetall(){
        Map map = new HashMap();
        map.put("a1","1");
        map.put("a2","2");
        map.put("a3","3");
        redisTemplate.opsForHash().putAll("hgetall",map);
        Map<String,String> result = redisTemplate.opsForHash().entries("hgetall");
        for (Map.Entry<String,String> aa:result.entrySet()){
            System.out.println("key "+aa.getKey()+" value "+aa.getValue());
        }
    }

    /**
     * 将值增加10
     * Redis Documentation: HINCRBY
     */
    @Test
    public void hincrby(){
        redisTemplate.opsForHash().put("hincrby","key",1);
        System.out.println(redisTemplate.opsForHash().increment("hincrby", "key", 10));;
    }

    /**
     * 将值增加10.5
     * Redis Documentation: HINCRBYFLOAT
     */
    @Test
    public void hincrbyfloat(){
        redisTemplate.opsForHash().put("hincrbyfloat","key",1);
        System.out.println(redisTemplate.opsForHash().increment("hincrbyfloat", "key", 10.5));;
    }
}
