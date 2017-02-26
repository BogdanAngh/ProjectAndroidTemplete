package org.apache.commons.math4.geometry.euclidean.threed;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.euclidean.oned.Interval;
import org.apache.commons.math4.geometry.euclidean.oned.IntervalsSet;
import org.apache.commons.math4.geometry.euclidean.oned.Vector1D;
import org.apache.commons.math4.geometry.partitioning.Region.Location;

public class SubLine {
    private final Line line;
    private final IntervalsSet remainingRegion;

    public SubLine(Line line, IntervalsSet remainingRegion) {
        this.line = line;
        this.remainingRegion = remainingRegion;
    }

    public SubLine(Vector3D start, Vector3D end, double tolerance) throws MathIllegalArgumentException {
        this(new Line(start, end, tolerance), buildIntervalSet(start, end, tolerance));
    }

    public SubLine(Segment segment) throws MathIllegalArgumentException {
        this(segment.getLine(), buildIntervalSet(segment.getStart(), segment.getEnd(), segment.getLine().getTolerance()));
    }

    public List<Segment> getSegments() {
        List<Interval> list = this.remainingRegion.asList();
        List<Segment> segments = new ArrayList(list.size());
        for (Interval interval : list) {
            segments.add(new Segment(this.line.toSpace(new Vector1D(interval.getInf())), this.line.toSpace(new Vector1D(interval.getSup())), this.line));
        }
        return segments;
    }

    public Vector3D intersection(SubLine subLine, boolean includeEndPoints) {
        Point v1D = this.line.intersection(subLine.line);
        if (v1D == null) {
            return null;
        }
        Location loc1 = this.remainingRegion.checkPoint(this.line.toSubSpace(v1D));
        Location loc2 = subLine.remainingRegion.checkPoint(subLine.line.toSubSpace(v1D));
        if (!includeEndPoints) {
            return (loc1 == Location.INSIDE && loc2 == Location.INSIDE) ? v1D : null;
        } else {
            if (loc1 == Location.OUTSIDE || loc2 == Location.OUTSIDE) {
                return null;
            }
            return v1D;
        }
    }

    private static IntervalsSet buildIntervalSet(Vector3D start, Vector3D end, double tolerance) throws MathIllegalArgumentException {
        Line line = new Line(start, end, tolerance);
        return new IntervalsSet(line.toSubSpace((Point) start).getX(), line.toSubSpace((Point) end).getX(), tolerance);
    }
}
