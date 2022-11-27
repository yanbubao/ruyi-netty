package com.example.base;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

/**
 * @Author yanzx
 * @Date 2022/11/21 19:57
 */
public class ByteBufPrinter {

    public static void print(ByteBuf byteBuf) {
        StringBuilder sb = new StringBuilder();
        sb.append(" read index:").append(byteBuf.readerIndex());  //读索引
        sb.append(" write index:").append(byteBuf.writerIndex()); //写索引
        sb.append(" capacity :").append(byteBuf.capacity()); // 容量
        ByteBufUtil.appendPrettyHexDump(sb, byteBuf);
        System.out.println(sb);
    }
}
