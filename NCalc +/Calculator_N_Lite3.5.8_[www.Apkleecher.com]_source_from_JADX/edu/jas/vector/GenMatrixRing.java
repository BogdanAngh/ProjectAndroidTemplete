package edu.jas.vector;

import edu.jas.kern.StringUtil;
import edu.jas.structure.AlgebraFactory;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;

public class GenMatrixRing<C extends RingElem<C>> implements AlgebraFactory<GenMatrix<C>, C> {
    public static final int DEFAULT_BSIZE = 10;
    public static final float DEFAULT_DENSITY = 0.5f;
    private static final Logger logger;
    private static final Random random;
    public final GenMatrix<C> ONE;
    public final GenMatrix<C> ZERO;
    public final int blocksize;
    public final RingFactory<C> coFac;
    public final int cols;
    private final float density;
    public final int rows;

    static {
        logger = Logger.getLogger(GenMatrixRing.class);
        random = new Random();
    }

    public GenMatrixRing(RingFactory<C> b, int r, int c) {
        this(b, r, c, DEFAULT_BSIZE);
    }

    public GenMatrixRing(RingFactory<C> b, int r, int c, int s) {
        this.density = DEFAULT_DENSITY;
        if (b == null) {
            throw new IllegalArgumentException("RingFactory is null");
        } else if (r < 1) {
            throw new IllegalArgumentException("rows < 1 " + r);
        } else if (c < 1) {
            throw new IllegalArgumentException("cols < 1 " + c);
        } else {
            int i;
            this.coFac = b;
            this.rows = r;
            this.cols = c;
            this.blocksize = s;
            ArrayList<C> z = new ArrayList(this.cols);
            for (i = 0; i < this.cols; i++) {
                z.add(this.coFac.getZERO());
            }
            ArrayList m = new ArrayList(this.rows);
            for (i = 0; i < this.rows; i++) {
                m.add(new ArrayList(z));
            }
            this.ZERO = new GenMatrix(this, m);
            m = new ArrayList(this.rows);
            RingElem one = (RingElem) this.coFac.getONE();
            for (i = 0; i < this.rows; i++) {
                if (i < this.cols) {
                    ArrayList<C> v = new ArrayList(z);
                    v.set(i, one);
                    m.add(v);
                }
            }
            this.ONE = new GenMatrix(this, m);
            logger.info(this.rows + " x " + this.cols + " with blocksize " + this.blocksize + " matrix ring over " + this.coFac + "constructed");
        }
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append(this.coFac.getClass().getSimpleName());
        s.append("[" + this.rows + "," + this.cols + "]");
        return s.toString();
    }

    public String toScript() {
        String f;
        StringBuffer s = new StringBuffer("Mat(");
        try {
            f = ((RingElem) this.coFac).toScriptFactory();
        } catch (Exception e) {
            f = this.coFac.toScript();
        }
        s.append(f + "," + this.rows + "," + this.cols + ")");
        return s.toString();
    }

    public GenMatrix<C> getZERO() {
        return this.ZERO;
    }

    public GenMatrix<C> getONE() {
        return this.ONE;
    }

    public List<GenMatrix<C>> generators() {
        List<C> rgens = this.coFac.generators();
        List<GenMatrix<C>> gens = new ArrayList((this.rows * this.cols) * rgens.size());
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                for (C el : rgens) {
                    gens.add(this.ZERO.set(i, j, el));
                }
            }
        }
        return gens;
    }

    public boolean isFinite() {
        return this.coFac.isFinite();
    }

    public boolean equals(Object other) {
        if (!(other instanceof GenMatrixRing)) {
            return false;
        }
        GenMatrixRing omod = (GenMatrixRing) other;
        if (this.rows == omod.rows && this.cols == omod.cols && this.coFac.equals(omod.coFac)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((this.rows * 17) + this.cols) * 37) + this.coFac.hashCode();
    }

    public boolean isField() {
        return false;
    }

    public boolean isCommutative() {
        return false;
    }

    public boolean isAssociative() {
        return this.rows == this.cols;
    }

    public BigInteger characteristic() {
        return this.coFac.characteristic();
    }

    public GenMatrixRing<C> transpose() {
        return this.rows == this.cols ? this : new GenMatrixRing(this.coFac, this.cols, this.rows, this.blocksize);
    }

    public GenMatrixRing<C> product(GenMatrixRing<C> other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("invalid dimensions in product");
        } else if (this.coFac.equals(other.coFac)) {
            return (this.rows == other.rows && this.cols == other.cols) ? this : new GenMatrixRing(this.coFac, this.rows, other.cols, this.blocksize);
        } else {
            throw new IllegalArgumentException("invalid coefficients in product");
        }
    }

    public GenMatrix<C> fromInteger(long a) {
        return this.ONE.scalarMultiply((RingElem) this.coFac.fromInteger(a));
    }

    public GenMatrix<C> fromInteger(BigInteger a) {
        return this.ONE.scalarMultiply((RingElem) this.coFac.fromInteger(a));
    }

    public GenMatrix<C> fromList(List<List<C>> om) {
        if (om == null) {
            return this.ZERO;
        }
        if (om.size() > this.rows) {
            throw new IllegalArgumentException("size v > rows " + om + " > " + this.rows);
        }
        ArrayList m = new ArrayList(this.rows);
        for (int i = 0; i < this.rows; i++) {
            ArrayList<C> v;
            List<C> ov = (List) om.get(i);
            if (ov == null) {
                v = (ArrayList) this.ZERO.matrix.get(0);
            } else if (ov.size() > this.cols) {
                throw new IllegalArgumentException("size v > cols " + ov + " > " + this.cols);
            } else {
                v = new ArrayList(this.cols);
                v.addAll(ov);
                for (int j = v.size(); j < this.cols; j++) {
                    v.add(this.coFac.getZERO());
                }
            }
            m.add(v);
        }
        return new GenMatrix(this, m);
    }

    public GenMatrix<C> random(int k) {
        return random(k, DEFAULT_DENSITY, random);
    }

    public GenMatrix<C> random(int k, float q) {
        return random(k, q, random);
    }

    public GenMatrix<C> random(int k, Random random) {
        return random(k, DEFAULT_DENSITY, random);
    }

    public GenMatrix<C> random(int k, float q, Random random) {
        ArrayList m = new ArrayList(this.rows);
        for (int i = 0; i < this.rows; i++) {
            ArrayList<C> v = new ArrayList(this.cols);
            for (int j = 0; j < this.cols; j++) {
                C e;
                if (random.nextFloat() < q) {
                    e = (RingElem) this.coFac.random(k);
                } else {
                    RingElem e2 = (RingElem) this.coFac.getZERO();
                }
                v.add(e);
            }
            m.add(v);
        }
        return new GenMatrix(this, m);
    }

    public GenMatrix<C> randomUpper(int k, float q) {
        return randomUpper(k, q, random);
    }

    public GenMatrix<C> randomUpper(int k, float q, Random random) {
        ArrayList m = new ArrayList(this.rows);
        for (int i = 0; i < this.rows; i++) {
            ArrayList<C> v = new ArrayList(this.cols);
            for (int j = 0; j < this.cols; j++) {
                C e = (RingElem) this.coFac.getZERO();
                if (j >= i && random.nextFloat() < q) {
                    e = (RingElem) this.coFac.random(k);
                }
                v.add(e);
            }
            m.add(v);
        }
        return new GenMatrix(this, m);
    }

    public GenMatrix<C> randomLower(int k, float q) {
        return randomLower(k, q, random);
    }

    public GenMatrix<C> randomLower(int k, float q, Random random) {
        ArrayList m = new ArrayList(this.rows);
        for (int i = 0; i < this.rows; i++) {
            ArrayList<C> v = new ArrayList(this.cols);
            for (int j = 0; j < this.cols; j++) {
                C e = (RingElem) this.coFac.getZERO();
                if (j <= i && random.nextFloat() < q) {
                    e = (RingElem) this.coFac.random(k);
                }
                v.add(e);
            }
            m.add(v);
        }
        return new GenMatrix(this, m);
    }

    public GenMatrix<C> copy(GenMatrix<C> c) {
        return c == null ? c : c.copy();
    }

    public GenMatrix<C> parse(String s) {
        int i = s.indexOf("[");
        if (i >= 0) {
            s = s.substring(i + 1);
        }
        ArrayList mat = new ArrayList(this.rows);
        GenVectorModul<C> vmod = new GenVectorModul(this.coFac, this.cols);
        do {
            i = s.indexOf("]");
            String e;
            if (i != s.lastIndexOf("]")) {
                if (i >= 0) {
                    e = s.substring(0, i);
                    s = s.substring(i);
                    mat.add(vmod.parse(e).val);
                    i = s.indexOf(",");
                    if (i >= 0) {
                        s = s.substring(i + 1);
                        continue;
                    } else {
                        continue;
                    }
                }
            } else if (i >= 0) {
                e = s.substring(0, i);
                if (e.trim().length() > 0) {
                    throw new RuntimeException("Error e not empty " + e);
                }
            }
            return new GenMatrix(this, mat);
        } while (i >= 0);
        return new GenMatrix(this, mat);
    }

    public GenMatrix<C> parse(Reader r) {
        return parse(StringUtil.nextPairedString(r, '[', ']'));
    }
}
