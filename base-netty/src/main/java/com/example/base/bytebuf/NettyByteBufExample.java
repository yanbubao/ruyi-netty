package com.example.base.bytebuf;

import com.example.base.ByteBufPrinter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @Author yanzx
 * @Date 2022/11/22 14:22
 */
public class NettyByteBufExample {

    public static void main(String[] args) {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        System.out.println("======= before ======");
        ByteBufPrinter.print(buffer);

        //构建一个字符串
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 400; i++) {
            stringBuilder.append("-").append(i);
        }
        buffer.writeBytes(stringBuilder.toString().getBytes());
        System.out.println("======= add data ======");
        ByteBufPrinter.print(buffer);

        System.out.println("======= after ======");
        buffer.readShort(); // 读取2个字节
        buffer.readByte(); // 读取一个字节
        ByteBufPrinter.print(buffer);
    }
}
