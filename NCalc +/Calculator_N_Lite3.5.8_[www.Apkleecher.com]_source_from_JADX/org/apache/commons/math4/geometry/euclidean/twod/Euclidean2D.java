package org.apache.commons.math4.geometry.euclidean.twod;

import java.io.Serializable;
import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.geometry.euclidean.oned.Euclidean1D;

public class Euclidean2D implements Serializable, Space {
    private static final long serialVersionUID = 4793432849757649566L;

    private static class LazyHolder {
        private static final Euclidean2D INSTANCE;

        private LazyHolder() {
        }

        static {
            INSTANCE = new Euclidean2D();
        }
    }

    private Euclidean2D() {
    }

    public static Euclidean2D getInstance() {
        return LazyHolder.INSTANCE;
    }

    public int getDimension() {
        return 2;
    }

    public Euclidean1D getSubSpace() {
        return Euclidean1D.getInstance();
    }

    private Object readResolve() {
        return LazyHolder.INSTANCE;
    }
}
