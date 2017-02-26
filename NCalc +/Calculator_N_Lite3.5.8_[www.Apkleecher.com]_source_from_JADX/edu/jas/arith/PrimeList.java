package edu.jas.arith;

import android.support.v4.media.TransportMediator;
import android.support.v4.view.InputDeviceCompat;
import com.getkeepsafe.taptargetview.R;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public final class PrimeList implements Iterable<BigInteger> {
    private static volatile List<BigInteger> LARGE_LIST;
    private static volatile List<BigInteger> LOW_LIST;
    private static volatile List<BigInteger> MEDIUM_LIST;
    private static volatile List<BigInteger> MERSENNE_LIST;
    private static volatile List<BigInteger> SMALL_LIST;
    private BigInteger last;
    private List<BigInteger> val;

    class 1 implements Iterator<BigInteger> {
        int index;

        1() {
            this.index = -1;
        }

        public boolean hasNext() {
            return true;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove not implemented");
        }

        public BigInteger next() {
            this.index++;
            return PrimeList.this.get(this.index);
        }
    }

    static /* synthetic */ class 2 {
        static final /* synthetic */ int[] $SwitchMap$edu$jas$arith$PrimeList$Range;

        static {
            $SwitchMap$edu$jas$arith$PrimeList$Range = new int[Range.values().length];
            try {
                $SwitchMap$edu$jas$arith$PrimeList$Range[Range.small.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$edu$jas$arith$PrimeList$Range[Range.low.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$edu$jas$arith$PrimeList$Range[Range.medium.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$edu$jas$arith$PrimeList$Range[Range.large.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$edu$jas$arith$PrimeList$Range[Range.mersenne.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public enum Range {
        small,
        low,
        medium,
        large,
        mersenne
    }

    static {
        SMALL_LIST = null;
        LOW_LIST = null;
        MEDIUM_LIST = null;
        LARGE_LIST = null;
        MERSENNE_LIST = null;
    }

    public PrimeList() {
        this(Range.medium);
    }

    public PrimeList(Range r) {
        this.val = null;
        switch (2.$SwitchMap$edu$jas$arith$PrimeList$Range[r.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                if (SMALL_LIST == null) {
                    this.val = new ArrayList(50);
                    addSmall();
                    SMALL_LIST = this.val;
                    break;
                }
                this.val = SMALL_LIST;
                break;
            case IExpr.DOUBLEID /*2*/:
                if (LOW_LIST == null) {
                    this.val = new ArrayList(50);
                    addLow();
                    LOW_LIST = this.val;
                    break;
                }
                this.val = LOW_LIST;
                break;
            case IExpr.DOUBLECOMPLEXID /*4*/:
                if (LARGE_LIST == null) {
                    this.val = new ArrayList(50);
                    addLarge();
                    LARGE_LIST = this.val;
                    break;
                }
                this.val = LARGE_LIST;
                break;
            case ValueServer.CONSTANT_MODE /*5*/:
                if (MERSENNE_LIST == null) {
                    this.val = new ArrayList(50);
                    addMersenne();
                    MERSENNE_LIST = this.val;
                    break;
                }
                this.val = MERSENNE_LIST;
                break;
            default:
                if (MEDIUM_LIST == null) {
                    this.val = new ArrayList(50);
                    addMedium();
                    MEDIUM_LIST = this.val;
                    break;
                }
                this.val = MEDIUM_LIST;
                break;
        }
        this.last = get(size() - 1);
    }

    private void addSmall() {
        this.val.add(BigInteger.valueOf(2));
        this.val.add(BigInteger.valueOf(3));
        this.val.add(BigInteger.valueOf(5));
        this.val.add(BigInteger.valueOf(7));
        this.val.add(BigInteger.valueOf(11));
        this.val.add(BigInteger.valueOf(13));
        this.val.add(BigInteger.valueOf(17));
        this.val.add(BigInteger.valueOf(19));
        this.val.add(BigInteger.valueOf(23));
        this.val.add(BigInteger.valueOf(29));
    }

    private void addLow() {
        this.val.add(getLongPrime(15, 19));
        this.val.add(getLongPrime(15, 49));
        this.val.add(getLongPrime(15, 51));
        this.val.add(getLongPrime(15, 55));
        this.val.add(getLongPrime(15, 61));
        this.val.add(getLongPrime(15, 75));
        this.val.add(getLongPrime(15, 81));
        this.val.add(getLongPrime(15, 115));
        this.val.add(getLongPrime(15, 121));
        this.val.add(getLongPrime(15, 135));
        this.val.add(getLongPrime(16, 15));
        this.val.add(getLongPrime(16, 17));
        this.val.add(getLongPrime(16, 39));
        this.val.add(getLongPrime(16, 57));
        this.val.add(getLongPrime(16, 87));
        this.val.add(getLongPrime(16, 89));
        this.val.add(getLongPrime(16, 99));
        this.val.add(getLongPrime(16, R.styleable.AppCompatTheme_switchStyle));
        this.val.add(getLongPrime(16, 117));
        this.val.add(getLongPrime(16, 123));
    }

    private void addMedium() {
        this.val.add(getLongPrime(28, 57));
        this.val.add(getLongPrime(28, 89));
        this.val.add(getLongPrime(28, 95));
        this.val.add(getLongPrime(28, 119));
        this.val.add(getLongPrime(28, 125));
        this.val.add(getLongPrime(28, 143));
        this.val.add(getLongPrime(28, 165));
        this.val.add(getLongPrime(28, 183));
        this.val.add(getLongPrime(28, 213));
        this.val.add(getLongPrime(28, 273));
        this.val.add(getLongPrime(29, 3));
        this.val.add(getLongPrime(29, 33));
        this.val.add(getLongPrime(29, 43));
        this.val.add(getLongPrime(29, 63));
        this.val.add(getLongPrime(29, 73));
        this.val.add(getLongPrime(29, 75));
        this.val.add(getLongPrime(29, 93));
        this.val.add(getLongPrime(29, 99));
        this.val.add(getLongPrime(29, 121));
        this.val.add(getLongPrime(29, 133));
        this.val.add(getLongPrime(32, 5));
        this.val.add(getLongPrime(32, 17));
        this.val.add(getLongPrime(32, 65));
        this.val.add(getLongPrime(32, 99));
        this.val.add(getLongPrime(32, R.styleable.AppCompatTheme_radioButtonStyle));
        this.val.add(getLongPrime(32, 135));
        this.val.add(getLongPrime(32, 153));
        this.val.add(getLongPrime(32, 185));
        this.val.add(getLongPrime(32, 209));
        this.val.add(getLongPrime(32, 267));
    }

    private void addLarge() {
        this.val.add(getLongPrime(59, 55));
        this.val.add(getLongPrime(59, 99));
        this.val.add(getLongPrime(59, 225));
        this.val.add(getLongPrime(59, 427));
        this.val.add(getLongPrime(59, 517));
        this.val.add(getLongPrime(59, 607));
        this.val.add(getLongPrime(59, 649));
        this.val.add(getLongPrime(59, 687));
        this.val.add(getLongPrime(59, 861));
        this.val.add(getLongPrime(59, 871));
        this.val.add(getLongPrime(60, 93));
        this.val.add(getLongPrime(60, R.styleable.AppCompatTheme_radioButtonStyle));
        this.val.add(getLongPrime(60, 173));
        this.val.add(getLongPrime(60, 179));
        this.val.add(getLongPrime(60, InputDeviceCompat.SOURCE_KEYBOARD));
        this.val.add(getLongPrime(60, 279));
        this.val.add(getLongPrime(60, 369));
        this.val.add(getLongPrime(60, 395));
        this.val.add(getLongPrime(60, 399));
        this.val.add(getLongPrime(60, 453));
        this.val.add(getLongPrime(63, 25));
        this.val.add(getLongPrime(63, 165));
        this.val.add(getLongPrime(63, 259));
        this.val.add(getLongPrime(63, 301));
        this.val.add(getLongPrime(63, 375));
        this.val.add(getLongPrime(63, 387));
        this.val.add(getLongPrime(63, 391));
        this.val.add(getLongPrime(63, 409));
        this.val.add(getLongPrime(63, 457));
        this.val.add(getLongPrime(63, 471));
    }

    private void addMersenne() {
        this.val.add(getMersennePrime(2));
        this.val.add(getMersennePrime(3));
        this.val.add(getMersennePrime(5));
        this.val.add(getMersennePrime(7));
        this.val.add(getMersennePrime(13));
        this.val.add(getMersennePrime(17));
        this.val.add(getMersennePrime(19));
        this.val.add(getMersennePrime(31));
        this.val.add(getMersennePrime(61));
        this.val.add(getMersennePrime(89));
        this.val.add(getMersennePrime(R.styleable.AppCompatTheme_radioButtonStyle));
        this.val.add(getMersennePrime(TransportMediator.KEYCODE_MEDIA_PAUSE));
        this.val.add(getMersennePrime(521));
        this.val.add(getMersennePrime(607));
        this.val.add(getMersennePrime(1279));
        this.val.add(getMersennePrime(2203));
        this.val.add(getMersennePrime(2281));
        this.val.add(getMersennePrime(3217));
        this.val.add(getMersennePrime(4253));
        this.val.add(getMersennePrime(4423));
        this.val.add(getMersennePrime(9689));
        this.val.add(getMersennePrime(9941));
        this.val.add(getMersennePrime(11213));
        this.val.add(getMersennePrime(19937));
    }

    public static BigInteger getLongPrime(int n, int m) {
        if (n < 30) {
            return BigInteger.valueOf((long) ((1 << n) - m));
        }
        return BigInteger.ONE.shiftLeft(n).subtract(BigInteger.valueOf((long) m));
    }

    public static BigInteger getMersennePrime(int n) {
        return BigInteger.ONE.shiftLeft(n).subtract(BigInteger.ONE);
    }

    protected boolean checkPrimes() {
        return checkPrimes(size());
    }

    protected boolean checkPrimes(int n) {
        int i = 0;
        for (BigInteger p : this.val) {
            int i2 = i + 1;
            if (i >= n) {
                i = i2;
                break;
            } else if (p.isProbablePrime(63)) {
                i = i2;
            } else {
                System.out.println("not prime = " + p);
                i = i2;
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return this.val.toString();
    }

    public int size() {
        return this.val.size();
    }

    public BigInteger get(int i) {
        if (i < size()) {
            return (BigInteger) this.val.get(i);
        }
        if (i == size()) {
            BigInteger p = this.last.nextProbablePrime();
            this.val.add(p);
            this.last = p;
            return p;
        }
        p = get(i - 1);
        p = this.last.nextProbablePrime();
        this.val.add(p);
        this.last = p;
        return p;
    }

    public Iterator<BigInteger> iterator() {
        return new 1();
    }
}
