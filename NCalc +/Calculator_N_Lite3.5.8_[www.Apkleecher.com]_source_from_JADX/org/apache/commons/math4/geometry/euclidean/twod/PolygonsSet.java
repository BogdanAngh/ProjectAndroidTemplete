package org.apache.commons.math4.geometry.euclidean.twod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.euclidean.oned.Euclidean1D;
import org.apache.commons.math4.geometry.euclidean.oned.Interval;
import org.apache.commons.math4.geometry.euclidean.oned.IntervalsSet;
import org.apache.commons.math4.geometry.euclidean.oned.Vector1D;
import org.apache.commons.math4.geometry.partitioning.AbstractRegion;
import org.apache.commons.math4.geometry.partitioning.AbstractSubHyperplane;
import org.apache.commons.math4.geometry.partitioning.BSPTree;
import org.apache.commons.math4.geometry.partitioning.BSPTreeVisitor;
import org.apache.commons.math4.geometry.partitioning.BSPTreeVisitor.Order;
import org.apache.commons.math4.geometry.partitioning.BoundaryAttribute;
import org.apache.commons.math4.geometry.partitioning.Hyperplane;
import org.apache.commons.math4.geometry.partitioning.Side;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane;
import org.apache.commons.math4.random.ValueServer;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.Precision;
import org.matheclipse.core.interfaces.IExpr;

public class PolygonsSet extends AbstractRegion<Euclidean2D, Euclidean1D> {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$geometry$partitioning$Side;
    private Vector2D[][] vertices;

    private static class ConnectableSegment extends Segment {
        private final BSPTree<Euclidean2D> endNode;
        private ConnectableSegment next;
        private final BSPTree<Euclidean2D> node;
        private ConnectableSegment previous;
        private boolean processed;
        private final BSPTree<Euclidean2D> startNode;

        public ConnectableSegment(Vector2D start, Vector2D end, Line line, BSPTree<Euclidean2D> node, BSPTree<Euclidean2D> startNode, BSPTree<Euclidean2D> endNode) {
            super(start, end, line);
            this.node = node;
            this.startNode = startNode;
            this.endNode = endNode;
            this.previous = null;
            this.next = null;
            this.processed = false;
        }

        public BSPTree<Euclidean2D> getNode() {
            return this.node;
        }

        public BSPTree<Euclidean2D> getStartNode() {
            return this.startNode;
        }

        public BSPTree<Euclidean2D> getEndNode() {
            return this.endNode;
        }

        public ConnectableSegment getPrevious() {
            return this.previous;
        }

        public void setPrevious(ConnectableSegment previous) {
            this.previous = previous;
        }

        public ConnectableSegment getNext() {
            return this.next;
        }

        public void setNext(ConnectableSegment next) {
            this.next = next;
        }

        public void setProcessed(boolean processed) {
            this.processed = processed;
        }

        public boolean isProcessed() {
            return this.processed;
        }
    }

    private static class Edge {
        private final Vertex end;
        private final Line line;
        private BSPTree<Euclidean2D> node;
        private final Vertex start;

        public Edge(Vertex start, Vertex end, Line line) {
            this.start = start;
            this.end = end;
            this.line = line;
            this.node = null;
            start.setOutgoing(this);
            end.setIncoming(this);
        }

        public Vertex getStart() {
            return this.start;
        }

        public Vertex getEnd() {
            return this.end;
        }

        public Line getLine() {
            return this.line;
        }

        public void setNode(BSPTree<Euclidean2D> node) {
            this.node = node;
        }

        public BSPTree<Euclidean2D> getNode() {
            return this.node;
        }

        public Vertex split(Line splitLine) {
            Vertex splitVertex = new Vertex(this.line.intersection(splitLine));
            splitVertex.bindWith(splitLine);
            Edge startHalf = new Edge(this.start, splitVertex, this.line);
            Edge endHalf = new Edge(splitVertex, this.end, this.line);
            startHalf.node = this.node;
            endHalf.node = this.node;
            return splitVertex;
        }
    }

    private static class SegmentsBuilder implements BSPTreeVisitor<Euclidean2D> {
        private final List<ConnectableSegment> segments;
        private final double tolerance;

        public SegmentsBuilder(double tolerance) {
            this.tolerance = tolerance;
            this.segments = new ArrayList();
        }

        public Order visitOrder(BSPTree<Euclidean2D> bSPTree) {
            return Order.MINUS_SUB_PLUS;
        }

        public void visitInternalNode(BSPTree<Euclidean2D> node) {
            BoundaryAttribute<Euclidean2D> attribute = (BoundaryAttribute) node.getAttribute();
            Iterable<BSPTree<Euclidean2D>> splitters = attribute.getSplitters();
            if (attribute.getPlusOutside() != null) {
                addContribution(attribute.getPlusOutside(), node, splitters, false);
            }
            if (attribute.getPlusInside() != null) {
                addContribution(attribute.getPlusInside(), node, splitters, true);
            }
        }

        public void visitLeafNode(BSPTree<Euclidean2D> bSPTree) {
        }

        private void addContribution(SubHyperplane<Euclidean2D> sub, BSPTree<Euclidean2D> node, Iterable<BSPTree<Euclidean2D>> splitters, boolean reversed) {
            Line line = (Line) sub.getHyperplane();
            for (Interval i : ((IntervalsSet) ((AbstractSubHyperplane) sub).getRemainingRegion()).asList()) {
                Vector2D startV = Double.isInfinite(i.getInf()) ? null : line.toSpace(new Vector1D(i.getInf()));
                Vector2D endV = Double.isInfinite(i.getSup()) ? null : line.toSpace(new Vector1D(i.getSup()));
                BSPTree<Euclidean2D> startN = selectClosest(startV, splitters);
                BSPTree<Euclidean2D> endN = selectClosest(endV, splitters);
                if (reversed) {
                    this.segments.add(new ConnectableSegment(endV, startV, line.getReverse(), node, endN, startN));
                } else {
                    this.segments.add(new ConnectableSegment(startV, endV, line, node, startN, endN));
                }
            }
        }

        private BSPTree<Euclidean2D> selectClosest(Vector2D point, Iterable<BSPTree<Euclidean2D>> candidates) {
            BSPTree<Euclidean2D> selected = null;
            double min = Double.POSITIVE_INFINITY;
            for (BSPTree<Euclidean2D> node : candidates) {
                double distance = FastMath.abs(node.getCut().getHyperplane().getOffset(point));
                if (distance < min) {
                    selected = node;
                    min = distance;
                }
            }
            return min <= this.tolerance ? selected : null;
        }

        public List<ConnectableSegment> getSegments() {
            return this.segments;
        }
    }

    private static class Vertex {
        private Edge incoming;
        private final List<Line> lines;
        private final Vector2D location;
        private Edge outgoing;

        public Vertex(Vector2D location) {
            this.location = location;
            this.incoming = null;
            this.outgoing = null;
            this.lines = new ArrayList();
        }

        public Vector2D getLocation() {
            return this.location;
        }

        public void bindWith(Line line) {
            this.lines.add(line);
        }

        public Line sharedLineWith(Vertex vertex) {
            for (Line line1 : this.lines) {
                for (Line line2 : vertex.lines) {
                    if (line1 == line2) {
                        return line1;
                    }
                }
            }
            return null;
        }

        public void setIncoming(Edge incoming) {
            this.incoming = incoming;
            bindWith(incoming.getLine());
        }

        public Edge getIncoming() {
            return this.incoming;
        }

        public void setOutgoing(Edge outgoing) {
            this.outgoing = outgoing;
            bindWith(outgoing.getLine());
        }

        public Edge getOutgoing() {
            return this.outgoing;
        }
    }

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

    public PolygonsSet(double tolerance) {
        super(tolerance);
    }

    public PolygonsSet(BSPTree<Euclidean2D> tree, double tolerance) {
        super((BSPTree) tree, tolerance);
    }

    public PolygonsSet(Collection<SubHyperplane<Euclidean2D>> boundary, double tolerance) {
        super((Collection) boundary, tolerance);
    }

    public PolygonsSet(double xMin, double xMax, double yMin, double yMax, double tolerance) {
        super(boxBoundary(xMin, xMax, yMin, yMax, tolerance), tolerance);
    }

    public PolygonsSet(double hyperplaneThickness, Vector2D... vertices) {
        super(verticesToTree(hyperplaneThickness, vertices), hyperplaneThickness);
    }

    private static Line[] boxBoundary(double xMin, double xMax, double yMin, double yMax, double tolerance) {
        if (xMin >= xMax - tolerance || yMin >= yMax - tolerance) {
            return null;
        }
        Vector2D minMin = new Vector2D(xMin, yMin);
        Vector2D minMax = new Vector2D(xMin, yMax);
        Vector2D maxMin = new Vector2D(xMax, yMin);
        Vector2D maxMax = new Vector2D(xMax, yMax);
        return new Line[]{new Line(minMin, maxMin, tolerance), new Line(maxMin, maxMax, tolerance), new Line(maxMax, minMax, tolerance), new Line(minMax, minMin, tolerance)};
    }

    private static BSPTree<Euclidean2D> verticesToTree(double hyperplaneThickness, Vector2D... vertices) {
        int n = vertices.length;
        if (n == 0) {
            return new BSPTree(Boolean.TRUE);
        }
        int i;
        Vertex[] vArray = new Vertex[n];
        for (i = 0; i < n; i++) {
            vArray[i] = new Vertex(vertices[i]);
        }
        List<Edge> edges = new ArrayList(n);
        for (i = 0; i < n; i++) {
            Vertex start = vArray[i];
            Vertex end = vArray[(i + 1) % n];
            Line line = start.sharedLineWith(end);
            if (line == null) {
                line = new Line(start.getLocation(), end.getLocation(), hyperplaneThickness);
            }
            edges.add(new Edge(start, end, line));
            for (Vertex vertex : vArray) {
                if (!(vertex == start || vertex == end || FastMath.abs(line.getOffset(vertex.getLocation())) > hyperplaneThickness)) {
                    vertex.bindWith(line);
                }
            }
        }
        BSPTree<Euclidean2D> tree = new BSPTree();
        insertEdges(hyperplaneThickness, tree, edges);
        return tree;
    }

    private static void insertEdges(double hyperplaneThickness, BSPTree<Euclidean2D> node, List<Edge> edges) {
        Edge inserted = null;
        int index = 0;
        while (inserted == null && index < edges.size()) {
            int index2 = index + 1;
            inserted = (Edge) edges.get(index);
            if (inserted.getNode() == null) {
                if (node.insertCut(inserted.getLine())) {
                    inserted.setNode(node);
                    index = index2;
                } else {
                    inserted = null;
                    index = index2;
                }
            } else {
                inserted = null;
                index = index2;
            }
        }
        if (inserted == null) {
            BSPTree<Euclidean2D> parent = node.getParent();
            if (parent == null || node == parent.getMinus()) {
                node.setAttribute(Boolean.TRUE);
                return;
            } else {
                node.setAttribute(Boolean.FALSE);
                return;
            }
        }
        List<Edge> plusList = new ArrayList();
        List<Edge> minusList = new ArrayList();
        for (Edge edge : edges) {
            if (edge != inserted) {
                double startOffset = inserted.getLine().getOffset(edge.getStart().getLocation());
                double endOffset = inserted.getLine().getOffset(edge.getEnd().getLocation());
                Side startSide = FastMath.abs(startOffset) <= hyperplaneThickness ? Side.HYPER : startOffset < 0.0d ? Side.MINUS : Side.PLUS;
                Side endSide = FastMath.abs(endOffset) <= hyperplaneThickness ? Side.HYPER : endOffset < 0.0d ? Side.MINUS : Side.PLUS;
                Vertex splitPoint;
                switch ($SWITCH_TABLE$org$apache$commons$math4$geometry$partitioning$Side()[startSide.ordinal()]) {
                    case ValueServer.REPLAY_MODE /*1*/:
                        if (endSide != Side.MINUS) {
                            plusList.add(edge);
                            break;
                        }
                        splitPoint = edge.split(inserted.getLine());
                        minusList.add(splitPoint.getOutgoing());
                        plusList.add(splitPoint.getIncoming());
                        break;
                    case IExpr.DOUBLEID /*2*/:
                        if (endSide != Side.PLUS) {
                            minusList.add(edge);
                            break;
                        }
                        splitPoint = edge.split(inserted.getLine());
                        minusList.add(splitPoint.getIncoming());
                        plusList.add(splitPoint.getOutgoing());
                        break;
                    default:
                        if (endSide != Side.PLUS) {
                            if (endSide != Side.MINUS) {
                                break;
                            }
                            minusList.add(edge);
                            break;
                        }
                        plusList.add(edge);
                        break;
                }
            }
        }
        if (plusList.isEmpty()) {
            node.getPlus().setAttribute(Boolean.FALSE);
        } else {
            insertEdges(hyperplaneThickness, node.getPlus(), plusList);
        }
        if (minusList.isEmpty()) {
            node.getMinus().setAttribute(Boolean.TRUE);
        } else {
            insertEdges(hyperplaneThickness, node.getMinus(), minusList);
        }
    }

    public PolygonsSet buildNew(BSPTree<Euclidean2D> tree) {
        return new PolygonsSet((BSPTree) tree, getTolerance());
    }

    protected void computeGeometricalProperties() {
        Vector2D[][] v = getVertices();
        if (v.length == 0) {
            BSPTree<Euclidean2D> tree = getTree(false);
            if (tree.getCut() == null && ((Boolean) tree.getAttribute()).booleanValue()) {
                setSize(Double.POSITIVE_INFINITY);
                setBarycenter((Point) Vector2D.NaN);
                return;
            }
            setSize(0.0d);
            setBarycenter((Point) new Vector2D(0.0d, 0.0d));
        } else if (v[0][0] == null) {
            setSize(Double.POSITIVE_INFINITY);
            setBarycenter((Point) Vector2D.NaN);
        } else {
            double sum = 0.0d;
            double sumX = 0.0d;
            double sumY = 0.0d;
            for (Vector2D[] loop : v) {
                double x1 = loop[loop.length - 1].getX();
                double y1 = loop[loop.length - 1].getY();
                for (Vector2D point : loop) {
                    double x0 = x1;
                    double y0 = y1;
                    x1 = point.getX();
                    y1 = point.getY();
                    double factor = (x0 * y1) - (y0 * x1);
                    sum += factor;
                    sumX += (x0 + x1) * factor;
                    sumY += (y0 + y1) * factor;
                }
            }
            if (sum < 0.0d) {
                setSize(Double.POSITIVE_INFINITY);
                setBarycenter((Point) Vector2D.NaN);
                return;
            }
            setSize(sum / 2.0d);
            setBarycenter((Point) new Vector2D(sumX / (3.0d * sum), sumY / (3.0d * sum)));
        }
    }

    public Vector2D[][] getVertices() {
        if (this.vertices == null) {
            if (getTree(false).getCut() == null) {
                this.vertices = new Vector2D[0][];
            } else {
                List<Segment> loop;
                SegmentsBuilder segmentsBuilder = new SegmentsBuilder(getTolerance());
                getTree(true).visit(segmentsBuilder);
                List<ConnectableSegment> segments = segmentsBuilder.getSegments();
                int pending = segments.size() - naturalFollowerConnections(segments);
                if (pending > 0) {
                    pending -= splitEdgeConnections(segments);
                }
                if (pending > 0) {
                    pending -= closeVerticesConnections(segments);
                }
                ArrayList<List<Segment>> loops = new ArrayList();
                ConnectableSegment s = getUnprocessed(segments);
                while (s != null) {
                    loop = followLoop(s);
                    if (loop != null) {
                        if (((Segment) loop.get(0)).getStart() == null) {
                            loops.add(0, loop);
                        } else {
                            loops.add(loop);
                        }
                    }
                    s = getUnprocessed(segments);
                }
                this.vertices = new Vector2D[loops.size()][];
                int i = 0;
                Iterator it = loops.iterator();
                while (it.hasNext()) {
                    loop = (List) it.next();
                    int i2;
                    if (loop.size() < 2 || (loop.size() == 2 && ((Segment) loop.get(0)).getStart() == null && ((Segment) loop.get(1)).getEnd() == null)) {
                        Line line = ((Segment) loop.get(0)).getLine();
                        Vector2D[][] vector2DArr = this.vertices;
                        i2 = i + 1;
                        Vector2D[] vector2DArr2 = new Vector2D[3];
                        vector2DArr2[1] = line.toSpace(new Vector1D(-3.4028234663852886E38d));
                        vector2DArr2[2] = line.toSpace(new Vector1D(3.4028234663852886E38d));
                        vector2DArr[i] = vector2DArr2;
                        i = i2;
                    } else if (((Segment) loop.get(0)).getStart() == null) {
                        array = new Vector2D[(loop.size() + 2)];
                        j = 0;
                        for (Segment segment : loop) {
                            double x;
                            if (j == 0) {
                                x = segment.getLine().toSubSpace(segment.getEnd()).getX();
                                x -= FastMath.max(1.0d, FastMath.abs(x / 2.0d));
                                r8 = j + 1;
                                array[j] = null;
                                j = r8 + 1;
                                array[r8] = segment.getLine().toSpace(new Vector1D(x));
                            }
                            if (j < array.length - 1) {
                                r8 = j + 1;
                                array[j] = segment.getEnd();
                                j = r8;
                            }
                            if (j == array.length - 1) {
                                x = segment.getLine().toSubSpace(segment.getStart()).getX();
                                r8 = j + 1;
                                array[j] = segment.getLine().toSpace(new Vector1D(x + FastMath.max(1.0d, FastMath.abs(x / 2.0d))));
                                j = r8;
                            }
                        }
                        i2 = i + 1;
                        this.vertices[i] = array;
                        i = i2;
                    } else {
                        array = new Vector2D[loop.size()];
                        j = 0;
                        for (Segment segment2 : loop) {
                            r8 = j + 1;
                            array[j] = segment2.getStart();
                            j = r8;
                        }
                        i2 = i + 1;
                        this.vertices[i] = array;
                        i = i2;
                    }
                }
            }
        }
        return (Vector2D[][]) this.vertices.clone();
    }

    private int naturalFollowerConnections(List<ConnectableSegment> segments) {
        int connected = 0;
        for (ConnectableSegment segment : segments) {
            if (segment.getNext() == null) {
                BSPTree<Euclidean2D> node = segment.getNode();
                BSPTree<Euclidean2D> end = segment.getEndNode();
                for (ConnectableSegment candidateNext : segments) {
                    if (candidateNext.getPrevious() == null && candidateNext.getNode() == end && candidateNext.getStartNode() == node) {
                        segment.setNext(candidateNext);
                        candidateNext.setPrevious(segment);
                        connected++;
                        break;
                    }
                }
            }
        }
        return connected;
    }

    private int splitEdgeConnections(List<ConnectableSegment> segments) {
        int connected = 0;
        for (ConnectableSegment segment : segments) {
            if (segment.getNext() == null) {
                Hyperplane<Euclidean2D> hyperplane = segment.getNode().getCut().getHyperplane();
                BSPTree<Euclidean2D> end = segment.getEndNode();
                for (ConnectableSegment candidateNext : segments) {
                    if (candidateNext.getPrevious() == null && candidateNext.getNode().getCut().getHyperplane() == hyperplane && candidateNext.getStartNode() == end) {
                        segment.setNext(candidateNext);
                        candidateNext.setPrevious(segment);
                        connected++;
                        break;
                    }
                }
            }
        }
        return connected;
    }

    private int closeVerticesConnections(List<ConnectableSegment> segments) {
        int connected = 0;
        for (ConnectableSegment segment : segments) {
            if (segment.getNext() == null && segment.getEnd() != null) {
                Vector2D end = segment.getEnd();
                ConnectableSegment selectedNext = null;
                double min = Double.POSITIVE_INFINITY;
                for (ConnectableSegment candidateNext : segments) {
                    if (candidateNext.getPrevious() == null && candidateNext.getStart() != null) {
                        double distance = Vector2D.distance(end, candidateNext.getStart());
                        if (distance < min) {
                            selectedNext = candidateNext;
                            min = distance;
                        }
                    }
                }
                if (min <= getTolerance()) {
                    segment.setNext(selectedNext);
                    selectedNext.setPrevious(segment);
                    connected++;
                }
            }
        }
        return connected;
    }

    private ConnectableSegment getUnprocessed(List<ConnectableSegment> segments) {
        for (ConnectableSegment segment : segments) {
            if (!segment.isProcessed()) {
                return segment;
            }
        }
        return null;
    }

    private List<Segment> followLoop(ConnectableSegment defining) {
        List<Segment> loop = new ArrayList();
        loop.add(defining);
        defining.setProcessed(true);
        ConnectableSegment next = defining.getNext();
        while (next != defining && next != null) {
            loop.add(next);
            next.setProcessed(true);
            next = next.getNext();
        }
        if (next == null) {
            for (ConnectableSegment previous = defining.getPrevious(); previous != null; previous = previous.getPrevious()) {
                loop.add(0, previous);
                previous.setProcessed(true);
            }
        }
        filterSpuriousVertices(loop);
        if (loop.size() != 2 || ((Segment) loop.get(0)).getStart() == null) {
            return loop;
        }
        return null;
    }

    private void filterSpuriousVertices(List<Segment> loop) {
        int i = 0;
        while (i < loop.size()) {
            Segment previous = (Segment) loop.get(i);
            int j = (i + 1) % loop.size();
            Segment next = (Segment) loop.get(j);
            if (next != null && Precision.equals(previous.getLine().getAngle(), next.getLine().getAngle(), Precision.EPSILON)) {
                loop.set(j, new Segment(previous.getStart(), next.getEnd(), previous.getLine()));
                int i2 = i - 1;
                loop.remove(i);
                i = i2;
            }
            i++;
        }
    }
}
