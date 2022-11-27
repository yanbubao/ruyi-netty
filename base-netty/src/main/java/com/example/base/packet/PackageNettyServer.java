package com.example.base.packet;

import com.example.base.NettyConstants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Author yanzx
 * @Date 2022/11/22 14:52
 */
public class PackageNettyServer {

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup(NettyConstants.WORK_THREAD_NUM);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline()
                                // 固定长度解码器FixedLengthFrameDecoder
                                //.addLast(new FixedLengthFrameDecoder(8))

                                /**
                                 * maxFrameLength，最大字节长度
                                 * lengthFieldOffset，长度字段的偏移量，也就是存放长度数据的起始位置
                                 * lengthFieldLength，长度字段锁占用的字节数
                                 * lengthAdjustment，在一些较为复杂的协议设计中，长度域不仅仅包含消息的长度，还包含其他数据比如版本号、数据类型、数据状态等，这个时候我们可以使用lengthAdjustment进行修正，它的值=包体的长度值-长度域的值
                                 * initialBytesToStrip，解码后需要跳过的初始字节数，也就是消息内容字段的起始位置
                                 * lengthFieldEndOffset，长度字段结束的偏移量， 该属性的值=lengthFieldOffset+lengthFieldLength
                                 */
                                .addLast(
                                        new LengthFieldBasedFrameDecoder(
                                                1024 * 1024,
                                                0, 2,
                                                0, 2))

                                .addLast(new StringDecoder())
                                //.addLast(new SimpleServerHandler());
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        System.out.println("[Server]服务器端收到消息：" + msg);
                                    }
                                });
                    }
                });

        try {
            ChannelFuture channelFuture = serverBootstrap.bind(8000).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
