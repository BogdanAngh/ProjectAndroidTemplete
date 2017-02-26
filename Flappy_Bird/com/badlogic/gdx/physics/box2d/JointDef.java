package com.badlogic.gdx.physics.box2d;

public class JointDef {
    public Body bodyA;
    public Body bodyB;
    public boolean collideConnected;
    public JointType type;

    public enum JointType {
        Unknown(0),
        RevoluteJoint(1),
        PrismaticJoint(2),
        DistanceJoint(3),
        PulleyJoint(4),
        MouseJoint(5),
        GearJoint(6),
        WheelJoint(7),
        WeldJoint(8),
        FrictionJoint(9),
        RopeJoint(10);
        
        public static JointType[] valueTypes;
        private int value;

        static {
            valueTypes = new JointType[]{Unknown, RevoluteJoint, PrismaticJoint, DistanceJoint, PulleyJoint, MouseJoint, GearJoint, WheelJoint, WeldJoint, FrictionJoint, RopeJoint};
        }

        private JointType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public JointDef() {
        this.type = JointType.Unknown;
        this.bodyA = null;
        this.bodyB = null;
        this.collideConnected = false;
    }
}
