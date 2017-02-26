package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;

public class ConvexHull {
    private final FloatArray hull;
    private final IntArray quicksortStack;
    private float[] sortedPoints;

    public ConvexHull() {
        this.hull = new FloatArray();
        this.quicksortStack = new IntArray();
    }

    public FloatArray computePolygon(FloatArray points, boolean sorted) {
        return computePolygon(points.items, 0, points.size, sorted);
    }

    public FloatArray computePolygon(float[] polygon, boolean sorted) {
        return computePolygon(polygon, 0, polygon.length, sorted);
    }

    public FloatArray computePolygon(float[] points, int offset, int count, boolean sorted) {
        int i;
        int end = offset + count;
        if (!sorted) {
            if (this.sortedPoints == null || this.sortedPoints.length < count) {
                this.sortedPoints = new float[count];
            }
            System.arraycopy(points, offset, this.sortedPoints, 0, count);
            points = this.sortedPoints;
            offset = 0;
            sort(points, count);
        }
        FloatArray hull = this.hull;
        hull.clear();
        for (i = offset; i < end; i += 2) {
            float x = points[i];
            float y = points[i + 1];
            while (hull.size >= 4 && ccw(x, y) <= 0.0f) {
                hull.size -= 2;
            }
            hull.add(x);
            hull.add(y);
        }
        int t = hull.size + 2;
        for (i = end - 4; i >= offset; i -= 2) {
            x = points[i];
            y = points[i + 1];
            while (hull.size >= t && ccw(x, y) <= 0.0f) {
                hull.size -= 2;
            }
            hull.add(x);
            hull.add(y);
        }
        return hull;
    }

    private float ccw(float p3x, float p3y) {
        FloatArray hull = this.hull;
        int size = hull.size;
        float p1x = hull.get(size - 4);
        float p1y = hull.get(size - 3);
        return ((hull.get(size - 2) - p1x) * (p3y - p1y)) - ((hull.peek() - p1y) * (p3x - p1x));
    }

    private void sort(float[] values, int count) {
        int upper = count - 1;
        IntArray stack = this.quicksortStack;
        stack.add(0);
        stack.add(upper - 1);
        while (stack.size > 0) {
            upper = stack.pop();
            int lower = stack.pop();
            if (upper > lower) {
                int i = quicksortPartition(values, lower, upper);
                if (i - lower > upper - i) {
                    stack.add(lower);
                    stack.add(i - 2);
                }
                stack.add(i + 2);
                stack.add(upper);
                if (upper - i >= i - lower) {
                    stack.add(lower);
                    stack.add(i - 2);
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int quicksortPartition(float[] r8, int r9, int r10) {
        /*
        r7 = this;
        r3 = r8[r9];
        r5 = r9 + 1;
        r4 = r8[r5];
        r2 = r10;
        r0 = r9;
    L_0x0008:
        if (r0 >= r2) goto L_0x0047;
    L_0x000a:
        if (r0 >= r2) goto L_0x0015;
    L_0x000c:
        r5 = r8[r0];
        r5 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1));
        if (r5 > 0) goto L_0x0015;
    L_0x0012:
        r0 = r0 + 2;
        goto L_0x000a;
    L_0x0015:
        r5 = r8[r2];
        r5 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1));
        if (r5 > 0) goto L_0x0029;
    L_0x001b:
        r5 = r8[r2];
        r5 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1));
        if (r5 != 0) goto L_0x002c;
    L_0x0021:
        r5 = r2 + 1;
        r5 = r8[r5];
        r5 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1));
        if (r5 <= 0) goto L_0x002c;
    L_0x0029:
        r2 = r2 + -2;
        goto L_0x0015;
    L_0x002c:
        if (r0 >= r2) goto L_0x0008;
    L_0x002e:
        r1 = r8[r0];
        r5 = r8[r2];
        r8[r0] = r5;
        r8[r2] = r1;
        r5 = r0 + 1;
        r1 = r8[r5];
        r5 = r0 + 1;
        r6 = r2 + 1;
        r6 = r8[r6];
        r8[r5] = r6;
        r5 = r2 + 1;
        r8[r5] = r1;
        goto L_0x0008;
    L_0x0047:
        r5 = r8[r2];
        r8[r9] = r5;
        r8[r2] = r3;
        r5 = r9 + 1;
        r6 = r2 + 1;
        r6 = r8[r6];
        r8[r5] = r6;
        r5 = r2 + 1;
        r8[r5] = r4;
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.math.ConvexHull.quicksortPartition(float[], int, int):int");
    }
}
