package org.apache.commons.math4.geometry.euclidean.threed;

public class Segment {
    private final Vector3D end;
    private final Line line;
    private final Vector3D start;

    public Segment(Vector3D start, Vector3D end, Line line) {
        this.start = start;
        this.end = end;
        this.line = line;
    }

    public Vector3D getStart() {
        return this.start;
    }

    public Vector3D getEnd() {
        return this.end;
    }

    public Line getLine() {
        return this.line;
    }
}
