package com.dream.redis7.demo;

//import redis.clients.jedis.Jedis;


import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * @Author huzejun
 * @Date 2025-07-13 16:58
 **/
public class JedisDemo {

    public static void main(String[] args) {
        //1 connection获得，通过指定id和端口号
        Jedis jedis = new Jedis("192.168.1.60", 6379);
        //2.指定访问服务器的密友
        jedis.auth("111111");

        //3 a获得jedis客户端，可以像jdbc一样，访问我们的redis
        System.out.println(jedis.ping());

        //keys
        Set<String> keys = jedis.keys("*");
        System.out.println(keys);

        //string
        jedis.set("k3","hello-jedis");
        System.out.println(jedis.del("k3"));
        System.out.println(jedis.ttl("k3"));
        jedis.expire("k3",20);

        //list
        jedis.lpush("list","11","12","13");
        List<String> list = jedis.lrange("list", 0, -1);
        for (String element : list) {
            System.out.println(element);
        }
        //家庭作业

    }
}
