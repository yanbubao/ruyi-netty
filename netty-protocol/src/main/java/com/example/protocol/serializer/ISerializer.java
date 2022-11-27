package com.example.protocol.serializer;

/**
 * @Author yanzx
 * @Date 2022/11/25 23:20
 */
public interface ISerializer {

    <T> byte[] serialize(T t) throws IllegalAccessException;

    <T> T deserialize(byte[] bytes, Class<T> clazz);

    byte type();
}
