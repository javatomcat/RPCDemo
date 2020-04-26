package com.carry.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 表示网络的端点
 */
@Data
@AllArgsConstructor
public class Peer {
    private String host;
    private int port;
}
