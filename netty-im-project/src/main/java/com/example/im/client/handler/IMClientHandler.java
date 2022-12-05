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
        CLIENT_HANDLER_MAP = new HashMap<>();
        CLIENT_HANDLER_MAP.put(Command.MESSAGE_RESPONSE, MessageResponseHandler.INSTANCE);
        CLIENT_HANDLER_MAP.put(Command.CREATE_GROUP_RESPONSE, CreateGroupResponseHandler.INSTANCE);
        CLIENT_HANDLER_MAP.put(Command.JOIN_GROUP_RESPONSE, JoinGroupResponseHandler.INSTANCE);
        CLIENT_HANDLER_MAP.put(Command.QUIT_GROUP_RESPONSE, QuitGroupResponseHandler.INSTANCE);
        CLIENT_HANDLER_MAP.put(Command.LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersResponseHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        Byte command = packet.getCommand();
        if (CLIENT_HANDLER_MAP.containsKey(command)) {
            CLIENT_HANDLER_MAP.get(command).channelRead(ctx, packet);
        }
    }

}
