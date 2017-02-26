package com.github.mikephil.charting.utils;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.github.mikephil.charting.utils.ObjectPool.Poolable;
import edu.jas.vector.GenVectorModul;
import java.util.List;

public class MPPointF extends Poolable {
    public static final Creator<MPPointF> CREATOR;
    private static ObjectPool<MPPointF> pool;
    public float x;
    public float y;

    static class 1 implements Creator<MPPointF> {
        1() {
        }

        public MPPointF createFromParcel(Parcel in) {
            MPPointF r = new MPPointF(0.0f, null);
            r.my_readFromParcel(in);
            return r;
        }

        public MPPointF[] newArray(int size) {
            return new MPPointF[size];
        }
    }

    static {
        pool = ObjectPool.create(32, new MPPointF(0.0f, 0.0f));
        pool.setReplenishPercentage(GenVectorModul.DEFAULT_DENSITY);
        CREATOR = new 1();
    }

    private MPPointF(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static MPPointF getInstance(float x, float y) {
        MPPointF result = (MPPointF) pool.get();
        result.x = x;
        result.y = y;
        return result;
    }

    public static void recycleInstance(MPPointF instance) {
        pool.recycle((Poolable) instance);
    }

    public static void recycleInstances(List<MPPointF> instances) {
        pool.recycle((List) instances);
    }

    public void my_readFromParcel(Parcel in) {
        this.x = in.readFloat();
        this.y = in.readFloat();
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    protected Poolable instantiate() {
        return new MPPointF(0.0f, 0.0f);
    }
}
