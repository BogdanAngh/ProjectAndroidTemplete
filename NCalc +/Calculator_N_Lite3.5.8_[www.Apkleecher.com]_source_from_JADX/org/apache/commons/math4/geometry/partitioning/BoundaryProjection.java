package org.apache.commons.math4.geometry.partitioning;

import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Space;

public class BoundaryProjection<S extends Space> {
    private final double offset;
    private final Point<S> original;
    private final Point<S> projected;

    public BoundaryProjection(Point<S> original, Point<S> projected, double offset) {
        this.original = original;
        this.projected = projected;
        this.offset = offset;
    }

    public Point<S> getOriginal() {
        return this.original;
    }

    public Point<S> getProjected() {
        return this.projected;
    }

    public double getOffset() {
        return this.offset;
    }
}
