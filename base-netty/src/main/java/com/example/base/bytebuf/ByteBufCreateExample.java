package com.example.base.bytebuf;

import com.example.base.ByteBufPrinter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @Author yanzx
 * @Date 2022/11/21 19:56
 */
public class ByteBufCreateExample {

    public static void main(String[] args) {
        createByteBuf();
    }

    /**
     * ByteBuf默认采用了池化技术来创建，核心思想是实现对象的复用，从而减少对象频繁创建销毁带来的性能开销
     * 池化功能是否开启，可以通过下面的环境变量来控制：
     * -Dio.netty.allocator.type={unpooled|pooled}
     * <p>
     * <p>
     * ByteBuf扩容：如果小于4MB，2倍扩容，大于4MB，每次扩容+4MB
     */
    public static void createByteBuf() {
        // 创建于堆内存，由JVM来管理
        ByteBuf buffer = ByteBufAllocator.DEFAULT.heapBuffer(); // 支持自动扩容

        // 创建基于直接内存（堆外内存）的ByteBuf
        // 缺少了JVM的内存管理，所以需要我们自己来维护堆外内存，防止内存溢出
        //ByteBuf buffer = ByteBufAllocator.DEFAULT.directBuffer(10);

        buffer.writeBytes(new byte[]{1, 2, 3, 4, 5});
        ByteBufPrinter.print(buffer); // 01 02 03 04 05

        // 写入一个int
        buffer.writeInt(6);
        ByteBufPrinter.print(buffer); // 01 02 03 04 05 00 00 00 06

        // 标记读索引位置
        buffer.markReaderIndex();

        System.out.println("开始进行读取操作：");
        byte b = buffer.readByte();
        System.out.println("读取ByteBuf数据：" + b);
        ByteBufPrinter.print(buffer);

        // 回到mark索引位置，还能读到b原值
        buffer.resetReaderIndex();
        System.out.println("resetReaderIndex：");
        ByteBufPrinter.print(buffer);
    }
}
