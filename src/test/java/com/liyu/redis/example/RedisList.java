package com.liyu.redis.example;

import com.liyu.redis.util.RedisUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: liyu.guan
 * @Date: 2019/3/20 上午11:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisList {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * Redis Documentation: RPUSH
     * list向列表右端添加数据
     * 返回列表个数
     */
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
        System.out.println(redisTemplate.opsForList().rightPush(rpushlist, list));
    }

    /**
     * Redis Documentation: LPUSH
     * list向列表左端添加数据
     * 返回列表个数
     */
    @Test
    public void lpush() {
        List list = new LinkedList();
        list.add("cctv1");
        System.out.println(redisTemplate.opsForList().leftPush(11, list));
    }

    /**
     *Redis Documentation: LPOP
     * 移出并获取列表的左边第一个元素
     */
    @Test
    public void lpop(){
        List list = new LinkedList();
        list.add("cctv2");
        System.out.println(redisTemplate.opsForList().leftPush("lpoptest", list));
        List aa = (List)redisTemplate.opsForList().leftPop("lpoptest");
        System.out.println(aa.get(0));
    }

    /**
     * Redis Documentation: RPOP
     * 移出并获取列表的右边第一个元素
     */
    @Test
    public void rpop(){
        List list = new LinkedList();
        list.add("cctv2");
        System.out.println(redisTemplate.opsForList().leftPush("rpoptest", list));
        List aa = (List)redisTemplate.opsForList().rightPop("lpoptest");
        System.out.println(aa);
    }

    /**
     *Redis Documentation: LINDEX
     * 返回列表偏移量index的数据 下标从0开始
     */
    @Test
    public void lindex(){
        List list = new LinkedList();
        list.add("cctv2");
        System.out.println(redisTemplate.opsForList().leftPush("lindex", list));
        List list1 = new LinkedList();
        list1.add("cctv1");
        System.out.println(redisTemplate.opsForList().leftPush("lindex", list1));
        List aa = (List)redisTemplate.opsForList().index("lindex",0);
        System.out.println(aa.get(0));
    }

    /**
     *Redis Documentation: LRANGE
     * 取列表数据 0起始位
     */
    @Test
    public void lrange(){
        User user = new User();
        user.setName("小王");
        user.setAge(1);
        List<User> list = new ArrayList();
        list.add(user);


        User user1 = new User();
        user1.setName("小李");
        user1.setAge(2);
        List<User> list1 = new ArrayList();
        list1.add(user1);

        List<User> list2 = new ArrayList();

        User user2 = new User();
        user2.setName("小刘");
        user2.setAge(3);
        list2.add(user2);
        System.out.println(redisTemplate.opsForList().leftPush("lrange", list));
        System.out.println(redisTemplate.opsForList().leftPush("lrange", list1));
        System.out.println(redisTemplate.opsForList().leftPush("lrange", list2));
        List<List<User>> aa = redisTemplate.opsForList().range("lrange", 0, -1);
        for (List<User> a:aa){
            System.out.println("姓名"+a.get(0).getName()+"年龄"+a.get(0).getAge());
        }
//        System.out.println(aa);
//        System.out.println(list.get(0));
//        System.out.println(list1);
//        System.out.println(list2);

    }


    /**
     * 删除元素 值保留 start end 之间的数据(如果删除所有则可以 设置 start:1 end:0)
     * Redis Documentation: LTRIM
     */
    @Test
    public void ltrim(){
        List list = new LinkedList();
        list.add("cctv2");
        redisTemplate.opsForList().leftPush("dsadsad", list);
        redisTemplate.opsForList().trim("ltirm",0,1);
    }

    /**
     * 阻塞行为
     * 当 BLPOP 被调用时，如果给定 key 内至少有一个非空列表，那么弹出遇到的第一个非空列表的头元素，并和被弹出元素所属的列表的名字一起，组成结果返回给调用者
     * 当存在多个给定 key 时， BLPOP 按给定 key 参数排列的先后顺序，依次检查各个列表 从某一个列表的左边取出值
     * 如果所有给定 key 都不存在或包含空列表，那么 BLPOP 命令将阻塞连接，直到等待超时 或有另一个客户端对给定 key 的任意一个执行push添加值
     * Redis Documentation: BLPOP
     */
    @Test
    public void blpop(){
        List list1 = new ArrayList();
        list1.add("a");
        List list2 = new ArrayList();
        list2.add("b");

        redisTemplate.opsForList().leftPush("blpop1",list1);
        redisTemplate.opsForList().leftPush("blpop2",list2);
        List print = (List)redisTemplate.opsForList().leftPop("blpop2", 1, TimeUnit.MINUTES);
        System.out.println(print.get(0));
    }

    /**
     * 当 BLPOP 被调用时，如果给定 key 内至少有一个非空列表，那么弹出遇到的第一个非空列表的头元素，并和被弹出元素所属的列表的名字一起，组成结果返回给调用者
     * 当存在多个给定 key 时， BLPOP 按给定 key 参数排列的先后顺序，依次检查各个列表 从某一个列表的右边取出值
     * 如果所有给定 key 都不存在或包含空列表，那么 BLPOP 命令将阻塞连接，直到等待超时 或有另一个客户端对给定 key 的任意一个执行push添加值
     * Redis Documentation: BRPOP
     */
    @Test
    public void brpop(){
        List list1 = new ArrayList();
        list1.add("a");
        List list2 = new ArrayList();
        list2.add("b");

        redisTemplate.opsForList().leftPush("brpop1",list1);
        redisTemplate.opsForList().leftPush("brpop2",list2);
        List print = (List)redisTemplate.opsForList().rightPop("brpop2", 1, TimeUnit.MINUTES);
        System.out.println(print.get(0));
    }

    /**
     * 将列表 rpoplpush1 中的最后一个元素(尾元素)弹出 将 rpoplpush1 弹出的元素插入到列表 rpoplpush2开头
     * Redis Documentation: RPOPLPUSH
     */
    @Test
    public void rpoplpush(){
        List list1 = new ArrayList();
        list1.add("a");

        List list2 = new ArrayList();
        list2.add("b");

        List list3 = new ArrayList();
        list3.add("c");

        redisTemplate.opsForList().leftPush("rpoplpush1",list1);
        redisTemplate.opsForList().leftPush("rpoplpush1",list3);
        redisTemplate.opsForList().leftPush("rpoplpush2",list2);
        redisTemplate.opsForList().rightPopAndLeftPush("rpoplpush1","rpoplpush2");
    }


    /**
     * 将列表 rpoplpush1 中的最后一个元素(尾元素)弹出 将 rpoplpush1 弹出的元素插入到列表 rpoplpush2开头
     * 如果 rpoplpush1为空则在timeout秒之内阻塞并等待rpoplpush1有元素插入
     * Redis Documentation: RPOPLPUSH
     */
    @Test
    public void brpoplpush(){
        List list1 = new ArrayList();
        list1.add("a");

        List list2 = new ArrayList();
        list2.add("b");

        List list3 = new ArrayList();
        list3.add("c");

        redisTemplate.opsForList().leftPush("rpoplpush1",list1);
        redisTemplate.opsForList().leftPush("rpoplpush1",list3);
        redisTemplate.opsForList().leftPush("rpoplpush2",list2);
        redisTemplate.opsForList().rightPopAndLeftPush("rpoplpush1","rpoplpush2",60,TimeUnit.SECONDS);

    }
}

class User {

    private String name;

    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
