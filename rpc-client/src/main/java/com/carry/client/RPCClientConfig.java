package com.carry.client;

import com.carry.codec.Decoder;
import com.carry.codec.Encoder;
import com.carry.codec.JSONDecoder;
import com.carry.codec.JSONEncoder;
import com.carry.protocol.Peer;
import com.carry.transport.TransportClient;
import com.carry.transport.http.HTTPTransportClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPCClientConfig {
    private Class<? extends TransportClient> transportClientClass = HTTPTransportClient.class;

    private Class<? extends Encoder> encoderClass = JSONEncoder.class;

    private Class<? extends Decoder> decoderClass = JSONDecoder.class;

    private Class<? extends TransportSelector> selectorClass = RandomTransportSelector.class;

    private int connectCount = 1;

    private List<Peer> servers = Arrays.asList(new Peer("127.0.0.1", 3000));


}
