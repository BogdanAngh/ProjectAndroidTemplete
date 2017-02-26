package edu.jas.arith;

import edu.jas.kern.StringUtil;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;

public final class ModLongRing implements ModularRingFactory<ModLong>, Iterable<ModLong> {
    public static final BigInteger MAX_LONG;
    private static final Random random;
    private int isField;
    public final long modul;

    static {
        random = new Random();
        MAX_LONG = new BigInteger(String.valueOf(BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT));
    }

    public ModLongRing(long m) {
        this.isField = -1;
        this.modul = m;
    }

    public ModLongRing(long m, boolean isField) {
        this.isField = -1;
        this.modul = m;
        this.isField = isField ? 1 : 0;
    }

    public ModLongRing(Long m) {
        this(m.longValue());
    }

    public ModLongRing(Long m, boolean isField) {
        this(m.longValue(), isField);
    }

    public ModLongRing(BigInteger m) {
        this(m.longValue());
        if (MAX_LONG.compareTo(m) < 0) {
            System.out.println("modul to large for long " + m + ",max=" + MAX_LONG);
            throw new IllegalArgumentException("modul to large for long " + m);
        }
    }

    public ModLongRing(BigInteger m, boolean isField) {
        this(m.longValue(), isField);
        if (MAX_LONG.compareTo(m) < 0) {
            System.out.println("modul to large for long " + m + ",max=" + MAX_LONG);
            throw new IllegalArgumentException("modul to large for long " + m);
        }
    }

    public ModLongRing(String m) {
        this(new Long(m.trim()));
    }

    public ModLongRing(String m, boolean isField) {
        this(new Long(m.trim()), isField);
    }

    public BigInteger getModul() {
        return new BigInteger(Long.toString(this.modul));
    }

    public long getLongModul() {
        return this.modul;
    }

    public BigInteger getIntegerModul() {
        return new BigInteger(this.modul);
    }

    public ModLong create(BigInteger c) {
        return new ModLong(this, c);
    }

    public ModLong create(long c) {
        return new ModLong(this, c);
    }

    public ModLong create(String c) {
        return parse(c);
    }

    public ModLong copy(ModLong c) {
        return new ModLong(this, c.val);
    }

    public ModLong getZERO() {
        return new ModLong(this, 0);
    }

    public ModLong getONE() {
        return new ModLong(this, 1);
    }

    public List<ModLong> generators() {
        List<ModLong> g = new ArrayList(1);
        g.add(getONE());
        return g;
    }

    public boolean isFinite() {
        return true;
    }

    public boolean isCommutative() {
        return true;
    }

    public boolean isAssociative() {
        return true;
    }

    public boolean isField() {
        if (this.isField > 0) {
            return true;
        }
        if (this.isField == 0) {
            return false;
        }
        BigInteger m = new BigInteger(Long.toString(this.modul));
        if (m.isProbablePrime(m.bitLength())) {
            this.isField = 1;
            return true;
        }
        this.isField = 0;
        return false;
    }

    public BigInteger characteristic() {
        return new BigInteger(Long.toString(this.modul));
    }

    public ModLong fromInteger(BigInteger a) {
        return new ModLong(this, a);
    }

    public ModLong fromInteger(long a) {
        return new ModLong(this, a);
    }

    public String toString() {
        return " mod(" + this.modul + ")";
    }

    public String toScript() {
        if (isField()) {
            return "GFL(" + this.modul + ")";
        }
        return "ZL(" + this.modul + ")";
    }

    public boolean equals(Object b) {
        if (!(b instanceof ModLongRing)) {
            return false;
        }
        if (this.modul == ((ModLongRing) b).modul) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (int) this.modul;
    }

    public ModLong random(int n) {
        return random(n, random);
    }

    public ModLong random(int n, Random rnd) {
        return new ModLong(this, new BigInteger(n, rnd));
    }

    public ModLong parse(String s) {
        return new ModLong(this, s);
    }

    public ModLong parse(Reader r) {
        return parse(StringUtil.nextString(r));
    }

    public ModLong chineseRemainder(ModLong c, ModLong ci, ModLong a) {
        if (c.ring.modul < a.ring.modul) {
            System.out.println("ModLong error " + c.ring + ", " + a.ring);
        }
        ModLong d = a.subtract(a.ring.fromInteger(c.val));
        if (d.isZERO()) {
            return new ModLong(this, c.val);
        }
        return new ModLong(this, (c.ring.modul * d.multiply(ci).val) + c.val);
    }

    public Iterator<ModLong> iterator() {
        return new ModLongIterator(this);
    }
}
