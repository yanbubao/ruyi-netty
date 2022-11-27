package com.example.reactor.multi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * multi-reactor server
 *
 * @Author yanzx
 * @Date 2022/11/19 23:04
 */
public class MultiReactor implements Runnable {

    final Selector selector;

    public MultiReactor(int port) throws IOException {
        this.selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, new MultiAcceptor(selector, serverSocketChannel));
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            try {
                // blocking
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

    public void dispatch(SelectionKey selectionKey) {
        // 如果是accept，这里的runnable就是Acceptor
        // 如果是read事件，这里的runnable就是handler
        Runnable runnable = (Runnable) selectionKey.attachment();
        if (runnable != null) {
            runnable.run();
        }
    }
}
