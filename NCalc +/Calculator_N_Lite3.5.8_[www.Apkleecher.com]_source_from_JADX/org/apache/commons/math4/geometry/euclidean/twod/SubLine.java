package org.apache.commons.math4.geometry.euclidean.twod;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.euclidean.oned.Euclidean1D;
import org.apache.commons.math4.geometry.euclidean.oned.Interval;
import org.apache.commons.math4.geometry.euclidean.oned.IntervalsSet;
import org.apache.commons.math4.geometry.euclidean.oned.OrientedPoint;
import org.apache.commons.math4.geometry.euclidean.oned.Vector1D;
import org.apache.commons.math4.geometry.partitioning.AbstractSubHyperplane;
import org.apache.commons.math4.geometry.partitioning.BSPTree;
import org.apache.commons.math4.geometry.partitioning.Hyperplane;
import org.apache.commons.math4.geometry.partitioning.Region;
import org.apache.commons.math4.geometry.partitioning.Region.Location;
import org.apache.commons.math4.geometry.partitioning.Side;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane.SplitSubHyperplane;
import org.apache.commons.math4.linear.CholeskyDecomposition;
import org.apache.commons.math4.util.FastMath;

public class SubLine extends AbstractSubHyperplane<Euclidean2D, Euclidean1D> {
    public SubLine(Hyperplane<Euclidean2D> hyperplane, Region<Euclidean1D> remainingRegion) {
        super(hyperplane, remainingRegion);
    }

    public SubLine(Vector2D start, Vector2D end, double tolerance) {
        super(new Line(start, end, tolerance), buildIntervalSet(start, end, tolerance));
    }

    public SubLine(Segment segment) {
        super(segment.getLine(), buildIntervalSet(segment.getStart(), segment.getEnd(), segment.getLine().getTolerance()));
    }

    public List<Segment> getSegments() {
        Line line = (Line) getHyperplane();
        List<Interval> list = ((IntervalsSet) getRemainingRegion()).asList();
        List<Segment> segments = new ArrayList(list.size());
        for (Interval interval : list) {
            segments.add(new Segment(line.toSpace(new Vector1D(interval.getInf())), line.toSpace(new Vector1D(interval.getSup())), line));
        }
        return segments;
    }

    public Vector2D intersection(SubLine subLine, boolean includeEndPoints) {
        Line line1 = (Line) getHyperplane();
        Line line2 = (Line) subLine.getHyperplane();
        Point v2D = line1.intersection(line2);
        if (v2D == null) {
            return null;
        }
        Location loc1 = getRemainingRegion().checkPoint(line1.toSubSpace(v2D));
        Location loc2 = subLine.getRemainingRegion().checkPoint(line2.toSubSpace(v2D));
        if (!includeEndPoints) {
            return (loc1 == Location.INSIDE && loc2 == Location.INSIDE) ? v2D : null;
        } else {
            if (loc1 == Location.OUTSIDE || loc2 == Location.OUTSIDE) {
                return null;
            }
            return v2D;
        }
    }

    private static IntervalsSet buildIntervalSet(Vector2D start, Vector2D end, double tolerance) {
        Line line = new Line(start, end, tolerance);
        return new IntervalsSet(line.toSubSpace((Point) start).getX(), line.toSubSpace((Point) end).getX(), tolerance);
    }

    protected AbstractSubHyperplane<Euclidean2D, Euclidean1D> buildNew(Hyperplane<Euclidean2D> hyperplane, Region<Euclidean1D> remainingRegion) {
        return new SubLine(hyperplane, remainingRegion);
    }

    public Side side(Hyperplane<Euclidean2D> hyperplane) {
        Line thisLine = (Line) getHyperplane();
        Line otherLine = (Line) hyperplane;
        Point crossing = thisLine.intersection(otherLine);
        if (crossing == null) {
            double global = otherLine.getOffset(thisLine);
            if (global < -1.0E-10d) {
                return Side.MINUS;
            }
            return global > CholeskyDecomposition.DEFAULT_ABSOLUTE_POSITIVITY_THRESHOLD ? Side.PLUS : Side.HYPER;
        } else {
            return getRemainingRegion().side(new OrientedPoint(thisLine.toSubSpace(crossing), FastMath.sin(thisLine.getAngle() - otherLine.getAngle()) < 0.0d, thisLine.getTolerance()));
        }
    }

    public SplitSubHyperplane<Euclidean2D> split(Hyperplane<Euclidean2D> hyperplane) {
        Line thisLine = (Line) getHyperplane();
        Line otherLine = (Line) hyperplane;
        Point crossing = thisLine.intersection(otherLine);
        double tolerance = thisLine.getTolerance();
        if (crossing != null) {
            BSPTree plusTree;
            BSPTree minusTree;
            boolean direct = FastMath.sin(thisLine.getAngle() - otherLine.getAngle()) < 0.0d;
            Vector1D x = thisLine.toSubSpace(crossing);
            SubHyperplane<Euclidean1D> subPlus = new OrientedPoint(x, !direct, tolerance).wholeHyperplane();
            SubHyperplane<Euclidean1D> subMinus = new OrientedPoint(x, direct, tolerance).wholeHyperplane();
            BSPTree<Euclidean1D> splitTree = getRemainingRegion().getTree(false).split(subMinus);
            if (getRemainingRegion().isEmpty(splitTree.getPlus())) {
                plusTree = new BSPTree(Boolean.FALSE);
            } else {
                plusTree = new BSPTree(subPlus, new BSPTree(Boolean.FALSE), splitTree.getPlus(), null);
            }
            if (getRemainingRegion().isEmpty(splitTree.getMinus())) {
                minusTree = new BSPTree(Boolean.FALSE);
            } else {
                minusTree = new BSPTree(subMinus, new BSPTree(Boolean.FALSE), splitTree.getMinus(), null);
            }
            return new SplitSubHyperplane(new SubLine(thisLine.copySelf(), new IntervalsSet(plusTree, tolerance)), new SubLine(thisLine.copySelf(), new IntervalsSet(minusTree, tolerance)));
        } else if (otherLine.getOffset(thisLine) < -1.0E-10d) {
            return new SplitSubHyperplane(null, this);
        } else {
            return new SplitSubHyperplane(this, null);
        }
    }
}
