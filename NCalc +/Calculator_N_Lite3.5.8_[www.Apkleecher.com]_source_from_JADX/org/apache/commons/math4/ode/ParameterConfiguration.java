package org.apache.commons.math4.ode;

import java.io.Serializable;

class ParameterConfiguration implements Serializable {
    private static final long serialVersionUID = 2247518849090889379L;
    private double hP;
    private String parameterName;

    public ParameterConfiguration(String parameterName, double hP) {
        this.parameterName = parameterName;
        this.hP = hP;
    }

    public String getParameterName() {
        return this.parameterName;
    }

    public double getHP() {
        return this.hP;
    }

    public void setHP(double hParam) {
        this.hP = hParam;
    }
}
