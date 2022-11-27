package com.example.reactor.multi;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Author yanzx
 * @Date 2022/11/19 23:19
 */
public class MultiDispatchHandler implements Runnable {
    final SocketChannel socketChannel;

    final Executor executor = Executors.newFixedThreadPool(1);

    public MultiDispatchHandler(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        executor.execute(new ReadHandler(socketChannel));
    }

    static class ReadHandler implements Runnable {

        final SocketChannel socketChannel;

        ReadHandler(SocketChannel socketChannel) {
            this.socketChannel = socketChannel;
        }

        @Override
        public void run() {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int length;
            StringBuilder readMessage = new StringBuilder();
            try {
                do {
                    length = socketChannel.read(byteBuffer);
                    readMessage.append(new String(byteBuffer.array()));
                    System.out.println("每次读取字节数量: " + length);
                } while (length > byteBuffer.capacity());

                System.out.println("Handler read message: " + readMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
