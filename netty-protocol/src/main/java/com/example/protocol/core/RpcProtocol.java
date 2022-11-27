package com.example.protocol.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 定义Rpc通讯消息协议
 *
 * @Author yanzx
 * @Date 2022/11/25 23:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcProtocol<T> implements Serializable {
    private Header header;
    private T body;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Header implements Serializable {
        /**
         * 魔数 2byte
         */
        private short magic;
        /**
         * 序列化类型 1byte
         */
        private byte serializationType;
        /**
         * 消息类型 1byte
         */
        private byte requestType;
        /**
         * 请求id 8byte
         */
        private long requestId;
        /**
         * 消息体长度 4byte
         */
        private int contentLength;
    }
}


