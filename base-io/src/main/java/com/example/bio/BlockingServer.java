package com.example.bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author yanzx
 * @Date 2022/11/18 23:59
 */
public class BlockingServer {

    final static ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    public static void main(String[] args) {

        try (
                // 监听端口
                ServerSocket serverSocket = new ServerSocket(8001)
        ) {

            // 等待客户端的连接过来,如果没有连接过来，就会阻塞
            while (true) {
                // 阻塞IO中一个线程只能处理一个连接
                Socket socket = serverSocket.accept();
                EXECUTOR_SERVICE.execute(() -> {
                    // 获取数据
                    String line;
                    try {
                        // 获取Socket中的输入流
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        line = bufferedReader.readLine();
                        System.out.println("客户端的数据：" + line);

                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        bufferedWriter.write("ok\n");
                        bufferedWriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                });

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
