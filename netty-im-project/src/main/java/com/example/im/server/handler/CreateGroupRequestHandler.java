package com.example.im.server.handler;

import com.example.im.protocol.request.CreateGroupRequestPacket;
import com.example.im.protocol.response.CreateGroupResponsePacket;
import com.example.im.util.IDUtil;
import com.example.im.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author yanzx
 * @Date 2022/12/5 20:52
 */
@ChannelHandler.Sharable
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    private CreateGroupRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket requestPacket) throws Exception {
        List<String> userIdList = requestPacket.getUserIdList();

        // 创建一个Channel分组
        DefaultChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        // 筛选出加入群聊的用户
        List<String> userNameList = new ArrayList<>();
        userIdList.forEach(userId -> {
            Channel channel = SessionUtil.getChannel(userId);
            // 用户存在Channel即可加入群聊
            if (channel != null) {
                channelGroup.add(channel);
                userNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        });

        // 构建响应
        String groupId = IDUtil.randomId();
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setGroupId(groupId);
        createGroupResponsePacket.setUserNameList(userNameList);

        // 给群聊中每个用户推送创建结果消息
        channelGroup.writeAndFlush(createGroupResponsePacket);

        // 维护Channel组Session信息
        SessionUtil.bindChannelGroup(groupId, channelGroup);

        System.out.print("群创建成功，id 为 " + createGroupResponsePacket.getGroupId() + ", ");
        System.out.println("群里面有：" + createGroupResponsePacket.getUserNameList());
    }
}
