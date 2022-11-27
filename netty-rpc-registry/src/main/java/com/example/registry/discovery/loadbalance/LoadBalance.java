package com.example.registry.discovery.loadbalance;

import java.util.List;

/**
 * @Author yanzx
 * @Date 2022/11/26 17:44
 */
public interface LoadBalance<T> {
    T select(List<T> servers);
}
