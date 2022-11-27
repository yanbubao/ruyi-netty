package com.example.reactor.multi;

import java.io.IOException;

/**
 * @Author yanzx
 * @Date 2022/11/19 23:03
 */
public class MultiReactorMain {
    public static void main(String[] args) throws IOException {
        new Thread(new MultiReactor(8000)).start();
    }
}
