package edu.hws.jcm.data;

public interface Expression extends Value {
    boolean dependsOn(Variable variable);

    Expression derivative(Variable variable);

    double getValueWithCases(Cases cases);

    String toString();
}
