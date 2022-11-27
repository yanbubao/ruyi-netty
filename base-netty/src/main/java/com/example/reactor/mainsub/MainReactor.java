package com.example.reactor.mainsub;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * main-sub-reactor server
 *
 * @Author yanzx
 * @Date 2022/11/19 23:39
 */
public class MainReactor implements Runnable {

    final Selector selector;

    public MainReactor(int port) throws IOException {
        selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, new MainAcceptor(serverSocketChannel));
    }

    @Override
    public void run() {
        // 查看线程是否被中断过，只要没有中断，就一直等待客户端过来
        while (!Thread.interrupted()) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterable = selectionKeys.iterator();
                while (iterable.hasNext()) {
                    dispatch(iterable.next());
                    iterable.remove();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatch(SelectionKey selectionKey) {
        // 如果是accept，这里的runnable就是Acceptor
        // 如果是read事件，这里的runnable就是handler
        Runnable runnable = (Runnable) selectionKey.attachment();
        if (runnable != null) {
            runnable.run();
        }
    }
}
