package com.x5.template;

import com.example.duy.calculator.math_eval.Constants;
import com.x5.template.filters.RegexFilter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IfTag extends BlockTag {
    private static final Pattern UNIVERSAL_LF;
    private Snippet body;
    private boolean doTrim;
    private Map<String, String> options;
    private String primaryCond;

    public IfTag(String params, Snippet body) {
        this.doTrim = true;
        parseParams(params);
        initBody(body);
    }

    public IfTag() {
        this.doTrim = true;
    }

    public String getBlockStartMarker() {
        return "if";
    }

    public String getBlockEndMarker() {
        return "/if";
    }

    private void initBody(Snippet body) {
        this.body = body;
    }

    private void parseParams(String params) {
        this.primaryCond = parseCond(params);
        this.options = parseAttributes(params);
        if (this.options != null) {
            String trimOpt = (String) this.options.get("trim");
            if (trimOpt == null) {
                return;
            }
            if (trimOpt.equalsIgnoreCase(Constants.FALSE) || trimOpt.equalsIgnoreCase("none")) {
                this.doTrim = false;
            }
        }
    }

    private String parseCond(String params) {
        if (params == null) {
            return null;
        }
        int openParenPos = params.indexOf("(");
        int quotedCondPos = params.indexOf(" cond=\"");
        if (openParenPos > -1) {
            int closeParenPos = params.lastIndexOf(")");
            if (quotedCondPos < 0 && closeParenPos > openParenPos) {
                return params.substring(openParenPos + 1, closeParenPos);
            }
        }
        if (quotedCondPos <= -1) {
            return null;
        }
        quotedCondPos += " cond='".length();
        int closeQuotePos = params.indexOf("\"", quotedCondPos);
        if (closeQuotePos < 0) {
            return params.substring(quotedCondPos);
        }
        return params.substring(quotedCondPos, closeQuotePos);
    }

    private Map<String, String> parseAttributes(String params) {
        Matcher m = Pattern.compile(" ([a-zA-Z0-9_-]+)=(\"([^\"]*)\"|'([^']*)')").matcher(params);
        HashMap<String, String> opts = null;
        while (m.find()) {
            m.group(0);
            String paramName = m.group(1);
            String paramValue = m.group(3);
            if (opts == null) {
                opts = new HashMap();
            }
            opts.put(paramName, paramValue);
        }
        return opts;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean isTrueExpr(java.lang.String r18, com.x5.template.Chunk r19) {
        /*
        r17 = this;
        if (r18 != 0) goto L_0x0004;
    L_0x0002:
        r15 = 0;
    L_0x0003:
        return r15;
    L_0x0004:
        r18 = r18.trim();
        r15 = r18.length();
        if (r15 != 0) goto L_0x0010;
    L_0x000e:
        r15 = 0;
        goto L_0x0003;
    L_0x0010:
        r15 = 0;
        r0 = r18;
        r2 = r0.charAt(r15);
        r15 = 33;
        if (r2 == r15) goto L_0x0023;
    L_0x001b:
        r15 = 126; // 0x7e float:1.77E-43 double:6.23E-322;
        if (r2 == r15) goto L_0x0023;
    L_0x001f:
        r15 = 36;
        if (r2 != r15) goto L_0x002a;
    L_0x0023:
        r15 = 1;
        r0 = r18;
        r18 = r0.substring(r15);
    L_0x002a:
        r15 = 33;
        if (r2 != r15) goto L_0x004f;
    L_0x002e:
        r15 = 0;
        r0 = r18;
        r15 = r0.charAt(r15);
        r16 = 126; // 0x7e float:1.77E-43 double:6.23E-322;
        r0 = r16;
        if (r15 == r0) goto L_0x0048;
    L_0x003b:
        r15 = 0;
        r0 = r18;
        r15 = r0.charAt(r15);
        r16 = 36;
        r0 = r16;
        if (r15 != r0) goto L_0x004f;
    L_0x0048:
        r15 = 1;
        r0 = r18;
        r18 = r0.substring(r15);
    L_0x004f:
        r15 = 61;
        r0 = r18;
        r15 = r0.indexOf(r15);
        if (r15 >= 0) goto L_0x0093;
    L_0x0059:
        r15 = "!~";
        r0 = r18;
        r15 = r0.indexOf(r15);
        if (r15 >= 0) goto L_0x0093;
    L_0x0063:
        r0 = r19;
        r1 = r18;
        r11 = r0.get(r1);
        r15 = 126; // 0x7e float:1.77E-43 double:6.23E-322;
        if (r2 == r15) goto L_0x0073;
    L_0x006f:
        r15 = 36;
        if (r2 != r15) goto L_0x0079;
    L_0x0073:
        if (r11 == 0) goto L_0x0077;
    L_0x0075:
        r15 = 1;
        goto L_0x0003;
    L_0x0077:
        r15 = 0;
        goto L_0x0003;
    L_0x0079:
        r15 = 33;
        if (r2 != r15) goto L_0x0083;
    L_0x007d:
        if (r11 != 0) goto L_0x0081;
    L_0x007f:
        r15 = 1;
        goto L_0x0003;
    L_0x0081:
        r15 = 0;
        goto L_0x0003;
    L_0x0083:
        r15 = "true";
        r0 = r18;
        r15 = r0.equalsIgnoreCase(r15);
        if (r15 == 0) goto L_0x0090;
    L_0x008d:
        r15 = 1;
        goto L_0x0003;
    L_0x0090:
        r15 = 0;
        goto L_0x0003;
    L_0x0093:
        r4 = 0;
        r15 = "==";
        r0 = r18;
        r15 = r0.indexOf(r15);
        if (r15 > 0) goto L_0x00ab;
    L_0x009e:
        r15 = "!=";
        r0 = r18;
        r15 = r0.indexOf(r15);
        if (r15 <= 0) goto L_0x0102;
    L_0x00a8:
        r4 = 1;
    L_0x00a9:
        if (r4 == 0) goto L_0x0195;
    L_0x00ab:
        r15 = "!=|==";
        r0 = r18;
        r7 = r0.split(r15);
        r15 = r7.length;
        r16 = 2;
        r0 = r16;
        if (r15 != r0) goto L_0x0195;
    L_0x00ba:
        r15 = 0;
        r15 = r7[r15];
        r9 = r15.trim();
        r15 = 1;
        r15 = r7[r15];
        r10 = r15.trim();
        r0 = r19;
        r11 = r0.get(r9);
        if (r11 != 0) goto L_0x0104;
    L_0x00d0:
        r12 = "";
    L_0x00d2:
        r15 = 0;
        r15 = r10.charAt(r15);
        r16 = 126; // 0x7e float:1.77E-43 double:6.23E-322;
        r0 = r16;
        if (r15 == r0) goto L_0x00e8;
    L_0x00dd:
        r15 = 0;
        r15 = r10.charAt(r15);
        r16 = 36;
        r0 = r16;
        if (r15 != r0) goto L_0x011d;
    L_0x00e8:
        r15 = 1;
        r15 = r10.substring(r15);
        r0 = r19;
        r11 = r0.get(r15);
        if (r11 != 0) goto L_0x0109;
    L_0x00f5:
        r13 = "";
    L_0x00f7:
        if (r4 == 0) goto L_0x0111;
    L_0x00f9:
        r15 = r12.equals(r13);
        if (r15 == 0) goto L_0x010e;
    L_0x00ff:
        r15 = 0;
        goto L_0x0003;
    L_0x0102:
        r4 = 0;
        goto L_0x00a9;
    L_0x0104:
        r12 = r11.toString();
        goto L_0x00d2;
    L_0x0109:
        r13 = r11.toString();
        goto L_0x00f7;
    L_0x010e:
        r15 = 1;
        goto L_0x0003;
    L_0x0111:
        r15 = r12.equals(r13);
        if (r15 == 0) goto L_0x011a;
    L_0x0117:
        r15 = 1;
        goto L_0x0003;
    L_0x011a:
        r15 = 0;
        goto L_0x0003;
    L_0x011d:
        r5 = r10;
        r15 = 0;
        r15 = r10.charAt(r15);
        r16 = 34;
        r0 = r16;
        if (r15 != r0) goto L_0x0157;
    L_0x0129:
        r15 = r5.length();
        r15 = r15 + -1;
        r15 = r10.charAt(r15);
        r16 = 34;
        r0 = r16;
        if (r15 != r0) goto L_0x0157;
    L_0x0139:
        r15 = 1;
        r16 = r10.length();
        r16 = r16 + -1;
        r0 = r16;
        r5 = r10.substring(r15, r0);
        r0 = r17;
        r5 = r0.unescape(r5);
    L_0x014c:
        if (r4 == 0) goto L_0x0189;
    L_0x014e:
        r15 = r12.equals(r5);
        if (r15 == 0) goto L_0x0186;
    L_0x0154:
        r15 = 0;
        goto L_0x0003;
    L_0x0157:
        r15 = 0;
        r15 = r10.charAt(r15);
        r16 = 39;
        r0 = r16;
        if (r15 != r0) goto L_0x014c;
    L_0x0162:
        r15 = r5.length();
        r15 = r15 + -1;
        r15 = r10.charAt(r15);
        r16 = 39;
        r0 = r16;
        if (r15 != r0) goto L_0x014c;
    L_0x0172:
        r15 = 1;
        r16 = r10.length();
        r16 = r16 + -1;
        r0 = r16;
        r5 = r10.substring(r15, r0);
        r0 = r17;
        r5 = r0.unescape(r5);
        goto L_0x014c;
    L_0x0186:
        r15 = 1;
        goto L_0x0003;
    L_0x0189:
        r15 = r12.equals(r5);
        if (r15 == 0) goto L_0x0192;
    L_0x018f:
        r15 = 1;
        goto L_0x0003;
    L_0x0192:
        r15 = 0;
        goto L_0x0003;
    L_0x0195:
        r15 = "=~";
        r0 = r18;
        r7 = r0.split(r15);
        r6 = 0;
        r15 = r7.length;
        r16 = 2;
        r0 = r16;
        if (r15 == r0) goto L_0x01b8;
    L_0x01a5:
        r15 = "!~";
        r0 = r18;
        r7 = r0.split(r15);
        r6 = 1;
        r15 = r7.length;
        r16 = 2;
        r0 = r16;
        if (r15 == r0) goto L_0x01b8;
    L_0x01b5:
        r15 = 0;
        goto L_0x0003;
    L_0x01b8:
        r15 = 0;
        r15 = r7[r15];
        r14 = r15.trim();
        r15 = 1;
        r15 = r7[r15];
        r8 = r15.trim();
        r0 = r19;
        r11 = r0.get(r14);
        if (r11 != 0) goto L_0x01dc;
    L_0x01ce:
        r15 = 0;
    L_0x01cf:
        r0 = r17;
        r3 = r0.isMatch(r15, r8);
        if (r6 == 0) goto L_0x01e4;
    L_0x01d7:
        if (r3 == 0) goto L_0x01e1;
    L_0x01d9:
        r15 = 0;
        goto L_0x0003;
    L_0x01dc:
        r15 = r11.toString();
        goto L_0x01cf;
    L_0x01e1:
        r15 = 1;
        goto L_0x0003;
    L_0x01e4:
        if (r3 == 0) goto L_0x01e9;
    L_0x01e6:
        r15 = 1;
        goto L_0x0003;
    L_0x01e9:
        r15 = 0;
        goto L_0x0003;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.x5.template.IfTag.isTrueExpr(java.lang.String, com.x5.template.Chunk):boolean");
    }

    private String unescape(String x) {
        return RegexFilter.parseRegexEscapes(x);
    }

    private boolean isMatch(String text, String regex) {
        if (text == null || regex == null) {
            return false;
        }
        regex = regex.trim();
        int cursor = 0;
        if (regex.charAt(0) == 'm') {
            cursor = 0 + 1;
        }
        if (regex.charAt(cursor) == Constants.DIV_UNICODE) {
            cursor++;
        }
        int regexEnd = RegexFilter.nextRegexDelim(regex, cursor);
        if (regexEnd < 0) {
            return false;
        }
        String pattern = regex.substring(cursor, regexEnd);
        boolean ignoreCase = false;
        boolean multiLine = false;
        boolean dotAll = false;
        for (int i = regex.length() - 1; i > regexEnd; i--) {
            char option = regex.charAt(i);
            if (option == 'i') {
                ignoreCase = true;
            }
            if (option == 'm') {
                multiLine = true;
            }
            if (option == 's') {
                dotAll = true;
            }
        }
        if (multiLine) {
            pattern = "(?m)" + pattern;
        }
        if (ignoreCase) {
            pattern = "(?i)" + pattern;
        }
        if (dotAll) {
            pattern = "(?s)" + pattern;
        }
        return Pattern.compile(pattern).matcher(text).find();
    }

    public boolean doSmartTrimAroundBlock() {
        return true;
    }

    private String trimLeft(String x) {
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

    private String trimRight(String x) {
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
        if (trimOpt == null || (!trimOpt.equals("all") && !trimOpt.equals(Constants.TRUE))) {
            return false;
        }
        return true;
    }

    private String smartTrim(String x) {
        return smartTrim(x, false);
    }

    static {
        UNIVERSAL_LF = Pattern.compile("\n|\r\n|\r\r");
    }

    private String smartTrim(String x, boolean ignoreAll) {
        if (!ignoreAll && isTrimAll()) {
            return x.trim();
        }
        Matcher m = UNIVERSAL_LF.matcher(x);
        if (m.find() && x.substring(0, m.start()).trim().length() == 0) {
            return x.substring(m.end());
        }
        return x;
    }

    private int nextElseTag(List<SnippetPart> bodyParts, int startAt) {
        for (int i = startAt; i < bodyParts.size(); i++) {
            SnippetPart part = (SnippetPart) bodyParts.get(i);
            if ((part instanceof SnippetTag) && ((SnippetTag) part).getTag().startsWith(".else")) {
                return i;
            }
        }
        return -1;
    }

    public void renderBlock(Writer out, Chunk context, String origin, int depth) throws IOException {
        List<SnippetPart> bodyParts = this.body.getParts();
        int nextElseTag = nextElseTag(bodyParts, 0);
        if (isTrueExpr(this.primaryCond, context)) {
            if (nextElseTag < 0) {
                nextElseTag = bodyParts.size();
            }
            renderChosenParts(out, context, origin, depth, bodyParts, 0, nextElseTag);
            return;
        }
        while (nextElseTag > -1) {
            String elseTag = ((SnippetTag) bodyParts.get(nextElseTag)).getTag();
            if (elseTag.equals(".else")) {
                renderChosenParts(out, context, origin, depth, bodyParts, nextElseTag + 1, bodyParts.size());
                return;
            }
            if (isTrueExpr(parseCond(elseTag), context)) {
                int nextBoundary = nextElseTag(bodyParts, nextElseTag + 1);
                if (nextBoundary == -1) {
                    nextBoundary = bodyParts.size();
                }
                renderChosenParts(out, context, origin, depth, bodyParts, nextElseTag + 1, nextBoundary);
                return;
            }
            nextElseTag = nextElseTag(bodyParts, nextElseTag + 1);
        }
    }

    public void renderChosenParts(Writer out, Chunk context, String origin, int depth, List<SnippetPart> parts, int a, int b) throws IOException {
        int i;
        if (!this.doTrim) {
            for (i = a; i < b; i++) {
                ((SnippetPart) parts.get(i)).render(out, context, origin, depth);
            }
        } else if (b <= a) {
        } else {
            SnippetPart partA;
            if (isTrimAll()) {
                while ((parts.get(a) instanceof SnippetComment) && a < b - 1) {
                    a++;
                }
                if (a + 1 == b) {
                    SnippetPart onlyPart = (SnippetPart) parts.get(a);
                    if (onlyPart.isLiteral()) {
                        out.append(onlyPart.getText().trim());
                        return;
                    } else {
                        onlyPart.render(out, context, origin, depth);
                        return;
                    }
                }
                partA = (SnippetPart) parts.get(a);
                if (partA.isLiteral()) {
                    out.append(trimLeft(partA.getText()));
                }
                for (i = a + 1; i < b - 1; i++) {
                    ((SnippetPart) parts.get(i)).render(out, context, origin, depth);
                }
                SnippetPart partB = (SnippetPart) parts.get(b - 1);
                if (partB.isLiteral()) {
                    out.append(trimRight(partB.getText()));
                    return;
                }
                return;
            }
            partA = (SnippetPart) parts.get(a);
            if (partA.isLiteral()) {
                out.append(smartTrim(partA.getText()));
            } else {
                partA.render(out, context, origin, depth);
            }
            for (i = a + 1; i < b; i++) {
                ((SnippetPart) parts.get(i)).render(out, context, origin, depth);
            }
        }
    }
}
