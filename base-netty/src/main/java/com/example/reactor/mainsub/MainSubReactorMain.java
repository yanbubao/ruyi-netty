package com.example.reactor.mainsub;

import java.io.IOException;

/**
 * @Author yanzx
 * @Date 2022/11/19 23:39
 */
public class MainSubReactorMain {
    public static void main(String[] args) throws IOException {
        new Thread(new MainReactor(8000)).start();
    }
}
