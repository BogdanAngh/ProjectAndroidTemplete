package org.apache.commons.math4.genetics;

import org.apache.commons.math4.exception.MathIllegalArgumentException;

public interface CrossoverPolicy {
    ChromosomePair crossover(Chromosome chromosome, Chromosome chromosome2) throws MathIllegalArgumentException;
}
