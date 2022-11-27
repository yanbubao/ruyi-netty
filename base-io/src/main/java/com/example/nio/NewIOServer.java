package com.example.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author yanzx
 * @Date 2022/11/19 00:25
 */
public class NewIOServer {
    static Selector selector;

    public static void main(String[] args) {

        try (
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()
        ) {
            selector = Selector.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(8001));

            // 需要把serverSocketChannel注册到多路复用器上
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                // 阻塞
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        handlerAccept(key);
                    } else if (key.isReadable()) {
                        handlerRead(key);
                    } else if (key.isWritable()) {
                        // ...
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handlerRead(SelectionKey key) {
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        try (SocketChannel socketChannel = (SocketChannel) key.channel()) {
            socketChannel.read(allocate);
            System.out.println("server msg:" + new String(allocate.array()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void handlerAccept(SelectionKey key) {
        // 从selector中获取ServerSocketChannel，因为当初把ServerSocketChannel注册再selector上，并且注册的accept事件
        try (ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel()) {
            // 不阻塞，能到这里，一定时有客户端连接过来，所以一定会有连接
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.write(ByteBuffer.wrap("server received connection success".getBytes(StandardCharsets.UTF_8)));
            // 然后注册read事件，等while的循环再次获取read事件，然后读取SocketChannel中的数据
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
