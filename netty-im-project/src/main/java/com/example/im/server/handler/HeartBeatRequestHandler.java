package com.example.im.server.handler;

import com.example.im.protocol.request.HeartBeatRequestPacket;
import com.example.im.protocol.response.HeartBeatResponsePacket;
import com.example.im.util.TimeUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 心跳监测Handler
 *
 * @Author yanzx
 * @Date 2022/12/4 15:14
 */
@Slf4j
@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {

    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket requestPacket) {
        log.info("[NettyServer] received heartbeat, datetime:{}", TimeUtil.timeString());
        ctx.writeAndFlush(new HeartBeatResponsePacket());
    }
}
