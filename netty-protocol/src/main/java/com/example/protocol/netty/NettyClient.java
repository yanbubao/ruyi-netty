package com.example.protocol.netty;

import com.example.protocol.core.Request;
import com.example.protocol.core.RpcProtocol;
import com.example.protocol.handler.RpcClientInitializer;
import com.example.registry.ServiceMetaData;
import com.example.registry.discovery.RegistryService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author yanzx
 * @Date 2022/11/26 18:33
 */
@Slf4j
public class NettyClient {
    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

    public NettyClient() {
        log.info("[NettyClient] begin init Netty Client.");
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new RpcClientInitializer());
    }

    public void sendRequest(RpcProtocol<Request> protocol, RegistryService registryService) throws Exception {
        // 从注册中心发现服务信息
        ServiceMetaData serviceMetaData = registryService.discovery(protocol.getBody().getClassName());

        final ChannelFuture future = bootstrap.connect(
                serviceMetaData.getServiceAddress(), serviceMetaData.getServicePort()).sync();

        future.addListener(listener -> {
            if (future.isSuccess()) {
                log.info("[NettyClient] connect rpc server: {} success.", serviceMetaData.getServiceAddress());
            } else {
                log.error("[NettyClient] connect rpc server: {} failed. ", serviceMetaData.getServiceAddress());
                future.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }
        });
        log.info("[NettyClient] begin transfer data.");
        future.channel().writeAndFlush(protocol);
    }
}
