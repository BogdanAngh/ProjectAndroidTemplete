package edu.jas.arith;

import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.vector.GenVectorModul;
import io.github.kexanie.library.BuildConfig;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public class ProductRing<C extends RingElem<C>> implements RingFactory<Product<C>> {
    private static final Logger logger;
    protected static final Random random;
    protected int nCopies;
    protected final RingFactory<C> ring;
    protected final List<RingFactory<C>> ringList;

    static {
        logger = Logger.getLogger(ProductRing.class);
        random = new Random();
    }

    public ProductRing(RingFactory<C> r, int n) {
        this.ring = r;
        this.nCopies = n;
        this.ringList = null;
    }

    public ProductRing(List<RingFactory<C>> l) {
        this.ringList = l;
        this.ring = null;
        this.nCopies = 0;
    }

    public RingFactory<C> getFactory(int i) {
        if (this.nCopies == 0) {
            return (RingFactory) this.ringList.get(i);
        }
        if (i >= 0 && i < this.nCopies) {
            return this.ring;
        }
        logger.info("index: " + i);
        throw new IllegalArgumentException("index out of bound " + this);
    }

    public synchronized void addFactory(RingFactory<C> rf) {
        if (this.nCopies != 0) {
            if (this.ring.equals(rf)) {
                this.nCopies++;
            }
            throw new IllegalArgumentException("wrong RingFactory: " + rf);
        }
        this.ringList.add(rf);
    }

    public boolean containsFactory(RingFactory<C> rf) {
        if (this.nCopies == 0) {
            return this.ringList.contains(rf);
        }
        if (this.ring.equals(rf)) {
            return true;
        }
        return false;
    }

    public boolean isFinite() {
        if (this.nCopies != 0) {
            return this.ring.isFinite();
        }
        for (RingFactory<C> f : this.ringList) {
            if (!f.isFinite()) {
                return false;
            }
        }
        return true;
    }

    public Product<C> copy(Product<C> c) {
        return new Product(c.ring, c.val, c.isunit);
    }

    public Product<C> getZERO() {
        return new Product(this);
    }

    public Product<C> getONE() {
        SortedMap<Integer, C> elem = new TreeMap();
        int i;
        if (this.nCopies != 0) {
            for (i = 0; i < this.nCopies; i++) {
                elem.put(Integer.valueOf(i), this.ring.getONE());
            }
        } else {
            i = 0;
            for (RingFactory<C> f : this.ringList) {
                elem.put(Integer.valueOf(i), f.getONE());
                i++;
            }
        }
        return new Product(this, elem, 1);
    }

    public List<Product<C>> generators() {
        List<Product<C>> gens = new ArrayList();
        int n = this.nCopies;
        if (n == 0) {
            n = this.ringList.size();
        }
        for (int i = 0; i < n; i++) {
            Iterator i$ = getFactory(i).generators().iterator();
            while (i$.hasNext()) {
                RingElem c = (RingElem) i$.next();
                SortedMap<Integer, C> elem = new TreeMap();
                elem.put(Integer.valueOf(i), c);
                gens.add(new Product(this, elem));
            }
        }
        return gens;
    }

    public Product<C> getAtomic(int i) {
        if (i < 0 || i >= length()) {
            throw new IllegalArgumentException("index out of bounds " + i);
        }
        SortedMap<Integer, C> elem = new TreeMap();
        if (this.nCopies != 0) {
            elem.put(Integer.valueOf(i), this.ring.getONE());
        } else {
            elem.put(Integer.valueOf(i), ((RingFactory) this.ringList.get(i)).getONE());
        }
        return new Product(this, elem, 1);
    }

    public int length() {
        if (this.nCopies != 0) {
            return this.nCopies;
        }
        return this.ringList.size();
    }

    public boolean isCommutative() {
        if (this.nCopies != 0) {
            return this.ring.isCommutative();
        }
        for (RingFactory<C> f : this.ringList) {
            if (!f.isCommutative()) {
                return false;
            }
        }
        return true;
    }

    public boolean isAssociative() {
        if (this.nCopies != 0) {
            return this.ring.isAssociative();
        }
        for (RingFactory<C> f : this.ringList) {
            if (!f.isAssociative()) {
                return false;
            }
        }
        return true;
    }

    public boolean isField() {
        if (this.nCopies != 0) {
            if (this.nCopies == 1) {
                return this.ring.isField();
            }
            return false;
        } else if (this.ringList.size() == 1) {
            return ((RingFactory) this.ringList.get(0)).isField();
        } else {
            return false;
        }
    }

    public boolean onlyFields() {
        if (this.nCopies != 0) {
            return this.ring.isField();
        }
        for (RingFactory<C> f : this.ringList) {
            if (!f.isField()) {
                return false;
            }
        }
        return true;
    }

    public BigInteger characteristic() {
        if (this.nCopies != 0) {
            return this.ring.characteristic();
        }
        BigInteger c = null;
        for (RingFactory<C> f : this.ringList) {
            if (c == null) {
                c = f.characteristic();
            } else {
                BigInteger d = f.characteristic();
                if (c.compareTo(d) > 0) {
                    c = d;
                }
            }
        }
        return c;
    }

    public Product<C> fromInteger(BigInteger a) {
        SortedMap<Integer, C> elem = new TreeMap();
        int i;
        if (this.nCopies != 0) {
            RingElem c = (RingElem) this.ring.fromInteger(a);
            for (i = 0; i < this.nCopies; i++) {
                elem.put(Integer.valueOf(i), c);
            }
        } else {
            i = 0;
            for (RingFactory<C> f : this.ringList) {
                elem.put(Integer.valueOf(i), f.fromInteger(a));
                i++;
            }
        }
        return new Product(this, elem);
    }

    public Product<C> fromInteger(long a) {
        return fromInteger(new BigInteger(BuildConfig.FLAVOR + a));
    }

    public String toString() {
        String cf;
        if (this.nCopies != 0) {
            cf = this.ring.toString();
            if (cf.matches("[0-9].*")) {
                cf = this.ring.getClass().getSimpleName();
            }
            return "ProductRing[ " + cf + "^" + this.nCopies + " ]";
        }
        StringBuffer sb = new StringBuffer("ProductRing[ ");
        int i = 0;
        for (RingFactory<C> f : this.ringList) {
            if (i != 0) {
                sb.append(", ");
            }
            cf = f.toString();
            if (cf.matches("[0-9].*")) {
                cf = f.getClass().getSimpleName();
            }
            sb.append(cf);
            i++;
        }
        sb.append(" ]");
        return sb.toString();
    }

    public String toScript() {
        StringBuffer s = new StringBuffer("RR( [ ");
        for (int i = 0; i < length(); i++) {
            String f;
            if (i > 0) {
                s.append(", ");
            }
            RingFactory<C> v = getFactory(i);
            try {
                f = ((RingElem) v).toScriptFactory();
            } catch (Exception e) {
                f = v.toScript();
            }
            s.append(f);
        }
        s.append(" ] )");
        return s.toString();
    }

    public boolean equals(Object b) {
        if (b == null || !(b instanceof ProductRing)) {
            return false;
        }
        ProductRing<C> a = (ProductRing) b;
        if (this.nCopies != 0) {
            if (!(this.nCopies == a.nCopies && this.ring.equals(a.ring))) {
                return false;
            }
        } else if (this.ringList.size() != a.ringList.size()) {
            return false;
        } else {
            int i = 0;
            for (RingFactory<C> f : this.ringList) {
                if (!f.equals(a.ringList.get(i))) {
                    return false;
                }
                i++;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = 0;
        if (this.nCopies != 0) {
            return (this.ring.hashCode() * 37) + this.nCopies;
        }
        for (RingFactory<C> f : this.ringList) {
            h = (h * 37) + f.hashCode();
        }
        return h;
    }

    public Product<C> random(int n) {
        return random(n, (float) GenVectorModul.DEFAULT_DENSITY);
    }

    public Product<C> random(int n, float q) {
        return random(n, q, random);
    }

    public Product<C> random(int n, Random rnd) {
        return random(n, GenVectorModul.DEFAULT_DENSITY, random);
    }

    public Product<C> random(int n, float q, Random rnd) {
        SortedMap<Integer, C> elem = new TreeMap();
        int i;
        RingElem r;
        if (this.nCopies != 0) {
            for (i = 0; i < this.nCopies; i++) {
                if (rnd.nextFloat() < q) {
                    r = (RingElem) this.ring.random(n, rnd);
                    if (!r.isZERO()) {
                        elem.put(Integer.valueOf(i), r);
                    }
                }
            }
        } else {
            i = 0;
            for (RingFactory<C> f : this.ringList) {
                if (rnd.nextFloat() < q) {
                    r = (RingElem) f.random(n, rnd);
                    if (!r.isZERO()) {
                        elem.put(Integer.valueOf(i), r);
                    }
                }
                i++;
            }
        }
        return new Product(this, elem);
    }

    public Product<C> parse(String s) {
        return parse(new StringReader(s));
    }

    public Product<C> parse(Reader r) {
        SortedMap<Integer, C> elem = new TreeMap();
        int i;
        if (this.nCopies != 0) {
            for (i = 0; i < this.nCopies; i++) {
                elem.put(Integer.valueOf(i), this.ring.parse(r));
            }
        } else {
            i = 0;
            for (RingFactory<C> f : this.ringList) {
                elem.put(Integer.valueOf(i), f.parse(r));
                i++;
            }
        }
        return new Product(this, elem);
    }
}
