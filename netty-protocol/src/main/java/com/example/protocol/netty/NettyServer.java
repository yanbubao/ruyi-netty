package com.example.protocol.netty;

import com.example.protocol.handler.RpcServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author yanzx
 * @Date 2022/11/26 16:40
 */
@Slf4j
public class NettyServer {

    private final String serverAddress;

    private final int serverPort;

    public NettyServer(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void start() {
        log.info("[NettyServer] begin start..");
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new RpcServerInitializer());

        try {
            ChannelFuture channelFuture = serverBootstrap.bind(this.serverAddress, this.serverPort).sync();
            log.info("[NettyServer] started success, ip: {}, port: {}", this.serverAddress, this.serverPort);

            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
