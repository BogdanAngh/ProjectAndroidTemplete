package edu.jas.application;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.OrderedPolynomialList;
import edu.jas.poly.PolynomialList;
import edu.jas.structure.GcdRingElem;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

public class GroebnerSystem<C extends GcdRingElem<C>> {
    private static final Logger logger;
    protected PolynomialList<GenPolynomial<C>> cgb;
    protected List<Condition<C>> conds;
    private final boolean debug;
    public final List<ColoredSystem<C>> list;

    static {
        logger = Logger.getLogger(GroebnerSystem.class);
    }

    public GroebnerSystem(List<ColoredSystem<C>> S) {
        this.debug = logger.isDebugEnabled();
        this.list = S;
        this.conds = null;
        this.cgb = null;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("GroebnerSystem: \n");
        boolean first = true;
        for (ColoredSystem<C> cs : this.list) {
            if (first) {
                first = false;
            } else {
                sb.append("\n");
            }
            sb.append(cs.toString());
        }
        sb.append("Conditions:\n");
        first = true;
        for (Condition<C> cond : getConditions()) {
            if (first) {
                first = false;
            } else {
                sb.append("\n");
            }
            sb.append(cond.toString());
        }
        sb.append("\n");
        if (this.cgb == null) {
            sb.append("Comprehensive Groebner Base not jet computed\n");
        } else {
            sb.append("Comprehensive Groebner Base:\n");
            first = true;
            for (GenPolynomial<GenPolynomial<C>> p : getCGB()) {
                if (first) {
                    first = false;
                } else {
                    sb.append(",\n");
                }
                sb.append(p.toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String toScript() {
        StringBuffer sb = new StringBuffer("GroebnerSystem: \n");
        boolean first = true;
        for (ColoredSystem<C> cs : this.list) {
            if (first) {
                first = false;
            } else {
                sb.append("\n");
            }
            sb.append(cs.toScript());
        }
        sb.append("Conditions:\n");
        first = true;
        for (Condition<C> cond : getConditions()) {
            if (first) {
                first = false;
            } else {
                sb.append("\n");
            }
            sb.append(cond.toScript());
        }
        sb.append("\n");
        if (this.cgb == null) {
            sb.append("Comprehensive Groebner Base not jet computed\n");
        } else {
            sb.append("Comprehensive Groebner Base:\n");
            first = true;
            for (GenPolynomial<GenPolynomial<C>> p : getCGB()) {
                if (first) {
                    first = false;
                } else {
                    sb.append(",\n");
                }
                sb.append(p.toScript());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public boolean equals(Object c) {
        try {
            GroebnerSystem<C> cs = (GroebnerSystem) c;
            if (cs == null) {
                return false;
            }
            return this.list.equals(cs.list);
        } catch (ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return this.list.hashCode();
    }

    public boolean checkInvariant() {
        for (ColoredSystem<C> s : this.list) {
            if (!s.checkInvariant()) {
                return false;
            }
        }
        return true;
    }

    public boolean isDetermined() {
        for (ColoredSystem<C> s : this.list) {
            if (!s.isDetermined()) {
                return false;
            }
        }
        return true;
    }

    public List<Condition<C>> getConditions() {
        if (this.conds != null) {
            return this.conds;
        }
        List<Condition<C>> cd = new ArrayList(this.list.size());
        for (ColoredSystem<C> cs : this.list) {
            cd.add(cs.condition);
        }
        this.conds = cd;
        return this.conds;
    }

    public List<GenPolynomial<GenPolynomial<C>>> getCGB() {
        if (this.cgb != null) {
            return this.cgb.list;
        }
        if (getConditions().isEmpty()) {
            logger.info("unused is empty");
        }
        Set<GenPolynomial<GenPolynomial<C>>> Gs = new HashSet();
        for (ColoredSystem<C> cs : this.list) {
            if (this.debug) {
                if (!cs.isDetermined()) {
                    System.out.println("not determined, cs = " + cs);
                }
                if (!cs.checkInvariant()) {
                    System.out.println("not invariant, cs = " + cs);
                }
            }
            for (ColorPolynomial<C> p : cs.list) {
                Gs.add(p.getPolynomial());
            }
        }
        List<GenPolynomial<GenPolynomial<C>>> G = new ArrayList(Gs);
        GenPolynomialRing<GenPolynomial<C>> ring = null;
        if (G.size() > 0) {
            ring = ((GenPolynomial) G.get(0)).ring;
        }
        this.cgb = new OrderedPolynomialList(ring, G);
        return G;
    }
}
