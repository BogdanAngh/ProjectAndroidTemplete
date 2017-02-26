package edu.jas.poly;

import edu.jas.structure.GcdRingElem;
import edu.jas.structure.QuotPair;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import org.apache.log4j.Logger;

public class QLRSolvablePolynomial<C extends GcdRingElem<C> & QuotPair<GenPolynomial<D>>, D extends GcdRingElem<D>> extends GenSolvablePolynomial<C> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Logger logger;
    private final boolean debug;
    public final QLRSolvablePolynomialRing<C, D> ring;

    static {
        $assertionsDisabled = !QLRSolvablePolynomial.class.desiredAssertionStatus();
        logger = Logger.getLogger(QLRSolvablePolynomial.class);
    }

    public QLRSolvablePolynomial(QLRSolvablePolynomialRing<C, D> r) {
        super(r);
        this.debug = logger.isDebugEnabled();
        this.ring = r;
    }

    public QLRSolvablePolynomial(QLRSolvablePolynomialRing<C, D> r, C c, ExpVector e) {
        this(r);
        if (c != null && !c.isZERO()) {
            this.val.put(e, c);
        }
    }

    public QLRSolvablePolynomial(QLRSolvablePolynomialRing<C, D> r, C c) {
        this(r, c, r.evzero);
    }

    public QLRSolvablePolynomial(QLRSolvablePolynomialRing<C, D> r, GenSolvablePolynomial<C> S) {
        this((QLRSolvablePolynomialRing) r, S.getMap());
    }

    protected QLRSolvablePolynomial(QLRSolvablePolynomialRing<C, D> r, SortedMap<ExpVector, C> v) {
        this(r);
        this.val.putAll(v);
    }

    public QLRSolvablePolynomialRing<C, D> factory() {
        return this.ring;
    }

    public QLRSolvablePolynomial<C, D> copy() {
        return new QLRSolvablePolynomial(this.ring, this.val);
    }

    public boolean equals(Object B) {
        if (B instanceof QLRSolvablePolynomial) {
            return super.equals(B);
        }
        return false;
    }

    public QLRSolvablePolynomial<C, D> multiply(QLRSolvablePolynomial<C, D> Bp) {
        if (Bp == null || Bp.isZERO()) {
            return this.ring.getZERO();
        } else if (isZERO() || Bp.isONE()) {
            return this;
        } else {
            if (isONE()) {
                return Bp;
            }
            if ($assertionsDisabled || this.ring.nvar == Bp.ring.nvar) {
                if (this.debug) {
                    logger.debug("ring = " + this.ring);
                }
                ExpVector Z = this.ring.evzero;
                QLRSolvablePolynomial<C, D> Dp = this.ring.getZERO().copy();
                QLRSolvablePolynomial<C, D> zero = this.ring.getZERO().copy();
                GcdRingElem one = (GcdRingElem) this.ring.getONECoefficient();
                Map<ExpVector, C> A = this.val;
                Set<Entry<ExpVector, C>> Bk = Bp.val.entrySet();
                for (Entry<ExpVector, C> y : A.entrySet()) {
                    GcdRingElem a = (GcdRingElem) y.getValue();
                    ExpVector e = (ExpVector) y.getKey();
                    if (this.debug) {
                        logger.info("e = " + e + ", a = " + a);
                    }
                    for (Entry<ExpVector, C> x : Bk) {
                        QLRSolvablePolynomial<C, D> Ds;
                        GcdRingElem b = (GcdRingElem) x.getValue();
                        ExpVector f = (ExpVector) x.getKey();
                        if (this.debug) {
                            logger.info("f = " + f + ", b = " + b);
                        }
                        int[] fp = f.dependencyOnVariables();
                        int fl1 = 0;
                        if (fp.length > 0) {
                            fl1 = fp[fp.length - 1];
                        }
                        int fl1s = (this.ring.nvar + 1) - fl1;
                        QLRSolvablePolynomial<C, D> Cps = this.ring.getZERO().copy();
                        if (this.ring.polCoeff.isCommutative() || ((QuotPair) b).isConstant() || e.isZERO()) {
                            Cps = new QLRSolvablePolynomial(this.ring, b, e);
                            if (this.debug) {
                                logger.info("symmetric coeff: b = " + b + ", e = " + e);
                            }
                        } else {
                            if (this.debug) {
                                logger.info("unsymmetric coeff: b = " + b + ", e = " + e);
                            }
                            if (((GenPolynomial) ((QuotPair) b).denominator()).isONE()) {
                                GenSolvablePolynomial rsp3 = new RecSolvablePolynomial(this.ring.polCoeff, e).multiply((RecSolvablePolynomial) new RecSolvablePolynomial(this.ring.polCoeff, (GenPolynomial) ((QuotPair) b).numerator()));
                                Cps = this.ring.fromPolyCoefficients(rsp3);
                            } else {
                                if (this.debug) {
                                    logger.info("coeff-num: Cps = " + Cps + ", num = " + ((QuotPair) b).numerator() + ", den = " + ((QuotPair) b).denominator());
                                }
                                RingFactory<C> bfq = (RingFactory) b.factory();
                                QLRSolvablePolynomial<C, D> qv = new QLRSolvablePolynomial(this.ring, (GcdRingElem) bfq.getONE(), e);
                                GcdRingElem qden = (GcdRingElem) this.ring.qpfac.create(((QuotPair) b).denominator());
                                QLRSolvablePolynomial<C, D> vr = (QLRSolvablePolynomial) qv.multiply(qden).subtract((GenPolynomial) qv.multiplyLeft(qden));
                                GcdRingElem qdeni = (GcdRingElem) this.ring.qpfac.create((RingElem) this.ring.qpfac.pairFactory().getONE(), ((QuotPair) b).denominator());
                                if (qv.leadingExpVector().equals(vr.leadingExpVector())) {
                                    throw new IllegalArgumentException("qr !> vr: qv = " + qv + ", vr = " + vr);
                                }
                                Cps = ((QLRSolvablePolynomial) qv.subtract(vr.multiply(qdeni))).multiplyLeft(qdeni);
                                if (!((GenPolynomial) ((QuotPair) b).numerator()).isONE()) {
                                    Cps = Cps.multiply((GcdRingElem) this.ring.qpfac.create(((QuotPair) b).numerator()));
                                }
                            }
                        }
                        if (this.debug) {
                            logger.info("coeff-den: Cps = " + Cps);
                        }
                        QLRSolvablePolynomial<C, D> Dps = this.ring.getZERO().copy();
                        ExpVector g;
                        if (this.ring.isCommutative() || Cps.isConstant() || f.isZERO()) {
                            if (this.debug) {
                                logger.info("symmetric poly: b = " + b + ", e = " + e);
                            }
                            if (Cps.isConstant()) {
                                g = e.sum(f);
                                Ds = new QLRSolvablePolynomial(this.ring, (GcdRingElem) Cps.leadingBaseCoefficient(), g);
                            } else {
                                Ds = shift(Cps, f);
                            }
                        } else {
                            if (this.debug) {
                                logger.info("unsymmetric poly: Cps = " + Cps + ", f = " + f);
                            }
                            for (Entry<ExpVector, C> z : Cps.val.entrySet()) {
                                GcdRingElem c = (GcdRingElem) z.getValue();
                                g = (ExpVector) z.getKey();
                                if (this.debug) {
                                    logger.info("g = " + g + ", c = " + c);
                                }
                                int[] gp = g.dependencyOnVariables();
                                int gl1 = this.ring.nvar + 1;
                                if (gp.length > 0) {
                                    gl1 = gp[0];
                                }
                                if ((this.ring.nvar + 1) - gl1 <= fl1s) {
                                    ExpVector h = g.sum(f);
                                    if (this.debug) {
                                        logger.info("disjoint poly: g = " + g + ", f = " + f + ", h = " + h);
                                    }
                                    Ds = (QLRSolvablePolynomial) zero.sum(one, h);
                                } else {
                                    ExpVector g1 = g.subst(gl1, 0);
                                    ExpVector g2 = Z.subst(gl1, g.getVal(gl1));
                                    ExpVector f1 = f.subst(fl1, 0);
                                    ExpVector f2 = Z.subst(fl1, f.getVal(fl1));
                                    if (this.debug) {
                                        logger.info("poly, g1 = " + g1 + ", f1 = " + f1 + ", Dps = " + Dps);
                                        logger.info("poly, g2 = " + g2 + ", f2 = " + f2);
                                    }
                                    TableRelation<C> rel = this.ring.table.lookup(g2, f2);
                                    if (this.debug) {
                                        logger.info("poly, g  = " + g + ", f  = " + f + ", rel = " + rel);
                                    }
                                    Ds = new QLRSolvablePolynomial(this.ring, rel.p);
                                    if (rel.f != null) {
                                        ExpVector g4;
                                        Ds = Ds.multiply(new QLRSolvablePolynomial(this.ring, one, rel.f));
                                        if (rel.e == null) {
                                            g4 = g2;
                                        } else {
                                            g4 = g2.subtract(rel.e);
                                        }
                                        this.ring.table.update(g4, f2, (GenSolvablePolynomial) Ds);
                                    }
                                    if (rel.e != null) {
                                        Ds = new QLRSolvablePolynomial(this.ring, one, rel.e).multiply((QLRSolvablePolynomial) Ds);
                                        this.ring.table.update(g2, f2, (GenSolvablePolynomial) Ds);
                                    }
                                    if (!f1.isZERO()) {
                                        Ds = Ds.multiply(new QLRSolvablePolynomial(this.ring, one, f1));
                                    }
                                    if (!g1.isZERO()) {
                                        Ds = new QLRSolvablePolynomial(this.ring, one, g1).multiply(Ds);
                                    }
                                }
                                Dps.doAddTo((GenPolynomial) Ds.multiplyLeft(c));
                            }
                            Ds = Dps;
                        }
                        Ds = Ds.multiplyLeft(a);
                        if (this.debug) {
                            logger.debug("Ds = " + Ds);
                        }
                        Dp.doAddTo((GenPolynomial) Ds);
                    }
                }
                return Dp;
            }
            throw new AssertionError();
        }
    }

    public QLRSolvablePolynomial<C, D> multiply(QLRSolvablePolynomial<C, D> S, QLRSolvablePolynomial<C, D> T) {
        if (S.isZERO() || T.isZERO() || isZERO()) {
            return this.ring.getZERO();
        }
        if (S.isONE()) {
            return multiply((QLRSolvablePolynomial) T);
        }
        if (T.isONE()) {
            return S.multiply(this);
        }
        return S.multiply(this).multiply((QLRSolvablePolynomial) T);
    }

    public QLRSolvablePolynomial<C, D> multiply(C b) {
        QLRSolvablePolynomial<C, D> Cp = this.ring.getZERO().copy();
        if (b == null || b.isZERO()) {
            return Cp;
        }
        return !b.isONE() ? multiply(new QLRSolvablePolynomial(this.ring, b, this.ring.evzero)) : this;
    }

    public QLRSolvablePolynomial<C, D> multiply(C b, C c) {
        QLRSolvablePolynomial<C, D> Cp = this.ring.getZERO().copy();
        if (b == null || b.isZERO()) {
            return Cp;
        }
        if (c == null || c.isZERO()) {
            return Cp;
        }
        return (b.isONE() && c.isONE()) ? this : multiply(new QLRSolvablePolynomial(this.ring, b, this.ring.evzero), new QLRSolvablePolynomial(this.ring, c, this.ring.evzero));
    }

    public QLRSolvablePolynomial<C, D> multiply(ExpVector e) {
        return (e == null || e.isZERO()) ? this : multiply((GcdRingElem) this.ring.getONECoefficient(), e);
    }

    public QLRSolvablePolynomial<C, D> multiply(ExpVector e, ExpVector f) {
        if (e == null || e.isZERO() || f == null || f.isZERO()) {
            return this;
        }
        GcdRingElem b = (GcdRingElem) this.ring.getONECoefficient();
        return multiply(b, e, b, f);
    }

    public QLRSolvablePolynomial<C, D> multiply(C b, ExpVector e) {
        if (b == null || b.isZERO()) {
            return this.ring.getZERO();
        }
        return (b.isONE() && e.isZERO()) ? this : multiply(new QLRSolvablePolynomial(this.ring, b, e));
    }

    public QLRSolvablePolynomial<C, D> multiply(C b, ExpVector e, C c, ExpVector f) {
        if (b == null || b.isZERO()) {
            return this.ring.getZERO();
        }
        if (c == null || c.isZERO()) {
            return this.ring.getZERO();
        }
        return (b.isONE() && e.isZERO() && c.isONE() && f.isZERO()) ? this : multiply(new QLRSolvablePolynomial(this.ring, b, e), new QLRSolvablePolynomial(this.ring, c, f));
    }

    public QLRSolvablePolynomial<C, D> multiplyLeft(C b, ExpVector e) {
        if (b == null || b.isZERO()) {
            return this.ring.getZERO();
        }
        return new QLRSolvablePolynomial(this.ring, b, e).multiply(this);
    }

    public QLRSolvablePolynomial<C, D> multiplyLeft(ExpVector e) {
        if (e == null || e.isZERO()) {
            return this;
        }
        return new QLRSolvablePolynomial(this.ring, (GcdRingElem) this.ring.getONECoefficient(), e).multiply(this);
    }

    public QLRSolvablePolynomial<C, D> multiplyLeft(C b) {
        QLRSolvablePolynomial<C, D> Cp = this.ring.getZERO().copy();
        if (!(b == null || b.isZERO())) {
            Map<ExpVector, C> Cm = Cp.val;
            for (Entry<ExpVector, C> y : this.val.entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                GcdRingElem c = (GcdRingElem) b.multiply((GcdRingElem) y.getValue());
                if (!c.isZERO()) {
                    Cm.put(e, c);
                }
            }
        }
        return Cp;
    }

    public QLRSolvablePolynomial<C, D> multiplyLeft(Entry<ExpVector, C> m) {
        if (m == null) {
            return this.ring.getZERO();
        }
        return multiplyLeft((GcdRingElem) m.getValue(), (ExpVector) m.getKey());
    }

    public QLRSolvablePolynomial<C, D> multiply(Entry<ExpVector, C> m) {
        if (m == null) {
            return this.ring.getZERO();
        }
        return multiply((GcdRingElem) m.getValue(), (ExpVector) m.getKey());
    }

    protected QLRSolvablePolynomial<C, D> shift(QLRSolvablePolynomial<C, D> B, ExpVector f) {
        QLRSolvablePolynomial<C, D> C = this.ring.getZERO().copy();
        if (B == null || B.isZERO()) {
            return C;
        }
        if (f == null || f.isZERO()) {
            return B;
        }
        Map<ExpVector, C> Cm = C.val;
        for (Entry<ExpVector, C> y : B.val.entrySet()) {
            GcdRingElem a = (GcdRingElem) y.getValue();
            ExpVector d = ((ExpVector) y.getKey()).sum(f);
            if (!a.isZERO()) {
                Cm.put(d, a);
            }
        }
        return C;
    }
}
