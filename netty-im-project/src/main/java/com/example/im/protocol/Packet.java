package com.example.im.protocol;

import com.alibaba.fastjson.annotation.JSONField;
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
    @JSONField(deserialize = false, serialize = false)
    public Byte version = 1;

    /**
     * 操作指令
     *
     * @return option command
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();

}
