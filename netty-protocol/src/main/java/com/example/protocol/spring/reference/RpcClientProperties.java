package com.example.protocol.spring.reference;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author yanzx
 * @Date 2022/11/26 18:02
 */
@Data
@ConfigurationProperties(prefix = "rpc.client")
public class RpcClientProperties {
    private String serviceAddress;
    private int servicePort;
    private byte registryType;
    private String registryAddress;
}
