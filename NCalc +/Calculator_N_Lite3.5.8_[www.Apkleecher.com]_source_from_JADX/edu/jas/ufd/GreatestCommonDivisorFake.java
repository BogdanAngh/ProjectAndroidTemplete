package edu.jas.ufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import org.apache.log4j.Logger;

public class GreatestCommonDivisorFake<C extends GcdRingElem<C>> extends GreatestCommonDivisorAbstract<C> {
    private static final Logger logger;

    static {
        logger = Logger.getLogger(GreatestCommonDivisorFake.class);
    }

    public C baseContent(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        } else if (P.isZERO()) {
            return (GcdRingElem) P.ring.getZEROCoefficient();
        } else {
            return (GcdRingElem) P.ring.getONECoefficient();
        }
    }

    public GenPolynomial<C> basePrimitivePart(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        } else if (P.isZERO()) {
            return P;
        } else {
            if (P.isConstant()) {
                return P.ring.getONE();
            }
            if (P.length() == 1) {
                return P.ring.valueOf(P.leadingExpVector());
            }
            return P;
        }
    }

    public GenPolynomial<C> baseGcd(GenPolynomial<C> P, GenPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            return P;
        }
        if (P == null || P.isZERO()) {
            return S;
        }
        if (P.ring.nvar <= 1) {
            return P.ring.getONE();
        }
        throw new IllegalArgumentException(getClass().getName() + " no univariate polynomial");
    }

    public GenPolynomial<C> recursiveContent(GenPolynomial<GenPolynomial<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        } else if (P.isZERO()) {
            return (GenPolynomial) P.ring.getZEROCoefficient();
        } else {
            return (GenPolynomial) P.ring.getONECoefficient();
        }
    }

    public GenPolynomial<GenPolynomial<C>> recursivePrimitivePart(GenPolynomial<GenPolynomial<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        } else if (P.isZERO()) {
            return P;
        } else {
            if (P.isConstant()) {
                return P.ring.getONE();
            }
            if (P.length() == 1) {
                return P.ring.valueOf(P.leadingExpVector());
            }
            return P;
        }
    }

    public GenPolynomial<GenPolynomial<C>> recursiveUnivariateGcd(GenPolynomial<GenPolynomial<C>> P, GenPolynomial<GenPolynomial<C>> S) {
        if (S == null || S.isZERO()) {
            return P;
        }
        if (P == null || P.isZERO()) {
            return S;
        }
        if (P.ring.nvar > 1) {
            throw new IllegalArgumentException(getClass().getName() + " no univariate polynomial");
        }
        logger.debug("returning 1");
        return P.ring.getONE();
    }
}
