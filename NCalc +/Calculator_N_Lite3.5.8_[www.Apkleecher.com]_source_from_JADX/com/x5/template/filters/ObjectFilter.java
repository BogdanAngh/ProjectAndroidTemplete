package com.x5.template.filters;

import com.x5.template.Chunk;
import com.x5.util.ObjectDataMap;

public abstract class ObjectFilter implements ChunkFilter {
    public abstract String getFilterName();

    public abstract Object transformObject(Chunk chunk, Object obj, FilterArgs filterArgs);

    public Object applyFilter(Chunk chunk, String text, FilterArgs args) {
        return text;
    }

    public String[] getFilterAliases() {
        return null;
    }

    public Object applyFilter(Chunk chunk, Object object, FilterArgs args) {
        if (object instanceof ObjectDataMap) {
            object = ((ObjectDataMap) object).unwrap();
        }
        return transformObject(chunk, object, args);
    }
}
