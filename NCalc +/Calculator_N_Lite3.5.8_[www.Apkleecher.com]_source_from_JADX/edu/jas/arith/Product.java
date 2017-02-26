package edu.jas.arith;

import edu.jas.structure.Element;
import edu.jas.structure.NotInvertibleException;
import edu.jas.structure.RegularRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public class Product<C extends RingElem<C>> implements RegularRingElem<Product<C>> {
    private static final Logger logger;
    protected int isunit;
    public final ProductRing<C> ring;
    public final SortedMap<Integer, C> val;

    static {
        logger = Logger.getLogger(Product.class);
    }

    public Product(ProductRing<C> r) {
        this(r, new TreeMap(), 0);
    }

    public Product(ProductRing<C> r, SortedMap<Integer, C> a) {
        this(r, a, -1);
    }

    public Product(ProductRing<C> r, SortedMap<Integer, C> a, int u) {
        this.isunit = -1;
        this.ring = r;
        this.val = a;
        this.isunit = u;
    }

    public C get(int i) {
        return (RingElem) this.val.get(Integer.valueOf(i));
    }

    public ProductRing<C> factory() {
        return this.ring;
    }

    public Product<C> copy() {
        return new Product(this.ring, this.val, this.isunit);
    }

    public boolean isZERO() {
        return this.val.size() == 0;
    }

    public boolean isONE() {
        if (this.val.size() != this.ring.length()) {
            return false;
        }
        for (RingElem e : this.val.values()) {
            if (!e.isONE()) {
                return false;
            }
        }
        return true;
    }

    public boolean isFull() {
        if (this.val.size() != this.ring.length()) {
            return false;
        }
        return true;
    }

    public boolean isUnit() {
        if (this.isunit > 0) {
            return true;
        }
        if (this.isunit == 0) {
            return false;
        }
        if (isZERO()) {
            this.isunit = 0;
            return false;
        }
        for (RingElem e : this.val.values()) {
            if (!e.isUnit()) {
                this.isunit = 0;
                return false;
            }
        }
        this.isunit = 1;
        return true;
    }

    public boolean isIdempotent() {
        for (RingElem e : this.val.values()) {
            if (!e.isONE()) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return this.val.toString();
    }

    public String toScript() {
        StringBuffer s = new StringBuffer("( ");
        boolean first = true;
        for (Integer i : this.val.keySet()) {
            C v = (RingElem) this.val.get(i);
            if (first) {
                first = false;
            } else if (v.signum() < 0) {
                s.append(" - ");
                RingElem v2 = (RingElem) v.negate();
            } else {
                s.append(" + ");
            }
            if (!v.isONE()) {
                s.append(v.toScript() + "*");
            }
            s.append("pg" + i);
        }
        s.append(" )");
        return s.toString();
    }

    public String toScriptFactory() {
        return factory().toScript();
    }

    public int compareTo(Product<C> b) {
        if (this.ring.equals(b.ring)) {
            SortedMap<Integer, C> v = b.val;
            Iterator<Entry<Integer, C>> ti = this.val.entrySet().iterator();
            Iterator<Entry<Integer, C>> bi = v.entrySet().iterator();
            while (ti.hasNext() && bi.hasNext()) {
                Entry<Integer, C> te = (Entry) ti.next();
                Entry<Integer, C> be = (Entry) bi.next();
                int s = ((Integer) te.getKey()).compareTo((Integer) be.getKey());
                if (s != 0) {
                    return s;
                }
                s = ((RingElem) te.getValue()).compareTo((Element) be.getValue());
                if (s != 0) {
                    return s;
                }
            }
            if (ti.hasNext()) {
                return -1;
            }
            if (bi.hasNext()) {
                return 1;
            }
            return 0;
        }
        logger.info("other ring " + b.ring);
        throw new IllegalArgumentException("rings not comparable " + this);
    }

    public boolean equals(Object b) {
        if (b != null && (b instanceof Product) && compareTo((Product) b) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.ring.hashCode() * 37) + this.val.hashCode();
    }

    public Product<C> extend(int i, int j) {
        RingFactory<C> rf = this.ring.getFactory(j);
        SortedMap<Integer, C> elem = new TreeMap(this.val);
        RingElem w = (RingElem) rf.copy((RingElem) this.val.get(Integer.valueOf(i)));
        if (!w.isZERO()) {
            elem.put(Integer.valueOf(j), w);
        }
        return new Product(this.ring, elem, this.isunit);
    }

    public Product<C> abs() {
        SortedMap<Integer, C> elem = new TreeMap();
        for (Integer i : this.val.keySet()) {
            elem.put(i, (RingElem) ((RingElem) this.val.get(i)).abs());
        }
        return new Product(this.ring, elem, this.isunit);
    }

    public Product<C> sum(Product<C> S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return S;
        }
        SortedMap<Integer, C> elem = new TreeMap(this.val);
        for (Entry<Integer, C> is : S.val.entrySet()) {
            Integer i = (Integer) is.getKey();
            RingElem x = (RingElem) elem.get(i);
            RingElem y = (RingElem) is.getValue();
            if (x != null) {
                x = (RingElem) x.sum(y);
                if (x.isZERO()) {
                    elem.remove(i);
                } else {
                    elem.put(i, x);
                }
            } else {
                elem.put(i, y);
            }
        }
        return new Product(this.ring, elem);
    }

    public Product<C> negate() {
        SortedMap<Integer, C> elem = new TreeMap();
        for (Integer i : this.val.keySet()) {
            elem.put(i, (RingElem) ((RingElem) this.val.get(i)).negate());
        }
        return new Product(this.ring, elem, this.isunit);
    }

    public int signum() {
        if (this.val.size() == 0) {
            return 0;
        }
        return ((RingElem) this.val.get(this.val.firstKey())).signum();
    }

    public Product<C> subtract(Product<C> S) {
        return sum(S.negate());
    }

    public Product<C> inverse() {
        if (isZERO()) {
            return this;
        }
        int isu = 0;
        SortedMap<Integer, C> elem = new TreeMap();
        for (Integer i : this.val.keySet()) {
            C x;
            try {
                x = (RingElem) ((RingElem) this.val.get(i)).inverse();
            } catch (NotInvertibleException e) {
                x = null;
            }
            if (!(x == null || x.isZERO())) {
                elem.put(i, x);
                isu = 1;
            }
        }
        return new Product(this.ring, elem, isu);
    }

    public Product<C> idempotent() {
        if (isZERO()) {
            return this;
        }
        SortedMap<Integer, C> elem = new TreeMap();
        for (Integer i : this.val.keySet()) {
            elem.put(i, (RingElem) this.ring.getFactory(i.intValue()).getONE());
        }
        return new Product(this.ring, elem, 1);
    }

    public Product<C> idemComplement() {
        if (isZERO()) {
            return this.ring.getONE();
        }
        int isu = 0;
        SortedMap<Integer, C> elem = new TreeMap();
        for (int i = 0; i < this.ring.length(); i++) {
            if (((RingElem) this.val.get(Integer.valueOf(i))) == null) {
                elem.put(Integer.valueOf(i), (RingElem) this.ring.getFactory(i).getONE());
                isu = 1;
            }
        }
        return new Product(this.ring, elem, isu);
    }

    public Product<C> idempotentAnd(Product<C> S) {
        if (isZERO() && S.isZERO()) {
            return this;
        }
        int isu = 0;
        SortedMap<Integer, C> elem = new TreeMap();
        for (int i = 0; i < this.ring.length(); i++) {
            RingElem w = (RingElem) S.val.get(Integer.valueOf(i));
            if (!(((RingElem) this.val.get(Integer.valueOf(i))) == null || w == null)) {
                elem.put(Integer.valueOf(i), (RingElem) this.ring.getFactory(i).getONE());
                isu = 1;
            }
        }
        return new Product(this.ring, elem, isu);
    }

    public Product<C> idempotentOr(Product<C> S) {
        if (isZERO() && S.isZERO()) {
            return this;
        }
        int isu = 0;
        SortedMap<Integer, C> elem = new TreeMap();
        for (int i = 0; i < this.ring.length(); i++) {
            RingElem w = (RingElem) S.val.get(Integer.valueOf(i));
            if (((RingElem) this.val.get(Integer.valueOf(i))) != null || w != null) {
                elem.put(Integer.valueOf(i), (RingElem) this.ring.getFactory(i).getONE());
                isu = 1;
            }
        }
        return new Product(this.ring, elem, isu);
    }

    public Product<C> fillIdempotent(Product<C> S) {
        if (S.isZERO()) {
            return this;
        }
        SortedMap<Integer, C> elem = new TreeMap(this.val);
        int i = 0;
        while (i < this.ring.length()) {
            if (((RingElem) elem.get(Integer.valueOf(i))) == null && ((RingElem) S.val.get(Integer.valueOf(i))) != null) {
                elem.put(Integer.valueOf(i), (RingElem) this.ring.getFactory(i).getONE());
            }
            i++;
        }
        return new Product(this.ring, elem, this.isunit);
    }

    public Product<C> fillOne() {
        if (isFull()) {
            return this;
        }
        if (isZERO()) {
            return this.ring.getONE();
        }
        SortedMap<Integer, C> elem = new TreeMap(this.val);
        for (int i = 0; i < this.ring.length(); i++) {
            if (((RingElem) elem.get(Integer.valueOf(i))) == null) {
                elem.put(Integer.valueOf(i), (RingElem) this.ring.getFactory(i).getONE());
            }
        }
        return new Product(this.ring, elem, this.isunit);
    }

    public Product<C> divide(Product<C> S) {
        if (S == null) {
            return this.ring.getZERO();
        }
        if (S.isZERO()) {
            return S;
        }
        if (isZERO()) {
            return this;
        }
        SortedMap<Integer, C> elem = new TreeMap();
        SortedMap<Integer, C> sel = S.val;
        for (Integer i : this.val.keySet()) {
            RingElem y = (RingElem) sel.get(i);
            if (y != null) {
                C x;
                RingElem x2 = (RingElem) this.val.get(i);
                try {
                    x = (RingElem) x2.divide(y);
                } catch (NotInvertibleException e) {
                    System.out.println("product divide error: x = " + x2 + ", y = " + y);
                    x = null;
                }
                if (!(x == null || x.isZERO())) {
                    elem.put(i, x);
                }
            }
        }
        return new Product(this.ring, elem);
    }

    public Product<C> remainder(Product<C> S) {
        if (S == null || S.isZERO() || isZERO()) {
            return this;
        }
        SortedMap<Integer, C> elem = new TreeMap();
        SortedMap<Integer, C> sel = S.val;
        for (Integer i : this.val.keySet()) {
            RingElem y = (RingElem) sel.get(i);
            if (y != null) {
                RingElem x = (RingElem) ((RingElem) this.val.get(i)).remainder(y);
                if (!(x == null || x.isZERO())) {
                    elem.put(i, x);
                }
            }
        }
        return new Product(this.ring, elem);
    }

    public Product<C>[] quotientRemainder(Product<C> S) {
        return new Product[]{divide((Product) S), remainder((Product) S)};
    }

    public Product<C> multiply(Product<C> S) {
        if (S == null) {
            return this.ring.getZERO();
        }
        if (S.isZERO()) {
            return S;
        }
        if (isZERO()) {
            return this;
        }
        SortedMap<Integer, C> elem = new TreeMap();
        SortedMap<Integer, C> sel = S.val;
        for (Integer i : this.val.keySet()) {
            RingElem y = (RingElem) sel.get(i);
            if (y != null) {
                RingElem x = (RingElem) ((RingElem) this.val.get(i)).multiply(y);
                if (!(x == null || x.isZERO())) {
                    elem.put(i, x);
                }
            }
        }
        return new Product(this.ring, elem);
    }

    public Product<C> multiply(C c) {
        SortedMap<Integer, C> elem = new TreeMap();
        for (Integer i : this.val.keySet()) {
            RingElem v = (RingElem) ((RingElem) this.val.get(i)).multiply(c);
            if (!(v == null || v.isZERO())) {
                elem.put(i, v);
            }
        }
        return new Product(this.ring, elem);
    }

    public Product<C> gcd(Product<C> S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return S;
        }
        SortedMap<Integer, C> elem = new TreeMap(this.val);
        for (Entry<Integer, C> is : S.val.entrySet()) {
            Integer i = (Integer) is.getKey();
            RingElem x = (RingElem) elem.get(i);
            RingElem y = (RingElem) is.getValue();
            if (x != null) {
                C x2 = x.gcd(y);
                if (x2 == null || x2.isZERO()) {
                    elem.remove(i);
                } else {
                    elem.put(i, x2);
                }
            } else {
                elem.put(i, y);
            }
        }
        return new Product(this.ring, elem);
    }

    public Product<C>[] egcd(Product<C> S) {
        Product[] ret = (Product[]) new Product[3];
        ret[0] = null;
        ret[1] = null;
        ret[2] = null;
        if (S == null || S.isZERO()) {
            ret[0] = this;
        } else if (isZERO()) {
            ret[0] = S;
        } else {
            SortedMap<Integer, C> elem = new TreeMap(this.val);
            SortedMap<Integer, C> elem1 = idempotent().val;
            SortedMap<Integer, C> elem2 = new TreeMap();
            for (Entry<Integer, C> is : S.val.entrySet()) {
                Integer i = (Integer) is.getKey();
                RingElem x = (RingElem) elem.get(i);
                RingElem y = (RingElem) is.getValue();
                if (x != null) {
                    C[] g = x.egcd(y);
                    if (g[0].isZERO()) {
                        elem.remove(i);
                    } else {
                        elem.put(i, g[0]);
                        elem1.put(i, g[1]);
                        elem2.put(i, g[2]);
                    }
                } else {
                    elem.put(i, y);
                    elem2.put(i, this.ring.getFactory(i.intValue()).getONE());
                }
            }
            ret[0] = new Product(this.ring, elem);
            ret[1] = new Product(this.ring, elem1);
            ret[2] = new Product(this.ring, elem2);
        }
        return ret;
    }
}
