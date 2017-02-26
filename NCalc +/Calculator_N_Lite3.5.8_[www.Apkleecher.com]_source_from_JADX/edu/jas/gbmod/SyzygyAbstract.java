package edu.jas.gbmod;

import edu.jas.gb.Reduction;
import edu.jas.gb.ReductionSeq;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.ModuleList;
import edu.jas.poly.PolynomialList;
import edu.jas.structure.GcdRingElem;
import edu.jas.vector.BasicLinAlg;
import edu.jas.vector.GenVector;
import edu.jas.vector.GenVectorModul;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

public abstract class SyzygyAbstract<C extends GcdRingElem<C>> implements Syzygy<C> {
    private static final Logger logger;
    protected BasicLinAlg<GenPolynomial<C>> blas;
    private final boolean debug;
    protected Reduction<C> red;

    static {
        logger = Logger.getLogger(SyzygyAbstract.class);
    }

    public SyzygyAbstract() {
        this.debug = logger.isDebugEnabled();
        this.red = new ReductionSeq();
        this.blas = new BasicLinAlg();
    }

    public List<List<GenPolynomial<C>>> zeroRelations(List<GenPolynomial<C>> F) {
        return zeroRelations(0, (List) F);
    }

    public List<List<GenPolynomial<C>>> zeroRelations(int modv, List<GenPolynomial<C>> F) {
        List<List<GenPolynomial<C>>> Z = new ArrayList();
        if (F == null) {
            return Z;
        }
        GenVectorModul<GenPolynomial<C>> mfac = null;
        while (mfac == null && 0 < F.size()) {
            GenPolynomial<C> p = (GenPolynomial) F.get(0);
            if (p != null) {
                mfac = new GenVectorModul(p.ring, F.size());
            }
        }
        return mfac != null ? zeroRelations(modv, mfac.fromList((List) F)) : Z;
    }

    public List<List<GenPolynomial<C>>> zeroRelations(int modv, GenVector<GenPolynomial<C>> v) {
        List<List<GenPolynomial<C>>> Z = new ArrayList();
        GenVectorModul<GenPolynomial<C>> mfac = v.modul;
        List<GenPolynomial<C>> F = v.val;
        GenVector<GenPolynomial<C>> S = mfac.getZERO();
        for (int i = 0; i < F.size(); i++) {
            GenPolynomial pi = (GenPolynomial) F.get(i);
            for (int j = i + 1; j < F.size(); j++) {
                GenPolynomial pj = (GenPolynomial) F.get(j);
                if (this.red.moduleCriterion(modv, pi, pj)) {
                    List<GenPolynomial<C>> row = S.copy().val;
                    GenPolynomial<C> s = this.red.SPolynomial(row, i, pi, j, pj);
                    if (s.isZERO()) {
                        Z.add(row);
                    } else if (this.red.normalform(row, F, s).isZERO()) {
                        if (this.debug) {
                            logger.info("row = " + row.size());
                        }
                        Z.add(row);
                    } else {
                        throw new RuntimeException("Syzygy no GB");
                    }
                }
            }
        }
        return Z;
    }

    public ModuleList<C> zeroRelations(ModuleList<C> M) {
        ModuleList<C> N = M;
        if (M == null || M.list == null) {
            return N;
        }
        if (M.rows == 0 || M.cols == 0) {
            return N;
        }
        GenPolynomial<C> zero = M.ring.getZERO();
        PolynomialList<C> F = M.getPolynomialList();
        List<List<GenPolynomial<C>>> G = zeroRelations(M.cols, F.list);
        List Z = new ArrayList();
        for (int i = 0; i < G.size(); i++) {
            List<GenPolynomial<C>> Gi = (List) G.get(i);
            List<GenPolynomial<C>> Zi = new ArrayList();
            for (int j = 0; j < Gi.size(); j++) {
                GenPolynomial<C> p = (GenPolynomial) Gi.get(j);
                if (p != null) {
                    Map<ExpVector, GenPolynomial<C>> r = p.contract(M.ring);
                    int s = 0;
                    for (GenPolynomial<C> add : r.values()) {
                        Zi.add(add);
                        s++;
                    }
                    if (s == 0) {
                        Zi.add(zero);
                    } else if (s > 1) {
                        System.out.println("p = " + p);
                        System.out.println("map(" + i + "," + j + ") = " + r + ", size = " + r.size());
                        throw new RuntimeException("Map.size() > 1 = " + r.size());
                    }
                }
            }
            Z.add(Zi);
        }
        return new ModuleList(M.ring, Z);
    }

    public boolean isZeroRelation(List<List<GenPolynomial<C>>> Z, List<GenPolynomial<C>> F) {
        for (List row : Z) {
            GenPolynomial<C> p = (GenPolynomial) this.blas.scalarProduct(row, (List) F);
            if (p != null && !p.isZERO()) {
                logger.info("is not ZeroRelation = " + p.toString(p.ring.getVars()));
                logger.info("row = " + row);
                return false;
            }
        }
        return true;
    }

    public boolean isZeroRelation(ModuleList<C> Z, ModuleList<C> F) {
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

    public List<List<GenPolynomial<C>>> zeroRelationsArbitrary(List<GenPolynomial<C>> F) {
        return zeroRelationsArbitrary(0, F);
    }

    public ModuleList<C> zeroRelationsArbitrary(ModuleList<C> M) {
        ModuleList<C> N = M;
        if (M == null || M.list == null) {
            return N;
        }
        if (M.rows == 0 || M.cols == 0) {
            return N;
        }
        GenPolynomial<C> zero = M.ring.getZERO();
        PolynomialList<C> F = M.getPolynomialList();
        List<List<GenPolynomial<C>>> G = zeroRelationsArbitrary(M.cols, F.list);
        List Z = new ArrayList();
        for (int i = 0; i < G.size(); i++) {
            List<GenPolynomial<C>> Gi = (List) G.get(i);
            List<GenPolynomial<C>> Zi = new ArrayList();
            for (int j = 0; j < Gi.size(); j++) {
                GenPolynomial<C> p = (GenPolynomial) Gi.get(j);
                if (p != null) {
                    Map<ExpVector, GenPolynomial<C>> r = p.contract(M.ring);
                    int s = 0;
                    for (GenPolynomial<C> add : r.values()) {
                        Zi.add(add);
                        s++;
                    }
                    if (s == 0) {
                        Zi.add(zero);
                    } else if (s > 1) {
                        System.out.println("p = " + p);
                        System.out.println("map(" + i + "," + j + ") = " + r + ", size = " + r.size());
                        throw new RuntimeException("Map.size() > 1 = " + r.size());
                    }
                }
            }
            Z.add(Zi);
        }
        return new ModuleList(M.ring, Z);
    }
}
