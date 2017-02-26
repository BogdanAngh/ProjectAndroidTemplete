package com.x5.template.filters;

import com.x5.template.Chunk;
import com.x5.util.Base64;
import java.io.UnsupportedEncodingException;

public class Base64DecodeFilter extends BasicFilter implements ChunkFilter {
    public String transformText(Chunk chunk, String text, FilterArgs args) {
        if (text == null) {
            return null;
        }
        return base64Decode(text);
    }

    public String getFilterName() {
        return "base64decode";
    }

    public static String base64Decode(String text) {
        byte[] textBytes;
        try {
            textBytes = text.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            textBytes = text.getBytes();
        }
        byte[] decoded = Base64.decode(textBytes, 0, textBytes.length);
        if (decoded == null) {
            return text;
        }
        try {
            return new String(decoded, "UTF-8");
        } catch (UnsupportedEncodingException e2) {
            return new String(decoded);
        }
    }
}
