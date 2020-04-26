package com.carry.server;

import com.carry.codec.Decoder;
import com.carry.codec.Encoder;
import com.carry.common.utils.ReflectionUtils;
import com.carry.protocol.Request;
import com.carry.protocol.Response;
import com.carry.transport.RequestHandler;
import com.carry.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class RPCServer {

    private RPCServerConfig rpcServerConfig;
    private TransportServer transport;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;

    public RPCServer() {
        this.rpcServerConfig = new RPCServerConfig();
        this.transport = ReflectionUtils.newInstance(rpcServerConfig.getTransportClass());
        this.transport.init(rpcServerConfig.getPort(), this.handler);
        this.encoder = ReflectionUtils.newInstance(rpcServerConfig.getEncoder());
        this.decoder = ReflectionUtils.newInstance(rpcServerConfig.getDecoder());
        this.serviceManager = new ServiceManager();
        this.serviceInvoker = new ServiceInvoker();
    }

    public <T> void register(Class<T> interfaceClass, T bean) {
        serviceManager.register(interfaceClass, bean);
    }

    public void start() {
        this.transport.start();
    }

    public void stop() {
        this.transport.stop();
    }

    /**
     * 1.读取数据
     * 2.
     */
    private RequestHandler handler = new RequestHandler() {
        @Override
        public void doRequest(InputStream receive, OutputStream response) {
            Response resp = new Response();

            try {
                byte[] inBytes = IOUtils.readFully(receive, receive.available());
                Request request = decoder.decode(inBytes, Request.class);
                log.info("====================request================" + request);

                ServiceInstance serviceInstance = serviceManager.lookup(request);
                Object data = serviceInvoker.invoke(serviceInstance, request);
                resp.setData(data);

            } catch (Exception e) {
                log.warn(e.getMessage(), e);
                resp.setCode(1);
                resp.setMessage("RPCServer Error:" + e.getClass().getName() + ":" + e.getMessage());
            }finally {
                try {
                    byte[] outBytes = encoder.encode(resp);
                    response.write(outBytes);
                    log.info("TO RPC Client");
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }


        }
    };
}
