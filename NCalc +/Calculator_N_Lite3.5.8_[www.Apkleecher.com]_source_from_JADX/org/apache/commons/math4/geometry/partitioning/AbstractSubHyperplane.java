package org.apache.commons.math4.geometry.partitioning;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane.SplitSubHyperplane;

public abstract class AbstractSubHyperplane<S extends Space, T extends Space> implements SubHyperplane<S> {
    private final Hyperplane<S> hyperplane;
    private final Region<T> remainingRegion;

    protected abstract AbstractSubHyperplane<S, T> buildNew(Hyperplane<S> hyperplane, Region<T> region);

    public abstract Side side(Hyperplane<S> hyperplane);

    public abstract SplitSubHyperplane<S> split(Hyperplane<S> hyperplane);

    protected AbstractSubHyperplane(Hyperplane<S> hyperplane, Region<T> remainingRegion) {
        this.hyperplane = hyperplane;
        this.remainingRegion = remainingRegion;
    }

    public AbstractSubHyperplane<S, T> copySelf() {
        return buildNew(this.hyperplane.copySelf(), this.remainingRegion);
    }

    public Hyperplane<S> getHyperplane() {
        return this.hyperplane;
    }

    public Region<T> getRemainingRegion() {
        return this.remainingRegion;
    }

    public double getSize() {
        return this.remainingRegion.getSize();
    }

    public AbstractSubHyperplane<S, T> reunite(SubHyperplane<S> other) {
        return buildNew(this.hyperplane, new RegionFactory().union(this.remainingRegion, ((AbstractSubHyperplane) other).remainingRegion));
    }

    public AbstractSubHyperplane<S, T> applyTransform(Transform<S, T> transform) {
        Hyperplane<S> tHyperplane = transform.apply(this.hyperplane);
        Map<BSPTree<T>, BSPTree<T>> map = new HashMap();
        BSPTree<T> tTree = recurseTransform(this.remainingRegion.getTree(false), tHyperplane, transform, map);
        for (Entry<BSPTree<T>, BSPTree<T>> entry : map.entrySet()) {
            if (((BSPTree) entry.getKey()).getCut() != null) {
                BoundaryAttribute<T> original = (BoundaryAttribute) ((BSPTree) entry.getKey()).getAttribute();
                if (original != null) {
                    BoundaryAttribute<T> transformed = (BoundaryAttribute) ((BSPTree) entry.getValue()).getAttribute();
                    Iterator it = original.getSplitters().iterator();
                    while (it.hasNext()) {
                        transformed.getSplitters().add((BSPTree) map.get((BSPTree) it.next()));
                    }
                }
            }
        }
        return buildNew(tHyperplane, this.remainingRegion.buildNew(tTree));
    }

    private BSPTree<T> recurseTransform(BSPTree<T> node, Hyperplane<S> transformed, Transform<S, T> transform, Map<BSPTree<T>, BSPTree<T>> map) {
        BSPTree<T> transformedNode;
        if (node.getCut() == null) {
            transformedNode = new BSPTree(node.getAttribute());
        } else {
            BoundaryAttribute<T> attribute = (BoundaryAttribute) node.getAttribute();
            if (attribute != null) {
                SubHyperplane<T> tPO;
                SubHyperplane<T> tPI;
                if (attribute.getPlusOutside() == null) {
                    tPO = null;
                } else {
                    tPO = transform.apply(attribute.getPlusOutside(), this.hyperplane, transformed);
                }
                if (attribute.getPlusInside() == null) {
                    tPI = null;
                } else {
                    tPI = transform.apply(attribute.getPlusInside(), this.hyperplane, transformed);
                }
                attribute = new BoundaryAttribute(tPO, tPI, new NodesSet());
            }
            transformedNode = new BSPTree(transform.apply(node.getCut(), this.hyperplane, transformed), recurseTransform(node.getPlus(), transformed, transform, map), recurseTransform(node.getMinus(), transformed, transform, map), attribute);
        }
        map.put(node, transformedNode);
        return transformedNode;
    }

    public boolean isEmpty() {
        return this.remainingRegion.isEmpty();
    }
}
