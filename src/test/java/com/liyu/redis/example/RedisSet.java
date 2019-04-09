package com.liyu.redis.example;

import com.liyu.redis.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * @Author: liyu.guan
 * @Date: 2019/4/1 下午5:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisSet {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 将一个或者多个元素加入到集合中 返回插入数据的个数，不包括已经存在的数据
     * Redis Documentation: SADD
     */
    @Test
    public void sadd(){
        String[] strarrays = new String[]{"strarr1","sgtarr2"};
        System.out.println(redisTemplate.opsForSet().add("sadd","1","2"));
    }

    /**
     * 移除某个元素 返回移除的个数
     * Redis Documentation: SREM
     */
    @Test
    public void srem(){
        redisTemplate.opsForSet().add("srem","1","2");
        System.out.println(redisTemplate.opsForSet().remove("srem","1"));
    }

    /**
     *判断 member 元素是否是集合 key 的成员
     * Redis Documentation: SISMEMBER
     * 返回 true false
     */
    @Test
    public void sismember(){
        redisTemplate.opsForSet().add("sismember","x","z");
        System.out.println(redisTemplate.opsForSet().isMember("sismember","x"));
    }

    /**
     * 返回集合中元素的个数
     * Redis Documentation: SCARD
     */
    @Test
    public void scard(){
        redisTemplate.opsForSet().add("scard","x","z");
        System.out.println(redisTemplate.opsForSet().size("scard"));
    }

    /**
     * 返回集合包含的元素
     * Redis Documentation: SMEMBERS
     */
    @Test
    public void smembers(){
        redisTemplate.opsForSet().add("smembers","c","d","a","v");
        Set<String> aa = redisTemplate.opsForSet().members("smembers");
         for (String a:aa){
             System.out.println(a);
         }
    }

    /**
     * 返回集合包含的元素
     * Redis Documentation: SRANDMEMBER
     * 命令详解 如果 count 为正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。如果 count 大于等于集合基数，那么返回整个集合。
     *         如果 count 为负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为 count 的绝对值。
     */
    @Test
    public void srandmember(){
        redisTemplate.opsForSet().add("srandmember","c","d","a","v","v");
        System.out.println(redisTemplate.opsForSet().randomMember("srandmember"));
        // 有可能重复
        for(int i=0;i<=10;i++){
            System.out.println("有重复"+redisTemplate.opsForSet().randomMembers("srandmember",3l));

        }
        //不可能重复
        for(int i=0;i<=10;i++){
            System.out.println("无重复"+redisTemplate.opsForSet().distinctRandomMembers("srandmember",6l));

        }
    }

    /**
     * 移除并返回集合中的一个随机元素(如果不想删除的话就用SRANDMEMBER)
     *Redis Documentation: SPOP
     */
    @Test
    public void spop(){
        redisTemplate.opsForSet().add("pop","c","d","a","v","v");
        System.out.println(redisTemplate.opsForSet().pop("pop"));

    }

    /**
     * 将  元素从 smove1 集合移动到 smove2 集合。
     * Redis Documentation: SMOVE
     * 命令详解
     * 如果 smove1 集合不存在或不包含指定的 member 元素，则 SMOVE 命令不执行任何操作，仅返回 0 。否则， member 元素从 source 集合中被移除，并添加到 smove2 集合中去。
     * 当 destination 集合已经包含 member 元素时， SMOVE 命令只是简单地将 source 集合中的 member 元素删除。
     */
    @Test
    public void smove(){
        redisTemplate.opsForSet().add("smove1","c","d");
        redisTemplate.opsForSet().add("smove2","v","s");
        System.out.println(redisTemplate.opsForSet().move("smove1","c","smove2"));
    }


    /**
     * 将存在第一个集合的元素但并不存在于其他集合的元素显示出来(差集)
     * Redis Documentation: SDIFF
     */
    @Test
    public void sdiff(){
        redisTemplate.opsForSet().add("sdiff1","c","d","e","f");
        redisTemplate.opsForSet().add("sdiff2","e","f","g","h");
        System.out.println(redisTemplate.opsForSet().difference("sdiff1","sdiff2"));;
    }

    /**
     * 将存在第一个集合的元素但并不存在于其他集合的元素存储到第三个集合(差集)
     * Redis Documentation: SDIFFSTORE
     */
    @Test
    public void sdiffstore(){
        redisTemplate.opsForSet().add("sdiffstore1","c","d","e","f");
        redisTemplate.opsForSet().add("sdiffstore2","e","f","g","h");
        System.out.println(redisTemplate.opsForSet().differenceAndStore("sdiffstore1","sdiffstore2","sdiffstore3"));
    }


    /**
     * 将交集显示出来
     * Redis Documentation: SINTER
     */
    @Test
    public void sinter(){
        redisTemplate.opsForSet().add("sinter1","c","d","e","f");
        redisTemplate.opsForSet().add("sinter2","e","f","g","h");
        System.out.println(redisTemplate.opsForSet().intersect("sinter1", "sinter2"));
    }

    /**
     * 将交集 存储到sinterstore3
     * Redis Documentation: SINTERSTORE
     */
    @Test
    public void sinterstore(){
        redisTemplate.opsForSet().add("sinterstore1","c","d","e","f");
        redisTemplate.opsForSet().add("sinterstore2","e","f","g","h");
        System.out.println(redisTemplate.opsForSet().intersectAndStore("sinterstore1", "sinterstore2","sinterstore3"));

    }

    /**
     * 显示并集
     * Redis Documentation: SUNION
     */
    @Test
    public void sunion(){
        redisTemplate.opsForSet().add("sunion","c","d","e","f");
        redisTemplate.opsForSet().add("sunion","e","f","g","h");
        System.out.println(redisTemplate.opsForSet().union("sunion", "sunion"));

    }


    /**
     * 将并集存贮在sunionstore3
     * Redis Documentation: SUNIONSTORE
     */
    @Test
    public void sunionstore(){
        redisTemplate.opsForSet().add("sunionstore1","c","d","e","f");
        redisTemplate.opsForSet().add("sunionstore2","e","f","g","h");
        System.out.println(redisTemplate.opsForSet().unionAndStore("sunionstore1", "sunionstore2","sunionstore3"));

    }

}
