package com.dream.redis7.iomultiplex.nio;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author huzejun
 * @Date 2025-08-23 10:52
 **/
public class RedisClient02 {
    public static void main(String[] args) throws IOException {
        System.out.println("-----------RedisClient02 start");
        Socket socket = new Socket("192.168.1.4", 6379);
        OutputStream outputStream = socket.getOutputStream();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String string = scanner.nextLine();
            if (string.equalsIgnoreCase("quit")) {
                break;
            }
            socket.getOutputStream().write(string.getBytes());
            System.out.println("-------------input quit keyword to finish....");
        }

        outputStream.close();
        socket.close();
    }
}
