package org.apache.commons.math4.genetics;

public abstract class Chromosome implements Comparable<Chromosome>, Fitness {
    private static final double NO_FITNESS = Double.NEGATIVE_INFINITY;
    private double fitness;

    public Chromosome() {
        this.fitness = NO_FITNESS;
    }

    public double getFitness() {
        if (this.fitness == NO_FITNESS) {
            this.fitness = fitness();
        }
        return this.fitness;
    }

    public int compareTo(Chromosome another) {
        return Double.compare(getFitness(), another.getFitness());
    }

    protected boolean isSame(Chromosome another) {
        return false;
    }

    protected Chromosome findSameChromosome(Population population) {
        for (Chromosome anotherChr : population) {
            if (isSame(anotherChr)) {
                return anotherChr;
            }
        }
        return null;
    }

    public void searchForFitnessUpdate(Population population) {
        Chromosome sameChromosome = findSameChromosome(population);
        if (sameChromosome != null) {
            this.fitness = sameChromosome.getFitness();
        }
    }
}
