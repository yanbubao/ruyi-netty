package com.example.protocol.spring.server;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @Author yanzx
 * @Date 2022/11/26 16:57
 */
@Data
public class BeanMethod {
    private Object bean;
    private Method method;
}
