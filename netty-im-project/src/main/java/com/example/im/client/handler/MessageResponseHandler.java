package com.example.im.client.handler;

import com.example.im.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author yanzx
 * @Date 2022/12/4 17:10
 */
@ChannelHandler.Sharable
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    public static final MessageResponseHandler INSTANCE = new MessageResponseHandler();

    private MessageResponseHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket responsePacket) {
        String fromUserId = responsePacket.getFromUserId();
        String fromUserName = responsePacket.getFromUserName();
        System.out.println(fromUserId + ":" + fromUserName + " -> " + responsePacket
                .getMessage());
    }
}
