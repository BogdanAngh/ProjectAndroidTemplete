package org.apache.commons.math4.geometry.partitioning;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.geometry.partitioning.BSPTree.LeafMerger;
import org.apache.commons.math4.geometry.partitioning.BSPTree.VanishingCutHandler;
import org.apache.commons.math4.geometry.partitioning.BSPTreeVisitor.Order;
import org.apache.commons.math4.geometry.partitioning.Region.Location;

public class RegionFactory<S extends Space> {
    private final NodesCleaner nodeCleaner;

    private class DifferenceMerger implements LeafMerger<S>, VanishingCutHandler<S> {
        private final Region<S> region1;
        private final Region<S> region2;

        public DifferenceMerger(Region<S> region1, Region<S> region2) {
            this.region1 = region1.copySelf();
            this.region2 = region2.copySelf();
        }

        public BSPTree<S> merge(BSPTree<S> leaf, BSPTree<S> tree, BSPTree<S> parentTree, boolean isPlusChild, boolean leafFromInstance) {
            if (((Boolean) leaf.getAttribute()).booleanValue()) {
                RegionFactory regionFactory = RegionFactory.this;
                if (!leafFromInstance) {
                    tree = leaf;
                }
                BSPTree<S> argTree = regionFactory.recurseComplement(tree);
                argTree.insertInTree(parentTree, isPlusChild, this);
                return argTree;
            }
            BSPTree<S> instanceTree;
            if (leafFromInstance) {
                instanceTree = leaf;
            } else {
                instanceTree = tree;
            }
            instanceTree.insertInTree(parentTree, isPlusChild, this);
            return instanceTree;
        }

        public BSPTree<S> fixNode(BSPTree<S> node) {
            Point<S> p = this.region1.buildNew(node.pruneAroundConvexCell(Boolean.TRUE, Boolean.FALSE, null)).getBarycenter();
            boolean z = this.region1.checkPoint(p) == Location.INSIDE && this.region2.checkPoint(p) == Location.OUTSIDE;
            return new BSPTree(Boolean.valueOf(z));
        }
    }

    private class IntersectionMerger implements LeafMerger<S> {
        private IntersectionMerger() {
        }

        public BSPTree<S> merge(BSPTree<S> leaf, BSPTree<S> tree, BSPTree<S> parentTree, boolean isPlusChild, boolean leafFromInstance) {
            if (((Boolean) leaf.getAttribute()).booleanValue()) {
                tree.insertInTree(parentTree, isPlusChild, new VanishingToLeaf(true));
                return tree;
            }
            leaf.insertInTree(parentTree, isPlusChild, new VanishingToLeaf(false));
            return leaf;
        }
    }

    private class NodesCleaner implements BSPTreeVisitor<S> {
        private NodesCleaner() {
        }

        public Order visitOrder(BSPTree<S> bSPTree) {
            return Order.PLUS_SUB_MINUS;
        }

        public void visitInternalNode(BSPTree<S> node) {
            node.setAttribute(null);
        }

        public void visitLeafNode(BSPTree<S> bSPTree) {
        }
    }

    private class UnionMerger implements LeafMerger<S> {
        private UnionMerger() {
        }

        public BSPTree<S> merge(BSPTree<S> leaf, BSPTree<S> tree, BSPTree<S> parentTree, boolean isPlusChild, boolean leafFromInstance) {
            if (((Boolean) leaf.getAttribute()).booleanValue()) {
                leaf.insertInTree(parentTree, isPlusChild, new VanishingToLeaf(true));
                return leaf;
            }
            tree.insertInTree(parentTree, isPlusChild, new VanishingToLeaf(false));
            return tree;
        }
    }

    private class VanishingToLeaf implements VanishingCutHandler<S> {
        private final boolean inside;

        public VanishingToLeaf(boolean inside) {
            this.inside = inside;
        }

        public BSPTree<S> fixNode(BSPTree<S> node) {
            if (node.getPlus().getAttribute().equals(node.getMinus().getAttribute())) {
                return new BSPTree(node.getPlus().getAttribute());
            }
            return new BSPTree(Boolean.valueOf(this.inside));
        }
    }

    private class XorMerger implements LeafMerger<S> {
        private XorMerger() {
        }

        public BSPTree<S> merge(BSPTree<S> leaf, BSPTree<S> tree, BSPTree<S> parentTree, boolean isPlusChild, boolean leafFromInstance) {
            BSPTree<S> t = tree;
            if (((Boolean) leaf.getAttribute()).booleanValue()) {
                t = RegionFactory.this.recurseComplement(t);
            }
            t.insertInTree(parentTree, isPlusChild, new VanishingToLeaf(true));
            return t;
        }
    }

    public RegionFactory() {
        this.nodeCleaner = new NodesCleaner();
    }

    public Region<S> buildConvex(Hyperplane<S>... hyperplanes) {
        int i = 0;
        if (hyperplanes == null || hyperplanes.length == 0) {
            return null;
        }
        Region<S> region = hyperplanes[0].wholeSpace();
        BSPTree<S> node = region.getTree(false);
        node.setAttribute(Boolean.TRUE);
        int length = hyperplanes.length;
        while (i < length) {
            if (node.insertCut(hyperplanes[i])) {
                node.setAttribute(null);
                node.getPlus().setAttribute(Boolean.FALSE);
                node = node.getMinus();
                node.setAttribute(Boolean.TRUE);
            }
            i++;
        }
        return region;
    }

    public Region<S> union(Region<S> region1, Region<S> region2) {
        BSPTree<S> tree = region1.getTree(false).merge(region2.getTree(false), new UnionMerger());
        tree.visit(this.nodeCleaner);
        return region1.buildNew(tree);
    }

    public Region<S> intersection(Region<S> region1, Region<S> region2) {
        BSPTree<S> tree = region1.getTree(false).merge(region2.getTree(false), new IntersectionMerger());
        tree.visit(this.nodeCleaner);
        return region1.buildNew(tree);
    }

    public Region<S> xor(Region<S> region1, Region<S> region2) {
        BSPTree<S> tree = region1.getTree(false).merge(region2.getTree(false), new XorMerger());
        tree.visit(this.nodeCleaner);
        return region1.buildNew(tree);
    }

    public Region<S> difference(Region<S> region1, Region<S> region2) {
        BSPTree<S> tree = region1.getTree(false).merge(region2.getTree(false), new DifferenceMerger(region1, region2));
        tree.visit(this.nodeCleaner);
        return region1.buildNew(tree);
    }

    public Region<S> getComplement(Region<S> region) {
        return region.buildNew(recurseComplement(region.getTree(false)));
    }

    private BSPTree<S> recurseComplement(BSPTree<S> node) {
        Map<BSPTree<S>, BSPTree<S>> map = new HashMap();
        BSPTree<S> transformedTree = recurseComplement(node, map);
        for (Entry<BSPTree<S>, BSPTree<S>> entry : map.entrySet()) {
            if (((BSPTree) entry.getKey()).getCut() != null) {
                BoundaryAttribute<S> original = (BoundaryAttribute) ((BSPTree) entry.getKey()).getAttribute();
                if (original != null) {
                    BoundaryAttribute<S> transformed = (BoundaryAttribute) ((BSPTree) entry.getValue()).getAttribute();
                    Iterator it = original.getSplitters().iterator();
                    while (it.hasNext()) {
                        transformed.getSplitters().add((BSPTree) map.get((BSPTree) it.next()));
                    }
                }
            }
        }
        return transformedTree;
    }

    private BSPTree<S> recurseComplement(BSPTree<S> node, Map<BSPTree<S>, BSPTree<S>> map) {
        BSPTree<S> transformedNode;
        if (node.getCut() == null) {
            Object obj;
            if (((Boolean) node.getAttribute()).booleanValue()) {
                obj = Boolean.FALSE;
            } else {
                obj = Boolean.TRUE;
            }
            transformedNode = new BSPTree(obj);
        } else {
            BoundaryAttribute<S> attribute = (BoundaryAttribute) node.getAttribute();
            if (attribute != null) {
                attribute = new BoundaryAttribute(attribute.getPlusInside() == null ? null : attribute.getPlusInside().copySelf(), attribute.getPlusOutside() == null ? null : attribute.getPlusOutside().copySelf(), new NodesSet());
            }
            transformedNode = new BSPTree(node.getCut().copySelf(), recurseComplement(node.getPlus(), map), recurseComplement(node.getMinus(), map), attribute);
        }
        map.put(node, transformedNode);
        return transformedNode;
    }
}
