package com.x5.template.filters;

import com.x5.template.Chunk;
import com.x5.template.ChunkLocale;

public class TranslateFilter extends BasicFilter implements ChunkFilter {
    public String transformText(Chunk context, String text, FilterArgs args) {
        if (text == null) {
            return null;
        }
        if (context == null) {
            return text;
        }
        ChunkLocale translator = context.getLocale();
        return translator != null ? translator.translate(text, null, context) : text;
    }

    public String getFilterName() {
        return "_";
    }

    public String[] getFilterAliases() {
        return new String[]{"__", "translate", "xlate"};
    }
}
