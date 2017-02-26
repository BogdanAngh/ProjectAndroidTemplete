package edu.jas.poly;

import edu.jas.kern.Scripting;
import edu.jas.kern.Scripting.Lang;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import io.github.kexanie.library.BuildConfig;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.commons.math4.random.ValueServer;
import org.apache.log4j.Logger;

public class AlgebraicNumberRing<C extends RingElem<C>> implements RingFactory<AlgebraicNumber<C>>, Iterable<AlgebraicNumber<C>> {
    private static final Logger logger;
    protected int isField;
    public final GenPolynomial<C> modul;
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
        logger = Logger.getLogger(AlgebraicNumberRing.class);
    }

    public AlgebraicNumberRing(GenPolynomial<C> m) {
        this.isField = -1;
        this.ring = m.ring;
        this.modul = m;
        if (this.ring.nvar > 1) {
            throw new IllegalArgumentException("only univariate polynomials allowed");
        }
    }

    public AlgebraicNumberRing(GenPolynomial<C> m, boolean isField) {
        this.isField = -1;
        this.ring = m.ring;
        this.modul = m;
        this.isField = isField ? 1 : 0;
        if (this.ring.nvar > 1) {
            throw new IllegalArgumentException("only univariate polynomials allowed");
        }
    }

    public GenPolynomial<C> getModul() {
        return this.modul;
    }

    public AlgebraicNumber<C> copy(AlgebraicNumber<C> c) {
        return new AlgebraicNumber(this, c.val);
    }

    public AlgebraicNumber<C> getZERO() {
        return new AlgebraicNumber(this, this.ring.getZERO());
    }

    public AlgebraicNumber<C> getONE() {
        return new AlgebraicNumber(this, this.ring.getONE());
    }

    public AlgebraicNumber<C> getGenerator() {
        return new AlgebraicNumber(this, this.ring.univariate(0));
    }

    public List<AlgebraicNumber<C>> generators() {
        List<GenPolynomial<C>> gc = this.ring.generators();
        List<AlgebraicNumber<C>> gens = new ArrayList(gc.size());
        for (GenPolynomial<C> g : gc) {
            gens.add(new AlgebraicNumber(this, g));
        }
        return gens;
    }

    public boolean isFinite() {
        return this.ring.coFac.isFinite();
    }

    public boolean isCommutative() {
        return this.ring.isCommutative();
    }

    public boolean isAssociative() {
        return this.ring.isAssociative();
    }

    public boolean isField() {
        if (this.isField > 0) {
            return true;
        }
        if (this.isField == 0 || this.ring.coFac.isField()) {
            return false;
        }
        this.isField = 0;
        return false;
    }

    public void setField(boolean field) {
        if (this.isField > 0 && field) {
            return;
        }
        if (this.isField == 0 && !field) {
            return;
        }
        if (field) {
            this.isField = 1;
        } else {
            this.isField = 0;
        }
    }

    public int getField() {
        return this.isField;
    }

    public BigInteger characteristic() {
        return this.ring.characteristic();
    }

    public AlgebraicNumber<C> fillFromInteger(BigInteger a) {
        if (characteristic().signum() == 0) {
            return new AlgebraicNumber(this, this.ring.fromInteger(a));
        }
        BigInteger p = characteristic();
        BigInteger b = a;
        GenPolynomial<C> v = this.ring.getZERO();
        GenPolynomial x = this.ring.univariate(0, 1);
        GenPolynomial<C> t = this.ring.getONE();
        do {
            BigInteger[] qr = b.divideAndRemainder(p);
            BigInteger q = qr[0];
            v = v.sum(t.multiply(this.ring.fromInteger(qr[1])));
            t = t.multiply(x);
            b = q;
        } while (!b.equals(BigInteger.ZERO));
        AlgebraicNumber<C> an = new AlgebraicNumber(this, v);
        logger.info("fill(" + a + ") = " + v + ", mod: " + an);
        return an;
    }

    public AlgebraicNumber<C> fillFromInteger(long a) {
        return fillFromInteger(new BigInteger(BuildConfig.FLAVOR + a));
    }

    public AlgebraicNumber<C> fromInteger(BigInteger a) {
        return new AlgebraicNumber(this, this.ring.fromInteger(a));
    }

    public AlgebraicNumber<C> fromInteger(long a) {
        return new AlgebraicNumber(this, this.ring.fromInteger(a));
    }

    public String toString() {
        return "AlgebraicNumberRing[ " + this.modul.toString() + " | isField=" + this.isField + " :: " + this.ring.toString() + " ]";
    }

    public String toScript() {
        StringBuffer s = new StringBuffer();
        s.append("AN(");
        s.append(this.modul.toScript());
        switch (1.$SwitchMap$edu$jas$kern$Scripting$Lang[Scripting.getLang().ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                s.append(isField() ? ",true" : ",false");
                break;
            default:
                s.append(isField() ? ",True" : ",False");
                break;
        }
        s.append(",");
        s.append(this.ring.toScript());
        s.append(")");
        return s.toString();
    }

    public boolean equals(Object b) {
        if (b == null || !(b instanceof AlgebraicNumberRing)) {
            return false;
        }
        return this.modul.equals(((AlgebraicNumberRing) b).modul);
    }

    public int hashCode() {
        return (this.modul.hashCode() * 37) + this.ring.hashCode();
    }

    public AlgebraicNumber<C> random(int n) {
        return new AlgebraicNumber(this, this.ring.random(n).monic());
    }

    public AlgebraicNumber<C> random(int n, Random rnd) {
        return new AlgebraicNumber(this, this.ring.random(n, rnd).monic());
    }

    public AlgebraicNumber<C> parse(String s) {
        return new AlgebraicNumber(this, this.ring.parse(s));
    }

    public AlgebraicNumber<C> parse(Reader r) {
        return new AlgebraicNumber(this, this.ring.parse(r));
    }

    public AlgebraicNumber<C> chineseRemainder(AlgebraicNumber<C> c, AlgebraicNumber<C> ci, AlgebraicNumber<C> a) {
        if (c.ring.modul.compareTo(a.ring.modul) < 1) {
            System.out.println("AlgebraicNumber error " + c + ", " + a);
        }
        AlgebraicNumber<C> d = a.subtract(new AlgebraicNumber(a.ring, c.val));
        if (d.isZERO()) {
            return new AlgebraicNumber(this, c.val);
        }
        return new AlgebraicNumber(this, c.ring.modul.multiply(d.multiply((AlgebraicNumber) ci).val).sum(c.val));
    }

    public AlgebraicNumber<C> interpolate(AlgebraicNumber<C> c, C ci, C am, C a) {
        RingElem d = (RingElem) a.subtract(PolyUtil.evaluateMain(this.ring.coFac, c.val, (RingElem) am));
        if (d.isZERO()) {
            return new AlgebraicNumber(this, c.val);
        }
        return new AlgebraicNumber(this, c.ring.modul.multiply((RingElem) d.multiply(ci)).sum(c.val));
    }

    public int depth() {
        RingFactory<C> cf = this.ring.coFac;
        if (cf instanceof AlgebraicNumberRing) {
            return 1 + ((AlgebraicNumberRing) cf).depth();
        }
        return 1;
    }

    public long extensionDegree() {
        return this.modul.degree(0);
    }

    public long totalExtensionDegree() {
        long degree = this.modul.degree(0);
        RingFactory<C> cf = this.ring.coFac;
        if (!(cf instanceof AlgebraicNumberRing)) {
            return degree;
        }
        AlgebraicNumberRing<C> arr = (AlgebraicNumberRing) cf;
        if (degree == 0) {
            return arr.totalExtensionDegree();
        }
        return degree * arr.totalExtensionDegree();
    }

    public Iterator<AlgebraicNumber<C>> iterator() {
        return new AlgebraicNumberIterator(this);
    }
}
