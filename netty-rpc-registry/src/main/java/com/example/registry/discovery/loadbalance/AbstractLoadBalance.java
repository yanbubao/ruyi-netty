package com.example.registry.discovery.loadbalance;

import com.example.registry.ServiceMetaData;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;

/**
 * @Author yanzx
 * @Date 2022/11/26 17:44
 */
public abstract class AbstractLoadBalance implements LoadBalance<ServiceInstance<ServiceMetaData>> {
    @Override
    public ServiceInstance<ServiceMetaData> select(List<ServiceInstance<ServiceMetaData>> servers) {
        if (servers == null || servers.size() == 0) {
            return null;
        }
        if (servers.size() == 1) {
            return servers.get(0);
        }
        return doSelect(servers);
    }

    protected abstract ServiceInstance<ServiceMetaData> doSelect(List<ServiceInstance<ServiceMetaData>> servers);
}
