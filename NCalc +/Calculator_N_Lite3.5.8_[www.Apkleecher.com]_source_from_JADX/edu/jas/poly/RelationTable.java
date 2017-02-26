package edu.jas.poly;

import edu.jas.kern.PrettyPrint;
import edu.jas.structure.RingElem;
import io.github.kexanie.library.BuildConfig;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public class RelationTable<C extends RingElem<C>> implements Serializable {
    private static final Logger logger;
    public final boolean coeffTable;
    private final boolean debug;
    public final GenSolvablePolynomialRing<C> ring;
    public final Map<List<Integer>, List> table;

    static {
        logger = Logger.getLogger(RelationTable.class);
    }

    public RelationTable(GenSolvablePolynomialRing<C> r) {
        this(r, false);
    }

    public RelationTable(GenSolvablePolynomialRing<C> r, boolean coeffTable) {
        this.debug = logger.isDebugEnabled();
        this.table = new HashMap();
        this.ring = r;
        if (this.ring == null) {
            throw new IllegalArgumentException("RelationTable no ring");
        }
        this.coeffTable = coeffTable;
    }

    public boolean equals(Object p) {
        if (p == null) {
            return false;
        }
        if (p instanceof RelationTable) {
            RelationTable<C> tab = (RelationTable) p;
            if (this.table.keySet().equals(tab.table.keySet())) {
                for (List<Integer> k : this.table.keySet()) {
                    if (!equalMaps(fromListDeg2((List) this.table.get(k)), fromListDeg2((List) tab.table.get(k)))) {
                        return false;
                    }
                }
                return true;
            }
            logger.info("keySet != :  a = " + this.table.keySet() + ", b = " + tab.table.keySet());
            return false;
        }
        logger.info("no RelationTable");
        return false;
    }

    Map<ExpVectorPair, GenPolynomial<C>> fromListDeg2(List a) {
        Map<ExpVectorPair, GenPolynomial<C>> tex = new HashMap();
        Iterator ait = a.iterator();
        while (ait.hasNext()) {
            ExpVectorPair ae = (ExpVectorPair) ait.next();
            if (!ait.hasNext()) {
                break;
            }
            GenPolynomial<C> p = (GenPolynomial) ait.next();
            if (ae.totalDeg() == 2) {
                tex.put(ae, p);
            }
        }
        return tex;
    }

    int fromListDeg2HashCode(List a) {
        int h = 0;
        Iterator ait = a.iterator();
        while (ait.hasNext()) {
            ExpVectorPair ae = (ExpVectorPair) ait.next();
            h = (h * 31) + ae.hashCode();
            if (!ait.hasNext()) {
                break;
            }
            GenPolynomial<C> p = (GenPolynomial) ait.next();
            if (ae.totalDeg() == 2) {
                h = (h * 31) + p.val.hashCode();
            }
        }
        return h;
    }

    boolean equalMaps(Map<ExpVectorPair, GenPolynomial<C>> m1, Map<ExpVectorPair, GenPolynomial<C>> m2) {
        if (!m1.keySet().equals(m2.keySet())) {
            return false;
        }
        for (Entry<ExpVectorPair, GenPolynomial<C>> me : m1.entrySet()) {
            GenPolynomial<C> p1 = (GenPolynomial) me.getValue();
            ExpVectorPair ep = (ExpVectorPair) me.getKey();
            GenPolynomial p2 = (GenPolynomial) m2.get(ep);
            if (p1.compareTo(p2) != 0) {
                logger.info("ep = " + ep + ", p1 = " + p1 + ", p2 = " + p2);
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = this.table.keySet().hashCode();
        for (List<Integer> k : this.table.keySet()) {
            int i = h * 31;
            h = i + fromListDeg2HashCode((List) this.table.get(k));
        }
        return h;
    }

    public boolean isEmpty() {
        return this.table.isEmpty();
    }

    public String toString() {
        StringBuffer s = new StringBuffer("RelationTable[");
        boolean first = true;
        for (List<Integer> k : this.table.keySet()) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            s.append(k.toString());
            List v = (List) this.table.get(k);
            s.append("=");
            s.append(v.toString());
        }
        s.append("]");
        return s.toString();
    }

    public String toString(String[] vars) {
        if (vars == null) {
            return toString();
        }
        StringBuffer s = new StringBuffer(BuildConfig.FLAVOR);
        String[] cvars = null;
        if (this.coeffTable) {
            if (this.ring.coFac instanceof GenPolynomialRing) {
                cvars = ((GenPolynomialRing) this.ring.coFac).getVars();
            } else if (this.ring.coFac instanceof GenWordPolynomialRing) {
                cvars = ((GenWordPolynomialRing) this.ring.coFac).getVars();
            }
            s.append("Coefficient ");
        }
        s.append("RelationTable\n(");
        boolean first;
        Iterator jt;
        ExpVectorPair ep;
        if (PrettyPrint.isTrue()) {
            first = true;
            for (List<Integer> k : this.table.keySet()) {
                if (first) {
                    first = false;
                    s.append("\n");
                } else {
                    s.append(",\n");
                }
                jt = ((List) this.table.get(k)).iterator();
                while (jt.hasNext()) {
                    ep = (ExpVectorPair) jt.next();
                    GenSolvablePolynomial<C> p = (GenSolvablePolynomial) jt.next();
                    if (ep.totalDeg() == 2) {
                        s.append("( " + ep.getFirst().toString(vars) + " ), ");
                        if (cvars == null) {
                            s.append("( " + ep.getSecond().toString(vars) + " ), ");
                        } else {
                            s.append("( " + ep.getSecond().toString(cvars) + " ), ");
                        }
                        s.append("( " + p.toString(vars) + " )");
                        if (jt.hasNext()) {
                            s.append(",\n");
                        }
                    }
                }
            }
        } else {
            first = true;
            for (List<Integer> k2 : this.table.keySet()) {
                if (first) {
                    first = false;
                } else {
                    s.append(",\n");
                }
                jt = ((List) this.table.get(k2)).iterator();
                while (jt.hasNext()) {
                    ep = (ExpVectorPair) jt.next();
                    s.append("( " + ep.getFirst().toString(vars) + " ), ");
                    if (cvars == null) {
                        s.append("( " + ep.getSecond().toString(vars) + " ), ");
                    } else {
                        s.append("( " + ep.getSecond().toString(cvars) + " ), ");
                    }
                    s.append(" " + ((GenSolvablePolynomial) jt.next()).toString(vars));
                    if (jt.hasNext()) {
                        s.append(",\n");
                    }
                }
            }
        }
        s.append("\n)\n");
        return s.toString();
    }

    public String toScript() {
        String[] vars = this.ring.vars;
        String[] cvars = null;
        if (this.coeffTable) {
            if (this.ring.coFac instanceof GenPolynomialRing) {
                cvars = ((GenPolynomialRing) this.ring.coFac).getVars();
            } else if (this.ring.coFac instanceof GenWordPolynomialRing) {
                cvars = ((GenWordPolynomialRing) this.ring.coFac).getVars();
            }
        }
        StringBuffer s = new StringBuffer("[");
        boolean first = true;
        for (List<Integer> k : this.table.keySet()) {
            if (first) {
                first = false;
                s.append(BuildConfig.FLAVOR);
            } else {
                s.append(", ");
            }
            Iterator jt = ((List) this.table.get(k)).iterator();
            while (jt.hasNext()) {
                ExpVectorPair ep = (ExpVectorPair) jt.next();
                GenPolynomial<C> p = (GenPolynomial) jt.next();
                if (ep.totalDeg() <= 2) {
                    s.append(BuildConfig.FLAVOR + ep.getFirst().toScript(vars) + ", ");
                    if (this.coeffTable) {
                        String eps = ep.getSecond().toScript(cvars);
                        if (eps.isEmpty()) {
                            eps = ((RingElem) p.leadingBaseCoefficient().abs()).toScript();
                        }
                        s.append(BuildConfig.FLAVOR + eps + ", ");
                    } else {
                        s.append(BuildConfig.FLAVOR + ep.getSecond().toScript(vars) + ", ");
                    }
                    s.append(" " + p.toScript());
                    if (jt.hasNext()) {
                        s.append(", ");
                    }
                }
            }
        }
        s.append("]");
        return s.toString();
    }

    public synchronized void update(ExpVector e, ExpVector f, GenSolvablePolynomial<C> p) {
        if (p == null || e == null || f == null) {
            throw new IllegalArgumentException("RelationTable update e|f|p == null");
        }
        GenSolvablePolynomialRing<C> sring = p.ring;
        if (this.debug) {
            logger.info("new relation = " + sring.toScript(e) + " .*. " + sring.toScript(f) + " = " + p.toScript());
        }
        ExpVector lp;
        if (this.coeffTable) {
            lp = p.leadingExpVector();
            if (e.equals(lp)) {
                if (p.leadingBaseCoefficient() instanceof GenPolynomial) {
                    if (!f.equals(((GenPolynomial) p.leadingBaseCoefficient()).leadingExpVector())) {
                        logger.error("relation term order = " + this.ring.tord);
                        logger.error("Coefficient RelationTable update f != lt(lfcd(p)): " + sring.toScript(e) + ", f = " + f + ", p = " + p.toScript());
                        throw new IllegalArgumentException("Coefficient RelationTable update f != lt(lfcd(p)): " + e + ", f = " + f + ", p = " + p);
                    }
                }
                if (p.leadingBaseCoefficient() instanceof GenWordPolynomial) {
                    if (!f.equals(((GenWordPolynomial) p.leadingBaseCoefficient()).leadingWord().leadingExpVector())) {
                        logger.error("relation term order = " + this.ring.tord);
                        logger.error("Coefficient RelationTable update f != lt(lfcd(p)): " + sring.toScript(e) + ", f = " + f + ", p = " + p.toScript());
                        throw new IllegalArgumentException("Coefficient RelationTable update f != lt(lfcd(p)): " + e + ", f = " + f + ", p = " + p);
                    }
                }
            }
            logger.error("relation term order = " + this.ring.tord);
            throw new IllegalArgumentException("Coefficient RelationTable update e != lt(p): " + sring.toScript(e) + " != " + sring.toScript(lp));
        }
        ExpVector ef;
        if (e.totalDeg() == 1 && f.totalDeg() == 1) {
            int[] de = e.dependencyOnVariables();
            int[] df = f.dependencyOnVariables();
            logger.debug("update e ? f " + de[0] + " " + df[0]);
            if (de[0] == df[0]) {
                throw new IllegalArgumentException("RelationTable update e==f");
            }
            if (de[0] > df[0]) {
                logger.error("update e < f: " + sring.toScript(e) + " < " + sring.toScript(f));
                ExpVector tmp = e;
                e = f;
                f = tmp;
                Entry<ExpVector, C> m = p.leadingMonomial();
                ef = e.sum(f);
                if (ef.equals(m.getKey())) {
                    p = (GenSolvablePolynomial) p.reductum().negate();
                    p.doPutToMap((ExpVector) m.getKey(), (RingElem) m.getValue());
                } else {
                    throw new IllegalArgumentException("update e*f != lt(p): " + sring.toScript(ef) + ", lt = " + sring.toScript((ExpVector) m.getKey()));
                }
            }
        }
        ef = e.sum(f);
        lp = p.leadingExpVector();
        if (!ef.equals(lp)) {
            logger.error("relation term order = " + this.ring.tord);
            throw new IllegalArgumentException("update e*f != lt(p): " + sring.toScript(ef) + " != " + sring.toScript(lp));
        }
        List<Integer> key = makeKey(e, f);
        ExpVectorPair evp = new ExpVectorPair(e, f);
        if (key.size() != 2) {
            logger.warn("key = " + key + ", evp = " + evp);
        }
        List part = (List) this.table.get(key);
        if (part == null) {
            part = new LinkedList();
            part.add(evp);
            part.add(p);
            this.table.put(key, part);
        } else {
            int index = -1;
            synchronized (part) {
                ListIterator it = part.listIterator();
                while (it.hasNext()) {
                    ExpVectorPair look = (ExpVectorPair) it.next();
                    Object skip = it.next();
                    if (look.isMultiple(evp)) {
                        index = it.nextIndex();
                    }
                }
                if (index < 0) {
                    index = 0;
                }
                part.add(index, evp);
                part.add(index + 1, p);
            }
        }
    }

    public void update(GenPolynomial<C> E, GenPolynomial<C> F, GenSolvablePolynomial<C> p) {
        if (E.isZERO() || F.isZERO()) {
            throw new IllegalArgumentException("polynomials may not be zero: " + E + ", " + F);
        }
        C ce = E.leadingBaseCoefficient();
        C cf = F.leadingBaseCoefficient();
        if (ce.isONE()) {
            ExpVector e = E.leadingExpVector();
            ExpVector f = F.leadingExpVector();
            if (this.coeffTable && f.isZERO()) {
                if (cf instanceof GenPolynomial) {
                    f = ((GenPolynomial) cf).leadingExpVector();
                } else if (cf instanceof GenWordPolynomial) {
                    f = ((GenWordPolynomial) cf).leadingWord().leadingExpVector();
                }
            }
            update(e, f, (GenSolvablePolynomial) p);
            return;
        }
        throw new IllegalArgumentException("lbcf of polynomials must be one: " + ce + ", " + cf + ", p = " + p);
    }

    public void update(GenPolynomial<C> E, GenPolynomial<C> F, GenPolynomial<C> p) {
        if (p.isZERO()) {
            throw new IllegalArgumentException("polynomial may not be zero: " + p);
        } else if (p.isONE()) {
            throw new IllegalArgumentException("product of polynomials may not be one: " + p);
        } else {
            update((GenPolynomial) E, (GenPolynomial) F, new GenSolvablePolynomial(this.ring, p.val));
        }
    }

    public void update(ExpVector e, ExpVector f, GenPolynomial<C> p) {
        if (p.isZERO()) {
            throw new IllegalArgumentException("polynomial may not be zero: " + p);
        } else if (p.isONE()) {
            throw new IllegalArgumentException("product of polynomials may not be one: " + p);
        } else {
            update(e, f, new GenSolvablePolynomial(this.ring, p.val));
        }
    }

    public TableRelation<C> lookup(ExpVector e, ExpVector f) {
        List<Integer> key = makeKey(e, f);
        List part = (List) this.table.get(key);
        GenSolvablePolynomial<C> p;
        if (part == null) {
            C c = null;
            if (this.coeffTable) {
                if (this.ring.coFac instanceof GenPolynomialRing) {
                    c = this.ring.coFac.valueOf(f);
                } else {
                    if (this.ring.coFac instanceof GenWordPolynomialRing) {
                        RingElem c2 = this.ring.coFac.valueOf(f);
                    }
                }
                p = new GenSolvablePolynomial(this.ring, c, e);
            } else {
                ExpVector ef = e.sum(f);
                p = this.ring.valueOf(ef);
            }
            return new TableRelation(null, null, p);
        }
        ExpVectorPair evp = new ExpVectorPair(e, f);
        synchronized (part) {
            Iterator it = part.iterator();
            while (it.hasNext()) {
                ExpVectorPair look = (ExpVectorPair) it.next();
                p = (GenSolvablePolynomial) it.next();
                if (evp.isMultiple(look)) {
                    ExpVector ep = e.subtract(look.getFirst());
                    ExpVector fp = f.subtract(look.getSecond());
                    if (ep.isZERO()) {
                        ep = null;
                    }
                    if (fp.isZERO()) {
                        fp = null;
                    }
                    if (this.debug) {
                        if (p != null) {
                            if (p.ring.vars != null) {
                                logger.info("found relation = " + e.toString(p.ring.vars) + " .*. " + f.toString(p.ring.vars) + " = " + p);
                            }
                        }
                        logger.info("found relation = " + e + " .*. " + f + " = " + p);
                    }
                    TableRelation<C> tableRelation = new TableRelation(ep, fp, p);
                    return tableRelation;
                }
            }
            throw new RuntimeException("no entry found in relation table for " + evp);
        }
    }

    protected List<Integer> makeKey(ExpVector e, ExpVector f) {
        int[] de = e.dependencyOnVariables();
        int[] df = f.dependencyOnVariables();
        List<Integer> key = new ArrayList(de.length + df.length);
        for (int valueOf : de) {
            key.add(Integer.valueOf(valueOf));
        }
        for (int valueOf2 : df) {
            key.add(Integer.valueOf(valueOf2));
        }
        return key;
    }

    public int size() {
        int s = 0;
        if (this.table == null || this.table.isEmpty()) {
            return 0;
        }
        for (List list : this.table.values()) {
            s += list.size() / 2;
        }
        return s;
    }

    public void extend(RelationTable<C> tab) {
        if (!tab.table.isEmpty()) {
            int i = this.ring.nvar - tab.ring.nvar;
            for (List<Integer> key : tab.table.keySet()) {
                Iterator jt = ((List) tab.table.get(key)).iterator();
                while (jt.hasNext()) {
                    ExpVector fx;
                    ExpVectorPair ep = (ExpVectorPair) jt.next();
                    ExpVector e = ep.getFirst();
                    ExpVector f = ep.getSecond();
                    GenSolvablePolynomial<C> p = (GenSolvablePolynomial) jt.next();
                    ExpVector ex = e.extend(i, 0, 0);
                    if (this.coeffTable) {
                        fx = f;
                    } else {
                        fx = f.extend(i, 0, 0);
                    }
                    update(ex, fx, (GenSolvablePolynomial) p.extend(this.ring, 0, 0));
                }
            }
        }
    }

    public void contract(RelationTable<C> tab) {
        if (!tab.table.isEmpty()) {
            int i = tab.ring.nvar - this.ring.nvar;
            for (List<Integer> key : tab.table.keySet()) {
                Iterator jt = ((List) tab.table.get(key)).iterator();
                while (jt.hasNext()) {
                    ExpVector fc;
                    ExpVectorPair ep = (ExpVectorPair) jt.next();
                    ExpVector e = ep.getFirst();
                    ExpVector f = ep.getSecond();
                    GenSolvablePolynomial<C> p = (GenSolvablePolynomial) jt.next();
                    ExpVector ec = e.contract(i, e.length() - i);
                    if (this.coeffTable) {
                        fc = f;
                    } else {
                        fc = f.contract(i, f.length() - i);
                    }
                    if (!ec.isZERO()) {
                        Map<ExpVector, GenPolynomial<C>> mc = p.contract(this.ring);
                        if (mc.size() == 1) {
                            update(ec, fc, (GenPolynomial) mc.values().iterator().next());
                        }
                    }
                }
            }
        }
    }

    public void recursive(RelationTable tab) {
        if (!tab.table.isEmpty()) {
            GenPolynomialRing<C> cring = this.ring.coFac;
            int i = this.ring.nvar;
            for (List<Integer> key : tab.table.keySet()) {
                Iterator jt = ((List) tab.table.get(key)).iterator();
                while (jt.hasNext()) {
                    ExpVector fc;
                    ExpVectorPair ep = (ExpVectorPair) jt.next();
                    ExpVector e = ep.getFirst();
                    ExpVector f = ep.getSecond();
                    GenSolvablePolynomial<C> p = (GenSolvablePolynomial) jt.next();
                    ExpVector ec = e.contract(0, i);
                    if (this.coeffTable) {
                        fc = f;
                    } else {
                        fc = f.contract(0, i);
                    }
                    if (!ec.isZERO()) {
                        Map<ExpVector, GenPolynomial<C>> mc = p.contract(cring);
                        if (mc.size() == 1) {
                            update(ec, fc, (GenSolvablePolynomial) mc.values().iterator().next());
                        } else {
                            GenSolvablePolynomial<C> qr = this.ring.getZERO();
                            for (Entry<ExpVector, GenPolynomial<C>> mce : mc.entrySet()) {
                                ExpVector g = (ExpVector) mce.getKey();
                                RingElem cq = (RingElem) ((GenPolynomial) mce.getValue());
                                qr = (GenSolvablePolynomial) qr.sum(new GenSolvablePolynomial(this.ring, cq, g));
                            }
                            if (this.coeffTable) {
                                fc = ((GenPolynomial) qr.leadingBaseCoefficient()).leadingExpVector();
                            }
                            if (!fc.isZERO()) {
                                if (this.coeffTable) {
                                    logger.info("coeffTable: adding " + qr);
                                } else {
                                    logger.info("no coeffTable: adding " + qr);
                                }
                                update(ec, fc, (GenSolvablePolynomial) qr);
                            }
                        }
                    }
                }
            }
        }
    }

    public void reverse(RelationTable<C> tab) {
        if (!tab.table.isEmpty()) {
            if (!this.table.isEmpty()) {
                logger.error("reverse table not empty");
            }
            int k = -1;
            if (this.ring.tord.getEvord2() != 0 && this.ring.partial) {
                k = this.ring.tord.getSplit();
            }
            logger.debug("k split = " + k);
            for (List<Integer> key : tab.table.keySet()) {
                Iterator jt = ((List) tab.table.get(key)).iterator();
                while (jt.hasNext()) {
                    ExpVector ex;
                    ExpVector fx;
                    ExpVectorPair ep = (ExpVectorPair) jt.next();
                    ExpVector e = ep.getFirst();
                    ExpVector f = ep.getSecond();
                    GenSolvablePolynomial<C> p = (GenSolvablePolynomial) jt.next();
                    if (k >= 0) {
                        ex = e.reverse(k);
                        if (this.coeffTable) {
                            fx = f;
                        } else {
                            fx = f.reverse(k);
                        }
                    } else {
                        ex = e.reverse();
                        if (this.coeffTable) {
                            fx = f;
                        } else {
                            fx = f.reverse();
                        }
                    }
                    GenSolvablePolynomial px = (GenSolvablePolynomial) p.reverse(this.ring);
                    if (!true) {
                        update(e, f, px);
                    } else if (this.coeffTable) {
                        update(ex, fx, px);
                    } else {
                        update(fx, ex, px);
                    }
                }
            }
        }
    }

    public List<GenSolvablePolynomial<C>> relationList() {
        List<GenSolvablePolynomial<C>> rels = new ArrayList();
        for (List<Integer> k : this.table.keySet()) {
            Iterator jt = ((List) this.table.get(k)).iterator();
            while (jt.hasNext()) {
                GenSolvablePolynomial<C> pf;
                ExpVectorPair ep = (ExpVectorPair) jt.next();
                GenSolvablePolynomial<C> pe = this.ring.valueOf(ep.getFirst());
                ExpVector f = ep.getSecond();
                if (this.coeffTable) {
                    RingElem cf = null;
                    if (this.ring.coFac instanceof GenPolynomialRing) {
                        cf = ((GenPolynomialRing) this.ring.coFac).valueOf(f);
                    } else if (this.ring.coFac instanceof GenWordPolynomialRing) {
                        cf = ((GenWordPolynomialRing) this.ring.coFac).valueOf(f);
                    }
                    pf = this.ring.valueOf(cf);
                } else {
                    pf = this.ring.valueOf(f);
                }
                GenSolvablePolynomial<C> p = (GenSolvablePolynomial) jt.next();
                rels.add(pe);
                rels.add(pf);
                rels.add(p);
            }
        }
        return rels;
    }

    public void addSolvRelations(List<GenSolvablePolynomial<C>> rel) {
        addRelations(new PolynomialList(this.ring, (List) rel).getList());
    }

    public void addRelations(List<GenPolynomial<C>> rel) {
        if (rel != null && !rel.isEmpty()) {
            Iterator<GenPolynomial<C>> relit = rel.iterator();
            while (relit.hasNext()) {
                ExpVector e = ((GenPolynomial) relit.next()).leadingExpVector();
                ExpVector f = null;
                if (relit.hasNext()) {
                    GenPolynomial<C> F = (GenPolynomial) relit.next();
                    if (relit.hasNext()) {
                        GenPolynomial P = (GenPolynomial) relit.next();
                        if (!this.coeffTable || !F.isConstant()) {
                            f = F.leadingExpVector();
                        } else if (this.ring.coFac instanceof GenPolynomialRing) {
                            f = ((GenPolynomial) F.leadingBaseCoefficient()).leadingExpVector();
                        } else if (this.ring.coFac instanceof GenWordPolynomialRing) {
                            f = ((GenWordPolynomial) F.leadingBaseCoefficient()).leadingWord().leadingExpVector();
                        }
                        update(e, f, P);
                    } else {
                        throw new IllegalArgumentException("poly part missing");
                    }
                }
                throw new IllegalArgumentException("F and poly part missing");
            }
        }
    }
}
