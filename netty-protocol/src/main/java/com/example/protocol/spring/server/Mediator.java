package com.example.protocol.spring.server;

import com.example.protocol.core.Request;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务调度器
 *
 * @Author yanzx
 * @Date 2022/11/26 16:56
 */
@Slf4j
public class Mediator {

    public final static Map<String, BeanMethod> BEAN_METHOD_MAP = new ConcurrentHashMap<>();

    private volatile static Mediator INSTANCE = null;

    private Mediator() {
    }

    public static Mediator getInstance() {
        if (INSTANCE == null) {
            synchronized (Mediator.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Mediator();
                }
            }
        }
        return INSTANCE;
    }

    public Object process(Request request) {
        String key = request.getClassName() + "." + request.getMethodName();
        if (BEAN_METHOD_MAP.containsKey(key)) {
            BeanMethod beanMethod = BEAN_METHOD_MAP.get(key);
            Object bean = beanMethod.getBean();
            Method method = beanMethod.getMethod();

            try {
                return method.invoke(bean, request.getParams());

            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalArgumentException("[Mediator] process not found BeanMethod.");
    }
}
