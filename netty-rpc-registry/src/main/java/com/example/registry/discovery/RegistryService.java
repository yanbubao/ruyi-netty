package com.example.registry.discovery;

import com.example.registry.ServiceMetaData;

/**
 * @Author yanzx
 * @Date 2022/11/26 17:26
 */
public interface RegistryService {
    void register(ServiceMetaData metaData) throws Exception;

    ServiceMetaData discovery(String serviceName) throws Exception;
}
