package com.badlogic.gdx.physics.box2d.joints;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.GroundOverlayOptions;

public class RopeJointDef extends JointDef {
    public final Vector2 localAnchorA;
    public final Vector2 localAnchorB;
    public float maxLength;

    public RopeJointDef() {
        this.localAnchorA = new Vector2(GroundOverlayOptions.NO_DIMENSION, 0.0f);
        this.localAnchorB = new Vector2(TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f);
        this.maxLength = 0.0f;
        this.type = JointType.RopeJoint;
    }
}
