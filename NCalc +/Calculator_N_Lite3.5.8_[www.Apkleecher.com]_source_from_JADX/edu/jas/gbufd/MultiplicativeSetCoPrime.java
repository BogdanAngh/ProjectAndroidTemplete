package edu.jas.gbufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.ufd.GCDFactory;
import edu.jas.ufd.GreatestCommonDivisorAbstract;
import java.util.List;
import org.apache.log4j.Logger;

public class MultiplicativeSetCoPrime<C extends GcdRingElem<C>> extends MultiplicativeSet<C> {
    private static final Logger logger;
    protected final GreatestCommonDivisorAbstract<C> engine;

    static {
        logger = Logger.getLogger(MultiplicativeSetCoPrime.class);
    }

    public MultiplicativeSetCoPrime(GenPolynomialRing<C> ring) {
        super(ring);
        this.engine = GCDFactory.getProxy(ring.coFac);
    }

    protected MultiplicativeSetCoPrime(GenPolynomialRing<C> ring, List<GenPolynomial<C>> ms, GreatestCommonDivisorAbstract<C> eng) {
        super(ring, ms);
        this.engine = eng;
    }

    public String toString() {
        return "MultiplicativeSetCoPrime" + this.mset;
    }

    public boolean equals(Object B) {
        if (B instanceof MultiplicativeSetCoPrime) {
            return super.equals(B);
        }
        return false;
    }

    public MultiplicativeSetCoPrime<C> add(GenPolynomial<C> cc) {
        if (cc == null || cc.isZERO() || cc.isConstant()) {
            return this;
        }
        if (this.ring.coFac.isField()) {
            cc = cc.monic();
        }
        List<GenPolynomial<C>> list;
        if (this.mset.size() == 0) {
            list = this.engine.coPrime(cc, this.mset);
            if (this.ring.coFac.isField()) {
                list = PolyUtil.monic((List) list);
            }
            return new MultiplicativeSetCoPrime(this.ring, list, this.engine);
        }
        GenPolynomial<C> c = removeFactors((GenPolynomial) cc);
        if (c.isConstant()) {
            logger.info("skipped unit or constant = " + c);
            return this;
        }
        logger.info("added to co-prime mset = " + c);
        list = this.engine.coPrime(c, this.mset);
        if (this.ring.coFac.isField()) {
            list = PolyUtil.monic((List) list);
        }
        return new MultiplicativeSetCoPrime(this.ring, list, this.engine);
    }

    public MultiplicativeSetCoPrime<C> replace(List<GenPolynomial<C>> L) {
        MultiplicativeSetCoPrime<C> ms = new MultiplicativeSetCoPrime(this.ring);
        if (L == null || L.size() == 0) {
            return ms;
        }
        for (GenPolynomial p : L) {
            ms = ms.add(p);
        }
        return ms;
    }
}
