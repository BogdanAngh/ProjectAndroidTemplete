package org.apache.commons.math4.genetics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractListChromosome<T> extends Chromosome {
    private final List<T> representation;

    protected abstract void checkValidity(List<T> list) throws InvalidRepresentationException;

    public abstract AbstractListChromosome<T> newFixedLengthChromosome(List<T> list);

    public AbstractListChromosome(List<T> representation) throws InvalidRepresentationException {
        this(representation, true);
    }

    public AbstractListChromosome(T[] representation) throws InvalidRepresentationException {
        this(Arrays.asList(representation));
    }

    public AbstractListChromosome(List<T> representation, boolean copyList) {
        checkValidity(representation);
        if (copyList) {
            representation = new ArrayList(representation);
        }
        this.representation = Collections.unmodifiableList(representation);
    }

    protected List<T> getRepresentation() {
        return this.representation;
    }

    public int getLength() {
        return getRepresentation().size();
    }

    public String toString() {
        return String.format("(f=%s %s)", new Object[]{Double.valueOf(getFitness()), getRepresentation()});
    }
}
