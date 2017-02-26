package com.badlogic.gdx.scenes.scene2d;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Cullable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.google.android.gms.cast.TextTrackStyle;

public class Group extends Actor implements Cullable {
    private final Matrix4 batchTransform;
    private final SnapshotArray<Actor> children;
    private Rectangle cullingArea;
    private final Matrix3 localTransform;
    private final Matrix4 oldBatchTransform;
    private final Vector2 point;
    private boolean transform;
    private final Matrix3 worldTransform;

    public Group() {
        this.children = new SnapshotArray(true, 4, Actor.class);
        this.localTransform = new Matrix3();
        this.worldTransform = new Matrix3();
        this.batchTransform = new Matrix4();
        this.oldBatchTransform = new Matrix4();
        this.transform = true;
        this.point = new Vector2();
    }

    public void act(float delta) {
        super.act(delta);
        Actor[] actors = (Actor[]) this.children.begin();
        int n = this.children.size;
        for (int i = 0; i < n; i++) {
            actors[i].act(delta);
        }
        this.children.end();
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        if (this.transform) {
            applyTransform(batch, computeTransform());
        }
        drawChildren(batch, parentAlpha);
        if (this.transform) {
            resetTransform(batch);
        }
    }

    protected void drawChildren(SpriteBatch batch, float parentAlpha) {
        parentAlpha *= this.color.f37a;
        SnapshotArray<Actor> children = this.children;
        Actor[] actors = (Actor[]) children.begin();
        Rectangle cullingArea = this.cullingArea;
        int n;
        int i;
        Actor child;
        float cx;
        float cy;
        float offsetX;
        float offsetY;
        if (cullingArea != null) {
            float cullLeft = cullingArea.f75x;
            float cullRight = cullLeft + cullingArea.width;
            float cullBottom = cullingArea.f76y;
            float cullTop = cullBottom + cullingArea.height;
            if (this.transform) {
                n = children.size;
                for (i = 0; i < n; i++) {
                    child = actors[i];
                    if (child.isVisible()) {
                        cx = child.f83x;
                        cy = child.f84y;
                        if (cx <= cullRight && cy <= cullTop) {
                            if (child.width + cx >= cullLeft) {
                                if (child.height + cy >= cullBottom) {
                                    child.draw(batch, parentAlpha);
                                }
                            }
                        }
                    }
                }
                batch.flush();
            } else {
                offsetX = this.x;
                offsetY = this.y;
                this.x = 0.0f;
                this.y = 0.0f;
                n = children.size;
                for (i = 0; i < n; i++) {
                    child = actors[i];
                    if (child.isVisible()) {
                        cx = child.f83x;
                        cy = child.f84y;
                        if (cx <= cullRight && cy <= cullTop) {
                            if (child.width + cx >= cullLeft) {
                                if (child.height + cy >= cullBottom) {
                                    child.f83x = cx + offsetX;
                                    child.f84y = cy + offsetY;
                                    child.draw(batch, parentAlpha);
                                    child.f83x = cx;
                                    child.f84y = cy;
                                }
                            }
                        }
                    }
                }
                this.x = offsetX;
                this.y = offsetY;
            }
        } else if (this.transform) {
            n = children.size;
            for (i = 0; i < n; i++) {
                child = actors[i];
                if (child.isVisible()) {
                    child.draw(batch, parentAlpha);
                }
            }
            batch.flush();
        } else {
            offsetX = this.x;
            offsetY = this.y;
            this.x = 0.0f;
            this.y = 0.0f;
            n = children.size;
            for (i = 0; i < n; i++) {
                child = actors[i];
                if (child.isVisible()) {
                    cx = child.f83x;
                    cy = child.f84y;
                    child.f83x = cx + offsetX;
                    child.f84y = cy + offsetY;
                    child.draw(batch, parentAlpha);
                    child.f83x = cx;
                    child.f84y = cy;
                }
            }
            this.x = offsetX;
            this.y = offsetY;
        }
        children.end();
    }

    protected void applyTransform(SpriteBatch batch, Matrix4 transform) {
        this.oldBatchTransform.set(batch.getTransformMatrix());
        batch.setTransformMatrix(transform);
    }

    protected Matrix4 computeTransform() {
        Matrix3 temp = this.worldTransform;
        float originX = this.originX;
        float originY = this.originY;
        float rotation = this.rotation;
        float scaleX = this.scaleX;
        float scaleY = this.scaleY;
        if (originX == 0.0f && originY == 0.0f) {
            this.localTransform.idt();
        } else {
            this.localTransform.setToTranslation(originX, originY);
        }
        if (rotation != 0.0f) {
            this.localTransform.rotate(rotation);
        }
        if (!(scaleX == TextTrackStyle.DEFAULT_FONT_SCALE && scaleY == TextTrackStyle.DEFAULT_FONT_SCALE)) {
            this.localTransform.scale(scaleX, scaleY);
        }
        if (!(originX == 0.0f && originY == 0.0f)) {
            this.localTransform.translate(-originX, -originY);
        }
        this.localTransform.trn(this.x, this.y);
        Group parentGroup = getParent();
        while (parentGroup != null && !parentGroup.transform) {
            parentGroup = parentGroup.getParent();
        }
        if (parentGroup != null) {
            this.worldTransform.set(parentGroup.worldTransform);
            this.worldTransform.mul(this.localTransform);
        } else {
            this.worldTransform.set(this.localTransform);
        }
        this.batchTransform.set(this.worldTransform);
        return this.batchTransform;
    }

    protected void resetTransform(SpriteBatch batch) {
        batch.setTransformMatrix(this.oldBatchTransform);
    }

    public void setCullingArea(Rectangle cullingArea) {
        this.cullingArea = cullingArea;
    }

    public Actor hit(float x, float y, boolean touchable) {
        if (touchable && getTouchable() == Touchable.disabled) {
            return null;
        }
        Array<Actor> children = this.children;
        for (int i = children.size - 1; i >= 0; i--) {
            Actor child = (Actor) children.get(i);
            if (child.isVisible()) {
                child.parentToLocalCoordinates(this.point.set(x, y));
                Actor hit = child.hit(this.point.f100x, this.point.f101y, touchable);
                if (hit != null) {
                    return hit;
                }
            }
        }
        return super.hit(x, y, touchable);
    }

    protected void childrenChanged() {
    }

    public void addActor(Actor actor) {
        actor.remove();
        this.children.add(actor);
        actor.setParent(this);
        actor.setStage(getStage());
        childrenChanged();
    }

    public void addActorAt(int index, Actor actor) {
        actor.remove();
        if (index >= this.children.size) {
            this.children.add(actor);
        } else {
            this.children.insert(index, actor);
        }
        actor.setParent(this);
        actor.setStage(getStage());
        childrenChanged();
    }

    public void addActorBefore(Actor actorBefore, Actor actor) {
        actor.remove();
        this.children.insert(this.children.indexOf(actorBefore, true), actor);
        actor.setParent(this);
        actor.setStage(getStage());
        childrenChanged();
    }

    public void addActorAfter(Actor actorAfter, Actor actor) {
        actor.remove();
        int index = this.children.indexOf(actorAfter, true);
        if (index == this.children.size) {
            this.children.add(actor);
        } else {
            this.children.insert(index + 1, actor);
        }
        actor.setParent(this);
        actor.setStage(getStage());
        childrenChanged();
    }

    public boolean removeActor(Actor actor) {
        if (!this.children.removeValue(actor, true)) {
            return false;
        }
        Stage stage = getStage();
        if (stage != null) {
            stage.unfocus(actor);
        }
        actor.setParent(null);
        actor.setStage(null);
        childrenChanged();
        return true;
    }

    public void clearChildren() {
        Actor[] actors = (Actor[]) this.children.begin();
        int n = this.children.size;
        for (int i = 0; i < n; i++) {
            Actor child = actors[i];
            child.setStage(null);
            child.setParent(null);
        }
        this.children.end();
        this.children.clear();
        childrenChanged();
    }

    public void clear() {
        super.clear();
        clearChildren();
    }

    public Actor findActor(String name) {
        int i;
        Array<Actor> children = this.children;
        int n = children.size;
        for (i = 0; i < n; i++) {
            if (name.equals(((Actor) children.get(i)).getName())) {
                return (Actor) children.get(i);
            }
        }
        n = children.size;
        for (i = 0; i < n; i++) {
            Actor child = (Actor) children.get(i);
            if (child instanceof Group) {
                Actor actor = ((Group) child).findActor(name);
                if (actor != null) {
                    return actor;
                }
            }
        }
        return null;
    }

    protected void setStage(Stage stage) {
        super.setStage(stage);
        Array<Actor> children = this.children;
        int n = children.size;
        for (int i = 0; i < n; i++) {
            ((Actor) children.get(i)).setStage(stage);
        }
    }

    public boolean swapActor(int first, int second) {
        int maxIndex = this.children.size;
        if (first < 0 || first >= maxIndex || second < 0 || second >= maxIndex) {
            return false;
        }
        this.children.swap(first, second);
        return true;
    }

    public boolean swapActor(Actor first, Actor second) {
        int firstIndex = this.children.indexOf(first, true);
        int secondIndex = this.children.indexOf(second, true);
        if (firstIndex == -1 || secondIndex == -1) {
            return false;
        }
        this.children.swap(firstIndex, secondIndex);
        return true;
    }

    public SnapshotArray<Actor> getChildren() {
        return this.children;
    }

    public boolean hasChildren() {
        return this.children.size > 0;
    }

    public void setTransform(boolean transform) {
        this.transform = transform;
    }

    public boolean isTransform() {
        return this.transform;
    }

    public Vector2 localToDescendantCoordinates(Actor descendant, Vector2 localCoords) {
        Group parent = descendant.getParent();
        if (parent == null) {
            throw new IllegalArgumentException("Child is not a descendant: " + descendant);
        }
        if (parent != this) {
            localToDescendantCoordinates(parent, localCoords);
        }
        descendant.parentToLocalCoordinates(localCoords);
        return localCoords;
    }

    public void print() {
        print("");
    }

    private void print(String indent) {
        Actor[] actors = (Actor[]) this.children.begin();
        int n = this.children.size;
        for (int i = 0; i < n; i++) {
            System.out.println(indent + actors[i]);
            if (actors[i] instanceof Group) {
                ((Group) actors[i]).print(indent + "|  ");
            }
        }
        this.children.end();
    }
}
