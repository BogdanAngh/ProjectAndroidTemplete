package org.apache.commons.math4.genetics;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.exception.util.LocalizedFormats;

public abstract class BinaryChromosome extends AbstractListChromosome<Integer> {
    public BinaryChromosome(List<Integer> representation) throws InvalidRepresentationException {
        super((List) representation);
    }

    public BinaryChromosome(Integer[] representation) throws InvalidRepresentationException {
        super((Object[]) representation);
    }

    protected void checkValidity(List<Integer> chromosomeRepresentation) throws InvalidRepresentationException {
        for (Integer intValue : chromosomeRepresentation) {
            int i = intValue.intValue();
            if (i >= 0) {
                if (i > 1) {
                }
            }
            throw new InvalidRepresentationException(LocalizedFormats.INVALID_BINARY_DIGIT, Integer.valueOf(i));
        }
    }

    public static List<Integer> randomBinaryRepresentation(int length) {
        List<Integer> rList = new ArrayList(length);
        for (int j = 0; j < length; j++) {
            rList.add(Integer.valueOf(GeneticAlgorithm.getRandomGenerator().nextInt(2)));
        }
        return rList;
    }

    protected boolean isSame(Chromosome another) {
        if (!(another instanceof BinaryChromosome)) {
            return false;
        }
        BinaryChromosome anotherBc = (BinaryChromosome) another;
        if (getLength() != anotherBc.getLength()) {
            return false;
        }
        for (int i = 0; i < getRepresentation().size(); i++) {
            if (!((Integer) getRepresentation().get(i)).equals(anotherBc.getRepresentation().get(i))) {
                return false;
            }
        }
        return true;
    }
}
