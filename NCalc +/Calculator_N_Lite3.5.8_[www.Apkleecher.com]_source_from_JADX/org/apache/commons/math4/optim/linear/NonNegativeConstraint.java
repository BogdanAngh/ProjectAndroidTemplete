package org.apache.commons.math4.optim.linear;

import org.apache.commons.math4.optim.OptimizationData;

public class NonNegativeConstraint implements OptimizationData {
    private final boolean isRestricted;

    public NonNegativeConstraint(boolean restricted) {
        this.isRestricted = restricted;
    }

    public boolean isRestrictedToNonNegative() {
        return this.isRestricted;
    }
}
