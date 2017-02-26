package org.apache.commons.math4.geometry.partitioning;

import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Space;

public interface Transform<S extends Space, T extends Space> {
    Point<S> apply(Point<S> point);

    Hyperplane<S> apply(Hyperplane<S> hyperplane);

    SubHyperplane<T> apply(SubHyperplane<T> subHyperplane, Hyperplane<S> hyperplane, Hyperplane<S> hyperplane2);
}
