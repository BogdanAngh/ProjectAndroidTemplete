package com.x5.template.filters;

import com.x5.template.Chunk;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class HexFilter extends BasicFilter implements ChunkFilter {
    public String transformText(Chunk chunk, String text, FilterArgs args) {
        if (text == null) {
            return null;
        }
        String hex;
        try {
            hex = new BigInteger(1, text.getBytes("UTF-8")).toString(16);
        } catch (UnsupportedEncodingException e) {
            hex = new BigInteger(1, text.getBytes()).toString(16);
        }
        if (hex != null) {
            return hex;
        }
        return text;
    }

    public String getFilterName() {
        return "hex";
    }
}
