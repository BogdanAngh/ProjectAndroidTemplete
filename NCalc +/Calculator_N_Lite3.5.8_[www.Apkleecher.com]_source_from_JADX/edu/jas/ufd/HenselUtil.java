package edu.jas.ufd;

import edu.jas.arith.BigInteger;
import edu.jas.arith.ModInteger;
import edu.jas.arith.ModIntegerRing;
import edu.jas.arith.ModLongRing;
import edu.jas.arith.Modular;
import edu.jas.arith.ModularRingFactory;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.Monomial;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

public class HenselUtil {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final boolean debug;
    private static final Logger logger;

    static {
        $assertionsDisabled = !HenselUtil.class.desiredAssertionStatus();
        logger = Logger.getLogger(HenselUtil.class);
        debug = logger.isDebugEnabled();
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> HenselApprox<MOD> liftHensel(GenPolynomial<BigInteger> C, BigInteger M, GenPolynomial<MOD> A, GenPolynomial<MOD> B, GenPolynomial<MOD> S, GenPolynomial<MOD> T) throws NoLiftingException {
        if (C == null || C.isZERO()) {
            return new HenselApprox(C, C, A, B);
        }
        if (A == null || A.isZERO() || B == null || B.isZERO()) {
            throw new IllegalArgumentException("A and B must be nonzero");
        }
        GenPolynomialRing<BigInteger> fac = C.ring;
        int i = fac.nvar;
        if (r0 != 1) {
            throw new IllegalArgumentException("polynomial ring not univariate");
        }
        GenPolynomialRing<MOD> pfac = A.ring;
        RingFactory<MOD> p = pfac.coFac;
        ModularRingFactory<MOD> P = (ModularRingFactory) p;
        ModularRingFactory<MOD> Q = (ModularRingFactory) p;
        BigInteger Qi = Q.getIntegerModul();
        BigInteger M2 = M.multiply(M.fromInteger(2));
        BigInteger Mq = Qi;
        BigInteger c = (BigInteger) C.leadingBaseCoefficient();
        C = C.multiply((RingElem) c);
        GcdRingElem a = (GcdRingElem) A.leadingBaseCoefficient();
        if (!a.isONE()) {
            A = A.divide((RingElem) a);
            S = S.multiply((RingElem) a);
        }
        GcdRingElem b = (GcdRingElem) B.leadingBaseCoefficient();
        if (!b.isONE()) {
            B = B.divide((RingElem) b);
            T = T.multiply((RingElem) b);
        }
        GcdRingElem cm = (GcdRingElem) P.fromInteger(c.getVal());
        A = A.multiply((RingElem) cm);
        B = B.multiply((RingElem) cm);
        T = T.divide((RingElem) cm);
        S = S.divide((RingElem) cm);
        GenPolynomial<BigInteger> Ai = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) A);
        GenPolynomial Bi = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) B);
        ExpVector ea = Ai.leadingExpVector();
        ExpVector eb = Bi.leadingExpVector();
        Ai.doPutToMap(ea, c);
        Bi.doPutToMap(eb, c);
        GenPolynomial<MOD> A1p = A;
        GenPolynomial<MOD> B1p = B;
        while (Mq.compareTo(M2) < 0) {
            GenPolynomial<BigInteger> E = C.subtract(Ai.multiply(Bi));
            if (E.isZERO()) {
                logger.info("leaving on zero E");
                break;
            }
            try {
                GenPolynomial<MOD> Ep = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) pfac, E.divide((RingElem) Qi));
                GenPolynomial<MOD> Ap = S.multiply((GenPolynomial) Ep);
                GenPolynomial<MOD> Bp = T.multiply((GenPolynomial) Ep);
                GenPolynomial<MOD>[] QR = Ap.quotientRemainder(B);
                GenPolynomial<MOD> Qp = QR[0];
                GenPolynomial A1p2 = QR[1];
                GenPolynomial B1p2 = Bp.sum(A.multiply((GenPolynomial) Qp));
                GenPolynomial<BigInteger> Ea = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, A1p2);
                GenPolynomial<BigInteger> Eb = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, B1p2);
                GenPolynomial Ea1 = Ea.multiply((RingElem) Qi);
                Ea = Ai.sum(Eb.multiply((RingElem) Qi));
                Eb = Bi.sum(Ea1);
                if ($assertionsDisabled || Ea.degree(0) + Eb.degree(0) <= C.degree(0)) {
                    Mq = Qi;
                    Qi = Q.getIntegerModul().multiply(P.getIntegerModul());
                    ModularRingFactory<MOD> modLongRing;
                    if (ModLongRing.MAX_LONG.compareTo(Qi.getVal()) > 0) {
                        modLongRing = new ModLongRing(Qi.getVal());
                    } else {
                        modLongRing = new ModIntegerRing(Qi.getVal());
                    }
                    Ai = Ea;
                    GenPolynomial<BigInteger> Bi2 = Eb;
                } else {
                    throw new AssertionError();
                }
            } catch (RuntimeException e) {
                throw e;
            }
        }
        BigInteger ai = (BigInteger) new GreatestCommonDivisorPrimitive().baseContent(Ai);
        try {
            return new HenselApprox(Ai.divide((RingElem) ai), Bi.divide(c.divide(ai)), A1p, B1p);
        } catch (RuntimeException e2) {
            throw new NoLiftingException("no exact lifting possible " + e2);
        }
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> HenselApprox<MOD> liftHensel(GenPolynomial<BigInteger> C, BigInteger M, GenPolynomial<MOD> A, GenPolynomial<MOD> B) throws NoLiftingException {
        if (C == null || C.isZERO()) {
            return new HenselApprox(C, C, A, B);
        }
        if (A == null || A.isZERO() || B == null || B.isZERO()) {
            throw new IllegalArgumentException("A and B must be nonzero");
        } else if (C.ring.nvar != 1) {
            throw new IllegalArgumentException("polynomial ring not univariate");
        } else {
            try {
                GenPolynomial<MOD>[] gst = A.egcd((GenPolynomial) B);
                if (gst[0].isONE()) {
                    return liftHensel(C, M, A, B, gst[1], gst[2]);
                }
                throw new NoLiftingException("A and B not coprime, gcd = " + gst[0] + ", A = " + A + ", B = " + B);
            } catch (ArithmeticException e) {
                throw new NoLiftingException("coefficient error " + e);
            }
        }
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> HenselApprox<MOD> liftHenselQuadratic(GenPolynomial<BigInteger> C, BigInteger M, GenPolynomial<MOD> A, GenPolynomial<MOD> B, GenPolynomial<MOD> S, GenPolynomial<MOD> T) throws NoLiftingException {
        if (C == null || C.isZERO()) {
            return new HenselApprox(C, C, A, B);
        }
        if (A == null || A.isZERO() || B == null || B.isZERO()) {
            throw new IllegalArgumentException("A and B must be nonzero");
        }
        GenPolynomialRing<BigInteger> fac = C.ring;
        int i = fac.nvar;
        if (r0 != 1) {
            throw new IllegalArgumentException("polynomial ring not univariate");
        }
        GenPolynomialRing<MOD> pfac = A.ring;
        RingFactory<MOD> p = pfac.coFac;
        ModularRingFactory<MOD> P = (ModularRingFactory) p;
        ModularRingFactory<MOD> Q = (ModularRingFactory) p;
        BigInteger Qi = Q.getIntegerModul();
        BigInteger M2 = M.multiply(M.fromInteger(2));
        BigInteger Mq = Qi;
        GenPolynomialRing<MOD> genPolynomialRing = new GenPolynomialRing((RingFactory) Q, (GenPolynomialRing) pfac);
        BigInteger c = (BigInteger) C.leadingBaseCoefficient();
        C = C.multiply((RingElem) c);
        GcdRingElem a = (GcdRingElem) A.leadingBaseCoefficient();
        if (!a.isONE()) {
            A = A.divide((RingElem) a);
            S = S.multiply((RingElem) a);
        }
        GcdRingElem b = (GcdRingElem) B.leadingBaseCoefficient();
        if (!b.isONE()) {
            B = B.divide((RingElem) b);
            T = T.multiply((RingElem) b);
        }
        GcdRingElem cm = (GcdRingElem) P.fromInteger(c.getVal());
        A = A.multiply((RingElem) cm);
        B = B.multiply((RingElem) cm);
        T = T.divide((RingElem) cm);
        S = S.divide((RingElem) cm);
        GenPolynomial<BigInteger> Ai = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) A);
        GenPolynomial Bi = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) B);
        ExpVector ea = Ai.leadingExpVector();
        ExpVector eb = Bi.leadingExpVector();
        Ai.doPutToMap(ea, c);
        Bi.doPutToMap(eb, c);
        GenPolynomial<MOD> A1p = A;
        GenPolynomial<MOD> B1p = B;
        GenPolynomial<MOD> Sp = S;
        GenPolynomial<MOD> Tp = T;
        GenPolynomial<BigInteger> Si = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) S);
        GenPolynomial<BigInteger> Ti = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) T);
        GenPolynomial<MOD> Aq = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) Ai);
        GenPolynomial<MOD> Bq = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, Bi);
        while (Mq.compareTo(M2) < 0) {
            GenPolynomial<BigInteger> E = C.subtract(Ai.multiply(Bi));
            if (E.isZERO()) {
                logger.info("leaving on zero E");
                break;
            }
            GenPolynomial<MOD> Ep = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) qfac, E.divide((RingElem) Qi));
            GenPolynomial<MOD> Ap = Sp.multiply((GenPolynomial) Ep);
            GenPolynomial<MOD> Bp = Tp.multiply((GenPolynomial) Ep);
            GenPolynomial<MOD>[] QR = Ap.quotientRemainder(Bq);
            GenPolynomial<MOD> Qp = QR[0];
            GenPolynomial A1p2 = QR[1];
            GenPolynomial B1p2 = Bp.sum(Aq.multiply((GenPolynomial) Qp));
            GenPolynomial<BigInteger> Ea = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, A1p2);
            GenPolynomial<BigInteger> Eb = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, B1p2);
            GenPolynomial<BigInteger> Ea1 = Ea.multiply((RingElem) Qi);
            Ea = Ai.sum(Eb.multiply((RingElem) Qi));
            Eb = Bi.sum((GenPolynomial) Ea1);
            if ($assertionsDisabled || Ea.degree(0) + Eb.degree(0) <= C.degree(0)) {
                GenPolynomial Ai2 = Ea;
                Bi = Eb;
                Ep = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) qfac, fac.getONE().subtract(Si.multiply(Ai2)).subtract(Ti.multiply(Bi)).divide((RingElem) Qi));
                Ap = Sp.multiply((GenPolynomial) Ep);
                QR = Tp.multiply((GenPolynomial) Ep).quotientRemainder(Aq);
                Qp = QR[0];
                B1p2 = QR[1];
                A1p2 = Ap.sum(Bq.multiply((GenPolynomial) Qp));
                Ea = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, A1p2);
                Eb = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, B1p2);
                Ea1 = Ea.multiply((RingElem) Qi);
                GenPolynomial<BigInteger> Eb1 = Eb.multiply((RingElem) Qi);
                Si = Si.sum((GenPolynomial) Ea1);
                Ti = Ti.sum((GenPolynomial) Eb1);
                Mq = Qi;
                Qi = Q.getIntegerModul().multiply(Q.getIntegerModul());
                ModularRingFactory<MOD> modLongRing;
                if (ModLongRing.MAX_LONG.compareTo(Qi.getVal()) > 0) {
                    modLongRing = new ModLongRing(Qi.getVal());
                } else {
                    modLongRing = new ModIntegerRing(Qi.getVal());
                }
                genPolynomialRing = new GenPolynomialRing((RingFactory) Q, (GenPolynomialRing) pfac);
                Aq = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, Ai2);
                Bq = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, Bi);
                Sp = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) Si);
                Tp = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) Ti);
            } else {
                throw new AssertionError();
            }
        }
        BigInteger ai = (BigInteger) new GreatestCommonDivisorPrimitive().baseContent(Ai);
        try {
            return new HenselApprox(Ai.divide((RingElem) ai), Bi.divide(c.divide(ai)), A1p, B1p);
        } catch (RuntimeException e) {
            throw new NoLiftingException("no exact lifting possible " + e);
        }
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> HenselApprox<MOD> liftHenselQuadratic(GenPolynomial<BigInteger> C, BigInteger M, GenPolynomial<MOD> A, GenPolynomial<MOD> B) throws NoLiftingException {
        if (C == null || C.isZERO()) {
            return new HenselApprox(C, C, A, B);
        }
        if (A == null || A.isZERO() || B == null || B.isZERO()) {
            throw new IllegalArgumentException("A and B must be nonzero");
        } else if (C.ring.nvar != 1) {
            throw new IllegalArgumentException("polynomial ring not univariate");
        } else {
            try {
                GenPolynomial<MOD>[] gst = A.egcd((GenPolynomial) B);
                if (gst[0].isONE()) {
                    return liftHenselQuadratic(C, M, A, B, gst[1], gst[2]);
                }
                throw new NoLiftingException("A and B not coprime, gcd = " + gst[0] + ", A = " + A + ", B = " + B);
            } catch (ArithmeticException e) {
                throw new NoLiftingException("coefficient error " + e);
            }
        }
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> HenselApprox<MOD> liftHenselQuadraticFac(GenPolynomial<BigInteger> C, BigInteger M, GenPolynomial<MOD> A, GenPolynomial<MOD> B) throws NoLiftingException {
        if (C == null || C.isZERO()) {
            throw new IllegalArgumentException("C must be nonzero");
        } else if (A == null || A.isZERO() || B == null || B.isZERO()) {
            throw new IllegalArgumentException("A and B must be nonzero");
        } else if (C.ring.nvar != 1) {
            throw new IllegalArgumentException("polynomial ring not univariate");
        } else {
            try {
                GenPolynomial<MOD>[] gst = A.egcd((GenPolynomial) B);
                if (gst[0].isONE()) {
                    return liftHenselQuadraticFac(C, M, A, B, gst[1], gst[2]);
                }
                throw new NoLiftingException("A and B not coprime, gcd = " + gst[0] + ", A = " + A + ", B = " + B);
            } catch (ArithmeticException e) {
                throw new NoLiftingException("coefficient error " + e);
            }
        }
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> HenselApprox<MOD> liftHenselQuadraticFac(GenPolynomial<BigInteger> C, BigInteger M, GenPolynomial<MOD> A, GenPolynomial<MOD> B, GenPolynomial<MOD> S, GenPolynomial<MOD> T) throws NoLiftingException {
        if (C == null || C.isZERO()) {
            throw new IllegalArgumentException("C must be nonzero");
        } else if (A == null || A.isZERO() || B == null || B.isZERO()) {
            throw new IllegalArgumentException("A and B must be nonzero");
        } else {
            GenPolynomialRing<BigInteger> fac = C.ring;
            int i = fac.nvar;
            if (r0 != 1) {
                throw new IllegalArgumentException("polynomial ring not univariate");
            }
            ModularRingFactory<MOD> modLongRing;
            GenPolynomialRing<MOD> pfac = A.ring;
            RingFactory<MOD> p = pfac.coFac;
            ModularRingFactory<MOD> P = (ModularRingFactory) p;
            ModularRingFactory<MOD> Q = (ModularRingFactory) p;
            BigInteger PP = Q.getIntegerModul();
            BigInteger Qi = PP;
            BigInteger M2 = M.multiply(M.fromInteger(2));
            if (debug) {
                logger.debug("M2 =  " + M2);
            }
            BigInteger Mq = Qi;
            GenPolynomialRing<MOD> genPolynomialRing = new GenPolynomialRing((RingFactory) Q, (GenPolynomialRing) pfac);
            BigInteger Mi = Q.getIntegerModul().multiply(Q.getIntegerModul());
            if (ModLongRing.MAX_LONG.compareTo(Mi.getVal()) > 0) {
                modLongRing = new ModLongRing(Mi.getVal());
            } else {
                modLongRing = new ModIntegerRing(Mi.getVal());
            }
            genPolynomialRing = new GenPolynomialRing((RingFactory) Qmm, (GenPolynomialRing) genPolynomialRing);
            MOD Qm = (GcdRingElem) Qmm.fromInteger(Qi.getVal());
            BigInteger c = (BigInteger) C.leadingBaseCoefficient();
            C = C.multiply((RingElem) c);
            GcdRingElem a = (GcdRingElem) A.leadingBaseCoefficient();
            if (!a.isONE()) {
                A = A.divide((RingElem) a);
                S = S.multiply((RingElem) a);
            }
            GcdRingElem b = (GcdRingElem) B.leadingBaseCoefficient();
            if (!b.isONE()) {
                B = B.divide((RingElem) b);
                T = T.multiply((RingElem) b);
            }
            GcdRingElem cm = (GcdRingElem) P.fromInteger(c.getVal());
            if (cm.isZERO()) {
                System.out.println("c =  " + c);
                System.out.println("P =  " + P);
                throw new ArithmeticException("c mod p == 0 not meaningful");
            }
            GenPolynomial<MOD> Em;
            GenPolynomial<BigInteger> Bi;
            A = A.multiply((RingElem) cm);
            S = S.divide((RingElem) cm);
            B = B.multiply((RingElem) cm);
            T = T.divide((RingElem) cm);
            GenPolynomial<BigInteger> Ai = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) A);
            GenPolynomial Bi2 = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) B);
            ExpVector ea = Ai.leadingExpVector();
            ExpVector eb = Bi2.leadingExpVector();
            Ai.doPutToMap(ea, c);
            Bi2.doPutToMap(eb, c);
            GenPolynomial<MOD> A1p = A;
            GenPolynomial<MOD> B1p = B;
            GenPolynomial<MOD> Sp = S;
            GenPolynomial<MOD> Tp = T;
            GenPolynomial<BigInteger> Si = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) S);
            GenPolynomial<BigInteger> Ti = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) T);
            GenPolynomial<MOD> Aq = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) Ai);
            GenPolynomial<MOD> Bq = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, Bi2);
            GenPolynomial<MOD> Cm = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) C);
            GenPolynomial<MOD> Am = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) Ai);
            GenPolynomial Bm = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, Bi2);
            GenPolynomial<MOD> Sm = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) Si);
            GenPolynomial<MOD> Tm = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) Ti);
            while (Mq.compareTo(M2) < 0) {
                if (debug) {
                    logger.debug("mfac =  " + Cm.ring);
                }
                Em = Cm.subtract(Am.multiply(Bm));
                if (Em.isZERO()) {
                    if (C.subtract(Ai.multiply(Bi2)).isZERO()) {
                        logger.info("leaving on zero E");
                        break;
                    }
                }
                GenPolynomial<MOD> Emp = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) qfac, PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) Em).divide((RingElem) Qi));
                if (Emp.isZERO()) {
                    if (C.subtract(Ai.multiply(Bi2)).isZERO()) {
                        logger.info("leaving on zero Emp");
                        break;
                    }
                }
                GenPolynomial<MOD> Ap = Sp.multiply((GenPolynomial) Emp);
                GenPolynomial<MOD> Bp = Tp.multiply((GenPolynomial) Emp);
                GenPolynomial<MOD>[] QR = Ap.quotientRemainder(Bq);
                GenPolynomial<MOD> Qp = QR[0];
                GenPolynomial A1p2 = QR[1];
                GenPolynomial B1p2 = Bp.sum(Aq.multiply((GenPolynomial) Qp));
                GenPolynomial<MOD> Ema = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) mfac, PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, A1p2));
                GenPolynomial<MOD> Emb = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) mfac, PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, B1p2));
                GenPolynomial<MOD> Ema1 = Ema.multiply((RingElem) Qm);
                Ema = Am.sum(Emb.multiply((RingElem) Qm));
                Emb = Bm.sum((GenPolynomial) Ema1);
                if ($assertionsDisabled || Ema.degree(0) + Emb.degree(0) <= Cm.degree(0)) {
                    GenPolynomial Am2 = Ema;
                    Bm = Emb;
                    GenPolynomial Ai2 = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, Am2);
                    Bi2 = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, Bm);
                    Emp = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) qfac, PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, mfac.getONE().subtract(Sm.multiply(Am2)).subtract(Tm.multiply(Bm))).divide((RingElem) Qi));
                    Ap = Sp.multiply((GenPolynomial) Emp);
                    QR = Tp.multiply((GenPolynomial) Emp).quotientRemainder(Aq);
                    Qp = QR[0];
                    B1p2 = QR[1];
                    Ema = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) mfac, PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, Ap.sum(Bq.multiply((GenPolynomial) Qp))));
                    Emb = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) mfac, PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, B1p2));
                    Ema1 = Ema.multiply((RingElem) Qm);
                    GenPolynomial<MOD> Emb1 = Emb.multiply((RingElem) Qm);
                    Ema = Sm.sum((GenPolynomial) Ema1);
                    Tm = Tm.sum((GenPolynomial) Emb1);
                    Si = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) Ema);
                    Ti = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) Tm);
                    Qi = Q.getIntegerModul().multiply(Q.getIntegerModul());
                    Mq = Qi;
                    if (ModLongRing.MAX_LONG.compareTo(Qi.getVal()) > 0) {
                        modLongRing = new ModLongRing(Qi.getVal());
                    } else {
                        modLongRing = new ModIntegerRing(Qi.getVal());
                    }
                    genPolynomialRing = new GenPolynomialRing((RingFactory) Q, (GenPolynomialRing) pfac);
                    BigInteger Qmmi = Qmm.getIntegerModul().multiply(Qmm.getIntegerModul());
                    if (ModLongRing.MAX_LONG.compareTo(Qmmi.getVal()) > 0) {
                        modLongRing = new ModLongRing(Qmmi.getVal());
                    } else {
                        modLongRing = new ModIntegerRing(Qmmi.getVal());
                    }
                    genPolynomialRing = new GenPolynomialRing((RingFactory) Qmm, (GenPolynomialRing) genPolynomialRing);
                    GcdRingElem Qm2 = (GcdRingElem) Qmm.fromInteger(Qi.getVal());
                    Cm = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) C);
                    Am = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, Ai2);
                    Bm = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, Bi2);
                    Sm = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) Si);
                    Tm = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) Ti);
                    if ($assertionsDisabled || isHenselLift(C, Mi, PP, Ai2, Bi2)) {
                        Mi = Mi.fromInteger(Qmm.getIntegerModul().getVal());
                        Aq = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, Ai2);
                        Bq = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, Bi2);
                        Sp = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) Si);
                        Tp = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) Ti);
                    } else {
                        throw new AssertionError();
                    }
                }
                throw new AssertionError();
            }
            Em = Cm.subtract(Am.multiply(Bm));
            if (!Em.isZERO()) {
                System.out.println("Em =  " + Em);
            }
            BigInteger ai = (BigInteger) new GreatestCommonDivisorPrimitive().baseContent(Ai);
            Ai = Ai.divide((RingElem) ai);
            BigInteger[] qr = c.quotientRemainder(ai);
            BigInteger bi = null;
            boolean exact = true;
            if (qr[1].isZERO()) {
                bi = qr[0];
                try {
                    Bi = Bi2.divide((RingElem) bi);
                } catch (RuntimeException e) {
                    System.out.println("*catch: no exact factorization: " + bi + ", e = " + e);
                    exact = false;
                }
            } else {
                System.out.println("*remainder: no exact factorization: q = " + qr[0] + ", r = " + qr[1]);
                exact = false;
            }
            if (exact) {
                return new HenselApprox(Ai, Bi, Aq, Bq);
            }
            System.out.println("*Ai =  " + Ai);
            System.out.println("*ai =  " + ai);
            System.out.println("*Bi =  " + Bi);
            System.out.println("*bi =  " + bi);
            System.out.println("*c  =  " + c);
            throw new NoLiftingException("no exact lifting possible");
        }
    }

    public static boolean isHenselLift(GenPolynomial<BigInteger> C, BigInteger M, BigInteger p, List<GenPolynomial<BigInteger>> G) {
        if (C == null || C.isZERO()) {
            return false;
        }
        GenPolynomialRing pfac = C.ring;
        GenPolynomialRing mfac = new GenPolynomialRing(new ModIntegerRing(p.getVal(), true), pfac);
        GenPolynomial cl = mfac.getONE();
        for (GenPolynomial hl : G) {
            cl = cl.multiply(PolyUtil.fromIntegerCoefficients(mfac, hl));
        }
        GenPolynomial<ModInteger> cp = PolyUtil.fromIntegerCoefficients(mfac, (GenPolynomial) C);
        if (cp.equals(cl)) {
            BigInteger mip = p;
            while (mip.compareTo(M) < 0) {
                mip = mip.multiply(mip);
            }
            mfac = new GenPolynomialRing(new ModIntegerRing(mip.getVal(), false), pfac);
            cl = mfac.getONE();
            for (GenPolynomial hl2 : G) {
                cl = cl.multiply(PolyUtil.fromIntegerCoefficients(mfac, hl2));
            }
            cp = PolyUtil.fromIntegerCoefficients(mfac, (GenPolynomial) C);
            if (cp.equals(cl)) {
                return true;
            }
            System.out.println("Hensel post condition wrong!");
            System.out.println("cl    = " + cl);
            System.out.println("cp    = " + cp);
            System.out.println("cp-cl = " + cp.subtract(cl));
            System.out.println("M = " + M + ", p = " + p + ", p^e = " + mip);
            return false;
        }
        System.out.println("Hensel precondition wrong!");
        if (debug) {
            System.out.println("cl      = " + cl);
            System.out.println("cp      = " + cp);
            System.out.println("mon(cl) = " + cl.monic());
            System.out.println("mon(cp) = " + cp.monic());
            System.out.println("cp-cl   = " + cp.subtract(cl));
            System.out.println("M       = " + M + ", p = " + p);
        }
        return false;
    }

    public static boolean isHenselLift(GenPolynomial<BigInteger> C, BigInteger M, BigInteger p, GenPolynomial<BigInteger> A, GenPolynomial<BigInteger> B) {
        List G = new ArrayList(2);
        G.add(A);
        G.add(B);
        return isHenselLift((GenPolynomial) C, M, p, G);
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> boolean isHenselLift(GenPolynomial<BigInteger> C, BigInteger M, BigInteger p, HenselApprox<MOD> Ha) {
        List G = new ArrayList(2);
        G.add(Ha.A);
        G.add(Ha.B);
        return isHenselLift((GenPolynomial) C, M, p, G);
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> GenPolynomial<MOD>[] liftExtendedEuclidean(GenPolynomial<MOD> A, GenPolynomial<MOD> B, long k) throws NoLiftingException {
        if (A == null || A.isZERO() || B == null || B.isZERO()) {
            throw new IllegalArgumentException("A and B must be nonzero, A = " + A + ", B = " + B);
        }
        GenPolynomialRing fac = A.ring;
        int i = fac.nvar;
        if (r0 != 1) {
            throw new IllegalArgumentException("polynomial ring not univariate");
        }
        try {
            GenPolynomial<MOD>[] gst = A.egcd((GenPolynomial) B);
            if (gst[0].isONE()) {
                GenPolynomial S = gst[1];
                GenPolynomial T = gst[2];
                GenPolynomialRing<BigInteger> genPolynomialRing = new GenPolynomialRing(new BigInteger(), fac);
                GenPolynomial<BigInteger> one = genPolynomialRing.getONE();
                GenPolynomial Ai = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) A);
                GenPolynomial Bi = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) B);
                GenPolynomial Si = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, S);
                GenPolynomial Ti = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, T);
                BigInteger p = ((ModularRingFactory) fac.coFac).getIntegerModul();
                BigInteger modul = p;
                int i2 = 1;
                while (true) {
                    if (((long) i2) >= k) {
                        break;
                    }
                    GenPolynomial<BigInteger> e = one.subtract(Si.multiply(Ai)).subtract(Ti.multiply(Bi));
                    if (e.isZERO()) {
                        break;
                    }
                    GenPolynomial c = PolyUtil.fromIntegerCoefficients(fac, e.divide((RingElem) modul));
                    GenPolynomial<MOD> s = S.multiply(c);
                    GenPolynomial<MOD> t = T.multiply(c);
                    GenPolynomial<MOD>[] QR = s.quotientRemainder(B);
                    GenPolynomial<MOD> q = QR[0];
                    s = QR[1];
                    t = t.sum(q.multiply((GenPolynomial) A));
                    GenPolynomial<BigInteger> si = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) s);
                    GenPolynomial<BigInteger> ti = PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) t);
                    Si = Si.sum(si.multiply((RingElem) modul));
                    Ti = Ti.sum(ti.multiply((RingElem) modul));
                    modul = modul.multiply(p);
                    i2++;
                }
                logger.info("leaving on zero e in liftExtendedEuclidean");
                ModularRingFactory<MOD> modLongRing;
                if (ModLongRing.MAX_LONG.compareTo(modul.getVal()) > 0) {
                    modLongRing = new ModLongRing(modul.getVal());
                } else {
                    modLongRing = new ModIntegerRing(modul.getVal());
                }
                GenPolynomialRing<MOD> genPolynomialRing2 = new GenPolynomialRing((RingFactory) mcfac, fac);
                GenPolynomial<MOD> S2 = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing2, Si);
                GenPolynomial<MOD> T2 = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing2, Ti);
                List<GenPolynomial<MOD>> AP = new ArrayList();
                AP.add(B);
                AP.add(A);
                List<GenPolynomial<MOD>> SP = new ArrayList();
                SP.add(S2);
                SP.add(T2);
                if (!isExtendedEuclideanLift(AP, SP)) {
                    System.out.println("isExtendedEuclideanLift: false");
                }
                GenPolynomial[] rel = (GenPolynomial[]) new GenPolynomial[2];
                rel[0] = S2;
                rel[1] = T2;
                return rel;
            }
            throw new NoLiftingException("A and B not coprime, gcd = " + gst[0] + ", A = " + A + ", B = " + B);
        } catch (ArithmeticException e2) {
            throw new NoLiftingException("coefficient error " + e2);
        }
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> List<GenPolynomial<MOD>> liftExtendedEuclidean(List<GenPolynomial<MOD>> A, long k) throws NoLiftingException {
        if (A == null || A.size() == 0) {
            throw new IllegalArgumentException("A must be non null and non empty");
        }
        GenPolynomialRing fac = ((GenPolynomial) A.get(0)).ring;
        int i = fac.nvar;
        int i2;
        if (i2 != 1) {
            throw new IllegalArgumentException("polynomial ring not univariate");
        }
        int i3;
        int j;
        GenPolynomial<MOD> zero = fac.getZERO();
        int r = A.size();
        List<GenPolynomial<MOD>> Q = new ArrayList(r);
        for (i3 = 0; i3 < r; i3++) {
            Q.add(zero);
        }
        Q.set(r - 2, A.get(r - 1));
        for (j = r - 3; j >= 0; j--) {
            Q.set(j, ((GenPolynomial) A.get(j + 1)).multiply((GenPolynomial) Q.get(j + 1)));
        }
        List<GenPolynomial<MOD>> B = new ArrayList(r + 1);
        List<GenPolynomial<MOD>> lift = new ArrayList(r);
        for (i3 = 0; i3 < r; i3++) {
            B.add(zero);
            lift.add(zero);
        }
        GenPolynomial<MOD> one = fac.getONE();
        GenPolynomialRing ifac = new GenPolynomialRing(new BigInteger(), fac);
        B.add(0, one);
        GenPolynomial<MOD> b = one;
        for (j = 0; j < r - 1; j++) {
            List<GenPolynomial<MOD>> S = liftDiophant((GenPolynomial) Q.get(j), (GenPolynomial) A.get(j), (GenPolynomial) B.get(j), k);
            GenPolynomial b2 = (GenPolynomial) S.get(0);
            i2 = j + 1;
            B.set(i, PolyUtil.fromIntegerCoefficients(fac, PolyUtil.integerFromModularCoefficients(ifac, b2)));
            lift.set(j, S.get(1));
            if (debug) {
                logger.info("lift(" + j + ") = " + lift.get(j));
            }
        }
        lift.set(r - 1, b);
        if (debug) {
            logger.info("lift(" + (r - 1) + ") = " + b);
        }
        return lift;
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> List<GenPolynomial<MOD>> liftDiophant(GenPolynomial<MOD> A, GenPolynomial<MOD> B, GenPolynomial<MOD> C, long k) throws NoLiftingException {
        if (A == null || A.isZERO() || B == null || B.isZERO()) {
            throw new IllegalArgumentException("A and B must be nonzero, A = " + A + ", B = " + B + ", C = " + C);
        }
        List<GenPolynomial<MOD>> sol = new ArrayList();
        GenPolynomialRing fac = C.ring;
        if (fac.nvar != 1) {
            throw new IllegalArgumentException("polynomial ring not univariate");
        }
        int i;
        GenPolynomial<MOD> zero = fac.getZERO();
        for (i = 0; i < 2; i++) {
            sol.add(zero);
        }
        GenPolynomialRing<BigInteger> genPolynomialRing = new GenPolynomialRing(new BigInteger(), fac);
        Iterator it = C.iterator();
        while (it.hasNext()) {
            Monomial<MOD> m = (Monomial) it.next();
            RingElem a = (GcdRingElem) fac.coFac.fromInteger(((Modular) ((GcdRingElem) m.c)).getSymmetricInteger().getVal());
            i = 0;
            for (GenPolynomial d : liftDiophant((GenPolynomial) A, (GenPolynomial) B, m.e.getVal(0), k)) {
                GenPolynomial d2 = PolyUtil.fromIntegerCoefficients(fac, PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, d2)).multiply(a);
                int i2 = i + 1;
                sol.set(i, ((GenPolynomial) sol.get(i)).sum(d2));
                i = i2;
            }
        }
        A = PolyUtil.fromIntegerCoefficients(fac, PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) A));
        B = PolyUtil.fromIntegerCoefficients(fac, PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) B));
        C = PolyUtil.fromIntegerCoefficients(fac, PolyUtil.integerFromModularCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) C));
        GenPolynomial<MOD> y = B.multiply((GenPolynomial) sol.get(0)).sum(A.multiply((GenPolynomial) sol.get(1)));
        if (!y.equals(C)) {
            System.out.println("A = " + A + ", B = " + B);
            System.out.println("s1 = " + sol.get(0) + ", s2 = " + sol.get(1));
            System.out.println("Error: A*r1 + B*r2 = " + y + " : " + fac.coFac);
        }
        return sol;
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> List<GenPolynomial<MOD>> liftDiophant(List<GenPolynomial<MOD>> A, GenPolynomial<MOD> C, long k) throws NoLiftingException {
        List<GenPolynomial<MOD>> sol = new ArrayList();
        GenPolynomialRing fac = C.ring;
        int i = fac.nvar;
        if (r0 != 1) {
            throw new IllegalArgumentException("polynomial ring not univariate");
        }
        int i2;
        GenPolynomial<MOD> zero = fac.getZERO();
        for (i2 = 0; i2 < A.size(); i2++) {
            sol.add(zero);
        }
        GenPolynomialRing ifac = new GenPolynomialRing(new BigInteger(), fac);
        Iterator it = C.iterator();
        while (it.hasNext()) {
            Monomial<MOD> m = (Monomial) it.next();
            List list = A;
            List<GenPolynomial<MOD>> S = liftDiophant(list, m.e.getVal(0), k);
            GcdRingElem a = (GcdRingElem) m.c;
            RingElem a2 = (GcdRingElem) fac.coFac.fromInteger(((Modular) a).getSymmetricInteger().getVal());
            i2 = 0;
            for (GenPolynomial d : S) {
                GenPolynomial genPolynomial = (GenPolynomial) sol.get(i2);
                int i3 = i2 + 1;
                sol.set(i2, r18.sum(PolyUtil.fromIntegerCoefficients(fac, PolyUtil.integerFromModularCoefficients(ifac, d)).multiply(a2)));
                i2 = i3;
            }
        }
        return sol;
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> List<GenPolynomial<MOD>> liftDiophant(GenPolynomial<MOD> A, GenPolynomial<MOD> B, long e, long k) throws NoLiftingException {
        if (A == null || A.isZERO() || B == null || B.isZERO()) {
            throw new IllegalArgumentException("A and B must be nonzero, A = " + A + ", B = " + B);
        }
        List<GenPolynomial<MOD>> sol = new ArrayList();
        int i = A.ring.nvar;
        if (r0 != 1) {
            throw new IllegalArgumentException("polynomial ring not univariate");
        }
        GenPolynomial<MOD>[] lee = liftExtendedEuclidean(B, A, k);
        GenPolynomial<MOD> s1 = lee[0];
        GenPolynomial<MOD> s2 = lee[1];
        if (e == 0) {
            sol.add(s1);
            sol.add(s2);
        } else {
            GenPolynomialRing fac = s1.ring;
            GenPolynomialRing ifac = new GenPolynomialRing(new BigInteger(), fac);
            A = PolyUtil.fromIntegerCoefficients(fac, PolyUtil.integerFromModularCoefficients(ifac, (GenPolynomial) A));
            B = PolyUtil.fromIntegerCoefficients(fac, PolyUtil.integerFromModularCoefficients(ifac, (GenPolynomial) B));
            GenPolynomial xe = fac.univariate(0, e);
            GenPolynomial<MOD>[] QR = s1.multiply(xe).quotientRemainder(A);
            GenPolynomial<MOD> q = QR[0];
            GenPolynomial r1 = QR[1];
            GenPolynomial r2 = s2.multiply(xe).sum(q.multiply((GenPolynomial) B));
            sol.add(r1);
            sol.add(r2);
            GenPolynomial<MOD> y = B.multiply(r1).sum(A.multiply(r2));
            if (!y.equals(xe)) {
                System.out.println("A = " + A + ", B = " + B);
                System.out.println("r1 = " + r1 + ", r2 = " + r2);
                System.out.println("Error: A*r1 + B*r2 = " + y);
            }
        }
        return sol;
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> List<GenPolynomial<MOD>> liftDiophant(List<GenPolynomial<MOD>> A, long e, long k) throws NoLiftingException {
        List<GenPolynomial<MOD>> sol = new ArrayList();
        int i = ((GenPolynomial) A.get(0)).ring.nvar;
        if (r0 != 1) {
            throw new IllegalArgumentException("polynomial ring not univariate");
        }
        List<GenPolynomial<MOD>> lee = liftExtendedEuclidean(A, k);
        if (e == 0) {
            return lee;
        }
        GenPolynomialRing fac = ((GenPolynomial) lee.get(0)).ring;
        GenPolynomialRing ifac = new GenPolynomialRing(new BigInteger(), fac);
        List<GenPolynomial<MOD>> S = new ArrayList(lee.size());
        for (GenPolynomial a : lee) {
            S.add(PolyUtil.fromIntegerCoefficients(fac, PolyUtil.integerFromModularCoefficients(ifac, a)));
        }
        GenPolynomial<MOD> xe = fac.univariate(0, e);
        int i2 = 0;
        for (GenPolynomial<MOD> s : S) {
            int i3 = i2 + 1;
            sol.add(s.multiply((GenPolynomial) xe).remainder((GenPolynomial) A.get(i2)));
            i2 = i3;
        }
        return sol;
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> boolean isDiophantLift(GenPolynomial<MOD> A, GenPolynomial<MOD> B, GenPolynomial<MOD> S1, GenPolynomial<MOD> S2, GenPolynomial<MOD> C) {
        GenPolynomialRing fac = C.ring;
        GenPolynomialRing ifac = new GenPolynomialRing(new BigInteger(), fac);
        GenPolynomial<MOD> a = PolyUtil.fromIntegerCoefficients(fac, PolyUtil.integerFromModularCoefficients(ifac, (GenPolynomial) A));
        GenPolynomial<MOD> b = PolyUtil.fromIntegerCoefficients(fac, PolyUtil.integerFromModularCoefficients(ifac, (GenPolynomial) B));
        GenPolynomial s1 = PolyUtil.fromIntegerCoefficients(fac, PolyUtil.integerFromModularCoefficients(ifac, (GenPolynomial) S1));
        GenPolynomial s2 = PolyUtil.fromIntegerCoefficients(fac, PolyUtil.integerFromModularCoefficients(ifac, (GenPolynomial) S2));
        GenPolynomial<MOD> t = a.multiply(s1).sum(b.multiply(s2));
        if (t.equals(C)) {
            return true;
        }
        if (debug) {
            System.out.println("a  = " + a);
            System.out.println("b  = " + b);
            System.out.println("s1 = " + s1);
            System.out.println("s2 = " + s2);
            System.out.println("t  = " + t);
            System.out.println("C  = " + C);
        }
        return false;
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> boolean isExtendedEuclideanLift(List<GenPolynomial<MOD>> A, List<GenPolynomial<MOD>> S) {
        return isDiophantLift(A, S, ((GenPolynomial) A.get(0)).ring.getONE());
    }

    public static <MOD extends GcdRingElem<MOD> & Modular> boolean isDiophantLift(List<GenPolynomial<MOD>> A, List<GenPolynomial<MOD>> S, GenPolynomial<MOD> C) {
        GenPolynomialRing fac = ((GenPolynomial) A.get(0)).ring;
        GenPolynomialRing ifac = new GenPolynomialRing(new BigInteger(), fac);
        List<GenPolynomial<MOD>> B = new ArrayList(A.size());
        int i = 0;
        for (GenPolynomial<MOD> ai : A) {
            GenPolynomial b = fac.getONE();
            int j = 0;
            for (GenPolynomial aj : A) {
                if (i != j) {
                    b = b.multiply(aj);
                }
                j++;
            }
            B.add(PolyUtil.fromIntegerCoefficients(fac, PolyUtil.integerFromModularCoefficients(ifac, b)));
            i++;
        }
        GenPolynomial<MOD> t = fac.getZERO();
        i = 0;
        for (GenPolynomial<MOD> a : B) {
            int i2 = i + 1;
            t = t.sum(a.multiply(PolyUtil.fromIntegerCoefficients(fac, PolyUtil.integerFromModularCoefficients(ifac, (GenPolynomial) S.get(i)))));
            i = i2;
        }
        if (t.equals(C)) {
            return true;
        }
        if (debug) {
            System.out.println("no diophant lift!");
            System.out.println("A = " + A);
            System.out.println("B = " + B);
            System.out.println("S = " + S);
            System.out.println("C = " + C);
            System.out.println("t = " + t);
        }
        return false;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <MOD extends edu.jas.structure.GcdRingElem<MOD> & edu.jas.arith.Modular> java.util.List<edu.jas.poly.GenPolynomial<MOD>> liftHenselMonic(edu.jas.poly.GenPolynomial<edu.jas.arith.BigInteger> r40, java.util.List<edu.jas.poly.GenPolynomial<MOD>> r41, long r42) throws edu.jas.ufd.NoLiftingException {
        /*
        if (r40 == 0) goto L_0x0010;
    L_0x0002:
        r36 = r40.isZERO();
        if (r36 != 0) goto L_0x0010;
    L_0x0008:
        if (r41 == 0) goto L_0x0010;
    L_0x000a:
        r36 = r41.size();
        if (r36 != 0) goto L_0x0018;
    L_0x0010:
        r36 = new java.lang.IllegalArgumentException;
        r37 = "C must be nonzero and F must be nonempty";
        r36.<init>(r37);
        throw r36;
    L_0x0018:
        r0 = r40;
        r0 = r0.ring;
        r18 = r0;
        r0 = r18;
        r0 = r0.nvar;
        r36 = r0;
        r37 = 1;
        r0 = r36;
        r1 = r37;
        if (r0 == r1) goto L_0x0034;
    L_0x002c:
        r36 = new java.lang.IllegalArgumentException;
        r37 = "polynomial ring not univariate";
        r36.<init>(r37);
        throw r36;
    L_0x0034:
        r25 = new java.util.ArrayList;
        r36 = r41.size();
        r0 = r25;
        r1 = r36;
        r0.<init>(r1);
        r36 = 0;
        r0 = r41;
        r1 = r36;
        r36 = r0.get(r1);
        r36 = (edu.jas.poly.GenPolynomial) r36;
        r0 = r36;
        r0 = r0.ring;
        r33 = r0;
        r0 = r33;
        r0 = r0.coFac;
        r32 = r0;
        r7 = r32;
        r7 = (edu.jas.arith.ModularRingFactory) r7;
        r6 = r7.getIntegerModul();
        r30 = r41.size();
        r36 = 1;
        r0 = r30;
        r1 = r36;
        if (r0 != r1) goto L_0x00c5;
    L_0x006d:
        r36 = 0;
        r0 = r41;
        r1 = r36;
        r17 = r0.get(r1);
        r17 = (edu.jas.poly.GenPolynomial) r17;
        r36 = edu.jas.arith.ModLongRing.MAX_LONG;
        r37 = r6.getVal();
        r36 = r36.compareTo(r37);
        if (r36 <= 0) goto L_0x00b7;
    L_0x0085:
        r27 = new edu.jas.arith.ModLongRing;
        r36 = r6.getVal();
        r0 = r27;
        r1 = r36;
        r0.<init>(r1);
    L_0x0092:
        r28 = new edu.jas.poly.GenPolynomialRing;
        r0 = r28;
        r1 = r27;
        r2 = r18;
        r0.<init>(r1, r2);
        r0 = r18;
        r1 = r17;
        r36 = edu.jas.poly.PolyUtil.integerFromModularCoefficients(r0, r1);
        r0 = r28;
        r1 = r36;
        r17 = edu.jas.poly.PolyUtil.fromIntegerCoefficients(r0, r1);
        r0 = r25;
        r1 = r17;
        r0.add(r1);
        r26 = r25;
    L_0x00b6:
        return r26;
    L_0x00b7:
        r27 = new edu.jas.arith.ModIntegerRing;
        r36 = r6.getVal();
        r0 = r27;
        r1 = r36;
        r0.<init>(r1);
        goto L_0x0092;
    L_0x00c5:
        r22 = new edu.jas.poly.GenPolynomialRing;
        r36 = new edu.jas.arith.BigInteger;
        r36.<init>();
        r0 = r22;
        r1 = r36;
        r2 = r18;
        r0.<init>(r1, r2);
        r0 = r22;
        r1 = r41;
        r4 = edu.jas.poly.PolyUtil.integerFromModularCoefficients(r0, r1);
        r36 = 1;
        r36 = r36 + r42;
        r0 = r41;
        r1 = r36;
        r8 = liftExtendedEuclidean(r0, r1);
        r36 = logger;
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r38 = "EE lift = ";
        r37 = r37.append(r38);
        r0 = r37;
        r37 = r0.append(r8);
        r37 = r37.toString();
        r36.info(r37);
        r0 = r22;
        r36 = edu.jas.poly.PolyUtil.integerFromModularCoefficients(r0, r8);
        r0 = r33;
        r1 = r36;
        r11 = edu.jas.poly.PolyUtil.fromIntegerCoefficients(r0, r1);
        r0 = r41;
        isExtendedEuclideanLift(r0, r11);	 Catch:{ RuntimeException -> 0x015b }
    L_0x0116:
        r0 = r22;
        r9 = edu.jas.poly.PolyUtil.integerFromModularCoefficients(r0, r8);
        r27 = r7;
        r31 = r27.getIntegerModul();
        r29 = r31;
        r28 = new edu.jas.poly.GenPolynomialRing;
        r0 = r28;
        r1 = r27;
        r2 = r18;
        r0.<init>(r1, r2);
        r0 = r28;
        r10 = edu.jas.poly.PolyUtil.fromIntegerCoefficients(r0, r9);
        r20 = 1;
    L_0x0137:
        r0 = r20;
        r0 = (long) r0;
        r36 = r0;
        r36 = (r36 > r42 ? 1 : (r36 == r42 ? 0 : -1));
        if (r36 >= 0) goto L_0x0173;
    L_0x0140:
        r14 = r18.getONE();
        r21 = r4.iterator();
    L_0x0148:
        r36 = r21.hasNext();
        if (r36 == 0) goto L_0x0160;
    L_0x014e:
        r19 = r21.next();
        r19 = (edu.jas.poly.GenPolynomial) r19;
        r0 = r19;
        r14 = r14.multiply(r0);
        goto L_0x0148;
    L_0x015b:
        r13 = move-exception;
        r13.printStackTrace();
        goto L_0x0116;
    L_0x0160:
        r0 = r40;
        r14 = r0.subtract(r14);
        r36 = r14.isZERO();
        if (r36 == 0) goto L_0x01ab;
    L_0x016c:
        r36 = logger;
        r37 = "leaving on zero e";
        r36.info(r37);
    L_0x0173:
        r0 = r31;
        r1 = r42;
        r29 = edu.jas.structure.Power.positivePower(r0, r1);
        r29 = (edu.jas.arith.BigInteger) r29;
        r36 = edu.jas.arith.ModLongRing.MAX_LONG;
        r37 = r29.getVal();
        r36 = r36.compareTo(r37);
        if (r36 <= 0) goto L_0x027e;
    L_0x0189:
        r27 = new edu.jas.arith.ModLongRing;
        r36 = r29.getVal();
        r0 = r27;
        r1 = r36;
        r0.<init>(r1);
    L_0x0196:
        r28 = new edu.jas.poly.GenPolynomialRing;
        r0 = r28;
        r1 = r27;
        r2 = r18;
        r0.<init>(r1, r2);
        r0 = r28;
        r25 = edu.jas.poly.PolyUtil.fromIntegerCoefficients(r0, r4);
        r26 = r25;
        goto L_0x00b6;
    L_0x01ab:
        r0 = r29;
        r14 = r14.divide(r0);	 Catch:{ RuntimeException -> 0x01fa }
        r0 = r28;
        r12 = edu.jas.poly.PolyUtil.fromIntegerCoefficients(r0, r14);
        r34 = new java.util.ArrayList;
        r36 = r8.size();
        r0 = r34;
        r1 = r36;
        r0.<init>(r1);
        r23 = 0;
        r21 = r10.iterator();
    L_0x01ca:
        r36 = r21.hasNext();
        if (r36 == 0) goto L_0x01ff;
    L_0x01d0:
        r17 = r21.next();
        r17 = (edu.jas.poly.GenPolynomial) r17;
        r0 = r17;
        r17 = r0.multiply(r12);
        r24 = r23 + 1;
        r0 = r41;
        r1 = r23;
        r36 = r0.get(r1);
        r36 = (edu.jas.poly.GenPolynomial) r36;
        r0 = r17;
        r1 = r36;
        r17 = r0.remainder(r1);
        r0 = r34;
        r1 = r17;
        r0.add(r1);
        r23 = r24;
        goto L_0x01ca;
    L_0x01fa:
        r15 = move-exception;
        r15.printStackTrace();
        throw r15;
    L_0x01ff:
        r0 = r22;
        r1 = r34;
        r35 = edu.jas.poly.PolyUtil.integerFromModularCoefficients(r0, r1);
        r5 = new java.util.ArrayList;
        r36 = r41.size();
        r0 = r36;
        r5.<init>(r0);
        r23 = 0;
        r21 = r4.iterator();
    L_0x0218:
        r36 = r21.hasNext();
        if (r36 == 0) goto L_0x0248;
    L_0x021e:
        r16 = r21.next();
        r16 = (edu.jas.poly.GenPolynomial) r16;
        r24 = r23 + 1;
        r0 = r35;
        r1 = r23;
        r36 = r0.get(r1);
        r36 = (edu.jas.poly.GenPolynomial) r36;
        r0 = r36;
        r1 = r29;
        r36 = r0.multiply(r1);
        r0 = r16;
        r1 = r36;
        r16 = r0.sum(r1);
        r0 = r16;
        r5.add(r0);
        r23 = r24;
        goto L_0x0218;
    L_0x0248:
        r4 = r5;
        r0 = r29;
        r1 = r31;
        r29 = r0.multiply(r1);
        r0 = r20;
        r0 = (long) r0;
        r36 = r0;
        r38 = 1;
        r38 = r42 - r38;
        r36 = (r36 > r38 ? 1 : (r36 == r38 ? 0 : -1));
        if (r36 < 0) goto L_0x027a;
    L_0x025e:
        r36 = logger;
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r38 = "e != 0 for k = ";
        r37 = r37.append(r38);
        r0 = r37;
        r1 = r42;
        r37 = r0.append(r1);
        r37 = r37.toString();
        r36.info(r37);
    L_0x027a:
        r20 = r20 + 1;
        goto L_0x0137;
    L_0x027e:
        r27 = new edu.jas.arith.ModIntegerRing;
        r36 = r29.getVal();
        r0 = r27;
        r1 = r36;
        r0.<init>(r1);
        goto L_0x0196;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.ufd.HenselUtil.liftHenselMonic(edu.jas.poly.GenPolynomial, java.util.List, long):java.util.List<edu.jas.poly.GenPolynomial<MOD>>");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <MOD extends edu.jas.structure.GcdRingElem<MOD> & edu.jas.arith.Modular> java.util.List<edu.jas.poly.GenPolynomial<MOD>> liftHensel(edu.jas.poly.GenPolynomial<edu.jas.arith.BigInteger> r50, java.util.List<edu.jas.poly.GenPolynomial<MOD>> r51, long r52, edu.jas.arith.BigInteger r54) throws edu.jas.ufd.NoLiftingException {
        /*
        if (r50 == 0) goto L_0x0010;
    L_0x0002:
        r46 = r50.isZERO();
        if (r46 != 0) goto L_0x0010;
    L_0x0008:
        if (r51 == 0) goto L_0x0010;
    L_0x000a:
        r46 = r51.size();
        if (r46 != 0) goto L_0x0018;
    L_0x0010:
        r46 = new java.lang.IllegalArgumentException;
        r47 = "C must be nonzero and F must be nonempty";
        r46.<init>(r47);
        throw r46;
    L_0x0018:
        r0 = r50;
        r0 = r0.ring;
        r26 = r0;
        r0 = r26;
        r0 = r0.nvar;
        r46 = r0;
        r47 = 1;
        r0 = r46;
        r1 = r47;
        if (r0 == r1) goto L_0x0034;
    L_0x002c:
        r46 = new java.lang.IllegalArgumentException;
        r47 = "polynomial ring not univariate";
        r46.<init>(r47);
        throw r46;
    L_0x0034:
        r34 = new java.util.ArrayList;
        r46 = r51.size();
        r0 = r34;
        r1 = r46;
        r0.<init>(r1);
        r46 = 0;
        r0 = r51;
        r1 = r46;
        r46 = r0.get(r1);
        r46 = (edu.jas.poly.GenPolynomial) r46;
        r0 = r46;
        r0 = r0.ring;
        r42 = r0;
        r0 = r42;
        r0 = r0.coFac;
        r41 = r0;
        r8 = r41;
        r8 = (edu.jas.arith.ModularRingFactory) r8;
        r7 = r8.getIntegerModul();
        r39 = r51.size();
        r46 = 1;
        r0 = r39;
        r1 = r46;
        if (r0 != r1) goto L_0x00c5;
    L_0x006d:
        r46 = 0;
        r0 = r51;
        r1 = r46;
        r25 = r0.get(r1);
        r25 = (edu.jas.poly.GenPolynomial) r25;
        r46 = edu.jas.arith.ModLongRing.MAX_LONG;
        r47 = r7.getVal();
        r46 = r46.compareTo(r47);
        if (r46 <= 0) goto L_0x00b7;
    L_0x0085:
        r36 = new edu.jas.arith.ModLongRing;
        r46 = r7.getVal();
        r0 = r36;
        r1 = r46;
        r0.<init>(r1);
    L_0x0092:
        r37 = new edu.jas.poly.GenPolynomialRing;
        r0 = r37;
        r1 = r36;
        r2 = r26;
        r0.<init>(r1, r2);
        r0 = r26;
        r1 = r25;
        r46 = edu.jas.poly.PolyUtil.integerFromModularCoefficients(r0, r1);
        r0 = r37;
        r1 = r46;
        r25 = edu.jas.poly.PolyUtil.fromIntegerCoefficients(r0, r1);
        r0 = r34;
        r1 = r25;
        r0.add(r1);
        r35 = r34;
    L_0x00b6:
        return r35;
    L_0x00b7:
        r36 = new edu.jas.arith.ModIntegerRing;
        r46 = r7.getVal();
        r0 = r36;
        r1 = r46;
        r0.<init>(r1);
        goto L_0x0092;
    L_0x00c5:
        r17 = r54;
        r29 = 1;
    L_0x00c9:
        r46 = r51.size();
        r0 = r29;
        r1 = r46;
        if (r0 >= r1) goto L_0x00de;
    L_0x00d3:
        r0 = r50;
        r1 = r17;
        r50 = r0.multiply(r1);
        r29 = r29 + 1;
        goto L_0x00c9;
    L_0x00de:
        r46 = r17.getVal();
        r0 = r46;
        r19 = r8.fromInteger(r0);
        r19 = (edu.jas.structure.GcdRingElem) r19;
        r6 = new java.util.ArrayList;
        r46 = r51.size();
        r0 = r46;
        r6.<init>(r0);
        r30 = r51.iterator();
    L_0x00f9:
        r46 = r30.hasNext();
        if (r46 == 0) goto L_0x0113;
    L_0x00ff:
        r28 = r30.next();
        r28 = (edu.jas.poly.GenPolynomial) r28;
        r14 = r28.monic();
        r0 = r19;
        r14 = r14.multiply(r0);
        r6.add(r14);
        goto L_0x00f9;
    L_0x0113:
        r51 = r6;
        r31 = new edu.jas.poly.GenPolynomialRing;
        r46 = new edu.jas.arith.BigInteger;
        r46.<init>();
        r0 = r31;
        r1 = r46;
        r2 = r26;
        r0.<init>(r1, r2);
        r0 = r31;
        r1 = r51;
        r4 = edu.jas.poly.PolyUtil.integerFromModularCoefficients(r0, r1);
        r30 = r4.iterator();
    L_0x0131:
        r46 = r30.hasNext();
        if (r46 == 0) goto L_0x014f;
    L_0x0137:
        r13 = r30.next();
        r13 = (edu.jas.poly.GenPolynomial) r13;
        r46 = r13.isZERO();
        if (r46 != 0) goto L_0x0131;
    L_0x0143:
        r22 = r13.leadingExpVector();
        r0 = r22;
        r1 = r17;
        r13.doPutToMap(r0, r1);
        goto L_0x0131;
    L_0x014f:
        r46 = 1;
        r46 = r46 + r52;
        r0 = r51;
        r1 = r46;
        r9 = liftExtendedEuclidean(r0, r1);
        r46 = logger;
        r47 = new java.lang.StringBuilder;
        r47.<init>();
        r48 = "EE lift = ";
        r47 = r47.append(r48);
        r0 = r47;
        r47 = r0.append(r9);
        r47 = r47.toString();
        r46.info(r47);
        r0 = r31;
        r46 = edu.jas.poly.PolyUtil.integerFromModularCoefficients(r0, r9);
        r0 = r42;
        r1 = r46;
        r12 = edu.jas.poly.PolyUtil.fromIntegerCoefficients(r0, r1);
        r0 = r51;
        isExtendedEuclideanLift(r0, r12);	 Catch:{ RuntimeException -> 0x01cf }
    L_0x0188:
        r0 = r31;
        r10 = edu.jas.poly.PolyUtil.integerFromModularCoefficients(r0, r9);
        r36 = r8;
        r40 = r36.getIntegerModul();
        r38 = r40;
        r37 = new edu.jas.poly.GenPolynomialRing;
        r0 = r37;
        r1 = r36;
        r2 = r26;
        r0.<init>(r1, r2);
        r0 = r37;
        r11 = edu.jas.poly.PolyUtil.fromIntegerCoefficients(r0, r10);
        r29 = 1;
    L_0x01a9:
        r0 = r29;
        r0 = (long) r0;
        r46 = r0;
        r46 = (r46 > r52 ? 1 : (r46 == r52 ? 0 : -1));
        if (r46 >= 0) goto L_0x01e9;
    L_0x01b2:
        r21 = r26.getONE();
        r30 = r4.iterator();
    L_0x01ba:
        r46 = r30.hasNext();
        if (r46 == 0) goto L_0x01d4;
    L_0x01c0:
        r27 = r30.next();
        r27 = (edu.jas.poly.GenPolynomial) r27;
        r0 = r21;
        r1 = r27;
        r21 = r0.multiply(r1);
        goto L_0x01ba;
    L_0x01cf:
        r20 = move-exception;
        r20.printStackTrace();
        goto L_0x0188;
    L_0x01d4:
        r0 = r50;
        r1 = r21;
        r21 = r0.subtract(r1);
        r46 = r21.isZERO();
        if (r46 == 0) goto L_0x0216;
    L_0x01e2:
        r46 = logger;
        r47 = "leaving on zero e";
        r46.info(r47);
    L_0x01e9:
        r45 = edu.jas.ufd.GCDFactory.getImplementation(r17);
        r5 = new java.util.ArrayList;
        r46 = r51.size();
        r0 = r46;
        r5.<init>(r0);
        r30 = r4.iterator();
    L_0x01fc:
        r46 = r30.hasNext();
        if (r46 == 0) goto L_0x02ef;
    L_0x0202:
        r15 = r30.next();
        r15 = (edu.jas.poly.GenPolynomial) r15;
        r18 = 0;
        r0 = r45;
        r18 = r0.basePrimitivePart(r15);
        r0 = r18;
        r5.add(r0);
        goto L_0x01fc;
    L_0x0216:
        r0 = r21;
        r1 = r38;
        r21 = r0.divide(r1);	 Catch:{ RuntimeException -> 0x026b }
        r0 = r37;
        r1 = r21;
        r16 = edu.jas.poly.PolyUtil.fromIntegerCoefficients(r0, r1);
        r43 = new java.util.ArrayList;
        r46 = r9.size();
        r0 = r43;
        r1 = r46;
        r0.<init>(r1);
        r32 = 0;
        r30 = r11.iterator();
    L_0x0239:
        r46 = r30.hasNext();
        if (r46 == 0) goto L_0x0270;
    L_0x023f:
        r25 = r30.next();
        r25 = (edu.jas.poly.GenPolynomial) r25;
        r0 = r25;
        r1 = r16;
        r25 = r0.multiply(r1);
        r33 = r32 + 1;
        r0 = r51;
        r1 = r32;
        r46 = r0.get(r1);
        r46 = (edu.jas.poly.GenPolynomial) r46;
        r0 = r25;
        r1 = r46;
        r25 = r0.remainder(r1);
        r0 = r43;
        r1 = r25;
        r0.add(r1);
        r32 = r33;
        goto L_0x0239;
    L_0x026b:
        r23 = move-exception;
        r23.printStackTrace();
        throw r23;
    L_0x0270:
        r0 = r31;
        r1 = r43;
        r44 = edu.jas.poly.PolyUtil.integerFromModularCoefficients(r0, r1);
        r5 = new java.util.ArrayList;
        r46 = r51.size();
        r0 = r46;
        r5.<init>(r0);
        r32 = 0;
        r30 = r4.iterator();
    L_0x0289:
        r46 = r30.hasNext();
        if (r46 == 0) goto L_0x02b9;
    L_0x028f:
        r24 = r30.next();
        r24 = (edu.jas.poly.GenPolynomial) r24;
        r33 = r32 + 1;
        r0 = r44;
        r1 = r32;
        r46 = r0.get(r1);
        r46 = (edu.jas.poly.GenPolynomial) r46;
        r0 = r46;
        r1 = r38;
        r46 = r0.multiply(r1);
        r0 = r24;
        r1 = r46;
        r24 = r0.sum(r1);
        r0 = r24;
        r5.add(r0);
        r32 = r33;
        goto L_0x0289;
    L_0x02b9:
        r4 = r5;
        r0 = r38;
        r1 = r40;
        r38 = r0.multiply(r1);
        r0 = r29;
        r0 = (long) r0;
        r46 = r0;
        r48 = 1;
        r48 = r52 - r48;
        r46 = (r46 > r48 ? 1 : (r46 == r48 ? 0 : -1));
        if (r46 < 0) goto L_0x02eb;
    L_0x02cf:
        r46 = logger;
        r47 = new java.lang.StringBuilder;
        r47.<init>();
        r48 = "e != 0 for k = ";
        r47 = r47.append(r48);
        r0 = r47;
        r1 = r52;
        r47 = r0.append(r1);
        r47 = r47.toString();
        r46.info(r47);
    L_0x02eb:
        r29 = r29 + 1;
        goto L_0x01a9;
    L_0x02ef:
        r4 = r5;
        r0 = r40;
        r1 = r52;
        r38 = edu.jas.structure.Power.positivePower(r0, r1);
        r38 = (edu.jas.arith.BigInteger) r38;
        r46 = edu.jas.arith.ModLongRing.MAX_LONG;
        r47 = r38.getVal();
        r46 = r46.compareTo(r47);
        if (r46 <= 0) goto L_0x0328;
    L_0x0306:
        r36 = new edu.jas.arith.ModLongRing;
        r46 = r38.getVal();
        r0 = r36;
        r1 = r46;
        r0.<init>(r1);
    L_0x0313:
        r37 = new edu.jas.poly.GenPolynomialRing;
        r0 = r37;
        r1 = r36;
        r2 = r26;
        r0.<init>(r1, r2);
        r0 = r37;
        r34 = edu.jas.poly.PolyUtil.fromIntegerCoefficients(r0, r4);
        r35 = r34;
        goto L_0x00b6;
    L_0x0328:
        r36 = new edu.jas.arith.ModIntegerRing;
        r46 = r38.getVal();
        r0 = r36;
        r1 = r46;
        r0.<init>(r1);
        goto L_0x0313;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.ufd.HenselUtil.liftHensel(edu.jas.poly.GenPolynomial, java.util.List, long, edu.jas.arith.BigInteger):java.util.List<edu.jas.poly.GenPolynomial<MOD>>");
    }
}
