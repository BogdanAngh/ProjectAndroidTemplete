package com.x5.template.filters;

import com.x5.template.Chunk;
import com.x5.template.Snippet;
import com.x5.util.ObjectDataMap;
import java.util.HashMap;
import java.util.Map;

public abstract class BasicFilter implements ChunkFilter {
    public static ChunkFilter[] stockFilters;

    public abstract String getFilterName();

    public abstract String transformText(Chunk chunk, String str, FilterArgs filterArgs);

    public Object applyFilter(Chunk chunk, String text, FilterArgs args) {
        return transformText(chunk, text, args);
    }

    public String[] getFilterAliases() {
        return null;
    }

    public Object applyFilter(Chunk chunk, Object object, FilterArgs args) {
        return transformText(chunk, object == null ? null : stringify(object), args);
    }

    public static String stringify(Snippet snippet) {
        return snippet.toSimpleString();
    }

    public static String stringify(Object object) {
        if (object instanceof Snippet) {
            return stringify((Snippet) object);
        }
        return ObjectDataMap.getAsString(object);
    }

    static {
        stockFilters = new ChunkFilter[]{new AlternateFilter(), new Base64DecodeFilter(), new Base64EncodeFilter(), new CalcFilter(), new CheckedFilter(), new DefangFilter(), new DefaultFilter(), new EscapeQuotesFilter(), new EscapeXMLFilter(), new UnescapeXMLFilter(), new ExecFilter(), new FormatFilter(), new HexFilter(), new HexUpperFilter(), new IndentFilter(), new LetterCaseFilter(), new MD5HexFilter(), new MD5Base64Filter(), new OnEmptyFilter(), new OnDefinedFilter(), new OnMatchFilter(), new OrdinalSuffixFilter(), new PadLeftFilter(), new PadRightFilter(), new QuickCalcFilter(), new RegexFilter(), new SelectedFilter(), new SHA1HexFilter(), new SHA1Base64Filter(), new StringFilter(), new TranslateFilter(), new URLDecodeFilter(), new URLEncodeFilter(), new SliceFilter(), new SortFilter(), new ReverseFilter(), new JoinFilter(), new ListIndexFilter(), new SplitFilter(), new LengthFilter()};
    }

    public static Map<String, ChunkFilter> getStockFilters() {
        Map<String, ChunkFilter> filters = new HashMap();
        for (ChunkFilter filter : stockFilters) {
            filters.put(filter.getFilterName(), filter);
            String[] aliases = filter.getFilterAliases();
            if (aliases != null) {
                for (String alias : aliases) {
                    filters.put(alias, filter);
                }
            }
        }
        return filters;
    }
}
