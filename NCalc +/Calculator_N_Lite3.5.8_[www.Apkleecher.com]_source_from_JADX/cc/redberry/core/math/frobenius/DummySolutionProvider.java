package cc.redberry.core.math.frobenius;

final class DummySolutionProvider implements SolutionProvider {
    private int[] currentRemainder;
    private int[] solution;

    DummySolutionProvider(int[] solution, int[] currentRemainder) {
        this.solution = solution;
        this.currentRemainder = currentRemainder;
    }

    public boolean tick() {
        return this.solution != null;
    }

    public int[] take() {
        if (this.solution == null) {
            return null;
        }
        int[] ret = this.solution;
        this.solution = null;
        return ret;
    }

    public int[] currentRemainders() {
        return this.currentRemainder;
    }
}
