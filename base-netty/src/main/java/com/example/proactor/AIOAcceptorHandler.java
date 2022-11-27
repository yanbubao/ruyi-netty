package com.example.proactor;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @Author yanzx
 * @Date 2022/11/19 23:57
 */
public class AIOAcceptorHandler implements CompletionHandler<AsynchronousSocketChannel, AIOProactor> {
    @Override
    public void completed(AsynchronousSocketChannel channel, AIOProactor serverHandler) {
        // 每接收一个连接之后，再执行一次异步连接请求，这样就能一直处理多个连接
        serverHandler.asynchronousServerSocketChannel.accept(serverHandler, this);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 异步读取
        channel.read(byteBuffer, byteBuffer, new ReadHandler(channel));
    }

    @Override
    public void failed(Throwable exc, AIOProactor attachment) {
        attachment.countDownLatch.countDown();
    }
}
