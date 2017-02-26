package edu.jas.poly;

import edu.jas.arith.BigInteger;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.vector.BasicLinAlg;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public class TermOrderOptimization {
    private static final Logger logger;

    static {
        logger = Logger.getLogger(TermOrderOptimization.class);
    }

    public static <C extends RingElem<C>> List<GenPolynomial<BigInteger>> degreeMatrix(GenPolynomial<C> A) {
        if (A == null) {
            return null;
        }
        GenPolynomialRing<BigInteger> ufac = new GenPolynomialRing(new BigInteger(), 1);
        int nvar = A.numberOfVariables();
        List<GenPolynomial<BigInteger>> dem = new ArrayList(nvar);
        for (int i = 0; i < nvar; i++) {
            dem.add(ufac.getZERO());
        }
        if (A.isZERO()) {
            return dem;
        }
        for (ExpVector e : A.getMap().keySet()) {
            dem = expVectorAdd(dem, e);
        }
        return dem;
    }

    public static List<GenPolynomial<BigInteger>> expVectorAdd(List<GenPolynomial<BigInteger>> dm, ExpVector e) {
        int i = 0;
        while (i < dm.size() && i < e.length()) {
            GenPolynomial<BigInteger> p = (GenPolynomial) dm.get(i);
            dm.set(i, p.sum(p.ring.getONECoefficient(), ExpVector.create(1, 0, e.getVal(i))));
            i++;
        }
        return dm;
    }

    public static <C extends RingElem<C>> List<GenPolynomial<BigInteger>> degreeMatrixOfCoefficients(GenPolynomial<GenPolynomial<C>> A) {
        if (A != null) {
            return degreeMatrix(A.getMap().values());
        }
        throw new IllegalArgumentException("polynomial must not be null");
    }

    public static <C extends RingElem<C>> List<GenPolynomial<BigInteger>> degreeMatrix(Collection<GenPolynomial<C>> L) {
        if (L == null) {
            throw new IllegalArgumentException("list must be non null");
        }
        BasicLinAlg<GenPolynomial<BigInteger>> blas = new BasicLinAlg();
        List<GenPolynomial<BigInteger>> dem = null;
        for (GenPolynomial p : L) {
            List<GenPolynomial<BigInteger>> dm = degreeMatrix(p);
            if (dem == null) {
                dem = dm;
            } else {
                dem = blas.vectorAdd(dem, dm);
            }
        }
        return dem;
    }

    public static <C extends RingElem<C>> List<GenPolynomial<BigInteger>> degreeMatrixOfCoefficients(Collection<GenPolynomial<GenPolynomial<C>>> L) {
        if (L == null) {
            throw new IllegalArgumentException("list must not be null");
        }
        BasicLinAlg<GenPolynomial<BigInteger>> blas = new BasicLinAlg();
        List<GenPolynomial<BigInteger>> dem = null;
        for (GenPolynomial p : L) {
            List<GenPolynomial<BigInteger>> dm = degreeMatrixOfCoefficients(p);
            if (dem == null) {
                dem = dm;
            } else {
                dem = blas.vectorAdd(dem, dm);
            }
        }
        return dem;
    }

    public static List<Integer> optimalPermutation(List<GenPolynomial<BigInteger>> D) {
        if (D == null) {
            throw new IllegalArgumentException("list must be non null");
        }
        List<Integer> P = new ArrayList(D.size());
        if (D.size() != 0) {
            if (D.size() == 1) {
                P.add(Integer.valueOf(0));
            } else {
                SortedMap<GenPolynomial<BigInteger>, List<Integer>> map = new TreeMap();
                int i = 0;
                for (GenPolynomial<BigInteger> p : D) {
                    List<Integer> il = (List) map.get(p);
                    if (il == null) {
                        il = new ArrayList(3);
                    }
                    il.add(Integer.valueOf(i));
                    map.put(p, il);
                    i++;
                }
                List<List<Integer>> V = new ArrayList(map.values());
                if (logger.isDebugEnabled()) {
                    logger.info("V = " + V);
                }
                for (int j = 0; j < V.size(); j++) {
                    for (Integer k : (List) V.get(j)) {
                        P.add(k);
                    }
                }
            }
        }
        return P;
    }

    public static List<Integer> inversePermutation(List<Integer> P) {
        if (P == null || P.size() <= 1) {
            return P;
        }
        List<Integer> ip = new ArrayList(P);
        for (int i = 0; i < P.size(); i++) {
            ip.set(((Integer) P.get(i)).intValue(), Integer.valueOf(i));
        }
        return ip;
    }

    public static boolean isIdentityPermutation(List<Integer> P) {
        if (P == null || P.size() <= 1) {
            return true;
        }
        for (int i = 0; i < P.size(); i++) {
            if (((Integer) P.get(i)).intValue() != i) {
                return false;
            }
        }
        return true;
    }

    public static List<Integer> multiplyPermutation(List<Integer> P, List<Integer> S) {
        if (P == null || S == null) {
            return null;
        }
        if (P.size() != S.size()) {
            throw new IllegalArgumentException("#P != #S: P =" + P + ", S = " + S);
        }
        List<Integer> ip = new ArrayList(P);
        for (int i = 0; i < P.size(); i++) {
            ip.set(i, S.get(((Integer) P.get(i)).intValue()));
        }
        return ip;
    }

    public static <T> List<T> listPermutation(List<Integer> P, List<T> L) {
        if (L == null || L.size() <= 1) {
            return L;
        }
        List<T> pl = new ArrayList(L.size());
        for (Integer i : P) {
            pl.add(L.get(i.intValue()));
        }
        return pl;
    }

    public static <C extends RingElem<C>> GenPolynomial<C> permutation(List<Integer> P, GenPolynomialRing<C> R, GenPolynomial<C> A) {
        if (A == null) {
            return A;
        }
        GenPolynomial<C> B = R.getZERO().copy();
        Map<ExpVector, C> Bv = B.val;
        for (Entry<ExpVector, C> y : A.getMap().entrySet()) {
            RingElem a = (RingElem) y.getValue();
            Bv.put(((ExpVector) y.getKey()).permutation(P), a);
        }
        return B;
    }

    public static <C extends RingElem<C>> List<GenPolynomial<C>> permutation(List<Integer> P, GenPolynomialRing<C> R, List<GenPolynomial<C>> L) {
        if (L == null || L.size() == 0) {
            return L;
        }
        List<GenPolynomial<C>> K = new ArrayList(L.size());
        for (GenPolynomial a : L) {
            K.add(permutation((List) P, (GenPolynomialRing) R, a));
        }
        return K;
    }

    public static <C extends RingElem<C>> GenPolynomial<GenPolynomial<C>> permutationOnCoefficients(List<Integer> P, GenPolynomialRing<GenPolynomial<C>> R, GenPolynomial<GenPolynomial<C>> A) {
        if (A == null) {
            return A;
        }
        GenPolynomial<GenPolynomial<C>> B = R.getZERO().copy();
        GenPolynomialRing cf = R.coFac;
        Map<ExpVector, GenPolynomial<C>> Bv = B.val;
        for (Entry<ExpVector, GenPolynomial<C>> y : A.getMap().entrySet()) {
            Bv.put((ExpVector) y.getKey(), permutation((List) P, cf, (GenPolynomial) y.getValue()));
        }
        return B;
    }

    public static <C extends RingElem<C>> List<GenPolynomial<GenPolynomial<C>>> permutationOnCoefficients(List<Integer> P, GenPolynomialRing<GenPolynomial<C>> R, List<GenPolynomial<GenPolynomial<C>>> L) {
        if (L == null || L.size() == 0) {
            return L;
        }
        List<GenPolynomial<GenPolynomial<C>>> K = new ArrayList(L.size());
        for (GenPolynomial a : L) {
            K.add(permutationOnCoefficients((List) P, (GenPolynomialRing) R, a));
        }
        return K;
    }

    public static <C extends RingElem<C>> GenPolynomialRing<C> permutation(List<Integer> P, GenPolynomialRing<C> R) {
        return R.permutation(P);
    }

    public static <C extends RingElem<C>> OptimizedPolynomialList<C> optimizeTermOrder(GenPolynomialRing<C> R, List<GenPolynomial<C>> L) {
        Collection Lp = new ArrayList(L);
        if (R instanceof GenSolvablePolynomialRing) {
            Lp.addAll(((GenSolvablePolynomialRing) R).table.relationList());
        }
        List perm = optimalPermutation(degreeMatrix(Lp));
        GenPolynomialRing pring = R.permutation(perm);
        return new OptimizedPolynomialList(perm, pring, permutation(perm, pring, (List) L));
    }

    public static <C extends RingElem<C>> OptimizedPolynomialList<C> optimizeTermOrder(PolynomialList<C> P) {
        if (P == null) {
            return null;
        }
        return optimizeTermOrder(P.ring, P.list);
    }

    public static <C extends RingElem<C>> OptimizedPolynomialList<GenPolynomial<C>> optimizeTermOrderOnCoefficients(PolynomialList<GenPolynomial<C>> P) {
        return optimizeTermOrderOnCoefficients(P.ring, P.list);
    }

    public static <C extends RingElem<C>> OptimizedPolynomialList<GenPolynomial<C>> optimizeTermOrderOnCoefficients(GenPolynomialRing<GenPolynomial<C>> ring, List<GenPolynomial<GenPolynomial<C>>> L) {
        if (L == null) {
            return null;
        }
        GenPolynomialRing pring;
        Collection Lp = new ArrayList(L);
        if (ring instanceof GenSolvablePolynomialRing) {
            Lp.addAll(((GenSolvablePolynomialRing) ring).table.relationList());
        }
        List perm = optimalPermutation(degreeMatrixOfCoefficients(Lp));
        RingFactory pFac = ring.coFac.permutation(perm);
        if (ring instanceof GenSolvablePolynomialRing) {
            GenPolynomialRing sring = (GenSolvablePolynomialRing) ring;
            GenPolynomialRing psring = new GenSolvablePolynomialRing(pFac, sring);
            psring.addRelations(permutationOnCoefficients(perm, psring, PolynomialList.castToList(sring.table.relationList())));
            pring = psring;
        } else {
            pring = new GenPolynomialRing(pFac, (GenPolynomialRing) ring);
        }
        return new OptimizedPolynomialList(perm, pring, permutationOnCoefficients(perm, pring, (List) L));
    }

    public static <C extends RingElem<C>> OptimizedModuleList<C> optimizeTermOrder(ModuleList<C> P) {
        if (P == null) {
            return null;
        }
        return optimizeTermOrderModule(P.ring, P.list);
    }

    public static <C extends RingElem<C>> OptimizedModuleList<C> optimizeTermOrderModule(GenPolynomialRing<C> R, List<List<GenPolynomial<C>>> L) {
        Collection M = new ArrayList();
        for (List<GenPolynomial<C>> ll : L) {
            M.addAll(ll);
        }
        if (R instanceof GenSolvablePolynomialRing) {
            M.addAll(((GenSolvablePolynomialRing) R).table.relationList());
        }
        List perm = optimalPermutation(degreeMatrix(M));
        GenPolynomialRing pring = R.permutation(perm);
        List<List<GenPolynomial<C>>> mpolys = new ArrayList();
        for (List ll2 : L) {
            mpolys.add(permutation(perm, pring, ll2));
        }
        return new OptimizedModuleList(perm, pring, mpolys);
    }

    public static <C extends RingElem<C>> OptimizedModuleList<GenPolynomial<C>> optimizeTermOrderOnCoefficients(ModuleList<GenPolynomial<C>> P) {
        if (P == null) {
            return null;
        }
        GenPolynomialRing pring;
        GenPolynomialRing ring = P.ring;
        Collection M = new ArrayList();
        for (List<GenPolynomial<GenPolynomial<C>>> ll : P.list) {
            M.addAll(ll);
        }
        if (ring instanceof GenSolvablePolynomialRing) {
            M.addAll(((GenSolvablePolynomialRing) ring).table.relationList());
        }
        List perm = optimalPermutation(degreeMatrixOfCoefficients(M));
        RingFactory pFac = ring.coFac.permutation(perm);
        if (ring instanceof GenSolvablePolynomialRing) {
            GenPolynomialRing sring = (GenSolvablePolynomialRing) ring;
            GenPolynomialRing psring = new GenSolvablePolynomialRing(pFac, sring);
            psring.addRelations(permutationOnCoefficients(perm, psring, PolynomialList.castToList(sring.table.relationList())));
            pring = psring;
        } else {
            pring = new GenPolynomialRing(pFac, ring);
        }
        List<List<GenPolynomial<GenPolynomial<C>>>> mpolys = new ArrayList();
        for (List ll2 : P.list) {
            mpolys.add(permutationOnCoefficients(perm, pring, ll2));
        }
        return new OptimizedModuleList(perm, pring, mpolys);
    }
}
