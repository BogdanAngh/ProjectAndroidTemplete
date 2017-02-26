package edu.jas.ufd;

import edu.jas.arith.BigInteger;
import edu.jas.arith.BigRational;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.util.ListUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public class PolyUfdUtil {
    private static boolean debug;
    private static final Logger logger;

    static {
        logger = Logger.getLogger(PolyUfdUtil.class);
        debug = logger.isDebugEnabled();
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<GenPolynomial<C>> integralFromQuotientCoefficients(GenPolynomialRing<GenPolynomial<C>> fac, GenPolynomial<Quotient<C>> A) {
        GenPolynomial<GenPolynomial<C>> B = fac.getZERO().copy();
        if (!(A == null || A.isZERO())) {
            GenPolynomial<C> c = null;
            GreatestCommonDivisor<C> ufd = new GreatestCommonDivisorSubres();
            int s = 0;
            for (Quotient<C> y : A.getMap().values()) {
                GenPolynomial<C> x = y.den;
                if (c == null) {
                    c = x;
                    s = x.signum();
                } else {
                    c = c.multiply(x.divide(ufd.gcd(c, x)));
                }
            }
            if (s < 0) {
                c = c.negate();
            }
            for (Entry<ExpVector, Quotient<C>> y2 : A.getMap().entrySet()) {
                Quotient<C> a = (Quotient) y2.getValue();
                B.doPutToMap((ExpVector) y2.getKey(), a.num.multiply(c.divide(a.den)));
            }
        }
        return B;
    }

    public static <C extends GcdRingElem<C>> List<GenPolynomial<GenPolynomial<C>>> integralFromQuotientCoefficients(GenPolynomialRing<GenPolynomial<C>> fac, Collection<GenPolynomial<Quotient<C>>> L) {
        if (L == null) {
            return null;
        }
        List<GenPolynomial<GenPolynomial<C>>> list = new ArrayList(L.size());
        for (GenPolynomial p : L) {
            list.add(integralFromQuotientCoefficients((GenPolynomialRing) fac, p));
        }
        return list;
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<Quotient<C>> quotientFromIntegralCoefficients(GenPolynomialRing<Quotient<C>> fac, GenPolynomial<GenPolynomial<C>> A) {
        GenPolynomial<Quotient<C>> B = fac.getZERO().copy();
        if (!(A == null || A.isZERO())) {
            QuotientRing<C> qfac = (QuotientRing) fac.coFac;
            for (Entry<ExpVector, GenPolynomial<C>> y : A.getMap().entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                Quotient<C> p = new Quotient(qfac, (GenPolynomial) y.getValue());
                if (!p.isZERO()) {
                    B.doPutToMap(e, p);
                }
            }
        }
        return B;
    }

    public static <C extends GcdRingElem<C>> List<GenPolynomial<Quotient<C>>> quotientFromIntegralCoefficients(GenPolynomialRing<Quotient<C>> fac, Collection<GenPolynomial<GenPolynomial<C>>> L) {
        if (L == null) {
            return null;
        }
        List<GenPolynomial<Quotient<C>>> list = new ArrayList(L.size());
        for (GenPolynomial p : L) {
            list.add(quotientFromIntegralCoefficients((GenPolynomialRing) fac, p));
        }
        return list;
    }

    public static <C extends RingElem<C>> GenPolynomial<GenPolynomial<C>> fromIntegerCoefficients(GenPolynomialRing<GenPolynomial<C>> fac, GenPolynomial<GenPolynomial<BigInteger>> A) {
        GenPolynomial<GenPolynomial<C>> B = fac.getZERO().copy();
        if (!(A == null || A.isZERO())) {
            GenPolynomialRing rfac = (GenPolynomialRing) fac.coFac;
            for (Entry<ExpVector, GenPolynomial<BigInteger>> y : A.getMap().entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                GenPolynomial<C> p = PolyUtil.fromIntegerCoefficients(rfac, (GenPolynomial) y.getValue());
                if (!p.isZERO()) {
                    B.doPutToMap(e, p);
                }
            }
        }
        return B;
    }

    public static <C extends RingElem<C>> List<GenPolynomial<GenPolynomial<C>>> fromIntegerCoefficients(GenPolynomialRing<GenPolynomial<C>> fac, List<GenPolynomial<GenPolynomial<BigInteger>>> L) {
        if (L == null) {
            return null;
        }
        List<GenPolynomial<GenPolynomial<C>>> K = new ArrayList(L.size());
        if (L.size() == 0) {
            return K;
        }
        for (GenPolynomial a : L) {
            K.add(fromIntegerCoefficients((GenPolynomialRing) fac, a));
        }
        return K;
    }

    public static GenPolynomial<GenPolynomial<BigInteger>> integerFromRationalCoefficients(GenPolynomialRing<GenPolynomial<BigInteger>> fac, GenPolynomial<GenPolynomial<BigRational>> A) {
        GenPolynomial<GenPolynomial<BigInteger>> B = fac.getZERO().copy();
        if (!(A == null || A.isZERO())) {
            GenPolynomialRing rfac = (GenPolynomialRing) fac.coFac;
            for (Entry<ExpVector, GenPolynomial<BigRational>> y : A.getMap().entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                GenPolynomial<BigInteger> p = PolyUtil.integerFromRationalCoefficients(rfac, (GenPolynomial) y.getValue());
                if (!p.isZERO()) {
                    B.doPutToMap(e, p);
                }
            }
        }
        return B;
    }

    public static List<GenPolynomial<GenPolynomial<BigInteger>>> integerFromRationalCoefficients(GenPolynomialRing<GenPolynomial<BigInteger>> fac, List<GenPolynomial<GenPolynomial<BigRational>>> L) {
        List<GenPolynomial<GenPolynomial<BigInteger>>> K = new ArrayList(L.size());
        if (!(L == null || L.isEmpty())) {
            for (GenPolynomial a : L) {
                K.add(integerFromRationalCoefficients((GenPolynomialRing) fac, a));
            }
        }
        return K;
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<GenPolynomial<C>> introduceLowerVariable(GenPolynomialRing<GenPolynomial<C>> rfac, GenPolynomial<C> A) {
        if (A == null || rfac == null) {
            return null;
        }
        GenPolynomial<GenPolynomial<C>> Pc = rfac.getONE().multiply((RingElem) A);
        if (Pc.isZERO()) {
            return Pc;
        }
        return PolyUtil.switchVariables(Pc);
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<GenPolynomial<C>> substituteFromAlgebraicCoefficients(GenPolynomialRing<GenPolynomial<C>> rfac, GenPolynomial<AlgebraicNumber<C>> A, long k) {
        if (A == null || rfac == null) {
            return null;
        }
        if (A.isZERO()) {
            return rfac.getZERO();
        }
        AlgebraicNumberRing<C> afac = A.ring.coFac;
        GenPolynomial<AlgebraicNumber<C>> s = A.ring.univariate(0).subtract(afac.fromInteger(k).multiply(afac.getGenerator()));
        if (debug) {
            logger.info("x - k alpha: " + s);
        }
        return PolyUtil.switchVariables(PolyUtil.fromAlgebraicCoefficients(rfac, PolyUtil.substituteMain(A, s)));
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<AlgebraicNumber<C>> substituteConvertToAlgebraicCoefficients(GenPolynomialRing<AlgebraicNumber<C>> pfac, GenPolynomial<C> A, long k) {
        if (A == null || pfac == null) {
            return null;
        }
        if (A.isZERO()) {
            return pfac.getZERO();
        }
        AlgebraicNumberRing<C> afac = pfac.coFac;
        return PolyUtil.substituteMain(PolyUtil.convertToAlgebraicCoefficients(pfac, A), pfac.univariate(0).sum(afac.fromInteger(k).multiply(afac.getGenerator())));
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<C> norm(GenPolynomial<AlgebraicNumber<C>> A, long k) {
        if (A == null) {
            return null;
        }
        GenPolynomialRing pfac = A.ring;
        if (pfac.nvar > 1) {
            throw new IllegalArgumentException("only for univariate polynomials");
        }
        AlgebraicNumberRing<C> afac = pfac.coFac;
        GenPolynomial<C> agen = afac.modul;
        RingFactory cfac = afac.ring;
        if (A.isZERO()) {
            return cfac.getZERO();
        }
        if (!((AlgebraicNumber) A.leadingBaseCoefficient()).isONE()) {
            A = A.monic();
        }
        GenPolynomialRing<GenPolynomial<C>> rfac = new GenPolynomialRing(cfac, pfac);
        GenPolynomial<GenPolynomial<C>> Ac = introduceLowerVariable(rfac, agen);
        return ((GenPolynomial) new GreatestCommonDivisorSubres().recursiveUnivariateResultant(PolyUtil.monic(substituteFromAlgebraicCoefficients(rfac, A, k)), Ac).leadingBaseCoefficient()).monic();
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<C> norm(GenPolynomial<AlgebraicNumber<C>> A) {
        return norm(A, 0);
    }

    public static <C extends GcdRingElem<C>> void ensureFieldProperty(AlgebraicNumberRing<C> afac) {
        if (afac.getField() == -1) {
            if (!afac.ring.coFac.isField()) {
                afac.setField(false);
            } else if (FactorFactory.getImplementation(afac.ring).isIrreducible(afac.modul)) {
                afac.setField(true);
            } else {
                afac.setField(false);
            }
        }
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<C> substituteKronecker(GenPolynomial<C> A) {
        return A == null ? A : substituteKronecker((GenPolynomial) A, A.degree() + 1);
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<C> substituteKronecker(GenPolynomial<C> A, long d) {
        if (A == null) {
            return A;
        }
        GenPolynomial<C> B = new GenPolynomialRing(A.ring.coFac, 1).getZERO().copy();
        if (A.isZERO()) {
            return B;
        }
        for (Entry<ExpVector, C> y : A.getMap().entrySet()) {
            ExpVector e = (ExpVector) y.getKey();
            GcdRingElem a = (GcdRingElem) y.getValue();
            long f = 0;
            long h = 1;
            for (int i = 0; i < e.length(); i++) {
                f += e.getVal(i) * h;
                h *= d;
            }
            B.doPutToMap(ExpVector.create(1, 0, f), a);
        }
        return B;
    }

    public static <C extends GcdRingElem<C>> List<GenPolynomial<C>> substituteKronecker(List<GenPolynomial<C>> A, int d) {
        if (A == null || A.get(0) == null) {
            return null;
        }
        return ListUtil.map(A, new SubstKronecker((long) d));
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<C> backSubstituteKronecker(GenPolynomialRing<C> fac, GenPolynomial<C> A, long d) {
        if (A == null) {
            return A;
        }
        if (fac == null) {
            throw new IllegalArgumentException("null factory not allowed ");
        }
        int n = fac.nvar;
        GenPolynomial<C> B = fac.getZERO().copy();
        if (A.isZERO()) {
            return B;
        }
        for (Entry<ExpVector, C> y : A.getMap().entrySet()) {
            GcdRingElem a = (GcdRingElem) y.getValue();
            long f = ((ExpVector) y.getKey()).getVal(0);
            ExpVector g = ExpVector.create(n);
            for (int i = 0; i < n; i++) {
                long j = f % d;
                f /= d;
                g = g.subst(i, j);
            }
            B.doPutToMap(g, a);
        }
        return B;
    }

    public static <C extends GcdRingElem<C>> List<GenPolynomial<C>> backSubstituteKronecker(GenPolynomialRing<C> fac, List<GenPolynomial<C>> A, long d) {
        return ListUtil.map(A, new BackSubstKronecker(fac, d));
    }
}
