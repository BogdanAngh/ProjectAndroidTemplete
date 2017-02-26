package cc.redberry.core.math.frobenius;

import cc.redberry.concurrent.OutputPortUnsafe;

public final class FrobeniusSolver implements OutputPortUnsafe<int[]> {
    private final OutputPortUnsafe<int[]> provider;

    public FrobeniusSolver(int[]... equations) {
        if (equations.length == 0) {
            throw new IllegalArgumentException();
        }
        int length = equations[0].length;
        if (length < 2) {
            throw new IllegalArgumentException();
        }
        int i = 1;
        while (i < equations.length) {
            if (equations[i].length == length || assertEq(equations[i])) {
                i++;
            } else {
                throw new IllegalArgumentException();
            }
        }
        int[] initialSolution = new int[(length - 1)];
        int zeroCoefficientsCount = 0;
        for (i = 0; i < length - 1; i++) {
            int j;
            for (int[] iArr : equations) {
                if (iArr[i] != 0) {
                    break;
                }
            }
            initialSolution[i] = -1;
            zeroCoefficientsCount++;
        }
        int[] initialRemainders = new int[equations.length];
        for (j = 0; j < equations.length; j++) {
            initialRemainders[j] = equations[j][length - 1];
        }
        SolutionProvider dummy = new DummySolutionProvider(initialSolution, initialRemainders);
        int providersCount = (length - 1) - zeroCoefficientsCount;
        SolutionProvider[] providers = new SolutionProvider[providersCount];
        int count = 0;
        for (i = 0; i < length - 1; i++) {
            if (initialSolution[i] != -1) {
                int[] coefficients = new int[equations.length];
                for (j = 0; j < equations.length; j++) {
                    coefficients[j] = equations[j][i];
                }
                if (count == 0) {
                    if (providersCount == 1) {
                        providers[count] = new FinalSolutionProvider(dummy, i, coefficients);
                    } else {
                        providers[count] = new SingleSolutionProvider(dummy, i, coefficients);
                    }
                } else if (count == providersCount - 1) {
                    providers[count] = new FinalSolutionProvider(providers[count - 1], i, coefficients);
                } else {
                    providers[count] = new SingleSolutionProvider(providers[count - 1], i, coefficients);
                }
                count++;
            }
        }
        this.provider = new TotalSolutionProvider(providers);
    }

    public int[] take() {
        return (int[]) this.provider.take();
    }

    private boolean assertEq(int[] equation) {
        for (int i : equation) {
            if (i < 0) {
                return false;
            }
        }
        return true;
    }
}
