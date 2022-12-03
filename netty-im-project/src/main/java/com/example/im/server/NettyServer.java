package com.example.im.server;

import com.example.im.handler.IMIdleStateHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.NettyRuntime;


/**
 * @Author yanzx
 * @Date 2022/12/3 14:50
 */
public class NettyServer {

    public static void main(String[] args) {
        startServer();
    }

    private static final int PORT = 8000;

    private final static int OS_SYSTEM_CPU_NUM = NettyRuntime.availableProcessors();

    private static void startServer() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(OS_SYSTEM_CPU_NUM * 2);
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                /**
                 * backlog 指定了内核为此套接口排队的最大连接个数，对于给定的监听套接口，内核要维护两个队列: 未连接队列和已连接队列；backlog的值即为未连接队列和已连接队列的和
                 * ps:临时存放已完成三次握手的请求的队列的最大长度。如果未设置或所设置的值小于1，Java将使用默认值50。如果大于队列的最大长度，请求会被拒绝
                 */
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                // 空闲监测
                                .addLast(new IMIdleStateHandler())

                        ;
                    }
                });

        bind(serverBootstrap);
    }

    private static void bind(ServerBootstrap serverBootstrap) {
        serverBootstrap.bind(NettyServer.PORT).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("[NettyServer] started, port: [" + NettyServer.PORT + "] bind success.");
            } else {
                System.out.println("[NettyServer] port: [" + NettyServer.PORT + "] bind failed.");
            }
        });
    }

}
