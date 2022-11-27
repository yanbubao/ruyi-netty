package com.example.proactor;

import java.io.IOException;

/**
 * @Author yanzx
 * @Date 2022/11/19 23:55
 */
public class ProactorMain {
    public static void main(String[] args) throws IOException {
        new Thread(new AIOProactor(8000)).start();
    }
}
