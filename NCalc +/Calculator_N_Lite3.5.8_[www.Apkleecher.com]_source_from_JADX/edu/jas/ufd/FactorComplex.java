package edu.jas.ufd;

import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.Complex;
import edu.jas.poly.ComplexRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class FactorComplex<C extends GcdRingElem<C>> extends FactorAbsolute<Complex<C>> {
    private static final Logger logger;
    public final AlgebraicNumberRing<C> afac;
    private final boolean debug;
    public final FactorAbstract<AlgebraicNumber<C>> factorAlgeb;

    static {
        logger = Logger.getLogger(FactorComplex.class);
    }

    protected FactorComplex() {
        this.debug = logger.isDebugEnabled();
        throw new IllegalArgumentException("don't use this constructor");
    }

    public FactorComplex(RingFactory<Complex<C>> fac) {
        this((ComplexRing) fac);
    }

    public FactorComplex(ComplexRing<C> fac) {
        super(fac);
        this.debug = logger.isDebugEnabled();
        this.afac = fac.algebraicRing();
        this.factorAlgeb = FactorFactory.getImplementation(this.afac);
    }

    public FactorComplex(ComplexRing<C> fac, FactorAbstract<AlgebraicNumber<C>> factorAlgeb) {
        super(fac);
        this.debug = logger.isDebugEnabled();
        this.afac = fac.algebraicRing();
        this.factorAlgeb = factorAlgeb;
    }

    public List<GenPolynomial<Complex<C>>> baseFactorsSquarefree(GenPolynomial<Complex<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P == null");
        }
        List<GenPolynomial<Complex<C>>> factors = new ArrayList();
        if (!P.isZERO()) {
            if (P.isONE()) {
                factors.add(P);
            } else {
                GenPolynomialRing pfac = P.ring;
                if (pfac.nvar > 1) {
                    throw new IllegalArgumentException("only for univariate polynomials");
                }
                if (this.afac.ring.coFac.equals(pfac.coFac.ring)) {
                    RingElem ldcf = (Complex) P.leadingBaseCoefficient();
                    if (!ldcf.isONE()) {
                        P = P.monic();
                        factors.add(pfac.getONE().multiply(ldcf));
                    }
                    List<GenPolynomial<AlgebraicNumber<C>>> afactors = this.factorAlgeb.baseFactorsSquarefree(PolyUtil.algebraicFromComplex(new GenPolynomialRing(this.afac, pfac), P));
                    if (this.debug) {
                        logger.info("complex afactors = " + afactors);
                    }
                    for (GenPolynomial<AlgebraicNumber<C>> pa : afactors) {
                        factors.add(PolyUtil.complexFromAlgebraic(pfac, pa));
                    }
                } else {
                    throw new IllegalArgumentException("coefficient rings do not match");
                }
            }
        }
        return factors;
    }
}
