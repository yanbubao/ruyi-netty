package com.example.reactor.single;

import java.io.IOException;

/**
 * @Author yanzx
 * @Date 2022/11/19 22:38
 */
public class SingleReactorMain {

    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(8000, "single-main")).start();
    }
}
