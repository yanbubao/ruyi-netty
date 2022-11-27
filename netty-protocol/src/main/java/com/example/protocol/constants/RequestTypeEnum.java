package com.example.protocol.constants;

/**
 * @Author yanzx
 * @Date 2022/11/26 16:05
 */
public enum RequestTypeEnum {

    REQUEST((byte) 1),
    RESPONSE((byte) 2),
    HEARTBEAT((byte) 3);

    private final byte type;

    RequestTypeEnum(byte type) {
        this.type = type;
    }

    public byte code() {
        return this.type;
    }

    public static RequestTypeEnum findByType(byte type) {
        for (RequestTypeEnum requestTypeEnum : RequestTypeEnum.values()) {
            if (requestTypeEnum.type == type) {
                return requestTypeEnum;
            }
        }
        throw new IllegalArgumentException("requestType error type: " + type);
    }
}
