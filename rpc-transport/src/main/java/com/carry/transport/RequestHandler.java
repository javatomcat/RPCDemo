package com.carry.transport;

import jdk.internal.util.xml.impl.Input;

import java.io.InputStream;
import java.io.OutputStream;

public interface RequestHandler {
    void doRequest(InputStream receive, OutputStream response);
}
