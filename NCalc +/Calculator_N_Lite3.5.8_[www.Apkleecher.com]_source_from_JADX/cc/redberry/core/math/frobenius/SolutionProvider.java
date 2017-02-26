package cc.redberry.core.math.frobenius;

import cc.redberry.concurrent.OutputPortUnsafe;

interface SolutionProvider extends OutputPortUnsafe<int[]> {
    int[] currentRemainders();

    boolean tick();
}
