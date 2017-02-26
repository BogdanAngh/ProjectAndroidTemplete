package org.apache.commons.math4.geometry.spherical.oned;

import com.example.duy.calculator.geom2d.util.Angle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.MathInternalError;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.partitioning.AbstractRegion;
import org.apache.commons.math4.geometry.partitioning.BSPTree;
import org.apache.commons.math4.geometry.partitioning.BoundaryProjection;
import org.apache.commons.math4.geometry.partitioning.Side;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathUtils;
import org.apache.commons.math4.util.Precision;

public class ArcsSet extends AbstractRegion<Sphere1D, Sphere1D> implements Iterable<double[]> {

    public static class InconsistentStateAt2PiWrapping extends MathIllegalArgumentException {
        private static final long serialVersionUID = 20140107;

        public InconsistentStateAt2PiWrapping() {
            super(LocalizedFormats.INCONSISTENT_STATE_AT_2_PI_WRAPPING, new Object[0]);
        }
    }

    public static class Split {
        private final ArcsSet minus;
        private final ArcsSet plus;

        private Split(ArcsSet plus, ArcsSet minus) {
            this.plus = plus;
            this.minus = minus;
        }

        public ArcsSet getPlus() {
            return this.plus;
        }

        public ArcsSet getMinus() {
            return this.minus;
        }
    }

    private class SubArcsIterator implements Iterator<double[]> {
        private BSPTree<Sphere1D> current;
        private final BSPTree<Sphere1D> firstStart;
        private double[] pending;

        public SubArcsIterator() {
            this.firstStart = ArcsSet.this.getFirstArcStart();
            this.current = this.firstStart;
            if (this.firstStart != null) {
                selectPending();
            } else if (((Boolean) ArcsSet.this.getFirstLeaf(ArcsSet.this.getTree(false)).getAttribute()).booleanValue()) {
                this.pending = new double[]{0.0d, Angle2D.M_2PI};
            } else {
                this.pending = null;
            }
        }

        private void selectPending() {
            BSPTree<Sphere1D> start = this.current;
            while (start != null && !ArcsSet.this.isArcStart(start)) {
                start = ArcsSet.this.nextInternalNode(start);
            }
            if (start == null) {
                this.current = null;
                this.pending = null;
                return;
            }
            BSPTree<Sphere1D> end = start;
            while (end != null && !ArcsSet.this.isArcEnd(end)) {
                end = ArcsSet.this.nextInternalNode(end);
            }
            if (end != null) {
                this.pending = new double[]{ArcsSet.this.getAngle(start), ArcsSet.this.getAngle(end)};
                this.current = end;
                return;
            }
            end = this.firstStart;
            while (end != null && !ArcsSet.this.isArcEnd(end)) {
                end = ArcsSet.this.previousInternalNode(end);
            }
            if (end == null) {
                throw new MathInternalError();
            }
            this.pending = new double[]{ArcsSet.this.getAngle(start), ArcsSet.this.getAngle(end) + Angle2D.M_2PI};
            this.current = null;
        }

        public boolean hasNext() {
            return this.pending != null;
        }

        public double[] next() {
            if (this.pending == null) {
                throw new NoSuchElementException();
            }
            double[] next = this.pending;
            selectPending();
            return next;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public ArcsSet(double tolerance) {
        super(tolerance);
    }

    public ArcsSet(double lower, double upper, double tolerance) throws NumberIsTooLargeException {
        super(buildTree(lower, upper, tolerance), tolerance);
    }

    public ArcsSet(BSPTree<Sphere1D> tree, double tolerance) throws InconsistentStateAt2PiWrapping {
        super((BSPTree) tree, tolerance);
        check2PiConsistency();
    }

    public ArcsSet(Collection<SubHyperplane<Sphere1D>> boundary, double tolerance) throws InconsistentStateAt2PiWrapping {
        super((Collection) boundary, tolerance);
        check2PiConsistency();
    }

    private static BSPTree<Sphere1D> buildTree(double lower, double upper, double tolerance) throws NumberIsTooLargeException {
        if (Precision.equals(lower, upper, 0) || upper - lower >= Angle2D.M_2PI) {
            return new BSPTree(Boolean.TRUE);
        }
        if (lower > upper) {
            throw new NumberIsTooLargeException(LocalizedFormats.ENDPOINTS_NOT_AN_INTERVAL, Double.valueOf(lower), Double.valueOf(upper), true);
        }
        double normalizedLower = MathUtils.normalizeAngle(lower, FastMath.PI);
        double normalizedUpper = normalizedLower + (upper - lower);
        SubHyperplane<Sphere1D> lowerCut = new LimitAngle(new S1Point(normalizedLower), false, tolerance).wholeHyperplane();
        if (normalizedUpper > Angle2D.M_2PI) {
            return new BSPTree(lowerCut, new BSPTree(new LimitAngle(new S1Point(normalizedUpper - Angle2D.M_2PI), true, tolerance).wholeHyperplane(), new BSPTree(Boolean.FALSE), new BSPTree(Boolean.TRUE), null), new BSPTree(Boolean.TRUE), null);
        }
        return new BSPTree(lowerCut, new BSPTree(Boolean.FALSE), new BSPTree(new LimitAngle(new S1Point(normalizedUpper), true, tolerance).wholeHyperplane(), new BSPTree(Boolean.FALSE), new BSPTree(Boolean.TRUE), null), null);
    }

    private void check2PiConsistency() throws InconsistentStateAt2PiWrapping {
        BSPTree<Sphere1D> root = getTree(false);
        if (root.getCut() != null) {
            if ((((Boolean) getFirstLeaf(root).getAttribute()).booleanValue() ^ ((Boolean) getLastLeaf(root).getAttribute()).booleanValue()) != 0) {
                throw new InconsistentStateAt2PiWrapping();
            }
        }
    }

    private BSPTree<Sphere1D> getFirstLeaf(BSPTree<Sphere1D> root) {
        if (root.getCut() == null) {
            return root;
        }
        BSPTree<Sphere1D> smallest = null;
        BSPTree<Sphere1D> n = root;
        while (n != null) {
            smallest = n;
            n = previousInternalNode(n);
        }
        return leafBefore(smallest);
    }

    private BSPTree<Sphere1D> getLastLeaf(BSPTree<Sphere1D> root) {
        if (root.getCut() == null) {
            return root;
        }
        BSPTree<Sphere1D> largest = null;
        BSPTree<Sphere1D> n = root;
        while (n != null) {
            largest = n;
            n = nextInternalNode(n);
        }
        return leafAfter(largest);
    }

    private BSPTree<Sphere1D> getFirstArcStart() {
        BSPTree<Sphere1D> node = getTree(false);
        if (node.getCut() == null) {
            return null;
        }
        node = getFirstLeaf(node).getParent();
        while (node != null && !isArcStart(node)) {
            node = nextInternalNode(node);
        }
        return node;
    }

    private boolean isArcStart(BSPTree<Sphere1D> node) {
        if (((Boolean) leafBefore(node).getAttribute()).booleanValue()) {
            return false;
        }
        if (((Boolean) leafAfter(node).getAttribute()).booleanValue()) {
            return true;
        }
        return false;
    }

    private boolean isArcEnd(BSPTree<Sphere1D> node) {
        if (!((Boolean) leafBefore(node).getAttribute()).booleanValue()) {
            return false;
        }
        if (((Boolean) leafAfter(node).getAttribute()).booleanValue()) {
            return false;
        }
        return true;
    }

    private BSPTree<Sphere1D> nextInternalNode(BSPTree<Sphere1D> node) {
        if (childAfter(node).getCut() != null) {
            return leafAfter(node).getParent();
        }
        while (isAfterParent(node)) {
            node = node.getParent();
        }
        return node.getParent();
    }

    private BSPTree<Sphere1D> previousInternalNode(BSPTree<Sphere1D> node) {
        if (childBefore(node).getCut() != null) {
            return leafBefore(node).getParent();
        }
        while (isBeforeParent(node)) {
            node = node.getParent();
        }
        return node.getParent();
    }

    private BSPTree<Sphere1D> leafBefore(BSPTree<Sphere1D> node) {
        node = childBefore(node);
        while (node.getCut() != null) {
            node = childAfter(node);
        }
        return node;
    }

    private BSPTree<Sphere1D> leafAfter(BSPTree<Sphere1D> node) {
        node = childAfter(node);
        while (node.getCut() != null) {
            node = childBefore(node);
        }
        return node;
    }

    private boolean isBeforeParent(BSPTree<Sphere1D> node) {
        BSPTree<Sphere1D> parent = node.getParent();
        if (parent != null && node == childBefore(parent)) {
            return true;
        }
        return false;
    }

    private boolean isAfterParent(BSPTree<Sphere1D> node) {
        BSPTree<Sphere1D> parent = node.getParent();
        if (parent != null && node == childAfter(parent)) {
            return true;
        }
        return false;
    }

    private BSPTree<Sphere1D> childBefore(BSPTree<Sphere1D> node) {
        if (isDirect(node)) {
            return node.getMinus();
        }
        return node.getPlus();
    }

    private BSPTree<Sphere1D> childAfter(BSPTree<Sphere1D> node) {
        if (isDirect(node)) {
            return node.getPlus();
        }
        return node.getMinus();
    }

    private boolean isDirect(BSPTree<Sphere1D> node) {
        return ((LimitAngle) node.getCut().getHyperplane()).isDirect();
    }

    private double getAngle(BSPTree<Sphere1D> node) {
        return ((LimitAngle) node.getCut().getHyperplane()).getLocation().getAlpha();
    }

    public ArcsSet buildNew(BSPTree<Sphere1D> tree) {
        return new ArcsSet((BSPTree) tree, getTolerance());
    }

    protected void computeGeometricalProperties() {
        if (getTree(false).getCut() == null) {
            double d;
            setBarycenter(S1Point.NaN);
            if (((Boolean) getTree(false).getAttribute()).booleanValue()) {
                d = Angle2D.M_2PI;
            } else {
                d = 0.0d;
            }
            setSize(d);
            return;
        }
        double size = 0.0d;
        double sum = 0.0d;
        Iterator it = iterator();
        while (it.hasNext()) {
            double[] a = (double[]) it.next();
            double length = a[1] - a[0];
            size += length;
            sum += (a[0] + a[1]) * length;
        }
        setSize(size);
        if (Precision.equals(size, Angle2D.M_2PI, 0)) {
            setBarycenter(S1Point.NaN);
        } else if (size >= Precision.SAFE_MIN) {
            setBarycenter(new S1Point(sum / (2.0d * size)));
        } else {
            setBarycenter(((LimitAngle) getTree(false).getCut().getHyperplane()).getLocation());
        }
    }

    public BoundaryProjection<Sphere1D> projectToBoundary(Point<Sphere1D> point) {
        double previousOffset;
        double currentOffset;
        double alpha = ((S1Point) point).getAlpha();
        boolean wrapFirst = false;
        double first = Double.NaN;
        double previous = Double.NaN;
        Iterator it = iterator();
        while (it.hasNext()) {
            double[] a = (double[]) it.next();
            if (Double.isNaN(first)) {
                first = a[0];
            }
            if (!wrapFirst) {
                if (alpha < a[0]) {
                    if (Double.isNaN(previous)) {
                        wrapFirst = true;
                    } else {
                        previousOffset = alpha - previous;
                        currentOffset = a[0] - alpha;
                        if (previousOffset < currentOffset) {
                            return new BoundaryProjection(point, new S1Point(previous), previousOffset);
                        }
                        return new BoundaryProjection(point, new S1Point(a[0]), currentOffset);
                    }
                } else if (alpha <= a[1]) {
                    double offset0 = a[0] - alpha;
                    double offset1 = alpha - a[1];
                    if (offset0 < offset1) {
                        return new BoundaryProjection(point, new S1Point(a[1]), offset1);
                    }
                    return new BoundaryProjection(point, new S1Point(a[0]), offset0);
                }
            }
            previous = a[1];
        }
        if (Double.isNaN(previous)) {
            return new BoundaryProjection(point, null, Angle2D.M_2PI);
        }
        if (wrapFirst) {
            previousOffset = alpha - (previous - Angle2D.M_2PI);
            currentOffset = first - alpha;
            if (previousOffset < currentOffset) {
                return new BoundaryProjection(point, new S1Point(previous), previousOffset);
            }
            return new BoundaryProjection(point, new S1Point(first), currentOffset);
        }
        previousOffset = alpha - previous;
        currentOffset = (Angle2D.M_2PI + first) - alpha;
        if (previousOffset < currentOffset) {
            return new BoundaryProjection(point, new S1Point(previous), previousOffset);
        }
        return new BoundaryProjection(point, new S1Point(first), currentOffset);
    }

    public List<Arc> asList() {
        List<Arc> list = new ArrayList();
        Iterator it = iterator();
        while (it.hasNext()) {
            double[] a = (double[]) it.next();
            list.add(new Arc(a[0], a[1], getTolerance()));
        }
        return list;
    }

    public Iterator<double[]> iterator() {
        return new SubArcsIterator();
    }

    public Side side(Arc arc) {
        double reference = FastMath.PI + arc.getInf();
        double arcLength = arc.getSup() - arc.getInf();
        boolean inMinus = false;
        boolean inPlus = false;
        Iterator it = iterator();
        while (it.hasNext()) {
            double[] a = (double[]) it.next();
            double syncedStart = MathUtils.normalizeAngle(a[0], reference) - arc.getInf();
            double syncedEnd = a[1] - (a[0] - syncedStart);
            if (syncedStart <= arcLength - getTolerance() || syncedEnd >= Angle2D.M_2PI + getTolerance()) {
                inMinus = true;
            }
            if (syncedEnd >= getTolerance() + arcLength) {
                inPlus = true;
            }
        }
        if (inMinus) {
            if (inPlus) {
                return Side.BOTH;
            }
            return Side.MINUS;
        } else if (inPlus) {
            return Side.PLUS;
        } else {
            return Side.HYPER;
        }
    }

    public Split split(Arc arc) {
        List<Double> minus = new ArrayList();
        List<Double> plus = new ArrayList();
        double reference = FastMath.PI + arc.getInf();
        double arcLength = arc.getSup() - arc.getInf();
        Iterator it = iterator();
        while (it.hasNext()) {
            double[] a = (double[]) it.next();
            double syncedStart = MathUtils.normalizeAngle(a[0], reference) - arc.getInf();
            double arcOffset = a[0] - syncedStart;
            double syncedEnd = a[1] - arcOffset;
            double minusToPlus;
            double plusToMinus;
            if (syncedStart < arcLength) {
                minus.add(Double.valueOf(a[0]));
                if (syncedEnd > arcLength) {
                    minusToPlus = arcLength + arcOffset;
                    minus.add(Double.valueOf(minusToPlus));
                    plus.add(Double.valueOf(minusToPlus));
                    if (syncedEnd > Angle2D.M_2PI) {
                        plusToMinus = Angle2D.M_2PI + arcOffset;
                        plus.add(Double.valueOf(plusToMinus));
                        minus.add(Double.valueOf(plusToMinus));
                        minus.add(Double.valueOf(a[1]));
                    } else {
                        plus.add(Double.valueOf(a[1]));
                    }
                } else {
                    minus.add(Double.valueOf(a[1]));
                }
            } else {
                plus.add(Double.valueOf(a[0]));
                if (syncedEnd > Angle2D.M_2PI) {
                    plusToMinus = Angle2D.M_2PI + arcOffset;
                    plus.add(Double.valueOf(plusToMinus));
                    minus.add(Double.valueOf(plusToMinus));
                    if (syncedEnd > Angle2D.M_2PI + arcLength) {
                        minusToPlus = (Angle2D.M_2PI + arcLength) + arcOffset;
                        minus.add(Double.valueOf(minusToPlus));
                        plus.add(Double.valueOf(minusToPlus));
                        plus.add(Double.valueOf(a[1]));
                    } else {
                        minus.add(Double.valueOf(a[1]));
                    }
                } else {
                    plus.add(Double.valueOf(a[1]));
                }
            }
        }
        return new Split(createSplitPart(minus), null);
    }

    private void addArcLimit(BSPTree<Sphere1D> tree, double alpha, boolean isStart) {
        LimitAngle limit = new LimitAngle(new S1Point(alpha), !isStart, getTolerance());
        BSPTree<Sphere1D> node = tree.getCell(limit.getLocation(), getTolerance());
        if (node.getCut() != null) {
            throw new MathInternalError();
        }
        node.insertCut(limit);
        node.setAttribute(null);
        node.getPlus().setAttribute(Boolean.FALSE);
        node.getMinus().setAttribute(Boolean.TRUE);
    }

    private ArcsSet createSplitPart(List<Double> limits) {
        if (limits.isEmpty()) {
            return null;
        }
        int i = 0;
        while (i < limits.size()) {
            int j = (i + 1) % limits.size();
            double lA = ((Double) limits.get(i)).doubleValue();
            if (FastMath.abs(MathUtils.normalizeAngle(((Double) limits.get(j)).doubleValue(), lA) - lA) <= getTolerance()) {
                if (j > 0) {
                    limits.remove(j);
                    limits.remove(i);
                    i--;
                } else {
                    double lEnd = ((Double) limits.remove(limits.size() - 1)).doubleValue();
                    double lStart = ((Double) limits.remove(0)).doubleValue();
                    if (!limits.isEmpty()) {
                        limits.add(Double.valueOf(((Double) limits.remove(0)).doubleValue() + Angle2D.M_2PI));
                    } else if (lEnd - lStart <= FastMath.PI) {
                        return null;
                    } else {
                        return new ArcsSet(new BSPTree(Boolean.TRUE), getTolerance());
                    }
                }
            }
            i++;
        }
        BSPTree tree = new BSPTree(Boolean.FALSE);
        for (i = 0; i < limits.size() - 1; i += 2) {
            addArcLimit(tree, ((Double) limits.get(i)).doubleValue(), true);
            addArcLimit(tree, ((Double) limits.get(i + 1)).doubleValue(), false);
        }
        if (tree.getCut() == null) {
            return null;
        }
        return new ArcsSet(tree, getTolerance());
    }
}
