package cc.redberry.core.math.frobenius;

final class SingleSolutionProvider extends SolutionProviderAbstract {
    SingleSolutionProvider(SolutionProvider provider, int position, int[] coefficient) {
        super(provider, position, coefficient);
    }

    public int[] take() {
        if (this.currentSolution == null) {
            return null;
        }
        for (int i = 0; i < this.coefficients.length; i++) {
            if (this.currentRemainder[i] - (this.coefficients[i] * this.currentCounter) < 0) {
                this.currentCounter = 0;
                this.currentSolution = null;
                return null;
            }
        }
        int[] solution = (int[]) this.currentSolution.clone();
        int i2 = this.position;
        solution[i2] = solution[i2] + this.currentCounter;
        this.currentCounter++;
        return solution;
    }
}
