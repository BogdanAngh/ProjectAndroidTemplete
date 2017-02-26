package org.apache.commons.math4.geometry.partitioning;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.geometry.partitioning.BSPTreeVisitor.Order;
import org.apache.commons.math4.geometry.partitioning.Region.Location;
import org.apache.commons.math4.util.FastMath;

class BoundaryProjector<S extends Space, T extends Space> implements BSPTreeVisitor<S> {
    private BSPTree<S> leaf;
    private double offset;
    private final Point<S> original;
    private Point<S> projected;

    public BoundaryProjector(Point<S> original) {
        this.original = original;
        this.projected = null;
        this.leaf = null;
        this.offset = Double.POSITIVE_INFINITY;
    }

    public Order visitOrder(BSPTree<S> node) {
        if (node.getCut().getHyperplane().getOffset(this.original) <= 0.0d) {
            return Order.MINUS_SUB_PLUS;
        }
        return Order.PLUS_SUB_MINUS;
    }

    public void visitInternalNode(BSPTree<S> node) {
        Hyperplane<S> hyperplane = node.getCut().getHyperplane();
        double signedOffset = hyperplane.getOffset(this.original);
        if (FastMath.abs(signedOffset) < this.offset) {
            Point<S> regular = hyperplane.project(this.original);
            List<Region<T>> boundaryParts = boundaryRegions(node);
            boolean regularFound = false;
            for (Region<T> part : boundaryParts) {
                if (!regularFound && belongsToPart(regular, hyperplane, part)) {
                    this.projected = regular;
                    this.offset = FastMath.abs(signedOffset);
                    regularFound = true;
                }
            }
            if (!regularFound) {
                for (Region<T> part2 : boundaryParts) {
                    Point<S> spI = singularProjection(regular, hyperplane, part2);
                    if (spI != null) {
                        double distance = this.original.distance(spI);
                        if (distance < this.offset) {
                            this.projected = spI;
                            this.offset = distance;
                        }
                    }
                }
            }
        }
    }

    public void visitLeafNode(BSPTree<S> node) {
        if (this.leaf == null) {
            this.leaf = node;
        }
    }

    public BoundaryProjection<S> getProjection() {
        this.offset = FastMath.copySign(this.offset, (double) (((Boolean) this.leaf.getAttribute()).booleanValue() ? -1 : 1));
        return new BoundaryProjection(this.original, this.projected, this.offset);
    }

    private List<Region<T>> boundaryRegions(BSPTree<S> node) {
        List<Region<T>> regions = new ArrayList(2);
        BoundaryAttribute<S> ba = (BoundaryAttribute) node.getAttribute();
        addRegion(ba.getPlusInside(), regions);
        addRegion(ba.getPlusOutside(), regions);
        return regions;
    }

    private void addRegion(SubHyperplane<S> sub, List<Region<T>> list) {
        if (sub != null) {
            Region<T> region = ((AbstractSubHyperplane) sub).getRemainingRegion();
            if (region != null) {
                list.add(region);
            }
        }
    }

    private boolean belongsToPart(Point<S> point, Hyperplane<S> hyperplane, Region<T> part) {
        return part.checkPoint(((Embedding) hyperplane).toSubSpace(point)) != Location.OUTSIDE;
    }

    private Point<S> singularProjection(Point<S> point, Hyperplane<S> hyperplane, Region<T> part) {
        Embedding<S, T> embedding = (Embedding) hyperplane;
        BoundaryProjection<T> bp = part.projectToBoundary(embedding.toSubSpace(point));
        return bp.getProjected() == null ? null : embedding.toSpace(bp.getProjected());
    }
}
