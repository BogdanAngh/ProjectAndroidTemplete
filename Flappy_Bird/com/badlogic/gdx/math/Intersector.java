package com.badlogic.gdx.math;

import com.badlogic.gdx.math.Plane.PlaneSide;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import java.util.Arrays;
import java.util.List;

public final class Intersector {
    static Vector3 best;
    private static final Vector3 dir;
    private static final Vector3 f64i;
    static Vector3 intersection;
    private static final Plane f65p;
    private static final Vector3 start;
    static Vector3 tmp;
    static Vector3 tmp1;
    static Vector3 tmp2;
    static Vector3 tmp3;
    private static final Vector3 v0;
    private static final Vector3 v1;
    private static final Vector3 v2;
    static Vector2 v2tmp;

    public static class MinimumTranslationVector {
        public float depth;
        public Vector2 normal;

        public MinimumTranslationVector() {
            this.normal = new Vector2();
            this.depth = 0.0f;
        }
    }

    public static class SplitTriangle {
        public float[] back;
        int backOffset;
        float[] edgeSplit;
        public float[] front;
        boolean frontCurrent;
        int frontOffset;
        public int numBack;
        public int numFront;
        public int total;

        public SplitTriangle(int numAttributes) {
            this.frontCurrent = false;
            this.frontOffset = 0;
            this.backOffset = 0;
            this.front = new float[((numAttributes * 3) * 2)];
            this.back = new float[((numAttributes * 3) * 2)];
            this.edgeSplit = new float[numAttributes];
        }

        public String toString() {
            return "SplitTriangle [front=" + Arrays.toString(this.front) + ", back=" + Arrays.toString(this.back) + ", numFront=" + this.numFront + ", numBack=" + this.numBack + ", total=" + this.total + "]";
        }

        void setSide(boolean front) {
            this.frontCurrent = front;
        }

        boolean getSide() {
            return this.frontCurrent;
        }

        void add(float[] vertex, int offset, int stride) {
            if (this.frontCurrent) {
                System.arraycopy(vertex, offset, this.front, this.frontOffset, stride);
                this.frontOffset += stride;
                return;
            }
            System.arraycopy(vertex, offset, this.back, this.backOffset, stride);
            this.backOffset += stride;
        }

        void reset() {
            this.frontCurrent = false;
            this.frontOffset = 0;
            this.backOffset = 0;
            this.numFront = 0;
            this.numBack = 0;
            this.total = 0;
        }
    }

    static {
        v0 = new Vector3();
        v1 = new Vector3();
        v2 = new Vector3();
        f65p = new Plane(new Vector3(), 0.0f);
        f64i = new Vector3();
        dir = new Vector3();
        start = new Vector3();
        best = new Vector3();
        tmp = new Vector3();
        tmp1 = new Vector3();
        tmp2 = new Vector3();
        tmp3 = new Vector3();
        v2tmp = new Vector2();
        intersection = new Vector3();
    }

    public static boolean isPointInTriangle(Vector3 point, Vector3 t1, Vector3 t2, Vector3 t3) {
        v0.set(t1).sub(point);
        v1.set(t2).sub(point);
        v2.set(t3).sub(point);
        float ab = v0.dot(v1);
        float ac = v0.dot(v2);
        float bc = v1.dot(v2);
        if ((bc * ac) - (v2.dot(v2) * ab) < 0.0f) {
            return false;
        }
        if ((ab * bc) - (ac * v1.dot(v1)) >= 0.0f) {
            return true;
        }
        return false;
    }

    public static boolean isPointInTriangle(Vector2 p, Vector2 a, Vector2 b, Vector2 c) {
        boolean side12;
        boolean z;
        float px1 = p.f100x - a.f100x;
        float py1 = p.f101y - a.f101y;
        if (((b.f100x - a.f100x) * py1) - ((b.f101y - a.f101y) * px1) > 0.0f) {
            side12 = true;
        } else {
            side12 = false;
        }
        if (((c.f100x - a.f100x) * py1) - ((c.f101y - a.f101y) * px1) > 0.0f) {
            z = true;
        } else {
            z = false;
        }
        if (z == side12) {
            return false;
        }
        if (((c.f100x - b.f100x) * (p.f101y - b.f101y)) - ((c.f101y - b.f101y) * (p.f100x - b.f100x)) > 0.0f) {
            z = true;
        } else {
            z = false;
        }
        if (z == side12) {
            return true;
        }
        return false;
    }

    public static boolean isPointInTriangle(float px, float py, float ax, float ay, float bx, float by, float cx, float cy) {
        float px1 = px - ax;
        float py1 = py - ay;
        boolean side12 = ((bx - ax) * py1) - ((by - ay) * px1) > 0.0f;
        if ((((cx - ax) * py1) - ((cy - ay) * px1) > 0.0f) == side12) {
            return false;
        }
        if ((((cx - bx) * (py - by)) - ((cy - by) * (px - bx)) > 0.0f) != side12) {
            return false;
        }
        return true;
    }

    public static boolean intersectSegmentPlane(Vector3 start, Vector3 end, Plane plane, Vector3 intersection) {
        Vector3 dir = end.tmp().sub(start);
        float t = (-(start.dot(plane.getNormal()) + plane.getD())) / dir.dot(plane.getNormal());
        if (t < 0.0f || t > TextTrackStyle.DEFAULT_FONT_SCALE) {
            return false;
        }
        intersection.set(start).add(dir.scl(t));
        return true;
    }

    public static int pointLineSide(Vector2 linePoint1, Vector2 linePoint2, Vector2 point) {
        return (int) Math.signum(((linePoint2.f100x - linePoint1.f100x) * (point.f101y - linePoint1.f101y)) - ((linePoint2.f101y - linePoint1.f101y) * (point.f100x - linePoint1.f100x)));
    }

    public static int pointLineSide(float linePoint1X, float linePoint1Y, float linePoint2X, float linePoint2Y, float pointX, float pointY) {
        return (int) Math.signum(((linePoint2X - linePoint1X) * (pointY - linePoint1Y)) - ((linePoint2Y - linePoint1Y) * (pointX - linePoint1X)));
    }

    public static boolean isPointInPolygon(Array<Vector2> polygon, Vector2 point) {
        Vector2 lastVertice = (Vector2) polygon.peek();
        boolean oddNodes = false;
        for (int i = 0; i < polygon.size; i++) {
            Vector2 vertice = (Vector2) polygon.get(i);
            if (((vertice.f101y < point.f101y && lastVertice.f101y >= point.f101y) || (lastVertice.f101y < point.f101y && vertice.f101y >= point.f101y)) && vertice.f100x + (((point.f101y - vertice.f101y) / (lastVertice.f101y - vertice.f101y)) * (lastVertice.f100x - vertice.f100x)) < point.f100x) {
                oddNodes = !oddNodes;
            }
            lastVertice = vertice;
        }
        return oddNodes;
    }

    public static boolean isPointInPolygon(float[] polygon, int offset, int count, float x, float y) {
        boolean oddNodes = false;
        int j = (offset + count) - 2;
        int n = j;
        for (int i = offset; i <= n; i += 2) {
            float yi = polygon[i + 1];
            float yj = polygon[j + 1];
            if ((yi < y && yj >= y) || (yj < y && yi >= y)) {
                float xi = polygon[i];
                if ((((y - yi) / (yj - yi)) * (polygon[j] - xi)) + xi < x) {
                    oddNodes = !oddNodes;
                }
            }
            j = i;
        }
        return oddNodes;
    }

    public static float distanceLinePoint(Vector2 start, Vector2 end, Vector2 point) {
        tmp.set(end.f100x, end.f101y, 0.0f);
        float l2 = tmp.sub(start.f100x, start.f101y, 0.0f).len2();
        if (l2 == 0.0f) {
            return point.dst(start);
        }
        tmp.set(point.f100x, point.f101y, 0.0f);
        tmp.sub(start.f100x, start.f101y, 0.0f);
        tmp2.set(end.f100x, end.f101y, 0.0f);
        tmp2.sub(start.f100x, start.f101y, 0.0f);
        float t = tmp.dot(tmp2) / l2;
        if (t < 0.0f) {
            return point.dst(start);
        }
        if (t > TextTrackStyle.DEFAULT_FONT_SCALE) {
            return point.dst(end);
        }
        tmp.set(end.f100x, end.f101y, 0.0f);
        tmp.sub(start.f100x, start.f101y, 0.0f).scl(t).add(start.f100x, start.f101y, 0.0f);
        return tmp2.set(point.f100x, point.f101y, 0.0f).dst(tmp);
    }

    public static float distanceLinePoint(float startX, float startY, float endX, float endY, float pointX, float pointY) {
        return Math.abs(((pointX - startX) * (endY - startY)) - ((pointY - startY) * (endX - startX))) / ((float) Math.sqrt((double) (((endX - startX) * (endX - startX)) + ((endY - startY) * (endY - startY)))));
    }

    public static float distanceSegmentPoint(float startX, float startY, float endX, float endY, float pointX, float pointY) {
        return nearestSegmentPoint(startX, startY, endX, endY, pointX, pointY, v2tmp).dst(pointX, pointY);
    }

    public static float distanceSegmentPoint(Vector2 start, Vector2 end, Vector2 point) {
        return nearestSegmentPoint(start, end, point, v2tmp).dst(point);
    }

    public static Vector2 nearestSegmentPoint(Vector2 start, Vector2 end, Vector2 point, Vector2 nearest) {
        float length2 = start.dst2(end);
        if (length2 == 0.0f) {
            return nearest.set(start);
        }
        float t = (((point.f100x - start.f100x) * (end.f100x - start.f100x)) + ((point.f101y - start.f101y) * (end.f101y - start.f101y))) / length2;
        if (t < 0.0f) {
            return nearest.set(start);
        }
        if (t > TextTrackStyle.DEFAULT_FONT_SCALE) {
            return nearest.set(end);
        }
        return nearest.set(start.f100x + ((end.f100x - start.f100x) * t), start.f101y + ((end.f101y - start.f101y) * t));
    }

    public static Vector2 nearestSegmentPoint(float startX, float startY, float endX, float endY, float pointX, float pointY, Vector2 nearest) {
        float xDiff = endX - startX;
        float yDiff = endY - startY;
        float length2 = (xDiff * xDiff) + (yDiff * yDiff);
        if (length2 == 0.0f) {
            return nearest.set(startX, startY);
        }
        float t = (((pointX - startX) * (endX - startX)) + ((pointY - startY) * (endY - startY))) / length2;
        if (t < 0.0f) {
            return nearest.set(startX, startY);
        }
        if (t > TextTrackStyle.DEFAULT_FONT_SCALE) {
            return nearest.set(endX, endY);
        }
        return nearest.set(((endX - startX) * t) + startX, ((endY - startY) * t) + startY);
    }

    public static boolean intersectSegmentCircle(Vector2 start, Vector2 end, Vector2 center, float squareRadius) {
        tmp.set(end.f100x - start.f100x, end.f101y - start.f101y, 0.0f);
        tmp1.set(center.f100x - start.f100x, center.f101y - start.f101y, 0.0f);
        float l = tmp.len();
        float u = tmp1.dot(tmp.nor());
        if (u <= 0.0f) {
            tmp2.set(start.f100x, start.f101y, 0.0f);
        } else if (u >= l) {
            tmp2.set(end.f100x, end.f101y, 0.0f);
        } else {
            tmp3.set(tmp.scl(u));
            tmp2.set(tmp3.f105x + start.f100x, tmp3.f106y + start.f101y, 0.0f);
        }
        float x = center.f100x - tmp2.f105x;
        float y = center.f101y - tmp2.f106y;
        if ((x * x) + (y * y) <= squareRadius) {
            return true;
        }
        return false;
    }

    public static float intersectSegmentCircleDisplace(Vector2 start, Vector2 end, Vector2 point, float radius, Vector2 displacement) {
        float u = ((point.f100x - start.f100x) * (end.f100x - start.f100x)) + ((point.f101y - start.f101y) * (end.f101y - start.f101y));
        float d = start.dst(end);
        u /= d * d;
        if (u < 0.0f || u > TextTrackStyle.DEFAULT_FONT_SCALE) {
            return Float.POSITIVE_INFINITY;
        }
        tmp.set(end.f100x, end.f101y, 0.0f).sub(start.f100x, start.f101y, 0.0f);
        tmp2.set(start.f100x, start.f101y, 0.0f).add(tmp.scl(u));
        d = tmp2.dst(point.f100x, point.f101y, 0.0f);
        if (d >= radius) {
            return Float.POSITIVE_INFINITY;
        }
        displacement.set(point).sub(tmp2.f105x, tmp2.f106y).nor();
        return d;
    }

    public static boolean intersectRayPlane(Ray ray, Plane plane, Vector3 intersection) {
        float denom = ray.direction.dot(plane.getNormal());
        if (denom != 0.0f) {
            float t = (-(ray.origin.dot(plane.getNormal()) + plane.getD())) / denom;
            if (t < 0.0f) {
                return false;
            }
            if (intersection != null) {
                intersection.set(ray.origin).add(ray.direction.tmp().scl(t));
            }
            return true;
        } else if (plane.testPoint(ray.origin) != PlaneSide.OnPlane) {
            return false;
        } else {
            if (intersection != null) {
                intersection.set(ray.origin);
            }
            return true;
        }
    }

    public static float intersectLinePlane(float x, float y, float z, float x2, float y2, float z2, Plane plane, Vector3 intersection) {
        Vector3 direction = tmp.set(x2, y2, z2).sub(x, y, z);
        Vector3 origin = tmp2.set(x, y, z);
        float denom = direction.dot(plane.getNormal());
        if (denom != 0.0f) {
            float t = (-(origin.dot(plane.getNormal()) + plane.getD())) / denom;
            if (t < 0.0f || t > TextTrackStyle.DEFAULT_FONT_SCALE || intersection == null) {
                return t;
            }
            intersection.set(origin).add(direction.scl(t));
            return t;
        } else if (plane.testPoint(origin) != PlaneSide.OnPlane) {
            return GroundOverlayOptions.NO_DIMENSION;
        } else {
            if (intersection != null) {
                intersection.set(origin);
            }
            return 0.0f;
        }
    }

    public static boolean intersectRayTriangle(Ray ray, Vector3 t1, Vector3 t2, Vector3 t3, Vector3 intersection) {
        f65p.set(t1, t2, t3);
        if (!intersectRayPlane(ray, f65p, f64i)) {
            return false;
        }
        v0.set(t3).sub(t1);
        v1.set(t2).sub(t1);
        v2.set(f64i).sub(t1);
        float dot00 = v0.dot(v0);
        float dot01 = v0.dot(v1);
        float dot02 = v0.dot(v2);
        float dot11 = v1.dot(v1);
        float dot12 = v1.dot(v2);
        float denom = (dot00 * dot11) - (dot01 * dot01);
        if (denom == 0.0f) {
            return false;
        }
        float u = ((dot11 * dot02) - (dot01 * dot12)) / denom;
        float v = ((dot00 * dot12) - (dot01 * dot02)) / denom;
        if (u < 0.0f || v < 0.0f || u + v > TextTrackStyle.DEFAULT_FONT_SCALE) {
            return false;
        }
        if (intersection != null) {
            intersection.set(f64i);
        }
        return true;
    }

    public static boolean intersectRaySphere(Ray ray, Vector3 center, float radius, Vector3 intersection) {
        dir.set(ray.direction).nor();
        start.set(ray.origin);
        float b = 2.0f * dir.dot(start.tmp().sub(center));
        float c = start.dst2(center) - (radius * radius);
        float disc = (b * b) - (4.0f * c);
        if (disc < 0.0f) {
            return false;
        }
        float q;
        float distSqrt = (float) Math.sqrt((double) disc);
        if (b < 0.0f) {
            q = ((-b) - distSqrt) / 2.0f;
        } else {
            q = ((-b) + distSqrt) / 2.0f;
        }
        float t0 = q / TextTrackStyle.DEFAULT_FONT_SCALE;
        float t1 = c / q;
        if (t0 > t1) {
            float temp = t0;
            t0 = t1;
            t1 = temp;
        }
        if (t1 < 0.0f) {
            return false;
        }
        if (t0 < 0.0f) {
            if (intersection != null) {
                intersection.set(start).add(dir.tmp().scl(t1));
            }
            return true;
        }
        if (intersection != null) {
            intersection.set(start).add(dir.tmp().scl(t0));
        }
        return true;
    }

    public static boolean intersectRayBounds(Ray ray, BoundingBox box, Vector3 intersection) {
        Vector3.tmp.set(ray.origin);
        Vector3.tmp2.set(ray.origin);
        Vector3.tmp.sub(box.min);
        Vector3.tmp2.sub(box.max);
        if (Vector3.tmp.f105x > 0.0f && Vector3.tmp.f106y > 0.0f && Vector3.tmp.f107z > 0.0f && Vector3.tmp2.f105x < 0.0f && Vector3.tmp2.f106y < 0.0f && Vector3.tmp2.f107z < 0.0f) {
            return true;
        }
        float t;
        float lowest = 0.0f;
        boolean hit = false;
        if (ray.origin.f105x <= box.min.f105x && ray.direction.f105x > 0.0f) {
            t = (box.min.f105x - ray.origin.f105x) / ray.direction.f105x;
            if (t >= 0.0f) {
                Vector3.tmp3.set(ray.direction).scl(t).add(ray.origin);
                if (Vector3.tmp3.f106y >= box.min.f106y && Vector3.tmp3.f106y <= box.max.f106y && Vector3.tmp3.f107z >= box.min.f107z && Vector3.tmp3.f107z <= box.max.f107z && (null == null || t < 0.0f)) {
                    hit = true;
                    lowest = t;
                }
            }
        }
        if (ray.origin.f105x >= box.max.f105x && ray.direction.f105x < 0.0f) {
            t = (box.max.f105x - ray.origin.f105x) / ray.direction.f105x;
            if (t >= 0.0f) {
                Vector3.tmp3.set(ray.direction).scl(t).add(ray.origin);
                if (Vector3.tmp3.f106y >= box.min.f106y && Vector3.tmp3.f106y <= box.max.f106y && Vector3.tmp3.f107z >= box.min.f107z && Vector3.tmp3.f107z <= box.max.f107z && (!hit || t < lowest)) {
                    hit = true;
                    lowest = t;
                }
            }
        }
        if (ray.origin.f106y <= box.min.f106y && ray.direction.f106y > 0.0f) {
            t = (box.min.f106y - ray.origin.f106y) / ray.direction.f106y;
            if (t >= 0.0f) {
                Vector3.tmp3.set(ray.direction).scl(t).add(ray.origin);
                if (Vector3.tmp3.f105x >= box.min.f105x && Vector3.tmp3.f105x <= box.max.f105x && Vector3.tmp3.f107z >= box.min.f107z && Vector3.tmp3.f107z <= box.max.f107z && (!hit || t < lowest)) {
                    hit = true;
                    lowest = t;
                }
            }
        }
        if (ray.origin.f106y >= box.max.f106y && ray.direction.f106y < 0.0f) {
            t = (box.max.f106y - ray.origin.f106y) / ray.direction.f106y;
            if (t >= 0.0f) {
                Vector3.tmp3.set(ray.direction).scl(t).add(ray.origin);
                if (Vector3.tmp3.f105x >= box.min.f105x && Vector3.tmp3.f105x <= box.max.f105x && Vector3.tmp3.f107z >= box.min.f107z && Vector3.tmp3.f107z <= box.max.f107z && (!hit || t < lowest)) {
                    hit = true;
                    lowest = t;
                }
            }
        }
        if (ray.origin.f107z <= box.min.f106y && ray.direction.f107z > 0.0f) {
            t = (box.min.f107z - ray.origin.f107z) / ray.direction.f107z;
            if (t >= 0.0f) {
                Vector3.tmp3.set(ray.direction).scl(t).add(ray.origin);
                if (Vector3.tmp3.f105x >= box.min.f105x && Vector3.tmp3.f105x <= box.max.f105x && Vector3.tmp3.f106y >= box.min.f106y && Vector3.tmp3.f106y <= box.max.f106y && (!hit || t < lowest)) {
                    hit = true;
                    lowest = t;
                }
            }
        }
        if (ray.origin.f107z >= box.max.f107z && ray.direction.f107z < 0.0f) {
            t = (box.max.f107z - ray.origin.f107z) / ray.direction.f107z;
            if (t >= 0.0f) {
                Vector3.tmp3.set(ray.direction).scl(t).add(ray.origin);
                if (Vector3.tmp3.f105x >= box.min.f105x && Vector3.tmp3.f105x <= box.max.f105x && Vector3.tmp3.f106y >= box.min.f106y && Vector3.tmp3.f106y <= box.max.f106y && (!hit || t < lowest)) {
                    hit = true;
                    lowest = t;
                }
            }
        }
        if (!hit || intersection == null) {
            return hit;
        }
        intersection.set(ray.direction).scl(lowest).add(ray.origin);
        return hit;
    }

    public static boolean intersectRayBoundsFast(Ray ray, BoundingBox box) {
        float min;
        float max;
        float divX = TextTrackStyle.DEFAULT_FONT_SCALE / ray.direction.f105x;
        float divY = TextTrackStyle.DEFAULT_FONT_SCALE / ray.direction.f106y;
        float divZ = TextTrackStyle.DEFAULT_FONT_SCALE / ray.direction.f107z;
        float a = (box.min.f105x - ray.origin.f105x) * divX;
        float b = (box.max.f105x - ray.origin.f105x) * divX;
        if (a < b) {
            min = a;
            max = b;
        } else {
            min = b;
            max = a;
        }
        a = (box.min.f106y - ray.origin.f106y) * divY;
        b = (box.max.f106y - ray.origin.f106y) * divY;
        if (a > b) {
            float t = a;
            a = b;
            b = t;
        }
        if (a > min) {
            min = a;
        }
        if (b < max) {
            max = b;
        }
        a = (box.min.f107z - ray.origin.f107z) * divZ;
        b = (box.max.f107z - ray.origin.f107z) * divZ;
        if (a > b) {
            t = a;
            a = b;
            b = t;
        }
        if (a > min) {
            min = a;
        }
        if (b < max) {
            max = b;
        }
        if (max < 0.0f || max < min) {
            return false;
        }
        return true;
    }

    public static boolean intersectRayTriangles(Ray ray, float[] triangles, Vector3 intersection) {
        float min_dist = Float.MAX_VALUE;
        boolean hit = false;
        if ((triangles.length / 3) % 3 != 0) {
            throw new RuntimeException("triangle list size is not a multiple of 3");
        }
        for (int i = 0; i < triangles.length - 6; i += 9) {
            if (intersectRayTriangle(ray, tmp1.set(triangles[i], triangles[i + 1], triangles[i + 2]), tmp2.set(triangles[i + 3], triangles[i + 4], triangles[i + 5]), tmp3.set(triangles[i + 6], triangles[i + 7], triangles[i + 8]), tmp)) {
                float dist = ray.origin.tmp().sub(tmp).len2();
                if (dist < min_dist) {
                    min_dist = dist;
                    best.set(tmp);
                    hit = true;
                }
            }
        }
        if (!hit) {
            return false;
        }
        if (intersection == null) {
            return true;
        }
        intersection.set(best);
        return true;
    }

    public static boolean intersectRayTriangles(Ray ray, float[] vertices, short[] indices, int vertexSize, Vector3 intersection) {
        float min_dist = Float.MAX_VALUE;
        boolean hit = false;
        if (indices.length % 3 != 0) {
            throw new RuntimeException("triangle list size is not a multiple of 3");
        }
        for (int i = 0; i < indices.length; i += 3) {
            int i1 = indices[i] * vertexSize;
            int i2 = indices[i + 1] * vertexSize;
            int i3 = indices[i + 2] * vertexSize;
            if (intersectRayTriangle(ray, tmp1.set(vertices[i1], vertices[i1 + 1], vertices[i1 + 2]), tmp2.set(vertices[i2], vertices[i2 + 1], vertices[i2 + 2]), tmp3.set(vertices[i3], vertices[i3 + 1], vertices[i3 + 2]), tmp)) {
                float dist = ray.origin.tmp().sub(tmp).len2();
                if (dist < min_dist) {
                    min_dist = dist;
                    best.set(tmp);
                    hit = true;
                }
            }
        }
        if (!hit) {
            return false;
        }
        if (intersection != null) {
            intersection.set(best);
        }
        return true;
    }

    public static boolean intersectRayTriangles(Ray ray, List<Vector3> triangles, Vector3 intersection) {
        float min_dist = Float.MAX_VALUE;
        boolean hit = false;
        if (triangles.size() % 3 != 0) {
            throw new RuntimeException("triangle list size is not a multiple of 3");
        }
        for (int i = 0; i < triangles.size() - 2; i += 3) {
            if (intersectRayTriangle(ray, (Vector3) triangles.get(i), (Vector3) triangles.get(i + 1), (Vector3) triangles.get(i + 2), tmp)) {
                float dist = ray.origin.tmp().sub(tmp).len2();
                if (dist < min_dist) {
                    min_dist = dist;
                    best.set(tmp);
                    hit = true;
                }
            }
        }
        if (!hit) {
            return false;
        }
        if (intersection != null) {
            intersection.set(best);
        }
        return true;
    }

    public static boolean intersectLines(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4, Vector2 intersection) {
        float x1 = p1.f100x;
        float y1 = p1.f101y;
        float x2 = p2.f100x;
        float y2 = p2.f101y;
        float x3 = p3.f100x;
        float y3 = p3.f101y;
        float x4 = p4.f100x;
        float y4 = p4.f101y;
        float d = ((y4 - y3) * (x2 - x1)) - ((x4 - x3) * (y2 - y1));
        if (d == 0.0f) {
            return false;
        }
        float ua = (((x4 - x3) * (y1 - y3)) - ((y4 - y3) * (x1 - x3))) / d;
        if (intersection != null) {
            intersection.set(((x2 - x1) * ua) + x1, ((y2 - y1) * ua) + y1);
        }
        return true;
    }

    public static boolean intersectLines(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, Vector2 intersection) {
        float d = ((y4 - y3) * (x2 - x1)) - ((x4 - x3) * (y2 - y1));
        if (d == 0.0f) {
            return false;
        }
        float ua = (((x4 - x3) * (y1 - y3)) - ((y4 - y3) * (x1 - x3))) / d;
        if (intersection != null) {
            intersection.set(((x2 - x1) * ua) + x1, ((y2 - y1) * ua) + y1);
        }
        return true;
    }

    public static boolean intersectLinePolygon(Vector2 p1, Vector2 p2, Polygon polygon) {
        float[] vertices = polygon.getTransformedVertices();
        float x1 = p1.f100x;
        float y1 = p1.f101y;
        float x2 = p2.f100x;
        float y2 = p2.f101y;
        float width12 = x1 - x2;
        float height12 = y1 - y2;
        float det1 = (x1 * y2) - (y1 * x2);
        int n = vertices.length;
        float x3 = vertices[n - 2];
        float y3 = vertices[n - 1];
        for (int i = 0; i < n; i += 2) {
            float x4 = vertices[i];
            float y4 = vertices[i + 1];
            float det2 = (x3 * y4) - (y3 * x4);
            float width34 = x3 - x4;
            float height34 = y3 - y4;
            float det3 = (width12 * height34) - (height12 * width34);
            float x = ((det1 * width34) - (width12 * det2)) / det3;
            if ((x >= x3 && x <= x4) || (x >= x4 && x <= x3)) {
                float y = ((det1 * height34) - (height12 * det2)) / det3;
                if ((y >= y3 && y <= y4) || (y >= y4 && y <= y3)) {
                    return true;
                }
            }
            x3 = x4;
            y3 = y4;
        }
        return false;
    }

    public static boolean intersectRectangles(Rectangle rectangle1, Rectangle rectangle2, Rectangle intersection) {
        if (!rectangle1.overlaps(rectangle2)) {
            return false;
        }
        intersection.f75x = Math.max(rectangle1.f75x, rectangle2.f75x);
        intersection.width = Math.min(rectangle1.f75x + rectangle1.width, rectangle2.f75x + rectangle2.width) - intersection.f75x;
        intersection.f76y = Math.max(rectangle1.f76y, rectangle2.f76y);
        intersection.height = Math.min(rectangle1.f76y + rectangle1.height, rectangle2.f76y + rectangle2.height) - intersection.f76y;
        return true;
    }

    public static boolean intersectSegmentPolygon(Vector2 p1, Vector2 p2, Polygon polygon) {
        float[] vertices = polygon.getTransformedVertices();
        float x1 = p1.f100x;
        float y1 = p1.f101y;
        float x2 = p2.f100x;
        float y2 = p2.f101y;
        float width12 = x1 - x2;
        float height12 = y1 - y2;
        float det1 = (x1 * y2) - (y1 * x2);
        int n = vertices.length;
        float x3 = vertices[n - 2];
        float y3 = vertices[n - 1];
        for (int i = 0; i < n; i += 2) {
            float x4 = vertices[i];
            float y4 = vertices[i + 1];
            float det2 = (x3 * y4) - (y3 * x4);
            float width34 = x3 - x4;
            float height34 = y3 - y4;
            float det3 = (width12 * height34) - (height12 * width34);
            float x = ((det1 * width34) - (width12 * det2)) / det3;
            if (((x >= x3 && x <= x4) || (x >= x4 && x <= x3)) && ((x >= x1 && x <= x2) || (x >= x2 && x <= x1))) {
                float y = ((det1 * height34) - (height12 * det2)) / det3;
                if (((y >= y3 && y <= y4) || (y >= y4 && y <= y3)) && ((y >= y1 && y <= y2) || (y >= y2 && y <= y1))) {
                    return true;
                }
            }
            x3 = x4;
            y3 = y4;
        }
        return false;
    }

    public static boolean intersectSegments(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4, Vector2 intersection) {
        float x1 = p1.f100x;
        float y1 = p1.f101y;
        float x2 = p2.f100x;
        float y2 = p2.f101y;
        float x3 = p3.f100x;
        float y3 = p3.f101y;
        float x4 = p4.f100x;
        float y4 = p4.f101y;
        float d = ((y4 - y3) * (x2 - x1)) - ((x4 - x3) * (y2 - y1));
        if (d == 0.0f) {
            return false;
        }
        float ua = (((x4 - x3) * (y1 - y3)) - ((y4 - y3) * (x1 - x3))) / d;
        float ub = (((x2 - x1) * (y1 - y3)) - ((y2 - y1) * (x1 - x3))) / d;
        if (ua < 0.0f || ua > TextTrackStyle.DEFAULT_FONT_SCALE) {
            return false;
        }
        if (ub < 0.0f || ub > TextTrackStyle.DEFAULT_FONT_SCALE) {
            return false;
        }
        if (intersection != null) {
            intersection.set(((x2 - x1) * ua) + x1, ((y2 - y1) * ua) + y1);
        }
        return true;
    }

    static float det(float a, float b, float c, float d) {
        return (a * d) - (b * c);
    }

    static double detd(double a, double b, double c, double d) {
        return (a * d) - (b * c);
    }

    public static boolean overlaps(Circle c1, Circle c2) {
        return c1.overlaps(c2);
    }

    public static boolean overlaps(Rectangle r1, Rectangle r2) {
        return r1.overlaps(r2);
    }

    public static boolean overlaps(Circle c, Rectangle r) {
        float closestX = c.f55x;
        float closestY = c.f56y;
        if (c.f55x < r.f75x) {
            closestX = r.f75x;
        } else if (c.f55x > r.f75x + r.width) {
            closestX = r.f75x + r.width;
        }
        if (c.f56y < r.f76y) {
            closestY = r.f76y;
        } else if (c.f56y > r.f76y + r.height) {
            closestY = r.f76y + r.height;
        }
        closestX -= c.f55x;
        closestY -= c.f56y;
        if ((closestX * closestX) + (closestY * closestY) < c.radius * c.radius) {
            return true;
        }
        return false;
    }

    public static boolean overlapConvexPolygons(Polygon p1, Polygon p2) {
        return overlapConvexPolygons(p1, p2, null);
    }

    public static boolean overlapConvexPolygons(Polygon p1, Polygon p2, MinimumTranslationVector mtv) {
        return overlapConvexPolygons(p1.getTransformedVertices(), p2.getTransformedVertices(), mtv);
    }

    public static boolean overlapConvexPolygons(float[] verts1, float[] verts2, MinimumTranslationVector mtv) {
        return overlapConvexPolygons(verts1, 0, verts1.length, verts2, 0, verts2.length, mtv);
    }

    public static boolean overlapConvexPolygons(float[] verts1, int offset1, int count1, float[] verts2, int offset2, int count2, MinimumTranslationVector mtv) {
        int i;
        float mins;
        float maxs;
        float overlap = Float.MAX_VALUE;
        float smallestAxisX = 0.0f;
        float smallestAxisY = 0.0f;
        int end1 = offset1 + count1;
        int end2 = offset2 + count2;
        for (i = offset1; i < end1; i += 2) {
            int j;
            float x1 = verts1[i];
            float y1 = verts1[i + 1];
            float axisX = y1 - verts1[(i + 3) % count1];
            float axisY = -(x1 - verts1[(i + 2) % count1]);
            float length = (float) Math.sqrt((double) ((axisX * axisX) + (axisY * axisY)));
            axisX /= length;
            axisY /= length;
            float min1 = (verts1[0] * axisX) + (verts1[1] * axisY);
            float max1 = min1;
            for (j = offset1; j < end1; j += 2) {
                float p = (verts1[j] * axisX) + (verts1[j + 1] * axisY);
                if (p < min1) {
                    min1 = p;
                } else if (p > max1) {
                    max1 = p;
                }
            }
            float min2 = (verts2[0] * axisX) + (verts2[1] * axisY);
            float max2 = min2;
            for (j = offset2; j < end2; j += 2) {
                p = (verts2[j] * axisX) + (verts2[j + 1] * axisY);
                if (p < min2) {
                    min2 = p;
                } else if (p > max2) {
                    max2 = p;
                }
            }
            if ((min1 > min2 || max1 < min2) && (min2 > min1 || max2 < min1)) {
                return false;
            }
            float o = Math.min(max1, max2) - Math.max(min1, min2);
            if ((min1 < min2 && max1 > max2) || (min2 < min1 && max2 > max1)) {
                mins = Math.abs(min1 - min2);
                maxs = Math.abs(max1 - max2);
                if (mins < maxs) {
                    axisX = -axisX;
                    axisY = -axisY;
                    o += mins;
                } else {
                    o += maxs;
                }
            }
            if (o < overlap) {
                overlap = o;
                smallestAxisX = axisX;
                smallestAxisY = axisY;
            }
        }
        for (i = offset2; i < end2; i += 2) {
            x1 = verts2[i];
            y1 = verts2[i + 1];
            axisX = y1 - verts2[(i + 3) % count2];
            axisY = -(x1 - verts2[(i + 2) % count2]);
            length = (float) Math.sqrt((double) ((axisX * axisX) + (axisY * axisY)));
            axisX /= length;
            axisY /= length;
            min1 = (verts1[0] * axisX) + (verts1[1] * axisY);
            max1 = min1;
            for (j = offset1; j < end1; j += 2) {
                p = (verts1[j] * axisX) + (verts1[j + 1] * axisY);
                if (p < min1) {
                    min1 = p;
                } else if (p > max1) {
                    max1 = p;
                }
            }
            min2 = (verts2[0] * axisX) + (verts2[1] * axisY);
            max2 = min2;
            for (j = offset2; j < end2; j += 2) {
                p = (verts2[j] * axisX) + (verts2[j + 1] * axisY);
                if (p < min2) {
                    min2 = p;
                } else if (p > max2) {
                    max2 = p;
                }
            }
            if ((min1 > min2 || max1 < min2) && (min2 > min1 || max2 < min1)) {
                return false;
            }
            o = Math.min(max1, max2) - Math.max(min1, min2);
            if ((min1 < min2 && max1 > max2) || (min2 < min1 && max2 > max1)) {
                mins = Math.abs(min1 - min2);
                maxs = Math.abs(max1 - max2);
                if (mins < maxs) {
                    axisX = -axisX;
                    axisY = -axisY;
                    o += mins;
                } else {
                    o += maxs;
                }
            }
            if (o < overlap) {
                overlap = o;
                smallestAxisX = axisX;
                smallestAxisY = axisY;
            }
        }
        if (mtv != null) {
            mtv.normal.set(smallestAxisX, smallestAxisY);
            mtv.depth = overlap;
        }
        return true;
    }

    public static void splitTriangle(float[] triangle, Plane plane, SplitTriangle split) {
        boolean r1;
        boolean r2;
        boolean r3;
        boolean z = true;
        int stride = triangle.length / 3;
        if (plane.testPoint(triangle[0], triangle[1], triangle[2]) == PlaneSide.Back) {
            r1 = true;
        } else {
            r1 = false;
        }
        if (plane.testPoint(triangle[stride + 0], triangle[stride + 1], triangle[stride + 2]) == PlaneSide.Back) {
            r2 = true;
        } else {
            r2 = false;
        }
        if (plane.testPoint(triangle[(stride * 2) + 0], triangle[(stride * 2) + 1], triangle[(stride * 2) + 2]) == PlaneSide.Back) {
            r3 = true;
        } else {
            r3 = false;
        }
        split.reset();
        if (r1 == r2 && r2 == r3) {
            split.total = 1;
            if (r1) {
                split.numBack = 1;
                System.arraycopy(triangle, 0, split.back, 0, triangle.length);
                return;
            }
            split.numFront = 1;
            System.arraycopy(triangle, 0, split.front, 0, triangle.length);
            return;
        }
        int i;
        split.total = 3;
        if (r1) {
            i = 1;
        } else {
            i = 0;
        }
        split.numFront = (r3 ? 1 : 0) + (i + (r2 ? 1 : 0));
        split.numBack = split.total - split.numFront;
        split.setSide(r1);
        int second = stride;
        if (r1 != r2) {
            boolean z2;
            splitEdge(triangle, 0, second, stride, plane, split.edgeSplit, 0);
            split.add(triangle, 0, stride);
            split.add(split.edgeSplit, 0, stride);
            if (split.getSide()) {
                z2 = false;
            } else {
                z2 = true;
            }
            split.setSide(z2);
            split.add(split.edgeSplit, 0, stride);
        } else {
            split.add(triangle, 0, stride);
        }
        int first = stride;
        second = stride + stride;
        if (r2 != r3) {
            splitEdge(triangle, first, second, stride, plane, split.edgeSplit, 0);
            split.add(triangle, first, stride);
            split.add(split.edgeSplit, 0, stride);
            split.setSide(!split.getSide());
            split.add(split.edgeSplit, 0, stride);
        } else {
            split.add(triangle, first, stride);
        }
        first = stride + stride;
        if (r3 != r1) {
            splitEdge(triangle, first, 0, stride, plane, split.edgeSplit, 0);
            split.add(triangle, first, stride);
            split.add(split.edgeSplit, 0, stride);
            if (split.getSide()) {
                z = false;
            }
            split.setSide(z);
            split.add(split.edgeSplit, 0, stride);
        } else {
            split.add(triangle, first, stride);
        }
        if (split.numFront == 2) {
            System.arraycopy(split.front, stride * 2, split.front, stride * 3, stride * 2);
            System.arraycopy(split.front, 0, split.front, stride * 5, stride);
            return;
        }
        System.arraycopy(split.back, stride * 2, split.back, stride * 3, stride * 2);
        System.arraycopy(split.back, 0, split.back, stride * 5, stride);
    }

    private static void splitEdge(float[] vertices, int s, int e, int stride, Plane plane, float[] split, int offset) {
        float t = intersectLinePlane(vertices[s], vertices[s + 1], vertices[s + 2], vertices[e], vertices[e + 1], vertices[e + 2], plane, intersection);
        split[offset + 0] = intersection.f105x;
        split[offset + 1] = intersection.f106y;
        split[offset + 2] = intersection.f107z;
        for (int i = 3; i < stride; i++) {
            float a = vertices[s + i];
            split[offset + i] = ((vertices[e + i] - a) * t) + a;
        }
    }

    public static void main(String[] args) {
        Plane plane = new Plane(new Vector3(TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f), 0.0f);
        SplitTriangle split = new SplitTriangle(3);
        splitTriangle(new float[]{-10.0f, 0.0f, 10.0f, GroundOverlayOptions.NO_DIMENSION, 0.0f, 0.0f, -10.0f, 0.0f, 10.0f}, plane, split);
        System.out.println(split);
        splitTriangle(new float[]{-10.0f, 0.0f, 10.0f, 10.0f, 0.0f, 0.0f, -10.0f, 0.0f, -10.0f}, plane, split);
        System.out.println(split);
        Circle c1 = new Circle(0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        Circle c2 = new Circle(0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        Circle c3 = new Circle(2.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        Circle c4 = new Circle(0.0f, 0.0f, 2.0f);
        System.out.println("Circle test cases");
        System.out.println(c1.overlaps(c1));
        System.out.println(c1.overlaps(c2));
        System.out.println(c1.overlaps(c3));
        System.out.println(c1.overlaps(c4));
        System.out.println(c4.overlaps(c1));
        System.out.println(c1.contains(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE));
        System.out.println(c1.contains(0.0f, 2.0f));
        System.out.println(c1.contains(c1));
        System.out.println(c1.contains(c4));
        System.out.println(c4.contains(c1));
        System.out.println("Rectangle test cases");
        Rectangle r1 = new Rectangle(0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        Rectangle r2 = new Rectangle(TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 2.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        System.out.println(r1.overlaps(r1));
        System.out.println(r1.overlaps(r2));
        System.out.println(r1.contains(0.0f, 0.0f));
        System.out.println("BoundingBox test cases");
        BoundingBox b1 = new BoundingBox(Vector3.Zero, new Vector3(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE));
        BoundingBox b2 = new BoundingBox(new Vector3(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE), new Vector3(2.0f, 2.0f, 2.0f));
        System.out.println(b1.contains(Vector3.Zero));
        System.out.println(b1.contains(b1));
        System.out.println(b1.contains(b2));
    }
}
