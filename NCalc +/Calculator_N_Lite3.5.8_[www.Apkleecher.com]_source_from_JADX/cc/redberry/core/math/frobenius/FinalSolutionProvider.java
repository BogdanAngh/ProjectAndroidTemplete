package cc.redberry.core.math.frobenius;

final class FinalSolutionProvider extends SolutionProviderAbstract {
    static final /* synthetic */ boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !FinalSolutionProvider.class.desiredAssertionStatus();
    }

    public FinalSolutionProvider(SolutionProvider provider, int position, int[] coefficient) {
        super(provider, position, coefficient);
    }

    public int[] take() {
        if (this.currentSolution == null) {
            return null;
        }
        int i = 0;
        while (i < this.coefficients.length) {
            int i2 = i + 1;
            if (this.coefficients[i] != 0) {
                i = i2;
                break;
            }
            i = i2;
        }
        i--;
        if (!$assertionsDisabled && i != 0 && i == this.coefficients.length) {
            throw new AssertionError();
        } else if (this.currentRemainder[i] % this.coefficients[i] != 0) {
            this.currentSolution = null;
            return null;
        } else {
            this.currentCounter = this.currentRemainder[i] / this.coefficients[i];
            for (i = 0; i < this.coefficients.length; i++) {
                if (this.coefficients[i] == 0) {
                    if (this.currentRemainder[i] != 0) {
                        this.currentSolution = null;
                        return null;
                    }
                } else if (this.currentRemainder[i] % this.coefficients[i] != 0) {
                    this.currentSolution = null;
                    return null;
                } else if (this.currentRemainder[i] / this.coefficients[i] != this.currentCounter) {
                    this.currentSolution = null;
                    return null;
                }
            }
            int[] solution = (int[]) this.currentSolution.clone();
            int i3 = this.position;
            solution[i3] = solution[i3] + this.currentCounter;
            this.currentSolution = null;
            return solution;
        }
    }
}
