package edu.jas.root;

import edu.jas.arith.BigRational;
import edu.jas.arith.Rational;
import edu.jas.poly.Complex;
import edu.jas.poly.ComplexRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.ufd.FactorFactory;
import edu.jas.ufd.SquarefreeFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RootFactory {
    public static <C extends GcdRingElem<C> & Rational> boolean isRoot(GenPolynomial<C> f, RealAlgebraicNumber<C> r) {
        RingFactory rr = r.factory();
        return ((RealAlgebraicNumber) PolyUtil.evaluateMain(rr, PolyUtilRoot.convertToRealCoefficients(new GenPolynomialRing(rr, f.factory()), f), (RingElem) r)).isZERO();
    }

    public static <C extends GcdRingElem<C> & Rational> List<RealAlgebraicNumber<C>> realAlgebraicNumbers(GenPolynomial<C> f) {
        RealRoots<C> rr = new RealRootsSturm();
        Map<GenPolynomial<C>, Long> SF = SquarefreeFactory.getImplementation(f.ring.coFac).squarefreeFactors((GenPolynomial) f);
        Set<GenPolynomial<C>> S = SF.keySet();
        List<RealAlgebraicNumber<C>> list = new ArrayList();
        for (GenPolynomial<C> sp : S) {
            for (Interval<C> I : rr.realRoots(sp)) {
                RealAlgebraicNumber<C> rn = new RealAlgebraicRing(sp, I).getGenerator();
                long mult = ((Long) SF.get(sp)).longValue();
                int i = 0;
                while (true) {
                    if (((long) i) < mult) {
                        list.add(rn);
                        i++;
                    }
                }
            }
        }
        return list;
    }

    public static <C extends GcdRingElem<C> & Rational> List<RealAlgebraicNumber<C>> realAlgebraicNumbers(GenPolynomial<C> f, BigRational eps) {
        RealRoots<C> rr = new RealRootsSturm();
        Map<GenPolynomial<C>, Long> SF = SquarefreeFactory.getImplementation(f.ring.coFac).squarefreeFactors((GenPolynomial) f);
        Set<GenPolynomial<C>> S = SF.keySet();
        List<RealAlgebraicNumber<C>> list = new ArrayList();
        for (GenPolynomial<C> sp : S) {
            for (Interval<C> I : rr.realRoots((GenPolynomial) sp, eps)) {
                RealAlgebraicRing<C> rar = new RealAlgebraicRing(sp, I);
                rar.setEps(eps);
                RealAlgebraicNumber<C> rn = rar.getGenerator();
                long mult = ((Long) SF.get(sp)).longValue();
                int i = 0;
                while (true) {
                    if (((long) i) < mult) {
                        list.add(rn);
                        i++;
                    }
                }
            }
        }
        return list;
    }

    public static <C extends GcdRingElem<C> & Rational> List<RealAlgebraicNumber<C>> realAlgebraicNumbersField(GenPolynomial<C> f) {
        RealRoots<C> rr = new RealRootsSturm();
        Map<GenPolynomial<C>, Long> SF = FactorFactory.getImplementation(f.ring.coFac).baseFactors(f);
        Set<GenPolynomial<C>> S = SF.keySet();
        List<RealAlgebraicNumber<C>> list = new ArrayList();
        for (GenPolynomial<C> sp : S) {
            for (Interval<C> I : rr.realRoots(sp)) {
                RealAlgebraicNumber<C> rn = new RealAlgebraicRing(sp, I, true).getGenerator();
                long mult = ((Long) SF.get(sp)).longValue();
                int i = 0;
                while (true) {
                    if (((long) i) < mult) {
                        list.add(rn);
                        i++;
                    }
                }
            }
        }
        return list;
    }

    public static <C extends GcdRingElem<C> & Rational> List<RealAlgebraicNumber<C>> realAlgebraicNumbersField(GenPolynomial<C> f, BigRational eps) {
        RealRoots<C> rr = new RealRootsSturm();
        Map<GenPolynomial<C>, Long> SF = FactorFactory.getImplementation(f.ring.coFac).baseFactors(f);
        Set<GenPolynomial<C>> S = SF.keySet();
        List<RealAlgebraicNumber<C>> list = new ArrayList();
        for (GenPolynomial<C> sp : S) {
            for (Interval<C> I : rr.realRoots((GenPolynomial) sp, eps)) {
                RealAlgebraicRing<C> rar = new RealAlgebraicRing(sp, I, true);
                rar.setEps(eps);
                RealAlgebraicNumber<C> rn = rar.getGenerator();
                long mult = ((Long) SF.get(sp)).longValue();
                int i = 0;
                while (true) {
                    if (((long) i) < mult) {
                        list.add(rn);
                        i++;
                    }
                }
            }
        }
        return list;
    }

    public static <C extends GcdRingElem<C> & Rational> List<RealAlgebraicNumber<C>> realAlgebraicNumbersIrred(GenPolynomial<C> f) {
        RealRoots<C> rr = new RealRootsSturm();
        List<RealAlgebraicNumber<C>> list = new ArrayList();
        for (Interval<C> I : rr.realRoots(f)) {
            list.add(new RealAlgebraicRing(f, I, true).getGenerator());
        }
        return list;
    }

    public static <C extends GcdRingElem<C> & Rational> List<RealAlgebraicNumber<C>> realAlgebraicNumbersIrred(GenPolynomial<C> f, BigRational eps) {
        RealRoots<C> rr = new RealRootsSturm();
        List<RealAlgebraicNumber<C>> list = new ArrayList();
        for (Interval<C> I : rr.realRoots((GenPolynomial) f, eps)) {
            RealAlgebraicRing<C> rar = new RealAlgebraicRing(f, I, true);
            rar.setEps(eps);
            list.add(rar.getGenerator());
        }
        return list;
    }

    public static <C extends GcdRingElem<C> & Rational> boolean isRoot(GenPolynomial<C> f, ComplexAlgebraicNumber<C> r) {
        RingFactory cr = r.factory();
        return ((ComplexAlgebraicNumber) PolyUtil.evaluateMain(cr, PolyUtilRoot.convertToComplexCoefficients(new GenPolynomialRing(cr, f.factory()), f), (RingElem) r)).isZERO();
    }

    public static <C extends GcdRingElem<C> & Rational> boolean isRootComplex(GenPolynomial<Complex<C>> f, ComplexAlgebraicNumber<C> r) {
        RingFactory cr = r.factory();
        return ((ComplexAlgebraicNumber) PolyUtil.evaluateMain(cr, PolyUtilRoot.convertToComplexCoefficientsFromComplex(new GenPolynomialRing(cr, f.factory()), f), (RingElem) r)).isZERO();
    }

    public static <C extends GcdRingElem<C> & Rational> List<ComplexAlgebraicNumber<C>> complexAlgebraicNumbersComplex(GenPolynomial<Complex<C>> f) {
        ComplexRoots<C> cr = new ComplexRootsSturm(f.ring.coFac);
        Map<GenPolynomial<Complex<C>>, Long> SF = SquarefreeFactory.getImplementation(f.ring.coFac).squarefreeFactors((GenPolynomial) f);
        Set<GenPolynomial<Complex<C>>> S = SF.keySet();
        List<ComplexAlgebraicNumber<C>> list = new ArrayList();
        for (GenPolynomial<Complex<C>> sp : S) {
            for (Rectangle<C> I : cr.complexRoots(sp)) {
                ComplexAlgebraicNumber<C> cn = new ComplexAlgebraicRing(sp, I).getGenerator();
                long mult = ((Long) SF.get(sp)).longValue();
                int i = 0;
                while (true) {
                    if (((long) i) < mult) {
                        list.add(cn);
                        i++;
                    }
                }
            }
        }
        return list;
    }

    public static <C extends GcdRingElem<C> & Rational> List<ComplexAlgebraicNumber<C>> complexAlgebraicNumbersComplex(GenPolynomial<Complex<C>> f, BigRational eps) {
        ComplexRoots<C> cr = new ComplexRootsSturm(f.ring.coFac);
        Map<GenPolynomial<Complex<C>>, Long> SF = SquarefreeFactory.getImplementation(f.ring.coFac).squarefreeFactors((GenPolynomial) f);
        Set<GenPolynomial<Complex<C>>> S = SF.keySet();
        List<ComplexAlgebraicNumber<C>> list = new ArrayList();
        for (GenPolynomial<Complex<C>> sp : S) {
            for (Rectangle<C> I : cr.complexRoots(sp)) {
                Rectangle<C> Iv = I;
                try {
                    Iv = cr.complexRootRefinement(I, sp, eps);
                } catch (InvalidBoundaryException e) {
                    e.printStackTrace();
                }
                ComplexAlgebraicRing<C> car = new ComplexAlgebraicRing(sp, Iv);
                car.setEps(eps);
                ComplexAlgebraicNumber<C> cn = car.getGenerator();
                long mult = ((Long) SF.get(sp)).longValue();
                int i = 0;
                while (true) {
                    if (((long) i) < mult) {
                        list.add(cn);
                        i++;
                    }
                }
            }
        }
        return list;
    }

    public static <C extends GcdRingElem<C> & Rational> List<ComplexAlgebraicNumber<C>> complexAlgebraicNumbers(GenPolynomial<C> f) {
        if (f.ring.coFac instanceof Complex) {
            throw new IllegalArgumentException("f already has Complex coefficients " + f.ring);
        } else if (!(f.ring.coFac instanceof ComplexAlgebraicRing)) {
            return complexAlgebraicNumbersComplex(PolyUtil.complexFromAny(new GenPolynomialRing(new ComplexRing(f.ring.coFac), f.ring), f));
        } else {
            throw new UnsupportedOperationException("unsupported ComplexAlgebraicRing coefficients " + f.ring);
        }
    }

    public static <C extends GcdRingElem<C> & Rational> List<ComplexAlgebraicNumber<C>> complexAlgebraicNumbers(GenPolynomial<C> f, BigRational eps) {
        if (f.ring.coFac instanceof Complex) {
            throw new IllegalArgumentException("f already has Complex coefficients " + f.ring);
        } else if (!(f.ring.coFac instanceof ComplexAlgebraicRing)) {
            return complexAlgebraicNumbersComplex(PolyUtil.complexFromAny(new GenPolynomialRing(new ComplexRing(f.ring.coFac), f.ring), f), eps);
        } else {
            throw new UnsupportedOperationException("unsupported ComplexAlgebraicRing coefficients " + f.ring);
        }
    }
}
