package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodeAnimation;
import com.badlogic.gdx.graphics.g3d.model.NodeKeyframe;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.Iterator;

public class BaseAnimationController {
    private static final Transform tmpT;
    private static final ObjectMap<Node, Transform> transforms;
    private boolean applying;
    public final ModelInstance target;
    private final Pool<Transform> transformPool;

    /* renamed from: com.badlogic.gdx.graphics.g3d.utils.BaseAnimationController.1 */
    class C03631 extends Pool<Transform> {
        C03631() {
        }

        protected Transform newObject() {
            return new Transform();
        }
    }

    public static final class Transform implements Poolable {
        public final Quaternion rotation;
        public final Vector3 scale;
        public final Vector3 translation;

        public Transform() {
            this.translation = new Vector3();
            this.rotation = new Quaternion();
            this.scale = new Vector3(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        }

        public Transform idt() {
            this.translation.set(0.0f, 0.0f, 0.0f);
            this.rotation.idt();
            this.scale.set(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
            return this;
        }

        public Transform set(Vector3 t, Quaternion r, Vector3 s) {
            this.translation.set(t);
            this.rotation.set(r);
            this.scale.set(s);
            return this;
        }

        public Transform set(Transform other) {
            return set(other.translation, other.rotation, other.scale);
        }

        public Transform lerp(Transform target, float alpha) {
            return lerp(target.translation, target.rotation, target.scale, alpha);
        }

        public Transform lerp(Vector3 targetT, Quaternion targetR, Vector3 targetS, float alpha) {
            this.translation.lerp(targetT, alpha);
            this.rotation.slerp(targetR, alpha);
            this.scale.lerp(targetS, alpha);
            return this;
        }

        public Matrix4 toMatrix4(Matrix4 out) {
            return out.set(this.translation, this.rotation, this.scale);
        }

        public void reset() {
            idt();
        }
    }

    static {
        transforms = new ObjectMap();
        tmpT = new Transform();
    }

    public BaseAnimationController(ModelInstance target) {
        this.transformPool = new C03631();
        this.applying = false;
        this.target = target;
    }

    protected void begin() {
        if (this.applying) {
            throw new GdxRuntimeException("You must call end() after each call to being()");
        }
        this.applying = true;
    }

    protected void apply(Animation animation, float time, float weight) {
        if (this.applying) {
            applyAnimation(transforms, this.transformPool, weight, animation, time);
            return;
        }
        throw new GdxRuntimeException("You must call begin() before adding an animation");
    }

    protected void end() {
        if (this.applying) {
            Iterator i$ = transforms.entries().iterator();
            while (i$.hasNext()) {
                Entry<Node, Transform> entry = (Entry) i$.next();
                ((Transform) entry.value).toMatrix4(((Node) entry.key).localTransform);
                this.transformPool.free(entry.value);
            }
            transforms.clear();
            this.target.calculateTransforms();
            this.applying = false;
            return;
        }
        throw new GdxRuntimeException("You must call begin() first");
    }

    protected void applyAnimation(Animation animation, float time) {
        if (this.applying) {
            throw new GdxRuntimeException("Call end() first");
        }
        applyAnimation(null, null, TextTrackStyle.DEFAULT_FONT_SCALE, animation, time);
        this.target.calculateTransforms();
    }

    protected void applyAnimations(Animation anim1, float time1, Animation anim2, float time2, float weight) {
        if (anim2 == null || weight == 0.0f) {
            applyAnimation(anim1, time1);
        } else if (anim1 == null || weight == TextTrackStyle.DEFAULT_FONT_SCALE) {
            applyAnimation(anim2, time2);
        } else if (this.applying) {
            throw new GdxRuntimeException("Call end() first");
        } else {
            begin();
            apply(anim1, time1, TextTrackStyle.DEFAULT_FONT_SCALE);
            apply(anim2, time2, weight);
            end();
        }
    }

    protected static void applyAnimation(ObjectMap<Node, Transform> out, Pool<Transform> pool, float alpha, Animation animation, float time) {
        Iterator i$ = animation.nodeAnimations.iterator();
        while (i$.hasNext()) {
            NodeAnimation nodeAnim = (NodeAnimation) i$.next();
            Node node = nodeAnim.node;
            node.isAnimated = true;
            int n = nodeAnim.keyframes.size - 1;
            int first = 0;
            int second = -1;
            int i = 0;
            while (i < n) {
                if (time >= ((NodeKeyframe) nodeAnim.keyframes.get(i)).keytime && time <= ((NodeKeyframe) nodeAnim.keyframes.get(i + 1)).keytime) {
                    first = i;
                    second = i + 1;
                    break;
                }
                i++;
            }
            Transform transform = tmpT;
            NodeKeyframe firstKeyframe = (NodeKeyframe) nodeAnim.keyframes.get(first);
            transform.set(firstKeyframe.translation, firstKeyframe.rotation, firstKeyframe.scale);
            if (second > first) {
                NodeKeyframe secondKeyframe = (NodeKeyframe) nodeAnim.keyframes.get(second);
                transform.lerp(secondKeyframe.translation, secondKeyframe.rotation, secondKeyframe.scale, (time - firstKeyframe.keytime) / (secondKeyframe.keytime - firstKeyframe.keytime));
            }
            if (out == null) {
                transform.toMatrix4(node.localTransform);
            } else if (!out.containsKey(node)) {
                out.put(node, ((Transform) pool.obtain()).set(transform));
            } else if (alpha == TextTrackStyle.DEFAULT_FONT_SCALE) {
                ((Transform) out.get(node)).set(transform);
            } else {
                ((Transform) out.get(node)).lerp(transform, alpha);
            }
        }
    }

    protected void removeAnimation(Animation animation) {
        Iterator i$ = animation.nodeAnimations.iterator();
        while (i$.hasNext()) {
            ((NodeAnimation) i$.next()).node.isAnimated = false;
        }
    }
}
