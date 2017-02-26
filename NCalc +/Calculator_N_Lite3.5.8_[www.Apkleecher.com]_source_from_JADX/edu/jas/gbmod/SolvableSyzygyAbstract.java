package edu.jas.gbmod;

import edu.jas.gb.Reduction;
import edu.jas.gb.ReductionSeq;
import edu.jas.gb.SolvableReduction;
import edu.jas.gb.SolvableReductionSeq;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.poly.GenSolvablePolynomialRing;
import edu.jas.poly.ModuleList;
import edu.jas.poly.PolynomialList;
import edu.jas.structure.GcdRingElem;
import edu.jas.vector.BasicLinAlg;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public abstract class SolvableSyzygyAbstract<C extends GcdRingElem<C>> implements SolvableSyzygy<C> {
    private static final Logger logger;
    protected BasicLinAlg<GenPolynomial<C>> blas;
    private final boolean debug;
    protected Reduction<C> red;
    public final SolvableReduction<C> sred;

    public abstract GenSolvablePolynomial<C>[] leftSimplifier(GenSolvablePolynomial<C> genSolvablePolynomial, GenSolvablePolynomial<C> genSolvablePolynomial2);

    static {
        logger = Logger.getLogger(SolvableSyzygyAbstract.class);
    }

    public SolvableSyzygyAbstract() {
        this.debug = logger.isDebugEnabled();
        this.red = new ReductionSeq();
        this.sred = new SolvableReductionSeq();
        this.blas = new BasicLinAlg();
    }

    public List<List<GenSolvablePolynomial<C>>> leftZeroRelations(List<GenSolvablePolynomial<C>> F) {
        return leftZeroRelations(0, F);
    }

    public List<List<GenSolvablePolynomial<C>>> leftZeroRelations(int modv, List<GenSolvablePolynomial<C>> F) {
        int i;
        int j;
        List<List<GenSolvablePolynomial<C>>> Z = new ArrayList();
        ArrayList<GenSolvablePolynomial<C>> S = new ArrayList(F.size());
        for (i = 0; i < F.size(); i++) {
            S.add(null);
        }
        GenSolvablePolynomial<C> zero = null;
        for (i = 0; i < F.size(); i++) {
            GenPolynomial pi = (GenSolvablePolynomial) F.get(i);
            if (zero == null) {
                zero = pi.ring.getZERO();
            }
            for (j = i + 1; j < F.size(); j++) {
                GenPolynomial pj = (GenSolvablePolynomial) F.get(j);
                if (this.red.moduleCriterion(modv, pi, pj)) {
                    List<GenSolvablePolynomial<C>> row = new ArrayList(S);
                    GenSolvablePolynomial<C> s = this.sred.leftSPolynomial(row, i, pi, j, pj);
                    if (s.isZERO()) {
                        Z.add(row);
                    } else if (this.sred.leftNormalform(row, F, s).isZERO()) {
                        if (logger.isDebugEnabled()) {
                            logger.info("row = " + row);
                        }
                        Z.add(row);
                    } else {
                        throw new ArithmeticException("Syzygy no leftGB");
                    }
                }
            }
        }
        for (List<GenSolvablePolynomial<C>> vr : Z) {
            for (j = 0; j < vr.size(); j++) {
                if (vr.get(j) == null) {
                    vr.set(j, zero);
                }
            }
        }
        return Z;
    }

    public ModuleList<C> leftZeroRelations(ModuleList<C> M) {
        if (M == null || M.list == null) {
            return null;
        }
        if (M.rows == 0 || M.cols == 0) {
            return null;
        }
        GenSolvablePolynomial<C> zero = (GenSolvablePolynomial) M.ring.getZERO();
        PolynomialList<C> F = M.getPolynomialList();
        int modv = M.cols;
        logger.info("modv = " + modv);
        List<List<GenSolvablePolynomial<C>>> G = leftZeroRelations(modv, F.castToSolvableList());
        List Z = new ArrayList();
        for (int i = 0; i < G.size(); i++) {
            List<GenSolvablePolynomial<C>> Gi = (List) G.get(i);
            List<GenSolvablePolynomial<C>> Zi = new ArrayList();
            for (int j = 0; j < Gi.size(); j++) {
                GenSolvablePolynomial<C> p = (GenSolvablePolynomial) Gi.get(j);
                if (p != null) {
                    Map<ExpVector, GenPolynomial<C>> r = p.contract(M.ring);
                    if (r.size() == 0) {
                        Zi.add(zero);
                    } else if (r.size() == 1) {
                        Zi.add(r.values().toArray()[0]);
                    } else {
                        throw new RuntimeException("Map.size() > 1 = " + r.size());
                    }
                }
            }
            Z.add(Zi);
        }
        return new ModuleList((GenSolvablePolynomialRing) M.ring, Z);
    }

    public List<List<GenSolvablePolynomial<C>>> rightZeroRelations(List<GenSolvablePolynomial<C>> F) {
        return rightZeroRelations(0, F);
    }

    public List<List<GenSolvablePolynomial<C>>> rightZeroRelations(int modv, List<GenSolvablePolynomial<C>> F) {
        GenSolvablePolynomialRing<C> ring = null;
        for (GenSolvablePolynomial<C> p : F) {
            if (p != null) {
                ring = p.ring;
                break;
            }
        }
        if (ring == null) {
            List<List<GenSolvablePolynomial<C>>> Z = new ArrayList(1);
            Z.add(F);
            return Z;
        }
        GenSolvablePolynomialRing rring = ring.reverse(true);
        List rF = new ArrayList(F.size());
        for (GenSolvablePolynomial<C> p2 : F) {
            if (p2 != null) {
                rF.add((GenSolvablePolynomial) p2.reverse(rring));
            }
        }
        if (this.debug) {
            logger.info("reversed problem = " + new PolynomialList(rring, rF).toScript());
        }
        List<List<GenSolvablePolynomial<C>>> rZ = leftZeroRelations(modv, rF);
        if (this.debug) {
            boolean isit = isLeftZeroRelation((List) rZ, rF);
            logger.debug("isLeftZeroRelation = " + isit);
        }
        GenSolvablePolynomialRing<C> oring = rring.reverse(true);
        if (this.debug) {
            logger.debug("ring == oring: " + ring.equals(oring));
        }
        ring = oring;
        Z = new ArrayList(rZ.size());
        for (List<GenSolvablePolynomial<C>> z : rZ) {
            if (z != null) {
                List<GenSolvablePolynomial<C>> s = new ArrayList(z.size());
                for (GenSolvablePolynomial<C> p22 : z) {
                    if (p22 != null) {
                        s.add((GenSolvablePolynomial) p22.reverse(ring));
                    }
                }
                Z.add(s);
            }
        }
        return Z;
    }

    public ModuleList<C> rightZeroRelations(ModuleList<C> M) {
        if (M == null || M.list == null) {
            return null;
        }
        if (M.rows == 0 || M.cols == 0) {
            return null;
        }
        GenSolvablePolynomial<C> zero = (GenSolvablePolynomial) M.ring.getZERO();
        PolynomialList<C> F = M.getPolynomialList();
        int modv = M.cols;
        logger.info("modv = " + modv);
        List<List<GenSolvablePolynomial<C>>> G = rightZeroRelations(modv, F.castToSolvableList());
        List Z = new ArrayList();
        for (int i = 0; i < G.size(); i++) {
            List<GenSolvablePolynomial<C>> Gi = (List) G.get(i);
            List<GenSolvablePolynomial<C>> Zi = new ArrayList();
            for (int j = 0; j < Gi.size(); j++) {
                GenSolvablePolynomial<C> p = (GenSolvablePolynomial) Gi.get(j);
                if (p != null) {
                    Map<ExpVector, GenPolynomial<C>> r = p.contract(M.ring);
                    if (r.size() == 0) {
                        Zi.add(zero);
                    } else if (r.size() == 1) {
                        Zi.add(r.values().toArray()[0]);
                    } else {
                        throw new RuntimeException("Map.size() > 1 = " + r.size());
                    }
                }
            }
            Z.add(Zi);
        }
        return new ModuleList((GenSolvablePolynomialRing) M.ring, Z);
    }

    public boolean isLeftZeroRelation(List<List<GenSolvablePolynomial<C>>> Z, List<GenSolvablePolynomial<C>> F) {
        List Fp = PolynomialList.castToList(F);
        for (List<GenSolvablePolynomial<C>> row : Z) {
            GenPolynomial<C> p = (GenPolynomial) this.blas.scalarProduct(PolynomialList.castToList(row), Fp);
            if (p != null && !p.isZERO()) {
                logger.info("is not ZeroRelation = " + p);
                return false;
            }
        }
        return true;
    }

    public boolean isRightZeroRelation(List<List<GenSolvablePolynomial<C>>> Z, List<GenSolvablePolynomial<C>> F) {
        List Fp = PolynomialList.castToList(F);
        for (List<GenSolvablePolynomial<C>> row : Z) {
            GenPolynomial<C> p = (GenPolynomial) this.blas.scalarProduct(Fp, PolynomialList.castToList(row));
            if (p != null && !p.isZERO()) {
                logger.info("is not ZeroRelation = " + p);
                return false;
            }
        }
        return true;
    }

    public boolean isLeftZeroRelation(ModuleList<C> Z, ModuleList<C> F) {
        if (Z == null || Z.list == null) {
            return true;
        }
        for (List<GenPolynomial<C>> row : Z.list) {
            List<GenPolynomial<C>> zr = this.blas.leftScalarProduct(row, F.list);
            if (!this.blas.isZero(zr)) {
                logger.info("is not ZeroRelation (" + zr.size() + ") = " + zr);
                return false;
            }
        }
        return true;
    }

    public boolean isRightZeroRelation(ModuleList<C> Z, ModuleList<C> F) {
        if (Z == null || Z.list == null) {
            return true;
        }
        for (List<GenPolynomial<C>> row : Z.list) {
            List<GenPolynomial<C>> zr = this.blas.rightScalarProduct(row, F.list);
            if (!this.blas.isZero(zr)) {
                logger.info("is not ZeroRelation (" + zr.size() + ") = " + zr);
                return false;
            }
        }
        return true;
    }

    public List<List<GenSolvablePolynomial<C>>> leftZeroRelationsArbitrary(List<GenSolvablePolynomial<C>> F) {
        return leftZeroRelationsArbitrary(0, F);
    }

    public ModuleList<C> leftZeroRelationsArbitrary(ModuleList<C> M) {
        if (M == null || M.list == null) {
            return null;
        }
        if (M.rows == 0 || M.cols == 0) {
            return null;
        }
        GenSolvablePolynomial<C> zero = (GenSolvablePolynomial) M.ring.getZERO();
        PolynomialList<C> F = M.getPolynomialList();
        int modv = M.cols;
        logger.info("modv = " + modv);
        List<List<GenSolvablePolynomial<C>>> G = leftZeroRelationsArbitrary(modv, F.castToSolvableList());
        if (G == null) {
            return null;
        }
        List Z = new ArrayList();
        for (int i = 0; i < G.size(); i++) {
            List<GenSolvablePolynomial<C>> Gi = (List) G.get(i);
            List<GenSolvablePolynomial<C>> Zi = new ArrayList();
            for (int j = 0; j < Gi.size(); j++) {
                GenSolvablePolynomial<C> p = (GenSolvablePolynomial) Gi.get(j);
                if (p != null) {
                    Map<ExpVector, GenPolynomial<C>> r = p.contract(M.ring);
                    if (r.size() == 0) {
                        Zi.add(zero);
                    } else if (r.size() == 1) {
                        Zi.add(r.values().toArray()[0]);
                    } else {
                        throw new RuntimeException("Map.size() > 1 = " + r.size());
                    }
                }
            }
            Z.add(Zi);
        }
        return new ModuleList((GenSolvablePolynomialRing) M.ring, Z);
    }

    public List<List<GenSolvablePolynomial<C>>> rightZeroRelationsArbitrary(List<GenSolvablePolynomial<C>> F) {
        return rightZeroRelationsArbitrary(0, F);
    }

    public List<List<GenSolvablePolynomial<C>>> rightZeroRelationsArbitrary(int modv, List<GenSolvablePolynomial<C>> F) {
        GenSolvablePolynomialRing<C> ring = null;
        for (GenSolvablePolynomial<C> p : F) {
            if (p != null) {
                ring = p.ring;
                break;
            }
        }
        if (ring == null) {
            List<List<GenSolvablePolynomial<C>>> Z = new ArrayList(1);
            Z.add(F);
            return Z;
        }
        GenSolvablePolynomialRing rring = ring.reverse(true);
        List rF = new ArrayList(F.size());
        for (GenSolvablePolynomial<C> p2 : F) {
            if (p2 != null) {
                rF.add((GenSolvablePolynomial) p2.reverse(rring));
            }
        }
        if (this.debug) {
            logger.info("reversed problem = " + new PolynomialList(rring, rF).toScript());
        }
        List<List<GenSolvablePolynomial<C>>> rZ = leftZeroRelationsArbitrary(modv, rF);
        if (this.debug) {
            logger.info("reversed syzygies = " + new ModuleList(rring, (List) rZ).toScript());
            boolean isit = isLeftZeroRelation((List) rZ, rF);
            logger.info("isLeftZeroRelation = " + isit);
        }
        GenSolvablePolynomialRing<C> oring = rring.reverse(true);
        if (this.debug) {
            logger.info("ring == oring: " + ring.equals(oring));
        }
        ring = oring;
        Z = new ArrayList(rZ.size());
        for (List<GenSolvablePolynomial<C>> z : rZ) {
            if (z != null) {
                List<GenSolvablePolynomial<C>> arrayList = new ArrayList(z.size());
                for (GenSolvablePolynomial<C> p22 : z) {
                    if (p22 != null) {
                        arrayList.add((GenSolvablePolynomial) p22.reverse(ring));
                    }
                }
                Z.add(arrayList);
            }
        }
        return Z;
    }

    public ModuleList<C> rightZeroRelationsArbitrary(ModuleList<C> M) {
        if (M == null || M.list == null) {
            return null;
        }
        if (M.rows == 0 || M.cols == 0) {
            return null;
        }
        GenSolvablePolynomial<C> zero = (GenSolvablePolynomial) M.ring.getZERO();
        PolynomialList<C> F = M.getPolynomialList();
        int modv = M.cols;
        logger.info("modv = " + modv);
        List<List<GenSolvablePolynomial<C>>> G = rightZeroRelationsArbitrary(modv, F.castToSolvableList());
        List Z = new ArrayList();
        for (int i = 0; i < G.size(); i++) {
            List<GenSolvablePolynomial<C>> Gi = (List) G.get(i);
            List<GenSolvablePolynomial<C>> Zi = new ArrayList();
            for (int j = 0; j < Gi.size(); j++) {
                GenSolvablePolynomial<C> p = (GenSolvablePolynomial) Gi.get(j);
                if (p != null) {
                    Map<ExpVector, GenPolynomial<C>> r = p.contract(M.ring);
                    if (r.size() == 0) {
                        Zi.add(zero);
                    } else if (r.size() == 1) {
                        Zi.add(r.values().toArray()[0]);
                    } else {
                        logger.error("p = " + p + ", r = " + r);
                        throw new RuntimeException("Map.size() > 1 = " + r.size());
                    }
                }
            }
            Z.add(Zi);
        }
        return new ModuleList((GenSolvablePolynomialRing) M.ring, Z);
    }

    public boolean isLeftOreCond(GenSolvablePolynomial<C> a, GenSolvablePolynomial<C> b, GenSolvablePolynomial<C>[] oc) {
        return oc[0].multiply((GenSolvablePolynomial) a).equals(oc[1].multiply((GenSolvablePolynomial) b));
    }

    public boolean isRightOreCond(GenSolvablePolynomial<C> a, GenSolvablePolynomial<C> b, GenSolvablePolynomial<C>[] oc) {
        return a.multiply(oc[0]).equals(b.multiply(oc[1]));
    }

    public int compare(GenSolvablePolynomial<C> num, GenSolvablePolynomial<C> den, GenSolvablePolynomial<C> n, GenSolvablePolynomial<C> d) {
        if (n == null || n.isZERO()) {
            return num.signum();
        }
        if (num.isZERO()) {
            return -n.signum();
        }
        int t = (num.signum() - n.signum()) / 2;
        if (t != 0) {
            System.out.println("compareTo: t = " + t);
        }
        if (den.compareTo((GenPolynomial) d) == 0) {
            return num.compareTo((GenPolynomial) n);
        }
        GenSolvablePolynomial<C>[] oc = leftOreCond(den, d);
        if (this.debug) {
            System.out.println("oc[0] den =<>= oc[1] d: (" + oc[0] + ") (" + den + ") = (" + oc[1] + ") (" + d + ")");
        }
        GenSolvablePolynomial<C> r = oc[0].multiply((GenSolvablePolynomial) num);
        GenSolvablePolynomial<C> s = oc[1].multiply((GenSolvablePolynomial) n);
        logger.info("compare: r = " + r + ", s = " + s);
        return r.compareTo((GenPolynomial) s);
    }
}
