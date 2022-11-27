package com.example.proactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

/**
 * @Author yanzx
 * @Date 2022/11/19 23:59
 */
public class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {
    private final AsynchronousSocketChannel socketChannel;

    public ReadHandler(AsynchronousSocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    /**
     * 读取到消息后处理
     */
    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        attachment.flip();
        byte[] message = new byte[attachment.remaining()];
        attachment.get(message);

        String str = new String(message, StandardCharsets.UTF_8);
        System.out.println("收到客户端的信息：" + str);
        //向客户端发送消息
        String clientStr = "收到客户端的消息！！！";
        doWrite(clientStr);

    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        exc.printStackTrace();
    }

    //发送消息
    private void doWrite(String result) {

        byte[] bytes = result.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();

        // 异步写数据，参数与前面的read一样
        // 将回调接口作为内部类实现了
        socketChannel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer buffer) {
                //如果没有发送完，就继续发送直到完成
                if (buffer.hasRemaining()) {
                    socketChannel.write(buffer, buffer, this);
                } else {
                    // 创建新的Buffer
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    // 异步读，第三个参数为接收消息回调的业务Handler
                    socketChannel.read(readBuffer, readBuffer, new ReadHandler(socketChannel));

                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    socketChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

    }
}
