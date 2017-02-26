package edu.jas.gbmod;

import edu.jas.gb.ExtendedGB;
import edu.jas.gb.GroebnerBaseAbstract;
import edu.jas.gbufd.GBFactory;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.ModuleList;
import edu.jas.poly.PolynomialList;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

public class SyzygySeq<C extends GcdRingElem<C>> extends SyzygyAbstract<C> {
    private static final Logger logger;
    protected GroebnerBaseAbstract<C> bb;
    private final boolean debug;
    protected ModGroebnerBaseAbstract<C> mbb;

    static {
        logger = Logger.getLogger(SyzygySeq.class);
    }

    public SyzygySeq(RingFactory<C> cf) {
        this.debug = logger.isDebugEnabled();
        this.bb = GBFactory.getImplementation((RingFactory) cf);
        this.mbb = new ModGroebnerBaseSeq((RingFactory) cf);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<edu.jas.gbmod.ResPart<C>> resolution(edu.jas.poly.ModuleList<C> r6) {
        /*
        r5 = this;
        r2 = new java.util.ArrayList;
        r2.<init>();
        r1 = r6;
    L_0x0006:
        r4 = r5.mbb;
        r0 = r4.GB(r1);
        r3 = r5.zeroRelations(r0);
        r4 = new edu.jas.gbmod.ResPart;
        r4.<init>(r1, r0, r3);
        r2.add(r4);
        if (r3 == 0) goto L_0x0026;
    L_0x001a:
        r4 = r3.list;
        if (r4 == 0) goto L_0x0026;
    L_0x001e:
        r4 = r3.list;
        r4 = r4.size();
        if (r4 != 0) goto L_0x0027;
    L_0x0026:
        return r2;
    L_0x0027:
        r1 = r3;
        goto L_0x0006;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.gbmod.SyzygySeq.resolution(edu.jas.poly.ModuleList):java.util.List<edu.jas.gbmod.ResPart<C>>");
    }

    public List resolution(PolynomialList<C> F) {
        List G = this.bb.GB(F.list);
        List Z = zeroRelations(G);
        PolynomialList<C> Gl = new PolynomialList(F.ring, G);
        ModuleList Zm = new ModuleList(F.ring, Z);
        List R = resolution(Zm);
        R.add(0, new ResPolPart(F, Gl, Zm));
        return R;
    }

    public List resolutionArbitrary(PolynomialList<C> F) {
        ModuleList Zm = new ModuleList(F.ring, zeroRelationsArbitrary(F.list));
        List R = resolutionArbitrary(Zm);
        R.add(0, new ResPolPart(F, null, Zm));
        return R;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<edu.jas.gbmod.ResPart<C>> resolutionArbitrary(edu.jas.poly.ModuleList<C> r6) {
        /*
        r5 = this;
        r2 = new java.util.ArrayList;
        r2.<init>();
        r1 = r6;
        r0 = 0;
    L_0x0007:
        r3 = r5.zeroRelationsArbitrary(r1);
        r4 = new edu.jas.gbmod.ResPart;
        r4.<init>(r1, r0, r3);
        r2.add(r4);
        if (r3 == 0) goto L_0x0021;
    L_0x0015:
        r4 = r3.list;
        if (r4 == 0) goto L_0x0021;
    L_0x0019:
        r4 = r3.list;
        r4 = r4.size();
        if (r4 != 0) goto L_0x0022;
    L_0x0021:
        return r2;
    L_0x0022:
        r1 = r3;
        goto L_0x0007;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.gbmod.SyzygySeq.resolutionArbitrary(edu.jas.poly.ModuleList):java.util.List<edu.jas.gbmod.ResPart<C>>");
    }

    public List<List<GenPolynomial<C>>> zeroRelationsArbitrary(int modv, List<GenPolynomial<C>> F) {
        if (F == null) {
            return new ArrayList();
        }
        if (F.size() <= 1) {
            return zeroRelations(modv, (List) F);
        }
        int lenf = F.size();
        ExtendedGB<C> exgb = this.bb.extGB(F);
        if (this.debug) {
            logger.debug("exgb = " + exgb);
            if (!this.bb.isReductionMatrix(exgb)) {
                logger.error("is reduction matrix ? false");
            }
        }
        List<GenPolynomial<C>> G = exgb.G;
        List<List<GenPolynomial<C>>> G2F = exgb.G2F;
        List<List<GenPolynomial<C>>> F2G = exgb.F2G;
        List<List<GenPolynomial<C>>> sg = zeroRelations(modv, (List) G);
        GenPolynomialRing<C> ring = ((GenPolynomial) G.get(0)).ring;
        ModuleList<C> S = new ModuleList((GenPolynomialRing) ring, (List) sg);
        if (this.debug) {
            logger.debug("syz = " + S);
            if (!isZeroRelation((List) sg, (List) G)) {
                logger.error("is syzygy ? false");
            }
        }
        List<List<GenPolynomial<C>>> arrayList = new ArrayList(sg.size());
        for (List<GenPolynomial<C>> r : sg) {
            int m;
            List<GenPolynomial<C>> rf;
            Iterator<GenPolynomial<C>> it = r.iterator();
            Iterator<List<GenPolynomial<C>>> jt = G2F.iterator();
            List<GenPolynomial<C>> arrayList2 = new ArrayList(lenf);
            for (m = 0; m < lenf; m++) {
                arrayList2.add(ring.getZERO());
            }
            while (it.hasNext() && jt.hasNext()) {
                GenPolynomial<C> si = (GenPolynomial) it.next();
                List ai = (List) jt.next();
                if (!(si == null || ai == null)) {
                    List<GenPolynomial<C>> pi = this.blas.scalarProduct((RingElem) si, ai);
                    rf = this.blas.vectorAdd(rf, pi);
                }
            }
            if (it.hasNext() || jt.hasNext()) {
                logger.error("zeroRelationsArbitrary wrong sizes");
            }
            arrayList.add(rf);
        }
        List<List<GenPolynomial<C>>> M = new ArrayList(lenf);
        for (List<GenPolynomial<C>> r2 : F2G) {
            it = r2.iterator();
            jt = G2F.iterator();
            arrayList2 = new ArrayList(lenf);
            for (m = 0; m < lenf; m++) {
                arrayList2.add(ring.getZERO());
            }
            while (it.hasNext() && jt.hasNext()) {
                si = (GenPolynomial) it.next();
                ai = (List) jt.next();
                if (!(si == null || ai == null)) {
                    pi = this.blas.scalarProduct(ai, (RingElem) si);
                    rf = this.blas.vectorAdd(rf, pi);
                }
            }
            if (it.hasNext() || jt.hasNext()) {
                logger.error("zeroRelationsArbitrary wrong sizes");
            }
            M.add(rf);
        }
        int sflen = arrayList.size();
        List M2 = new ArrayList(lenf);
        int i = 0;
        for (List<GenPolynomial<C>> ri : M) {
            arrayList2 = new ArrayList(ri.size());
            int j = 0;
            for (GenPolynomial<C> rij : ri) {
                GenPolynomial<C> p = null;
                if (i == j) {
                    p = ring.getONE().subtract((GenPolynomial) rij);
                } else if (rij != null) {
                    p = rij.negate();
                }
                arrayList2.add(p);
                j++;
            }
            M2.add(arrayList2);
            if (!this.blas.isZero(arrayList2)) {
                arrayList.add(arrayList2);
            }
            i++;
        }
        if (!this.debug) {
            return arrayList;
        }
        ModuleList<C> M2L = new ModuleList((GenPolynomialRing) ring, M2);
        logger.debug("syz M2L = " + M2L);
        ModuleList<C> SF = new ModuleList((GenPolynomialRing) ring, (List) arrayList);
        logger.debug("syz sf = " + SF);
        logger.debug("#syz " + sflen + ", " + arrayList.size());
        if (isZeroRelation((List) arrayList, (List) F)) {
            return arrayList;
        }
        logger.error("is syz sf ? false");
        return arrayList;
    }
}
