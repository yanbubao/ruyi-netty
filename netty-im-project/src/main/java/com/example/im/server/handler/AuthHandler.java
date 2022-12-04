package com.example.im.server.handler;

import com.example.im.util.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author yanzx
 * @Date 2022/12/4 15:25
 */
@ChannelHandler.Sharable
public class AuthHandler extends ChannelInboundHandlerAdapter {

    public static final AuthHandler INSTANCE = new AuthHandler();

    private AuthHandler() {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (SessionUtil.hasLogin(ctx.channel())) {
            // 用户已登录时后续不再进行鉴权
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        } else {
            System.out.println("[NettyServer] auth failed, msg: " + msg);
            ctx.channel().close();
        }
    }
}
