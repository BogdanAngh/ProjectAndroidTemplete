package com.badlogic.gdx.physics.box2d;

import com.badlogic.gdx.math.Vector2;
import com.google.android.gms.cast.TextTrackStyle;

public class BodyDef {
    public boolean active;
    public boolean allowSleep;
    public float angle;
    public float angularDamping;
    public float angularVelocity;
    public boolean awake;
    public boolean bullet;
    public boolean fixedRotation;
    public float gravityScale;
    public float linearDamping;
    public final Vector2 linearVelocity;
    public final Vector2 position;
    public BodyType type;

    public enum BodyType {
        StaticBody(0),
        KinematicBody(1),
        DynamicBody(2);
        
        private int value;

        private BodyType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }

    public BodyDef() {
        this.type = BodyType.StaticBody;
        this.position = new Vector2();
        this.angle = 0.0f;
        this.linearVelocity = new Vector2();
        this.angularVelocity = 0.0f;
        this.linearDamping = 0.0f;
        this.angularDamping = 0.0f;
        this.allowSleep = true;
        this.awake = true;
        this.fixedRotation = false;
        this.bullet = false;
        this.active = true;
        this.gravityScale = TextTrackStyle.DEFAULT_FONT_SCALE;
    }
}
