package com.example.base.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

/**
 * @Author yanzx
 * @Date 2022/11/18 23:27
 */
public class NormalMessageHandler extends ChannelInboundHandlerAdapter {

    /**
     * channelRead方法表示读到消息以后如何处理，这里我们把消息打印出来
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf req = (ByteBuf) msg;
        byte[] reqBytes = new byte[req.readableBytes()];
        req.readBytes(reqBytes);
        String reqData = new String(reqBytes, StandardCharsets.UTF_8);
        System.out.println("server received data: " + reqData);

        // 回写数据到客户端
        String respData = "server received success. client data: " + reqData;
        ByteBuf resp = Unpooled.copiedBuffer(respData.getBytes());
        // ctx.write表示把消息再发送回客户端，但是仅仅是写到缓冲区，没有发送，flush才会真正写到网络上去
        ctx.write(resp);
    }

    /**
     * channelReadComplete方法表示消息读完了的处理，writeAndFlush方法表示写入并发送消息
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 这里的逻辑就是所有的消息读取完毕了，在统一写回到客户端
        // Unpooled.EMPTY_BUFFER表示空消息，addListener(ChannelFutureListener.CLOSE)表示写完后，就关闭连接
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // exceptionCaught方法就是发生异常的处理
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
    }

}
