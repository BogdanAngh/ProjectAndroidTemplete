package com.badlogic.gdx.physics.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.FrictionJoint;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.badlogic.gdx.physics.box2d.joints.GearJoint;
import com.badlogic.gdx.physics.box2d.joints.GearJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.PulleyJoint;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.LongMap;
import com.badlogic.gdx.utils.Pool;
import java.util.Iterator;

public final class World implements Disposable {
    private final long addr;
    protected final LongMap<Body> bodies;
    private final Contact contact;
    private long[] contactAddrs;
    protected ContactFilter contactFilter;
    protected ContactListener contactListener;
    private final Array<Contact> contacts;
    protected final LongMap<Fixture> fixtures;
    protected final Pool<Body> freeBodies;
    private final Array<Contact> freeContacts;
    protected final Pool<Fixture> freeFixtures;
    final Vector2 gravity;
    private final ContactImpulse impulse;
    protected final LongMap<Joint> joints;
    private final Manifold manifold;
    private QueryCallback queryCallback;
    private RayCastCallback rayCastCallback;
    private Vector2 rayNormal;
    private Vector2 rayPoint;
    final float[] tmpGravity;

    /* renamed from: com.badlogic.gdx.physics.box2d.World.1 */
    class C03751 extends Pool<Body> {
        C03751(int x0, int x1) {
            super(x0, x1);
        }

        protected Body newObject() {
            return new Body(World.this, 0);
        }
    }

    /* renamed from: com.badlogic.gdx.physics.box2d.World.2 */
    class C03762 extends Pool<Fixture> {
        C03762(int x0, int x1) {
            super(x0, x1);
        }

        protected Fixture newObject() {
            return new Fixture(null, 0);
        }
    }

    public static native float getVelocityThreshold();

    private native void jniClearForces(long j);

    private native long jniCreateBody(long j, int i, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, float f9);

    private native long jniCreateDistanceJoint(long j, long j2, long j3, boolean z, float f, float f2, float f3, float f4, float f5, float f6, float f7);

    private native long jniCreateFrictionJoint(long j, long j2, long j3, boolean z, float f, float f2, float f3, float f4, float f5, float f6);

    private native long jniCreateGearJoint(long j, long j2, long j3, boolean z, long j4, long j5, float f);

    private native long jniCreateMouseJoint(long j, long j2, long j3, boolean z, float f, float f2, float f3, float f4, float f5);

    private native long jniCreatePrismaticJoint(long j, long j2, long j3, boolean z, float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean z2, float f8, float f9, boolean z3, float f10, float f11);

    private native long jniCreatePulleyJoint(long j, long j2, long j3, boolean z, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11);

    private native long jniCreateRevoluteJoint(long j, long j2, long j3, boolean z, float f, float f2, float f3, float f4, float f5, boolean z2, float f6, float f7, boolean z3, float f8, float f9);

    private native long jniCreateRopeJoint(long j, long j2, long j3, boolean z, float f, float f2, float f3, float f4, float f5);

    private native long jniCreateWeldJoint(long j, long j2, long j3, boolean z, float f, float f2, float f3, float f4, float f5);

    private native long jniCreateWheelJoint(long j, long j2, long j3, boolean z, float f, float f2, float f3, float f4, float f5, float f6, boolean z2, float f7, float f8, float f9, float f10);

    private native void jniDestroyBody(long j, long j2);

    private native void jniDestroyJoint(long j, long j2);

    private native void jniDispose(long j);

    private native boolean jniGetAutoClearForces(long j);

    private native int jniGetBodyCount(long j);

    private native int jniGetContactCount(long j);

    private native void jniGetContactList(long j, long[] jArr);

    private native void jniGetGravity(long j, float[] fArr);

    private native int jniGetJointcount(long j);

    private native int jniGetProxyCount(long j);

    private native boolean jniIsLocked(long j);

    private native void jniQueryAABB(long j, float f, float f2, float f3, float f4);

    private native void jniRayCast(long j, float f, float f2, float f3, float f4);

    private native void jniSetAutoClearForces(long j, boolean z);

    private native void jniSetContiousPhysics(long j, boolean z);

    private native void jniSetGravity(long j, float f, float f2);

    private native void jniSetWarmStarting(long j, boolean z);

    private native void jniStep(long j, float f, int i, int i2);

    private native long newWorld(float f, float f2, boolean z);

    private native void setUseDefaultContactFilter(boolean z);

    public static native void setVelocityThreshold(float f);

    public World(Vector2 gravity, boolean doSleep) {
        this.freeBodies = new C03751(100, HttpStatus.SC_OK);
        this.freeFixtures = new C03762(100, HttpStatus.SC_OK);
        this.bodies = new LongMap(100);
        this.fixtures = new LongMap(100);
        this.joints = new LongMap(100);
        this.contactFilter = null;
        this.contactListener = null;
        this.tmpGravity = new float[2];
        this.gravity = new Vector2();
        this.queryCallback = null;
        this.contactAddrs = new long[HttpStatus.SC_OK];
        this.contacts = new Array();
        this.freeContacts = new Array();
        this.contact = new Contact(this, 0);
        this.manifold = new Manifold(0);
        this.impulse = new ContactImpulse(this, 0);
        this.rayCastCallback = null;
        this.rayPoint = new Vector2();
        this.rayNormal = new Vector2();
        this.addr = newWorld(gravity.f100x, gravity.f101y, doSleep);
        this.contacts.ensureCapacity(this.contactAddrs.length);
        this.freeContacts.ensureCapacity(this.contactAddrs.length);
        for (int i = 0; i < this.contactAddrs.length; i++) {
            this.freeContacts.add(new Contact(this, 0));
        }
    }

    public void setDestructionListener(DestructionListener listener) {
    }

    public void setContactFilter(ContactFilter filter) {
        this.contactFilter = filter;
        setUseDefaultContactFilter(filter == null);
    }

    public void setContactListener(ContactListener listener) {
        this.contactListener = listener;
    }

    public Body createBody(BodyDef def) {
        Body body = (Body) this.freeBodies.obtain();
        body.reset(jniCreateBody(this.addr, def.type.getValue(), def.position.f100x, def.position.f101y, def.angle, def.linearVelocity.f100x, def.linearVelocity.f101y, def.angularVelocity, def.linearDamping, def.angularDamping, def.allowSleep, def.awake, def.fixedRotation, def.bullet, def.active, def.gravityScale));
        this.bodies.put(body.addr, body);
        return body;
    }

    public void destroyBody(Body body) {
        body.setUserData(null);
        this.bodies.remove(body.addr);
        Array<Fixture> fixtureList = body.getFixtureList();
        while (fixtureList.size > 0) {
            ((Fixture) this.fixtures.remove(((Fixture) fixtureList.removeIndex(0)).addr)).setUserData(null);
        }
        Array<JointEdge> jointList = body.getJointList();
        while (jointList.size > 0) {
            destroyJoint(((JointEdge) body.getJointList().get(0)).joint);
        }
        jniDestroyBody(this.addr, body.addr);
        this.freeBodies.free(body);
    }

    public Joint createJoint(JointDef def) {
        long jointAddr = createProperJoint(def);
        Joint joint = null;
        if (def.type == JointType.DistanceJoint) {
            joint = new DistanceJoint(this, jointAddr);
        }
        if (def.type == JointType.FrictionJoint) {
            joint = new FrictionJoint(this, jointAddr);
        }
        if (def.type == JointType.GearJoint) {
            joint = new GearJoint(this, jointAddr);
        }
        if (def.type == JointType.MouseJoint) {
            joint = new MouseJoint(this, jointAddr);
        }
        if (def.type == JointType.PrismaticJoint) {
            joint = new PrismaticJoint(this, jointAddr);
        }
        if (def.type == JointType.PulleyJoint) {
            joint = new PulleyJoint(this, jointAddr);
        }
        if (def.type == JointType.RevoluteJoint) {
            joint = new RevoluteJoint(this, jointAddr);
        }
        if (def.type == JointType.WeldJoint) {
            joint = new WeldJoint(this, jointAddr);
        }
        if (def.type == JointType.RopeJoint) {
            joint = new RopeJoint(this, jointAddr);
        }
        if (def.type == JointType.WheelJoint) {
            joint = new WheelJoint(this, jointAddr);
        }
        if (joint != null) {
            this.joints.put(joint.addr, joint);
        }
        JointEdge jointEdgeA = new JointEdge(def.bodyB, joint);
        JointEdge jointEdgeB = new JointEdge(def.bodyA, joint);
        joint.jointEdgeA = jointEdgeA;
        joint.jointEdgeB = jointEdgeB;
        def.bodyA.joints.add(jointEdgeA);
        def.bodyB.joints.add(jointEdgeB);
        return joint;
    }

    private long createProperJoint(JointDef def) {
        if (def.type == JointType.DistanceJoint) {
            DistanceJointDef d = (DistanceJointDef) def;
            return jniCreateDistanceJoint(this.addr, d.bodyA.addr, d.bodyB.addr, d.collideConnected, d.localAnchorA.f100x, d.localAnchorA.f101y, d.localAnchorB.f100x, d.localAnchorB.f101y, d.length, d.frequencyHz, d.dampingRatio);
        } else if (def.type == JointType.FrictionJoint) {
            FrictionJointDef d2 = (FrictionJointDef) def;
            return jniCreateFrictionJoint(this.addr, d2.bodyA.addr, d2.bodyB.addr, d2.collideConnected, d2.localAnchorA.f100x, d2.localAnchorA.f101y, d2.localAnchorB.f100x, d2.localAnchorB.f101y, d2.maxForce, d2.maxTorque);
        } else if (def.type == JointType.GearJoint) {
            GearJointDef d3 = (GearJointDef) def;
            return jniCreateGearJoint(this.addr, d3.bodyA.addr, d3.bodyB.addr, d3.collideConnected, d3.joint1.addr, d3.joint2.addr, d3.ratio);
        } else if (def.type == JointType.MouseJoint) {
            MouseJointDef d4 = (MouseJointDef) def;
            return jniCreateMouseJoint(this.addr, d4.bodyA.addr, d4.bodyB.addr, d4.collideConnected, d4.target.f100x, d4.target.f101y, d4.maxForce, d4.frequencyHz, d4.dampingRatio);
        } else if (def.type == JointType.PrismaticJoint) {
            PrismaticJointDef d5 = (PrismaticJointDef) def;
            return jniCreatePrismaticJoint(this.addr, d5.bodyA.addr, d5.bodyB.addr, d5.collideConnected, d5.localAnchorA.f100x, d5.localAnchorA.f101y, d5.localAnchorB.f100x, d5.localAnchorB.f101y, d5.localAxisA.f100x, d5.localAxisA.f101y, d5.referenceAngle, d5.enableLimit, d5.lowerTranslation, d5.upperTranslation, d5.enableMotor, d5.maxMotorForce, d5.motorSpeed);
        } else if (def.type == JointType.PulleyJoint) {
            PulleyJointDef d6 = (PulleyJointDef) def;
            return jniCreatePulleyJoint(this.addr, d6.bodyA.addr, d6.bodyB.addr, d6.collideConnected, d6.groundAnchorA.f100x, d6.groundAnchorA.f101y, d6.groundAnchorB.f100x, d6.groundAnchorB.f101y, d6.localAnchorA.f100x, d6.localAnchorA.f101y, d6.localAnchorB.f100x, d6.localAnchorB.f101y, d6.lengthA, d6.lengthB, d6.ratio);
        } else if (def.type == JointType.RevoluteJoint) {
            RevoluteJointDef d7 = (RevoluteJointDef) def;
            return jniCreateRevoluteJoint(this.addr, d7.bodyA.addr, d7.bodyB.addr, d7.collideConnected, d7.localAnchorA.f100x, d7.localAnchorA.f101y, d7.localAnchorB.f100x, d7.localAnchorB.f101y, d7.referenceAngle, d7.enableLimit, d7.lowerAngle, d7.upperAngle, d7.enableMotor, d7.motorSpeed, d7.maxMotorTorque);
        } else if (def.type == JointType.WeldJoint) {
            WeldJointDef d8 = (WeldJointDef) def;
            return jniCreateWeldJoint(this.addr, d8.bodyA.addr, d8.bodyB.addr, d8.collideConnected, d8.localAnchorA.f100x, d8.localAnchorA.f101y, d8.localAnchorB.f100x, d8.localAnchorB.f101y, d8.referenceAngle);
        } else if (def.type == JointType.RopeJoint) {
            RopeJointDef d9 = (RopeJointDef) def;
            return jniCreateRopeJoint(this.addr, d9.bodyA.addr, d9.bodyB.addr, d9.collideConnected, d9.localAnchorA.f100x, d9.localAnchorA.f101y, d9.localAnchorB.f100x, d9.localAnchorB.f101y, d9.maxLength);
        } else if (def.type != JointType.WheelJoint) {
            return 0;
        } else {
            WheelJointDef d10 = (WheelJointDef) def;
            return jniCreateWheelJoint(this.addr, d10.bodyA.addr, d10.bodyB.addr, d10.collideConnected, d10.localAnchorA.f100x, d10.localAnchorA.f101y, d10.localAnchorB.f100x, d10.localAnchorB.f101y, d10.localAxisA.f100x, d10.localAxisA.f101y, d10.enableMotor, d10.maxMotorTorque, d10.motorSpeed, d10.frequencyHz, d10.dampingRatio);
        }
    }

    public void destroyJoint(Joint joint) {
        joint.setUserData(null);
        this.joints.remove(joint.addr);
        joint.jointEdgeA.other.joints.removeValue(joint.jointEdgeB, true);
        joint.jointEdgeB.other.joints.removeValue(joint.jointEdgeA, true);
        jniDestroyJoint(this.addr, joint.addr);
    }

    public void step(float timeStep, int velocityIterations, int positionIterations) {
        jniStep(this.addr, timeStep, velocityIterations, positionIterations);
    }

    public void clearForces() {
        jniClearForces(this.addr);
    }

    public void setWarmStarting(boolean flag) {
        jniSetWarmStarting(this.addr, flag);
    }

    public void setContinuousPhysics(boolean flag) {
        jniSetContiousPhysics(this.addr, flag);
    }

    public int getProxyCount() {
        return jniGetProxyCount(this.addr);
    }

    public int getBodyCount() {
        return jniGetBodyCount(this.addr);
    }

    public int getJointCount() {
        return jniGetJointcount(this.addr);
    }

    public int getContactCount() {
        return jniGetContactCount(this.addr);
    }

    public void setGravity(Vector2 gravity) {
        jniSetGravity(this.addr, gravity.f100x, gravity.f101y);
    }

    public Vector2 getGravity() {
        jniGetGravity(this.addr, this.tmpGravity);
        this.gravity.f100x = this.tmpGravity[0];
        this.gravity.f101y = this.tmpGravity[1];
        return this.gravity;
    }

    public boolean isLocked() {
        return jniIsLocked(this.addr);
    }

    public void setAutoClearForces(boolean flag) {
        jniSetAutoClearForces(this.addr, flag);
    }

    public boolean getAutoClearForces() {
        return jniGetAutoClearForces(this.addr);
    }

    public void QueryAABB(QueryCallback callback, float lowerX, float lowerY, float upperX, float upperY) {
        this.queryCallback = callback;
        jniQueryAABB(this.addr, lowerX, lowerY, upperX, upperY);
    }

    public Array<Contact> getContactList() {
        int i;
        int numContacts = getContactCount();
        if (numContacts > this.contactAddrs.length) {
            int newSize = numContacts * 2;
            this.contactAddrs = new long[newSize];
            this.contacts.ensureCapacity(newSize);
            this.freeContacts.ensureCapacity(newSize);
        }
        if (numContacts > this.freeContacts.size) {
            int freeConts = this.freeContacts.size;
            for (i = 0; i < numContacts - freeConts; i++) {
                this.freeContacts.add(new Contact(this, 0));
            }
        }
        jniGetContactList(this.addr, this.contactAddrs);
        this.contacts.clear();
        for (i = 0; i < numContacts; i++) {
            Contact contact = (Contact) this.freeContacts.get(i);
            contact.addr = this.contactAddrs[i];
            this.contacts.add(contact);
        }
        return this.contacts;
    }

    public void getBodies(Array<Body> bodies) {
        bodies.clear();
        bodies.ensureCapacity(this.bodies.size);
        Iterator<Body> iter = this.bodies.values();
        while (iter.hasNext()) {
            bodies.add(iter.next());
        }
    }

    public void getJoints(Array<Joint> joints) {
        joints.clear();
        joints.ensureCapacity(this.joints.size);
        Iterator<Joint> iter = this.joints.values();
        while (iter.hasNext()) {
            joints.add(iter.next());
        }
    }

    public void dispose() {
        jniDispose(this.addr);
    }

    private boolean contactFilter(long fixtureA, long fixtureB) {
        if (this.contactFilter != null) {
            return this.contactFilter.shouldCollide((Fixture) this.fixtures.get(fixtureA), (Fixture) this.fixtures.get(fixtureB));
        }
        Filter filterA = ((Fixture) this.fixtures.get(fixtureA)).getFilterData();
        Filter filterB = ((Fixture) this.fixtures.get(fixtureB)).getFilterData();
        if (filterA.groupIndex == filterB.groupIndex && filterA.groupIndex != (short) 0) {
            return filterA.groupIndex > (short) 0;
        } else {
            boolean collide;
            if ((filterA.maskBits & filterB.categoryBits) == 0 || (filterA.categoryBits & filterB.maskBits) == 0) {
                collide = false;
            } else {
                collide = true;
            }
            return collide;
        }
    }

    private void beginContact(long contactAddr) {
        this.contact.addr = contactAddr;
        if (this.contactListener != null) {
            this.contactListener.beginContact(this.contact);
        }
    }

    private void endContact(long contactAddr) {
        this.contact.addr = contactAddr;
        if (this.contactListener != null) {
            this.contactListener.endContact(this.contact);
        }
    }

    private void preSolve(long contactAddr, long manifoldAddr) {
        this.contact.addr = contactAddr;
        this.manifold.addr = manifoldAddr;
        if (this.contactListener != null) {
            this.contactListener.preSolve(this.contact, this.manifold);
        }
    }

    private void postSolve(long contactAddr, long impulseAddr) {
        this.contact.addr = contactAddr;
        this.impulse.addr = impulseAddr;
        if (this.contactListener != null) {
            this.contactListener.postSolve(this.contact, this.impulse);
        }
    }

    private boolean reportFixture(long addr) {
        if (this.queryCallback != null) {
            return this.queryCallback.reportFixture((Fixture) this.fixtures.get(addr));
        }
        return false;
    }

    public void rayCast(RayCastCallback callback, Vector2 point1, Vector2 point2) {
        this.rayCastCallback = callback;
        jniRayCast(this.addr, point1.f100x, point1.f101y, point2.f100x, point2.f101y);
    }

    private float reportRayFixture(long addr, float pX, float pY, float nX, float nY, float fraction) {
        if (this.rayCastCallback == null) {
            return 0.0f;
        }
        this.rayPoint.f100x = pX;
        this.rayPoint.f101y = pY;
        this.rayNormal.f100x = nX;
        this.rayNormal.f101y = nY;
        return this.rayCastCallback.reportRayFixture((Fixture) this.fixtures.get(addr), this.rayPoint, this.rayNormal, fraction);
    }
}
