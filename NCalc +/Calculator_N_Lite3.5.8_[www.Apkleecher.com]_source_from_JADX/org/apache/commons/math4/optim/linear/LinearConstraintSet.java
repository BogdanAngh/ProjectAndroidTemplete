package org.apache.commons.math4.optim.linear;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.commons.math4.optim.OptimizationData;

public class LinearConstraintSet implements OptimizationData {
    private final Set<LinearConstraint> linearConstraints;

    public LinearConstraintSet(LinearConstraint... constraints) {
        this.linearConstraints = new LinkedHashSet();
        for (LinearConstraint c : constraints) {
            this.linearConstraints.add(c);
        }
    }

    public LinearConstraintSet(Collection<LinearConstraint> constraints) {
        this.linearConstraints = new LinkedHashSet();
        this.linearConstraints.addAll(constraints);
    }

    public Collection<LinearConstraint> getConstraints() {
        return Collections.unmodifiableSet(this.linearConstraints);
    }
}
