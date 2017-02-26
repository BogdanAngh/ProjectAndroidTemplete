package edu.jas.ufd;

import edu.jas.arith.BigInteger;
import edu.jas.arith.ModIntegerRing;
import edu.jas.arith.ModLongRing;
import edu.jas.arith.Modular;
import edu.jas.arith.ModularRingFactory;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.ps.PolynomialTaylorFunction;
import edu.jas.ps.TaylorFunction;
import edu.jas.ps.UnivPowerSeries;
import edu.jas.ps.UnivPowerSeriesRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.Power;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class HenselMultUtil {
    private static final boolean debug;
    private static final Logger logger;

    static {
        logger = Logger.getLogger(HenselMultUtil.class);
        debug = logger.isInfoEnabled();
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> List<GenPolynomial<MOD>> liftDiophant(GenPolynomial<MOD> A, GenPolynomial<MOD> B, GenPolynomial<MOD> C, List<MOD> V, long d, long k) throws NoLiftingException {
        GenPolynomialRing<MOD> pkfac = C.ring;
        if (pkfac.nvar == 1) {
            return HenselUtil.liftDiophant((GenPolynomial) A, (GenPolynomial) B, (GenPolynomial) C, k);
        }
        if (pkfac.equals(A.ring)) {
            List<MOD> Vp = new ArrayList(V);
            GcdRingElem v = (GcdRingElem) Vp.remove(Vp.size() - 1);
            GenPolynomial<MOD> mon = pkfac.getONE();
            GenPolynomial<MOD> xv = pkfac.univariate(0, 1).subtract(pkfac.fromInteger(((Modular) v).getSymmetricInteger().getVal()));
            ModularRingFactory<MOD> modularRingFactory = (ModularRingFactory) pkfac.coFac;
            GcdRingElem vp = (GcdRingElem) cf.fromInteger(((Modular) v).getSymmetricInteger().getVal());
            GenPolynomialRing<MOD> ckfac = pkfac.contract(1);
            GenPolynomial<MOD> Ap = PolyUtil.evaluateMain((GenPolynomialRing) ckfac, (GenPolynomial) A, (RingElem) vp);
            GenPolynomial<MOD> Bp = PolyUtil.evaluateMain((GenPolynomialRing) ckfac, (GenPolynomial) B, (RingElem) vp);
            GenPolynomial<MOD> Cp = PolyUtil.evaluateMain((GenPolynomialRing) ckfac, (GenPolynomial) C, (RingElem) vp);
            List<GenPolynomial<MOD>> su = liftDiophant(Ap, Bp, Cp, Vp, d, k);
            if (pkfac.nvar != 2 || HenselUtil.isDiophantLift(Bp, Ap, (GenPolynomial) su.get(0), (GenPolynomial) su.get(1), Cp)) {
                if (ckfac.equals(((GenPolynomial) su.get(0)).ring)) {
                    GenPolynomialRing<BigInteger> genPolynomialRing = new GenPolynomialRing(new BigInteger(), (GenPolynomialRing) pkfac);
                    GenPolynomialRing<GenPolynomial<MOD>> genPolynomialRing2 = new GenPolynomialRing((RingFactory) ckfac, 1, new String[]{pkfac.getVars()[pkfac.nvar - 1]});
                    List<GenPolynomial<MOD>> arrayList = new ArrayList(su.size());
                    List<GenPolynomial<BigInteger>> arrayList2 = new ArrayList(su.size());
                    for (GenPolynomial<MOD> extend : su) {
                        GenPolynomial<MOD> sp = extend.extend(pkfac, 0, 0);
                        arrayList.add(sp);
                        arrayList2.add(PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) sp));
                    }
                    GenPolynomial<BigInteger> Ai = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) A);
                    GenPolynomial<BigInteger> Bi = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) B);
                    GenPolynomial<BigInteger> E = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) C).subtract(Bi.multiply((GenPolynomial) arrayList2.get(0))).subtract(Ai.multiply((GenPolynomial) arrayList2.get(1)));
                    if (E.isZERO()) {
                        logger.info("liftDiophant leaving on zero E");
                        return arrayList;
                    }
                    GenPolynomial<MOD> Ep = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) pkfac, (GenPolynomial) E);
                    logger.info("Ep(0," + pkfac.nvar + ") = " + Ep);
                    if (Ep.isZERO()) {
                        logger.info("liftDiophant leaving on zero Ep mod p^k");
                        return arrayList;
                    }
                    int e = 1;
                    while (((long) e) <= d) {
                        GenPolynomial<GenPolynomial<MOD>> Epr = PolyUtil.recursive((GenPolynomialRing) genPolynomialRing2, (GenPolynomial) Ep);
                        UnivPowerSeriesRing<GenPolynomial<MOD>> psfac = new UnivPowerSeriesRing((GenPolynomialRing) genPolynomialRing2);
                        TaylorFunction<GenPolynomial<MOD>> polynomialTaylorFunction = new PolynomialTaylorFunction(Epr);
                        arrayList = new ArrayList(1);
                        GenPolynomial<MOD> vq = ckfac.fromInteger(((Modular) v).getSymmetricInteger().getVal());
                        arrayList.add(vq);
                        GenPolynomial<MOD> cm = (GenPolynomial) psfac.seriesOfTaylor(polynomialTaylorFunction, vq).coefficient(e);
                        List<GenPolynomial<MOD>> S = liftDiophant(Ap, Bp, cm, Vp, d, k);
                        if (!ckfac.coFac.equals(((GenPolynomial) S.get(0)).ring.coFac)) {
                            throw new IllegalArgumentException("ckfac != pkfac: " + ckfac.coFac + " != " + ((GenPolynomial) S.get(0)).ring.coFac);
                        } else if (pkfac.nvar != 2 || HenselUtil.isDiophantLift(Ap, Bp, (GenPolynomial) S.get(1), (GenPolynomial) S.get(0), cm)) {
                            mon = mon.multiply((GenPolynomial) xv);
                            arrayList = new ArrayList(S.size());
                            int i = 0;
                            arrayList2 = new ArrayList(su.size());
                            for (GenPolynomial<MOD> extend2 : S) {
                                GenPolynomial<MOD> dm = extend2.extend(pkfac, 0, 0).multiply((GenPolynomial) mon);
                                arrayList.add(dm);
                                int i2 = i + 1;
                                arrayList.set(i, ((GenPolynomial) arrayList.get(i)).sum((GenPolynomial) dm));
                                arrayList2.add(PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) dm));
                                i = i2;
                            }
                            E = E.subtract(Bi.multiply((GenPolynomial) arrayList2.get(0))).subtract(Ai.multiply((GenPolynomial) arrayList2.get(1)));
                            if (E.isZERO()) {
                                logger.info("liftDiophant leaving on zero E");
                                return arrayList;
                            }
                            Ep = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) pkfac, (GenPolynomial) E);
                            logger.info("Ep(" + e + "," + pkfac.nvar + ") = " + Ep);
                            if (Ep.isZERO()) {
                                logger.info("liftDiophant leaving on zero Ep mod p^k");
                                return arrayList;
                            }
                            e++;
                        } else {
                            throw new NoLiftingException("isDiophantLift: false");
                        }
                    }
                    return arrayList;
                }
                throw new IllegalArgumentException("qfac != ckfac: " + ((GenPolynomial) su.get(0)).ring + " != " + ckfac);
            }
            throw new NoLiftingException("isDiophantLift: false");
        }
        throw new IllegalArgumentException("A.ring != pkfac: " + A.ring + " != " + pkfac);
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> List<GenPolynomial<MOD>> liftDiophant(List<GenPolynomial<MOD>> A, GenPolynomial<MOD> C, List<MOD> V, long d, long k) throws NoLiftingException {
        GenPolynomialRing<MOD> pkfac = C.ring;
        if (pkfac.nvar == 1) {
            return HenselUtil.liftDiophant((List) A, (GenPolynomial) C, k);
        }
        if (pkfac.equals(((GenPolynomial) A.get(0)).ring)) {
            GenPolynomial<MOD> As = pkfac.getONE();
            for (GenPolynomial multiply : A) {
                As = As.multiply(multiply);
            }
            List<GenPolynomial<MOD>> arrayList = new ArrayList(A.size());
            for (GenPolynomial<MOD> basePseudoDivide : A) {
                arrayList.add(PolyUtil.basePseudoDivide(As, basePseudoDivide));
            }
            List<MOD> Vp = new ArrayList(V);
            GcdRingElem v = (GcdRingElem) Vp.remove(Vp.size() - 1);
            GenPolynomial<MOD> mon = pkfac.getONE();
            GenPolynomial<MOD> xv = pkfac.univariate(0, 1).subtract(pkfac.fromInteger(((Modular) v).getSymmetricInteger().getVal()));
            ModularRingFactory<MOD> modularRingFactory = (ModularRingFactory) pkfac.coFac;
            GcdRingElem vp = (GcdRingElem) cf.fromInteger(((Modular) v).getSymmetricInteger().getVal());
            GenPolynomialRing<MOD> ckfac = pkfac.contract(1);
            List<GenPolynomial<MOD>> Ap = new ArrayList(A.size());
            for (GenPolynomial multiply2 : A) {
                Ap.add(PolyUtil.evaluateMain((GenPolynomialRing) ckfac, multiply2, (RingElem) vp));
            }
            GenPolynomial<MOD> Cp = PolyUtil.evaluateMain((GenPolynomialRing) ckfac, (GenPolynomial) C, (RingElem) vp);
            List<GenPolynomial<MOD>> su = liftDiophant(Ap, Cp, Vp, d, k);
            if (pkfac.nvar != 2 || HenselUtil.isDiophantLift(Ap, su, Cp)) {
                if (ckfac.equals(((GenPolynomial) su.get(0)).ring)) {
                    int i;
                    GenPolynomialRing<BigInteger> genPolynomialRing = new GenPolynomialRing(new BigInteger(), (GenPolynomialRing) pkfac);
                    GenPolynomialRing<GenPolynomial<MOD>> genPolynomialRing2 = new GenPolynomialRing((RingFactory) ckfac, 1, new String[]{pkfac.getVars()[pkfac.nvar - 1]});
                    arrayList = new ArrayList(su.size());
                    List<GenPolynomial<BigInteger>> arrayList2 = new ArrayList(su.size());
                    for (GenPolynomial<MOD> extend : su) {
                        GenPolynomial<MOD> sp = extend.extend(pkfac, 0, 0);
                        arrayList.add(sp);
                        arrayList2.add(PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) sp));
                    }
                    List<GenPolynomial<BigInteger>> Ai = new ArrayList(A.size());
                    for (GenPolynomial multiply22 : A) {
                        Ai.add(PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, multiply22));
                    }
                    arrayList2 = new ArrayList(A.size());
                    for (GenPolynomial multiply222 : arrayList) {
                        arrayList2.add(PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, multiply222));
                    }
                    GenPolynomial<BigInteger> Ci = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) C);
                    arrayList = new ArrayList(A.size());
                    for (GenPolynomial multiply2222 : Ai) {
                        arrayList.add(PolyUtil.fromIntegerCoefficients((GenPolynomialRing) pkfac, multiply2222));
                    }
                    GenPolynomial<BigInteger> E = Ci;
                    int i2 = 0;
                    for (GenPolynomial<BigInteger> genPolynomial : arrayList2) {
                        i = i2 + 1;
                        E = E.subtract(bi.multiply((GenPolynomial) arrayList2.get(i2)));
                        i2 = i;
                    }
                    if (E.isZERO()) {
                        logger.info("liftDiophant leaving on zero E");
                        return arrayList;
                    }
                    GenPolynomial<MOD> Ep = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) pkfac, (GenPolynomial) E);
                    logger.info("Ep(0," + pkfac.nvar + ") = " + Ep);
                    if (Ep.isZERO()) {
                        logger.info("liftDiophant leaving on zero Ep mod p^k");
                        return arrayList;
                    }
                    for (int e = 1; ((long) e) <= d; e++) {
                        GenPolynomial<GenPolynomial<MOD>> Epr = PolyUtil.recursive((GenPolynomialRing) genPolynomialRing2, (GenPolynomial) Ep);
                        UnivPowerSeriesRing<GenPolynomial<MOD>> psfac = new UnivPowerSeriesRing((GenPolynomialRing) genPolynomialRing2);
                        TaylorFunction<GenPolynomial<MOD>> polynomialTaylorFunction = new PolynomialTaylorFunction(Epr);
                        arrayList = new ArrayList(1);
                        GenPolynomial<MOD> vq = ckfac.fromInteger(((Modular) v).getSymmetricInteger().getVal());
                        arrayList.add(vq);
                        GenPolynomial<MOD> cm = (GenPolynomial) psfac.seriesOfTaylor(polynomialTaylorFunction, vq).coefficient(e);
                        if (!cm.isZERO()) {
                            List<GenPolynomial<MOD>> S = liftDiophant(Ap, cm, Vp, d, k);
                            if (!ckfac.coFac.equals(((GenPolynomial) S.get(0)).ring.coFac)) {
                                throw new IllegalArgumentException("ckfac != pkfac: " + ckfac.coFac + " != " + ((GenPolynomial) S.get(0)).ring.coFac);
                            } else if (pkfac.nvar != 2 || HenselUtil.isDiophantLift(Ap, S, cm)) {
                                mon = mon.multiply((GenPolynomial) xv);
                                arrayList = new ArrayList(S.size());
                                i2 = 0;
                                arrayList2 = new ArrayList(su.size());
                                for (GenPolynomial<MOD> extend2 : S) {
                                    GenPolynomial<MOD> dm = extend2.extend(pkfac, 0, 0).multiply((GenPolynomial) mon);
                                    arrayList.add(dm);
                                    i = i2 + 1;
                                    arrayList.set(i2, ((GenPolynomial) arrayList.get(i2)).sum((GenPolynomial) dm));
                                    arrayList2.add(PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) dm));
                                    i2 = i;
                                }
                                i2 = 0;
                                for (GenPolynomial<BigInteger> genPolynomial2 : arrayList2) {
                                    i = i2 + 1;
                                    E = E.subtract(bi.multiply((GenPolynomial) arrayList2.get(i2)));
                                    i2 = i;
                                }
                                if (E.isZERO()) {
                                    logger.info("liftDiophant leaving on zero E");
                                    return arrayList;
                                }
                                Ep = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) pkfac, (GenPolynomial) E);
                                logger.info("Ep(" + e + "," + pkfac.nvar + ") = " + Ep);
                                if (Ep.isZERO()) {
                                    logger.info("liftDiophant leaving on zero Ep mod p^k");
                                    return arrayList;
                                }
                            } else {
                                throw new NoLiftingException("isDiophantLift: false");
                            }
                        }
                    }
                    return arrayList;
                }
                throw new IllegalArgumentException("qfac != ckfac: " + ((GenPolynomial) su.get(0)).ring + " != " + ckfac);
            }
            throw new NoLiftingException("isDiophantLift: false");
        }
        throw new IllegalArgumentException("A.ring != pkfac: " + ((GenPolynomial) A.get(0)).ring + " != " + pkfac);
    }

    @Deprecated
    public static <MOD extends GcdRingElem<MOD> & Modular> boolean isHenselLift(GenPolynomial<BigInteger> C, GenPolynomial<MOD> Cp, List<GenPolynomial<MOD>> F, long k, List<GenPolynomial<MOD>> L) {
        return isHenselLift(C, Cp, F, L);
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> boolean isHenselLift(GenPolynomial<BigInteger> C, GenPolynomial<MOD> Cp, List<GenPolynomial<MOD>> list, List<GenPolynomial<MOD>> L) {
        GenPolynomial q = ((GenPolynomial) L.get(0)).ring.getONE();
        for (GenPolynomial fi : L) {
            q = q.multiply(fi);
        }
        boolean t = Cp.equals(q);
        if (t) {
            GenPolynomial Ci = PolyUtil.integerFromModularCoefficients(C.ring, q);
            t = C.equals(Ci);
            if (t) {
                return t;
            }
            System.out.println("C      = " + C);
            System.out.println("Ci     = " + Ci);
            System.out.println("C != Ci: " + C.subtract(Ci));
            return t;
        }
        System.out.println("Cp     = " + Cp);
        System.out.println("q      = " + q);
        System.out.println("Cp != q: " + Cp.subtract(q));
        return t;
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> List<GenPolynomial<MOD>> liftHenselMonic(GenPolynomial<BigInteger> C, GenPolynomial<MOD> Cp, List<GenPolynomial<MOD>> F, List<BigInteger> V, long k) throws NoLiftingException {
        GenPolynomialRing<MOD> pkfac = Cp.ring;
        long d = C.degree();
        List<GenPolynomialRing<MOD>> Pfac = new ArrayList();
        List<GenPolynomial<MOD>> Ap = new ArrayList();
        List<MOD> Vb = new ArrayList();
        GcdRingElem v = (GcdRingElem) pkfac.coFac.fromInteger(((BigInteger) V.get(0)).getVal());
        Pfac.add(pkfac);
        Ap.add(Cp);
        Vb.add(v);
        GenPolynomialRing<MOD> pf = pkfac;
        GenPolynomial<MOD> ap = Cp;
        for (int j = pkfac.nvar; j > 2; j--) {
            pf = pf.contract(1);
            Pfac.add(0, pf);
            GcdRingElem vp = (GcdRingElem) pkfac.coFac.fromInteger(((BigInteger) V.get(j - 2)).getVal());
            Vb.add(1, vp);
            ap = PolyUtil.evaluateMain((GenPolynomialRing) pf, (GenPolynomial) ap, (RingElem) vp);
            Ap.add(0, ap);
        }
        if (debug) {
            logger.debug("Pfac   = " + Pfac);
        }
        GenPolynomialRing<MOD> pk1fac = ((GenPolynomial) F.get(0)).ring;
        if (pkfac.coFac.equals(pk1fac.coFac)) {
            List<GenPolynomial<MOD>> U;
            pkfac = (GenPolynomialRing) Pfac.get(0);
            GenPolynomialRing<BigInteger> genPolynomialRing = new GenPolynomialRing(new BigInteger(), (GenPolynomialRing) pk1fac);
            List<GenPolynomial<BigInteger>> Bi = new ArrayList(F.size());
            for (GenPolynomial integerFromModularCoefficients : F) {
                Bi.add(PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, integerFromModularCoefficients));
            }
            List<GenPolynomial<MOD>> arrayList = new ArrayList(F.size());
            for (GenPolynomial<MOD> extend : F) {
                arrayList.add(extend.extend(pkfac, 0, 0));
            }
            List<GenPolynomial<MOD>> U1 = F;
            GenPolynomial<BigInteger> E = C.ring.getZERO();
            List<MOD> Vh = new ArrayList();
            while (Pfac.size() > 0) {
                pkfac = (GenPolynomialRing) Pfac.remove(0);
                Cp = (GenPolynomial) Ap.remove(0);
                v = (GcdRingElem) Vb.remove(0);
                GenPolynomial<MOD> mon = pkfac.getONE();
                GenPolynomial<MOD> xv = pkfac.univariate(0, 1).subtract(pkfac.fromInteger(((Modular) v).getSymmetricInteger().getVal()));
                long deg = Cp.degree(pkfac.nvar - 1);
                genPolynomialRing = new GenPolynomialRing(new BigInteger(), (GenPolynomialRing) pkfac);
                List<GenPolynomial<BigInteger>> Bip = new ArrayList(F.size());
                for (GenPolynomial<BigInteger> extend2 : Bi) {
                    Bip.add(extend2.extend(genPolynomialRing, 0, 0));
                }
                Bi = Bip;
                GenPolynomial<BigInteger> Ci = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) Cp);
                GenPolynomial E2 = genPolynomialRing.getONE();
                for (GenPolynomial multiply : Bi) {
                    E2 = E2.multiply(multiply);
                }
                E = Ci.subtract(E2);
                GenPolynomial<MOD> Ep = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) pkfac, (GenPolynomial) E);
                String str = ",";
                int i = pkfac.nvar;
                str = ") = ";
                logger.info("Ep(0," + deg + r58 + i + r58 + Ep);
                String[] mn = new String[1];
                mn[0] = pkfac.getVars()[pkfac.nvar - 1];
                GenPolynomialRing<MOD> ckfac = pkfac.contract(1);
                GenPolynomialRing<GenPolynomial<MOD>> genPolynomialRing2 = new GenPolynomialRing((RingFactory) ckfac, 1, mn);
                for (int e = 1; ((long) e) <= deg && !Ep.isZERO(); e++) {
                    GenPolynomial<GenPolynomial<MOD>> Epr = PolyUtil.recursive((GenPolynomialRing) genPolynomialRing2, (GenPolynomial) Ep);
                    UnivPowerSeriesRing<GenPolynomial<MOD>> univPowerSeriesRing = new UnivPowerSeriesRing((GenPolynomialRing) genPolynomialRing2);
                    TaylorFunction<GenPolynomial<MOD>> polynomialTaylorFunction = new PolynomialTaylorFunction(Epr);
                    arrayList = new ArrayList(1);
                    GenPolynomial<MOD> vq = ckfac.fromInteger(((Modular) v).getSymmetricInteger().getVal());
                    arrayList.add(vq);
                    UnivPowerSeries<GenPolynomial<MOD>> Epst = univPowerSeriesRing.seriesOfTaylor(polynomialTaylorFunction, vq);
                    str = ",";
                    str = ", ";
                    i = pkfac.nvar;
                    str = ") = ";
                    logger.info("Epst(" + e + r58 + deg + r58 + i + r58 + Epst);
                    GenPolynomial<MOD> cm = (GenPolynomial) Epst.coefficient(e);
                    if (!cm.isZERO()) {
                        List<GenPolynomial<MOD>> Ud = liftDiophant(U1, cm, Vh, d, k);
                        mon = mon.multiply((GenPolynomial) xv);
                        arrayList = new ArrayList(Ud.size());
                        int i2 = 0;
                        List<GenPolynomial<BigInteger>> arrayList2 = new ArrayList(Ud.size());
                        for (GenPolynomial<MOD> extend3 : Ud) {
                            GenPolynomial<MOD> dm = extend3.extend(pkfac, 0, 0).multiply((GenPolynomial) mon);
                            arrayList.add(dm);
                            GenPolynomial<MOD> de = ((GenPolynomial) U.get(i2)).sum((GenPolynomial) dm);
                            int i3 = i2 + 1;
                            U.set(i2, de);
                            arrayList2.add(PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) de));
                            i2 = i3;
                        }
                        E2 = genPolynomialRing.getONE();
                        for (GenPolynomial multiply2 : arrayList2) {
                            E2 = E2.multiply(multiply2);
                        }
                        E2 = Ci.subtract(E2);
                        Ep = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) pkfac, E2);
                        str = ",";
                        str = ",";
                        i = pkfac.nvar;
                        str = ") = ";
                        logger.info("Ep(" + e + r58 + deg + r58 + i + r58 + Ep);
                    }
                }
                Vh.add(v);
                U1 = U;
                if (Pfac.size() > 0) {
                    arrayList = new ArrayList(U.size());
                    pkfac = (GenPolynomialRing) Pfac.get(0);
                    for (GenPolynomial<MOD> extend32 : U) {
                        arrayList.add(extend32.extend(pkfac, 0, 0));
                    }
                    U = arrayList;
                }
            }
            if (E.isZERO()) {
                logger.info("liftHensel leaving with zero E");
            }
            return U;
        }
        throw new IllegalArgumentException("F.ring != pkfac: " + pk1fac + " != " + pkfac);
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> List<GenPolynomial<MOD>> liftHensel(GenPolynomial<BigInteger> C, GenPolynomial<MOD> Cp, List<GenPolynomial<MOD>> F, List<BigInteger> V, long k, List<GenPolynomial<BigInteger>> G) throws NoLiftingException {
        int j;
        GenPolynomialRing<MOD> pkfac = Cp.ring;
        long d = C.degree();
        GenPolynomialRing<MOD> genPolynomialRing = new GenPolynomialRing(pkfac.coFac, ((GenPolynomial) G.get(0)).ring);
        List<GenPolynomial<MOD>> arrayList = new ArrayList(G.size());
        for (GenPolynomial fromIntegerCoefficients : G) {
            arrayList.add(PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, fromIntegerCoefficients).extendLower(pkfac, 0, 0));
        }
        logger.info("G modulo p^k: " + arrayList);
        List<GenPolynomialRing<MOD>> Pfac = new ArrayList();
        List<GenPolynomial<MOD>> Ap = new ArrayList();
        List<List<GenPolynomial<MOD>>> Gp = new ArrayList();
        List<MOD> Vb = new ArrayList();
        Pfac.add(pkfac);
        Ap.add(Cp);
        Gp.add(arrayList);
        GenPolynomialRing<MOD> pf = pkfac;
        GenPolynomial<MOD> ap = Cp;
        List<GenPolynomial<MOD>> Lpp = arrayList;
        for (j = pkfac.nvar; j > 2; j--) {
            pf = pf.contract(1);
            Pfac.add(0, pf);
            GcdRingElem vp = (GcdRingElem) pkfac.coFac.fromInteger(((BigInteger) V.get(pkfac.nvar - j)).getVal());
            Vb.add(vp);
            ap = PolyUtil.evaluateMain((GenPolynomialRing) pf, (GenPolynomial) ap, (RingElem) vp);
            Ap.add(0, ap);
            arrayList = new ArrayList(Lpp.size());
            for (GenPolynomial fromIntegerCoefficients2 : Lpp) {
                arrayList.add(PolyUtil.evaluateMain((GenPolynomialRing) pf, fromIntegerCoefficients2, (RingElem) vp));
            }
            Lpp = arrayList;
            Gp.add(0, Lpp);
        }
        List<MOD> list = Vb;
        list.add(pkfac.coFac.fromInteger(((BigInteger) V.get(pkfac.nvar - 2)).getVal()));
        if (debug) {
            logger.debug("Pfac   = " + Pfac);
        }
        GenPolynomialRing<MOD> pk1fac = ((GenPolynomial) F.get(0)).ring;
        if (pkfac.coFac.equals(pk1fac.coFac)) {
            String toScript;
            List<GenPolynomial<MOD>> U = F;
            GenPolynomial<BigInteger> E = C.ring.getZERO();
            List<MOD> Vh = new ArrayList();
            while (Pfac.size() > 0) {
                GenPolynomialRing<GenPolynomial<MOD>> pkrfac;
                pkfac = (GenPolynomialRing) Pfac.remove(0);
                Cp = (GenPolynomial) Ap.remove(0);
                Lpp = (List) Gp.remove(0);
                GcdRingElem v = (GcdRingElem) Vb.remove(Vb.size() - 1);
                toScript = pkfac.toScript();
                toScript = " v = ";
                logger.info("stack loop: pkfac = " + r68 + r68 + v);
                List<GenPolynomial<MOD>> U1 = U;
                logger.info("to lift U1 = " + U1);
                arrayList = new ArrayList(U1.size());
                j = 0;
                for (GenPolynomial<MOD> extend : U1) {
                    GenPolynomial<MOD> bi = extend.extend(pkfac, 0, 0);
                    GenPolynomial<MOD> li = (GenPolynomial) Lpp.get(j);
                    if (!li.isONE()) {
                        pkrfac = pkfac.recursive(pkfac.nvar - 1);
                        GenPolynomial<GenPolynomial<MOD>> bs = PolyUtil.switchVariables(PolyUtil.recursive((GenPolynomialRing) pkrfac, (GenPolynomial) bi));
                        GenPolynomial<GenPolynomial<MOD>> ls = PolyUtil.switchVariables(PolyUtil.recursive((GenPolynomialRing) pkrfac, (GenPolynomial) li));
                        if (ls.isConstant() || ls.isZERO()) {
                            bs.doPutToMap(bs.leadingExpVector(), ls.leadingBaseCoefficient());
                            bi = PolyUtil.distribute((GenPolynomialRing) pkfac, PolyUtil.switchVariables(bs));
                        } else {
                            throw new RuntimeException("ls not constant " + ls + ", li = " + li);
                        }
                    }
                    arrayList.add(bi);
                    j++;
                }
                logger.info("U with leading coefficient replaced = " + arrayList);
                GenPolynomial<MOD> mon = pkfac.getONE();
                GenPolynomial<MOD> xv = pkfac.univariate(0, 1).subtract(pkfac.fromInteger(((Modular) v).getSymmetricInteger().getVal()));
                long deg = Cp.degree(pkfac.nvar - 1);
                GenPolynomialRing<BigInteger> genPolynomialRing2 = new GenPolynomialRing(new BigInteger(), (GenPolynomialRing) pkfac);
                List<GenPolynomial<BigInteger>> Bi = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing2, (List) arrayList);
                GenPolynomial<BigInteger> Ci = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing2, (GenPolynomial) Cp);
                GenPolynomial E2 = genPolynomialRing2.getONE();
                for (GenPolynomial multiply : Bi) {
                    E2 = E2.multiply(multiply);
                }
                E = Ci.subtract(E2);
                GenPolynomial<MOD> Ep = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) pkfac, (GenPolynomial) E);
                toScript = ",";
                int i = pkfac.nvar;
                toScript = ") = ";
                logger.info("Ep(0," + deg + r68 + i + r68 + Ep);
                pkrfac = pkfac.recursive(1);
                GenPolynomialRing<MOD> ckfac = (GenPolynomialRing) pkrfac.coFac;
                for (int e = 1; ((long) e) <= deg && !Ep.isZERO(); e++) {
                    toScript = " of deg = ";
                    logger.info("approximation loop: e = " + e + r68 + deg);
                    GenPolynomial<GenPolynomial<MOD>> Epr = PolyUtil.recursive((GenPolynomialRing) pkrfac, (GenPolynomial) Ep);
                    UnivPowerSeries<GenPolynomial<MOD>> Epst = new UnivPowerSeriesRing((GenPolynomialRing) pkrfac).seriesOfTaylor(new PolynomialTaylorFunction(Epr), ckfac.fromInteger(((Modular) v).getSymmetricInteger().getVal()));
                    toScript = ",";
                    toScript = ",";
                    i = pkfac.nvar;
                    toScript = ") = ";
                    logger.info("Epst(" + e + r68 + deg + r68 + i + r68 + Epst);
                    GenPolynomial<MOD> cm = (GenPolynomial) Epst.coefficient(e);
                    if (!cm.isZERO()) {
                        List<GenPolynomial<MOD>> Ud = liftDiophant(U1, cm, Vh, d, k);
                        mon = mon.multiply((GenPolynomial) xv);
                        arrayList = new ArrayList(Ud.size());
                        int i2 = 0;
                        List<GenPolynomial<BigInteger>> arrayList2 = new ArrayList(Ud.size());
                        for (GenPolynomial<MOD> extend2 : Ud) {
                            GenPolynomial<MOD> dm = extend2.extend(pkfac, 0, 0).multiply((GenPolynomial) mon);
                            arrayList.add(dm);
                            GenPolynomial<MOD> de = ((GenPolynomial) arrayList.get(i2)).sum((GenPolynomial) dm);
                            int i3 = i2 + 1;
                            arrayList.set(i2, de);
                            arrayList2.add(PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing2, (GenPolynomial) de));
                            i2 = i3;
                        }
                        E2 = genPolynomialRing2.getONE();
                        for (GenPolynomial multiply2 : arrayList2) {
                            E2 = E2.multiply(multiply2);
                        }
                        E2 = Ci.subtract(E2);
                        Ep = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) pkfac, E2);
                        toScript = ",";
                        toScript = ",";
                        i = pkfac.nvar;
                        toScript = ") = ";
                        logger.info("Ep(" + e + r68 + deg + r68 + i + r68 + Ep);
                    }
                }
                Vh.add(v);
                GenPolynomial<MOD> Uf = ((GenPolynomial) arrayList.get(0)).ring.getONE();
                for (GenPolynomial Upp : arrayList) {
                    Uf = Uf.multiply(Upp);
                }
            }
            if (E.isZERO()) {
                logger.info("liftHensel leaving with zero E, Ep");
            }
            toScript = ", of ";
            logger.info("multivariate lift: U = " + U + r68 + F);
            return U;
        }
        throw new IllegalArgumentException("F.ring != pkfac: " + pk1fac + " != " + pkfac);
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> List<GenPolynomial<MOD>> liftHenselFull(GenPolynomial<BigInteger> C, List<GenPolynomial<MOD>> F, List<BigInteger> V, long k, List<GenPolynomial<BigInteger>> G) throws NoLiftingException {
        if (F == null || F.size() == 0) {
            return new ArrayList();
        }
        ModularRingFactory<MOD> modLongRing;
        int j;
        BigInteger p = ((ModularRingFactory) ((GenPolynomial) F.get(0)).ring.coFac).getIntegerModul();
        BigInteger q = (BigInteger) Power.positivePower((RingElem) p, k);
        if (ModLongRing.MAX_LONG.compareTo(q.getVal()) > 0) {
            modLongRing = new ModLongRing(q.getVal());
        } else {
            modLongRing = new ModIntegerRing(q.getVal());
        }
        GenPolynomialRing<MOD> genPolynomialRing = new GenPolynomialRing((RingFactory) mcfac, C.ring);
        GenPolynomial<MOD> Cq = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) C);
        genPolynomialRing = new GenPolynomialRing((RingFactory) mcfac, ((GenPolynomial) G.get(0)).ring);
        List<GenPolynomial<MOD>> GQ = new ArrayList();
        boolean allOnes = true;
        for (GenPolynomial<BigInteger> g : G) {
            if (!g.isONE()) {
                allOnes = false;
            }
            GQ.add(PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) g));
        }
        GenPolynomialRing<MOD> pf = genPolynomialRing;
        GenPolynomial<MOD> ap = Cq;
        for (j = C.ring.nvar; j > 1; j--) {
            pf = pf.contract(1);
            modLongRing = mcfac;
            ap = PolyUtil.evaluateMain((GenPolynomialRing) pf, (GenPolynomial) ap, (GcdRingElem) modLongRing.fromInteger(((BigInteger) V.get(C.ring.nvar - j)).getVal()));
        }
        GenPolynomial Cq1 = ap;
        if (Cq1.isZERO()) {
            throw new NoLiftingException("C mod (I, p^k) == 0: " + C);
        }
        List U1;
        GenPolynomial<BigInteger> Ci = PolyUtil.integerFromModularCoefficients(new GenPolynomialRing(new BigInteger(), (GenPolynomialRing) pf), Cq1);
        GreatestCommonDivisorAbstract<BigInteger> ufd = GCDFactory.getImplementation(new BigInteger());
        Ci = Ci.abs();
        BigInteger cCi = (BigInteger) ufd.baseContent(Ci);
        GenPolynomial Ci2 = Ci.divide((RingElem) cCi);
        List<GenPolynomial<MOD>> GP = new ArrayList();
        for (GenPolynomial<MOD> gp : GQ) {
            GenPolynomial<MOD> gp2;
            GenPolynomialRing<MOD> gf = genPolynomialRing;
            for (j = genPolynomialRing.nvar; j > 1; j--) {
                gf = gf.contract(1);
                modLongRing = mcfac;
                gp2 = PolyUtil.evaluateMain((GenPolynomialRing) gf, (GenPolynomial) gp2, (GcdRingElem) modLongRing.fromInteger(((BigInteger) V.get(genPolynomialRing.nvar - j)).getVal()));
            }
            GP.add(gp2);
        }
        BigInteger gi0 = (BigInteger) Ci2.leadingBaseCoefficient();
        if (gi0.isONE()) {
            U1 = HenselUtil.liftHenselMonic(Ci2, F, k);
        } else {
            U1 = HenselUtil.liftHensel(Ci2, (List) F, k, gi0);
        }
        logger.info("univariate lift: Ci = " + Ci2 + ", F = " + F + ", U1 = " + U1);
        List<GenPolynomial<BigInteger>> U1i = PolyUtil.integerFromModularCoefficients(Ci2.ring, (List) U1);
        if (HenselUtil.isHenselLift(Ci2, q, p, (List) U1i)) {
            MOD cC = (GcdRingElem) mcfac.fromInteger(cCi.getVal());
            List<GenPolynomial<MOD>> U1f = PolyUtil.fromIntegerCoefficients(((GenPolynomial) F.get(0)).ring, (List) U1i);
            List<GenPolynomial<MOD>> arrayList = new ArrayList(U1.size());
            j = 0;
            int s = 0;
            for (GenPolynomial<MOD> u : U1) {
                GenPolynomial<MOD> u2;
                GenPolynomial<MOD> uf = (GenPolynomial) U1f.get(j);
                GenPolynomial<MOD> f = (GenPolynomial) F.get(j);
                if (((GenPolynomial) U1i.get(j)).signum() != ((GenPolynomial) G.get(j)).signum()) {
                    u2 = u2.negate();
                    uf = uf.negate();
                    s++;
                }
                j++;
                if (uf.isConstant()) {
                    u2 = u2.monic().multiply((RingElem) cC);
                    cC = (GcdRingElem) cC.divide(cC);
                } else {
                    GcdRingElem x = (GcdRingElem) ((GcdRingElem) f.leadingBaseCoefficient()).divide(uf.leadingBaseCoefficient());
                    if (!x.isONE()) {
                        GcdRingElem xq = (GcdRingElem) mcfac.fromInteger(((Modular) x).getSymmetricInteger().getVal());
                        u2 = u2.multiply((RingElem) xq);
                        GcdRingElem cC2 = (GcdRingElem) cC.divide(xq);
                    }
                }
                arrayList.add(u2);
            }
            if (cC.isONE()) {
                U1 = arrayList;
                U1f = PolyUtil.fromIntegerCoefficients(((GenPolynomial) F.get(0)).ring, (List) PolyUtil.integerFromModularCoefficients(Ci2.ring, U1));
                if (F.equals(U1f)) {
                    List<GenPolynomial<MOD>> U;
                    logger.info("multivariate lift: U1 = " + U1);
                    if (allOnes) {
                        U = liftHenselMonic(C, Cq, U1, V, k);
                    } else {
                        U = liftHensel(C, Cq, U1, V, k, G);
                    }
                    logger.info("multivariate lift: C = " + C + ", U1 = " + U1 + ", U = " + U);
                    return U;
                }
                System.out.println("F   = " + F);
                System.out.println("U1f = " + U1f);
                throw new NoLiftingException("F = " + F + ", U1f = " + U1f);
            }
            throw new NoLiftingException("s = " + s + ", Ci = " + Ci2 + ", U1i = " + U1i + ", cC = " + cC);
        }
        throw new NoLiftingException("Ci = " + Ci2 + ", U1i = " + U1i);
    }
}
