package com.liyu.redis.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author: liyu.guan
 * @Date: 2019/4/8 下午3:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisZset {


    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 添加zset
     * Redis Documentation: ZADD
     */
    @Test
    public void zadd(){
        redisTemplate.opsForZSet().add("zadd","key1",1);
    }

    /**
     * 添加zset的另一种方式
     * Redis Documentation: ZADD
     */
    @Test
    public void zadd1(){
        ZSetOperations.TypedTuple typedTuple1 = new DefaultTypedTuple("zadd1",5.2);
        ZSetOperations.TypedTuple typedTuple2 = new DefaultTypedTuple("zadd2",1.1);
        Set tuples = new HashSet();
        tuples.add(typedTuple1);
        tuples.add(typedTuple2);
        redisTemplate.opsForZSet().add("zadd",tuples);
        System.out.println(redisTemplate.opsForZSet().range("zadd", 0, -1));
    }

    /**
     * 移除某个元素 返回移除的个数
     * Redis Documentation: ZREM
     */
    @Test
    public void zrem(){
        redisTemplate.opsForZSet().add("zrem","key1",1);
        System.out.println(redisTemplate.opsForZSet().remove("zrem","key1"));
    }

    /**
     * 返回集合中元素的个数
     * Redis Documentation: SCARD
     */
    @Test
    public void zcard(){
        ZSetOperations.TypedTuple typedTuple1 = new DefaultTypedTuple("zcard1",5.2);
        ZSetOperations.TypedTuple typedTuple2 = new DefaultTypedTuple("zcard2",1.1);
        Set tuples = new HashSet();
        tuples.add(typedTuple1);
        tuples.add(typedTuple2);
        redisTemplate.opsForZSet().add("zcard",tuples);
        System.out.println(redisTemplate.opsForZSet().zCard("zcard"));

    }

    /**
     * score自动加
     * Redis Documentation: ZINCRBY
     */
    @Test
    public void zincrby(){
        redisTemplate.opsForZSet().add("zincrby","key1",1);
        System.out.println(redisTemplate.opsForZSet().incrementScore("zincrby","key1",1.5));
    }


    /**
     * 返回介于 min max 之间的数量
     * Redis Documentation: ZCOUNT
     */
    @Test
    public void zcount(){
        ZSetOperations.TypedTuple typedTuple1 = new DefaultTypedTuple("zcount1",5.2);
        ZSetOperations.TypedTuple typedTuple2 = new DefaultTypedTuple("zcount2",1.1);
        Set tuples = new HashSet();
        tuples.add(typedTuple1);
        tuples.add(typedTuple2);
        redisTemplate.opsForZSet().add("zcount",tuples);
        System.out.println(redisTemplate.opsForZSet().count("zcount",1.1,5.2));
    }

    /**
     * 返回zrank4在zrank中的排名 0表示第一
     * Redis Documentation: ZRANK
     */
    @Test
    public void zrank(){
        ZSetOperations.TypedTuple typedTuple1 = new DefaultTypedTuple("zrank1",5.2);
        ZSetOperations.TypedTuple typedTuple2 = new DefaultTypedTuple("zrank2",1.1);
        ZSetOperations.TypedTuple typedTuple3 = new DefaultTypedTuple("zrank3",3.1);
        ZSetOperations.TypedTuple typedTuple4 = new DefaultTypedTuple("zrank4",2.5);
        Set tuples = new HashSet();
        tuples.add(typedTuple1);
        tuples.add(typedTuple2);
        tuples.add(typedTuple3);
        tuples.add(typedTuple4);
        redisTemplate.opsForZSet().add("zrank",tuples);
        System.out.println(redisTemplate.opsForZSet().rank("zrank","zrank4"));
    }


    /**
     * 返回zscore中zscore2的score值
     * Redis Documentation: ZSCORE
     */
    @Test
    public void zscore(){
        ZSetOperations.TypedTuple typedTuple1 = new DefaultTypedTuple("zscore1",5.2);
        ZSetOperations.TypedTuple typedTuple2 = new DefaultTypedTuple("zscore2",1.1);
        Set tuples = new HashSet();
        tuples.add(typedTuple1);
        tuples.add(typedTuple2);
        redisTemplate.opsForZSet().add("zscore",tuples);
        System.out.println(redisTemplate.opsForZSet().score("zscore","zscore2"));
    }

    /**
     * 返回介于 start end 的数据(注意 start end 不是score)
     * Redis Documentation: ZRANGE
     */
    @Test
    public void zrange(){
        ZSetOperations.TypedTuple typedTuple1 = new DefaultTypedTuple("zrange1",5.2);
        ZSetOperations.TypedTuple typedTuple2 = new DefaultTypedTuple("zrange2",1.1);
        Set tuples = new HashSet();
        tuples.add(typedTuple1);
        tuples.add(typedTuple2);
        redisTemplate.opsForZSet().add("zrange",tuples);
        System.out.println(redisTemplate.opsForZSet().range("zrange",0,-1));
        // 返回带score的
        Set<ZSetOperations.TypedTuple> aa = redisTemplate.opsForZSet().rangeWithScores("zrange", 0, -1);
        for (ZSetOperations.TypedTuple a:aa){
            System.out.println("value "+a.getValue()+" score "+a.getScore());
        }
    }

    /**
     * 返回有序集合里面的排名 按照分值从大到小排序 (zrevrank1 为0 zrevrank2为2 zrevrank3为1)
     * Redis Documentation: ZREVRANK
     */
    @Test
    public void zrevrank(){
        ZSetOperations.TypedTuple typedTuple1 = new DefaultTypedTuple("zrevrank1",5.2);
        ZSetOperations.TypedTuple typedTuple2 = new DefaultTypedTuple("zrevrank2",1.1);
        ZSetOperations.TypedTuple typedTuple3 = new DefaultTypedTuple("zrevrank3",1.2);
        Set tuples = new HashSet();
        tuples.add(typedTuple1);
        tuples.add(typedTuple2);
        tuples.add(typedTuple3);
        redisTemplate.opsForZSet().add("zrevrank",tuples);
        System.out.println(redisTemplate.opsForZSet().reverseRank("zrevrank","zrevrank2"));
    }


    /**
     * 返回有序集合里面的排名 按照分值从大到小排序
     * Redis Documentation: zrevrange
     */
    @Test
    public void zrevrange(){
        ZSetOperations.TypedTuple typedTuple1 = new DefaultTypedTuple("zrevrange1",5.2);
        ZSetOperations.TypedTuple typedTuple2 = new DefaultTypedTuple("zrevrange2",1.1);
        ZSetOperations.TypedTuple typedTuple3 = new DefaultTypedTuple("zrevrange3",1.2);
        Set tuples = new HashSet();
        tuples.add(typedTuple1);
        tuples.add(typedTuple2);
        tuples.add(typedTuple3);
        redisTemplate.opsForZSet().add("zrevrange",tuples);
        System.out.println(redisTemplate.opsForZSet().reverseRange("zrevrange",0,-1));
    }



}
