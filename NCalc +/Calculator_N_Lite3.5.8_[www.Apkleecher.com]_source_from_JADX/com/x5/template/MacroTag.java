package com.x5.template;

import com.example.duy.calculator.math_eval.Constants;
import com.x5.util.LiteXml;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.ContainerFactory;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public class MacroTag extends BlockTag {
    private static final int ARG_START;
    private static final String FMT_JSON_LAX = "json";
    private static final String FMT_JSON_STRICT = "json-strict";
    private static final String FMT_ORIGINAL = "original";
    private static final String FMT_XML = "xml";
    public static final String MACRO_END_MARKER = "/exec";
    public static final String MACRO_MARKER = "exec";
    private String dataFormat;
    private List<String> inputErrs;
    private Map<String, Object> macroDefs;
    private Snippet template;
    private String templateRef;

    static {
        ARG_START = MACRO_MARKER.length() + 2;
    }

    public MacroTag() {
        this.dataFormat = FMT_ORIGINAL;
        this.inputErrs = null;
    }

    public MacroTag(String tagName, Snippet body) {
        this.dataFormat = FMT_ORIGINAL;
        this.inputErrs = null;
        if (tagName.length() > ARG_START) {
            this.templateRef = tagName.substring(ARG_START).trim();
            int spacePos = this.templateRef.indexOf(32);
            if (spacePos > 0) {
                this.dataFormat = this.templateRef.substring(spacePos + 1).toLowerCase();
                if (this.dataFormat.charAt(ARG_START) == '@') {
                    this.dataFormat = this.dataFormat.substring(1);
                }
                this.templateRef = this.templateRef.substring(ARG_START, spacePos);
            }
            if (this.templateRef.charAt(ARG_START) == '@') {
                if (!this.templateRef.startsWith("@inline") && spacePos < 0) {
                    this.dataFormat = this.templateRef.substring(1).toLowerCase();
                }
                this.templateRef = null;
            }
        }
        Snippet bodyDouble = body.copy();
        if (this.templateRef == null) {
            parseInlineTemplate(bodyDouble);
        }
        parseDefs(bodyDouble);
    }

    private void parseInlineTemplate(Snippet body) {
        List<SnippetPart> parts = body.getParts();
        int bodyEnd = parts.size();
        for (int i = bodyEnd - 1; i >= 0; i--) {
            SnippetPart part = (SnippetPart) parts.get(i);
            if (part.isTag()) {
                SnippetTag tag = (SnippetTag) part;
                if (tag.getTag().equals("./body")) {
                    bodyEnd = i;
                } else if (tag.getTag().startsWith(".data")) {
                    bodyEnd = i;
                } else if (tag.getTag().equals(".body")) {
                    Snippet inlineSnippet = new Snippet(parts, i + 1, bodyEnd);
                    inlineSnippet.setOrigin(body.getOrigin());
                    LoopTag.smartTrimSnippetParts(inlineSnippet.getParts(), false);
                    this.template = inlineSnippet;
                    for (int j = bodyEnd - 1; j >= i; j--) {
                        parts.remove(j);
                    }
                    return;
                }
            }
        }
    }

    private void parseDefs(Snippet body) {
        body = stripCasing(body);
        if (this.dataFormat.equals(FMT_ORIGINAL)) {
            parseDefsOriginal(body);
        } else if (this.dataFormat.equals(FMT_JSON_STRICT)) {
            parseDefsJsonStrict(body);
        } else if (this.dataFormat.equals(FMT_JSON_LAX)) {
            parseDefsJsonLax(body);
        } else if (this.dataFormat.equals(FMT_XML)) {
            parseDefsXML(body);
        }
    }

    private Snippet stripCasing(Snippet body) {
        List<SnippetPart> parts = body.getParts();
        if (parts == null) {
            return body;
        }
        int i;
        int dataStart = -1;
        int dataEnd = parts.size();
        for (i = ARG_START; i < dataEnd; i++) {
            SnippetPart part = (SnippetPart) parts.get(i);
            if (part.isTag()) {
                String tagMeat = ((SnippetTag) part).getTag();
                if (tagMeat.startsWith(".data")) {
                    parseDataFormat(tagMeat);
                    dataStart = i;
                } else if (tagMeat.equals("./data")) {
                    dataEnd = i;
                }
            }
        }
        if (dataStart == -1) {
            return body;
        }
        Snippet dataSnippet = new Snippet(parts, dataStart + 1, dataEnd);
        if (this.templateRef == null && this.template == null) {
            LoopTag.smartTrimSnippetParts(parts.subList(ARG_START, dataStart), false);
            if (dataEnd < parts.size()) {
                LoopTag.smartTrimSnippetParts(parts.subList(dataEnd + 1, parts.size()), false);
                parts.remove(dataEnd);
            }
            for (i = dataEnd - 1; i >= dataStart; i--) {
                parts.remove(i);
            }
            this.template = body;
        }
        return dataSnippet;
    }

    private void parseDataFormat(String dataTag) {
        if (dataTag.length() >= 6) {
            String params = dataTag.substring(5);
            String format = params;
            Map<String, Object> opts = Attributes.parse(params);
            if (opts != null && opts.containsKey("format")) {
                format = (String) opts.get("format");
            }
            format = format.trim();
            if (format.startsWith("@")) {
                format = format.substring(1);
            }
            this.dataFormat = format;
        }
    }

    private void parseDefsJsonLax(Snippet body) {
        body.setOrigin(null);
        String json = body.toString();
        try {
            Class.forName("net.minidev.json.JSONValue");
        } catch (ClassNotFoundException e) {
            logInputError("Error: template uses json-formatted args in exec, but json-smart jar is not in the classpath!");
        }
        Map<String, Object> parsedValue = JSONValue.parseKeepingOrder(json);
        if (parsedValue instanceof Map) {
            importJSONDefs(parsedValue);
        } else if ((parsedValue instanceof JSONArray) || (parsedValue instanceof List)) {
            logInputError("Error processing template: exec expected JSON object, not JSON array.");
        } else if ((parsedValue instanceof String) && parsedValue.toString().trim().length() > 0) {
            logInputError("Error processing template: exec expected JSON object, not String.");
        }
    }

    private void parseDefsJsonStrict(Snippet body) {
        try {
            body.setOrigin(null);
            String json = body.toString();
            try {
                Class.forName("net.minidev.json.JSONValue");
            } catch (ClassNotFoundException e) {
                logInputError("Error: template uses json-formatted args in exec, but json-smart jar is not in the classpath!");
            }
            Object parsedValue = parseStrictJsonKeepingOrder(json);
            if (parsedValue instanceof Map) {
                importJSONDefs((Map) parsedValue);
            } else if ((parsedValue instanceof JSONArray) || (parsedValue instanceof List)) {
                logInputError("Error processing template: exec expected JSON object, not JSON array.");
            } else if ((parsedValue instanceof String) && parsedValue.toString().trim().length() > 0) {
                logInputError("Error processing template: exec expected JSON object, not String.");
            }
        } catch (Exception e2) {
            e2.printStackTrace(System.err);
        }
    }

    private void logInputError(String errMsg) {
        if (this.inputErrs == null) {
            this.inputErrs = new ArrayList();
        }
        this.inputErrs.add(errMsg);
    }

    private Object parseStrictJsonKeepingOrder(String json) throws ParseException {
        return new JSONParser(400).parse(json, ContainerFactory.FACTORY_ORDERED);
    }

    private void importJSONDefs(Map<String, Object> defs) {
        this.macroDefs = defs;
    }

    private void parseDefsXML(Snippet body) {
        body.setOrigin(null);
        this.macroDefs = parseXMLObject(new LiteXml(body.toString()));
    }

    private Map<String, Object> parseXMLObject(LiteXml xml) {
        LiteXml[] rules = xml.getChildNodes();
        if (rules == null) {
            return null;
        }
        Map<String, Object> tags = new HashMap();
        LiteXml[] arr$ = rules;
        int len$ = arr$.length;
        for (int i$ = ARG_START; i$ < len$; i$++) {
            LiteXml rule = arr$[i$];
            String tagName = rule.getNodeType();
            if (rule.getChildNodes() == null) {
                tags.put(tagName, rule.getNodeValue());
            } else {
                tags.put(tagName, parseXMLObject(rule));
            }
            Map<String, String> attrs = rule.getAttributes();
            if (attrs != null) {
                for (String key : attrs.keySet()) {
                    tags.put(tagName + "@" + key, attrs.get(key));
                }
            }
        }
        return tags;
    }

    private void parseDefsOriginal(Snippet body) {
        List<SnippetPart> parts = body.getParts();
        if (parts != null) {
            int i = ARG_START;
            while (i < parts.size()) {
                SnippetPart part = (SnippetPart) parts.get(i);
                if (part.isTag()) {
                    String tagText = ((SnippetTag) part).getTag();
                    if (tagText.trim().endsWith("=")) {
                        int j = findMatchingDefEnd(parts, i + 1);
                        Snippet def = new Snippet(parts, i + 1, j);
                        def.setOrigin(body.getOrigin());
                        saveDef(tagText.substring(ARG_START, tagText.length() - 1), def);
                        i = j;
                        if (j < parts.size() && ((SnippetPart) parts.get(j)).getText().equals("{=}")) {
                            i = j + 1;
                        }
                    } else {
                        String[] simpleDef = getSimpleDef(tagText);
                        if (simpleDef != null) {
                            saveDef(simpleDef[ARG_START], simpleDef[1], body.getOrigin());
                        }
                    }
                }
                i++;
            }
        }
    }

    private int findMatchingDefEnd(List<SnippetPart> parts, int startAt) {
        int allTheRest = parts.size();
        for (int i = startAt; i < allTheRest; i++) {
            SnippetPart part = (SnippetPart) parts.get(i);
            if (part.isTag()) {
                String tagText = ((SnippetTag) part).getTag();
                int eqPos = tagText.indexOf(61);
                if (eqPos < 0) {
                    continue;
                } else if (tagText.length() == 1) {
                    return i;
                } else {
                    char[] tagChars = tagText.toCharArray();
                    char c = Constants.EQUAL_UNICODE;
                    for (int x = ARG_START; x < eqPos; x++) {
                        c = tagChars[x];
                        if (c == '.' || c == '|' || c == ':' || c == Constants.LEFT_PAREN) {
                            c = '\u0000';
                            break;
                        }
                    }
                    if (c != '\u0000') {
                        return i;
                    }
                }
            }
        }
        return allTheRest;
    }

    private String[] getSimpleDef(String tagText) {
        int eqPos = tagText.indexOf(61);
        if (eqPos <= -1) {
            return null;
        }
        String varName = tagText.substring(ARG_START, eqPos).trim();
        String varValue = tagText.substring(eqPos + 1);
        if (varValue.charAt(ARG_START) == Letters.SPACE && tagText.charAt(eqPos - 1) == Letters.SPACE) {
            varValue = varValue.trim();
        }
        return new String[]{varName, varValue};
    }

    private void saveDef(String tag, String def, String origin) {
        if (tag != null && def != null) {
            saveDef(tag, Snippet.getSnippet(def, origin));
        }
    }

    private void saveDef(String tag, Snippet snippet) {
        if (tag != null && snippet != null) {
            if (this.macroDefs == null) {
                this.macroDefs = new HashMap();
            }
            this.macroDefs.put(tag, snippet);
        }
    }

    public void renderBlock(Writer out, Chunk context, String origin, int depth) throws IOException {
        Chunk macro;
        ChunkFactory theme = context.getChunkFactory();
        if (this.templateRef != null && theme != null) {
            this.templateRef = BlockTag.qualifyTemplateRef(origin, this.templateRef);
            macro = theme.makeChunk(this.templateRef);
        } else if (this.template != null) {
            macro = theme == null ? new Chunk() : theme.makeChunk();
            macro.append(this.template);
        } else {
            return;
        }
        if (this.inputErrs != null) {
            if (context.renderErrorsToOutput()) {
                for (String err : this.inputErrs) {
                    out.append('[');
                    out.append(err);
                    out.append(']');
                }
            }
            for (String err2 : this.inputErrs) {
                context.logError(err2);
            }
        }
        if (this.macroDefs != null) {
            Set<String> keys = this.macroDefs.keySet();
            if (keys != null) {
                for (String tagName : keys) {
                    macro.setOrDelete(tagName, resolvePointers(context, origin, this.macroDefs.get(tagName), ARG_START));
                }
            }
        }
        macro.render(out, context);
    }

    private Object resolvePointers(Chunk context, String origin, Object o, int depth) {
        if (depth > 10) {
            return o;
        }
        if (o instanceof String) {
            Snippet o2 = Snippet.getSnippet((String) o, origin);
        }
        if (!(o2 instanceof Snippet)) {
            return o2;
        }
        Snippet s = o2;
        if (!s.isSimplePointer()) {
            return o2;
        }
        Object n = context.resolveTagValue(s.getPointerTag(), 1, origin);
        if (n != null) {
            return resolvePointers(context, origin, n, depth + 1);
        }
        return o2;
    }

    public String getBlockStartMarker() {
        return MACRO_MARKER;
    }

    public String getBlockEndMarker() {
        return MACRO_END_MARKER;
    }

    public boolean doSmartTrimAroundBlock() {
        return true;
    }
}
