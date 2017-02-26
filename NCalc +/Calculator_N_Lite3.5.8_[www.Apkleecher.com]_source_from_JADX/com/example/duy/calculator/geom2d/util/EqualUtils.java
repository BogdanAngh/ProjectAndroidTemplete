package com.example.duy.calculator.geom2d.util;

import org.apache.commons.math4.linear.OpenMapRealVector;

public final class EqualUtils {
    public static boolean areEqual(boolean aThis, boolean aThat) {
        return aThis == aThat;
    }

    public static boolean areEqual(char aThis, char aThat) {
        return aThis == aThat;
    }

    public static boolean areEqual(long aThis, long aThat) {
        return aThis == aThat;
    }

    public static boolean areEqual(float aThis, float aThat) {
        return Float.floatToIntBits(aThis) == Float.floatToIntBits(aThat);
    }

    public static boolean areEqual(double aThis, double aThat) {
        return Double.doubleToLongBits(aThis) == Double.doubleToLongBits(aThat);
    }

    public static boolean areEqual(Object aThis, Object aThat) {
        if (aThis == null) {
            return aThat == null;
        } else {
            return aThis.equals(aThat);
        }
    }

    public static boolean isLE(double aThis, double aThat) {
        if (aThis >= aThat && Math.abs(aThat - aThis) > OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return false;
        }
        return true;
    }

    public static boolean isGE(double aThis, double aThat) {
        if (aThis <= aThat && Math.abs(aThat - aThis) > OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return false;
        }
        return true;
    }
}
