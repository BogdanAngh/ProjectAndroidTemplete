package org.apache.commons.math4.geometry;

import java.io.Serializable;

public interface Point<S extends Space> extends Serializable {
    double distance(Point<S> point);

    Space getSpace();

    boolean isNaN();
}
