package org.apache.commons.math4.util;

import com.example.duy.calculator.math_eval.Constants;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

public class CompositeFormat {
    private CompositeFormat() {
    }

    public static NumberFormat getDefaultNumberFormat() {
        return getDefaultNumberFormat(Locale.getDefault());
    }

    public static NumberFormat getDefaultNumberFormat(Locale locale) {
        NumberFormat nf = NumberFormat.getInstance(locale);
        nf.setMaximumFractionDigits(10);
        return nf;
    }

    public static void parseAndIgnoreWhitespace(String source, ParsePosition pos) {
        parseNextCharacter(source, pos);
        pos.setIndex(pos.getIndex() - 1);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static char parseNextCharacter(java.lang.String r6, java.text.ParsePosition r7) {
        /*
        r1 = r7.getIndex();
        r3 = r6.length();
        r4 = 0;
        if (r1 >= r3) goto L_0x0020;
    L_0x000b:
        r2 = r1 + 1;
        r0 = r6.charAt(r1);
        r5 = java.lang.Character.isWhitespace(r0);
        if (r5 == 0) goto L_0x0019;
    L_0x0017:
        if (r2 < r3) goto L_0x0023;
    L_0x0019:
        r7.setIndex(r2);
        if (r2 >= r3) goto L_0x0021;
    L_0x001e:
        r4 = r0;
        r1 = r2;
    L_0x0020:
        return r4;
    L_0x0021:
        r1 = r2;
        goto L_0x0020;
    L_0x0023:
        r1 = r2;
        goto L_0x000b;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.util.CompositeFormat.parseNextCharacter(java.lang.String, java.text.ParsePosition):char");
    }

    private static Number parseNumber(String source, double value, ParsePosition pos) {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.LEFT_PAREN);
        sb.append(value);
        sb.append(Constants.RIGHT_PAREN);
        int n = sb.length();
        int startIndex = pos.getIndex();
        int endIndex = startIndex + n;
        if (endIndex >= source.length() || source.substring(startIndex, endIndex).compareTo(sb.toString()) != 0) {
            return null;
        }
        Number ret = Double.valueOf(value);
        pos.setIndex(endIndex);
        return ret;
    }

    public static Number parseNumber(String source, NumberFormat format, ParsePosition pos) {
        int startIndex = pos.getIndex();
        Number number = format.parse(source, pos);
        if (startIndex == pos.getIndex()) {
            double[] special = new double[]{Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
            for (double parseNumber : special) {
                number = parseNumber(source, parseNumber, pos);
                if (number != null) {
                    break;
                }
            }
        }
        return number;
    }

    public static boolean parseFixedstring(String source, String expected, ParsePosition pos) {
        int startIndex = pos.getIndex();
        int endIndex = startIndex + expected.length();
        if (startIndex >= source.length() || endIndex > source.length() || source.substring(startIndex, endIndex).compareTo(expected) != 0) {
            pos.setIndex(startIndex);
            pos.setErrorIndex(startIndex);
            return false;
        }
        pos.setIndex(endIndex);
        return true;
    }

    public static StringBuffer formatDouble(double value, NumberFormat format, StringBuffer toAppendTo, FieldPosition pos) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            toAppendTo.append(Constants.LEFT_PAREN);
            toAppendTo.append(value);
            toAppendTo.append(Constants.RIGHT_PAREN);
        } else {
            format.format(value, toAppendTo, pos);
        }
        return toAppendTo;
    }
}
