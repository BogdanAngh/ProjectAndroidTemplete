package com.x5.template.filters;

import com.example.duy.calculator.math_eval.Constants;
import com.x5.template.Chunk;
import com.x5.template.TemplateSet;
import org.apache.commons.math4.geometry.VectorFormat;

public class FilterArgs {
    private String[] filterArgs;
    private String filterName;
    private String rawArgs;
    private String rawInvocation;

    public FilterArgs(String filterInvocation) {
        this.rawInvocation = filterInvocation;
        init();
    }

    public String getFilterName() {
        return this.filterName;
    }

    public String[] getFilterArgs() {
        return this.filterArgs;
    }

    public String getUnparsedFilter() {
        return this.rawInvocation;
    }

    public String getUnparsedArgs() {
        return this.rawArgs;
    }

    private void init() {
        this.filterName = this.rawInvocation;
        int parenPos = this.rawInvocation.indexOf(40);
        int slashPos = this.rawInvocation.indexOf(47);
        if (slashPos > -1 && (parenPos < 0 || parenPos > slashPos)) {
            this.filterName = this.rawInvocation.substring(0, slashPos);
            this.rawArgs = this.rawInvocation.substring(slashPos);
            this.filterArgs = new String[]{this.rawArgs};
        } else if (parenPos > -1) {
            this.filterName = this.rawInvocation.substring(0, parenPos);
            int closeParenPos = this.rawInvocation.lastIndexOf(")");
            if (closeParenPos > parenPos) {
                this.rawArgs = this.rawInvocation.substring(parenPos + 1, closeParenPos);
                this.filterArgs = parseArgs(this.rawArgs);
            }
        }
    }

    private static String[] parseArgs(String parenthetical) {
        return parseArgs(parenthetical, true);
    }

    private static String[] parseArgs(String filter, boolean splitOnComma) {
        int quote2;
        int quote1 = filter.indexOf("\"");
        boolean isQuoted = true;
        if (quote1 < 0 || filter.substring(0, quote1).trim().length() > 0) {
            quote1 = -1;
            quote2 = filter.length();
            isQuoted = false;
        } else {
            quote2 = filter.indexOf("\"", quote1 + 1);
            if (quote2 < 0) {
                quote2 = filter.length();
            }
        }
        String arg0 = filter.substring(quote1 + 1, quote2);
        String arg1 = null;
        if (isQuoted) {
            int quote3 = filter.indexOf("\"", quote2 + 1);
            if (quote3 > 0) {
                int quote4 = filter.indexOf("\"", quote3 + 1);
                if (quote4 > 0) {
                    arg1 = filter.substring(quote3 + 1, quote4);
                }
            }
        }
        if (arg1 != null) {
            return new String[]{arg0, arg1};
        } else if (!isQuoted && splitOnComma && arg0.indexOf(",") >= 0) {
            return parseCommaDelimitedArgs(arg0);
        } else {
            return new String[]{arg0};
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String[] parseCommaDelimitedArgs(java.lang.String r24) {
        /*
        r22 = 15;
        r0 = r22;
        r7 = new java.lang.String[r0];
        r6 = 0;
        r16 = 0;
    L_0x0009:
        r0 = r7.length;
        r22 = r0;
        r0 = r22;
        if (r6 >= r0) goto L_0x001a;
    L_0x0010:
        r0 = r24;
        r1 = r16;
        r9 = nextArgDelim(r0, r1);
        if (r9 >= 0) goto L_0x0022;
    L_0x001a:
        r0 = r7.length;
        r22 = r0;
        r0 = r22;
        if (r6 != r0) goto L_0x00fb;
    L_0x0021:
        return r7;
    L_0x0022:
        r22 = "\"";
        r0 = r22;
        r1 = r24;
        r2 = r16;
        r18 = nextUnescapedDelim(r0, r1, r2);
        r22 = -1;
        r0 = r18;
        r1 = r22;
        if (r0 <= r1) goto L_0x0080;
    L_0x0036:
        r0 = r18;
        if (r0 >= r9) goto L_0x0080;
    L_0x003a:
        r0 = r24;
        r1 = r16;
        r2 = r18;
        r22 = r0.substring(r1, r2);
        r22 = r22.trim();
        r22 = r22.length();
        if (r22 != 0) goto L_0x0080;
    L_0x004e:
        r22 = "\"";
        r23 = r18 + 1;
        r0 = r22;
        r1 = r24;
        r2 = r23;
        r12 = nextUnescapedDelim(r0, r1, r2);
        if (r12 <= 0) goto L_0x0080;
    L_0x005e:
        r22 = r18 + 1;
        r0 = r24;
        r1 = r22;
        r5 = r0.substring(r1, r12);
        r7[r6] = r5;
        r6 = r6 + 1;
        r22 = r12 + 1;
        r0 = r24;
        r1 = r22;
        r9 = nextArgDelim(r0, r1);
        if (r9 <= 0) goto L_0x007b;
    L_0x0078:
        r16 = r9 + 1;
        goto L_0x0009;
    L_0x007b:
        r16 = r24.length();
        goto L_0x0009;
    L_0x0080:
        r0 = r24;
        r1 = r16;
        r20 = com.x5.template.filters.RegexFilter.nextRegexDelim(r0, r1);
        r22 = -1;
        r0 = r20;
        r1 = r22;
        if (r0 <= r1) goto L_0x00e4;
    L_0x0090:
        r0 = r20;
        if (r0 >= r9) goto L_0x00e4;
    L_0x0094:
        r0 = r24;
        r1 = r16;
        r2 = r20;
        r22 = r0.substring(r1, r2);
        r19 = r22.trim();
        r22 = r19.length();
        if (r22 == 0) goto L_0x00b4;
    L_0x00a8:
        r22 = "m";
        r0 = r19;
        r1 = r22;
        r22 = r0.equals(r1);
        if (r22 == 0) goto L_0x00e4;
    L_0x00b4:
        r22 = r20 + 1;
        r0 = r24;
        r1 = r22;
        r13 = com.x5.template.filters.RegexFilter.nextRegexDelim(r0, r1);
        if (r13 <= 0) goto L_0x00e4;
    L_0x00c0:
        r22 = r13 + 1;
        r0 = r24;
        r1 = r22;
        r9 = nextArgDelim(r0, r1);
        r10 = r9;
        if (r9 >= 0) goto L_0x00e1;
    L_0x00cd:
        r10 = r24.length();
        r16 = r10;
    L_0x00d3:
        r0 = r24;
        r1 = r20;
        r5 = r0.substring(r1, r10);
        r7[r6] = r5;
        r6 = r6 + 1;
        goto L_0x0009;
    L_0x00e1:
        r16 = r9 + 1;
        goto L_0x00d3;
    L_0x00e4:
        r0 = r24;
        r1 = r16;
        r5 = r0.substring(r1, r9);
        r7[r6] = r5;
        r6 = r6 + 1;
        r16 = r9 + 1;
        r0 = r24;
        r1 = r16;
        nextArgDelim(r0, r1);
        goto L_0x0009;
    L_0x00fb:
        r22 = ")";
        r0 = r22;
        r1 = r24;
        r2 = r16;
        r8 = nextUnescapedDelim(r0, r1, r2);
        r15 = r24.length();
        if (r8 <= 0) goto L_0x010e;
    L_0x010d:
        r15 = r8;
    L_0x010e:
        r0 = r24;
        r1 = r16;
        r14 = r0.substring(r1, r15);
        r7[r6] = r14;
        r6 = r6 + 1;
        r22 = r6 + 1;
        r0 = r7.length;
        r23 = r0;
        r0 = r22;
        r1 = r23;
        if (r0 >= r1) goto L_0x018e;
    L_0x0125:
        if (r8 <= 0) goto L_0x018e;
    L_0x0127:
        r22 = r8 + 1;
        r23 = r24.length();
        r0 = r22;
        r1 = r23;
        if (r0 >= r1) goto L_0x018e;
    L_0x0133:
        r22 = 40;
        r23 = r8 + 1;
        r0 = r24;
        r1 = r22;
        r2 = r23;
        r17 = r0.indexOf(r1, r2);
        if (r17 <= 0) goto L_0x018e;
    L_0x0143:
        r22 = r8 + 1;
        r0 = r24;
        r1 = r22;
        r2 = r17;
        r4 = r0.substring(r1, r2);
        r22 = new java.lang.StringBuilder;
        r22.<init>();
        r23 = "|";
        r22 = r22.append(r23);
        r0 = r22;
        r22 = r0.append(r4);
        r23 = "|";
        r22 = r22.append(r23);
        r22 = r22.toString();
        r7[r6] = r22;
        r6 = r6 + 1;
        r11 = r24.length();
        r22 = ")";
        r0 = r24;
        r1 = r22;
        r22 = r0.endsWith(r1);
        if (r22 == 0) goto L_0x0180;
    L_0x017e:
        r11 = r11 + -1;
    L_0x0180:
        r22 = r17 + 1;
        r0 = r24;
        r1 = r22;
        r3 = r0.substring(r1, r11);
        r7[r6] = r3;
        r6 = r6 + 1;
    L_0x018e:
        r0 = new java.lang.String[r6];
        r21 = r0;
        r22 = 0;
        r23 = 0;
        r0 = r22;
        r1 = r21;
        r2 = r23;
        java.lang.System.arraycopy(r7, r0, r1, r2, r6);
        r7 = r21;
        goto L_0x0021;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.x5.template.filters.FilterArgs.parseCommaDelimitedArgs(java.lang.String):java.lang.String[]");
    }

    public static String magicBraces(Chunk context, String output) {
        if (output == null || output.length() == 0) {
            return output;
        }
        char firstChar = output.charAt(0);
        if (firstChar == '~' || firstChar == '$') {
            return context != null ? context.makeTag(output) : VectorFormat.DEFAULT_PREFIX + output + VectorFormat.DEFAULT_SUFFIX;
        } else if (firstChar == Constants.POWER_UNICODE || firstChar == '.') {
            if (context == null) {
                return TemplateSet.PROTOCOL_SHORTHAND + output.substring(1) + TemplateSet.DEFAULT_TAG_END;
            }
            return context.makeTag('.' + output.substring(1));
        } else if (firstChar == Constants.PLUS_UNICODE) {
            return VectorFormat.DEFAULT_PREFIX + output + VectorFormat.DEFAULT_SUFFIX;
        } else {
            return output;
        }
    }

    public static int nextArgDelim(String arglist, int searchFrom) {
        return nextUnescapedDelim(",", arglist, searchFrom);
    }

    public static int nextUnescapedDelim(String delim, String regex, int searchFrom) {
        int delimPos = regex.indexOf(delim, searchFrom);
        boolean isProvenDelimeter = false;
        while (!isProvenDelimeter) {
            int bsCount = 0;
            while (delimPos - (bsCount + 1) >= searchFrom && regex.charAt(delimPos - (bsCount + 1)) == Letters.BACKSLASH) {
                bsCount++;
            }
            if (bsCount % 2 == 0) {
                isProvenDelimeter = true;
            } else {
                delimPos = regex.indexOf(delim, delimPos + 1);
                if (delimPos < 0) {
                    return -1;
                }
            }
        }
        return delimPos;
    }

    public static int grokValidColonScanPoint(String wholeTag, int startHere) {
        if (wholeTag.charAt(startHere) == 's' && wholeTag.charAt(startHere + 1) == Constants.DIV_UNICODE) {
            return RegexFilter.nextRegexDelim(wholeTag, RegexFilter.nextRegexDelim(wholeTag, startHere + 2) + 1) + 1;
        }
        int closeParen;
        if (wholeTag.length() > startHere + 7 && wholeTag.substring(startHere, startHere + 7).equals("onmatch")) {
            boolean skippedArgs = false;
            startHere += 8;
            while (!skippedArgs) {
                int slashPos = wholeTag.indexOf("/", startHere);
                if (slashPos >= 0) {
                    slashPos = RegexFilter.nextRegexDelim(wholeTag, slashPos + 1);
                    if (slashPos < 0) {
                        break;
                    }
                    int commaPos = nextUnescapedDelim(",", wholeTag, slashPos + 1);
                    if (commaPos < 0) {
                        break;
                    }
                    int moreArgs = nextUnescapedDelim(",", wholeTag, commaPos + 1);
                    if (moreArgs < 0) {
                        closeParen = nextUnescapedDelim(")", wholeTag, commaPos + 1);
                        if (closeParen < 0) {
                            break;
                        } else if (wholeTag.length() <= closeParen + 8 || !wholeTag.substring(closeParen + 1, closeParen + 8).equals("nomatch")) {
                            return closeParen + 1;
                        } else {
                            startHere = closeParen + 1;
                            skippedArgs = true;
                        }
                    } else {
                        startHere = moreArgs + 1;
                    }
                } else {
                    break;
                }
            }
        }
        int openParen = wholeTag.indexOf("(", startHere);
        if (openParen < 0) {
            return startHere;
        }
        closeParen = nextUnescapedDelim(")", wholeTag, openParen + 1);
        if (closeParen < 0) {
            return startHere;
        }
        return closeParen + 1;
    }
}
