package com.example.base.packet;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author yanzx
 * @Date 2022/11/22 15:02
 */
public class PacketNettyClient {

    public static void main(String[] args) {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                // 表示传输消息的时候，在消息报文中增加2个字节的length。->发送的ByteBuf
                                .addLast(new LengthFieldPrepender(2, 0, false))
                                .addLast(new StringEncoder())
                                // .addLast(new SimpleClientHandler());
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        System.out.println("客户端建立连接成功..");

                                        ctx.writeAndFlush("i am first request");
                                        ctx.writeAndFlush("i am second request");
                                    }
                                });
                    }
                });

        try {
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8000).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
