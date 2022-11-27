package com.example.base.bytebuf;

import com.example.base.ByteBufPrinter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @Author yanzx
 * @Date 2022/11/21 20:26
 */
public class ByteBufCopyExample {

    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        buf.writeBytes(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        ByteBufPrinter.print(buf);

        // 从buf这个总的数据中，分别拆分5个字节保存到两个ByteBuf中
        // 零拷贝机制（浅克隆）
        ByteBuf buf1 = buf.slice(0, 5);
        ByteBuf buf2 = buf.slice(5, 5);
        ByteBufPrinter.print(buf1);
        ByteBufPrinter.print(buf2);

        System.out.println("修改原始数据After");
        buf.setByte(1, 20);
        // 可以发现buf1的结果发生了变化。说明两个分片和原始buf指向的数据是同一个
        ByteBufPrinter.print(buf1);
    }
}
