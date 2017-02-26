package org.apache.commons.math4.geometry.partitioning;

import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.geometry.partitioning.BSPTreeVisitor.Order;

class BoundarySizeVisitor<S extends Space> implements BSPTreeVisitor<S> {
    private double boundarySize;

    public BoundarySizeVisitor() {
        this.boundarySize = 0.0d;
    }

    public Order visitOrder(BSPTree<S> bSPTree) {
        return Order.MINUS_SUB_PLUS;
    }

    public void visitInternalNode(BSPTree<S> node) {
        BoundaryAttribute<S> attribute = (BoundaryAttribute) node.getAttribute();
        if (attribute.getPlusOutside() != null) {
            this.boundarySize += attribute.getPlusOutside().getSize();
        }
        if (attribute.getPlusInside() != null) {
            this.boundarySize += attribute.getPlusInside().getSize();
        }
    }

    public void visitLeafNode(BSPTree<S> bSPTree) {
    }

    public double getSize() {
        return this.boundarySize;
    }
}
