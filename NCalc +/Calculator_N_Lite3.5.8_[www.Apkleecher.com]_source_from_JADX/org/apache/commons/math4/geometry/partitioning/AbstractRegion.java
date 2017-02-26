package org.apache.commons.math4.geometry.partitioning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;
import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.geometry.Vector;
import org.apache.commons.math4.geometry.partitioning.BSPTreeVisitor.Order;
import org.apache.commons.math4.geometry.partitioning.Region.Location;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane.SplitSubHyperplane;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public abstract class AbstractRegion<S extends Space, T extends Space> implements Region<S> {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$geometry$partitioning$Side;
    private Point<S> barycenter;
    private double size;
    private final double tolerance;
    private BSPTree<S> tree;

    class 1 implements Comparator<SubHyperplane<S>> {
        1() {
        }

        public int compare(SubHyperplane<S> o1, SubHyperplane<S> o2) {
            if (o2.getSize() < o1.getSize()) {
                return -1;
            }
            return o1 == o2 ? 0 : 1;
        }
    }

    class 2 implements BSPTreeVisitor<S> {
        2() {
        }

        public Order visitOrder(BSPTree<S> bSPTree) {
            return Order.PLUS_SUB_MINUS;
        }

        public void visitInternalNode(BSPTree<S> bSPTree) {
        }

        public void visitLeafNode(BSPTree<S> node) {
            if (node.getParent() == null || node == node.getParent().getMinus()) {
                node.setAttribute(Boolean.TRUE);
            } else {
                node.setAttribute(Boolean.FALSE);
            }
        }
    }

    public abstract AbstractRegion<S, T> buildNew(BSPTree<S> bSPTree);

    protected abstract void computeGeometricalProperties();

    static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$geometry$partitioning$Side() {
        int[] iArr = $SWITCH_TABLE$org$apache$commons$math4$geometry$partitioning$Side;
        if (iArr == null) {
            iArr = new int[Side.values().length];
            try {
                iArr[Side.BOTH.ordinal()] = 3;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Side.HYPER.ordinal()] = 4;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[Side.MINUS.ordinal()] = 2;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[Side.PLUS.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            $SWITCH_TABLE$org$apache$commons$math4$geometry$partitioning$Side = iArr;
        }
        return iArr;
    }

    protected AbstractRegion(double tolerance) {
        this.tree = new BSPTree(Boolean.TRUE);
        this.tolerance = tolerance;
    }

    protected AbstractRegion(BSPTree<S> tree, double tolerance) {
        this.tree = tree;
        this.tolerance = tolerance;
    }

    protected AbstractRegion(Collection<SubHyperplane<S>> boundary, double tolerance) {
        this.tolerance = tolerance;
        if (boundary.size() == 0) {
            this.tree = new BSPTree(Boolean.TRUE);
            return;
        }
        TreeSet<SubHyperplane<S>> ordered = new TreeSet(new 1());
        ordered.addAll(boundary);
        this.tree = new BSPTree();
        insertCuts(this.tree, ordered);
        this.tree.visit(new 2());
    }

    public AbstractRegion(Hyperplane<S>[] hyperplanes, double tolerance) {
        int i = 0;
        this.tolerance = tolerance;
        if (hyperplanes == null || hyperplanes.length == 0) {
            this.tree = new BSPTree(Boolean.FALSE);
            return;
        }
        this.tree = hyperplanes[0].wholeSpace().getTree(false);
        BSPTree<S> node = this.tree;
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
    }

    public double getTolerance() {
        return this.tolerance;
    }

    private void insertCuts(BSPTree<S> node, Collection<SubHyperplane<S>> boundary) {
        Iterator<SubHyperplane<S>> iterator = boundary.iterator();
        Hyperplane<S> inserted = null;
        while (inserted == null && iterator.hasNext()) {
            inserted = ((SubHyperplane) iterator.next()).getHyperplane();
            if (!node.insertCut(inserted.copySelf())) {
                inserted = null;
            }
        }
        if (iterator.hasNext()) {
            ArrayList<SubHyperplane<S>> plusList = new ArrayList();
            ArrayList<SubHyperplane<S>> minusList = new ArrayList();
            while (iterator.hasNext()) {
                SubHyperplane<S> other = (SubHyperplane) iterator.next();
                switch ($SWITCH_TABLE$org$apache$commons$math4$geometry$partitioning$Side()[other.side(inserted).ordinal()]) {
                    case ValueServer.REPLAY_MODE /*1*/:
                        plusList.add(other);
                        break;
                    case IExpr.DOUBLEID /*2*/:
                        minusList.add(other);
                        break;
                    case ValueServer.EXPONENTIAL_MODE /*3*/:
                        SplitSubHyperplane<S> split = other.split(inserted);
                        plusList.add(split.getPlus());
                        minusList.add(split.getMinus());
                        break;
                    default:
                        break;
                }
            }
            insertCuts(node.getPlus(), plusList);
            insertCuts(node.getMinus(), minusList);
        }
    }

    public AbstractRegion<S, T> copySelf() {
        return buildNew(this.tree.copySelf());
    }

    public boolean isEmpty() {
        return isEmpty(this.tree);
    }

    public boolean isEmpty(BSPTree<S> node) {
        if (node.getCut() != null) {
            return isEmpty(node.getMinus()) && isEmpty(node.getPlus());
        } else {
            if (((Boolean) node.getAttribute()).booleanValue()) {
                return false;
            }
            return true;
        }
    }

    public boolean isFull() {
        return isFull(this.tree);
    }

    public boolean isFull(BSPTree<S> node) {
        if (node.getCut() == null) {
            return ((Boolean) node.getAttribute()).booleanValue();
        }
        return isFull(node.getMinus()) && isFull(node.getPlus());
    }

    public boolean contains(Region<S> region) {
        return new RegionFactory().difference(region, this).isEmpty();
    }

    public BoundaryProjection<S> projectToBoundary(Point<S> point) {
        BoundaryProjector<S, T> projector = new BoundaryProjector(point);
        getTree(true).visit(projector);
        return projector.getProjection();
    }

    public Location checkPoint(Vector<S> point) {
        return checkPoint((Point) point);
    }

    public Location checkPoint(Point<S> point) {
        return checkPoint(this.tree, (Point) point);
    }

    protected Location checkPoint(BSPTree<S> node, Vector<S> point) {
        return checkPoint((BSPTree) node, (Point) point);
    }

    protected Location checkPoint(BSPTree<S> node, Point<S> point) {
        BSPTree<S> cell = node.getCell(point, this.tolerance);
        if (cell.getCut() == null) {
            return ((Boolean) cell.getAttribute()).booleanValue() ? Location.INSIDE : Location.OUTSIDE;
        } else {
            Location minusCode = checkPoint(cell.getMinus(), (Point) point);
            if (minusCode != checkPoint(cell.getPlus(), (Point) point)) {
                minusCode = Location.BOUNDARY;
            }
            return minusCode;
        }
    }

    public BSPTree<S> getTree(boolean includeBoundaryAttributes) {
        if (includeBoundaryAttributes && this.tree.getCut() != null && this.tree.getAttribute() == null) {
            this.tree.visit(new BoundaryBuilder());
        }
        return this.tree;
    }

    public double getBoundarySize() {
        BoundarySizeVisitor<S> visitor = new BoundarySizeVisitor();
        getTree(true).visit(visitor);
        return visitor.getSize();
    }

    public double getSize() {
        if (this.barycenter == null) {
            computeGeometricalProperties();
        }
        return this.size;
    }

    protected void setSize(double size) {
        this.size = size;
    }

    public Point<S> getBarycenter() {
        if (this.barycenter == null) {
            computeGeometricalProperties();
        }
        return this.barycenter;
    }

    protected void setBarycenter(Vector<S> barycenter) {
        setBarycenter((Point) barycenter);
    }

    protected void setBarycenter(Point<S> barycenter) {
        this.barycenter = barycenter;
    }

    public Side side(Hyperplane<S> hyperplane) {
        InsideFinder<S> finder = new InsideFinder(this);
        finder.recurseSides(this.tree, hyperplane.wholeHyperplane());
        return finder.plusFound() ? finder.minusFound() ? Side.BOTH : Side.PLUS : finder.minusFound() ? Side.MINUS : Side.HYPER;
    }

    public SubHyperplane<S> intersection(SubHyperplane<S> sub) {
        return recurseIntersection(this.tree, sub);
    }

    private SubHyperplane<S> recurseIntersection(BSPTree<S> node, SubHyperplane<S> sub) {
        if (node.getCut() != null) {
            Hyperplane<S> hyperplane = node.getCut().getHyperplane();
            switch ($SWITCH_TABLE$org$apache$commons$math4$geometry$partitioning$Side()[sub.side(hyperplane).ordinal()]) {
                case ValueServer.REPLAY_MODE /*1*/:
                    return recurseIntersection(node.getPlus(), sub);
                case IExpr.DOUBLEID /*2*/:
                    return recurseIntersection(node.getMinus(), sub);
                case ValueServer.EXPONENTIAL_MODE /*3*/:
                    SplitSubHyperplane<S> split = sub.split(hyperplane);
                    SubHyperplane<S> plus = recurseIntersection(node.getPlus(), split.getPlus());
                    SubHyperplane<S> minus = recurseIntersection(node.getMinus(), split.getMinus());
                    if (plus == null) {
                        return minus;
                    }
                    if (minus == null) {
                        return plus;
                    }
                    return plus.reunite(minus);
                default:
                    return recurseIntersection(node.getPlus(), recurseIntersection(node.getMinus(), sub));
            }
        } else if (((Boolean) node.getAttribute()).booleanValue()) {
            return sub.copySelf();
        } else {
            return null;
        }
    }

    public AbstractRegion<S, T> applyTransform(Transform<S, T> transform) {
        Map<BSPTree<S>, BSPTree<S>> map = new HashMap();
        BSPTree transformedTree = recurseTransform(getTree(false), transform, map);
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
        return buildNew(transformedTree);
    }

    private BSPTree<S> recurseTransform(BSPTree<S> node, Transform<S, T> transform, Map<BSPTree<S>, BSPTree<S>> map) {
        BSPTree<S> transformedNode;
        if (node.getCut() == null) {
            transformedNode = new BSPTree(node.getAttribute());
        } else {
            SubHyperplane<S> tSub = ((AbstractSubHyperplane) node.getCut()).applyTransform(transform);
            BoundaryAttribute<S> attribute = (BoundaryAttribute) node.getAttribute();
            if (attribute != null) {
                SubHyperplane<S> tPO;
                SubHyperplane<S> tPI;
                if (attribute.getPlusOutside() == null) {
                    tPO = null;
                } else {
                    tPO = ((AbstractSubHyperplane) attribute.getPlusOutside()).applyTransform(transform);
                }
                if (attribute.getPlusInside() == null) {
                    tPI = null;
                } else {
                    tPI = ((AbstractSubHyperplane) attribute.getPlusInside()).applyTransform(transform);
                }
                attribute = new BoundaryAttribute(tPO, tPI, new NodesSet());
            }
            transformedNode = new BSPTree(tSub, recurseTransform(node.getPlus(), transform, map), recurseTransform(node.getMinus(), transform, map), attribute);
        }
        map.put(node, transformedNode);
        return transformedNode;
    }
}
