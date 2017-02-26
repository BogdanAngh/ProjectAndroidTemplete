package org.apache.commons.math4.genetics;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.util.LocalizedFormats;

public class TournamentSelection implements SelectionPolicy {
    private int arity;

    class 1 extends ListPopulation {
        1(int $anonymous0) throws NotPositiveException {
            super($anonymous0);
        }

        public Population nextGeneration() {
            return null;
        }
    }

    public TournamentSelection(int arity) {
        this.arity = arity;
    }

    public ChromosomePair select(Population population) throws MathIllegalArgumentException {
        return new ChromosomePair(tournament((ListPopulation) population), tournament((ListPopulation) population));
    }

    private Chromosome tournament(ListPopulation population) throws MathIllegalArgumentException {
        if (population.getPopulationSize() < this.arity) {
            throw new MathIllegalArgumentException(LocalizedFormats.TOO_LARGE_TOURNAMENT_ARITY, Integer.valueOf(this.arity), Integer.valueOf(population.getPopulationSize()));
        }
        ListPopulation tournamentPopulation = new 1(this.arity);
        List<Chromosome> chromosomes = new ArrayList(population.getChromosomes());
        for (int i = 0; i < this.arity; i++) {
            int rind = GeneticAlgorithm.getRandomGenerator().nextInt(chromosomes.size());
            tournamentPopulation.addChromosome((Chromosome) chromosomes.get(rind));
            chromosomes.remove(rind);
        }
        return tournamentPopulation.getFittestChromosome();
    }

    public int getArity() {
        return this.arity;
    }

    public void setArity(int arity) {
        this.arity = arity;
    }
}
