package org.apache.commons.math4.geometry.partitioning;

import org.apache.commons.math4.geometry.Space;

public interface SubHyperplane<S extends Space> {

    public static class SplitSubHyperplane<U extends Space> {
        private final SubHyperplane<U> minus;
        private final SubHyperplane<U> plus;

        public SplitSubHyperplane(SubHyperplane<U> plus, SubHyperplane<U> minus) {
            this.plus = plus;
            this.minus = minus;
        }

        public SubHyperplane<U> getPlus() {
            return this.plus;
        }

        public SubHyperplane<U> getMinus() {
            return this.minus;
        }
    }

    SubHyperplane<S> copySelf();

    Hyperplane<S> getHyperplane();

    double getSize();

    boolean isEmpty();

    SubHyperplane<S> reunite(SubHyperplane<S> subHyperplane);

    Side side(Hyperplane<S> hyperplane);

    SplitSubHyperplane<S> split(Hyperplane<S> hyperplane);
}
