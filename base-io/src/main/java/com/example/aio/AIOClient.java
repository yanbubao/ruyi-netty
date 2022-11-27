package com.example.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * @Author yanzx
 * @Date 2022/11/19 00:36
 */
public class AIOClient {
    private final AsynchronousSocketChannel client;

    public AIOClient() throws IOException {
        client = AsynchronousSocketChannel.open();
    }

    public static void main(String[] args) throws Exception {
        new AIOClient().connect("localhost", 8001);
    }

    public void connect(String host, int port) throws Exception {
        // 客户端向服务端发起连接
        client.connect(new InetSocketAddress(host, port), null, new CompletionHandler<Void, Object>() {
            @Override
            public void completed(Void result, Object attachment) {
                try {
                    client.write(ByteBuffer.wrap("这是一条测试数据".getBytes())).get();
                    System.out.println("已发送到服务端");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });

        final ByteBuffer bb = ByteBuffer.allocate(1024);
        // 客户端接收服务端的数据，获取的数据写入到bb中
        client.read(bb, null, new CompletionHandler<Integer, Object>() {
            @Override
            public void completed(Integer result, Object attachment) {
                // 服务端返回数据的长度result
                System.out.println("I/O操作完成：" + result);
                System.out.println("获取反馈结果：" + new String(bb.array()));
            }

            @Override
            public void failed(Throwable exc, Object attachment) {
                exc.printStackTrace();
            }
        });

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
