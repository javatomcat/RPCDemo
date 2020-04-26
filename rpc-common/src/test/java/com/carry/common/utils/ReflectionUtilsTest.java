package com.carry.common.utils;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReflectionUtilsTest {

    private TestBean newInstance;

    @Test
    public void newInstance() {
        TestBean testBean = ReflectionUtils.newInstance(TestBean.class);
        assertNotNull(testBean);
    }

    @Test
    public void getPublicMethods() {
        Method[] publicMethods = ReflectionUtils.getPublicMethods(TestBean.class);
        assertEquals(1, publicMethods.length);
        assertEquals("a", publicMethods[0].getName());
    }

    @Test
    public void invoke() {
        TestBean testBean = new TestBean();
        Method[] publicMethods = ReflectionUtils.getPublicMethods(TestBean.class);
        Object data = ReflectionUtils.invoke(testBean, publicMethods[0], null);

        assertEquals("a", data);
    }
}