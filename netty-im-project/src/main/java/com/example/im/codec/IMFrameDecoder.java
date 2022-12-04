package com.example.im.codec;


import com.example.im.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Author yanzx
 * @Date 2022/12/4 15:38
 */
public class IMFrameDecoder extends LengthFieldBasedFrameDecoder {
    /**
     * magic 4 byte
     * version 1 byte
     * serializer algorithm 1 byte
     * command 1 byte
     * before length total 7 byte
     */
    private static final int LENGTH_FIELD_OFFSET = 7;
    /**
     * msg length 4 byte
     */
    private static final int LENGTH_FIELD_LENGTH = 4;

    public IMFrameDecoder() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    /**
     * 疑问点解决说明：decode方法是在数据满足完整Frame格式才会被调用，待验证
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // notice 此处校验魔数疑问 待验证
        //  此处在拆包器逻辑中校验魔数Magic不太合理，如果出现半包且不够Magic数据时，不应该直接关闭连接
        //  不应该放在业务拆包之前，个人觉得应该是放在拆包之后解码器之前

        // getXX方法类似byte数组操作，不影响readIndex
        if (in.getInt(in.readerIndex()) != PacketCodec.MAGIC_NUMBER) {
            System.out.println("[IMFrameDecoder] valid magic error.");
            ctx.channel().close();
            return null;
        }

        return super.decode(ctx, in);
    }
}
