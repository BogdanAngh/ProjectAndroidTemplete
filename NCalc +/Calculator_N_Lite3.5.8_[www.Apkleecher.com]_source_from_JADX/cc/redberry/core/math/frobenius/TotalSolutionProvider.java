package cc.redberry.core.math.frobenius;

import cc.redberry.concurrent.OutputPortUnsafe;

final class TotalSolutionProvider implements OutputPortUnsafe<int[]> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private boolean inited;
    private final SolutionProvider[] providers;

    static {
        $assertionsDisabled = !TotalSolutionProvider.class.desiredAssertionStatus();
    }

    public TotalSolutionProvider(SolutionProvider[] providers) {
        this.inited = false;
        this.providers = providers;
    }

    public int[] take() {
        if (!this.inited) {
            for (SolutionProvider provider : this.providers) {
                provider.tick();
            }
            this.inited = true;
        }
        int i = this.providers.length - 1;
        int[] solution = (int[]) this.providers[i].take();
        if (solution != null) {
            return solution;
        }
        while (true) {
            int i2 = i - 1;
            boolean r = !this.providers[i].tick();
            if (r && i2 >= 0) {
                i = i2;
            } else if (i2 != -1 || !r) {
                i = i2 + 2;
                while (i < this.providers.length) {
                    if (!this.providers[i].tick()) {
                        i--;
                        break;
                    }
                    i++;
                }
                if (!$assertionsDisabled && i != this.providers.length) {
                    break;
                }
                i--;
                solution = (int[]) this.providers[i].take();
                if (solution != null) {
                    return solution;
                }
            } else {
                i = i2;
                return null;
            }
        }
        throw new AssertionError();
    }
}
