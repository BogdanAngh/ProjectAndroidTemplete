package edu.jas.application;

import edu.jas.application.Condition.Color;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;

public class ColoredSystem<C extends GcdRingElem<C>> {
    private static final Logger logger;
    public final Condition<C> condition;
    private final boolean debug;
    public final List<ColorPolynomial<C>> list;
    public final OrderedCPairlist<C> pairlist;

    static {
        logger = Logger.getLogger(ColoredSystem.class);
    }

    public ColoredSystem(Condition<C> cond, List<ColorPolynomial<C>> S) {
        this(cond, S, null);
    }

    public ColoredSystem(Condition<C> cond, List<ColorPolynomial<C>> S, OrderedCPairlist<C> pl) {
        this.debug = logger.isDebugEnabled();
        this.condition = cond;
        this.list = S;
        this.pairlist = pl;
    }

    public ColoredSystem<C> copy() {
        return new ColoredSystem(this.condition, this.list, this.pairlist.copy());
    }

    public List<ColoredSystem<C>> addToList(List<ColoredSystem<C>> L) {
        List<ColoredSystem<C>> S = new ArrayList(L.size() + 1);
        boolean contained = false;
        for (ColoredSystem<C> x : L) {
            if (this.condition.equals(x.condition) && this.list.equals(x.list)) {
                logger.info("replaced system = " + x.condition);
                S.add(this);
                contained = true;
            } else {
                S.add(x);
            }
        }
        if (!contained) {
            S.add(this);
        }
        return S;
    }

    public String toString() {
        StringBuffer s = new StringBuffer("ColoredSystem: \n");
        if (this.list.size() > 0) {
            s.append("polynomial ring : " + ((ColorPolynomial) this.list.get(0)).green.ring + "\n");
        } else {
            s.append("parameter polynomial ring : " + this.condition.zero.getRing() + "\n");
        }
        s.append("conditions == 0 : " + getConditionZero() + "\n");
        s.append("conditions != 0 : " + getConditionNonZero() + "\n");
        if (this.debug) {
            s.append("green coefficients:\n" + getGreenCoefficients() + "\n");
            s.append("red coefficients:\n" + getRedCoefficients() + "\n");
        }
        s.append("colored polynomials:\n" + this.list + "\n");
        s.append("uncolored polynomials:\n" + getPolynomialList() + "\n");
        if (this.debug) {
            s.append("essential polynomials:\n" + getEssentialPolynomialList() + "\n");
        }
        if (this.pairlist != null) {
            s.append(this.pairlist.toString() + "\n");
        }
        return s.toString();
    }

    public String toScript() {
        StringBuffer s = new StringBuffer("ColoredSystem: \n");
        if (this.list.size() > 0) {
            s.append("polynomial ring : " + ((ColorPolynomial) this.list.get(0)).green.ring.toScript() + "\n");
        } else {
            s.append("parameter polynomial ring : " + this.condition.zero.getRing().toScript() + "\n");
        }
        s.append("conditions == 0 : " + getConditionZero().toString() + "\n");
        s.append("conditions != 0 : " + getConditionNonZero().toString() + "\n");
        if (this.debug) {
            s.append("green coefficients:\n" + getGreenCoefficients().toString() + "\n");
            s.append("red coefficients:\n" + getRedCoefficients().toString() + "\n");
        }
        s.append("colored polynomials:\n" + this.list + "\n");
        s.append("uncolored polynomials:\n" + getPolynomialList() + "\n");
        if (this.debug) {
            s.append("essential polynomials:\n" + getEssentialPolynomialList() + "\n");
        }
        if (this.pairlist != null) {
            s.append(this.pairlist.toString() + "\n");
        }
        return s.toString();
    }

    public boolean equals(Object c) {
        boolean t = false;
        try {
            ColoredSystem<C> cs = (ColoredSystem) c;
            if (cs == null) {
                return false;
            }
            if (this.condition.equals(cs.condition) && this.list.equals(cs.list)) {
                t = true;
            }
            if (!t) {
                return t;
            }
            if (!this.pairlist.equals(cs.pairlist)) {
                System.out.println("pairlists not equal " + this.pairlist + ", " + cs.pairlist);
            }
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return (this.condition.hashCode() << 17) + this.list.hashCode();
    }

    public List<GenPolynomial<C>> getConditionZero() {
        return this.condition.zero.getList();
    }

    public List<GenPolynomial<C>> getConditionNonZero() {
        return this.condition.nonZero.mset;
    }

    public List<GenPolynomial<C>> getRedCoefficients() {
        Set<GenPolynomial<C>> F = new HashSet();
        for (ColorPolynomial<C> s : this.list) {
            F.addAll(s.red.getMap().values());
        }
        return new ArrayList(F);
    }

    public List<GenPolynomial<C>> getGreenCoefficients() {
        Set<GenPolynomial<C>> F = new HashSet();
        for (ColorPolynomial<C> s : this.list) {
            F.addAll(s.green.getMap().values());
        }
        return new ArrayList(F);
    }

    public List<GenPolynomial<GenPolynomial<C>>> getPolynomialList() {
        List<GenPolynomial<GenPolynomial<C>>> F = new ArrayList();
        for (ColorPolynomial<C> s : this.list) {
            F.add(s.getPolynomial());
        }
        return F;
    }

    public List<GenPolynomial<GenPolynomial<C>>> getEssentialPolynomialList() {
        List<GenPolynomial<GenPolynomial<C>>> F = new ArrayList();
        for (ColorPolynomial<C> s : this.list) {
            F.add(s.getEssentialPolynomial());
        }
        return F;
    }

    public boolean checkInvariant() {
        if (!isDetermined() || !this.condition.isDetermined(this.list)) {
            return false;
        }
        for (ColorPolynomial<C> s : this.list) {
            if (s.checkInvariant()) {
                for (GenPolynomial<C> g : s.green.getMap().values()) {
                    if (this.condition.color(g) != Color.GREEN) {
                        System.out.println("notGreen   " + g);
                        System.out.println("condition: " + this.condition);
                        System.out.println("colors:    " + s);
                        return false;
                    }
                }
                for (GenPolynomial<C> r : s.red.getMap().values()) {
                    if (this.condition.color(r) != Color.RED) {
                        System.out.println("notRed     " + r);
                        System.out.println("condition: " + this.condition);
                        System.out.println("colors:    " + s);
                        return false;
                    }
                }
                for (GenPolynomial<C> w : s.white.getMap().values()) {
                    if (this.condition.color(w) != Color.WHITE) {
                    }
                }
            } else {
                System.out.println("notInvariant " + s);
                System.out.println("condition:   " + this.condition);
                return false;
            }
        }
        return true;
    }

    public boolean isDetermined() {
        for (ColorPolynomial s : this.list) {
            if (!s.isZERO()) {
                if (!s.isDetermined()) {
                    System.out.println("not simple determined " + s);
                    System.out.println("condition:            " + this.condition);
                    return false;
                } else if (!this.condition.isDetermined(s)) {
                    return false;
                }
            }
        }
        return true;
    }

    public ColoredSystem<C> reDetermine() {
        if (this.condition == null || this.condition.zero.isONE()) {
            return this;
        }
        List<ColorPolynomial<C>> Sn = new ArrayList(this.list.size());
        for (ColorPolynomial c : this.list) {
            Sn.add(this.condition.reDetermine(c));
        }
        return new ColoredSystem(this.condition, Sn, this.pairlist);
    }
}
