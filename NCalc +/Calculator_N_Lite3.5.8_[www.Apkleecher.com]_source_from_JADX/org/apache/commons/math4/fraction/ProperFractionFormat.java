package org.apache.commons.math4.fraction;

import com.getkeepsafe.taptargetview.R;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.ValueServer;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathUtils;

public class ProperFractionFormat extends FractionFormat {
    private static final long serialVersionUID = 760934726031766749L;
    private NumberFormat wholeFormat;

    public ProperFractionFormat() {
        this(FractionFormat.getDefaultNumberFormat());
    }

    public ProperFractionFormat(NumberFormat format) {
        this(format, (NumberFormat) format.clone(), (NumberFormat) format.clone());
    }

    public ProperFractionFormat(NumberFormat wholeFormat, NumberFormat numeratorFormat, NumberFormat denominatorFormat) {
        super(numeratorFormat, denominatorFormat);
        setWholeFormat(wholeFormat);
    }

    public StringBuffer format(Fraction fraction, StringBuffer toAppendTo, FieldPosition pos) {
        pos.setBeginIndex(0);
        pos.setEndIndex(0);
        int num = fraction.getNumerator();
        int den = fraction.getDenominator();
        int whole = num / den;
        num %= den;
        if (whole != 0) {
            getWholeFormat().format((long) whole, toAppendTo, pos);
            toAppendTo.append(Letters.SPACE);
            num = FastMath.abs(num);
        }
        getNumeratorFormat().format((long) num, toAppendTo, pos);
        toAppendTo.append(" / ");
        getDenominatorFormat().format((long) den, toAppendTo, pos);
        return toAppendTo;
    }

    public NumberFormat getWholeFormat() {
        return this.wholeFormat;
    }

    public Fraction parse(String source, ParsePosition pos) {
        Fraction ret = super.parse(source, pos);
        if (ret != null) {
            return ret;
        }
        int initialIndex = pos.getIndex();
        AbstractFormat.parseAndIgnoreWhitespace(source, pos);
        Number whole = getWholeFormat().parse(source, pos);
        if (whole == null) {
            pos.setIndex(initialIndex);
            return null;
        }
        AbstractFormat.parseAndIgnoreWhitespace(source, pos);
        Number num = getNumeratorFormat().parse(source, pos);
        if (num == null) {
            pos.setIndex(initialIndex);
            return null;
        } else if (num.intValue() < 0) {
            pos.setIndex(initialIndex);
            return null;
        } else {
            int startIndex = pos.getIndex();
            switch (AbstractFormat.parseNextCharacter(source, pos)) {
                case ValueServer.DIGEST_MODE /*0*/:
                    return new Fraction(num.intValue(), 1);
                case R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /*47*/:
                    AbstractFormat.parseAndIgnoreWhitespace(source, pos);
                    Number den = getDenominatorFormat().parse(source, pos);
                    if (den == null) {
                        pos.setIndex(initialIndex);
                        return null;
                    } else if (den.intValue() < 0) {
                        pos.setIndex(initialIndex);
                        return null;
                    } else {
                        int w = whole.intValue();
                        int n = num.intValue();
                        int d = den.intValue();
                        return new Fraction(((FastMath.abs(w) * d) + n) * MathUtils.copySign(1, w), d);
                    }
                default:
                    pos.setIndex(initialIndex);
                    pos.setErrorIndex(startIndex);
                    return null;
            }
        }
    }

    public void setWholeFormat(NumberFormat format) {
        if (format == null) {
            throw new NullArgumentException(LocalizedFormats.WHOLE_FORMAT, new Object[0]);
        }
        this.wholeFormat = format;
    }
}
