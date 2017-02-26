package com.badlogic.gdx.math;

import com.badlogic.gdx.math.Plane.PlaneSide;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.GroundOverlayOptions;

public class Frustum {
    protected static final Vector3[] clipSpacePlanePoints;
    protected static final float[] clipSpacePlanePointsArray;
    public final Vector3[] planePoints;
    protected final float[] planePointsArray;
    public final Plane[] planes;

    static {
        clipSpacePlanePoints = new Vector3[]{new Vector3(GroundOverlayOptions.NO_DIMENSION, GroundOverlayOptions.NO_DIMENSION, GroundOverlayOptions.NO_DIMENSION), new Vector3(TextTrackStyle.DEFAULT_FONT_SCALE, GroundOverlayOptions.NO_DIMENSION, GroundOverlayOptions.NO_DIMENSION), new Vector3(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, GroundOverlayOptions.NO_DIMENSION), new Vector3(GroundOverlayOptions.NO_DIMENSION, TextTrackStyle.DEFAULT_FONT_SCALE, GroundOverlayOptions.NO_DIMENSION), new Vector3(GroundOverlayOptions.NO_DIMENSION, GroundOverlayOptions.NO_DIMENSION, TextTrackStyle.DEFAULT_FONT_SCALE), new Vector3(TextTrackStyle.DEFAULT_FONT_SCALE, GroundOverlayOptions.NO_DIMENSION, TextTrackStyle.DEFAULT_FONT_SCALE), new Vector3(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE), new Vector3(GroundOverlayOptions.NO_DIMENSION, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE)};
        clipSpacePlanePointsArray = new float[24];
        Vector3[] arr$ = clipSpacePlanePoints;
        int len$ = arr$.length;
        int i$ = 0;
        int j = 0;
        while (i$ < len$) {
            Vector3 v = arr$[i$];
            int i = j + 1;
            clipSpacePlanePointsArray[j] = v.f105x;
            j = i + 1;
            clipSpacePlanePointsArray[i] = v.f106y;
            i = j + 1;
            clipSpacePlanePointsArray[j] = v.f107z;
            i$++;
            j = i;
        }
    }

    public Frustum() {
        this.planes = new Plane[6];
        this.planePoints = new Vector3[]{new Vector3(), new Vector3(), new Vector3(), new Vector3(), new Vector3(), new Vector3(), new Vector3(), new Vector3()};
        this.planePointsArray = new float[24];
        for (int i = 0; i < 6; i++) {
            this.planes[i] = new Plane(new Vector3(), 0.0f);
        }
    }

    public void update(Matrix4 inverseProjectionView) {
        System.arraycopy(clipSpacePlanePointsArray, 0, this.planePointsArray, 0, clipSpacePlanePointsArray.length);
        Matrix4.prj(inverseProjectionView.val, this.planePointsArray, 0, 8, 3);
        int i = 0;
        int j = 0;
        while (i < 8) {
            Vector3 v = this.planePoints[i];
            int j2 = j + 1;
            v.f105x = this.planePointsArray[j];
            j = j2 + 1;
            v.f106y = this.planePointsArray[j2];
            j2 = j + 1;
            v.f107z = this.planePointsArray[j];
            i++;
            j = j2;
        }
        this.planes[0].set(this.planePoints[1], this.planePoints[0], this.planePoints[2]);
        this.planes[1].set(this.planePoints[4], this.planePoints[5], this.planePoints[7]);
        this.planes[2].set(this.planePoints[0], this.planePoints[4], this.planePoints[3]);
        this.planes[3].set(this.planePoints[5], this.planePoints[1], this.planePoints[6]);
        this.planes[4].set(this.planePoints[2], this.planePoints[3], this.planePoints[6]);
        this.planes[5].set(this.planePoints[4], this.planePoints[0], this.planePoints[1]);
    }

    public boolean pointInFrustum(Vector3 point) {
        for (Plane testPoint : this.planes) {
            if (testPoint.testPoint(point) == PlaneSide.Back) {
                return false;
            }
        }
        return true;
    }

    public boolean sphereInFrustum(Vector3 center, float radius) {
        for (int i = 0; i < 6; i++) {
            if (((this.planes[i].normal.f105x * center.f105x) + (this.planes[i].normal.f106y * center.f106y)) + (this.planes[i].normal.f107z * center.f107z) < (-radius) - this.planes[i].f66d) {
                return false;
            }
        }
        return true;
    }

    public boolean sphereInFrustumWithoutNearFar(Vector3 center, float radius) {
        for (int i = 2; i < 6; i++) {
            if (((this.planes[i].normal.f105x * center.f105x) + (this.planes[i].normal.f106y * center.f106y)) + (this.planes[i].normal.f107z * center.f107z) < (-radius) - this.planes[i].f66d) {
                return false;
            }
        }
        return true;
    }

    public boolean boundsInFrustum(BoundingBox bounds) {
        for (Plane testPoint : this.planes) {
            int out = 0;
            for (Vector3 testPoint2 : bounds.getCorners()) {
                if (testPoint.testPoint(testPoint2) == PlaneSide.Back) {
                    out++;
                }
            }
            if (out == 8) {
                return false;
            }
        }
        return true;
    }
}
