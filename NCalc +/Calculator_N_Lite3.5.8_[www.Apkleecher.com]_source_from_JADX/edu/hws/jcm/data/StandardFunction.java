package edu.hws.jcm.data;

public class StandardFunction implements MathObject {
    private int code;
    private String name;

    public StandardFunction(int opCode) {
        this(standardFunctionName(opCode), opCode);
    }

    public StandardFunction(String name, int opCode) {
        setName(name);
        this.code = opCode;
    }

    public int getOpCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String standardFunctionName(int opCode) {
        switch (opCode) {
            case ExpressionProgram.CUBERT /*-36*/:
                return "cubert";
            case ExpressionProgram.CEILING /*-35*/:
                return "ceiling";
            case ExpressionProgram.FLOOR /*-34*/:
                return "floor";
            case ExpressionProgram.ROUND /*-33*/:
                return "round";
            case ExpressionProgram.TRUNC /*-32*/:
                return "trunc";
            case ExpressionProgram.LOG10 /*-31*/:
                return "log10";
            case ExpressionProgram.LOG2 /*-30*/:
                return "log2";
            case ExpressionProgram.LN /*-29*/:
                return "ln";
            case ExpressionProgram.EXP /*-28*/:
                return "exp";
            case ExpressionProgram.SQRT /*-27*/:
                return "sqrt";
            case ExpressionProgram.ABS /*-26*/:
                return "abs";
            case ExpressionProgram.ARCTAN /*-25*/:
                return "arctan";
            case ExpressionProgram.ARCCOS /*-24*/:
                return "arccos";
            case ExpressionProgram.ARCSIN /*-23*/:
                return "arcsin";
            case ExpressionProgram.CSC /*-22*/:
                return "csc";
            case ExpressionProgram.SEC /*-21*/:
                return "sec";
            case ExpressionProgram.COT /*-20*/:
                return "cot";
            case ExpressionProgram.TAN /*-19*/:
                return "tan";
            case ExpressionProgram.COS /*-18*/:
                return "cos";
            case ExpressionProgram.SIN /*-17*/:
                return "sin";
            default:
                throw new IllegalArgumentException("Internal Error: Unknown standard function code.");
        }
    }
}
