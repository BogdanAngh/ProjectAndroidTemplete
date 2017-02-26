package edu.jas.poly;

import com.example.duy.calculator.math_eval.Constants;
import edu.jas.kern.Scripting;
import edu.jas.kern.Scripting.Lang;
import edu.jas.structure.RingElem;
import edu.jas.vector.GenVector;
import edu.jas.vector.GenVectorModul;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.random.ValueServer;
import org.apache.log4j.Logger;

public class ModuleList<C extends RingElem<C>> implements Serializable {
    private static final Logger logger;
    public final int cols;
    public final List<List<GenPolynomial<C>>> list;
    public final GenPolynomialRing<C> ring;
    public final int rows;

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
        logger = Logger.getLogger(ModuleList.class);
    }

    public ModuleList(GenPolynomialRing<C> r, List<List<GenPolynomial<C>>> l) {
        this.ring = r;
        this.list = padCols(r, l);
        if (this.list == null) {
            this.rows = -1;
            this.cols = -1;
            return;
        }
        this.rows = this.list.size();
        if (this.rows > 0) {
            this.cols = ((List) this.list.get(0)).size();
        } else {
            this.cols = -1;
        }
    }

    public ModuleList(GenSolvablePolynomialRing<C> r, List<List<GenSolvablePolynomial<C>>> l) {
        this((GenPolynomialRing) r, castToList(l));
    }

    public ModuleList(GenVectorModul<GenPolynomial<C>> r, List<GenVector<GenPolynomial<C>>> l) {
        this((GenPolynomialRing) r.coFac, vecToList(l));
    }

    public boolean equals(Object m) {
        if (m == null || !(m instanceof ModuleList)) {
            return false;
        }
        ModuleList<C> ml = (ModuleList) m;
        if (!this.ring.equals(ml.ring)) {
            return false;
        }
        if (this.list == ml.list) {
            return true;
        }
        if (this.list == null || ml.list == null || this.list.size() != ml.list.size() || !OrderedModuleList.sort(this.ring, this.list).equals(OrderedModuleList.sort(this.ring, ml.list))) {
            return false;
        }
        return true;
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
        if (this.list == null) {
            erg.append(")");
            return erg.toString();
        }
        boolean first = true;
        erg.append("(\n");
        for (List<GenPolynomial<C>> row : this.list) {
            if (first) {
                first = false;
            } else {
                erg.append(",\n");
            }
            boolean ifirst = true;
            erg.append(" ( ");
            for (GenPolynomial<C> oa : row) {
                String os;
                if (oa == null) {
                    os = Constants.ZERO;
                } else if (vars != null) {
                    os = oa.toString(vars);
                } else {
                    os = oa.toString();
                }
                if (ifirst) {
                    ifirst = false;
                } else {
                    erg.append(", ");
                    if (os.length() > 100) {
                        erg.append("\n");
                    }
                }
                erg.append(os);
            }
            erg.append(" )");
        }
        erg.append("\n)");
        return erg.toString();
    }

    public String toScript() {
        StringBuffer s = new StringBuffer();
        if (this.ring instanceof GenSolvablePolynomialRing) {
            s.append("Solvable");
        }
        switch (1.$SwitchMap$edu$jas$kern$Scripting$Lang[Scripting.getLang().ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                s.append("SubModule.new(");
                break;
            default:
                s.append("SubModule(");
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
        for (List<GenPolynomial<C>> row : this.list) {
            if (first) {
                first = false;
            } else {
                s.append(",");
            }
            boolean ifirst = true;
            s.append(" ( ");
            for (GenPolynomial<C> oa : row) {
                String os;
                if (oa == null) {
                    os = Constants.ZERO;
                } else {
                    os = oa.toScript();
                }
                if (ifirst) {
                    ifirst = false;
                } else {
                    s.append(", ");
                }
                s.append(os);
            }
            s.append(" )");
        }
        s.append(" ])");
        return s.toString();
    }

    public static <C extends RingElem<C>> List<List<GenPolynomial<C>>> padCols(GenPolynomialRing<C> ring, List<List<GenPolynomial<C>>> l) {
        if (l == null) {
            return l;
        }
        int mcols = 0;
        int rs = 0;
        for (List<GenPolynomial<C>> row : l) {
            if (row != null) {
                rs++;
                if (row.size() > mcols) {
                    mcols = row.size();
                }
            }
        }
        List<List<GenPolynomial<C>>> norm = new ArrayList(rs);
        for (List<GenPolynomial<C>> row2 : l) {
            if (row2 != null) {
                List<GenPolynomial<C>> rn = new ArrayList(row2);
                while (rn.size() < mcols) {
                    rn.add(ring.getZERO());
                }
                norm.add(rn);
            }
        }
        return norm;
    }

    public PolynomialList<C> getPolynomialList() {
        GenPolynomialRing pfac = this.ring.extend(this.cols);
        logger.debug("extended ring = " + pfac);
        if (this.list == null) {
            return new PolynomialList(pfac, (List) null);
        }
        List pols = new ArrayList(this.rows);
        if (this.rows == 0) {
            return new PolynomialList(pfac, pols);
        }
        GenPolynomial<C> zero = pfac.getZERO();
        for (List<GenPolynomial<C>> r : this.list) {
            GenPolynomial<C> ext = zero;
            int m = 0;
            for (GenPolynomial<C> c : r) {
                ext = ext.sum(c.extend(pfac, m, 1));
                m++;
            }
            pols.add(ext);
        }
        return new PolynomialList(pfac, pols);
    }

    public List<List<GenSolvablePolynomial<C>>> castToSolvableList() {
        if (this.list == null) {
            return null;
        }
        List<List<GenSolvablePolynomial<C>>> slist = new ArrayList(this.list.size());
        for (List<GenPolynomial<C>> row : this.list) {
            List<GenSolvablePolynomial<C>> srow = new ArrayList(row.size());
            for (GenPolynomial<C> p : row) {
                if (p instanceof GenSolvablePolynomial) {
                    srow.add((GenSolvablePolynomial) p);
                } else {
                    throw new RuntimeException("no solvable polynomial " + p);
                }
            }
            slist.add(srow);
        }
        return slist;
    }

    public static <C extends RingElem<C>> List<List<GenPolynomial<C>>> castToList(List<List<GenSolvablePolynomial<C>>> slist) {
        if (slist == null) {
            return null;
        }
        List<List<GenPolynomial<C>>> list = new ArrayList(slist.size());
        for (List<GenSolvablePolynomial<C>> srow : slist) {
            List<GenPolynomial<C>> row = new ArrayList(srow.size());
            for (GenSolvablePolynomial<C> s : srow) {
                row.add(s);
            }
            list.add(row);
        }
        return list;
    }

    public static <C extends RingElem<C>> List<List<GenPolynomial<C>>> vecToList(List<GenVector<GenPolynomial<C>>> vlist) {
        if (vlist == null) {
            return null;
        }
        List<List<GenPolynomial<C>>> list = new ArrayList(vlist.size());
        for (GenVector<GenPolynomial<C>> srow : vlist) {
            list.add(srow.val);
        }
        return list;
    }
}
