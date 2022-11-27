package com.example.protocol.spring.server;

import com.example.registry.common.RegistryTypeEnum;
import com.example.registry.discovery.RegistryFactory;
import com.example.registry.discovery.RegistryService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

/**
 * @Author yanzx
 * @Date 2022/11/26 17:32
 */
@Configuration
@EnableConfigurationProperties(RpcServerProperties.class)
public class RpcProviderAutoConfiguration {

    @Bean
    public SpringRpcProviderBean springRpcProviderBean(RpcServerProperties rpcServerProperties) throws UnknownHostException {

        RegistryService registryService = RegistryFactory.createRegistryService(
                rpcServerProperties.getRegistryAddress(),
                RegistryTypeEnum.findByCode(rpcServerProperties.getRegistryType()));

        return new SpringRpcProviderBean(rpcServerProperties.getServicePort(), registryService);
    }
}
