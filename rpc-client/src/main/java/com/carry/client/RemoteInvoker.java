package com.carry.client;

import com.carry.codec.Decoder;
import com.carry.codec.Encoder;
import com.carry.protocol.Request;
import com.carry.protocol.Response;
import com.carry.protocol.ServiceDescriptor;
import com.carry.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class RemoteInvoker implements InvocationHandler {

    private Class clazz;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector transportSelector;

    RemoteInvoker(Class clazz,
                  Encoder encoder,
                  Decoder decoder,
                  TransportSelector transportSelector) {
        this.clazz = clazz;
        this.encoder = encoder;
        this.decoder = decoder;
        this.transportSelector = transportSelector;
    }


    @Override
    public Object invoke(Object proxy,
                         Method method,
                         Object[] args) throws Throwable {
        Request request = new Request();
        request.setService(ServiceDescriptor.from(clazz, method));
        request.setParameters(args);


        Response response = invokeRemote(request);

        if (response == null || response.getCode() != 0) {
            throw new IllegalStateException("fail to invoke remote :" + response.getMessage());
        }
        return response.getData();
    }

    private Response invokeRemote(Request request) {
        TransportClient client = null;
        Response response = new Response();
        try {
            client = transportSelector.select();
            byte[] outBytes = encoder.encode(request);
            InputStream receive = client.write(new ByteArrayInputStream(outBytes));
            byte[] inBytes = IOUtils.readFully(receive, receive.available());
            response = decoder.decode(inBytes, Response.class);
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            response.setCode(1);
            response.setMessage("RPC Client get Error:" + e.getMessage());
        } finally {
            if (client != null) {
                transportSelector.release(client);
            }
        }
        return response;

    }
}
