package edu.jas.gbufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.OrderedPolynomialList;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.ufd.GCDFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

public class CharacteristicSetWu<C extends GcdRingElem<C>> implements CharacteristicSet<C> {
    private static boolean debug;
    private static final Logger logger;

    static {
        logger = Logger.getLogger(CharacteristicSetWu.class);
        debug = logger.isDebugEnabled();
    }

    public List<GenPolynomial<C>> characteristicSet(List<GenPolynomial<C>> A) {
        List<GenPolynomial<C>> S = new ArrayList();
        if (A == null || A.isEmpty()) {
            return S;
        }
        GenPolynomialRing pfac = ((GenPolynomial) A.get(0)).ring;
        int i = pfac.nvar;
        if (r0 <= 1) {
            GenPolynomial<C> g = GCDFactory.getImplementation(pfac.coFac).gcd(A).monic();
            logger.info("charSet base gcd = " + g);
            S.add(g);
            return S;
        }
        GenPolynomial<GenPolynomial<C>> fr;
        GenPolynomialRing<GenPolynomial<C>> rfac = pfac.recursive(1);
        List<GenPolynomial<GenPolynomial<C>>> positiveDeg = new ArrayList();
        List<GenPolynomial<C>> zeroDeg = new ArrayList();
        for (GenPolynomial<C> f : A) {
            if (!f.isZERO()) {
                GenPolynomial f2 = f.monic();
                if (f2.isONE()) {
                    S.add(f2);
                    return S;
                }
                fr = PolyUtil.recursive((GenPolynomialRing) rfac, f2);
                if (fr.degree(0) == 0) {
                    zeroDeg.add(fr.leadingBaseCoefficient());
                } else {
                    positiveDeg.add(fr);
                }
            }
        }
        if (positiveDeg.isEmpty() && zeroDeg.isEmpty()) {
            return S;
        }
        List<GenPolynomial<GenPolynomial<C>>> pd = new ArrayList(new OrderedPolynomialList(rfac, positiveDeg).list);
        Collections.reverse(pd);
        if (debug) {
            logger.info("positive degrees: " + pd);
        }
        while (pd.size() > 1) {
            fr = (GenPolynomial) pd.remove(0);
            GenPolynomial<GenPolynomial<C>> qr = (GenPolynomial) pd.get(0);
            logger.info("pseudo remainder by deg = " + qr.degree() + " in variable " + rfac.getVars()[0]);
            GenPolynomial<GenPolynomial<C>> rr = PolyUtil.recursiveSparsePseudoRemainder(fr, qr);
            if (rr.isZERO()) {
                logger.warn("variety is reducible " + fr + " reduces to zero mod " + pd);
            } else if (rr.degree(0) == 0) {
                zeroDeg.add(((GenPolynomial) rr.leadingBaseCoefficient()).monic());
            } else {
                pd.add(rr);
                pd = OrderedPolynomialList.sort(rfac, pd);
                Collections.reverse(pd);
            }
        }
        List<GenPolynomial<C>> Sp = characteristicSet(zeroDeg);
        for (GenPolynomial<C> f3 : Sp) {
            S.add(f3.extend(pfac, 0, 0));
        }
        if (pd.isEmpty()) {
            return S;
        }
        GenPolynomial<C> sr = PolyGBUtil.topCoefficientPseudoRemainder(Sp, PolyUtil.distribute(pfac, (GenPolynomial) pd.get(0)));
        logger.info("charSet rereduced sr = " + sr);
        if (sr.isZERO()) {
            return S;
        }
        if (sr.degree(pfac.nvar - 1) == 0) {
            S.add(0, sr);
            logger.warn("reducible characteristic set, restarting with S = " + S);
            return characteristicSet(S);
        }
        S.add(0, sr.monic());
        return S;
    }

    public boolean isCharacteristicSet(List<GenPolynomial<C>> A) {
        if (A == null || A.isEmpty()) {
            return true;
        }
        GenPolynomialRing<C> pfac = ((GenPolynomial) A.get(0)).ring;
        if (pfac.nvar <= 1) {
            return A.size() <= 1;
        } else if (pfac.nvar < A.size()) {
            logger.info("not CS: pfac = " + pfac + ", A = " + A);
            return false;
        } else {
            GenPolynomialRing rfac = pfac.recursive(1);
            List<GenPolynomial<C>> zeroDeg = new ArrayList();
            int positiveDeg = 0;
            for (GenPolynomial f : A) {
                if (f.isZERO()) {
                    logger.info("not CS: f = " + f);
                    return false;
                }
                GenPolynomial<GenPolynomial<C>> fr = PolyUtil.recursive(rfac, f);
                if (fr.degree(0) == 0) {
                    zeroDeg.add(fr.leadingBaseCoefficient());
                } else {
                    positiveDeg++;
                    if (positiveDeg > 1) {
                        logger.info("not CS: f = " + f + ", positiveDeg = " + positiveDeg);
                        return false;
                    }
                }
            }
            return isCharacteristicSet(zeroDeg);
        }
    }

    public GenPolynomial<C> characteristicSetReduction(List<GenPolynomial<C>> A, GenPolynomial<C> P) {
        if (A == null || A.isEmpty()) {
            return P.monic();
        }
        if (P.isZERO()) {
            return P;
        }
        GenPolynomial<C> R = PolyGBUtil.topPseudoRemainder(A, P);
        if (R.isZERO()) {
            return R;
        }
        return PolyGBUtil.topCoefficientPseudoRemainder(PolyGBUtil.zeroDegrees(A), R);
    }
}
