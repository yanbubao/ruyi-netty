package com.example.im.protocol;

import lombok.Data;

/**
 * 通信对象
 *
 * @Author yanzx
 * @Date 2022/12/1 22:23
 */
@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    public Byte version = 1;

    /**
     * 操作指令
     *
     * @return option command
     */
    public abstract Byte getCommand();

}
