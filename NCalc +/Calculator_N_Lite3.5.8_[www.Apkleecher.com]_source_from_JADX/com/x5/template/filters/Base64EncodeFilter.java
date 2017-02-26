package com.x5.template.filters;

import com.x5.template.Chunk;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import sun.misc.BASE64Encoder;

public class Base64EncodeFilter extends BasicFilter implements ChunkFilter {
    public String transformText(Chunk chunk, String text, FilterArgs args) {
        if (text == null) {
            return null;
        }
        return base64(text);
    }

    public String getFilterName() {
        return "base64";
    }

    public String[] getFilterAliases() {
        return new String[]{"base64encode"};
    }

    public static String base64(String text) {
        try {
            return base64(text.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return base64(text.getBytes());
        }
    }

    public static String base64(byte[] bytes) {
        try {
            return ((BASE64Encoder) Class.forName("sun.misc.BASE64Encoder").newInstance()).encode(bytes);
        } catch (ClassNotFoundException e) {
            try {
                return (String) Class.forName("com.x5.util.Base64").getMethod("encodeBytes", new Class[]{byte[].class}).invoke(null, new Object[]{bytes});
            } catch (ClassNotFoundException e2) {
                try {
                    return new String(bytes, "UTF-8");
                } catch (UnsupportedEncodingException e3) {
                    return new String(bytes);
                }
            } catch (NoSuchMethodException e4) {
                return new String(bytes, "UTF-8");
            } catch (IllegalAccessException e5) {
                return new String(bytes, "UTF-8");
            } catch (InvocationTargetException e6) {
                return new String(bytes, "UTF-8");
            }
        } catch (InstantiationException e7) {
            return (String) Class.forName("com.x5.util.Base64").getMethod("encodeBytes", new Class[]{byte[].class}).invoke(null, new Object[]{bytes});
        } catch (IllegalAccessException e8) {
            return (String) Class.forName("com.x5.util.Base64").getMethod("encodeBytes", new Class[]{byte[].class}).invoke(null, new Object[]{bytes});
        }
    }
}
