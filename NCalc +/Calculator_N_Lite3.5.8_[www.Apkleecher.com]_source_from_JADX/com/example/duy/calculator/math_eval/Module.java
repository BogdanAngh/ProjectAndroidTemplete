package com.example.duy.calculator.math_eval;

public class Module {
    private static final int mBinSeparatorDistance = 4;
    private static final int mDecSeparatorDistance = 3;
    private static final int mHexSeparatorDistance = 2;
    private static final int mOctSeparatorDistance = 3;
    private final Solver mSolver;

    Module(Solver solver) {
        this.mSolver = solver;
    }

    public int getDecSeparatorDistance() {
        return mOctSeparatorDistance;
    }

    public int getBinSeparatorDistance() {
        return mBinSeparatorDistance;
    }

    public int getHexSeparatorDistance() {
        return mHexSeparatorDistance;
    }

    public int getOctSeparatorDistance() {
        return mOctSeparatorDistance;
    }

    public char getDecimalPoint() {
        return Constants.DECIMAL_POINT;
    }

    public char getDecSeparator() {
        return Constants.DECIMAL_SEPARATOR;
    }

    public char getBinSeparator() {
        return Constants.BINARY_SEPARATOR;
    }

    public char getHexSeparator() {
        return Constants.HEXADECIMAL_SEPARATOR;
    }

    public char getOctSeparator() {
        return Constants.OCTAL_SEPARATOR;
    }

    public Solver getSolver() {
        return this.mSolver;
    }
}
