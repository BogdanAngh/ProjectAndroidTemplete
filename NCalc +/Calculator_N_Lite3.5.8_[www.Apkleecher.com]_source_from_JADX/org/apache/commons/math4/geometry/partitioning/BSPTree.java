package org.apache.commons.math4.geometry.partitioning;

import io.github.kexanie.library.R;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.exception.MathInternalError;
import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.geometry.partitioning.BSPTreeVisitor.Order;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane.SplitSubHyperplane;
import org.apache.commons.math4.random.ValueServer;
import org.apache.commons.math4.util.FastMath;
import org.matheclipse.core.interfaces.IExpr;

public class BSPTree<S extends Space> {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$geometry$partitioning$BSPTreeVisitor$Order;
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$geometry$partitioning$Side;
    private Object attribute;
    private SubHyperplane<S> cut;
    private BSPTree<S> minus;
    private BSPTree<S> parent;
    private BSPTree<S> plus;

    public interface LeafMerger<S extends Space> {
        BSPTree<S> merge(BSPTree<S> bSPTree, BSPTree<S> bSPTree2, BSPTree<S> bSPTree3, boolean z, boolean z2);
    }

    public interface VanishingCutHandler<S extends Space> {
        BSPTree<S> fixNode(BSPTree<S> bSPTree);
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$geometry$partitioning$BSPTreeVisitor$Order() {
        int[] iArr = $SWITCH_TABLE$org$apache$commons$math4$geometry$partitioning$BSPTreeVisitor$Order;
        if (iArr == null) {
            iArr = new int[Order.values().length];
            try {
                iArr[Order.MINUS_PLUS_SUB.ordinal()] = 3;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Order.MINUS_SUB_PLUS.ordinal()] = 4;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[Order.PLUS_MINUS_SUB.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[Order.PLUS_SUB_MINUS.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[Order.SUB_MINUS_PLUS.ordinal()] = 6;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[Order.SUB_PLUS_MINUS.ordinal()] = 5;
            } catch (NoSuchFieldError e6) {
            }
            $SWITCH_TABLE$org$apache$commons$math4$geometry$partitioning$BSPTreeVisitor$Order = iArr;
        }
        return iArr;
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

    public BSPTree() {
        this.cut = null;
        this.plus = null;
        this.minus = null;
        this.parent = null;
        this.attribute = null;
    }

    public BSPTree(Object attribute) {
        this.cut = null;
        this.plus = null;
        this.minus = null;
        this.parent = null;
        this.attribute = attribute;
    }

    public BSPTree(SubHyperplane<S> cut, BSPTree<S> plus, BSPTree<S> minus, Object attribute) {
        this.cut = cut;
        this.plus = plus;
        this.minus = minus;
        this.parent = null;
        this.attribute = attribute;
        plus.parent = this;
        minus.parent = this;
    }

    public boolean insertCut(Hyperplane<S> hyperplane) {
        if (this.cut != null) {
            this.plus.parent = null;
            this.minus.parent = null;
        }
        SubHyperplane<S> chopped = fitToCell(hyperplane.wholeHyperplane());
        if (chopped == null || chopped.isEmpty()) {
            this.cut = null;
            this.plus = null;
            this.minus = null;
            return false;
        }
        this.cut = chopped;
        this.plus = new BSPTree();
        this.plus.parent = this;
        this.minus = new BSPTree();
        this.minus.parent = this;
        return true;
    }

    public BSPTree<S> copySelf() {
        if (this.cut == null) {
            return new BSPTree(this.attribute);
        }
        return new BSPTree(this.cut.copySelf(), this.plus.copySelf(), this.minus.copySelf(), this.attribute);
    }

    public SubHyperplane<S> getCut() {
        return this.cut;
    }

    public BSPTree<S> getPlus() {
        return this.plus;
    }

    public BSPTree<S> getMinus() {
        return this.minus;
    }

    public BSPTree<S> getParent() {
        return this.parent;
    }

    public void setAttribute(Object attribute) {
        this.attribute = attribute;
    }

    public Object getAttribute() {
        return this.attribute;
    }

    public void visit(BSPTreeVisitor<S> visitor) {
        if (this.cut == null) {
            visitor.visitLeafNode(this);
            return;
        }
        switch ($SWITCH_TABLE$org$apache$commons$math4$geometry$partitioning$BSPTreeVisitor$Order()[visitor.visitOrder(this).ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                this.plus.visit(visitor);
                this.minus.visit(visitor);
                visitor.visitInternalNode(this);
            case IExpr.DOUBLEID /*2*/:
                this.plus.visit(visitor);
                visitor.visitInternalNode(this);
                this.minus.visit(visitor);
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                this.minus.visit(visitor);
                this.plus.visit(visitor);
                visitor.visitInternalNode(this);
            case IExpr.DOUBLECOMPLEXID /*4*/:
                this.minus.visit(visitor);
                visitor.visitInternalNode(this);
                this.plus.visit(visitor);
            case ValueServer.CONSTANT_MODE /*5*/:
                visitor.visitInternalNode(this);
                this.plus.visit(visitor);
                this.minus.visit(visitor);
            case R.styleable.Toolbar_contentInsetEnd /*6*/:
                visitor.visitInternalNode(this);
                this.minus.visit(visitor);
                this.plus.visit(visitor);
            default:
                throw new MathInternalError();
        }
    }

    private SubHyperplane<S> fitToCell(SubHyperplane<S> sub) {
        SubHyperplane<S> s = sub;
        for (BSPTree<S> tree = this; tree.parent != null && s != null; tree = tree.parent) {
            if (tree == tree.parent.plus) {
                s = s.split(tree.parent.cut.getHyperplane()).getPlus();
            } else {
                s = s.split(tree.parent.cut.getHyperplane()).getMinus();
            }
        }
        return s;
    }

    public BSPTree<S> getCell(Point<S> point, double tolerance) {
        if (this.cut == null) {
            return this;
        }
        double offset = this.cut.getHyperplane().getOffset(point);
        if (FastMath.abs(offset) < tolerance) {
            return this;
        }
        if (offset <= 0.0d) {
            return this.minus.getCell(point, tolerance);
        }
        return this.plus.getCell(point, tolerance);
    }

    public List<BSPTree<S>> getCloseCuts(Point<S> point, double maxOffset) {
        List<BSPTree<S>> close = new ArrayList();
        recurseCloseCuts(point, maxOffset, close);
        return close;
    }

    private void recurseCloseCuts(Point<S> point, double maxOffset, List<BSPTree<S>> close) {
        if (this.cut != null) {
            double offset = this.cut.getHyperplane().getOffset(point);
            if (offset < (-maxOffset)) {
                this.minus.recurseCloseCuts(point, maxOffset, close);
            } else if (offset > maxOffset) {
                this.plus.recurseCloseCuts(point, maxOffset, close);
            } else {
                close.add(this);
                this.minus.recurseCloseCuts(point, maxOffset, close);
                this.plus.recurseCloseCuts(point, maxOffset, close);
            }
        }
    }

    private void condense() {
        if (this.cut == null || this.plus.cut != null || this.minus.cut != null) {
            return;
        }
        if ((this.plus.attribute == null && this.minus.attribute == null) || (this.plus.attribute != null && this.plus.attribute.equals(this.minus.attribute))) {
            this.attribute = this.plus.attribute == null ? this.minus.attribute : this.plus.attribute;
            this.cut = null;
            this.plus = null;
            this.minus = null;
        }
    }

    public BSPTree<S> merge(BSPTree<S> tree, LeafMerger<S> leafMerger) {
        return merge(tree, leafMerger, null, false);
    }

    private BSPTree<S> merge(BSPTree<S> tree, LeafMerger<S> leafMerger, BSPTree<S> parentTree, boolean isPlusChild) {
        if (this.cut == null) {
            return leafMerger.merge(this, tree, parentTree, isPlusChild, true);
        }
        if (tree.cut == null) {
            return leafMerger.merge(tree, this, parentTree, isPlusChild, false);
        }
        BSPTree<S> merged = tree.split(this.cut);
        if (parentTree != null) {
            merged.parent = parentTree;
            if (isPlusChild) {
                parentTree.plus = merged;
            } else {
                parentTree.minus = merged;
            }
        }
        this.plus.merge(merged.plus, leafMerger, merged, true);
        this.minus.merge(merged.minus, leafMerger, merged, false);
        merged.condense();
        if (merged.cut == null) {
            return merged;
        }
        merged.cut = merged.fitToCell(merged.cut.getHyperplane().wholeHyperplane());
        return merged;
    }

    public BSPTree<S> split(SubHyperplane<S> sub) {
        if (this.cut == null) {
            return new BSPTree(sub, copySelf(), new BSPTree(this.attribute), null);
        }
        Hyperplane<S> cHyperplane = this.cut.getHyperplane();
        Hyperplane<S> sHyperplane = sub.getHyperplane();
        BSPTree<S> split;
        switch ($SWITCH_TABLE$org$apache$commons$math4$geometry$partitioning$Side()[sub.side(cHyperplane).ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                split = this.plus.split(sub);
                if (this.cut.side(sHyperplane) == Side.PLUS) {
                    split.plus = new BSPTree(this.cut.copySelf(), split.plus, this.minus.copySelf(), this.attribute);
                    split.plus.condense();
                    split.plus.parent = split;
                    return split;
                }
                split.minus = new BSPTree(this.cut.copySelf(), split.minus, this.minus.copySelf(), this.attribute);
                split.minus.condense();
                split.minus.parent = split;
                return split;
            case IExpr.DOUBLEID /*2*/:
                split = this.minus.split(sub);
                if (this.cut.side(sHyperplane) == Side.PLUS) {
                    split.plus = new BSPTree(this.cut.copySelf(), this.plus.copySelf(), split.plus, this.attribute);
                    split.plus.condense();
                    split.plus.parent = split;
                    return split;
                }
                split.minus = new BSPTree(this.cut.copySelf(), this.plus.copySelf(), split.minus, this.attribute);
                split.minus.condense();
                split.minus.parent = split;
                return split;
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                SplitSubHyperplane<S> cutParts = this.cut.split(sHyperplane);
                SplitSubHyperplane<S> subParts = sub.split(cHyperplane);
                split = new BSPTree(sub, this.plus.split(subParts.getPlus()), this.minus.split(subParts.getMinus()), null);
                split.plus.cut = cutParts.getPlus();
                split.minus.cut = cutParts.getMinus();
                BSPTree<S> tmp = split.plus.minus;
                split.plus.minus = split.minus.plus;
                split.plus.minus.parent = split.plus;
                split.minus.plus = tmp;
                split.minus.plus.parent = split.minus;
                split.plus.condense();
                split.minus.condense();
                return split;
            default:
                BSPTree<S> bSPTree;
                if (cHyperplane.sameOrientationAs(sHyperplane)) {
                    bSPTree = new BSPTree(sub, this.plus.copySelf(), this.minus.copySelf(), this.attribute);
                } else {
                    bSPTree = new BSPTree(sub, this.minus.copySelf(), this.plus.copySelf(), this.attribute);
                }
                return bSPTree;
        }
    }

    public void insertInTree(BSPTree<S> parentTree, boolean isPlusChild, VanishingCutHandler<S> vanishingHandler) {
        this.parent = parentTree;
        if (parentTree != null) {
            if (isPlusChild) {
                parentTree.plus = this;
            } else {
                parentTree.minus = this;
            }
        }
        if (this.cut != null) {
            for (BSPTree<S> tree = this; tree.parent != null; tree = tree.parent) {
                Hyperplane<S> hyperplane = tree.parent.cut.getHyperplane();
                if (tree == tree.parent.plus) {
                    this.cut = this.cut.split(hyperplane).getPlus();
                    this.plus.chopOffMinus(hyperplane, vanishingHandler);
                    this.minus.chopOffMinus(hyperplane, vanishingHandler);
                } else {
                    this.cut = this.cut.split(hyperplane).getMinus();
                    this.plus.chopOffPlus(hyperplane, vanishingHandler);
                    this.minus.chopOffPlus(hyperplane, vanishingHandler);
                }
                if (this.cut == null) {
                    BSPTree<S> fixed = vanishingHandler.fixNode(this);
                    this.cut = fixed.cut;
                    this.plus = fixed.plus;
                    this.minus = fixed.minus;
                    this.attribute = fixed.attribute;
                    if (this.cut == null) {
                        break;
                    }
                }
            }
            condense();
        }
    }

    public BSPTree<S> pruneAroundConvexCell(Object cellAttribute, Object otherLeafsAttributes, Object internalAttributes) {
        BSPTree<S> tree = new BSPTree(cellAttribute);
        for (BSPTree<S> current = this; current.parent != null; current = current.parent) {
            SubHyperplane<S> parentCut = current.parent.cut.copySelf();
            BSPTree<S> sibling = new BSPTree(otherLeafsAttributes);
            if (current == current.parent.plus) {
                tree = new BSPTree(parentCut, tree, sibling, internalAttributes);
            } else {
                tree = new BSPTree(parentCut, sibling, tree, internalAttributes);
            }
        }
        return tree;
    }

    private void chopOffMinus(Hyperplane<S> hyperplane, VanishingCutHandler<S> vanishingHandler) {
        if (this.cut != null) {
            this.cut = this.cut.split(hyperplane).getPlus();
            this.plus.chopOffMinus(hyperplane, vanishingHandler);
            this.minus.chopOffMinus(hyperplane, vanishingHandler);
            if (this.cut == null) {
                BSPTree<S> fixed = vanishingHandler.fixNode(this);
                this.cut = fixed.cut;
                this.plus = fixed.plus;
                this.minus = fixed.minus;
                this.attribute = fixed.attribute;
            }
        }
    }

    private void chopOffPlus(Hyperplane<S> hyperplane, VanishingCutHandler<S> vanishingHandler) {
        if (this.cut != null) {
            this.cut = this.cut.split(hyperplane).getMinus();
            this.plus.chopOffPlus(hyperplane, vanishingHandler);
            this.minus.chopOffPlus(hyperplane, vanishingHandler);
            if (this.cut == null) {
                BSPTree<S> fixed = vanishingHandler.fixNode(this);
                this.cut = fixed.cut;
                this.plus = fixed.plus;
                this.minus = fixed.minus;
                this.attribute = fixed.attribute;
            }
        }
    }
}
