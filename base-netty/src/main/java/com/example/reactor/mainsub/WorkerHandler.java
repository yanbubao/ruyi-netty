package com.example.reactor.mainsub;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Author yanzx
 * @Date 2022/11/19 23:46
 */
public class WorkerHandler implements Runnable {
    final SocketChannel socketChannel;


    public WorkerHandler(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;

    }

    @Override
    public void run() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        try {
            socketChannel.read(byteBuffer);
            String message = new String(byteBuffer.array(), StandardCharsets.UTF_8);
            System.out.println(socketChannel.getRemoteAddress() + ":" + message);
            socketChannel.write(ByteBuffer.wrap("消息收到了".getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
