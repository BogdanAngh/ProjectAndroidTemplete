package org.apache.commons.math4.genetics;

import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.JDKRandomGenerator;
import org.apache.commons.math4.random.RandomGenerator;

public class GeneticAlgorithm {
    private static RandomGenerator randomGenerator;
    private final CrossoverPolicy crossoverPolicy;
    private final double crossoverRate;
    private int generationsEvolved;
    private final MutationPolicy mutationPolicy;
    private final double mutationRate;
    private final SelectionPolicy selectionPolicy;

    static {
        randomGenerator = new JDKRandomGenerator();
    }

    public GeneticAlgorithm(CrossoverPolicy crossoverPolicy, double crossoverRate, MutationPolicy mutationPolicy, double mutationRate, SelectionPolicy selectionPolicy) throws OutOfRangeException {
        this.generationsEvolved = 0;
        if (crossoverRate < 0.0d || crossoverRate > 1.0d) {
            throw new OutOfRangeException(LocalizedFormats.CROSSOVER_RATE, Double.valueOf(crossoverRate), Integer.valueOf(0), Integer.valueOf(1));
        } else if (mutationRate < 0.0d || mutationRate > 1.0d) {
            throw new OutOfRangeException(LocalizedFormats.MUTATION_RATE, Double.valueOf(mutationRate), Integer.valueOf(0), Integer.valueOf(1));
        } else {
            this.crossoverPolicy = crossoverPolicy;
            this.crossoverRate = crossoverRate;
            this.mutationPolicy = mutationPolicy;
            this.mutationRate = mutationRate;
            this.selectionPolicy = selectionPolicy;
        }
    }

    public static synchronized void setRandomGenerator(RandomGenerator random) {
        synchronized (GeneticAlgorithm.class) {
            randomGenerator = random;
        }
    }

    public static synchronized RandomGenerator getRandomGenerator() {
        RandomGenerator randomGenerator;
        synchronized (GeneticAlgorithm.class) {
            randomGenerator = randomGenerator;
        }
        return randomGenerator;
    }

    public Population evolve(Population initial, StoppingCondition condition) {
        Population current = initial;
        this.generationsEvolved = 0;
        while (!condition.isSatisfied(current)) {
            current = nextGeneration(current);
            this.generationsEvolved++;
        }
        return current;
    }

    public Population nextGeneration(Population current) {
        Population nextGeneration = current.nextGeneration();
        RandomGenerator randGen = getRandomGenerator();
        while (nextGeneration.getPopulationSize() < nextGeneration.getPopulationLimit()) {
            ChromosomePair pair = getSelectionPolicy().select(current);
            if (randGen.nextDouble() < getCrossoverRate()) {
                pair = getCrossoverPolicy().crossover(pair.getFirst(), pair.getSecond());
            }
            if (randGen.nextDouble() < getMutationRate()) {
                pair = new ChromosomePair(getMutationPolicy().mutate(pair.getFirst()), getMutationPolicy().mutate(pair.getSecond()));
            }
            nextGeneration.addChromosome(pair.getFirst());
            if (nextGeneration.getPopulationSize() < nextGeneration.getPopulationLimit()) {
                nextGeneration.addChromosome(pair.getSecond());
            }
        }
        return nextGeneration;
    }

    public CrossoverPolicy getCrossoverPolicy() {
        return this.crossoverPolicy;
    }

    public double getCrossoverRate() {
        return this.crossoverRate;
    }

    public MutationPolicy getMutationPolicy() {
        return this.mutationPolicy;
    }

    public double getMutationRate() {
        return this.mutationRate;
    }

    public SelectionPolicy getSelectionPolicy() {
        return this.selectionPolicy;
    }

    public int getGenerationsEvolved() {
        return this.generationsEvolved;
    }
}
