package com.badlogic.gdx.math;

import com.google.android.gms.cast.TextTrackStyle;

public class GeometryUtils {
    private static final Vector2 tmp1;
    private static final Vector2 tmp2;
    private static final Vector2 tmp3;

    static {
        tmp1 = new Vector2();
        tmp2 = new Vector2();
        tmp3 = new Vector2();
    }

    public static Vector2 barycentric(Vector2 p, Vector2 a, Vector2 b, Vector2 c, Vector2 barycentricOut) {
        Vector2 v0 = tmp1.set(b).sub(a);
        Vector2 v1 = tmp2.set(c).sub(a);
        Vector2 v2 = tmp3.set(p).sub(a);
        float d00 = v0.dot(v0);
        float d01 = v0.dot(v1);
        float d11 = v1.dot(v1);
        float d20 = v2.dot(v0);
        float d21 = v2.dot(v1);
        float denom = (d00 * d11) - (d01 * d01);
        barycentricOut.f100x = ((d11 * d20) - (d01 * d21)) / denom;
        barycentricOut.f101y = ((d00 * d21) - (d01 * d20)) / denom;
        return barycentricOut;
    }

    public static float lowestPositiveRoot(float a, float b, float c) {
        float det = (b * b) - ((4.0f * a) * c);
        if (det < 0.0f) {
            return Float.NaN;
        }
        float sqrtD = (float) Math.sqrt((double) det);
        float invA = TextTrackStyle.DEFAULT_FONT_SCALE / (2.0f * a);
        float r1 = ((-b) - sqrtD) * invA;
        float r2 = ((-b) + sqrtD) * invA;
        if (r1 > r2) {
            float tmp = r2;
            r2 = r1;
            r1 = tmp;
        }
        if (r1 <= 0.0f) {
            return r2 > 0.0f ? r2 : Float.NaN;
        } else {
            return r1;
        }
    }

    public static Vector2 triangleCentroid(float x1, float y1, float x2, float y2, float x3, float y3, Vector2 centroid) {
        centroid.f100x = ((x1 + x2) + x3) / 3.0f;
        centroid.f101y = ((y1 + y2) + y3) / 3.0f;
        return centroid;
    }

    public static Vector2 quadrilateralCentroid(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, Vector2 centroid) {
        float avgX1 = ((x1 + x2) + x3) / 3.0f;
        float avgY1 = ((y1 + y2) + y3) / 3.0f;
        float avgY2 = ((y1 + y4) + y3) / 3.0f;
        centroid.f100x = avgX1 - ((avgX1 - (((x1 + x4) + x3) / 3.0f)) / 2.0f);
        centroid.f101y = avgY1 - ((avgY1 - avgY2) / 2.0f);
        return centroid;
    }

    public static Vector2 polygonCentroid(float[] polygon, int offset, int count, Vector2 centroid) {
        if (polygon.length < 6) {
            throw new IllegalArgumentException("A polygon must have 3 or more coordinate pairs.");
        }
        float x0;
        float y0;
        float x1;
        float y1;
        float a;
        float x = 0.0f;
        float y = 0.0f;
        float signedArea = 0.0f;
        int i = offset;
        int n = (offset + count) - 2;
        while (i < n) {
            x0 = polygon[i];
            y0 = polygon[i + 1];
            x1 = polygon[i + 2];
            y1 = polygon[i + 3];
            a = (x0 * y1) - (x1 * y0);
            signedArea += a;
            x += (x0 + x1) * a;
            y += (y0 + y1) * a;
            i += 2;
        }
        x0 = polygon[i];
        y0 = polygon[i + 1];
        x1 = polygon[offset];
        y1 = polygon[offset + 1];
        a = (x0 * y1) - (x1 * y0);
        y += (y0 + y1) * a;
        signedArea = (signedArea + a) * 0.5f;
        centroid.f100x = (x + ((x0 + x1) * a)) / (6.0f * signedArea);
        centroid.f101y = y / (6.0f * signedArea);
        return centroid;
    }
}
