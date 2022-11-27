package com.example.protocol.spring.reference;

import com.example.protocol.annotation.RemoteReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author yanzx
 * @Date 2022/11/26 18:10
 */
@Slf4j
public class SpringRpcReferencePostProcessor
        implements ApplicationContextAware, BeanClassLoaderAware, BeanFactoryPostProcessor {

    /**
     * 保存发布的引用bean的信息
     */
    private final Map<String, BeanDefinition> RPC_REF_BEAN_DEFINITION = new ConcurrentHashMap<>();

    private ApplicationContext applicationContext;
    private ClassLoader classLoader;
    private final RpcClientProperties rpcClientProperties;

    public SpringRpcReferencePostProcessor(RpcClientProperties rpcClientProperties) {
        this.rpcClientProperties = rpcClientProperties;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * spring容器加载了bean的定义文件之后， 在bean实例化之前执行
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            if (beanClassName != null) {
                Class<?> clazz = ClassUtils.resolveClassName(beanClassName, this.classLoader);
                ReflectionUtils.doWithFields(clazz, this::parseRpcReference);
            }
        }
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        this.RPC_REF_BEAN_DEFINITION.forEach((beanName, beanDefinition) -> {
            if (applicationContext.containsBean(beanName)) {
                log.warn("[Protocol] springContext already register bean {}", beanName);
                return;
            }
            registry.registerBeanDefinition(beanName, beanDefinition);
            log.info("[Protocol] registered RpcReferenceBean {} success", beanName);
        });
    }

    private void parseRpcReference(Field field) {
        RemoteReference gpRemoteReference = AnnotationUtils.getAnnotation(field, RemoteReference.class);
        if (gpRemoteReference != null) {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.
                    genericBeanDefinition(SpringRpcReferenceBean.class);
            builder.setInitMethodName("init");
            builder.addPropertyValue("interfaceClass", field.getType());
            builder.addPropertyValue("registryAddress", rpcClientProperties.getRegistryAddress());
            builder.addPropertyValue("registryType", rpcClientProperties.getRegistryType());

            BeanDefinition beanDefinition = builder.getBeanDefinition();
            RPC_REF_BEAN_DEFINITION.put(field.getName(), beanDefinition);
        }
    }

}
