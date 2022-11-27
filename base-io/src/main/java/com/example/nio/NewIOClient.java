package com.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author yanzx
 * @Date 2022/11/19 00:15
 */
public class NewIOClient {

    static Selector selector;

    public static void main(String[] args) {
        try {
            selector = Selector.open();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("localhost", 8001));

            // 需要把socketChannel注册到多路复用器上
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            while (true) {
                // 阻塞
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isConnectable()) {
                        handlerConnect(key);
                    } else if (key.isReadable()) {
                        handlerRead(key);
                    } else if (key.isWritable()) {
                        // ...
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handlerRead(SelectionKey key) {

        try (
                SocketChannel socketChannel = (SocketChannel) key.channel();
        ) {
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            socketChannel.read(allocate);
            System.out.println("client msg:" + new String(allocate.array()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handlerConnect(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        if (socketChannel.isConnectionPending()) {
            socketChannel.finishConnect();
        }
        socketChannel.configureBlocking(false);
        socketChannel.write(ByteBuffer.wrap("client connection success.".getBytes(StandardCharsets.UTF_8)));
        socketChannel.register(selector, SelectionKey.OP_READ);
    }
}
