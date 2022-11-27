package com.example.base.simple;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.ServerSocket;

/**
 * EventLoopEvent是可以独立使用的
 * 利用EventLoop的selector多路复用特性
 * <p>
 * EventLoop中持有一个Selector实例
 *
 * @Author yanzx
 * @Date 2022/11/18 23:45
 */
public class SingleEventLoopEventDemo {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup(2);
//        group.register(); //把某个channel注册到某一个EventLoop的Selector
        NioEventLoop eventExecutors = (NioEventLoop) group.next();
        ServerSocket ss = null;
//        System.out.println(group.next().register(ss));
        System.out.println(group.next());

        group.next().submit(() -> {
            System.out.println(Thread.currentThread().getName() + "----");
        });
    }
}
