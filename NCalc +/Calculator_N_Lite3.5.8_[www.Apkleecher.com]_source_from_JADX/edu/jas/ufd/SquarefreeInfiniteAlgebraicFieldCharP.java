package edu.jas.ufd;

import edu.jas.gb.GroebnerBaseSeq;
import edu.jas.gb.ReductionSeq;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.Monomial;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.Power;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public class SquarefreeInfiniteAlgebraicFieldCharP<C extends GcdRingElem<C>> extends SquarefreeFieldCharP<AlgebraicNumber<C>> {
    private static final Logger logger;
    protected final SquarefreeAbstract<C> aengine;

    static {
        logger = Logger.getLogger(SquarefreeInfiniteAlgebraicFieldCharP.class);
    }

    public SquarefreeInfiniteAlgebraicFieldCharP(RingFactory<AlgebraicNumber<C>> fac) {
        super(fac);
        if (fac.isFinite()) {
            throw new IllegalArgumentException("fac must be in-finite");
        }
        this.aengine = SquarefreeFactory.getImplementation(((AlgebraicNumberRing) fac).ring);
    }

    public SortedMap<AlgebraicNumber<C>, Long> squarefreeFactors(AlgebraicNumber<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P == null");
        }
        SortedMap<AlgebraicNumber<C>, Long> factors = new TreeMap();
        if (!P.isZERO()) {
            if (P.isONE()) {
                factors.put(P, Long.valueOf(1));
            } else {
                GenPolynomial an = P.val;
                AlgebraicNumberRing<C> pfac = P.ring;
                if (!an.isONE()) {
                    for (Entry<GenPolynomial<C>, Long> me : this.aengine.squarefreeFactors(an).entrySet()) {
                        factors.put(new AlgebraicNumber(pfac, (GenPolynomial) me.getKey()), me.getValue());
                    }
                }
                if (factors.size() == 0) {
                    factors.put(P, Long.valueOf(1));
                }
            }
        }
        return factors;
    }

    public SortedMap<AlgebraicNumber<C>, Long> rootCharacteristic(AlgebraicNumber<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P == null");
        }
        BigInteger c = P.ring.characteristic();
        if (c.signum() == 0) {
            return null;
        }
        SortedMap<AlgebraicNumber<C>, Long> root = new TreeMap();
        if (P.isZERO()) {
            return root;
        }
        if (P.isONE()) {
            root.put(P, Long.valueOf(1));
            return root;
        }
        Iterator i$;
        ExpVector f;
        Iterator i$2;
        ExpVector e;
        C cc;
        GenPolynomial<C> car;
        int[] v;
        C tc;
        RingElem bc;
        long ll;
        AlgebraicNumber<C> algebraicNumber;
        RingFactory afac = P.ring;
        long deg = afac.modul.degree(0);
        int d = (int) deg;
        GenPolynomialRing<AlgebraicNumber<C>> genPolynomialRing = new GenPolynomialRing(afac, d, GenPolynomialRing.newVars("c", d));
        List<GenPolynomial<AlgebraicNumber<C>>> uv = genPolynomialRing.univariateList();
        GenPolynomial<AlgebraicNumber<C>> cp = genPolynomialRing.getZERO();
        GenPolynomialRing<C> apfac = afac.ring;
        long i = 0;
        for (GenPolynomial<AlgebraicNumber<C>> pa : uv) {
            long i2 = i + 1;
            cp = cp.sum((GenPolynomial) pa.multiply((RingElem) new AlgebraicNumber(afac, apfac.univariate(0, i))));
            i = i2;
        }
        GenPolynomial<AlgebraicNumber<C>> cpp = (GenPolynomial) Power.positivePower((RingElem) cp, c);
        if (logger.isInfoEnabled()) {
            logger.info("cp   = " + cp);
            logger.info("cp^p = " + cpp);
            logger.info("P    = " + P);
        }
        GenPolynomialRing<C> genPolynomialRing2 = new GenPolynomialRing(apfac.coFac, (GenPolynomialRing) genPolynomialRing);
        List<GenPolynomial<C>> gl = new ArrayList();
        if (deg == c.longValue()) {
            if (afac.modul.length() == 2) {
                logger.info("deg(" + deg + ") == char_p(" + c.longValue() + ")");
                i$ = cpp.iterator();
                while (i$.hasNext()) {
                    Monomial<AlgebraicNumber<C>> m = (Monomial) i$.next();
                    f = m.e;
                    i$2 = m.c.val.iterator();
                    while (i$2.hasNext()) {
                        Monomial<C> ma = (Monomial) i$2.next();
                        e = ma.e;
                        cc = (GcdRingElem) ma.c;
                        C pc = (GcdRingElem) P.val.coefficient(e);
                        C cc1 = (GcdRingElem) ((RingFactory) pc.factory()).getONE();
                        C pc1 = (GcdRingElem) ((RingFactory) pc.factory()).getZERO();
                        if ((cc instanceof AlgebraicNumber) && (pc instanceof AlgebraicNumber)) {
                            throw new UnsupportedOperationException("case multiple algebraic extensions not implemented");
                        } else if ((cc instanceof Quotient) && (pc instanceof Quotient)) {
                            Quotient<C> ccp = (Quotient) cc;
                            Quotient<C> pcp = (Quotient) pc;
                            if (pcp.isConstant()) {
                                throw new ArithmeticException("finite field not allowed here " + afac.toScript());
                            }
                            if (ccp.divide((Quotient) pcp).isConstant()) {
                                cc1 = cc;
                                pc1 = pc;
                            }
                            gl.add(new GenPolynomial(genPolynomialRing2, cc1, f).subtract((RingElem) pc1));
                        }
                    }
                }
                if (logger.isInfoEnabled()) {
                    logger.info("equations = " + gl);
                }
                gl = new ReductionSeq().irreducibleSet(gl);
                if (new GroebnerBaseSeq().commonZeroTest(gl) < 0) {
                    return null;
                }
                if (logger.isInfoEnabled()) {
                    logger.info("solution = " + gl);
                }
                car = apfac.getZERO();
                for (GenPolynomial<C> pl : gl) {
                    if (pl.length() <= 1) {
                        if (pl.length() <= 2) {
                            throw new IllegalArgumentException("dim > 0 not implemented " + pl);
                        }
                        e = pl.leadingExpVector();
                        v = e.dependencyOnVariables();
                        if (!(v == null || v.length == 0)) {
                            GenPolynomial<C> ca = apfac.univariate(0, (deg - 1) - ((long) v[0]));
                            tc = (GcdRingElem) ((GcdRingElem) pl.trailingBaseCoefficient()).negate();
                            if (e.maxDeg() == c.longValue()) {
                                SortedMap<C, Long> br = this.aengine.squarefreeFactors((GcdRingElem) tc);
                                if (br != null && br.size() > 0) {
                                    cc = (GcdRingElem) apfac.coFac.getONE();
                                    for (Entry<C, Long> me : br.entrySet()) {
                                        bc = (GcdRingElem) me.getKey();
                                        ll = ((Long) me.getValue()).longValue();
                                        if (ll % c.longValue() != 0) {
                                            cc = (GcdRingElem) cc.multiply(Power.positivePower(bc, ll / c.longValue()));
                                        } else {
                                            cc = (GcdRingElem) cc.multiply(bc);
                                        }
                                    }
                                    tc = cc;
                                }
                            }
                            car = car.sum(ca.multiply((RingElem) tc));
                        }
                    }
                }
                algebraicNumber = new AlgebraicNumber(afac, car);
                if (logger.isInfoEnabled()) {
                    logger.info("solution AN = " + algebraicNumber);
                }
                root.put(algebraicNumber, Long.valueOf(1));
                return root;
            }
        }
        i$ = cpp.iterator();
        while (i$.hasNext()) {
            m = (Monomial) i$.next();
            f = m.e;
            i$2 = ((AlgebraicNumber) m.c).val.iterator();
            while (i$2.hasNext()) {
                ma = (Monomial) i$2.next();
                e = ma.e;
                GcdRingElem cc2 = (GcdRingElem) ma.c;
                GcdRingElem pc2 = (GcdRingElem) P.val.coefficient(e);
                gl.add(new GenPolynomial(genPolynomialRing2, cc2, f).subtract((RingElem) pc2));
            }
        }
        if (logger.isInfoEnabled()) {
            logger.info("equations = " + gl);
        }
        gl = new ReductionSeq().irreducibleSet(gl);
        if (new GroebnerBaseSeq().commonZeroTest(gl) < 0) {
            return null;
        }
        if (logger.isInfoEnabled()) {
            logger.info("solution = " + gl);
        }
        car = apfac.getZERO();
        for (GenPolynomial<C> pl2 : gl) {
            if (pl2.length() <= 1) {
                if (pl2.length() <= 2) {
                    e = pl2.leadingExpVector();
                    v = e.dependencyOnVariables();
                    GenPolynomial<C> ca2 = apfac.univariate(0, (deg - 1) - ((long) v[0]));
                    tc = (GcdRingElem) ((GcdRingElem) pl2.trailingBaseCoefficient()).negate();
                    if (e.maxDeg() == c.longValue()) {
                        SortedMap<C, Long> br2 = this.aengine.squarefreeFactors((GcdRingElem) tc);
                        cc = (GcdRingElem) apfac.coFac.getONE();
                        for (Entry<C, Long> me2 : br2.entrySet()) {
                            bc = (GcdRingElem) me2.getKey();
                            ll = ((Long) me2.getValue()).longValue();
                            if (ll % c.longValue() != 0) {
                                cc = (GcdRingElem) cc.multiply(bc);
                            } else {
                                cc = (GcdRingElem) cc.multiply(Power.positivePower(bc, ll / c.longValue()));
                            }
                        }
                        tc = cc;
                    }
                    car = car.sum(ca2.multiply((RingElem) tc));
                } else {
                    throw new IllegalArgumentException("dim > 0 not implemented " + pl2);
                }
            }
        }
        algebraicNumber = new AlgebraicNumber(afac, car);
        if (logger.isInfoEnabled()) {
            logger.info("solution AN = " + algebraicNumber);
        }
        root.put(algebraicNumber, Long.valueOf(1));
        return root;
    }

    public GenPolynomial<AlgebraicNumber<C>> rootCharacteristic(GenPolynomial<AlgebraicNumber<C>> P) {
        if (P == null || P.isZERO()) {
            return P;
        }
        GenPolynomialRing<AlgebraicNumber<C>> pfac = P.ring;
        int i = pfac.nvar;
        if (r0 > 1) {
            GenPolynomial Prc = recursiveUnivariateRootCharacteristic(PolyUtil.recursive(new GenPolynomialRing(pfac.contract(1), 1), (GenPolynomial) P));
            if (Prc == null) {
                return null;
            }
            return PolyUtil.distribute((GenPolynomialRing) pfac, Prc);
        }
        RingFactory<AlgebraicNumber<C>> rf = pfac.coFac;
        if (rf.characteristic().signum() != 1) {
            throw new IllegalArgumentException(P.getClass().getName() + " only for ModInteger polynomials " + rf);
        }
        long mp = rf.characteristic().longValue();
        GenPolynomial<AlgebraicNumber<C>> d = pfac.getZERO().copy();
        Iterator it = P.iterator();
        while (it.hasNext()) {
            Monomial<AlgebraicNumber<C>> m = (Monomial) it.next();
            long fl = m.e.getVal(0);
            if (fl % mp != 0) {
                return null;
            }
            fl /= mp;
            SortedMap<AlgebraicNumber<C>, Long> sm = rootCharacteristic((AlgebraicNumber) m.c);
            if (sm == null) {
                return null;
            }
            if (logger.isInfoEnabled()) {
                logger.info("sm_alg,root = " + sm);
            }
            AlgebraicNumber<C> r = (AlgebraicNumber) rf.getONE();
            for (Entry<AlgebraicNumber<C>, Long> me : sm.entrySet()) {
                AlgebraicNumber<C> rp = (AlgebraicNumber) me.getKey();
                long gl = ((Long) me.getValue()).longValue();
                if (gl > 1) {
                    rp = (AlgebraicNumber) Power.positivePower((RingElem) rp, gl);
                }
                r = r.multiply((AlgebraicNumber) rp);
            }
            d.doPutToMap(ExpVector.create(1, 0, fl), r);
        }
        logger.info("sm_alg,root,d = " + d);
        return d;
    }

    public GenPolynomial<AlgebraicNumber<C>> baseRootCharacteristic(GenPolynomial<AlgebraicNumber<C>> P) {
        if (P == null || P.isZERO()) {
            return P;
        }
        GenPolynomialRing<AlgebraicNumber<C>> pfac = P.ring;
        int i = pfac.nvar;
        if (r0 > 1) {
            throw new IllegalArgumentException(P.getClass().getName() + " only for univariate polynomials");
        }
        RingFactory<AlgebraicNumber<C>> rf = pfac.coFac;
        if (rf.characteristic().signum() != 1) {
            throw new IllegalArgumentException(P.getClass().getName() + " only for char p > 0 " + rf);
        }
        long mp = rf.characteristic().longValue();
        GenPolynomial<AlgebraicNumber<C>> d = pfac.getZERO().copy();
        Iterator it = P.iterator();
        while (it.hasNext()) {
            Monomial<AlgebraicNumber<C>> m = (Monomial) it.next();
            long fl = m.e.getVal(0);
            if (fl % mp != 0) {
                return null;
            }
            fl /= mp;
            SortedMap<AlgebraicNumber<C>, Long> sm = rootCharacteristic((AlgebraicNumber) m.c);
            if (sm == null) {
                return null;
            }
            if (logger.isInfoEnabled()) {
                logger.info("sm_alg,base,root = " + sm);
            }
            AlgebraicNumber<C> r = (AlgebraicNumber) rf.getONE();
            for (Entry<AlgebraicNumber<C>, Long> me : sm.entrySet()) {
                AlgebraicNumber<C> rp = (AlgebraicNumber) me.getKey();
                long gl = ((Long) me.getValue()).longValue();
                AlgebraicNumber re = rp;
                if (gl > 1) {
                    re = (AlgebraicNumber) Power.positivePower((RingElem) rp, gl);
                }
                r = r.multiply(re);
            }
            d.doPutToMap(ExpVector.create(1, 0, fl), r);
        }
        if (!logger.isInfoEnabled()) {
            return d;
        }
        logger.info("sm_alg,base,d = " + d);
        return d;
    }

    public GenPolynomial<GenPolynomial<AlgebraicNumber<C>>> recursiveUnivariateRootCharacteristic(GenPolynomial<GenPolynomial<AlgebraicNumber<C>>> P) {
        if (P == null || P.isZERO()) {
            return P;
        }
        GenPolynomialRing<GenPolynomial<AlgebraicNumber<C>>> pfac = P.ring;
        if (pfac.nvar > 1) {
            throw new IllegalArgumentException(P.getClass().getName() + " only for univariate recursive polynomials");
        }
        RingFactory<GenPolynomial<AlgebraicNumber<C>>> rf = pfac.coFac;
        if (rf.characteristic().signum() != 1) {
            throw new IllegalArgumentException(P.getClass().getName() + " only for char p > 0 " + rf);
        }
        long mp = rf.characteristic().longValue();
        GenPolynomial<GenPolynomial<AlgebraicNumber<C>>> d = pfac.getZERO().copy();
        Iterator i$ = P.iterator();
        while (i$.hasNext()) {
            Monomial<GenPolynomial<AlgebraicNumber<C>>> m = (Monomial) i$.next();
            long fl = m.e.getVal(0);
            if (fl % mp != 0) {
                return null;
            }
            fl /= mp;
            GenPolynomial<AlgebraicNumber<C>> r = rootCharacteristic((GenPolynomial) m.c);
            if (r == null) {
                return null;
            }
            d.doPutToMap(ExpVector.create(1, 0, fl), r);
        }
        return d;
    }
}
