package com.carry.client;

import com.carry.protocol.Peer;
import com.carry.transport.TransportClient;
import lombok.Data;

import java.util.List;

/**
 * 表示选择的Server来进行链接
 */
public interface TransportSelector {
    /**
     * 初始化selector
     *
     * @param peerList 可以连接的server端点数
     * @param count    client与server建立了多少个链接
     * @param clazz    client实现class
     */
    void init(List<Peer> peerList,
              int count,
              Class<? extends TransportClient> clazz);

    /**
     * 选择一个client与Server做交互
     *
     * @return
     */
    TransportClient select();

    void release(TransportClient client);

    void close();


}
