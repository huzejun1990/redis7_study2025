package com.dream.redis7.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author huzejun
 * @Date 2025-07-24 16:42
 **/
@Service
@Slf4j
public class HyperLogLogService {


    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 模拟后台有用户点击淘宝首页www.bai
     */
    @PostConstruct
    public void initIP(){
        new Thread(() ->{
            String ip = null;   //255.255.2
            for (int i = 0; i < 200; i++) {
                Random random = new Random();
                ip = random.nextInt(256) + "."+
                        random.nextInt(256) + "."+
                        random.nextInt(256) + "."+
                        random.nextInt(256);

                Long hll = redisTemplate.opsForHyperLogLog().add("hll", ip);
                log.info("ip={},该ip地址访问首页的次数={}",ip,hll);
                //暂停3秒钟线程
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        },"t1").start();

    }

    public long uv() {
        //PFCOUNT

        return redisTemplate.opsForHyperLogLog().size("hll");
    }
}
