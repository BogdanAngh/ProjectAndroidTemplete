package edu.jas.ps;

import edu.jas.structure.RingElem;
import java.io.Serializable;
import java.util.HashMap;

public abstract class Coefficients<C extends RingElem<C>> implements Serializable {
    public final HashMap<Integer, C> coeffCache;

    protected abstract C generate(int i);

    public Coefficients() {
        this(new HashMap());
    }

    public Coefficients(HashMap<Integer, C> cache) {
        this.coeffCache = cache;
    }

    public C get(int index) {
        if (this.coeffCache == null) {
            return generate(index);
        }
        Integer i = Integer.valueOf(index);
        RingElem c = (RingElem) this.coeffCache.get(i);
        if (c != null) {
            return c;
        }
        C c2 = generate(index);
        this.coeffCache.put(i, c2);
        return c2;
    }
}
