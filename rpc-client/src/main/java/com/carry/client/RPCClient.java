package com.carry.client;

import com.carry.codec.Decoder;
import com.carry.codec.Encoder;
import com.carry.common.utils.ReflectionUtils;
import com.carry.transport.TransportClient;
import com.carry.transport.TransportServer;

import java.lang.reflect.Proxy;

public class RPCClient {
    private RPCClientConfig clientConfig;
    private TransportSelector transportSelector;
    private Encoder encoder;
    private Decoder decoder;

    public RPCClient() {
        this(new RPCClientConfig());
    }

    public RPCClient(RPCClientConfig config) {
        this.clientConfig = config;
        this.encoder = ReflectionUtils.newInstance(clientConfig.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(clientConfig.getDecoderClass());
        this.transportSelector = ReflectionUtils.newInstance(clientConfig.getSelectorClass());
        transportSelector.init(clientConfig.getServers(),
                clientConfig.getConnectCount(),
                clientConfig.getTransportClientClass());
    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{clazz},
                new RemoteInvoker(clazz, encoder, decoder, transportSelector)
        );
    }
}
