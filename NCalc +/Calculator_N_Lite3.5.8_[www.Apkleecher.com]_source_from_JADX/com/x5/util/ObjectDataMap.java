package com.x5.util;

import com.madrobot.beans.Introspector;
import com.madrobot.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ObjectDataMap implements Map {
    private static final Map<String, Object> EMPTY_MAP;
    private static final Class[] NO_ARGS;
    private static final HashSet<Class<?>> WRAPPER_TYPES;
    private boolean isBean;
    private Object object;
    private Map<String, Object> pickle;

    private static class IntrospectionException extends Exception {
        private static final long serialVersionUID = 8890979383599687484L;

        private IntrospectionException() {
        }
    }

    private static class MadRobotIntrospector {
        private MadRobotIntrospector() {
        }

        private static Map<String, Object> mapifyBean(Object bean) throws IntrospectionException {
            Map<String, Object> map = null;
            try {
                PropertyDescriptor[] properties = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
                if (properties != null) {
                    PropertyDescriptor[] arr$ = properties;
                    int len$ = arr$.length;
                    int i$ = 0;
                    map = null;
                    while (i$ < len$) {
                        Map<String, Object> pickle;
                        PropertyDescriptor property = arr$[i$];
                        Class paramClass = property.getPropertyType();
                        try {
                            Object paramValue = property.getReadMethod().invoke(bean, (Object[]) null);
                            if (paramValue != null) {
                                String paramName = ObjectDataMap.splitCamelCase(property.getName());
                                if (paramValue instanceof Boolean) {
                                    paramName = "is_" + paramName;
                                }
                                if (map == null) {
                                    pickle = new HashMap();
                                } else {
                                    pickle = map;
                                }
                                try {
                                    ObjectDataMap.storeValue(pickle, paramClass, paramName, paramValue, true);
                                } catch (InvocationTargetException e) {
                                } catch (IllegalAccessException e2) {
                                }
                            } else {
                                pickle = map;
                            }
                        } catch (InvocationTargetException e3) {
                            pickle = map;
                        } catch (IllegalAccessException e4) {
                            pickle = map;
                        }
                        i$++;
                        map = pickle;
                    }
                }
                return map;
            } catch (com.madrobot.beans.IntrospectionException e5) {
                throw new IntrospectionException();
            }
        }
    }

    private static class StandardIntrospector {
        private StandardIntrospector() {
        }

        private static Map<String, Object> mapifyBean(Object bean) throws IntrospectionException {
            Map<String, Object> map = null;
            try {
                java.beans.PropertyDescriptor[] properties = java.beans.Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
                if (properties != null) {
                    java.beans.PropertyDescriptor[] arr$ = properties;
                    int len$ = arr$.length;
                    int i$ = 0;
                    map = null;
                    while (i$ < len$) {
                        Map<String, Object> pickle;
                        java.beans.PropertyDescriptor property = arr$[i$];
                        Class paramClass = property.getPropertyType();
                        try {
                            Object paramValue = property.getReadMethod().invoke(bean, (Object[]) null);
                            if (paramValue != null) {
                                String paramName = ObjectDataMap.splitCamelCase(property.getName());
                                if (paramValue instanceof Boolean) {
                                    paramName = "is_" + paramName;
                                }
                                if (map == null) {
                                    pickle = new HashMap();
                                } else {
                                    pickle = map;
                                }
                                try {
                                    ObjectDataMap.storeValue(pickle, paramClass, paramName, paramValue, true);
                                } catch (InvocationTargetException e) {
                                } catch (IllegalAccessException e2) {
                                }
                            } else {
                                pickle = map;
                            }
                        } catch (InvocationTargetException e3) {
                            pickle = map;
                        } catch (IllegalAccessException e4) {
                            pickle = map;
                        }
                        i$++;
                        map = pickle;
                    }
                }
                return map;
            } catch (java.beans.IntrospectionException e5) {
                throw new IntrospectionException();
            }
        }
    }

    static {
        EMPTY_MAP = new HashMap();
        WRAPPER_TYPES = getWrapperTypes();
        NO_ARGS = new Class[0];
    }

    private static HashSet<Class<?>> getWrapperTypes() {
        HashSet<Class<?>> ret = new HashSet();
        ret.add(Boolean.class);
        ret.add(Character.class);
        ret.add(Byte.class);
        ret.add(Short.class);
        ret.add(Integer.class);
        ret.add(Long.class);
        ret.add(Float.class);
        ret.add(Double.class);
        ret.add(Void.class);
        return ret;
    }

    public static boolean isWrapperType(Class<?> clazz) {
        return WRAPPER_TYPES.contains(clazz);
    }

    public ObjectDataMap(Object pojo) {
        this.pickle = null;
        this.isBean = false;
        this.object = pojo;
    }

    private void init() {
        if (this.pickle == null) {
            this.pickle = mapify(this.object);
            if (this.pickle == null) {
                this.pickle = EMPTY_MAP;
            }
        }
    }

    public static ObjectDataMap wrapBean(Object bean) {
        if (bean == null) {
            return null;
        }
        ObjectDataMap boxedBean = new ObjectDataMap(bean);
        boxedBean.isBean = true;
        return boxedBean;
    }

    public static String getAsString(Object obj) {
        Method toString = null;
        try {
            toString = obj.getClass().getMethod("toString", NO_ARGS);
        } catch (NoSuchMethodException e) {
        } catch (SecurityException e2) {
        }
        if (toString.getDeclaringClass().equals(Object.class)) {
            return "OBJECT:" + obj.getClass().getName();
        }
        return obj.toString();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Map<java.lang.String, java.lang.Object> mapify(java.lang.Object r6) {
        /*
        r5 = this;
        r1 = 0;
        r4 = r6 instanceof com.x5.util.DataCapsule;
        if (r4 == 0) goto L_0x000c;
    L_0x0005:
        r6 = (com.x5.util.DataCapsule) r6;
        r4 = r5.mapifyCapsule(r6);
    L_0x000b:
        return r4;
    L_0x000c:
        r4 = r5.isBean;
        if (r4 != 0) goto L_0x001f;
    L_0x0010:
        r1 = r5.mapifyPOJO(r6);
        if (r1 == 0) goto L_0x001c;
    L_0x0016:
        r4 = r1.isEmpty();
        if (r4 == 0) goto L_0x002e;
    L_0x001c:
        r4 = 1;
        r5.isBean = r4;
    L_0x001f:
        r4 = r5.isBean;
        if (r4 == 0) goto L_0x003d;
    L_0x0023:
        r4 = "java.beans.Introspector";
        r0 = java.lang.Class.forName(r4);	 Catch:{ ClassNotFoundException -> 0x0030, IntrospectionException -> 0x003c }
        r4 = com.x5.util.ObjectDataMap.StandardIntrospector.mapifyBean(r6);	 Catch:{ ClassNotFoundException -> 0x0030, IntrospectionException -> 0x003c }
        goto L_0x000b;
    L_0x002e:
        r4 = r1;
        goto L_0x000b;
    L_0x0030:
        r2 = move-exception;
        r4 = "com.madrobot.beans.Introspector";
        r3 = java.lang.Class.forName(r4);	 Catch:{ ClassNotFoundException -> 0x003f, IntrospectionException -> 0x003c }
        r4 = com.x5.util.ObjectDataMap.MadRobotIntrospector.mapifyBean(r6);	 Catch:{ ClassNotFoundException -> 0x003f, IntrospectionException -> 0x003c }
        goto L_0x000b;
    L_0x003c:
        r4 = move-exception;
    L_0x003d:
        r4 = r1;
        goto L_0x000b;
    L_0x003f:
        r4 = move-exception;
        goto L_0x003d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.x5.util.ObjectDataMap.mapify(java.lang.Object):java.util.Map<java.lang.String, java.lang.Object>");
    }

    public Map<String, Object> mapifyPOJO(Object pojo) {
        Field[] fields = pojo.getClass().getDeclaredFields();
        Map<String, Object> pickle = null;
        for (Field field : fields) {
            String paramName = field.getName();
            Class paramClass = field.getType();
            int mods = field.getModifiers();
            if (!(Modifier.isPrivate(mods) || Modifier.isProtected(mods))) {
                field.setAccessible(true);
            }
            Object obj = null;
            try {
                obj = field.get(pojo);
            } catch (IllegalAccessException e) {
            }
            if (obj != null) {
                if (pickle == null) {
                    pickle = new HashMap();
                }
                storeValue(pickle, paramClass, splitCamelCase(paramName), obj, this.isBean);
            }
        }
        return pickle;
    }

    private Map<String, Object> mapifyCapsule(DataCapsule capsule) {
        DataCapsuleReader reader = DataCapsuleReader.getReader(capsule);
        String[] tags = reader.getColumnLabels(null);
        Object[] data = reader.extractData(capsule);
        this.pickle = new HashMap();
        for (int i = 0; i < tags.length; i++) {
            Object val = data[i];
            if (val != null) {
                if (val instanceof String) {
                    this.pickle.put(tags[i], val);
                } else if (val instanceof DataCapsule) {
                    this.pickle.put(tags[i], new ObjectDataMap(val));
                } else {
                    this.pickle.put(tags[i], val.toString());
                }
            }
        }
        return this.pickle;
    }

    private static void storeValue(Map<String, Object> pickle, Class paramClass, String paramName, Object paramValue, boolean isBean) {
        if (paramClass.isArray() || (paramValue instanceof List)) {
            pickle.put(paramName, paramValue);
        } else if (paramClass == String.class) {
            pickle.put(paramName, paramValue);
        } else if (paramValue instanceof Boolean) {
            if (((Boolean) paramValue).booleanValue()) {
                pickle.put(paramName, "TRUE");
            }
        } else if (paramClass.isPrimitive() || isWrapperType(paramClass)) {
            pickle.put(paramName, paramValue.toString());
        } else {
            pickle.put(paramName, isBean ? wrapBean(paramValue) : new ObjectDataMap(paramValue));
        }
    }

    public static String splitCamelCase(String s) {
        return s.replaceAll(String.format("%s|%s|%s", new Object[]{"(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])"}), "_").toLowerCase();
    }

    public int size() {
        init();
        return this.pickle.size();
    }

    public boolean isEmpty() {
        init();
        return this.pickle.isEmpty();
    }

    public boolean containsKey(Object key) {
        init();
        return this.pickle.containsKey(key);
    }

    public boolean containsValue(Object value) {
        init();
        return this.pickle.containsValue(value);
    }

    public Object get(Object key) {
        init();
        return this.pickle.get(key);
    }

    public Object put(Object key, Object value) {
        return null;
    }

    public Object remove(Object key) {
        return null;
    }

    public void putAll(Map m) {
    }

    public void clear() {
    }

    public Set keySet() {
        init();
        return this.pickle.keySet();
    }

    public Collection values() {
        init();
        return this.pickle.values();
    }

    public Set entrySet() {
        init();
        return this.pickle.entrySet();
    }

    public String toString() {
        return getAsString(this.object);
    }

    public Object unwrap() {
        return this.object;
    }
}
