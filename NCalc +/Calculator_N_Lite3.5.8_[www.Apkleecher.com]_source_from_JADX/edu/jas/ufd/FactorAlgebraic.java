package edu.jas.ufd;

import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class FactorAlgebraic<C extends GcdRingElem<C>> extends FactorAbsolute<AlgebraicNumber<C>> {
    private static final Logger logger;
    private final boolean debug;
    public final FactorAbstract<C> factorCoeff;

    static {
        logger = Logger.getLogger(FactorAlgebraic.class);
    }

    protected FactorAlgebraic() {
        this.debug = logger.isDebugEnabled();
        throw new IllegalArgumentException("don't use this constructor");
    }

    public FactorAlgebraic(AlgebraicNumberRing<C> fac) {
        this(fac, FactorFactory.getImplementation(fac.ring.coFac));
    }

    public FactorAlgebraic(AlgebraicNumberRing<C> fac, FactorAbstract<C> factorCoeff) {
        super(fac);
        this.debug = logger.isDebugEnabled();
        this.factorCoeff = factorCoeff;
    }

    public List<GenPolynomial<AlgebraicNumber<C>>> baseFactorsSquarefree(GenPolynomial<AlgebraicNumber<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P == null");
        }
        List<GenPolynomial<AlgebraicNumber<C>>> factors = new ArrayList();
        if (!P.isZERO()) {
            if (P.isONE()) {
                factors.add(P);
            } else {
                GenPolynomialRing<AlgebraicNumber<C>> pfac = P.ring;
                int i = pfac.nvar;
                int i2;
                if (i2 > 1) {
                    throw new IllegalArgumentException("only for univariate polynomials");
                }
                RingFactory afac = pfac.coFac;
                RingElem ldcf = (AlgebraicNumber) P.leadingBaseCoefficient();
                if (!ldcf.isONE()) {
                    P = P.monic();
                    factors.add(pfac.getONE().multiply(ldcf));
                }
                if (this.debug) {
                    Squarefree<AlgebraicNumber<C>> sqengine = SquarefreeFactory.getImplementation(afac);
                    if (sqengine.isSquarefree((GenPolynomial) P)) {
                        GenPolynomial<C> modu = afac.modul;
                        if (this.factorCoeff.isIrreducible(modu)) {
                            System.out.println("P squarefree and modul irreducible");
                        } else {
                            throw new RuntimeException("modul not irreducible: " + this.factorCoeff.factors(modu));
                        }
                    }
                    throw new RuntimeException("P not squarefree: " + sqengine.squarefreeFactors(P));
                }
                long ks = 0;
                GenPolynomial<C> res = null;
                boolean sqf = false;
                i2 = 5;
                int[] klist = new int[]{0, -1, -2, 1, 2};
                int ki = 0;
                while (!sqf) {
                    i = klist.length;
                    if (ki >= i2) {
                        break;
                    }
                    i2 = klist[ki];
                    ki++;
                    ks = (long) i;
                    res = PolyUfdUtil.norm(P, ks);
                    if (!(res.isZERO() || res.isConstant())) {
                        sqf = this.factorCoeff.isSquarefree(res);
                    }
                }
                if (!sqf) {
                    System.out.println("sqf(" + ks + ") = " + res.degree());
                }
                if (logger.isInfoEnabled()) {
                    logger.info("res = " + res);
                }
                List<GenPolynomial<C>> nfacs = this.factorCoeff.baseFactorsRadical(res);
                if (logger.isInfoEnabled()) {
                    logger.info("res facs = " + nfacs);
                }
                if (nfacs.size() == 1) {
                    factors.add(P);
                } else {
                    GenPolynomial<AlgebraicNumber<C>> Pp = P;
                    for (GenPolynomial<C> substituteConvertToAlgebraicCoefficients : nfacs) {
                        GenPolynomial Ni = PolyUfdUtil.substituteConvertToAlgebraicCoefficients(pfac, substituteConvertToAlgebraicCoefficients, ks);
                        if (logger.isInfoEnabled()) {
                            logger.info("Ni = " + Ni);
                        }
                        GenPolynomial<AlgebraicNumber<C>> pni = this.engine.gcd(Ni, (GenPolynomial) Pp);
                        if (!((AlgebraicNumber) pni.leadingBaseCoefficient()).isONE()) {
                            pni = pni.monic();
                        }
                        if (logger.isInfoEnabled()) {
                            logger.info("gcd(Ni,Pp) = " + pni);
                        }
                        if (!pni.isONE()) {
                            factors.add(pni);
                            Pp = Pp.divide((GenPolynomial) pni);
                        }
                    }
                    if (!(Pp.isZERO() || Pp.isONE())) {
                        factors.add(Pp);
                    }
                }
            }
        }
        return factors;
    }
}
