package org.apache.commons.math4.geometry.partitioning;

import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Space;

public interface Embedding<S extends Space, T extends Space> {
    Point<S> toSpace(Point<T> point);

    Point<T> toSubSpace(Point<S> point);
}
