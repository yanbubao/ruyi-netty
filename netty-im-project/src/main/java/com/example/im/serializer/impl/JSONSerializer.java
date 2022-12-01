package com.example.im.serializer.impl;

import com.alibaba.fastjson.JSON;
import com.example.im.serializer.Serializer;
import com.example.im.serializer.SerializerAlgorithm;

/**
 * @Author yanzx
 * @Date 2022/12/1 22:36
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte serializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
