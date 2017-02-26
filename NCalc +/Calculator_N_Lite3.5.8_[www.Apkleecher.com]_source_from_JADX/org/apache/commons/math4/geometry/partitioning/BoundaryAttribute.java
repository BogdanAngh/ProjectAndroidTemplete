package org.apache.commons.math4.geometry.partitioning;

import org.apache.commons.math4.geometry.Space;

public class BoundaryAttribute<S extends Space> {
    private final SubHyperplane<S> plusInside;
    private final SubHyperplane<S> plusOutside;
    private final NodesSet<S> splitters;

    BoundaryAttribute(SubHyperplane<S> plusOutside, SubHyperplane<S> plusInside, NodesSet<S> splitters) {
        this.plusOutside = plusOutside;
        this.plusInside = plusInside;
        this.splitters = splitters;
    }

    public SubHyperplane<S> getPlusOutside() {
        return this.plusOutside;
    }

    public SubHyperplane<S> getPlusInside() {
        return this.plusInside;
    }

    public NodesSet<S> getSplitters() {
        return this.splitters;
    }
}
