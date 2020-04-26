package com.carry.rpc.example;

import com.carry.client.RPCClient;
import lombok.extern.slf4j.Slf4j;

public class Client {
    public static void main(String[] args) {
        RPCClient rpcClient = new RPCClient();
        CalcService calc = rpcClient.getProxy(CalcService.class);
        int add = calc.add(2, 3);
        int minus = calc.minus(10, 8);

        System.out.println("加法的结果：" + add);
        System.out.println("减法的结果：" + minus);
    }

}
