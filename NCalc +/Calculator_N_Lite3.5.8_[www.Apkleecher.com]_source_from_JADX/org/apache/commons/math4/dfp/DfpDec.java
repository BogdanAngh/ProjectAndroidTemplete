package org.apache.commons.math4.dfp;

import io.github.kexanie.library.R;
import org.apache.commons.math4.dfp.DfpField.RoundingMode;
import org.apache.commons.math4.random.EmpiricalDistribution;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public class DfpDec extends Dfp {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$dfp$DfpField$RoundingMode;

    static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$dfp$DfpField$RoundingMode() {
        int[] iArr = $SWITCH_TABLE$org$apache$commons$math4$dfp$DfpField$RoundingMode;
        if (iArr == null) {
            iArr = new int[RoundingMode.values().length];
            try {
                iArr[RoundingMode.ROUND_CEIL.ordinal()] = 7;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[RoundingMode.ROUND_DOWN.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[RoundingMode.ROUND_FLOOR.ordinal()] = 8;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[RoundingMode.ROUND_HALF_DOWN.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[RoundingMode.ROUND_HALF_EVEN.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[RoundingMode.ROUND_HALF_ODD.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[RoundingMode.ROUND_HALF_UP.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr[RoundingMode.ROUND_UP.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            $SWITCH_TABLE$org$apache$commons$math4$dfp$DfpField$RoundingMode = iArr;
        }
        return iArr;
    }

    protected DfpDec(DfpField factory) {
        super(factory);
    }

    protected DfpDec(DfpField factory, byte x) {
        super(factory, x);
    }

    protected DfpDec(DfpField factory, int x) {
        super(factory, x);
    }

    protected DfpDec(DfpField factory, long x) {
        super(factory, x);
    }

    protected DfpDec(DfpField factory, double x) {
        super(factory, x);
        round(0);
    }

    public DfpDec(Dfp d) {
        super(d);
        round(0);
    }

    protected DfpDec(DfpField factory, String s) {
        super(factory, s);
        round(0);
    }

    protected DfpDec(DfpField factory, byte sign, byte nans) {
        super(factory, sign, nans);
    }

    public Dfp newInstance() {
        return new DfpDec(getField());
    }

    public Dfp newInstance(byte x) {
        return new DfpDec(getField(), x);
    }

    public Dfp newInstance(int x) {
        return new DfpDec(getField(), x);
    }

    public Dfp newInstance(long x) {
        return new DfpDec(getField(), x);
    }

    public Dfp newInstance(double x) {
        return new DfpDec(getField(), x);
    }

    public Dfp newInstance(Dfp d) {
        if (getField().getRadixDigits() == d.getField().getRadixDigits()) {
            return new DfpDec(d);
        }
        getField().setIEEEFlagsBits(1);
        Dfp result = newInstance(getZero());
        result.nans = (byte) 3;
        return dotrap(1, "newInstance", d, result);
    }

    public Dfp newInstance(String s) {
        return new DfpDec(getField(), s);
    }

    public Dfp newInstance(byte sign, byte nans) {
        return new DfpDec(getField(), sign, nans);
    }

    protected int getDecimalDigits() {
        return (getRadixDigits() * 4) - 3;
    }

    protected int round(int in) {
        int msb = this.mant[this.mant.length - 1];
        if (msb == 0) {
            return 0;
        }
        int i;
        int cmaxdigits = this.mant.length * 4;
        int lsbthreshold = EmpiricalDistribution.DEFAULT_BIN_COUNT;
        while (lsbthreshold > msb) {
            lsbthreshold /= 10;
            cmaxdigits--;
        }
        int digits = getDecimalDigits();
        int lsbshift = cmaxdigits - digits;
        int lsd = lsbshift / 4;
        lsbthreshold = 1;
        for (i = 0; i < lsbshift % 4; i++) {
            lsbthreshold *= 10;
        }
        int lsb = this.mant[lsd];
        if (lsbthreshold <= 1 && digits == (this.mant.length * 4) - 3) {
            return super.round(in);
        }
        int n;
        boolean inc;
        int discarded = in;
        if (lsbthreshold == 1) {
            n = (this.mant[lsd - 1] / EmpiricalDistribution.DEFAULT_BIN_COUNT) % 10;
            int[] iArr = this.mant;
            int i2 = lsd - 1;
            iArr[i2] = iArr[i2] % EmpiricalDistribution.DEFAULT_BIN_COUNT;
            discarded |= this.mant[lsd - 1];
        } else {
            n = ((lsb * 10) / lsbthreshold) % 10;
            discarded |= lsb % (lsbthreshold / 10);
        }
        for (i = 0; i < lsd; i++) {
            discarded |= this.mant[i];
            this.mant[i] = 0;
        }
        this.mant[lsd] = (lsb / lsbthreshold) * lsbthreshold;
        switch ($SWITCH_TABLE$org$apache$commons$math4$dfp$DfpField$RoundingMode()[getField().getRoundingMode().ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                inc = false;
                break;
            case IExpr.DOUBLEID /*2*/:
                inc = (n == 0 && discarded == 0) ? false : true;
                break;
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                inc = n >= 5;
                break;
            case IExpr.DOUBLECOMPLEXID /*4*/:
                inc = n > 5;
                break;
            case ValueServer.CONSTANT_MODE /*5*/:
                inc = n > 5 || ((n == 5 && discarded != 0) || (n == 5 && discarded == 0 && ((lsb / lsbthreshold) & 1) == 1));
                break;
            case R.styleable.Toolbar_contentInsetEnd /*6*/:
                inc = n > 5 || ((n == 5 && discarded != 0) || (n == 5 && discarded == 0 && ((lsb / lsbthreshold) & 1) == 0));
                break;
            case R.styleable.Toolbar_contentInsetLeft /*7*/:
                inc = this.sign == 1 && !(n == 0 && discarded == 0);
                break;
            default:
                if (this.sign == -1 && (n != 0 || discarded != 0)) {
                    inc = true;
                    break;
                }
                inc = false;
                break;
        }
        if (inc) {
            int rh = lsbthreshold;
            for (i = lsd; i < this.mant.length; i++) {
                int r = this.mant[i] + rh;
                rh = r / Dfp.RADIX;
                this.mant[i] = r % Dfp.RADIX;
            }
            if (rh != 0) {
                shiftRight();
                this.mant[this.mant.length - 1] = rh;
            }
        }
        if (this.exp < Dfp.MIN_EXP) {
            getField().setIEEEFlagsBits(8);
            return 8;
        } else if (this.exp > Dfp.MAX_EXP) {
            getField().setIEEEFlagsBits(4);
            return 4;
        } else if (n == 0 && discarded == 0) {
            return 0;
        } else {
            getField().setIEEEFlagsBits(16);
            return 16;
        }
    }

    public Dfp nextAfter(Dfp x) {
        String trapName = "nextAfter";
        if (getField().getRadixDigits() != x.getField().getRadixDigits()) {
            getField().setIEEEFlagsBits(1);
            Dfp result = newInstance(getZero());
            result.nans = (byte) 3;
            return dotrap(1, "nextAfter", x, result);
        }
        boolean up = false;
        if (lessThan(x)) {
            up = true;
        }
        if (equals(x)) {
            return newInstance(x);
        }
        if (lessThan(getZero())) {
            if (up) {
                up = false;
            } else {
                up = true;
            }
        }
        Dfp inc;
        if (up) {
            inc = Dfp.copysign(power10((intLog10() - getDecimalDigits()) + 1), this);
            if (equals(getZero())) {
                inc = power10K((-32767 - this.mant.length) - 1);
            }
            if (inc.equals(getZero())) {
                result = Dfp.copysign(newInstance(getZero()), this);
            } else {
                result = add(inc);
            }
        } else {
            inc = Dfp.copysign(power10(intLog10()), this);
            if (equals(inc)) {
                inc = inc.divide(power10(getDecimalDigits()));
            } else {
                inc = inc.divide(power10(getDecimalDigits() - 1));
            }
            if (equals(getZero())) {
                inc = power10K((-32767 - this.mant.length) - 1);
            }
            if (inc.equals(getZero())) {
                result = Dfp.copysign(newInstance(getZero()), this);
            } else {
                result = subtract(inc);
            }
        }
        if (result.classify() == 1 && classify() != 1) {
            getField().setIEEEFlagsBits(16);
            result = dotrap(16, "nextAfter", x, result);
        }
        if (!result.equals(getZero()) || equals(getZero())) {
            return result;
        }
        getField().setIEEEFlagsBits(16);
        return dotrap(16, "nextAfter", x, result);
    }
}
