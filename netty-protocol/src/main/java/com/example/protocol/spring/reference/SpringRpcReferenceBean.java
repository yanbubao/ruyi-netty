package com.example.protocol.spring.reference;

import com.example.registry.common.RegistryTypeEnum;
import com.example.registry.discovery.RegistryFactory;
import com.example.registry.discovery.RegistryService;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * @Author yanzx
 * @Date 2022/11/26 18:19
 */
public class SpringRpcReferenceBean implements FactoryBean<Object> {

    private Object object;
    private Class<?> interfaceClass;
    private String registryAddress;
    private byte registryType;

    public void init() {
        RegistryService registryService = RegistryFactory.createRegistryService(
                this.registryAddress,
                RegistryTypeEnum.findByCode(this.registryType));

        this.object = Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class<?>[]{interfaceClass},
                new RpcInvokerProxy(registryService));
    }

    @Override
    public Object getObject() throws Exception {
        return this.object;
    }

    @Override
    public Class<?> getObjectType() {
        return this.interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public void setRegistryType(byte registryType) {
        this.registryType = registryType;
    }
}
