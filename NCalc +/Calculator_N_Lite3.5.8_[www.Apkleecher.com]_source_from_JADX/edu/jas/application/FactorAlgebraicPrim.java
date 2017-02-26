package edu.jas.application;

import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.poly.TermOrder;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.ufd.FactorAbsolute;
import edu.jas.ufd.FactorAbstract;
import edu.jas.ufd.Squarefree;
import edu.jas.ufd.SquarefreeFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class FactorAlgebraicPrim<C extends GcdRingElem<C>> extends FactorAbsolute<AlgebraicNumber<C>> {
    private static final Logger logger;
    public final FactorAbstract<C> factorCoeff;

    static {
        logger = Logger.getLogger(FactorAlgebraicPrim.class);
    }

    protected FactorAlgebraicPrim() {
        throw new IllegalArgumentException("don't use this constructor");
    }

    public FactorAlgebraicPrim(AlgebraicNumberRing<C> fac) {
        this(fac, FactorFactory.getImplementation(fac.ring.coFac));
    }

    public FactorAlgebraicPrim(AlgebraicNumberRing<C> fac, FactorAbstract<C> factorCoeff) {
        super(fac);
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
                if (r0 > 1) {
                    throw new IllegalArgumentException("only for univariate polynomials");
                }
                RingFactory afac = pfac.coFac;
                AlgebraicNumber<C> ldcf = (AlgebraicNumber) P.leadingBaseCoefficient();
                if (!ldcf.isONE()) {
                    P = P.monic();
                    factors.add(pfac.getONE().multiply((RingElem) ldcf));
                }
                if (logger.isDebugEnabled()) {
                    Squarefree<AlgebraicNumber<C>> sqengine = SquarefreeFactory.getImplementation(afac);
                    if (sqengine.isSquarefree((GenPolynomial) P)) {
                        GenPolynomial<C> modu = afac.modul;
                        if (this.factorCoeff.isIrreducible(modu)) {
                            System.out.println("P squarefree and modul irreducible via ideal decomposition");
                        } else {
                            throw new RuntimeException("modul not irreducible: " + this.factorCoeff.factors(modu));
                        }
                    }
                    throw new RuntimeException("P not squarefree: " + sqengine.squarefreeFactors(P));
                }
                GenPolynomial<C> agen = afac.modul;
                RingFactory cfac = afac.ring;
                GenPolynomialRing<GenPolynomial<C>> genPolynomialRing = new GenPolynomialRing(cfac, (GenPolynomialRing) pfac);
                TermOrder termOrder = new TermOrder(2);
                String[] vars = new String[]{cfac.getVars()[0], genPolynomialRing.getVars()[0]};
                GenPolynomialRing dfac = new GenPolynomialRing(cfac.coFac, termOrder, vars);
                GenPolynomial<C> Ad = agen.extend(dfac, 0, 0);
                GenPolynomial<C> Pd = PolyUtil.distribute(dfac, PolyUtil.fromAlgebraicCoefficients(genPolynomialRing, P));
                List<GenPolynomial<C>> arrayList = new ArrayList(2);
                arrayList.add(Ad);
                arrayList.add(Pd);
                List<IdealWithUniv<C>> Iul = new Ideal(dfac, (List) arrayList).zeroDimPrimeDecomposition();
                if (Iul.size() == 1) {
                    factors.add(P);
                } else {
                    GenPolynomial<AlgebraicNumber<C>> f = pfac.getONE();
                    for (IdealWithUniv<C> Iu : Iul) {
                        List<GenPolynomial<C>> pl = Iu.ideal.getList();
                        GenPolynomial<C> ag = PolyUtil.selectWithVariable(pl, 1);
                        GenPolynomial<C> pg = PolyUtil.selectWithVariable(pl, 0);
                        if (ag.equals(Ad)) {
                            GenPolynomial<AlgebraicNumber<C>> pga = PolyUtil.convertRecursiveToAlgebraicCoefficients(pfac, PolyUtil.recursive((GenPolynomialRing) genPolynomialRing, (GenPolynomial) pg));
                            f = f.multiply((GenPolynomial) pga);
                            factors.add(pga);
                        } else {
                            logger.warn("algebraic number mismatch: ag = " + ag + ", expected Ad = " + Ad);
                        }
                    }
                    f = f.subtract((GenPolynomial) P);
                    if (!f.isZERO()) {
                        throw new RuntimeException("no factorization: " + f + ", factors = " + factors);
                    }
                }
            }
        }
        return factors;
    }
}
