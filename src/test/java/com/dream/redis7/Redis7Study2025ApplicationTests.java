package com.dream.redis7;

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

}
