package com.example.reactor.single;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Author yanzx
 * @Date 2022/11/19 22:45
 */
public class Handler implements Runnable {

    final SocketChannel socketChannel;

    public Handler(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public void run() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(3);
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
            throw new RuntimeException(e);
        }
    }
}
