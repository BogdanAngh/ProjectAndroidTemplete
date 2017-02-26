package edu.jas.vector;

import edu.jas.kern.PrettyPrint;
import edu.jas.structure.AbelianGroupElem;
import edu.jas.structure.AlgebraElem;
import edu.jas.structure.Element;
import edu.jas.structure.MonoidElem;
import edu.jas.structure.RingElem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

public class GenMatrix<C extends RingElem<C>> implements AlgebraElem<GenMatrix<C>, C> {
    private static final Logger logger;
    private int hashValue;
    public final ArrayList<ArrayList<C>> matrix;
    public final GenMatrixRing<C> ring;

    static {
        logger = Logger.getLogger(GenMatrix.class);
    }

    public GenMatrix(GenMatrixRing<C> r) {
        this((GenMatrixRing) r, r.getZERO().matrix);
    }

    public GenMatrix(GenMatrixRing<C> r, List<List<C>> m) {
        this.hashValue = 0;
        this.ring = r;
        this.matrix = new ArrayList(r.rows);
        for (List<C> row : m) {
            this.matrix.add(new ArrayList(row));
        }
        logger.info(this.ring.rows + " x " + this.ring.cols + " matrix constructed");
    }

    public GenMatrix(GenMatrixRing<C> r, ArrayList<ArrayList<C>> m) {
        this.hashValue = 0;
        if (r == null || m == null) {
            throw new IllegalArgumentException("Empty r or m not allowed, r = " + r + ", m = " + m);
        }
        this.ring = r;
        this.matrix = new ArrayList(m);
        logger.info(this.ring.rows + " x " + this.ring.cols + " matrix constructed");
    }

    public C get(int i, int j) {
        return (RingElem) ((ArrayList) this.matrix.get(i)).get(j);
    }

    public void setMutate(int i, int j, C el) {
        ((ArrayList) this.matrix.get(i)).set(j, el);
        this.hashValue = 0;
    }

    public GenMatrix<C> set(int i, int j, C el) {
        GenMatrix<C> mat = copy();
        mat.setMutate(i, j, el);
        return mat;
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        boolean firstRow = true;
        s.append("[\n");
        Iterator it = this.matrix.iterator();
        while (it.hasNext()) {
            List<C> val = (List) it.next();
            if (firstRow) {
                firstRow = false;
            } else {
                s.append(",\n");
            }
            boolean first = true;
            s.append("[ ");
            for (C c : val) {
                if (first) {
                    first = false;
                } else {
                    s.append(", ");
                }
                s.append(c.toString());
            }
            s.append(" ]");
        }
        s.append(" ] ");
        if (!PrettyPrint.isTrue()) {
            s.append(":: " + this.ring.toString());
            s.append("\n");
        }
        return s.toString();
    }

    public String toScript() {
        StringBuffer s = new StringBuffer();
        boolean firstRow = true;
        s.append("( ");
        Iterator it = this.matrix.iterator();
        while (it.hasNext()) {
            List<C> val = (List) it.next();
            if (firstRow) {
                firstRow = false;
            } else {
                s.append(", ");
            }
            boolean first = true;
            s.append("( ");
            for (C c : val) {
                if (first) {
                    first = false;
                } else {
                    s.append(", ");
                }
                s.append(c.toScript());
            }
            s.append(" )");
        }
        s.append(" ) ");
        return s.toString();
    }

    public String toScriptFactory() {
        return factory().toScript();
    }

    public GenMatrixRing<C> factory() {
        return this.ring;
    }

    public GenMatrix<C> copy() {
        ArrayList m = new ArrayList(this.ring.rows);
        Iterator i$ = this.matrix.iterator();
        while (i$.hasNext()) {
            m.add(new ArrayList((ArrayList) i$.next()));
        }
        return new GenMatrix(this.ring, m);
    }

    public boolean isZERO() {
        Iterator it = this.matrix.iterator();
        while (it.hasNext()) {
            for (C elem : (List) it.next()) {
                if (!elem.isZERO()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isONE() {
        int i = 0;
        Iterator it = this.matrix.iterator();
        while (it.hasNext()) {
            int j = 0;
            for (C elem : (List) it.next()) {
                if (i == j) {
                    if (!elem.isONE()) {
                        return false;
                    }
                } else if (!elem.isZERO()) {
                    return false;
                }
                j++;
            }
            i++;
        }
        return true;
    }

    public boolean equals(Object other) {
        if (!(other instanceof GenMatrix)) {
            return false;
        }
        GenMatrix om = (GenMatrix) other;
        if (this.ring.equals(om.ring) && this.matrix.equals(om.matrix)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.hashValue == 0) {
            this.hashValue = (this.matrix.hashCode() * 37) + this.ring.hashCode();
            if (this.hashValue == 0) {
                this.hashValue = 1;
            }
        }
        return this.hashValue;
    }

    public int compareTo(GenMatrix<C> b) {
        if (!this.ring.equals(b.ring)) {
            return -1;
        }
        ArrayList<ArrayList<C>> om = b.matrix;
        int i = 0;
        Iterator it = this.matrix.iterator();
        while (it.hasNext()) {
            int i2 = i + 1;
            ArrayList<C> ov = (ArrayList) om.get(i);
            int j = 0;
            Iterator i$ = ((ArrayList) it.next()).iterator();
            while (i$.hasNext()) {
                int j2 = j + 1;
                int s = ((RingElem) i$.next()).compareTo((Element) ov.get(j));
                if (s != 0) {
                    return s;
                }
                j = j2;
            }
            i = i2;
        }
        return 0;
    }

    public boolean isUnit() {
        int i = 0;
        Iterator it = this.matrix.iterator();
        while (it.hasNext()) {
            int j = 0;
            Iterator i$ = ((ArrayList) it.next()).iterator();
            while (i$.hasNext()) {
                RingElem el = (RingElem) i$.next();
                if (i == j) {
                    if (!el.isUnit()) {
                        return false;
                    }
                } else if (!el.isZERO()) {
                    return false;
                }
                j++;
            }
            i++;
        }
        return true;
    }

    public int signum() {
        return compareTo(this.ring.getZERO());
    }

    public GenMatrix<C> sum(GenMatrix<C> b) {
        ArrayList<ArrayList<C>> om = b.matrix;
        ArrayList m = new ArrayList(this.ring.rows);
        int i = 0;
        Iterator it = this.matrix.iterator();
        while (it.hasNext()) {
            ArrayList<C> val = (ArrayList) it.next();
            int i2 = i + 1;
            ArrayList<C> ov = (ArrayList) om.get(i);
            ArrayList<C> v = new ArrayList(this.ring.cols);
            int j = 0;
            Iterator i$ = val.iterator();
            while (i$.hasNext()) {
                int j2 = j + 1;
                v.add((RingElem) ((RingElem) i$.next()).sum((AbelianGroupElem) ov.get(j)));
                j = j2;
            }
            m.add(v);
            i = i2;
        }
        return new GenMatrix(this.ring, m);
    }

    public GenMatrix<C> subtract(GenMatrix<C> b) {
        ArrayList<ArrayList<C>> om = b.matrix;
        ArrayList m = new ArrayList(this.ring.rows);
        int i = 0;
        Iterator it = this.matrix.iterator();
        while (it.hasNext()) {
            ArrayList<C> val = (ArrayList) it.next();
            int i2 = i + 1;
            ArrayList<C> ov = (ArrayList) om.get(i);
            ArrayList<C> v = new ArrayList(this.ring.cols);
            int j = 0;
            Iterator i$ = val.iterator();
            while (i$.hasNext()) {
                int j2 = j + 1;
                v.add((RingElem) ((RingElem) i$.next()).subtract((AbelianGroupElem) ov.get(j)));
                j = j2;
            }
            m.add(v);
            i = i2;
        }
        return new GenMatrix(this.ring, m);
    }

    public GenMatrix<C> negate() {
        ArrayList m = new ArrayList(this.ring.rows);
        Iterator it = this.matrix.iterator();
        while (it.hasNext()) {
            ArrayList<C> val = (ArrayList) it.next();
            ArrayList<C> v = new ArrayList(this.ring.cols);
            Iterator i$ = val.iterator();
            while (i$.hasNext()) {
                v.add((RingElem) ((RingElem) i$.next()).negate());
            }
            m.add(v);
        }
        return new GenMatrix(this.ring, m);
    }

    public GenMatrix<C> abs() {
        if (signum() < 0) {
            return negate();
        }
        return this;
    }

    public GenMatrix<C> scalarMultiply(C s) {
        ArrayList m = new ArrayList(this.ring.rows);
        Iterator it = this.matrix.iterator();
        while (it.hasNext()) {
            ArrayList<C> val = (ArrayList) it.next();
            ArrayList<C> v = new ArrayList(this.ring.cols);
            Iterator i$ = val.iterator();
            while (i$.hasNext()) {
                v.add((RingElem) ((RingElem) i$.next()).multiply(s));
            }
            m.add(v);
        }
        return new GenMatrix(this.ring, m);
    }

    public GenMatrix<C> leftScalarMultiply(C s) {
        ArrayList m = new ArrayList(this.ring.rows);
        Iterator it = this.matrix.iterator();
        while (it.hasNext()) {
            ArrayList<C> val = (ArrayList) it.next();
            ArrayList<C> v = new ArrayList(this.ring.cols);
            Iterator i$ = val.iterator();
            while (i$.hasNext()) {
                v.add((RingElem) s.multiply((RingElem) i$.next()));
            }
            m.add(v);
        }
        return new GenMatrix(this.ring, m);
    }

    public GenMatrix<C> linearCombination(C s, GenMatrix<C> b, C t) {
        ArrayList<ArrayList<C>> om = b.matrix;
        ArrayList m = new ArrayList(this.ring.rows);
        int i = 0;
        Iterator it = this.matrix.iterator();
        while (it.hasNext()) {
            ArrayList<C> val = (ArrayList) it.next();
            int i2 = i + 1;
            ArrayList<C> ov = (ArrayList) om.get(i);
            ArrayList<C> v = new ArrayList(this.ring.cols);
            int j = 0;
            Iterator i$ = val.iterator();
            while (i$.hasNext()) {
                int j2 = j + 1;
                v.add((RingElem) ((RingElem) ((RingElem) i$.next()).multiply(s)).sum((RingElem) ((RingElem) ov.get(j)).multiply(t)));
                j = j2;
            }
            m.add(v);
            i = i2;
        }
        return new GenMatrix(this.ring, m);
    }

    public GenMatrix<C> linearCombination(GenMatrix<C> b, C t) {
        ArrayList<ArrayList<C>> om = b.matrix;
        ArrayList m = new ArrayList(this.ring.rows);
        int i = 0;
        Iterator it = this.matrix.iterator();
        while (it.hasNext()) {
            ArrayList<C> val = (ArrayList) it.next();
            int i2 = i + 1;
            ArrayList<C> ov = (ArrayList) om.get(i);
            ArrayList<C> v = new ArrayList(this.ring.cols);
            int j = 0;
            Iterator i$ = val.iterator();
            while (i$.hasNext()) {
                int j2 = j + 1;
                v.add((RingElem) ((RingElem) i$.next()).sum((RingElem) ((RingElem) ov.get(j)).multiply(t)));
                j = j2;
            }
            m.add(v);
            i = i2;
        }
        return new GenMatrix(this.ring, m);
    }

    public GenMatrix<C> linearCombination(C t, GenMatrix<C> b) {
        ArrayList<ArrayList<C>> om = b.matrix;
        ArrayList m = new ArrayList(this.ring.rows);
        int i = 0;
        Iterator it = this.matrix.iterator();
        while (it.hasNext()) {
            ArrayList<C> val = (ArrayList) it.next();
            int i2 = i + 1;
            ArrayList<C> ov = (ArrayList) om.get(i);
            ArrayList<C> v = new ArrayList(this.ring.cols);
            int j = 0;
            Iterator i$ = val.iterator();
            while (i$.hasNext()) {
                int j2 = j + 1;
                v.add((RingElem) ((RingElem) i$.next()).sum((RingElem) t.multiply((MonoidElem) ov.get(j))));
                j = j2;
            }
            m.add(v);
            i = i2;
        }
        return new GenMatrix(this.ring, m);
    }

    public GenMatrix<C> leftLinearCombination(C s, C t, GenMatrix<C> b) {
        ArrayList<ArrayList<C>> om = b.matrix;
        ArrayList m = new ArrayList(this.ring.rows);
        int i = 0;
        Iterator it = this.matrix.iterator();
        while (it.hasNext()) {
            ArrayList<C> val = (ArrayList) it.next();
            int i2 = i + 1;
            ArrayList<C> ov = (ArrayList) om.get(i);
            ArrayList<C> v = new ArrayList(this.ring.cols);
            int j = 0;
            Iterator i$ = val.iterator();
            while (i$.hasNext()) {
                int j2 = j + 1;
                v.add((RingElem) ((RingElem) s.multiply((RingElem) i$.next())).sum((RingElem) t.multiply((MonoidElem) ov.get(j))));
                j = j2;
            }
            m.add(v);
            i = i2;
        }
        return new GenMatrix(this.ring, m);
    }

    public GenMatrix<C> transpose(GenMatrixRing<C> tr) {
        GenMatrix<C> t = tr.getZERO().copy();
        ArrayList<ArrayList<C>> m = t.matrix;
        int i = 0;
        Iterator it = this.matrix.iterator();
        while (it.hasNext()) {
            int j = 0;
            Iterator i$ = ((ArrayList) it.next()).iterator();
            while (i$.hasNext()) {
                ((ArrayList) m.get(j)).set(i, (RingElem) i$.next());
                j++;
            }
            i++;
        }
        return t;
    }

    public GenMatrix<C> multiply(GenMatrix<C> S) {
        int na = this.ring.blocksize;
        int nb = this.ring.blocksize;
        ArrayList<ArrayList<C>> m = this.matrix;
        ArrayList<ArrayList<C>> t = S.transpose(S.ring.transpose()).matrix;
        GenMatrixRing<C> pr = this.ring.product(S.ring);
        ArrayList p = pr.getZERO().copy().matrix;
        for (int ii = 0; ii < m.size(); ii += na) {
            for (int jj = 0; jj < t.size(); jj += nb) {
                for (int i = ii; i < Math.min(ii + na, m.size()); i++) {
                    ArrayList<C> Ai = (ArrayList) m.get(i);
                    for (int j = jj; j < Math.min(jj + nb, t.size()); j++) {
                        ArrayList<C> Bj = (ArrayList) t.get(j);
                        C c = (RingElem) this.ring.coFac.getZERO();
                        for (int k = 0; k < Bj.size(); k++) {
                            RingElem c2 = (RingElem) c.sum((AbelianGroupElem) ((RingElem) Ai.get(k)).multiply((MonoidElem) Bj.get(k)));
                        }
                        ((ArrayList) p.get(i)).set(j, c);
                    }
                }
            }
        }
        return new GenMatrix((GenMatrixRing) pr, p);
    }

    public GenMatrix<C> multiplySimple(GenMatrix<C> S) {
        ArrayList<ArrayList<C>> m = this.matrix;
        ArrayList<ArrayList<C>> B = S.matrix;
        GenMatrixRing pr = this.ring.product(S.ring);
        ArrayList p = pr.getZERO().copy().matrix;
        for (int i = 0; i < pr.rows; i++) {
            ArrayList<C> Ai = (ArrayList) m.get(i);
            for (int j = 0; j < pr.cols; j++) {
                C c = (RingElem) this.ring.coFac.getZERO();
                for (int k = 0; k < S.ring.rows; k++) {
                    RingElem c2 = (RingElem) c.sum((AbelianGroupElem) ((RingElem) Ai.get(k)).multiply((MonoidElem) ((ArrayList) B.get(k)).get(j)));
                }
                ((ArrayList) p.get(i)).set(j, c);
            }
        }
        return new GenMatrix(pr, p);
    }

    public GenMatrix<C> divide(GenMatrix<C> genMatrix) {
        throw new UnsupportedOperationException("divide not yet implemented");
    }

    public GenMatrix<C> remainder(GenMatrix<C> genMatrix) {
        throw new UnsupportedOperationException("remainder not implemented");
    }

    public GenMatrix<C>[] quotientRemainder(GenMatrix<C> S) {
        throw new UnsupportedOperationException("quotientRemainder not implemented, input = " + S);
    }

    public GenMatrix<C> inverse() {
        throw new UnsupportedOperationException("inverse not yet implemented");
    }

    public GenMatrix<C> gcd(GenMatrix<C> genMatrix) {
        throw new UnsupportedOperationException("gcd not implemented");
    }

    public GenMatrix<C>[] egcd(GenMatrix<C> genMatrix) {
        throw new UnsupportedOperationException("egcd not implemented");
    }
}
