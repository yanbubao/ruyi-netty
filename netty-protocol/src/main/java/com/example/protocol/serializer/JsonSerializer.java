package com.example.protocol.serializer;


import com.alibaba.fastjson.JSON;
import com.example.protocol.constants.SerializationTypeEnum;

/**
 * @Author yanzx
 * @Date 2022/11/25 23:36
 */
public class JsonSerializer implements ISerializer {
    @Override
    public <T> byte[] serialize(T t) throws IllegalAccessException {
        return JSON.toJSONString(t).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(new String(bytes), clazz);
    }

    @Override
    public byte type() {
        return SerializationTypeEnum.JSON.type();
    }
}
