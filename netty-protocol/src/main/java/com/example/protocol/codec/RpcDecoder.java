package com.example.protocol.codec;

import com.example.protocol.constants.ProtocolConstants;
import com.example.protocol.constants.RequestTypeEnum;
import com.example.protocol.constants.SerializationTypeEnum;
import com.example.protocol.core.Response;
import com.example.protocol.core.RpcProtocol;
import com.example.protocol.core.Request;
import com.example.protocol.exception.ProtocolSerializerException;
import com.example.protocol.serializer.DefaultSerializerFactory;
import com.example.protocol.serializer.ISerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author yanzx
 * @Date 2022/11/26 15:42
 */
@Slf4j
public class RpcDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        log.info("[Protocol] Decode begin..");

        // header部分字节数量不符合协议约定
        if (byteBuf.readableBytes() < ProtocolConstants.HEAD_TOTAL_LEN) {
            log.warn("[Protocol] Decode readableBytes < head total len.");
            return;
        }

        // 标记读取开始索引
        byteBuf.markReaderIndex();
        // 读取2个字节的magic
        short magic = byteBuf.readShort();
        if (ProtocolConstants.MAGIC != magic) {
            throw new ProtocolSerializerException("[Protocol] Illegal magic. value: " + magic);
        }
        // 读取一个字节的序列化类型
        byte serializationType = byteBuf.readByte();
        // 读取一个字节的消息类型
        byte requestType = byteBuf.readByte();
        // 读取八个字节请求id
        long requestId = byteBuf.readLong();
        // 读取四个字节报文长度
        int contentLength = byteBuf.readInt();

        // 可读字节数小于报文长度resetReaderIndex
        if (byteBuf.readableBytes() < contentLength) {
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] contentBytes = new byte[contentLength];
        // 读取byteBuf中剩余body部分字节
        byteBuf.readBytes(contentBytes);

        RpcProtocol.Header header = new RpcProtocol.Header();
        header.setMagic(magic);
        header.setSerializationType(serializationType);
        header.setRequestType(requestType);
        header.setRequestId(requestId);
        header.setContentLength(contentLength);

        RequestTypeEnum requestTypeEnum = RequestTypeEnum.findByType(requestType);
        SerializationTypeEnum serializationTypeEnum = SerializationTypeEnum.findByType(serializationType);
        ISerializer serializer = DefaultSerializerFactory.newSerializer(serializationTypeEnum);

        switch (requestTypeEnum) {
            case REQUEST:
                Request request = serializer.deserialize(contentBytes, Request.class);
                RpcProtocol<Request> requestRpcProtocol = new RpcProtocol<>();
                requestRpcProtocol.setHeader(header);
                requestRpcProtocol.setBody(request);
                list.add(requestRpcProtocol);
                break;
            case RESPONSE:
                Response response = serializer.deserialize(contentBytes, Response.class);
                RpcProtocol<Response> responseRpcProtocol = new RpcProtocol<>();
                responseRpcProtocol.setHeader(header);
                responseRpcProtocol.setBody(response);
                list.add(responseRpcProtocol);
                break;
            case HEARTBEAT:
                // todo HEARTBEAT
                break;

            default:
                throw new IllegalArgumentException("requestType error type: " + requestType);
        }


    }

}
