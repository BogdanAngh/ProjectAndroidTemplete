package org.apache.commons.math4.genetics;

public interface StoppingCondition {
    boolean isSatisfied(Population population);
}
