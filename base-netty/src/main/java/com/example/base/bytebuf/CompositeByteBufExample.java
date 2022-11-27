package com.example.base.bytebuf;

import com.example.base.ByteBufPrinter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Author yanzx
 * @Date 2022/11/21 20:36
 */
public class CompositeByteBufExample {

    public static void main(String[] args) {
        ByteBuf header = ByteBufAllocator.DEFAULT.buffer();
        header.writeBytes(new byte[]{1, 2, 3, 4, 5});
        ByteBuf body = ByteBufAllocator.DEFAULT.buffer();
        body.writeBytes(new byte[]{6, 7, 8, 9, 10});

        System.out.println("直接构建新的ByteBuf：");
        //simpleComposite(header, body);

        /**
         * 之所以CompositeByteBuf能够实现零拷贝，是因为在组合header和body时，并没有对这两个数据进
         * 行复制，而是通过CompositeByteBuf构建了一个逻辑整体，里面仍然是两个真实对象，也就是有一个
         * 指针指向了同一个对象，所以这里类似于浅拷贝的实现
         */
        System.out.println("使用CompositeByteBuf合并：");
        compositeByteBuf(header, body);

        // 在Unpooled工具类中，提供了一个wrappedBuffer方法，来实现CompositeByteBuf零拷贝功能
        System.out.println("使用Unpooled工具类wrappedBuffer合并：");
        ByteBufPrinter.print(Unpooled.wrappedBuffer(header, body));

        System.out.println("使用Unpooled工具类copiedBuffer合并：");
        ByteBuf copiedBuffer = Unpooled.copiedBuffer(header, body);
        ByteBufPrinter.print(copiedBuffer);

        System.out.println("修改原始数据：");
        header.setByte(1, 20);
        // copiedBuffer该方法会实现数据复制，所以数据不会改变
        ByteBufPrinter.print(copiedBuffer);

    }

    private static void compositeByteBuf(ByteBuf header, ByteBuf body) {
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
        // 其中第一个参数是true，表示当添加新的ByteBuf时，自动递增CompositeByteBuf的writeIndex.
        // 默认是false，也就是writeIndex=0，这样的话我们不可能从compositeByteBuf中读取到数据
        compositeByteBuf.addComponents(true, header, body);
        ByteBufPrinter.print(compositeByteBuf);
    }

    private static void simpleComposite(ByteBuf header, ByteBuf body) {
        ByteBuf total = Unpooled.buffer(header.readableBytes() + body.readableBytes());
        total.writeBytes(header);
        total.writeBytes(body);
        ByteBufPrinter.print(total);
    }
}
