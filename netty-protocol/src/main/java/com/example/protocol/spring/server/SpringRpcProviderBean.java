package com.example.protocol.spring.server;

import com.example.protocol.annotation.RemoteService;
import com.example.protocol.netty.NettyServer;
import com.example.registry.ServiceMetaData;
import com.example.registry.discovery.RegistryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author yanzx
 * @Date 2022/11/26 17:10
 */
@Slf4j
public class SpringRpcProviderBean implements InitializingBean, BeanPostProcessor {

    private final int serverPort;
    private final String serverAddress;
    /**
     * 注册中心服务
     */
    private final RegistryService registryService;

    public SpringRpcProviderBean(int serverPort, RegistryService registryService) throws UnknownHostException {
        this.serverPort = serverPort;
        this.serverAddress = InetAddress.getLocalHost().getHostAddress();
        this.registryService = registryService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("[Protocol] begin deploy netty server to host: {}, on port: {}",
                this.serverAddress, this.serverPort);

        new Thread(() -> new NettyServer(this.serverAddress, this.serverPort).start()).start();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 将标记了@RemoteService注解的SpringBean发布到服务注册中心
        if (bean.getClass().isAnnotationPresent(RemoteService.class)) {
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                String serviceName = bean.getClass().getInterfaces()[0].getName();
                String key = serviceName + "." + method.getName();
                BeanMethod beanMethod = new BeanMethod();
                beanMethod.setBean(bean);
                beanMethod.setMethod(method);
                Mediator.BEAN_METHOD_MAP.put(key, beanMethod);

                ServiceMetaData metaData = new ServiceMetaData();
                metaData.setServiceAddress(this.serverAddress);
                metaData.setServicePort(this.serverPort);
                metaData.setServiceName(serviceName);
                try {
                    // 发布服务
                    registryService.register(metaData);

                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("[Protocol] register service: {} failed", serviceName, e);
                }
            }
        }
        return bean;
    }

}
