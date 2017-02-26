package org.apache.commons.math4.geometry.partitioning;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.exception.MathInternalError;
import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane.SplitSubHyperplane;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

class Characterization<S extends Space> {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$geometry$partitioning$Side;
    private final NodesSet<S> insideSplitters;
    private SubHyperplane<S> insideTouching;
    private final NodesSet<S> outsideSplitters;
    private SubHyperplane<S> outsideTouching;

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

    public Characterization(BSPTree<S> node, SubHyperplane<S> sub) {
        this.outsideTouching = null;
        this.insideTouching = null;
        this.outsideSplitters = new NodesSet();
        this.insideSplitters = new NodesSet();
        characterize(node, sub, new ArrayList());
    }

    private void characterize(BSPTree<S> node, SubHyperplane<S> sub, List<BSPTree<S>> splitters) {
        if (node.getCut() != null) {
            Hyperplane<S> hyperplane = node.getCut().getHyperplane();
            switch ($SWITCH_TABLE$org$apache$commons$math4$geometry$partitioning$Side()[sub.side(hyperplane).ordinal()]) {
                case ValueServer.REPLAY_MODE /*1*/:
                    characterize(node.getPlus(), sub, splitters);
                case IExpr.DOUBLEID /*2*/:
                    characterize(node.getMinus(), sub, splitters);
                case ValueServer.EXPONENTIAL_MODE /*3*/:
                    SplitSubHyperplane<S> split = sub.split(hyperplane);
                    splitters.add(node);
                    characterize(node.getPlus(), split.getPlus(), splitters);
                    characterize(node.getMinus(), split.getMinus(), splitters);
                    splitters.remove(splitters.size() - 1);
                default:
                    throw new MathInternalError();
            }
        } else if (((Boolean) node.getAttribute()).booleanValue()) {
            addInsideTouching(sub, splitters);
        } else {
            addOutsideTouching(sub, splitters);
        }
    }

    private void addOutsideTouching(SubHyperplane<S> sub, List<BSPTree<S>> splitters) {
        if (this.outsideTouching == null) {
            this.outsideTouching = sub;
        } else {
            this.outsideTouching = this.outsideTouching.reunite(sub);
        }
        this.outsideSplitters.addAll(splitters);
    }

    private void addInsideTouching(SubHyperplane<S> sub, List<BSPTree<S>> splitters) {
        if (this.insideTouching == null) {
            this.insideTouching = sub;
        } else {
            this.insideTouching = this.insideTouching.reunite(sub);
        }
        this.insideSplitters.addAll(splitters);
    }

    public boolean touchOutside() {
        return (this.outsideTouching == null || this.outsideTouching.isEmpty()) ? false : true;
    }

    public SubHyperplane<S> outsideTouching() {
        return this.outsideTouching;
    }

    public NodesSet<S> getOutsideSplitters() {
        return this.outsideSplitters;
    }

    public boolean touchInside() {
        return (this.insideTouching == null || this.insideTouching.isEmpty()) ? false : true;
    }

    public SubHyperplane<S> insideTouching() {
        return this.insideTouching;
    }

    public NodesSet<S> getInsideSplitters() {
        return this.insideSplitters;
    }
}
