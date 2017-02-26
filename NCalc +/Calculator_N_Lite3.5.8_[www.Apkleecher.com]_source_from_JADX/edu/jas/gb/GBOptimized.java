package edu.jas.gb;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.OptimizedPolynomialList;
import edu.jas.poly.TermOrderOptimization;
import edu.jas.structure.GcdRingElem;
import java.util.List;
import org.apache.log4j.Logger;

public class GBOptimized<C extends GcdRingElem<C>> extends GroebnerBaseAbstract<C> {
    private static final Logger logger;
    private final boolean debug;
    public final GroebnerBaseAbstract<C> e1;
    public final boolean retPermuted;

    static {
        logger = Logger.getLogger(GBOptimized.class);
    }

    public GBOptimized(GroebnerBaseAbstract<C> e1) {
        this(e1, false);
    }

    public GBOptimized(GroebnerBaseAbstract<C> e1, boolean rP) {
        this.debug = logger.isDebugEnabled();
        this.e1 = e1;
        this.retPermuted = rP;
    }

    public String toString() {
        return "GBOptimized[ " + this.e1.toString() + " ]";
    }

    public void terminate() {
        this.e1.terminate();
    }

    public int cancel() {
        return this.e1.cancel();
    }

    public List<GenPolynomial<C>> GB(int modv, List<GenPolynomial<C>> F) {
        if (F == null || F.isEmpty()) {
            return F;
        }
        if (modv != 0) {
            throw new UnsupportedOperationException("implemented only for modv = 0, not " + modv);
        }
        GenPolynomialRing pfac = ((GenPolynomial) F.get(0)).ring;
        OptimizedPolynomialList<C> opt = TermOrderOptimization.optimizeTermOrder(pfac, F);
        List<GenPolynomial<C>> P = opt.list;
        if (this.debug) {
            logger.info("optimized polynomials: " + P);
        }
        List iperm = TermOrderOptimization.inversePermutation(opt.perm);
        logger.info("optimize perm: " + opt.perm + ", de-optimize perm: " + iperm);
        List G = this.e1.GB(modv, P);
        if (this.retPermuted || G.isEmpty()) {
            return G;
        }
        List<GenPolynomial<C>> iopt = TermOrderOptimization.permutation(iperm, pfac, G);
        if (this.debug) {
            logger.info("de-optimized polynomials: " + iopt);
        }
        if (iopt.size() == 1) {
            return iopt;
        }
        logger.warn("recomputing GB");
        return this.e1.GB(modv, iopt);
    }
}
