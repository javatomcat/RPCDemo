package com.carry.server;


import com.carry.common.utils.ReflectionUtils;
import com.carry.protocol.Request;
import com.carry.protocol.ServiceDescriptor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * RPC管理类
 * <p>
 * 注册  查找
 * <p>
 * 主要有两个方法：
 * 1.register
 * 2.lookup
 */
@Slf4j
public class ServiceManager {
    private Map<ServiceDescriptor, ServiceInstance> services;

    public ServiceManager() {
        this.services = new ConcurrentHashMap<ServiceDescriptor, ServiceInstance>();
    }

    public <T> void register(Class<T> interfaceClass, T bean) {
        Method[] publicMethods = ReflectionUtils.getPublicMethods(interfaceClass);
        for (Method method : publicMethods) {
            ServiceInstance sis = new ServiceInstance(bean, method);
            ServiceDescriptor sdp = ServiceDescriptor.from(interfaceClass, method);
            services.put(sdp, sis);
            log.info("register start {} {}", sdp.getClazz(), sdp.getMethod());
        }
    }

    public ServiceInstance lookup(Request request) {
        ServiceDescriptor sdp = request.getService();
        return services.get(sdp);
    }
}
