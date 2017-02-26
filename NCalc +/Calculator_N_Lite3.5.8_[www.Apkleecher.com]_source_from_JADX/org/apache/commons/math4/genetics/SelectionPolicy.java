package org.apache.commons.math4.genetics;

import org.apache.commons.math4.exception.MathIllegalArgumentException;

public interface SelectionPolicy {
    ChromosomePair select(Population population) throws MathIllegalArgumentException;
}
