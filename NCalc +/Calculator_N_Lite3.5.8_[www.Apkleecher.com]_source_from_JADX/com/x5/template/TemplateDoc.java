package com.x5.template;

import com.example.duy.calculator.math_eval.Constants;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.math4.geometry.VectorFormat;

public class TemplateDoc implements Iterator<Doclet>, Iterable<Doclet> {
    private static final String COMMENT_END = "--}";
    private static final String COMMENT_START = "{!--";
    private static final Pattern LITERAL_CLOSE;
    public static final String LITERAL_END = "{^}";
    public static final String LITERAL_END_LONGHAND = "{/literal}";
    private static final String LITERAL_OPEN = "(\\{\\^\\^\\}|\\{[\\.\\^]literal\\}|\\{\\% *literal *\\%?\\})";
    private static final Pattern LITERAL_OPEN_ANYWHERE;
    private static final Pattern LITERAL_OPEN_HERE;
    public static final String LITERAL_SHORTHAND = "{^^}";
    public static final String LITERAL_START = "{^literal}";
    public static final String LITERAL_START2 = "{.literal}";
    public static final String MACRO_END = "{*}";
    public static final String MACRO_LET = "{=";
    public static final String MACRO_LET_END = "}";
    public static final String MACRO_NAME_END = "}";
    public static final String MACRO_START = "{*";
    private static final String SKIP_BLANK_LINE = "";
    private static final String SUB_END = "{#}";
    private static final String SUB_NAME_END = "}";
    private static final String SUB_START = "{#";
    private static final Pattern SUPER_TAG;
    private BufferedReader brTemp;
    private ArrayList<StringBuilder> bufferStack;
    private String encoding;
    private InputStream in;
    private String line;
    private ArrayList<String> lineStack;
    private ArrayList<String> nameStack;
    private Doclet queued;
    private StringBuilder rootTemplate;
    private String stub;

    public class Doclet {
        private String name;
        private String origin;
        private String rawTemplate;

        public Doclet(String name, String rawTemplate, String origin) {
            this.name = name;
            this.rawTemplate = rawTemplate;
            this.origin = origin;
        }

        public String getName() {
            return this.name;
        }

        public String getTemplate() {
            return this.rawTemplate;
        }

        public String getOrigin() {
            return this.origin;
        }

        public Snippet getSnippet() {
            return Snippet.getSnippet(this.rawTemplate, this.origin);
        }
    }

    public TemplateDoc(String name, String rawTemplate) {
        this.encoding = getDefaultEncoding();
        this.queued = null;
        this.rootTemplate = new StringBuilder();
        this.line = null;
        this.lineStack = new ArrayList();
        this.nameStack = new ArrayList();
        this.bufferStack = new ArrayList();
        this.stub = truncateNameToStub(name);
        try {
            this.in = new ByteArrayInputStream(rawTemplate.getBytes(this.encoding));
        } catch (UnsupportedEncodingException e) {
            this.in = new ByteArrayInputStream(rawTemplate.getBytes());
        }
    }

    public TemplateDoc(String name, InputStream in) {
        this.encoding = getDefaultEncoding();
        this.queued = null;
        this.rootTemplate = new StringBuilder();
        this.line = null;
        this.lineStack = new ArrayList();
        this.nameStack = new ArrayList();
        this.bufferStack = new ArrayList();
        this.stub = truncateNameToStub(name);
        this.in = in;
    }

    public Iterable<Doclet> parseTemplates(String encoding) throws IOException {
        this.encoding = encoding;
        this.brTemp = new BufferedReader(new InputStreamReader(this.in, encoding));
        return this;
    }

    static String truncateNameToStub(String name) {
        String stub;
        int slashPos = name.lastIndexOf(47);
        if (slashPos < -1) {
            slashPos = name.lastIndexOf(92);
        }
        String folder = null;
        if (slashPos > -1) {
            folder = name.substring(0, slashPos + 1);
            stub = name.substring(slashPos + 1);
        } else {
            stub = name;
        }
        int hashPos = stub.indexOf("#");
        if (hashPos > -1) {
            stub = stub.substring(0, hashPos);
        }
        if (slashPos <= -1) {
            return stub;
        }
        char fs = System.getProperty("file.separator").charAt(0);
        folder.replace(Letters.BACKSLASH, fs);
        folder.replace(Constants.DIV_UNICODE, fs);
        return folder + stub;
    }

    protected Doclet nextTemplate() throws IOException {
        if (this.rootTemplate == null) {
            return null;
        }
        Doclet subtpl;
        if (this.bufferStack.size() > 0) {
            subtpl = nextSubtemplate(popNameFromStack(), SKIP_BLANK_LINE);
            if (subtpl != null) {
                return subtpl;
            }
        }
        while (this.brTemp.ready()) {
            this.line = this.brTemp.readLine();
            if (this.line == null) {
                break;
            }
            int comPos = this.line.indexOf(COMMENT_START);
            int subPos = this.line.indexOf(SUB_START);
            while (comPos > -1 && (subPos < 0 || subPos > comPos)) {
                StringBuilder commentBuf = new StringBuilder();
                this.line = skipComment(comPos, this.line, this.brTemp, commentBuf);
                String beforeComment = this.line.substring(0, comPos);
                String afterComment = this.line.substring(comPos);
                this.rootTemplate.append(beforeComment);
                this.rootTemplate.append(commentBuf);
                this.line = afterComment;
                comPos = this.line.indexOf(COMMENT_START);
                subPos = this.line.indexOf(SUB_START);
            }
            subtpl = null;
            boolean lineFeed = true;
            if (subPos > -1 && this.line.indexOf(SUB_END) != subPos) {
                int subNameEnd = this.line.indexOf(SUB_NAME_END, SUB_START.length() + subPos);
                if (subNameEnd > -1) {
                    this.rootTemplate.append(this.line.substring(0, subPos));
                    String subName = this.line.substring(SUB_START.length() + subPos, subNameEnd);
                    subtpl = nextSubtemplate(this.stub + "#" + subName, this.line.substring(SUB_NAME_END.length() + subNameEnd));
                    if (this.line.length() < 1) {
                        lineFeed = false;
                    }
                }
            }
            if (lineFeed) {
                this.rootTemplate.append(this.line);
                this.rootTemplate.append("\n");
                continue;
            }
            if (subtpl != null) {
                return subtpl;
            }
        }
        String root = this.rootTemplate.toString();
        this.rootTemplate = null;
        return new Doclet(this.stub, root, this.stub);
    }

    private String getCommentLines(int comBegin, String firstLine, BufferedReader brTemp, StringBuilder sbTemp) throws IOException {
        int comEnd = firstLine.indexOf(COMMENT_END, comBegin + 2);
        int endMarkerLen = COMMENT_END.length();
        if (comEnd > -1) {
            comEnd += endMarkerLen;
            sbTemp.append(firstLine.substring(0, comEnd));
            return firstLine.substring(comEnd);
        }
        sbTemp.append(firstLine);
        sbTemp.append("\n");
        while (brTemp.ready()) {
            String line = brTemp.readLine();
            if (line == null) {
                break;
            }
            comEnd = line.indexOf(COMMENT_END);
            if (comEnd > -1) {
                comEnd += endMarkerLen;
                sbTemp.append(line.substring(0, comEnd));
                return line.substring(comEnd);
            }
            sbTemp.append(line);
            sbTemp.append("\n");
        }
        return SKIP_BLANK_LINE;
    }

    private String getLiteralLines(int litBegin, String firstLine, BufferedReader brTemp, StringBuilder sbTemp) throws IOException {
        Matcher m = LITERAL_CLOSE.matcher(firstLine);
        if (m.find(litBegin + 2)) {
            int litEnd = m.end();
            sbTemp.append(firstLine.substring(0, litEnd));
            return firstLine.substring(litEnd);
        }
        sbTemp.append(firstLine);
        sbTemp.append("\n");
        while (brTemp.ready()) {
            String line = brTemp.readLine();
            if (line == null) {
                break;
            }
            m.reset(line);
            if (m.find()) {
                litEnd = m.end();
                sbTemp.append(line.substring(0, litEnd));
                return line.substring(litEnd);
            }
            sbTemp.append(line);
            sbTemp.append("\n");
        }
        return SKIP_BLANK_LINE;
    }

    private String skipComment(int comPos, String firstLine, BufferedReader brTemp, StringBuilder commentBuf) throws IOException {
        String beforeComment = firstLine.substring(0, comPos);
        int comEndPos = firstLine.indexOf(COMMENT_END);
        if (comEndPos > -1) {
            comEndPos += COMMENT_END.length();
            commentBuf.append(firstLine.substring(comPos, comEndPos));
            return beforeComment + firstLine.substring(comEndPos);
        }
        commentBuf.append(firstLine.substring(comPos));
        commentBuf.append("\n");
        while (brTemp.ready()) {
            String line = brTemp.readLine();
            if (line == null) {
                return beforeComment;
            }
            comEndPos = line.indexOf(COMMENT_END);
            if (comEndPos > -1) {
                comEndPos += COMMENT_END.length();
                commentBuf.append(line.substring(0, comEndPos));
                return beforeComment + line.substring(comEndPos);
            }
            commentBuf.append(line);
            commentBuf.append("\n");
        }
        return beforeComment;
    }

    private String stripComment(int comPos, String firstLine, BufferedReader brTemp) throws IOException {
        String beforeComment = firstLine.substring(0, comPos);
        int comEndPos = firstLine.indexOf(COMMENT_END);
        if (comEndPos > -1) {
            return beforeComment + firstLine.substring(comEndPos + COMMENT_END.length());
        }
        while (brTemp.ready()) {
            String line = brTemp.readLine();
            if (line == null) {
                return beforeComment;
            }
            comEndPos = line.indexOf(COMMENT_END);
            if (comEndPos > -1) {
                return beforeComment + line.substring(comEndPos + COMMENT_END.length());
            }
        }
        return beforeComment;
    }

    public static int findLiteralMarker(String text) {
        return findLiteralMarker(text, 0);
    }

    public static int findLiteralMarker(String text, int startAt) {
        Matcher m = LITERAL_OPEN_ANYWHERE.matcher(text);
        if (m.find(startAt)) {
            return m.start();
        }
        return -1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.x5.template.TemplateDoc.Doclet nextSubtemplate(java.lang.String r13, java.lang.String r14) throws java.io.IOException {
        /*
        r12 = this;
        r11 = -1;
        r10 = r12.bufferStack;
        r10 = r10.size();
        if (r10 <= 0) goto L_0x004f;
    L_0x0009:
        r7 = r12.popBufferFromStack();
    L_0x000d:
        r10 = "{#}";
        r9 = r14.indexOf(r10);
        r10 = "{!--";
        r0 = r14.indexOf(r10);
        r5 = findLiteralMarker(r14);
        r8 = 0;
    L_0x001e:
        if (r5 > r11) goto L_0x0022;
    L_0x0020:
        if (r0 <= r11) goto L_0x002c;
    L_0x0022:
        if (r9 <= r11) goto L_0x0055;
    L_0x0024:
        if (r5 < 0) goto L_0x0028;
    L_0x0026:
        if (r9 >= r5) goto L_0x0055;
    L_0x0028:
        if (r0 < 0) goto L_0x002c;
    L_0x002a:
        if (r9 >= r0) goto L_0x0055;
    L_0x002c:
        if (r9 <= r11) goto L_0x00ac;
    L_0x002e:
        r10 = 0;
        r10 = r14.substring(r10, r9);
        r7.append(r10);
        r10 = "{#}";
        r10 = r10.length();
        r10 = r10 + r9;
        r10 = r14.substring(r10);
        r12.line = r10;
        r6 = new com.x5.template.TemplateDoc$Doclet;
        r10 = r7.toString();
        r11 = r12.stub;
        r6.<init>(r13, r10, r11);
    L_0x004e:
        return r6;
    L_0x004f:
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        goto L_0x000d;
    L_0x0055:
        if (r5 <= r11) goto L_0x0076;
    L_0x0057:
        if (r0 < 0) goto L_0x005b;
    L_0x0059:
        if (r0 <= r5) goto L_0x0076;
    L_0x005b:
        if (r9 < 0) goto L_0x005f;
    L_0x005d:
        if (r9 <= r5) goto L_0x0076;
    L_0x005f:
        r10 = r12.brTemp;
        r14 = r12.getLiteralLines(r5, r14, r10, r7);
        r10 = "{!--";
        r0 = r14.indexOf(r10);
        r10 = "{#}";
        r9 = r14.indexOf(r10);
        r5 = findLiteralMarker(r14);
        goto L_0x0055;
    L_0x0076:
        if (r0 <= r11) goto L_0x001e;
    L_0x0078:
        if (r9 < 0) goto L_0x007c;
    L_0x007a:
        if (r9 <= r0) goto L_0x001e;
    L_0x007c:
        if (r5 < 0) goto L_0x0080;
    L_0x007e:
        if (r5 <= r0) goto L_0x001e;
    L_0x0080:
        r3 = r14.length();
        r10 = r12.brTemp;
        r14 = r12.stripComment(r0, r14, r10);
        r2 = r14.length();
        if (r3 == r2) goto L_0x009b;
    L_0x0090:
        r10 = r14.trim();
        r10 = r10.length();
        if (r10 != 0) goto L_0x009b;
    L_0x009a:
        r8 = 1;
    L_0x009b:
        r10 = "{!--";
        r0 = r14.indexOf(r10);
        r10 = "{#}";
        r9 = r14.indexOf(r10);
        r5 = findLiteralMarker(r14);
        goto L_0x0076;
    L_0x00ac:
        if (r8 != 0) goto L_0x00c4;
    L_0x00ae:
        r7.append(r14);
        r10 = r12.brTemp;
        r10 = r10.ready();
        if (r10 == 0) goto L_0x00c4;
    L_0x00b9:
        r10 = r14.length();
        if (r10 <= 0) goto L_0x00c4;
    L_0x00bf:
        r10 = "\n";
        r7.append(r10);
    L_0x00c4:
        r10 = r12.brTemp;
        r10 = r10.ready();
        if (r10 == 0) goto L_0x00d8;
    L_0x00cc:
        r6 = r12.getNestedTemplate(r13, r7);	 Catch:{ EndOfSnippetException -> 0x00fe }
        if (r6 != 0) goto L_0x004e;
    L_0x00d2:
        r4 = r12.popLineFromStack();	 Catch:{ EndOfSnippetException -> 0x00fe }
        if (r4 != 0) goto L_0x00e9;
    L_0x00d8:
        r10 = "";
        r12.line = r10;
        r6 = new com.x5.template.TemplateDoc$Doclet;
        r10 = r7.toString();
        r11 = r12.stub;
        r6.<init>(r13, r10, r11);
        goto L_0x004e;
    L_0x00e9:
        r10 = "";
        if (r4 == r10) goto L_0x00c4;
    L_0x00ed:
        r7.append(r4);	 Catch:{ EndOfSnippetException -> 0x00fe }
        r10 = r12.brTemp;	 Catch:{ EndOfSnippetException -> 0x00fe }
        r10 = r10.ready();	 Catch:{ EndOfSnippetException -> 0x00fe }
        if (r10 == 0) goto L_0x00c4;
    L_0x00f8:
        r10 = "\n";
        r7.append(r10);	 Catch:{ EndOfSnippetException -> 0x00fe }
        goto L_0x00c4;
    L_0x00fe:
        r1 = move-exception;
        r10 = r1.getRestOfLine();
        r12.line = r10;
        r6 = new com.x5.template.TemplateDoc$Doclet;
        r10 = r7.toString();
        r11 = r12.stub;
        r6.<init>(r13, r10, r11);
        goto L_0x004e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.x5.template.TemplateDoc.nextSubtemplate(java.lang.String, java.lang.String):com.x5.template.TemplateDoc$Doclet");
    }

    private StringBuilder popBufferFromStack() {
        if (this.bufferStack.size() > 0) {
            return (StringBuilder) this.bufferStack.remove(this.bufferStack.size() - 1);
        }
        return null;
    }

    private String popLineFromStack() {
        return popStringFromStack(this.lineStack);
    }

    private String popNameFromStack() {
        return popStringFromStack(this.nameStack);
    }

    private String popStringFromStack(ArrayList<String> stack) {
        if (stack.size() > 0) {
            return (String) stack.remove(stack.size() - 1);
        }
        return null;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.x5.template.TemplateDoc.Doclet getNestedTemplate(java.lang.String r14, java.lang.StringBuilder r15) throws java.io.IOException, com.x5.template.EndOfSnippetException {
        /*
        r13 = this;
        r12 = 0;
        r3 = 0;
        r11 = -1;
        r9 = r13.brTemp;
        r1 = r9.readLine();
        if (r1 != 0) goto L_0x0011;
    L_0x000b:
        r9 = r13.lineStack;
        r9.add(r3);
    L_0x0010:
        return r3;
    L_0x0011:
        r9 = "{!--";
        r0 = r1.indexOf(r9);
        r9 = "{#";
        r8 = r1.indexOf(r9);
        r9 = "{#}";
        r5 = r1.indexOf(r9);
        r2 = findLiteralMarker(r1);
    L_0x0027:
        if (r2 > r11) goto L_0x002b;
    L_0x0029:
        if (r0 <= r11) goto L_0x0035;
    L_0x002b:
        if (r5 <= r11) goto L_0x0057;
    L_0x002d:
        if (r2 < 0) goto L_0x0031;
    L_0x002f:
        if (r5 >= r2) goto L_0x0057;
    L_0x0031:
        if (r0 < 0) goto L_0x0035;
    L_0x0033:
        if (r0 >= r5) goto L_0x0057;
    L_0x0035:
        if (r8 > r11) goto L_0x0039;
    L_0x0037:
        if (r5 <= r11) goto L_0x0116;
    L_0x0039:
        if (r5 <= r11) goto L_0x00b3;
    L_0x003b:
        if (r8 == r11) goto L_0x003f;
    L_0x003d:
        if (r5 > r8) goto L_0x00b3;
    L_0x003f:
        r9 = r1.substring(r12, r5);
        r15.append(r9);
        r9 = new com.x5.template.EndOfSnippetException;
        r10 = "{#}";
        r10 = r10.length();
        r10 = r10 + r5;
        r10 = r1.substring(r10);
        r9.<init>(r10);
        throw r9;
    L_0x0057:
        if (r8 <= r11) goto L_0x0061;
    L_0x0059:
        if (r2 < 0) goto L_0x005d;
    L_0x005b:
        if (r8 >= r2) goto L_0x0061;
    L_0x005d:
        if (r0 < 0) goto L_0x0035;
    L_0x005f:
        if (r0 < r8) goto L_0x0035;
    L_0x0061:
        if (r2 <= r11) goto L_0x0088;
    L_0x0063:
        if (r0 < 0) goto L_0x0067;
    L_0x0065:
        if (r0 <= r2) goto L_0x0088;
    L_0x0067:
        if (r5 < 0) goto L_0x006b;
    L_0x0069:
        if (r5 <= r2) goto L_0x0088;
    L_0x006b:
        r9 = r13.brTemp;
        r1 = r13.getLiteralLines(r2, r1, r9, r15);
        r9 = "{!--";
        r0 = r1.indexOf(r9);
        r9 = "{#";
        r8 = r1.indexOf(r9);
        r9 = "{#}";
        r5 = r1.indexOf(r9);
        r2 = findLiteralMarker(r1);
        goto L_0x0061;
    L_0x0088:
        if (r0 <= r11) goto L_0x0027;
    L_0x008a:
        if (r8 < 0) goto L_0x008e;
    L_0x008c:
        if (r8 <= r0) goto L_0x0027;
    L_0x008e:
        if (r5 < 0) goto L_0x0092;
    L_0x0090:
        if (r5 <= r0) goto L_0x0027;
    L_0x0092:
        if (r2 < 0) goto L_0x0096;
    L_0x0094:
        if (r2 <= r0) goto L_0x0027;
    L_0x0096:
        r9 = r13.brTemp;
        r1 = r13.getCommentLines(r0, r1, r9, r15);
        r9 = "{!--";
        r0 = r1.indexOf(r9);
        r9 = "{#";
        r8 = r1.indexOf(r9);
        r9 = "{#}";
        r5 = r1.indexOf(r9);
        r2 = findLiteralMarker(r1);
        goto L_0x0088;
    L_0x00b3:
        if (r8 <= r11) goto L_0x0116;
    L_0x00b5:
        r9 = "}";
        r10 = "{#";
        r10 = r10.length();
        r10 = r10 + r8;
        r7 = r1.indexOf(r9, r10);
        if (r7 <= r11) goto L_0x0116;
    L_0x00c4:
        r9 = r1.substring(r12, r8);
        r15.append(r9);
        r9 = "{#";
        r9 = r9.length();
        r9 = r9 + r8;
        r6 = r1.substring(r9, r7);
        r9 = "}";
        r9 = r9.length();
        r9 = r9 + r7;
        r4 = r1.substring(r9);
        r9 = r13.bufferStack;
        r9.add(r15);
        r9 = r13.nameStack;
        r9.add(r14);
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r9 = r9.append(r14);
        r10 = "#";
        r9 = r9.append(r10);
        r9 = r9.append(r6);
        r9 = r9.toString();
        r3 = r13.nextSubtemplate(r9, r4);
        r9 = r1.length();
        r10 = 1;
        if (r9 >= r10) goto L_0x0010;
    L_0x010d:
        r9 = r13.lineStack;
        r10 = "";
        r9.add(r10);
        goto L_0x0010;
    L_0x0116:
        r9 = r13.lineStack;
        r9.add(r1);
        goto L_0x0010;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.x5.template.TemplateDoc.getNestedTemplate(java.lang.String, java.lang.StringBuilder):com.x5.template.TemplateDoc$Doclet");
    }

    static {
        SUPER_TAG = Pattern.compile("\\{\\% *super *\\%?\\}");
        LITERAL_OPEN_HERE = Pattern.compile("\\G(\\{\\^\\^\\}|\\{[\\.\\^]literal\\}|\\{\\% *literal *\\%?\\})");
        LITERAL_OPEN_ANYWHERE = Pattern.compile(LITERAL_OPEN);
        LITERAL_CLOSE = Pattern.compile("(\\{\\^\\}|\\{/literal\\}|\\{\\% *endliteral *\\%?\\})");
    }

    public static StringBuilder expandShorthand(String name, StringBuilder template) {
        if (template.indexOf("{^super}") > -1 || template.indexOf("{.super}") > -1) {
            return null;
        }
        if (SUPER_TAG.matcher(template).find()) {
            return null;
        }
        int cursor = template.indexOf(VectorFormat.DEFAULT_PREFIX);
        while (cursor > -1 && template.length() != cursor + 1) {
            char afterBrace = template.charAt(cursor + 1);
            if (afterBrace == Constants.POWER_UNICODE || afterBrace == '.' || afterBrace == '%') {
                int afterLiteralBlock = skipLiterals(template, cursor);
                if (afterLiteralBlock != cursor) {
                    cursor = afterLiteralBlock;
                } else {
                    if (afterBrace != '%') {
                        template.replace(cursor + 1, cursor + 2, "~.");
                    } else {
                        int exprStart = cursor + 2;
                        while (exprStart < template.length() && Character.isWhitespace(template.charAt(exprStart))) {
                            exprStart++;
                        }
                        if (Snippet.MAGIC_CHARS.indexOf(template.charAt(exprStart)) < 0) {
                            template.replace(exprStart, exprStart, "~.");
                        }
                    }
                    cursor += 2;
                }
            } else if (afterBrace == Constants.DIV_UNICODE) {
                template.replace(cursor + 1, cursor + 2, "~./");
            } else {
                cursor += 2;
            }
            if (cursor > -1) {
                cursor = template.indexOf(VectorFormat.DEFAULT_PREFIX, cursor);
            }
        }
        return template;
    }

    private static int skipLiterals(StringBuilder template, int cursor) {
        int scanStart = cursor;
        Matcher m = LITERAL_OPEN_HERE.matcher(template);
        if (m.find(scanStart)) {
            scanStart = m.end();
        }
        if (scanStart <= cursor) {
            return cursor;
        }
        m = LITERAL_CLOSE.matcher(template);
        if (m.find(scanStart)) {
            return m.end();
        }
        return template.length();
    }

    public static int nextUnescapedDelim(String delim, StringBuilder sb, int searchFrom) {
        int delimPos = sb.indexOf(delim, searchFrom);
        boolean isProvenDelimeter = false;
        while (!isProvenDelimeter) {
            int bsCount = 0;
            while (delimPos - (bsCount + 1) >= searchFrom && sb.charAt(delimPos - (bsCount + 1)) == Letters.BACKSLASH) {
                bsCount++;
            }
            if (bsCount % 2 == 0) {
                isProvenDelimeter = true;
            } else {
                delimPos = sb.indexOf(delim, delimPos + 1);
                if (delimPos < 0) {
                    return -1;
                }
            }
        }
        return delimPos;
    }

    public boolean hasNext() {
        if (this.queued != null) {
            return true;
        }
        try {
            this.queued = nextTemplate();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        if (this.queued == null) {
            return false;
        }
        return true;
    }

    public Doclet next() {
        if (this.queued != null) {
            Doclet nextDoc = this.queued;
            this.queued = null;
            return nextDoc;
        }
        try {
            return nextTemplate();
        } catch (IOException e) {
            e.printStackTrace(System.err);
            return null;
        }
    }

    public void remove() {
    }

    public Iterator<Doclet> iterator() {
        return this;
    }

    static String getDefaultEncoding() {
        String override = System.getProperty("chunk.template.charset");
        if (override == null) {
            return "UTF-8";
        }
        if (override.equalsIgnoreCase("SYSTEM")) {
            return Charset.defaultCharset().toString();
        }
        return override;
    }
}
