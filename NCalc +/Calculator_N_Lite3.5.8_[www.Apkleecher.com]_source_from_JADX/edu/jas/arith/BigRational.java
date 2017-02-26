package edu.jas.arith;

import com.example.duy.calculator.math_eval.Constants;
import edu.jas.kern.Scripting;
import edu.jas.kern.Scripting.Lang;
import edu.jas.kern.StringUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.Power;
import edu.jas.structure.RingFactory;
import java.io.Reader;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.random.ValueServer;

public final class BigRational implements GcdRingElem<BigRational>, RingFactory<BigRational>, Rational, Iterable<BigRational> {
    public static final BigRational ONE;
    public static final BigRational ZERO;
    private static final Random random;
    public final BigInteger den;
    private boolean duplicates;
    private boolean nonNegative;
    public final BigInteger num;

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$edu$jas$kern$Scripting$Lang;

        static {
            $SwitchMap$edu$jas$kern$Scripting$Lang = new int[Lang.values().length];
            try {
                $SwitchMap$edu$jas$kern$Scripting$Lang[Lang.Python.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$edu$jas$kern$Scripting$Lang[Lang.Ruby.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static {
        ZERO = new BigRational(BigInteger.ZERO);
        ONE = new BigRational(BigInteger.ONE);
        random = new Random();
    }

    protected BigRational(BigInteger n, BigInteger d) {
        this.nonNegative = true;
        this.duplicates = true;
        this.num = n;
        this.den = d;
    }

    public BigRational(BigInteger n) {
        this.nonNegative = true;
        this.duplicates = true;
        this.num = n;
        this.den = BigInteger.ONE;
    }

    public BigRational(BigInteger n) {
        this(n.getVal());
    }

    public BigRational(BigInteger n, BigInteger d) {
        this.nonNegative = true;
        this.duplicates = true;
        BigRational r = RNRED(n.getVal(), d.getVal());
        this.num = r.num;
        this.den = r.den;
    }

    public BigRational(long n, long d) {
        this.nonNegative = true;
        this.duplicates = true;
        BigRational r = RNRED(BigInteger.valueOf(n), BigInteger.valueOf(d));
        this.num = r.num;
        this.den = r.den;
    }

    public BigRational(long n) {
        this.nonNegative = true;
        this.duplicates = true;
        this.num = BigInteger.valueOf(n);
        this.den = BigInteger.ONE;
    }

    public BigRational() {
        this.nonNegative = true;
        this.duplicates = true;
        this.num = BigInteger.ZERO;
        this.den = BigInteger.ONE;
    }

    public BigRational(String s) throws NumberFormatException {
        this.nonNegative = true;
        this.duplicates = true;
        if (s == null) {
            this.num = BigInteger.ZERO;
            this.den = BigInteger.ONE;
        } else if (s.length() == 0) {
            this.num = BigInteger.ZERO;
            this.den = BigInteger.ONE;
        } else {
            s = s.trim();
            int i = s.indexOf(47);
            BigRational r;
            if (i < 0) {
                i = s.indexOf(46);
                if (i < 0) {
                    this.num = new BigInteger(s);
                    this.den = BigInteger.ONE;
                    return;
                }
                BigInteger n;
                if (s.charAt(0) == Constants.MINUS_UNICODE) {
                    n = new BigInteger(s.substring(1, i));
                } else {
                    n = new BigInteger(s.substring(0, i));
                }
                BigRational z = (BigRational) Power.positivePower(new BigRational(1, 10), (long) ((s.length() - i) - 1));
                r = new BigRational(n).sum(new BigRational(new BigInteger(s.substring(i + 1, s.length()))).multiply(z));
                if (s.charAt(0) == Constants.MINUS_UNICODE) {
                    this.num = r.num.negate();
                } else {
                    this.num = r.num;
                }
                this.den = r.den;
                return;
            }
            r = RNRED(new BigInteger(s.substring(0, i)), new BigInteger(s.substring(i + 1, s.length())));
            this.num = r.num;
            this.den = r.den;
        }
    }

    public BigRational factory() {
        return this;
    }

    public List<BigRational> generators() {
        List<BigRational> g = new ArrayList(1);
        g.add(getONE());
        return g;
    }

    public boolean isFinite() {
        return false;
    }

    public BigRational copy() {
        return new BigRational(this.num, this.den);
    }

    public BigRational copy(BigRational c) {
        return new BigRational(c.num, c.den);
    }

    public BigRational getRational() {
        return this;
    }

    public BigInteger numerator() {
        return this.num;
    }

    public BigInteger denominator() {
        return this.den;
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append(this.num);
        if (!this.den.equals(BigInteger.ONE)) {
            s.append("/").append(this.den);
        }
        return s.toString();
    }

    public String toString(int n) {
        return new BigDecimal(this, new MathContext(n)).toString();
    }

    public double doubleValue() {
        return new BigDecimal(this, MathContext.DECIMAL64).doubleValue();
    }

    public String toScript() {
        StringBuffer s = new StringBuffer();
        if (this.den.equals(BigInteger.ONE)) {
            s.append(this.num.toString());
            return s.toString();
        }
        switch (1.$SwitchMap$edu$jas$kern$Scripting$Lang[Scripting.getLang().ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                s.append("(");
                s.append(this.num.toString());
                s.append(",");
                s.append(this.den.toString());
                s.append(")");
                break;
            default:
                s.append(this.num.toString());
                s.append("/");
                s.append(this.den.toString());
                break;
        }
        return s.toString();
    }

    public String toScriptFactory() {
        return "QQ()";
    }

    public BigRational getZERO() {
        return ZERO;
    }

    public BigRational getONE() {
        return ONE;
    }

    public boolean isCommutative() {
        return true;
    }

    public boolean isAssociative() {
        return true;
    }

    public boolean isField() {
        return true;
    }

    public BigInteger characteristic() {
        return BigInteger.ZERO;
    }

    public BigRational fromInteger(BigInteger a) {
        return new BigRational(a);
    }

    public BigRational fromInteger(BigInteger a) {
        return new BigRational(a);
    }

    public static BigRational valueOf(BigInteger a) {
        return new BigRational(a);
    }

    public BigRational fromInteger(long a) {
        return new BigRational(a);
    }

    public static BigRational valueOf(long a) {
        return new BigRational(a);
    }

    public boolean isZERO() {
        return this.num.equals(BigInteger.ZERO);
    }

    public boolean isONE() {
        return this.num.equals(this.den);
    }

    public boolean isUnit() {
        return !isZERO();
    }

    public boolean equals(Object b) {
        if (!(b instanceof BigRational)) {
            return false;
        }
        BigRational br = (BigRational) b;
        if (this.num.equals(br.num) && this.den.equals(br.den)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.num.hashCode() * 37) + this.den.hashCode();
    }

    public static BigRational RNRED(BigInteger n, BigInteger d) {
        if (n.equals(BigInteger.ZERO)) {
            return new BigRational(n, BigInteger.ONE);
        }
        if (n.equals(d)) {
            return new BigRational(BigInteger.ONE, BigInteger.ONE);
        }
        BigInteger num;
        BigInteger den;
        BigInteger c = n.gcd(d);
        if (c.equals(BigInteger.ONE)) {
            num = n;
            den = d;
        } else {
            num = n.divide(c);
            den = d.divide(c);
        }
        if (den.signum() < 0) {
            num = num.negate();
            den = den.negate();
        }
        return new BigRational(num, den);
    }

    public static BigRational reduction(BigInteger n, BigInteger d) {
        return RNRED(n, d);
    }

    public BigRational abs() {
        return signum() >= 0 ? this : negate();
    }

    public static BigRational RNABS(BigRational R) {
        if (R == null) {
            return null;
        }
        return R.abs();
    }

    public int compareTo(BigRational S) {
        if (equals(ZERO)) {
            return -S.signum();
        }
        if (S.equals(ZERO)) {
            return signum();
        }
        BigInteger R1 = this.num;
        BigInteger R2 = this.den;
        BigInteger S1 = S.num;
        BigInteger S2 = S.den;
        int TL = (R1.signum() - S1.signum()) / 2;
        if (TL == 0) {
            return R1.multiply(S2).compareTo(R2.multiply(S1));
        }
        return TL;
    }

    public static int RNCOMP(BigRational R, BigRational S) {
        if (R == null) {
            return BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT;
        }
        return R.compareTo(S);
    }

    public static BigInteger RNDEN(BigRational R) {
        if (R == null) {
            return null;
        }
        return R.den;
    }

    public BigRational subtract(BigRational S) {
        return sum(S.negate());
    }

    public static BigRational RNDIF(BigRational R, BigRational S) {
        if (R == null) {
            return S.negate();
        }
        return R.subtract(S);
    }

    public static void RNDWR(BigRational R, int NL) {
        System.out.print(new BigDecimal(R, new MathContext(NL)).toString());
    }

    public static BigRational RNINT(BigInteger A) {
        return new BigRational(A);
    }

    public BigRational inverse() {
        BigInteger S1;
        BigInteger S2;
        BigInteger R1 = this.num;
        BigInteger R2 = this.den;
        if (R1.signum() >= 0) {
            S1 = R2;
            S2 = R1;
        } else {
            S1 = R2.negate();
            S2 = R1.negate();
        }
        return new BigRational(S1, S2);
    }

    public static BigRational RNINV(BigRational R) {
        if (R == null) {
            return null;
        }
        return R.inverse();
    }

    public BigRational negate() {
        return new BigRational(this.num.negate(), this.den);
    }

    public static BigRational RNNEG(BigRational R) {
        if (R == null) {
            return null;
        }
        return R.negate();
    }

    public static BigInteger RNNUM(BigRational R) {
        if (R == null) {
            return null;
        }
        return R.num;
    }

    public BigRational multiply(BigRational S) {
        if (!equals(ZERO)) {
            if (!S.equals(ZERO)) {
                BigInteger R1 = this.num;
                BigInteger R2 = this.den;
                BigInteger S1 = S.num;
                BigInteger S2 = S.den;
                if (R2.equals(BigInteger.ONE) && S2.equals(BigInteger.ONE)) {
                    return new BigRational(R1.multiply(S1), BigInteger.ONE);
                }
                BigInteger D1;
                BigInteger RB1;
                if (R2.equals(BigInteger.ONE)) {
                    if (R1.equals(S2)) {
                        D1 = R1;
                    } else {
                        D1 = R1.gcd(S2);
                    }
                    RB1 = R1.divide(D1);
                    return new BigRational(RB1.multiply(S1), S2.divide(D1));
                } else if (S2.equals(BigInteger.ONE)) {
                    if (S1.equals(R2)) {
                        D2 = S1;
                    } else {
                        D2 = S1.gcd(R2);
                    }
                    BigInteger SB1 = S1.divide(D2);
                    return new BigRational(SB1.multiply(R1), R2.divide(D2));
                } else {
                    if (R1.equals(S2)) {
                        D1 = R1;
                    } else {
                        D1 = R1.gcd(S2);
                    }
                    RB1 = R1.divide(D1);
                    BigInteger SB2 = S2.divide(D1);
                    if (S1.equals(R2)) {
                        D2 = S1;
                    } else {
                        D2 = S1.gcd(R2);
                    }
                    return new BigRational(RB1.multiply(S1.divide(D2)), R2.divide(D2).multiply(SB2));
                }
            }
        }
        return ZERO;
    }

    public static BigRational RNPROD(BigRational R, BigRational S) {
        return R == null ? R : R.multiply(S);
    }

    public BigRational divide(BigRational S) {
        return multiply(S.inverse());
    }

    public static BigRational RNQ(BigRational R, BigRational S) {
        return R == null ? R : R.divide(S);
    }

    public BigRational remainder(BigRational S) {
        if (!S.isZERO()) {
            return ZERO;
        }
        throw new ArithmeticException("division by zero");
    }

    public BigRational[] quotientRemainder(BigRational S) {
        return new BigRational[]{divide(S), ZERO};
    }

    public BigRational random(int n) {
        return random(n, random);
    }

    public BigRational random(int n, Random rnd) {
        BigInteger A = new BigInteger(n, rnd);
        if (rnd.nextBoolean()) {
            A = A.negate();
        }
        return RNRED(A, new BigInteger(n, rnd).add(BigInteger.ONE));
    }

    public static BigRational RNRAND(int NL) {
        return ONE.random(NL, random);
    }

    public int signum() {
        return this.num.signum();
    }

    public static int RNSIGN(BigRational R) {
        if (R == null) {
            return 0;
        }
        return R.signum();
    }

    public BigRational sum(BigRational S) {
        if (equals(ZERO)) {
            return S;
        }
        if (S.equals(ZERO)) {
            return this;
        }
        BigInteger R1 = this.num;
        BigInteger R2 = this.den;
        BigInteger S1 = S.num;
        BigInteger S2 = S.den;
        if (R2.equals(BigInteger.ONE) && S2.equals(BigInteger.ONE)) {
            return new BigRational(R1.add(S1), BigInteger.ONE);
        }
        if (R2.equals(BigInteger.ONE)) {
            return new BigRational(R1.multiply(S2).add(S1), S2);
        }
        if (S2.equals(BigInteger.ONE)) {
            return new BigRational(R2.multiply(S1).add(R1), R2);
        }
        BigInteger D;
        BigInteger RB2;
        BigInteger SB2;
        if (R2.equals(S2)) {
            D = R2;
        } else {
            D = R2.gcd(S2);
        }
        if (D.equals(BigInteger.ONE)) {
            RB2 = R2;
            SB2 = S2;
        } else {
            RB2 = R2.divide(D);
            SB2 = S2.divide(D);
        }
        BigInteger T1 = R1.multiply(SB2).add(RB2.multiply(S1));
        if (T1.equals(BigInteger.ZERO)) {
            return ZERO;
        }
        if (!D.equals(BigInteger.ONE)) {
            BigInteger E;
            if (T1.equals(D)) {
                E = D;
            } else {
                E = T1.gcd(D);
            }
            if (!E.equals(BigInteger.ONE)) {
                T1 = T1.divide(E);
                R2 = R2.divide(E);
            }
        }
        return new BigRational(T1, R2.multiply(SB2));
    }

    public static BigRational RNSUM(BigRational R, BigRational S) {
        return R == null ? S : R.sum(S);
    }

    public BigRational parse(String s) {
        return new BigRational(s);
    }

    public BigRational parse(Reader r) {
        return parse(StringUtil.nextString(r));
    }

    public BigRational gcd(BigRational S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        return !isZERO() ? ONE : S;
    }

    public BigRational[] egcd(BigRational S) {
        BigRational[] ret = new BigRational[]{null, null, null};
        if (S == null || S.isZERO()) {
            ret[0] = this;
        } else if (isZERO()) {
            ret[0] = S;
        } else {
            BigRational half = new BigRational(1, 2);
            ret[0] = ONE;
            ret[1] = inverse().multiply(half);
            ret[2] = S.inverse().multiply(half);
        }
        return ret;
    }

    public void setAllIterator() {
        this.nonNegative = false;
    }

    public void setNonNegativeIterator() {
        this.nonNegative = true;
    }

    public void setNoDuplicatesIterator() {
        this.duplicates = false;
    }

    public void setDuplicatesIterator() {
        this.duplicates = true;
    }

    public Iterator<BigRational> iterator() {
        if (this.duplicates) {
            return new BigRationalIterator(this.nonNegative);
        }
        return new BigRationalUniqueIterator(new BigRationalIterator(this.nonNegative));
    }

    public Iterator<BigRational> uniqueIterator() {
        return new BigRationalUniqueIterator(new BigRationalIterator(this.nonNegative));
    }
}
