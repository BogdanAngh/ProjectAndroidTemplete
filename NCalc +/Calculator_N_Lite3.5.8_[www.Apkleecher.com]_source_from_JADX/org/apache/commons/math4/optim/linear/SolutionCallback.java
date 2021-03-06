package org.apache.commons.math4.optim.linear;

import org.apache.commons.math4.optim.OptimizationData;
import org.apache.commons.math4.optim.PointValuePair;

public class SolutionCallback implements OptimizationData {
    private SimplexTableau tableau;

    void setTableau(SimplexTableau tableau) {
        this.tableau = tableau;
    }

    public PointValuePair getSolution() {
        return this.tableau != null ? this.tableau.getSolution() : null;
    }

    public boolean isSolutionOptimal() {
        return this.tableau != null ? this.tableau.isOptimal() : false;
    }
}
