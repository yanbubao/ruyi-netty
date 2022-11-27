package com.example.base.bytebuf;

import io.netty.buffer.*;


/**
 * @Author yanzx
 * @Date 2022/11/21 23:42
 */
public class ByteBufReleaseExample {

    public static void main(String[] args) {
        // UnpooledHeapByteBuf，使用JVM内存，只需要等待GC回收即可
        UnpooledHeapByteBuf unpooledHeapByteBuf =
                new UnpooledHeapByteBuf(ByteBufAllocator.DEFAULT, 1024, 1024 * 1024);

        // UnpooledDirectByteBuf，使用对外内存，需要特殊方法来回收内存
        UnpooledDirectByteBuf unpooledDirectByteBuf =
                new UnpooledDirectByteBuf(ByteBufAllocator.DEFAULT, 1024, 1024 * 1024);

        // PooledByteBuf和它的之类使用了池化机制，需要更复杂的规则来回收内存
        ByteBuf buffer = PooledByteBufAllocator.DEFAULT.buffer();

        /**
         * Netty采用了引用计数方法来控制内存回收，每个ByteBuf都实现了ReferenceCounted接口。
         * 每个ByteBuf对象的初始计数为1
         * 调用release方法时，计数器减一，如果计数器为0，ByteBuf被回收
         * 调用retain方法时，计数器加一，表示调用者没用完之前，其他handler即时调用了release也不会造成回收。
         * 当计数器为0时，底层内存会被回收，这时即使ByteBuf对象还存在，但是它的各个方法都无法正常使用
         */
    }
}
