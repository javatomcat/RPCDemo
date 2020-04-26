package com.carry.transport;


import com.carry.protocol.Peer;

import java.io.InputStream;

/**
 * 1.创建连接
 * 2.发送响应
 * 3.关闭连接
 */
public interface TransportClient {

    void connect(Peer peer);

    InputStream write(InputStream inputStream);

    void close();
}
