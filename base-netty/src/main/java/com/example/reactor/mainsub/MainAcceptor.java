package com.example.reactor.mainsub;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author yanzx
 * @Date 2022/11/19 23:42
 */
public class MainAcceptor implements Runnable {

    final ServerSocketChannel serverSocketChannel;
    int index = 0;
    Selector[] selectors = new Selector[Runtime.getRuntime().availableProcessors() * 2];
    SubReactor[] subReactors = new SubReactor[Runtime.getRuntime().availableProcessors() * 2];
    Thread[] threads = new Thread[Runtime.getRuntime().availableProcessors() * 2];

    public MainAcceptor(ServerSocketChannel serverSocketChannel) throws IOException {
        this.serverSocketChannel = serverSocketChannel;
        for (int i = 0; i < Runtime.getRuntime().availableProcessors() * 2; i++) {
            selectors[i] = Selector.open();
            subReactors[i] = new SubReactor(selectors[i]);
            threads[i] = new Thread(subReactors[i]);
            // 每一个服务员都像线程一样启动
            threads[i].start();
        }
    }

    @Override
    public void run() {
        SocketChannel socketChannel;
        try {
            socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            // 唤醒阻塞的selector
            selectors[index].wakeup();
            // SubReactor的selector去调度WorkerHandler
            socketChannel.register(selectors[index], SelectionKey.OP_READ, new WorkerHandler(socketChannel));

            // 当前selector处理完成后，交给下一个SubReactor->selector去调度WorkerHandler
            if (++index == threads.length) {
                index = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
