package com.example.registry;

import lombok.Data;
import lombok.ToString;

/**
 * 注册中心服务元数据
 *
 * @Author yanzx
 * @Date 2022/11/26 17:19
 */
@Data
@ToString
public class ServiceMetaData {
    private String serviceName;
    private String serviceAddress;
    private int servicePort;
}
