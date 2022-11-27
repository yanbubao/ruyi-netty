package com.example.protocol.exception;

/**
 * @Author yanzx
 * @Date 2022/11/25 23:31
 */
public class ProtocolSerializerException extends RuntimeException {

    public ProtocolSerializerException(String errorMessage) {
        super(errorMessage);
    }
}
