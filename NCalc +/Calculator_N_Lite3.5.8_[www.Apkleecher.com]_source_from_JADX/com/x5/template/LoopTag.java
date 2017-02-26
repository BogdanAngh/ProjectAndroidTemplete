package com.x5.template;

import com.example.duy.calculator.math_eval.Constants;
import com.x5.template.filters.RegexFilter;
import com.x5.util.DataCapsuleTable;
import com.x5.util.ObjectDataMap;
import com.x5.util.TableData;
import io.github.kexanie.library.BuildConfig;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoopTag extends BlockTag {
    private static final String FIRST_MARKER = "first";
    private static final String LAST_MARKER = "last";
    private static final String PLACE_TAG = "place";
    private static final Pattern UNIVERSAL_LF;
    private Chunk chunk;
    private Snippet dividerSnippet;
    private Snippet emptySnippet;
    private String emptyTemplate;
    private Map<String, Object> options;
    private Snippet rowSnippet;
    private String rowTemplate;
    private Chunk rowX;

    public static void main(String[] args) {
        LoopTag loop = new LoopTag();
        loop.parseParams("{~.loop data=\"~mydata\" template=\"#test_row\" no_data=\"#test_empty\"}");
        System.out.println("row_tpl=" + loop.rowTemplate);
        System.out.println("empty_tpl=" + loop.emptyTemplate);
    }

    public static String expandLoop(String params, Chunk ch, String origin, int depth) {
        LoopTag loop = new LoopTag(params, ch, origin);
        StringWriter out = new StringWriter();
        try {
            loop.renderBlock(out, ch, origin, depth);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        return out.toString();
    }

    public LoopTag() {
        this.emptySnippet = null;
        this.dividerSnippet = null;
        this.rowSnippet = null;
    }

    public LoopTag(String params, Chunk ch, String origin) {
        this.emptySnippet = null;
        this.dividerSnippet = null;
        this.rowSnippet = null;
        this.chunk = ch;
        parseParams(params);
        initWithoutBlock(origin);
    }

    private void initWithoutBlock(String origin) {
        if (this.chunk != null) {
            ContentSource snippetRepo = this.chunk.getTemplateSet();
            if (snippetRepo != null) {
                if (this.rowTemplate != null) {
                    this.rowSnippet = snippetRepo.getSnippet(BlockTag.qualifyTemplateRef(origin, this.rowTemplate));
                }
                if (this.emptyTemplate != null) {
                    this.emptySnippet = snippetRepo.getSnippet(BlockTag.qualifyTemplateRef(origin, this.emptyTemplate));
                }
            }
        }
    }

    public LoopTag(String params, Snippet body) {
        this.emptySnippet = null;
        this.dividerSnippet = null;
        this.rowSnippet = null;
        parseParams(params);
        initBody(body);
    }

    private void parseParams(String params) {
        if (params != null) {
            if (params.startsWith(".loop(")) {
                parseFnParams(params);
            } else if (params.matches("\\.loop [^\" ]+ .*")) {
                parseEZParams(params);
            } else {
                parseAttributes(params);
            }
        }
    }

    private void parseEZParams(String paramString) {
        String[] params = paramString.split(" +");
        String dataVar = params[2];
        this.options = Attributes.parse(paramString);
        if (this.options == null) {
            this.options = new HashMap();
        }
        this.options.put("data", dataVar);
        if (this.options.containsKey("counter")) {
            this.options.put("counter_tag", this.options.get("counter"));
        }
        if (params.length > 3 && params[3].equals("as")) {
            String loopVarPrefix = params[4];
            if (loopVarPrefix != null) {
                if (loopVarPrefix.startsWith("~") || loopVarPrefix.startsWith("$")) {
                    loopVarPrefix = loopVarPrefix.substring(1);
                }
                if (loopVarPrefix.contains(":")) {
                    String[] labels = loopVarPrefix.split(":");
                    String valuePrefix = labels[1];
                    if (valuePrefix.startsWith("~") || valuePrefix.startsWith("$")) {
                        valuePrefix = valuePrefix.substring(1);
                    }
                    this.options.put("keyname", labels[0]);
                    this.options.put("valname", valuePrefix);
                }
                this.options.put("name", loopVarPrefix);
            }
        }
    }

    private void parseFnParams(String params) {
        int endOfParams = params.length();
        if (params.endsWith(")")) {
            endOfParams--;
        }
        String[] args = params.substring(".loop(".length(), endOfParams).split(",");
        if (args != null && args.length >= 2) {
            String dataVar = args[0];
            if (this.options == null) {
                this.options = new HashMap();
            }
            this.options.put("data", dataVar);
            this.rowTemplate = args[1];
            if (args.length > 2) {
                this.emptyTemplate = args[2];
            } else {
                this.emptyTemplate = null;
            }
        }
    }

    private void parseAttributes(String params) {
        Map<String, Object> opts = Attributes.parse(params);
        if (opts != null) {
            if (opts.containsKey("counter")) {
                opts.put("counter_tag", opts.get("counter"));
            }
            this.options = opts;
            this.rowTemplate = (String) opts.get("template");
            this.emptyTemplate = (String) opts.get("no_data");
        }
    }

    private TableData fetchData(String dataVar, String origin) {
        TableData data = null;
        if (dataVar == null) {
            return null;
        }
        int rangeMarker = dataVar.indexOf("[");
        if (rangeMarker > 0) {
            int rangeMarker2 = dataVar.indexOf("]", rangeMarker);
            if (rangeMarker2 < 0) {
                rangeMarker2 = dataVar.length();
            }
            String range = dataVar.substring(rangeMarker + 1, rangeMarker2);
            dataVar = dataVar.substring(0, rangeMarker);
            registerOption("range", range);
        }
        char c0 = dataVar.charAt(0);
        boolean isDirective = false;
        if (c0 == '^' || c0 == '.') {
            dataVar = RegexFilter.applyRegex(dataVar, "s/^[\\^\\.]/~./");
            isDirective = true;
        }
        if (isDirective || c0 == '~' || c0 == '$') {
            dataVar = dataVar.substring(1);
            if (this.chunk == null) {
                return null;
            }
            TableData dataStore = this.chunk.get(dataVar);
            int depth = 0;
            while (dataStore != null && depth < 10) {
                if (dataStore instanceof TableData) {
                    return dataStore;
                }
                if (dataStore instanceof String) {
                    return InlineTable.parseTable((String) dataStore);
                }
                if (dataStore instanceof Snippet) {
                    Snippet snippetData = (Snippet) dataStore;
                    if (!snippetData.isSimplePointer()) {
                        return InlineTable.parseTable(snippetData.toString());
                    }
                    dataStore = this.chunk.get(snippetData.getPointer());
                    depth++;
                } else if (dataStore instanceof String[]) {
                    return new SimpleTable((String[]) dataStore);
                } else {
                    if (dataStore instanceof List) {
                        List list = (List) dataStore;
                        if (list.size() <= 0) {
                            return null;
                        }
                        Object a = list.get(0);
                        if (a instanceof String) {
                            return new SimpleTable(list);
                        }
                        if (a instanceof Map) {
                            return new TableOfMaps(list);
                        }
                        return TableOfMaps.boxCollection(list);
                    } else if (dataStore instanceof Object[]) {
                        data = DataCapsuleTable.extractData((Object[]) dataStore);
                        if (data == null) {
                            return TableOfMaps.boxObjectArray((Object[]) dataStore);
                        }
                        return data;
                    } else if (!(dataStore instanceof Map)) {
                        return null;
                    } else {
                        if (dataStore instanceof ObjectDataMap) {
                            Object unwrapped = ((ObjectDataMap) dataStore).unwrap();
                            if (unwrapped instanceof Collection) {
                                data = TableOfMaps.boxCollection((Collection) unwrapped);
                            } else if (unwrapped instanceof Enumeration) {
                                data = TableOfMaps.boxEnumeration((Enumeration) unwrapped);
                            } else if (unwrapped instanceof Iterator) {
                                data = TableOfMaps.boxIterator((Iterator) unwrapped);
                            }
                        }
                        if (data == null) {
                            return new ObjectTable((Map) dataStore);
                        }
                        return data;
                    }
                }
            }
            return null;
        } else if (this.chunk == null) {
            return null;
        } else {
            dataVar = BlockTag.qualifyTemplateRef(origin, dataVar);
            String tableAsString = this.chunk.getTemplateSet().fetch(dataVar);
            if (tableAsString != null) {
                return InlineTable.parseTable(tableAsString);
            }
            return null;
        }
    }

    private void registerOption(String param, String value) {
        if (this.options == null) {
            this.options = new HashMap();
        }
        this.options.put(param, value);
    }

    public void cookLoopToPrinter(Writer out, Chunk context, String origin, boolean isBlock, int depth, TableData data) throws IOException {
        if (data != null && data.hasNext()) {
            int length;
            int i;
            Snippet dividerSnippet = null;
            boolean createArrayTags = false;
            boolean counterTags = false;
            int counterOffset = 0;
            int counterStep = 1;
            String counterTag = null;
            String firstRunTag = null;
            String lastRunTag = null;
            String placeTag = null;
            String str = null;
            String objectValueLabel = null;
            if (this.options != null) {
                if (this.options.containsKey("dividerSnippet")) {
                    dividerSnippet = (Snippet) this.options.get("dividerSnippet");
                } else {
                    if (this.options.containsKey("divider")) {
                        String dividerTemplate = BlockTag.qualifyTemplateRef(origin, (String) this.options.get("divider"));
                        ContentSource templates = context.getTemplateSet();
                        if (templates == null || !templates.provides(dividerTemplate)) {
                            dividerSnippet = Snippet.getSnippet(dividerTemplate);
                        } else {
                            dividerSnippet = templates.getSnippet(dividerTemplate);
                        }
                        this.options.put("dividerSnippet", dividerSnippet);
                    }
                }
                if (this.options.containsKey("array_tags")) {
                    createArrayTags = true;
                }
                if (this.options.containsKey("counter_tags")) {
                    counterTags = true;
                }
                if (this.options.containsKey("counter_tag")) {
                    counterTag = eatTagSymbol((String) this.options.get("counter_tag"));
                    if (counterTag.indexOf(",") > 0) {
                        String[] counterArgs = counterTag.split(",");
                        counterTag = counterArgs[0];
                        try {
                            counterOffset = Integer.parseInt(counterArgs[1]);
                        } catch (NumberFormatException e) {
                        }
                        length = counterArgs.length;
                        if (r0 > 2) {
                            try {
                                counterStep = Integer.parseInt(counterArgs[2]);
                            } catch (NumberFormatException e2) {
                            }
                        }
                    }
                }
                if (this.options.containsKey("first_last")) {
                    String tagNames = (String) this.options.get("first_last");
                    if (tagNames.indexOf(",") > 0) {
                        String[] userFirstLast = tagNames.split(",");
                        firstRunTag = eatTagSymbol(userFirstLast[0]);
                        lastRunTag = eatTagSymbol(userFirstLast[1]);
                        length = userFirstLast.length;
                        if (r0 > 2) {
                            placeTag = eatTagSymbol(userFirstLast[2]);
                        }
                    }
                    if (firstRunTag == null || firstRunTag.length() == 0) {
                        firstRunTag = FIRST_MARKER;
                    }
                    if (lastRunTag == null || lastRunTag.length() == 0) {
                        lastRunTag = LAST_MARKER;
                    }
                    if (placeTag == null || placeTag.length() == 0) {
                        placeTag = PLACE_TAG;
                    }
                }
                if (this.options.containsKey("valname")) {
                    objectValueLabel = (String) this.options.get("valname");
                    if (this.options.containsKey("keyname")) {
                        str = (String) this.options.get("keyname");
                    }
                }
            }
            ChunkFactory factory = context.getChunkFactory();
            if (this.rowX == null) {
                this.rowX = factory == null ? new Chunk() : factory.makeChunk();
                this.rowX.append(this.rowSnippet);
            }
            this.rowX.setLocale(context.getLocale());
            String prefix = null;
            if (this.options != null) {
                if (this.options.containsKey("name")) {
                    prefix = (String) this.options.get("name");
                }
            }
            if (objectValueLabel == null && (data instanceof ObjectTable)) {
                str = "attr";
                objectValueLabel = prefix == null ? prefix : ObjectTable.VALUE;
            }
            String[] columnLabels = data.getColumnLabels();
            if (createArrayTags && columnLabels == null) {
                createArrayTags = false;
            }
            String[] prefixedIndices = null;
            String[] anonIndices = null;
            if (!(prefix == null || columnLabels == null)) {
                String[] prefixedLabels = new String[columnLabels.length];
                for (i = columnLabels.length - 1; i > -1; i--) {
                    prefixedLabels[i] = prefix + "." + columnLabels[i];
                }
                if (createArrayTags) {
                    prefixedIndices = new String[columnLabels.length];
                    for (i = 0; i < prefixedIndices.length; i++) {
                        prefixedIndices[i] = prefix + "[" + i + "]";
                    }
                }
            }
            if (createArrayTags) {
                anonIndices = new String[columnLabels.length];
                for (i = 0; i < anonIndices.length; i++) {
                    anonIndices[i] = "DATA[" + i + "]";
                }
            }
            int counter = 0;
            while (data.hasNext()) {
                if (counterTags) {
                    this.rowX.set(Constants.ZERO, counter);
                    this.rowX.set("1", counter + 1);
                }
                if (counterTag != null) {
                    this.rowX.set(counterTag, (counter * counterStep) + counterOffset);
                }
                if (dividerSnippet != null && counter > 0) {
                    dividerSnippet.render(out, context, depth);
                }
                Map<String, Object> record = data.nextRecord();
                if (objectValueLabel != null) {
                    if (str != null) {
                        this.rowX.setOrDelete(str, record.get(ObjectTable.KEY));
                    }
                    this.rowX.setOrDelete(objectValueLabel, record.get(ObjectTable.VALUE));
                } else if (prefix != null) {
                    this.rowX.set(prefix, (Object) record);
                    if (createArrayTags) {
                        for (i = columnLabels.length - 1; i > -1; i--) {
                            this.rowX.setOrDelete(prefixedIndices[i], record.get(columnLabels[i]));
                        }
                    }
                } else if (columnLabels == null) {
                    for (String key : record.keySet()) {
                        this.rowX.setOrDelete(key, record.get(key));
                    }
                } else {
                    for (i = columnLabels.length - 1; i > -1; i--) {
                        String field = columnLabels[i];
                        Object value = record.get(field);
                        this.rowX.setOrDelete(field, value);
                        if (createArrayTags) {
                            this.rowX.setOrDelete(anonIndices[i], value);
                        }
                    }
                }
                if (!(prefix == null || columnLabels == null)) {
                    length = columnLabels.length;
                    if (r0 == 1 && columnLabels[0].equals(SimpleTable.ANON_ARRAY_LABEL)) {
                        this.rowX.setOrDelete(prefix, record.get(SimpleTable.ANON_ARRAY_LABEL));
                    }
                }
                if (firstRunTag != null) {
                    if (counter == 0) {
                        this.rowX.set(firstRunTag, "TRUE");
                        this.rowX.set(placeTag, firstRunTag);
                        if (prefix != null) {
                            this.rowX.set(prefix + "." + firstRunTag, "TRUE");
                            this.rowX.set(prefix + "." + placeTag, firstRunTag);
                        }
                    } else if (counter == 1) {
                        this.rowX.unset(firstRunTag);
                        this.rowX.set(placeTag, BuildConfig.FLAVOR);
                        if (prefix != null) {
                            this.rowX.unset(prefix + "." + firstRunTag);
                            this.rowX.set(prefix + "." + placeTag, BuildConfig.FLAVOR);
                        }
                    }
                }
                if (!(lastRunTag == null || data.hasNext())) {
                    String place;
                    if (counter == 0) {
                        place = firstRunTag + " " + lastRunTag;
                    } else {
                        place = lastRunTag;
                    }
                    this.rowX.set(lastRunTag, "TRUE");
                    this.rowX.set(placeTag, place);
                    if (prefix != null) {
                        this.rowX.set(prefix + "." + lastRunTag, "TRUE");
                        this.rowX.set(prefix + "." + placeTag, place);
                    }
                }
                this.rowX.render(out, context);
                counter++;
            }
            data.reset();
            this.rowX.resetTags();
        } else if (this.emptySnippet == null) {
            String errMsg = "[Loop error: Empty Table - please " + (isBlock ? "supply onEmpty section in loop block]" : "specify no_data template parameter in loop tag]");
            if (context == null || context.renderErrorsToOutput()) {
                out.append(errMsg);
            }
            if (context != null) {
                context.logError(errMsg);
            }
        } else {
            this.emptySnippet.render(out, context, depth);
        }
    }

    private String eatTagSymbol(String tag) {
        char c0 = '\u0000';
        if (tag == null) {
            return null;
        }
        if (tag.length() > 0) {
            c0 = tag.charAt(0);
        }
        if (c0 == '$' || c0 == '~') {
            return tag.substring(1);
        }
        return tag;
    }

    public boolean hasBody(String openingTag) {
        if (openingTag == null || openingTag.indexOf("template=") >= 0) {
            return false;
        }
        return true;
    }

    public static String getAttribute(String attr, String toScan) {
        if (toScan == null) {
            return null;
        }
        int spacePos = toScan.indexOf(32);
        if (spacePos < 0) {
            return null;
        }
        String attrs = toScan.substring(spacePos + 1);
        int attrPos = attrs.indexOf(attr);
        if (attrPos < 0) {
            return null;
        }
        int begQuotePos = attrs.indexOf(34, attrs.indexOf(61, attr.length() + attrPos));
        if (begQuotePos < 0) {
            return null;
        }
        int endQuotePos = begQuotePos + 1;
        do {
            endQuotePos = attrs.indexOf(34, endQuotePos);
            if (endQuotePos >= 0) {
                if (attrs.charAt(endQuotePos - 1) == Letters.BACKSLASH) {
                    endQuotePos++;
                }
                if (endQuotePos >= attrs.length()) {
                    break;
                }
            } else {
                return null;
            }
        } while (attrs.charAt(endQuotePos) != Letters.QUOTE);
        if (endQuotePos < attrs.length()) {
            return attrs.substring(begQuotePos + 1, endQuotePos);
        }
        return null;
    }

    public String getBlockStartMarker() {
        return "loop";
    }

    public String getBlockEndMarker() {
        return "/loop";
    }

    public boolean doSmartTrimAroundBlock() {
        return true;
    }

    private void smartTrim(List<SnippetPart> subParts) {
        smartTrimSnippetParts(subParts, isTrimAll());
    }

    public static void smartTrimSnippetParts(List<SnippetPart> subParts, boolean isTrimAll) {
        if (subParts != null && subParts.size() > 0) {
            SnippetPart firstPart = (SnippetPart) subParts.get(0);
            if (firstPart.isLiteral()) {
                firstPart.setText(isTrimAll ? trimLeft(firstPart.getText()) : smartTrimString(firstPart.getText(), true, false));
            }
            if (isTrimAll) {
                SnippetPart lastPart = (SnippetPart) subParts.get(subParts.size() - 1);
                if (lastPart.isLiteral()) {
                    lastPart.setText(trimRight(lastPart.getText()));
                }
            }
        }
    }

    private static String trimLeft(String x) {
        if (x == null) {
            return null;
        }
        int i = 0;
        char c = x.charAt(0);
        while (true) {
            if (c != '\n' && c != Letters.SPACE && c != Letters.CR && c != '\t') {
                break;
            }
            i++;
            if (i == x.length()) {
                break;
            }
            c = x.charAt(i);
        }
        return i != 0 ? x.substring(i) : x;
    }

    private static String trimRight(String x) {
        if (x == null) {
            return null;
        }
        int i = x.length() - 1;
        char c = x.charAt(i);
        while (true) {
            if (c != '\n' && c != Letters.SPACE && c != Letters.CR && c != '\t') {
                break;
            }
            i--;
            if (i == -1) {
                break;
            }
            c = x.charAt(i);
        }
        i++;
        return i < x.length() ? x.substring(0, i) : x;
    }

    private boolean isTrimAll() {
        String trimOpt = this.options != null ? (String) this.options.get("trim") : null;
        if (trimOpt == null || !trimOpt.equals("all")) {
            return false;
        }
        return true;
    }

    static {
        UNIVERSAL_LF = Pattern.compile("\n|\r\n|\r\r");
    }

    private static String smartTrimString(String x, boolean ignoreAll, boolean isTrimAll) {
        if (!ignoreAll && isTrimAll) {
            return x.trim();
        }
        Matcher m = UNIVERSAL_LF.matcher(x);
        if (m.find() && x.substring(0, m.start()).trim().length() == 0) {
            return x.substring(m.end());
        }
        return !ignoreAll ? x.trim() : x;
    }

    private void initBody(Snippet body) {
        int i;
        String trimOpt;
        List<SnippetPart> bodyParts = body.getParts();
        int eMarker = -1;
        int dMarker = -1;
        int eMarkerEnd = bodyParts.size();
        int dMarkerEnd = bodyParts.size();
        for (i = bodyParts.size() - 1; i >= 0; i--) {
            SnippetPart part = (SnippetPart) bodyParts.get(i);
            if (part.isTag()) {
                String tagText = ((SnippetTag) part).getTag();
                if (tagText.equals(".onEmpty")) {
                    eMarker = i;
                } else if (tagText.equals(".divider")) {
                    dMarker = i;
                } else if (tagText.equals("./divider")) {
                    dMarkerEnd = i;
                } else if (tagText.equals("./onEmpty")) {
                    eMarkerEnd = i;
                }
            }
        }
        boolean doTrim = true;
        if (this.options == null) {
            trimOpt = null;
        } else {
            trimOpt = (String) this.options.get("trim");
        }
        if (trimOpt != null && trimOpt.equalsIgnoreCase(Constants.FALSE)) {
            doTrim = false;
        }
        int bodyEnd = -1;
        if (eMarker > -1 && dMarker > -1) {
            if (eMarker > dMarker) {
                bodyEnd = dMarker;
                dMarkerEnd = Math.min(eMarker, dMarkerEnd);
            } else {
                bodyEnd = eMarker;
                eMarkerEnd = Math.min(dMarker, eMarkerEnd);
            }
            this.emptySnippet = extractParts(bodyParts, eMarker + 1, eMarkerEnd, doTrim);
            this.dividerSnippet = extractParts(bodyParts, dMarker + 1, dMarkerEnd, doTrim);
        } else if (eMarker > -1) {
            bodyEnd = eMarker;
            this.emptySnippet = extractParts(bodyParts, eMarker + 1, eMarkerEnd, doTrim);
            this.dividerSnippet = null;
        } else if (dMarker > -1) {
            bodyEnd = dMarker;
            this.emptySnippet = null;
            this.dividerSnippet = extractParts(bodyParts, dMarker + 1, dMarkerEnd, doTrim);
        } else {
            this.emptySnippet = null;
            this.dividerSnippet = null;
        }
        if (bodyEnd > -1) {
            for (i = bodyParts.size() - 1; i >= bodyEnd; i--) {
                bodyParts.remove(i);
            }
        }
        if (doTrim) {
            smartTrim(bodyParts);
        }
        this.rowSnippet = body;
    }

    private Snippet extractParts(List<SnippetPart> parts, int a, int b, boolean doTrim) {
        List subParts = new ArrayList();
        for (int i = a; i < b; i++) {
            subParts.add(parts.get(i));
        }
        if (doTrim) {
            smartTrim(subParts);
        }
        return new Snippet(subParts);
    }

    public void renderBlock(Writer out, Chunk context, String origin, int depth) throws IOException {
        if (!(this.dividerSnippet == null || this.options.containsKey("dividerSnippet"))) {
            this.options.put("dividerSnippet", this.dividerSnippet);
        }
        this.chunk = context;
        TableData data = null;
        if (this.options != null) {
            data = fetchData((String) this.options.get("data"), origin);
        }
        cookLoopToPrinter(out, context, origin, true, depth, data);
    }
}
