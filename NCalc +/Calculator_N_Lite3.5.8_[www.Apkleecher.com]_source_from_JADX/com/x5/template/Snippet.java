package com.x5.template;

import com.example.duy.calculator.math_eval.Constants;
import io.github.kexanie.library.BuildConfig;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Snippet {
    private static final int BLOCKSTART_ALONE = 0;
    private static final int BLOCKSTART_MIDLINE = 2;
    private static final int BLOCKSTART_TRIMMED_ALONE = -1;
    private static final int BLOCKSTART_WITHBODY = 1;
    private static final long CAN_GC_AFTER = 60000;
    public static final String MAGIC_CHARS = "~$%^./!*=+_";
    private static final Pattern UNIVERSAL_LF;
    private static HashMap<String, Long> cacheAge = null;
    private static long gcCounter = 0;
    private static final int gcInterval = 500;
    private static long lastGC;
    private static HashMap<String, Snippet> snippetCache;
    private static boolean useCache;
    private String origin;
    private List<SnippetPart> parts;
    private String simpleText;

    static {
        useCache = isCacheEnabled();
        snippetCache = new HashMap();
        cacheAge = new HashMap();
        lastGC = 0;
        gcCounter = 0;
        UNIVERSAL_LF = Pattern.compile("\n|\r\n|\r\r");
    }

    private Snippet(String template) {
        this.parts = null;
        this.simpleText = null;
        this.origin = null;
        parseParts(template);
    }

    private Snippet(String template, String origin) {
        this.parts = null;
        this.simpleText = null;
        this.origin = null;
        this.origin = origin;
        parseParts(template);
    }

    public static Snippet getSnippet(String template) {
        if (useCache) {
            return getSnippetFromCache(template);
        }
        return new Snippet(template);
    }

    public static Snippet getSnippet(String template, String origin) {
        if (useCache) {
            return getSnippetFromCache(template);
        }
        return new Snippet(template, origin);
    }

    private static Snippet getSnippetFromCache(String template) {
        long timestamp = System.currentTimeMillis();
        long j = gcCounter + 1;
        gcCounter = j;
        if (j % 500 == 0) {
            pruneCache(timestamp);
        }
        Snippet s = (Snippet) snippetCache.get(template);
        if (s != null) {
            cacheAge.put(template, Long.valueOf(timestamp));
            return s;
        }
        s = new Snippet(template);
        snippetCache.put(template, s);
        cacheAge.put(template, Long.valueOf(timestamp));
        return s;
    }

    private static boolean isCacheEnabled() {
        if (System.getProperty("chunk.snippetcache") != null) {
            return true;
        }
        return false;
    }

    private static void pruneCache(long timestamp) {
        long threshhold = timestamp - CAN_GC_AFTER;
        if (lastGC <= threshhold) {
            Iterator<String> i = snippetCache.keySet().iterator();
            while (i.hasNext()) {
                String key = (String) i.next();
                if (((Long) cacheAge.get(key)).longValue() < threshhold) {
                    i.remove();
                    cacheAge.remove(key);
                }
            }
            lastGC = timestamp;
        }
    }

    public Snippet(List<SnippetPart> bodyParts) {
        this.parts = null;
        this.simpleText = null;
        this.origin = null;
        if (bodyParts == null || bodyParts.size() == 0) {
            this.simpleText = BuildConfig.FLAVOR;
        } else {
            this.parts = bodyParts;
        }
    }

    public Snippet(List<SnippetPart> bodyParts, int from, int to) {
        this.parts = null;
        this.simpleText = null;
        this.origin = null;
        if (bodyParts == null || bodyParts.size() == 0) {
            this.simpleText = BuildConfig.FLAVOR;
            return;
        }
        ArrayList<SnippetPart> subParts = new ArrayList();
        for (int i = from; i < to; i += BLOCKSTART_WITHBODY) {
            subParts.add(bodyParts.get(i));
        }
        this.parts = subParts;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOrigin() {
        return this.origin;
    }

    public List<SnippetPart> getParts() {
        return this.parts;
    }

    private void parseParts(String template) {
        if (template != null) {
            SnippetPart snippetPart;
            this.simpleText = null;
            int regexDelimCount = BLOCKSTART_ALONE;
            int marker = BLOCKSTART_ALONE;
            int tagStart = BLOCKSTART_TRIMMED_ALONE;
            int exprStart = BLOCKSTART_TRIMMED_ALONE;
            int trailingBackslashes = BLOCKSTART_ALONE;
            boolean insideRegex = false;
            boolean insideTrToken = false;
            boolean insideComment = false;
            boolean insideLiteral = false;
            char magicChar = '\u0000';
            int len = template.length();
            int i = BLOCKSTART_ALONE;
            while (i < len) {
                char c = template.charAt(i);
                if (tagStart < 0) {
                    if (i + BLOCKSTART_WITHBODY >= len) {
                        break;
                    }
                    char c2 = template.charAt(i + BLOCKSTART_WITHBODY);
                    if (c == '{') {
                        if (MAGIC_CHARS.indexOf(c2) > BLOCKSTART_TRIMMED_ALONE && !(c2 == '$' && isJavascriptHeadFake(i, template))) {
                            tagStart = i;
                            trailingBackslashes = BLOCKSTART_ALONE;
                            if (c2 == '%') {
                                i += BLOCKSTART_MIDLINE;
                                if (i == len) {
                                    break;
                                }
                                char e0 = template.charAt(i);
                                while (i < len && Character.isWhitespace(e0)) {
                                    i += BLOCKSTART_WITHBODY;
                                    e0 = template.charAt(i);
                                }
                                if (MAGIC_CHARS.indexOf(e0) > BLOCKSTART_TRIMMED_ALONE) {
                                    magicChar = e0;
                                    exprStart = i + BLOCKSTART_WITHBODY;
                                } else {
                                    magicChar = '.';
                                    exprStart = i;
                                }
                            } else {
                                i += BLOCKSTART_WITHBODY;
                                magicChar = c2;
                                exprStart = i + BLOCKSTART_WITHBODY;
                            }
                        }
                    } else if (c == '_' && c2 == '[') {
                        tagStart = i;
                        insideTrToken = true;
                        i += BLOCKSTART_WITHBODY;
                    }
                } else if (insideTrToken && c == ']') {
                    if (trailingBackslashes % BLOCKSTART_MIDLINE == 0) {
                        this.parts = getPartsForAppend();
                        if (marker < tagStart) {
                            snippetPart = new SnippetPart(template.substring(marker, tagStart));
                            snippetPart.setLiteral(true);
                            this.parts.add(snippetPart);
                        }
                        this.parts.add(new SnippetToken(template.substring(tagStart, i + BLOCKSTART_WITHBODY), template.substring(tagStart + BLOCKSTART_MIDLINE, i)));
                        marker = i + BLOCKSTART_WITHBODY;
                        tagStart = BLOCKSTART_TRIMMED_ALONE;
                        insideTrToken = false;
                    }
                } else if (c == '}') {
                    if (!insideRegex && trailingBackslashes % BLOCKSTART_MIDLINE == 0) {
                        if (insideLiteral) {
                            if (isLiteralClose(template, magicChar, tagStart, i)) {
                                SnippetPart literal = new SnippetPart(template.substring(marker, i + BLOCKSTART_WITHBODY));
                                literal.setLiteral(true);
                                this.parts.add(literal);
                                marker = i + BLOCKSTART_WITHBODY;
                                insideLiteral = false;
                            }
                            tagStart = BLOCKSTART_TRIMMED_ALONE;
                        } else if (magicChar == Constants.FACTORIAL_UNICODE) {
                            c0 = template.charAt(i + BLOCKSTART_TRIMMED_ALONE);
                            c00 = template.charAt(i - 2);
                            if (c0 == '-' && c00 == '-') {
                                i = extractComment(marker, tagStart, i, template, len);
                                tagStart = BLOCKSTART_TRIMMED_ALONE;
                                marker = i + BLOCKSTART_WITHBODY;
                                insideComment = false;
                            } else {
                                insideComment = true;
                            }
                        } else {
                            SnippetPart tag = extractTag(magicChar, template, marker, tagStart, exprStart, i);
                            if (tag != null) {
                                this.parts.add(tag);
                                marker = i + BLOCKSTART_WITHBODY;
                                tagStart = BLOCKSTART_TRIMMED_ALONE;
                            } else {
                                insideLiteral = true;
                                marker = tagStart;
                                tagStart = BLOCKSTART_TRIMMED_ALONE;
                            }
                        }
                    }
                } else if (c == '/' && trailingBackslashes % BLOCKSTART_MIDLINE == 0) {
                    if (regexDelimCount > 0) {
                        regexDelimCount += BLOCKSTART_TRIMMED_ALONE;
                        if (regexDelimCount < BLOCKSTART_WITHBODY) {
                            insideRegex = false;
                        }
                    } else {
                        c0 = template.charAt(i + BLOCKSTART_TRIMMED_ALONE);
                        c00 = template.charAt(i - 2);
                        if (c0 == 's' && c00 == '|') {
                            regexDelimCount = BLOCKSTART_MIDLINE;
                            insideRegex = true;
                        } else if (c0 == 'm' && (c00 == ',' || c00 == '(')) {
                            regexDelimCount = BLOCKSTART_WITHBODY;
                            insideRegex = true;
                        } else if (c0 == ',' || (c0 == '(' && c00 == 'h')) {
                            regexDelimCount = BLOCKSTART_WITHBODY;
                            insideRegex = true;
                        }
                    }
                } else if (c == '\\') {
                    trailingBackslashes += BLOCKSTART_WITHBODY;
                } else if (trailingBackslashes > 0) {
                    trailingBackslashes = BLOCKSTART_ALONE;
                }
                i += BLOCKSTART_WITHBODY;
            }
            if (this.parts == null) {
                this.simpleText = template;
                return;
            }
            if (insideComment) {
                this.parts.add(new SnippetComment(template.substring(marker)));
            } else if (marker < template.length()) {
                snippetPart = new SnippetPart(template.substring(marker));
                snippetPart.setLiteral(true);
                this.parts.add(snippetPart);
            }
            groupBlocks(this.parts);
        }
    }

    private static boolean isJavascriptHeadFake(int i, String template) {
        int len = template.length();
        if (i + BLOCKSTART_MIDLINE >= len) {
            return true;
        }
        char c3 = template.charAt(i + BLOCKSTART_MIDLINE);
        if (c3 == '.' || c3 == Constants.LEFT_PAREN || c3 == Letters.SPACE || c3 == '$') {
            return true;
        }
        if (i + 3 >= len || template.charAt(i + 3) != Constants.LEFT_PAREN) {
            return false;
        }
        return true;
    }

    public boolean isSimple() {
        return this.simpleText != null;
    }

    private boolean isLiteralClose(String template, char magicChar, int tagStart, int i) {
        if (magicChar != '.' || i - tagStart <= 8) {
            if (magicChar == Constants.POWER_UNICODE) {
                if (tagStart == i - 2) {
                    return true;
                }
            } else if (magicChar == '~') {
                if (tagStart == i - 3 && template.charAt(i + BLOCKSTART_TRIMMED_ALONE) == '.') {
                    return true;
                }
                if (tagStart == i - 11 && template.substring(tagStart + 3, i).equals("/literal")) {
                    return true;
                }
            } else if (tagStart == i - 9 && magicChar == Constants.DIV_UNICODE && template.substring(tagStart + BLOCKSTART_WITHBODY, i).equals("/literal")) {
                return true;
            }
        } else if (template.charAt(tagStart + BLOCKSTART_WITHBODY) == '%') {
            int exprEnd = i + BLOCKSTART_TRIMMED_ALONE;
            if (template.charAt(exprEnd) == '%') {
                exprEnd += BLOCKSTART_TRIMMED_ALONE;
            }
            String expr = template.substring(tagStart + BLOCKSTART_MIDLINE, exprEnd).trim();
            if (expr.equals("endliteral") || expr.equals("/literal")) {
                return true;
            }
        }
        return false;
    }

    private SnippetPart extractTag(char magicChar, String template, int marker, int tagStart, int exprStart, int i) {
        this.parts = getPartsForAppend();
        if (marker < tagStart) {
            SnippetPart literal = new SnippetPart(template.substring(marker, tagStart));
            literal.setLiteral(true);
            this.parts.add(literal);
        }
        String wholeTag = template.substring(tagStart, i + BLOCKSTART_WITHBODY);
        if (wholeTag.charAt(BLOCKSTART_WITHBODY) == '%') {
            if (wholeTag.charAt(wholeTag.length() - 2) == '%') {
                i += BLOCKSTART_TRIMMED_ALONE;
            }
            while (i > tagStart && Character.isWhitespace(wholeTag.charAt((i - tagStart) + BLOCKSTART_TRIMMED_ALONE))) {
                i += BLOCKSTART_TRIMMED_ALONE;
            }
        }
        String gooeyCenter;
        if (magicChar == '~' || magicChar == '$') {
            gooeyCenter = template.substring(exprStart, i);
            if (gooeyCenter.startsWith(".end")) {
                gooeyCenter = "./" + gooeyCenter.substring(4);
            }
            return new SnippetTag(wholeTag, gooeyCenter);
        } else if (magicChar == '^' || magicChar == '.') {
            gooeyCenter = template.substring(exprStart, i);
            if (!gooeyCenter.equals("literal")) {
                if (!gooeyCenter.equals("^")) {
                    if (gooeyCenter.startsWith("end")) {
                        gooeyCenter = "/" + gooeyCenter.substring(3);
                    }
                    return new SnippetTag(wholeTag, "." + gooeyCenter);
                }
            }
            return null;
        } else if (magicChar == '/') {
            gooeyCenter = template.substring(exprStart, i);
            return new SnippetTag(wholeTag, "./" + gooeyCenter);
        } else if (magicChar != '*') {
            if (magicChar == '=') {
                if (wholeTag.length() == 3) {
                    return new SnippetTag(wholeTag, "=");
                }
            } else if (magicChar == '_') {
                return SnippetToken.parseTokenWithArgs(wholeTag);
            } else {
                if (magicChar == '+') {
                    if (!wholeTag.startsWith("{+(") && wholeTag.indexOf("+(") != exprStart - tagStart) {
                        return new SnippetTag(wholeTag, ".include " + template.substring(exprStart, i));
                    }
                    return new SnippetTag(wholeTag, ".includeIf(" + template.substring(exprStart + BLOCKSTART_WITHBODY, i));
                } else if (magicChar == '%') {
                    literal = new SnippetPart(wholeTag);
                    literal.setLiteral(true);
                    return literal;
                }
            }
            return new SnippetPart(wholeTag);
        } else if (wholeTag.length() == 3) {
            return new SnippetTag(wholeTag, "./exec");
        } else {
            int refEnd = i;
            if (template.charAt(i + BLOCKSTART_TRIMMED_ALONE) == '*') {
                refEnd += BLOCKSTART_TRIMMED_ALONE;
            }
            String macroTemplate = template.substring(exprStart, refEnd).trim();
            return new SnippetTag(wholeTag, ".exec " + macroTemplate + " original");
        }
    }

    private List<SnippetPart> getPartsForAppend() {
        if (this.parts == null) {
            this.parts = new ArrayList();
        }
        return this.parts;
    }

    private int extractComment(int marker, int tagStart, int i, String template, int len) {
        this.parts = getPartsForAppend();
        String precedingComment = null;
        int startOfThisLine = marker;
        if (marker < tagStart) {
            precedingComment = template.substring(marker, tagStart);
            int lineBreakPos = precedingComment.lastIndexOf(10);
            if (lineBreakPos > BLOCKSTART_TRIMMED_ALONE) {
                startOfThisLine = (marker + lineBreakPos) + BLOCKSTART_WITHBODY;
            }
        }
        if (startOfThisLine == tagStart || template.substring(startOfThisLine, tagStart).trim().length() == 0) {
            int endOfLine = template.indexOf(10, i + BLOCKSTART_WITHBODY);
            if (endOfLine < 0) {
                endOfLine = len;
            }
            if (template.substring(i + BLOCKSTART_WITHBODY, endOfLine).trim().length() == 0) {
                if (startOfThisLine < tagStart) {
                    precedingComment = template.substring(marker, startOfThisLine);
                    tagStart = startOfThisLine;
                }
                if (endOfLine == len) {
                    i = endOfLine + BLOCKSTART_TRIMMED_ALONE;
                } else {
                    i = endOfLine;
                }
            }
        }
        if (precedingComment != null) {
            SnippetPart literal = new SnippetPart(precedingComment);
            literal.setLiteral(true);
            this.parts.add(literal);
        }
        String wholeComment = template.substring(tagStart, i + BLOCKSTART_WITHBODY);
        if (this.origin == null && this.parts.size() == 0 && wholeComment.startsWith("{!--@ORIGIN:")) {
            int endOfOrigin = wholeComment.indexOf("@", 12);
            if (endOfOrigin < 0) {
                endOfOrigin = wholeComment.length();
            }
            this.origin = wholeComment.substring(12, endOfOrigin);
        } else {
            this.parts.add(new SnippetComment(wholeComment));
        }
        return i;
    }

    private void groupBlocks(List<SnippetPart> bodyParts) {
        int i = BLOCKSTART_ALONE;
        while (i < bodyParts.size()) {
            SnippetPart part = (SnippetPart) bodyParts.get(i);
            if (part.isTag()) {
                SnippetTag tag = (SnippetTag) part;
                BlockTag helper = tag.getBlockTagType();
                if (helper != null) {
                    int j = BlockTag.findMatchingBlockEnd(helper, bodyParts, i + BLOCKSTART_WITHBODY);
                    if (j > i) {
                        int x;
                        SnippetTag endTag = (SnippetTag) bodyParts.remove(j);
                        ArrayList<SnippetPart> subBodyParts = new ArrayList();
                        for (x = i + BLOCKSTART_WITHBODY; x < j; x += BLOCKSTART_WITHBODY) {
                            subBodyParts.add(bodyParts.get(x));
                        }
                        for (x = j + BLOCKSTART_TRIMMED_ALONE; x >= i; x += BLOCKSTART_TRIMMED_ALONE) {
                            bodyParts.remove(x);
                        }
                        groupBlocks(subBodyParts);
                        SnippetBlockTag blockTag = new SnippetBlockTag(tag, subBodyParts, endTag, this.origin);
                        bodyParts.add(i, blockTag);
                        if (blockTag.doSmartTrimAroundBlock() && smartTrimBeforeBlockStart(bodyParts, blockTag, i + BLOCKSTART_TRIMMED_ALONE) != BLOCKSTART_MIDLINE) {
                            smartTrimAfterBlockEnd(bodyParts, blockTag, i + BLOCKSTART_WITHBODY);
                        }
                    } else {
                        bodyParts.add(i + BLOCKSTART_WITHBODY, new SnippetError("[ERROR in template! " + helper.getBlockStartMarker() + " block with no matching end marker! ]"));
                        i += BLOCKSTART_WITHBODY;
                    }
                }
            }
            i += BLOCKSTART_WITHBODY;
        }
    }

    private int smartTrimBeforeBlockStart(List<SnippetPart> parts, SnippetBlockTag blockTag, int prevPartIdx) {
        if (blockBodyStartsOnSameLine(blockTag)) {
            return BLOCKSTART_WITHBODY;
        }
        if (prevPartIdx < 0) {
            return BLOCKSTART_ALONE;
        }
        SnippetPart prevPart = (SnippetPart) parts.get(prevPartIdx);
        while (prevPart instanceof SnippetComment) {
            prevPartIdx += BLOCKSTART_TRIMMED_ALONE;
            if (prevPartIdx < 0) {
                return BLOCKSTART_ALONE;
            }
            prevPart = (SnippetPart) parts.get(prevPartIdx);
        }
        if (!prevPart.isLiteral()) {
            if (!(prevPart instanceof SnippetBlockTag)) {
                return BLOCKSTART_MIDLINE;
            }
            prevPart = ((SnippetBlockTag) prevPart).getCloseTag();
        }
        String text = prevPart.getText();
        if (text.length() == 0) {
            return smartTrimBeforeBlockStart(parts, blockTag, prevPartIdx + BLOCKSTART_TRIMMED_ALONE);
        }
        int i = text.length() + BLOCKSTART_TRIMMED_ALONE;
        char c = text.charAt(i);
        boolean eatWhitespace = false;
        while (Character.isWhitespace(c)) {
            if (c == '\n' || c == Letters.CR) {
                i += BLOCKSTART_WITHBODY;
                eatWhitespace = true;
                break;
            }
            i += BLOCKSTART_TRIMMED_ALONE;
            if (i < 0) {
                i = BLOCKSTART_ALONE;
                if (prevPartIdx == 0) {
                    eatWhitespace = true;
                } else {
                    int trimState = smartTrimBeforeBlockStart(parts, blockTag, prevPartIdx + BLOCKSTART_TRIMMED_ALONE);
                    if (trimState > 0) {
                        return trimState;
                    }
                    eatWhitespace = true;
                }
            } else {
                c = text.charAt(i);
            }
        }
        if (eatWhitespace) {
            prevPart.setText(text.substring(BLOCKSTART_ALONE, i));
        }
        if (eatWhitespace) {
            return BLOCKSTART_TRIMMED_ALONE;
        }
        return BLOCKSTART_MIDLINE;
    }

    private boolean blockBodyStartsOnSameLine(SnippetBlockTag blockTag) {
        Snippet blockBody = blockTag.getBody();
        if (blockBody.parts == null) {
            return false;
        }
        int i = BLOCKSTART_ALONE;
        while (i < blockBody.parts.size()) {
            SnippetPart firstPart = (SnippetPart) blockBody.parts.get(i);
            if (firstPart instanceof SnippetComment) {
                String commentText = firstPart.toString();
                if (commentText.charAt(commentText.length() + BLOCKSTART_TRIMMED_ALONE) != '}') {
                    return false;
                }
                i += BLOCKSTART_WITHBODY;
            } else if (!firstPart.isLiteral()) {
                return false;
            } else {
                String text = firstPart.getText();
                Matcher m = UNIVERSAL_LF.matcher(text);
                if (!m.find() || text.substring(BLOCKSTART_ALONE, m.start()).trim().length() == 0) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    private void smartTrimAfterBlockEnd(List<SnippetPart> parts, SnippetBlockTag blockTag, int nextPartIdx) {
        if (parts.size() > nextPartIdx) {
            SnippetPart nextPart = (SnippetPart) parts.get(nextPartIdx);
            while (nextPart instanceof SnippetComment) {
                String commentText = nextPart.toString();
                if (commentText.charAt(commentText.length() + BLOCKSTART_TRIMMED_ALONE) == '}') {
                    nextPartIdx += BLOCKSTART_WITHBODY;
                    if (parts.size() > nextPartIdx) {
                        nextPart = (SnippetPart) parts.get(nextPartIdx);
                    } else {
                        return;
                    }
                }
                return;
            }
            if (nextPart.isLiteral()) {
                String text = nextPart.getText();
                Matcher m = UNIVERSAL_LF.matcher(text);
                if (m.find() && text.substring(BLOCKSTART_ALONE, m.start()).trim().length() == 0) {
                    nextPart.setText(text.substring(m.end()));
                    StringBuilder stringBuilder = new StringBuilder();
                    SnippetTag closeTag = blockTag.getCloseTag();
                    closeTag.snippetText = stringBuilder.append(closeTag.snippetText).append(text.substring(BLOCKSTART_ALONE, m.end())).toString();
                }
            }
        }
    }

    public String toString() {
        return _toString(true);
    }

    private String _toString(boolean withOrigin) {
        if (this.simpleText != null) {
            return this.simpleText;
        }
        if (this.parts == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (withOrigin && this.origin != null) {
            sb.append("{!--@ORIGIN:");
            sb.append(this.origin);
            sb.append("@--}");
        }
        for (SnippetPart part : this.parts) {
            sb.append(part.toString());
        }
        return sb.toString();
    }

    public String toSimpleString() {
        return _toString(false);
    }

    public void render(Writer out, Chunk rules, int depth) throws IOException {
        if (this.simpleText != null) {
            out.append(this.simpleText);
        } else if (this.parts != null) {
            for (SnippetPart part : this.parts) {
                part.render(out, rules, this.origin, depth + BLOCKSTART_WITHBODY);
            }
        }
    }

    public Snippet copy() {
        if (this.simpleText != null) {
            Snippet copy = new Snippet(BuildConfig.FLAVOR);
            copy.simpleText = this.simpleText;
            return copy;
        }
        List partsCopy = new ArrayList();
        partsCopy.addAll(this.parts);
        copy = new Snippet(partsCopy);
        copy.origin = this.origin;
        return copy;
    }

    public static Snippet makeLiteralSnippet(String literal) {
        SnippetPart x = new SnippetPart(literal);
        x.setLiteral(true);
        List listOfOne = new ArrayList();
        listOfOne.add(x);
        return new Snippet(listOfOne);
    }

    boolean isSimplePointer() {
        if (this.parts != null && this.parts.size() == BLOCKSTART_WITHBODY && (((SnippetPart) this.parts.get(BLOCKSTART_ALONE)) instanceof SnippetTag)) {
            return true;
        }
        return false;
    }

    String getPointer() {
        if (!isSimplePointer()) {
            return null;
        }
        return ((SnippetTag) ((SnippetPart) this.parts.get(BLOCKSTART_ALONE))).getTag();
    }

    SnippetTag getPointerTag() {
        if (!isSimplePointer()) {
            return null;
        }
        return (SnippetTag) ((SnippetPart) this.parts.get(BLOCKSTART_ALONE));
    }

    static Snippet consolidateSnippets(Vector<Snippet> template) throws EndOfSnippetException {
        if (template == null) {
            return null;
        }
        if (template.size() == BLOCKSTART_WITHBODY) {
            return (Snippet) template.get(BLOCKSTART_ALONE);
        }
        int i;
        for (i = BLOCKSTART_WITHBODY; i < template.size(); i += BLOCKSTART_WITHBODY) {
            Snippet a = (Snippet) template.get(i + BLOCKSTART_TRIMMED_ALONE);
            Snippet b = (Snippet) template.get(i);
            if (!(a.origin == null && b.origin == null) && (a.origin == null || b.origin == null || !a.origin.equals(b.origin))) {
                throw new EndOfSnippetException("Can't merge snippets, incompatible origins.");
            }
        }
        List merged = new ArrayList();
        for (i = BLOCKSTART_ALONE; i < template.size(); i += BLOCKSTART_WITHBODY) {
            List<SnippetPart> parts = ((Snippet) template.get(i)).ungroupBlocks();
            if (parts != null) {
                merged.addAll(parts);
            }
        }
        Snippet voltron = new Snippet(merged);
        voltron.origin = ((Snippet) template.get(BLOCKSTART_ALONE)).origin;
        voltron.groupBlocks(voltron.parts);
        return voltron;
    }

    private List<SnippetPart> ungroupBlocks() {
        if (this.parts != null) {
            boolean noBlocks = true;
            for (SnippetPart part : this.parts) {
                if (!(part instanceof SnippetBlockTag)) {
                    if (part instanceof SnippetError) {
                    }
                }
                noBlocks = false;
                break;
            }
            if (noBlocks) {
                return this.parts;
            }
            List<SnippetPart> flat = new ArrayList();
            for (SnippetPart part2 : this.parts) {
                if (part2 instanceof SnippetBlockTag) {
                    SnippetBlockTag block = (SnippetBlockTag) part2;
                    flat.add(block.getOpenTag());
                    flat.addAll(block.getBody().ungroupBlocks());
                    flat.add(block.getCloseTag());
                } else if (!(part2 instanceof SnippetError)) {
                    flat.add(part2);
                }
            }
            return flat;
        } else if (this.simpleText.length() < BLOCKSTART_WITHBODY) {
            return null;
        } else {
            List<SnippetPart> onePart = new ArrayList();
            SnippetPart simplePart = new SnippetPart(this.simpleText);
            simplePart.setLiteral(true);
            onePart.add(simplePart);
            return onePart;
        }
    }
}
