package edu.jas.gbufd;

import edu.jas.gb.GroebnerBaseAbstract;
import edu.jas.gb.GroebnerBaseSeq;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.OptimizedPolynomialList;
import edu.jas.poly.PolyUtil;
import edu.jas.poly.TermOrder;
import edu.jas.poly.TermOrderOptimization;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;

public class GroebnerBasePartial<C extends GcdRingElem<C>> extends GroebnerBaseAbstract<C> {
    private static final Logger logger;
    protected GroebnerBaseAbstract<C> bb;
    protected GroebnerBaseAbstract<GenPolynomial<C>> rbb;

    static {
        logger = Logger.getLogger(GroebnerBasePartial.class);
    }

    public GroebnerBasePartial() {
        this(new GroebnerBaseSeq(), null);
    }

    public GroebnerBasePartial(RingFactory<GenPolynomial<C>> rf) {
        this(new GroebnerBaseSeq(), new GroebnerBasePseudoRecSeq(rf));
    }

    public GroebnerBasePartial(GroebnerBaseAbstract<C> bb, GroebnerBaseAbstract<GenPolynomial<C>> rbb) {
        this.bb = bb;
        this.rbb = rbb;
    }

    public List<GenPolynomial<C>> GB(int modv, List<GenPolynomial<C>> F) {
        return this.bb.GB(modv, F);
    }

    public boolean isGBrec(List<GenPolynomial<GenPolynomial<C>>> F) {
        return isGBrec(0, F);
    }

    public boolean isGBrec(int modv, List<GenPolynomial<GenPolynomial<C>>> F) {
        if (F == null || F.isEmpty()) {
            return true;
        }
        this.rbb = new GroebnerBasePseudoRecSeq(((GenPolynomial) F.get(0)).ring.coFac);
        return this.rbb.isGB(modv, (List) F);
    }

    public static List<Integer> partialPermutation(String[] vars, String[] pvars) {
        return partialPermutation(vars, pvars, null);
    }

    public static List<Integer> getPermutation(String[] aname, String[] ename) {
        if (aname == null || ename == null) {
            throw new IllegalArgumentException("aname or ename may not be null");
        }
        int i;
        List<Integer> perm = new ArrayList(aname.length);
        for (String indexOf : ename) {
            int j = indexOf(indexOf, aname);
            if (j < 0) {
                throw new IllegalArgumentException("ename not contained in aname");
            }
            perm.add(Integer.valueOf(j));
        }
        for (i = 0; i < aname.length; i++) {
            if (!perm.contains(Integer.valueOf(i))) {
                perm.add(Integer.valueOf(i));
            }
        }
        int n1 = aname.length - 1;
        List<Integer> perm1 = new ArrayList(aname.length);
        for (Integer k : perm) {
            perm1.add(Integer.valueOf(n1 - k.intValue()));
        }
        perm = perm1;
        Collections.reverse(perm);
        return perm;
    }

    public static int indexOf(String s, String[] A) {
        for (int i = 0; i < A.length; i++) {
            if (s.equals(A[i])) {
                return i;
            }
        }
        return -1;
    }

    public static List<Integer> partialPermutation(String[] vars, String[] pvars, String[] rvars) {
        if (vars == null || pvars == null) {
            throw new IllegalArgumentException("no variable names found");
        }
        List<String> variables = new ArrayList(vars.length);
        List<String> pvariables = new ArrayList(pvars.length);
        for (Object add : vars) {
            variables.add(add);
        }
        for (Object add2 : pvars) {
            pvariables.add(add2);
        }
        if (rvars == null) {
            rvars = remainingVars(vars, pvars);
        }
        List<String> rvariables = new ArrayList(rvars.length);
        for (Object add22 : rvars) {
            rvariables.add(add22);
        }
        if (rvars.length + pvars.length == vars.length) {
            return getPermutation(vars, rvars);
        }
        logger.info("not implemented for " + variables + " != " + pvariables + " cup " + rvariables);
        throw new UnsupportedOperationException("not implemented");
    }

    public static List<Integer> partialPermutation(String[] vars, String[] evars, String[] pvars, String[] rvars) {
        if (vars == null || evars == null || pvars == null) {
            throw new IllegalArgumentException("not all variable names given");
        }
        String[] uvars;
        if (rvars != null) {
            int i;
            uvars = new String[(pvars.length + rvars.length)];
            for (i = 0; i < pvars.length; i++) {
                uvars[i] = pvars[i];
            }
            for (i = 0; i < rvars.length; i++) {
                uvars[pvars.length + i] = rvars[i];
            }
        } else {
            uvars = pvars;
        }
        return partialPermutation(vars, evars, uvars);
    }

    public static String[] remainingVars(String[] vars, String[] pvars) {
        if (vars == null || pvars == null) {
            throw new IllegalArgumentException("no variable names found");
        }
        int i;
        List<String> variables = new ArrayList(vars.length);
        List<String> pvariables = new ArrayList(pvars.length);
        for (Object add : vars) {
            variables.add(add);
        }
        for (Object add2 : pvars) {
            pvariables.add(add2);
        }
        if (variables.containsAll(pvariables)) {
            List<String> rvariables = new ArrayList(variables);
            for (String s : pvariables) {
                rvariables.remove(s);
            }
            String[] rvars = new String[(vars.length - pvars.length)];
            i = 0;
            for (String s2 : rvariables) {
                int i2 = i + 1;
                rvars[i] = s2;
                i = i2;
            }
            return rvars;
        }
        throw new IllegalArgumentException("partial variables not contained in all variables ");
    }

    public OptimizedPolynomialList<GenPolynomial<C>> partialGBrec(List<GenPolynomial<C>> F, String[] pvars) {
        if (F == null || F.isEmpty()) {
            throw new IllegalArgumentException("empty F not allowed");
        }
        GenPolynomialRing<C> fac = ((GenPolynomial) F.get(0)).ring;
        String[] vars = fac.getVars();
        if (vars == null || pvars == null) {
            throw new IllegalArgumentException("not all variable names found");
        } else if (vars.length == pvars.length) {
            throw new IllegalArgumentException("use non recursive partialGB algorithm");
        } else {
            List perm = partialPermutation(vars, pvars);
            GenPolynomialRing pfac = TermOrderOptimization.permutation(perm, fac);
            if (logger.isInfoEnabled()) {
                logger.info("pfac = " + pfac);
            }
            List ppolys = TermOrderOptimization.permutation(perm, pfac, (List) F);
            int cl = fac.nvar - pvars.length;
            int pl = pvars.length;
            GenPolynomialRing<C> cfac = new GenPolynomialRing(fac.coFac, cl, fac.tord, remainingVars(vars, pvars));
            GenPolynomialRing rfac = new GenPolynomialRing(cfac, pl, fac.tord, pvars);
            if (logger.isInfoEnabled()) {
                logger.info("rfac = " + rfac);
            }
            List<GenPolynomial<GenPolynomial<C>>> Fr = PolyUtil.recursive(rfac, ppolys);
            this.rbb = new GroebnerBasePseudoRecSeq(cfac);
            return new OptimizedPolynomialList(perm, rfac, this.rbb.GB(Fr));
        }
    }

    public OptimizedPolynomialList<C> partialGB(List<GenPolynomial<C>> F, String[] pvars) {
        if (F == null || F.isEmpty()) {
            throw new IllegalArgumentException("empty F not allowed");
        }
        GenPolynomialRing<C> fac = ((GenPolynomial) F.get(0)).ring;
        String[] vars = fac.getVars();
        List perm = partialPermutation(vars, pvars);
        GenPolynomialRing pfac = TermOrderOptimization.permutation(perm, fac);
        if (logger.isInfoEnabled()) {
            logger.info("pfac = " + pfac);
        }
        List ppolys = TermOrderOptimization.permutation(perm, pfac, (List) F);
        int cl = fac.nvar - pvars.length;
        if (cl == 0) {
            return new OptimizedPolynomialList(perm, pfac, this.bb.GB(ppolys));
        }
        int pl = pvars.length;
        String[] rvars = remainingVars(vars, pvars);
        GenPolynomialRing<C> cfac = new GenPolynomialRing(fac.coFac, cl, fac.tord, rvars);
        GenPolynomialRing rfac = new GenPolynomialRing(cfac, pl, fac.tord, pvars);
        if (logger.isInfoEnabled()) {
            logger.info("rfac = " + rfac);
        }
        List<GenPolynomial<GenPolynomial<C>>> Fr = PolyUtil.recursive(rfac, ppolys);
        this.rbb = new GroebnerBasePseudoRecSeq(cfac);
        return new OptimizedPolynomialList(perm, pfac, PolyUtil.distribute(pfac, this.rbb.GB(Fr)));
    }

    public OptimizedPolynomialList<C> elimPartialGB(List<GenPolynomial<C>> F, String[] evars, String[] pvars) {
        if (F == null || F.isEmpty()) {
            throw new IllegalArgumentException("empty F not allowed");
        }
        GenPolynomialRing<C> fac = ((GenPolynomial) F.get(0)).ring;
        String[] vars = fac.getVars();
        List<Integer> perm = partialPermutation(vars, evars, pvars);
        GenPolynomialRing<C> pfac = TermOrderOptimization.permutation(perm, fac);
        if (logger.isInfoEnabled()) {
            logger.info("pfac = " + pfac);
        }
        List<GenPolynomial<C>> ppolys = TermOrderOptimization.permutation((List) perm, (GenPolynomialRing) pfac, (List) F);
        int i = fac.nvar;
        int length = evars.length;
        int cl = (r0 - r0) - pvars.length;
        if (cl == 0) {
            int ev = pfac.tord.getEvord();
            TermOrder termOrder = new TermOrder(ev, ev, pfac.nvar, evars.length);
            GenPolynomialRing<C> genPolynomialRing = new GenPolynomialRing(pfac.coFac, pfac.nvar, termOrder, pfac.getVars());
            if (logger.isInfoEnabled()) {
                logger.info("pfac = " + genPolynomialRing);
            }
            List<GenPolynomial<C>> Fs = new ArrayList(ppolys.size());
            for (GenPolynomial p : ppolys) {
                Fs.add(genPolynomialRing.copy(p));
            }
            List<Integer> list = perm;
            GenPolynomialRing<C> genPolynomialRing2 = genPolynomialRing;
            OptimizedPolynomialList<C> optimizedPolynomialList = new OptimizedPolynomialList(list, genPolynomialRing2, this.bb.GB(Fs));
            if (logger.isInfoEnabled()) {
                logger.info("pgb = " + optimizedPolynomialList);
            }
            pfac = genPolynomialRing;
            return optimizedPolynomialList;
        }
        logger.warn("not meaningful for elimination " + cl);
        int pl = pvars.length + pvars.length;
        String[] rvars = remainingVars(remainingVars(vars, evars), pvars);
        String[] uvars = new String[(evars.length + pvars.length)];
        int i2 = 0;
        while (true) {
            i = pvars.length;
            if (i2 >= r0) {
                break;
            }
            uvars[i2] = pvars[i2];
            i2++;
        }
        i2 = 0;
        while (true) {
            i = evars.length;
            if (i2 >= r0) {
                break;
            }
            uvars[pvars.length + i2] = evars[i2];
            i2++;
        }
        GenPolynomialRing<C> cfac = new GenPolynomialRing(fac.coFac, cl, fac.tord, rvars);
        ev = pfac.tord.getEvord();
        GenPolynomialRing<GenPolynomial<C>> genPolynomialRing3 = new GenPolynomialRing(cfac, pl, new TermOrder(ev, ev, pl, evars.length), uvars);
        List<GenPolynomial<GenPolynomial<C>>> Fr = PolyUtil.recursive((GenPolynomialRing) genPolynomialRing3, (List) ppolys);
        if (logger.isInfoEnabled()) {
            logger.info("rfac = " + genPolynomialRing3);
            logger.info("Fr   = " + Fr);
        }
        this.rbb = new GroebnerBasePseudoRecSeq(cfac);
        return new OptimizedPolynomialList(perm, pfac, PolyUtil.distribute((GenPolynomialRing) pfac, this.rbb.GB(Fr)));
    }
}
