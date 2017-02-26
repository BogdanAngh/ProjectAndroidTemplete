package com.example.duy.calculator.geom2d.polygon;

import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.line.LineSegment2D;
import java.util.ArrayList;
import java.util.Collection;

public abstract class Polylines2D {
    static ArrayList<Point2D> simplifyPolyline(ArrayList<Point2D> vertices, double distMax) {
        int i = 0;
        int last = vertices.size() - 1;
        int[] inds = recurseSimplify(vertices, 0, last, distMax);
        ArrayList<Point2D> newVerts = new ArrayList(inds.length + 2);
        newVerts.add(vertices.get(0));
        int length = inds.length;
        while (i < length) {
            newVerts.add(vertices.get(inds[i]));
            i++;
        }
        newVerts.add(vertices.get(last));
        return newVerts;
    }

    static ArrayList<Point2D> simplifyClosedPolyline(ArrayList<Point2D> vertices, double distMax) {
        int nv = vertices.size();
        int indMax = 0;
        double maxDist = 0.0d;
        Point2D p0 = (Point2D) vertices.get(0);
        for (int i = 1; i < vertices.size(); i++) {
            double dist = ((Point2D) vertices.get(i)).distance(p0);
            if (dist > maxDist) {
                maxDist = dist;
                indMax = i;
            }
        }
        int[] inds = concatInds(recurseSimplify(vertices, 0, indMax, distMax), indMax, recurseSimplify(vertices, indMax, nv, distMax));
        ArrayList<Point2D> newVerts = new ArrayList(inds.length + 2);
        newVerts.add(vertices.get(0));
        for (int i2 : inds) {
            newVerts.add(vertices.get(i2));
        }
        return newVerts;
    }

    private static int[] recurseSimplify(ArrayList<Point2D> vertices, int first, int last, double distMax) {
        if (last - first < 2) {
            return new int[0];
        }
        LineSegment2D line = new LineSegment2D((Point2D) vertices.get(first), (Point2D) vertices.get(last % vertices.size()));
        double midDist = 0.0d;
        int indMid = 0;
        for (int ind = first; ind < last; ind++) {
            double dist = line.distance((Point2D) vertices.get(ind));
            if (dist > midDist) {
                midDist = dist;
                indMid = ind;
            }
        }
        if (midDist < distMax) {
            return new int[0];
        }
        return concatInds(recurseSimplify(vertices, first, indMid, distMax), indMid, recurseSimplify(vertices, indMid, last, distMax));
    }

    private static int[] concatInds(int[] inds1, int indMid, int[] inds2) {
        int i;
        int n = (inds1.length + inds2.length) + 1;
        int[] res = new int[n];
        for (i = 0; i < inds1.length; i++) {
            res[i] = inds1[i];
        }
        res[inds1.length] = indMid;
        for (i = inds1.length + 1; i < n; i++) {
            res[i] = inds2[i - (inds1.length + 1)];
        }
        return res;
    }

    public static Collection<Point2D> intersect(LinearCurve2D poly1, LinearCurve2D poly2) {
        ArrayList<Point2D> points = new ArrayList();
        for (LineSegment2D edge1 : poly1.edges()) {
            for (LineSegment2D edge2 : poly2.edges()) {
                Point2D point = edge1.intersection(edge2);
                if (!(point == null || points.contains(point))) {
                    points.add(point);
                }
            }
        }
        return points;
    }
}
