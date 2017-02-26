package org.apache.commons.math4.geometry.partitioning;

import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.geometry.partitioning.BSPTreeVisitor.Order;

class BoundaryBuilder<S extends Space> implements BSPTreeVisitor<S> {
    BoundaryBuilder() {
    }

    public Order visitOrder(BSPTree<S> bSPTree) {
        return Order.PLUS_MINUS_SUB;
    }

    public void visitInternalNode(BSPTree<S> node) {
        Characterization<S> minusChar;
        SubHyperplane<S> plusOutside = null;
        SubHyperplane<S> plusInside = null;
        NodesSet<S> nodesSet = null;
        Characterization<S> plusChar = new Characterization(node.getPlus(), node.getCut().copySelf());
        if (plusChar.touchOutside()) {
            minusChar = new Characterization(node.getMinus(), plusChar.outsideTouching());
            if (minusChar.touchInside()) {
                plusOutside = minusChar.insideTouching();
                nodesSet = new NodesSet();
                nodesSet.addAll(minusChar.getInsideSplitters());
                nodesSet.addAll(plusChar.getOutsideSplitters());
            }
        }
        if (plusChar.touchInside()) {
            minusChar = new Characterization(node.getMinus(), plusChar.insideTouching());
            if (minusChar.touchOutside()) {
                plusInside = minusChar.outsideTouching();
                if (nodesSet == null) {
                    nodesSet = new NodesSet();
                }
                nodesSet.addAll(minusChar.getOutsideSplitters());
                nodesSet.addAll(plusChar.getInsideSplitters());
            }
        }
        if (nodesSet != null) {
            for (BSPTree<S> up = node.getParent(); up != null; up = up.getParent()) {
                nodesSet.add(up);
            }
        }
        node.setAttribute(new BoundaryAttribute(plusOutside, plusInside, nodesSet));
    }

    public void visitLeafNode(BSPTree<S> bSPTree) {
    }
}
