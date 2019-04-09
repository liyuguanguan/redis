package com.liyu.redis.example;


import com.liyu.redis.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * redisString
 * @Author: liyu.guan
 * @Date: 2019/3/18 上午10:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisString {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test(){
        // 设置值
        stringRedisTemplate.opsForValue().set("dsa", "1");
        // 增加1
//        stringRedisTemplate.opsForValue().increment("dsa", 1);
//        // 减去2
//        stringRedisTemplate.opsForValue().decrement("dsa", 2);
//        // 减去1
//        // redis命令
//        // DECR(key)  -1
//        // DECRBY (kEY decrement) - decrement
//        stringRedisTemplate.opsForValue().decrement("dsa");
//        // 增加1
//        // redis命令 INCRBY 同上
//        stringRedisTemplate.opsForValue().increment("dsa");
    }

    /**
     * 追加命令
     */
    @Test
    public void append(){
        redisUtil.set("stringappend", "1");
         //追加
        //redis 命令 append （key value） 将value增加到 key的values后面 example:stringappend 11
        redisTemplate.opsForValue().append("stringappend", "1");


        /**
         *  表示截取
         * dsa的值为6adseruvm
         * 对应redis命令为 GETRANGE (key,start,end) 0表示第一位
         */
        System.out.println(redisTemplate.opsForValue().get("dsa", 0, -1));;
    }


    /**
     *
     *redis命令 SETRANGE(key offset value)
     * 从offset开始替换成vaues
     * 输出h321oworld
     */
    @Test
    public void setRange(){
        redisTemplate.opsForValue().set("setrange","helloworld");
        redisTemplate.opsForValue().set("setrange","321",1);
        System.out.println(redisTemplate.opsForValue().get("setrange"));
    }

    /**
     * setbit命令
     * 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)
     * key键对应的值value对应的ascii码,在offset的位置(从左向右数)变为value
     */
    @Test
    public void setbit(){
        // 'a' 的ASCII码是 97。转换为二进制是：1100001
        // 'b' 的ASCII码是 98  转换为二进制是：1100010
        redisTemplate.opsForValue().set("bittest","a");
        redisTemplate.opsForValue().setBit("bittest", 6, true);
        redisTemplate.opsForValue().setBit("bittest", 7, false);
        System.out.println(redisTemplate.opsForValue().get("bittest"));
    }

    /**
     * 获取键对应值的ascii码的在offset处位值
     * Redis Documentation: GETBIT 下标从1开始
     */
    @Test
    public void getbit(){
        // 'a' 的ASCII码是 97。转换为二进制是：1100001
        redisTemplate.opsForValue().set("bittest",1100001);
        System.out.println(redisTemplate.opsForValue().get("bittest"));;
        System.out.println(redisTemplate.opsForValue().getBit("bittest", 1));;
        System.out.println(redisTemplate.opsForValue().getBit("bittest", 2));;
        System.out.println(redisTemplate.opsForValue().getBit("bittest", 3));;
        System.out.println(redisTemplate.opsForValue().getBit("bittest", 4));;
        System.out.println(redisTemplate.opsForValue().getBit("bittest", 5));;
        System.out.println(redisTemplate.opsForValue().getBit("bittest", 6));;
        System.out.println(redisTemplate.opsForValue().getBit("bittest", 7));;
        System.out.println(redisTemplate.opsForValue().getBit("bittest", 8));;

    }

    /**
     * Redis Documentation: BITCOUNT
     *统计获取键对应值的ascii码的在start ebd处bit位为1的总数
     * 结果为:5
     */
    @Test
    public void bitcount(){
        //'g' 的ASCII码是 103。转换为二进制是：110 0111
        redisTemplate.opsForValue().set("bitcounttest","g");
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                System.out.println(connection.bitCount("bitcounttest".getBytes(), 0, -1));;
                return null;
            }
        });


    }


    /**
     * 删除
     */
    @Test
    public void del(){
        redisTemplate.opsForValue().set("del","123");
        redisTemplate.delete("del");
    }

    /**
     * 互斥锁 针对缓存击穿方案
     * @param key
     * @return
     * @throws InterruptedException
     */
    String mutex(String key) throws InterruptedException {
        String value = redisTemplate.opsForValue().get(key).toString();
        if (value  == null) {
            // 设置3min的超时，防止del操作失败的时候，下次缓存过期一直不能load db
            if (redisTemplate.opsForValue().setIfAbsent(key+"_mutex","1",3,TimeUnit.MINUTES)) {

                // 数据库获取值
//                value = db.get(key);
                redisTemplate.opsForValue().set(key, value);
                redisTemplate.delete(key+"_mutex");
            } else {
                //其他线程休息50毫秒后重试
                Thread.sleep(50);
                redisTemplate.opsForValue().get(key);
            }
            return value;
        } else {
            return value;
        }
    }
}
