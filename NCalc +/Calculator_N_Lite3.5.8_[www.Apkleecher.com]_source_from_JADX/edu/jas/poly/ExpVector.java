package edu.jas.poly;

import edu.jas.ps.UnivPowerSeriesRing;
import edu.jas.structure.AbelianGroupElem;
import edu.jas.structure.AbelianGroupFactory;
import edu.jas.structure.Power;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public abstract class ExpVector implements AbelianGroupElem<ExpVector> {
    private static final Random random;
    public static final StorUnit storunit;
    protected int hash;

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$edu$jas$poly$ExpVector$StorUnit;

        static {
            $SwitchMap$edu$jas$poly$ExpVector$StorUnit = new int[StorUnit.values().length];
            try {
                $SwitchMap$edu$jas$poly$ExpVector$StorUnit[StorUnit.INT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$edu$jas$poly$ExpVector$StorUnit[StorUnit.LONG.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$edu$jas$poly$ExpVector$StorUnit[StorUnit.SHORT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$edu$jas$poly$ExpVector$StorUnit[StorUnit.BYTE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public enum StorUnit {
        LONG,
        INT,
        SHORT,
        BYTE
    }

    public abstract ExpVector abs();

    public abstract ExpVector combine(ExpVector expVector);

    public abstract ExpVector contract(int i, int i2);

    public abstract ExpVector copy();

    public abstract int[] dependencyOnVariables();

    public abstract ExpVector extend(int i, int i2, long j);

    public abstract ExpVector extendLower(int i, int i2, long j);

    public abstract ExpVector gcd(ExpVector expVector);

    public abstract long getVal(int i);

    abstract long[] getVal();

    public abstract int invGradCompareTo(ExpVector expVector);

    public abstract int invGradCompareTo(ExpVector expVector, int i, int i2);

    public abstract int invLexCompareTo(ExpVector expVector);

    public abstract int invLexCompareTo(ExpVector expVector, int i, int i2);

    public abstract int invWeightCompareTo(long[][] jArr, ExpVector expVector);

    public abstract int invWeightCompareTo(long[][] jArr, ExpVector expVector, int i, int i2);

    public abstract ExpVector lcm(ExpVector expVector);

    public abstract int length();

    public abstract long maxDeg();

    public abstract boolean multipleOf(ExpVector expVector);

    public abstract ExpVector negate();

    public abstract ExpVector permutation(List<Integer> list);

    public abstract int revInvGradCompareTo(ExpVector expVector);

    public abstract int revInvGradCompareTo(ExpVector expVector, int i, int i2);

    public abstract int revInvLexCompareTo(ExpVector expVector);

    public abstract int revInvLexCompareTo(ExpVector expVector, int i, int i2);

    public abstract ExpVector reverse();

    public abstract ExpVector reverse(int i);

    protected abstract long setVal(int i, long j);

    public abstract int signum();

    public abstract ExpVector subtract(ExpVector expVector);

    public abstract ExpVector sum(ExpVector expVector);

    public abstract long totalDeg();

    public abstract long weightDeg(long[][] jArr);

    static {
        random = new Random();
        storunit = StorUnit.LONG;
    }

    public ExpVector() {
        this.hash = 0;
        this.hash = 0;
    }

    public static ExpVector create(int n) {
        switch (1.$SwitchMap$edu$jas$poly$ExpVector$StorUnit[storunit.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                return new ExpVectorInteger(n);
            case IExpr.DOUBLEID /*2*/:
                return new ExpVectorLong(n);
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                return new ExpVectorShort(n);
            case IExpr.DOUBLECOMPLEXID /*4*/:
                return new ExpVectorByte(n);
            default:
                return new ExpVectorInteger(n);
        }
    }

    public static ExpVector create(int n, int i, long e) {
        switch (1.$SwitchMap$edu$jas$poly$ExpVector$StorUnit[storunit.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                return new ExpVectorInteger(n, i, e);
            case IExpr.DOUBLEID /*2*/:
                return new ExpVectorLong(n, i, e);
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                return new ExpVectorShort(n, i, e);
            case IExpr.DOUBLECOMPLEXID /*4*/:
                return new ExpVectorByte(n, i, e);
            default:
                return new ExpVectorInteger(n, i, e);
        }
    }

    public static ExpVector create(long[] v) {
        switch (1.$SwitchMap$edu$jas$poly$ExpVector$StorUnit[storunit.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                return new ExpVectorInteger(v);
            case IExpr.DOUBLEID /*2*/:
                return new ExpVectorLong(v);
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                return new ExpVectorShort(v);
            case IExpr.DOUBLECOMPLEXID /*4*/:
                return new ExpVectorByte(v);
            default:
                return new ExpVectorInteger(v);
        }
    }

    public static ExpVector create(String s) {
        switch (1.$SwitchMap$edu$jas$poly$ExpVector$StorUnit[storunit.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                return new ExpVectorInteger(s);
            case IExpr.DOUBLEID /*2*/:
                return new ExpVectorLong(s);
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                return new ExpVectorShort(s);
            case IExpr.DOUBLECOMPLEXID /*4*/:
                return new ExpVectorByte(s);
            default:
                return new ExpVectorInteger(s);
        }
    }

    public static ExpVector create(Collection<Long> v) {
        long[] w = new long[v.size()];
        int i = 0;
        for (Long k : v) {
            int i2 = i + 1;
            w[i] = k.longValue();
            i = i2;
        }
        return create(w);
    }

    public AbelianGroupFactory<ExpVector> factory() {
        throw new UnsupportedOperationException("no factory implemented for ExpVector");
    }

    public boolean isFinite() {
        return true;
    }

    public String toString() {
        StringBuffer s = new StringBuffer("(");
        for (int i = 0; i < length(); i++) {
            s.append(getVal(i));
            if (i < length() - 1) {
                s.append(",");
            }
        }
        s.append(")");
        return s.toString();
    }

    public String toString(String[] vars) {
        StringBuffer s = new StringBuffer();
        int r = length();
        if (r != vars.length) {
            return toString();
        }
        if (r == 0) {
            return s.toString();
        }
        long vi;
        for (int i = r - 1; i > 0; i--) {
            vi = getVal(i);
            if (vi != 0) {
                s.append(vars[(r - 1) - i]);
                if (vi != 1) {
                    s.append("^" + vi);
                }
                boolean pit = false;
                for (int j = i - 1; j >= 0; j--) {
                    if (getVal(j) != 0) {
                        pit = true;
                    }
                }
                if (pit) {
                    s.append(" * ");
                }
            }
        }
        vi = getVal(0);
        if (vi != 0) {
            s.append(vars[r - 1]);
            if (vi != 1) {
                s.append("^" + vi);
            }
        }
        return s.toString();
    }

    public static String varsToString(String[] vars) {
        if (vars == null) {
            return "null";
        }
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < vars.length; i++) {
            s.append(vars[i]);
            if (i < vars.length - 1) {
                s.append(",");
            }
        }
        return s.toString();
    }

    public String toScript() {
        return toScript(stdVars());
    }

    public String toScript(String[] vars) {
        int r = length();
        if (r != vars.length) {
            return toString();
        }
        long vi;
        StringBuffer s = new StringBuffer();
        for (int i = r - 1; i > 0; i--) {
            vi = getVal(i);
            if (vi != 0) {
                s.append(vars[(r - 1) - i]);
                if (vi != 1) {
                    s.append("**" + vi);
                }
                boolean pit = false;
                for (int j = i - 1; j >= 0; j--) {
                    if (getVal(j) != 0) {
                        pit = true;
                    }
                }
                if (pit) {
                    s.append(" * ");
                }
            }
        }
        vi = getVal(0);
        if (vi != 0) {
            s.append(vars[r - 1]);
            if (vi != 1) {
                s.append("**" + vi);
            }
        }
        return s.toString();
    }

    public String toScriptFactory() {
        return "ExpVector()";
    }

    public String indexVarName(int idx, String... vars) {
        return vars[(length() - idx) - 1];
    }

    public int varIndex(int idx) {
        return (length() - idx) - 1;
    }

    public int indexVar(String x, String... vars) {
        for (int i = 0; i < length(); i++) {
            if (x.equals(vars[i])) {
                return (length() - i) - 1;
            }
        }
        return -1;
    }

    public <C extends RingElem<C>> C evaluate(RingFactory<C> cf, List<C> a) {
        C c = (RingElem) cf.getONE();
        for (int i = 0; i < length(); i++) {
            long ei = getVal(i);
            if (ei != 0) {
                RingElem ai = (RingElem) a.get((length() - 1) - i);
                if (ai.isZERO()) {
                    return ai;
                }
                RingElem c2 = (RingElem) c.multiply(Power.positivePower(ai, ei));
            }
        }
        return c;
    }

    public boolean equals(Object B) {
        if ((B instanceof ExpVector) && invLexCompareTo((ExpVector) B) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.hash == 0) {
            for (int i = 0; i < length(); i++) {
                this.hash <<= (int) (4 + getVal(i));
            }
            if (this.hash == 0) {
                this.hash = 1;
            }
        }
        return this.hash;
    }

    public boolean isZERO() {
        return signum() == 0;
    }

    public String[] stdVars() {
        return STDVARS(UnivPowerSeriesRing.DEFAULT_NAME, length());
    }

    public String[] stdVars(String prefix) {
        return STDVARS(prefix, length());
    }

    public static String[] STDVARS(int n) {
        return STDVARS(UnivPowerSeriesRing.DEFAULT_NAME, n);
    }

    public static String[] STDVARS(String prefix, int n) {
        String[] vars = new String[n];
        if (prefix == null || prefix.length() == 0) {
            prefix = UnivPowerSeriesRing.DEFAULT_NAME;
        }
        for (int i = 0; i < n; i++) {
            vars[i] = prefix + i;
        }
        return vars;
    }

    public static ExpVector EVABS(ExpVector U) {
        return U.abs();
    }

    public static ExpVector EVNEG(ExpVector U) {
        return U.negate();
    }

    public static ExpVector EVSUM(ExpVector U, ExpVector V) {
        return U.sum(V);
    }

    public static ExpVector EVDIF(ExpVector U, ExpVector V) {
        return U.subtract(V);
    }

    public static ExpVector EVSU(ExpVector U, int i, long d) {
        return U.subst(i, d);
    }

    public ExpVector subst(int i, long d) {
        ExpVector V = copy();
        V.setVal(i, d);
        return V;
    }

    public static ExpVector EVRAND(int r, long k, float q) {
        return EVRAND(r, k, q, random);
    }

    public static ExpVector EVRAND(int r, long k, float q, Random rnd) {
        long[] w = new long[r];
        for (int i = 0; i < w.length; i++) {
            long e;
            if (rnd.nextFloat() > q) {
                e = 0;
            } else {
                e = rnd.nextLong() % k;
                if (e < 0) {
                    e = -e;
                }
            }
            w[i] = e;
        }
        return create(w);
    }

    public static ExpVector random(int r, long k, float q) {
        return EVRAND(r, k, q, random);
    }

    public static ExpVector random(int r, long k, float q, Random rnd) {
        return EVRAND(r, k, q, rnd);
    }

    public static int EVSIGN(ExpVector U) {
        return U.signum();
    }

    public static long EVTDEG(ExpVector U) {
        return U.totalDeg();
    }

    public long degree() {
        return totalDeg();
    }

    public static long EVMDEG(ExpVector U) {
        return U.maxDeg();
    }

    public static long EVWDEG(long[][] w, ExpVector U) {
        return U.weightDeg(w);
    }

    public static ExpVector EVLCM(ExpVector U, ExpVector V) {
        return U.lcm(V);
    }

    public static ExpVector EVGCD(ExpVector U, ExpVector V) {
        return U.gcd(V);
    }

    public static int[] EVDOV(ExpVector U) {
        return U.dependencyOnVariables();
    }

    public static boolean EVMT(ExpVector U, ExpVector V) {
        return U.multipleOf(V);
    }

    public boolean divides(ExpVector V) {
        return V.multipleOf(this);
    }

    public int compareTo(ExpVector V) {
        return invLexCompareTo(V);
    }

    public static int EVILCP(ExpVector U, ExpVector V) {
        return U.invLexCompareTo(V);
    }

    public static int EVILCP(ExpVector U, ExpVector V, int begin, int end) {
        return U.invLexCompareTo(V, begin, end);
    }

    public static int EVIGLC(ExpVector U, ExpVector V) {
        return U.invGradCompareTo(V);
    }

    public static int EVIGLC(ExpVector U, ExpVector V, int begin, int end) {
        return U.invGradCompareTo(V, begin, end);
    }

    public static int EVRILCP(ExpVector U, ExpVector V) {
        return U.revInvLexCompareTo(V);
    }

    public static int EVRILCP(ExpVector U, ExpVector V, int begin, int end) {
        return U.revInvLexCompareTo(V, begin, end);
    }

    public static int EVRIGLC(ExpVector U, ExpVector V) {
        return U.revInvGradCompareTo(V);
    }

    public static int EVRIGLC(ExpVector U, ExpVector V, int begin, int end) {
        return U.revInvGradCompareTo(V, begin, end);
    }

    public static int EVIWLC(long[][] w, ExpVector U, ExpVector V) {
        return U.invWeightCompareTo(w, V);
    }

    public static int EVIWLC(long[][] w, ExpVector U, ExpVector V, int begin, int end) {
        return U.invWeightCompareTo(w, V, begin, end);
    }
}
