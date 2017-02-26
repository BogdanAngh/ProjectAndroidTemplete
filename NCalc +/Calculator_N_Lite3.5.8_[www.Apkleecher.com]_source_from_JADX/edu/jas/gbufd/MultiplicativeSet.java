package edu.jas.gbufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.GcdRingElem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class MultiplicativeSet<C extends GcdRingElem<C>> implements Serializable {
    private static final Logger logger;
    public final List<GenPolynomial<C>> mset;
    public final GenPolynomialRing<C> ring;

    static {
        logger = Logger.getLogger(MultiplicativeSet.class);
    }

    public MultiplicativeSet(GenPolynomialRing<C> ring) {
        this(ring, new ArrayList());
        if (ring == null) {
            throw new IllegalArgumentException("only for non null rings");
        }
    }

    protected MultiplicativeSet(GenPolynomialRing<C> ring, List<GenPolynomial<C>> ms) {
        if (ms == null || ring == null) {
            throw new IllegalArgumentException("only for non null parts");
        }
        this.ring = ring;
        this.mset = ms;
    }

    public String toString() {
        return "MultiplicativeSet" + this.mset;
    }

    public boolean equals(Object ob) {
        try {
            MultiplicativeSet<C> c = (MultiplicativeSet) ob;
            if (c != null && this.ring.equals(c.ring)) {
                return this.mset.equals(c.mset);
            }
            return false;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return (this.ring.hashCode() << 17) + this.mset.hashCode();
    }

    public boolean isEmpty() {
        return this.mset.size() == 0;
    }

    public boolean contains(GenPolynomial<C> c) {
        if (c == null || c.isZERO()) {
            return false;
        }
        if (c.isConstant()) {
            return true;
        }
        if (this.mset.isEmpty()) {
            return false;
        }
        GenPolynomial<C> d = c;
        for (GenPolynomial<C> n : this.mset) {
            if (!n.isONE()) {
                do {
                    GenPolynomial<C>[] qr = d.quotientRemainder(n);
                    GenPolynomial<C> q = qr[0];
                    GenPolynomial<C> r = qr[1];
                    if (r.isZERO()) {
                        if (q.isConstant()) {
                            return true;
                        }
                        d = q;
                    }
                    if (!r.isZERO()) {
                        break;
                    }
                } while (!d.isConstant());
            }
        }
        return d.isConstant();
    }

    public boolean contains(List<GenPolynomial<C>> L) {
        if (L == null || L.size() == 0) {
            return true;
        }
        for (GenPolynomial c : L) {
            if (!contains(c)) {
                return false;
            }
        }
        return true;
    }

    public MultiplicativeSet<C> add(GenPolynomial<C> cc) {
        if (cc == null || cc.isZERO() || cc.isConstant()) {
            return this;
        }
        if (this.ring.coFac.isField()) {
            GenPolynomial cc2 = cc.monic();
        }
        if (this.mset.size() == 0) {
            List<GenPolynomial<C>> list = new ArrayList(1);
            list.add(cc2);
            return new MultiplicativeSet(this.ring, list);
        }
        GenPolynomial<C> c = removeFactors(cc2);
        if (c.isConstant()) {
            logger.info("skipped unit or constant = " + c);
            return this;
        }
        if (this.ring.coFac.isField()) {
            c = c.monic();
        }
        if (this.mset.size() == 0) {
            logger.info("added to empty mset = " + c);
        } else {
            logger.info("added to mset = " + c);
        }
        list = new ArrayList(this.mset);
        list.add(c);
        return new MultiplicativeSet(this.ring, list);
    }

    public MultiplicativeSet<C> replace(List<GenPolynomial<C>> L) {
        MultiplicativeSet<C> ms = new MultiplicativeSet(this.ring);
        if (L == null || L.size() == 0) {
            return ms;
        }
        for (GenPolynomial<C> p : L) {
            ms = ms.add(p);
        }
        return ms;
    }

    public GenPolynomial<C> removeFactors(GenPolynomial<C> cc) {
        if (cc == null || cc.isZERO() || cc.isConstant() || this.mset.size() == 0) {
            return cc;
        }
        GenPolynomial<C> c = cc;
        for (GenPolynomial<C> n : this.mset) {
            if (!n.isConstant()) {
                do {
                    GenPolynomial<C>[] qr = c.quotientRemainder(n);
                    GenPolynomial<C> q = qr[0];
                    GenPolynomial<C> r = qr[1];
                    if (r.isZERO()) {
                        if (q.isConstant()) {
                            return q;
                        }
                        c = q;
                    }
                    if (!r.isZERO()) {
                        break;
                    }
                } while (!c.isConstant());
            }
        }
        return c;
    }

    public List<GenPolynomial<C>> removeFactors(List<GenPolynomial<C>> L) {
        if (L == null || L.size() == 0 || this.mset.size() == 0) {
            return L;
        }
        List<GenPolynomial<C>> M = new ArrayList(L.size());
        for (GenPolynomial p : L) {
            M.add(removeFactors(p));
        }
        return M;
    }
}
