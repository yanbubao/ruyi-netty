package com.example.registry.discovery.loadbalance;

import com.example.registry.ServiceMetaData;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;
import java.util.Random;

/**
 * @Author yanzx
 * @Date 2022/11/26 17:45
 */
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected ServiceInstance<ServiceMetaData> doSelect(List<ServiceInstance<ServiceMetaData>> servers) {
        int len = servers.size();
        Random random = new Random();
        return servers.get(random.nextInt(len));
    }
}
