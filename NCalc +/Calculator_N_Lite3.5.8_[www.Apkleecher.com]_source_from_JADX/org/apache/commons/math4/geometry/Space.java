package org.apache.commons.math4.geometry;

import java.io.Serializable;
import org.apache.commons.math4.exception.MathUnsupportedOperationException;

public interface Space extends Serializable {
    int getDimension();

    Space getSubSpace() throws MathUnsupportedOperationException;
}
