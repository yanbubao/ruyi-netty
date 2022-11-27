package com.example.reactor.single;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * server
 *
 * @Author yanzx
 * @Date 2022/11/19 22:37
 */
public class Reactor implements Runnable {

    final Selector selector;

    public Reactor(int port, String threadName) throws IOException {
        System.out.println("reactor thread is running, thread: " + threadName);

        selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, new Acceptor(selector, serverSocketChannel));
    }

    @Override
    public void run() {

        while (!Thread.interrupted()) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    dispatch(key);
                    iterator.remove();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatch(SelectionKey key) {
        // 如果是accept，这里的runnable就是Acceptor
        // 如果是read事件，这里的runnable就是handler
        Runnable task = (Runnable) key.attachment();
        if (task != null) {
            task.run();
        }
    }
}
