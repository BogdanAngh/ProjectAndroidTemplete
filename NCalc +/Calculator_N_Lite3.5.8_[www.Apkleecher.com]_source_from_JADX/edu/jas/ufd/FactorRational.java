package edu.jas.ufd;

import edu.jas.arith.BigInteger;
import edu.jas.arith.BigRational;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.RingElem;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class FactorRational extends FactorAbsolute<BigRational> {
    private static final Logger logger;
    private final boolean debug;
    protected final FactorAbstract<BigInteger> iengine;

    static {
        logger = Logger.getLogger(FactorRational.class);
    }

    protected FactorRational() {
        super(BigRational.ONE);
        this.debug = logger.isInfoEnabled();
        this.iengine = FactorFactory.getImplementation(BigInteger.ONE);
    }

    public List<GenPolynomial<BigRational>> baseFactorsSquarefree(GenPolynomial<BigRational> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P == null");
        }
        List<GenPolynomial<BigRational>> factors = new ArrayList();
        if (!P.isZERO()) {
            if (P.isONE()) {
                factors.add(P);
            } else {
                GenPolynomialRing pfac = P.ring;
                if (pfac.nvar > 1) {
                    throw new IllegalArgumentException(getClass().getName() + " only for univariate polynomials");
                }
                GenPolynomial Pr = P;
                RingElem ldcf = (BigRational) P.leadingBaseCoefficient();
                if (!ldcf.isONE()) {
                    Pr = Pr.monic();
                }
                GenPolynomial<BigInteger> Pi = PolyUtil.integerFromRationalCoefficients(new GenPolynomialRing(BigInteger.ONE, pfac), Pr);
                if (this.debug) {
                    logger.info("Pi = " + Pi);
                }
                List ifacts = this.iengine.baseFactorsSquarefree(Pi);
                if (logger.isInfoEnabled()) {
                    logger.info("ifacts = " + ifacts);
                }
                if (ifacts.size() <= 1) {
                    factors.add(P);
                } else {
                    List<GenPolynomial<BigRational>> rfacts = PolyUtil.monic(PolyUtil.fromIntegerCoefficients(pfac, ifacts));
                    if (!ldcf.isONE()) {
                        GenPolynomial<BigRational> r = (GenPolynomial) rfacts.get(0);
                        rfacts.remove(r);
                        rfacts.set(0, r.multiply(ldcf));
                    }
                    if (logger.isInfoEnabled()) {
                        logger.info("rfacts = " + rfacts);
                    }
                    factors.addAll(rfacts);
                }
            }
        }
        return factors;
    }

    public List<GenPolynomial<BigRational>> factorsSquarefree(GenPolynomial<BigRational> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P == null");
        }
        List<GenPolynomial<BigRational>> factors = new ArrayList();
        if (P.isZERO()) {
            return factors;
        }
        if (P.isONE()) {
            factors.add(P);
            return factors;
        }
        GenPolynomialRing pfac = P.ring;
        if (pfac.nvar == 1) {
            return baseFactorsSquarefree(P);
        }
        GenPolynomial Pr = P;
        RingElem ldcf = (BigRational) P.leadingBaseCoefficient();
        if (!ldcf.isONE()) {
            Pr = Pr.monic();
        }
        GenPolynomial<BigInteger> Pi = PolyUtil.integerFromRationalCoefficients(new GenPolynomialRing(BigInteger.ONE, pfac), Pr);
        if (this.debug) {
            logger.info("Pi = " + Pi);
        }
        List ifacts = this.iengine.factorsSquarefree(Pi);
        if (logger.isInfoEnabled()) {
            logger.info("ifacts = " + ifacts);
        }
        if (ifacts.size() <= 1) {
            factors.add(P);
            return factors;
        }
        List<GenPolynomial<BigRational>> rfacts = PolyUtil.monic(PolyUtil.fromIntegerCoefficients(pfac, ifacts));
        if (!ldcf.isONE()) {
            GenPolynomial<BigRational> r = (GenPolynomial) rfacts.get(0);
            rfacts.remove(r);
            rfacts.set(0, r.multiply(ldcf));
        }
        if (logger.isInfoEnabled()) {
            logger.info("rfacts = " + rfacts);
        }
        factors.addAll(rfacts);
        return factors;
    }
}
