package com.github.mikephil.charting.utils;

import com.github.mikephil.charting.utils.ObjectPool.Poolable;
import edu.jas.ps.UnivPowerSeriesRing;
import edu.jas.vector.GenVectorModul;
import java.util.List;
import org.matheclipse.core.interfaces.IExpr;

public final class FSize extends Poolable {
    private static ObjectPool<FSize> pool;
    public float height;
    public float width;

    static {
        pool = ObjectPool.create(IExpr.BLANKID, new FSize(0.0f, 0.0f));
        pool.setReplenishPercentage(GenVectorModul.DEFAULT_DENSITY);
    }

    protected Poolable instantiate() {
        return new FSize(0.0f, 0.0f);
    }

    public static FSize getInstance(float width, float height) {
        FSize result = (FSize) pool.get();
        result.width = width;
        result.height = height;
        return result;
    }

    public static void recycleInstance(FSize instance) {
        pool.recycle((Poolable) instance);
    }

    public static void recycleInstances(List<FSize> instances) {
        pool.recycle((List) instances);
    }

    private FSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FSize)) {
            return false;
        }
        FSize other = (FSize) obj;
        if (!(this.width == other.width && this.height == other.height)) {
            z = false;
        }
        return z;
    }

    public String toString() {
        return this.width + UnivPowerSeriesRing.DEFAULT_NAME + this.height;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.width) ^ Float.floatToIntBits(this.height);
    }
}
