package com.x5.template.filters;

import com.x5.template.Chunk;

public interface ChunkFilter {
    Object applyFilter(Chunk chunk, Object obj, FilterArgs filterArgs);

    Object applyFilter(Chunk chunk, String str, FilterArgs filterArgs);

    String[] getFilterAliases();

    String getFilterName();
}
