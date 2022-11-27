package com.example.protocol.core;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author yanzx
 * @Date 2022/11/26 16:14
 */
@Data
public class Request implements Serializable {

    /**
     * 类名
     */
    private String className;
    /**
     * 请求目标方法
     */
    private String methodName;
    /**
     * 请求参数
     */
    private Object[] params;
    /**
     * 参数类型
     */
    private Class<?>[] parameterTypes;
}
