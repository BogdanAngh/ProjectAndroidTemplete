package edu.jas.poly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ExpVectorLong extends ExpVector {
    final long[] val;

    public ExpVectorLong(int n) {
        this(new long[n]);
    }

    public ExpVectorLong(int n, int i, long e) {
        this(new long[n]);
        this.val[i] = e;
    }

    public ExpVectorLong(long[] v) {
        if (v == null) {
            throw new IllegalArgumentException("null val not allowed");
        }
        this.val = Arrays.copyOf(v, v.length);
    }

    public ExpVectorLong(String s) throws NumberFormatException {
        List<Long> exps = new ArrayList();
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
            exps.add(Long.valueOf(Long.parseLong(s.substring(b, k))));
            b = k + 1;
        }
        if (b <= e) {
            exps.add(Long.valueOf(Long.parseLong(s.substring(b, e))));
        }
        int length = exps.size();
        this.val = new long[length];
        for (int j = 0; j < length; j++) {
            this.val[j] = ((Long) exps.get(j)).longValue();
        }
    }

    public ExpVectorLong copy() {
        long[] w = new long[this.val.length];
        System.arraycopy(this.val, 0, w, 0, this.val.length);
        return new ExpVectorLong(w);
    }

    long[] getVal() {
        return this.val;
    }

    public long getVal(int i) {
        return this.val[i];
    }

    protected long setVal(int i, long e) {
        long x = this.val[i];
        this.val[i] = e;
        this.hash = 0;
        return x;
    }

    public int length() {
        return this.val.length;
    }

    public ExpVectorLong extend(int i, int j, long e) {
        long[] w = new long[(this.val.length + i)];
        System.arraycopy(this.val, 0, w, i, this.val.length);
        if (j >= i) {
            throw new IllegalArgumentException("i " + i + " <= j " + j + " invalid");
        }
        w[j] = e;
        return new ExpVectorLong(w);
    }

    public ExpVectorLong extendLower(int i, int j, long e) {
        long[] w = new long[(this.val.length + i)];
        System.arraycopy(this.val, 0, w, 0, this.val.length);
        if (j >= i) {
            throw new IllegalArgumentException("i " + i + " <= j " + j + " invalid");
        }
        w[this.val.length + j] = e;
        return new ExpVectorLong(w);
    }

    public ExpVectorLong contract(int i, int len) {
        if (i + len > this.val.length) {
            throw new IllegalArgumentException("len " + len + " > val.len " + this.val.length);
        }
        long[] w = new long[len];
        System.arraycopy(this.val, i, w, 0, len);
        return new ExpVectorLong(w);
    }

    public ExpVectorLong reverse() {
        long[] w = new long[this.val.length];
        for (int i = 0; i < this.val.length; i++) {
            w[i] = this.val[(this.val.length - 1) - i];
        }
        return new ExpVectorLong(w);
    }

    public ExpVectorLong reverse(int j) {
        if (j <= 0 || j > this.val.length) {
            return this;
        }
        int i;
        long[] w = new long[this.val.length];
        for (i = 0; i < j; i++) {
            w[i] = this.val[i];
        }
        for (i = j; i < this.val.length; i++) {
            w[i] = this.val[((this.val.length + j) - 1) - i];
        }
        this(w);
        return this;
    }

    public ExpVectorLong reverseUpper(int j) {
        if (j <= 0 || j > this.val.length) {
            return this;
        }
        int i;
        long[] w = new long[this.val.length];
        for (i = 0; i < j; i++) {
            w[i] = this.val[(j - 1) - i];
        }
        for (i = j; i < this.val.length; i++) {
            w[i] = this.val[i];
        }
        this(w);
        return this;
    }

    public ExpVectorLong combine(ExpVector V) {
        if (V == null || V.length() == 0) {
            return this;
        }
        ExpVectorLong Vl = (ExpVectorLong) V;
        if (this.val.length == 0) {
            return Vl;
        }
        long[] w = new long[(this.val.length + Vl.val.length)];
        System.arraycopy(this.val, 0, w, 0, this.val.length);
        System.arraycopy(Vl.val, 0, w, this.val.length, Vl.val.length);
        return new ExpVectorLong(w);
    }

    public ExpVectorLong permutation(List<Integer> P) {
        long[] w = new long[this.val.length];
        int j = 0;
        for (Integer i : P) {
            int j2 = j + 1;
            w[j] = this.val[i.intValue()];
            j = j2;
        }
        return new ExpVectorLong(w);
    }

    public String toString() {
        return super.toString() + ":long";
    }

    public boolean equals(Object B) {
        if ((B instanceof ExpVectorLong) && invLexCompareTo((ExpVectorLong) B) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public ExpVectorLong abs() {
        long[] u = this.val;
        long[] w = new long[u.length];
        for (int i = 0; i < u.length; i++) {
            if (u[i] >= 0) {
                w[i] = u[i];
            } else {
                w[i] = -u[i];
            }
        }
        return new ExpVectorLong(w);
    }

    public ExpVectorLong negate() {
        long[] u = this.val;
        long[] w = new long[u.length];
        for (int i = 0; i < u.length; i++) {
            w[i] = -u[i];
        }
        return new ExpVectorLong(w);
    }

    public ExpVectorLong sum(ExpVector V) {
        long[] u = this.val;
        long[] v = ((ExpVectorLong) V).val;
        long[] w = new long[u.length];
        for (int i = 0; i < u.length; i++) {
            w[i] = u[i] + v[i];
        }
        return new ExpVectorLong(w);
    }

    public ExpVectorLong subtract(ExpVector V) {
        long[] u = this.val;
        long[] v = ((ExpVectorLong) V).val;
        long[] w = new long[u.length];
        for (int i = 0; i < u.length; i++) {
            w[i] = u[i] - v[i];
        }
        return new ExpVectorLong(w);
    }

    public ExpVectorLong subst(int i, long d) {
        ExpVectorLong V = copy();
        V.setVal(i, d);
        return V;
    }

    public int signum() {
        int t = 0;
        long[] u = this.val;
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
        for (long j : this.val) {
            t += j;
        }
        return t;
    }

    public long maxDeg() {
        long t = 0;
        long[] u = this.val;
        for (int i = 0; i < u.length; i++) {
            if (u[i] > t) {
                t = u[i];
            }
        }
        return t;
    }

    public long weightDeg(long[][] w) {
        if (w == null || w.length == 0) {
            return totalDeg();
        }
        long t = 0;
        long[] u = this.val;
        for (long[] wj : w) {
            for (int i = 0; i < u.length; i++) {
                t += wj[i] * u[i];
            }
        }
        return t;
    }

    public ExpVectorLong lcm(ExpVector V) {
        long[] u = this.val;
        long[] v = ((ExpVectorLong) V).val;
        long[] w = new long[u.length];
        int i = 0;
        while (i < u.length) {
            w[i] = u[i] >= v[i] ? u[i] : v[i];
            i++;
        }
        return new ExpVectorLong(w);
    }

    public ExpVectorLong gcd(ExpVector V) {
        long[] u = this.val;
        long[] v = ((ExpVectorLong) V).val;
        long[] w = new long[u.length];
        int i = 0;
        while (i < u.length) {
            w[i] = u[i] <= v[i] ? u[i] : v[i];
            i++;
        }
        return new ExpVectorLong(w);
    }

    public int[] dependencyOnVariables() {
        int i;
        long[] u = this.val;
        int l = 0;
        for (long j : u) {
            if (j > 0) {
                l++;
            }
        }
        int[] dep = new int[l];
        if (l != 0) {
            int j2 = 0;
            for (i = 0; i < u.length; i++) {
                if (u[i] > 0) {
                    dep[j2] = i;
                    j2++;
                }
            }
        }
        return dep;
    }

    public boolean multipleOf(ExpVector V) {
        long[] u = this.val;
        long[] v = ((ExpVectorLong) V).val;
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
        long[] u = this.val;
        long[] v = ((ExpVectorLong) V).val;
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
        long[] u = this.val;
        long[] v = ((ExpVectorLong) V).val;
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
        long[] u = this.val;
        long[] v = ((ExpVectorLong) V).val;
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
            up += u[j];
            vp += v[j];
        }
        if (up > vp) {
            t = 1;
        } else if (up < vp) {
            t = -1;
        }
        return t;
    }

    public int invGradCompareTo(ExpVector V, int begin, int end) {
        long[] u = this.val;
        long[] v = ((ExpVectorLong) V).val;
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
            up += u[j];
            vp += v[j];
        }
        if (up > vp) {
            t = 1;
        } else if (up < vp) {
            t = -1;
        }
        return t;
    }

    public int revInvLexCompareTo(ExpVector V) {
        long[] u = this.val;
        long[] v = ((ExpVectorLong) V).val;
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
        long[] u = this.val;
        long[] v = ((ExpVectorLong) V).val;
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
        long[] u = this.val;
        long[] v = ((ExpVectorLong) V).val;
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
            up += u[j];
            vp += v[j];
        }
        if (up > vp) {
            t = 1;
        } else if (up < vp) {
            t = -1;
        }
        return t;
    }

    public int revInvGradCompareTo(ExpVector V, int begin, int end) {
        long[] u = this.val;
        long[] v = ((ExpVectorLong) V).val;
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
            up += u[j];
            vp += v[j];
        }
        if (up > vp) {
            t = 1;
        } else if (up < vp) {
            t = -1;
        }
        return t;
    }

    public int invWeightCompareTo(long[][] w, ExpVector V) {
        long[] u = this.val;
        long[] v = ((ExpVectorLong) V).val;
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
                up += wk[j] * u[j];
                vp += wk[j] * v[j];
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
        long[] u = this.val;
        long[] v = ((ExpVectorLong) V).val;
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
                up += wk[j] * u[j];
                vp += wk[j] * v[j];
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
