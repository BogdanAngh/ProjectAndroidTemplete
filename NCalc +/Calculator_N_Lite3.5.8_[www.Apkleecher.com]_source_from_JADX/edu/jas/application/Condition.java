package edu.jas.application;

import edu.jas.gbufd.MultiplicativeSet;
import edu.jas.gbufd.MultiplicativeSetSquarefree;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.GcdRingElem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.apache.commons.math4.random.ValueServer;
import org.apache.log4j.Logger;
import org.matheclipse.core.interfaces.IExpr;

public class Condition<C extends GcdRingElem<C>> implements Serializable {
    private static final Logger logger;
    public final MultiplicativeSet<C> nonZero;
    public final Ideal<C> zero;

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$edu$jas$application$Condition$Color;

        static {
            $SwitchMap$edu$jas$application$Condition$Color = new int[Color.values().length];
            try {
                $SwitchMap$edu$jas$application$Condition$Color[Color.GREEN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$edu$jas$application$Condition$Color[Color.RED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$edu$jas$application$Condition$Color[Color.WHITE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public enum Color {
        GREEN,
        RED,
        WHITE
    }

    static {
        logger = Logger.getLogger(Condition.class);
    }

    public Condition(GenPolynomialRing<C> ring) {
        this(new Ideal((GenPolynomialRing) ring), new MultiplicativeSetSquarefree(ring));
    }

    public Condition(Ideal<C> z) {
        this(z, new MultiplicativeSetSquarefree(z.list.ring));
    }

    public Condition(MultiplicativeSet<C> nz) {
        this(new Ideal(nz.ring), nz);
    }

    public Condition(Ideal<C> z, MultiplicativeSet<C> nz) {
        if (z == null || nz == null) {
            throw new IllegalArgumentException("only for non null condition parts");
        }
        this.zero = z;
        this.nonZero = nz;
    }

    public String toString() {
        return "Condition[ 0 == " + this.zero.getList().toString() + ", 0 != " + this.nonZero.mset.toString() + " ]";
    }

    public String toScript() {
        return "Condition[ 0 == " + this.zero.getList().toString() + ", 0 != " + this.nonZero.mset.toString() + " ]";
    }

    public boolean equals(Object ob) {
        try {
            Condition<C> c = (Condition) ob;
            if (c != null && this.zero.equals(c.zero) && this.nonZero.equals(c.nonZero)) {
                return true;
            }
            return false;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return (this.zero.getList().hashCode() << 17) + this.nonZero.hashCode();
    }

    public boolean isEmpty() {
        return this.zero.isZERO() && this.nonZero.isEmpty();
    }

    public boolean isContradictory() {
        if (this.zero.isZERO()) {
            return false;
        }
        boolean t = this.zero.isONE();
        if (t) {
            logger.info("contradiction zero.isONE(): " + this);
            return t;
        }
        for (GenPolynomial p : this.zero.getList()) {
            t = this.nonZero.contains(p);
            if (t) {
                logger.info("contradiction nonZero.contains(zero): " + this + ", pol = " + p);
                return t;
            }
        }
        for (GenPolynomial p2 : this.nonZero.mset) {
            t = this.zero.contains(p2);
            if (t) {
                logger.info("contradiction zero.contains(nonZero): " + this + ", pol = " + p2);
                return t;
            }
        }
        return false;
    }

    public Condition<C> extendZero(GenPolynomial<C> z) {
        GenPolynomial z2 = z.monic();
        Ideal<C> idz = this.zero.sum(z2);
        logger.info("added to ideal: " + z2);
        return new Condition(idz, this.nonZero).simplify();
    }

    public Condition<C> extendNonZero(GenPolynomial<C> nz) {
        GenPolynomial<C> n = this.zero.normalform((GenPolynomial) nz).monic();
        if (n == null || n.isZERO()) {
            return this;
        }
        MultiplicativeSet<C> ms = this.nonZero.add(n);
        logger.info("added to non zero: " + n);
        return new Condition(this.zero, ms).simplify();
    }

    public Condition<C> simplify() {
        Ideal<C> idz = this.zero.squarefree();
        if (!idz.getList().equals(this.zero.getList())) {
            logger.info("simplify squarefree: " + this.zero.getList() + " to " + idz.getList());
        }
        List<GenPolynomial<C>> ml = idz.normalform(this.nonZero.mset);
        MultiplicativeSet<C> ms = this.nonZero;
        if (!ml.equals(this.nonZero.mset)) {
            if (ml.size() != this.nonZero.mset.size()) {
                logger.info("contradiction(==0):");
                logger.info("simplify normalform contradiction: " + this.nonZero.mset + " to " + ml);
                return null;
            }
            logger.info("simplify normalform: " + this.nonZero.mset + " to " + ml);
            ms = this.nonZero.replace(ml);
        }
        List Z = ms.removeFactors(idz.getList());
        if (!Z.equals(idz.getList())) {
            if (Z.size() != idz.getList().size()) {
                logger.info("contradiction(!=0):");
                logger.info("simplify removeFactors contradiction: " + idz.getList() + " to " + Z);
                return null;
            }
            logger.info("simplify removeFactors: " + idz.getList() + " to " + Z);
            idz = new Ideal(this.zero.getRing(), Z);
        }
        Condition<C> nc = new Condition(idz, ms);
        if (nc.isContradictory()) {
            logger.info("simplify contradiction: " + nc);
            return null;
        } else if (idz.equals(this.zero) && ms.equals(this.nonZero)) {
            return this;
        } else {
            logger.info("condition simplified: " + this + " to " + nc);
            return nc.simplify();
        }
    }

    public Color color(GenPolynomial<C> c) {
        GenPolynomial m = this.zero.normalform((GenPolynomial) c).monic();
        if (m.isZERO()) {
            return Color.GREEN;
        }
        if (m.isConstant()) {
            return Color.RED;
        }
        if (this.nonZero.contains(m) || this.nonZero.contains((GenPolynomial) c)) {
            return Color.RED;
        }
        return Color.WHITE;
    }

    public ColorPolynomial<C> determine(GenPolynomial<GenPolynomial<C>> A) {
        if (A == null) {
            return null;
        }
        GenPolynomial<GenPolynomial<C>> zero = A.ring.getZERO();
        GenPolynomial<GenPolynomial<C>> green = zero;
        GenPolynomial<GenPolynomial<C>> red = zero;
        GenPolynomial<GenPolynomial<C>> white = zero;
        if (A.isZERO()) {
            return new ColorPolynomial(green, red, white);
        }
        GenPolynomial<GenPolynomial<C>> Ap = A;
        while (!Ap.isZERO()) {
            Entry<ExpVector, GenPolynomial<C>> m = Ap.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            GenPolynomial<C> c = (GenPolynomial) m.getValue();
            GenPolynomial<GenPolynomial<C>> Bp = Ap.reductum();
            switch (1.$SwitchMap$edu$jas$application$Condition$Color[color(c).ordinal()]) {
                case ValueServer.REPLAY_MODE /*1*/:
                    green = green.sum(c, e);
                    Ap = Bp;
                    break;
                case IExpr.DOUBLEID /*2*/:
                    return new ColorPolynomial(green, red.sum(c, e), Bp);
                default:
                    logger.info("recheck undetermined coeff c = " + c + ", cond = " + this);
                    if (extendZero(c) == null) {
                        logger.info("recheck assume red");
                        return new ColorPolynomial(green, red.sum(c, e), Bp);
                    } else if (extendNonZero(c) == null) {
                        logger.info("recheck assume green");
                        green = green.sum(c, e);
                        Ap = Bp;
                        break;
                    } else {
                        System.out.println("undetermined cond       = " + this);
                        System.out.println("undetermined poly     A = " + A);
                        System.out.println("undetermined poly green = " + green);
                        System.out.println("undetermined poly   red = " + red);
                        System.out.println("undetermined poly    Bp = " + Bp);
                        System.out.println("undetermined coeff    c = " + c);
                        throw new RuntimeException("undetermined, c is white = " + c);
                    }
            }
        }
        return new ColorPolynomial(green, red, white);
    }

    public List<ColorPolynomial<C>> determine(List<GenPolynomial<GenPolynomial<C>>> L) {
        if (L == null) {
            return null;
        }
        List<ColorPolynomial<C>> cl = new ArrayList(L.size());
        for (GenPolynomial A : L) {
            ColorPolynomial<C> c = determine(A);
            if (!(c == null || c.isZERO())) {
                cl.add(c);
            }
        }
        return cl;
    }

    public ColorPolynomial<C> reDetermine(ColorPolynomial<C> s) {
        ColorPolynomial<C> p = determine(s.getEssentialPolynomial());
        return new ColorPolynomial(s.green.sum(p.green), p.red, p.white);
    }

    public List<ColorPolynomial<C>> reDetermine(List<ColorPolynomial<C>> S) {
        if (S == null || S.isEmpty()) {
            return S;
        }
        List<ColorPolynomial<C>> P = new ArrayList();
        for (ColorPolynomial s : S) {
            P.add(reDetermine(s));
        }
        return P;
    }

    public boolean isDetermined(ColorPolynomial<C> s) {
        ColorPolynomial<C> p = determine(s.getPolynomial());
        boolean t = p.equals(s);
        if (!t) {
            System.out.println("not determined s    = " + s);
            System.out.println("not determined p    = " + p);
            System.out.println("not determined cond = " + this);
        }
        return t;
    }

    public boolean isDetermined(List<ColorPolynomial<C>> S) {
        if (S == null) {
            return true;
        }
        for (ColorPolynomial p : S) {
            if (!isDetermined(p)) {
                return false;
            }
        }
        return true;
    }
}
