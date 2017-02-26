package org.apache.commons.math4.geometry.spherical.twod;

import com.example.duy.calculator.geom2d.util.Angle2D;
import java.util.List;
import org.apache.commons.math4.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math4.geometry.spherical.oned.Arc;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathUtils;

public class Edge {
    private final Circle circle;
    private Vertex end;
    private final double length;
    private final Vertex start;

    Edge(Vertex start, Vertex end, double length, Circle circle) {
        this.start = start;
        this.end = end;
        this.length = length;
        this.circle = circle;
        start.setOutgoing(this);
        end.setIncoming(this);
    }

    public Vertex getStart() {
        return this.start;
    }

    public Vertex getEnd() {
        return this.end;
    }

    public double getLength() {
        return this.length;
    }

    public Circle getCircle() {
        return this.circle;
    }

    public Vector3D getPointAt(double alpha) {
        return this.circle.getPointAt(this.circle.getPhase(this.start.getLocation().getVector()) + alpha);
    }

    void setNextEdge(Edge next) {
        this.end = next.getStart();
        this.end.setIncoming(this);
        this.end.bindWith(getCircle());
    }

    void split(Circle splitCircle, List<Edge> outsideList, List<Edge> insideList) {
        double edgeStart = this.circle.getPhase(this.start.getLocation().getVector());
        Arc arc = this.circle.getInsideArc(splitCircle);
        double arcRelativeStart = MathUtils.normalizeAngle(arc.getInf(), FastMath.PI + edgeStart) - edgeStart;
        double arcRelativeEnd = arcRelativeStart + arc.getSize();
        double unwrappedEnd = arcRelativeEnd - Angle2D.M_2PI;
        double tolerance = this.circle.getTolerance();
        Vertex previousVertex = this.start;
        if (unwrappedEnd >= this.length - tolerance) {
            insideList.add(this);
            return;
        }
        double alreadyManagedLength = 0.0d;
        if (unwrappedEnd >= 0.0d) {
            previousVertex = addSubEdge(previousVertex, new Vertex(new S2Point(this.circle.getPointAt(edgeStart + unwrappedEnd))), unwrappedEnd, insideList, splitCircle);
            alreadyManagedLength = unwrappedEnd;
        }
        if (arcRelativeStart < this.length - tolerance) {
            previousVertex = addSubEdge(previousVertex, new Vertex(new S2Point(this.circle.getPointAt(edgeStart + arcRelativeStart))), arcRelativeStart - alreadyManagedLength, outsideList, splitCircle);
            alreadyManagedLength = arcRelativeStart;
            if (arcRelativeEnd >= this.length - tolerance) {
                previousVertex = addSubEdge(previousVertex, this.end, this.length - alreadyManagedLength, insideList, splitCircle);
                return;
            }
            previousVertex = addSubEdge(previousVertex, new Vertex(new S2Point(this.circle.getPointAt(edgeStart + arcRelativeStart))), arcRelativeStart - alreadyManagedLength, insideList, splitCircle);
            Vertex vertex = previousVertex;
            previousVertex = addSubEdge(vertex, this.end, this.length - arcRelativeStart, outsideList, splitCircle);
        } else if (unwrappedEnd >= 0.0d) {
            previousVertex = addSubEdge(previousVertex, this.end, this.length - alreadyManagedLength, outsideList, splitCircle);
        } else {
            outsideList.add(this);
        }
    }

    private Vertex addSubEdge(Vertex subStart, Vertex subEnd, double subLength, List<Edge> list, Circle splitCircle) {
        if (subLength <= this.circle.getTolerance()) {
            return subStart;
        }
        subEnd.bindWith(splitCircle);
        list.add(new Edge(subStart, subEnd, subLength, this.circle));
        return subEnd;
    }
}
