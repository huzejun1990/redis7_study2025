package com.dream.redis7.service;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.dream.redis7.entities.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author huzejun
 * @Date 2025-08-05 12:47
 **/
@Service
@Slf4j
public class JHSTaskService {

    private static final String JHS_KEY = "jhs";
    private static final String JHS_KEY_A = "jhs:a";
    private static final String JHS_KEY_B = "jhs:b";

    @Autowired
    private RedisTemplate redisTemplate;

    private List<Product> getProductsFromMysql() {
        List<Product> list = new ArrayList<>();
        for (int i = 1; i <= 20 ; i++) {
            Random rand = new Random();
            int id = rand.nextInt(10000);
            Product obj = new Product((long) id, "product" + i, i, "detail");
            list.add(obj);
        }
        return list;
    }

//    @PostConstruct
    public void initJHS() {

        log.info("启动定时器天猫聚划算功能模拟开始....哈哈~");

        //1 用线程模拟定时任务，后台任务定时将mysql里面的参加活动的商品刷新到redis里面
        new Thread(() -> {
            while (true) {
                //2 模拟从mysql查出数据，用于加载到redis并给聚划算页面显示
                List<Product> list = this.getProductsFromMysql();
                //3 采用redis list数据结构的lpush命令来存储
                redisTemplate.delete(JHS_KEY);
                //4 加入最新的数据给redis
                redisTemplate.opsForList().leftPushAll(JHS_KEY, list);
                //5 暂停1分钟,间隔一分钟执行一次，模拟聚划算一天执行的参加活动的品牌
                try {
                    TimeUnit.MINUTES.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();
    }

    @PostConstruct
    public void initJHSAB() {
        log.info("启动AB定时器计划任务天猫聚划算功能模拟........."+ DateUtil.now());

        //1 用线程模拟定时任务，后台任务定时将mysql里面的参加活动的商品刷新到redis里面
        new Thread(() -> {
            while (true) {
                //2 模拟从mysql查出数据，用于加载到redis并给聚划算页面显示
                List<Product> list = this.getProductsFromMysql();
                //3 先更新B缓存且让B缓存过期时间超过A缓存，如果A突然失效了还有B兜底，防止击穿
                redisTemplate.delete(JHS_KEY_B);
                redisTemplate.opsForList().leftPushAll(JHS_KEY_B, list);
                redisTemplate.expire(JHS_KEY_B, 86410L, TimeUnit.DAYS);
                //4 再更新A缓存
                redisTemplate.delete(JHS_KEY_A);
                redisTemplate.opsForList().leftPushAll(JHS_KEY_A, list);
                redisTemplate.expire(JHS_KEY_A, 86400L, TimeUnit.DAYS);

                //5 暂停1分钟,间隔一分钟执行一次，模拟聚划算一天执行的参加活动的品牌
                try {
                    TimeUnit.MINUTES.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();
    }

}
