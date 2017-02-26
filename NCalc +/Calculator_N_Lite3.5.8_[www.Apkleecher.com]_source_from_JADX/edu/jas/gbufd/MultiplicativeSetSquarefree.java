package edu.jas.gbufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.ufd.SquarefreeAbstract;
import edu.jas.ufd.SquarefreeFactory;
import java.util.List;
import org.apache.log4j.Logger;

public class MultiplicativeSetSquarefree<C extends GcdRingElem<C>> extends MultiplicativeSet<C> {
    private static final Logger logger;
    protected final SquarefreeAbstract<C> engine;

    static {
        logger = Logger.getLogger(MultiplicativeSetSquarefree.class);
    }

    public MultiplicativeSetSquarefree(GenPolynomialRing<C> ring) {
        super(ring);
        this.engine = SquarefreeFactory.getImplementation(ring.coFac);
    }

    protected MultiplicativeSetSquarefree(GenPolynomialRing<C> ring, List<GenPolynomial<C>> ms, SquarefreeAbstract<C> eng) {
        super(ring, ms);
        this.engine = eng;
    }

    public String toString() {
        return "MultiplicativeSetSquarefree" + this.mset;
    }

    public boolean equals(Object B) {
        if (B instanceof MultiplicativeSetSquarefree) {
            return super.equals(B);
        }
        return false;
    }

    public MultiplicativeSetSquarefree<C> add(GenPolynomial<C> cc) {
        if (cc == null || cc.isZERO() || cc.isConstant()) {
            return this;
        }
        if (this.ring.coFac.isField()) {
            cc = cc.monic();
        }
        List<GenPolynomial<C>> list;
        if (this.mset.size() == 0) {
            list = this.engine.coPrimeSquarefree(cc, this.mset);
            if (this.ring.coFac.isField()) {
                list = PolyUtil.monic((List) list);
            }
            return new MultiplicativeSetSquarefree(this.ring, list, this.engine);
        }
        GenPolynomial<C> c = removeFactors((GenPolynomial) cc);
        if (c.isConstant()) {
            logger.info("skipped unit or constant = " + c);
            return this;
        }
        logger.info("added to squarefree mset = " + c);
        list = this.engine.coPrimeSquarefree(c, this.mset);
        if (this.ring.coFac.isField()) {
            list = PolyUtil.monic((List) list);
        }
        return new MultiplicativeSetSquarefree(this.ring, list, this.engine);
    }

    public MultiplicativeSetSquarefree<C> replace(List<GenPolynomial<C>> L) {
        MultiplicativeSetSquarefree<C> ms = new MultiplicativeSetSquarefree(this.ring);
        if (L == null || L.size() == 0) {
            return ms;
        }
        for (GenPolynomial p : L) {
            ms = ms.add(p);
        }
        return ms;
    }
}
