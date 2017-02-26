package edu.jas.gbufd;

import edu.jas.gb.GroebnerBaseAbstract;
import edu.jas.gb.PairList;
import edu.jas.gb.Reduction;
import edu.jas.gb.ReductionSeq;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.poly.TermOrder;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class GroebnerBaseFGLM<C extends GcdRingElem<C>> extends GroebnerBaseAbstract<C> {
    private static final Logger logger;
    private GroebnerBaseAbstract<C> sgb;

    static {
        logger = Logger.getLogger(GroebnerBaseFGLM.class);
    }

    public GroebnerBaseFGLM() {
        this.sgb = null;
    }

    public GroebnerBaseFGLM(Reduction<C> red) {
        super((Reduction) red);
        this.sgb = null;
    }

    public GroebnerBaseFGLM(Reduction<C> red, PairList<C> pl) {
        super(red, pl);
        this.sgb = null;
    }

    public GroebnerBaseFGLM(Reduction<C> red, PairList<C> pl, GroebnerBaseAbstract<C> gb) {
        super(red, pl);
        this.sgb = gb;
    }

    public GroebnerBaseFGLM(GroebnerBaseAbstract<C> gb) {
        this.sgb = gb;
    }

    public List<GenPolynomial<C>> GB(int modv, List<GenPolynomial<C>> F) {
        if (modv != 0) {
            throw new UnsupportedOperationException("case modv != 0 not yet implemented");
        } else if (F == null || F.size() == 0) {
            return F;
        } else {
            List<GenPolynomial<C>> G = new ArrayList();
            if (F.size() <= 1) {
                G.add(((GenPolynomial) F.get(0)).monic());
                return G;
            }
            List<GenPolynomial<C>> Fp = new ArrayList(F.size());
            GenPolynomialRing<C> pfac = ((GenPolynomial) F.get(0)).ring;
            if (pfac.coFac.isField()) {
                TermOrder tord = new TermOrder(4);
                GenPolynomialRing<C> gfac = new GenPolynomialRing(pfac.coFac, pfac.nvar, tord, pfac.getVars());
                for (GenPolynomial p : F) {
                    Fp.add(gfac.copy(p));
                }
                if (this.sgb == null) {
                    this.sgb = GBFactory.getImplementation(pfac.coFac);
                }
                List<GenPolynomial<C>> Gp = this.sgb.GB(modv, Fp);
                logger.info("graded GB = " + Gp);
                if (tord.equals(pfac.tord)) {
                    return Gp;
                }
                if (Gp.size() == 0) {
                    return Gp;
                }
                if (Gp.size() != 1) {
                    return convGroebnerToLex(Gp);
                }
                G.add(pfac.copy((GenPolynomial) Gp.get(0)));
                return G;
            }
            throw new IllegalArgumentException("coefficients not from a field: " + pfac.coFac);
        }
    }

    public List<GenPolynomial<C>> convGroebnerToLex(List<GenPolynomial<C>> groebnerBasis) {
        if (groebnerBasis == null || groebnerBasis.size() == 0) {
            throw new IllegalArgumentException("G may not be null or empty");
        }
        int z = commonZeroTest(groebnerBasis);
        if (z != 0) {
            throw new IllegalArgumentException("ideal(G) not zero dimensional, dim =  " + z);
        }
        GenPolynomialRing<C> ring = ((GenPolynomial) groebnerBasis.get(0)).ring;
        int numberOfVariables = ring.nvar;
        String[] ArrayOfVariables = ring.getVars();
        GenPolynomialRing<C> genPolynomialRing = new GenPolynomialRing(ring.coFac, numberOfVariables, new TermOrder(2), ArrayOfVariables);
        List<GenPolynomial<C>> newGB = new ArrayList();
        List<GenPolynomial<C>> H = new ArrayList();
        List<GenPolynomial<C>> redTerms = new ArrayList();
        GenPolynomial<C> t = ring.ONE;
        redTerms.add(t);
        int indeterminates = 1;
        GenPolynomialRing<C> cpfac = createRingOfIndeterminates(ring, 1);
        GenPolynomialRing<GenPolynomial<C>> genPolynomialRing2 = new GenPolynomialRing((RingFactory) cpfac, (GenPolynomialRing) ring);
        GenPolynomial<GenPolynomial<C>> q = genPolynomialRing2.getZERO().sum(cpfac.univariate(0));
        t = lMinterm(H, t);
        while (t != null) {
            GenPolynomial<C> h = this.red.normalform((List) groebnerBasis, (GenPolynomial) t);
            List<GenPolynomial<C>> Cf = new ArrayList(PolyUtil.toRecursive((GenPolynomialRing) r27, (GenPolynomial) h).sum((GenPolynomial) q).getMap().values());
            Cf = this.red.irreducibleSet(Cf);
            if (commonZeroTest(Cf) != 0) {
                indeterminates++;
                redTerms.add(t);
                cpfac = addIndeterminate(cpfac);
                genPolynomialRing2 = new GenPolynomialRing((RingFactory) cpfac, (GenPolynomialRing) ring);
                GenPolynomial<GenPolynomial<C>> toRecursive = PolyUtil.toRecursive((GenPolynomialRing) genPolynomialRing2, (GenPolynomial) h);
                q = toRecursive.multiply(genPolynomialRing2.getZERO().sum(cpfac.univariate(0))).sum(PolyUtil.extendCoefficients((GenPolynomialRing) genPolynomialRing2, (GenPolynomial) q, 0, 0));
            } else {
                GenPolynomial<C> g = genPolynomialRing.getZERO();
                for (GenPolynomial<C> pc : Cf) {
                    ExpVector e = pc.leadingExpVector();
                    if (e != null) {
                        int[] v = e.dependencyOnVariables();
                        if (!(v == null || v.length == 0)) {
                            int vi = indeterminates - v[0];
                            GcdRingElem tc = (GcdRingElem) pc.trailingBaseCoefficient();
                            if (!tc.isZERO()) {
                                GenPolynomial genPolynomial = (GenPolynomial) redTerms.get(vi - 1);
                                g = g.sum(r35.multiply((RingElem) (GcdRingElem) tc.negate()));
                            }
                        }
                    }
                }
                g = genPolynomialRing.copy(g.sum((GenPolynomial) t));
                H.add(t);
                if (!g.isZERO()) {
                    newGB.add(g);
                    logger.info("new element for GB = " + g.leadingExpVector());
                }
            }
            t = lMinterm(H, t);
        }
        return newGB;
    }

    public GenPolynomial<C> lMinterm(List<GenPolynomial<C>> G, GenPolynomial<C> t) {
        GenPolynomialRing<C> ring = t.ring;
        int numberOfVariables = ring.nvar;
        GenPolynomial<C> u = new GenPolynomial(ring, t.leadingBaseCoefficient(), t.leadingExpVector());
        ReductionSeq<C> redHelp = new ReductionSeq();
        for (int i = numberOfVariables - 1; i >= 0; i--) {
            u = u.multiply(ring.univariate(i));
            if (!redHelp.isTopReducible(G, u)) {
                return u;
            }
            u = u.divide(ring.univariate(i, u.degree(numberOfVariables - (i + 1))));
        }
        return null;
    }

    public List<GenPolynomial<C>> redTerms(List<GenPolynomial<C>> groebnerBasis) {
        if (groebnerBasis == null || groebnerBasis.size() == 0) {
            throw new IllegalArgumentException("groebnerBasis may not be null or empty");
        }
        int i;
        GenPolynomialRing<C> ring = ((GenPolynomial) groebnerBasis.get(0)).ring;
        int numberOfVariables = ring.nvar;
        long[] degrees = new long[numberOfVariables];
        List<GenPolynomial<C>> terms = new ArrayList();
        for (GenPolynomial<C> g : groebnerBasis) {
            if (g.isONE()) {
                terms.clear();
                break;
            }
            ExpVector e = g.leadingExpVector();
            if (e.totalDeg() == e.maxDeg()) {
                for (i = 0; i < numberOfVariables; i++) {
                    long exp = e.getVal(i);
                    if (exp > 0) {
                        degrees[i] = exp;
                    }
                }
            }
        }
        long max = maxArray(degrees);
        i = 0;
        while (true) {
            int length = degrees.length;
            if (i >= r0) {
                break;
            }
            if (degrees[i] == 0) {
                logger.info("dimension not zero, setting degree to " + max);
                degrees[i] = max;
            }
            i++;
        }
        terms.add(ring.ONE);
        ReductionSeq<C> s = new ReductionSeq();
        for (i = 0; i < numberOfVariables; i++) {
            GenPolynomial<C> x = ring.univariate(i);
            for (GenPolynomial<C> t : new ArrayList(terms)) {
                int l = 1;
                while (true) {
                    if (((long) l) <= degrees[i]) {
                        GenPolynomial<C> t2 = t2.multiply((GenPolynomial) x);
                        if (!s.isReducible(groebnerBasis, t2)) {
                            terms.add(t2);
                        }
                        l++;
                    }
                }
            }
        }
        return terms;
    }

    GenPolynomialRing<C> createRingOfIndeterminates(GenPolynomialRing<C> ring, int i) {
        RingFactory<C> cfac = ring.coFac;
        int indeterminates = i;
        String[] stringIndeterminates = new String[indeterminates];
        for (int j = 1; j <= indeterminates; j++) {
            stringIndeterminates[j - 1] = "Y" + j;
        }
        return new GenPolynomialRing(cfac, indeterminates, new TermOrder(2), stringIndeterminates);
    }

    GenPolynomialRing<C> addIndeterminate(GenPolynomialRing<C> ring) {
        String[] stringIndeterminates = new String[1];
        stringIndeterminates[0] = "Y" + (ring.nvar + 1);
        return ring.extend(stringIndeterminates);
    }

    long maxArray(long[] t) {
        if (t.length == 0) {
            return 0;
        }
        long maximum = t[0];
        for (int i = 1; i < t.length; i++) {
            if (t[i] > maximum) {
                maximum = t[i];
            }
        }
        return maximum;
    }

    public void terminate() {
        if (this.sgb != null) {
            this.sgb.terminate();
        }
    }

    public int cancel() {
        if (this.sgb == null) {
            return 0;
        }
        return this.sgb.cancel();
    }
}
