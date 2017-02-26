package com.x5.template.filters;

import com.x5.template.Chunk;
import java.util.List;

public class ReverseFilter extends SliceFilter {
    public String getFilterName() {
        return "reverse";
    }

    public Object transformList(Chunk chunk, List list, FilterArgs args) {
        return super.transformList(chunk, list, new FilterArgs("slice(::-1)"));
    }
}
