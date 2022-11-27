package com.example.protocol.constants;

/**
 * @Author yanzx
 * @Date 2022/11/25 23:34
 */
public enum SerializationTypeEnum {

    JSON((byte) 1),
    JAVA((byte) 2);

    private final byte type;

    SerializationTypeEnum(byte type) {
        this.type = type;
    }

    public byte type() {
        return this.type;
    }

    public static SerializationTypeEnum findByType(byte serializationType) {
        for (SerializationTypeEnum serializationTypeEnum : SerializationTypeEnum.values()) {
            if (serializationTypeEnum.type == serializationType) {
                return serializationTypeEnum;
            }
        }
        throw new IllegalArgumentException("serializationType error type: " + serializationType);
    }

}
