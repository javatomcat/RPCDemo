package com.carry.codec;

import org.junit.Test;

import static org.junit.Assert.*;

public class JSONEncoderTest {

    @Test
    public void encode() {
        Encoder encoder = new JSONEncoder();
        TestBean testBean = new TestBean();
        testBean.setAge(35);
        testBean.setName("李四");
        byte[] encode = encoder.encode(testBean);
        assertNotNull(encode);
    }
}