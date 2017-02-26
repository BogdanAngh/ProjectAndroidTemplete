package edu.jas.gbmod;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.poly.RecSolvablePolynomial;
import edu.jas.poly.TableRelation;
import edu.jas.structure.GcdRingElem;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import org.apache.log4j.Logger;

public class QuotSolvablePolynomial<C extends GcdRingElem<C>> extends GenSolvablePolynomial<SolvableQuotient<C>> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Logger logger;
    private final boolean debug;
    public final QuotSolvablePolynomialRing<C> ring;

    static {
        $assertionsDisabled = !QuotSolvablePolynomial.class.desiredAssertionStatus();
        logger = Logger.getLogger(QuotSolvablePolynomial.class);
    }

    public QuotSolvablePolynomial(QuotSolvablePolynomialRing<C> r) {
        super(r);
        this.debug = logger.isDebugEnabled();
        this.ring = r;
    }

    public QuotSolvablePolynomial(QuotSolvablePolynomialRing<C> r, SolvableQuotient<C> c, ExpVector e) {
        this(r);
        if (c != null && !c.isZERO()) {
            this.val.put(e, c);
        }
    }

    public QuotSolvablePolynomial(QuotSolvablePolynomialRing<C> r, SolvableQuotient<C> c) {
        this(r, c, r.evzero);
    }

    public QuotSolvablePolynomial(QuotSolvablePolynomialRing<C> r, GenSolvablePolynomial<SolvableQuotient<C>> S) {
        this((QuotSolvablePolynomialRing) r, S.getMap());
    }

    protected QuotSolvablePolynomial(QuotSolvablePolynomialRing<C> r, SortedMap<ExpVector, SolvableQuotient<C>> v) {
        this(r);
        this.val.putAll(v);
    }

    public QuotSolvablePolynomialRing<C> factory() {
        return this.ring;
    }

    public QuotSolvablePolynomial<C> copy() {
        return new QuotSolvablePolynomial(this.ring, this.val);
    }

    public boolean equals(Object B) {
        if (B instanceof QuotSolvablePolynomial) {
            return super.equals(B);
        }
        return false;
    }

    public QuotSolvablePolynomial<C> multiply(QuotSolvablePolynomial<C> Bp) {
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
                QuotSolvablePolynomial<C> Dp = this.ring.getZERO().copy();
                QuotSolvablePolynomial<C> zero = this.ring.getZERO().copy();
                SolvableQuotient<C> one = (SolvableQuotient) this.ring.getONECoefficient();
                Map<ExpVector, SolvableQuotient<C>> A = this.val;
                Set<Entry<ExpVector, SolvableQuotient<C>>> Bk = Bp.val.entrySet();
                for (Entry<ExpVector, SolvableQuotient<C>> y : A.entrySet()) {
                    SolvableQuotient a = (SolvableQuotient) y.getValue();
                    ExpVector e = (ExpVector) y.getKey();
                    if (this.debug) {
                        logger.info("e = " + e + ", a = " + a);
                    }
                    for (Entry<ExpVector, SolvableQuotient<C>> x : Bk) {
                        QuotSolvablePolynomial<C> Ds;
                        SolvableQuotient<C> b = (SolvableQuotient) x.getValue();
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
                        QuotSolvablePolynomial<C> Cps = this.ring.getZERO().copy();
                        if (this.ring.polCoeff.coeffTable.isEmpty() || b.isConstant() || e.isZERO()) {
                            Cps = new QuotSolvablePolynomial(this.ring, b, e);
                            if (this.debug) {
                                logger.info("symmetric coeff: b = " + b + ", e = " + e);
                            }
                        } else {
                            if (this.debug) {
                                logger.info("unsymmetric coeff: b = " + b + ", e = " + e);
                            }
                            if (b.den.isONE()) {
                                GenSolvablePolynomial rsp3 = new RecSolvablePolynomial(this.ring.polCoeff, e).multiply((RecSolvablePolynomial) new RecSolvablePolynomial(this.ring.polCoeff, b.num));
                                Cps = this.ring.fromPolyCoefficients(rsp3);
                            } else {
                                if (this.debug) {
                                    StringBuilder append = new StringBuilder().append("coeff-num: Cps = ");
                                    GenSolvablePolynomial genSolvablePolynomial = b.num;
                                    logger.info(r55.append(Cps).append(", num = ").append(r0).append(", den = ").append(b.den).toString());
                                }
                                QuotSolvablePolynomial<C> qv = new QuotSolvablePolynomial(this.ring, b.ring.getONE(), e);
                                SolvableQuotient<C> solvableQuotient = new SolvableQuotient(b.ring, b.den);
                                QuotSolvablePolynomial<C> vr = (QuotSolvablePolynomial) qv.multiply((SolvableQuotient) solvableQuotient).subtract((GenPolynomial) qv.multiplyLeft((SolvableQuotient) solvableQuotient));
                                solvableQuotient = new SolvableQuotient(b.ring, b.ring.ring.getONE(), b.den);
                                Cps = ((QuotSolvablePolynomial) qv.subtract(vr.multiply((SolvableQuotient) solvableQuotient))).multiplyLeft((SolvableQuotient) solvableQuotient);
                                if (!b.num.isONE()) {
                                    Cps = Cps.multiply(new SolvableQuotient(b.ring, b.num));
                                }
                            }
                        }
                        if (this.debug) {
                            logger.info("coeff-den: Cps = " + Cps);
                        }
                        QuotSolvablePolynomial<C> Dps = this.ring.getZERO().copy();
                        ExpVector g;
                        if (this.ring.table.isEmpty() || Cps.isConstant() || f.isZERO()) {
                            if (this.debug) {
                                logger.info("symmetric poly: b = " + b + ", e = " + e);
                            }
                            g = e.sum(f);
                            if (Cps.isConstant()) {
                                Ds = new QuotSolvablePolynomial(this.ring, (SolvableQuotient) Cps.leadingBaseCoefficient(), g);
                            } else {
                                Ds = shift(Cps, f);
                            }
                        } else {
                            if (this.debug) {
                                logger.info("unsymmetric poly: Cps = " + Cps + ", f = " + f);
                            }
                            for (Entry<ExpVector, SolvableQuotient<C>> z : Cps.val.entrySet()) {
                                SolvableQuotient<C> c = (SolvableQuotient) z.getValue();
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
                                    Ds = (QuotSolvablePolynomial) zero.sum(one, h);
                                } else {
                                    ExpVector g1 = g.subst(gl1, 0);
                                    ExpVector g2 = Z.subst(gl1, g.getVal(gl1));
                                    ExpVector f1 = f.subst(fl1, 0);
                                    ExpVector f2 = Z.subst(fl1, f.getVal(fl1));
                                    if (this.debug) {
                                        logger.info("poly, g1 = " + g1 + ", f1 = " + f1 + ", Dps = " + Dps);
                                        logger.info("poly, g2 = " + g2 + ", f2 = " + f2);
                                    }
                                    TableRelation<SolvableQuotient<C>> rel = this.ring.table.lookup(g2, f2);
                                    if (this.debug) {
                                        logger.info("poly, g  = " + g + ", f  = " + f + ", rel = " + rel);
                                    }
                                    Ds = new QuotSolvablePolynomial(this.ring, rel.p);
                                    if (rel.f != null) {
                                        ExpVector g4;
                                        Ds = Ds.multiply(new QuotSolvablePolynomial(this.ring, one, rel.f));
                                        if (rel.e == null) {
                                            g4 = g2;
                                        } else {
                                            g4 = g2.subtract(rel.e);
                                        }
                                        this.ring.table.update(g4, f2, (GenSolvablePolynomial) Ds);
                                    }
                                    if (rel.e != null) {
                                        Ds = new QuotSolvablePolynomial(this.ring, one, rel.e).multiply((QuotSolvablePolynomial) Ds);
                                        this.ring.table.update(g2, f2, (GenSolvablePolynomial) Ds);
                                    }
                                    if (!f1.isZERO()) {
                                        Ds = Ds.multiply(new QuotSolvablePolynomial(this.ring, one, f1));
                                    }
                                    if (!g1.isZERO()) {
                                        Ds = new QuotSolvablePolynomial(this.ring, one, g1).multiply(Ds);
                                    }
                                }
                                Dps = (QuotSolvablePolynomial) Dps.sum((GenPolynomial) Ds.multiplyLeft((SolvableQuotient) c));
                            }
                            Ds = Dps;
                        }
                        Ds = Ds.multiplyLeft(a);
                        if (this.debug) {
                            logger.debug("Ds = " + Ds);
                        }
                        Dp = (QuotSolvablePolynomial) Dp.sum((GenPolynomial) Ds);
                    }
                }
                return Dp;
            }
            throw new AssertionError();
        }
    }

    public QuotSolvablePolynomial<C> multiply(QuotSolvablePolynomial<C> S, QuotSolvablePolynomial<C> T) {
        if (S.isZERO() || T.isZERO() || isZERO()) {
            return this.ring.getZERO();
        }
        if (S.isONE()) {
            return multiply((QuotSolvablePolynomial) T);
        }
        if (T.isONE()) {
            return S.multiply(this);
        }
        return S.multiply(this).multiply((QuotSolvablePolynomial) T);
    }

    public QuotSolvablePolynomial<C> multiply(SolvableQuotient<C> b) {
        QuotSolvablePolynomial<C> Cp = this.ring.getZERO().copy();
        if (b == null || b.isZERO()) {
            return Cp;
        }
        return !b.isONE() ? multiply(new QuotSolvablePolynomial(this.ring, b, this.ring.evzero)) : this;
    }

    public QuotSolvablePolynomial<C> multiply(SolvableQuotient<C> b, SolvableQuotient<C> c) {
        QuotSolvablePolynomial<C> Cp = this.ring.getZERO().copy();
        if (b == null || b.isZERO()) {
            return Cp;
        }
        if (c == null || c.isZERO()) {
            return Cp;
        }
        return (b.isONE() && c.isONE()) ? this : multiply(new QuotSolvablePolynomial(this.ring, b, this.ring.evzero), new QuotSolvablePolynomial(this.ring, c, this.ring.evzero));
    }

    public QuotSolvablePolynomial<C> multiply(ExpVector e) {
        return (e == null || e.isZERO()) ? this : multiply((SolvableQuotient) this.ring.getONECoefficient(), e);
    }

    public QuotSolvablePolynomial<C> multiply(ExpVector e, ExpVector f) {
        if (e == null || e.isZERO() || f == null || f.isZERO()) {
            return this;
        }
        SolvableQuotient b = (SolvableQuotient) this.ring.getONECoefficient();
        return multiply(b, e, b, f);
    }

    public QuotSolvablePolynomial<C> multiply(SolvableQuotient<C> b, ExpVector e) {
        if (b == null || b.isZERO()) {
            return this.ring.getZERO();
        }
        return (b.isONE() && e.isZERO()) ? this : multiply(new QuotSolvablePolynomial(this.ring, b, e));
    }

    public QuotSolvablePolynomial<C> multiply(SolvableQuotient<C> b, ExpVector e, SolvableQuotient<C> c, ExpVector f) {
        if (b == null || b.isZERO()) {
            return this.ring.getZERO();
        }
        if (c == null || c.isZERO()) {
            return this.ring.getZERO();
        }
        return (b.isONE() && e.isZERO() && c.isONE() && f.isZERO()) ? this : multiply(new QuotSolvablePolynomial(this.ring, b, e), new QuotSolvablePolynomial(this.ring, c, f));
    }

    public QuotSolvablePolynomial<C> multiplyLeft(SolvableQuotient<C> b, ExpVector e) {
        if (b == null || b.isZERO()) {
            return this.ring.getZERO();
        }
        return new QuotSolvablePolynomial(this.ring, b, e).multiply(this);
    }

    public QuotSolvablePolynomial<C> multiplyLeft(ExpVector e) {
        if (e == null || e.isZERO()) {
            return this;
        }
        return new QuotSolvablePolynomial(this.ring, (SolvableQuotient) this.ring.getONECoefficient(), e).multiply(this);
    }

    public QuotSolvablePolynomial<C> multiplyLeft(SolvableQuotient<C> b) {
        QuotSolvablePolynomial<C> Cp = this.ring.getZERO().copy();
        if (!(b == null || b.isZERO())) {
            Map<ExpVector, SolvableQuotient<C>> Cm = Cp.val;
            for (Entry<ExpVector, SolvableQuotient<C>> y : this.val.entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                SolvableQuotient<C> c = b.multiply((SolvableQuotient) y.getValue());
                if (!c.isZERO()) {
                    Cm.put(e, c);
                }
            }
        }
        return Cp;
    }

    public QuotSolvablePolynomial<C> multiplyLeft(Entry<ExpVector, SolvableQuotient<C>> m) {
        if (m == null) {
            return this.ring.getZERO();
        }
        return multiplyLeft((SolvableQuotient) m.getValue(), (ExpVector) m.getKey());
    }

    public QuotSolvablePolynomial<C> multiply(Entry<ExpVector, SolvableQuotient<C>> m) {
        if (m == null) {
            return this.ring.getZERO();
        }
        return multiply((SolvableQuotient) m.getValue(), (ExpVector) m.getKey());
    }

    protected QuotSolvablePolynomial<C> shift(QuotSolvablePolynomial<C> B, ExpVector f) {
        QuotSolvablePolynomial<C> C = this.ring.getZERO().copy();
        if (B == null || B.isZERO()) {
            return C;
        }
        if (f == null || f.isZERO()) {
            return B;
        }
        Map<ExpVector, SolvableQuotient<C>> Cm = C.val;
        for (Entry<ExpVector, SolvableQuotient<C>> y : B.val.entrySet()) {
            SolvableQuotient<C> a = (SolvableQuotient) y.getValue();
            ExpVector d = ((ExpVector) y.getKey()).sum(f);
            if (!a.isZERO()) {
                Cm.put(d, a);
            }
        }
        return C;
    }
}
