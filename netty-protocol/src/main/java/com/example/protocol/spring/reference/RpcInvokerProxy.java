package com.example.protocol.spring.reference;

import com.example.protocol.constants.ProtocolConstants;
import com.example.protocol.constants.RequestTypeEnum;
import com.example.protocol.constants.SerializationTypeEnum;
import com.example.protocol.core.Request;
import com.example.protocol.core.RequestHolder;
import com.example.protocol.core.Response;
import com.example.protocol.core.RpcProtocol;
import com.example.protocol.netty.NettyClient;
import com.example.registry.discovery.RegistryService;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author yanzx
 * @Date 2022/11/26 18:30
 */
@Slf4j
public class RpcInvokerProxy implements InvocationHandler {

    private final RegistryService registryService;

    public RpcInvokerProxy(RegistryService registryService) {
        this.registryService = registryService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("[Protocol] begin invoke target server.");
        RpcProtocol<Request> reqProtocol = new RpcProtocol<>();
        long requestId = RequestHolder.REQUEST_ID.incrementAndGet();
        RpcProtocol.Header header = new RpcProtocol.Header(
                ProtocolConstants.MAGIC,
                SerializationTypeEnum.JSON.type(),
                RequestTypeEnum.REQUEST.code(), requestId, 0);

        reqProtocol.setHeader(header);
        Request request = new Request();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParams(args);
        reqProtocol.setBody(request);

        // 创建NettyClient
        NettyClient nettyClient = new NettyClient();
        RequestHolder.RpcRequestFuture<Response> future =
                new RequestHolder.RpcRequestFuture<>(new DefaultPromise<>(new DefaultEventLoop()));

        RequestHolder.REQUEST_FUTURE_MAP.put(requestId, future);
        // client发送拉去服务请求到注册中心
        nettyClient.sendRequest(reqProtocol, registryService);

        return future.getPromise().get().getData();
    }
}
