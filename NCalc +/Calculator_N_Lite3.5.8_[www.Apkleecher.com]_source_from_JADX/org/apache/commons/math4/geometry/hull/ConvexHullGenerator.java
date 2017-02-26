package org.apache.commons.math4.geometry.hull;

import java.util.Collection;
import org.apache.commons.math4.exception.ConvergenceException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Space;

public interface ConvexHullGenerator<S extends Space, P extends Point<S>> {
    ConvexHull<S, P> generate(Collection<P> collection) throws NullArgumentException, ConvergenceException;
}
