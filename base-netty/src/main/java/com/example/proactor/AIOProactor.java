package com.example.proactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * @Author yanzx
 * @Date 2022/11/19 23:56
 */
public class AIOProactor implements Runnable {

    public CountDownLatch countDownLatch;
    public final AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AIOProactor(int port) throws IOException {
        asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
        asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
    }

    @Override
    public void run() {
        countDownLatch = new CountDownLatch(1);
        asynchronousServerSocketChannel.accept(this, new AIOAcceptorHandler());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
