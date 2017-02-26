package edu.jas.poly;

import java.util.ArrayList;
import java.util.List;

public final class ExpVectorInteger extends ExpVector {
    public static final long maxInt = 1073741823;
    public static final long minInt = -1073741824;
    final int[] val;

    public ExpVectorInteger(int n) {
        this(new int[n]);
    }

    public ExpVectorInteger(int n, int i, int e) {
        this(n);
        this.val[i] = e;
    }

    public ExpVectorInteger(int n, int i, long e) {
        this(n);
        if (e >= maxInt || e <= minInt) {
            throw new IllegalArgumentException("exponent to large: " + e);
        }
        this.val[i] = (int) e;
    }

    protected ExpVectorInteger(int[] v) {
        this.val = v;
    }

    public ExpVectorInteger(long[] v) {
        this(v.length);
        int i = 0;
        while (i < v.length) {
            if (v[i] >= maxInt || v[i] <= minInt) {
                throw new IllegalArgumentException("exponent to large: " + v[i]);
            }
            this.val[i] = (int) v[i];
            i++;
        }
    }

    public ExpVectorInteger(String s) throws NumberFormatException {
        List<Integer> exps = new ArrayList();
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
            exps.add(Integer.valueOf(Integer.parseInt(s.substring(b, k))));
            b = k + 1;
        }
        if (b <= e) {
            exps.add(Integer.valueOf(Integer.parseInt(s.substring(b, e))));
        }
        int length = exps.size();
        this.val = new int[length];
        for (int j = 0; j < length; j++) {
            this.val[j] = ((Integer) exps.get(j)).intValue();
        }
    }

    public ExpVectorInteger copy() {
        int[] w = new int[this.val.length];
        System.arraycopy(this.val, 0, w, 0, this.val.length);
        return new ExpVectorInteger(w);
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
        int x = this.val[i];
        if (e >= maxInt || e <= minInt) {
            throw new IllegalArgumentException("exponent to large: " + e);
        }
        this.val[i] = (int) e;
        this.hash = 0;
        return (long) x;
    }

    protected int setVal(int i, int e) {
        int x = this.val[i];
        this.val[i] = e;
        this.hash = 0;
        return x;
    }

    public int length() {
        return this.val.length;
    }

    public ExpVectorInteger extend(int i, int j, long e) {
        int[] w = new int[(this.val.length + i)];
        System.arraycopy(this.val, 0, w, i, this.val.length);
        if (j >= i) {
            throw new IllegalArgumentException("i " + i + " <= j " + j + " invalid");
        } else if (e >= maxInt || e <= minInt) {
            throw new IllegalArgumentException("exponent to large: " + e);
        } else {
            w[j] = (int) e;
            return new ExpVectorInteger(w);
        }
    }

    public ExpVectorInteger extendLower(int i, int j, long e) {
        int[] w = new int[(this.val.length + i)];
        System.arraycopy(this.val, 0, w, 0, this.val.length);
        if (j >= i) {
            throw new IllegalArgumentException("i " + i + " <= j " + j + " invalid");
        }
        w[this.val.length + j] = (int) e;
        return new ExpVectorInteger(w);
    }

    public ExpVectorInteger contract(int i, int len) {
        if (i + len > this.val.length) {
            throw new IllegalArgumentException("len " + len + " > val.len " + this.val.length);
        }
        int[] w = new int[len];
        System.arraycopy(this.val, i, w, 0, len);
        return new ExpVectorInteger(w);
    }

    public ExpVectorInteger reverse() {
        int[] w = new int[this.val.length];
        for (int i = 0; i < this.val.length; i++) {
            w[i] = this.val[(this.val.length - 1) - i];
        }
        return new ExpVectorInteger(w);
    }

    public ExpVectorInteger reverse(int j) {
        if (j <= 0 || j > this.val.length) {
            return this;
        }
        int i;
        int[] w = new int[this.val.length];
        for (i = 0; i < j; i++) {
            w[i] = this.val[i];
        }
        for (i = j; i < this.val.length; i++) {
            w[i] = this.val[((this.val.length + j) - 1) - i];
        }
        this(w);
        return this;
    }

    public ExpVectorInteger combine(ExpVector V) {
        if (V == null || V.length() == 0) {
            return this;
        }
        ExpVectorInteger Vi = (ExpVectorInteger) V;
        if (this.val.length == 0) {
            return Vi;
        }
        int[] w = new int[(this.val.length + Vi.val.length)];
        System.arraycopy(this.val, 0, w, 0, this.val.length);
        System.arraycopy(Vi.val, 0, w, this.val.length, Vi.val.length);
        return new ExpVectorInteger(w);
    }

    public ExpVectorInteger permutation(List<Integer> P) {
        int[] w = new int[this.val.length];
        int j = 0;
        for (Integer i : P) {
            int j2 = j + 1;
            w[j] = this.val[i.intValue()];
            j = j2;
        }
        return new ExpVectorInteger(w);
    }

    public String toString() {
        return super.toString() + ":int";
    }

    public boolean equals(Object B) {
        if ((B instanceof ExpVectorInteger) && invLexCompareTo((ExpVectorInteger) B) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public ExpVectorInteger abs() {
        int[] u = this.val;
        int[] w = new int[u.length];
        for (int i = 0; i < u.length; i++) {
            if (((long) u[i]) >= 0) {
                w[i] = u[i];
            } else {
                w[i] = -u[i];
            }
        }
        return new ExpVectorInteger(w);
    }

    public ExpVectorInteger negate() {
        int[] u = this.val;
        int[] w = new int[u.length];
        for (int i = 0; i < u.length; i++) {
            w[i] = -u[i];
        }
        return new ExpVectorInteger(w);
    }

    public ExpVectorInteger sum(ExpVector V) {
        int[] u = this.val;
        int[] v = ((ExpVectorInteger) V).val;
        int[] w = new int[u.length];
        for (int i = 0; i < u.length; i++) {
            w[i] = u[i] + v[i];
        }
        return new ExpVectorInteger(w);
    }

    public ExpVectorInteger subtract(ExpVector V) {
        int[] u = this.val;
        int[] v = ((ExpVectorInteger) V).val;
        int[] w = new int[u.length];
        for (int i = 0; i < u.length; i++) {
            w[i] = u[i] - v[i];
        }
        return new ExpVectorInteger(w);
    }

    public ExpVectorInteger subst(int i, long d) {
        ExpVectorInteger V = copy();
        V.setVal(i, d);
        return V;
    }

    public ExpVectorInteger subst(int i, int d) {
        ExpVectorInteger V = copy();
        V.setVal(i, d);
        return V;
    }

    public int signum() {
        int t = 0;
        int[] u = this.val;
        for (int i = 0; i < u.length; i++) {
            if (u[i] < 0) {
                return -1;
            }
            if (u[i] > 0) {
                t = 1;
            }
        }
        return t;
    }

    public long totalDeg() {
        long t = 0;
        for (int i : this.val) {
            t += (long) i;
        }
        return t;
    }

    public long maxDeg() {
        long t = 0;
        int[] u = this.val;
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
        int[] u = this.val;
        for (long[] wj : w) {
            for (int i = 0; i < u.length; i++) {
                t += wj[i] * ((long) u[i]);
            }
        }
        return t;
    }

    public ExpVectorInteger lcm(ExpVector V) {
        int[] u = this.val;
        int[] v = ((ExpVectorInteger) V).val;
        int[] w = new int[u.length];
        int i = 0;
        while (i < u.length) {
            w[i] = u[i] >= v[i] ? u[i] : v[i];
            i++;
        }
        return new ExpVectorInteger(w);
    }

    public ExpVectorInteger gcd(ExpVector V) {
        int[] u = this.val;
        int[] v = ((ExpVectorInteger) V).val;
        int[] w = new int[u.length];
        int i = 0;
        while (i < u.length) {
            w[i] = u[i] <= v[i] ? u[i] : v[i];
            i++;
        }
        return new ExpVectorInteger(w);
    }

    public int[] dependencyOnVariables() {
        int i;
        int[] u = this.val;
        int l = 0;
        for (int i2 : u) {
            if (i2 > 0) {
                l++;
            }
        }
        int[] dep = new int[l];
        if (l != 0) {
            int j = 0;
            for (i = 0; i < u.length; i++) {
                if (u[i] > 0) {
                    dep[j] = i;
                    j++;
                }
            }
        }
        return dep;
    }

    public boolean multipleOf(ExpVector V) {
        int[] u = this.val;
        int[] v = ((ExpVectorInteger) V).val;
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
        int[] u = this.val;
        int[] v = ((ExpVectorInteger) V).val;
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
        int[] u = this.val;
        int[] v = ((ExpVectorInteger) V).val;
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
        int[] u = this.val;
        int[] v = ((ExpVectorInteger) V).val;
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
        int[] u = this.val;
        int[] v = ((ExpVectorInteger) V).val;
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
        int[] u = this.val;
        int[] v = ((ExpVectorInteger) V).val;
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
        int[] u = this.val;
        int[] v = ((ExpVectorInteger) V).val;
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
        int[] u = this.val;
        int[] v = ((ExpVectorInteger) V).val;
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
        int[] u = this.val;
        int[] v = ((ExpVectorInteger) V).val;
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
        int[] u = this.val;
        int[] v = ((ExpVectorInteger) V).val;
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
        int[] u = this.val;
        int[] v = ((ExpVectorInteger) V).val;
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
