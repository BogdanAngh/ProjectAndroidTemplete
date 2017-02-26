package org.apache.commons.math4.ode;

public interface ParameterizedODE extends Parameterizable {
    double getParameter(String str) throws UnknownParameterException;

    void setParameter(String str, double d) throws UnknownParameterException;
}
