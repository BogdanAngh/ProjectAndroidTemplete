package edu.jas.gb;

import edu.jas.poly.GenWordPolynomial;
import edu.jas.structure.RingElem;

public class WordPair<C extends RingElem<C>> implements Comparable<WordPair> {
    public final int i;
    public final int j;
    protected int n;
    public final GenWordPolynomial<C> pi;
    public final GenWordPolynomial<C> pj;

    public WordPair(GenWordPolynomial<C> a, GenWordPolynomial<C> b, int i, int j) {
        this.pi = a;
        this.pj = b;
        this.i = i;
        this.j = j;
        this.n = 0;
    }

    public String toString() {
        return "wordPair(" + this.i + "," + this.j + ",{" + this.pi.length() + "," + this.pj.length() + "}," + this.n + ")";
    }

    public void pairNumber(int n) {
        this.n = n;
    }

    public int getPairNumber() {
        return this.n;
    }

    public boolean equals(Object ob) {
        if ((ob instanceof WordPair) && compareTo((WordPair) ob) == 0) {
            return true;
        }
        return false;
    }

    public int compareTo(WordPair p) {
        int x = p.getPairNumber();
        if (this.n > x) {
            return 1;
        }
        if (this.n < x) {
            return -1;
        }
        return 0;
    }

    public int hashCode() {
        return (this.i << 16) + this.j;
    }
}
