package edu.jas.ufd;

import edu.jas.arith.BigInteger;
import edu.jas.arith.ModIntegerRing;
import edu.jas.arith.ModLongRing;
import edu.jas.arith.Modular;
import edu.jas.arith.ModularRingFactory;
import edu.jas.arith.PrimeList;
import edu.jas.arith.PrimeList.Range;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.OptimizedPolynomialList;
import edu.jas.poly.PolyUtil;
import edu.jas.poly.TermOrderOptimization;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.Power;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.util.KsubSet;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.log4j.Logger;

public class FactorInteger<MOD extends GcdRingElem<MOD> & Modular> extends FactorAbstract<BigInteger> {
    private static final Logger logger;
    private final boolean debug;
    protected final GreatestCommonDivisorAbstract<MOD> mengine;
    protected final FactorAbstract<MOD> mfactor;

    static {
        logger = Logger.getLogger(FactorInteger.class);
    }

    public FactorInteger() {
        this(BigInteger.ONE);
    }

    public FactorInteger(RingFactory<BigInteger> cfac) {
        super(cfac);
        this.debug = logger.isDebugEnabled();
        RingFactory mcofac = new ModLongRing(13, true);
        this.mfactor = FactorFactory.getImplementation(mcofac);
        this.mengine = GCDFactory.getImplementation(mcofac);
    }

    public List<GenPolynomial<BigInteger>> baseFactorsSquarefree(GenPolynomial<BigInteger> P) {
        long t;
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P == null");
        }
        List<GenPolynomial<BigInteger>> factors = new ArrayList();
        if (P.isZERO()) {
            return factors;
        }
        if (P.isONE()) {
            factors.add(P);
            return factors;
        }
        GenPolynomialRing<BigInteger> pfac = P.ring;
        int i = pfac.nvar;
        if (r0 > 1) {
            throw new IllegalArgumentException(getClass().getName() + " only for univariate polynomials");
        }
        if (!((BigInteger) this.engine.baseContent(P)).isONE()) {
            throw new IllegalArgumentException(getClass().getName() + " P not primitive");
        } else if (P.degree(0) <= 1) {
            factors.add(P);
            return factors;
        } else {
            int k;
            List<GenPolynomial<MOD>> mlist;
            BigInteger an = (BigInteger) P.maxNorm();
            BigInteger ac = (BigInteger) P.leadingBaseCoefficient();
            ExpVector degv = P.degreeVector();
            int degi = (int) P.degree(0);
            BigInteger factorBound = PolyUtil.factorBound(degv);
            BigInteger M = an.multiply(r40).multiply(ac.abs().multiply(ac.fromInteger(8)));
            PrimeList primeList = new PrimeList(Range.small);
            ModularRingFactory<MOD> cofac = null;
            GenPolynomial<MOD> am = null;
            GenPolynomialRing<MOD> mfac = null;
            List<GenPolynomial<MOD>>[] modfac = new List[5];
            List<GenPolynomial<BigInteger>>[] intfac = new List[5];
            BigInteger[] plist = new BigInteger[5];
            List mlist2 = null;
            int i2 = 0;
            if (this.debug) {
                logger.debug("an  = " + an);
                logger.debug("ac  = " + ac);
                logger.debug("M   = " + M);
                logger.info("degv = " + degv);
            }
            Iterator<java.math.BigInteger> pit = primeList.iterator();
            pit.next();
            pit.next();
            MOD nf = null;
            for (k = 0; k < 5; k++) {
                if (k == 4) {
                    pit = new PrimeList(Range.medium).iterator();
                }
                if (k == 6) {
                    pit = new PrimeList(Range.large).iterator();
                }
                while (pit.hasNext()) {
                    java.math.BigInteger p = (java.math.BigInteger) pit.next();
                    i2++;
                    if (i2 >= 30) {
                        logger.error("prime list exhausted, pn = " + 30);
                        throw new ArithmeticException("prime list exhausted");
                    }
                    if (ModLongRing.MAX_LONG.compareTo(p) > 0) {
                        cofac = new ModLongRing(p, true);
                    } else {
                        cofac = new ModIntegerRing(p, true);
                    }
                    logger.info("prime = " + cofac);
                    nf = (GcdRingElem) cofac.fromInteger(ac.getVal());
                    if (nf.isZERO()) {
                        logger.info("unlucky prime (nf) = " + p);
                    } else {
                        GenPolynomialRing<MOD> genPolynomialRing = new GenPolynomialRing((RingFactory) cofac, (GenPolynomialRing) pfac);
                        am = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) P);
                        if (am.degreeVector().equals(degv)) {
                            GenPolynomial<MOD> ap = PolyUtil.baseDeriviative(am);
                            if (ap.isZERO()) {
                                logger.info("unlucky prime (a')= " + p);
                            } else {
                                if (this.mengine.baseGcd(am, ap).isONE()) {
                                    logger.info("**lucky prime = " + p);
                                    break;
                                }
                            }
                        } else {
                            logger.info("unlucky prime (deg) = " + p);
                        }
                    }
                }
                if (!nf.isONE()) {
                    am = am.divide(nf);
                }
                mlist = this.mfactor.baseFactorsSquarefree(am);
                if (logger.isInfoEnabled()) {
                    logger.info("modlist  = " + mlist);
                }
                if (mlist.size() <= 1) {
                    factors.add(P);
                    return factors;
                }
                if (!nf.isONE()) {
                    mlist.add(0, mfac.getONE().multiply(nf));
                }
                modfac[k] = mlist;
                plist[k] = cofac.getIntegerModul();
            }
            int min = BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT;
            BitSet AD = null;
            for (k = 0; k < 5; k++) {
                BitSet D = factorDegrees(PolyUtil.leadingExpVector(modfac[k]), degi);
                if (AD == null) {
                    AD = D;
                } else {
                    AD.and(D);
                }
                int s = modfac[k].size();
                logger.info("mod(" + plist[k] + ") #s = " + s + ", D = " + D);
                if (s < min) {
                    min = s;
                    mlist2 = modfac[k];
                }
            }
            logger.info("min = " + min + ", AD = " + AD);
            if (mlist2.size() <= 1) {
                logger.info("mlist.size() = 1");
                factors.add(P);
                return factors;
            } else if (AD.cardinality() <= 2) {
                logger.info("degree set cardinality = " + AD.cardinality());
                factors.add(P);
                return factors;
            } else {
                if (this.debug) {
                    logger.info("lifting shortest from " + mlist2);
                }
                long currentTimeMillis;
                if (((BigInteger) P.leadingBaseCoefficient()).isONE()) {
                    t = System.currentTimeMillis();
                    try {
                        mlist = PolyUtil.monic(mlist2);
                        factors = searchFactorsMonic(P, M, mlist, AD);
                        t = System.currentTimeMillis() - t;
                        if (this.debug) {
                            t = System.currentTimeMillis();
                            List<GenPolynomial<BigInteger>> fnm = searchFactorsNonMonic(P, M, mlist, AD);
                            t = System.currentTimeMillis() - t;
                            System.out.println("non monic time = " + t);
                            if (!factors.equals(fnm)) {
                                System.out.println("monic factors     = " + factors);
                                System.out.println("non monic factors = " + fnm);
                            }
                        }
                    } catch (RuntimeException e) {
                        t = System.currentTimeMillis();
                        factors = searchFactorsNonMonic(P, M, mlist2, AD);
                        currentTimeMillis = System.currentTimeMillis() - t;
                    }
                } else {
                    t = System.currentTimeMillis();
                    factors = searchFactorsNonMonic(P, M, mlist2, AD);
                    currentTimeMillis = System.currentTimeMillis() - t;
                }
                return normalizeFactorization(factors);
            }
        }
    }

    public BitSet factorDegrees(List<ExpVector> E, int deg) {
        BitSet D = new BitSet(deg + 1);
        D.set(0);
        for (ExpVector e : E) {
            int i = (int) e.getVal(0);
            BitSet s = new BitSet(deg + 1);
            for (int k = 0; k < (deg + 1) - i; k++) {
                s.set(i + k, D.get(k));
            }
            D.or(s);
        }
        return D;
    }

    public static <C extends RingElem<C>> long degreeSum(List<GenPolynomial<C>> L) {
        long s = 0;
        for (GenPolynomial<C> p : L) {
            s += p.leadingExpVector().getVal(0);
        }
        return s;
    }

    List<GenPolynomial<BigInteger>> searchFactorsMonic(GenPolynomial<BigInteger> C, BigInteger M, List<GenPolynomial<MOD>> F, BitSet D) {
        if (C == null || C.isZERO() || F == null || F.size() == 0) {
            throw new IllegalArgumentException("C must be nonzero and F must be nonempty");
        }
        GenPolynomialRing<BigInteger> pfac = C.ring;
        int i = pfac.nvar;
        if (r0 != 1) {
            throw new IllegalArgumentException("polynomial ring not univariate");
        }
        List<GenPolynomial<BigInteger>> factors = new ArrayList(F.size());
        List<GenPolynomial<MOD>> mlist = F;
        GenPolynomial<MOD> ct = (GenPolynomial) mlist.get(0);
        if (ct.isConstant()) {
            mlist.remove(ct);
            if (mlist.size() <= 1) {
                factors.add(C);
                return factors;
            }
        }
        BigInteger m = ((ModularRingFactory) ct.ring.coFac).getIntegerModul();
        long k = 1;
        for (BigInteger pi = m; pi.compareTo(M) < 0; pi = pi.multiply(m)) {
            k++;
        }
        logger.info("p^k = " + m + "^" + k);
        GenPolynomial<BigInteger> PP = C;
        GenPolynomial<BigInteger> P = C;
        try {
            List<GenPolynomial<MOD>> lift = HenselUtil.liftHenselMonic(PP, mlist, k);
            if (logger.isInfoEnabled()) {
                logger.info("lifted modlist = " + lift);
            }
            GenPolynomialRing<MOD> mpfac = ((GenPolynomial) lift.get(0)).ring;
            int dl = (lift.size() + 1) / 2;
            GenPolynomial<BigInteger> u = PP;
            long deg = (u.degree(0) + 1) / 2;
            int j = 1;
            while (j <= dl) {
                Iterator i$ = new KsubSet(lift, j).iterator();
                while (i$.hasNext()) {
                    List flist = (List) i$.next();
                    if (D.get((int) degreeSum(flist))) {
                        GenPolynomial<MOD> mtrial = (GenPolynomial) Power.multiply((RingFactory) mpfac, flist);
                        if (mtrial.degree(0) > deg) {
                            logger.info("degree " + mtrial.degree(0) + " > deg " + deg);
                        }
                        GenPolynomial<BigInteger> trial = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) pfac, (GenPolynomial) mtrial);
                        trial = this.engine.basePrimitivePart((GenPolynomial) trial);
                        if (PolyUtil.baseSparsePseudoRemainder(u, trial).isZERO()) {
                            logger.info("successful trial = " + trial);
                            factors.add(trial);
                            u = PolyUtil.basePseudoDivide(u, trial);
                            lift = FactorAbstract.removeOnce(lift, flist);
                            logger.info("new lift= " + lift);
                            dl = (lift.size() + 1) / 2;
                            j = 0;
                            break;
                        }
                    } else {
                        logger.info("skipped by degree set " + D + ", deg = " + degreeSum(flist));
                    }
                }
                j++;
            }
            if (!(u.isONE() || u.equals(P))) {
                logger.info("rest u = " + u);
                factors.add(u);
            }
            if (factors.size() == 0) {
                logger.info("irred u = " + u);
                factors.add(PP);
            }
            return normalizeFactorization(factors);
        } catch (NoLiftingException e) {
            throw new RuntimeException(e);
        }
    }

    List<GenPolynomial<BigInteger>> searchFactorsNonMonic(GenPolynomial<BigInteger> C, BigInteger M, List<GenPolynomial<MOD>> F, BitSet D) {
        if (C == null || C.isZERO() || F == null || F.size() == 0) {
            throw new IllegalArgumentException("C must be nonzero and F must be nonempty");
        }
        int i = C.ring.nvar;
        if (r0 != 1) {
            throw new IllegalArgumentException("polynomial ring not univariate");
        }
        List<GenPolynomial<BigInteger>> factors = new ArrayList(F.size());
        List<GenPolynomial<MOD>> mlist = F;
        GenPolynomial<MOD> ct = (GenPolynomial) mlist.get(0);
        if (ct.isConstant()) {
            MOD nf = (GcdRingElem) ct.leadingBaseCoefficient();
            mlist.remove(ct);
            if (mlist.size() <= 1) {
                factors.add(C);
                return factors;
            }
        }
        nf = (GcdRingElem) ct.ring.coFac.getONE();
        GenPolynomialRing<MOD> mfac = ct.ring;
        GenPolynomial<BigInteger> PP = C;
        GenPolynomial<BigInteger> P = C;
        int dl = (mlist.size() + 1) / 2;
        GenPolynomial<BigInteger> u = PP;
        long deg = (u.degree(0) + 1) / 2;
        GenPolynomial<MOD> um = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) mfac, (GenPolynomial) C);
        int j = 1;
        while (j <= dl) {
            Iterator i$ = new KsubSet(mlist, j).iterator();
            while (i$.hasNext()) {
                List<GenPolynomial<MOD>> flist = (List) i$.next();
                if (D.get((int) degreeSum(flist))) {
                    GenPolynomial<MOD> trial = mfac.getONE().multiply((RingElem) nf);
                    for (int kk = 0; kk < flist.size(); kk++) {
                        trial = trial.multiply((GenPolynomial) flist.get(kk));
                    }
                    if (trial.degree(0) > deg) {
                        logger.info("degree > deg " + deg + ", degree = " + trial.degree(0));
                    }
                    GenPolynomial<MOD> cofactor = um.divide((GenPolynomial) trial);
                    try {
                        HenselApprox<MOD> ilist = HenselUtil.liftHenselQuadratic(PP, M, trial, cofactor);
                        GenPolynomial<BigInteger> itrial = ilist.A;
                        GenPolynomial<BigInteger> icofactor = ilist.B;
                        if (logger.isDebugEnabled()) {
                            logger.info("       modlist = " + trial + ", cofactor " + cofactor);
                            logger.info("lifted intlist = " + itrial + ", cofactor " + icofactor);
                        }
                        itrial = this.engine.basePrimitivePart((GenPolynomial) itrial);
                        if (PolyUtil.baseSparsePseudoRemainder(u, itrial).isZERO()) {
                            logger.info("successful trial = " + itrial);
                            factors.add(itrial);
                            u = icofactor;
                            PP = u;
                            um = cofactor;
                            mlist = FactorAbstract.removeOnce(mlist, flist);
                            logger.info("new mlist= " + mlist);
                            dl = (mlist.size() + 1) / 2;
                            j = 0;
                            break;
                        }
                    } catch (NoLiftingException e) {
                        if (logger.isDebugEnabled()) {
                            logger.info("no liftable factors " + e);
                        }
                    }
                } else {
                    logger.info("skipped by degree set " + D + ", deg = " + degreeSum(flist));
                }
            }
            j++;
        }
        if (!(u.isONE() || u.equals(P))) {
            logger.info("rest u = " + u);
            factors.add(u);
        }
        if (factors.size() == 0) {
            logger.info("irred u = " + PP);
            factors.add(PP);
        }
        return normalizeFactorization(factors);
    }

    public List<GenPolynomial<BigInteger>> factorsSquarefree(GenPolynomial<BigInteger> P) {
        GenPolynomialRing<BigInteger> pfac = P.ring;
        int i = pfac.nvar;
        if (r0 <= 1) {
            return baseFactorsSquarefree(P);
        }
        List<GenPolynomial<BigInteger>> topt = new ArrayList(1);
        topt.add(P);
        OptimizedPolynomialList<BigInteger> opt = TermOrderOptimization.optimizeTermOrder(pfac, topt);
        P = (GenPolynomial) opt.list.get(0);
        logger.info("optimized polynomial: " + P);
        List iperm = TermOrderOptimization.inversePermutation(opt.perm);
        logger.info("optimize perm: " + opt.perm + ", de-optimize perm: " + iperm);
        ExpVector degv = P.degreeVector();
        int[] donv = degv.dependencyOnVariables();
        List facs = null;
        if (degv.length() == donv.length) {
            try {
                logger.info("try factorsSquarefreeHensel: " + P);
                facs = factorsSquarefreeHensel(P);
            } catch (Exception e) {
                logger.warn("exception " + e);
            }
        } else {
            GenPolynomial<BigInteger> pu = PolyUtil.removeUnusedUpperVariables(P);
            GenPolynomial<BigInteger> pl = PolyUtil.removeUnusedLowerVariables(pu);
            try {
                logger.info("try factorsSquarefreeHensel: " + pl);
                List<GenPolynomial<BigInteger>> facs2 = factorsSquarefreeHensel(pu);
                List<GenPolynomial<BigInteger>> fs = new ArrayList(facs2.size());
                GenPolynomialRing<BigInteger> pf = P.ring;
                GenPolynomialRing<BigInteger> pfu = pu.ring;
                for (GenPolynomial<BigInteger> p : facs2) {
                    fs.add(p.extendLower(pfu, 0, 0).extend(pf, 0, 0));
                }
                facs2 = fs;
            } catch (Exception e2) {
                logger.warn("exception " + e2);
            }
        }
        if (facs == null) {
            logger.info("factorsSquarefreeHensel not applicable or failed, reverting to Kronecker for: " + P);
            facs = super.factorsSquarefree(P);
        }
        List<GenPolynomial<BigInteger>> iopt = TermOrderOptimization.permutation(iperm, (GenPolynomialRing) pfac, facs);
        logger.info("de-optimized polynomials: " + iopt);
        return normalizeFactorization(iopt);
    }

    public List<GenPolynomial<BigInteger>> factorsSquarefreeHensel(GenPolynomial<BigInteger> P) {
        ArrayList arrayList;
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        }
        GenPolynomialRing<BigInteger> pfac = P.ring;
        if (pfac.nvar == 1) {
            return baseFactorsSquarefree(P);
        }
        List<GenPolynomial<BigInteger>> factors = new ArrayList();
        if (P.isZERO()) {
            return factors;
        }
        if (P.degreeVector().totalDeg() <= 1) {
            factors.add(P);
            return factors;
        }
        List<GenPolynomial<BigInteger>> arrayList2;
        Iterator i$;
        int j;
        List<BigInteger> V;
        List<BigInteger> cei;
        int i;
        BigInteger q;
        TrialParts tp;
        GenPolynomial<BigInteger> pd = P;
        BigInteger ac = (BigInteger) pd.leadingBaseCoefficient();
        GenPolynomial<GenPolynomial<BigInteger>> prr = PolyUtil.switchVariables(PolyUtil.recursive(pfac.recursive(pfac.nvar - 1), (GenPolynomial) pd));
        GenPolynomial prrc = this.engine.recursiveContent(prr);
        List<GenPolynomial<BigInteger>> cfactors = null;
        if (!prrc.isONE()) {
            prr = PolyUtil.recursiveDivide((GenPolynomial) prr, prrc);
            pd = PolyUtil.basePseudoDivide(pd, prrc.extendLower(pfac, 0, 0));
            logger.info("recursive content = " + prrc + ", new P = " + pd);
            cfactors = factorsSquarefree(prrc);
            arrayList2 = new ArrayList(cfactors.size());
            for (GenPolynomial<BigInteger> extendLower : cfactors) {
                arrayList2.add(extendLower.extendLower(pfac, 0, 0));
            }
            cfactors = arrayList2;
            logger.info("cfactors = " + cfactors);
        }
        GenPolynomial<BigInteger> lprr = (GenPolynomial) prr.leadingBaseCoefficient();
        logger.info("leading coeffcient = " + lprr);
        boolean isMonic = false;
        if (lprr.isConstant()) {
            isMonic = true;
        }
        arrayList2 = new ArrayList(factors(lprr).keySet());
        logger.info("leading coefficient factors = " + arrayList2);
        GenPolynomialRing<BigInteger> cpfac = pfac;
        GenPolynomial<BigInteger> pe = pd;
        GenPolynomialRing<BigInteger> ccpfac = lprr.ring;
        List<GenPolynomial<BigInteger>> ce = arrayList2;
        List<BigInteger> dei = new ArrayList();
        List<TrialParts> tParts = new ArrayList();
        List<GenPolynomial<BigInteger>> ln = null;
        List<GenPolynomial<BigInteger>> un = null;
        GenPolynomial<BigInteger> pes = null;
        long evStart = 0;
        List<Long> arrayList3 = new ArrayList(pfac.nvar + 1);
        for (j = 0; j <= pfac.nvar; j++) {
            arrayList3.add(Long.valueOf(0));
        }
        int countSeparate = 0;
        double ran = 1.001d;
        boolean notLucky = true;
        while (notLucky) {
            if (Math.abs(evStart) > 371) {
                logger.warn("no lucky evaluation point for: P = " + P + ", lprr = " + lprr + ", lfacs = " + arrayList2);
                throw new RuntimeException("no lucky evaluation point found after " + Math.abs(evStart) + " iterations");
            }
            int ii;
            if (Math.abs(evStart) % 100 <= 3) {
                ran *= 1.001592653589793d;
            }
            notLucky = false;
            V = new ArrayList();
            cpfac = pfac;
            pe = pd;
            ccpfac = lprr.ring;
            ce = arrayList2;
            List<GenPolynomial<BigInteger>> cep = null;
            cei = null;
            for (j = pfac.nvar; j > 1; j--) {
                long degp = pe.degree(cpfac.nvar - 2);
                cpfac = cpfac.contract(1);
                ccpfac = ccpfac.contract(1);
                long vi = ((Long) arrayList3.get(j)).longValue();
                boolean doIt = true;
                BigInteger Vi = null;
                GenPolynomial<BigInteger> pep = null;
                while (doIt) {
                    logger.info("vi(" + j + ") = " + vi);
                    BigInteger bigInteger = new BigInteger(vi);
                    pep = PolyUtil.evaluateMain((GenPolynomialRing) cpfac, (GenPolynomial) pe, (RingElem) bigInteger);
                    if (degp == pep.degree(cpfac.nvar - 1)) {
                        logger.info("pep = " + pep);
                        if (this.sengine.isSquarefree((GenPolynomial) pep)) {
                            doIt = false;
                        }
                    }
                    if (vi > 0) {
                        vi = -vi;
                    } else {
                        vi = 1 - vi;
                    }
                }
                if (ccpfac.nvar >= 1) {
                    cep = PolyUtil.evaluateMain((GenPolynomialRing) ccpfac, (List) ce, (RingElem) Vi);
                } else {
                    cei = PolyUtil.evaluateMain(ccpfac.coFac, (List) ce, (RingElem) Vi);
                }
                int jj = (int) Math.round((0.52d * Math.random()) + ran);
                if (vi > 0) {
                    arrayList3.set(j, Long.valueOf(((long) jj) + vi));
                    evStart = vi + ((long) jj);
                } else {
                    arrayList3.set(j, Long.valueOf(vi - ((long) jj)));
                    evStart = vi - ((long) jj);
                }
                V.add(Vi);
                pe = pep;
                ce = cep;
            }
            BigInteger pecw = (BigInteger) this.engine.baseContent(pe);
            boolean isPrimitive = pecw.isONE();
            BigInteger ped = (BigInteger) ccpfac.coFac.getONE();
            BigInteger pec = (BigInteger) pe.ring.coFac.getONE();
            if (!isMonic) {
                if (countSeparate > 50) {
                    pec = (BigInteger) pe.ring.coFac.getONE();
                } else {
                    pec = pecw;
                }
                if (((GenPolynomial) arrayList2.get(0)).isConstant()) {
                    ped = (BigInteger) cei.remove(0);
                }
                dei = new ArrayList();
                dei.add(pec.multiply(ped).abs());
                i = 1;
                for (BigInteger ci : cei) {
                    if (ci.isZERO()) {
                        logger.info("condition (0) not met for cei = " + cei);
                        notLucky = true;
                        break;
                    }
                    q = ci.abs();
                    for (ii = i - 1; ii >= 0; ii--) {
                        BigInteger r = (BigInteger) dei.get(ii);
                        while (!r.isONE()) {
                            r = r.gcd(q);
                            q = q.divide(r);
                        }
                    }
                    dei.add(q);
                    if (q.isONE()) {
                        logger.info("condition (1) not met for dei = " + dei + ", cei = " + cei);
                        if (!testSeparate(cei, pecw)) {
                            countSeparate++;
                            if (countSeparate > 50) {
                                logger.info("too many inseparable evaluation points: " + countSeparate + ", removing " + pecw);
                            }
                        }
                        notLucky = true;
                    } else {
                        i++;
                    }
                }
            }
            if (!notLucky) {
                logger.info("evaluation points  = " + V + ", dei = " + dei);
                logger.info("univariate polynomial = " + pe + ", pecw = " + pecw);
                List<GenPolynomial<BigInteger>> ufactors = baseFactorsSquarefree(pe.divide((RingElem) pecw));
                if (!pecw.isONE()) {
                    ufactors.add(0, cpfac.getONE().multiply((RingElem) pecw));
                }
                if (ufactors.size() <= 1) {
                    logger.info("irreducible univariate polynomial");
                    factors.add(pd);
                    if (cfactors == null) {
                        return factors;
                    }
                    cfactors.addAll(factors);
                    return cfactors;
                }
                logger.info("univariate factors = " + ufactors);
                List lf = new ArrayList();
                GenPolynomial<BigInteger> lpx = lprr.ring.getONE();
                for (GenPolynomial<BigInteger> unused : ufactors) {
                    lf.add(lprr.ring.getONE());
                }
                if (!(isMonic && pecw.isONE())) {
                    if (arrayList2.size() > 0 && ((GenPolynomial) arrayList2.get(0)).isConstant()) {
                        GenPolynomial genPolynomial = (GenPolynomial) arrayList2.remove(0);
                    }
                    for (i = ufactors.size() - 1; i >= 0; i--) {
                        BigInteger ppl = ((BigInteger) ((GenPolynomial) ufactors.get(i)).leadingBaseCoefficient()).multiply(pec);
                        GenPolynomial<BigInteger> lfp = (GenPolynomial) lf.get(i);
                        ii = 0;
                        for (BigInteger ci2 : cei) {
                            if (ci2.abs().isONE()) {
                                System.out.println("ppl = " + ppl + ", ci = " + ci2 + ", lfp = " + lfp + ", lfacs.get(ii) = " + arrayList2.get(ii));
                                throw new RuntimeException("something is wrong, ci is a unit");
                            }
                            while (ppl.remainder(ci2).isZERO() && arrayList2.size() > ii) {
                                ppl = ppl.divide(ci2);
                                lfp = lfp.multiply((GenPolynomial) arrayList2.get(ii));
                            }
                            ii++;
                        }
                        lf.set(i, lfp.multiply((RingElem) ppl));
                    }
                    pec = pecw;
                    lpx = (GenPolynomial) Power.multiply(lprr.ring, lf);
                    if (lprr.degreeVector().equals(lpx.degreeVector())) {
                        if (!pec.isONE()) {
                            GenPolynomial<BigInteger> up;
                            GenPolynomial<BigInteger> lp;
                            List<GenPolynomial<BigInteger>> lfe = lf;
                            List<BigInteger> lfei = null;
                            ccpfac = lprr.ring;
                            for (j = lprr.ring.nvar; j > 0; j--) {
                                ccpfac = ccpfac.contract(1);
                                Vi = (BigInteger) V.get(lprr.ring.nvar - j);
                                if (ccpfac.nvar >= 1) {
                                    lfe = PolyUtil.evaluateMain((GenPolynomialRing) ccpfac, (List) lfe, (RingElem) Vi);
                                } else {
                                    lfei = PolyUtil.evaluateMain(ccpfac.coFac, (List) lfe, (RingElem) Vi);
                                }
                            }
                            ln = new ArrayList(lf.size());
                            un = new ArrayList(lf.size());
                            for (jj = 0; jj < lf.size(); jj++) {
                                up = (GenPolynomial) ufactors.get(jj);
                                BigInteger ui = (BigInteger) up.leadingBaseCoefficient();
                                BigInteger li = (BigInteger) lfei.get(jj);
                                BigInteger di = ui.gcd(li).abs();
                                BigInteger udi = ui.divide(di);
                                BigInteger ldi = li.divide(di);
                                lp = (GenPolynomial) lf.get(jj);
                                GenPolynomial<BigInteger> lpd = lp.multiply((RingElem) udi);
                                GenPolynomial<BigInteger> upd = up.multiply((RingElem) ldi);
                                if (pec.isONE()) {
                                    ln.add(lp);
                                    un.add(up);
                                } else {
                                    ln.add(lpd);
                                    un.add(upd);
                                    pec = pec.divide(ldi);
                                }
                            }
                            if (!(lf.equals(ln) && un.equals(ufactors))) {
                                logger.debug("!lf.equals(ln) || !un.equals(ufactors)");
                            }
                            if (!pec.isONE()) {
                                ln = new ArrayList(lf.size());
                                un = new ArrayList(lf.size());
                                pes = pe;
                                for (jj = 0; jj < lf.size(); jj++) {
                                    up = (GenPolynomial) ufactors.get(jj);
                                    lp = (GenPolynomial) lf.get(jj);
                                    if (!up.isConstant()) {
                                        up = up.multiply((RingElem) pec);
                                    }
                                    lp = lp.multiply((RingElem) pec);
                                    if (jj != 0) {
                                        pes = pes.multiply((RingElem) pec);
                                    }
                                    un.add(up);
                                    ln.add(lp);
                                }
                                if (pes.equals(Power.multiply(pe.ring, (List) un))) {
                                    isPrimitive = false;
                                }
                            }
                        }
                        if (!notLucky) {
                            logger.info("distributed factors of leading coefficient = " + lf);
                            lpx = (GenPolynomial) Power.multiply(lprr.ring, lf);
                            if (!(lprr.abs().equals(lpx.abs()) || lprr.degreeVector().equals(lpx.degreeVector()))) {
                                logger.info("lprr != lpx: lprr = " + lprr + ", lpx = " + lpx);
                                notLucky = true;
                            }
                        }
                    } else {
                        logger.info("deg(lprr) != deg(lpx): lprr = " + lprr + ", lpx = " + lpx);
                        notLucky = true;
                    }
                }
                if (!notLucky) {
                    if (isPrimitive) {
                        tp = new TrialParts(V, pe, ufactors, cei, lf);
                    } else {
                        TrialParts trialParts = new TrialParts(V, pes, un, cei, ln);
                    }
                    if (!(tp.univPoly == null || tp.ldcfEval.size() == 0)) {
                        tParts.add(tp);
                    }
                    if (tParts.size() < 4) {
                        notLucky = true;
                    }
                }
            }
        }
        int min = BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT;
        TrialParts tpmin = null;
        for (TrialParts tp2 : tParts) {
            logger.info("tp.univFactors.size() = " + tp2.univFactors.size());
            if (tp2.univFactors.size() < min) {
                min = tp2.univFactors.size();
                tpmin = tp2;
            }
        }
        for (TrialParts tp22 : tParts) {
            if (tp22.univFactors.size() == min && !((GenPolynomial) tp22.univFactors.get(0)).isConstant()) {
                tpmin = tp22;
                break;
            }
        }
        V = tpmin.evalPoints;
        GenPolynomial pe2 = tpmin.univPoly;
        List ufactors2 = tpmin.univFactors;
        cei = tpmin.ldcfEval;
        List<GenPolynomial<BigInteger>> lf2 = tpmin.ldcfFactors;
        logger.info("iterations    = " + Math.abs(evStart));
        logger.info("minimal trial = " + tpmin);
        GenPolynomialRing<BigInteger> ufac = pe2.ring;
        Iterator<java.math.BigInteger> primeIter = new PrimeList(Range.medium).iterator();
        BigInteger ae = (BigInteger) pe2.leadingBaseCoefficient();
        GenPolynomial<MOD> Pm = null;
        ModularRingFactory<MOD> cofac = null;
        for (i = 0; i < 11; i++) {
            if (i == 0) {
                primeIter = new PrimeList(Range.medium).iterator();
            }
            if (i == 5) {
                primeIter = new PrimeList(Range.small).iterator();
                java.math.BigInteger p = (java.math.BigInteger) primeIter.next();
                p = (java.math.BigInteger) primeIter.next();
                p = (java.math.BigInteger) primeIter.next();
                p = (java.math.BigInteger) primeIter.next();
            }
            if (i == 7) {
                primeIter = new PrimeList(Range.large).iterator();
            }
            while (0 < 50 && primeIter.hasNext()) {
                ModularRingFactory<MOD> modLongRing;
                p = (java.math.BigInteger) primeIter.next();
                logger.info("prime = " + p);
                if (ModLongRing.MAX_LONG.compareTo(p) > 0) {
                    modLongRing = new ModLongRing(p, true);
                } else {
                    modLongRing = new ModIntegerRing(p, true);
                }
                if (!((GcdRingElem) cf.fromInteger(ae.getVal())).isZERO()) {
                    Pm = PolyUtil.fromIntegerCoefficients(new GenPolynomialRing((RingFactory) cf, (GenPolynomialRing) ufac), pe2);
                    if (this.mfactor.isSquarefree(Pm)) {
                        cofac = cf;
                        break;
                    }
                }
            }
            if (cofac != null) {
                break;
            }
        }
        if (cofac == null) {
            throw new RuntimeException("giving up on Hensel preparation, no lucky prime found");
        }
        logger.info("lucky prime = " + cofac.getIntegerModul());
        if (logger.isDebugEnabled()) {
            logger.debug("univariate modulo p: = " + Pm);
        }
        bigInteger = (BigInteger) pd.maxNorm();
        long k = Power.logarithm(cofac.getIntegerModul(), an.multiply(ac.abs()).multiply(new BigInteger(2))) + 1;
        q = (BigInteger) Power.positivePower(cofac.getIntegerModul(), k);
        if (ModLongRing.MAX_LONG.compareTo(q.getVal()) > 0) {
            modLongRing = new ModLongRing(q.getVal());
        } else {
            modLongRing = new ModIntegerRing(q.getVal());
        }
        GenPolynomialRing<MOD> genPolynomialRing = new GenPolynomialRing((RingFactory) muqfac, (GenPolynomialRing) ufac);
        List<GenPolynomial<MOD>> muqfactors = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, ufactors2);
        GenPolynomial<MOD> peqq = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, pe2);
        if (!this.debug || this.mfactor.isFactorization((GenPolynomial) peqq, (List) muqfactors)) {
            logger.info("univariate modulo p^k: " + peqq + " = " + muqfactors);
            GenPolynomial<MOD> pq = PolyUtil.fromIntegerCoefficients(new GenPolynomialRing((RingFactory) muqfac, pd.ring), (GenPolynomial) pd);
            logger.info("multivariate modulo p^k: " + pq);
            try {
                List<GenPolynomial<MOD>> mlift = HenselMultUtil.liftHensel(pd, pq, muqfactors, V, k, lf2);
                logger.info("mlift = " + mlift);
                if (mlift.size() <= 1) {
                    logger.info("modular lift size == 1: " + mlift);
                    factors.add(pd);
                    if (cfactors == null) {
                        return factors;
                    }
                    cfactors.addAll(factors);
                    return cfactors;
                }
                GenPolynomialRing<MOD> mfac = ((GenPolynomial) mlift.get(0)).ring;
                int dl = (mlift.size() + 1) / 2;
                long deg = (P.degree() + 1) / 2;
                GenPolynomial<BigInteger> ui2 = pd;
                j = 1;
                while (j <= dl) {
                    i$ = new KsubSet(mlift, j).iterator();
                    while (i$.hasNext()) {
                        List<GenPolynomial<MOD>> flist = (List) i$.next();
                        GenPolynomial<MOD> mtrial = (GenPolynomial) Power.multiply((RingFactory) mfac, (List) flist);
                        if (mtrial.degree() > deg) {
                            logger.info("degree > deg " + deg + ", degree = " + mtrial.degree());
                        }
                        GenPolynomial<BigInteger> trial = this.engine.basePrimitivePart((GenPolynomial) PolyUtil.integerFromModularCoefficients((GenPolynomialRing) pfac, (GenPolynomial) mtrial));
                        if (this.debug) {
                            logger.info("trial    = " + trial);
                        }
                        if (PolyUtil.baseSparsePseudoRemainder(ui2, trial).isZERO()) {
                            logger.info("successful trial = " + trial);
                            factors.add(trial);
                            ui2 = PolyUtil.basePseudoDivide(ui2, trial);
                            mlift = FactorAbstract.removeOnce(mlift, flist);
                            logger.info("new mlift= " + mlift);
                            if (mlift.size() > 1) {
                                dl = (mlift.size() + 1) / 2;
                                j = 0;
                                j++;
                            } else {
                                logger.info("last factor = " + ui2);
                                factors.add(ui2);
                                if (cfactors != null) {
                                    cfactors.addAll(factors);
                                    factors = cfactors;
                                }
                                return normalizeFactorization(factors);
                            }
                        }
                    }
                    j++;
                }
                if (!(ui2.isONE() || ui2.equals(pd))) {
                    logger.info("rest factor = " + ui2);
                    factors.add(ui2);
                }
                if (factors.size() == 0) {
                    logger.info("irreducible P = " + P);
                    factors.add(pd);
                }
                if (cfactors != null) {
                    cfactors.addAll(factors);
                    factors = cfactors;
                }
                return normalizeFactorization(factors);
            } catch (Throwable nle) {
                arrayList = new ArrayList();
                throw new RuntimeException(nle);
            } catch (ArithmeticException aex) {
                arrayList = new ArrayList();
                throw aex;
            }
        }
        System.out.println("muqfactors = " + muqfactors);
        System.out.println("peqq       = " + peqq);
        throw new RuntimeException("something is wrong, no modular p^k factorization");
    }

    boolean testSeparate(List<BigInteger> A, BigInteger b) {
        int i = 0;
        List<BigInteger> gei = new ArrayList(A.size());
        for (BigInteger c : A) {
            BigInteger g = c.gcd(b).abs();
            gei.add(g);
            if (!g.isONE()) {
                i++;
            }
        }
        if (i <= 1) {
            return true;
        }
        return false;
    }

    boolean isNearlySquarefree(GenPolynomial<BigInteger> P) {
        GenPolynomialRing<BigInteger> pfac = P.ring;
        if (pfac.nvar >= 0) {
            return this.sengine.isSquarefree((GenPolynomial) P);
        }
        GenPolynomial<GenPolynomial<BigInteger>> Pr = PolyUtil.recursive(pfac.recursive(1), (GenPolynomial) P);
        GenPolynomial<GenPolynomial<BigInteger>> Ps = PolyUtil.recursiveDeriviative(Pr);
        System.out.println("Pr = " + Pr);
        System.out.println("Ps = " + Ps);
        GenPolynomial<GenPolynomial<BigInteger>> g = this.engine.recursiveUnivariateGcd(Pr, Ps);
        System.out.println("g_m = " + g);
        if (!g.isONE()) {
            return false;
        }
        Pr = PolyUtil.switchVariables(PolyUtil.recursive(pfac.recursive(pfac.nvar - 1), (GenPolynomial) P));
        Ps = PolyUtil.recursiveDeriviative(Pr);
        System.out.println("Pr = " + Pr);
        System.out.println("Ps = " + Ps);
        g = this.engine.recursiveUnivariateGcd(Pr, Ps);
        System.out.println("g_1 = " + g);
        if (g.isONE()) {
            return true;
        }
        return false;
    }
}
