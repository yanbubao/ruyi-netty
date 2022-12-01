package com.example.im.serializer;

import com.example.im.serializer.impl.JSONSerializer;

/**
 * @Author yanzx
 * @Date 2022/12/1 22:35
 */
public interface Serializer {

    Serializer DEFAULT_SERIALIZER = new JSONSerializer();

    /**
     * 序列化算法标识
     *
     * @return {@link SerializerAlgorithm}
     */
    byte serializerAlgorithm();

    byte[] serialize(Object object);

    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
