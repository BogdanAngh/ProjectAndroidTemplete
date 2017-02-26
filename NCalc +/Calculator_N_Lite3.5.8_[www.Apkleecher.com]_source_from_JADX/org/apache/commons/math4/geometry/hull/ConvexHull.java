package org.apache.commons.math4.geometry.hull;

import java.io.Serializable;
import org.apache.commons.math4.exception.InsufficientDataException;
import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.geometry.partitioning.Region;

public interface ConvexHull<S extends Space, P extends Point<S>> extends Serializable {
    Region<S> createRegion() throws InsufficientDataException;

    P[] getVertices();
}
