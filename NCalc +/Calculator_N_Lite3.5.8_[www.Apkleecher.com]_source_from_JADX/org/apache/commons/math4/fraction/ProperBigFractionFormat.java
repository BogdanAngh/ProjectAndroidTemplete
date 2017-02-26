package org.apache.commons.math4.fraction;

import com.getkeepsafe.taptargetview.R;
import java.math.BigInteger;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.ValueServer;

public class ProperBigFractionFormat extends BigFractionFormat {
    private static final long serialVersionUID = -6337346779577272307L;
    private NumberFormat wholeFormat;

    public ProperBigFractionFormat() {
        this(AbstractFormat.getDefaultNumberFormat());
    }

    public ProperBigFractionFormat(NumberFormat format) {
        this(format, (NumberFormat) format.clone(), (NumberFormat) format.clone());
    }

    public ProperBigFractionFormat(NumberFormat wholeFormat, NumberFormat numeratorFormat, NumberFormat denominatorFormat) {
        super(numeratorFormat, denominatorFormat);
        setWholeFormat(wholeFormat);
    }

    public StringBuffer format(BigFraction fraction, StringBuffer toAppendTo, FieldPosition pos) {
        pos.setBeginIndex(0);
        pos.setEndIndex(0);
        BigInteger num = fraction.getNumerator();
        BigInteger den = fraction.getDenominator();
        BigInteger whole = num.divide(den);
        num = num.remainder(den);
        if (!BigInteger.ZERO.equals(whole)) {
            getWholeFormat().format(whole, toAppendTo, pos);
            toAppendTo.append(Letters.SPACE);
            if (num.compareTo(BigInteger.ZERO) < 0) {
                num = num.negate();
            }
        }
        getNumeratorFormat().format(num, toAppendTo, pos);
        toAppendTo.append(" / ");
        getDenominatorFormat().format(den, toAppendTo, pos);
        return toAppendTo;
    }

    public NumberFormat getWholeFormat() {
        return this.wholeFormat;
    }

    public BigFraction parse(String source, ParsePosition pos) {
        BigFraction ret = super.parse(source, pos);
        if (ret != null) {
            return ret;
        }
        int initialIndex = pos.getIndex();
        AbstractFormat.parseAndIgnoreWhitespace(source, pos);
        BigInteger whole = parseNextBigInteger(source, pos);
        if (whole == null) {
            pos.setIndex(initialIndex);
            return null;
        }
        AbstractFormat.parseAndIgnoreWhitespace(source, pos);
        BigInteger num = parseNextBigInteger(source, pos);
        if (num == null) {
            pos.setIndex(initialIndex);
            return null;
        } else if (num.compareTo(BigInteger.ZERO) < 0) {
            pos.setIndex(initialIndex);
            return null;
        } else {
            int startIndex = pos.getIndex();
            switch (AbstractFormat.parseNextCharacter(source, pos)) {
                case ValueServer.DIGEST_MODE /*0*/:
                    return new BigFraction(num);
                case R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /*47*/:
                    AbstractFormat.parseAndIgnoreWhitespace(source, pos);
                    BigInteger den = parseNextBigInteger(source, pos);
                    if (den == null) {
                        pos.setIndex(initialIndex);
                        return null;
                    } else if (den.compareTo(BigInteger.ZERO) < 0) {
                        pos.setIndex(initialIndex);
                        return null;
                    } else {
                        boolean wholeIsNeg = whole.compareTo(BigInteger.ZERO) < 0;
                        if (wholeIsNeg) {
                            whole = whole.negate();
                        }
                        num = whole.multiply(den).add(num);
                        if (wholeIsNeg) {
                            num = num.negate();
                        }
                        return new BigFraction(num, den);
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
