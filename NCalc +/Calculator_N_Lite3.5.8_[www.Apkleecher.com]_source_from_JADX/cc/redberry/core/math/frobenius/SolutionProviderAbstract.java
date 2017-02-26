package cc.redberry.core.math.frobenius;

abstract class SolutionProviderAbstract implements SolutionProvider {
    final int[] coefficients;
    int currentCounter;
    int[] currentRemainder;
    int[] currentSolution;
    final int position;
    private final SolutionProvider provider;

    SolutionProviderAbstract(SolutionProvider provider, int position, int[] coefficients) {
        this.currentCounter = 0;
        this.provider = provider;
        this.position = position;
        this.coefficients = coefficients;
    }

    public boolean tick() {
        this.currentSolution = (int[]) this.provider.take();
        this.currentRemainder = this.provider.currentRemainders();
        this.currentCounter = 0;
        return this.currentSolution != null;
    }

    public int[] currentRemainders() {
        int[] remainders = new int[this.coefficients.length];
        for (int i = 0; i < this.coefficients.length; i++) {
            remainders[i] = this.currentRemainder[i] - (this.coefficients[i] * (this.currentCounter - 1));
        }
        return remainders;
    }
}
