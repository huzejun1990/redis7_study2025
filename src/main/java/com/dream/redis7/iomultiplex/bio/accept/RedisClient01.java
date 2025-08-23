package com.dream.redis7.iomultiplex.bio.accept;

import java.io.IOException;
import java.net.Socket;

/**
 * @Author huzejun
 * @Date 2025-08-22 14:16
 **/
public class RedisClient01 {
    public static void main(String[] args) throws IOException {
        System.out.println("--------------RedisClient01 start");
//        Socket socket = new Socket("192.168.1.60",6379);    // 127.0.0.1
        Socket socket = new Socket("127.0.0.1",6379);    // 127.0.0.1
        System.out.println("--------------RedisClient01 connection over");
    }
}
