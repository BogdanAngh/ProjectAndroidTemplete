package org.apache.commons.math4.geometry.euclidean.twod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.euclidean.oned.IntervalsSet;
import org.apache.commons.math4.geometry.partitioning.Region;
import org.apache.commons.math4.geometry.partitioning.RegionFactory;

class NestedLoops {
    private Vector2D[] loop;
    private boolean originalIsClockwise;
    private Region<Euclidean2D> polygon;
    private ArrayList<NestedLoops> surrounded;
    private final double tolerance;

    public NestedLoops(double tolerance) {
        this.surrounded = new ArrayList();
        this.tolerance = tolerance;
    }

    private NestedLoops(Vector2D[] loop, double tolerance) throws MathIllegalArgumentException {
        if (loop[0] == null) {
            throw new MathIllegalArgumentException(LocalizedFormats.OUTLINE_BOUNDARY_LOOP_OPEN, new Object[0]);
        }
        this.loop = loop;
        this.surrounded = new ArrayList();
        this.tolerance = tolerance;
        Collection edges = new ArrayList();
        Vector2D current = loop[loop.length - 1];
        for (Point current2 : loop) {
            Point previous = current;
            Line line = new Line((Vector2D) previous, (Vector2D) current2, tolerance);
            edges.add(new SubLine(line, new IntervalsSet(line.toSubSpace(previous).getX(), line.toSubSpace(current2).getX(), tolerance)));
        }
        this.polygon = new PolygonsSet(edges, tolerance);
        if (Double.isInfinite(this.polygon.getSize())) {
            this.polygon = new RegionFactory().getComplement(this.polygon);
            this.originalIsClockwise = false;
            return;
        }
        this.originalIsClockwise = true;
    }

    public void add(Vector2D[] bLoop) throws MathIllegalArgumentException {
        add(new NestedLoops(bLoop, this.tolerance));
    }

    private void add(NestedLoops node) throws MathIllegalArgumentException {
        Iterator it = this.surrounded.iterator();
        while (it.hasNext()) {
            NestedLoops child = (NestedLoops) it.next();
            if (child.polygon.contains(node.polygon)) {
                child.add(node);
                return;
            }
        }
        Iterator<NestedLoops> iterator = this.surrounded.iterator();
        while (iterator.hasNext()) {
            child = (NestedLoops) iterator.next();
            if (node.polygon.contains(child.polygon)) {
                node.surrounded.add(child);
                iterator.remove();
            }
        }
        RegionFactory<Euclidean2D> factory = new RegionFactory();
        it = this.surrounded.iterator();
        while (it.hasNext()) {
            if (!factory.intersection(node.polygon, ((NestedLoops) it.next()).polygon).isEmpty()) {
                throw new MathIllegalArgumentException(LocalizedFormats.CROSSING_BOUNDARY_LOOPS, new Object[0]);
            }
        }
        this.surrounded.add(node);
    }

    public void correctOrientation() {
        Iterator it = this.surrounded.iterator();
        while (it.hasNext()) {
            ((NestedLoops) it.next()).setClockWise(true);
        }
    }

    private void setClockWise(boolean clockwise) {
        if ((this.originalIsClockwise ^ clockwise) != 0) {
            int min = -1;
            int max = this.loop.length;
            while (true) {
                min++;
                max--;
                if (min >= max) {
                    break;
                }
                Vector2D tmp = this.loop[min];
                this.loop[min] = this.loop[max];
                this.loop[max] = tmp;
            }
        }
        Iterator it = this.surrounded.iterator();
        while (it.hasNext()) {
            ((NestedLoops) it.next()).setClockWise(!clockwise);
        }
    }
}
