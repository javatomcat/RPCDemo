package com.carry.codec;

import org.junit.Test;

import static org.junit.Assert.*;

public class JSONDecoderTest {

    @Test
    public void decode() {
        JSONDecoder jsonDecoder = new JSONDecoder();
        Encoder encoder = new JSONEncoder();
        TestBean testBean = new TestBean();
        testBean.setAge(35);
        testBean.setName("李四");
        byte[] encodes = encoder.encode(testBean);
        assertNotNull(encodes);

        TestBean testBean2 = jsonDecoder.decode(encodes, TestBean.class);
        assertEquals(testBean.getAge(), testBean2.getAge());
        assertEquals(testBean.getName(), testBean2.getName());

    }
}