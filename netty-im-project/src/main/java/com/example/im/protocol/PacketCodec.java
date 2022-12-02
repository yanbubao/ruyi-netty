package com.example.im.protocol;

import com.example.im.serializer.DefaultSerializerFactory;
import com.example.im.serializer.Serializer;
import io.netty.buffer.ByteBuf;

/**
 * @Author yanzx
 * @Date 2022/12/2 16:34
 */
public class PacketCodec {

    public static final PacketCodec INSTANCE = new PacketCodec();

    public static final int MAGIC_NUMBER = 0x12345678;

    private PacketCodec() {
    }

    /**
     * 编码
     *
     * @param packet  {@link Packet}
     * @param byteBuf write in byteBuf
     */
    public void encode(Packet packet, ByteBuf byteBuf) {
        // 序列化
        byte[] bytes = Serializer.DEFAULT_SERIALIZER.serialize(packet);

        // 按自定义协议进行编码
        // 魔数4byte
        byteBuf.writeInt(MAGIC_NUMBER);
        // 协议版本1byte
        byteBuf.writeByte(packet.getVersion());
        // 序列化算法标识1byte
        byteBuf.writeByte(Serializer.DEFAULT_SERIALIZER.serializerAlgorithm());
        // 操作命令1byte
        byteBuf.writeByte(packet.getCommand());
        // 数据长度4byte
        byteBuf.writeInt(bytes.length);
        // 数据
        byteBuf.writeBytes(bytes);
    }

    /**
     * 解码
     *
     * @param byteBuf read from byteBuf
     * @return {@link Packet}
     */
    public Packet decode(ByteBuf byteBuf) {
        // 跳过魔数
        byteBuf.skipBytes(4);
        // 跳过版本号
        byteBuf.skipBytes(1);
        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();
        // 操作指令
        byte command = byteBuf.readByte();
        // 数据包长度
        int length = byteBuf.readInt();
        // 从byteBuf读出数据到字节数组
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        // 反序列化
        Class<? extends Packet> packetClazz = DefaultPacketFactory.getPacketClazz(command);
        Serializer serializer = DefaultSerializerFactory.getSerializer(serializeAlgorithm);
        return serializer.deserialize(bytes, packetClazz);
    }

}
