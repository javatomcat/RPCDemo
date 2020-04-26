package com.carry.client;

import com.carry.common.utils.ReflectionUtils;
import com.carry.protocol.Peer;
import com.carry.transport.TransportClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class RandomTransportSelector implements TransportSelector {
    private List<TransportClient> clients = new ArrayList<TransportClient>();

    @Override
    public synchronized void init(List<Peer> peerList,
                                  int count,
                                  Class<? extends TransportClient> clazz) {

        count = Math.max(count, 1);

        for (Peer peer : peerList) {
            for (int i = 0; i < count; i++) {
                TransportClient client = ReflectionUtils.newInstance(clazz);
                client.connect(peer);
                clients.add(client);
            }
            log.info("connnect to server", peer);
        }

    }

    @Override
    public synchronized TransportClient select() {
        int i = new Random().nextInt(clients.size());
        return clients.remove(i);
    }

    @Override
    public synchronized void release(TransportClient client) {
        clients.add(client);
    }

    @Override
    public synchronized void close() {
        for (TransportClient client : clients) {
            client.close();
        }
        clients.clear();
    }
}
