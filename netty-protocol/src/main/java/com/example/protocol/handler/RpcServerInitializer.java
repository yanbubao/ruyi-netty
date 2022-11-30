package com.example.protocol.handler;

import com.example.protocol.codec.RpcDecoder;
import com.example.protocol.codec.RpcEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author yanzx
 * @Date 2022/11/26 16:44
 */
@Slf4j
public class RpcServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        log.info("[NettyServer] begin server initializer.");
        socketChannel.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(
                        Integer.MAX_VALUE,
                        12,
                        4,
                        0,
                        0))
                .addLast(new RpcDecoder())
                .addLast(new RpcEncoder())
                .addLast(new RpcServerHandler());
    }
}
