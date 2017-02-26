package org.apache.commons.math4.genetics;

import org.apache.commons.math4.exception.NumberIsTooSmallException;

public class FixedGenerationCount implements StoppingCondition {
    private final int maxGenerations;
    private int numGenerations;

    public FixedGenerationCount(int maxGenerations) throws NumberIsTooSmallException {
        this.numGenerations = 0;
        if (maxGenerations <= 0) {
            throw new NumberIsTooSmallException(Integer.valueOf(maxGenerations), Integer.valueOf(1), true);
        }
        this.maxGenerations = maxGenerations;
    }

    public boolean isSatisfied(Population population) {
        if (this.numGenerations >= this.maxGenerations) {
            return true;
        }
        this.numGenerations++;
        return false;
    }

    public int getNumGenerations() {
        return this.numGenerations;
    }
}
