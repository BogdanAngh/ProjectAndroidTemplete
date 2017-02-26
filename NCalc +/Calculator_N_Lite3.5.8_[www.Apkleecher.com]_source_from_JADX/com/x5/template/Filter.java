package com.x5.template;

import com.x5.template.filters.BasicFilter;
import com.x5.template.filters.ChunkFilter;
import com.x5.template.filters.FilterArgs;
import com.x5.template.filters.RegexFilter;
import com.x5.util.DataCapsule;
import com.x5.util.TableData;
import io.github.kexanie.library.BuildConfig;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filter {
    public static String FILTER_FIRST;
    public static String FILTER_LAST;
    private static Map<String, ChunkFilter> filters;
    private static final Pattern parsePattern;
    private static final Pattern parsePatternAlt;

    static {
        FILTER_FIRST = "FILTER_FIRST";
        FILTER_LAST = "FILTER_LAST";
        filters = registerStockFilters();
        parsePattern = Pattern.compile("includeIf\\(([\\!\\~])(.*)\\)\\.?([^\\)]*)$");
        parsePatternAlt = Pattern.compile("include\\.\\(([\\!\\~])(.*)\\)([^\\)]*)$");
    }

    private static Map<String, ChunkFilter> registerStockFilters() {
        if (filters == null) {
            filters = BasicFilter.getStockFilters();
        }
        return filters;
    }

    public static Object applyFilter(Chunk context, String filter, Object input) {
        if (filter == null) {
            return input;
        }
        int pipePos = findNextFilter(filter);
        if (pipePos >= 0) {
            return applyFilter(context, filter.substring(pipePos + 1), applyFilter(context, filter.substring(0, pipePos), input));
        }
        FilterArgs filterArgs = new FilterArgs(filter);
        String filterName = filterArgs.getFilterName();
        Map<String, ChunkFilter> customFilters = null;
        ChunkFactory userTheme = context.getChunkFactory();
        if (userTheme != null) {
            customFilters = userTheme.getFilters();
        }
        if (customFilters != null) {
            ChunkFilter userFilter = (ChunkFilter) customFilters.get(filterName);
            if (userFilter != null) {
                try {
                    return userFilter.applyFilter(context, input, filterArgs);
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                    return input;
                }
            }
        }
        if (filter.equals("type")) {
            return typeFilter(context, input);
        }
        if ((input instanceof String) || (input instanceof Snippet)) {
            String text = BasicFilter.stringify(input);
            if (filter.equals("trim")) {
                return text == null ? null : text.trim();
            }
            TableData array;
            if (!filter.startsWith("join(")) {
                if (!filter.startsWith("get(")) {
                    if (filter.equals("type")) {
                        return "STRING";
                    }
                } else if (text != null) {
                    array = InlineTable.parseTable(text);
                    if (array != null) {
                        return accessArrayIndex(array, filterArgs);
                    }
                }
            } else if (text != null) {
                array = InlineTable.parseTable(text);
                if (array != null) {
                    return joinInlineTable(array, filterArgs);
                }
            }
        }
        ChunkFilter stockFilter = (ChunkFilter) filters.get(filterName);
        if (stockFilter != null) {
            return stockFilter.applyFilter(context, input, filterArgs);
        }
        return input;
    }

    public static String[] splitFilters(String filter) {
        int startOfNext = findNextFilter(filter);
        if (startOfNext < 0) {
            return new String[]{filter};
        }
        ArrayList<String> filterList = new ArrayList();
        while (startOfNext >= 0) {
            filterList.add(filter.substring(0, startOfNext));
            filter = filter.substring(startOfNext + 1);
            startOfNext = findNextFilter(filter);
        }
        filterList.add(filter);
        return (String[]) filterList.toArray(new String[filterList.size()]);
    }

    private static int findNextFilter(String filter) {
        int pipePos = filter.indexOf(124);
        if (pipePos >= 0 && filter.startsWith("s/")) {
            int regexEnd = RegexFilter.nextRegexDelim(filter, 2);
            if (regexEnd < 0) {
                return pipePos;
            }
            regexEnd = RegexFilter.nextRegexDelim(filter, regexEnd + 1);
            if (regexEnd < 0) {
                return pipePos;
            }
            if (regexEnd < pipePos) {
                return pipePos;
            }
            return filter.indexOf("|", regexEnd + 1);
        } else if (pipePos < 0 || !filter.startsWith("onmatch")) {
            return pipePos;
        } else {
            int closeParen;
            boolean skippedArgs = false;
            int cursor = 8;
            while (!skippedArgs) {
                int slashPos = filter.indexOf("/", cursor);
                if (slashPos >= 0) {
                    slashPos = RegexFilter.nextRegexDelim(filter, slashPos + 1);
                    if (slashPos < 0) {
                        break;
                    }
                    int commaPos = FilterArgs.nextUnescapedDelim(",", filter, slashPos + 1);
                    if (commaPos < 0) {
                        break;
                    }
                    int moreArgs = FilterArgs.nextUnescapedDelim(",", filter, commaPos + 1);
                    if (moreArgs < 0) {
                        closeParen = FilterArgs.nextUnescapedDelim(")", filter, commaPos + 1);
                        if (closeParen < 0) {
                            break;
                        } else if (filter.length() <= closeParen + 8 || !filter.substring(closeParen + 1, closeParen + 8).equals("nomatch")) {
                            return filter.indexOf("|", closeParen + 1);
                        } else {
                            cursor = closeParen + 1;
                            skippedArgs = true;
                        }
                    } else {
                        cursor = moreArgs + 1;
                    }
                } else {
                    break;
                }
            }
            int openParen = filter.indexOf("(", cursor);
            if (openParen > 0) {
                closeParen = FilterArgs.nextUnescapedDelim(")", filter, openParen + 1);
                if (closeParen > 0) {
                    return filter.indexOf("|", closeParen + 1);
                }
            }
            return filter.indexOf("|", cursor);
        }
    }

    public static String translateIncludeIf(String tag, String open, String close, Map<String, Object> tagTable) {
        Matcher parseMatcher = parsePattern.matcher(tag);
        if (!parseMatcher.find()) {
            parseMatcher = parsePatternAlt.matcher(tag);
            if (!parseMatcher.find()) {
                return "[includeIf bad syntax: " + tag + "]";
            }
        }
        parseMatcher.group(0);
        String negater = parseMatcher.group(1);
        String test = parseMatcher.group(2);
        String includeTemplate = parseMatcher.group(3).replaceAll("[\\|:].*$", BuildConfig.FLAVOR);
        if (test.indexOf(61) >= 0 || test.indexOf("!~") >= 0) {
            String[] parts;
            String match;
            boolean isNeg = false;
            if (test.indexOf("==") <= 0) {
                isNeg = test.indexOf("!=") > 0;
                if (!isNeg) {
                    parts = test.split("=~");
                    boolean neg = false;
                    if (parts.length != 2) {
                        parts = test.split("!~");
                        neg = true;
                        if (parts.length != 2) {
                            return "[includeIf bad syntax: " + tag + "]";
                        }
                    }
                    String var = parts[0].trim();
                    match = parts[1].trim();
                    if (neg) {
                        return open + var + "|onmatch(" + match + ",)nomatch(+" + includeTemplate + ")" + close;
                    }
                    return open + var + "|onmatch(" + match + ",+" + includeTemplate + ")nomatch()" + close;
                }
            }
            parts = test.split("!=|==");
            if (parts.length != 2) {
                return "[includeIf bad syntax: " + tag + "]";
            }
            String tagA = parts[0].trim();
            String tagB = parts[1].trim();
            if (tagB.charAt(0) == '~') {
                String tagValue = null;
                Object tagValueObj = tagTable.get(tagA);
                if (tagValueObj != null) {
                    tagValue = tagValueObj.toString();
                }
                if (tagValue == null) {
                    tagValue = BuildConfig.FLAVOR;
                }
                if (isNeg) {
                    return open + tagB.substring(1) + "|onmatch(/^" + RegexFilter.escapeRegex(tagValue) + "$/,)nomatch(+" + includeTemplate + ")" + close;
                }
                return open + tagB.substring(1) + "|onmatch(/^" + RegexFilter.escapeRegex(tagValue) + "$/,+" + includeTemplate + ")nomatch()" + close;
            }
            match = tagB;
            if (tagB.charAt(0) == '\"' && tagB.charAt(match.length() - 1) == '\"') {
                match = tagB.substring(1, tagB.length() - 1);
            }
            if (isNeg) {
                return open + tagA + "|onmatch(/^" + RegexFilter.escapeRegex(match) + "$/,)nomatch(+" + includeTemplate + ")" + close;
            }
            return open + tagA + "|onmatch(/^" + RegexFilter.escapeRegex(match) + "$/,+" + includeTemplate + ")nomatch()" + close;
        } else if (negater.charAt(0) == '~') {
            return open + test + "|ondefined(+" + includeTemplate + "):" + close;
        } else {
            return open + test + "|ondefined():+" + includeTemplate + close;
        }
    }

    public static int grokFinalFilterPipe(String wholeTag, int startHere) {
        int cursor = startHere;
        String filter = wholeTag.substring(cursor + 1);
        for (int startOfNext = findNextFilter(filter); startOfNext >= 0; startOfNext = findNextFilter(filter.substring(startOfNext + 1))) {
            cursor = (cursor + 1) + startOfNext;
        }
        return cursor;
    }

    public static String accessArrayIndex(TableData table, FilterArgs getFilter) {
        return accessArrayIndex(extractListFromTable(table), getFilter);
    }

    public static String accessArrayIndex(String[] array, FilterArgs getFilter) {
        if (array == null) {
            return BuildConfig.FLAVOR;
        }
        return accessArrayIndex(Arrays.asList(array), getFilter);
    }

    public static String accessArrayIndex(List<String> list, FilterArgs getFilter) {
        if (list == null) {
            return BuildConfig.FLAVOR;
        }
        String[] args = getFilter.getFilterArgs();
        if (args != null) {
            try {
                int x = Integer.parseInt(args[0]);
                if (x < 0) {
                    x += list.size();
                }
                if (x >= 0 && x < list.size()) {
                    return (String) list.get(x);
                }
            } catch (NumberFormatException e) {
            }
        }
        return BuildConfig.FLAVOR;
    }

    private static List<String> extractListFromTable(TableData table) {
        if (table == null) {
            return null;
        }
        List<String> list = new ArrayList();
        while (table.hasNext()) {
            table.nextRecord();
            list.add(table.getRow()[0]);
        }
        return list;
    }

    public static String joinInlineTable(TableData table, FilterArgs joinFilter) {
        return joinStringList(extractListFromTable(table), joinFilter);
    }

    public static String joinStringArray(String[] array, FilterArgs joinFilter) {
        if (array == null) {
            return BuildConfig.FLAVOR;
        }
        if (array.length == 1) {
            return array[0];
        }
        return joinStringList(Arrays.asList(array), joinFilter);
    }

    public static String joinStringList(List<String> list, FilterArgs joinFilter) {
        if (list == null) {
            return BuildConfig.FLAVOR;
        }
        if (list.size() == 1) {
            return (String) list.get(0);
        }
        String divider = joinFilter.getUnparsedArgs();
        StringBuilder x = new StringBuilder();
        int i = 0;
        for (String s : list) {
            if (i > 0 && divider != null) {
                x.append(divider);
            }
            if (s != null) {
                x.append(s);
            }
            i++;
        }
        return x.toString();
    }

    public static String typeFilter(Chunk context, Object tagValue) {
        return _typeFilter(context, tagValue, 0);
    }

    private static String _typeFilter(Chunk context, Object tagValue, int depth) {
        if (depth > 7) {
            return "CIRCULAR_POINTER";
        }
        if (tagValue == null) {
            return "NULL";
        }
        if (tagValue instanceof String) {
            if (isInlineTable((String) tagValue)) {
                return "LIST";
            }
            return "STRING";
        } else if (tagValue instanceof Snippet) {
            if (isInlineTable(tagValue.toString())) {
                return "LIST";
            }
            Snippet snippet = (Snippet) tagValue;
            if (snippet.isSimplePointer()) {
                return _typeFilter(context, context.get(snippet.getPointer()), depth + 1);
            }
            return "STRING";
        } else if (tagValue instanceof Chunk) {
            return "CHUNK";
        } else {
            if ((tagValue instanceof String[]) || (tagValue instanceof List) || (tagValue instanceof Object[]) || (tagValue instanceof TableData)) {
                return "LIST";
            }
            if ((tagValue instanceof Map) || (tagValue instanceof DataCapsule)) {
                return "OBJECT";
            }
            return "UNKNOWN";
        }
    }

    private static boolean isInlineTable(String value) {
        if (InlineTable.parseTable(value) != null) {
            return true;
        }
        return false;
    }
}
