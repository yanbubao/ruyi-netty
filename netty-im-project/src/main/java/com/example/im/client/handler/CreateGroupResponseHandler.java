package com.example.im.client.handler;

import com.example.im.protocol.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author yanzx
 * @Date 2022/12/5 21:08
 */
@Slf4j
@ChannelHandler.Sharable
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    public static final CreateGroupResponseHandler INSTANCE = new CreateGroupResponseHandler();

    private CreateGroupResponseHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket responsePacket) throws Exception {
        if (responsePacket.isSuccess()) {
            System.out.print("群创建成功，id 为[" + responsePacket.getGroupId() + "], ");
            System.out.println("群里面有：" + responsePacket.getUserNameList());
        } else {
            log.debug("[CreateGroupResponseHandler] 创建群聊失败");
        }
    }
}
