package com.example.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;

/**
 * @Author yanzx
 * @Date 2022/11/19 00:41
 */
public class AIOServer {

    public static void main(String[] args) throws Exception {

        // 创建一个SocketChannel并绑定了8001端口
        final AsynchronousServerSocketChannel serverChannel =
                AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(8001));

        serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
            @Override
            public void completed(AsynchronousSocketChannel socketChannel, Object attachment) {

                try {
                    // 打印线程的名字
                    System.out.println("2--" + Thread.currentThread().getName());
                    System.out.println(socketChannel.getRemoteAddress());
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    // socketChannel异步的读取数据到buffer中
                    socketChannel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer attachment) {
                            // 打印线程的名字
                            System.out.println("3--" + Thread.currentThread().getName());
                            buffer.flip();
                            System.out.println(StandardCharsets.UTF_8.decode(buffer));
                            socketChannel.write(ByteBuffer.wrap("HelloClient".getBytes(StandardCharsets.UTF_8)));
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
                            exc.printStackTrace();
                        }

                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });

        System.out.println("1--" + Thread.currentThread().getName());
        Thread.sleep(Integer.MAX_VALUE);
    }
}
