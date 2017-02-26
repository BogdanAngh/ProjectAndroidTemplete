package org.apache.commons.math4.geometry.partitioning;

import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane.SplitSubHyperplane;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

class InsideFinder<S extends Space> {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$geometry$partitioning$Side;
    private boolean minusFound;
    private boolean plusFound;
    private final Region<S> region;

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

    public InsideFinder(Region<S> region) {
        this.region = region;
        this.plusFound = false;
        this.minusFound = false;
    }

    public void recurseSides(BSPTree<S> node, SubHyperplane<S> sub) {
        if (node.getCut() != null) {
            Hyperplane<S> hyperplane = node.getCut().getHyperplane();
            switch ($SWITCH_TABLE$org$apache$commons$math4$geometry$partitioning$Side()[sub.side(hyperplane).ordinal()]) {
                case ValueServer.REPLAY_MODE /*1*/:
                    if (node.getCut().side(sub.getHyperplane()) == Side.PLUS) {
                        if (!this.region.isEmpty(node.getMinus())) {
                            this.plusFound = true;
                        }
                    } else if (!this.region.isEmpty(node.getMinus())) {
                        this.minusFound = true;
                    }
                    if (!this.plusFound || !this.minusFound) {
                        recurseSides(node.getPlus(), sub);
                    }
                case IExpr.DOUBLEID /*2*/:
                    if (node.getCut().side(sub.getHyperplane()) == Side.PLUS) {
                        if (!this.region.isEmpty(node.getPlus())) {
                            this.plusFound = true;
                        }
                    } else if (!this.region.isEmpty(node.getPlus())) {
                        this.minusFound = true;
                    }
                    if (!this.plusFound || !this.minusFound) {
                        recurseSides(node.getMinus(), sub);
                    }
                case ValueServer.EXPONENTIAL_MODE /*3*/:
                    SplitSubHyperplane<S> split = sub.split(hyperplane);
                    recurseSides(node.getPlus(), split.getPlus());
                    if (!this.plusFound || !this.minusFound) {
                        recurseSides(node.getMinus(), split.getMinus());
                    }
                default:
                    if (node.getCut().getHyperplane().sameOrientationAs(sub.getHyperplane())) {
                        if (node.getPlus().getCut() != null || ((Boolean) node.getPlus().getAttribute()).booleanValue()) {
                            this.plusFound = true;
                        }
                        if (node.getMinus().getCut() != null || ((Boolean) node.getMinus().getAttribute()).booleanValue()) {
                            this.minusFound = true;
                            return;
                        }
                        return;
                    }
                    if (node.getPlus().getCut() != null || ((Boolean) node.getPlus().getAttribute()).booleanValue()) {
                        this.minusFound = true;
                    }
                    if (node.getMinus().getCut() != null || ((Boolean) node.getMinus().getAttribute()).booleanValue()) {
                        this.plusFound = true;
                    }
            }
        } else if (((Boolean) node.getAttribute()).booleanValue()) {
            this.plusFound = true;
            this.minusFound = true;
        }
    }

    public boolean plusFound() {
        return this.plusFound;
    }

    public boolean minusFound() {
        return this.minusFound;
    }
}
