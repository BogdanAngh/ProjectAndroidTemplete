package edu.hws.jcm.data;

import com.example.duy.calculator.geom2d.util.Angle2D;
import com.example.duy.calculator.math_eval.Constants;
import org.apache.commons.math4.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;
import org.apache.commons.math4.util.FastMath;

public class ExpressionProgram implements Expression {
    public static final int ABS = -26;
    public static final int AND = -12;
    public static final int ARCCOS = -24;
    public static final int ARCSIN = -23;
    public static final int ARCTAN = -25;
    public static final int CEILING = -35;
    public static final int COS = -18;
    public static final int COT = -20;
    public static final int CSC = -22;
    public static final int CUBERT = -36;
    public static final int DIVIDE = -4;
    public static final int EQ = -6;
    public static final int EXP = -28;
    public static final int FACTORIAL = -16;
    public static final int FLOOR = -34;
    public static final int GE = -11;
    public static final int GT = -9;
    public static final int LE = -10;
    public static final int LN = -29;
    public static final int LOG10 = -31;
    public static final int LOG2 = -30;
    public static final int LT = -8;
    public static final int MINUS = -2;
    public static final int NE = -7;
    public static final int NOT = -14;
    public static final int OR = -13;
    public static final int PLUS = -1;
    public static final int POWER = -5;
    public static final int ROUND = -33;
    public static final int SEC = -21;
    public static final int SIN = -17;
    public static final int SQRT = -27;
    public static final int TAN = -19;
    public static final int TIMES = -3;
    public static final int TRUNC = -32;
    public static final int UNARY_MINUS = -15;
    private Cases cases;
    private ExpressionCommand[] command;
    private int commandCt;
    private double[] constant;
    private int constantCt;
    private int[] prog;
    private int progCt;
    public String sourceString;
    private StackOfDouble stack;

    public ExpressionProgram() {
        this.prog = new int[1];
        this.stack = new StackOfDouble();
        this.constant = new double[1];
        this.command = new ExpressionCommand[1];
    }

    public void addCommandObject(ExpressionCommand com) {
        addCommandCode(1073741823 + findCommand(com));
    }

    public void addConstant(double d) {
        addCommandCode(findConstant(d));
    }

    public void addCommand(int code) {
        if (code >= 0 || code < CUBERT) {
            throw new IllegalArgumentException("Internal Error.  Illegal command code.");
        }
        addCommandCode(code);
    }

    public void trim() {
        if (this.progCt != this.prog.length) {
            int[] temp = new int[this.progCt];
            System.arraycopy(this.prog, 0, temp, 0, this.progCt);
            this.prog = temp;
        }
        if (this.commandCt != this.command.length) {
            ExpressionCommand[] temp2 = new ExpressionCommand[this.commandCt];
            System.arraycopy(this.command, 0, temp2, 0, this.commandCt);
            this.command = temp2;
        }
        if (this.constantCt != this.constant.length) {
            double[] temp3 = new double[this.constantCt];
            System.arraycopy(this.constant, 0, temp3, 0, this.constantCt);
            this.constant = temp3;
        }
    }

    private void addCommandCode(int code) {
        if (this.progCt == this.prog.length) {
            int[] temp = new int[(this.prog.length * 2)];
            System.arraycopy(this.prog, 0, temp, 0, this.progCt);
            this.prog = temp;
        }
        int[] iArr = this.prog;
        int i = this.progCt;
        this.progCt = i + 1;
        iArr[i] = code;
    }

    public synchronized double getVal() {
        this.cases = null;
        return basicGetValue();
    }

    public synchronized double getValueWithCases(Cases c) {
        double d;
        this.cases = c;
        d = basicGetValue();
        this.cases = null;
        return d;
    }

    private double basicGetValue() {
        int i = 0;
        this.stack.makeEmpty();
        for (int pc = 0; pc < this.progCt; pc++) {
            int code = this.prog[pc];
            if (code < 0) {
                double ans = applyCommandCode(code);
                if (Double.isNaN(ans) || Double.isInfinite(ans)) {
                    if (this.cases != null) {
                        this.cases.addCase(0);
                    }
                    return Double.NaN;
                }
                this.stack.push(ans);
            } else if (code < 1073741823) {
                this.stack.push(this.constant[code]);
            } else {
                this.command[code - 1073741823].apply(this.stack, this.cases);
            }
        }
        if (this.stack.size() != 1) {
            throw new IllegalArgumentException("Internal Error:  Improper stack state after expression evaluation.");
        }
        double val = this.stack.pop();
        if (this.cases == null) {
            return val;
        }
        Cases cases = this.cases;
        if (!Double.isNaN(val)) {
            i = 1;
        }
        cases.addCase(i);
        return val;
    }

    private void addCase(int c) {
        if (this.cases != null) {
            this.cases.addCase(c);
        }
    }

    protected double applyCommandCode(int code) {
        if (code < OR) {
            return eval(code, this.stack.pop());
        }
        return eval(code, this.stack.pop(), this.stack.pop());
    }

    private double eval(int commandCode, double x) {
        double d = 1.0d;
        switch (commandCode) {
            case CUBERT /*-36*/:
                addCase(x > 0.0d ? 1 : PLUS);
                return x >= 0.0d ? Math.pow(x, 0.3333333333333333d) : -Math.pow(-x, 0.3333333333333333d);
            case CEILING /*-35*/:
                addCase((int) Math.floor(x));
                return Math.ceil(x);
            case FLOOR /*-34*/:
                addCase((int) Math.floor(x));
                return Math.floor(x);
            case ROUND /*-33*/:
                addCase((int) Math.floor(0.5d + x));
                return Math.floor(0.5d + x);
            case TRUNC /*-32*/:
                addCase((int) x);
                return (double) ((long) x);
            case LOG10 /*-31*/:
                if (x > 0.0d) {
                    return Math.log(x) / Math.log(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS);
                }
                return Double.NaN;
            case LOG2 /*-30*/:
                if (x > 0.0d) {
                    return Math.log(x) / Math.log(2.0d);
                }
                return Double.NaN;
            case LN /*-29*/:
                if (x > 0.0d) {
                    return Math.log(x);
                }
                return Double.NaN;
            case EXP /*-28*/:
                return Math.exp(x);
            case SQRT /*-27*/:
                return x < 0.0d ? Double.NaN : Math.sqrt(x);
            case ABS /*-26*/:
                int i = x > 0.0d ? 1 : x < 0.0d ? PLUS : 0;
                addCase(i);
                return Math.abs(x);
            case ARCTAN /*-25*/:
                return Math.atan(x);
            case ARCCOS /*-24*/:
                return Math.acos(x);
            case ARCSIN /*-23*/:
                return Math.asin(x);
            case CSC /*-22*/:
                addCase((int) Math.floor(x / FastMath.PI));
                return 1.0d / Math.sin(x);
            case SEC /*-21*/:
                addCase((int) Math.floor((x - Angle2D.M_PI_2) / FastMath.PI));
                return 1.0d / Math.cos(x);
            case COT /*-20*/:
                addCase((int) Math.floor(x / FastMath.PI));
                return Math.cos(x) / Math.sin(x);
            case TAN /*-19*/:
                addCase((int) Math.floor((x - Angle2D.M_PI_2) / FastMath.PI));
                return Math.tan(x);
            case COS /*-18*/:
                return Math.cos(x);
            case SIN /*-17*/:
                return Math.sin(x);
            case FACTORIAL /*-16*/:
                return factorial(x);
            case UNARY_MINUS /*-15*/:
                return -x;
            case NOT /*-14*/:
                if (x != 0.0d) {
                    d = 0.0d;
                }
                return d;
            default:
                return Double.NaN;
        }
    }

    private double factorial(double x) {
        if (x <= -0.5d || x > 170.5d) {
            addCase(PLUS);
            return Double.NaN;
        }
        int n = (int) x;
        addCase(n);
        double ans = 1.0d;
        for (int i = 1; i <= n; i++) {
            ans *= (double) i;
        }
        return ans;
    }

    private double eval(int commandCode, double y, double x) {
        double d = 0.0d;
        switch (commandCode) {
            case OR /*-13*/:
                if (!(x == 0.0d && y == 0.0d)) {
                    d = 1.0d;
                }
                return d;
            case AND /*-12*/:
                if (x == 0.0d || y == 0.0d) {
                    return 0.0d;
                }
                return 1.0d;
            case GE /*-11*/:
                if (x < y) {
                    return 0.0d;
                }
                return 1.0d;
            case LE /*-10*/:
                if (x > y) {
                    return 0.0d;
                }
                return 1.0d;
            case GT /*-9*/:
                if (x <= y) {
                    return 0.0d;
                }
                return 1.0d;
            case LT /*-8*/:
                if (x >= y) {
                    return 0.0d;
                }
                return 1.0d;
            case NE /*-7*/:
                if (x == y) {
                    return 0.0d;
                }
                return 1.0d;
            case EQ /*-6*/:
                if (x != y) {
                    return 0.0d;
                }
                return 1.0d;
            case POWER /*-5*/:
                return Math.pow(x, y);
            case DIVIDE /*-4*/:
                int i = y > 0.0d ? 1 : y < 0.0d ? PLUS : 0;
                addCase(i);
                return x / y;
            case TIMES /*-3*/:
                return x * y;
            case MINUS /*-2*/:
                return x - y;
            case PLUS /*-1*/:
                return x + y;
            default:
                return Double.NaN;
        }
    }

    public String toString() {
        if (this.sourceString != null) {
            return this.sourceString;
        }
        StringBuffer buffer = new StringBuffer();
        appendOutputString(this.progCt + PLUS, buffer);
        return buffer.toString();
    }

    public void appendOutputString(int index, StringBuffer buffer) {
        if (this.prog[index] >= 1073741823) {
            this.command[this.prog[index] - 1073741823].appendOutputString(this, index, buffer);
        } else if (this.prog[index] >= 0) {
            buffer.append(NumUtils.realToString(this.constant[this.prog[index]]));
        } else if (this.prog[index] >= OR) {
            int op2 = index + PLUS;
            int op1 = op2 - extent(op2);
            if (precedence(this.prog[op1]) < precedence(this.prog[index]) || (this.prog[index] == POWER && precedence(this.prog[op1]) == precedence(this.prog[index]))) {
                buffer.append(Constants.LEFT_PAREN);
                appendOutputString(op1, buffer);
                buffer.append(Constants.RIGHT_PAREN);
            } else {
                appendOutputString(op1, buffer);
            }
            switch (this.prog[index]) {
                case OR /*-13*/:
                    buffer.append(" OR ");
                    break;
                case AND /*-12*/:
                    buffer.append(" AND ");
                    break;
                case GE /*-11*/:
                    buffer.append(" >= ");
                    break;
                case LE /*-10*/:
                    buffer.append(" <= ");
                    break;
                case GT /*-9*/:
                    buffer.append(" > ");
                    break;
                case LT /*-8*/:
                    buffer.append(" < ");
                    break;
                case NE /*-7*/:
                    buffer.append(" <> ");
                    break;
                case EQ /*-6*/:
                    buffer.append(" = ");
                    break;
                case POWER /*-5*/:
                    buffer.append("^");
                    break;
                case DIVIDE /*-4*/:
                    buffer.append("/");
                    break;
                case TIMES /*-3*/:
                    buffer.append("*");
                    break;
                case MINUS /*-2*/:
                    buffer.append(" - ");
                    break;
                case PLUS /*-1*/:
                    buffer.append(" + ");
                    break;
            }
            if (this.prog[op2] == UNARY_MINUS || precedence(this.prog[op2]) < precedence(this.prog[index]) || ((this.prog[index] == MINUS || this.prog[index] == DIVIDE) && precedence(this.prog[op2]) == precedence(this.prog[index]))) {
                buffer.append(Constants.LEFT_PAREN);
                appendOutputString(op2, buffer);
                buffer.append(Constants.RIGHT_PAREN);
                return;
            }
            appendOutputString(op2, buffer);
        } else if (this.prog[index] <= SIN) {
            buffer.append(StandardFunction.standardFunctionName(this.prog[index]));
            buffer.append(Constants.LEFT_PAREN);
            appendOutputString(index + PLUS, buffer);
            buffer.append(Constants.RIGHT_PAREN);
        } else if (this.prog[index] == UNARY_MINUS) {
            buffer.append(Constants.MINUS_UNICODE);
            if (precedence(this.prog[index + PLUS]) <= precedence(UNARY_MINUS)) {
                buffer.append(Constants.LEFT_PAREN);
                appendOutputString(index + PLUS, buffer);
                buffer.append(Constants.RIGHT_PAREN);
                return;
            }
            appendOutputString(index + PLUS, buffer);
        } else if (this.prog[index] == NOT) {
            buffer.append("NOT (");
            appendOutputString(index + PLUS, buffer);
            buffer.append(Constants.RIGHT_PAREN);
        } else if (this.prog[index] == FACTORIAL) {
            if (this.prog[index + PLUS] < 0 || !(this.prog[index + PLUS] < 1073741823 || (this.command[this.prog[index + PLUS] - 1073741823] instanceof Variable) || (this.command[this.prog[index + PLUS] - 1073741823] instanceof Constant))) {
                buffer.append(Constants.LEFT_PAREN);
                appendOutputString(index + PLUS, buffer);
                buffer.append(Constants.RIGHT_PAREN);
            } else {
                appendOutputString(index + PLUS, buffer);
            }
            buffer.append(Constants.FACTORIAL_UNICODE);
        }
    }

    private int precedence(int opcode) {
        if (opcode < 0) {
            switch (opcode) {
                case FACTORIAL /*-16*/:
                case OR /*-13*/:
                case AND /*-12*/:
                    return 1;
                case UNARY_MINUS /*-15*/:
                case MINUS /*-2*/:
                case PLUS /*-1*/:
                    return 3;
                case GE /*-11*/:
                case LE /*-10*/:
                case GT /*-9*/:
                case LT /*-8*/:
                case NE /*-7*/:
                case EQ /*-6*/:
                    return 2;
                case POWER /*-5*/:
                    return 6;
                case DIVIDE /*-4*/:
                case TIMES /*-3*/:
                    return 4;
                default:
                    return 7;
            }
        } else if (opcode < 1073741823 || !(this.command[opcode - 1073741823] instanceof ConditionalExpression)) {
            return 7;
        } else {
            return 0;
        }
    }

    public Expression derivative(Variable wrt) {
        ExpressionProgram deriv = new ExpressionProgram();
        compileDerivative(this.progCt + PLUS, deriv, wrt);
        deriv.trim();
        return deriv;
    }

    public void compileDerivative(int index, ExpressionProgram deriv, Variable wrt) {
        if (!dependsOn(index, wrt)) {
            deriv.addConstant(0.0d);
        } else if (this.prog[index] >= 1073741823) {
            this.command[this.prog[index] - 1073741823].compileDerivative(this, index, deriv, wrt);
        } else if (this.prog[index] >= 0) {
            deriv.addConstant(0.0d);
        } else if (this.prog[index] >= POWER) {
            int indexOp2 = index + PLUS;
            int indexOp1 = indexOp2 - extent(indexOp2);
            doBinDeriv(this.prog[index], indexOp1, indexOp2, deriv, wrt);
        } else if (this.prog[index] <= SIN) {
            doFuncDeriv(this.prog[index], index + PLUS, deriv, wrt);
        } else if (this.prog[index] == UNARY_MINUS) {
            compileDerivative(index + PLUS, deriv, wrt);
            deriv.addCommand(UNARY_MINUS);
        } else if (this.prog[index] == FACTORIAL) {
            deriv.addConstant(Double.NaN);
        } else if (this.prog[index] >= NOT) {
            throw new IllegalArgumentException("Internal Error: Attempt to take the derivative of a logical-valued expression.");
        } else {
            throw new IllegalArgumentException("Internal Error: Unknown opcode.");
        }
    }

    public int extent(int index) {
        if (this.prog[index] <= NOT) {
            return extent(index + PLUS) + 1;
        }
        if (this.prog[index] < 0) {
            int extentOp1 = extent(index + PLUS);
            return (extentOp1 + extent((index + PLUS) - extentOp1)) + 1;
        } else if (this.prog[index] < 1073741823) {
            return 1;
        } else {
            return this.command[this.prog[index] - 1073741823].extent(this, index);
        }
    }

    public void copyExpression(int index, ExpressionProgram destination) {
        for (int i = (index - extent(index)) + 1; i <= index; i++) {
            if (this.prog[i] < 0) {
                destination.addCommand(this.prog[i]);
            } else if (this.prog[i] >= 1073741823) {
                destination.addCommandObject(this.command[this.prog[i] - 1073741823]);
            } else {
                destination.addConstant(this.constant[this.prog[i]]);
            }
        }
    }

    public boolean dependsOn(int index, Variable x) {
        for (int i = (index - extent(index)) + 1; i <= index; i++) {
            if (this.prog[i] >= 1073741823) {
                Variable c = this.command[this.prog[i] - 1073741823];
                if (c == x || c.dependsOn(x)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean dependsOn(Variable x) {
        return dependsOn(this.progCt + PLUS, x);
    }

    private void doBinDeriv(int opCode, int op1, int op2, ExpressionProgram deriv, Variable wrt) {
        switch (opCode) {
            case POWER /*-5*/:
                if (!dependsOn(op2, wrt)) {
                    copyExpression(op2, deriv);
                    copyExpression(op1, deriv);
                    if (this.prog[op2] < 0 || this.prog[op2] >= 1073741823) {
                        if (this.prog[op2] != UNARY_MINUS || this.prog[op2 + PLUS] < 0 || this.prog[op2 + PLUS] >= 1073741823) {
                            copyExpression(op2, deriv);
                            deriv.addConstant(1.0d);
                            deriv.addCommand(MINUS);
                            deriv.addCommand(POWER);
                        } else {
                            deriv.addConstant(this.constant[this.prog[op2 + PLUS]] + 1.0d);
                            deriv.addCommand(UNARY_MINUS);
                            deriv.addCommand(POWER);
                        }
                    } else if (this.constant[this.prog[op2]] != 2.0d) {
                        deriv.addConstant(this.constant[this.prog[op2]] - 1.0d);
                        deriv.addCommand(POWER);
                    }
                    deriv.addCommand(TIMES);
                    if (this.prog[op1] < 1073741823 || this.command[this.prog[op1] - 1073741823] != wrt) {
                        compileDerivative(op1, deriv, wrt);
                        deriv.addCommand(TIMES);
                    }
                } else if (dependsOn(op1, wrt)) {
                    copyExpression(op1, deriv);
                    copyExpression(op2, deriv);
                    deriv.addCommand(POWER);
                    boolean eq = true;
                    int ext1 = extent(op1);
                    if (ext1 != extent(op2)) {
                        eq = false;
                    } else {
                        int i = 0;
                        while (i < ext1) {
                            if (this.prog[op1 - i] != this.prog[op2 - i]) {
                                eq = false;
                            } else {
                                i++;
                            }
                        }
                    }
                    if (eq) {
                        deriv.addConstant(1.0d);
                    } else {
                        copyExpression(op2, deriv);
                        copyExpression(op1, deriv);
                        deriv.addCommand(DIVIDE);
                    }
                    if (this.prog[op1] < 1073741823 || this.command[this.prog[op1] - 1073741823] != wrt) {
                        compileDerivative(op1, deriv, wrt);
                        deriv.addCommand(TIMES);
                    }
                    copyExpression(op1, deriv);
                    deriv.addCommand(LN);
                    if (this.prog[op2] < 1073741823 || this.command[this.prog[op2] - 1073741823] != wrt) {
                        compileDerivative(op2, deriv, wrt);
                        deriv.addCommand(TIMES);
                    }
                    deriv.addCommand(PLUS);
                    deriv.addCommand(TIMES);
                } else {
                    copyExpression(op1, deriv);
                    copyExpression(op2, deriv);
                    deriv.addCommand(POWER);
                    if (!(this.prog[op1] >= 1073741823 && (this.command[this.prog[op1] - 1073741823] instanceof Constant) && ((Constant) this.command[this.prog[op1] - 1073741823]).getVal() == FastMath.E)) {
                        copyExpression(op1, deriv);
                        deriv.addCommand(LN);
                        deriv.addCommand(TIMES);
                    }
                    if (this.prog[op2] < 1073741823 || this.command[this.prog[op2] - 1073741823] != wrt) {
                        compileDerivative(op2, deriv, wrt);
                        deriv.addCommand(TIMES);
                    }
                }
            case DIVIDE /*-4*/:
                if (!dependsOn(op2, wrt)) {
                    compileDerivative(op1, deriv, wrt);
                    copyExpression(op2, deriv);
                    deriv.addCommand(DIVIDE);
                } else if (dependsOn(op1, wrt)) {
                    copyExpression(op2, deriv);
                    if (this.prog[op1] < 1073741823 || this.command[this.prog[op1] - 1073741823] != wrt) {
                        compileDerivative(op1, deriv, wrt);
                        deriv.addCommand(TIMES);
                    }
                    copyExpression(op1, deriv);
                    if (this.prog[op2] < 1073741823 || this.command[this.prog[op2] - 1073741823] != wrt) {
                        compileDerivative(op2, deriv, wrt);
                        deriv.addCommand(TIMES);
                    }
                    deriv.addCommand(MINUS);
                    copyExpression(op2, deriv);
                    deriv.addConstant(2.0d);
                    deriv.addCommand(POWER);
                    deriv.addCommand(DIVIDE);
                } else {
                    copyExpression(op1, deriv);
                    deriv.addCommand(UNARY_MINUS);
                    copyExpression(op2, deriv);
                    deriv.addConstant(2.0d);
                    deriv.addCommand(POWER);
                    deriv.addCommand(DIVIDE);
                    if (this.prog[op2] < 1073741823 || this.command[this.prog[op2] - 1073741823] != wrt) {
                        compileDerivative(op2, deriv, wrt);
                        deriv.addCommand(TIMES);
                    }
                }
            case TIMES /*-3*/:
                int cases = 0;
                if (dependsOn(op2, wrt)) {
                    copyExpression(op1, deriv);
                    if (this.prog[op2] < 1073741823 || this.command[this.prog[op2] - 1073741823] != wrt) {
                        compileDerivative(op2, deriv, wrt);
                        deriv.addCommand(TIMES);
                    }
                    cases = 0 + 1;
                }
                if (dependsOn(op1, wrt)) {
                    copyExpression(op2, deriv);
                    if (this.prog[op1] < 1073741823 || this.command[this.prog[op1] - 1073741823] != wrt) {
                        compileDerivative(op1, deriv, wrt);
                        deriv.addCommand(TIMES);
                    }
                    cases++;
                }
                if (cases == 2) {
                    deriv.addCommand(PLUS);
                }
            case MINUS /*-2*/:
                if (!dependsOn(op1, wrt)) {
                    compileDerivative(op2, deriv, wrt);
                    deriv.addCommand(UNARY_MINUS);
                } else if (dependsOn(op2, wrt)) {
                    compileDerivative(op1, deriv, wrt);
                    compileDerivative(op2, deriv, wrt);
                    deriv.addCommand(MINUS);
                } else {
                    compileDerivative(op1, deriv, wrt);
                }
            case PLUS /*-1*/:
                if (!dependsOn(op1, wrt)) {
                    compileDerivative(op2, deriv, wrt);
                } else if (dependsOn(op2, wrt)) {
                    compileDerivative(op1, deriv, wrt);
                    compileDerivative(op2, deriv, wrt);
                    deriv.addCommand(PLUS);
                } else {
                    compileDerivative(op1, deriv, wrt);
                }
            default:
        }
    }

    private void doFuncDeriv(int opCode, int op, ExpressionProgram deriv, Variable wrt) {
        switch (opCode) {
            case CUBERT /*-36*/:
                deriv.addConstant(1.0d);
                deriv.addConstant(3.0d);
                copyExpression(op, deriv);
                deriv.addConstant(2.0d);
                deriv.addCommand(POWER);
                deriv.addCommand(CUBERT);
                deriv.addCommand(TIMES);
                deriv.addCommand(DIVIDE);
                break;
            case CEILING /*-35*/:
            case FLOOR /*-34*/:
            case ROUND /*-33*/:
            case TRUNC /*-32*/:
                copyExpression(op, deriv);
                if (opCode == ROUND) {
                    deriv.addConstant(0.5d);
                    deriv.addCommand(PLUS);
                }
                deriv.addCommand(ROUND);
                copyExpression(op, deriv);
                if (opCode == ROUND) {
                    deriv.addConstant(0.5d);
                    deriv.addCommand(PLUS);
                }
                deriv.addCommand(NE);
                if (opCode == TRUNC) {
                    copyExpression(op, deriv);
                    deriv.addConstant(0.0d);
                    deriv.addCommand(EQ);
                    deriv.addCommand(OR);
                }
                ExpressionProgram zero = new ExpressionProgram();
                zero.addConstant(0.0d);
                deriv.addCommandObject(new ConditionalExpression(zero, null));
                return;
            case LOG10 /*-31*/:
            case LOG2 /*-30*/:
            case LN /*-29*/:
                ExpressionProgram d = new ExpressionProgram();
                d.addConstant(1.0d);
                copyExpression(op, d);
                d.addCommand(DIVIDE);
                if (opCode != LN) {
                    d.addConstant(opCode == LOG2 ? 2.0d : BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS);
                    d.addCommand(LN);
                    d.addCommand(DIVIDE);
                }
                copyExpression(op, deriv);
                deriv.addConstant(0.0d);
                deriv.addCommand(GT);
                deriv.addCommandObject(new ConditionalExpression(d, null));
                break;
            case EXP /*-28*/:
                copyExpression(op, deriv);
                deriv.addCommand(EXP);
                break;
            case SQRT /*-27*/:
                deriv.addConstant(1.0d);
                deriv.addConstant(2.0d);
                copyExpression(op, deriv);
                deriv.addCommand(SQRT);
                deriv.addCommand(TIMES);
                deriv.addCommand(DIVIDE);
                break;
            case ABS /*-26*/:
                ExpressionProgram pos = new ExpressionProgram();
                ExpressionProgram neg = new ExpressionProgram();
                compileDerivative(op, pos, wrt);
                compileDerivative(op, neg, wrt);
                neg.addCommand(UNARY_MINUS);
                ExpressionProgram negTest = new ExpressionProgram();
                copyExpression(op, negTest);
                negTest.addConstant(0.0d);
                negTest.addCommand(LT);
                negTest.addCommandObject(new ConditionalExpression(neg, null));
                copyExpression(op, deriv);
                deriv.addConstant(0.0d);
                deriv.addCommand(GT);
                deriv.addCommandObject(new ConditionalExpression(pos, negTest));
                return;
            case ARCTAN /*-25*/:
                deriv.addConstant(1.0d);
                deriv.addConstant(1.0d);
                copyExpression(op, deriv);
                deriv.addConstant(2.0d);
                deriv.addCommand(POWER);
                deriv.addCommand(PLUS);
                deriv.addCommand(DIVIDE);
                break;
            case ARCCOS /*-24*/:
            case ARCSIN /*-23*/:
                deriv.addConstant(1.0d);
                if (opCode == ARCCOS) {
                    deriv.addCommand(UNARY_MINUS);
                }
                deriv.addConstant(1.0d);
                copyExpression(op, deriv);
                deriv.addConstant(2.0d);
                deriv.addCommand(POWER);
                deriv.addCommand(MINUS);
                deriv.addCommand(SQRT);
                deriv.addCommand(DIVIDE);
                break;
            case CSC /*-22*/:
                copyExpression(op, deriv);
                deriv.addCommand(CSC);
                copyExpression(op, deriv);
                deriv.addCommand(COT);
                deriv.addCommand(TIMES);
                deriv.addCommand(UNARY_MINUS);
                break;
            case SEC /*-21*/:
                copyExpression(op, deriv);
                deriv.addCommand(SEC);
                copyExpression(op, deriv);
                deriv.addCommand(TAN);
                deriv.addCommand(TIMES);
                break;
            case COT /*-20*/:
                copyExpression(op, deriv);
                deriv.addCommand(CSC);
                deriv.addConstant(2.0d);
                deriv.addCommand(POWER);
                deriv.addCommand(UNARY_MINUS);
                break;
            case TAN /*-19*/:
                copyExpression(op, deriv);
                deriv.addCommand(SEC);
                deriv.addConstant(2.0d);
                deriv.addCommand(POWER);
                break;
            case COS /*-18*/:
                copyExpression(op, deriv);
                deriv.addCommand(SIN);
                deriv.addCommand(UNARY_MINUS);
                break;
            case SIN /*-17*/:
                copyExpression(op, deriv);
                deriv.addCommand(COS);
                break;
        }
        if (this.prog[op] < 1073741823 || this.command[this.prog[op] - 1073741823] != wrt) {
            compileDerivative(op, deriv, wrt);
            deriv.addCommand(TIMES);
        }
    }

    private int findConstant(double d) {
        for (int i = 0; i < this.constantCt; i++) {
            if (this.constant[i] == d) {
                return i;
            }
        }
        if (this.constantCt == this.constant.length) {
            double[] temp = new double[(this.constant.length * 2)];
            System.arraycopy(this.constant, 0, temp, 0, this.constantCt);
            this.constant = temp;
        }
        double[] dArr = this.constant;
        int i2 = this.constantCt;
        this.constantCt = i2 + 1;
        dArr[i2] = d;
        return this.constantCt + PLUS;
    }

    private int findCommand(ExpressionCommand com) {
        for (int i = 0; i < this.commandCt; i++) {
            if (this.command[i] == com) {
                return i;
            }
        }
        if (this.commandCt == this.command.length) {
            ExpressionCommand[] temp = new ExpressionCommand[(this.command.length * 2)];
            System.arraycopy(this.command, 0, temp, 0, this.commandCt);
            this.command = temp;
        }
        ExpressionCommand[] expressionCommandArr = this.command;
        int i2 = this.commandCt;
        this.commandCt = i2 + 1;
        expressionCommandArr[i2] = com;
        return this.commandCt + PLUS;
    }
}
