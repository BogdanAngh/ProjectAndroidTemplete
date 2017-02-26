package edu.jas.poly;

import java.io.Serializable;

public class ExpVectorPair implements Serializable {
    private final ExpVector e1;
    private final ExpVector e2;

    public ExpVectorPair(ExpVector e, ExpVector f) {
        this.e1 = e;
        this.e2 = f;
    }

    public ExpVector getFirst() {
        return this.e1;
    }

    public ExpVector getSecond() {
        return this.e2;
    }

    public long totalDeg() {
        return this.e1.totalDeg() + this.e2.totalDeg();
    }

    public String toString() {
        StringBuffer s = new StringBuffer("ExpVectorPair[");
        s.append(this.e1.toString());
        s.append(",");
        s.append(this.e2.toString());
        s.append("]");
        return s.toString();
    }

    public boolean equals(Object B) {
        if (B instanceof ExpVectorPair) {
            return equals((ExpVectorPair) B);
        }
        return false;
    }

    public boolean equals(ExpVectorPair b) {
        return this.e1.equals(b.getFirst()) && this.e2.equals(b.getSecond());
    }

    public int hashCode() {
        return (this.e1.hashCode() << 16) + this.e2.hashCode();
    }

    public boolean isMultiple(ExpVectorPair p) {
        boolean w = this.e1.multipleOf(p.getFirst());
        if (!w) {
            return w;
        }
        w = this.e2.multipleOf(p.getSecond());
        if (w) {
            return true;
        }
        return w;
    }
}
