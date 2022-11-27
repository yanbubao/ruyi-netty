package com.example.reactor.single;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author yanzx
 * @Date 2022/11/19 22:42
 */
public class Acceptor implements Runnable {

    final Selector selector;
    final ServerSocketChannel serverSocketChannel;

    public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }


    @Override
    public void run() {
        System.out.println("Acceptor is running.");
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ, new Handler(socketChannel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
