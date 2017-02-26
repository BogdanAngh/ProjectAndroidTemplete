package edu.jas.application;

import io.github.kexanie.library.BuildConfig;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;

public class Dimension implements Serializable {
    public final Set<Set<Integer>> M;
    public final Set<Integer> S;
    public final int d;
    public final String[] v;

    public Dimension(int d, Set<Integer> S, Set<Set<Integer>> M, String[] v) {
        this.d = d;
        this.S = S;
        this.M = M;
        this.v = (String[]) Arrays.copyOf(v, v.length);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("Dimension( " + this.d + ", ");
        if (this.v == null) {
            sb.append(BuildConfig.FLAVOR + this.S + ", " + this.M + " )");
            return sb.toString();
        }
        String[] s = new String[this.S.size()];
        int j = 0;
        for (Integer i : this.S) {
            s[j] = this.v[i.intValue()];
            j++;
        }
        sb.append(Arrays.toString(s) + ", ");
        sb.append("[ ");
        boolean first = true;
        for (Set<Integer> m : this.M) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            s = new String[m.size()];
            j = 0;
            for (Integer i2 : m) {
                s[j] = this.v[i2.intValue()];
                j++;
            }
            sb.append(Arrays.toString(s));
        }
        sb.append(" ] )");
        return sb.toString();
    }
}
