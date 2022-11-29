package com.example.protocol.serializer;

import com.example.protocol.constants.SerializationTypeEnum;
import com.example.protocol.exception.ProtocolSerializerException;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author yanzx
 * @Date 2022/11/25 23:18
 */
public class DefaultSerializerFactory implements SerializerFactory {

    private final static ConcurrentHashMap<SerializationTypeEnum, ISerializer> SUPPORT_SERIALIZER_MAP = new ConcurrentHashMap<>();

    static {
        SUPPORT_SERIALIZER_MAP.put(SerializationTypeEnum.JSON, new JsonSerializer());
        SUPPORT_SERIALIZER_MAP.put(SerializationTypeEnum.JAVA, new JavaSerializer());
    }

    public static ISerializer newSerializer(SerializationTypeEnum serializationType) {
        if (SUPPORT_SERIALIZER_MAP.containsKey(serializationType)) {
            return SUPPORT_SERIALIZER_MAP.get(serializationType);
        }
        throw new ProtocolSerializerException("DefaultSerializerFactory not found Serializer. serializationType: " + serializationType);
    }
}
