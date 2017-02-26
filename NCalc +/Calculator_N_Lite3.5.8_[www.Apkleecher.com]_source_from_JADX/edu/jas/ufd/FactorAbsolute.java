package edu.jas.ufd;

import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.Power;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public abstract class FactorAbsolute<C extends GcdRingElem<C>> extends FactorAbstract<C> {
    private static final Logger logger;
    private final boolean debug;

    static {
        logger = Logger.getLogger(FactorAbsolute.class);
    }

    protected FactorAbsolute() {
        this.debug = logger.isDebugEnabled();
        throw new IllegalArgumentException("don't use this constructor");
    }

    public FactorAbsolute(RingFactory<C> cfac) {
        super(cfac);
        this.debug = logger.isDebugEnabled();
    }

    public String toString() {
        return getClass().getName();
    }

    public boolean isAbsoluteIrreducible(GenPolynomial<C> P) {
        if (!isIrreducible(P)) {
            return false;
        }
        Factors<C> F = factorsAbsoluteIrreducible(P);
        if (F.afac == null) {
            return true;
        }
        if (F.afactors.size() > 2) {
            return false;
        }
        boolean cnst = false;
        for (GenPolynomial<AlgebraicNumber<C>> p : F.afactors) {
            if (p.isConstant()) {
                cnst = true;
            }
        }
        return cnst;
    }

    public FactorsMap<C> baseFactorsAbsolute(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P == null");
        }
        SortedMap<GenPolynomial<C>, Long> factors = new TreeMap();
        if (P.isZERO()) {
            return new FactorsMap(P, factors);
        }
        GenPolynomialRing<C> pfac = P.ring;
        if (pfac.nvar > 1) {
            throw new IllegalArgumentException("only for univariate polynomials");
        } else if (!pfac.coFac.isField()) {
            throw new IllegalArgumentException("only for field coefficients");
        } else if (P.degree(0) <= 1) {
            factors.put(P, Long.valueOf(1));
            return new FactorsMap(P, factors);
        } else {
            SortedMap<GenPolynomial<C>, Long> facs = baseFactors(P);
            if (!this.debug || isFactorization((GenPolynomial) P, (SortedMap) facs)) {
                if (logger.isInfoEnabled()) {
                    logger.info("all K factors = " + facs);
                }
                SortedMap<Factors<C>, Long> afactors = new TreeMap();
                for (Entry<GenPolynomial<C>, Long> me : facs.entrySet()) {
                    GenPolynomial<C> p = (GenPolynomial) me.getKey();
                    Long e = (Long) me.getValue();
                    if (p.degree(0) <= 1) {
                        factors.put(p, e);
                    } else {
                        afactors.put(baseFactorsAbsoluteIrreducible(p), e);
                    }
                }
                return new FactorsMap(P, factors, afactors);
            }
            System.out.println("facs   = " + facs);
            throw new ArithmeticException("isFactorization = false");
        }
    }

    public FactorsList<C> baseFactorsAbsoluteSquarefree(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P == null");
        }
        List<GenPolynomial<C>> factors = new ArrayList();
        if (P.isZERO()) {
            return new FactorsList(P, factors);
        }
        GenPolynomialRing<C> pfac = P.ring;
        if (pfac.nvar > 1) {
            throw new IllegalArgumentException("only for univariate polynomials");
        } else if (!pfac.coFac.isField()) {
            throw new IllegalArgumentException("only for field coefficients");
        } else if (P.degree(0) <= 1) {
            factors.add(P);
            return new FactorsList(P, factors);
        } else {
            List<GenPolynomial<C>> facs = baseFactorsSquarefree(P);
            if (!this.debug || isFactorization((GenPolynomial) P, (List) facs)) {
                if (logger.isInfoEnabled()) {
                    logger.info("all K factors = " + facs);
                }
                List<Factors<C>> afactors = new ArrayList();
                for (GenPolynomial<C> p : facs) {
                    if (p.degree(0) <= 1) {
                        factors.add(p);
                    } else {
                        Factors<C> afacs = baseFactorsAbsoluteIrreducible(p);
                        if (logger.isInfoEnabled()) {
                            logger.info("K(alpha) factors = " + afacs);
                        }
                        afactors.add(afacs);
                    }
                }
                return new FactorsList(P, factors, afactors);
            }
            throw new ArithmeticException("isFactorization = false");
        }
    }

    public Factors<C> baseFactorsAbsoluteIrreducible(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P == null");
        } else if (P.isZERO()) {
            return new Factors(P);
        } else {
            GenPolynomialRing<C> pfac = P.ring;
            if (pfac.nvar > 1) {
                throw new IllegalArgumentException("only for univariate polynomials");
            } else if (!pfac.coFac.isField()) {
                throw new IllegalArgumentException("only for field coefficients");
            } else if (P.degree(0) <= 1) {
                return new Factors(P);
            } else {
                String[] vars = pfac.newVars("z_");
                pfac = pfac.copy();
                vars = pfac.setVars(vars);
                GenPolynomial<C> aP = pfac.copy((GenPolynomial) P);
                AlgebraicNumberRing afac = new AlgebraicNumberRing(aP, true);
                if (logger.isInfoEnabled()) {
                    logger.info("K(alpha) = " + afac);
                    String toScript = afac.toScript();
                    logger.info("K(alpha) = " + r18);
                }
                GenPolynomial<AlgebraicNumber<C>> Pa = PolyUtil.convertToAlgebraicCoefficients(new GenPolynomialRing(afac, aP.ring.nvar, aP.ring.tord, vars), P);
                if (logger.isInfoEnabled()) {
                    logger.info("P over K(alpha) = " + Pa);
                }
                List<GenPolynomial<AlgebraicNumber<C>>> factors = FactorFactory.getImplementation(afac).baseFactorsSquarefree(Pa);
                if (logger.isInfoEnabled()) {
                    logger.info("factors over K(alpha) = " + factors);
                }
                List<GenPolynomial<AlgebraicNumber<C>>> faca = new ArrayList(factors.size());
                List<Factors<AlgebraicNumber<C>>> facar = new ArrayList();
                for (GenPolynomial<AlgebraicNumber<C>> fi : factors) {
                    if (fi.degree(0) <= 1) {
                        faca.add(fi);
                    } else {
                        facar.add(((FactorAbsolute) FactorFactory.getImplementation(afac)).baseFactorsAbsoluteIrreducible(fi));
                    }
                }
                if (facar.size() == 0) {
                    facar = null;
                }
                return new Factors(P, afac, Pa, faca, facar);
            }
        }
    }

    public PartialFraction<C> baseAlgebraicPartialFraction(GenPolynomial<C> A, GenPolynomial<C> P) {
        if (P == null || P.isZERO()) {
            throw new IllegalArgumentException(" P == null or P == 0");
        } else if (A == null || A.isZERO()) {
            throw new IllegalArgumentException(" A == null or A == 0");
        } else {
            GenPolynomialRing<C> pfac = P.ring;
            if (pfac.nvar > 1) {
                throw new IllegalArgumentException("only for univariate polynomials");
            } else if (pfac.coFac.isField()) {
                List<C> cfactors = new ArrayList();
                List<GenPolynomial<C>> cdenom = new ArrayList();
                List<AlgebraicNumber<C>> afactors = new ArrayList();
                List<GenPolynomial<AlgebraicNumber<C>>> adenom = new ArrayList();
                if (P.degree(0) <= 1) {
                    cfactors.add(A.leadingBaseCoefficient());
                    cdenom.add(P);
                    return new PartialFraction(A, P, cfactors, cdenom, afactors, adenom);
                }
                List<GenPolynomial<C>> Pfac = baseFactorsSquarefree(P);
                List<GenPolynomial<C>> Afac = this.engine.basePartialFraction(A, Pfac);
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
                            PartialFraction<C> pf = baseAlgebraicPartialFractionIrreducibleAbsolute(ai, pi);
                            cfactors.addAll(pf.cfactors);
                            cdenom.addAll(pf.cdenom);
                            afactors.addAll(pf.afactors);
                            adenom.addAll(pf.adenom);
                            i = i2;
                        }
                    }
                    return new PartialFraction(A, P, cfactors, cdenom, afactors, adenom);
                }
                throw new ArithmeticException(" A0 != 0: deg(A)>= deg(P)");
            } else {
                throw new IllegalArgumentException("only for field coefficients");
            }
        }
    }

    @Deprecated
    public PartialFraction<C> baseAlgebraicPartialFractionIrreducible(GenPolynomial<C> A, GenPolynomial<C> P) {
        if (P == null || P.isZERO()) {
            throw new IllegalArgumentException(" P == null or P == 0");
        }
        GenPolynomialRing<C> pfac = P.ring;
        if (pfac.nvar > 1) {
            throw new IllegalArgumentException("only for univariate polynomials");
        } else if (pfac.coFac.isField()) {
            List<C> cfactors = new ArrayList();
            List<GenPolynomial<C>> cdenom = new ArrayList();
            List<AlgebraicNumber<C>> afactors = new ArrayList();
            List<GenPolynomial<AlgebraicNumber<C>>> adenom = new ArrayList();
            if (P.degree(0) <= 1) {
                cfactors.add(A.leadingBaseCoefficient());
                cdenom.add(P);
                return new PartialFraction(A, P, cfactors, cdenom, afactors, adenom);
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
            for (GenPolynomial<C> r : baseFactors((GenPolynomial) new GreatestCommonDivisorSubres().recursiveUnivariateResultant(Pc, At).leadingBaseCoefficient()).keySet()) {
                if (!r.isConstant()) {
                    String[] vars = pfac.newVars("z_");
                    pfac = pfac.copy();
                    pfac.setVars(vars);
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
            return new PartialFraction(A, P, cfactors, cdenom, afactors, adenom);
        } else {
            throw new IllegalArgumentException("only for field coefficients");
        }
    }

    public PartialFraction<C> baseAlgebraicPartialFractionIrreducibleAbsolute(GenPolynomial<C> A, GenPolynomial<C> P) {
        if (P == null || P.isZERO()) {
            throw new IllegalArgumentException(" P == null or P == 0");
        }
        GenPolynomialRing<C> pfac = P.ring;
        if (pfac.nvar > 1) {
            throw new IllegalArgumentException("only for univariate polynomials");
        } else if (pfac.coFac.isField()) {
            List<C> cfactors = new ArrayList();
            List<GenPolynomial<C>> cdenom = new ArrayList();
            List<AlgebraicNumber<C>> afactors = new ArrayList();
            List<GenPolynomial<AlgebraicNumber<C>>> adenom = new ArrayList();
            if (P.degree(0) <= 1) {
                cfactors.add(A.leadingBaseCoefficient());
                cdenom.add(P);
                return new PartialFraction(A, P, cfactors, cdenom, afactors, adenom);
            }
            Factors<C> afacs = factorsAbsoluteIrreducible(P);
            List<GenPolynomial<AlgebraicNumber<C>>> fact = afacs.getFactors();
            List<GenPolynomial<AlgebraicNumber<C>>> numers = GCDFactory.getProxy(afacs.afac).basePartialFraction(PolyUtil.convertToRecAlgebraicCoefficients(1, afacs.apoly.ring, A), fact);
            if (((GenPolynomial) numers.remove(0)).isZERO()) {
                int i = 0;
                for (GenPolynomial<AlgebraicNumber<C>> fa : fact) {
                    int i2 = i + 1;
                    GenPolynomial<AlgebraicNumber<C>> an = (GenPolynomial) numers.get(i);
                    if (fa.degree(0) <= 1) {
                        afactors.add(an.leadingBaseCoefficient());
                        adenom.add(fa);
                        i = i2;
                    } else {
                        System.out.println("fa = " + fa);
                        Factors<AlgebraicNumber<C>> faf = afacs.getFactor(fa);
                        System.out.println("faf = " + faf);
                        List<GenPolynomial<AlgebraicNumber<AlgebraicNumber<C>>>> fafact = faf.getFactors();
                        List<GenPolynomial<AlgebraicNumber<AlgebraicNumber<C>>>> anumers = GCDFactory.getImplementation(faf.afac).basePartialFraction(PolyUtil.convertToRecAlgebraicCoefficients(1, faf.apoly.ring, an), fafact);
                        System.out.println("algeb part frac = " + anumers);
                        if (((GenPolynomial) anumers.remove(0)).isZERO()) {
                            int k = 0;
                            for (GenPolynomial<AlgebraicNumber<AlgebraicNumber<C>>> faa : fafact) {
                                int k2 = k + 1;
                                GenPolynomial<AlgebraicNumber<AlgebraicNumber<C>>> ana = (GenPolynomial) anumers.get(k);
                                System.out.println("faa = " + faa);
                                System.out.println("ana = " + ana);
                                if (faa.degree(0) > 1) {
                                    throw new ArithmeticException(" faa not linear");
                                }
                                GenPolynomial<AlgebraicNumber<C>> faa1 = faa;
                                afactors.add(ana.leadingBaseCoefficient());
                                adenom.add(faa1);
                                k = k2;
                            }
                            i = i2;
                        } else {
                            throw new ArithmeticException(" A0 != 0: deg(A)>= deg(P)");
                        }
                    }
                }
                return new PartialFraction(A, P, cfactors, cdenom, afactors, adenom);
            }
            throw new ArithmeticException(" A0 != 0: deg(A)>= deg(P)");
        } else {
            throw new IllegalArgumentException("only for field coefficients");
        }
    }

    public FactorsMap<C> factorsAbsolute(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P == null");
        }
        SortedMap<GenPolynomial<C>, Long> factors = new TreeMap();
        if (P.isZERO()) {
            return new FactorsMap(P, factors);
        }
        GenPolynomialRing<C> pfac = P.ring;
        if (pfac.nvar <= 1) {
            return baseFactorsAbsolute(P);
        }
        if (!pfac.coFac.isField()) {
            throw new IllegalArgumentException("only for field coefficients");
        } else if (P.degree() <= 1) {
            factors.put(P, Long.valueOf(1));
            return new FactorsMap(P, factors);
        } else {
            SortedMap<GenPolynomial<C>, Long> facs = factors(P);
            if (!this.debug || isFactorization((GenPolynomial) P, (SortedMap) facs)) {
                if (logger.isInfoEnabled()) {
                    logger.info("all K factors = " + facs);
                }
                SortedMap<Factors<C>, Long> afactors = new TreeMap();
                for (Entry<GenPolynomial<C>, Long> me : facs.entrySet()) {
                    GenPolynomial<C> p = (GenPolynomial) me.getKey();
                    Long e = (Long) me.getValue();
                    if (p.degree() <= 1) {
                        factors.put(p, e);
                    } else {
                        Factors<C> afacs = factorsAbsoluteIrreducible(p);
                        if (afacs.afac == null) {
                            factors.put(p, e);
                        } else {
                            afactors.put(afacs, e);
                        }
                    }
                }
                return new FactorsMap(P, factors, afactors);
            }
            throw new ArithmeticException("isFactorization = false");
        }
    }

    public FactorsList<C> factorsAbsoluteSquarefree(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P == null");
        }
        List<GenPolynomial<C>> factors = new ArrayList();
        if (P.isZERO()) {
            return new FactorsList(P, factors);
        }
        GenPolynomialRing<C> pfac = P.ring;
        if (pfac.nvar <= 1) {
            return baseFactorsAbsoluteSquarefree(P);
        }
        if (!pfac.coFac.isField()) {
            throw new IllegalArgumentException("only for field coefficients");
        } else if (P.degree() <= 1) {
            factors.add(P);
            return new FactorsList(P, factors);
        } else {
            List<GenPolynomial<C>> facs = factorsSquarefree(P);
            if (!this.debug || isFactorization((GenPolynomial) P, (List) facs)) {
                if (logger.isInfoEnabled()) {
                    logger.info("all K factors = " + facs);
                }
                List<Factors<C>> afactors = new ArrayList();
                for (GenPolynomial<C> p : facs) {
                    if (p.degree() <= 1) {
                        factors.add(p);
                    } else {
                        Factors<C> afacs = factorsAbsoluteIrreducible(p);
                        if (this.debug) {
                            logger.info("K(alpha) factors = " + afacs);
                        }
                        if (afacs.afac == null) {
                            factors.add(p);
                        } else {
                            afactors.add(afacs);
                        }
                    }
                }
                return new FactorsList(P, factors, afactors);
            }
            throw new ArithmeticException("isFactorization = false");
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public edu.jas.ufd.Factors<C> factorsAbsoluteIrreducible(edu.jas.poly.GenPolynomial<C> r43) {
        /*
        r42 = this;
        if (r43 != 0) goto L_0x0023;
    L_0x0002:
        r38 = new java.lang.IllegalArgumentException;
        r39 = new java.lang.StringBuilder;
        r39.<init>();
        r40 = r42.getClass();
        r40 = r40.getName();
        r39 = r39.append(r40);
        r40 = " P == null";
        r39 = r39.append(r40);
        r39 = r39.toString();
        r38.<init>(r39);
        throw r38;
    L_0x0023:
        r38 = r43.isZERO();
        if (r38 == 0) goto L_0x0033;
    L_0x0029:
        r38 = new edu.jas.ufd.Factors;
        r0 = r38;
        r1 = r43;
        r0.<init>(r1);
    L_0x0032:
        return r38;
    L_0x0033:
        r0 = r43;
        r0 = r0.ring;
        r28 = r0;
        r0 = r28;
        r0 = r0.nvar;
        r38 = r0;
        r39 = 1;
        r0 = r38;
        r1 = r39;
        if (r0 > r1) goto L_0x004c;
    L_0x0047:
        r38 = r42.baseFactorsAbsoluteIrreducible(r43);
        goto L_0x0032;
    L_0x004c:
        r0 = r28;
        r0 = r0.coFac;
        r38 = r0;
        r38 = r38.isField();
        if (r38 != 0) goto L_0x0060;
    L_0x0058:
        r38 = new java.lang.IllegalArgumentException;
        r39 = "only for field coefficients";
        r38.<init>(r39);
        throw r38;
    L_0x0060:
        r38 = r43.degree();
        r40 = 1;
        r38 = (r38 > r40 ? 1 : (r38 == r40 ? 0 : -1));
        if (r38 > 0) goto L_0x0074;
    L_0x006a:
        r38 = new edu.jas.ufd.Factors;
        r0 = r38;
        r1 = r43;
        r0.<init>(r1);
        goto L_0x0032;
    L_0x0074:
        r31 = r43;
        r0 = r28;
        r12 = r0.coFac;
        r38 = r12.characteristic();
        r14 = r38.longValue();
        r38 = 0;
        r38 = (r14 > r38 ? 1 : (r14 == r38 ? 0 : -1));
        if (r38 != 0) goto L_0x008d;
    L_0x0088:
        r14 = 9223372036854775807; // 0x7fffffffffffffff float:NaN double:NaN;
    L_0x008d:
        r32 = 0;
        r22 = 0;
    L_0x0091:
        r0 = r28;
        r0 = r0.nvar;
        r38 = r0;
        r38 = r38 + -1;
        r0 = r22;
        r1 = r38;
        if (r0 >= r1) goto L_0x0127;
    L_0x009f:
        r32 = 0;
        r38 = 1;
        r0 = r28;
        r1 = r38;
        r23 = r0.contract(r1);
        r38 = 1;
        r0 = r38;
        r0 = new java.lang.String[r0];
        r37 = r0;
        r38 = 0;
        r39 = r28.getVars();
        r0 = r28;
        r0 = r0.nvar;
        r40 = r0;
        r40 = r40 + -1;
        r39 = r39[r40];
        r37[r38] = r39;
        r30 = new edu.jas.poly.GenPolynomialRing;
        r38 = 1;
        r0 = r28;
        r0 = r0.tord;
        r39 = r0;
        r0 = r30;
        r1 = r23;
        r2 = r38;
        r3 = r39;
        r4 = r37;
        r0.<init>(r1, r2, r3, r4);
        r35 = edu.jas.poly.PolyUtil.recursive(r30, r31);
    L_0x00e0:
        r38 = (r32 > r14 ? 1 : (r32 == r14 ? 0 : -1));
        if (r38 < 0) goto L_0x00ff;
    L_0x00e4:
        r38 = new java.lang.ArithmeticException;
        r39 = new java.lang.StringBuilder;
        r39.<init>();
        r40 = "elements of prime field exhausted: ";
        r39 = r39.append(r40);
        r0 = r39;
        r39 = r0.append(r14);
        r39 = r39.toString();
        r38.<init>(r39);
        throw r38;
    L_0x00ff:
        r0 = r32;
        r29 = r12.fromInteger(r0);
        r29 = (edu.jas.structure.GcdRingElem) r29;
        r0 = r23;
        r1 = r35;
        r2 = r29;
        r21 = edu.jas.poly.PolyUtil.evaluateMainRecursive(r0, r1, r2);
        r38 = 1;
        r32 = r32 + r38;
        r0 = r42;
        r1 = r21;
        r38 = r0.isSquarefree(r1);
        if (r38 == 0) goto L_0x00e0;
    L_0x011f:
        r31 = r21;
        r28 = r23;
        r22 = r22 + 1;
        goto L_0x0091;
    L_0x0127:
        r31 = r31.monic();
        r0 = r42;
        r0 = r0.debug;
        r38 = r0;
        if (r38 == 0) goto L_0x015d;
    L_0x0133:
        r38 = logger;
        r39 = new java.lang.StringBuilder;
        r39.<init>();
        r40 = "P(";
        r39 = r39.append(r40);
        r0 = r39;
        r1 = r32;
        r39 = r0.append(r1);
        r40 = ") = ";
        r39 = r39.append(r40);
        r0 = r39;
        r1 = r31;
        r39 = r0.append(r1);
        r39 = r39.toString();
        r38.info(r39);
    L_0x015d:
        r0 = r42;
        r0 = r0.debug;
        r38 = r0;
        if (r38 == 0) goto L_0x018c;
    L_0x0165:
        r0 = r42;
        r1 = r31;
        r38 = r0.isSquarefree(r1);
        if (r38 != 0) goto L_0x018c;
    L_0x016f:
        r38 = new java.lang.ArithmeticException;
        r39 = new java.lang.StringBuilder;
        r39.<init>();
        r40 = "not irreducible up = ";
        r39 = r39.append(r40);
        r0 = r39;
        r1 = r31;
        r39 = r0.append(r1);
        r39 = r39.toString();
        r38.<init>(r39);
        throw r38;
    L_0x018c:
        r38 = 0;
        r0 = r31;
        r1 = r38;
        r38 = r0.degree(r1);
        r40 = 1;
        r38 = (r38 > r40 ? 1 : (r38 == r40 ? 0 : -1));
        if (r38 > 0) goto L_0x01a7;
    L_0x019c:
        r38 = new edu.jas.ufd.Factors;
        r0 = r38;
        r1 = r43;
        r0.<init>(r1);
        goto L_0x0032;
    L_0x01a7:
        r0 = r42;
        r1 = r31;
        r7 = r0.baseFactorsSquarefree(r1);
        r0 = r42;
        r1 = r31;
        r8 = r0.baseFactorsAbsoluteSquarefree(r1);
        r11 = r8.findExtensionField();
        r38 = 0;
        r0 = r31;
        r1 = r38;
        r18 = r0.degree(r1);
        r22 = 0;
    L_0x01c7:
        r38 = r7.size();
        r0 = r22;
        r1 = r38;
        if (r0 >= r1) goto L_0x01fc;
    L_0x01d1:
        r0 = r22;
        r34 = r7.get(r0);
        r34 = (edu.jas.poly.GenPolynomial) r34;
        r38 = 0;
        r0 = r34;
        r1 = r38;
        r16 = r0.degree(r1);
        r38 = 1;
        r38 = (r38 > r16 ? 1 : (r38 == r16 ? 0 : -1));
        if (r38 > 0) goto L_0x01f9;
    L_0x01e9:
        r38 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
        if (r38 > 0) goto L_0x01f9;
    L_0x01ed:
        r31 = r34;
        r38 = 0;
        r0 = r31;
        r1 = r38;
        r18 = r0.degree(r1);
    L_0x01f9:
        r22 = r22 + 1;
        goto L_0x01c7;
    L_0x01fc:
        r38 = 0;
        r0 = r31;
        r1 = r38;
        r38 = r0.degree(r1);
        r40 = 1;
        r38 = (r38 > r40 ? 1 : (r38 == r40 ? 0 : -1));
        if (r38 > 0) goto L_0x0217;
    L_0x020c:
        r38 = new edu.jas.ufd.Factors;
        r0 = r38;
        r1 = r43;
        r0.<init>(r1);
        goto L_0x0032;
    L_0x0217:
        r0 = r42;
        r0 = r0.debug;
        r38 = r0;
        if (r38 == 0) goto L_0x023b;
    L_0x021f:
        r38 = logger;
        r39 = new java.lang.StringBuilder;
        r39.<init>();
        r40 = "field extension by ";
        r39 = r39.append(r40);
        r0 = r39;
        r1 = r31;
        r39 = r0.append(r1);
        r39 = r39.toString();
        r38.info(r39);
    L_0x023b:
        r10 = new java.util.ArrayList;
        r10.<init>();
        r38 = "z_";
        r0 = r28;
        r1 = r38;
        r36 = r0.newVars(r1);
        r28 = r28.copy();
        r0 = r28;
        r1 = r36;
        r0.setVars(r1);
        r9 = r11;
        r13 = r9.depth();
        r27 = new edu.jas.poly.GenPolynomialRing;
        r0 = r43;
        r0 = r0.ring;
        r38 = r0;
        r0 = r38;
        r0 = r0.nvar;
        r38 = r0;
        r0 = r43;
        r0 = r0.ring;
        r39 = r0;
        r0 = r39;
        r0 = r0.tord;
        r39 = r0;
        r0 = r43;
        r0 = r0.ring;
        r40 = r0;
        r40 = r40.getVars();
        r0 = r27;
        r1 = r38;
        r2 = r39;
        r3 = r40;
        r0.<init>(r9, r1, r2, r3);
        r0 = r27;
        r1 = r43;
        r6 = edu.jas.poly.PolyUtil.convertToRecAlgebraicCoefficients(r13, r0, r1);
        r20 = edu.jas.ufd.FactorFactory.getImplementation(r9);
        r0 = r20;
        r10 = r0.factorsSquarefree(r6);
        r0 = r42;
        r0 = r0.debug;
        r38 = r0;
        if (r38 == 0) goto L_0x02bd;
    L_0x02a3:
        r38 = logger;
        r39 = new java.lang.StringBuilder;
        r39.<init>();
        r40 = "K(alpha) factors multi = ";
        r39 = r39.append(r40);
        r0 = r39;
        r39 = r0.append(r10);
        r39 = r39.toString();
        r38.info(r39);
    L_0x02bd:
        r38 = r10.size();
        r39 = 1;
        r0 = r38;
        r1 = r39;
        if (r0 > r1) goto L_0x02d4;
    L_0x02c9:
        r38 = new edu.jas.ufd.Factors;
        r0 = r38;
        r1 = r43;
        r0.<init>(r1);
        goto L_0x0032;
    L_0x02d4:
        r38 = 0;
        r0 = r38;
        r24 = r10.get(r0);
        r24 = (edu.jas.poly.GenPolynomial) r24;
        r25 = r24.leadingBaseCoefficient();
        r25 = (edu.jas.poly.AlgebraicNumber) r25;
        r38 = r25.isONE();
        if (r38 != 0) goto L_0x0314;
    L_0x02ea:
        r38 = 1;
        r0 = r38;
        r26 = r10.get(r0);
        r26 = (edu.jas.poly.GenPolynomial) r26;
        r0 = r24;
        r10.remove(r0);
        r0 = r26;
        r10.remove(r0);
        r24 = r24.divide(r25);
        r0 = r26;
        r1 = r25;
        r26 = r0.multiply(r1);
        r0 = r24;
        r10.add(r0);
        r0 = r26;
        r10.add(r0);
    L_0x0314:
        r38 = new edu.jas.ufd.Factors;
        r0 = r38;
        r1 = r43;
        r0.<init>(r1, r9, r6, r10);
        goto L_0x0032;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.ufd.FactorAbsolute.factorsAbsoluteIrreducible(edu.jas.poly.GenPolynomial):edu.jas.ufd.Factors<C>");
    }

    public boolean isAbsoluteFactorization(Factors<C> facs) {
        if (facs == null) {
            throw new IllegalArgumentException("facs may not be null");
        } else if (facs.afac == null) {
            return true;
        } else {
            boolean b;
            GenPolynomial<AlgebraicNumber<C>> fa = facs.apoly;
            GenPolynomial<AlgebraicNumber<C>> t = fa.ring.getONE();
            for (GenPolynomial f : facs.afactors) {
                t = t.multiply(f);
            }
            if (fa.equals(t) || fa.equals(t.negate())) {
                b = true;
            } else {
                b = false;
            }
            if (b) {
                return b;
            }
            if (facs.arfactors == null) {
                return false;
            }
            for (Factors<AlgebraicNumber<C>> arp : facs.arfactors) {
                t = t.multiply(arp.poly);
            }
            if (fa.equals(t) || fa.equals(t.negate())) {
                b = true;
            } else {
                b = false;
            }
            if (b) {
                return b;
            }
            System.out.println("\nFactors: " + facs);
            System.out.println("fa = " + fa);
            System.out.println("t = " + t);
            return b;
        }
    }

    public boolean isAbsoluteFactorization(FactorsList<C> facs) {
        boolean b = false;
        if (facs == null) {
            throw new IllegalArgumentException("facs may not be null");
        }
        GenPolynomial<C> P = facs.poly;
        GenPolynomial<C> t = P.ring.getONE();
        for (GenPolynomial f : facs.factors) {
            t = t.multiply(f);
        }
        if (P.equals(t) || P.equals(t.negate())) {
            return true;
        }
        if (facs.afactors == null) {
            return false;
        }
        for (Factors fs : facs.afactors) {
            if (!isAbsoluteFactorization(fs)) {
                return false;
            }
            t = t.multiply(facs.poly);
        }
        if (P.equals(t) || P.equals(t.negate())) {
            b = true;
        }
        if (b) {
            return b;
        }
        System.out.println("\nFactorsList: " + facs);
        System.out.println("P = " + P);
        System.out.println("t = " + t);
        return b;
    }

    public boolean isAbsoluteFactorization(FactorsMap<C> facs) {
        boolean b = false;
        if (facs == null) {
            throw new IllegalArgumentException("facs may not be null");
        }
        GenPolynomial<C> P = facs.poly;
        GenPolynomial<C> t = P.ring.getONE();
        for (Entry<GenPolynomial<C>, Long> me : facs.factors.entrySet()) {
            t = t.multiply((GenPolynomial) Power.positivePower((GenPolynomial) me.getKey(), ((Long) me.getValue()).longValue()));
        }
        if (P.equals(t) || P.equals(t.negate())) {
            return true;
        }
        if (facs.afactors == null) {
            return false;
        }
        for (Entry<Factors<C>, Long> me2 : facs.afactors.entrySet()) {
            Factors fs = (Factors) me2.getKey();
            if (!isAbsoluteFactorization(fs)) {
                return false;
            }
            t = t.multiply((GenPolynomial) Power.positivePower(fs.poly, ((Long) me2.getValue()).longValue()));
        }
        if (P.equals(t) || P.equals(t.negate())) {
            b = true;
        }
        if (b) {
            return b;
        }
        System.out.println("\nFactorsMap: " + facs);
        System.out.println("P = " + P);
        System.out.println("t = " + t);
        return b;
    }
}
