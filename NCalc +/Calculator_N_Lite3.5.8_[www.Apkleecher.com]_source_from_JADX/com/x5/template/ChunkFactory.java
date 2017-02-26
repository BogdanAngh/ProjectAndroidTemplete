package com.x5.template;

import com.x5.template.filters.ChunkFilter;
import java.util.Map;

public interface ChunkFactory {
    Map<String, ChunkFilter> getFilters();

    Chunk makeChunk();

    Chunk makeChunk(String str);

    Chunk makeChunk(String str, String str2);
}
