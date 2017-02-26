package com.x5.template;

import com.example.duy.calculator.math_eval.Constants;
import com.x5.template.filters.FilterArgs;
import com.x5.template.filters.RegexFilter;
import java.io.IOException;
import java.io.Writer;
import java.util.StringTokenizer;

public class SnippetTag extends SnippetPart {
    private static final BlockTag[] BLOCK_TAGS;
    private static final String[] BLOCK_TAG_TOKENS;
    private boolean applyFiltersIfNull;
    private String filters;
    private String ifNull;
    private String[] path;
    protected String tag;

    public SnippetTag(String text, String tag) {
        super(text);
        this.applyFiltersIfNull = false;
        this.tag = tag;
    }

    public boolean isTag() {
        return true;
    }

    public String getTag() {
        return this.tag;
    }

    public void render(Writer out, Chunk rules, String origin, int depth) throws IOException {
        if (!depthCheckFails(depth, out)) {
            if (this.path == null) {
                init();
            }
            Object tagValue = rules.resolveTagValue(this, depth, origin);
            if (tagValue == null) {
                out.append(this.snippetText);
            } else if (tagValue instanceof Snippet) {
                ((Snippet) tagValue).render(out, rules, depth);
            } else if (tagValue instanceof String) {
                Snippet.getSnippet((String) tagValue, origin).render(out, rules, depth + 1);
            } else {
                rules.explodeToPrinter(out, tagValue, depth + 1);
            }
        }
    }

    private void init() {
        String lookupName = this.tag;
        int colonPos = this.tag.indexOf(58);
        int pipePos = this.tag.indexOf(124);
        if (pipePos > -1) {
            pipePos = confirmPipe(this.tag, pipePos);
        }
        if (colonPos > 0 || pipePos > 0) {
            int firstMod = colonPos > 0 ? colonPos : pipePos;
            if (pipePos > 0 && pipePos < colonPos) {
                firstMod = pipePos;
            }
            lookupName = this.tag.substring(0, firstMod);
            String defValue = null;
            String filter = null;
            String order = Filter.FILTER_LAST;
            if (pipePos > 0 && colonPos > 0) {
                String[] tokens = parseTagTokens(this.tag, pipePos, colonPos);
                filter = tokens[0];
                defValue = tokens[1];
                order = tokens[2];
            } else if (colonPos > 0) {
                defValue = this.tag.substring(colonPos + 1);
            } else {
                filter = this.tag.substring(pipePos + 1);
            }
            this.ifNull = defValue;
            this.applyFiltersIfNull = order.equals(Filter.FILTER_LAST);
            this.filters = filter;
        }
        this.path = parsePath(lookupName);
    }

    private String[] parsePath(String deepRef) {
        if (deepRef.indexOf(46, 1) < 0 || deepRef.charAt(0) == '.') {
            return new String[]{deepRef};
        }
        StringTokenizer splitter = new StringTokenizer(deepRef, ".");
        String[] path = new String[splitter.countTokens()];
        int i = 0;
        while (splitter.hasMoreTokens()) {
            path[i] = splitter.nextToken();
            i++;
        }
        return path;
    }

    private String[] parseTagTokens(String tagName, int pipePos, int colonPos) {
        String filter;
        String defValue = null;
        String order = Filter.FILTER_LAST;
        if (colonPos < 0) {
            filter = tagName.substring(pipePos + 1);
        } else if (pipePos < colonPos) {
            int finalPipe = Filter.grokFinalFilterPipe(tagName, pipePos);
            if (tagName.indexOf(":", finalPipe + 1) < 0) {
                filter = tagName.substring(pipePos + 1);
            } else {
                int nextColon = tagName.indexOf(":", FilterArgs.grokValidColonScanPoint(tagName, finalPipe + 1));
                if (nextColon < 0) {
                    filter = tagName.substring(pipePos + 1);
                } else {
                    filter = tagName.substring(pipePos + 1, nextColon);
                    defValue = tagName.substring(nextColon + 1);
                    order = Filter.FILTER_FIRST;
                }
            }
        } else {
            filter = tagName.substring(pipePos + 1);
            defValue = tagName.substring(colonPos + 1, pipePos);
        }
        return new String[]{filter, defValue, order};
    }

    private int confirmPipe(String tagName, int pipePos) {
        String skipToken = "includeIf(";
        String closeToken = ")";
        int doesntCountParen = tagName.indexOf(skipToken);
        if (doesntCountParen < 0) {
            skipToken = "include.(";
            doesntCountParen = tagName.indexOf(skipToken);
            if (doesntCountParen < 0) {
                skipToken = "`";
                closeToken = "`";
                doesntCountParen = tagName.indexOf(skipToken);
            }
        }
        if (doesntCountParen < 0) {
            return pipePos;
        }
        int scanFrom = doesntCountParen + skipToken.length();
        int nextParen = tagName.indexOf(closeToken, scanFrom);
        if (!closeToken.equals("`")) {
            int nextSlash = tagName.indexOf("/", scanFrom);
            if (nextSlash < 0 || nextParen < 0 || nextParen < nextSlash) {
                return pipePos;
            }
            nextParen = tagName.indexOf(")", RegexFilter.nextRegexDelim(tagName, nextSlash + 1) + 1);
            if (nextParen < 0) {
                return pipePos;
            }
            if (nextParen < pipePos) {
                return pipePos;
            }
        } else if (pipePos > nextParen) {
            return pipePos;
        }
        return tagName.indexOf("|", nextParen + 1);
    }

    public String[] getPath() {
        if (this.path == null) {
            init();
        }
        return this.path;
    }

    public String getDefaultValue() {
        if (this.ifNull == null || this.ifNull.length() == 0) {
            return this.ifNull;
        }
        char firstChar = this.ifNull.charAt(0);
        if (firstChar == '~' || firstChar == '$' || firstChar == Constants.PLUS_UNICODE || firstChar == Constants.POWER_UNICODE || firstChar == '.') {
            if (this.filters == null) {
                return '{' + this.ifNull + '}';
            }
            if (this.applyFiltersIfNull) {
                return '{' + this.ifNull + '|' + this.filters + '}';
            }
            return '{' + this.ifNull + '}';
        } else if (this.ifNull.charAt(0) == Letters.BACKSLASH) {
            return this.ifNull.substring(1);
        } else {
            return this.ifNull;
        }
    }

    public String getFilters() {
        return this.filters;
    }

    public boolean applyFiltersFirst() {
        return !this.applyFiltersIfNull;
    }

    static SnippetTag parseTag(String tag) {
        SnippetTag parsedTag = new SnippetTag(tag, tag);
        parsedTag.init();
        return parsedTag;
    }

    static {
        BLOCK_TAGS = new BlockTag[]{new LoopTag(), new IfTag(), new LocaleTag(), new MacroTag()};
        BLOCK_TAG_TOKENS = extractTagTokens(BLOCK_TAGS);
    }

    private static String[] extractTagTokens(BlockTag[] blockTags) {
        String[] tokens = new String[blockTags.length];
        for (int i = 0; i < blockTags.length; i++) {
            tokens[i] = "." + blockTags[i].getBlockStartMarker();
        }
        return tokens;
    }

    public BlockTag getBlockTagType() {
        int i = 0;
        while (i < BLOCK_TAG_TOKENS.length) {
            if (!this.tag.startsWith(BLOCK_TAG_TOKENS[i])) {
                i++;
            } else if (BLOCK_TAGS[i].hasBody(this.tag)) {
                return BLOCK_TAGS[i];
            } else {
                return null;
            }
        }
        return null;
    }
}
