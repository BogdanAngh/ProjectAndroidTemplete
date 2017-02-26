package com.github.mikephil.charting.utils;

import com.github.mikephil.charting.utils.ObjectPool.Poolable;
import edu.jas.vector.GenVectorModul;
import java.util.List;

public class MPPointD extends Poolable {
    private static ObjectPool<MPPointD> pool;
    public double x;
    public double y;

    static {
        pool = ObjectPool.create(64, new MPPointD(0.0d, 0.0d));
        pool.setReplenishPercentage(GenVectorModul.DEFAULT_DENSITY);
    }

    public static MPPointD getInstance(double x, double y) {
        MPPointD result = (MPPointD) pool.get();
        result.x = x;
        result.y = y;
        return result;
    }

    public static void recycleInstance(MPPointD instance) {
        pool.recycle((Poolable) instance);
    }

    public static void recycleInstances(List<MPPointD> instances) {
        pool.recycle((List) instances);
    }

    protected Poolable instantiate() {
        return new MPPointD(0.0d, 0.0d);
    }

    private MPPointD(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "MPPointD, x: " + this.x + ", y: " + this.y;
    }
}
