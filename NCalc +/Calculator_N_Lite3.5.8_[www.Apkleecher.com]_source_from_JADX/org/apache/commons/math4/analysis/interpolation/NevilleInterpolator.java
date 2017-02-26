package org.apache.commons.math4.analysis.interpolation;

import java.io.Serializable;
import org.apache.commons.math4.analysis.polynomials.PolynomialFunctionLagrangeForm;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NonMonotonicSequenceException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;

public class NevilleInterpolator implements UnivariateInterpolator, Serializable {
    static final long serialVersionUID = 3003707660147873733L;

    public PolynomialFunctionLagrangeForm interpolate(double[] x, double[] y) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {
        return new PolynomialFunctionLagrangeForm(x, y);
    }
}
