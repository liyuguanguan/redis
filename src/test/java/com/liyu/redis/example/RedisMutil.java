package com.liyu.redis.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * redis 事务
 * @Author: liyu.guan
 * @Date: 2019/4/9 下午5:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisMutil {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 开始事务支持 不建议使用
     */
    @Test
    public void muilt(){
        stringRedisTemplate.setEnableTransactionSupport(true);
        stringRedisTemplate.multi();
        stringRedisTemplate.opsForValue().set("a","b");
        System.out.println(stringRedisTemplate.opsForValue().get("a"));
        stringRedisTemplate.delete("a");
        stringRedisTemplate.exec();

    }

    /**
     * 我们在 SpringBoot 中操作 Redis 时，使用 RedisTemplate 的默认配置已经能够满足大部分的场景了。
     * 如果要执行事务操作，使用 SessionCallback 是比较好，也是比较常用的选择。
     */
    @Test
    public void sessionCallBack(){

        SessionCallback callback = new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForValue().set("name", "qinyi");
                operations.opsForValue().set("gender", "male");
                operations.opsForValue().set("age", "19");
                return operations.exec();
            }
        };
        // [true, true, true]
        System.out.println(stringRedisTemplate.execute(callback));
    }
}
