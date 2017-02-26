package edu.jas.arith;

import edu.jas.kern.StringUtil;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public final class ModIntegerRing implements ModularRingFactory<ModInteger>, Iterable<ModInteger> {
    private static final Random random;
    private int isField;
    public final BigInteger modul;

    static {
        random = new Random();
    }

    public ModIntegerRing(BigInteger m) {
        this.isField = -1;
        this.modul = m;
    }

    public ModIntegerRing(BigInteger m, boolean isField) {
        this.isField = -1;
        this.modul = m;
        this.isField = isField ? 1 : 0;
    }

    public ModIntegerRing(long m) {
        this(new BigInteger(String.valueOf(m)));
    }

    public ModIntegerRing(long m, boolean isField) {
        this(new BigInteger(String.valueOf(m)), isField);
    }

    public ModIntegerRing(String m) {
        this(new BigInteger(m.trim()));
    }

    public ModIntegerRing(String m, boolean isField) {
        this(new BigInteger(m.trim()), isField);
    }

    public BigInteger getModul() {
        return this.modul;
    }

    public BigInteger getIntegerModul() {
        return new BigInteger(this.modul);
    }

    public ModInteger create(BigInteger c) {
        return new ModInteger(this, c);
    }

    public ModInteger create(long c) {
        return new ModInteger(this, c);
    }

    public ModInteger create(String c) {
        return parse(c);
    }

    public ModInteger copy(ModInteger c) {
        return new ModInteger(this, c.val);
    }

    public ModInteger getZERO() {
        return new ModInteger(this, BigInteger.ZERO);
    }

    public ModInteger getONE() {
        return new ModInteger(this, BigInteger.ONE);
    }

    public List<ModInteger> generators() {
        List<ModInteger> g = new ArrayList(1);
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
        if (this.modul.isProbablePrime(this.modul.bitLength())) {
            this.isField = 1;
            return true;
        }
        this.isField = 0;
        return false;
    }

    public BigInteger characteristic() {
        return this.modul;
    }

    public ModInteger fromInteger(BigInteger a) {
        return new ModInteger(this, a);
    }

    public ModInteger fromInteger(long a) {
        return new ModInteger(this, a);
    }

    public String toString() {
        return " bigMod(" + this.modul.toString() + ")";
    }

    public String toScript() {
        if (isField()) {
            return "GF(" + this.modul.toString() + ")";
        }
        return "ZM(" + this.modul.toString() + ")";
    }

    public boolean equals(Object b) {
        if (!(b instanceof ModIntegerRing)) {
            return false;
        }
        if (this.modul.compareTo(((ModIntegerRing) b).modul) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.modul.hashCode();
    }

    public ModInteger random(int n) {
        return random(n, random);
    }

    public ModInteger random(int n, Random rnd) {
        return new ModInteger(this, new BigInteger(n, rnd));
    }

    public ModInteger parse(String s) {
        return new ModInteger(this, s);
    }

    public ModInteger parse(Reader r) {
        return parse(StringUtil.nextString(r));
    }

    public ModInteger chineseRemainder(ModInteger c, ModInteger ci, ModInteger a) {
        ModInteger d = a.subtract(a.ring.fromInteger(c.val));
        if (d.isZERO()) {
            return fromInteger(c.val);
        }
        return fromInteger(c.ring.modul.multiply(d.multiply(ci).val).add(c.val));
    }

    public Iterator<ModInteger> iterator() {
        return new ModIntegerIterator(this);
    }
}
