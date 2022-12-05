package com.example.im.server.handler;

import com.example.im.protocol.request.ListGroupMembersRequestPacket;
import com.example.im.protocol.response.ListGroupMembersResponsePacket;
import com.example.im.session.Session;
import com.example.im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yanzx
 * @Date 2022/12/5 21:58
 */
@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    private ListGroupMembersRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket requestPacket) {

        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);

        if (channelGroup == null) {
            responsePacket.setSuccess(false);
            responsePacket.setGroupId(groupId);
            responsePacket.setReason("群聊groupId不存在");
        } else {

            List<Session> sessionList = new ArrayList<>();
            for (Channel channel : channelGroup) {
                Session session = SessionUtil.getSession(channel);
                sessionList.add(session);
            }
            responsePacket.setSuccess(true);
            responsePacket.setGroupId(groupId);
            responsePacket.setSessionList(sessionList);
        }

        ctx.writeAndFlush(responsePacket);
    }
}
