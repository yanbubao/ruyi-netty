package com.example.bio;

import java.io.*;
import java.net.Socket;

/**
 * @Author yanzx
 * @Date 2022/11/18 23:56
 */
public class BlockingClient {

    public static void main(String[] args) {

        try (
                Socket clientSocket = new Socket("localhost", 8001)
        ) {
            BufferedWriter bufferedWriter =
                    new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            bufferedWriter.write("我是客户端，收到请回答！！\n");
            bufferedWriter.flush();

            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line = bufferedReader.readLine();
            System.out.println(line);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
