package edu.jas.ps;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.RingElem;
import java.io.Serializable;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public abstract class MultiVarCoefficients<C extends RingElem<C>> implements Serializable {
    public final HashMap<Long, GenPolynomial<C>> coeffCache;
    public final BitSet homCheck;
    public final GenPolynomialRing<C> pfac;
    public final HashSet<ExpVector> zeroCache;

    protected abstract C generate(ExpVector expVector);

    public MultiVarCoefficients(MultiVarPowerSeriesRing<C> pf) {
        this(pf.polyRing(), new HashMap(), new HashSet());
    }

    public MultiVarCoefficients(MultiVarPowerSeriesRing<C> pf, BitSet hc) {
        this(pf.polyRing(), new HashMap(), new HashSet(), hc);
    }

    public MultiVarCoefficients(GenPolynomialRing<C> pf) {
        this((GenPolynomialRing) pf, new HashMap(), new HashSet());
    }

    public MultiVarCoefficients(GenPolynomialRing<C> pf, HashMap<Long, GenPolynomial<C>> cache) {
        this((GenPolynomialRing) pf, (HashMap) cache, new HashSet());
    }

    public MultiVarCoefficients(GenPolynomialRing<C> pf, HashMap<Long, GenPolynomial<C>> cache, HashSet<ExpVector> zeros) {
        this(pf, cache, zeros, new BitSet());
    }

    public MultiVarCoefficients(GenPolynomialRing<C> pf, BitSet hc) {
        this(pf, new HashMap(), new HashSet(), hc);
    }

    public MultiVarCoefficients(GenPolynomialRing<C> pf, HashMap<Long, GenPolynomial<C>> cache, BitSet hc) {
        this(pf, cache, new HashSet(), hc);
    }

    public MultiVarCoefficients(GenPolynomialRing<C> pf, HashMap<Long, GenPolynomial<C>> cache, HashSet<ExpVector> zeros, BitSet hc) {
        this.pfac = pf;
        this.coeffCache = cache;
        this.zeroCache = zeros;
        this.homCheck = hc;
    }

    public C get(ExpVector index) {
        long tdeg = index.totalDeg();
        GenPolynomial<C> p = (GenPolynomial) this.coeffCache.get(Long.valueOf(tdeg));
        if (p == null) {
            p = this.pfac.getZERO().copy();
            this.coeffCache.put(Long.valueOf(tdeg), p);
        }
        C c = p.coefficient(index);
        if (!c.isZERO() || this.homCheck.get((int) tdeg) || this.zeroCache.contains(index)) {
            return c;
        }
        C g = generate(index);
        if (g.isZERO()) {
            this.zeroCache.add(index);
        } else {
            p.doPutToMap(index, g);
        }
        return g;
    }

    public GenPolynomial<C> getHomPart(long tdeg) {
        if (this.coeffCache == null) {
            throw new IllegalArgumentException("null cache not allowed");
        }
        GenPolynomial<C> p = (GenPolynomial) this.coeffCache.get(Long.valueOf(tdeg));
        if (p == null) {
            p = this.pfac.getZERO().copy();
            this.coeffCache.put(Long.valueOf(tdeg), p);
        }
        if (!this.homCheck.get((int) tdeg)) {
            Iterator i$ = new ExpVectorIterable(this.pfac.nvar, tdeg).iterator();
            while (i$.hasNext()) {
                ExpVector e = (ExpVector) i$.next();
                if (this.zeroCache.contains(e)) {
                    if (!this.zeroCache.remove(e)) {
                        System.out.println("not removed e = " + e);
                    }
                } else if (p.coefficient(e).isZERO()) {
                    C g = generate(e);
                    if (!g.isZERO()) {
                        p.doPutToMap(e, g);
                    }
                }
            }
            this.homCheck.set((int) tdeg);
        }
        return p;
    }
}
