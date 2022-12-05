package com.example.im.server.handler;

import com.example.im.protocol.request.GroupMessageRequestPacket;
import com.example.im.protocol.response.GroupMessageResponsePacket;
import com.example.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @Author yanzx
 * @Date 2022/12/5 22:10
 */
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    private GroupMessageRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket requestPacket) {
        // 拿到groupId构造群聊消息的响应
        String groupId = requestPacket.getToGroupId();
        GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
        responsePacket.setFromGroupId(groupId);
        responsePacket.setMessage(requestPacket.getMessage());
        responsePacket.setFromUser(SessionUtil.getSession(ctx.channel()));

        // 拿到群聊对应的channelGroup写到每个客户端
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        assert channelGroup != null;
        channelGroup.writeAndFlush(responsePacket);
    }
}

