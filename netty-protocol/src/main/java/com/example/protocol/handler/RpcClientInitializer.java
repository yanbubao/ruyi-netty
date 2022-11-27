package com.example.protocol.handler;

import com.example.protocol.codec.RpcDecoder;
import com.example.protocol.codec.RpcEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author yanzx
 * @Date 2022/11/26 18:34
 */
@Slf4j
public class RpcClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        log.info("[NettyClient] begin client initializer.");
        socketChannel.pipeline().addLast(
                        new LengthFieldBasedFrameDecoder(
                                Integer.MAX_VALUE,
                                12,
                                4,
                                0,
                                0))

                .addLast(new LoggingHandler())
                .addLast(new RpcEncoder())
                .addLast(new RpcDecoder())
                .addLast(new RpcClientHandler());
    }
}
