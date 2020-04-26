package com.carry.rpc.example;

import com.carry.rpc.example.impl.CalcServiceImpl;
import com.carry.server.RPCServer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Server {

    public static void main(String[] args) {
        RPCServer server = new RPCServer();
        server.register(CalcService.class,new CalcServiceImpl());
        server.start();
    }
}
