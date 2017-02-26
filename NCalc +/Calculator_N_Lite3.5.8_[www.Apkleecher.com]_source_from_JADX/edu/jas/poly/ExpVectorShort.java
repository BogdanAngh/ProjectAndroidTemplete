package edu.jas.poly;

import java.util.ArrayList;
import java.util.List;

public final class ExpVectorShort extends ExpVector {
    public static final long maxShort = 16383;
    public static final long minShort = -16384;
    final short[] val;

    public ExpVectorShort(int n) {
        this(new short[n]);
    }

    public ExpVectorShort(int n, int i, short e) {
        this(n);
        this.val[i] = e;
    }

    public ExpVectorShort(int n, int i, long e) {
        this(n);
        if (e >= maxShort || e <= minShort) {
            throw new IllegalArgumentException("exponent to large: " + e);
        }
        this.val[i] = (short) ((int) e);
    }

    protected ExpVectorShort(short[] v) {
        this.val = v;
    }

    public ExpVectorShort(long[] v) {
        this(v.length);
        int i = 0;
        while (i < v.length) {
            if (v[i] >= maxShort || v[i] <= minShort) {
                throw new IllegalArgumentException("exponent to large: " + v[i]);
            }
            this.val[i] = (short) ((int) v[i]);
            i++;
        }
    }

    public ExpVectorShort(String s) throws NumberFormatException {
        List<Short> exps = new ArrayList();
        s = s.trim();
        int b = s.indexOf(40);
        int e = s.indexOf(41, b + 1);
        if (b < 0 || e < 0) {
            this.val = null;
            return;
        }
        b++;
        while (true) {
            int k = s.indexOf(44, b);
            if (k < 0) {
                break;
            }
            exps.add(Short.valueOf(Short.parseShort(s.substring(b, k))));
            b = k + 1;
        }
        if (b <= e) {
            exps.add(Short.valueOf(Short.parseShort(s.substring(b, e))));
        }
        int length = exps.size();
        this.val = new short[length];
        for (int j = 0; j < length; j++) {
            this.val[j] = ((Short) exps.get(j)).shortValue();
        }
    }

    public ExpVectorShort copy() {
        short[] w = new short[this.val.length];
        System.arraycopy(this.val, 0, w, 0, this.val.length);
        return new ExpVectorShort(w);
    }

    long[] getVal() {
        long[] v = new long[this.val.length];
        for (int i = 0; i < this.val.length; i++) {
            v[i] = (long) this.val[i];
        }
        return v;
    }

    public long getVal(int i) {
        return (long) this.val[i];
    }

    protected long setVal(int i, long e) {
        short x = this.val[i];
        if (e >= maxShort || e <= minShort) {
            throw new IllegalArgumentException("exponent to large: " + e);
        }
        this.val[i] = (short) ((int) e);
        this.hash = 0;
        return (long) x;
    }

    protected short setVal(int i, short e) {
        short x = this.val[i];
        this.val[i] = e;
        this.hash = 0;
        return x;
    }

    public int length() {
        return this.val.length;
    }

    public ExpVectorShort extend(int i, int j, long e) {
        short[] w = new short[(this.val.length + i)];
        System.arraycopy(this.val, 0, w, i, this.val.length);
        if (j >= i) {
            throw new IllegalArgumentException("i " + i + " <= j " + j + " invalid");
        } else if (e >= maxShort || e <= minShort) {
            throw new IllegalArgumentException("exponent to large: " + e);
        } else {
            w[j] = (short) ((int) e);
            return new ExpVectorShort(w);
        }
    }

    public ExpVectorShort extendLower(int i, int j, long e) {
        short[] w = new short[(this.val.length + i)];
        System.arraycopy(this.val, 0, w, 0, this.val.length);
        if (j >= i) {
            throw new IllegalArgumentException("i " + i + " <= j " + j + " invalid");
        }
        w[this.val.length + j] = (short) ((int) e);
        return new ExpVectorShort(w);
    }

    public ExpVectorShort contract(int i, int len) {
        if (i + len > this.val.length) {
            throw new IllegalArgumentException("len " + len + " > val.len " + this.val.length);
        }
        short[] w = new short[len];
        System.arraycopy(this.val, i, w, 0, len);
        return new ExpVectorShort(w);
    }

    public ExpVectorShort reverse() {
        short[] w = new short[this.val.length];
        for (int i = 0; i < this.val.length; i++) {
            w[i] = this.val[(this.val.length - 1) - i];
        }
        return new ExpVectorShort(w);
    }

    public ExpVectorShort reverse(int j) {
        if (j <= 0 || j > this.val.length) {
            return this;
        }
        int i;
        short[] w = new short[this.val.length];
        for (i = 0; i < j; i++) {
            w[i] = this.val[i];
        }
        for (i = j; i < this.val.length; i++) {
            w[i] = this.val[((this.val.length + j) - 1) - i];
        }
        this(w);
        return this;
    }

    public ExpVectorShort combine(ExpVector V) {
        if (V == null || V.length() == 0) {
            return this;
        }
        ExpVectorShort Vi = (ExpVectorShort) V;
        if (this.val.length == 0) {
            return Vi;
        }
        short[] w = new short[(this.val.length + Vi.val.length)];
        System.arraycopy(this.val, 0, w, 0, this.val.length);
        System.arraycopy(Vi.val, 0, w, this.val.length, Vi.val.length);
        return new ExpVectorShort(w);
    }

    public ExpVectorShort permutation(List<Integer> P) {
        short[] w = new short[this.val.length];
        int j = 0;
        for (Integer i : P) {
            int j2 = j + 1;
            w[j] = this.val[i.intValue()];
            j = j2;
        }
        return new ExpVectorShort(w);
    }

    public String toString() {
        return super.toString() + ":short";
    }

    public boolean equals(Object B) {
        if ((B instanceof ExpVectorShort) && invLexCompareTo((ExpVectorShort) B) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public ExpVectorShort abs() {
        short[] u = this.val;
        short[] w = new short[u.length];
        for (int i = 0; i < u.length; i++) {
            if (((long) u[i]) >= 0) {
                w[i] = u[i];
            } else {
                w[i] = (short) (-u[i]);
            }
        }
        return new ExpVectorShort(w);
    }

    public ExpVectorShort negate() {
        short[] u = this.val;
        short[] w = new short[u.length];
        for (int i = 0; i < u.length; i++) {
            w[i] = (short) (-u[i]);
        }
        return new ExpVectorShort(w);
    }

    public ExpVectorShort sum(ExpVector V) {
        short[] u = this.val;
        short[] v = ((ExpVectorShort) V).val;
        short[] w = new short[u.length];
        for (int i = 0; i < u.length; i++) {
            w[i] = (short) (u[i] + v[i]);
        }
        return new ExpVectorShort(w);
    }

    public ExpVectorShort subtract(ExpVector V) {
        short[] u = this.val;
        short[] v = ((ExpVectorShort) V).val;
        short[] w = new short[u.length];
        for (int i = 0; i < u.length; i++) {
            w[i] = (short) (u[i] - v[i]);
        }
        return new ExpVectorShort(w);
    }

    public ExpVectorShort subst(int i, short d) {
        ExpVectorShort V = copy();
        V.setVal(i, d);
        return V;
    }

    public ExpVectorShort subst(int i, long d) {
        ExpVectorShort V = copy();
        V.setVal(i, d);
        return V;
    }

    public int signum() {
        int t = 0;
        short[] u = this.val;
        for (int i = 0; i < u.length; i++) {
            if (u[i] < (short) 0) {
                return -1;
            }
            if (u[i] > (short) 0) {
                t = 1;
            }
        }
        return t;
    }

    public long totalDeg() {
        long t = 0;
        for (short s : this.val) {
            t += (long) s;
        }
        return t;
    }

    public long maxDeg() {
        long t = 0;
        short[] u = this.val;
        for (int i = 0; i < u.length; i++) {
            if (((long) u[i]) > t) {
                t = (long) u[i];
            }
        }
        return t;
    }

    public long weightDeg(long[][] w) {
        if (w == null || w.length == 0) {
            return totalDeg();
        }
        long t = 0;
        short[] u = this.val;
        for (long[] wj : w) {
            for (int i = 0; i < u.length; i++) {
                t += wj[i] * ((long) u[i]);
            }
        }
        return t;
    }

    public ExpVectorShort lcm(ExpVector V) {
        short[] u = this.val;
        short[] v = ((ExpVectorShort) V).val;
        short[] w = new short[u.length];
        int i = 0;
        while (i < u.length) {
            w[i] = u[i] >= v[i] ? u[i] : v[i];
            i++;
        }
        return new ExpVectorShort(w);
    }

    public ExpVectorShort gcd(ExpVector V) {
        short[] u = this.val;
        short[] v = ((ExpVectorShort) V).val;
        short[] w = new short[u.length];
        int i = 0;
        while (i < u.length) {
            w[i] = u[i] <= v[i] ? u[i] : v[i];
            i++;
        }
        return new ExpVectorShort(w);
    }

    public int[] dependencyOnVariables() {
        int i;
        short[] u = this.val;
        int l = 0;
        for (short s : u) {
            if (s > (short) 0) {
                l++;
            }
        }
        int[] dep = new int[l];
        if (l != 0) {
            int j = 0;
            for (i = 0; i < u.length; i++) {
                if (u[i] > (short) 0) {
                    dep[j] = i;
                    j++;
                }
            }
        }
        return dep;
    }

    public boolean multipleOf(ExpVector V) {
        short[] u = this.val;
        short[] v = ((ExpVectorShort) V).val;
        for (int i = 0; i < u.length; i++) {
            if (u[i] < v[i]) {
                return false;
            }
        }
        return true;
    }

    public int compareTo(ExpVector V) {
        return invLexCompareTo(V);
    }

    public int invLexCompareTo(ExpVector V) {
        short[] u = this.val;
        short[] v = ((ExpVectorShort) V).val;
        for (int i = 0; i < u.length; i++) {
            if (u[i] > v[i]) {
                return 1;
            }
            if (u[i] < v[i]) {
                return -1;
            }
        }
        return 0;
    }

    public int invLexCompareTo(ExpVector V, int begin, int end) {
        short[] u = this.val;
        short[] v = ((ExpVectorShort) V).val;
        for (int i = begin; i < end; i++) {
            if (u[i] > v[i]) {
                return 1;
            }
            if (u[i] < v[i]) {
                return -1;
            }
        }
        return 0;
    }

    public int invGradCompareTo(ExpVector V) {
        short[] u = this.val;
        short[] v = ((ExpVectorShort) V).val;
        int t = 0;
        int i = 0;
        while (i < u.length) {
            if (u[i] > v[i]) {
                t = 1;
                break;
            } else if (u[i] < v[i]) {
                t = -1;
                break;
            } else {
                i++;
            }
        }
        if (t == 0) {
            return t;
        }
        long up = 0;
        long vp = 0;
        for (int j = i; j < u.length; j++) {
            up += (long) u[j];
            vp += (long) v[j];
        }
        if (up > vp) {
            t = 1;
        } else if (up < vp) {
            t = -1;
        }
        return t;
    }

    public int invGradCompareTo(ExpVector V, int begin, int end) {
        short[] u = this.val;
        short[] v = ((ExpVectorShort) V).val;
        int t = 0;
        int i = begin;
        while (i < end) {
            if (u[i] > v[i]) {
                t = 1;
                break;
            } else if (u[i] < v[i]) {
                t = -1;
                break;
            } else {
                i++;
            }
        }
        if (t == 0) {
            return t;
        }
        long up = 0;
        long vp = 0;
        for (int j = i; j < end; j++) {
            up += (long) u[j];
            vp += (long) v[j];
        }
        if (up > vp) {
            t = 1;
        } else if (up < vp) {
            t = -1;
        }
        return t;
    }

    public int revInvLexCompareTo(ExpVector V) {
        short[] u = this.val;
        short[] v = ((ExpVectorShort) V).val;
        for (int i = u.length - 1; i >= 0; i--) {
            if (u[i] > v[i]) {
                return 1;
            }
            if (u[i] < v[i]) {
                return -1;
            }
        }
        return 0;
    }

    public int revInvLexCompareTo(ExpVector V, int begin, int end) {
        short[] u = this.val;
        short[] v = ((ExpVectorShort) V).val;
        for (int i = end - 1; i >= begin; i--) {
            if (u[i] > v[i]) {
                return 1;
            }
            if (u[i] < v[i]) {
                return -1;
            }
        }
        return 0;
    }

    public int revInvGradCompareTo(ExpVector V) {
        short[] u = this.val;
        short[] v = ((ExpVectorShort) V).val;
        int t = 0;
        int i = u.length - 1;
        while (i >= 0) {
            if (u[i] > v[i]) {
                t = 1;
                break;
            } else if (u[i] < v[i]) {
                t = -1;
                break;
            } else {
                i--;
            }
        }
        if (t == 0) {
            return t;
        }
        long up = 0;
        long vp = 0;
        for (int j = i; j >= 0; j--) {
            up += (long) u[j];
            vp += (long) v[j];
        }
        if (up > vp) {
            t = 1;
        } else if (up < vp) {
            t = -1;
        }
        return t;
    }

    public int revInvGradCompareTo(ExpVector V, int begin, int end) {
        short[] u = this.val;
        short[] v = ((ExpVectorShort) V).val;
        int t = 0;
        int i = end - 1;
        while (i >= begin) {
            if (u[i] > v[i]) {
                t = 1;
                break;
            } else if (u[i] < v[i]) {
                t = -1;
                break;
            } else {
                i--;
            }
        }
        if (t == 0) {
            return t;
        }
        long up = 0;
        long vp = 0;
        for (int j = i; j >= begin; j--) {
            up += (long) u[j];
            vp += (long) v[j];
        }
        if (up > vp) {
            t = 1;
        } else if (up < vp) {
            t = -1;
        }
        return t;
    }

    public int invWeightCompareTo(long[][] w, ExpVector V) {
        short[] u = this.val;
        short[] v = ((ExpVectorShort) V).val;
        int t = 0;
        int i = 0;
        while (i < u.length) {
            if (u[i] > v[i]) {
                t = 1;
                break;
            } else if (u[i] < v[i]) {
                t = -1;
                break;
            } else {
                i++;
            }
        }
        if (t == 0) {
            return t;
        }
        for (long[] wk : w) {
            long up = 0;
            long vp = 0;
            for (int j = i; j < u.length; j++) {
                up += wk[j] * ((long) u[j]);
                vp += wk[j] * ((long) v[j]);
            }
            if (up > vp) {
                return 1;
            }
            if (up < vp) {
                return -1;
            }
        }
        return t;
    }

    public int invWeightCompareTo(long[][] w, ExpVector V, int begin, int end) {
        short[] u = this.val;
        short[] v = ((ExpVectorShort) V).val;
        int t = 0;
        int i = begin;
        while (i < end) {
            if (u[i] > v[i]) {
                t = 1;
                break;
            } else if (u[i] < v[i]) {
                t = -1;
                break;
            } else {
                i++;
            }
        }
        if (t == 0) {
            return t;
        }
        for (long[] wk : w) {
            long up = 0;
            long vp = 0;
            for (int j = i; j < end; j++) {
                up += wk[j] * ((long) u[j]);
                vp += wk[j] * ((long) v[j]);
            }
            if (up > vp) {
                return 1;
            }
            if (up < vp) {
                return -1;
            }
        }
        return t;
    }
}
