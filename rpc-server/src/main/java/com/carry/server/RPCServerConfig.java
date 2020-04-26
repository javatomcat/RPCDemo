package com.carry.server;


import com.carry.codec.Decoder;
import com.carry.codec.Encoder;
import com.carry.codec.JSONDecoder;
import com.carry.codec.JSONEncoder;
import com.carry.transport.TransportServer;
import com.carry.transport.http.HTTPTransportServer;
import lombok.Data;

/**
 *
 */
@Data
public class RPCServerConfig {
    private Class<? extends TransportServer> transportClass = HTTPTransportServer.class;

    private Class<? extends Encoder> encoder = JSONEncoder.class;

    private Class<? extends Decoder> decoder = JSONDecoder.class;

    private int port = 3000;


}
