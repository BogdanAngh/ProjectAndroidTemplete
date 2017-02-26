package edu.jas.vector;

import edu.jas.kern.StringUtil;
import edu.jas.structure.ModulFactory;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;

public class GenVectorModul<C extends RingElem<C>> implements ModulFactory<GenVector<C>, C> {
    public static final float DEFAULT_DENSITY = 0.5f;
    private static final Logger logger;
    private static final Random random;
    public final List<GenVector<C>> BASIS;
    public final GenVector<C> ZERO;
    public final RingFactory<C> coFac;
    public final int cols;
    private final float density;

    static {
        logger = Logger.getLogger(GenVectorModul.class);
        random = new Random();
    }

    public GenVectorModul(RingFactory<C> b, int s) {
        int i;
        this.density = DEFAULT_DENSITY;
        this.coFac = b;
        this.cols = s;
        ArrayList<C> z = new ArrayList(this.cols);
        for (i = 0; i < this.cols; i++) {
            z.add(this.coFac.getZERO());
        }
        this.ZERO = new GenVector(this, z);
        this.BASIS = new ArrayList(this.cols);
        List<C> cgens = this.coFac.generators();
        for (i = 0; i < this.cols; i++) {
            for (C g : cgens) {
                ArrayList<C> v = new ArrayList(z);
                v.set(i, g);
                this.BASIS.add(new GenVector(this, v));
            }
        }
        logger.info(this.cols + " module over " + this.coFac + "constructed");
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append(this.coFac.getClass().getSimpleName());
        s.append("[" + this.cols + "]");
        return s.toString();
    }

    public String toScript() {
        String f;
        StringBuffer s = new StringBuffer("Vec(");
        try {
            f = ((RingElem) this.coFac).toScriptFactory();
        } catch (Exception e) {
            f = this.coFac.toScript();
        }
        s.append(f + "," + this.cols + " )");
        return s.toString();
    }

    public GenVector<C> getZERO() {
        return this.ZERO;
    }

    public List<GenVector<C>> generators() {
        return this.BASIS;
    }

    public boolean isFinite() {
        return this.coFac.isFinite();
    }

    public boolean equals(Object other) {
        if (!(other instanceof GenVectorModul)) {
            return false;
        }
        GenVectorModul omod = (GenVectorModul) other;
        if (this.cols == omod.cols && this.coFac.equals(omod.coFac)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.cols * 37) + this.coFac.hashCode();
    }

    public GenVector<C> fromInteger(long a) {
        return ((GenVector) this.BASIS.get(0)).scalarMultiply((RingElem) this.coFac.fromInteger(a));
    }

    public GenVector<C> fromInteger(BigInteger a) {
        return ((GenVector) this.BASIS.get(0)).scalarMultiply((RingElem) this.coFac.fromInteger(a));
    }

    public GenVector<C> fromList(List<C> v) {
        if (v == null) {
            return this.ZERO;
        }
        if (v.size() > this.cols) {
            throw new IllegalArgumentException("size v > cols " + v + " > " + this.cols);
        }
        List<C> r = new ArrayList(this.cols);
        r.addAll(v);
        for (int i = r.size(); i < this.cols; i++) {
            r.add(this.coFac.getZERO());
        }
        return new GenVector(this, r);
    }

    public GenVector<C> random(int k) {
        return random(k, DEFAULT_DENSITY, random);
    }

    public GenVector<C> random(int k, float q) {
        return random(k, q, random);
    }

    public GenVector<C> random(int k, Random random) {
        return random(k, DEFAULT_DENSITY, random);
    }

    public GenVector<C> random(int k, float q, Random random) {
        List<C> r = new ArrayList(this.cols);
        for (int i = 0; i < this.cols; i++) {
            if (random.nextFloat() < q) {
                r.add(this.coFac.random(k));
            } else {
                r.add(this.coFac.getZERO());
            }
        }
        return new GenVector(this, r);
    }

    public GenVector<C> copy(GenVector<C> c) {
        return c == null ? c : c.copy();
    }

    public GenVector<C> parse(String s) {
        int i = s.indexOf("[");
        if (i >= 0) {
            s = s.substring(i + 1);
        }
        i = s.indexOf("]");
        if (i >= 0) {
            s = s.substring(0, i);
        }
        List<C> vec = new ArrayList(this.cols);
        do {
            i = s.indexOf(",");
            if (i >= 0) {
                String e = s.substring(0, i);
                s = s.substring(i + 1);
                vec.add((RingElem) this.coFac.parse(e));
                continue;
            }
        } while (i >= 0);
        if (s.trim().length() > 0) {
            vec.add((RingElem) this.coFac.parse(s));
        }
        return new GenVector(this, vec);
    }

    public GenVector<C> parse(Reader r) {
        return parse(StringUtil.nextPairedString(r, '[', ']'));
    }
}
