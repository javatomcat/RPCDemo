package com.carry.common.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类
 */
public class ReflectionUtils {
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static Method[] getPublicMethods(Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> pmothods = new ArrayList<Method>();
        for (Method m : methods) {
            if (Modifier.isPublic(m.getModifiers())) {
                pmothods.add(m);
            }
        }
        return pmothods.toArray(new Method[0]);
    }

    public static Object invoke(Object object,
                                Method method,
                                Object... args) {
        try {
            return method.invoke(object, args);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
