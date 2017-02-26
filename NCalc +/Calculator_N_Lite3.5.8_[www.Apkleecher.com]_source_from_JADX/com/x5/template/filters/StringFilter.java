package com.x5.template.filters;

import com.x5.template.Chunk;

public class StringFilter extends BasicFilter {
    public String transformText(Chunk chunk, String text, FilterArgs args) {
        return text;
    }

    public String getFilterName() {
        return "string";
    }

    public String[] getFilterAliases() {
        return new String[]{"str"};
    }
}
