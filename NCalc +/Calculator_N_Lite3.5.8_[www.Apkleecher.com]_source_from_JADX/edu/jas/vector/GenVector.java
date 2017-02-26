package edu.jas.vector;

import edu.jas.kern.PrettyPrint;
import edu.jas.structure.AbelianGroupElem;
import edu.jas.structure.Element;
import edu.jas.structure.ModulElem;
import edu.jas.structure.MonoidElem;
import edu.jas.structure.RingElem;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class GenVector<C extends RingElem<C>> implements ModulElem<GenVector<C>, C> {
    private static final Logger logger;
    public final GenVectorModul<C> modul;
    public final List<C> val;

    static {
        logger = Logger.getLogger(GenVector.class);
    }

    public GenVector(GenVectorModul<C> m) {
        this(m, m.getZERO().val);
    }

    public GenVector(GenVectorModul<C> m, List<C> v) {
        if (m == null || v == null) {
            throw new IllegalArgumentException("Empty m or v not allowed, m = " + m + ", v = " + v);
        }
        this.modul = m;
        this.val = v;
        logger.info(this.modul.cols + " vector constructed");
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append("[ ");
        boolean first = true;
        for (RingElem c : this.val) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            s.append(c.toString());
        }
        s.append(" ]");
        if (!PrettyPrint.isTrue()) {
            s.append(" :: " + this.modul.toString());
            s.append("\n");
        }
        return s.toString();
    }

    public String toScript() {
        StringBuffer s = new StringBuffer();
        s.append("( ");
        boolean first = true;
        for (RingElem c : this.val) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            s.append(c.toScript());
        }
        s.append(" )");
        return s.toString();
    }

    public String toScriptFactory() {
        return factory().toScript();
    }

    public GenVectorModul<C> factory() {
        return this.modul;
    }

    public GenVector<C> copy() {
        return new GenVector(this.modul, new ArrayList(this.val));
    }

    public boolean isZERO() {
        return compareTo(this.modul.getZERO()) == 0;
    }

    public boolean equals(Object other) {
        if (!(other instanceof GenVector)) {
            return false;
        }
        GenVector ovec = (GenVector) other;
        if (this.modul.equals(ovec.modul) && this.val.equals(ovec.val)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.val.hashCode() * 37) + this.modul.hashCode();
    }

    public int compareTo(GenVector<C> b) {
        if (!this.modul.equals(b.modul)) {
            return -1;
        }
        List<C> oval = b.val;
        int i = 0;
        for (RingElem c : this.val) {
            int i2 = i + 1;
            int s = c.compareTo((Element) oval.get(i));
            if (s != 0) {
                return s;
            }
            i = i2;
        }
        return 0;
    }

    public int signum() {
        return compareTo(this.modul.getZERO());
    }

    public GenVector<C> sum(GenVector<C> b) {
        List<C> oval = b.val;
        ArrayList<C> a = new ArrayList(this.modul.cols);
        int i = 0;
        for (RingElem c : this.val) {
            int i2 = i + 1;
            a.add((RingElem) c.sum((AbelianGroupElem) oval.get(i)));
            i = i2;
        }
        return new GenVector(this.modul, a);
    }

    public GenVector<C> subtract(GenVector<C> b) {
        List<C> oval = b.val;
        ArrayList<C> a = new ArrayList(this.modul.cols);
        int i = 0;
        for (RingElem c : this.val) {
            int i2 = i + 1;
            a.add((RingElem) c.subtract((AbelianGroupElem) oval.get(i)));
            i = i2;
        }
        return new GenVector(this.modul, a);
    }

    public GenVector<C> negate() {
        ArrayList<C> a = new ArrayList(this.modul.cols);
        for (RingElem c : this.val) {
            a.add((RingElem) c.negate());
        }
        return new GenVector(this.modul, a);
    }

    public GenVector<C> abs() {
        if (signum() < 0) {
            return negate();
        }
        return this;
    }

    public GenVector<C> scalarMultiply(C s) {
        ArrayList<C> a = new ArrayList(this.modul.cols);
        for (RingElem c : this.val) {
            a.add((RingElem) c.multiply(s));
        }
        return new GenVector(this.modul, a);
    }

    public GenVector<C> leftScalarMultiply(C s) {
        ArrayList<C> a = new ArrayList(this.modul.cols);
        for (RingElem c : this.val) {
            a.add((RingElem) s.multiply(c));
        }
        return new GenVector(this.modul, a);
    }

    public GenVector<C> linearCombination(C s, GenVector<C> b, C t) {
        List<C> oval = b.val;
        ArrayList<C> a = new ArrayList(this.modul.cols);
        int i = 0;
        for (RingElem c : this.val) {
            int i2 = i + 1;
            a.add((RingElem) ((RingElem) c.multiply(s)).sum((RingElem) ((RingElem) oval.get(i)).multiply(t)));
            i = i2;
        }
        return new GenVector(this.modul, a);
    }

    public GenVector<C> linearCombination(GenVector<C> b, C t) {
        List<C> oval = b.val;
        ArrayList<C> a = new ArrayList(this.modul.cols);
        int i = 0;
        for (RingElem c : this.val) {
            int i2 = i + 1;
            a.add((RingElem) c.sum((RingElem) ((RingElem) oval.get(i)).multiply(t)));
            i = i2;
        }
        return new GenVector(this.modul, a);
    }

    public GenVector<C> linearCombination(C t, GenVector<C> b) {
        List<C> oval = b.val;
        ArrayList<C> a = new ArrayList(this.modul.cols);
        int i = 0;
        for (RingElem c : this.val) {
            int i2 = i + 1;
            a.add((RingElem) c.sum((RingElem) t.multiply((MonoidElem) oval.get(i))));
            i = i2;
        }
        return new GenVector(this.modul, a);
    }

    public GenVector<C> leftLinearCombination(C s, C t, GenVector<C> b) {
        List<C> oval = b.val;
        ArrayList<C> a = new ArrayList(this.modul.cols);
        int i = 0;
        for (RingElem c : this.val) {
            int i2 = i + 1;
            a.add((RingElem) ((RingElem) s.multiply(c)).sum((RingElem) t.multiply((MonoidElem) oval.get(i))));
            i = i2;
        }
        return new GenVector(this.modul, a);
    }

    public C scalarProduct(GenVector<C> b) {
        C a = (RingElem) this.modul.coFac.getZERO();
        List<C> oval = b.val;
        int i = 0;
        for (RingElem c : this.val) {
            RingElem a2 = (RingElem) a.sum((RingElem) c.multiply((MonoidElem) oval.get(i)));
            i++;
        }
        return a;
    }

    public GenVector<C> scalarProduct(List<GenVector<C>> B) {
        GenVector<C> A = this.modul.getZERO();
        int i = 0;
        for (RingElem c : this.val) {
            int i2 = i + 1;
            A = A.sum(((GenVector) B.get(i)).leftScalarMultiply(c));
            i = i2;
        }
        return A;
    }

    public GenVector<C> rightScalarProduct(List<GenVector<C>> B) {
        GenVector<C> A = this.modul.getZERO();
        int i = 0;
        for (RingElem c : this.val) {
            int i2 = i + 1;
            A = A.sum(((GenVector) B.get(i)).scalarMultiply(c));
            i = i2;
        }
        return A;
    }
}
