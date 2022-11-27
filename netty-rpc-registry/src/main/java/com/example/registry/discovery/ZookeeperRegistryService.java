package com.example.registry.discovery;

import com.example.registry.ServiceMetaData;
import com.example.registry.discovery.loadbalance.LoadBalance;
import com.example.registry.discovery.loadbalance.RandomLoadBalance;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;

import java.util.Collection;
import java.util.List;

/**
 * @Author yanzx
 * @Date 2022/11/26 17:28
 */
@Slf4j
public class ZookeeperRegistryService implements RegistryService {

    /**
     * zk path
     */
    private static final String REGISTRY_PATH = "/registry";
    /**
     * curator中提供的服务注册与发现的封装
     */
    private final ServiceDiscovery<ServiceMetaData> serviceDiscovery;

    /**
     * 使用curator封装好的做负载
     */
    private final LoadBalance<ServiceInstance<ServiceMetaData>> loadBalance;

    public ZookeeperRegistryService(String registryAddress) throws Exception {
        CuratorFramework client = CuratorFrameworkFactory
                .newClient(registryAddress, new ExponentialBackoffRetry(1000, 3));
        client.start();

        // 使用curator封装的json序列化器
        JsonInstanceSerializer<ServiceMetaData> serializer =
                new JsonInstanceSerializer<>(ServiceMetaData.class);

        this.serviceDiscovery = ServiceDiscoveryBuilder.builder(ServiceMetaData.class)
                .client(client)
                .serializer(serializer)
                .basePath(REGISTRY_PATH)
                .build();
        this.serviceDiscovery.start();
        this.loadBalance = new RandomLoadBalance();
    }

    @Override
    public void register(ServiceMetaData metaData) throws Exception {
        log.info("[Registry] begin registry server metadata to Zookeeper server. metadata: {}",
                metaData.toString());

        ServiceInstance<ServiceMetaData> serviceInstance = ServiceInstance.<ServiceMetaData>builder()
                .name(metaData.getServiceName())
                .address(metaData.getServiceAddress())
                .port(metaData.getServicePort())
                .payload(metaData)
                .build();

        this.serviceDiscovery.registerService(serviceInstance);
    }

    @Override
    public ServiceMetaData discovery(String serviceName) throws Exception {
        log.info("[Registry] begin discovery server from Zookeeper server. serviceName: {}", serviceName);
        // todo 可以优化远程服务缓存到本地，每次都去远程服务中心去拉去服务list
        Collection<ServiceInstance<ServiceMetaData>> serviceInstances =
                this.serviceDiscovery.queryForInstances(serviceName);

        // 动态路由获取服务实例信息
        ServiceInstance<ServiceMetaData> serviceInstance =
                this.loadBalance.select((List<ServiceInstance<ServiceMetaData>>) serviceInstances);

        if (serviceInstance != null) {
            return serviceInstance.getPayload();
        }

        throw new IllegalArgumentException("[Registry] discovery error. serviceName: " + serviceName);
    }
}
