package edu.jas.application;

import edu.jas.arith.Rational;
import edu.jas.poly.Complex;
import edu.jas.poly.ComplexRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.poly.TermOrder;
import edu.jas.root.Interval;
import edu.jas.root.RealAlgebraicNumber;
import edu.jas.root.RealRootTuple;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.ufd.SquarefreeFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

public class RootFactory {
    private static boolean debug;
    private static final Logger logger;

    static {
        logger = Logger.getLogger(RootFactory.class);
        debug = logger.isDebugEnabled();
    }

    public static <C extends GcdRingElem<C> & Rational> boolean isRootRealCoeff(GenPolynomial<C> f, Complex<RealAlgebraicNumber<C>> r) {
        return isRoot(PolyUtil.complexFromAny(new GenPolynomialRing(new ComplexRing(f.ring.coFac), f.ring), f), (Complex) r);
    }

    public static <C extends GcdRingElem<C> & Rational> boolean isRoot(GenPolynomial<Complex<C>> f, Complex<RealAlgebraicNumber<C>> r) {
        RingFactory cr = r.factory();
        Complex<RealAlgebraicNumber<C>> a = (Complex) PolyUtil.evaluateMain(cr, PolyUtilApp.convertToComplexRealCoefficients(new GenPolynomialRing(cr, f.factory()), f), (RingElem) r);
        boolean t = a.isZERO();
        if (t) {
            List<RealAlgebraicNumber<C>> rlist = cr.ring.getRoot().tuple;
            Interval<C> vr = ((RealAlgebraicNumber) rlist.get(0)).ring.getRoot();
            Interval<C> vi = ((RealAlgebraicNumber) rlist.get(1)).ring.getRoot();
            RingFactory ccfac = new ComplexRing((RingFactory) ((GcdRingElem) vr.left).factory());
            RingElem sw = new Complex(ccfac, vr.left, vi.left);
            Complex<C> epsw = (Complex) PolyUtil.evaluateMain(ccfac, (GenPolynomial) f, sw);
            Complex<C> epne = (Complex) PolyUtil.evaluateMain(ccfac, (GenPolynomial) f, new Complex(ccfac, vr.right, vi.right));
            int rootim = ((GcdRingElem) epsw.getIm()).signum() * ((GcdRingElem) epne.getIm()).signum();
            if (((GcdRingElem) epsw.getRe()).signum() * ((GcdRingElem) epne.getRe()).signum() > 0 || rootim > 0) {
            }
            return true;
        }
        logger.info("f(r) = " + a + ", f = " + f + ", r  = " + r);
        return t;
    }

    public static <C extends GcdRingElem<C> & Rational> boolean isRoot(GenPolynomial<Complex<C>> f, List<Complex<RealAlgebraicNumber<C>>> R) {
        for (Complex r : R) {
            if (!isRoot((GenPolynomial) f, r)) {
                return false;
            }
        }
        return true;
    }

    public static <C extends GcdRingElem<C> & Rational> List<Complex<RealAlgebraicNumber<C>>> complexAlgebraicNumbersComplex(GenPolynomial<Complex<C>> f) {
        GenPolynomialRing<Complex<C>> pfac = f.factory();
        if (pfac.nvar != 1) {
            throw new IllegalArgumentException("only for univariate polynomials");
        }
        Map<GenPolynomial<Complex<C>>, Long> F = SquarefreeFactory.getImplementation((RingFactory) pfac.coFac).squarefreeFactors(f.monic());
        Set<GenPolynomial<Complex<C>>> S = F.keySet();
        List<Complex<RealAlgebraicNumber<C>>> list = new ArrayList();
        for (GenPolynomial<Complex<C>> sp : S) {
            if (!(sp.isConstant() || sp.isZERO())) {
                List<Complex<RealAlgebraicNumber<C>>> ls = complexAlgebraicNumbersSquarefree(sp);
                long m = ((Long) F.get(sp)).longValue();
                for (long i = 0; i < m; i++) {
                    list.addAll(ls);
                }
            }
        }
        return list;
    }

    public static <C extends GcdRingElem<C> & Rational> List<Complex<RealAlgebraicNumber<C>>> complexAlgebraicNumbersSquarefree(GenPolynomial<Complex<C>> f) {
        GenPolynomialRing<Complex<C>> pfac = f.factory();
        int i = pfac.nvar;
        if (r0 != 1) {
            throw new IllegalArgumentException("only for univariate polynomials");
        }
        RingFactory cfac = pfac.coFac;
        GenPolynomialRing<Complex<C>> genPolynomialRing = new GenPolynomialRing(cfac, 2, new TermOrder(2));
        GenPolynomial<Complex<C>> t = genPolynomialRing.univariate(1, 1).sum(genPolynomialRing.univariate(0, 1).multiply(cfac.getIMAG()));
        GenPolynomialRing<C> genPolynomialRing2 = new GenPolynomialRing(cfac.ring, (GenPolynomialRing) genPolynomialRing);
        List<Complex<RealAlgebraicNumber<C>>> list = new ArrayList();
        GenPolynomial<Complex<C>> sp = f;
        if (!(sp.isConstant() || sp.isZERO())) {
            GenPolynomial<Complex<C>> su = PolyUtil.substituteUnivariate(sp, t).monic();
            GenPolynomial<C> re = PolyUtil.realPartFromComplex(genPolynomialRing2, su);
            GenPolynomial<C> im = PolyUtil.imaginaryPartFromComplex(genPolynomialRing2, su);
            if (debug) {
                logger.debug("rfac = " + genPolynomialRing2.toScript());
                logger.debug("t  = " + t + ", re = " + re.toScript() + ", im = " + im.toScript());
            }
            List<GenPolynomial<C>> arrayList = new ArrayList(2);
            arrayList.add(re);
            arrayList.add(im);
            for (IdealWithUniv idu : new Ideal((GenPolynomialRing) genPolynomialRing2, (List) arrayList).zeroDimRootDecomposition()) {
                for (List<RealAlgebraicNumber<C>> crr : PolyUtilApp.realAlgebraicRoots(idu).ran) {
                    RealAlgebraicRing<C> car = new RealAlgebraicRing(idu, new RealRootTuple(crr));
                    List<RealAlgebraicNumber<C>> gens = car.generators();
                    int sg = gens.size();
                    list.add(new Complex(new ComplexRing(car), (RealAlgebraicNumber) gens.get(sg - 2), (RealAlgebraicNumber) gens.get(sg - 1)));
                }
            }
        }
        return list;
    }
}
