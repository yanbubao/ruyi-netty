package com.example.im.server.handler;

import com.example.im.protocol.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

import static com.example.im.protocol.command.Command.*;

/**
 * 缩短事件传播路径
 * 合并平业务handler
 *
 * @Author yanzx
 * @Date 2022/12/4 15:00
 */
@ChannelHandler.Sharable
public class IMServerHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMServerHandler INSTANCE = new IMServerHandler();

    private final Map<Byte, SimpleChannelInboundHandler<? extends Packet>> SERVER_HANDLER_MAP;

    private IMServerHandler() {
        SERVER_HANDLER_MAP = new HashMap<>();
        SERVER_HANDLER_MAP.put(MESSAGE_REQUEST, MessageRequestHandler.INSTANCE);
        SERVER_HANDLER_MAP.put(CREATE_GROUP_REQUEST, CreateGroupRequestHandler.INSTANCE);
        SERVER_HANDLER_MAP.put(JOIN_GROUP_REQUEST, JoinGroupRequestHandler.INSTANCE);
//        SERVER_HANDLER_MAP.put(QUIT_GROUP_REQUEST, QuitGroupRequestHandler.INSTANCE);
//        SERVER_HANDLER_MAP.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestHandler.INSTANCE);
//        SERVER_HANDLER_MAP.put(GROUP_MESSAGE_REQUEST, GroupMessageRequestHandler.INSTANCE);

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        Byte command = packet.getCommand();
        if (SERVER_HANDLER_MAP.containsKey(command)) {
            SERVER_HANDLER_MAP.get(command).channelRead(ctx, packet);
        }
    }
}
