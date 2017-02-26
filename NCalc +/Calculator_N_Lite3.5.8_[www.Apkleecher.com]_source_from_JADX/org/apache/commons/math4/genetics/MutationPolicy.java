package org.apache.commons.math4.genetics;

import org.apache.commons.math4.exception.MathIllegalArgumentException;

public interface MutationPolicy {
    Chromosome mutate(Chromosome chromosome) throws MathIllegalArgumentException;
}
