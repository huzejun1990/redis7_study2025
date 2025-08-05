package com.dream.redis7.service;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @Author huzejun
 * @Date 2025-08-02 13:15
 **/
@Service
@Slf4j
public class GuavaBloomFilterService {

    //1 定义一个常量
    public static final int _1w = 10000;
    //2 定义我们guava布隆过滤器，初始容量
    public static final int SIZE = 100 * _1w;
    // 3 误判率，它越小误判的个数也就越小(思考，是否可以是无限小??没有误判岂不是更好)
    public static final double fpp = 0.01;

    //4 创建guava布隆过滤器
    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(),SIZE,fpp);

    public void guavaBloomFilter() {

        //1 先让bloomFilter加入100w白名单数据
        for (int i = 0; i < SIZE; i++) {
            bloomFilter.put(i);
        }
        //2 故意取10w不存合法范围内的数，来进行误判率的演示
        ArrayList<Integer> list = new ArrayList<>(10 * _1w);

        //3 验证
        for (int i = SIZE+1; i < SIZE+(10 * _1w); i++) {
            if (bloomFilter.mightContain(i)) {
                log.info("被误判了：{}",i);
                list.add(i);
            }
        }
        log.info("误判总数量：{}",list.size());

    }

}
