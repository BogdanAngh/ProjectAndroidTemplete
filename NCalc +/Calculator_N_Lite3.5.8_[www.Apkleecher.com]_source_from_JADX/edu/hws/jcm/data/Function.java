package edu.hws.jcm.data;

import java.io.Serializable;

public interface Function extends Serializable {
    boolean dependsOn(Variable variable);

    Function derivative(int i);

    Function derivative(Variable variable);

    int getArity();

    double getVal(double[] dArr);

    double getValueWithCases(double[] dArr, Cases cases);
}
