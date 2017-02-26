package edu.jas.poly;

import java.io.Serializable;

public class Overlap implements Serializable {
    public final Word l1;
    public final Word l2;
    public final Word r1;
    public final Word r2;

    public Overlap(Word l1, Word r1, Word l2, Word r2) {
        this.l1 = l1;
        this.r1 = r1;
        this.l2 = l2;
        this.r2 = r2;
    }

    public boolean isOverlap(Word u, Word v) {
        Word a = this.l1.multiply(u).multiply(this.r1);
        Word b = this.l2.multiply(v).multiply(this.r2);
        boolean t = a.equals(b);
        if (!t) {
            System.out.println("a = " + a + " !=  b = " + b);
        }
        return t;
    }

    public String toString() {
        StringBuffer s = new StringBuffer("Overlap[");
        s.append(this.l1);
        s.append(", ");
        s.append(this.r1);
        s.append(", ");
        s.append(this.l2);
        s.append(", ");
        s.append(this.r2);
        s.append("]");
        return s.toString();
    }
}
