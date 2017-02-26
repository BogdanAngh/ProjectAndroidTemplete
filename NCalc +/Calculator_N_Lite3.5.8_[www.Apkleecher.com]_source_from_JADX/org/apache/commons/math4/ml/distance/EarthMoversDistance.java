package org.apache.commons.math4.ml.distance;

import org.apache.commons.math4.util.FastMath;

public class EarthMoversDistance implements DistanceMeasure {
    private static final long serialVersionUID = -5406732779747414922L;

    public double compute(double[] a, double[] b) {
        double lastDistance = 0.0d;
        double totalDistance = 0.0d;
        for (int i = 0; i < a.length; i++) {
            double currentDistance = (a[i] + lastDistance) - b[i];
            totalDistance += FastMath.abs(currentDistance);
            lastDistance = currentDistance;
        }
        return totalDistance;
    }
}
