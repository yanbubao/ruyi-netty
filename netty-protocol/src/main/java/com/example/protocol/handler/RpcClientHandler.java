package com.example.protocol.handler;

import com.example.protocol.core.RequestHolder;
import com.example.protocol.core.Response;
import com.example.protocol.core.RpcProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author yanzx
 * @Date 2022/11/26 18:36
 */
@Slf4j
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcProtocol<Response>> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                RpcProtocol<Response> responseRpcProtocol) throws Exception {
        log.info("[NettyClient] receive rpc server message.");
        long requestId = responseRpcProtocol.getHeader().getRequestId();

        RequestHolder.RpcRequestFuture<Response> future = RequestHolder.REQUEST_FUTURE_MAP.remove(requestId);

        future.getPromise().setSuccess(responseRpcProtocol.getBody());
    }
}
