package com.example.reactor.multi;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author yanzx
 * @Date 2022/11/19 23:17
 */
public class MultiAcceptor implements Runnable {

    final Selector selector;
    final ServerSocketChannel serverSocketChannel;

    public MultiAcceptor(Selector selector, ServerSocketChannel serverSocketChannel) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;

    }

    @Override
    public void run() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ, new MultiDispatchHandler(socketChannel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
