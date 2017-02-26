package edu.jas.root;

import edu.jas.arith.Rational;
import edu.jas.poly.Complex;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.List;
import org.apache.log4j.Logger;

public class RootUtil {
    private static boolean debug;
    private static final Logger logger;

    static {
        logger = Logger.getLogger(RootUtil.class);
        debug = logger.isDebugEnabled();
    }

    public static <C extends RingElem<C>> long signVar(List<C> L) {
        long v = 0;
        if (L == null || L.isEmpty()) {
            return 0;
        }
        C A = (RingElem) L.get(0);
        int i = 1;
        while (i < L.size()) {
            C B = (RingElem) L.get(i);
            while (true) {
                if (B != null && B.signum() != 0) {
                    break;
                }
                i++;
                if (i >= L.size()) {
                    return v;
                }
                RingElem B2 = (RingElem) L.get(i);
            }
            if (A.signum() * B.signum() < 0) {
                v++;
            }
            A = B;
            i++;
        }
        return v;
    }

    public static <C extends RingElem<C> & Rational> Interval<C> parseInterval(RingFactory<C> fac, String s) {
        int r = s.length();
        int el = s.indexOf("[");
        if (el >= 0) {
            int ri = s.indexOf("]");
            if (ri > 0) {
                r = ri;
            }
        } else {
            el = -1;
        }
        String iv = s.substring(el + 1, r).trim();
        int k = iv.indexOf(",");
        if (k < 0) {
            k = s.indexOf(" ");
        }
        if (k < 0) {
            return new Interval((RingElem) fac.parse(iv));
        }
        String ls = iv.substring(0, k).trim();
        RingElem left = (RingElem) fac.parse(ls);
        RingElem right = (RingElem) fac.parse(iv.substring(k + 1, iv.length()).trim());
        if (debug) {
            logger.debug("Interval: left = " + left + ", right = " + right);
        }
        return new Interval(left, right);
    }

    public static <C extends RingElem<C> & Rational> Rectangle<C> parseRectangle(RingFactory<Complex<C>> fac, String s) {
        int r = s.length();
        int el = s.indexOf("[");
        if (el >= 0) {
            int ri = s.indexOf("]");
            if (ri > 0) {
                r = ri;
            }
        } else {
            el = -1;
        }
        String iv = s.substring(el + 1, r).trim();
        int k = iv.indexOf(",");
        if (k < 0) {
            k = s.indexOf(" ");
        }
        if (k < 0) {
            return new Rectangle((Complex) fac.parse(iv));
        }
        String ls = iv.substring(0, k).trim();
        Complex<C> sw = (Complex) fac.parse(ls);
        Complex<C> ne = (Complex) fac.parse(iv.substring(k + 1, iv.length()).trim());
        if (debug) {
            logger.debug("Rectangle: sw = " + sw + ", ne = " + ne);
        }
        return new Rectangle(sw, ne);
    }
}
