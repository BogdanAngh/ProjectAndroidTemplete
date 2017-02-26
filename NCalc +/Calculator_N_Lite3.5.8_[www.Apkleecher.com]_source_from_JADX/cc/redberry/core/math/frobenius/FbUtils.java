package cc.redberry.core.math.frobenius;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public final class FbUtils {
    public static final Comparator<int[]> SOLUTION_COMPARATOR;

    static class 1 implements Comparator<int[]> {
        1() {
        }

        public int compare(int[] o1, int[] o2) {
            if (o1.length != o2.length) {
                throw new IllegalArgumentException();
            }
            for (int i = 0; i < o1.length; i++) {
                if (o1[i] > o2[i]) {
                    return 1;
                }
                if (o1[i] < o2[i]) {
                    return -1;
                }
            }
            return 0;
        }
    }

    private static class SolutionsIterator implements Iterator<int[]>, Iterable<int[]> {
        private final FrobeniusSolver fbSolver;
        private int[] solution;

        SolutionsIterator(int[][] equations) {
            this.fbSolver = new FrobeniusSolver(equations);
        }

        public boolean hasNext() {
            int[] take = this.fbSolver.take();
            this.solution = take;
            return take != null;
        }

        public int[] next() {
            return this.solution;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported.");
        }

        public Iterator<int[]> iterator() {
            return this;
        }
    }

    static {
        SOLUTION_COMPARATOR = new 1();
    }

    public static List<int[]> getAllSolutions(int[]... equations) {
        List<int[]> solutions = new ArrayList();
        FrobeniusSolver fbSolver = new FrobeniusSolver(equations);
        while (true) {
            int[] solution = fbSolver.take();
            if (solution == null) {
                return solutions;
            }
            solutions.add(solution);
        }
    }

    public static Iterator<int[]> iterator(int[][] equations) {
        return new SolutionsIterator(equations);
    }

    public static Iterable<int[]> iterable(int[][] equations) {
        return new SolutionsIterator(equations);
    }
}
