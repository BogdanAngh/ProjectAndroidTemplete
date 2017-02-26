package org.apache.commons.math4.genetics;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;

public class BinaryMutation implements MutationPolicy {
    public Chromosome mutate(Chromosome original) throws MathIllegalArgumentException {
        if (original instanceof BinaryChromosome) {
            int i;
            BinaryChromosome origChrom = (BinaryChromosome) original;
            List<Integer> newRepr = new ArrayList(origChrom.getRepresentation());
            int geneIndex = GeneticAlgorithm.getRandomGenerator().nextInt(origChrom.getLength());
            if (((Integer) origChrom.getRepresentation().get(geneIndex)).intValue() == 0) {
                i = 1;
            } else {
                i = 0;
            }
            newRepr.set(geneIndex, Integer.valueOf(i));
            return origChrom.newFixedLengthChromosome(newRepr);
        }
        throw new MathIllegalArgumentException(LocalizedFormats.INVALID_BINARY_CHROMOSOME, new Object[0]);
    }
}
