package com.x5.template.filters;

import com.x5.template.Chunk;
import java.util.Arrays;
import java.util.List;

public abstract class ListFilter implements ChunkFilter {
    public abstract String getFilterName();

    public abstract Object transformList(Chunk chunk, List list, FilterArgs filterArgs);

    public Object applyFilter(Chunk chunk, String text, FilterArgs args) {
        return applyFilter(chunk, new String[]{text}, args);
    }

    public String[] getFilterAliases() {
        return null;
    }

    public Object applyFilter(Chunk chunk, Object object, FilterArgs args) {
        List<Object> list;
        if (object instanceof List) {
            list = (List) object;
        } else if (object instanceof Object[]) {
            list = Arrays.asList((Object[]) object);
        } else {
            list = Arrays.asList(new Object[]{object});
        }
        return transformList(chunk, list, args);
    }
}
