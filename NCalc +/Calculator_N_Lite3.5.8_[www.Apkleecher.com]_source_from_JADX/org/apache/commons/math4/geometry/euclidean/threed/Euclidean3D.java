package org.apache.commons.math4.geometry.euclidean.threed;

import java.io.Serializable;
import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.geometry.euclidean.twod.Euclidean2D;

public class Euclidean3D implements Serializable, Space {
    private static final long serialVersionUID = 6249091865814886817L;

    private static class LazyHolder {
        private static final Euclidean3D INSTANCE;

        private LazyHolder() {
        }

        static {
            INSTANCE = new Euclidean3D();
        }
    }

    private Euclidean3D() {
    }

    public static Euclidean3D getInstance() {
        return LazyHolder.INSTANCE;
    }

    public int getDimension() {
        return 3;
    }

    public Euclidean2D getSubSpace() {
        return Euclidean2D.getInstance();
    }

    private Object readResolve() {
        return LazyHolder.INSTANCE;
    }
}