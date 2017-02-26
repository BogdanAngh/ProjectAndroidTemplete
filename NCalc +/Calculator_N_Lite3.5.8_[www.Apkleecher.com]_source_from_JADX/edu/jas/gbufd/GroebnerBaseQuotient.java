package edu.jas.gbufd;

import edu.jas.gb.GroebnerBaseAbstract;
import edu.jas.gb.PairList;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.ufd.PolyUfdUtil;
import edu.jas.ufd.Quotient;
import edu.jas.ufd.QuotientRing;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;

public class GroebnerBaseQuotient<C extends GcdRingElem<C>> extends GroebnerBaseAbstract<Quotient<C>> {
    private static final Logger logger;
    public final GroebnerBaseAbstract<GenPolynomial<C>> bba;
    private final boolean debug;

    static {
        logger = Logger.getLogger(GroebnerBaseQuotient.class);
    }

    public GroebnerBaseQuotient(QuotientRing<C> rf) {
        this(new GroebnerBasePseudoRecSeq(rf.ring));
    }

    public GroebnerBaseQuotient(int threads, QuotientRing<C> rf) {
        this(new GroebnerBasePseudoRecParallel(threads, rf.ring));
    }

    public GroebnerBaseQuotient(QuotientRing<C> rf, PairList<GenPolynomial<C>> pl) {
        this(new GroebnerBasePseudoRecSeq(rf.ring, pl));
    }

    public GroebnerBaseQuotient(int threads, QuotientRing<C> rf, PairList<GenPolynomial<C>> pl) {
        this(new GroebnerBasePseudoRecParallel(threads, rf.ring, (PairList) pl));
    }

    public GroebnerBaseQuotient(GroebnerBaseAbstract<GenPolynomial<C>> bba) {
        this.debug = logger.isDebugEnabled();
        this.bba = bba;
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.bba.toString() + ")";
    }

    public List<GenPolynomial<Quotient<C>>> GB(int modv, List<GenPolynomial<Quotient<C>>> F) {
        List<GenPolynomial<Quotient<C>>> G = F;
        if (F == null || F.isEmpty()) {
            return G;
        }
        GenPolynomialRing rring = ((GenPolynomial) F.get(0)).ring;
        List<GenPolynomial<GenPolynomial<C>>> Fi = PolyUfdUtil.integralFromQuotientCoefficients(new GenPolynomialRing(rring.coFac.ring, rring), (Collection) F);
        logger.info("#Fi = " + Fi.size());
        Collection Gi = this.bba.GB(modv, Fi);
        logger.info("#Gi = " + Gi.size());
        return PolyUtil.monic(PolyUfdUtil.quotientFromIntegralCoefficients(rring, Gi));
    }

    public List<GenPolynomial<Quotient<C>>> minimalGB(List<GenPolynomial<Quotient<C>>> Gp) {
        if (Gp == null || Gp.size() <= 1) {
            return Gp;
        }
        List<GenPolynomial<Quotient<C>>> G = new ArrayList(Gp.size());
        for (GenPolynomial<Quotient<C>> a : Gp) {
            GenPolynomial<Quotient<C>> a2;
            if (!(a2 == null || a2.isZERO())) {
                G.add(a2);
            }
        }
        if (G.size() <= 1) {
            return G;
        }
        Collection F = new ArrayList(G.size());
        while (G.size() > 0) {
            GenPolynomial a3 = (GenPolynomial) G.remove(0);
            if (!this.red.isTopReducible(G, a3) && !this.red.isTopReducible(F, a3)) {
                F.add(a3);
            } else if (this.debug) {
                System.out.println("dropped " + a3);
                List ff = new ArrayList(G);
                ff.addAll(F);
                a2 = this.red.normalform(ff, a3);
                if (!a2.isZERO()) {
                    System.out.println("error, nf(a) " + a2);
                }
            }
        }
        Collection G2 = F;
        if (G2.size() <= 1) {
            return G2;
        }
        GenPolynomialRing rring = ((GenPolynomial) G2.get(0)).ring;
        List<GenPolynomial<GenPolynomial<C>>> Fi = PolyUfdUtil.integralFromQuotientCoefficients(new GenPolynomialRing(rring.coFac.ring, rring), F);
        logger.info("#Fi = " + Fi.size());
        Collection Gi = this.bba.minimalGB(Fi);
        logger.info("#Gi = " + Gi.size());
        return PolyUtil.monic(PolyUfdUtil.quotientFromIntegralCoefficients(rring, Gi));
    }

    public void terminate() {
        this.bba.terminate();
    }

    public int cancel() {
        return this.bba.cancel();
    }
}
