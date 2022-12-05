package com.example.im.server.handler;

import com.example.im.protocol.request.JoinGroupRequestPacket;
import com.example.im.protocol.response.JoinGroupResponsePacket;
import com.example.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @Author yanzx
 * @Date 2022/12/5 21:23
 */
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    private JoinGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket requestPacket) throws Exception {
        // 获取群对应的channelGroup，然后将当前用户的Channel添加进去
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        JoinGroupResponsePacket joinGroupResponsePacket = new JoinGroupResponsePacket();
        if (channelGroup != null) {
            channelGroup.add(ctx.channel());
            joinGroupResponsePacket.setSuccess(true);
            joinGroupResponsePacket.setGroupId(groupId);

        } else {
            joinGroupResponsePacket.setSuccess(false);
            joinGroupResponsePacket.setGroupId(groupId);
            joinGroupResponsePacket.setReason("群聊groupId不存在！");
        }
        ctx.writeAndFlush(joinGroupResponsePacket);
    }
}
