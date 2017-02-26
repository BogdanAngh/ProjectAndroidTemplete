package edu.jas.ufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;

public class FactorQuotient<C extends GcdRingElem<C>> extends FactorAbstract<Quotient<C>> {
    private static final Logger logger;
    protected final FactorAbstract<C> nengine;

    static {
        logger = Logger.getLogger(FactorQuotient.class);
    }

    protected FactorQuotient() {
        throw new IllegalArgumentException("don't use this constructor");
    }

    public FactorQuotient(QuotientRing<C> fac) {
        this(fac, FactorFactory.getImplementation(fac.ring.coFac));
    }

    public FactorQuotient(QuotientRing<C> fac, FactorAbstract<C> nengine) {
        super(fac);
        this.nengine = nengine;
    }

    public List<GenPolynomial<Quotient<C>>> baseFactorsSquarefree(GenPolynomial<Quotient<C>> P) {
        return factorsSquarefree(P);
    }

    public List<GenPolynomial<Quotient<C>>> factorsSquarefree(GenPolynomial<Quotient<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P == null");
        }
        List<GenPolynomial<Quotient<C>>> factors = new ArrayList();
        if (!P.isZERO()) {
            if (P.isONE()) {
                factors.add(P);
            } else {
                GenPolynomialRing pfac = P.ring;
                GenPolynomial Pr = P;
                RingElem ldcf = (Quotient) P.leadingBaseCoefficient();
                if (!ldcf.isONE()) {
                    Pr = Pr.monic();
                }
                Collection irfacts = this.nengine.recursiveFactorsSquarefree(PolyUfdUtil.integralFromQuotientCoefficients(new GenPolynomialRing(pfac.coFac.ring, pfac), Pr));
                if (logger.isInfoEnabled()) {
                    logger.info("irfacts = " + irfacts);
                }
                if (irfacts.size() <= 1) {
                    factors.add(P);
                } else {
                    List<GenPolynomial<Quotient<C>>> qfacts = PolyUfdUtil.quotientFromIntegralCoefficients(pfac, irfacts);
                    if (!ldcf.isONE()) {
                        GenPolynomial<Quotient<C>> r = (GenPolynomial) qfacts.get(0);
                        qfacts.remove(r);
                        qfacts.add(0, r.multiply(ldcf));
                    }
                    if (logger.isInfoEnabled()) {
                        logger.info("qfacts = " + qfacts);
                    }
                    factors.addAll(qfacts);
                }
            }
        }
        return factors;
    }
}
