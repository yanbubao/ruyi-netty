package com.example.protocol.spring.reference;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yanzx
 * @Date 2022/11/26 18:07
 */
@Configuration
@EnableConfigurationProperties(RpcClientProperties.class)
public class RpcReferenceAutoConfiguration {


    @Bean
    public SpringRpcReferencePostProcessor postProcessor(RpcClientProperties rpcClientProperties) {
        RpcClientProperties rpcClientProperties1 = new RpcClientProperties();
        rpcClientProperties1.setServiceAddress("192.168.31.19");
        rpcClientProperties1.setServicePort(20880);
        rpcClientProperties1.setRegistryType((byte)0);
        rpcClientProperties1.setRegistryAddress("192.168.31.19:2181");

        return new SpringRpcReferencePostProcessor(rpcClientProperties1);
    }
}
