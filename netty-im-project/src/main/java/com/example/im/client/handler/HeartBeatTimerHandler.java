package com.example.im.client.handler;

import com.example.im.protocol.request.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * 心跳定时器Handler
 *
 * @Author yanzx
 * @Date 2022/12/4 17:48
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 发送心跳时间间隔delay
     */
    private static final int HEARTBEAT_INTERVAL = 60;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);
        super.channelActive(ctx);
    }

    private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                ctx.writeAndFlush(new HeartBeatRequestPacket());
                // 递归调用发送心跳
                scheduleSendHeartBeat(ctx);
            }
        }, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
    }

}
