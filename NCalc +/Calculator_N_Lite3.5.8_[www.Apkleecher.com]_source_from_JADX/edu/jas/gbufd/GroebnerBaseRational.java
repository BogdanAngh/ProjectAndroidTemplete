package edu.jas.gbufd;

import edu.jas.arith.BigInteger;
import edu.jas.arith.BigRational;
import edu.jas.gb.GroebnerBaseAbstract;
import edu.jas.gb.PairList;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class GroebnerBaseRational<C extends BigRational> extends GroebnerBaseAbstract<BigRational> {
    private static final Logger logger;
    public final GroebnerBaseAbstract<BigInteger> bba;
    private final boolean debug;

    static {
        logger = Logger.getLogger(GroebnerBaseRational.class);
    }

    public GroebnerBaseRational() {
        this(new GroebnerBasePseudoSeq(new BigInteger()));
    }

    public GroebnerBaseRational(int threads) {
        this(new GroebnerBasePseudoParallel(threads, new BigInteger()));
    }

    public GroebnerBaseRational(PairList<BigInteger> pl) {
        this(new GroebnerBasePseudoSeq(new BigInteger(), pl));
    }

    public GroebnerBaseRational(int threads, PairList<BigInteger> pl) {
        this(new GroebnerBasePseudoParallel(threads, new BigInteger(), (PairList) pl));
    }

    public GroebnerBaseRational(GroebnerBaseAbstract<BigInteger> bba) {
        this.debug = logger.isDebugEnabled();
        this.bba = bba;
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.bba.toString() + ")";
    }

    public List<GenPolynomial<BigRational>> GB(int modv, List<GenPolynomial<BigRational>> F) {
        List<GenPolynomial<BigRational>> G = F;
        if (F == null || F.isEmpty()) {
            return G;
        }
        GenPolynomialRing rring = ((GenPolynomial) F.get(0)).ring;
        List<GenPolynomial<BigInteger>> Fi = PolyUtil.integerFromRationalCoefficients(new GenPolynomialRing(new BigInteger(), rring), (List) F);
        logger.info("#Fi = " + Fi.size());
        List Gi = this.bba.GB(modv, Fi);
        logger.info("#Gi = " + Gi.size());
        return PolyUtil.monic(PolyUtil.fromIntegerCoefficients(rring, Gi));
    }

    public List<GenPolynomial<BigRational>> minimalGB(List<GenPolynomial<BigRational>> Gp) {
        if (Gp == null || Gp.size() <= 1) {
            return Gp;
        }
        GenPolynomial<BigRational> a;
        List<GenPolynomial<BigRational>> G = new ArrayList(Gp.size());
        for (GenPolynomial<BigRational> a2 : Gp) {
            if (!(a2 == null || a2.isZERO())) {
                G.add(a2);
            }
        }
        if (G.size() <= 1) {
            return G;
        }
        List F = new ArrayList(G.size());
        while (G.size() > 0) {
            GenPolynomial a3 = (GenPolynomial) G.remove(0);
            if (!this.red.isTopReducible(G, a3) && !this.red.isTopReducible(F, a3)) {
                F.add(a3);
            } else if (this.debug) {
                System.out.println("dropped " + a3);
                List ff = new ArrayList(G);
                ff.addAll(F);
                a2 = this.red.normalform(ff, a3);
                if (!a2.isZERO()) {
                    System.out.println("error, nf(a) " + a2);
                }
            }
        }
        G = F;
        if (G.size() <= 1) {
            return G;
        }
        GenPolynomialRing rring = ((GenPolynomial) G.get(0)).ring;
        List<GenPolynomial<BigInteger>> Fi = PolyUtil.integerFromRationalCoefficients(new GenPolynomialRing(new BigInteger(), rring), F);
        logger.info("#Fi = " + Fi.size());
        List Gi = this.bba.minimalGB(Fi);
        logger.info("#Gi = " + Gi.size());
        return PolyUtil.monic(PolyUtil.fromIntegerCoefficients(rring, Gi));
    }

    public void terminate() {
        this.bba.terminate();
    }

    public int cancel() {
        return this.bba.cancel();
    }
}
