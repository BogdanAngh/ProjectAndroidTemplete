package org.apache.commons.math4.geometry.euclidean.twod.hull;

import java.io.Serializable;
import org.apache.commons.math4.exception.InsufficientDataException;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.geometry.Vector;
import org.apache.commons.math4.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math4.geometry.euclidean.twod.Line;
import org.apache.commons.math4.geometry.euclidean.twod.Segment;
import org.apache.commons.math4.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math4.geometry.hull.ConvexHull;
import org.apache.commons.math4.geometry.partitioning.Region;
import org.apache.commons.math4.geometry.partitioning.RegionFactory;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.Precision;

public class ConvexHull2D implements ConvexHull<Euclidean2D, Vector2D>, Serializable {
    private static final long serialVersionUID = 20140129;
    private transient Segment[] lineSegments;
    private final double tolerance;
    private final Vector2D[] vertices;

    public ConvexHull2D(Vector2D[] vertices, double tolerance) throws MathIllegalArgumentException {
        this.tolerance = tolerance;
        if (isConvex(vertices)) {
            this.vertices = (Vector2D[]) vertices.clone();
            return;
        }
        throw new MathIllegalArgumentException(LocalizedFormats.NOT_CONVEX, new Object[0]);
    }

    private boolean isConvex(Vector2D[] hullVertices) {
        if (hullVertices.length < 3) {
            return true;
        }
        int sign = 0;
        int i = 0;
        while (i < hullVertices.length) {
            Vector p1 = hullVertices[i == 0 ? hullVertices.length - 1 : i - 1];
            Vector p2 = hullVertices[i];
            Vector2D p3 = hullVertices[i == hullVertices.length + -1 ? 0 : i + 1];
            Vector2D d1 = p2.subtract(p1);
            Vector2D d2 = p3.subtract(p2);
            int cmp = Precision.compareTo(MathArrays.linearCombination(d1.getX(), d2.getY(), -d1.getY(), d2.getX()), 0.0d, this.tolerance);
            if (((double) cmp) != 0.0d) {
                if (((double) sign) != 0.0d && cmp != sign) {
                    return false;
                }
                sign = cmp;
            }
            i++;
        }
        return true;
    }

    public Vector2D[] getVertices() {
        return (Vector2D[]) this.vertices.clone();
    }

    public Segment[] getLineSegments() {
        return (Segment[]) retrieveLineSegments().clone();
    }

    private Segment[] retrieveLineSegments() {
        if (this.lineSegments == null) {
            int size = this.vertices.length;
            if (size <= 1) {
                this.lineSegments = new Segment[0];
            } else if (size == 2) {
                this.lineSegments = new Segment[1];
                Vector2D p1 = this.vertices[0];
                Vector2D p2 = this.vertices[1];
                this.lineSegments[0] = new Segment(p1, p2, new Line(p1, p2, this.tolerance));
            } else {
                this.lineSegments = new Segment[size];
                Vector2D firstPoint = null;
                Vector2D lastPoint = null;
                Vector2D[] vector2DArr = this.vertices;
                int length = vector2DArr.length;
                int i = 0;
                int index = 0;
                while (i < length) {
                    int index2;
                    Vector2D point = vector2DArr[i];
                    if (lastPoint == null) {
                        firstPoint = point;
                        lastPoint = point;
                        index2 = index;
                    } else {
                        index2 = index + 1;
                        this.lineSegments[index] = new Segment(lastPoint, point, new Line(lastPoint, point, this.tolerance));
                        lastPoint = point;
                    }
                    i++;
                    index = index2;
                }
                this.lineSegments[index] = new Segment(lastPoint, firstPoint, new Line(lastPoint, firstPoint, this.tolerance));
            }
        }
        return this.lineSegments;
    }

    public Region<Euclidean2D> createRegion() throws InsufficientDataException {
        if (this.vertices.length < 3) {
            throw new InsufficientDataException();
        }
        RegionFactory<Euclidean2D> factory = new RegionFactory();
        Segment[] segments = retrieveLineSegments();
        Line[] lineArray = new Line[segments.length];
        for (int i = 0; i < segments.length; i++) {
            lineArray[i] = segments[i].getLine();
        }
        return factory.buildConvex(lineArray);
    }
}
