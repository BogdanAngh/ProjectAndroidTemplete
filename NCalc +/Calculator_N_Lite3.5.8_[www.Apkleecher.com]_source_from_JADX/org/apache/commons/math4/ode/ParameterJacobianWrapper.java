package org.apache.commons.math4.ode;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MaxCountExceededException;

class ParameterJacobianWrapper implements ParameterJacobianProvider {
    private final FirstOrderDifferentialEquations fode;
    private final Map<String, Double> hParam;
    private final ParameterizedODE pode;

    public ParameterJacobianWrapper(FirstOrderDifferentialEquations fode, ParameterizedODE pode, ParameterConfiguration[] paramsAndSteps) {
        this.fode = fode;
        this.pode = pode;
        this.hParam = new HashMap();
        for (ParameterConfiguration param : paramsAndSteps) {
            String name = param.getParameterName();
            if (pode.isSupported(name)) {
                this.hParam.put(name, Double.valueOf(param.getHP()));
            }
        }
    }

    public Collection<String> getParametersNames() {
        return this.pode.getParametersNames();
    }

    public boolean isSupported(String name) {
        return this.pode.isSupported(name);
    }

    public void computeParameterJacobian(double t, double[] y, double[] yDot, String paramName, double[] dFdP) throws DimensionMismatchException, MaxCountExceededException {
        int n = this.fode.getDimension();
        if (this.pode.isSupported(paramName)) {
            double[] tmpDot = new double[n];
            double p = this.pode.getParameter(paramName);
            double hP = ((Double) this.hParam.get(paramName)).doubleValue();
            this.pode.setParameter(paramName, p + hP);
            this.fode.computeDerivatives(t, y, tmpDot);
            for (int i = 0; i < n; i++) {
                dFdP[i] = (tmpDot[i] - yDot[i]) / hP;
            }
            this.pode.setParameter(paramName, p);
            return;
        }
        Arrays.fill(dFdP, 0, n, 0.0d);
    }
}
