package com.example.base.packet.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author yanzx
 * @Date 2022/11/22 16:56
 */
public class SimpleClientHandler extends ChannelInboundHandlerAdapter {

    final AtomicInteger MESSAGE_COUNT = new AtomicInteger(0);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端建立连接成功..");
        for (int i = 0; i < 100; i++) {
            ByteBuf byteBuf = Unpooled.copiedBuffer("I'm Client, id: " + i, StandardCharsets.UTF_8);
            ctx.writeAndFlush(byteBuf);
        }

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("[Client]读取服务端消息..");
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        // 写入bytes中
        byteBuf.readBytes(bytes);

        System.out.println("[Client]收到服务端消息内容：" + new String(bytes, StandardCharsets.UTF_8));
        System.out.println("客户端收到的消息数量：" + (MESSAGE_COUNT.addAndGet(1)));
        super.channelRead(ctx, msg);
    }
}
