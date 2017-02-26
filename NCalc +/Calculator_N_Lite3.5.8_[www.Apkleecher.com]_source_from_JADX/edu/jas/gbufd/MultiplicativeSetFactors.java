package edu.jas.gbufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.ufd.FactorAbstract;
import edu.jas.ufd.FactorFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class MultiplicativeSetFactors<C extends GcdRingElem<C>> extends MultiplicativeSet<C> {
    private static final Logger logger;
    protected final FactorAbstract<C> engine;

    static {
        logger = Logger.getLogger(MultiplicativeSetFactors.class);
    }

    public MultiplicativeSetFactors(GenPolynomialRing<C> ring) {
        super(ring);
        this.engine = FactorFactory.getImplementation(ring.coFac);
    }

    protected MultiplicativeSetFactors(GenPolynomialRing<C> ring, List<GenPolynomial<C>> ms, FactorAbstract<C> eng) {
        super(ring, ms);
        this.engine = eng;
    }

    public String toString() {
        return "MultiplicativeSetFactors" + this.mset;
    }

    public boolean equals(Object B) {
        if (B instanceof MultiplicativeSetFactors) {
            return super.equals(B);
        }
        return false;
    }

    public MultiplicativeSetFactors<C> add(GenPolynomial<C> cc) {
        if (cc == null || cc.isZERO() || cc.isConstant()) {
            return this;
        }
        if (this.ring.coFac.isField()) {
            cc = cc.monic();
        }
        GenPolynomial c = removeFactors((GenPolynomial) cc);
        if (c.isConstant()) {
            logger.info("skipped unit or constant = " + c);
            return this;
        }
        List<GenPolynomial<C>> list = this.engine.factorsRadical(c);
        logger.info("factorsRadical = " + list);
        if (this.ring.coFac.isField()) {
            list = PolyUtil.monic((List) list);
        }
        List<GenPolynomial<C>> ms = new ArrayList(this.mset);
        for (GenPolynomial<C> p : list) {
            if (!(p.isConstant() || p.isZERO() || this.mset.contains(p))) {
                logger.info("added to irreducible mset = " + p);
                ms.add(p);
            }
        }
        return new MultiplicativeSetFactors(this.ring, ms, this.engine);
    }

    public MultiplicativeSetFactors<C> replace(List<GenPolynomial<C>> L) {
        MultiplicativeSetFactors<C> ms = new MultiplicativeSetFactors(this.ring);
        if (L == null || L.size() == 0) {
            return ms;
        }
        for (GenPolynomial p : L) {
            ms = ms.add(p);
        }
        return ms;
    }
}
