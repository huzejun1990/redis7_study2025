package com.dream.redis7.iomultiplex.bio.read.mthread;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author huzejun
 * @Date 2025-08-23 10:13
 **/
public class RedisClient01 {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 6379);
        OutputStream outputStream = socket.getOutputStream();

        while (true) {

            Scanner scanner = new Scanner(System.in);
            String string = scanner.nextLine();
            if (string.equals("quit")) {
                break;
            }
            socket.getOutputStream().write(string.getBytes());
            System.out.println("------------------RedisClient01 input quit keyword to finish.....");
        }
        outputStream.close();
        socket.close();

    }
}
