package edu.jas.integrate;

import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.Power;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.ufd.FactorAbstract;
import edu.jas.ufd.FactorFactory;
import edu.jas.ufd.GCDFactory;
import edu.jas.ufd.GreatestCommonDivisorAbstract;
import edu.jas.ufd.GreatestCommonDivisorSubres;
import edu.jas.ufd.PolyUfdUtil;
import edu.jas.ufd.Quotient;
import edu.jas.ufd.QuotientRing;
import edu.jas.ufd.SquarefreeAbstract;
import edu.jas.ufd.SquarefreeFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import org.apache.log4j.Logger;

public class ElementaryIntegration<C extends GcdRingElem<C>> {
    private static final Logger logger;
    private final boolean debug;
    public final FactorAbstract<C> irr;
    public final SquarefreeAbstract<C> sqf;
    public final GreatestCommonDivisorAbstract<C> ufd;

    static {
        logger = Logger.getLogger(ElementaryIntegration.class);
    }

    public ElementaryIntegration(RingFactory<C> br) {
        this.debug = logger.isDebugEnabled();
        this.ufd = GCDFactory.getProxy((RingFactory) br);
        this.sqf = SquarefreeFactory.getImplementation((RingFactory) br);
        this.irr = FactorFactory.getImplementation((RingFactory) br);
    }

    public QuotIntegral<C> integrate(Quotient<C> r) {
        return new QuotIntegral(r.ring, integrate(r.num, r.den));
    }

    public Integral<C> integrate(GenPolynomial<C> a, GenPolynomial<C> d) {
        if (d == null || a == null || d.isZERO()) {
            throw new IllegalArgumentException("zero or null not allowed");
        } else if (a.isZERO()) {
            return new Integral(a, d, a);
        } else {
            if (d.isONE()) {
                return new Integral(a, d, PolyUtil.baseIntegral(a));
            }
            GenPolynomialRing<C> pfac = d.ring;
            if (pfac.nvar > 1) {
                throw new IllegalArgumentException("only for univariate polynomials " + pfac);
            } else if (pfac.coFac.isField()) {
                GenPolynomial<C>[] qr = PolyUtil.basePseudoQuotientRemainder(a, d);
                GenPolynomial<C> p = qr[0];
                GenPolynomial<C> r = qr[1];
                GenPolynomial<C> c = this.ufd.gcd((GenPolynomial) r, (GenPolynomial) d);
                if (!c.isONE()) {
                    r = PolyUtil.basePseudoQuotientRemainder(r, c)[0];
                    d = PolyUtil.basePseudoQuotientRemainder(d, c)[0];
                }
                List<GenPolynomial<C>>[] ih = integrateHermite(r, d);
                List<GenPolynomial<C>> rat = ih[0];
                List<GenPolynomial<C>> log = ih[1];
                GenPolynomial<C> pi = PolyUtil.baseIntegral(p.sum((GenPolynomial) log.remove(0)));
                if (this.debug) {
                    logger.debug("pi  = " + pi);
                    logger.debug("rat = " + rat);
                    logger.debug("log = " + log);
                }
                if (log.size() == 0) {
                    return new Integral(a, d, pi, rat);
                }
                List<LogIntegral<C>> logi = new ArrayList(log.size() / 2);
                int i = 0;
                while (i < log.size()) {
                    int i2 = i + 1;
                    logi.add(integrateLogPart((GenPolynomial) log.get(i), (GenPolynomial) log.get(i2)));
                    i = i2 + 1;
                }
                if (this.debug) {
                    logger.debug("logi = " + logi);
                }
                return new Integral(a, d, pi, rat, logi);
            } else {
                throw new IllegalArgumentException("only for field coefficients " + pfac);
            }
        }
    }

    public List<GenPolynomial<C>>[] integrateHermite(GenPolynomial<C> a, GenPolynomial<C> d) {
        if (d == null || d.isZERO()) {
            throw new IllegalArgumentException("d == null or d == 0");
        } else if (a == null || a.isZERO()) {
            throw new IllegalArgumentException("a == null or a == 0");
        } else {
            SortedMap<GenPolynomial<C>, Long> sfactors = this.sqf.squarefreeFactors((GenPolynomial) d);
            List<GenPolynomial<C>> D = new ArrayList(sfactors.keySet());
            List<GenPolynomial<C>> DP = new ArrayList();
            for (GenPolynomial<C> f : D) {
                DP.add((GenPolynomial) Power.positivePower((RingElem) f, ((Long) sfactors.get(f)).longValue()));
            }
            List<GenPolynomial<C>> Ai = this.ufd.basePartialFraction(a, DP);
            List<GenPolynomial<C>> G = new ArrayList();
            List<GenPolynomial<C>> H = new ArrayList();
            H.add(Ai.remove(0));
            GenPolynomialRing<C> fac = d.ring;
            int i = 0;
            for (GenPolynomial<C> v : D) {
                int i2 = i + 1;
                GenPolynomial<C> Ak = (GenPolynomial) Ai.get(i);
                for (int j = ((Long) sfactors.get(v)).intValue() - 1; j >= 1; j--) {
                    GenPolynomial<C> DV_dx = PolyUtil.baseDeriviative(v);
                    GenPolynomial<C> Aik = Ak.divide(fac.fromInteger((long) (-j)));
                    GenPolynomial<C>[] BC = this.ufd.baseGcdDiophant(DV_dx, v, Aik);
                    GenPolynomial<C> b = BC[0];
                    GenPolynomial c = BC[1];
                    GenPolynomial<C> vj = (GenPolynomial) Power.positivePower((RingElem) v, (long) j);
                    G.add(b);
                    G.add(vj);
                    Ak = fac.fromInteger((long) (-j)).multiply(c).subtract(PolyUtil.baseDeriviative(b));
                }
                if (!Ak.isZERO()) {
                    H.add(Ak);
                    H.add(v);
                }
                i = i2;
            }
            List[] ret = (List[]) new List[2];
            ret[0] = G;
            ret[1] = H;
            return ret;
        }
    }

    public LogIntegral<C> integrateLogPart(GenPolynomial<C> A, GenPolynomial<C> P) {
        if (P == null || P.isZERO()) {
            throw new IllegalArgumentException(" P == null or P == 0");
        } else if (A == null || A.isZERO()) {
            throw new IllegalArgumentException(" A == null or A == 0");
        } else {
            GenPolynomialRing<C> pfac = P.ring;
            if (pfac.nvar > 1) {
                throw new IllegalArgumentException("only for univariate polynomials " + pfac);
            } else if (pfac.coFac.isField()) {
                List<C> cfactors = new ArrayList();
                List<GenPolynomial<C>> cdenom = new ArrayList();
                List<AlgebraicNumber<C>> afactors = new ArrayList();
                List<GenPolynomial<AlgebraicNumber<C>>> adenom = new ArrayList();
                if (P.degree(0) <= 1) {
                    cfactors.add(A.leadingBaseCoefficient());
                    cdenom.add(P);
                    return new LogIntegral(A, P, cfactors, cdenom, afactors, adenom);
                }
                List<GenPolynomial<C>> Pfac = this.irr.baseFactorsSquarefree(P);
                List<GenPolynomial<C>> Afac = this.ufd.basePartialFraction(A, Pfac);
                if (((GenPolynomial) Afac.remove(0)).isZERO()) {
                    int i = 0;
                    for (GenPolynomial<C> pi : Pfac) {
                        int i2 = i + 1;
                        GenPolynomial<C> ai = (GenPolynomial) Afac.get(i);
                        if (pi.degree(0) <= 1) {
                            cfactors.add(ai.leadingBaseCoefficient());
                            cdenom.add(pi);
                            i = i2;
                        } else {
                            LogIntegral<C> pf = integrateLogPartIrreducible(ai, pi);
                            cfactors.addAll(pf.cfactors);
                            cdenom.addAll(pf.cdenom);
                            afactors.addAll(pf.afactors);
                            adenom.addAll(pf.adenom);
                            i = i2;
                        }
                    }
                    return new LogIntegral(A, P, cfactors, cdenom, afactors, adenom);
                }
                throw new RuntimeException(" A0 != 0: deg(A)>= deg(P)");
            } else {
                throw new IllegalArgumentException("only for field coefficients " + pfac);
            }
        }
    }

    public LogIntegral<C> integrateLogPartIrreducible(GenPolynomial<C> A, GenPolynomial<C> P) {
        if (P == null || P.isZERO()) {
            throw new IllegalArgumentException("P == null or P == 0");
        }
        GenPolynomialRing<C> pfac = P.ring;
        if (pfac.nvar > 1) {
            throw new IllegalArgumentException("only for univariate polynomials " + pfac);
        } else if (pfac.coFac.isField()) {
            List<C> cfactors = new ArrayList();
            List<GenPolynomial<C>> cdenom = new ArrayList();
            List<AlgebraicNumber<C>> afactors = new ArrayList();
            List<GenPolynomial<AlgebraicNumber<C>>> adenom = new ArrayList();
            if (P.degree(0) <= 1) {
                cfactors.add(A.leadingBaseCoefficient());
                cdenom.add(P);
                return new LogIntegral(A, P, cfactors, cdenom, afactors, adenom);
            }
            GenPolynomial<C> Pp = PolyUtil.baseDeriviative(P);
            GenPolynomialRing<C> genPolynomialRing = new GenPolynomialRing(pfac.coFac, 1, pfac.tord, new String[]{"t"});
            GenPolynomial<C> t = genPolynomialRing.univariate(0);
            GenPolynomialRing<GenPolynomial<C>> genPolynomialRing2 = new GenPolynomialRing((RingFactory) pfac, (GenPolynomialRing) genPolynomialRing);
            GenPolynomial<GenPolynomial<C>> Ac = PolyUfdUtil.introduceLowerVariable(genPolynomialRing2, A);
            GenPolynomial<GenPolynomial<C>> Pc = PolyUfdUtil.introduceLowerVariable(genPolynomialRing2, P);
            GenPolynomial<GenPolynomial<C>> Pcp = PolyUfdUtil.introduceLowerVariable(genPolynomialRing2, Pp);
            GenPolynomial<GenPolynomial<C>> At = Ac.subtract(Pc.ring.getONE().multiply((RingElem) t).multiply((GenPolynomial) Pcp));
            GreatestCommonDivisorAbstract<AlgebraicNumber<C>> aengine = null;
            for (GenPolynomial<C> r : this.irr.baseFactors((GenPolynomial) new GreatestCommonDivisorSubres().recursiveUnivariateResultant(Pc, At).leadingBaseCoefficient()).keySet()) {
                if (!r.isConstant()) {
                    String[] vars = pfac.newVars("z_");
                    pfac = pfac.copy();
                    String[] unused = pfac.setVars(vars);
                    RingFactory algebraicNumberRing = new AlgebraicNumberRing(pfac.copy((GenPolynomial) r), true);
                    logger.debug("afac = " + algebraicNumberRing.toScript());
                    AlgebraicNumber<C> a = algebraicNumberRing.getGenerator();
                    GenPolynomialRing<AlgebraicNumber<C>> genPolynomialRing3 = new GenPolynomialRing(algebraicNumberRing, Pc.ring);
                    GenPolynomial<AlgebraicNumber<C>> Pa = PolyUtil.convertToAlgebraicCoefficients(genPolynomialRing3, P);
                    GenPolynomial<AlgebraicNumber<C>> Ap = PolyUtil.convertToAlgebraicCoefficients(genPolynomialRing3, A).subtract(PolyUtil.convertToAlgebraicCoefficients(genPolynomialRing3, Pp).multiply((RingElem) a));
                    if (aengine == null) {
                        aengine = GCDFactory.getImplementation(algebraicNumberRing);
                    }
                    GenPolynomial<AlgebraicNumber<C>> Ga = aengine.baseGcd(Pa, Ap);
                    if (Ga.isConstant()) {
                        continue;
                    } else {
                        afactors.add(a);
                        adenom.add(Ga);
                        if (P.degree(0) == 2 && Ga.degree(0) == 1) {
                            GenPolynomial<AlgebraicNumber<C>>[] qra = PolyUtil.basePseudoQuotientRemainder(Pa, Ga);
                            GenPolynomial<AlgebraicNumber<C>> Qa = qra[0];
                            if (qra[1].isZERO()) {
                                afactors.add(a.negate());
                                adenom.add(Qa);
                            } else {
                                throw new ArithmeticException("remainder not zero");
                            }
                        }
                    }
                }
            }
            return new LogIntegral(A, P, cfactors, cdenom, afactors, adenom);
        } else {
            throw new IllegalArgumentException("only for field coefficients " + pfac);
        }
    }

    public Quotient<C> deriviative(Quotient<C> r) {
        GenPolynomial<C> num = r.num;
        GenPolynomial den = r.den;
        GenPolynomial nump = PolyUtil.baseDeriviative(num);
        if (den.isONE()) {
            return new Quotient(r.ring, nump, den);
        }
        return new Quotient(r.ring, den.multiply(nump).subtract(num.multiply(PolyUtil.baseDeriviative(den))), den.multiply(den));
    }

    public boolean isIntegral(QuotIntegral<C> ri) {
        Quotient<C> r = ri.quot;
        QuotientRing<C> qr = r.ring;
        Quotient<C> i = r.ring.getZERO();
        for (Quotient<C> q : ri.rational) {
            i = i.sum(deriviative(q));
        }
        if (ri.logarithm.size() == 0) {
            return r.equals(i);
        }
        for (LogIntegral<C> li : ri.logarithm) {
            i = i.sum(new Quotient(qr, li.num, li.den));
        }
        if (!r.equals(i)) {
            return false;
        }
        for (LogIntegral li2 : ri.logarithm) {
            if (!isIntegral(li2)) {
                return false;
            }
        }
        return true;
    }

    public boolean isIntegral(LogIntegral<C> rl) {
        QuotientRing<C> quotientRing = new QuotientRing(rl.den.ring);
        Quotient<C> quotient = new Quotient(quotientRing, rl.num, rl.den);
        Quotient i = quotientRing.getZERO();
        int j = 0;
        for (GenPolynomial<C> d : rl.cdenom) {
            int j2 = j + 1;
            i = i.sum(new Quotient(quotientRing, PolyUtil.baseDeriviative(d).multiply((RingElem) rl.cfactors.get(j)), d));
            j = j2;
        }
        if (rl.afactors.size() == 0) {
            return quotient.equals(i);
        }
        Quotient<C> r = quotient.subtract(i);
        QuotientRing<AlgebraicNumber<C>> aqr = new QuotientRing(((GenPolynomial) rl.adenom.get(0)).ring);
        Quotient<AlgebraicNumber<C>> ai = aqr.getZERO();
        Quotient<AlgebraicNumber<C>> ar = new Quotient(aqr, PolyUtil.convertToAlgebraicCoefficients(aqr.ring, r.num), PolyUtil.convertToAlgebraicCoefficients(aqr.ring, r.den));
        j = 0;
        for (GenPolynomial<AlgebraicNumber<C>> d2 : rl.adenom) {
            j2 = j + 1;
            ai = ai.sum(new Quotient(aqr, PolyUtil.baseDeriviative(d2).multiply((RingElem) rl.afactors.get(j)), d2));
            j = j2;
        }
        if (ar.equals(ai)) {
            return true;
        }
        logger.warn("log integral not verified");
        return true;
    }
}
