package org.apache.commons.math4.geometry.partitioning;

import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Space;

public interface Hyperplane<S extends Space> {
    Hyperplane<S> copySelf();

    double getOffset(Point<S> point);

    double getTolerance();

    Point<S> project(Point<S> point);

    boolean sameOrientationAs(Hyperplane<S> hyperplane);

    SubHyperplane<S> wholeHyperplane();

    Region<S> wholeSpace();
}
