package edu.hws.jcm.data;

import java.io.Serializable;

public class ParserContext implements Serializable {
    public static final int END_OF_STRING = 1;
    public static final int IDENTIFIER = 3;
    private static final int NONE = 0;
    public static final int NUMBER = 2;
    public static final int OPCHARS = 4;
    public String data;
    public int options;
    public int pos;
    public ExpressionProgram prog;
    protected SymbolTable symbols;
    private StringBuffer tokBuf;
    public int token;
    public MathObject tokenObject;
    public String tokenString;
    public double tokenValue;

    public ParserContext(String data, int options, SymbolTable symbols) {
        this.tokBuf = new StringBuffer();
        this.data = data;
        this.options = options;
        this.symbols = symbols;
        this.prog = new ExpressionProgram();
    }

    public void mark() {
        this.symbols = new SymbolTable(this.symbols);
    }

    public void revert() {
        this.symbols = this.symbols.getParent();
    }

    public MathObject get(String name) {
        if ((this.options & END_OF_STRING) != 0) {
            return this.symbols.get(name);
        }
        return this.symbols.get(name.toLowerCase());
    }

    public void add(MathObject sym) {
        if ((this.options & END_OF_STRING) != 0) {
            this.symbols.add(sym);
        } else {
            this.symbols.add(sym.getName().toLowerCase(), sym);
        }
    }

    public int next() {
        int tok = look();
        if (this.token != END_OF_STRING) {
            this.token = NONE;
        }
        return tok;
    }

    public int look() {
        if (this.token == 0) {
            while (this.pos < this.data.length() && (this.data.charAt(this.pos) == Letters.SPACE || this.data.charAt(this.pos) == '\t')) {
                this.pos += END_OF_STRING;
            }
            if (this.pos >= this.data.length()) {
                this.token = END_OF_STRING;
                this.tokenString = null;
            } else {
                readToken();
            }
        }
        return this.token;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void readToken() {
        /*
        r14 = this;
        r13 = 61;
        r12 = 60;
        r11 = 46;
        r10 = 0;
        r9 = 4;
        r7 = r14.data;
        r8 = r14.pos;
        r0 = r7.charAt(r8);
        r5 = r14.pos;
        r7 = r14.tokBuf;
        r7.setLength(r10);
        r7 = java.lang.Character.isLetter(r0);
        if (r7 != 0) goto L_0x0027;
    L_0x001d:
        r7 = 95;
        if (r0 != r7) goto L_0x00da;
    L_0x0021:
        r7 = r14.options;
        r7 = r7 & 128;
        if (r7 != 0) goto L_0x00da;
    L_0x0027:
        r7 = 3;
        r14.token = r7;
    L_0x002a:
        r7 = java.lang.Character.isLetter(r0);
        if (r7 != 0) goto L_0x0046;
    L_0x0030:
        r7 = 95;
        if (r0 != r7) goto L_0x003a;
    L_0x0034:
        r7 = r14.options;
        r7 = r7 & 128;
        if (r7 == 0) goto L_0x0046;
    L_0x003a:
        r7 = java.lang.Character.isDigit(r0);
        if (r7 == 0) goto L_0x005b;
    L_0x0040:
        r7 = r14.options;
        r7 = r7 & 256;
        if (r7 != 0) goto L_0x005b;
    L_0x0046:
        r7 = r14.tokBuf;
        r7.append(r0);
        r7 = r14.pos;
        r7 = r7 + 1;
        r14.pos = r7;
        r7 = r14.pos;
        r8 = r14.data;
        r8 = r8.length();
        if (r7 < r8) goto L_0x008d;
    L_0x005b:
        r7 = r14.tokBuf;
        r7 = r7.toString();
        r14.tokenString = r7;
        r7 = 0;
        r14.tokenObject = r7;
        r7 = r14.tokenString;
        r1 = r7.length();
    L_0x006c:
        if (r1 <= 0) goto L_0x008c;
    L_0x006e:
        r7 = r14.tokenString;
        r6 = r7.substring(r10, r1);
        r7 = r14.options;
        r7 = r7 & 32;
        if (r7 == 0) goto L_0x00bc;
    L_0x007a:
        r7 = "and";
        r7 = r6.equalsIgnoreCase(r7);
        if (r7 == 0) goto L_0x0096;
    L_0x0082:
        r14.token = r9;
        r7 = "&";
        r14.tokenString = r7;
        r7 = r5 + 3;
        r14.pos = r7;
    L_0x008c:
        return;
    L_0x008d:
        r7 = r14.data;
        r8 = r14.pos;
        r0 = r7.charAt(r8);
        goto L_0x002a;
    L_0x0096:
        r7 = "or";
        r7 = r6.equalsIgnoreCase(r7);
        if (r7 == 0) goto L_0x00a9;
    L_0x009e:
        r14.token = r9;
        r7 = "|";
        r14.tokenString = r7;
        r7 = r5 + 2;
        r14.pos = r7;
        goto L_0x008c;
    L_0x00a9:
        r7 = "not";
        r7 = r6.equalsIgnoreCase(r7);
        if (r7 == 0) goto L_0x00bc;
    L_0x00b1:
        r14.token = r9;
        r7 = "~";
        r14.tokenString = r7;
        r7 = r5 + 3;
        r14.pos = r7;
        goto L_0x008c;
    L_0x00bc:
        r7 = r14.get(r6);
        if (r7 == 0) goto L_0x00d1;
    L_0x00c2:
        r14.tokenString = r6;
        r7 = r14.tokenString;
        r7 = r14.get(r7);
        r14.tokenObject = r7;
        r7 = r5 + r1;
        r14.pos = r7;
        goto L_0x008c;
    L_0x00d1:
        r7 = r14.options;
        r7 = r7 & 4;
        if (r7 == 0) goto L_0x008c;
    L_0x00d7:
        r1 = r1 + -1;
        goto L_0x006c;
    L_0x00da:
        r7 = java.lang.Character.isDigit(r0);
        if (r7 != 0) goto L_0x00fe;
    L_0x00e0:
        if (r0 != r11) goto L_0x02b9;
    L_0x00e2:
        r7 = r14.pos;
        r8 = r14.data;
        r8 = r8.length();
        r8 = r8 + -1;
        if (r7 >= r8) goto L_0x02b9;
    L_0x00ee:
        r7 = r14.data;
        r8 = r14.pos;
        r8 = r8 + 1;
        r7 = r7.charAt(r8);
        r7 = java.lang.Character.isDigit(r7);
        if (r7 == 0) goto L_0x02b9;
    L_0x00fe:
        r7 = 2;
        r14.token = r7;
    L_0x0101:
        r7 = r14.pos;
        r8 = r14.data;
        r8 = r8.length();
        if (r7 >= r8) goto L_0x012b;
    L_0x010b:
        r7 = r14.data;
        r8 = r14.pos;
        r7 = r7.charAt(r8);
        r7 = java.lang.Character.isDigit(r7);
        if (r7 == 0) goto L_0x012b;
    L_0x0119:
        r7 = r14.tokBuf;
        r8 = r14.data;
        r9 = r14.pos;
        r10 = r9 + 1;
        r14.pos = r10;
        r8 = r8.charAt(r9);
        r7.append(r8);
        goto L_0x0101;
    L_0x012b:
        r7 = r14.pos;
        r8 = r14.data;
        r8 = r8.length();
        if (r7 >= r8) goto L_0x017a;
    L_0x0135:
        r7 = r14.data;
        r8 = r14.pos;
        r7 = r7.charAt(r8);
        if (r7 != r11) goto L_0x017a;
    L_0x013f:
        r7 = r14.tokBuf;
        r8 = r14.data;
        r9 = r14.pos;
        r10 = r9 + 1;
        r14.pos = r10;
        r8 = r8.charAt(r9);
        r7.append(r8);
    L_0x0150:
        r7 = r14.pos;
        r8 = r14.data;
        r8 = r8.length();
        if (r7 >= r8) goto L_0x017a;
    L_0x015a:
        r7 = r14.data;
        r8 = r14.pos;
        r7 = r7.charAt(r8);
        r7 = java.lang.Character.isDigit(r7);
        if (r7 == 0) goto L_0x017a;
    L_0x0168:
        r7 = r14.tokBuf;
        r8 = r14.data;
        r9 = r14.pos;
        r10 = r9 + 1;
        r14.pos = r10;
        r8 = r8.charAt(r9);
        r7.append(r8);
        goto L_0x0150;
    L_0x017a:
        r7 = r14.pos;
        r8 = r14.data;
        r8 = r8.length();
        if (r7 >= r8) goto L_0x0227;
    L_0x0184:
        r7 = r14.data;
        r8 = r14.pos;
        r7 = r7.charAt(r8);
        r8 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        if (r7 == r8) goto L_0x019c;
    L_0x0190:
        r7 = r14.data;
        r8 = r14.pos;
        r7 = r7.charAt(r8);
        r8 = 69;
        if (r7 != r8) goto L_0x0227;
    L_0x019c:
        r5 = r14.pos;
        r7 = r14.tokBuf;
        r8 = r14.data;
        r9 = r14.pos;
        r10 = r9 + 1;
        r14.pos = r10;
        r8 = r8.charAt(r9);
        r7.append(r8);
        r7 = r14.pos;
        r8 = r14.data;
        r8 = r8.length();
        if (r7 >= r8) goto L_0x01e2;
    L_0x01b9:
        r7 = r14.data;
        r8 = r14.pos;
        r7 = r7.charAt(r8);
        r8 = 43;
        if (r7 == r8) goto L_0x01d1;
    L_0x01c5:
        r7 = r14.data;
        r8 = r14.pos;
        r7 = r7.charAt(r8);
        r8 = 45;
        if (r7 != r8) goto L_0x01e2;
    L_0x01d1:
        r7 = r14.tokBuf;
        r8 = r14.data;
        r9 = r14.pos;
        r10 = r9 + 1;
        r14.pos = r10;
        r8 = r8.charAt(r9);
        r7.append(r8);
    L_0x01e2:
        r7 = r14.pos;
        r8 = r14.data;
        r8 = r8.length();
        if (r7 >= r8) goto L_0x01fa;
    L_0x01ec:
        r7 = r14.data;
        r8 = r14.pos;
        r7 = r7.charAt(r8);
        r7 = java.lang.Character.isDigit(r7);
        if (r7 != 0) goto L_0x0260;
    L_0x01fa:
        r7 = r14.options;
        r7 = r7 & 2;
        if (r7 != 0) goto L_0x0225;
    L_0x0200:
        r7 = new edu.hws.jcm.data.ParseError;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "Illegal number, '";
        r8 = r8.append(r9);
        r9 = r14.tokBuf;
        r9 = r9.toString();
        r8 = r8.append(r9);
        r9 = "'.  No digits in exponential part.";
        r8 = r8.append(r9);
        r8 = r8.toString();
        r7.<init>(r8, r14);
        throw r7;
    L_0x0225:
        r14.pos = r5;
    L_0x0227:
        r7 = r14.tokBuf;
        r7 = r7.toString();
        r14.tokenString = r7;
        r7 = r14.tokenString;
        r2 = edu.hws.jcm.data.NumUtils.stringToReal(r7);
        r7 = java.lang.Double.isInfinite(r2);
        if (r7 == 0) goto L_0x028a;
    L_0x023b:
        r7 = new edu.hws.jcm.data.ParseError;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "The number '";
        r8 = r8.append(r9);
        r9 = r14.tokBuf;
        r9 = r9.toString();
        r8 = r8.append(r9);
        r9 = "' is outside the range of legal numbers.";
        r8 = r8.append(r9);
        r8 = r8.toString();
        r7.<init>(r8, r14);
        throw r7;
    L_0x0260:
        r7 = r14.pos;
        r8 = r14.data;
        r8 = r8.length();
        if (r7 >= r8) goto L_0x0227;
    L_0x026a:
        r7 = r14.data;
        r8 = r14.pos;
        r7 = r7.charAt(r8);
        r7 = java.lang.Character.isDigit(r7);
        if (r7 == 0) goto L_0x0227;
    L_0x0278:
        r7 = r14.tokBuf;
        r8 = r14.data;
        r9 = r14.pos;
        r10 = r9 + 1;
        r14.pos = r10;
        r8 = r8.charAt(r9);
        r7.append(r8);
        goto L_0x0260;
    L_0x028a:
        r7 = java.lang.Double.isNaN(r2);
        if (r7 == 0) goto L_0x02b5;
    L_0x0290:
        r7 = new edu.hws.jcm.data.ParseError;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "The string '";
        r8 = r8.append(r9);
        r9 = r14.tokBuf;
        r9 = r9.toString();
        r8 = r8.append(r9);
        r9 = "' is not a legal number.";
        r8 = r8.append(r9);
        r8 = r8.toString();
        r7.<init>(r8, r14);
        throw r7;
    L_0x02b5:
        r14.tokenValue = r2;
        goto L_0x008c;
    L_0x02b9:
        r14.token = r9;
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r8 = "";
        r7 = r7.append(r8);
        r7 = r7.append(r0);
        r7 = r7.toString();
        r14.tokenString = r7;
        r7 = r14.pos;
        r7 = r7 + 1;
        r14.pos = r7;
        r7 = r14.pos;
        r8 = r14.data;
        r8 = r8.length();
        if (r7 >= r8) goto L_0x008c;
    L_0x02e0:
        r7 = r14.data;
        r8 = r14.pos;
        r4 = r7.charAt(r8);
        switch(r0) {
            case 42: goto L_0x02ed;
            case 60: goto L_0x0326;
            case 61: goto L_0x02fd;
            case 62: goto L_0x034f;
            default: goto L_0x02eb;
        };
    L_0x02eb:
        goto L_0x008c;
    L_0x02ed:
        r7 = 42;
        if (r4 != r7) goto L_0x008c;
    L_0x02f1:
        r7 = "^";
        r14.tokenString = r7;
        r7 = r14.pos;
        r7 = r7 + 1;
        r14.pos = r7;
        goto L_0x008c;
    L_0x02fd:
        if (r4 == r12) goto L_0x0303;
    L_0x02ff:
        r7 = 62;
        if (r4 != r7) goto L_0x008c;
    L_0x0303:
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r8 = r14.data;
        r9 = r14.pos;
        r10 = r9 + 1;
        r14.pos = r10;
        r8 = r8.charAt(r9);
        r7 = r7.append(r8);
        r8 = r14.tokenString;
        r7 = r7.append(r8);
        r7 = r7.toString();
        r14.tokenString = r7;
        goto L_0x008c;
    L_0x0326:
        if (r4 == r13) goto L_0x032c;
    L_0x0328:
        r7 = 62;
        if (r4 != r7) goto L_0x008c;
    L_0x032c:
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r8 = r14.tokenString;
        r7 = r7.append(r8);
        r8 = r14.data;
        r9 = r14.pos;
        r10 = r9 + 1;
        r14.pos = r10;
        r8 = r8.charAt(r9);
        r7 = r7.append(r8);
        r7 = r7.toString();
        r14.tokenString = r7;
        goto L_0x008c;
    L_0x034f:
        if (r4 != r13) goto L_0x0374;
    L_0x0351:
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r8 = r14.tokenString;
        r7 = r7.append(r8);
        r8 = r14.data;
        r9 = r14.pos;
        r10 = r9 + 1;
        r14.pos = r10;
        r8 = r8.charAt(r9);
        r7 = r7.append(r8);
        r7 = r7.toString();
        r14.tokenString = r7;
        goto L_0x008c;
    L_0x0374:
        if (r4 != r12) goto L_0x008c;
    L_0x0376:
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r8 = r14.data;
        r9 = r14.pos;
        r10 = r9 + 1;
        r14.pos = r10;
        r8 = r8.charAt(r9);
        r7 = r7.append(r8);
        r8 = r14.tokenString;
        r7 = r7.append(r8);
        r7 = r7.toString();
        r14.tokenString = r7;
        goto L_0x008c;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.hws.jcm.data.ParserContext.readToken():void");
    }
}
