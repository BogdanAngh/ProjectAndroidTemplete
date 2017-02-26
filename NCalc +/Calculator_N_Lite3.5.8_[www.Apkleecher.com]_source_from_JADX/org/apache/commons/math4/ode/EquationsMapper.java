package org.apache.commons.math4.ode;

import java.io.Serializable;
import org.apache.commons.math4.exception.DimensionMismatchException;

public class EquationsMapper implements Serializable {
    private static final long serialVersionUID = 20110925;
    private final int dimension;
    private final int firstIndex;

    public EquationsMapper(int firstIndex, int dimension) {
        this.firstIndex = firstIndex;
        this.dimension = dimension;
    }

    public int getFirstIndex() {
        return this.firstIndex;
    }

    public int getDimension() {
        return this.dimension;
    }

    public void extractEquationData(double[] complete, double[] equationData) throws DimensionMismatchException {
        if (equationData.length != this.dimension) {
            throw new DimensionMismatchException(equationData.length, this.dimension);
        }
        System.arraycopy(complete, this.firstIndex, equationData, 0, this.dimension);
    }

    public void insertEquationData(double[] equationData, double[] complete) throws DimensionMismatchException {
        if (equationData.length != this.dimension) {
            throw new DimensionMismatchException(equationData.length, this.dimension);
        }
        System.arraycopy(equationData, 0, complete, this.firstIndex, this.dimension);
    }
}
