package com.x5.template.filters;

import com.x5.template.Chunk;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLEncodeFilter extends BasicFilter implements ChunkFilter {
    public String transformText(Chunk chunk, String text, FilterArgs args) {
        if (text == null) {
            return null;
        }
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return text;
        }
    }

    public String getFilterName() {
        return "urlencode";
    }

    public String[] getFilterAliases() {
        return new String[]{"url"};
    }
}
