package com.x5.template;

import com.example.duy.calculator.math_eval.Constants;
import com.x5.template.filters.Calc;
import com.x5.util.DataCapsule;
import com.x5.util.DataCapsuleReader;
import com.x5.util.ObjectDataMap;
import com.x5.util.TableData;
import io.github.kexanie.library.BuildConfig;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;

public class Chunk implements Map<String, Object> {
    public static final int DEPTH_LIMIT = 17;
    public static final int HASH_THRESH = 8;
    private static final Pattern INCLUDEIF_PATTERN;
    private static final SimpleDateFormat LOG_DATE;
    private static final String TRUE = "TRUE";
    public static final String VERSION = "3.0.1";
    private Hashtable<String, ContentSource> altSources;
    private ChunkFactory chunkFactory;
    private Vector<Vector<Chunk>> contextStack;
    private PrintStream errLog;
    private String[] firstTags;
    private Object[] firstValues;
    private ChunkLocale locale;
    private String localeCode;
    private ContentSource macroLibrary;
    private boolean renderErrs;
    private int tagCount;
    protected String tagEnd;
    protected String tagStart;
    private Hashtable<String, Object> tags;
    protected Vector<Snippet> template;
    private String templateOrigin;
    protected Snippet templateRoot;

    public Chunk() {
        this.templateRoot = null;
        this.templateOrigin = null;
        this.firstTags = new String[HASH_THRESH];
        this.firstValues = new Object[HASH_THRESH];
        this.tagCount = 0;
        this.template = null;
        this.tags = null;
        this.tagStart = TemplateSet.DEFAULT_TAG_START;
        this.tagEnd = TemplateSet.DEFAULT_TAG_END;
        this.contextStack = null;
        this.macroLibrary = null;
        this.chunkFactory = null;
        this.localeCode = null;
        this.locale = null;
        this.renderErrs = true;
        this.errLog = null;
        this.altSources = null;
    }

    void setMacroLibrary(ContentSource repository, ChunkFactory factory) {
        this.macroLibrary = repository;
        if (this.altSources != null) {
            addProtocol(repository);
        }
        this.chunkFactory = factory;
    }

    public ContentSource getTemplateSet() {
        return this.macroLibrary;
    }

    public void setChunkFactory(ChunkFactory factory) {
        this.chunkFactory = factory;
    }

    public ChunkFactory getChunkFactory() {
        return this.chunkFactory;
    }

    public void append(Snippet toAdd) {
        if (this.templateRoot == null && this.template == null) {
            this.templateRoot = toAdd;
        } else if (this.template == null) {
            this.template = new Vector();
            this.template.addElement(this.templateRoot);
            this.template.addElement(toAdd);
        } else {
            this.template.addElement(toAdd);
        }
    }

    public void append(String toAdd) {
        if (toAdd != null) {
            append(Snippet.getSnippet(toAdd));
        }
    }

    public void append(Chunk toAdd) {
        if (this.template == null) {
            this.template = new Vector();
            if (this.templateRoot != null) {
                this.template.addElement(this.templateRoot);
            }
        }
        String chunkKey = ";CHUNK_" + toAdd.hashCode();
        set(chunkKey, toAdd);
        this.template.addElement(Snippet.getSnippet(makeTag(chunkKey)));
    }

    public void set(String tagName, String tagValue) {
        set(tagName, tagValue, BuildConfig.FLAVOR);
    }

    public void set(String tagName, Chunk tagValue) {
        set(tagName, tagValue, BuildConfig.FLAVOR);
    }

    public void set(String tagName, Object tagValue) {
        set(tagName, tagValue, null);
    }

    public void setOrDelete(String tagName, Object tagValue) {
        if (tagValue != null) {
            set(tagName, tagValue, null);
        } else if (containsKey(tagName)) {
            this.tags.remove(tagName);
        }
    }

    public void setLiteral(String tagName, String literalValue) {
        set(tagName, Snippet.makeLiteralSnippet(literalValue));
    }

    public void set(String tagName, Object tagValue, String ifNull) {
        if (tagName != null) {
            if (tagValue != null) {
                tagValue = coercePrimitivesToStringAndBoxAliens(tagValue);
            }
            if (tagValue == null) {
                if (ifNull == null) {
                    tagValue = "NULL";
                } else {
                    String tagValue2 = ifNull;
                }
            }
            if (this.tags != null) {
                this.tags.put(tagName, tagValue);
                return;
            }
            for (int i = 0; i < this.tagCount; i++) {
                if (this.firstTags[i].equals(tagName)) {
                    this.firstValues[i] = tagValue;
                    return;
                }
            }
            if (this.tagCount >= HASH_THRESH) {
                this.tags = new Hashtable(16);
                copyToHashtable();
                this.tags.put(tagName, tagValue);
                return;
            }
            this.firstTags[this.tagCount] = tagName;
            this.firstValues[this.tagCount] = tagValue;
            this.tagCount++;
        }
    }

    public void setToBean(String tagName, Object bean) {
        setToBean(tagName, bean, null);
    }

    public void setToBean(String tagName, Object bean, String ifNull) {
        set(tagName, ObjectDataMap.wrapBean(bean), ifNull);
    }

    public void set(String tagName) {
        set(tagName, TRUE);
    }

    public void set(String tagName, int tagValue) {
        set(tagName, Integer.toString(tagValue));
    }

    public void set(String tagName, char tagValue) {
        set(tagName, Character.toString(tagValue));
    }

    public void set(String tagName, long tagValue) {
        set(tagName, Long.toString(tagValue));
    }

    public void set(String tagName, StringBuilder tagValue) {
        if (tagValue != null) {
            set(tagName, tagValue.toString());
        }
    }

    public void set(String tagName, StringBuffer tagValue) {
        if (tagValue != null) {
            set(tagName, tagValue.toString());
        }
    }

    public void unset(String tagName) {
        if (tagName != null) {
            setOrDelete(tagName, null);
        }
    }

    public boolean hasValue(String tagName) {
        if (tagName == null) {
            return false;
        }
        if (this.tags != null) {
            return this.tags.containsKey(tagName);
        }
        for (int i = 0; i < this.tagCount; i++) {
            if (this.firstTags[i].equals(tagName)) {
                return true;
            }
        }
        return false;
    }

    public boolean stillNeeds(String tagName) {
        if (tagName == null || hasValue(tagName)) {
            return false;
        }
        return true;
    }

    public String toString() {
        Writer out = new StringWriter();
        try {
            render(out);
            out.flush();
            return out.toString();
        } catch (IOException e) {
            return e.getLocalizedMessage();
        }
    }

    public String toString(Chunk context) {
        StringWriter out = new StringWriter();
        try {
            render(out, context);
            out.flush();
            return out.toString();
        } catch (IOException e) {
            return e.getLocalizedMessage();
        }
    }

    public void render(PrintStream out) throws IOException {
        Writer writer = new PrintWriter(out);
        render(writer);
        writer.flush();
    }

    public void render(Writer out) throws IOException {
        explodeForParentToPrinter(out, null);
    }

    public void render(Writer out, Chunk context) throws IOException {
        explodeForParentToPrinter(out, context.prepareParentContext());
    }

    private void pushContextStack(Vector<Chunk> parentContext) {
        if (this.contextStack == null) {
            this.contextStack = new Vector();
        }
        this.contextStack.insertElementAt(parentContext, 0);
    }

    private void popContextStack() {
        if (this.contextStack != null && this.contextStack.size() != 0) {
            this.contextStack.removeElementAt(0);
        }
    }

    private void explodeForParentToPrinter(Writer out, Vector<Chunk> ancestors) throws IOException {
        if (this.template != null || this.templateRoot != null) {
            if (ancestors != null) {
                synchronized (this) {
                    pushContextStack(ancestors);
                    renderForParentToPrinter(out);
                    popContextStack();
                }
                return;
            }
            renderForParentToPrinter(out);
        }
    }

    private void renderForParentToPrinter(Writer out) throws IOException {
        if (this.template == null) {
            explodeToPrinter(out, this.templateRoot, 1);
            return;
        }
        if (this.template.size() > 1) {
            this.template = mergeTemplateParts();
        }
        for (int i = 0; i < this.template.size(); i++) {
            explodeToPrinter(out, (Snippet) this.template.elementAt(i), 1);
        }
    }

    private Vector<Snippet> mergeTemplateParts() {
        try {
            Snippet merged = Snippet.consolidateSnippets(this.template);
            Vector<Snippet> newTemplate = new Vector();
            newTemplate.add(merged);
            return newTemplate;
        } catch (EndOfSnippetException e) {
            return this.template;
        }
    }

    void explodeToPrinter(Writer out, Object obj, int depth) throws IOException {
        String err;
        if (depth >= DEPTH_LIMIT) {
            err = handleError("[**ERR** max template recursions: 17]");
            if (err != null) {
                out.append(err);
            }
        } else if (obj instanceof Snippet) {
            ((Snippet) obj).render(out, this, depth);
        } else if (obj instanceof String) {
            explodeToPrinter(out, Snippet.getSnippet((String) obj), depth);
        } else if (obj instanceof Chunk) {
            ((Chunk) obj).explodeForParentToPrinter(out, prepareParentContext());
        } else if (obj instanceof DataCapsule[]) {
            err = handleError("[LIST(" + DataCapsuleReader.getReader((DataCapsule[]) obj).getDataClassName() + ") - Use a loop construct to display list data.]");
            if (err != null) {
                out.append(err);
            }
        } else if (obj instanceof String[]) {
            err = handleError("[LIST(java.lang.String) - Use a loop construct to display list data, or pipe to join().]");
            if (err != null) {
                out.append(err);
            }
        } else if (obj instanceof List) {
            err = handleError("[LIST - Use a loop construct to display list data, or pipe to join().]");
            if (err != null) {
                out.append(err);
            }
        } else {
            explodeToPrinter(out, ObjectDataMap.getAsString(obj), depth);
        }
    }

    private Vector<Chunk> prepareParentContext() {
        if (this.contextStack == null) {
            Vector<Chunk> parentContext = new Vector();
            parentContext.add(this);
            return parentContext;
        }
        parentContext = (Vector) ((Vector) this.contextStack.firstElement()).clone();
        parentContext.insertElementAt(this, 0);
        return parentContext;
    }

    private Vector<Chunk> getCurrentParentContext() {
        if (this.contextStack == null || this.contextStack.size() == 0) {
            return null;
        }
        return (Vector) this.contextStack.firstElement();
    }

    public Object getTagValue(String tagName) {
        Snippet x;
        Snippet s;
        if (this.tags != null) {
            x = this.tags.get(tagName);
            if (x instanceof String) {
                s = Snippet.getSnippet((String) x);
                this.tags.put(tagName, s);
                return s.isSimple() ? s.toString() : s;
            } else if (!(x instanceof Snippet)) {
                return x;
            } else {
                s = x;
                if (s.isSimple()) {
                    return s.toString();
                }
                return s;
            }
        }
        for (int i = 0; i < this.tagCount; i++) {
            if (this.firstTags[i].equals(tagName)) {
                x = this.firstValues[i];
                if (x instanceof String) {
                    s = Snippet.getSnippet((String) x);
                    this.firstValues[i] = s;
                    if (s.isSimple()) {
                        return s.toString();
                    }
                    return s;
                } else if (!(x instanceof Snippet)) {
                    return x;
                } else {
                    s = x;
                    if (s.isSimple()) {
                        return s.toString();
                    }
                    return s;
                }
            }
        }
        return null;
    }

    public void addProtocol(ContentSource src) {
        if (this.altSources == null) {
            this.altSources = new Hashtable();
            if (this.macroLibrary != null) {
                this.altSources.put(this.macroLibrary.getProtocol(), this.macroLibrary);
            }
        }
        this.altSources.put(src.getProtocol(), src);
    }

    private Object altFetch(String tagName, int depth) {
        return altFetch(tagName, depth, false);
    }

    static {
        INCLUDEIF_PATTERN = Pattern.compile("^\\.include(If|\\.\\()");
        LOG_DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zZ ");
    }

    private Object altFetch(String tagName, int depth, boolean ignoreParentContext) {
        String tagValue = null;
        if (tagName.startsWith(".calc(")) {
            try {
                return Calc.evalCalc(tagName, this);
            } catch (NoClassDefFoundError e) {
                return handleError("[ERROR: jeplite jar missing from classpath! .calc command requires jeplite library]");
            }
        }
        if (tagName.startsWith(".version")) {
            return VERSION;
        }
        if (tagName.startsWith(".loop")) {
            return LoopTag.expandLoop(tagName, this, this.templateOrigin, depth);
        }
        if (tagName.startsWith(".tagStack")) {
            String format = "text";
            if (tagName.contains("html")) {
                format = "html";
            }
            return formatTagStack(format);
        } else if (this.altSources == null && this.macroLibrary == null && getCurrentParentContext() == null) {
            return null;
        } else {
            if (INCLUDEIF_PATTERN.matcher(tagName).find()) {
                return Filter.translateIncludeIf(tagName, this.tagStart, this.tagEnd, this);
            }
            int delimPos = tagName.indexOf(".", 1);
            int spacePos = tagName.indexOf(" ", 1);
            if (delimPos >= 0 || spacePos >= 0) {
                if (spacePos > 0 && (delimPos < 0 || spacePos < delimPos)) {
                    delimPos = spacePos;
                }
                String srcName = tagName.substring(1, delimPos);
                String cleanItemName = tagName.substring(delimPos + 1).replaceAll("[\\|:].*$", BuildConfig.FLAVOR);
                ContentSource fetcher = null;
                if (this.altSources != null) {
                    fetcher = (ContentSource) this.altSources.get(srcName);
                } else if (this.macroLibrary != null) {
                    if (srcName.equals(this.macroLibrary.getProtocol())) {
                        fetcher = this.macroLibrary;
                    }
                }
                if (fetcher != null) {
                    if (fetcher instanceof Theme) {
                        Snippet s = ((Theme) fetcher).getSnippet(BlockTag.qualifyTemplateRef(this.templateOrigin, cleanItemName));
                        if (s != null) {
                            return s;
                        }
                    }
                    tagValue = fetcher.fetch(cleanItemName);
                }
                if (tagValue == null && !ignoreParentContext) {
                    Vector<Chunk> parentContext = getCurrentParentContext();
                    if (parentContext != null) {
                        Iterator i$ = parentContext.iterator();
                        while (i$.hasNext()) {
                            String x = ((Chunk) i$.next()).altFetch(tagName, depth, true);
                            if (x != null) {
                                return x;
                            }
                        }
                    }
                }
                return tagValue;
            }
            if (tagName.startsWith("./")) {
                return null;
            }
            return handleError("[CHUNK_ERR: malformed content reference: '" + tagName + "' -- missing argument]");
        }
    }

    private String resolveBackticks(String lookupName, int depth) {
        int backtickA = lookupName.indexOf(96);
        if (backtickA < 0) {
            return lookupName;
        }
        int backtickB = lookupName.indexOf(96, backtickA + 1);
        if (backtickB < 0) {
            return lookupName;
        }
        String embeddedTag = lookupName.substring(backtickA + 2, backtickB);
        char typeChar = lookupName.charAt(backtickA + 1);
        if (typeChar == Constants.POWER_UNICODE || typeChar == '.') {
            embeddedTag = '.' + embeddedTag;
        } else if (!(typeChar == '~' || typeChar == '$')) {
            return lookupName;
        }
        Object backtickExprValue = resolveTagValue(embeddedTag, depth);
        return backtickExprValue != null ? resolveBackticks(lookupName.substring(0, backtickA) + backtickExprValue + lookupName.substring(backtickB + 1), depth) : lookupName;
    }

    protected Object resolveTagValue(SnippetTag tag, int depth, String origin) {
        if (origin == null) {
            return _resolveTagValue(tag, depth, false);
        }
        this.templateOrigin = origin;
        Object value = _resolveTagValue(tag, depth, false);
        this.templateOrigin = null;
        return value;
    }

    protected Object resolveTagValue(SnippetTag tag, int depth) {
        return _resolveTagValue(tag, depth, false);
    }

    protected Object _resolveTagValue(SnippetTag tag, int depth, boolean ignoreParentContext) {
        String[] path = tag.getPath();
        String segmentName = path[0];
        if (segmentName.indexOf(96) > -1) {
            segmentName = resolveBackticks(segmentName, depth);
        }
        Object tagValue = null;
        if (segmentName.charAt(0) != '.') {
            if (!hasValue(segmentName)) {
                if (!ignoreParentContext) {
                    Vector<Chunk> parentContext = getCurrentParentContext();
                    if (parentContext != null) {
                        Iterator i$ = parentContext.iterator();
                        while (i$.hasNext()) {
                            tagValue = ((Chunk) i$.next()).getTagValue(segmentName);
                            if (tagValue != null) {
                                break;
                            }
                        }
                    }
                }
                return null;
            }
            tagValue = getTagValue(segmentName);
        } else {
            tagValue = altFetch(segmentName, depth);
        }
        int segment = 0 + 1;
        Object obj = tagValue;
        while (path.length > segment && obj != null) {
            if (obj instanceof Map) {
                segmentName = resolveBackticks(path[segment], depth);
                tagValue = ((Map) obj).get(segmentName);
                segment++;
                if (tagValue == null && path.length == segment) {
                    tagValue = getTagValue(path[segment - 2] + "." + segmentName);
                }
                obj = tagValue;
            } else {
                obj = null;
            }
        }
        if (!(obj == null || (obj instanceof String))) {
            obj = coercePrimitivesToStringAndBoxAliens(obj);
        }
        String filters = tag.getFilters();
        if (obj == null) {
            Object tagDefault = tag.getDefaultValue();
            if (filters != null && (tag.applyFiltersFirst() || tagDefault == null)) {
                Object filteredNull = Filter.applyFilter(this, filters, null);
                if (filteredNull != null) {
                    return filteredNull;
                }
            }
            if (tag.applyFiltersFirst() || filters == null) {
                return tagDefault;
            }
            return Filter.applyFilter(this, filters, tagDefault);
        } else if (filters == null) {
            return obj;
        } else {
            Object filteredVal = Filter.applyFilter(this, filters, obj);
            return (filteredVal == null && tag.applyFiltersFirst()) ? tag.getDefaultValue() : filteredVal;
        }
    }

    private Object coercePrimitivesToStringAndBoxAliens(Object o) {
        if (o == null) {
            return o;
        }
        if (o instanceof Boolean) {
            return ((Boolean) o).booleanValue() ? TRUE : null;
        } else if (o == null || !ObjectDataMap.isWrapperType(o.getClass())) {
            return boxIfAlienObject(o);
        } else {
            return o.toString();
        }
    }

    private Object boxIfAlienObject(Object o) {
        return (o == null || (o instanceof Chunk) || (o instanceof TableData) || (o instanceof Map) || (o instanceof String) || (o instanceof Snippet) || (o instanceof List) || (o instanceof Object[])) ? o : new ObjectDataMap(o);
    }

    protected Object resolveTagValue(String tagName, int depth) {
        return _resolveTagValue(SnippetTag.parseTag(tagName), depth, false);
    }

    public void resetTags() {
        if (this.tags != null) {
            this.tags.clear();
        } else {
            this.tagCount = 0;
        }
    }

    public void clear() {
        resetTags();
    }

    public void resetTemplate() {
        if (this.template == null) {
            this.templateRoot = null;
        } else {
            this.template.clear();
        }
    }

    public boolean containsKey(Object key) {
        if (this.tags == null) {
            this.tags = new Hashtable();
            copyToHashtable();
        }
        return this.tags.containsKey(key);
    }

    public boolean containsValue(Object value) {
        if (this.tags == null) {
            this.tags = new Hashtable();
            copyToHashtable();
        }
        return this.tags.containsValue(value);
    }

    public Set<Entry<String, Object>> entrySet() {
        if (this.tags == null) {
            this.tags = new Hashtable();
            copyToHashtable();
        }
        return this.tags.entrySet();
    }

    public boolean equals(Object o) {
        if (this.tags == null) {
            this.tags = new Hashtable();
            copyToHashtable();
        }
        return this.tags.equals(o);
    }

    public Object get(Object key) {
        return resolveTagValue((String) key, 1);
    }

    public int hashCode() {
        if (this.tags == null) {
            this.tags = new Hashtable();
            copyToHashtable();
        }
        return this.tags.hashCode();
    }

    public boolean isEmpty() {
        if (this.tags == null) {
            return this.tagCount == 0;
        } else {
            return this.tags.isEmpty();
        }
    }

    public Set<String> keySet() {
        if (this.tags == null) {
            this.tags = new Hashtable();
            copyToHashtable();
        }
        return this.tags.keySet();
    }

    public Object put(String key, Object value) {
        Object x = getTagValue(key);
        set(key, value, BuildConfig.FLAVOR);
        return x;
    }

    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    public void putAll(Map t) {
        if (t != null && t.size() >= 0) {
            for (String tagName : t.keySet()) {
                set(tagName, t.get(tagName), BuildConfig.FLAVOR);
            }
        }
    }

    public int size() {
        if (this.tags != null) {
            return this.tags.size();
        }
        return this.tagCount;
    }

    public Collection<Object> values() {
        if (this.tags == null) {
            this.tags = new Hashtable();
            copyToHashtable();
        }
        return this.tags.values();
    }

    public void setMultiple(Map<String, Object> rules) {
        if (rules != null && rules.size() > 0) {
            for (String tagName : rules.keySet()) {
                setOrDelete(tagName, rules.get(tagName));
            }
        }
    }

    public void setMultiple(Chunk copyFrom) {
        if (copyFrom != null) {
            setMultiple(copyFrom.getTagsTable());
        }
    }

    public Map<String, Object> getTagsTable() {
        if (this.tags != null) {
            return this.tags;
        }
        if (this.tagCount <= 0) {
            return null;
        }
        copyToHashtable();
        return this.tags;
    }

    private void copyToHashtable() {
        if (this.tags == null) {
            this.tags = new Hashtable(this.tagCount * 2);
        }
        for (int i = 0; i < this.tagCount; i++) {
            this.tags.put(this.firstTags[i], this.firstValues[i]);
        }
    }

    private String formatTagStack(String format) {
        StringBuilder stack = new StringBuilder();
        String lineFeed = "\n";
        String indent = "  ";
        if (format.equals("html")) {
            lineFeed = "<br/>\n";
            indent = "&nbsp;&nbsp;";
        }
        stack.append("Available tags:");
        stack.append(lineFeed);
        outputTags(stack, lineFeed, indent, 0);
        int indentLevel = 0 + 1;
        Vector<Chunk> parentContext = getCurrentParentContext();
        if (parentContext != null) {
            Iterator i$ = parentContext.iterator();
            while (i$.hasNext()) {
                ((Chunk) i$.next()).outputTags(stack, lineFeed, indent, indentLevel);
                indentLevel++;
            }
        }
        return stack.toString();
    }

    private void outputTags(StringBuilder output, String lf, String ind, int indent) {
        ArrayList<String> list = new ArrayList();
        if (this.tags == null) {
            for (int i = 0; i < this.tagCount; i++) {
                list.add(this.firstTags[i]);
            }
        } else {
            list.addAll(this.tags.keySet());
        }
        Collections.sort(list);
        Iterator i$ = list.iterator();
        while (i$.hasNext()) {
            String tag = (String) i$.next();
            for (int x = 0; x < indent; x++) {
                output.append(ind);
            }
            output.append('$');
            output.append(tag);
            output.append(lf);
        }
    }

    public void addData(DataCapsule smartObj) {
        DataCapsuleReader reader = DataCapsuleReader.getReader(smartObj);
        String[] tags = reader.getColumnLabels(null);
        Object[] data = reader.extractData(smartObj);
        for (int i = 0; i < tags.length; i++) {
            Object val = data[i];
            if (val == null || (val instanceof String) || (val instanceof DataCapsule)) {
                setOrDelete(tags[i], val);
            } else {
                set(tags[i], val.toString());
            }
        }
    }

    public void addData(DataCapsule smartObj, String altPrefix) {
        if (smartObj != null) {
            if (altPrefix == null) {
                addData(smartObj);
            } else {
                set(altPrefix, (Object) smartObj);
            }
        }
    }

    public String makeTag(String tagName) {
        return this.tagStart + tagName + this.tagEnd;
    }

    public void setErrorHandling(boolean renderErrs, PrintStream err) {
        this.renderErrs = renderErrs;
        this.errLog = err;
    }

    boolean renderErrorsToOutput() {
        return this.renderErrs;
    }

    private String handleError(String errMsg) {
        logError(errMsg);
        return this.renderErrs ? errMsg : null;
    }

    void logError(String errMsg) {
        logChunkError(this.errLog, errMsg);
    }

    static void logChunkError(PrintStream log, String errMsg) {
        if (log != null) {
            log.print(LOG_DATE.format(new Date()));
            log.println(errMsg);
        }
    }

    public void setLocale(String localeCode) {
        this.localeCode = localeCode;
    }

    public void setLocale(Locale javaLocale) {
        if (javaLocale == null) {
            this.localeCode = null;
        } else {
            setLocale(javaLocale.toString().replace(Constants.MINUS_UNICODE, '_'));
        }
    }

    public void setLocale(ChunkLocale chunkLocale) {
        if (chunkLocale == null) {
            this.localeCode = null;
        } else {
            setLocale(chunkLocale.toString());
        }
    }

    public ChunkLocale getLocale() {
        if (this.localeCode == null) {
            return null;
        }
        if (this.locale == null) {
            this.locale = ChunkLocale.getInstance(this.localeCode, this);
        }
        return this.locale;
    }

    public static String findAndReplace(String toSearch, String find, String replace) {
        if (find == null || toSearch == null || toSearch.indexOf(find) == -1) {
            return toSearch;
        }
        if (replace == null) {
            replace = BuildConfig.FLAVOR;
        }
        int marker = 0;
        int findLen = find.length();
        StringBuilder sb = new StringBuilder();
        while (true) {
            int findPos = toSearch.indexOf(find, marker);
            if (findPos > -1) {
                sb.append(toSearch.substring(marker, findPos));
                sb.append(replace);
                marker = findPos + findLen;
            } else {
                sb.append(toSearch.substring(marker));
                return sb.toString();
            }
        }
    }

    public String getTemplateOrigin() {
        return this.templateOrigin;
    }
}
