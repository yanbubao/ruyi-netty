package com.example.protocol.spring.server;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author yanzx
 * @Date 2022/11/26 17:33
 */
@Data
@ConfigurationProperties(prefix = "rpc.server")
public class RpcServerProperties {

    /**
     * 服务端口
     */
    private int servicePort;

    /**
     * 注册中心的地址
     */
    private String registryAddress;

    /**
     * 注册中心的类型
     */
    private byte registryType;
}
