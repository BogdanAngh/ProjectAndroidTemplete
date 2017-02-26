package com.badlogic.gdx.graphics.g3d.model;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.Iterator;

public class Node {
    public final Array<Node> children;
    public final Matrix4 globalTransform;
    public String id;
    public boolean isAnimated;
    public final Matrix4 localTransform;
    public Node parent;
    public Array<NodePart> parts;
    public final Quaternion rotation;
    public final Vector3 scale;
    public final Vector3 translation;

    public Node() {
        this.children = new Array(2);
        this.translation = new Vector3();
        this.rotation = new Quaternion(0.0f, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.scale = new Vector3(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.localTransform = new Matrix4();
        this.globalTransform = new Matrix4();
        this.parts = new Array(2);
    }

    public Matrix4 calculateLocalTransform() {
        if (!this.isAnimated) {
            this.localTransform.idt();
            this.localTransform.translate(this.translation);
            this.localTransform.rotate(this.rotation);
            this.localTransform.scale(this.scale.f105x, this.scale.f106y, this.scale.f107z);
        }
        return this.localTransform;
    }

    public Matrix4 calculateWorldTransform() {
        if (this.parent == null) {
            this.globalTransform.set(this.localTransform);
        } else {
            this.globalTransform.set(this.parent.globalTransform).mul(this.localTransform);
        }
        return this.globalTransform;
    }

    public void calculateTransforms(boolean recursive) {
        calculateLocalTransform();
        calculateWorldTransform();
        if (recursive) {
            Iterator i$ = this.children.iterator();
            while (i$.hasNext()) {
                ((Node) i$.next()).calculateTransforms(true);
            }
        }
    }

    public void calculateBoneTransforms(boolean recursive) {
        Iterator i$ = this.parts.iterator();
        while (i$.hasNext()) {
            NodePart part = (NodePart) i$.next();
            if (!(part.invBoneBindTransforms == null || part.bones == null || part.invBoneBindTransforms.size != part.bones.length)) {
                int n = part.invBoneBindTransforms.size;
                for (int i = 0; i < n; i++) {
                    part.bones[i].set(((Node[]) part.invBoneBindTransforms.keys)[i].globalTransform).mul(((Matrix4[]) part.invBoneBindTransforms.values)[i]);
                }
            }
        }
        if (recursive) {
            i$ = this.children.iterator();
            while (i$.hasNext()) {
                ((Node) i$.next()).calculateBoneTransforms(true);
            }
        }
    }

    public BoundingBox calculateBoundingBox(BoundingBox out) {
        out.inf();
        return extendBoundingBox(out);
    }

    public BoundingBox calculateBoundingBox(BoundingBox out, boolean transform) {
        out.inf();
        return extendBoundingBox(out, transform);
    }

    public BoundingBox extendBoundingBox(BoundingBox out) {
        return extendBoundingBox(out, true);
    }

    public BoundingBox extendBoundingBox(BoundingBox out, boolean transform) {
        int i;
        int partCount = this.parts.size;
        for (i = 0; i < partCount; i++) {
            MeshPart meshPart = ((NodePart) this.parts.get(i)).meshPart;
            if (transform) {
                meshPart.mesh.extendBoundingBox(out, meshPart.indexOffset, meshPart.numVertices, this.globalTransform);
            } else {
                meshPart.mesh.extendBoundingBox(out, meshPart.indexOffset, meshPart.numVertices);
            }
        }
        int childCount = this.children.size;
        for (i = 0; i < childCount; i++) {
            ((Node) this.children.get(i)).extendBoundingBox(out);
        }
        return out;
    }

    public Node getChild(String id, boolean recursive, boolean ignoreCase) {
        return getNode(this.children, id, recursive, ignoreCase);
    }

    public static Node getNode(Array<Node> nodes, String id, boolean recursive, boolean ignoreCase) {
        int i;
        Node node;
        int n = nodes.size;
        if (ignoreCase) {
            for (i = 0; i < n; i++) {
                node = (Node) nodes.get(i);
                if (node.id.equalsIgnoreCase(id)) {
                    return node;
                }
            }
        } else {
            for (i = 0; i < n; i++) {
                node = (Node) nodes.get(i);
                if (node.id.equals(id)) {
                    return node;
                }
            }
        }
        if (recursive) {
            for (i = 0; i < n; i++) {
                node = getNode(((Node) nodes.get(i)).children, id, true, ignoreCase);
                if (node != null) {
                    return node;
                }
            }
        }
        return null;
    }
}
