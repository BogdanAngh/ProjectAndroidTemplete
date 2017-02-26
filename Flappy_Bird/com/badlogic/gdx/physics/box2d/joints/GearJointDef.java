package com.badlogic.gdx.physics.box2d.joints;

import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.google.android.gms.cast.TextTrackStyle;

public class GearJointDef extends JointDef {
    public Joint joint1;
    public Joint joint2;
    public float ratio;

    public GearJointDef() {
        this.joint1 = null;
        this.joint2 = null;
        this.ratio = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.type = JointType.GearJoint;
    }
}
