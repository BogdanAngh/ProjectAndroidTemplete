package org.apache.commons.math4.fraction;

import com.getkeepsafe.taptargetview.R;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.MathParseException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.ValueServer;

public class FractionFormat extends AbstractFormat {
    private static final long serialVersionUID = 3008655719530972611L;

    public FractionFormat(NumberFormat format) {
        super(format);
    }

    public FractionFormat(NumberFormat numeratorFormat, NumberFormat denominatorFormat) {
        super(numeratorFormat, denominatorFormat);
    }

    public static Locale[] getAvailableLocales() {
        return NumberFormat.getAvailableLocales();
    }

    public static String formatFraction(Fraction f) {
        return getImproperInstance().format(f);
    }

    public static FractionFormat getImproperInstance() {
        return getImproperInstance(Locale.getDefault());
    }

    public static FractionFormat getImproperInstance(Locale locale) {
        return new FractionFormat(AbstractFormat.getDefaultNumberFormat(locale));
    }

    public static FractionFormat getProperInstance() {
        return getProperInstance(Locale.getDefault());
    }

    public static FractionFormat getProperInstance(Locale locale) {
        return new ProperFractionFormat(AbstractFormat.getDefaultNumberFormat(locale));
    }

    protected static NumberFormat getDefaultNumberFormat() {
        return AbstractFormat.getDefaultNumberFormat(Locale.getDefault());
    }

    public StringBuffer format(Fraction fraction, StringBuffer toAppendTo, FieldPosition pos) {
        pos.setBeginIndex(0);
        pos.setEndIndex(0);
        getNumeratorFormat().format((long) fraction.getNumerator(), toAppendTo, pos);
        toAppendTo.append(" / ");
        getDenominatorFormat().format((long) fraction.getDenominator(), toAppendTo, pos);
        return toAppendTo;
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) throws FractionConversionException, MathIllegalArgumentException {
        if (obj instanceof Fraction) {
            return format((Fraction) obj, toAppendTo, pos);
        }
        if (obj instanceof Number) {
            return format(new Fraction(((Number) obj).doubleValue()), toAppendTo, pos);
        }
        throw new MathIllegalArgumentException(LocalizedFormats.CANNOT_FORMAT_OBJECT_TO_FRACTION, new Object[0]);
    }

    public Fraction parse(String source) throws MathParseException {
        ParsePosition parsePosition = new ParsePosition(0);
        Fraction result = parse(source, parsePosition);
        if (parsePosition.getIndex() != 0) {
            return result;
        }
        throw new MathParseException(source, parsePosition.getErrorIndex(), Fraction.class);
    }

    public Fraction parse(String source, ParsePosition pos) {
        int initialIndex = pos.getIndex();
        AbstractFormat.parseAndIgnoreWhitespace(source, pos);
        Number num = getNumeratorFormat().parse(source, pos);
        if (num == null) {
            pos.setIndex(initialIndex);
            return null;
        }
        int startIndex = pos.getIndex();
        switch (AbstractFormat.parseNextCharacter(source, pos)) {
            case ValueServer.DIGEST_MODE /*0*/:
                return new Fraction(num.intValue(), 1);
            case R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /*47*/:
                AbstractFormat.parseAndIgnoreWhitespace(source, pos);
                Number den = getDenominatorFormat().parse(source, pos);
                if (den != null) {
                    return new Fraction(num.intValue(), den.intValue());
                }
                pos.setIndex(initialIndex);
                return null;
            default:
                pos.setIndex(initialIndex);
                pos.setErrorIndex(startIndex);
                return null;
        }
    }
}
