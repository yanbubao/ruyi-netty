package com.example.registry.discovery;

import com.example.registry.common.RegistryTypeEnum;

/**
 * @Author yanzx
 * @Date 2022/11/26 17:38
 */
public class RegistryFactory {

    public static RegistryService createRegistryService(String address, RegistryTypeEnum registryType) {
        RegistryService registryService = null;
        try {
            switch (registryType) {
                case ZOOKEEPER:
                    registryService = new ZookeeperRegistryService(address);
                    break;
                default:
                    throw new IllegalArgumentException("[Registry] not support registry type: " + registryType.name());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return registryService;
    }
}
