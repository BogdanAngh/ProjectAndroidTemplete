package edu.jas.poly;

import edu.jas.structure.RingElem;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import org.apache.log4j.Logger;

public class RecSolvablePolynomial<C extends RingElem<C>> extends GenSolvablePolynomial<GenPolynomial<C>> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Logger logger;
    private final boolean debug;
    public final RecSolvablePolynomialRing<C> ring;

    static {
        $assertionsDisabled = !RecSolvablePolynomial.class.desiredAssertionStatus();
        logger = Logger.getLogger(RecSolvablePolynomial.class);
    }

    public RecSolvablePolynomial(RecSolvablePolynomialRing<C> r) {
        super(r);
        this.debug = logger.isDebugEnabled();
        this.ring = r;
    }

    public RecSolvablePolynomial(RecSolvablePolynomialRing<C> r, ExpVector e) {
        this(r);
        this.val.put(e, this.ring.getONECoefficient());
    }

    public RecSolvablePolynomial(RecSolvablePolynomialRing<C> r, GenPolynomial<C> c, ExpVector e) {
        this(r);
        if (c != null && !c.isZERO()) {
            this.val.put(e, c);
        }
    }

    public RecSolvablePolynomial(RecSolvablePolynomialRing<C> r, GenPolynomial<C> c) {
        this(r, c, r.evzero);
    }

    public RecSolvablePolynomial(RecSolvablePolynomialRing<C> r, GenSolvablePolynomial<GenPolynomial<C>> S) {
        this((RecSolvablePolynomialRing) r, S.val);
    }

    protected RecSolvablePolynomial(RecSolvablePolynomialRing<C> r, SortedMap<ExpVector, GenPolynomial<C>> v) {
        this(r);
        this.val.putAll(v);
    }

    public RecSolvablePolynomialRing<C> factory() {
        return this.ring;
    }

    public RecSolvablePolynomial<C> copy() {
        return new RecSolvablePolynomial(this.ring, this.val);
    }

    public boolean equals(Object B) {
        if (B instanceof RecSolvablePolynomial) {
            return super.equals(B);
        }
        return false;
    }

    public RecSolvablePolynomial<C> multiply(RecSolvablePolynomial<C> Bp) {
        if (Bp == null || Bp.isZERO()) {
            return this.ring.getZERO();
        } else if (isZERO()) {
            return this;
        } else {
            if ($assertionsDisabled || this.ring.nvar == Bp.ring.nvar) {
                if (this.debug) {
                    logger.info("ring = " + this.ring.toScript());
                }
                boolean commute = this.ring.table.isEmpty();
                boolean commuteCoeff = this.ring.coeffTable.isEmpty();
                GenPolynomialRing<C> cfac = (GenPolynomialRing) this.ring.coFac;
                RecSolvablePolynomial<C> Dp = this.ring.getZERO().copy();
                ExpVector Z = this.ring.evzero;
                ExpVector Zc = cfac.evzero;
                GenPolynomial<C> one = (GenPolynomial) this.ring.getONECoefficient();
                Map<ExpVector, GenPolynomial<C>> A = this.val;
                Set<Entry<ExpVector, GenPolynomial<C>>> Bk = Bp.val.entrySet();
                if (this.debug) {
                    logger.info("input A = " + this);
                }
                for (Entry<ExpVector, GenPolynomial<C>> y : A.entrySet()) {
                    GenPolynomial<C> a = (GenPolynomial) y.getValue();
                    ExpVector e = (ExpVector) y.getKey();
                    if (this.debug) {
                        logger.info("e = " + e + ", a = " + a);
                    }
                    int[] ep = e.dependencyOnVariables();
                    int el1 = this.ring.nvar + 1;
                    if (ep.length > 0) {
                        el1 = ep[0];
                    }
                    if (this.debug) {
                        logger.info("input B = " + Bp);
                    }
                    for (Entry<ExpVector, GenPolynomial<C>> x : Bk) {
                        ExpVector g;
                        int[] gp;
                        int gl1;
                        ExpVector g1;
                        ExpVector g2;
                        RecSolvablePolynomial<C> Ds;
                        GenPolynomial<C> b = (GenPolynomial) x.getValue();
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
                        RecSolvablePolynomial<C> Cps = this.ring.getZERO().copy();
                        RecSolvablePolynomial<C> Cs = null;
                        if (commuteCoeff || b.isConstant() || e.isZERO()) {
                            Cps.doAddTo(b, e);
                            if (this.debug) {
                                logger.info("symmetric coeff, e*b: b = " + b + ", e = " + e);
                            }
                        } else {
                            if (this.debug) {
                                logger.info("unsymmetric coeff, e*b: b = " + b + ", e = " + e);
                            }
                            for (Entry<ExpVector, C> z : b.val.entrySet()) {
                                GenPolynomial<C> c2;
                                RingElem c = (RingElem) z.getValue();
                                GenPolynomial<C> cc = b.ring.valueOf(c);
                                g = (ExpVector) z.getKey();
                                if (this.debug) {
                                    logger.info("g = " + g + ", c = " + c);
                                }
                                gp = g.dependencyOnVariables();
                                gl1 = 0;
                                if (gp.length > 0) {
                                    gl1 = gp[gp.length - 1];
                                }
                                int gl1s = (b.ring.nvar + 1) - gl1;
                                if (this.debug) {
                                    logger.info("gl1s = " + gl1s);
                                }
                                ExpVector e1 = e;
                                ExpVector e2 = Z;
                                if (!e.isZERO()) {
                                    e1 = e.subst(el1, 0);
                                    e2 = Z.subst(el1, e.getVal(el1));
                                }
                                g1 = g;
                                g2 = Zc;
                                if (!g.isZERO()) {
                                    g1 = g.subst(gl1, 0);
                                    g2 = Zc.subst(gl1, g.getVal(gl1));
                                }
                                if (this.debug) {
                                    logger.info("coeff, e1 = " + e1 + ", e2 = " + e2 + ", Cps = " + Cps);
                                    logger.info("coeff, g1 = " + g1 + ", g2 = " + g2);
                                }
                                TableRelation<GenPolynomial<C>> crel = this.ring.coeffTable.lookup(e2, g2);
                                if (this.debug) {
                                    logger.info("coeff, crel = " + crel.p);
                                }
                                Cs = new RecSolvablePolynomial(this.ring, crel.p);
                                if (crel.f != null) {
                                    ExpVector e4;
                                    c2 = b.ring.valueOf(crel.f);
                                    Cs = Cs.multiply(new RecSolvablePolynomial(this.ring, c2, Z));
                                    if (crel.e == null) {
                                        e4 = e2;
                                    } else {
                                        e4 = e2.subtract(crel.e);
                                    }
                                    this.ring.coeffTable.update(e4, g2, (GenSolvablePolynomial) Cs);
                                }
                                if (crel.e != null) {
                                    Cs = new RecSolvablePolynomial(this.ring, one, crel.e).multiply((RecSolvablePolynomial) Cs);
                                    this.ring.coeffTable.update(e2, g2, (GenSolvablePolynomial) Cs);
                                }
                                if (!g1.isZERO()) {
                                    c2 = b.ring.valueOf(g1);
                                    Cs = Cs.multiply(new RecSolvablePolynomial(this.ring, c2, Z));
                                }
                                if (!e1.isZERO()) {
                                    Cs = new RecSolvablePolynomial(this.ring, one, e1).multiply((RecSolvablePolynomial) Cs);
                                }
                                Cs = Cs.multiplyLeft((GenPolynomial) cc);
                                Cps.doAddTo((GenPolynomial) Cs);
                            }
                            if (this.debug) {
                                logger.info("coeff, Cs = " + Cs + ", Cps = " + Cps);
                            }
                        }
                        if (this.debug) {
                            logger.info("coeff-poly: Cps = " + Cps);
                        }
                        RecSolvablePolynomial<C> Dps = this.ring.getZERO().copy();
                        if (commute || Cps.isConstant() || f.isZERO()) {
                            if (this.debug) {
                                logger.info("symmetric poly, P_eb*f: Cps = " + Cps + ", f = " + f);
                            }
                            g = e.sum(f);
                            if (Cps.isConstant()) {
                                Ds = new RecSolvablePolynomial(this.ring, (GenPolynomial) Cps.leadingBaseCoefficient(), g);
                            } else {
                                Ds = shift(Cps, f);
                            }
                        } else {
                            if (this.debug) {
                                logger.info("unsymmetric poly, P_eb*f: Cps = " + Cps + ", f = " + f);
                            }
                            for (Entry<ExpVector, GenPolynomial<C>> z2 : Cps.val.entrySet()) {
                                GenPolynomial<C> c3 = (GenPolynomial) z2.getValue();
                                g = (ExpVector) z2.getKey();
                                if (this.debug) {
                                    logger.info("g = " + g + ", c = " + c3);
                                }
                                gp = g.dependencyOnVariables();
                                gl1 = this.ring.nvar + 1;
                                if (gp.length > 0) {
                                    gl1 = gp[0];
                                }
                                if ((this.ring.nvar + 1) - gl1 <= fl1s) {
                                    ExpVector h = g.sum(f);
                                    if (this.debug) {
                                        logger.info("disjoint poly: g = " + g + ", f = " + f + ", h = " + h);
                                    }
                                    Ds = this.ring.valueOf(h);
                                } else {
                                    g1 = g.subst(gl1, 0);
                                    g2 = Z.subst(gl1, g.getVal(gl1));
                                    ExpVector f1 = f.subst(fl1, 0);
                                    ExpVector f2 = Z.subst(fl1, f.getVal(fl1));
                                    if (this.debug) {
                                        logger.info("poly, g1 = " + g1 + ", f1 = " + f1 + ", Dps = " + Dps);
                                        logger.info("poly, g2 = " + g2 + ", f2 = " + f2);
                                    }
                                    TableRelation<GenPolynomial<C>> rel = this.ring.table.lookup(g2, f2);
                                    if (this.debug) {
                                        logger.info("poly, g  = " + g + ", f  = " + f + ", rel = " + rel);
                                    }
                                    Ds = new RecSolvablePolynomial(this.ring, rel.p);
                                    if (rel.f != null) {
                                        ExpVector g4;
                                        Ds = Ds.multiply(this.ring.valueOf(rel.f));
                                        if (rel.e == null) {
                                            g4 = g2;
                                        } else {
                                            g4 = g2.subtract(rel.e);
                                        }
                                        this.ring.table.update(g4, f2, (GenSolvablePolynomial) Ds);
                                    }
                                    if (rel.e != null) {
                                        Ds = this.ring.valueOf(rel.e).multiply((RecSolvablePolynomial) Ds);
                                        this.ring.table.update(g2, f2, (GenSolvablePolynomial) Ds);
                                    }
                                    if (!f1.isZERO()) {
                                        Ds = Ds.multiply(this.ring.valueOf(f1));
                                    }
                                    if (!g1.isZERO()) {
                                        Ds = this.ring.valueOf(g1).multiply(Ds);
                                    }
                                }
                                Dps.doAddTo((GenPolynomial) Ds.multiplyLeft((GenPolynomial) c3));
                            }
                            Ds = Dps;
                        }
                        if (this.debug) {
                            logger.info("recursion+: Ds = " + Ds + ", a = " + a);
                        }
                        Ds = Ds.multiplyLeft((GenPolynomial) a);
                        if (this.debug) {
                            logger.info("recursion-: Ds = " + Ds);
                        }
                        Dp.doAddTo((GenPolynomial) Ds);
                        if (this.debug) {
                            logger.info("end B loop: Dp = " + Dp);
                        }
                    }
                    if (this.debug) {
                        logger.info("end A loop: Dp = " + Dp);
                    }
                }
                return Dp;
            }
            throw new AssertionError();
        }
    }

    public RecSolvablePolynomial<C> multiply(RecSolvablePolynomial<C> S, RecSolvablePolynomial<C> T) {
        if (S.isZERO() || T.isZERO() || isZERO()) {
            return this.ring.getZERO();
        }
        if (S.isONE()) {
            return multiply((RecSolvablePolynomial) T);
        }
        if (T.isONE()) {
            return S.multiply(this);
        }
        return S.multiply(this).multiply((RecSolvablePolynomial) T);
    }

    public RecSolvablePolynomial<C> recMultiply(GenPolynomial<C> b) {
        RecSolvablePolynomial<C> Cp = this.ring.getZERO().copy();
        if (b == null || b.isZERO()) {
            return Cp;
        }
        return multiply(new RecSolvablePolynomial(this.ring, b, this.ring.evzero));
    }

    public RecSolvablePolynomial<C> multiply(GenPolynomial<C> b, GenPolynomial<C> c) {
        RecSolvablePolynomial<C> Cp = this.ring.getZERO().copy();
        if (b == null || b.isZERO() || c == null || c.isZERO()) {
            return Cp;
        }
        RecSolvablePolynomial<C> Cb = this.ring.valueOf((GenPolynomial) b);
        return Cb.multiply(this).multiply(this.ring.valueOf((GenPolynomial) c));
    }

    public RecSolvablePolynomial<C> multiply(ExpVector e) {
        return (e == null || e.isZERO()) ? this : multiply((GenPolynomial) this.ring.getONECoefficient(), e);
    }

    public RecSolvablePolynomial<C> multiply(ExpVector e, ExpVector f) {
        if (e == null || e.isZERO() || f == null || f.isZERO()) {
            return this;
        }
        GenPolynomial b = (GenPolynomial) this.ring.getONECoefficient();
        return multiply(b, e, b, f);
    }

    public RecSolvablePolynomial<C> multiply(GenPolynomial<C> b, ExpVector e) {
        if (b == null || b.isZERO()) {
            return this.ring.getZERO();
        }
        return multiply(this.ring.valueOf((GenPolynomial) b, e));
    }

    public RecSolvablePolynomial<C> multiply(GenPolynomial<C> b, ExpVector e, GenPolynomial<C> c, ExpVector f) {
        if (b == null || b.isZERO()) {
            return this.ring.getZERO();
        }
        if (c == null || c.isZERO()) {
            return this.ring.getZERO();
        }
        return multiply(this.ring.valueOf((GenPolynomial) b, e), this.ring.valueOf((GenPolynomial) c, f));
    }

    public RecSolvablePolynomial<C> multiplyLeft(GenPolynomial<C> b, ExpVector e) {
        if (b == null || b.isZERO()) {
            return this.ring.getZERO();
        }
        return this.ring.valueOf((GenPolynomial) b, e).multiply(this);
    }

    public RecSolvablePolynomial<C> multiplyLeft(ExpVector e) {
        return (e == null || e.isZERO()) ? this : this.ring.valueOf(e).multiply(this);
    }

    public RecSolvablePolynomial<C> multiplyLeft(GenPolynomial<C> b) {
        RecSolvablePolynomial<C> Cp = this.ring.getZERO().copy();
        if (!(b == null || b.isZERO())) {
            GenSolvablePolynomial<C> bb = null;
            if (b instanceof GenSolvablePolynomial) {
                logger.debug("warn: wrong method dispatch in JRE multiply(b) - trying to fix");
                bb = (GenSolvablePolynomial) b;
            }
            Map<ExpVector, GenPolynomial<C>> Cm = Cp.val;
            for (Entry<ExpVector, GenPolynomial<C>> y : this.val.entrySet()) {
                GenPolynomial<C> c;
                ExpVector e = (ExpVector) y.getKey();
                GenPolynomial a = (GenPolynomial) y.getValue();
                if (bb != null) {
                    c = bb.multiply((GenSolvablePolynomial) a);
                } else {
                    c = b.multiply(a);
                }
                if (!c.isZERO()) {
                    Cm.put(e, c);
                }
            }
        }
        return Cp;
    }

    public RecSolvablePolynomial<C> multiplyLeft(Entry<ExpVector, GenPolynomial<C>> m) {
        if (m == null) {
            return this.ring.getZERO();
        }
        return multiplyLeft((GenPolynomial) m.getValue(), (ExpVector) m.getKey());
    }

    public RecSolvablePolynomial<C> multiply(Entry<ExpVector, GenPolynomial<C>> m) {
        if (m == null) {
            return this.ring.getZERO();
        }
        return multiply((GenPolynomial) m.getValue(), (ExpVector) m.getKey());
    }

    protected RecSolvablePolynomial<C> shift(RecSolvablePolynomial<C> B, ExpVector f) {
        RecSolvablePolynomial<C> C = this.ring.getZERO().copy();
        if (B == null || B.isZERO()) {
            return C;
        }
        if (f == null || f.isZERO()) {
            return B;
        }
        Map<ExpVector, GenPolynomial<C>> Cm = C.val;
        for (Entry<ExpVector, GenPolynomial<C>> y : B.val.entrySet()) {
            GenPolynomial<C> a = (GenPolynomial) y.getValue();
            ExpVector d = ((ExpVector) y.getKey()).sum(f);
            if (!a.isZERO()) {
                Cm.put(d, a);
            }
        }
        return C;
    }

    public GenSolvablePolynomial<GenPolynomial<C>> rightRecursivePolynomial() {
        if (isONE() || isZERO() || !(this instanceof RecSolvablePolynomial)) {
            return this;
        }
        RecSolvablePolynomialRing<C> rfac = this.ring;
        if (rfac.coeffTable.isEmpty()) {
            return this;
        }
        GenPolynomial one = rfac.coFac.getONE();
        RecSolvablePolynomial<C> onep = rfac.getONE();
        ExpVector zero = rfac.evzero;
        RecSolvablePolynomial<C> R = rfac.getZERO();
        RecSolvablePolynomial<C> p = this;
        while (!p.isZERO()) {
            ExpVector f = p.leadingExpVector();
            GenPolynomial a = (GenPolynomial) p.leadingBaseCoefficient();
            p = (RecSolvablePolynomial) p.subtract((GenPolynomial) onep.multiply(one, f, a, zero));
            R = (RecSolvablePolynomial) R.sum(a, f);
        }
        return R;
    }

    public GenSolvablePolynomial<GenPolynomial<C>> evalAsRightRecursivePolynomial() {
        if (isONE() || isZERO() || !(this instanceof RecSolvablePolynomial)) {
            return this;
        }
        RecSolvablePolynomialRing<C> rfac = this.ring;
        if (rfac.coeffTable.isEmpty()) {
            return this;
        }
        GenPolynomial one = rfac.coFac.getONE();
        RecSolvablePolynomial<C> onep = rfac.getONE();
        ExpVector zero = rfac.evzero;
        RecSolvablePolynomial<C> q = rfac.getZERO();
        for (Entry<ExpVector, GenPolynomial<C>> y : getMap().entrySet()) {
            q = (RecSolvablePolynomial) q.sum((GenPolynomial) onep.multiply(one, (ExpVector) y.getKey(), (GenPolynomial) y.getValue(), zero));
        }
        return q;
    }

    public boolean isRightRecursivePolynomial(GenSolvablePolynomial<GenPolynomial<C>> R) {
        if (isZERO()) {
            return R.isZERO();
        }
        if (isONE()) {
            return R.isONE();
        }
        if (this instanceof RecSolvablePolynomial) {
            if (!(R instanceof RecSolvablePolynomial)) {
                return false;
            }
            if (this.ring.coeffTable.isEmpty()) {
                return R.ring.coeffTable.isEmpty();
            }
            return ((RecSolvablePolynomial) PolyUtil.monic((GenSolvablePolynomial) this)).equals((RecSolvablePolynomial) PolyUtil.monic((RecSolvablePolynomial) R.evalAsRightRecursivePolynomial()));
        } else if (R instanceof RecSolvablePolynomial) {
            return false;
        } else {
            return true;
        }
    }
}
