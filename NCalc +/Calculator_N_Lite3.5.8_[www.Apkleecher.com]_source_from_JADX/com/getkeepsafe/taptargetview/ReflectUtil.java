package com.getkeepsafe.taptargetview;

import java.lang.reflect.Field;

class ReflectUtil {
    ReflectUtil() {
    }

    static Object getPrivateField(Object source, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field objectField = source.getClass().getDeclaredField(fieldName);
        objectField.setAccessible(true);
        return objectField.get(source);
    }
}
