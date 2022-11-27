package com.example.registry.common;

/**
 * @Author yanzx
 * @Date 2022/11/26 17:39
 */
public enum RegistryTypeEnum {

    ZOOKEEPER((byte) 0),
    EUREKA((byte) 1);
    private final byte code;

    RegistryTypeEnum(byte code) {
        this.code = code;
    }

    public static RegistryTypeEnum findByCode(byte code) {
        for (RegistryTypeEnum reqType : RegistryTypeEnum.values()) {
            if (reqType.code == code) {
                return reqType;
            }
        }
        throw new IllegalArgumentException("[Registry] RegistryType error value: " + code);
    }
}
