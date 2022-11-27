package com.example.base.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.NettyRuntime;

/**
 * 开发一个主从多reactor多线程模型的服务demo
 *
 * @Author yanzx
 * @Date 2022/11/18 23:18
 */
public class NettyBasicServerExample {

    public static void main(String[] args) {
        new NettyBasicServerExample().bind(8000);
    }

    private final static int OS_SYSTEM_CPU_NUM = NettyRuntime.availableProcessors();

    public void bind(int port) {
        // 主线程组
        NioEventLoopGroup bossEventLoopGroup = new NioEventLoopGroup();
        // worker线程组
        NioEventLoopGroup workerEventLoopGroup = new NioEventLoopGroup(OS_SYSTEM_CPU_NUM + 1);

        // 服务端要启动，需要创建ServerBootStrap，在这里面netty把nio的模板式的代码都给封装好了
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap
                // 配置boss和worker线程
                .group(bossEventLoopGroup, workerEventLoopGroup)
                /**
                 * 配置Server的通道，相当于NIO中的ServerSocketChannel:
                 * 1.EpollServerSocketChannel，epoll模型只有在linux kernel 2.6以上才能支持
                 * 在windows和mac都是不支持的，如果设置Epoll在window环境下运行会报错;
                 * 2.OioServerSocketChannel，用于服务端阻塞地接收TCP连接;
                 * 3.KQueueServerSocketChannel，kqueue模型，是Unix中比较高效的IO复用技术；
                 * 常见的IO复用技术有select, poll, epoll以及kqueue等等。其中epoll为Linux独占，而kqueue则在许多UNIX系统上存在
                 */
                .channel(NioServerSocketChannel.class)
                // childHandler表示给worker那些线程配置了一个处理器
                // 配置初始化channel，也就是给worker线程配置对应的handler，当收到客户端的请求时，分配给指定的handler处理
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                // 添加handler，也就是具体的IO事件处理器
                                .addLast(new NormalMessageHandler());
                    }
                });

        try {
            // 由于默认情况下是NIO异步非阻塞，所以绑定端口后，通过sync()方法阻塞直到连接建立
            // 绑定端口并同步等待客户端连接（sync方法会阻塞，直到整个启动过程完成）
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            System.out.println("Netty Server Started, Listening on :" + port);
            // 等待服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 释放线程资源
            bossEventLoopGroup.shutdownGracefully();
            workerEventLoopGroup.shutdownGracefully();
        }

    }
}
