package edu.jas.poly;

import edu.jas.arith.BigComplex;
import edu.jas.arith.BigDecimal;
import edu.jas.arith.BigInteger;
import edu.jas.arith.BigRational;
import edu.jas.arith.ModInteger;
import edu.jas.arith.ModIntegerRing;
import edu.jas.arith.Modular;
import edu.jas.arith.ModularRingFactory;
import edu.jas.arith.Product;
import edu.jas.arith.ProductRing;
import edu.jas.arith.Rational;
import edu.jas.structure.AbelianGroupElem;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.structure.UnaryFunctor;
import edu.jas.util.ListUtil;
import io.github.kexanie.library.BuildConfig;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public class PolyUtil {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static boolean debug;
    private static final Logger logger;

    static class 1 implements UnaryFunctor<GenPolynomial<C>, GenPolynomial<BigInteger>> {
        final /* synthetic */ GenPolynomialRing val$fac;

        1(GenPolynomialRing genPolynomialRing) {
            this.val$fac = genPolynomialRing;
        }

        public GenPolynomial<BigInteger> eval(GenPolynomial<C> c) {
            return PolyUtil.integerFromModularCoefficients(this.val$fac, (GenPolynomial) c);
        }
    }

    static class 2 implements UnaryFunctor<GenPolynomial<C>, GenPolynomial<C>> {
        2() {
        }

        public GenPolynomial<C> eval(GenPolynomial<C> c) {
            if (c == null) {
                return null;
            }
            return c.monic();
        }
    }

    static class 3 implements UnaryFunctor<GenWordPolynomial<C>, GenWordPolynomial<C>> {
        3() {
        }

        public GenWordPolynomial<C> eval(GenWordPolynomial<C> c) {
            if (c == null) {
                return null;
            }
            return c.monic();
        }
    }

    static class 4 implements UnaryFunctor<GenPolynomial<GenPolynomial<C>>, GenPolynomial<GenPolynomial<C>>> {
        4() {
        }

        public GenPolynomial<GenPolynomial<C>> eval(GenPolynomial<GenPolynomial<C>> c) {
            if (c == null) {
                return null;
            }
            return PolyUtil.monic((GenPolynomial) c);
        }
    }

    static class 5 implements UnaryFunctor<GenPolynomial<C>, ExpVector> {
        5() {
        }

        public ExpVector eval(GenPolynomial<C> c) {
            if (c == null) {
                return null;
            }
            return c.leadingExpVector();
        }
    }

    static {
        $assertionsDisabled = !PolyUtil.class.desiredAssertionStatus();
        logger = Logger.getLogger(PolyUtil.class);
        debug = logger.isDebugEnabled();
    }

    public static <C extends RingElem<C>> GenPolynomial<GenPolynomial<C>> recursive(GenPolynomialRing<GenPolynomial<C>> rfac, GenPolynomial<C> A) {
        GenPolynomial<GenPolynomial<C>> B = rfac.getZERO().copy();
        if (!A.isZERO()) {
            int i = rfac.nvar;
            GenPolynomial<C> zero = (GenPolynomial) rfac.getZEROCoefficient();
            Map<ExpVector, GenPolynomial<C>> Bv = B.val;
            for (Entry<ExpVector, C> y : A.getMap().entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                RingElem a = (RingElem) y.getValue();
                ExpVector f = e.contract(0, i);
                ExpVector g = e.contract(i, e.length() - i);
                GenPolynomial<C> p = (GenPolynomial) Bv.get(f);
                if (p == null) {
                    p = zero;
                }
                Bv.put(f, p.sum(a, g));
            }
        }
        return B;
    }

    public static <C extends RingElem<C>> GenPolynomial<C> distribute(GenPolynomialRing<C> dfac, GenPolynomial<GenPolynomial<C>> B) {
        GenPolynomial<C> C = dfac.getZERO().copy();
        if (!B.isZERO()) {
            Map<ExpVector, C> Cm = C.val;
            for (Entry<ExpVector, GenPolynomial<C>> y : B.getMap().entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                for (Entry<ExpVector, C> x : ((GenPolynomial) y.getValue()).val.entrySet()) {
                    RingElem b = (RingElem) x.getValue();
                    ExpVector g = e.combine((ExpVector) x.getKey());
                    if ($assertionsDisabled || Cm.get(g) == null) {
                        Cm.put(g, b);
                    } else {
                        throw new AssertionError();
                    }
                }
            }
        }
        return C;
    }

    public static <C extends RingElem<C>> List<GenPolynomial<GenPolynomial<C>>> recursive(GenPolynomialRing<GenPolynomial<C>> rfac, List<GenPolynomial<C>> L) {
        return ListUtil.map(L, new DistToRec(rfac));
    }

    public static <C extends RingElem<C>> List<GenPolynomial<C>> distribute(GenPolynomialRing<C> dfac, List<GenPolynomial<GenPolynomial<C>>> L) {
        return ListUtil.map(L, new RecToDist(dfac));
    }

    public static <C extends RingElem<C> & Modular> GenPolynomial<BigInteger> integerFromModularCoefficients(GenPolynomialRing<BigInteger> fac, GenPolynomial<C> A) {
        return map(fac, A, new ModSymToInt());
    }

    public static <C extends RingElem<C> & Modular> List<GenPolynomial<BigInteger>> integerFromModularCoefficients(GenPolynomialRing<BigInteger> fac, List<GenPolynomial<C>> L) {
        return ListUtil.map(L, new 1(fac));
    }

    public static <C extends RingElem<C> & Modular> GenPolynomial<BigInteger> integerFromModularCoefficientsPositive(GenPolynomialRing<BigInteger> fac, GenPolynomial<C> A) {
        return map(fac, A, new ModToInt());
    }

    public static GenPolynomial<BigInteger> integerFromRationalCoefficients(GenPolynomialRing<BigInteger> fac, GenPolynomial<BigRational> A) {
        if (A == null || A.isZERO()) {
            return fac.getZERO();
        }
        java.math.BigInteger c = null;
        int s = 0;
        for (BigRational y : A.val.values()) {
            java.math.BigInteger x = y.denominator();
            if (c == null) {
                c = x;
                s = x.signum();
            } else {
                c = c.multiply(x.divide(c.gcd(x)));
            }
        }
        if (s < 0) {
            c = c.negate();
        }
        return map(fac, A, new RatToInt(c));
    }

    public static Object[] integerFromRationalCoefficientsFactor(GenPolynomialRing<BigInteger> fac, GenPolynomial<BigRational> A) {
        Object[] result = new Object[3];
        if (A == null || A.isZERO()) {
            result[0] = java.math.BigInteger.ONE;
            result[1] = java.math.BigInteger.ZERO;
            result[2] = fac.getZERO();
        } else {
            java.math.BigInteger gcd = null;
            java.math.BigInteger lcm = null;
            int sLCM = 0;
            int sGCD = 0;
            for (BigRational y : A.val.values()) {
                java.math.BigInteger numerator = y.numerator();
                java.math.BigInteger denominator = y.denominator();
                if (lcm == null) {
                    lcm = denominator;
                    sLCM = denominator.signum();
                } else {
                    lcm = lcm.multiply(denominator.divide(lcm.gcd(denominator)));
                }
                if (gcd == null) {
                    gcd = numerator;
                    sGCD = numerator.signum();
                } else {
                    gcd = gcd.gcd(numerator);
                }
            }
            if (sLCM < 0) {
                lcm = lcm.negate();
            }
            if (sGCD < 0) {
                gcd = gcd.negate();
            }
            result[0] = gcd;
            result[1] = lcm;
            result[2] = map(fac, A, new RatToIntFactor(gcd, lcm));
        }
        return result;
    }

    public static List<GenPolynomial<BigInteger>> integerFromRationalCoefficients(GenPolynomialRing<BigInteger> fac, List<GenPolynomial<BigRational>> L) {
        return ListUtil.map(L, new RatToIntPoly(fac));
    }

    public static <C extends RingElem<C>> GenPolynomial<C> fromIntegerCoefficients(GenPolynomialRing<C> fac, GenPolynomial<BigInteger> A) {
        return map(fac, A, new FromInteger(fac.coFac));
    }

    public static <C extends RingElem<C>> List<GenPolynomial<C>> fromIntegerCoefficients(GenPolynomialRing<C> fac, List<GenPolynomial<BigInteger>> L) {
        return ListUtil.map(L, new FromIntegerPoly(fac));
    }

    public static <C extends RingElem<C> & Rational> GenPolynomial<BigDecimal> decimalFromRational(GenPolynomialRing<BigDecimal> fac, GenPolynomial<C> A) {
        return map(fac, A, new RatToDec());
    }

    public static <C extends RingElem<C> & Rational> GenPolynomial<Complex<BigDecimal>> complexDecimalFromRational(GenPolynomialRing<Complex<BigDecimal>> fac, GenPolynomial<Complex<C>> A) {
        return map(fac, A, new CompRatToDec(fac.coFac));
    }

    public static GenPolynomial<BigRational> realPart(GenPolynomialRing<BigRational> fac, GenPolynomial<BigComplex> A) {
        return map(fac, A, new RealPart());
    }

    public static GenPolynomial<BigRational> imaginaryPart(GenPolynomialRing<BigRational> fac, GenPolynomial<BigComplex> A) {
        return map(fac, A, new ImagPart());
    }

    public static <C extends RingElem<C>> GenPolynomial<C> realPartFromComplex(GenPolynomialRing<C> fac, GenPolynomial<Complex<C>> A) {
        return map(fac, A, new RealPartComplex());
    }

    public static <C extends RingElem<C>> GenPolynomial<C> imaginaryPartFromComplex(GenPolynomialRing<C> fac, GenPolynomial<Complex<C>> A) {
        return map(fac, A, new ImagPartComplex());
    }

    public static <C extends RingElem<C>> GenPolynomial<Complex<C>> toComplex(GenPolynomialRing<Complex<C>> fac, GenPolynomial<C> A) {
        return map(fac, A, new ToComplex(fac.coFac));
    }

    public static GenPolynomial<BigComplex> complexFromRational(GenPolynomialRing<BigComplex> fac, GenPolynomial<BigRational> A) {
        return map(fac, A, new RatToCompl());
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<Complex<C>> complexFromAny(GenPolynomialRing<Complex<C>> fac, GenPolynomial<C> A) {
        return map(fac, A, new AnyToComplex((ComplexRing) fac.coFac));
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<GenPolynomial<C>> fromAlgebraicCoefficients(GenPolynomialRing<GenPolynomial<C>> rfac, GenPolynomial<AlgebraicNumber<C>> A) {
        return map(rfac, A, new AlgToPoly());
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<AlgebraicNumber<C>> convertToAlgebraicCoefficients(GenPolynomialRing<AlgebraicNumber<C>> pfac, GenPolynomial<C> A) {
        return map(pfac, A, new CoeffToAlg(pfac.coFac));
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<AlgebraicNumber<C>> convertToRecAlgebraicCoefficients(int depth, GenPolynomialRing<AlgebraicNumber<C>> pfac, GenPolynomial<C> A) {
        return map(pfac, A, new CoeffToRecAlg(depth, pfac.coFac));
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<AlgebraicNumber<C>> convertRecursiveToAlgebraicCoefficients(GenPolynomialRing<AlgebraicNumber<C>> pfac, GenPolynomial<GenPolynomial<C>> A) {
        return map(pfac, A, new PolyToAlg(pfac.coFac));
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<Complex<C>> complexFromAlgebraic(GenPolynomialRing<Complex<C>> fac, GenPolynomial<AlgebraicNumber<C>> A) {
        return map(fac, A, new AlgebToCompl(fac.coFac));
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<AlgebraicNumber<C>> algebraicFromComplex(GenPolynomialRing<AlgebraicNumber<C>> fac, GenPolynomial<Complex<C>> A) {
        return map(fac, A, new ComplToAlgeb(fac.coFac));
    }

    public static <C extends RingElem<C> & Modular> GenPolynomial<C> chineseRemainder(GenPolynomialRing<C> fac, GenPolynomial<C> A, C mi, GenPolynomial<C> B) {
        C c;
        ModularRingFactory<C> cfac = fac.coFac;
        GenPolynomial<C> S = fac.getZERO().copy();
        SortedMap<ExpVector, C> av = A.copy().val;
        SortedMap<ExpVector, C> bv = B.getMap();
        SortedMap<ExpVector, C> sv = S.val;
        for (Entry<ExpVector, C> me : bv.entrySet()) {
            ExpVector e = (ExpVector) me.getKey();
            RingElem y = (RingElem) me.getValue();
            RingElem x = (RingElem) av.get(e);
            if (x != null) {
                av.remove(e);
                c = cfac.chineseRemainder(x, mi, y);
                if (!c.isZERO()) {
                    sv.put(e, c);
                }
            } else {
                c = cfac.chineseRemainder((RingElem) A.ring.coFac.getZERO(), mi, y);
                if (!c.isZERO()) {
                    sv.put(e, c);
                }
            }
        }
        for (Entry<ExpVector, C> me2 : av.entrySet()) {
            e = (ExpVector) me2.getKey();
            c = cfac.chineseRemainder((RingElem) me2.getValue(), mi, (RingElem) B.ring.coFac.getZERO());
            if (!c.isZERO()) {
                sv.put(e, c);
            }
        }
        return S;
    }

    public static <C extends RingElem<C>> GenPolynomial<GenPolynomial<C>> monic(GenPolynomial<GenPolynomial<C>> p) {
        if (p == null || p.isZERO()) {
            return p;
        }
        C lc = ((GenPolynomial) p.leadingBaseCoefficient()).leadingBaseCoefficient();
        if (!lc.isUnit()) {
            return p;
        }
        return p.multiply(((GenPolynomial) p.ring.coFac.getONE()).multiply((RingElem) lc.inverse()));
    }

    public static <C extends RingElem<C>> GenSolvablePolynomial<GenPolynomial<C>> monic(GenSolvablePolynomial<GenPolynomial<C>> p) {
        if (p == null || p.isZERO()) {
            return p;
        }
        C lc = ((GenPolynomial) p.leadingBaseCoefficient()).leadingBaseCoefficient();
        if (!lc.isUnit()) {
            return p;
        }
        return p.multiplyLeft(((GenPolynomial) p.ring.coFac.getONE()).multiply((RingElem) lc.inverse()));
    }

    public static <C extends RingElem<C>> List<GenPolynomial<C>> monic(List<GenPolynomial<C>> L) {
        return ListUtil.map(L, new 2());
    }

    public static <C extends RingElem<C>> List<GenWordPolynomial<C>> wordMonic(List<GenWordPolynomial<C>> L) {
        return ListUtil.map(L, new 3());
    }

    public static <C extends RingElem<C>> List<GenPolynomial<GenPolynomial<C>>> monicRec(List<GenPolynomial<GenPolynomial<C>>> L) {
        return ListUtil.map(L, new 4());
    }

    public static <C extends RingElem<C>> List<ExpVector> leadingExpVector(List<GenPolynomial<C>> L) {
        return ListUtil.map(L, new 5());
    }

    public static <C extends RingElem<C>> GenPolynomial<GenPolynomial<C>> extendCoefficients(GenPolynomialRing<GenPolynomial<C>> pfac, GenPolynomial<GenPolynomial<C>> A, int j, long k) {
        GenPolynomial<GenPolynomial<C>> Cp = pfac.getZERO().copy();
        if (!A.isZERO()) {
            GenPolynomialRing<C> cfac = pfac.coFac;
            Map<ExpVector, GenPolynomial<C>> CC = Cp.val;
            for (Entry<ExpVector, GenPolynomial<C>> y : A.val.entrySet()) {
                CC.put((ExpVector) y.getKey(), ((GenPolynomial) y.getValue()).extend(cfac, j, k));
            }
        }
        return Cp;
    }

    public static <C extends RingElem<C>> GenSolvablePolynomial<GenPolynomial<C>> extendCoefficients(GenSolvablePolynomialRing<GenPolynomial<C>> pfac, GenSolvablePolynomial<GenPolynomial<C>> A, int j, long k) {
        GenSolvablePolynomial<GenPolynomial<C>> Cp = pfac.getZERO().copy();
        if (!A.isZERO()) {
            GenPolynomialRing<C> cfac = pfac.coFac;
            Map<ExpVector, GenPolynomial<C>> CC = Cp.val;
            for (Entry<ExpVector, GenPolynomial<C>> y : A.val.entrySet()) {
                CC.put((ExpVector) y.getKey(), ((GenPolynomial) y.getValue()).extend(cfac, j, k));
            }
        }
        return Cp;
    }

    public static <C extends RingElem<C>> GenPolynomial<GenPolynomial<C>> toRecursive(GenPolynomialRing<GenPolynomial<C>> rfac, GenPolynomial<C> A) {
        GenPolynomial<GenPolynomial<C>> B = rfac.getZERO().copy();
        if (!A.isZERO()) {
            GenPolynomial<C> one = (GenPolynomial) rfac.getONECoefficient();
            Map<ExpVector, GenPolynomial<C>> Bv = B.val;
            Iterator i$ = A.iterator();
            while (i$.hasNext()) {
                Monomial<C> m = (Monomial) i$.next();
                Bv.put(m.e, one.multiply(m.c));
            }
        }
        return B;
    }

    public static <C extends RingElem<C>> GenSolvablePolynomial<GenPolynomial<C>> toRecursive(GenSolvablePolynomialRing<GenPolynomial<C>> rfac, GenSolvablePolynomial<C> A) {
        GenSolvablePolynomial<GenPolynomial<C>> B = rfac.getZERO().copy();
        if (!A.isZERO()) {
            GenPolynomial<C> one = (GenPolynomial) rfac.getONECoefficient();
            Map<ExpVector, GenPolynomial<C>> Bv = B.val;
            Iterator i$ = A.iterator();
            while (i$.hasNext()) {
                Monomial<C> m = (Monomial) i$.next();
                Bv.put(m.e, one.multiply(m.c));
            }
        }
        return B;
    }

    public static <C extends RingElem<C>> GenPolynomial<C> baseRemainderPoly(GenPolynomial<C> P, C s) {
        if (s == null || s.isZERO()) {
            throw new ArithmeticException(P + " division by zero " + s);
        }
        GenPolynomial<C> h = P.ring.getZERO().copy();
        Map<ExpVector, C> hm = h.val;
        for (Entry<ExpVector, C> m : P.getMap().entrySet()) {
            hm.put((ExpVector) m.getKey(), (RingElem) ((RingElem) m.getValue()).remainder(s));
        }
        return h;
    }

    @Deprecated
    public static <C extends RingElem<C>> GenPolynomial<C> basePseudoRemainder(GenPolynomial<C> P, GenPolynomial<C> S) {
        return baseSparsePseudoRemainder(P, S);
    }

    public static <C extends RingElem<C>> GenPolynomial<C> baseSparsePseudoRemainder(GenPolynomial<C> P, GenPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException(P.toString() + " division by zero " + S);
        } else if (P.isZERO()) {
            return P;
        } else {
            if (S.isConstant()) {
                return P.ring.getZERO();
            }
            RingElem c = S.leadingBaseCoefficient();
            ExpVector e = S.leadingExpVector();
            GenPolynomial<C> r = P;
            while (!r.isZERO()) {
                ExpVector f = r.leadingExpVector();
                if (!f.multipleOf(e)) {
                    break;
                }
                GenPolynomial h;
                C a = r.leadingBaseCoefficient();
                f = f.subtract(e);
                if (((RingElem) a.remainder(c)).isZERO()) {
                    h = S.multiply((RingElem) a.divide(c), f);
                } else {
                    r = r.multiply(c);
                    h = S.multiply(a, f);
                }
                r = r.subtract(h);
            }
            return r;
        }
    }

    public static <C extends RingElem<C>> GenPolynomial<C> baseDensePseudoRemainder(GenPolynomial<C> P, GenPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException(P + " division by zero " + S);
        } else if (P.isZERO()) {
            return P;
        } else {
            if (S.isConstant()) {
                return P.ring.getZERO();
            }
            long m = P.degree(0);
            long n = S.degree(0);
            RingElem c = S.leadingBaseCoefficient();
            ExpVector e = S.leadingExpVector();
            GenPolynomial<C> r = P;
            for (long i = m; i >= n && !r.isZERO(); i--) {
                if (i == r.degree(0)) {
                    ExpVector f = r.leadingExpVector();
                    r = r.multiply(c).subtract(S.multiply(r.leadingBaseCoefficient(), f.subtract(e)));
                } else {
                    r = r.multiply(c);
                }
            }
            return r;
        }
    }

    public static <C extends RingElem<C>> GenPolynomial<C> baseDensePseudoQuotient(GenPolynomial<C> P, GenPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException(P + " division by zero " + S);
        } else if (P.isZERO()) {
            return P;
        } else {
            long m = P.degree(0);
            long n = S.degree(0);
            RingElem c = S.leadingBaseCoefficient();
            ExpVector e = S.leadingExpVector();
            GenPolynomial<C> q = P.ring.getZERO();
            GenPolynomial<C> r = P;
            for (long i = m; i >= n && !r.isZERO(); i--) {
                if (i == r.degree(0)) {
                    ExpVector f = r.leadingExpVector();
                    C a = r.leadingBaseCoefficient();
                    f = f.subtract(e);
                    r = r.multiply(c).subtract(S.multiply(a, f));
                    q = q.multiply(c).sum(a, f);
                } else {
                    q = q.multiply(c);
                    r = r.multiply(c);
                }
            }
            return q;
        }
    }

    public static <C extends RingElem<C>> GenPolynomial<C> basePseudoDivide(GenPolynomial<C> P, GenPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException(P.toString() + " division by zero " + S);
        } else if (P.isZERO() || S.isONE()) {
            return P;
        } else {
            RingElem c = S.leadingBaseCoefficient();
            ExpVector e = S.leadingExpVector();
            GenPolynomial<C> r = P;
            GenPolynomial<C> q = S.ring.getZERO().copy();
            while (!r.isZERO()) {
                ExpVector f = r.leadingExpVector();
                if (!f.multipleOf(e)) {
                    return q;
                }
                GenPolynomial h;
                C a = r.leadingBaseCoefficient();
                f = f.subtract(e);
                if (((RingElem) a.remainder(c)).isZERO()) {
                    RingElem y = (RingElem) a.divide(c);
                    q = q.sum(y, f);
                    h = S.multiply(y, f);
                } else {
                    q = q.multiply(c).sum(a, f);
                    r = r.multiply(c);
                    h = S.multiply(a, f);
                }
                r = r.subtract(h);
            }
            return q;
        }
    }

    public static <C extends RingElem<C>> GenPolynomial<C>[] basePseudoQuotientRemainder(GenPolynomial<C> P, GenPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException(P.toString() + " division by zero " + S);
        }
        GenPolynomial<C>[] ret = new GenPolynomial[]{null, null};
        if (P.isZERO() || S.isONE()) {
            ret[0] = P;
            ret[1] = S.ring.getZERO();
        } else {
            RingElem c = S.leadingBaseCoefficient();
            ExpVector e = S.leadingExpVector();
            GenPolynomial<C> r = P;
            GenPolynomial<C> q = S.ring.getZERO().copy();
            while (!r.isZERO()) {
                ExpVector f = r.leadingExpVector();
                if (!f.multipleOf(e)) {
                    break;
                }
                GenPolynomial h;
                C a = r.leadingBaseCoefficient();
                f = f.subtract(e);
                if (((RingElem) a.remainder(c)).isZERO()) {
                    RingElem y = (RingElem) a.divide(c);
                    q = q.sum(y, f);
                    h = S.multiply(y, f);
                } else {
                    q = q.multiply(c).sum(a, f);
                    r = r.multiply(c);
                    h = S.multiply(a, f);
                }
                r = r.subtract(h);
            }
            ret[0] = q;
            ret[1] = r;
        }
        return ret;
    }

    public static <C extends RingElem<C>> boolean isBasePseudoQuotientRemainder(GenPolynomial<C> P, GenPolynomial<C> S, GenPolynomial<C> q, GenPolynomial<C> r) {
        GenPolynomial<C> rhs = q.multiply((GenPolynomial) S).sum((GenPolynomial) r);
        GenPolynomial<C> lhs = P;
        RingElem ldcf = S.leadingBaseCoefficient();
        long d = (P.degree(0) - S.degree(0)) + 1;
        if (d <= 0) {
            d = (-d) + 2;
        }
        long i = 0;
        while (i <= d) {
            if (!lhs.equals(rhs)) {
                if (!lhs.negate().equals(rhs)) {
                    lhs = lhs.multiply(ldcf);
                    i++;
                }
            }
            return true;
        }
        GenPolynomial<C> Pp = P;
        rhs = q.multiply((GenPolynomial) S);
        i = 0;
        while (i <= d) {
            lhs = Pp.subtract((GenPolynomial) r);
            if (!lhs.equals(rhs)) {
                if (!lhs.negate().equals(rhs)) {
                    Pp = Pp.multiply(ldcf);
                    i++;
                }
            }
            return true;
        }
        C a = P.leadingBaseCoefficient();
        rhs = q.multiply((GenPolynomial) S).sum((GenPolynomial) r);
        C b = rhs.leadingBaseCoefficient();
        RingElem lcm = (RingElem) ((RingElem) a.multiply(b)).divide(a.gcd(b));
        if (P.multiply((RingElem) lcm.divide(a)).equals(rhs.multiply((RingElem) lcm.divide(b)))) {
            return true;
        }
        return false;
    }

    public static <C extends RingElem<C>> GenPolynomial<GenPolynomial<C>> recursiveDivide(GenPolynomial<GenPolynomial<C>> P, GenPolynomial<C> s) {
        if (s == null || s.isZERO()) {
            throw new ArithmeticException("division by zero " + P + ", " + s);
        } else if (P.isZERO() || s.isONE()) {
            return P;
        } else {
            GenPolynomial<GenPolynomial<C>> p = P.ring.getZERO().copy();
            SortedMap<ExpVector, GenPolynomial<C>> pv = p.val;
            for (Entry<ExpVector, GenPolynomial<C>> m1 : P.getMap().entrySet()) {
                GenPolynomial<C> c1 = (GenPolynomial) m1.getValue();
                ExpVector e1 = (ExpVector) m1.getKey();
                GenPolynomial<C> c = basePseudoDivide(c1, s);
                if (c.isZERO()) {
                    System.out.println("rDiv, P  = " + P);
                    System.out.println("rDiv, c1 = " + c1);
                    System.out.println("rDiv, s  = " + s);
                    System.out.println("rDiv, c  = " + c);
                    throw new RuntimeException("something is wrong");
                }
                pv.put(e1, c);
            }
            return p;
        }
    }

    public static <C extends RingElem<C>> GenWordPolynomial<GenPolynomial<C>> recursiveDivide(GenWordPolynomial<GenPolynomial<C>> P, GenPolynomial<C> s) {
        if (s == null || s.isZERO()) {
            throw new ArithmeticException("division by zero " + P + ", " + s);
        } else if (P.isZERO() || s.isONE()) {
            return P;
        } else {
            GenWordPolynomial<GenPolynomial<C>> p = P.ring.getZERO().copy();
            SortedMap<Word, GenPolynomial<C>> pv = p.val;
            for (Entry<Word, GenPolynomial<C>> m1 : P.getMap().entrySet()) {
                GenPolynomial<C> c1 = (GenPolynomial) m1.getValue();
                Word e1 = (Word) m1.getKey();
                GenPolynomial<C> c = basePseudoDivide(c1, s);
                if (c.isZERO()) {
                    System.out.println("rDiv, P  = " + P);
                    System.out.println("rDiv, c1 = " + c1);
                    System.out.println("rDiv, s  = " + s);
                    System.out.println("rDiv, c  = " + c);
                    throw new RuntimeException("something is wrong");
                }
                pv.put(e1, c);
            }
            return p;
        }
    }

    public static <C extends RingElem<C>> GenPolynomial<GenPolynomial<C>> baseRecursiveDivide(GenPolynomial<GenPolynomial<C>> P, C s) {
        if (s == null || s.isZERO()) {
            throw new ArithmeticException("division by zero " + P + ", " + s);
        } else if (P.isZERO() || s.isONE()) {
            return P;
        } else {
            GenPolynomial<GenPolynomial<C>> p = P.ring.getZERO().copy();
            SortedMap<ExpVector, GenPolynomial<C>> pv = p.val;
            for (Entry<ExpVector, GenPolynomial<C>> m1 : P.getMap().entrySet()) {
                GenPolynomial<C> c1 = (GenPolynomial) m1.getValue();
                ExpVector e1 = (ExpVector) m1.getKey();
                GenPolynomial<C> c = coefficientBasePseudoDivide(c1, s);
                if (c.isZERO()) {
                    System.out.println("pu, c1 = " + c1);
                    System.out.println("pu, s  = " + s);
                    System.out.println("pu, c  = " + c);
                    throw new RuntimeException("something is wrong");
                }
                pv.put(e1, c);
            }
            return p;
        }
    }

    @Deprecated
    public static <C extends RingElem<C>> GenPolynomial<GenPolynomial<C>> recursivePseudoRemainder(GenPolynomial<GenPolynomial<C>> P, GenPolynomial<GenPolynomial<C>> S) {
        return recursiveSparsePseudoRemainder(P, S);
    }

    public static <C extends RingElem<C>> GenPolynomial<GenPolynomial<C>> recursiveSparsePseudoRemainder(GenPolynomial<GenPolynomial<C>> P, GenPolynomial<GenPolynomial<C>> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException(P + " division by zero " + S);
        } else if (P == null || P.isZERO()) {
            return P;
        } else {
            if (S.isConstant()) {
                return P.ring.getZERO();
            }
            RingElem c = (GenPolynomial) S.leadingBaseCoefficient();
            ExpVector e = S.leadingExpVector();
            GenPolynomial<GenPolynomial<C>> r = P;
            while (!r.isZERO()) {
                ExpVector f = r.leadingExpVector();
                if (!f.multipleOf(e)) {
                    return r;
                }
                GenPolynomial h;
                GenPolynomial<C> a = (GenPolynomial) r.leadingBaseCoefficient();
                f = f.subtract(e);
                if (c.isZERO()) {
                    h = S.multiply(basePseudoDivide(a, c), f);
                } else {
                    r = r.multiply(c);
                    h = S.multiply(a, f);
                }
                r = r.subtract(h);
            }
            return r;
        }
    }

    public static <C extends RingElem<C>> GenPolynomial<GenPolynomial<C>> recursiveDensePseudoRemainder(GenPolynomial<GenPolynomial<C>> P, GenPolynomial<GenPolynomial<C>> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException(P + " division by zero " + S);
        } else if (P == null || P.isZERO()) {
            return P;
        } else {
            if (S.isConstant()) {
                return P.ring.getZERO();
            }
            long m = P.degree(0);
            long n = S.degree(0);
            RingElem c = (GenPolynomial) S.leadingBaseCoefficient();
            ExpVector e = S.leadingExpVector();
            GenPolynomial<GenPolynomial<C>> r = P;
            for (long i = m; i >= n && !r.isZERO(); i--) {
                if (i == r.degree(0)) {
                    r = r.multiply(c).subtract(S.multiply((GenPolynomial) r.leadingBaseCoefficient(), r.leadingExpVector().subtract(e)));
                } else {
                    r = r.multiply(c);
                }
            }
            return r;
        }
    }

    public static <C extends RingElem<C>> GenPolynomial<GenPolynomial<C>> recursivePseudoDivide(GenPolynomial<GenPolynomial<C>> P, GenPolynomial<GenPolynomial<C>> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException(P + " division by zero " + S);
        } else if (P == null || P.isZERO() || S.isONE()) {
            return P;
        } else {
            RingElem c = (GenPolynomial) S.leadingBaseCoefficient();
            ExpVector e = S.leadingExpVector();
            GenPolynomial<GenPolynomial<C>> r = P;
            GenPolynomial<GenPolynomial<C>> q = S.ring.getZERO().copy();
            while (!r.isZERO()) {
                ExpVector f = r.leadingExpVector();
                if (!f.multipleOf(e)) {
                    break;
                }
                GenPolynomial h;
                GenPolynomial<C> a = (GenPolynomial) r.leadingBaseCoefficient();
                f = f.subtract(e);
                if (!baseSparsePseudoRemainder(a, c).isZERO() || c.isConstant()) {
                    q = q.multiply(c).sum(a, f);
                    r = r.multiply(c);
                    h = S.multiply(a, f);
                } else {
                    GenPolynomial<C> y = basePseudoDivide(a, c);
                    q = q.sum(y, f);
                    h = S.multiply(y, f);
                }
                r = r.subtract(h);
            }
            return q;
        }
    }

    public static <C extends RingElem<C>> boolean isRecursivePseudoQuotientRemainder(GenPolynomial<GenPolynomial<C>> P, GenPolynomial<GenPolynomial<C>> S, GenPolynomial<GenPolynomial<C>> q, GenPolynomial<GenPolynomial<C>> r) {
        long i;
        GenPolynomial<GenPolynomial<C>> rhs = q.multiply((GenPolynomial) S).sum((GenPolynomial) r);
        GenPolynomial<GenPolynomial<C>> lhs = P;
        RingElem ldcf = (GenPolynomial) S.leadingBaseCoefficient();
        long d = (P.degree(0) - S.degree(0)) + 1;
        if (d <= 0) {
            d = (-d) + 2;
        }
        for (i = 0; i <= d; i++) {
            if (lhs.equals(rhs)) {
                return true;
            }
            lhs = lhs.multiply(ldcf);
        }
        GenPolynomial<GenPolynomial<C>> Pp = P;
        rhs = q.multiply((GenPolynomial) S);
        for (i = 0; i <= d; i++) {
            if (Pp.subtract((GenPolynomial) r).equals(rhs)) {
                return true;
            }
            Pp = Pp.multiply(ldcf);
        }
        RingElem a = (GenPolynomial) P.leadingBaseCoefficient();
        rhs = q.multiply((GenPolynomial) S).sum((GenPolynomial) r);
        if (P.multiply((GenPolynomial) rhs.leadingBaseCoefficient()).equals(rhs.multiply(a))) {
            return true;
        }
        return false;
    }

    public static <C extends RingElem<C>> GenPolynomial<GenPolynomial<C>> coefficientPseudoDivide(GenPolynomial<GenPolynomial<C>> P, GenPolynomial<C> s) {
        if (s == null || s.isZERO()) {
            throw new ArithmeticException(P + " division by zero " + s);
        } else if (P.isZERO()) {
            return P;
        } else {
            GenPolynomial<GenPolynomial<C>> p = P.ring.getZERO().copy();
            SortedMap<ExpVector, GenPolynomial<C>> pv = p.val;
            for (Entry<ExpVector, GenPolynomial<C>> m : P.getMap().entrySet()) {
                ExpVector e = (ExpVector) m.getKey();
                GenPolynomial<C> c1 = (GenPolynomial) m.getValue();
                GenPolynomial<C> c = basePseudoDivide(c1, s);
                if (debug) {
                    GenPolynomial<C> x = c1.remainder((GenPolynomial) s);
                    if (!x.isZERO()) {
                        logger.info("divide x = " + x);
                        throw new ArithmeticException(" no exact division: " + c1 + "/" + s);
                    }
                }
                if (c.isZERO()) {
                    System.out.println(" no exact division: " + c1 + "/" + s);
                } else {
                    pv.put(e, c);
                }
            }
            return p;
        }
    }

    public static <C extends RingElem<C>> GenPolynomial<C> coefficientBasePseudoDivide(GenPolynomial<C> P, C s) {
        if (s == null || s.isZERO()) {
            throw new ArithmeticException(P + " division by zero " + s);
        } else if (P.isZERO()) {
            return P;
        } else {
            GenPolynomial<C> p = P.ring.getZERO().copy();
            SortedMap<ExpVector, C> pv = p.val;
            for (Entry<ExpVector, C> m : P.getMap().entrySet()) {
                ExpVector e = (ExpVector) m.getKey();
                RingElem c1 = (RingElem) m.getValue();
                RingElem c = (RingElem) c1.divide(s);
                if (debug) {
                    RingElem x = (RingElem) c1.remainder(s);
                    if (!x.isZERO()) {
                        logger.info("divide x = " + x);
                        throw new ArithmeticException(" no exact division: " + c1 + "/" + s);
                    }
                }
                if (c.isZERO()) {
                    System.out.println(" no exact division: " + c1 + "/" + s);
                } else {
                    pv.put(e, c);
                }
            }
            return p;
        }
    }

    public static <C extends RingElem<C>> GenPolynomial<C> baseDeriviative(GenPolynomial<C> P) {
        if (P == null || P.isZERO()) {
            return P;
        }
        GenPolynomialRing<C> pfac = P.ring;
        int i = pfac.nvar;
        if (r0 > 1) {
            throw new IllegalArgumentException(P.getClass().getName() + " only for univariate polynomials");
        }
        RingFactory<C> rf = pfac.coFac;
        GenPolynomial<C> d = pfac.getZERO().copy();
        Map<ExpVector, C> dm = d.val;
        for (Entry<ExpVector, C> m : P.getMap().entrySet()) {
            long fl = ((ExpVector) m.getKey()).getVal(0);
            if (fl > 0) {
                RingElem x = (RingElem) ((RingElem) m.getValue()).multiply((RingElem) rf.fromInteger(fl));
                if (!(x == null || x.isZERO())) {
                    dm.put(ExpVector.create(1, 0, fl - 1), x);
                }
            }
        }
        return d;
    }

    public static <C extends RingElem<C>> GenPolynomial<C> baseDeriviative(GenPolynomial<C> P, int r) {
        if (P == null || P.isZERO()) {
            return P;
        }
        GenPolynomialRing<C> pfac = P.ring;
        if (r >= 0) {
            int i = pfac.nvar;
            if (r0 > r) {
                int rp = (pfac.nvar - 1) - r;
                RingFactory<C> rf = pfac.coFac;
                GenPolynomial<C> d = pfac.getZERO().copy();
                Map<ExpVector, C> dm = d.val;
                for (Entry<ExpVector, C> m : P.getMap().entrySet()) {
                    ExpVector f = (ExpVector) m.getKey();
                    long fl = f.getVal(rp);
                    if (fl > 0) {
                        RingElem x = (RingElem) ((RingElem) m.getValue()).multiply((RingElem) rf.fromInteger(fl));
                        if (!(x == null || x.isZERO())) {
                            dm.put(f.subst(rp, fl - 1), x);
                        }
                    }
                }
                return d;
            }
        }
        throw new IllegalArgumentException(P.getClass().getName() + " deriviative variable out of bound " + r);
    }

    public static <C extends RingElem<C>> GenPolynomial<C> baseIntegral(GenPolynomial<C> P) {
        if (P == null || P.isZERO()) {
            return P;
        }
        GenPolynomialRing<C> pfac = P.ring;
        if (pfac.nvar > 1) {
            throw new IllegalArgumentException(P.getClass().getName() + " only for univariate polynomials");
        }
        RingFactory<C> rf = pfac.coFac;
        GenPolynomial<C> d = pfac.getZERO().copy();
        Map<ExpVector, C> dm = d.val;
        for (Entry<ExpVector, C> m : P.getMap().entrySet()) {
            long fl = ((ExpVector) m.getKey()).getVal(0) + 1;
            RingElem x = (RingElem) ((RingElem) m.getValue()).divide((RingElem) rf.fromInteger(fl));
            if (!(x == null || x.isZERO())) {
                dm.put(ExpVector.create(1, 0, fl), x);
            }
        }
        return d;
    }

    public static <C extends RingElem<C>> GenPolynomial<GenPolynomial<C>> recursiveDeriviative(GenPolynomial<GenPolynomial<C>> P) {
        if (P == null || P.isZERO()) {
            return P;
        }
        GenPolynomialRing<GenPolynomial<C>> pfac = P.ring;
        int i = pfac.nvar;
        if (r0 > 1) {
            throw new IllegalArgumentException(P.getClass().getName() + " only for univariate polynomials");
        }
        RingFactory<C> rf = pfac.coFac.coFac;
        GenPolynomial<GenPolynomial<C>> d = pfac.getZERO().copy();
        Map<ExpVector, GenPolynomial<C>> dm = d.val;
        for (Entry<ExpVector, GenPolynomial<C>> m : P.getMap().entrySet()) {
            long fl = ((ExpVector) m.getKey()).getVal(0);
            if (fl > 0) {
                GenPolynomial<C> x = ((GenPolynomial) m.getValue()).multiply((RingElem) rf.fromInteger(fl));
                if (!(x == null || x.isZERO())) {
                    dm.put(ExpVector.create(1, 0, fl - 1), x);
                }
            }
        }
        return d;
    }

    public static BigInteger factorBound(ExpVector e) {
        int n = 0;
        java.math.BigInteger p = java.math.BigInteger.ONE;
        if (e == null || e.isZERO()) {
            return BigInteger.ONE;
        }
        for (int i = 0; i < e.length(); i++) {
            if (e.getVal(i) > 0) {
                n = (int) (((long) n) + ((2 * e.getVal(i)) - 1));
                p = p.multiply(new java.math.BigInteger(BuildConfig.FLAVOR + (e.getVal(i) - 1)));
            }
        }
        return new BigInteger(new java.math.BigInteger("2").shiftLeft((n + (p.bitCount() + 1)) / 2));
    }

    public static <C extends RingElem<C>> GenPolynomial<C> evaluateMainRecursive(GenPolynomialRing<C> cfac, GenPolynomial<GenPolynomial<C>> A, C a) {
        if (A == null || A.isZERO()) {
            return cfac.getZERO();
        }
        if (A.ring.nvar != 1) {
            throw new IllegalArgumentException("evaluateMain no univariate polynomial");
        } else if (a == null || a.isZERO()) {
            return (GenPolynomial) A.trailingBaseCoefficient();
        } else {
            long i;
            GenPolynomial<C> B = null;
            long el1 = -1;
            long el2 = -1;
            for (Entry<ExpVector, GenPolynomial<C>> me : A.getMap().entrySet()) {
                el2 = ((ExpVector) me.getKey()).getVal(0);
                if (B == null) {
                    B = (GenPolynomial) me.getValue();
                } else {
                    for (i = el2; i < el1; i++) {
                        B = B.multiply((RingElem) a);
                    }
                    B = B.sum((GenPolynomial) me.getValue());
                }
                el1 = el2;
            }
            for (i = 0; i < el2; i++) {
                B = B.multiply((RingElem) a);
            }
            return B;
        }
    }

    public static <C extends RingElem<C>> GenPolynomial<C> evaluateMain(GenPolynomialRing<C> cfac, GenPolynomial<C> A, C a) {
        if (A == null || A.isZERO()) {
            return cfac.getZERO();
        }
        GenPolynomialRing rfac = new GenPolynomialRing((RingFactory) cfac, 1);
        if (rfac.nvar + cfac.nvar == A.ring.nvar) {
            return evaluateMainRecursive(cfac, recursive(rfac, (GenPolynomial) A), a);
        }
        throw new IllegalArgumentException("evaluateMain number of variabes mismatch");
    }

    public static <C extends RingElem<C>> List<GenPolynomial<C>> evaluateMain(GenPolynomialRing<C> cfac, List<GenPolynomial<C>> L, C a) {
        return ListUtil.map(L, new EvalMainPol(cfac, a));
    }

    public static <C extends RingElem<C>> C evaluateMain(RingFactory<C> cfac, GenPolynomial<C> A, C a) {
        if (A == null || A.isZERO()) {
            return (RingElem) cfac.getZERO();
        }
        if (A.ring.nvar != 1) {
            throw new IllegalArgumentException("evaluateMain no univariate polynomial");
        } else if (a == null || a.isZERO()) {
            return A.trailingBaseCoefficient();
        } else {
            long i;
            RingElem B;
            C B2 = null;
            long el1 = -1;
            long el2 = -1;
            for (Entry<ExpVector, C> me : A.getMap().entrySet()) {
                el2 = ((ExpVector) me.getKey()).getVal(0);
                if (B2 == null) {
                    B2 = (RingElem) me.getValue();
                } else {
                    for (i = el2; i < el1; i++) {
                        B = (RingElem) B2.multiply(a);
                    }
                    B = (RingElem) B2.sum((AbelianGroupElem) me.getValue());
                }
                el1 = el2;
            }
            for (i = 0; i < el2; i++) {
                B = (RingElem) B2.multiply(a);
            }
            return B2;
        }
    }

    public static <C extends RingElem<C>> List<C> evaluateMain(RingFactory<C> cfac, List<GenPolynomial<C>> L, C a) {
        return ListUtil.map(L, new EvalMain(cfac, a));
    }

    public static <C extends RingElem<C>> GenPolynomial<C> evaluate(GenPolynomialRing<C> cfac, GenPolynomialRing<GenPolynomial<C>> rfac, GenPolynomialRing<GenPolynomial<C>> nfac, GenPolynomialRing<C> dfac, GenPolynomial<C> A, C a) {
        if (rfac.nvar != 1) {
            throw new IllegalArgumentException("evaluate coefficient ring not univariate");
        } else if (A == null || A.isZERO()) {
            return cfac.getZERO();
        } else {
            Map<ExpVector, GenPolynomial<C>> Ap = A.contract(cfac);
            GenPolynomialRing<C> rcf = rfac.coFac;
            GenPolynomial Ev = nfac.getZERO().copy();
            Map<ExpVector, GenPolynomial<C>> Evm = Ev.val;
            for (Entry<ExpVector, GenPolynomial<C>> m : Ap.entrySet()) {
                ExpVector e = (ExpVector) m.getKey();
                GenPolynomial<C> d = evaluateMainRecursive(rcf, recursive((GenPolynomialRing) rfac, (GenPolynomial) m.getValue()), a);
                if (!(d == null || d.isZERO())) {
                    Evm.put(e, d);
                }
            }
            return distribute((GenPolynomialRing) dfac, Ev);
        }
    }

    public static <C extends RingElem<C>> GenPolynomial<C> evaluateFirst(GenPolynomialRing<C> cfac, GenPolynomialRing<C> dfac, GenPolynomial<C> A, C a) {
        if (A == null || A.isZERO()) {
            return dfac.getZERO();
        }
        Map<ExpVector, GenPolynomial<C>> Ap = A.contract(cfac);
        GenPolynomial<C> B = dfac.getZERO().copy();
        Map<ExpVector, C> Bm = B.val;
        for (Entry<ExpVector, GenPolynomial<C>> m : Ap.entrySet()) {
            ExpVector e = (ExpVector) m.getKey();
            C d = evaluateMain(cfac.coFac, (GenPolynomial) m.getValue(), (RingElem) a);
            if (!(d == null || d.isZERO())) {
                Bm.put(e, d);
            }
        }
        return B;
    }

    public static <C extends RingElem<C>> GenPolynomial<C> evaluateFirstRec(GenPolynomialRing<C> cfac, GenPolynomialRing<C> dfac, GenPolynomial<GenPolynomial<C>> A, C a) {
        if (A == null || A.isZERO()) {
            return dfac.getZERO();
        }
        Map<ExpVector, GenPolynomial<C>> Ap = A.getMap();
        GenPolynomial<C> B = dfac.getZERO().copy();
        Map<ExpVector, C> Bm = B.val;
        for (Entry<ExpVector, GenPolynomial<C>> m : Ap.entrySet()) {
            ExpVector e = (ExpVector) m.getKey();
            C d = evaluateMain(cfac.coFac, (GenPolynomial) m.getValue(), (RingElem) a);
            if (!(d == null || d.isZERO())) {
                Bm.put(e, d);
            }
        }
        return B;
    }

    @Deprecated
    public static <C extends RingElem<C>> C evaluateAll(RingFactory<C> cfac, GenPolynomialRing<C> genPolynomialRing, GenPolynomial<C> A, List<C> a) {
        return evaluateAll(cfac, A, a);
    }

    public static <C extends RingElem<C>> C evaluateAll(RingFactory<C> cfac, GenPolynomial<C> A, List<C> a) {
        if (A == null || A.isZERO()) {
            return (RingElem) cfac.getZERO();
        }
        GenPolynomialRing<C> dfac = A.ring;
        if (a == null || a.size() != dfac.nvar) {
            throw new IllegalArgumentException("evaluate tuple size not equal to number of variables");
        } else if (dfac.nvar == 0) {
            return A.trailingBaseCoefficient();
        } else {
            if (dfac.nvar == 1) {
                return evaluateMain((RingFactory) cfac, (GenPolynomial) A, (RingElem) a.get(0));
            }
            RingElem b = (RingElem) cfac.getZERO();
            GenPolynomial Ap = A;
            for (int k = 0; k < dfac.nvar - 1; k++) {
                GenPolynomial<C> Bp = evaluateFirst(new GenPolynomialRing((RingFactory) cfac, 1), new GenPolynomialRing((RingFactory) cfac, (dfac.nvar - 1) - k), Ap, (RingElem) a.get(k));
                if (Bp.isZERO()) {
                    return b;
                }
                GenPolynomial<C> Ap2 = Bp;
            }
            return evaluateMain((RingFactory) cfac, Ap, (RingElem) a.get(dfac.nvar - 1));
        }
    }

    public static <C extends RingElem<C>> GenPolynomial<C> substituteMain(GenPolynomial<C> A, GenPolynomial<C> s) {
        return substituteUnivariate(A, s);
    }

    public static <C extends RingElem<C>> GenPolynomial<C> substituteUnivariate(GenPolynomial<C> f, GenPolynomial<C> t) {
        if (f == null || t == null) {
            return null;
        }
        GenPolynomialRing<C> fac = f.ring;
        if (fac.nvar > 1) {
            throw new IllegalArgumentException("only for univariate polynomial f");
        } else if (f.isZERO() || f.isConstant()) {
            return f;
        } else {
            long i;
            if (t.ring.nvar > 1) {
                fac = t.ring;
            }
            GenPolynomial<C> s = null;
            long el1 = -1;
            long el2 = -1;
            for (Entry<ExpVector, C> me : f.getMap().entrySet()) {
                el2 = ((ExpVector) me.getKey()).getVal(0);
                if (s == null) {
                    s = fac.getZERO().sum((RingElem) me.getValue());
                } else {
                    for (i = el2; i < el1; i++) {
                        s = s.multiply((GenPolynomial) t);
                    }
                    s = s.sum((RingElem) me.getValue());
                }
                el1 = el2;
            }
            for (i = 0; i < el2; i++) {
                s = s.multiply((GenPolynomial) t);
            }
            return s;
        }
    }

    public static <C extends RingElem<C>> GenPolynomial<C> seriesOfTaylor(GenPolynomial<C> f, C a) {
        if (f == null) {
            return null;
        }
        GenPolynomialRing<C> fac = f.ring;
        if (fac.nvar > 1) {
            throw new IllegalArgumentException("only for univariate polynomials");
        } else if (f.isZERO() || f.isConstant()) {
            return f;
        } else {
            GenPolynomial<C> s = fac.getZERO().sum(evaluateMain(fac.coFac, (GenPolynomial) f, (RingElem) a));
            long n = 1;
            long i = 0;
            for (GenPolynomial g = baseDeriviative(f); !g.isZERO(); g = baseDeriviative(g)) {
                i++;
                n *= i;
                s = s.sum(fac.univariate(0, i).multiply(evaluateMain(fac.coFac, g, (RingElem) a)).divide(fac.fromInteger(n)));
            }
            return s;
        }
    }

    public static <C extends RingElem<C>> GenPolynomial<GenPolynomial<C>> interpolate(GenPolynomialRing<GenPolynomial<C>> fac, GenPolynomial<GenPolynomial<C>> A, GenPolynomial<C> M, C mi, GenPolynomial<C> B, C am) {
        GenPolynomial<C> c;
        GenPolynomial<GenPolynomial<C>> S = fac.getZERO().copy();
        SortedMap<ExpVector, GenPolynomial<C>> av = A.copy().val;
        SortedMap<ExpVector, C> bv = B.getMap();
        SortedMap<ExpVector, GenPolynomial<C>> sv = S.val;
        GenPolynomialRing cfac = fac.coFac;
        RingFactory<C> bfac = cfac.coFac;
        for (Entry<ExpVector, C> me : bv.entrySet()) {
            ExpVector e = (ExpVector) me.getKey();
            RingElem y = (RingElem) me.getValue();
            GenPolynomial x = (GenPolynomial) av.get(e);
            if (x != null) {
                av.remove(e);
                c = interpolate(cfac, x, (GenPolynomial) M, (RingElem) mi, y, (RingElem) am);
                if (!c.isZERO()) {
                    sv.put(e, c);
                }
            } else {
                c = interpolate(cfac, cfac.getZERO(), (GenPolynomial) M, (RingElem) mi, y, (RingElem) am);
                if (!c.isZERO()) {
                    sv.put(e, c);
                }
            }
        }
        for (Entry<ExpVector, GenPolynomial<C>> me2 : av.entrySet()) {
            e = (ExpVector) me2.getKey();
            GenPolynomial genPolynomial = (GenPolynomial) me2.getValue();
            c = interpolate(cfac, (GenPolynomial) x, (GenPolynomial) M, (RingElem) mi, (RingElem) bfac.getZERO(), (RingElem) am);
            if (!c.isZERO()) {
                sv.put(e, c);
            }
        }
        return S;
    }

    public static <C extends RingElem<C>> GenPolynomial<C> interpolate(GenPolynomialRing<C> fac, GenPolynomial<C> A, GenPolynomial<C> M, C mi, C a, C am) {
        RingElem d = (RingElem) a.subtract(evaluateMain(fac.coFac, (GenPolynomial) A, (RingElem) am));
        if (d.isZERO()) {
            return A;
        }
        return M.multiply((RingElem) d.multiply(mi)).sum((GenPolynomial) A);
    }

    public static <C extends RingElem<C>> GenPolynomial<GenPolynomial<C>> switchVariables(GenPolynomial<GenPolynomial<C>> P) {
        if (P == null) {
            throw new IllegalArgumentException("P == null");
        }
        GenPolynomialRing rfac1 = P.ring;
        GenPolynomialRing cfac1 = rfac1.coFac;
        RingFactory cfac2 = new GenPolynomialRing(cfac1.coFac, rfac1);
        GenPolynomial<C> zero = cfac2.getZERO();
        GenPolynomial<GenPolynomial<C>> B = new GenPolynomialRing(cfac2, cfac1).getZERO().copy();
        if (P.isZERO()) {
            return B;
        }
        Iterator it = P.iterator();
        while (it.hasNext()) {
            Monomial<GenPolynomial<C>> mr = (Monomial) it.next();
            Iterator i$ = mr.c.iterator();
            while (i$.hasNext()) {
                Monomial<C> mc = (Monomial) i$.next();
                B = B.sum(zero.sum(mc.c, mr.e), mc.e);
            }
        }
        return B;
    }

    public static <C extends RingElem<C>> long totalDegreeLeadingTerm(List<GenPolynomial<C>> P) {
        long degree = 0;
        for (GenPolynomial<C> g : P) {
            long total = g.leadingExpVector().totalDeg();
            if (degree < total) {
                degree = total;
            }
        }
        return degree;
    }

    public static <C extends RingElem<C>> long totalDegree(List<GenPolynomial<C>> P) {
        long degree = 0;
        for (GenPolynomial<C> g : P) {
            long total = g.totalDegree();
            if (degree < total) {
                degree = total;
            }
        }
        return degree;
    }

    public static <C extends RingElem<C>> long maxDegree(List<GenPolynomial<C>> P) {
        long degree = 0;
        for (GenPolynomial<C> g : P) {
            long total = g.degree();
            if (degree < total) {
                degree = total;
            }
        }
        return degree;
    }

    public static <C extends RingElem<C>> long coeffMaxDegree(GenPolynomial<GenPolynomial<C>> A) {
        if (A.isZERO()) {
            return 0;
        }
        long deg = 0;
        for (GenPolynomial<C> a : A.getMap().values()) {
            long d = a.degree();
            if (d > deg) {
                deg = d;
            }
        }
        return deg;
    }

    public static <C extends RingElem<C>, D extends RingElem<D>> GenPolynomial<D> map(GenPolynomialRing<D> ring, GenPolynomial<C> p, UnaryFunctor<C, D> f) {
        GenPolynomial<D> n = ring.getZERO().copy();
        SortedMap<ExpVector, D> nv = n.val;
        Iterator i$ = p.iterator();
        while (i$.hasNext()) {
            Monomial<C> m = (Monomial) i$.next();
            RingElem c = (RingElem) f.eval(m.c);
            if (!(c == null || c.isZERO())) {
                nv.put(m.e, c);
            }
        }
        return n;
    }

    public static <C extends GcdRingElem<C>> List<GenPolynomial<Product<C>>> toProductGen(GenPolynomialRing<Product<C>> pfac, List<GenPolynomial<C>> L) {
        List<GenPolynomial<Product<C>>> list = new ArrayList();
        if (!(L == null || L.size() == 0)) {
            for (GenPolynomial a : L) {
                list.add(toProductGen((GenPolynomialRing) pfac, a));
            }
        }
        return list;
    }

    public static <C extends GcdRingElem<C>> GenPolynomial<Product<C>> toProductGen(GenPolynomialRing<Product<C>> pfac, GenPolynomial<C> A) {
        GenPolynomial<Product<C>> P = pfac.getZERO().copy();
        if (!(A == null || A.isZERO())) {
            ProductRing rfac = (ProductRing) pfac.coFac;
            for (Entry<ExpVector, C> y : A.getMap().entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                Product<C> p = toProductGen(rfac, (GcdRingElem) y.getValue());
                if (!p.isZERO()) {
                    P.doPutToMap(e, p);
                }
            }
        }
        return P;
    }

    public static <C extends GcdRingElem<C>> Product<C> toProductGen(ProductRing<C> pfac, C c) {
        SortedMap<Integer, C> elem = new TreeMap();
        for (int i = 0; i < pfac.length(); i++) {
            GcdRingElem u = (GcdRingElem) pfac.getFactory(i).copy(c);
            if (!(u == null || u.isZERO())) {
                elem.put(Integer.valueOf(i), u);
            }
        }
        return new Product(pfac, elem);
    }

    public static <C extends RingElem<C>> Product<GenPolynomial<C>> toProduct(ProductRing<GenPolynomial<C>> pfac, C c, ExpVector e) {
        SortedMap<Integer, GenPolynomial<C>> elem = new TreeMap();
        for (int i = 0; i < e.length(); i++) {
            GenPolynomial<C> u;
            GenPolynomialRing<C> fac = (GenPolynomialRing) pfac.getFactory(i);
            long a = e.getVal(i);
            if (a == 0) {
                u = fac.getONE();
            } else {
                u = fac.univariate(0, a);
            }
            elem.put(Integer.valueOf(i), u.multiply((RingElem) c));
        }
        return new Product(pfac, elem);
    }

    public static <C extends RingElem<C>> Product<GenPolynomial<C>> toProduct(ProductRing<GenPolynomial<C>> pfac, GenPolynomial<C> A) {
        Product<GenPolynomial<C>> P = pfac.getZERO();
        if (A == null || A.isZERO()) {
            return P;
        }
        for (Entry<ExpVector, C> y : A.getMap().entrySet()) {
            P = P.sum(toProduct(pfac, (RingElem) y.getValue(), (ExpVector) y.getKey()));
        }
        return P;
    }

    public static Product<ModInteger> toProduct(ProductRing<ModInteger> pfac, BigInteger c) {
        SortedMap<Integer, ModInteger> elem = new TreeMap();
        for (int i = 0; i < pfac.length(); i++) {
            ModInteger u = ((ModIntegerRing) pfac.getFactory(i)).fromInteger(c.getVal());
            if (!u.isZERO()) {
                elem.put(Integer.valueOf(i), u);
            }
        }
        return new Product(pfac, elem);
    }

    public static GenPolynomial<Product<ModInteger>> toProduct(GenPolynomialRing<Product<ModInteger>> pfac, GenPolynomial<BigInteger> A) {
        GenPolynomial<Product<ModInteger>> P = pfac.getZERO().copy();
        if (!(A == null || A.isZERO())) {
            ProductRing fac = (ProductRing) pfac.coFac;
            for (Entry<ExpVector, BigInteger> y : A.getMap().entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                Product<ModInteger> p = toProduct(fac, (BigInteger) y.getValue());
                if (!p.isZERO()) {
                    P.doPutToMap(e, p);
                }
            }
        }
        return P;
    }

    public static List<GenPolynomial<Product<ModInteger>>> toProduct(GenPolynomialRing<Product<ModInteger>> pfac, List<GenPolynomial<BigInteger>> L) {
        List<GenPolynomial<Product<ModInteger>>> list = new ArrayList();
        if (!(L == null || L.size() == 0)) {
            for (GenPolynomial a : L) {
                list.add(toProduct((GenPolynomialRing) pfac, a));
            }
        }
        return list;
    }

    public static <C extends RingElem<C>> List<GenPolynomial<C>> intersect(GenPolynomialRing<C> R, List<GenPolynomial<C>> F) {
        if (F == null || F.isEmpty()) {
            return F;
        }
        GenPolynomialRing<C> pfac = ((GenPolynomial) F.get(0)).ring;
        int d = pfac.nvar - R.nvar;
        if (d <= 0) {
            return F;
        }
        List<GenPolynomial<C>> H = new ArrayList(F.size());
        for (GenPolynomial<C> p : F) {
            Map<ExpVector, GenPolynomial<C>> m = p.contract(R);
            if (logger.isDebugEnabled()) {
                logger.debug("intersect contract m = " + m);
            }
            if (m.size() == 1) {
                for (Entry<ExpVector, GenPolynomial<C>> me : m.entrySet()) {
                    if (((ExpVector) me.getKey()).isZERO()) {
                        H.add(me.getValue());
                    }
                }
            }
        }
        GenPolynomialRing<C> tfac = pfac.contract(d);
        if (tfac.equals(R)) {
            return H;
        }
        logger.warn("tfac != R: tfac = " + tfac.toScript() + ", R = " + R.toScript() + ", pfac = " + pfac.toScript());
        return H;
    }

    public static <C extends RingElem<C>> List<GenSolvablePolynomial<C>> intersect(GenSolvablePolynomialRing<C> R, List<GenSolvablePolynomial<C>> F) {
        return PolynomialList.castToSolvableList(intersect((GenPolynomialRing) R, PolynomialList.castToList(F)));
    }

    public static <C extends RingElem<C>> GenPolynomial<C> removeUnusedUpperVariables(GenPolynomial<C> p) {
        GenPolynomialRing<C> fac = p.ring;
        if (fac.nvar <= 1) {
            return p;
        }
        int[] dep = p.degreeVector().dependencyOnVariables();
        if (fac.nvar == dep.length) {
            return p;
        }
        if (dep.length == 0) {
            return new GenPolynomial(new GenPolynomialRing(fac.coFac, 0), p.leadingBaseCoefficient());
        }
        int l = dep[0];
        int r = dep[dep.length - 1];
        if (l == 0) {
            return p;
        }
        Map<ExpVector, GenPolynomial<C>> mpr = p.contract(fac.contract(l));
        if (mpr.size() != 1) {
            System.out.println("upper ex, l = " + l + ", r = " + r + ", p = " + p + ", fac = " + fac.toScript());
            throw new RuntimeException("this should not happen " + mpr);
        }
        GenPolynomial<C> pr = (GenPolynomial) mpr.values().iterator().next();
        if ((fac.nvar - 1) - r == 0) {
            return pr;
        }
        return pr;
    }

    public static <C extends RingElem<C>> GenPolynomial<C> removeUnusedLowerVariables(GenPolynomial<C> p) {
        GenPolynomialRing<C> fac = p.ring;
        int i = fac.nvar;
        if (r0 <= 1) {
            return p;
        }
        int[] dep = p.degreeVector().dependencyOnVariables();
        if (fac.nvar == dep.length) {
            return p;
        }
        if (dep.length == 0) {
            RingFactory ringFactory = fac.coFac;
            return new GenPolynomial(new GenPolynomialRing(ringFactory, 0), p.leadingBaseCoefficient());
        }
        int l = dep[0];
        int r = dep[dep.length - 1];
        if (r == fac.nvar - 1) {
            return p;
        }
        GenPolynomialRing<GenPolynomial<C>> rfac = fac.recursive(r + 1);
        GenPolynomial<GenPolynomial<C>> mpr = recursive((GenPolynomialRing) rfac, (GenPolynomial) p);
        if (mpr.length() != p.length()) {
            System.out.println("lower ex, l = " + l + ", r = " + r + ", p = " + p + ", fac = " + fac.toScript());
            throw new RuntimeException("this should not happen " + mpr);
        }
        GenPolynomial<C> pr = new GenPolynomialRing(fac.coFac, (GenPolynomialRing) rfac).getZERO().copy();
        Iterator i$ = mpr.iterator();
        while (i$.hasNext()) {
            Monomial<GenPolynomial<C>> m = (Monomial) i$.next();
            ExpVector e = m.e;
            GenPolynomial<C> a = m.c;
            if (a.isConstant()) {
                pr.doPutToMap(e, a.leadingBaseCoefficient());
            } else {
                throw new RuntimeException("this can not happen " + a);
            }
        }
        return pr;
    }

    public static <C extends RingElem<C>> GenPolynomial<C> removeUnusedMiddleVariables(GenPolynomial<C> p) {
        GenPolynomialRing<C> fac = p.ring;
        int i = fac.nvar;
        if (r0 <= 2) {
            return p;
        }
        int[] dep = p.degreeVector().dependencyOnVariables();
        if (fac.nvar == dep.length) {
            return p;
        }
        if (dep.length == 0) {
            RingFactory ringFactory = fac.coFac;
            return new GenPolynomial(new GenPolynomialRing(ringFactory, 0), p.leadingBaseCoefficient());
        }
        ExpVector e1 = p.leadingExpVector();
        i = dep.length;
        int i2;
        Iterator i$;
        if (r0 == 1) {
            TermOrder termOrder = new TermOrder(fac.tord.getEvord());
            i2 = dep[0];
            String v1 = e1.indexVarName(i2, fac.getVars());
            String[] vars = new String[]{v1};
            GenPolynomial<C> p1 = new GenPolynomialRing(fac.coFac, termOrder, vars).getZERO().copy();
            i$ = p.iterator();
            while (i$.hasNext()) {
                Monomial<C> m = (Monomial) i$.next();
                p1.doPutToMap(ExpVector.create(1, 0, m.e.getVal(i2)), m.c);
            }
            return p1;
        }
        GenPolynomial<GenPolynomial<C>> mpr = recursive(fac.recursive(1), (GenPolynomial) p);
        int l = dep[0];
        int r = fac.nvar - dep[1];
        termOrder = new TermOrder(fac.tord.getEvord());
        String[] vs = fac.getVars();
        vars = new String[(r + 1)];
        for (i2 = 0; i2 < r; i2++) {
            vars[i2] = vs[i2];
        }
        vars[r] = e1.indexVarName(l, vs);
        GenPolynomialRing dfac = new GenPolynomialRing(fac.coFac, termOrder, vars);
        GenPolynomialRing<GenPolynomial<C>> fac2 = dfac.recursive(1);
        GenPolynomialRing<C> cfac = fac2.coFac;
        GenPolynomial<GenPolynomial<C>> p2r = fac2.getZERO().copy();
        i$ = mpr.iterator();
        while (i$.hasNext()) {
            Monomial<GenPolynomial<C>> m2 = (Monomial) i$.next();
            ExpVector e = m2.e;
            Map<ExpVector, GenPolynomial<C>> cc = m2.c.contract(cfac);
            for (Entry<ExpVector, GenPolynomial<C>> me : cc.entrySet()) {
                if (((ExpVector) me.getKey()).isZERO()) {
                    p2r.doPutToMap(e, (GenPolynomial) me.getValue());
                } else {
                    throw new RuntimeException("this should not happen " + cc);
                }
            }
        }
        return distribute(dfac, (GenPolynomial) p2r);
    }

    public static <C extends RingElem<C>> GenPolynomial<C> selectWithVariable(List<GenPolynomial<C>> P, int i) {
        for (GenPolynomial<C> p : P) {
            int[] dep = p.leadingExpVector().dependencyOnVariables();
            if (dep.length == 1 && dep[0] == i) {
                return p;
            }
        }
        return null;
    }
}
