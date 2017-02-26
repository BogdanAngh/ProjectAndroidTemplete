package org.apache.commons.math4.genetics;

import org.apache.commons.math4.exception.NumberIsTooLargeException;

public interface Population extends Iterable<Chromosome> {
    void addChromosome(Chromosome chromosome) throws NumberIsTooLargeException;

    Chromosome getFittestChromosome();

    int getPopulationLimit();

    int getPopulationSize();

    Population nextGeneration();
}
