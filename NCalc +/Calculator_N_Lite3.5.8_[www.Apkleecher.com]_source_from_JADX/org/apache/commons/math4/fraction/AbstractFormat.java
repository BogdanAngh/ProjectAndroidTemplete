package org.apache.commons.math4.fraction;

import java.io.Serializable;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;

public abstract class AbstractFormat extends NumberFormat implements Serializable {
    private static final long serialVersionUID = -6981118387974191891L;
    private NumberFormat denominatorFormat;
    private NumberFormat numeratorFormat;

    protected AbstractFormat() {
        this(getDefaultNumberFormat());
    }

    protected AbstractFormat(NumberFormat format) {
        this(format, (NumberFormat) format.clone());
    }

    protected AbstractFormat(NumberFormat numeratorFormat, NumberFormat denominatorFormat) {
        this.numeratorFormat = numeratorFormat;
        this.denominatorFormat = denominatorFormat;
    }

    protected static NumberFormat getDefaultNumberFormat() {
        return getDefaultNumberFormat(Locale.getDefault());
    }

    protected static NumberFormat getDefaultNumberFormat(Locale locale) {
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        nf.setMaximumFractionDigits(0);
        nf.setParseIntegerOnly(true);
        return nf;
    }

    public NumberFormat getDenominatorFormat() {
        return this.denominatorFormat;
    }

    public NumberFormat getNumeratorFormat() {
        return this.numeratorFormat;
    }

    public void setDenominatorFormat(NumberFormat format) {
        if (format == null) {
            throw new NullArgumentException(LocalizedFormats.DENOMINATOR_FORMAT, new Object[0]);
        }
        this.denominatorFormat = format;
    }

    public void setNumeratorFormat(NumberFormat format) {
        if (format == null) {
            throw new NullArgumentException(LocalizedFormats.NUMERATOR_FORMAT, new Object[0]);
        }
        this.numeratorFormat = format;
    }

    protected static void parseAndIgnoreWhitespace(String source, ParsePosition pos) {
        parseNextCharacter(source, pos);
        pos.setIndex(pos.getIndex() - 1);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static char parseNextCharacter(java.lang.String r6, java.text.ParsePosition r7) {
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
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.fraction.AbstractFormat.parseNextCharacter(java.lang.String, java.text.ParsePosition):char");
    }

    public StringBuffer format(double value, StringBuffer buffer, FieldPosition position) {
        return format(Double.valueOf(value), buffer, position);
    }

    public StringBuffer format(long value, StringBuffer buffer, FieldPosition position) {
        return format(Long.valueOf(value), buffer, position);
    }
}
