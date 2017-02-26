package org.apache.commons.math4.genetics;

import java.util.Collections;
import java.util.List;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.FastMath;

public class ElitisticListPopulation extends ListPopulation {
    private double elitismRate;

    public ElitisticListPopulation(List<Chromosome> chromosomes, int populationLimit, double elitismRate) throws NullArgumentException, NotPositiveException, NumberIsTooLargeException, OutOfRangeException {
        super(chromosomes, populationLimit);
        this.elitismRate = 0.9d;
        setElitismRate(elitismRate);
    }

    public ElitisticListPopulation(int populationLimit, double elitismRate) throws NotPositiveException, OutOfRangeException {
        super(populationLimit);
        this.elitismRate = 0.9d;
        setElitismRate(elitismRate);
    }

    public Population nextGeneration() {
        ElitisticListPopulation nextGeneration = new ElitisticListPopulation(getPopulationLimit(), getElitismRate());
        List<Chromosome> oldChromosomes = getChromosomeList();
        Collections.sort(oldChromosomes);
        for (int i = (int) FastMath.ceil((1.0d - getElitismRate()) * ((double) oldChromosomes.size())); i < oldChromosomes.size(); i++) {
            nextGeneration.addChromosome((Chromosome) oldChromosomes.get(i));
        }
        return nextGeneration;
    }

    public void setElitismRate(double elitismRate) throws OutOfRangeException {
        if (elitismRate < 0.0d || elitismRate > 1.0d) {
            throw new OutOfRangeException(LocalizedFormats.ELITISM_RATE, Double.valueOf(elitismRate), Integer.valueOf(0), Integer.valueOf(1));
        }
        this.elitismRate = elitismRate;
    }

    public double getElitismRate() {
        return this.elitismRate;
    }
}
