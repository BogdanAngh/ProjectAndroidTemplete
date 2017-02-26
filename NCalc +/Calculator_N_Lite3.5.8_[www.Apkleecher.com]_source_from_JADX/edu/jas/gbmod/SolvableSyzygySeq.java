package edu.jas.gbmod;

import edu.jas.gb.SolvableExtendedGB;
import edu.jas.gb.SolvableGroebnerBase;
import edu.jas.gbufd.SGBFactory;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.poly.GenSolvablePolynomialRing;
import edu.jas.poly.ModuleList;
import edu.jas.poly.PolynomialList;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

public class SolvableSyzygySeq<C extends GcdRingElem<C>> extends SolvableSyzygyAbstract<C> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static boolean assertEnabled;
    private static final boolean debug;
    private static final Logger logger;
    protected ModSolvableGroebnerBase<C> msbb;
    protected SolvableGroebnerBase<C> sbb;

    static {
        $assertionsDisabled = !SolvableSyzygySeq.class.desiredAssertionStatus();
        logger = Logger.getLogger(SolvableSyzygySeq.class);
        debug = logger.isDebugEnabled();
        assertEnabled = false;
        if (!$assertionsDisabled) {
            assertEnabled = true;
        }
    }

    public SolvableSyzygySeq(RingFactory<C> cf) {
        this.sbb = SGBFactory.getImplementation((RingFactory) cf);
        this.msbb = new ModSolvableGroebnerBaseSeq((RingFactory) cf);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<edu.jas.gbmod.SolvResPart<C>> resolution(edu.jas.poly.ModuleList<C> r6) {
        /*
        r5 = this;
        r2 = new java.util.ArrayList;
        r2.<init>();
        r1 = r6;
    L_0x0006:
        r4 = r5.msbb;
        r0 = r4.leftGB(r1);
        r3 = r5.leftZeroRelations(r0);
        r4 = new edu.jas.gbmod.SolvResPart;
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
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.gbmod.SolvableSyzygySeq.resolution(edu.jas.poly.ModuleList):java.util.List<edu.jas.gbmod.SolvResPart<C>>");
    }

    public List resolution(PolynomialList<C> F) {
        List G = this.sbb.leftGB(F.castToSolvableList());
        List Z = leftZeroRelations(G);
        PolynomialList<C> Gl = new PolynomialList((GenSolvablePolynomialRing) F.ring, G);
        ModuleList Zm = new ModuleList((GenSolvablePolynomialRing) F.ring, Z);
        List R = resolution(Zm);
        R.add(0, new SolvResPolPart(F, Gl, Zm));
        return R;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<edu.jas.gbmod.SolvResPart<C>> resolutionArbitrary(edu.jas.poly.ModuleList<C> r6) {
        /*
        r5 = this;
        r2 = new java.util.ArrayList;
        r2.<init>();
        r1 = r6;
        r0 = 0;
    L_0x0007:
        r3 = r5.leftZeroRelationsArbitrary(r1);
        r4 = new edu.jas.gbmod.SolvResPart;
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
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.gbmod.SolvableSyzygySeq.resolutionArbitrary(edu.jas.poly.ModuleList):java.util.List<edu.jas.gbmod.SolvResPart<C>>");
    }

    public List resolutionArbitrary(PolynomialList<C> F) {
        ModuleList Zm = new ModuleList((GenSolvablePolynomialRing) F.ring, leftZeroRelationsArbitrary(F.castToSolvableList()));
        List R = resolutionArbitrary(Zm);
        R.add(0, new SolvResPolPart(F, null, Zm));
        return R;
    }

    public List<List<GenSolvablePolynomial<C>>> leftZeroRelationsArbitrary(int modv, List<GenSolvablePolynomial<C>> F) {
        if (F == null) {
            return null;
        }
        if (F.size() <= 1) {
            return leftZeroRelations(modv, F);
        }
        int m;
        List<GenSolvablePolynomial<C>> rf;
        int lenf = F.size();
        SolvableExtendedGB<C> exgb = this.sbb.extLeftGB(modv, F);
        if (debug) {
            logger.info("exgb = " + exgb);
        }
        if (assertEnabled) {
            logger.info("check1 exgb start");
            if (!this.sbb.isLeftReductionMatrix(exgb)) {
                logger.error("is reduction matrix ? false");
            }
            logger.info("check1 exgb end");
        }
        List<GenSolvablePolynomial<C>> G = exgb.G;
        List<List<GenSolvablePolynomial<C>>> G2F = exgb.G2F;
        List<List<GenSolvablePolynomial<C>>> F2G = exgb.F2G;
        List<List<GenSolvablePolynomial<C>>> sg = leftZeroRelations(modv, G);
        GenSolvablePolynomialRing<C> ring = ((GenSolvablePolynomial) G.get(0)).ring;
        ModuleList<C> S = new ModuleList((GenSolvablePolynomialRing) ring, (List) sg);
        if (debug) {
            logger.info("syz = " + S);
        }
        if (assertEnabled) {
            logger.info("check2 left syz start");
            if (!isLeftZeroRelation((List) sg, (List) G)) {
                logger.error("is syzygy ? false");
            }
            logger.info("check2 left syz end");
        }
        List<List<GenSolvablePolynomial<C>>> arrayList = new ArrayList(sg.size());
        for (List<GenSolvablePolynomial<C>> r : sg) {
            Iterator<GenSolvablePolynomial<C>> it = r.iterator();
            Iterator<List<GenSolvablePolynomial<C>>> jt = G2F.iterator();
            List<GenSolvablePolynomial<C>> arrayList2 = new ArrayList(lenf);
            for (m = 0; m < lenf; m++) {
                arrayList2.add(ring.getZERO());
            }
            while (it.hasNext() && jt.hasNext()) {
                GenSolvablePolynomial<C> si = (GenSolvablePolynomial) it.next();
                List<GenSolvablePolynomial<C>> ai = (List) jt.next();
                if (!(si == null || ai == null)) {
                    List<GenPolynomial<C>> pi = this.blas.scalarProduct((RingElem) si, PolynomialList.castToList(ai));
                    rf = PolynomialList.castToSolvableList(this.blas.vectorAdd(PolynomialList.castToList(rf), pi));
                }
            }
            if (it.hasNext() || jt.hasNext()) {
                logger.error("leftZeroRelationsArbitrary wrong sizes");
            }
            arrayList.add(rf);
        }
        if (assertEnabled) {
            logger.info("check3 left syz start");
            if (!isLeftZeroRelation((List) arrayList, (List) F)) {
                logger.error("is partial syz sf ? false");
            }
            logger.info("check3 left syz end");
        }
        List<List<GenSolvablePolynomial<C>>> M = new ArrayList(lenf);
        for (List<GenSolvablePolynomial<C>> r2 : F2G) {
            it = r2.iterator();
            jt = G2F.iterator();
            arrayList2 = new ArrayList(lenf);
            for (m = 0; m < lenf; m++) {
                arrayList2.add(ring.getZERO());
            }
            while (it.hasNext() && jt.hasNext()) {
                si = (GenSolvablePolynomial) it.next();
                ai = (List) jt.next();
                if (!(si == null || ai == null)) {
                    pi = this.blas.scalarProduct((RingElem) si, PolynomialList.castToList(ai));
                    rf = PolynomialList.castToSolvableList(this.blas.vectorAdd(PolynomialList.castToList(rf), pi));
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
        for (List<GenSolvablePolynomial<C>> ri : M) {
            arrayList2 = new ArrayList(ri.size());
            int j = 0;
            for (GenPolynomial rij : ri) {
                GenSolvablePolynomial<C> p = null;
                if (i == j) {
                    p = (GenSolvablePolynomial) ring.getONE().subtract(rij);
                } else if (rij != null) {
                    p = (GenSolvablePolynomial) rij.negate();
                }
                arrayList2.add(p);
                j++;
            }
            M2.add(arrayList2);
            if (!this.blas.isZero(PolynomialList.castToList(arrayList2))) {
                arrayList.add(arrayList2);
            }
            i++;
        }
        ModuleList<C> M2L = new ModuleList((GenSolvablePolynomialRing) ring, M2);
        if (debug) {
            logger.debug("syz M2L = " + M2L);
        }
        if (debug) {
            ModuleList<C> SF = new ModuleList((GenSolvablePolynomialRing) ring, (List) arrayList);
            logger.debug("syz sf = " + SF);
            logger.debug("#syz " + sflen + ", " + arrayList.size());
        }
        if (!assertEnabled) {
            return arrayList;
        }
        logger.info("check4 left syz start");
        if (!isLeftZeroRelation((List) arrayList, (List) F)) {
            logger.error("is syz sf ? false");
        }
        logger.info("check4 left syz end");
        return arrayList;
    }

    public GenSolvablePolynomial<C>[] leftOreCond(GenSolvablePolynomial<C> a, GenSolvablePolynomial<C> b) {
        if (a == null || a.isZERO() || b == null || b.isZERO()) {
            throw new IllegalArgumentException("a and b must be non zero");
        }
        GenSolvablePolynomialRing pfac = a.ring;
        GenSolvablePolynomial[] oc = (GenSolvablePolynomial[]) new GenSolvablePolynomial[2];
        if (a.equals(b)) {
            oc[0] = pfac.getONE();
            oc[1] = pfac.getONE();
        } else if (a.isConstant()) {
            if (pfac.coFac.isCommutative()) {
                oc[0] = b;
                oc[1] = a;
            } else {
                oc[1] = pfac.getONE();
                oc[0] = b.multiply((GcdRingElem) ((GcdRingElem) a.leadingBaseCoefficient()).inverse());
            }
        } else if (!b.isConstant()) {
            logger.info("computing left Ore condition: " + a + ", " + b);
            List<GenSolvablePolynomial<C>> F = new ArrayList(2);
            F.add(a);
            F.add(b);
            List<List<GenSolvablePolynomial<C>>> Gz = leftZeroRelationsArbitrary((List) F);
            if (Gz.size() < 0) {
                Gz = this.msbb.leftGB(new ModuleList(pfac, (List) Gz)).castToSolvableList();
            }
            List<GenSolvablePolynomial<C>> G1 = null;
            GenSolvablePolynomial<C> g1 = null;
            for (List<GenSolvablePolynomial<C>> Gi : Gz) {
                if (!((GenSolvablePolynomial) Gi.get(0)).isZERO()) {
                    if (G1 == null) {
                        G1 = Gi;
                    }
                    if (g1 == null) {
                        g1 = (GenSolvablePolynomial) G1.get(0);
                    } else if (g1.compareTo((GenPolynomial) Gi.get(0)) > 0) {
                        G1 = Gi;
                        g1 = (GenSolvablePolynomial) G1.get(0);
                    }
                }
            }
            oc[0] = g1;
            oc[1] = (GenSolvablePolynomial) ((GenSolvablePolynomial) G1.get(1)).negate();
        } else if (pfac.coFac.isCommutative()) {
            oc[0] = b;
            oc[1] = a;
        } else {
            oc[0] = pfac.getONE();
            oc[1] = a.multiply((GcdRingElem) ((GcdRingElem) b.leadingBaseCoefficient()).inverse());
        }
        return oc;
    }

    public GenSolvablePolynomial<C>[] rightOreCond(GenSolvablePolynomial<C> a, GenSolvablePolynomial<C> b) {
        if (a == null || a.isZERO() || b == null || b.isZERO()) {
            throw new IllegalArgumentException("a and b must be non zero");
        }
        GenSolvablePolynomialRing<C> pfac = a.ring;
        GenSolvablePolynomial[] oc = (GenSolvablePolynomial[]) new GenSolvablePolynomial[2];
        if (a.equals(b)) {
            oc[0] = pfac.getONE();
            oc[1] = pfac.getONE();
        } else if (a.isConstant()) {
            if (pfac.coFac.isCommutative()) {
                oc[0] = b;
                oc[1] = a;
            } else {
                oc[1] = pfac.getONE();
                oc[0] = b.multiply((GcdRingElem) ((GcdRingElem) a.leadingBaseCoefficient()).inverse());
            }
        } else if (!b.isConstant()) {
            logger.info("computing right Ore condition: " + a + ", " + b);
            List<GenSolvablePolynomial<C>> F = new ArrayList(2);
            F.add(a);
            F.add(b);
            List<GenSolvablePolynomial<C>> G1 = null;
            GenSolvablePolynomial<C> g1 = null;
            for (List<GenSolvablePolynomial<C>> Gi : rightZeroRelationsArbitrary((List) F)) {
                if (!((GenSolvablePolynomial) Gi.get(0)).isZERO()) {
                    if (G1 == null) {
                        G1 = Gi;
                    }
                    if (g1 == null) {
                        g1 = (GenSolvablePolynomial) G1.get(0);
                    } else if (g1.compareTo((GenPolynomial) Gi.get(0)) > 0) {
                        G1 = Gi;
                        g1 = (GenSolvablePolynomial) G1.get(0);
                    }
                }
            }
            oc[0] = (GenSolvablePolynomial) G1.get(0);
            oc[1] = (GenSolvablePolynomial) ((GenSolvablePolynomial) G1.get(1)).negate();
        } else if (pfac.coFac.isCommutative()) {
            oc[0] = b;
            oc[1] = a;
        } else {
            oc[0] = pfac.getONE();
            oc[1] = a.multiply((GcdRingElem) ((GcdRingElem) b.leadingBaseCoefficient()).inverse());
        }
        return oc;
    }

    public GenSolvablePolynomial<C>[] leftSimplifier(GenSolvablePolynomial<C> a, GenSolvablePolynomial<C> b) {
        if (a == null || a.isZERO() || b == null || b.isZERO()) {
            throw new IllegalArgumentException("a and b must be non zero");
        } else if (a.isConstant() || b.isConstant()) {
            return new GenSolvablePolynomial[]{a, b};
        } else if (a.totalDegree() > 3 || b.totalDegree() > 3) {
            logger.warn("skipping GB computation: degs = " + a.totalDegree() + ", " + b.totalDegree());
            return new GenSolvablePolynomial[]{a, b};
        } else {
            GenSolvablePolynomial<C>[] oc = rightOreCond(a, b);
            logger.info("oc = " + Arrays.toString(oc));
            List<GenSolvablePolynomial<C>> F = new ArrayList(oc.length);
            F.add((GenSolvablePolynomial) oc[1].negate());
            F.add(oc[0]);
            List<List<GenSolvablePolynomial<C>>> Gz = leftZeroRelationsArbitrary((List) F);
            List<GenSolvablePolynomial<C>> G1 = new ArrayList(Gz.size());
            List<GenSolvablePolynomial<C>> G2 = new ArrayList(Gz.size());
            for (List<GenSolvablePolynomial<C>> ll : Gz) {
                if (!((GenSolvablePolynomial) ll.get(0)).isZERO()) {
                    G1.add(ll.get(0));
                    G2.add(ll.get(1));
                }
            }
            logger.info("G1(den): " + G1 + ", G2(num): " + G2);
            SolvableExtendedGB<C> exgb = this.sbb.extLeftGB(G1);
            List list = exgb.F;
            logger.info("exgb.F: " + r0 + ", exgb.G: " + exgb.G);
            List<GenSolvablePolynomial<C>> G = exgb.G;
            int m = 0;
            GenSolvablePolynomial<C> min = null;
            for (int i = 0; i < G.size(); i++) {
                if (min == null) {
                    min = (GenSolvablePolynomial) G.get(i);
                    m = i;
                } else {
                    if (min.compareTo((GenPolynomial) G.get(i)) > 0) {
                        min = (GenSolvablePolynomial) G.get(i);
                        m = i;
                    }
                }
            }
            GenSolvablePolynomial<C> min2 = (GenSolvablePolynomial) this.blas.scalarProduct(PolynomialList.castToList((List) exgb.G2F.get(m)), PolynomialList.castToList(G2));
            logger.info("min(den): " + min + ", min(num): " + min2 + ", m = " + m + ", " + exgb.G2F.get(m));
            GenSolvablePolynomial<C> n = min2;
            GenSolvablePolynomial<C> d = min;
            if (d.signum() < 0) {
                n = (GenSolvablePolynomial) n.negate();
                d = (GenSolvablePolynomial) d.negate();
            }
            GcdRingElem lc = (GcdRingElem) d.leadingBaseCoefficient();
            if (!lc.isONE() && lc.isUnit()) {
                RingElem lc2 = (GcdRingElem) lc.inverse();
                n = n.multiplyLeft(lc2);
                d = d.multiplyLeft(lc2);
            }
            if (debug) {
                int t = compare(a, b, n, d);
                if (t != 0) {
                    oc[0] = a;
                    oc[1] = b;
                    throw new RuntimeException("simp wrong, giving up: t = " + t);
                }
            }
            oc[0] = n;
            oc[1] = d;
            return oc;
        }
    }
}
