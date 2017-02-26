package org.apache.commons.math4.analysis.differentiation;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.math4.Field;
import org.apache.commons.math4.FieldElement;
import org.apache.commons.math4.RealFieldElement;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.MathUtils;
import org.apache.commons.math4.util.Precision;

public class SparseGradient implements RealFieldElement<SparseGradient>, Serializable {
    private static final long serialVersionUID = 20131025;
    private final Map<Integer, Double> derivatives;
    private double value;

    class 1 implements Field<SparseGradient> {
        1() {
        }

        public SparseGradient getZero() {
            return SparseGradient.createConstant(0.0d);
        }

        public SparseGradient getOne() {
            return SparseGradient.createConstant(1.0d);
        }

        public Class<? extends FieldElement<SparseGradient>> getRuntimeClass() {
            return SparseGradient.class;
        }
    }

    private SparseGradient(double value, Map<Integer, Double> derivatives) {
        this.value = value;
        this.derivatives = new HashMap();
        if (derivatives != null) {
            this.derivatives.putAll(derivatives);
        }
    }

    private SparseGradient(double value, double scale, Map<Integer, Double> derivatives) {
        this.value = value;
        this.derivatives = new HashMap();
        if (derivatives != null) {
            for (Entry<Integer, Double> entry : derivatives.entrySet()) {
                this.derivatives.put((Integer) entry.getKey(), Double.valueOf(((Double) entry.getValue()).doubleValue() * scale));
            }
        }
    }

    public static SparseGradient createConstant(double value) {
        return new SparseGradient(value, Collections.emptyMap());
    }

    public static SparseGradient createVariable(int idx, double value) {
        return new SparseGradient(value, Collections.singletonMap(Integer.valueOf(idx), Double.valueOf(1.0d)));
    }

    public int numVars() {
        return this.derivatives.size();
    }

    public double getDerivative(int index) {
        Double out = (Double) this.derivatives.get(Integer.valueOf(index));
        return out == null ? 0.0d : out.doubleValue();
    }

    public double getValue() {
        return this.value;
    }

    public double getReal() {
        return this.value;
    }

    public SparseGradient add(SparseGradient a) {
        SparseGradient out = new SparseGradient(this.value + a.value, this.derivatives);
        for (Entry<Integer, Double> entry : a.derivatives.entrySet()) {
            int id = ((Integer) entry.getKey()).intValue();
            Double old = (Double) out.derivatives.get(Integer.valueOf(id));
            if (old == null) {
                out.derivatives.put(Integer.valueOf(id), (Double) entry.getValue());
            } else {
                out.derivatives.put(Integer.valueOf(id), Double.valueOf(old.doubleValue() + ((Double) entry.getValue()).doubleValue()));
            }
        }
        return out;
    }

    public void addInPlace(SparseGradient a) {
        this.value += a.value;
        for (Entry<Integer, Double> entry : a.derivatives.entrySet()) {
            int id = ((Integer) entry.getKey()).intValue();
            Double old = (Double) this.derivatives.get(Integer.valueOf(id));
            if (old == null) {
                this.derivatives.put(Integer.valueOf(id), (Double) entry.getValue());
            } else {
                this.derivatives.put(Integer.valueOf(id), Double.valueOf(old.doubleValue() + ((Double) entry.getValue()).doubleValue()));
            }
        }
    }

    public SparseGradient add(double c) {
        return new SparseGradient(this.value + c, this.derivatives);
    }

    public SparseGradient subtract(SparseGradient a) {
        SparseGradient out = new SparseGradient(this.value - a.value, this.derivatives);
        for (Entry<Integer, Double> entry : a.derivatives.entrySet()) {
            int id = ((Integer) entry.getKey()).intValue();
            Double old = (Double) out.derivatives.get(Integer.valueOf(id));
            if (old == null) {
                out.derivatives.put(Integer.valueOf(id), Double.valueOf(-((Double) entry.getValue()).doubleValue()));
            } else {
                out.derivatives.put(Integer.valueOf(id), Double.valueOf(old.doubleValue() - ((Double) entry.getValue()).doubleValue()));
            }
        }
        return out;
    }

    public SparseGradient subtract(double c) {
        return new SparseGradient(this.value - c, this.derivatives);
    }

    public SparseGradient multiply(SparseGradient a) {
        SparseGradient out = new SparseGradient(this.value * a.value, Collections.emptyMap());
        for (Entry<Integer, Double> entry : this.derivatives.entrySet()) {
            out.derivatives.put((Integer) entry.getKey(), Double.valueOf(a.value * ((Double) entry.getValue()).doubleValue()));
        }
        for (Entry<Integer, Double> entry2 : a.derivatives.entrySet()) {
            int id = ((Integer) entry2.getKey()).intValue();
            Double old = (Double) out.derivatives.get(Integer.valueOf(id));
            if (old == null) {
                out.derivatives.put(Integer.valueOf(id), Double.valueOf(this.value * ((Double) entry2.getValue()).doubleValue()));
            } else {
                out.derivatives.put(Integer.valueOf(id), Double.valueOf(old.doubleValue() + (this.value * ((Double) entry2.getValue()).doubleValue())));
            }
        }
        return out;
    }

    public void multiplyInPlace(SparseGradient a) {
        for (Entry<Integer, Double> entry : this.derivatives.entrySet()) {
            this.derivatives.put((Integer) entry.getKey(), Double.valueOf(a.value * ((Double) entry.getValue()).doubleValue()));
        }
        for (Entry<Integer, Double> entry2 : a.derivatives.entrySet()) {
            int id = ((Integer) entry2.getKey()).intValue();
            Double old = (Double) this.derivatives.get(Integer.valueOf(id));
            if (old == null) {
                this.derivatives.put(Integer.valueOf(id), Double.valueOf(this.value * ((Double) entry2.getValue()).doubleValue()));
            } else {
                this.derivatives.put(Integer.valueOf(id), Double.valueOf(old.doubleValue() + (this.value * ((Double) entry2.getValue()).doubleValue())));
            }
        }
        this.value *= a.value;
    }

    public SparseGradient multiply(double c) {
        return new SparseGradient(this.value * c, c, this.derivatives);
    }

    public SparseGradient multiply(int n) {
        return new SparseGradient(this.value * ((double) n), (double) n, this.derivatives);
    }

    public SparseGradient divide(SparseGradient a) {
        SparseGradient out = new SparseGradient(this.value / a.value, Collections.emptyMap());
        for (Entry<Integer, Double> entry : this.derivatives.entrySet()) {
            out.derivatives.put((Integer) entry.getKey(), Double.valueOf(((Double) entry.getValue()).doubleValue() / a.value));
        }
        for (Entry<Integer, Double> entry2 : a.derivatives.entrySet()) {
            int id = ((Integer) entry2.getKey()).intValue();
            Double old = (Double) out.derivatives.get(Integer.valueOf(id));
            if (old == null) {
                out.derivatives.put(Integer.valueOf(id), Double.valueOf(((-out.value) / a.value) * ((Double) entry2.getValue()).doubleValue()));
            } else {
                out.derivatives.put(Integer.valueOf(id), Double.valueOf(old.doubleValue() - ((out.value / a.value) * ((Double) entry2.getValue()).doubleValue())));
            }
        }
        return out;
    }

    public SparseGradient divide(double c) {
        return new SparseGradient(this.value / c, 1.0d / c, this.derivatives);
    }

    public SparseGradient negate() {
        return new SparseGradient(-this.value, -1.0d, this.derivatives);
    }

    public Field<SparseGradient> getField() {
        return new 1();
    }

    public SparseGradient remainder(double a) {
        return new SparseGradient(FastMath.IEEEremainder(this.value, a), this.derivatives);
    }

    public SparseGradient remainder(SparseGradient a) {
        return subtract(a.multiply(FastMath.rint((this.value - FastMath.IEEEremainder(this.value, a.value)) / a.value)));
    }

    public SparseGradient abs() {
        if (Double.doubleToLongBits(this.value) < 0) {
            return negate();
        }
        return this;
    }

    public SparseGradient ceil() {
        return createConstant(FastMath.ceil(this.value));
    }

    public SparseGradient floor() {
        return createConstant(FastMath.floor(this.value));
    }

    public SparseGradient rint() {
        return createConstant(FastMath.rint(this.value));
    }

    public long round() {
        return FastMath.round(this.value);
    }

    public SparseGradient signum() {
        return createConstant(FastMath.signum(this.value));
    }

    public SparseGradient copySign(SparseGradient sign) {
        long m = Double.doubleToLongBits(this.value);
        long s = Double.doubleToLongBits(sign.value);
        if (m < 0 || s < 0) {
            return (m >= 0 || s >= 0) ? negate() : this;
        } else {
            return this;
        }
    }

    public SparseGradient copySign(double sign) {
        long m = Double.doubleToLongBits(this.value);
        long s = Double.doubleToLongBits(sign);
        if (m < 0 || s < 0) {
            return (m >= 0 || s >= 0) ? negate() : this;
        } else {
            return this;
        }
    }

    public SparseGradient scalb(int n) {
        SparseGradient out = new SparseGradient(FastMath.scalb(this.value, n), Collections.emptyMap());
        for (Entry<Integer, Double> entry : this.derivatives.entrySet()) {
            out.derivatives.put((Integer) entry.getKey(), Double.valueOf(FastMath.scalb(((Double) entry.getValue()).doubleValue(), n)));
        }
        return out;
    }

    public SparseGradient hypot(SparseGradient y) {
        if (Double.isInfinite(this.value) || Double.isInfinite(y.value)) {
            return createConstant(Double.POSITIVE_INFINITY);
        }
        if (Double.isNaN(this.value) || Double.isNaN(y.value)) {
            return createConstant(Double.NaN);
        }
        int expX = FastMath.getExponent(this.value);
        int expY = FastMath.getExponent(y.value);
        if (expX > expY + 27) {
            return abs();
        }
        if (expY > expX + 27) {
            return y.abs();
        }
        int middleExp = (expX + expY) / 2;
        SparseGradient scaledX = scalb(-middleExp);
        SparseGradient scaledY = y.scalb(-middleExp);
        return scaledX.multiply(scaledX).add(scaledY.multiply(scaledY)).sqrt().scalb(middleExp);
    }

    public static SparseGradient hypot(SparseGradient x, SparseGradient y) {
        return x.hypot(y);
    }

    public SparseGradient reciprocal() {
        return new SparseGradient(1.0d / this.value, -1.0d / (this.value * this.value), this.derivatives);
    }

    public SparseGradient sqrt() {
        double sqrt = FastMath.sqrt(this.value);
        return new SparseGradient(sqrt, 0.5d / sqrt, this.derivatives);
    }

    public SparseGradient cbrt() {
        double cbrt = FastMath.cbrt(this.value);
        return new SparseGradient(cbrt, 1.0d / ((3.0d * cbrt) * cbrt), this.derivatives);
    }

    public SparseGradient rootN(int n) {
        if (n == 2) {
            return sqrt();
        }
        if (n == 3) {
            return cbrt();
        }
        double root = FastMath.pow(this.value, 1.0d / ((double) n));
        return new SparseGradient(root, 1.0d / (((double) n) * FastMath.pow(root, n - 1)), this.derivatives);
    }

    public SparseGradient pow(double p) {
        return new SparseGradient(FastMath.pow(this.value, p), FastMath.pow(this.value, p - 1.0d) * p, this.derivatives);
    }

    public SparseGradient pow(int n) {
        if (n == 0) {
            return (SparseGradient) getField().getOne();
        }
        double valueNm1 = FastMath.pow(this.value, n - 1);
        return new SparseGradient(this.value * valueNm1, ((double) n) * valueNm1, this.derivatives);
    }

    public SparseGradient pow(SparseGradient e) {
        return log().multiply(e).exp();
    }

    public static SparseGradient pow(double a, SparseGradient x) {
        if (a != 0.0d) {
            double ax = FastMath.pow(a, x.value);
            return new SparseGradient(ax, FastMath.log(a) * ax, x.derivatives);
        } else if (x.value == 0.0d) {
            return x.compose(1.0d, Double.NEGATIVE_INFINITY);
        } else {
            if (x.value < 0.0d) {
                return x.compose(Double.NaN, Double.NaN);
            }
            return (SparseGradient) x.getField().getZero();
        }
    }

    public SparseGradient exp() {
        double e = FastMath.exp(this.value);
        return new SparseGradient(e, e, this.derivatives);
    }

    public SparseGradient expm1() {
        return new SparseGradient(FastMath.expm1(this.value), FastMath.exp(this.value), this.derivatives);
    }

    public SparseGradient log() {
        return new SparseGradient(FastMath.log(this.value), 1.0d / this.value, this.derivatives);
    }

    public SparseGradient log10() {
        return new SparseGradient(FastMath.log10(this.value), 1.0d / (FastMath.log(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS) * this.value), this.derivatives);
    }

    public SparseGradient log1p() {
        return new SparseGradient(FastMath.log1p(this.value), 1.0d / (this.value + 1.0d), this.derivatives);
    }

    public SparseGradient cos() {
        return new SparseGradient(FastMath.cos(this.value), -FastMath.sin(this.value), this.derivatives);
    }

    public SparseGradient sin() {
        return new SparseGradient(FastMath.sin(this.value), FastMath.cos(this.value), this.derivatives);
    }

    public SparseGradient tan() {
        double t = FastMath.tan(this.value);
        return new SparseGradient(t, 1.0d + (t * t), this.derivatives);
    }

    public SparseGradient acos() {
        return new SparseGradient(FastMath.acos(this.value), -1.0d / FastMath.sqrt(1.0d - (this.value * this.value)), this.derivatives);
    }

    public SparseGradient asin() {
        return new SparseGradient(FastMath.asin(this.value), 1.0d / FastMath.sqrt(1.0d - (this.value * this.value)), this.derivatives);
    }

    public SparseGradient atan() {
        return new SparseGradient(FastMath.atan(this.value), 1.0d / ((this.value * this.value) + 1.0d), this.derivatives);
    }

    public SparseGradient atan2(SparseGradient x) {
        SparseGradient a;
        SparseGradient r = multiply(this).add(x.multiply(x)).sqrt();
        if (x.value >= 0.0d) {
            a = divide(r.add(x)).atan().multiply(2);
        } else {
            SparseGradient tmp = divide(r.subtract(x)).atan().multiply(-2);
            a = tmp.add(tmp.value <= 0.0d ? -3.141592653589793d : FastMath.PI);
        }
        a.value = FastMath.atan2(this.value, x.value);
        return a;
    }

    public static SparseGradient atan2(SparseGradient y, SparseGradient x) {
        return y.atan2(x);
    }

    public SparseGradient cosh() {
        return new SparseGradient(FastMath.cosh(this.value), FastMath.sinh(this.value), this.derivatives);
    }

    public SparseGradient sinh() {
        return new SparseGradient(FastMath.sinh(this.value), FastMath.cosh(this.value), this.derivatives);
    }

    public SparseGradient tanh() {
        double t = FastMath.tanh(this.value);
        return new SparseGradient(t, 1.0d - (t * t), this.derivatives);
    }

    public SparseGradient acosh() {
        return new SparseGradient(FastMath.acosh(this.value), 1.0d / FastMath.sqrt((this.value * this.value) - 1.0d), this.derivatives);
    }

    public SparseGradient asinh() {
        return new SparseGradient(FastMath.asinh(this.value), 1.0d / FastMath.sqrt((this.value * this.value) + 1.0d), this.derivatives);
    }

    public SparseGradient atanh() {
        return new SparseGradient(FastMath.atanh(this.value), 1.0d / (1.0d - (this.value * this.value)), this.derivatives);
    }

    public SparseGradient toDegrees() {
        return new SparseGradient(FastMath.toDegrees(this.value), FastMath.toDegrees(1.0d), this.derivatives);
    }

    public SparseGradient toRadians() {
        return new SparseGradient(FastMath.toRadians(this.value), FastMath.toRadians(1.0d), this.derivatives);
    }

    public double taylor(double... delta) {
        double y = this.value;
        for (int i = 0; i < delta.length; i++) {
            y += delta[i] * getDerivative(i);
        }
        return y;
    }

    public SparseGradient compose(double f0, double f1) {
        return new SparseGradient(f0, f1, this.derivatives);
    }

    public SparseGradient linearCombination(SparseGradient[] a, SparseGradient[] b) throws DimensionMismatchException {
        int i;
        SparseGradient out = (SparseGradient) a[0].getField().getZero();
        for (i = 0; i < a.length; i++) {
            out = out.add(a[i].multiply(b[i]));
        }
        double[] aDouble = new double[a.length];
        for (i = 0; i < a.length; i++) {
            aDouble[i] = a[i].getValue();
        }
        double[] bDouble = new double[b.length];
        for (i = 0; i < b.length; i++) {
            bDouble[i] = b[i].getValue();
        }
        out.value = MathArrays.linearCombination(aDouble, bDouble);
        return out;
    }

    public SparseGradient linearCombination(double[] a, SparseGradient[] b) {
        int i;
        SparseGradient out = (SparseGradient) b[0].getField().getZero();
        for (i = 0; i < a.length; i++) {
            out = out.add(b[i].multiply(a[i]));
        }
        double[] bDouble = new double[b.length];
        for (i = 0; i < b.length; i++) {
            bDouble[i] = b[i].getValue();
        }
        out.value = MathArrays.linearCombination(a, bDouble);
        return out;
    }

    public SparseGradient linearCombination(SparseGradient a1, SparseGradient b1, SparseGradient a2, SparseGradient b2) {
        SparseGradient out = a1.multiply(b1).add(a2.multiply(b2));
        out.value = MathArrays.linearCombination(a1.value, b1.value, a2.value, b2.value);
        return out;
    }

    public SparseGradient linearCombination(double a1, SparseGradient b1, double a2, SparseGradient b2) {
        SparseGradient out = b1.multiply(a1).add(b2.multiply(a2));
        out.value = MathArrays.linearCombination(a1, b1.value, a2, b2.value);
        return out;
    }

    public SparseGradient linearCombination(SparseGradient a1, SparseGradient b1, SparseGradient a2, SparseGradient b2, SparseGradient a3, SparseGradient b3) {
        SparseGradient out = a1.multiply(b1).add(a2.multiply(b2)).add(a3.multiply(b3));
        out.value = MathArrays.linearCombination(a1.value, b1.value, a2.value, b2.value, a3.value, b3.value);
        return out;
    }

    public SparseGradient linearCombination(double a1, SparseGradient b1, double a2, SparseGradient b2, double a3, SparseGradient b3) {
        SparseGradient out = b1.multiply(a1).add(b2.multiply(a2)).add(b3.multiply(a3));
        out.value = MathArrays.linearCombination(a1, b1.value, a2, b2.value, a3, b3.value);
        return out;
    }

    public SparseGradient linearCombination(SparseGradient a1, SparseGradient b1, SparseGradient a2, SparseGradient b2, SparseGradient a3, SparseGradient b3, SparseGradient a4, SparseGradient b4) {
        SparseGradient out = a1.multiply(b1).add(a2.multiply(b2)).add(a3.multiply(b3)).add(a4.multiply(b4));
        out.value = MathArrays.linearCombination(a1.value, b1.value, a2.value, b2.value, a3.value, b3.value, a4.value, b4.value);
        return out;
    }

    public SparseGradient linearCombination(double a1, SparseGradient b1, double a2, SparseGradient b2, double a3, SparseGradient b3, double a4, SparseGradient b4) {
        SparseGradient out = b1.multiply(a1).add(b2.multiply(a2)).add(b3.multiply(a3)).add(b4.multiply(a4));
        double d = a1;
        double d2 = a2;
        double d3 = a3;
        double d4 = a4;
        out.value = MathArrays.linearCombination(d, b1.value, d2, b2.value, d3, b3.value, d4, b4.value);
        return out;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SparseGradient)) {
            return false;
        }
        SparseGradient rhs = (SparseGradient) other;
        if (!Precision.equals(this.value, rhs.value, 1)) {
            return false;
        }
        if (this.derivatives.size() != rhs.derivatives.size()) {
            return false;
        }
        for (Entry<Integer, Double> entry : this.derivatives.entrySet()) {
            if (!rhs.derivatives.containsKey(entry.getKey())) {
                return false;
            }
            if (!Precision.equals(((Double) entry.getValue()).doubleValue(), ((Double) rhs.derivatives.get(entry.getKey())).doubleValue(), 1)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return ((MathUtils.hash(this.value) * 809) + 743) + (this.derivatives.hashCode() * 167);
    }
}
