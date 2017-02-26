package org.apache.commons.math4.genetics;

import java.util.List;

public interface PermutationChromosome<T> {
    List<T> decode(List<T> list);
}
