package edu.jas.fd;

import edu.jas.gbmod.SolvableQuotient;
import edu.jas.gbmod.SolvableQuotientRing;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.poly.GenSolvablePolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.poly.RecSolvablePolynomial;
import edu.jas.poly.RecSolvablePolynomialRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public class FDUtil {
    private static boolean debug;
    private static final Logger logger;

    static {
        logger = Logger.getLogger(FDUtil.class);
        debug = logger.isDebugEnabled();
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<C> leftBaseSparsePseudoRemainder(GenSolvablePolynomial<C> P, GenSolvablePolynomial<C> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException(P.toString() + " division by zero " + S);
        } else if (P.isZERO()) {
            return P;
        } else {
            if (S.isConstant()) {
                return P.ring.getZERO();
            }
            if (!(P instanceof RecSolvablePolynomial) || ((RecSolvablePolynomial) P).ring.coeffTable.isEmpty()) {
                GreatestCommonDivisorAbstract<C> fd = new GreatestCommonDivisorSimple(P.ring.coFac);
                ExpVector e = S.leadingExpVector();
                GenSolvablePolynomial<C> r = P;
                while (!r.isZERO()) {
                    ExpVector f = r.leadingExpVector();
                    if (!f.multipleOf(e)) {
                        break;
                    }
                    GcdRingElem a = (GcdRingElem) r.leadingBaseCoefficient();
                    GenSolvablePolynomial<C> h = S.multiplyLeft(f.subtract(e));
                    C[] oc = fd.leftOreCond(a, (GcdRingElem) h.leadingBaseCoefficient());
                    r = (GenSolvablePolynomial) r.multiplyLeft(oc[0]).subtract((GenPolynomial) h.multiplyLeft(oc[1]));
                }
                return r;
            }
            throw new UnsupportedOperationException("RecSolvablePolynomial with twisted coeffs not supported");
        }
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<C> rightBaseSparsePseudoRemainder(GenSolvablePolynomial<C> P, GenSolvablePolynomial<C> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException(P.toString() + " division by zero " + S);
        } else if (P.isZERO()) {
            return P;
        } else {
            if (S.isConstant()) {
                return P.ring.getZERO();
            }
            if (!(P instanceof RecSolvablePolynomial) || ((RecSolvablePolynomial) P).ring.coeffTable.isEmpty()) {
                GreatestCommonDivisorAbstract<C> fd = new GreatestCommonDivisorSimple(P.ring.coFac);
                ExpVector e = S.leadingExpVector();
                GenSolvablePolynomial<C> r = P;
                while (!r.isZERO()) {
                    ExpVector f = r.leadingExpVector();
                    if (!f.multipleOf(e)) {
                        break;
                    }
                    GenSolvablePolynomial<C> h = S.multiply(f.subtract(e));
                    C[] oc = fd.rightOreCond((GcdRingElem) r.leadingBaseCoefficient(), (GcdRingElem) h.leadingBaseCoefficient());
                    r = (GenSolvablePolynomial) r.multiply(oc[0]).subtract((GenPolynomial) h.multiply(oc[1]));
                }
                return r;
            }
            throw new UnsupportedOperationException("RecSolvablePolynomial with twisted coeffs not supported");
        }
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<C> leftBasePseudoQuotient(GenSolvablePolynomial<C> P, GenSolvablePolynomial<C> S) {
        return leftBasePseudoQuotientRemainder(P, S)[0];
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<C>[] leftBasePseudoQuotientRemainder(GenSolvablePolynomial<C> P, GenSolvablePolynomial<C> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException(P.toString() + " division by zero " + S);
        }
        GenSolvablePolynomial<C>[] ret = new GenSolvablePolynomial[]{null, null};
        if (P.isZERO() || S.isONE()) {
            ret[0] = P;
            ret[1] = S.ring.getZERO();
        } else if (!(P instanceof RecSolvablePolynomial) || ((RecSolvablePolynomial) P).ring.coeffTable.isEmpty()) {
            GreatestCommonDivisorAbstract<C> fd = new GreatestCommonDivisorSimple(P.ring.coFac);
            ExpVector e = S.leadingExpVector();
            GenSolvablePolynomial<C> r = P;
            GenSolvablePolynomial<C> q = S.ring.getZERO().copy();
            while (!r.isZERO()) {
                ExpVector f = r.leadingExpVector();
                if (!f.multipleOf(e)) {
                    break;
                }
                GcdRingElem a = (GcdRingElem) r.leadingBaseCoefficient();
                f = f.subtract(e);
                GenSolvablePolynomial<C> h = S.multiplyLeft(f);
                C[] oc = fd.leftOreCond(a, (GcdRingElem) h.leadingBaseCoefficient());
                RingElem ga = oc[0];
                RingElem gc = oc[1];
                q = (GenSolvablePolynomial) q.multiplyLeft(ga).sum(gc, f);
                r = (GenSolvablePolynomial) r.multiplyLeft(ga).subtract((GenPolynomial) h.multiplyLeft(gc));
            }
            ret[0] = q;
            ret[1] = r;
        } else {
            throw new UnsupportedOperationException("RecSolvablePolynomial with twisted coeffs not supported");
        }
        return ret;
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<GenPolynomial<C>> recursiveSparsePseudoRemainder(GenSolvablePolynomial<GenPolynomial<C>> P, GenSolvablePolynomial<GenPolynomial<C>> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException(P + " division by zero " + S);
        } else if (P == null || P.isZERO()) {
            return P;
        } else {
            GreatestCommonDivisorAbstract<C> fd = new GreatestCommonDivisorSimple(P.ring.coFac.coFac);
            ExpVector e = S.leadingExpVector();
            GenSolvablePolynomial<GenPolynomial<C>> r = P;
            while (!r.isZERO()) {
                ExpVector f = r.leadingExpVector();
                if (!f.multipleOf(e)) {
                    return r;
                }
                GenSolvablePolynomial a = (GenSolvablePolynomial) r.leadingBaseCoefficient();
                GenSolvablePolynomial<GenPolynomial<C>> h = S.multiplyLeft(f.subtract(e));
                GenSolvablePolynomial<C>[] oc = fd.leftOreCond(a, (GenSolvablePolynomial) h.leadingBaseCoefficient());
                r = (GenSolvablePolynomial) r.multiplyLeft(oc[0]).subtract((GenPolynomial) h.multiplyLeft(oc[1]));
            }
            return r;
        }
    }

    public static <C extends GcdRingElem<C>> boolean isRecursivePseudoQuotientRemainder(GenSolvablePolynomial<GenPolynomial<C>> P, GenSolvablePolynomial<GenPolynomial<C>> S, GenSolvablePolynomial<GenPolynomial<C>> q, GenSolvablePolynomial<GenPolynomial<C>> r) {
        long i;
        GenSolvablePolynomial<GenPolynomial<C>> rhs = (GenSolvablePolynomial) q.multiply((GenSolvablePolynomial) S).sum((GenPolynomial) r);
        GenSolvablePolynomial<GenPolynomial<C>> lhs = P;
        GenPolynomial<C> ldcf = (GenPolynomial) S.leadingBaseCoefficient();
        long d = (P.degree(0) - S.degree(0)) + 1;
        if (d <= 0) {
            d = (-d) + 2;
        }
        for (i = 0; i <= d; i++) {
            if (lhs.equals(rhs)) {
                return true;
            }
            lhs = lhs.multiply((RingElem) ldcf);
        }
        GenSolvablePolynomial<GenPolynomial<C>> Pp = P;
        rhs = q.multiply((GenSolvablePolynomial) S);
        for (i = 0; i <= d; i++) {
            if (((GenSolvablePolynomial) Pp.subtract((GenPolynomial) r)).equals(rhs)) {
                return true;
            }
            Pp = Pp.multiply((RingElem) ldcf);
        }
        GreatestCommonDivisorAbstract<C> fd = new GreatestCommonDivisorSimple(P.ring.coFac.coFac);
        GenSolvablePolynomial a = (GenSolvablePolynomial) P.leadingBaseCoefficient();
        rhs = (GenSolvablePolynomial) q.multiply((GenSolvablePolynomial) S).sum((GenPolynomial) r);
        GenSolvablePolynomial<C>[] oc = fd.leftOreCond(a, (GenSolvablePolynomial) rhs.leadingBaseCoefficient());
        GenSolvablePolynomial<GenPolynomial<C>> D = (GenSolvablePolynomial) P.multiplyLeft(oc[0]).subtract((GenPolynomial) rhs.multiplyLeft(oc[1]));
        if (D.isZERO()) {
            return true;
        }
        if (debug) {
            logger.info("not QR: D = " + D);
        }
        return false;
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<GenPolynomial<C>> recursivePseudoQuotient(GenSolvablePolynomial<GenPolynomial<C>> P, GenSolvablePolynomial<GenPolynomial<C>> S) {
        return recursivePseudoQuotientRemainder(P, S)[0];
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<GenPolynomial<C>>[] recursivePseudoQuotientRemainder(GenSolvablePolynomial<GenPolynomial<C>> P, GenSolvablePolynomial<GenPolynomial<C>> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException(P + " division by zero " + S);
        }
        GenSolvablePolynomial<GenPolynomial<C>>[] ret = new GenSolvablePolynomial[2];
        if (P == null || P.isZERO()) {
            ret[0] = S.ring.getZERO();
            ret[1] = S.ring.getZERO();
        } else if (S.isONE()) {
            ret[0] = P;
            ret[1] = S.ring.getZERO();
        } else {
            GreatestCommonDivisorAbstract<C> fd = new GreatestCommonDivisorSimple(P.ring.coFac.coFac);
            ExpVector e = S.leadingExpVector();
            GenSolvablePolynomial<GenPolynomial<C>> r = P;
            GenSolvablePolynomial<GenPolynomial<C>> q = S.ring.getZERO().copy();
            while (!r.isZERO()) {
                ExpVector f = r.leadingExpVector();
                if (!f.multipleOf(e)) {
                    break;
                }
                f = f.subtract(e);
                GenSolvablePolynomial<GenPolynomial<C>> h = S.multiplyLeft(f);
                GenSolvablePolynomial<C>[] oc = fd.leftOreCond((GenSolvablePolynomial) r.leadingBaseCoefficient(), (GenSolvablePolynomial) h.leadingBaseCoefficient());
                RingElem ga = oc[0];
                RingElem gd = oc[1];
                q = (GenSolvablePolynomial) q.multiplyLeft(ga).sum(gd, f);
                r = (GenSolvablePolynomial) r.multiplyLeft(ga).subtract((GenPolynomial) h.multiplyLeft(gd));
            }
            ret[0] = q;
            ret[1] = r;
        }
        return ret;
    }

    public static <C extends GcdRingElem<C>> boolean isRecursiveRightPseudoQuotientRemainder(GenSolvablePolynomial<GenPolynomial<C>> P, GenSolvablePolynomial<GenPolynomial<C>> S, GenSolvablePolynomial<GenPolynomial<C>> q, GenSolvablePolynomial<GenPolynomial<C>> r) {
        long i;
        GenSolvablePolynomial<GenPolynomial<C>> rhs = (GenSolvablePolynomial) S.multiply((GenSolvablePolynomial) q).sum((GenPolynomial) r);
        GenSolvablePolynomial<GenPolynomial<C>> lhs = P;
        GenPolynomial<C> ldcf = (GenPolynomial) S.leadingBaseCoefficient();
        long d = (P.degree(0) - S.degree(0)) + 1;
        if (d <= 0) {
            d = (-d) + 2;
        }
        for (i = 0; i <= d; i++) {
            if (lhs.equals(rhs)) {
                return true;
            }
            lhs = lhs.multiply((RingElem) ldcf);
        }
        GenSolvablePolynomial<GenPolynomial<C>> Pp = P;
        rhs = S.multiply((GenSolvablePolynomial) q);
        for (i = 0; i <= d; i++) {
            if (((GenSolvablePolynomial) Pp.subtract((GenPolynomial) r)).equals(rhs)) {
                return true;
            }
            Pp = Pp.multiply((RingElem) ldcf);
        }
        GreatestCommonDivisorAbstract<C> fd = new GreatestCommonDivisorSimple(P.ring.coFac.coFac);
        GenSolvablePolynomial<GenPolynomial<C>> pr = P.rightRecursivePolynomial();
        GenSolvablePolynomial a = (GenSolvablePolynomial) pr.leadingBaseCoefficient();
        GenSolvablePolynomial<GenPolynomial<C>> rr = ((GenSolvablePolynomial) S.multiply((GenSolvablePolynomial) q).sum((GenPolynomial) r)).rightRecursivePolynomial();
        GenSolvablePolynomial<C>[] oc = fd.rightOreCond(a, (GenSolvablePolynomial) rr.leadingBaseCoefficient());
        GenPolynomial<C> ga = oc[0];
        GenPolynomial<C> gb = oc[1];
        GenSolvablePolynomial<GenPolynomial<C>> Pa = multiplyRightRecursivePolynomial(pr, ga);
        GenSolvablePolynomial<GenPolynomial<C>> Rb = multiplyRightRecursivePolynomial(rr, gb);
        GenSolvablePolynomial<GenPolynomial<C>> D = (GenSolvablePolynomial) Pa.subtract((GenPolynomial) Rb);
        if (D.isZERO()) {
            return true;
        }
        System.out.println("Pa = " + Pa);
        System.out.println("Rb = " + Rb);
        logger.info("not right QR: Pa-Rb = " + D);
        return false;
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<GenPolynomial<C>> recursiveRightPseudoQuotient(GenSolvablePolynomial<GenPolynomial<C>> P, GenSolvablePolynomial<GenPolynomial<C>> S) {
        return recursiveRightPseudoQuotientRemainder(P, S)[0];
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<GenPolynomial<C>> recursiveRightSparsePseudoRemainder(GenSolvablePolynomial<GenPolynomial<C>> P, GenSolvablePolynomial<GenPolynomial<C>> S) {
        return recursiveRightPseudoQuotientRemainder(P, S)[1];
    }

    public static <C extends RingElem<C>> GenSolvablePolynomial<GenPolynomial<C>> recursiveRightSparsePseudoRemainderOld(GenSolvablePolynomial<GenPolynomial<C>> P, GenSolvablePolynomial<GenPolynomial<C>> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException(P + " division by zero " + S);
        } else if (P == null || P.isZERO()) {
            return P;
        } else {
            if (S.isConstant()) {
                return P.ring.getZERO();
            }
            ExpVector e = S.leadingExpVector();
            GenSolvablePolynomial<GenPolynomial<C>> r = P;
            while (!r.isZERO()) {
                ExpVector f = r.leadingExpVector();
                if (!f.multipleOf(e)) {
                    return r;
                }
                GenSolvablePolynomial<GenPolynomial<C>> h = S.multiply(f.subtract(e));
                r = (GenSolvablePolynomial) r.multiply((GenPolynomial) h.leadingBaseCoefficient()).subtract((GenPolynomial) h.multiply((GenPolynomial) r.leadingBaseCoefficient()));
            }
            return r;
        }
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<GenPolynomial<C>>[] recursiveRightPseudoQuotientRemainder(GenSolvablePolynomial<GenPolynomial<C>> P, GenSolvablePolynomial<GenPolynomial<C>> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException(P + " division by zero " + S);
        }
        GenSolvablePolynomial<GenPolynomial<C>>[] ret = new GenSolvablePolynomial[2];
        if (P == null || P.isZERO()) {
            ret[0] = S.ring.getZERO();
            ret[1] = S.ring.getZERO();
        } else if (S.isONE()) {
            ret[0] = P;
            ret[1] = S.ring.getZERO();
        } else {
            GreatestCommonDivisorAbstract<C> fd = new GreatestCommonDivisorSimple(P.ring.coFac.coFac);
            ExpVector e = S.leadingExpVector();
            GenSolvablePolynomial<GenPolynomial<C>> r = P;
            GenSolvablePolynomial<GenPolynomial<C>> qr = S.ring.getZERO().copy();
            while (!r.isZERO()) {
                ExpVector f = r.leadingExpVector();
                if (!f.multipleOf(e)) {
                    break;
                }
                f = f.subtract(e);
                GenSolvablePolynomial<GenPolynomial<C>> hr = S.multiply(f).rightRecursivePolynomial();
                GenSolvablePolynomial<GenPolynomial<C>> rr = r.rightRecursivePolynomial();
                GenSolvablePolynomial<C>[] oc = fd.rightOreCond((GenSolvablePolynomial) rr.leadingBaseCoefficient(), (GenSolvablePolynomial) hr.leadingBaseCoefficient());
                GenPolynomial<C> ga = oc[0];
                GenPolynomial<C> gd = oc[1];
                rr = multiplyRightRecursivePolynomial(rr, ga);
                r = (GenSolvablePolynomial) rr.evalAsRightRecursivePolynomial().subtract((GenPolynomial) multiplyRightRecursivePolynomial(hr, gd).evalAsRightRecursivePolynomial());
                qr = (GenSolvablePolynomial) multiplyRightRecursivePolynomial(qr, ga).sum(gd, f);
            }
            ret[0] = qr.evalAsRightRecursivePolynomial();
            ret[1] = r;
        }
        return ret;
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<GenPolynomial<C>> recursiveDivideRightEval(GenSolvablePolynomial<GenPolynomial<C>> P, GenSolvablePolynomial<C> s) {
        if (s.isONE()) {
            return P;
        }
        GenSolvablePolynomial<GenPolynomial<C>> Pr = P.rightRecursivePolynomial();
        GenSolvablePolynomial<GenPolynomial<C>> Qr = recursiveDivide(Pr, s);
        GenSolvablePolynomial<GenPolynomial<C>> Q = Qr.evalAsRightRecursivePolynomial();
        if (!debug || Q.multiply((RingElem) s).equals(P)) {
            return Q;
        }
        System.out.println("rDivREval: P   = " + P + ", right(P) = " + Pr);
        System.out.println("rDivREval: Q   = " + Q + ", right(Q) = " + Qr);
        System.out.println("rDivREval: Q*s = " + Q.multiply((RingElem) s) + ", s = " + s);
        throw new RuntimeException("rDivREval: Q*s != P");
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<GenPolynomial<C>> recursiveDivide(GenSolvablePolynomial<GenPolynomial<C>> P, GenSolvablePolynomial<C> s) {
        if (s == null || s.isZERO()) {
            throw new ArithmeticException("division by zero " + P + ", " + s);
        } else if (P.isZERO() || s.isONE()) {
            return P;
        } else {
            GenSolvablePolynomial<GenPolynomial<C>> p = P.ring.getZERO().copy();
            for (Entry<ExpVector, GenPolynomial<C>> m1 : P.getMap().entrySet()) {
                GenSolvablePolynomial<C> c1 = (GenSolvablePolynomial) m1.getValue();
                ExpVector e1 = (ExpVector) m1.getKey();
                GenSolvablePolynomial<C>[] QR = leftBasePseudoQuotientRemainder(c1, s);
                GenSolvablePolynomial c = QR[0];
                if (debug && !QR[1].isZERO()) {
                    System.out.println("rDiv, P   = " + P);
                    System.out.println("rDiv, c1  = " + c1);
                    System.out.println("rDiv, s   = " + s);
                    System.out.println("rDiv, c   = " + c + ", r = " + QR[1]);
                    System.out.println("rDiv, c*s = " + c.multiply((GenSolvablePolynomial) s));
                    System.out.println("rDiv, s*c = " + s.multiply(c));
                    throw new RuntimeException("something is wrong: rem = " + QR[1]);
                } else if (c.isZERO()) {
                    System.out.println("rDiv, P  = " + P);
                    System.out.println("rDiv, c1 = " + c1);
                    System.out.println("rDiv, s  = " + s);
                    System.out.println("rDiv, c  = " + c);
                    throw new RuntimeException("something is wrong: c is zero");
                } else {
                    p.doPutToMap(e1, c);
                }
            }
            return p;
        }
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<GenPolynomial<C>> recursiveDivideRightPolynomial(GenSolvablePolynomial<GenPolynomial<C>> P, GenSolvablePolynomial<C> s) {
        if (s == null || s.isZERO()) {
            throw new ArithmeticException("division by zero " + P + ", " + s);
        } else if (P.isZERO() || s.isONE()) {
            return P;
        } else {
            GenSolvablePolynomial<GenPolynomial<C>> p = P.ring.getZERO().copy();
            GenSolvablePolynomial<GenPolynomial<C>> Pr = P.rightRecursivePolynomial();
            logger.info("P = " + P + ", right(P) = " + Pr + ", left(s) = " + s);
            for (Entry<ExpVector, GenPolynomial<C>> m1 : Pr.getMap().entrySet()) {
                ExpVector e1 = (ExpVector) m1.getKey();
                GenSolvablePolynomial<C> c = divideRightPolynomial((GenSolvablePolynomial) m1.getValue(), s);
                if (!c.isZERO()) {
                    p.doPutToMap(e1, c);
                }
            }
            GenSolvablePolynomial<GenPolynomial<C>> pl = p.evalAsRightRecursivePolynomial();
            logger.info("pl = " + pl + ", p = " + p);
            return pl;
        }
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<C> divideRightPolynomial(GenSolvablePolynomial<C> P, GenSolvablePolynomial<C> s) {
        if (s == null || s.isZERO()) {
            throw new ArithmeticException("division by zero " + P + ", " + s);
        } else if (P.isZERO() || s.isONE()) {
            return P;
        } else {
            GenPolynomialRing pfac = P.ring;
            if (pfac.nvar <= 1) {
                GenSolvablePolynomial<C>[] QR1 = leftBasePseudoQuotientRemainder(P, s);
                GenSolvablePolynomial<C> q = QR1[0];
                if (!debug || QR1[1].isZERO()) {
                    return q;
                }
                System.out.println("rDivPol, P = " + P);
                System.out.println("rDivPol, s = " + s);
                throw new RuntimeException("non zero remainder, q = " + q + ", r = " + QR1[1]);
            }
            GenPolynomialRing rfac = pfac.recursive(1);
            GenSolvablePolynomial<GenPolynomial<C>> pr = (GenSolvablePolynomial) PolyUtil.recursive(rfac, (GenPolynomial) P);
            GenSolvablePolynomial<GenPolynomial<C>> sr = (GenSolvablePolynomial) PolyUtil.recursive(rfac, (GenPolynomial) s);
            GenSolvablePolynomial<GenPolynomial<C>>[] QR = recursiveRightPseudoQuotientRemainder(pr, sr);
            GenPolynomial qr = QR[0];
            GenSolvablePolynomial<GenPolynomial<C>> rr = QR[1];
            if (!debug || rr.isZERO()) {
                return (GenSolvablePolynomial) PolyUtil.distribute(pfac, qr);
            }
            System.out.println("rDivPol, pr = " + pr);
            System.out.println("rDivPol, sr = " + sr);
            throw new RuntimeException("non zero remainder, q = " + qr + ", r = " + rr);
        }
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<GenPolynomial<C>> recursiveRightDivide(GenSolvablePolynomial<GenPolynomial<C>> P, GenSolvablePolynomial<C> s) {
        if (s == null || s.isZERO()) {
            throw new ArithmeticException("division by zero " + P + ", " + s);
        } else if (P.isZERO() || s.isONE()) {
            return P;
        } else {
            RecSolvablePolynomialRing<C> rfac;
            RecSolvablePolynomial<C> onep;
            ExpVector zero;
            GenSolvablePolynomial q;
            RecSolvablePolynomial<C> p;
            if (P instanceof RecSolvablePolynomial) {
                rfac = P.ring;
            } else {
                rfac = P.ring;
            }
            if (rfac.coeffTable.isEmpty()) {
                onep = rfac.getONE();
                zero = rfac.evzero;
                q = rfac.getZERO();
                p = (RecSolvablePolynomial) P;
            } else {
                onep = rfac.getONE();
                zero = rfac.evzero;
                q = rfac.getZERO();
                p = (RecSolvablePolynomial) P;
            }
            while (!p.isZERO()) {
                ExpVector f = p.leadingExpVector();
                GenSolvablePolynomial<C> a = (GenSolvablePolynomial) p.leadingBaseCoefficient();
                GenSolvablePolynomial<C>[] QR = leftBasePseudoQuotientRemainder(a, s);
                if (!debug || (QR[1].isZERO() && a.remainder((GenPolynomial) s).isZERO())) {
                    GenPolynomial c = QR[0];
                    if (c.isZERO()) {
                        System.out.println("rDiv, P  = " + P);
                        System.out.println("rDiv, a  = " + a);
                        System.out.println("rDiv, s  = " + s);
                        System.out.println("rDiv, c  = " + c);
                        throw new RuntimeException("something is wrong: c is zero");
                    }
                    p = (RecSolvablePolynomial) p.subtract((GenPolynomial) onep.multiply(c, f, (GenPolynomial) s, zero));
                    RecSolvablePolynomial q2 = (RecSolvablePolynomial) q.sum(c, f);
                } else {
                    logger.info("no exact division, rem = " + a.remainder((GenPolynomial) s) + ", r =" + QR[1]);
                    throw new RuntimeException("no exact division: r = " + QR[1]);
                }
            }
            return q;
        }
    }

    public static <C extends RingElem<C>> GenSolvablePolynomial<GenPolynomial<C>> multiplyRightRecursivePolynomial(GenSolvablePolynomial<GenPolynomial<C>> P, GenPolynomial<C> b) {
        if (P == null || P.isZERO()) {
            return P;
        }
        GenSolvablePolynomial<GenPolynomial<C>> Cp = P.ring.getZERO().copy();
        if (b == null || b.isZERO()) {
            return Cp;
        }
        for (Entry<ExpVector, GenPolynomial<C>> y : P.getMap().entrySet()) {
            ExpVector e = (ExpVector) y.getKey();
            GenPolynomial<C> c = ((GenPolynomial) y.getValue()).multiply((GenPolynomial) b);
            if (!c.isZERO()) {
                Cp.doPutToMap(e, c);
            }
        }
        return Cp;
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<GenPolynomial<C>> integralFromQuotientCoefficients(GenSolvablePolynomialRing<GenPolynomial<C>> fac, GenSolvablePolynomial<SolvableQuotient<C>> A) {
        GenSolvablePolynomial<GenPolynomial<C>> B = fac.getZERO().copy();
        if (!(A == null || A.isZERO())) {
            GenSolvablePolynomial<C> c = null;
            GreatestCommonDivisorAbstract<C> fd = new GreatestCommonDivisorPrimitive(fac.coFac.coFac);
            int s = 0;
            Map<ExpVector, SolvableQuotient<C>> Am = A.getMap();
            for (SolvableQuotient<C> y : Am.values()) {
                GenSolvablePolynomial<C> x = y.den;
                if (c == null) {
                    c = x;
                    s = x.signum();
                } else {
                    c = ((GenSolvablePolynomial) x.divide((GenPolynomial) fd.leftGcd(c, x))).multiply((GenSolvablePolynomial) c);
                }
            }
            if (s < 0) {
                c = (GenSolvablePolynomial) c.negate();
            }
            for (Entry<ExpVector, SolvableQuotient<C>> y2 : Am.entrySet()) {
                ExpVector e = (ExpVector) y2.getKey();
                SolvableQuotient<C> a = (SolvableQuotient) y2.getValue();
                GenPolynomial<C> b = c.divide(a.den);
                B.doPutToMap(e, a.num.multiply((GenPolynomial) b));
            }
        }
        return B;
    }

    public static <C extends GcdRingElem<C>> List<GenSolvablePolynomial<GenPolynomial<C>>> integralFromQuotientCoefficients(GenSolvablePolynomialRing<GenPolynomial<C>> fac, Collection<GenSolvablePolynomial<SolvableQuotient<C>>> L) {
        if (L == null) {
            return null;
        }
        List<GenSolvablePolynomial<GenPolynomial<C>>> list = new ArrayList(L.size());
        for (GenSolvablePolynomial p : L) {
            list.add(integralFromQuotientCoefficients((GenSolvablePolynomialRing) fac, p));
        }
        return list;
    }

    public static <C extends GcdRingElem<C>> GenSolvablePolynomial<SolvableQuotient<C>> quotientFromIntegralCoefficients(GenSolvablePolynomialRing<SolvableQuotient<C>> fac, GenSolvablePolynomial<GenPolynomial<C>> A) {
        GenSolvablePolynomial<SolvableQuotient<C>> B = fac.getZERO().copy();
        if (!(A == null || A.isZERO())) {
            SolvableQuotientRing<C> qfac = (SolvableQuotientRing) fac.coFac;
            for (Entry<ExpVector, GenPolynomial<C>> y : A.getMap().entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                SolvableQuotient<C> p = new SolvableQuotient(qfac, (GenSolvablePolynomial) y.getValue());
                if (!p.isZERO()) {
                    B.doPutToMap(e, p);
                }
            }
        }
        return B;
    }

    public static <C extends GcdRingElem<C>> List<GenSolvablePolynomial<SolvableQuotient<C>>> quotientFromIntegralCoefficients(GenSolvablePolynomialRing<SolvableQuotient<C>> fac, Collection<GenSolvablePolynomial<GenPolynomial<C>>> L) {
        if (L == null) {
            return null;
        }
        List<GenSolvablePolynomial<SolvableQuotient<C>>> list = new ArrayList(L.size());
        for (GenSolvablePolynomial p : L) {
            list.add(quotientFromIntegralCoefficients((GenSolvablePolynomialRing) fac, p));
        }
        return list;
    }
}
