package com.example.base.packet.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @Author yanzx
 * @Date 2022/11/22 14:59
 */
public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        byte[] req = new byte[in.readableBytes()];
        in.readBytes(req);
        String body = new String(req, StandardCharsets.UTF_8);
        System.out.println("[Server]服务器端收到消息：" + body);
        // 写回数据
        ByteBuf resp = Unpooled.copiedBuffer(UUID.randomUUID().toString().getBytes());
        ctx.writeAndFlush(resp);
    }
}
