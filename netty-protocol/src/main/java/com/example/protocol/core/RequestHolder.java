package com.example.protocol.core;

import io.netty.util.concurrent.Promise;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author yanzx
 * @Date 2022/11/26 16:16
 */
public class RequestHolder {

    public static final AtomicLong REQUEST_ID = new AtomicLong();

    public static final Map<Long, RpcRequestFuture> REQUEST_FUTURE_MAP = new ConcurrentHashMap<>();

    @Data
    public static class RpcRequestFuture<T> {
        private Promise<T> promise;

        public RpcRequestFuture(Promise<T> promise) {
            this.promise = promise;
        }
    }
}
