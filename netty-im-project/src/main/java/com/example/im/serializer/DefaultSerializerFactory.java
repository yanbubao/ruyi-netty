package com.example.im.serializer;

import com.example.im.serializer.impl.JSONSerializer;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author yanzx
 * @Date 2022/12/2 22:20
 */
public class DefaultSerializerFactory {
    private final static ConcurrentHashMap<Byte, Serializer> SUPPORT_SERIALIZER_MAP = new ConcurrentHashMap<>();

    static {
        SUPPORT_SERIALIZER_MAP.put(SerializerAlgorithm.JSON, new JSONSerializer());
    }

    public static Serializer getSerializer(Byte algorithm) {
        if (SUPPORT_SERIALIZER_MAP.containsKey(algorithm)) {
            return SUPPORT_SERIALIZER_MAP.get(algorithm);
        }
        throw new IllegalArgumentException("[Protocol] not support serialize algorithm code: " + algorithm);
    }
}
