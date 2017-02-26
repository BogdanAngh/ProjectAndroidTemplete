package org.apache.commons.math4.geometry.euclidean.twod.hull;

import java.util.Collection;
import org.apache.commons.math4.exception.ConvergenceException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math4.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math4.geometry.hull.ConvexHullGenerator;

public interface ConvexHullGenerator2D extends ConvexHullGenerator<Euclidean2D, Vector2D> {
    ConvexHull2D generate(Collection<Vector2D> collection) throws NullArgumentException, ConvergenceException;
}
