package com.badlogic.gdx.physics.box2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.Shape.Type;
import com.badlogic.gdx.physics.box2d.joints.PulleyJoint;
import com.badlogic.gdx.utils.Array;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.location.GeofenceStatusCodes;
import java.util.Iterator;

public class Box2DDebugRenderer {
    private static Vector2 axis;
    private static final Array<Body> bodies;
    private static final Array<Joint> joints;
    private static final Vector2 lower;
    private static Vector2 f79t;
    private static final Vector2 upper;
    private static final Vector2[] vertices;
    private final Color AABB_COLOR;
    private final Color JOINT_COLOR;
    private final Color SHAPE_AWAKE;
    private final Color SHAPE_KINEMATIC;
    private final Color SHAPE_NOT_ACTIVE;
    private final Color SHAPE_NOT_AWAKE;
    private final Color SHAPE_STATIC;
    private final Color VELOCITY_COLOR;
    private boolean drawAABBs;
    private boolean drawBodies;
    private boolean drawContacts;
    private boolean drawInactiveBodies;
    private boolean drawJoints;
    private boolean drawVelocities;
    private final Vector2 f80f;
    private final Vector2 lv;
    protected ShapeRenderer renderer;
    private final Vector2 f81v;

    static {
        vertices = new Vector2[GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE];
        lower = new Vector2();
        upper = new Vector2();
        bodies = new Array();
        joints = new Array();
        f79t = new Vector2();
        axis = new Vector2();
    }

    public Box2DDebugRenderer() {
        this(true, true, false, true, false, true);
    }

    public Box2DDebugRenderer(boolean drawBodies, boolean drawJoints, boolean drawAABBs, boolean drawInactiveBodies, boolean drawVelocities, boolean drawContacts) {
        this.SHAPE_NOT_ACTIVE = new Color(0.5f, 0.5f, 0.3f, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.SHAPE_STATIC = new Color(0.5f, 0.9f, 0.5f, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.SHAPE_KINEMATIC = new Color(0.5f, 0.5f, 0.9f, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.SHAPE_NOT_AWAKE = new Color(0.6f, 0.6f, 0.6f, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.SHAPE_AWAKE = new Color(0.9f, 0.7f, 0.7f, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.JOINT_COLOR = new Color(0.5f, 0.8f, 0.8f, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.AABB_COLOR = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.VELOCITY_COLOR = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.f80f = new Vector2();
        this.f81v = new Vector2();
        this.lv = new Vector2();
        this.renderer = new ShapeRenderer();
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Vector2();
        }
        this.drawBodies = drawBodies;
        this.drawJoints = drawJoints;
        this.drawAABBs = drawAABBs;
        this.drawInactiveBodies = drawInactiveBodies;
        this.drawVelocities = drawVelocities;
        this.drawContacts = drawContacts;
    }

    public void render(World world, Matrix4 projMatrix) {
        this.renderer.setProjectionMatrix(projMatrix);
        renderBodies(world);
    }

    private void renderBodies(World world) {
        this.renderer.begin(ShapeType.Line);
        if (this.drawBodies || this.drawAABBs) {
            world.getBodies(bodies);
            Iterator<Body> iter = bodies.iterator();
            while (iter.hasNext()) {
                Body body = (Body) iter.next();
                if (body.isActive() || this.drawInactiveBodies) {
                    renderBody(body);
                }
            }
        }
        if (this.drawJoints) {
            world.getJoints(joints);
            Iterator<Joint> iter2 = joints.iterator();
            while (iter2.hasNext()) {
                drawJoint((Joint) iter2.next());
            }
        }
        this.renderer.end();
        if (this.drawContacts) {
            if (Gdx.gl10 != null) {
                Gdx.gl10.glPointSize(3.0f);
            }
            this.renderer.begin(ShapeType.Point);
            int len = world.getContactList().size;
            for (int i = 0; i < len; i++) {
                drawContact((Contact) world.getContactList().get(i));
            }
            this.renderer.end();
            if (Gdx.gl10 != null) {
                Gdx.gl10.glPointSize(TextTrackStyle.DEFAULT_FONT_SCALE);
            }
        }
    }

    protected void renderBody(Body body) {
        Transform transform = body.getTransform();
        Iterator i$ = body.getFixtureList().iterator();
        while (i$.hasNext()) {
            Fixture fixture = (Fixture) i$.next();
            if (this.drawBodies) {
                drawShape(fixture, transform, getColorByBody(body));
                if (this.drawVelocities) {
                    Vector2 position = body.getPosition();
                    drawSegment(position, body.getLinearVelocity().add(position), this.VELOCITY_COLOR);
                }
            }
            if (this.drawAABBs) {
                drawAABB(fixture, transform);
            }
        }
    }

    private Color getColorByBody(Body body) {
        if (!body.isActive()) {
            return this.SHAPE_NOT_ACTIVE;
        }
        if (body.getType() == BodyType.StaticBody) {
            return this.SHAPE_STATIC;
        }
        if (body.getType() == BodyType.KinematicBody) {
            return this.SHAPE_KINEMATIC;
        }
        if (body.isAwake()) {
            return this.SHAPE_AWAKE;
        }
        return this.SHAPE_NOT_AWAKE;
    }

    private void drawAABB(Fixture fixture, Transform transform) {
        if (fixture.getType() == Type.Circle) {
            CircleShape shape = (CircleShape) fixture.getShape();
            float radius = shape.getRadius();
            vertices[0].set(shape.getPosition());
            vertices[0].rotate(transform.getRotation()).add(transform.getPosition());
            lower.set(vertices[0].f100x - radius, vertices[0].f101y - radius);
            upper.set(vertices[0].f100x + radius, vertices[0].f101y + radius);
            vertices[0].set(lower.f100x, lower.f101y);
            vertices[1].set(upper.f100x, lower.f101y);
            vertices[2].set(upper.f100x, upper.f101y);
            vertices[3].set(lower.f100x, upper.f101y);
            drawSolidPolygon(vertices, 4, this.AABB_COLOR, true);
        } else if (fixture.getType() == Type.Polygon) {
            PolygonShape shape2 = (PolygonShape) fixture.getShape();
            int vertexCount = shape2.getVertexCount();
            shape2.getVertex(0, vertices[0]);
            lower.set(transform.mul(vertices[0]));
            upper.set(lower);
            for (int i = 1; i < vertexCount; i++) {
                shape2.getVertex(i, vertices[i]);
                transform.mul(vertices[i]);
                lower.f100x = Math.min(lower.f100x, vertices[i].f100x);
                lower.f101y = Math.min(lower.f101y, vertices[i].f101y);
                upper.f100x = Math.max(upper.f100x, vertices[i].f100x);
                upper.f101y = Math.max(upper.f101y, vertices[i].f101y);
            }
            vertices[0].set(lower.f100x, lower.f101y);
            vertices[1].set(upper.f100x, lower.f101y);
            vertices[2].set(upper.f100x, upper.f101y);
            vertices[3].set(lower.f100x, upper.f101y);
            drawSolidPolygon(vertices, 4, this.AABB_COLOR, true);
        }
    }

    private void drawShape(Fixture fixture, Transform transform, Color color) {
        if (fixture.getType() == Type.Circle) {
            CircleShape circle = (CircleShape) fixture.getShape();
            f79t.set(circle.getPosition());
            transform.mul(f79t);
            drawSolidCircle(f79t, circle.getRadius(), axis.set(transform.vals[2], transform.vals[3]), color);
        } else if (fixture.getType() == Type.Edge) {
            EdgeShape edge = (EdgeShape) fixture.getShape();
            edge.getVertex1(vertices[0]);
            edge.getVertex2(vertices[1]);
            transform.mul(vertices[0]);
            transform.mul(vertices[1]);
            drawSolidPolygon(vertices, 2, color, true);
        } else if (fixture.getType() == Type.Polygon) {
            PolygonShape chain = (PolygonShape) fixture.getShape();
            vertexCount = chain.getVertexCount();
            for (i = 0; i < vertexCount; i++) {
                chain.getVertex(i, vertices[i]);
                transform.mul(vertices[i]);
            }
            drawSolidPolygon(vertices, vertexCount, color, true);
        } else if (fixture.getType() == Type.Chain) {
            ChainShape chain2 = (ChainShape) fixture.getShape();
            vertexCount = chain2.getVertexCount();
            for (i = 0; i < vertexCount; i++) {
                chain2.getVertex(i, vertices[i]);
                transform.mul(vertices[i]);
            }
            drawSolidPolygon(vertices, vertexCount, color, false);
        }
    }

    private void drawSolidCircle(Vector2 center, float radius, Vector2 axis, Color color) {
        float angle = 0.0f;
        this.renderer.setColor(color.f40r, color.f39g, color.f38b, color.f37a);
        int i = 0;
        while (i < 20) {
            this.f81v.set((((float) Math.cos((double) angle)) * radius) + center.f100x, (((float) Math.sin((double) angle)) * radius) + center.f101y);
            if (i == 0) {
                this.lv.set(this.f81v);
                this.f80f.set(this.f81v);
            } else {
                this.renderer.line(this.lv.f100x, this.lv.f101y, this.f81v.f100x, this.f81v.f101y);
                this.lv.set(this.f81v);
            }
            i++;
            angle += 0.31415927f;
        }
        this.renderer.line(this.f80f.f100x, this.f80f.f101y, this.lv.f100x, this.lv.f101y);
        this.renderer.line(center.f100x, center.f101y, 0.0f, center.f100x + (axis.f100x * radius), center.f101y + (axis.f101y * radius), 0.0f);
    }

    private void drawSolidPolygon(Vector2[] vertices, int vertexCount, Color color, boolean closed) {
        this.renderer.setColor(color.f40r, color.f39g, color.f38b, color.f37a);
        this.lv.set(vertices[0]);
        this.f80f.set(vertices[0]);
        for (int i = 1; i < vertexCount; i++) {
            Vector2 v = vertices[i];
            this.renderer.line(this.lv.f100x, this.lv.f101y, v.f100x, v.f101y);
            this.lv.set(v);
        }
        if (closed) {
            this.renderer.line(this.f80f.f100x, this.f80f.f101y, this.lv.f100x, this.lv.f101y);
        }
    }

    private void drawJoint(Joint joint) {
        Body bodyA = joint.getBodyA();
        Body bodyB = joint.getBodyB();
        Transform xf1 = bodyA.getTransform();
        Transform xf2 = bodyB.getTransform();
        Vector2 x1 = xf1.getPosition();
        Vector2 x2 = xf2.getPosition();
        Vector2 p1 = joint.getAnchorA();
        Vector2 p2 = joint.getAnchorB();
        if (joint.getType() == JointType.DistanceJoint) {
            drawSegment(p1, p2, this.JOINT_COLOR);
        } else if (joint.getType() == JointType.PulleyJoint) {
            PulleyJoint pulley = (PulleyJoint) joint;
            Vector2 s1 = pulley.getGroundAnchorA();
            Vector2 s2 = pulley.getGroundAnchorB();
            drawSegment(s1, p1, this.JOINT_COLOR);
            drawSegment(s2, p2, this.JOINT_COLOR);
            drawSegment(s1, s2, this.JOINT_COLOR);
        } else if (joint.getType() == JointType.MouseJoint) {
            drawSegment(joint.getAnchorA(), joint.getAnchorB(), this.JOINT_COLOR);
        } else {
            drawSegment(x1, p1, this.JOINT_COLOR);
            drawSegment(p1, p2, this.JOINT_COLOR);
            drawSegment(x2, p2, this.JOINT_COLOR);
        }
    }

    private void drawSegment(Vector2 x1, Vector2 x2, Color color) {
        this.renderer.setColor(color);
        this.renderer.line(x1.f100x, x1.f101y, x2.f100x, x2.f101y);
    }

    private void drawContact(Contact contact) {
        WorldManifold worldManifold = contact.getWorldManifold();
        if (worldManifold.getNumberOfContactPoints() != 0) {
            Vector2 point = worldManifold.getPoints()[0];
            this.renderer.setColor(getColorByBody(contact.getFixtureA().getBody()));
            this.renderer.point(point.f100x, point.f101y, 0.0f);
        }
    }

    public boolean isDrawBodies() {
        return this.drawBodies;
    }

    public void setDrawBodies(boolean drawBodies) {
        this.drawBodies = drawBodies;
    }

    public boolean isDrawJoints() {
        return this.drawJoints;
    }

    public void setDrawJoints(boolean drawJoints) {
        this.drawJoints = drawJoints;
    }

    public boolean isDrawAABBs() {
        return this.drawAABBs;
    }

    public void setDrawAABBs(boolean drawAABBs) {
        this.drawAABBs = drawAABBs;
    }

    public boolean isDrawInactiveBodies() {
        return this.drawInactiveBodies;
    }

    public void setDrawInactiveBodies(boolean drawInactiveBodies) {
        this.drawInactiveBodies = drawInactiveBodies;
    }

    public boolean isDrawVelocities() {
        return this.drawVelocities;
    }

    public void setDrawVelocities(boolean drawVelocities) {
        this.drawVelocities = drawVelocities;
    }

    public boolean isDrawContacts() {
        return this.drawContacts;
    }

    public void setDrawContacts(boolean drawContacts) {
        this.drawContacts = drawContacts;
    }

    public static Vector2 getAxis() {
        return axis;
    }

    public static void setAxis(Vector2 axis) {
        axis = axis;
    }

    public void dispose() {
        this.renderer.dispose();
    }
}
