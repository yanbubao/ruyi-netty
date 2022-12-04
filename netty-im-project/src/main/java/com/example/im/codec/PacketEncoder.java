package com.example.im.codec;

import com.example.im.protocol.Packet;
import com.example.im.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * {@link PacketCodecHandler}
 *
 * @Author yanzx
 * @Date 2022/12/4 15:08
 */
@Deprecated
public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) {
        PacketCodec.INSTANCE.encode(packet, out);
    }
}
