package org.apache.commons.math4.analysis.differentiation;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.MathInternalError;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;
import org.apache.commons.math4.util.CombinatoricsUtils;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;

public class DSCompiler {
    private static AtomicReference<DSCompiler[][]> compilers;
    private final int[][][] compIndirection;
    private final int[][] derivativesIndirection;
    private final int[] lowerIndirection;
    private final int[][][] multIndirection;
    private final int order;
    private final int parameters;
    private final int[][] sizes;

    static {
        compilers = new AtomicReference(null);
    }

    private DSCompiler(int parameters, int order, DSCompiler valueCompiler, DSCompiler derivativeCompiler) throws NumberIsTooLargeException {
        this.parameters = parameters;
        this.order = order;
        this.sizes = compileSizes(parameters, order, valueCompiler);
        this.derivativesIndirection = compileDerivativesIndirection(parameters, order, valueCompiler, derivativeCompiler);
        this.lowerIndirection = compileLowerIndirection(parameters, order, valueCompiler, derivativeCompiler);
        this.multIndirection = compileMultiplicationIndirection(parameters, order, valueCompiler, derivativeCompiler, this.lowerIndirection);
        this.compIndirection = compileCompositionIndirection(parameters, order, valueCompiler, derivativeCompiler, this.sizes, this.derivativesIndirection);
    }

    public static DSCompiler getCompiler(int parameters, int order) throws NumberIsTooLargeException {
        DSCompiler[][] cache = (DSCompiler[][]) compilers.get();
        if (cache != null && cache.length > parameters && cache[parameters].length > order && cache[parameters][order] != null) {
            return cache[parameters][order];
        }
        DSCompiler[][] newCache = (DSCompiler[][]) Array.newInstance(DSCompiler.class, new int[]{FastMath.max(parameters, cache == null ? 0 : cache.length) + 1, FastMath.max(order, cache == null ? 0 : cache[0].length) + 1});
        if (cache != null) {
            for (int i = 0; i < cache.length; i++) {
                System.arraycopy(cache[i], 0, newCache[i], 0, cache[i].length);
            }
        }
        for (int diag = 0; diag <= parameters + order; diag++) {
            int o = FastMath.max(0, diag - parameters);
            while (o <= FastMath.min(order, diag)) {
                int p = diag - o;
                if (newCache[p][o] == null) {
                    newCache[p][o] = new DSCompiler(p, o, p == 0 ? null : newCache[p - 1][o], o == 0 ? null : newCache[p][o - 1]);
                }
                o++;
            }
        }
        compilers.compareAndSet(cache, newCache);
        return newCache[parameters][order];
    }

    private static int[][] compileSizes(int parameters, int order, DSCompiler valueCompiler) {
        int[][] sizes = (int[][]) Array.newInstance(Integer.TYPE, new int[]{parameters + 1, order + 1});
        if (parameters == 0) {
            Arrays.fill(sizes[0], 1);
        } else {
            System.arraycopy(valueCompiler.sizes, 0, sizes, 0, parameters);
            sizes[parameters][0] = 1;
            for (int i = 0; i < order; i++) {
                sizes[parameters][i + 1] = sizes[parameters][i] + sizes[parameters - 1][i + 1];
            }
        }
        return sizes;
    }

    private static int[][] compileDerivativesIndirection(int parameters, int order, DSCompiler valueCompiler, DSCompiler derivativeCompiler) {
        if (parameters == 0 || order == 0) {
            return (int[][]) Array.newInstance(Integer.TYPE, new int[]{1, parameters});
        }
        int i;
        int vSize = valueCompiler.derivativesIndirection.length;
        int dSize = derivativeCompiler.derivativesIndirection.length;
        int[][] derivativesIndirection = (int[][]) Array.newInstance(Integer.TYPE, new int[]{vSize + dSize, parameters});
        for (i = 0; i < vSize; i++) {
            System.arraycopy(valueCompiler.derivativesIndirection[i], 0, derivativesIndirection[i], 0, parameters - 1);
        }
        for (i = 0; i < dSize; i++) {
            System.arraycopy(derivativeCompiler.derivativesIndirection[i], 0, derivativesIndirection[vSize + i], 0, parameters);
            int[] iArr = derivativesIndirection[vSize + i];
            int i2 = parameters - 1;
            iArr[i2] = iArr[i2] + 1;
        }
        return derivativesIndirection;
    }

    private static int[] compileLowerIndirection(int parameters, int order, DSCompiler valueCompiler, DSCompiler derivativeCompiler) {
        if (parameters == 0 || order <= 1) {
            return new int[1];
        }
        int vSize = valueCompiler.lowerIndirection.length;
        int dSize = derivativeCompiler.lowerIndirection.length;
        int[] lowerIndirection = new int[(vSize + dSize)];
        System.arraycopy(valueCompiler.lowerIndirection, 0, lowerIndirection, 0, vSize);
        for (int i = 0; i < dSize; i++) {
            lowerIndirection[vSize + i] = valueCompiler.getSize() + derivativeCompiler.lowerIndirection[i];
        }
        return lowerIndirection;
    }

    private static int[][][] compileMultiplicationIndirection(int parameters, int order, DSCompiler valueCompiler, DSCompiler derivativeCompiler, int[] lowerIndirection) {
        if (parameters == 0 || order == 0) {
            int[][] iArr = new int[1][];
            int[] iArr2 = new int[]{1, iArr2, iArr};
            return new int[1][][];
        }
        int vSize = valueCompiler.multIndirection.length;
        int dSize = derivativeCompiler.multIndirection.length;
        int[][][] multIndirection = new int[(vSize + dSize)][][];
        System.arraycopy(valueCompiler.multIndirection, 0, multIndirection, 0, vSize);
        for (int i = 0; i < dSize; i++) {
            int j;
            int[][] dRow = derivativeCompiler.multIndirection[i];
            List<int[]> row = new ArrayList(dRow.length * 2);
            for (j = 0; j < dRow.length; j++) {
                row.add(new int[]{dRow[j][0], lowerIndirection[dRow[j][1]], dRow[j][2] + vSize});
                row.add(new int[]{dRow[j][0], dRow[j][1] + vSize, lowerIndirection[dRow[j][2]]});
            }
            List<int[]> combined = new ArrayList(row.size());
            for (j = 0; j < row.size(); j++) {
                int[] termJ = (int[]) row.get(j);
                if (termJ[0] > 0) {
                    for (int k = j + 1; k < row.size(); k++) {
                        int[] termK = (int[]) row.get(k);
                        if (termJ[1] == termK[1] && termJ[2] == termK[2]) {
                            termJ[0] = termJ[0] + termK[0];
                            termK[0] = 0;
                        }
                    }
                    combined.add(termJ);
                }
            }
            multIndirection[vSize + i] = (int[][]) combined.toArray(new int[combined.size()][]);
        }
        return multIndirection;
    }

    private static int[][][] compileCompositionIndirection(int parameters, int order, DSCompiler valueCompiler, DSCompiler derivativeCompiler, int[][] sizes, int[][] derivativesIndirection) throws NumberIsTooLargeException {
        if (parameters == 0 || order == 0) {
            int[][][] iArr = new int[1][][];
            int[] iArr2 = new int[]{1, iArr2};
            iArr[0] = new int[1][];
            return iArr;
        }
        int vSize = valueCompiler.compIndirection.length;
        int dSize = derivativeCompiler.compIndirection.length;
        iArr = new int[(vSize + dSize)][][];
        System.arraycopy(valueCompiler.compIndirection, 0, iArr, 0, vSize);
        for (int i = 0; i < dSize; i++) {
            List<int[]> row = new ArrayList();
            for (int[] term : derivativeCompiler.compIndirection[i]) {
                int j;
                int l;
                int[] derivedTermF = new int[(term.length + 1)];
                derivedTermF[0] = term[0];
                derivedTermF[1] = term[1] + 1;
                Object orders = new int[parameters];
                orders[parameters - 1] = 1;
                derivedTermF[term.length] = getPartialDerivativeIndex(parameters, order, sizes, orders);
                for (j = 2; j < term.length; j++) {
                    derivedTermF[j] = convertIndex(term[j], parameters, derivativeCompiler.derivativesIndirection, parameters, order, sizes);
                }
                Arrays.sort(derivedTermF, 2, derivedTermF.length);
                row.add(derivedTermF);
                for (l = 2; l < term.length; l++) {
                    int[] derivedTermG = new int[term.length];
                    derivedTermG[0] = term[0];
                    derivedTermG[1] = term[1];
                    for (j = 2; j < term.length; j++) {
                        derivedTermG[j] = convertIndex(term[j], parameters, derivativeCompiler.derivativesIndirection, parameters, order, sizes);
                        if (j == l) {
                            System.arraycopy(derivativesIndirection[derivedTermG[j]], 0, orders, 0, parameters);
                            int i2 = parameters - 1;
                            orders[i2] = orders[i2] + 1;
                            derivedTermG[j] = getPartialDerivativeIndex(parameters, order, sizes, orders);
                        }
                    }
                    Arrays.sort(derivedTermG, 2, derivedTermG.length);
                    row.add(derivedTermG);
                }
            }
            List<int[]> combined = new ArrayList(row.size());
            for (j = 0; j < row.size(); j++) {
                Object termJ = (int[]) row.get(j);
                if (termJ[0] > 0) {
                    for (int k = j + 1; k < row.size(); k++) {
                        int[] termK = (int[]) row.get(k);
                        boolean equals = termJ.length == termK.length;
                        l = 1;
                        while (equals && l < termJ.length) {
                            equals &= termJ[l] == termK[l] ? 1 : 0;
                            l++;
                        }
                        if (equals) {
                            termJ[0] = termJ[0] + termK[0];
                            termK[0] = 0;
                        }
                    }
                    combined.add(termJ);
                }
            }
            iArr[vSize + i] = (int[][]) combined.toArray(new int[combined.size()][]);
        }
        return iArr;
    }

    public int getPartialDerivativeIndex(int... orders) throws DimensionMismatchException, NumberIsTooLargeException {
        if (orders.length == getFreeParameters()) {
            return getPartialDerivativeIndex(this.parameters, this.order, this.sizes, orders);
        }
        throw new DimensionMismatchException(orders.length, getFreeParameters());
    }

    private static int getPartialDerivativeIndex(int parameters, int order, int[][] sizes, int... orders) throws NumberIsTooLargeException {
        int index = 0;
        int m = order;
        int ordersSum = 0;
        int i = parameters - 1;
        while (i >= 0) {
            int derivativeOrder = orders[i];
            ordersSum += derivativeOrder;
            if (ordersSum > order) {
                throw new NumberIsTooLargeException(Integer.valueOf(ordersSum), Integer.valueOf(order), true);
            }
            int derivativeOrder2 = derivativeOrder;
            int m2 = m;
            while (true) {
                derivativeOrder = derivativeOrder2 - 1;
                if (derivativeOrder2 <= 0) {
                    break;
                }
                index += sizes[i][m2];
                derivativeOrder2 = derivativeOrder;
                m2--;
            }
            i--;
            m = m2;
        }
        return index;
    }

    private static int convertIndex(int index, int srcP, int[][] srcDerivativesIndirection, int destP, int destO, int[][] destSizes) throws NumberIsTooLargeException {
        int[] orders = new int[destP];
        System.arraycopy(srcDerivativesIndirection[index], 0, orders, 0, FastMath.min(srcP, destP));
        return getPartialDerivativeIndex(destP, destO, destSizes, orders);
    }

    public int[] getPartialDerivativeOrders(int index) {
        return this.derivativesIndirection[index];
    }

    public int getFreeParameters() {
        return this.parameters;
    }

    public int getOrder() {
        return this.order;
    }

    public int getSize() {
        return this.sizes[this.parameters][this.order];
    }

    public void linearCombination(double a1, double[] c1, int offset1, double a2, double[] c2, int offset2, double[] result, int resultOffset) {
        for (int i = 0; i < getSize(); i++) {
            result[resultOffset + i] = MathArrays.linearCombination(a1, c1[offset1 + i], a2, c2[offset2 + i]);
        }
    }

    public void linearCombination(double a1, double[] c1, int offset1, double a2, double[] c2, int offset2, double a3, double[] c3, int offset3, double[] result, int resultOffset) {
        for (int i = 0; i < getSize(); i++) {
            result[resultOffset + i] = MathArrays.linearCombination(a1, c1[offset1 + i], a2, c2[offset2 + i], a3, c3[offset3 + i]);
        }
    }

    public void linearCombination(double a1, double[] c1, int offset1, double a2, double[] c2, int offset2, double a3, double[] c3, int offset3, double a4, double[] c4, int offset4, double[] result, int resultOffset) {
        for (int i = 0; i < getSize(); i++) {
            result[resultOffset + i] = MathArrays.linearCombination(a1, c1[offset1 + i], a2, c2[offset2 + i], a3, c3[offset3 + i], a4, c4[offset4 + i]);
        }
    }

    public void add(double[] lhs, int lhsOffset, double[] rhs, int rhsOffset, double[] result, int resultOffset) {
        for (int i = 0; i < getSize(); i++) {
            result[resultOffset + i] = lhs[lhsOffset + i] + rhs[rhsOffset + i];
        }
    }

    public void subtract(double[] lhs, int lhsOffset, double[] rhs, int rhsOffset, double[] result, int resultOffset) {
        for (int i = 0; i < getSize(); i++) {
            result[resultOffset + i] = lhs[lhsOffset + i] - rhs[rhsOffset + i];
        }
    }

    public void multiply(double[] lhs, int lhsOffset, double[] rhs, int rhsOffset, double[] result, int resultOffset) {
        for (int i = 0; i < this.multIndirection.length; i++) {
            int[][] mappingI = this.multIndirection[i];
            double r = 0.0d;
            for (int j = 0; j < mappingI.length; j++) {
                r += (((double) mappingI[j][0]) * lhs[mappingI[j][1] + lhsOffset]) * rhs[mappingI[j][2] + rhsOffset];
            }
            result[resultOffset + i] = r;
        }
    }

    public void divide(double[] lhs, int lhsOffset, double[] rhs, int rhsOffset, double[] result, int resultOffset) {
        double[] reciprocal = new double[getSize()];
        pow(rhs, lhsOffset, -1, reciprocal, 0);
        multiply(lhs, lhsOffset, reciprocal, 0, result, resultOffset);
    }

    public void remainder(double[] lhs, int lhsOffset, double[] rhs, int rhsOffset, double[] result, int resultOffset) {
        double rem = FastMath.IEEEremainder(lhs[lhsOffset], rhs[rhsOffset]);
        double k = FastMath.rint((lhs[lhsOffset] - rem) / rhs[rhsOffset]);
        result[resultOffset] = rem;
        for (int i = 1; i < getSize(); i++) {
            result[resultOffset + i] = lhs[lhsOffset + i] - (rhs[rhsOffset + i] * k);
        }
    }

    public void pow(double a, double[] operand, int operandOffset, double[] result, int resultOffset) {
        double[] function = new double[(this.order + 1)];
        int i;
        if (a != 0.0d) {
            function[0] = FastMath.pow(a, operand[operandOffset]);
            double lnA = FastMath.log(a);
            for (i = 1; i < function.length; i++) {
                function[i] = function[i - 1] * lnA;
            }
        } else if (operand[operandOffset] == 0.0d) {
            function[0] = 1.0d;
            double infinity = Double.POSITIVE_INFINITY;
            for (i = 1; i < function.length; i++) {
                infinity = -infinity;
                function[i] = infinity;
            }
        } else if (operand[operandOffset] < 0.0d) {
            Arrays.fill(function, Double.NaN);
        }
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void pow(double[] operand, int operandOffset, double p, double[] result, int resultOffset) {
        int i;
        double[] function = new double[(this.order + 1)];
        double xk = FastMath.pow(operand[operandOffset], p - ((double) this.order));
        for (i = this.order; i > 0; i--) {
            function[i] = xk;
            xk *= operand[operandOffset];
        }
        function[0] = xk;
        double coefficient = p;
        for (i = 1; i <= this.order; i++) {
            function[i] = function[i] * coefficient;
            coefficient *= p - ((double) i);
        }
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void pow(double[] operand, int operandOffset, int n, double[] result, int resultOffset) {
        if (n == 0) {
            result[resultOffset] = 1.0d;
            Arrays.fill(result, resultOffset + 1, getSize() + resultOffset, 0.0d);
            return;
        }
        int i;
        double[] function = new double[(this.order + 1)];
        double xk;
        if (n > 0) {
            int maxOrder = FastMath.min(this.order, n);
            xk = FastMath.pow(operand[operandOffset], n - maxOrder);
            for (i = maxOrder; i > 0; i--) {
                function[i] = xk;
                xk *= operand[operandOffset];
            }
            function[0] = xk;
        } else {
            double inv = 1.0d / operand[operandOffset];
            xk = FastMath.pow(inv, -n);
            for (i = 0; i <= this.order; i++) {
                function[i] = xk;
                xk *= inv;
            }
        }
        double coefficient = (double) n;
        for (i = 1; i <= this.order; i++) {
            function[i] = function[i] * coefficient;
            coefficient *= (double) (n - i);
        }
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void pow(double[] x, int xOffset, double[] y, int yOffset, double[] result, int resultOffset) {
        double[] logX = new double[getSize()];
        log(x, xOffset, logX, 0);
        double[] yLogX = new double[getSize()];
        multiply(logX, 0, y, yOffset, yLogX, 0);
        exp(yLogX, 0, result, resultOffset);
    }

    public void rootN(double[] operand, int operandOffset, int n, double[] result, int resultOffset) {
        double xk;
        double[] function = new double[(this.order + 1)];
        if (n == 2) {
            function[0] = FastMath.sqrt(operand[operandOffset]);
            xk = 0.5d / function[0];
        } else if (n == 3) {
            function[0] = FastMath.cbrt(operand[operandOffset]);
            xk = 1.0d / ((3.0d * function[0]) * function[0]);
        } else {
            function[0] = FastMath.pow(operand[operandOffset], 1.0d / ((double) n));
            xk = 1.0d / (((double) n) * FastMath.pow(function[0], n - 1));
        }
        double nReciprocal = 1.0d / ((double) n);
        double xReciprocal = 1.0d / operand[operandOffset];
        for (int i = 1; i <= this.order; i++) {
            function[i] = xk;
            xk *= (nReciprocal - ((double) i)) * xReciprocal;
        }
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void exp(double[] operand, int operandOffset, double[] result, int resultOffset) {
        double[] function = new double[(this.order + 1)];
        Arrays.fill(function, FastMath.exp(operand[operandOffset]));
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void expm1(double[] operand, int operandOffset, double[] result, int resultOffset) {
        double[] function = new double[(this.order + 1)];
        function[0] = FastMath.expm1(operand[operandOffset]);
        Arrays.fill(function, 1, this.order + 1, FastMath.exp(operand[operandOffset]));
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void log(double[] operand, int operandOffset, double[] result, int resultOffset) {
        double[] function = new double[(this.order + 1)];
        function[0] = FastMath.log(operand[operandOffset]);
        if (this.order > 0) {
            double inv = 1.0d / operand[operandOffset];
            double xk = inv;
            for (int i = 1; i <= this.order; i++) {
                function[i] = xk;
                xk *= ((double) (-i)) * inv;
            }
        }
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void log1p(double[] operand, int operandOffset, double[] result, int resultOffset) {
        double[] function = new double[(this.order + 1)];
        function[0] = FastMath.log1p(operand[operandOffset]);
        if (this.order > 0) {
            double inv = 1.0d / (1.0d + operand[operandOffset]);
            double xk = inv;
            for (int i = 1; i <= this.order; i++) {
                function[i] = xk;
                xk *= ((double) (-i)) * inv;
            }
        }
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void log10(double[] operand, int operandOffset, double[] result, int resultOffset) {
        double[] function = new double[(this.order + 1)];
        function[0] = FastMath.log10(operand[operandOffset]);
        if (this.order > 0) {
            double inv = 1.0d / operand[operandOffset];
            double xk = inv / FastMath.log(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS);
            for (int i = 1; i <= this.order; i++) {
                function[i] = xk;
                xk *= ((double) (-i)) * inv;
            }
        }
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void cos(double[] operand, int operandOffset, double[] result, int resultOffset) {
        double[] function = new double[(this.order + 1)];
        function[0] = FastMath.cos(operand[operandOffset]);
        if (this.order > 0) {
            function[1] = -FastMath.sin(operand[operandOffset]);
            for (int i = 2; i <= this.order; i++) {
                function[i] = -function[i - 2];
            }
        }
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void sin(double[] operand, int operandOffset, double[] result, int resultOffset) {
        double[] function = new double[(this.order + 1)];
        function[0] = FastMath.sin(operand[operandOffset]);
        if (this.order > 0) {
            function[1] = FastMath.cos(operand[operandOffset]);
            for (int i = 2; i <= this.order; i++) {
                function[i] = -function[i - 2];
            }
        }
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void tan(double[] operand, int operandOffset, double[] result, int resultOffset) {
        double[] function = new double[(this.order + 1)];
        double t = FastMath.tan(operand[operandOffset]);
        function[0] = t;
        if (this.order > 0) {
            double[] p = new double[(this.order + 2)];
            p[1] = 1.0d;
            double t2 = t * t;
            for (int n = 1; n <= this.order; n++) {
                double v = 0.0d;
                p[n + 1] = ((double) n) * p[n];
                for (int k = n + 1; k >= 0; k -= 2) {
                    v = (v * t2) + p[k];
                    if (k > 2) {
                        p[k - 2] = (((double) (k - 1)) * p[k - 1]) + (((double) (k - 3)) * p[k - 3]);
                    } else if (k == 2) {
                        p[0] = p[1];
                    }
                }
                if ((n & 1) == 0) {
                    v *= t;
                }
                function[n] = v;
            }
        }
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void acos(double[] operand, int operandOffset, double[] result, int resultOffset) {
        double[] function = new double[(this.order + 1)];
        double x = operand[operandOffset];
        function[0] = FastMath.acos(x);
        if (this.order > 0) {
            double[] p = new double[this.order];
            p[0] = -1.0d;
            double x2 = x * x;
            double f = 1.0d / (1.0d - x2);
            double coeff = FastMath.sqrt(f);
            function[1] = p[0] * coeff;
            for (int n = 2; n <= this.order; n++) {
                double v = 0.0d;
                p[n - 1] = ((double) (n - 1)) * p[n - 2];
                for (int k = n - 1; k >= 0; k -= 2) {
                    v = (v * x2) + p[k];
                    if (k > 2) {
                        p[k - 2] = (((double) (k - 1)) * p[k - 1]) + (((double) ((n * 2) - k)) * p[k - 3]);
                    } else if (k == 2) {
                        p[0] = p[1];
                    }
                }
                if ((n & 1) == 0) {
                    v *= x;
                }
                coeff *= f;
                function[n] = coeff * v;
            }
        }
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void asin(double[] operand, int operandOffset, double[] result, int resultOffset) {
        double[] function = new double[(this.order + 1)];
        double x = operand[operandOffset];
        function[0] = FastMath.asin(x);
        if (this.order > 0) {
            double[] p = new double[this.order];
            p[0] = 1.0d;
            double x2 = x * x;
            double f = 1.0d / (1.0d - x2);
            double coeff = FastMath.sqrt(f);
            function[1] = p[0] * coeff;
            for (int n = 2; n <= this.order; n++) {
                double v = 0.0d;
                p[n - 1] = ((double) (n - 1)) * p[n - 2];
                for (int k = n - 1; k >= 0; k -= 2) {
                    v = (v * x2) + p[k];
                    if (k > 2) {
                        p[k - 2] = (((double) (k - 1)) * p[k - 1]) + (((double) ((n * 2) - k)) * p[k - 3]);
                    } else if (k == 2) {
                        p[0] = p[1];
                    }
                }
                if ((n & 1) == 0) {
                    v *= x;
                }
                coeff *= f;
                function[n] = coeff * v;
            }
        }
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void atan(double[] operand, int operandOffset, double[] result, int resultOffset) {
        double[] function = new double[(this.order + 1)];
        double x = operand[operandOffset];
        function[0] = FastMath.atan(x);
        if (this.order > 0) {
            double[] q = new double[this.order];
            q[0] = 1.0d;
            double x2 = x * x;
            double f = 1.0d / (1.0d + x2);
            double coeff = f;
            function[1] = q[0] * coeff;
            for (int n = 2; n <= this.order; n++) {
                double v = 0.0d;
                q[n - 1] = ((double) (-n)) * q[n - 2];
                for (int k = n - 1; k >= 0; k -= 2) {
                    v = (v * x2) + q[k];
                    if (k > 2) {
                        q[k - 2] = (((double) (k - 1)) * q[k - 1]) + (((double) ((k - 1) - (n * 2))) * q[k - 3]);
                    } else if (k == 2) {
                        q[0] = q[1];
                    }
                }
                if ((n & 1) == 0) {
                    v *= x;
                }
                coeff *= f;
                function[n] = coeff * v;
            }
        }
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void atan2(double[] y, int yOffset, double[] x, int xOffset, double[] result, int resultOffset) {
        double[] tmp1 = new double[getSize()];
        multiply(x, xOffset, x, xOffset, tmp1, 0);
        double[] tmp2 = new double[getSize()];
        multiply(y, yOffset, y, yOffset, tmp2, 0);
        add(tmp1, 0, tmp2, 0, tmp2, 0);
        rootN(tmp2, 0, 2, tmp1, 0);
        int i;
        if (x[xOffset] >= 0.0d) {
            add(tmp1, 0, x, xOffset, tmp2, 0);
            divide(y, yOffset, tmp2, 0, tmp1, 0);
            atan(tmp1, 0, tmp2, 0);
            for (i = 0; i < tmp2.length; i++) {
                result[resultOffset + i] = 2.0d * tmp2[i];
            }
        } else {
            subtract(tmp1, 0, x, xOffset, tmp2, 0);
            divide(y, yOffset, tmp2, 0, tmp1, 0);
            atan(tmp1, 0, tmp2, 0);
            result[resultOffset] = (tmp2[0] <= 0.0d ? -3.141592653589793d : FastMath.PI) - (2.0d * tmp2[0]);
            for (i = 1; i < tmp2.length; i++) {
                result[resultOffset + i] = -2.0d * tmp2[i];
            }
        }
        result[resultOffset] = FastMath.atan2(y[yOffset], x[xOffset]);
    }

    public void cosh(double[] operand, int operandOffset, double[] result, int resultOffset) {
        double[] function = new double[(this.order + 1)];
        function[0] = FastMath.cosh(operand[operandOffset]);
        if (this.order > 0) {
            function[1] = FastMath.sinh(operand[operandOffset]);
            for (int i = 2; i <= this.order; i++) {
                function[i] = function[i - 2];
            }
        }
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void sinh(double[] operand, int operandOffset, double[] result, int resultOffset) {
        double[] function = new double[(this.order + 1)];
        function[0] = FastMath.sinh(operand[operandOffset]);
        if (this.order > 0) {
            function[1] = FastMath.cosh(operand[operandOffset]);
            for (int i = 2; i <= this.order; i++) {
                function[i] = function[i - 2];
            }
        }
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void tanh(double[] operand, int operandOffset, double[] result, int resultOffset) {
        double[] function = new double[(this.order + 1)];
        double t = FastMath.tanh(operand[operandOffset]);
        function[0] = t;
        if (this.order > 0) {
            double[] p = new double[(this.order + 2)];
            p[1] = 1.0d;
            double t2 = t * t;
            for (int n = 1; n <= this.order; n++) {
                double v = 0.0d;
                p[n + 1] = ((double) (-n)) * p[n];
                for (int k = n + 1; k >= 0; k -= 2) {
                    v = (v * t2) + p[k];
                    if (k > 2) {
                        p[k - 2] = (((double) (k - 1)) * p[k - 1]) - (((double) (k - 3)) * p[k - 3]);
                    } else if (k == 2) {
                        p[0] = p[1];
                    }
                }
                if ((n & 1) == 0) {
                    v *= t;
                }
                function[n] = v;
            }
        }
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void acosh(double[] operand, int operandOffset, double[] result, int resultOffset) {
        double[] function = new double[(this.order + 1)];
        double x = operand[operandOffset];
        function[0] = FastMath.acosh(x);
        if (this.order > 0) {
            double[] p = new double[this.order];
            p[0] = 1.0d;
            double x2 = x * x;
            double f = 1.0d / (x2 - 1.0d);
            double coeff = FastMath.sqrt(f);
            function[1] = p[0] * coeff;
            for (int n = 2; n <= this.order; n++) {
                double v = 0.0d;
                p[n - 1] = ((double) (1 - n)) * p[n - 2];
                for (int k = n - 1; k >= 0; k -= 2) {
                    v = (v * x2) + p[k];
                    if (k > 2) {
                        p[k - 2] = (((double) (1 - k)) * p[k - 1]) + (((double) (k - (n * 2))) * p[k - 3]);
                    } else if (k == 2) {
                        p[0] = -p[1];
                    }
                }
                if ((n & 1) == 0) {
                    v *= x;
                }
                coeff *= f;
                function[n] = coeff * v;
            }
        }
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void asinh(double[] operand, int operandOffset, double[] result, int resultOffset) {
        double[] function = new double[(this.order + 1)];
        double x = operand[operandOffset];
        function[0] = FastMath.asinh(x);
        if (this.order > 0) {
            double[] p = new double[this.order];
            p[0] = 1.0d;
            double x2 = x * x;
            double f = 1.0d / (1.0d + x2);
            double coeff = FastMath.sqrt(f);
            function[1] = p[0] * coeff;
            for (int n = 2; n <= this.order; n++) {
                double v = 0.0d;
                p[n - 1] = ((double) (1 - n)) * p[n - 2];
                for (int k = n - 1; k >= 0; k -= 2) {
                    v = (v * x2) + p[k];
                    if (k > 2) {
                        p[k - 2] = (((double) (k - 1)) * p[k - 1]) + (((double) (k - (n * 2))) * p[k - 3]);
                    } else if (k == 2) {
                        p[0] = p[1];
                    }
                }
                if ((n & 1) == 0) {
                    v *= x;
                }
                coeff *= f;
                function[n] = coeff * v;
            }
        }
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void atanh(double[] operand, int operandOffset, double[] result, int resultOffset) {
        double[] function = new double[(this.order + 1)];
        double x = operand[operandOffset];
        function[0] = FastMath.atanh(x);
        if (this.order > 0) {
            double[] q = new double[this.order];
            q[0] = 1.0d;
            double x2 = x * x;
            double f = 1.0d / (1.0d - x2);
            double coeff = f;
            function[1] = q[0] * coeff;
            for (int n = 2; n <= this.order; n++) {
                double v = 0.0d;
                q[n - 1] = ((double) n) * q[n - 2];
                for (int k = n - 1; k >= 0; k -= 2) {
                    v = (v * x2) + q[k];
                    if (k > 2) {
                        q[k - 2] = (((double) (k - 1)) * q[k - 1]) + (((double) (((n * 2) - k) + 1)) * q[k - 3]);
                    } else if (k == 2) {
                        q[0] = q[1];
                    }
                }
                if ((n & 1) == 0) {
                    v *= x;
                }
                coeff *= f;
                function[n] = coeff * v;
            }
        }
        compose(operand, operandOffset, function, result, resultOffset);
    }

    public void compose(double[] operand, int operandOffset, double[] f, double[] result, int resultOffset) {
        for (int i = 0; i < this.compIndirection.length; i++) {
            int[][] mappingI = this.compIndirection[i];
            double r = 0.0d;
            for (int[] mappingIJ : mappingI) {
                double product = ((double) mappingIJ[0]) * f[mappingIJ[1]];
                for (int k = 2; k < mappingIJ.length; k++) {
                    product *= operand[mappingIJ[k] + operandOffset];
                }
                r += product;
            }
            result[resultOffset + i] = r;
        }
    }

    public double taylor(double[] ds, int dsOffset, double... delta) throws MathArithmeticException {
        double value = 0.0d;
        for (int i = getSize() - 1; i >= 0; i--) {
            int[] orders = getPartialDerivativeOrders(i);
            double term = ds[dsOffset + i];
            for (int k = 0; k < orders.length; k++) {
                if (orders[k] > 0) {
                    try {
                        term *= FastMath.pow(delta[k], orders[k]) / ((double) CombinatoricsUtils.factorial(orders[k]));
                    } catch (NotPositiveException e) {
                        throw new MathInternalError(e);
                    }
                }
            }
            value += term;
        }
        return value;
    }

    public void checkCompatibility(DSCompiler compiler) throws DimensionMismatchException {
        if (this.parameters != compiler.parameters) {
            throw new DimensionMismatchException(this.parameters, compiler.parameters);
        } else if (this.order != compiler.order) {
            throw new DimensionMismatchException(this.order, compiler.order);
        }
    }
}
