package com.example.im.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 空闲检测Handler
 * <p>
 * 此Handler是有状态的，即每个Channel都享有一个实例，所以不做成单例模式
 *
 * @Author yanzx
 * @Date 2022/12/3 14:58
 */
public class IMIdleStateHandler extends IdleStateHandler {

    /**
     * 空闲检测时间间隔
     */
    private static final int READER_IDLE_TIME = 15;

    public IMIdleStateHandler() {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        System.out.println("[IMIdleStateHandler] " + READER_IDLE_TIME + "秒内未读到数据，关闭连接");
        ctx.channel().close();
    }
}
