package edu.jas.poly;

import edu.jas.kern.Scripting;
import edu.jas.kern.Scripting.Lang;
import edu.jas.structure.RingElem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.math4.random.ValueServer;
import org.apache.log4j.Logger;

public class PolynomialList<C extends RingElem<C>> implements Comparable<PolynomialList<C>>, Serializable {
    private static final Logger logger;
    public final List<GenPolynomial<C>> list;
    public final GenPolynomialRing<C> ring;

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$edu$jas$kern$Scripting$Lang;

        static {
            $SwitchMap$edu$jas$kern$Scripting$Lang = new int[Lang.values().length];
            try {
                $SwitchMap$edu$jas$kern$Scripting$Lang[Lang.Ruby.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$edu$jas$kern$Scripting$Lang[Lang.Python.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static {
        logger = Logger.getLogger(PolynomialList.class);
    }

    public PolynomialList(GenPolynomialRing<C> r, List<GenPolynomial<C>> l) {
        this.ring = r;
        this.list = l;
    }

    public PolynomialList(GenSolvablePolynomialRing<C> r, List<GenSolvablePolynomial<C>> l) {
        this((GenPolynomialRing) r, castToList(l));
    }

    public PolynomialList<C> copy() {
        return new PolynomialList(this.ring, new ArrayList(this.list));
    }

    public boolean equals(Object p) {
        if (p == null) {
            return false;
        }
        if (p instanceof PolynomialList) {
            PolynomialList pl = (PolynomialList) p;
            if (!this.ring.equals(pl.ring)) {
                System.out.println("not same Ring " + this.ring.toScript() + ", " + pl.ring.toScript());
                return false;
            } else if (compareTo(pl) == 0) {
                return true;
            } else {
                return false;
            }
        }
        System.out.println("no PolynomialList");
        return false;
    }

    public int compareTo(PolynomialList<C> L) {
        int si = L.list.size();
        if (this.list.size() < si) {
            si = this.list.size();
        }
        int s = 0;
        List<GenPolynomial<C>> l1 = OrderedPolynomialList.sort(this.ring, this.list);
        List<GenPolynomial<C>> l2 = OrderedPolynomialList.sort(this.ring, L.list);
        for (int i = 0; i < si; i++) {
            s = ((GenPolynomial) l1.get(i)).compareTo((GenPolynomial) l2.get(i));
            if (s != 0) {
                return s;
            }
        }
        if (this.list.size() > si) {
            return 1;
        }
        return L.list.size() > si ? -1 : s;
    }

    public int hashCode() {
        return (this.ring.hashCode() * 37) + (this.list == null ? 0 : this.list.hashCode());
    }

    public String toString() {
        StringBuffer erg = new StringBuffer();
        String[] vars = null;
        if (this.ring != null) {
            erg.append(this.ring.toString());
            vars = this.ring.getVars();
        }
        boolean first = true;
        erg.append("\n(\n");
        for (GenPolynomial<C> oa : this.list) {
            String sa;
            if (vars != null) {
                sa = oa.toString(vars);
            } else {
                sa = oa.toString();
            }
            if (first) {
                first = false;
            } else {
                erg.append(", ");
                if (sa.length() > 10) {
                    erg.append("\n");
                }
            }
            erg.append("( " + sa + " )");
        }
        erg.append("\n)");
        return erg.toString();
    }

    public String toScript() {
        StringBuffer s = new StringBuffer();
        if (!(this.ring instanceof GenSolvablePolynomialRing)) {
            switch (1.$SwitchMap$edu$jas$kern$Scripting$Lang[Scripting.getLang().ordinal()]) {
                case ValueServer.REPLAY_MODE /*1*/:
                    s.append("SimIdeal.new(");
                    break;
                default:
                    s.append("Ideal(");
                    break;
            }
        }
        switch (1.$SwitchMap$edu$jas$kern$Scripting$Lang[Scripting.getLang().ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                s.append("SolvIdeal.new(");
                break;
            default:
                s.append("SolvableIdeal(");
                break;
        }
        if (this.ring != null) {
            s.append(this.ring.toScript());
        }
        if (this.list == null) {
            s.append(")");
            return s.toString();
        }
        switch (1.$SwitchMap$edu$jas$kern$Scripting$Lang[Scripting.getLang().ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                s.append(",\"\",[");
                break;
            default:
                s.append(",list=[");
                break;
        }
        boolean first = true;
        for (GenPolynomial<C> oa : this.list) {
            String sa = oa.toScript();
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            s.append(sa);
        }
        s.append("])");
        return s.toString();
    }

    public ModuleList<C> getModuleList(int i) {
        GenPolynomialRing pfac = this.ring.contract(i);
        logger.debug("contracted ring = " + pfac);
        if (this.list == null) {
            return new ModuleList(pfac, (List) null);
        }
        int rows = this.list.size();
        List vecs = new ArrayList(rows);
        if (rows == 0) {
            return new ModuleList(pfac, vecs);
        }
        ArrayList<GenPolynomial<C>> zr = new ArrayList(i - 1);
        GenPolynomial<C> zero = pfac.getZERO();
        for (int j = 0; j < i; j++) {
            zr.add(j, zero);
        }
        for (GenPolynomial<C> p : this.list) {
            if (p != null) {
                Map<ExpVector, GenPolynomial<C>> r = p.contract(pfac);
                List<GenPolynomial<C>> row = new ArrayList(zr);
                for (Entry<ExpVector, GenPolynomial<C>> me : r.entrySet()) {
                    ExpVector e = (ExpVector) me.getKey();
                    int[] dov = e.dependencyOnVariables();
                    int ix = 0;
                    int length = dov.length;
                    if (r0 > 1) {
                        throw new IllegalArgumentException("wrong dependencyOnVariables " + e);
                    }
                    length = dov.length;
                    if (r0 == 1) {
                        ix = dov[0];
                    }
                    row.set(ix, (GenPolynomial) me.getValue());
                }
                vecs.add(row);
            }
        }
        return new ModuleList(pfac, vecs);
    }

    public List<GenPolynomial<C>> getList() {
        return this.list;
    }

    public List<GenSolvablePolynomial<C>> castToSolvableList() {
        return castToSolvableList(this.list);
    }

    public List<GenSolvablePolynomial<C>> getSolvableList() {
        return castToSolvableList(this.list);
    }

    public GenSolvablePolynomialRing<C> getSolvableRing() {
        return (GenSolvablePolynomialRing) this.ring;
    }

    public static <C extends RingElem<C>> List<GenSolvablePolynomial<C>> castToSolvableList(List<GenPolynomial<C>> list) {
        if (list == null) {
            return null;
        }
        List<GenSolvablePolynomial<C>> slist = new ArrayList(list.size());
        for (GenPolynomial<C> p : list) {
            if (p instanceof GenSolvablePolynomial) {
                slist.add((GenSolvablePolynomial) p);
            } else {
                throw new IllegalArgumentException("no solvable polynomial " + p);
            }
        }
        return slist;
    }

    public static <C extends RingElem<C>> List<List<GenSolvablePolynomial<C>>> castToSolvableMatrix(List<List<GenPolynomial<C>>> list) {
        if (list == null) {
            return null;
        }
        List<List<GenSolvablePolynomial<C>>> slist = new ArrayList(list.size());
        for (List<GenPolynomial<C>> p : list) {
            slist.add(castToSolvableList(p));
        }
        return slist;
    }

    public static <C extends RingElem<C>> List<GenPolynomial<C>> castToList(List<? extends GenPolynomial<C>> slist) {
        logger.debug("warn: can lead to wrong method dispatch");
        if (slist == null) {
            return null;
        }
        List<GenPolynomial<C>> list = new ArrayList(slist.size());
        for (GenPolynomial<C> p : slist) {
            list.add(p);
        }
        return list;
    }

    public static <C extends RingElem<C>> List<List<GenPolynomial<C>>> castToMatrix(List<List<? extends GenPolynomial<C>>> slist) {
        logger.debug("warn: can lead to wrong method dispatch");
        if (slist == null) {
            return null;
        }
        List<List<GenPolynomial<C>>> list = new ArrayList(slist.size());
        for (List<? extends GenPolynomial<C>> p : slist) {
            list.add(castToList(p));
        }
        return list;
    }

    public boolean isZERO() {
        if (this.list == null) {
            return true;
        }
        for (GenPolynomial<C> p : this.list) {
            if (p != null && !p.isZERO()) {
                return false;
            }
        }
        return true;
    }

    public boolean isONE() {
        if (this.list == null) {
            return false;
        }
        for (GenPolynomial<C> p : this.list) {
            if (p != null && p.isONE()) {
                return true;
            }
        }
        return false;
    }

    public PolynomialList<C> homogenize() {
        GenPolynomialRing pfac = this.ring.extend(1);
        List hom = new ArrayList(this.list.size());
        for (GenPolynomial<C> p : this.list) {
            hom.add(p.homogenize(pfac));
        }
        return new PolynomialList(pfac, hom);
    }

    public PolynomialList<C> deHomogenize() {
        GenPolynomialRing pfac = this.ring.contract(1);
        List dehom = new ArrayList(this.list.size());
        for (GenPolynomial<C> p : this.list) {
            dehom.add(p.deHomogenize(pfac));
        }
        return new PolynomialList(pfac, dehom);
    }

    public boolean isHomogeneous() {
        if (this.list == null) {
            return true;
        }
        for (GenPolynomial<C> p : this.list) {
            if (p != null && !p.isHomogeneous()) {
                return false;
            }
        }
        return true;
    }
}
