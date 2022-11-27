package com.example.protocol.handler;

import com.example.protocol.constants.RequestTypeEnum;
import com.example.protocol.core.Request;
import com.example.protocol.core.Response;
import com.example.protocol.core.RpcProtocol;
import com.example.protocol.spring.server.Mediator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author yanzx
 * @Date 2022/11/26 16:45
 */
@Slf4j
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcProtocol<Request>> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                RpcProtocol<Request> requestRpcProtocol) throws Exception {
        // 调度服务
        log.info("[NettyServer] server handler start process.");
        Object result = Mediator.getInstance().process(requestRpcProtocol.getBody());

        // 构建Response
        RpcProtocol.Header header = requestRpcProtocol.getHeader();
        header.setRequestType(RequestTypeEnum.RESPONSE.code());
        RpcProtocol<Response> responseRpcProtocol = new RpcProtocol<>();
        responseRpcProtocol.setHeader(header);
        responseRpcProtocol.setBody(Response.builder().data(result).message("success").build());

        channelHandlerContext.writeAndFlush(responseRpcProtocol);
    }
}
