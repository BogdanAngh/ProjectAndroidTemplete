package org.apache.commons.math4.geometry.spherical.twod;

import java.io.Serializable;
import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.geometry.spherical.oned.Sphere1D;

public class Sphere2D implements Serializable, Space {
    private static final long serialVersionUID = 20131218;

    private static class LazyHolder {
        private static final Sphere2D INSTANCE;

        private LazyHolder() {
        }

        static {
            INSTANCE = new Sphere2D();
        }
    }

    private Sphere2D() {
    }

    public static Sphere2D getInstance() {
        return LazyHolder.INSTANCE;
    }

    public int getDimension() {
        return 2;
    }

    public Sphere1D getSubSpace() {
        return Sphere1D.getInstance();
    }

    private Object readResolve() {
        return LazyHolder.INSTANCE;
    }
}
