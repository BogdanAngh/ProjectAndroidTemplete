package com.x5.template.filters;

import com.x5.template.Chunk;
import java.util.List;

public class ListIndexFilter extends ListFilter {
    public String getFilterName() {
        return "get";
    }

    public Object transformList(Chunk chunk, List list, FilterArgs arg) {
        if (list == null) {
            return null;
        }
        String[] args = arg.getFilterArgs();
        if (args.length < 1) {
            return null;
        }
        int i = Integer.parseInt(args[0]);
        if (i < 0) {
            i += list.size();
        }
        if (i < 0 || i >= list.size()) {
            return null;
        }
        return list.get(i);
    }
}
