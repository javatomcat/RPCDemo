package com.carry.server;

import com.carry.common.utils.ReflectionUtils;
import com.carry.protocol.Request;

public class ServiceInvoker {

    public Object invoke(ServiceInstance serviceInstance, Request request) {

        return ReflectionUtils.invoke(serviceInstance.getTarget(),
                serviceInstance.getMethod(),
                request.getParameters()
        );

    }
}
