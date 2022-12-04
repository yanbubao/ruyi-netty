package com.example.im.client.handler;

import com.example.im.protocol.Packet;
import com.example.im.protocol.command.Command;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * 缩短事件传播路径
 * 合并平业务handler
 *
 * @Author yanzx
 * @Date 2022/12/4 16:18
 */
@ChannelHandler.Sharable
public class IMClientHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMClientHandler INSTANCE = new IMClientHandler();

    private final Map<Byte, SimpleChannelInboundHandler<? extends Packet>> CLIENT_HANDLER_MAP;

    private IMClientHandler() {
        this.CLIENT_HANDLER_MAP = new HashMap<>();
        this.CLIENT_HANDLER_MAP.put(Command.MESSAGE_RESPONSE, MessageResponseHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        Byte command = packet.getCommand();
        if (CLIENT_HANDLER_MAP.containsKey(command)) {
            CLIENT_HANDLER_MAP.get(command).channelRead(ctx, packet);
        }
    }

}
