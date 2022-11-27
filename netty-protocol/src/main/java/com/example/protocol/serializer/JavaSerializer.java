package com.example.protocol.serializer;

import com.example.protocol.constants.SerializationTypeEnum;
import com.example.protocol.exception.ProtocolSerializerException;

import java.io.*;

/**
 * @Author yanzx
 * @Date 2022/11/25 23:21
 */
@SuppressWarnings("unchecked")
public class JavaSerializer implements ISerializer {

    @Override
    public <T> byte[] serialize(T t) throws IllegalAccessException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream;

        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(t);
            return byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ProtocolSerializerException("JavaSerializer error.");
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (T) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        throw new ProtocolSerializerException("JavaSerializer error.");
    }

    @Override
    public byte type() {
        return SerializationTypeEnum.JAVA.type();
    }
}
