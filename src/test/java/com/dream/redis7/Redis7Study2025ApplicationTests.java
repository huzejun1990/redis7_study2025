package com.dream.redis7;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class Redis7Study2025ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void test1() {
        System.out.println(new Date().getTime());
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void test2() {
        List<String> list = Arrays.asList("22.10.11.1","192.168.1.60","192.168.1.62","192.168.1.61");

        HashSet<String> sets = new HashSet<>(list);

    }

    /**
     * 模拟演示hash冲突
     */
    @Test
    public void testHash(){
        System.out.println("Aa".hashCode());
        System.out.println("BB".hashCode());

        System.out.println("柳柴".hashCode());
        System.out.println("柴杼".hashCode());

        System.out.println("----------------");
        Set<Integer> sets = new HashSet<>();
        int hashCode;
        for (int i = 0; i < 200000; i++) {
            hashCode = new Object().hashCode();
            if (sets.contains(hashCode)) {
                System.out.println("运行到第："+i+"次出现hash冲突，hashcode："+hashCode);
                continue;
            }
            sets.add(hashCode);
        }
    }

    /**
     * 创建guava版布隆过滤器,helloworld入门演示
     */
    @Test
    public void testGuavaWithBloomFilter() {
        // 1 创建guave版本布隆过滤器
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), 100);
        // 2 判断指定的元素是否存在
        System.out.println(bloomFilter.mightContain(1));
        System.out.println(bloomFilter.mightContain(2));

        System.out.println();

        //3 讲元素新增进入bloomfilter
        bloomFilter.put(1);
        bloomFilter.put(2);
        System.out.println(bloomFilter.mightContain(1));
        System.out.println(bloomFilter.mightContain(2));
    }


}
