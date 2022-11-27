package com.example.protocol.codec;

import com.example.protocol.constants.SerializationTypeEnum;
import com.example.protocol.core.RpcProtocol;
import com.example.protocol.serializer.DefaultSerializerFactory;
import com.example.protocol.serializer.ISerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author yanzx
 * @Date 2022/11/25 23:06
 */
@Slf4j
public class RpcEncoder extends MessageToByteEncoder<RpcProtocol<Object>> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcProtocol<Object> protocol, ByteBuf byteBuf) {
        log.info("[Protocol] Encode begin..");
        try {
            RpcProtocol.Header header = protocol.getHeader();
            byteBuf.writeShort(header.getMagic());
            byteBuf.writeByte(header.getSerializationType());
            byteBuf.writeByte(header.getRequestType());
            byteBuf.writeLong(header.getRequestId());

            ISerializer serializer = DefaultSerializerFactory.obtainByType(SerializationTypeEnum.JSON);
            // encode时将body序列化
            byte[] bodyBytes = serializer.serialize(protocol.getBody());
            byteBuf.writeInt(bodyBytes.length);
            byteBuf.writeBytes(bodyBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
