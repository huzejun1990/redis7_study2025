package com.dream.redis7;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

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

}
