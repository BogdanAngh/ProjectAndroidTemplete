package edu.jas.vector;

import edu.jas.structure.AbelianGroupElem;
import edu.jas.structure.RingElem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

public class BasicLinAlg<C extends RingElem<C>> implements Serializable {
    private static final Logger logger;

    static {
        logger = Logger.getLogger(BasicLinAlg.class);
    }

    public C scalarProduct(List<C> G, List<C> F) {
        C sp = null;
        Iterator<C> it = G.iterator();
        Iterator<C> jt = F.iterator();
        while (it.hasNext() && jt.hasNext()) {
            RingElem pi = (RingElem) it.next();
            RingElem pj = (RingElem) jt.next();
            if (!(pi == null || pj == null)) {
                if (sp == null) {
                    sp = (RingElem) pi.multiply(pj);
                } else {
                    sp = (RingElem) sp.sum((AbelianGroupElem) pi.multiply(pj));
                }
            }
        }
        if (it.hasNext() || jt.hasNext()) {
            logger.error("scalarProduct wrong sizes");
        }
        return sp;
    }

    public List<C> leftScalarProduct(List<C> G, List<List<C>> F) {
        List<C> sp = null;
        Iterator<C> it = G.iterator();
        Iterator<List<C>> jt = F.iterator();
        while (it.hasNext() && jt.hasNext()) {
            RingElem pi = (RingElem) it.next();
            List pj = (List) jt.next();
            if (!(pi == null || pj == null)) {
                List<C> s = scalarProduct(pi, pj);
                if (sp == null) {
                    sp = s;
                } else {
                    sp = vectorAdd(sp, s);
                }
            }
        }
        if (it.hasNext() || jt.hasNext()) {
            logger.error("scalarProduct wrong sizes");
        }
        return sp;
    }

    public List<C> rightScalarProduct(List<C> G, List<List<C>> F) {
        List<C> sp = null;
        Iterator<C> it = G.iterator();
        Iterator<List<C>> jt = F.iterator();
        while (it.hasNext() && jt.hasNext()) {
            RingElem pi = (RingElem) it.next();
            List pj = (List) jt.next();
            if (!(pi == null || pj == null)) {
                List<C> s = scalarProduct(pj, pi);
                if (sp == null) {
                    sp = s;
                } else {
                    sp = vectorAdd(sp, s);
                }
            }
        }
        if (it.hasNext() || jt.hasNext()) {
            logger.error("scalarProduct wrong sizes");
        }
        return sp;
    }

    public List<C> vectorAdd(List<C> a, List<C> b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        List<C> V = new ArrayList(a.size());
        Iterator<C> it = a.iterator();
        Iterator<C> jt = b.iterator();
        while (it.hasNext() && jt.hasNext()) {
            V.add((RingElem) ((RingElem) it.next()).sum((RingElem) jt.next()));
        }
        if (it.hasNext() || jt.hasNext()) {
            logger.error("vectorAdd wrong sizes");
        }
        return V;
    }

    public boolean isZero(List<C> a) {
        if (a == null) {
            return true;
        }
        for (C pi : a) {
            if (pi != null && !pi.isZERO()) {
                return false;
            }
        }
        return true;
    }

    public List<C> scalarProduct(C p, List<C> F) {
        List<C> V = new ArrayList(F.size());
        for (C pi : F) {
            C pi2;
            if (p != null) {
                pi2 = (RingElem) p.multiply(pi2);
            } else {
                pi2 = null;
            }
            V.add(pi2);
        }
        return V;
    }

    public List<C> scalarProduct(List<C> F, C p) {
        List<C> V = new ArrayList(F.size());
        for (C pi : F) {
            C pi2;
            if (pi2 != null) {
                pi2 = (RingElem) pi2.multiply(p);
            }
            V.add(pi2);
        }
        return V;
    }
}
