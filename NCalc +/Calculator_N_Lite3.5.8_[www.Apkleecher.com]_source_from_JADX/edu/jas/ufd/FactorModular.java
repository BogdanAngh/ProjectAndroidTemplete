package edu.jas.ufd;

import edu.jas.arith.ModLongRing;
import edu.jas.arith.Modular;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.Power;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import org.apache.log4j.Logger;

public class FactorModular<MOD extends GcdRingElem<MOD> & Modular> extends FactorAbsolute<MOD> {
    private static final Logger logger;
    private final boolean debug;

    static {
        logger = Logger.getLogger(FactorModular.class);
    }

    private FactorModular() {
        this(new ModLongRing(13, true));
    }

    public FactorModular(RingFactory<MOD> cfac) {
        super(cfac);
        this.debug = logger.isDebugEnabled();
    }

    public SortedMap<Long, GenPolynomial<MOD>> baseDistinctDegreeFactors(GenPolynomial<MOD> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        }
        SortedMap<Long, GenPolynomial<MOD>> facs = new TreeMap();
        if (!P.isZERO()) {
            GenPolynomialRing<MOD> pfac = P.ring;
            if (pfac.nvar > 1) {
                throw new IllegalArgumentException(getClass().getName() + " only for univariate polynomials");
            }
            BigInteger m = pfac.coFac.getIntegerModul().getVal();
            GenPolynomial x = pfac.univariate(0);
            RingElem h = x;
            GenPolynomial<MOD> f = P;
            Power<GenPolynomial<MOD>> pow = new Power(pfac);
            long d = 0;
            while (1 + d <= f.degree(0) / 2) {
                d++;
                GenPolynomial h2 = (GenPolynomial) pow.modPower(h, m, (RingElem) f);
                GenPolynomial g = this.engine.gcd(h2.subtract(x), (GenPolynomial) f);
                if (!g.isONE()) {
                    facs.put(Long.valueOf(d), g);
                    f = f.divide(g);
                }
            }
            if (!f.isONE()) {
                facs.put(Long.valueOf(f.degree(0)), f);
            }
        }
        return facs;
    }

    public List<GenPolynomial<MOD>> baseEqualDegreeFactors(GenPolynomial<MOD> P, long deg) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        }
        List<GenPolynomial<MOD>> facs = new ArrayList();
        if (!P.isZERO()) {
            GenPolynomialRing<MOD> pfac = P.ring;
            int i = pfac.nvar;
            if (r0 > 1) {
                throw new IllegalArgumentException(getClass().getName() + " only for univariate polynomials");
            } else if (P.degree(0) == deg) {
                facs.add(P);
            } else {
                GenPolynomial g;
                BigInteger m = pfac.coFac.getIntegerModul().getVal();
                boolean p2 = false;
                if (m.equals(BigInteger.valueOf(2))) {
                    p2 = true;
                }
                GenPolynomial one = pfac.getONE();
                GenPolynomial<MOD> t = pfac.univariate(0, 1);
                RingElem f = P;
                Power<GenPolynomial<MOD>> power = new Power(pfac);
                int degi = (int) deg;
                BigInteger d = ((edu.jas.arith.BigInteger) Power.positivePower(new edu.jas.arith.BigInteger(m), deg)).getVal().shiftRight(1);
                while (true) {
                    GenPolynomial h;
                    if (p2) {
                        h = t;
                        for (int i2 = 1; i2 < degi; i2++) {
                            h = t.sum(h.multiply(h)).remainder((GenPolynomial) f);
                        }
                        t = t.multiply(pfac.univariate(0, 2));
                    } else {
                        GenPolynomial<MOD> r = pfac.random(17, degi, degi * 2, 1.0f);
                        if (r.degree(0) >= f.degree(0)) {
                            r = r.remainder((GenPolynomial) f);
                        }
                        h = ((GenPolynomial) power.modPower(r.monic(), d, f)).subtract(one);
                        degi++;
                    }
                    g = this.engine.gcd(h, (GenPolynomial) f);
                    if (g.degree(0) != 0 && g.degree(0) != f.degree(0)) {
                        break;
                    }
                }
                facs.addAll(baseEqualDegreeFactors(f.divide(g), deg));
                facs.addAll(baseEqualDegreeFactors(g, deg));
            }
        }
        return facs;
    }

    public List<GenPolynomial<MOD>> baseFactorsSquarefree(GenPolynomial<MOD> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P == null");
        }
        List factors = new ArrayList();
        if (P.isZERO()) {
            return factors;
        }
        if (P.isONE()) {
            factors.add(P);
            return factors;
        } else if (P.ring.nvar > 1) {
            throw new IllegalArgumentException(getClass().getName() + " only for univariate polynomials");
        } else if (((GcdRingElem) P.leadingBaseCoefficient()).isONE()) {
            SortedMap<Long, GenPolynomial<MOD>> dfacs = baseDistinctDegreeFactors(P);
            if (this.debug) {
                logger.info("dfacs    = " + dfacs);
            }
            for (Entry<Long, GenPolynomial<MOD>> me : dfacs.entrySet()) {
                Long e = (Long) me.getKey();
                List<GenPolynomial<MOD>> efacs = baseEqualDegreeFactors((GenPolynomial) me.getValue(), e.longValue());
                if (this.debug) {
                    logger.info("efacs " + e + "   = " + efacs);
                }
                factors.addAll(efacs);
            }
            List<GenPolynomial<MOD>> factors2 = PolyUtil.monic(factors);
            SortedSet<GenPolynomial<MOD>> ss = new TreeSet(factors2);
            factors2.clear();
            factors2.addAll(ss);
            return factors2;
        } else {
            throw new IllegalArgumentException("ldcf(P) != 1: " + P);
        }
    }
}
