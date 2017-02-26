package org.apache.commons.math4.geometry.euclidean.twod.hull;

import java.util.Collection;
import org.apache.commons.math4.exception.ConvergenceException;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math4.util.MathUtils;

abstract class AbstractConvexHullGenerator2D implements ConvexHullGenerator2D {
    private static final double DEFAULT_TOLERANCE = 1.0E-10d;
    private final boolean includeCollinearPoints;
    private final double tolerance;

    protected abstract Collection<Vector2D> findHullVertices(Collection<Vector2D> collection);

    protected AbstractConvexHullGenerator2D(boolean includeCollinearPoints) {
        this(includeCollinearPoints, DEFAULT_TOLERANCE);
    }

    protected AbstractConvexHullGenerator2D(boolean includeCollinearPoints, double tolerance) {
        this.includeCollinearPoints = includeCollinearPoints;
        this.tolerance = tolerance;
    }

    public double getTolerance() {
        return this.tolerance;
    }

    public boolean isIncludeCollinearPoints() {
        return this.includeCollinearPoints;
    }

    public ConvexHull2D generate(Collection<Vector2D> points) throws NullArgumentException, ConvergenceException {
        Collection<Vector2D> hullVertices;
        MathUtils.checkNotNull(points);
        if (points.size() < 2) {
            hullVertices = points;
        } else {
            hullVertices = findHullVertices(points);
        }
        try {
            return new ConvexHull2D((Vector2D[]) hullVertices.toArray(new Vector2D[hullVertices.size()]), this.tolerance);
        } catch (MathIllegalArgumentException e) {
            throw new ConvergenceException();
        }
    }
}
