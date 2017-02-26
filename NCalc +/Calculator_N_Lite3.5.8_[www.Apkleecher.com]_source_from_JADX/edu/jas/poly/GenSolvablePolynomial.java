package edu.jas.poly;

import edu.jas.structure.NotInvertibleException;
import edu.jas.structure.RingElem;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import org.apache.log4j.Logger;

public class GenSolvablePolynomial<C extends RingElem<C>> extends GenPolynomial<C> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Logger logger;
    private final boolean debug;
    public final GenSolvablePolynomialRing<C> ring;

    static {
        boolean z;
        if (GenSolvablePolynomial.class.desiredAssertionStatus()) {
            z = false;
        } else {
            z = true;
        }
        $assertionsDisabled = z;
        logger = Logger.getLogger(GenSolvablePolynomial.class);
    }

    public GenSolvablePolynomial(GenSolvablePolynomialRing<C> r) {
        super(r);
        this.debug = false;
        this.ring = r;
    }

    public GenSolvablePolynomial(GenSolvablePolynomialRing<C> r, C c, ExpVector e) {
        this(r);
        if (c != null && !c.isZERO()) {
            this.val.put(e, c);
        }
    }

    public GenSolvablePolynomial(GenSolvablePolynomialRing<C> r, C c) {
        this(r, c, r.evzero);
    }

    protected GenSolvablePolynomial(GenSolvablePolynomialRing<C> r, SortedMap<ExpVector, C> v) {
        this(r);
        if (v.size() > 0) {
            GenPolynomialRing.creations++;
            this.val.putAll(v);
        }
    }

    public GenSolvablePolynomialRing<C> factory() {
        return this.ring;
    }

    public GenSolvablePolynomial<C> copy() {
        return new GenSolvablePolynomial(this.ring, this.val);
    }

    public boolean equals(Object B) {
        if (B instanceof GenSolvablePolynomial) {
            return super.equals(B);
        }
        return false;
    }

    public GenSolvablePolynomial<C> multiply(GenSolvablePolynomial<C> Bp) {
        if (Bp == null || Bp.isZERO()) {
            return this.ring.getZERO();
        } else if (isZERO()) {
            return this;
        } else {
            if (!$assertionsDisabled && this.ring.nvar != Bp.ring.nvar) {
                throw new AssertionError();
            } else if ((this instanceof RecSolvablePolynomial) && (Bp instanceof RecSolvablePolynomial)) {
                logger.info("warn: wrong method dispatch in JRE Rec.multiply(Rec Bp) - trying to fix");
                return ((RecSolvablePolynomial) this).multiply((RecSolvablePolynomial) Bp);
            } else if ((this instanceof QLRSolvablePolynomial) && (Bp instanceof QLRSolvablePolynomial)) {
                logger.info("warn: wrong method dispatch in JRE QLR.multiply(QLR Bp) - trying to fix");
                return ((QLRSolvablePolynomial) this).multiply((QLRSolvablePolynomial) Bp);
            } else {
                boolean commute = this.ring.table.isEmpty();
                GenSolvablePolynomial<C> Cp = this.ring.getZERO().copy();
                ExpVector Z = this.ring.evzero;
                Map<ExpVector, C> A = this.val;
                Set<Entry<ExpVector, C>> Bk = Bp.val.entrySet();
                for (Entry<ExpVector, C> y : A.entrySet()) {
                    RingElem a = (RingElem) y.getValue();
                    ExpVector e = (ExpVector) y.getKey();
                    int[] ep = e.dependencyOnVariables();
                    int el1 = this.ring.nvar + 1;
                    if (ep.length > 0) {
                        el1 = ep[0];
                    }
                    int el1s = (this.ring.nvar + 1) - el1;
                    for (Entry<ExpVector, C> x : Bk) {
                        GenSolvablePolynomial<C> Cs;
                        RingElem b = (RingElem) x.getValue();
                        ExpVector f = (ExpVector) x.getKey();
                        int[] fp = f.dependencyOnVariables();
                        int fl1 = 0;
                        if (fp.length > 0) {
                            fl1 = fp[fp.length - 1];
                        }
                        int fl1s = (this.ring.nvar + 1) - fl1;
                        if (commute || el1s <= fl1s) {
                            ExpVector g = e.sum(f);
                            Cs = this.ring.valueOf(g);
                        } else {
                            ExpVector e1 = e.subst(el1, 0);
                            ExpVector e2 = Z.subst(el1, e.getVal(el1));
                            ExpVector f1 = f.subst(fl1, 0);
                            ExpVector f2 = Z.subst(fl1, f.getVal(fl1));
                            TableRelation<C> rel = this.ring.table.lookup(e2, f2);
                            Cs = rel.p;
                            if (rel.f != null) {
                                ExpVector e4;
                                Cs = Cs.multiply(this.ring.valueOf(rel.f));
                                if (rel.e == null) {
                                    e4 = e2;
                                } else {
                                    e4 = e2.subtract(rel.e);
                                }
                                this.ring.table.update(e4, f2, (GenSolvablePolynomial) Cs);
                            }
                            if (rel.e != null) {
                                Cs = this.ring.valueOf(rel.e).multiply((GenSolvablePolynomial) Cs);
                                this.ring.table.update(e2, f2, (GenSolvablePolynomial) Cs);
                            }
                            if (!f1.isZERO()) {
                                Cs = Cs.multiply(this.ring.valueOf(f1));
                            }
                            if (!e1.isZERO()) {
                                Cs = this.ring.valueOf(e1).multiply(Cs);
                            }
                        }
                        Cp.doAddTo((GenPolynomial) Cs.multiply(a, b));
                    }
                }
                return Cp;
            }
        }
    }

    public GenSolvablePolynomial<C> multiply(GenSolvablePolynomial<C> S, GenSolvablePolynomial<C> T) {
        if (S.isZERO() || T.isZERO() || isZERO()) {
            return this.ring.getZERO();
        }
        if (S.isONE()) {
            return multiply((GenSolvablePolynomial) T);
        }
        if (T.isONE()) {
            return S.multiply(this);
        }
        return S.multiply(this).multiply((GenSolvablePolynomial) T);
    }

    public GenSolvablePolynomial<C> multiply(C b) {
        GenSolvablePolynomial<C> Cp = this.ring.getZERO();
        if (b == null || b.isZERO()) {
            return Cp;
        }
        if ((this instanceof RecSolvablePolynomial) && (b instanceof GenSolvablePolynomial)) {
            logger.info("warn: wrong method dispatch in JRE Rec.multiply(b) - trying to fix");
            return ((RecSolvablePolynomial) this).recMultiply((GenSolvablePolynomial) b);
        } else if ((this instanceof QLRSolvablePolynomial) && (b instanceof GenSolvablePolynomial)) {
            logger.info("warn: wrong method dispatch in JRE QLR.multiply(Bp) - trying to fix");
            return ((QLRSolvablePolynomial) this).multiply((GenSolvablePolynomial) b);
        } else {
            Cp = Cp.copy();
            Map<ExpVector, C> Cm = Cp.val;
            for (Entry<ExpVector, C> y : this.val.entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                RingElem c = (RingElem) ((RingElem) y.getValue()).multiply(b);
                if (!c.isZERO()) {
                    Cm.put(e, c);
                }
            }
            return Cp;
        }
    }

    public GenSolvablePolynomial<C> multiply(C b, C c) {
        GenSolvablePolynomial<C> Cp = this.ring.getZERO();
        if (b == null || b.isZERO()) {
            return Cp;
        }
        if (c == null || c.isZERO()) {
            return Cp;
        }
        if ((this instanceof RecSolvablePolynomial) && (b instanceof GenSolvablePolynomial) && (c instanceof GenSolvablePolynomial)) {
            logger.info("warn: wrong method dispatch in JRE Rec.multiply(b,c) - trying to fix");
            return ((RecSolvablePolynomial) this).multiply((GenSolvablePolynomial) b, (GenSolvablePolynomial) c);
        } else if ((this instanceof QLRSolvablePolynomial) && (b instanceof GenSolvablePolynomial) && (c instanceof GenSolvablePolynomial)) {
            logger.info("warn: wrong method dispatch in JRE QLR.multiply(b,c) - trying to fix");
            return ((QLRSolvablePolynomial) this).multiply((GenSolvablePolynomial) b, (GenSolvablePolynomial) c);
        } else {
            Cp = Cp.copy();
            Map<ExpVector, C> Cm = Cp.val;
            for (Entry<ExpVector, C> y : this.val.entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                RingElem d = (RingElem) ((RingElem) b.multiply((RingElem) y.getValue())).multiply(c);
                if (!d.isZERO()) {
                    Cm.put(e, d);
                }
            }
            return Cp;
        }
    }

    public GenSolvablePolynomial<C> multiply(ExpVector e) {
        return (e == null || e.isZERO()) ? this : multiply(this.ring.getONECoefficient(), e);
    }

    public GenSolvablePolynomial<C> multiply(ExpVector e, ExpVector f) {
        if (e == null || e.isZERO() || f == null || f.isZERO()) {
            return this;
        }
        C b = this.ring.getONECoefficient();
        return multiply(b, e, b, f);
    }

    public GenSolvablePolynomial<C> multiply(C b, ExpVector e) {
        if (b == null || b.isZERO()) {
            return this.ring.getZERO();
        }
        return multiply(this.ring.valueOf((RingElem) b, e));
    }

    public GenSolvablePolynomial<C> multiply(C b, ExpVector e, C c, ExpVector f) {
        if (b == null || b.isZERO()) {
            return this.ring.getZERO();
        }
        if (c == null || c.isZERO()) {
            return this.ring.getZERO();
        }
        return multiply(this.ring.valueOf((RingElem) b, e), this.ring.valueOf((RingElem) c, f));
    }

    public GenSolvablePolynomial<C> multiplyLeft(C b, ExpVector e) {
        if (b == null || b.isZERO()) {
            return this.ring.getZERO();
        }
        return this.ring.valueOf((RingElem) b, e).multiply(this);
    }

    public GenSolvablePolynomial<C> multiplyLeft(ExpVector e) {
        return (e == null || e.isZERO()) ? this : multiplyLeft(this.ring.getONECoefficient(), e);
    }

    public GenSolvablePolynomial<C> multiplyLeft(C b) {
        GenSolvablePolynomial<C> Cp = this.ring.getZERO();
        if (b == null || b.isZERO()) {
            return Cp;
        }
        Cp = Cp.copy();
        Map<ExpVector, C> Cm = Cp.val;
        for (Entry<ExpVector, C> y : this.val.entrySet()) {
            ExpVector e = (ExpVector) y.getKey();
            RingElem c = (RingElem) b.multiply((RingElem) y.getValue());
            if (!c.isZERO()) {
                Cm.put(e, c);
            }
        }
        return Cp;
    }

    public GenSolvablePolynomial<C> multiplyLeft(Entry<ExpVector, C> m) {
        if (m == null) {
            return this.ring.getZERO();
        }
        return multiplyLeft((RingElem) m.getValue(), (ExpVector) m.getKey());
    }

    public GenSolvablePolynomial<C> multiply(Entry<ExpVector, C> m) {
        if (m == null) {
            return this.ring.getZERO();
        }
        return multiply((RingElem) m.getValue(), (ExpVector) m.getKey());
    }

    public GenSolvablePolynomial<C> subtractMultiple(C a, GenSolvablePolynomial<C> S) {
        if (a == null || a.isZERO() || S == null || S.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return S.multiplyLeft((RingElem) a.negate());
        }
        if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
            GenSolvablePolynomial<C> n = copy();
            SortedMap<ExpVector, C> nv = n.val;
            for (Entry<ExpVector, C> me : S.val.entrySet()) {
                ExpVector f = (ExpVector) me.getKey();
                RingElem y = (RingElem) a.multiply((RingElem) me.getValue());
                RingElem x = (RingElem) nv.get(f);
                if (x != null) {
                    x = (RingElem) x.subtract(y);
                    if (x.isZERO()) {
                        nv.remove(f);
                    } else {
                        nv.put(f, x);
                    }
                } else if (!y.isZERO()) {
                    nv.put(f, y.negate());
                }
            }
            return n;
        }
        throw new AssertionError();
    }

    public GenSolvablePolynomial<C> subtractMultiple(C a, ExpVector e, GenSolvablePolynomial<C> S) {
        if (a == null || a.isZERO() || S == null || S.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return S.multiplyLeft((RingElem) a.negate(), e);
        }
        if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
            GenSolvablePolynomial<C> n = copy();
            SortedMap<ExpVector, C> nv = n.val;
            for (Entry<ExpVector, C> me : S.multiplyLeft(e).val.entrySet()) {
                ExpVector f = (ExpVector) me.getKey();
                RingElem y = (RingElem) a.multiply((RingElem) me.getValue());
                RingElem x = (RingElem) nv.get(f);
                if (x != null) {
                    x = (RingElem) x.subtract(y);
                    if (x.isZERO()) {
                        nv.remove(f);
                    } else {
                        nv.put(f, x);
                    }
                } else if (!y.isZERO()) {
                    nv.put(f, y.negate());
                }
            }
            return n;
        }
        throw new AssertionError();
    }

    public GenSolvablePolynomial<C> scaleSubtractMultiple(C b, C a, GenSolvablePolynomial<C> S) {
        if (a == null || S == null) {
            return multiplyLeft((RingElem) b);
        }
        if (a.isZERO() || S.isZERO()) {
            return multiplyLeft((RingElem) b);
        }
        if (isZERO() || b == null || b.isZERO()) {
            return S.multiplyLeft((RingElem) a.negate());
        }
        if (b.isONE()) {
            return subtractMultiple(a, S);
        }
        if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
            GenSolvablePolynomial<C> n = multiplyLeft((RingElem) b);
            SortedMap<ExpVector, C> nv = n.val;
            for (Entry<ExpVector, C> me : S.val.entrySet()) {
                ExpVector f = (ExpVector) me.getKey();
                RingElem y = (RingElem) a.multiply((RingElem) me.getValue());
                RingElem x = (RingElem) nv.get(f);
                if (x != null) {
                    x = (RingElem) x.subtract(y);
                    if (x.isZERO()) {
                        nv.remove(f);
                    } else {
                        nv.put(f, x);
                    }
                } else if (!y.isZERO()) {
                    nv.put(f, y.negate());
                }
            }
            return n;
        }
        throw new AssertionError();
    }

    public GenSolvablePolynomial<C> scaleSubtractMultiple(C b, C a, ExpVector e, GenSolvablePolynomial<C> S) {
        if (a == null || S == null) {
            return multiplyLeft((RingElem) b);
        }
        if (a.isZERO() || S.isZERO()) {
            return multiplyLeft((RingElem) b);
        }
        if (isZERO() || b == null || b.isZERO()) {
            return S.multiplyLeft((RingElem) a.negate(), e);
        }
        if (b.isONE()) {
            return subtractMultiple(a, e, S);
        }
        if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
            GenSolvablePolynomial<C> n = multiplyLeft((RingElem) b);
            SortedMap<ExpVector, C> nv = n.val;
            for (Entry<ExpVector, C> me : S.multiplyLeft(e).val.entrySet()) {
                ExpVector f = (ExpVector) me.getKey();
                RingElem y = (RingElem) a.multiply((RingElem) me.getValue());
                RingElem x = (RingElem) nv.get(f);
                if (x != null) {
                    x = (RingElem) x.subtract(y);
                    if (x.isZERO()) {
                        nv.remove(f);
                    } else {
                        nv.put(f, x);
                    }
                } else if (!y.isZERO()) {
                    nv.put(f, y.negate());
                }
            }
            return n;
        }
        throw new AssertionError();
    }

    public GenSolvablePolynomial<C> scaleSubtractMultiple(C b, ExpVector g, C a, ExpVector e, GenSolvablePolynomial<C> S) {
        if (a == null || S == null) {
            return multiplyLeft(b, g);
        }
        if (a.isZERO() || S.isZERO()) {
            return multiplyLeft(b, g);
        }
        if (isZERO() || b == null || b.isZERO()) {
            return S.multiplyLeft((RingElem) a.negate(), e);
        } else if (b.isONE() && g.isZERO()) {
            return subtractMultiple(a, e, S);
        } else {
            if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
                GenSolvablePolynomial<C> n = multiplyLeft(b, g);
                SortedMap<ExpVector, C> nv = n.val;
                for (Entry<ExpVector, C> me : S.multiplyLeft(e).val.entrySet()) {
                    ExpVector f = (ExpVector) me.getKey();
                    RingElem y = (RingElem) a.multiply((RingElem) me.getValue());
                    RingElem x = (RingElem) nv.get(f);
                    if (x != null) {
                        x = (RingElem) x.subtract(y);
                        if (x.isZERO()) {
                            nv.remove(f);
                        } else {
                            nv.put(f, x);
                        }
                    } else if (!y.isZERO()) {
                        nv.put(f, y.negate());
                    }
                }
                return n;
            }
            throw new AssertionError();
        }
    }

    public GenSolvablePolynomial<C> monic() {
        GenSolvablePolynomial<C> this;
        if (!isZERO()) {
            C lc = leadingBaseCoefficient();
            if (lc.isUnit()) {
                try {
                    this = multiplyLeft((RingElem) lc.inverse());
                } catch (NotInvertibleException e) {
                }
            }
        }
        return this;
    }

    public GenSolvablePolynomial<C>[] quotientRemainder(GenSolvablePolynomial<C> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException("division by zero");
        }
        C c = S.leadingBaseCoefficient();
        if (c.isUnit()) {
            RingElem ci = (RingElem) c.inverse();
            if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
                ExpVector e = S.leadingExpVector();
                GenSolvablePolynomial<C> q = this.ring.getZERO().copy();
                GenSolvablePolynomial<C> r = copy();
                while (!r.isZERO()) {
                    ExpVector f = r.leadingExpVector();
                    if (!f.multipleOf(e)) {
                        break;
                    }
                    C a = r.leadingBaseCoefficient();
                    f = f.subtract(e);
                    RingElem a2 = (RingElem) a.multiply(ci);
                    q = (GenSolvablePolynomial) q.sum(a2, f);
                    GenSolvablePolynomial<C> h = S.multiplyLeft(a2, f);
                    if (h.leadingBaseCoefficient().equals(r.leadingBaseCoefficient())) {
                        r = (GenSolvablePolynomial) r.subtract((GenPolynomial) h);
                    } else {
                        throw new RuntimeException("something is wrong: r = " + r + ", h = " + h);
                    }
                }
                return new GenSolvablePolynomial[]{q, r};
            }
            throw new AssertionError();
        }
        throw new ArithmeticException("lbcf not invertible " + c);
    }

    public GenSolvablePolynomial<C> rightRecursivePolynomial() {
        if (isONE() || isZERO() || !(this instanceof RecSolvablePolynomial) || this.ring.coeffTable.isEmpty()) {
            return this;
        }
        return (RecSolvablePolynomial) ((RecSolvablePolynomial) this).rightRecursivePolynomial();
    }

    public GenSolvablePolynomial<C> evalAsRightRecursivePolynomial() {
        if (isONE() || isZERO() || !(this instanceof RecSolvablePolynomial) || this.ring.coeffTable.isEmpty()) {
            return this;
        }
        return (RecSolvablePolynomial) ((RecSolvablePolynomial) this).evalAsRightRecursivePolynomial();
    }

    public boolean isRightRecursivePolynomial(GenSolvablePolynomial<C> R) {
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
            return ((RecSolvablePolynomial) this).isRightRecursivePolynomial((RecSolvablePolynomial) R);
        } else if (R instanceof RecSolvablePolynomial) {
            return false;
        } else {
            return true;
        }
    }
}
