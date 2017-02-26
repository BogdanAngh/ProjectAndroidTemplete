package edu.hws.jcm.data;

import com.example.duy.calculator.math_eval.Constants;
import com.example.duy.calculator.math_eval.LogicEvaluator;
import io.github.kexanie.library.BuildConfig;
import java.io.Serializable;
import org.apache.commons.math4.geometry.VectorFormat;
import org.apache.commons.math4.util.FastMath;

public class Parser implements Serializable {
    public static final int BOOLEANS = 32;
    public static final int BRACES = 16;
    public static final int BRACKETS = 8;
    public static final int CASE_SENSITIVE = 1;
    public static final int DEFAULT_OPTIONS = 1056;
    public static final int FACTORIAL = 64;
    public static final int NO_DIGITS_IN_IDENTIFIERS = 256;
    public static final int NO_UNDERSCORE_IN_IDENTIFIERS = 128;
    public static final int OPTIONAL_PARENS = 512;
    public static final int OPTIONAL_SPACES = 4;
    public static final int OPTIONAL_STARS = 2;
    public static final int STANDARD_FUNCTIONS = 1024;
    protected int options;
    protected SymbolTable symbols;

    public Parser() {
        this(null, DEFAULT_OPTIONS);
    }

    public Parser(Parser parent) {
        this(parent, 0);
    }

    public Parser(int options) {
        this(null, options);
    }

    public Parser(Parser parent, int options) {
        if (parent == null) {
            this.symbols = new SymbolTable();
            this.symbols.add(new Constant("e", FastMath.E));
            this.symbols.add(new Constant("pi", FastMath.PI));
        } else {
            this.symbols = new SymbolTable(parent.symbols);
            this.options = parent.options;
        }
        addOptions(options);
    }

    public void addOptions(int newOptions) {
        if ((newOptions & STANDARD_FUNCTIONS) != 0 && (this.options & STANDARD_FUNCTIONS) == 0) {
            for (int opCode = -36; opCode <= -17; opCode += CASE_SENSITIVE) {
                this.symbols.add(new StandardFunction(opCode));
            }
        }
        this.options |= newOptions;
    }

    public ExpressionProgram parse(String str) {
        ParserContext context = new ParserContext(str, this.options, this.symbols);
        if (str == null) {
            throw new ParseError("Can't parse a null string.", context);
        } else if (context.look() == CASE_SENSITIVE) {
            throw new ParseError("Can't parse an empty string.", context);
        } else {
            boolean isBool;
            if ((this.options & BOOLEANS) != 0) {
                isBool = parseLogicalExpression(context);
            } else {
                isBool = parseExpression(context);
            }
            if (context.look() != CASE_SENSITIVE) {
                throw new ParseError("Extra data found after the end of a complete legal expression.", context);
            } else if (isBool) {
                throw new ParseError("Found a logical-valued expression instead of a numeric expression.", context);
            } else {
                context.prog.trim();
                context.prog.sourceString = str;
                return context.prog;
            }
        }
    }

    public ExpressionProgram parseLogical(String str) {
        if ((this.options & BOOLEANS) == 0) {
            throw new IllegalArgumentException("Internal Error:  Attempt to parse a logical-valued expression, but BOOLEANS option is not turned on.");
        }
        ParserContext context = new ParserContext(str, this.options, this.symbols);
        if (str == null) {
            throw new ParseError("Can't parse a null string.", context);
        } else if (context.look() == CASE_SENSITIVE) {
            throw new ParseError("Can't parse an empty string.", context);
        } else {
            boolean isBool = parseLogicalExpression(context);
            if (context.look() != CASE_SENSITIVE) {
                throw new ParseError("Extra data found after the end of a complete legal expression.", context);
            } else if (isBool) {
                context.prog.trim();
                return context.prog;
            } else {
                throw new ParseError("Found a numeric-valued expression instead of a logical expression.", context);
            }
        }
    }

    public MathObject get(String name) {
        if ((this.options & CASE_SENSITIVE) != 0) {
            return this.symbols.get(name);
        }
        return this.symbols.get(name.toLowerCase());
    }

    public void add(MathObject sym) {
        if ((this.options & CASE_SENSITIVE) != 0) {
            this.symbols.add(sym);
        } else {
            this.symbols.add(sym.getName().toLowerCase(), sym);
        }
    }

    public void remove(String name) {
        if (name != null) {
            if ((this.options & CASE_SENSITIVE) != 0) {
                this.symbols.remove(name);
            } else {
                this.symbols.remove(name.toLowerCase());
            }
        }
    }

    public boolean parseLogicalExpression(ParserContext context) {
        boolean isBool = parseLogicalTerm(context);
        int tok = context.look();
        if (tok == OPTIONAL_SPACES && context.tokenString.equals("&") && !isBool) {
            throw new ParseError("The AND operator can only be used with logical expressions.", context);
        }
        while (tok == OPTIONAL_SPACES && context.tokenString.equals("&")) {
            context.next();
            if (parseLogicalTerm(context)) {
                context.prog.addCommand(-12);
                tok = context.look();
            } else {
                throw new ParseError("The AND operator can only be used with logical expressions.", context);
            }
        }
        if (tok != OPTIONAL_SPACES || !context.tokenString.equals(LogicEvaluator.ERROR_INDEX_STRING)) {
            return isBool;
        }
        if (isBool) {
            ExpressionProgram saveProg = context.prog;
            context.next();
            ExpressionProgram trueCase = new ExpressionProgram();
            context.prog = trueCase;
            if (parseLogicalExpression(context)) {
                throw new ParseError("The cases in a conditional expression cannot be logical-valued expressions.", context);
            }
            ExpressionProgram falseCase;
            if (context.look() == OPTIONAL_SPACES && context.tokenString.equals(":")) {
                context.next();
                falseCase = new ExpressionProgram();
                context.prog = falseCase;
                if (parseLogicalExpression(context)) {
                    throw new ParseError("The cases in a conditional expression cannot be logical-valued expressions.", context);
                }
            }
            falseCase = null;
            context.prog = saveProg;
            context.prog.addCommandObject(new ConditionalExpression(trueCase, falseCase));
            return false;
        }
        throw new ParseError("The conditional operator, ?, can only be applied to a logical-valued expression.", context);
    }

    public boolean parseLogicalTerm(ParserContext context) {
        boolean isBool = parseLogicalFactor(context);
        int tok = context.look();
        if (tok == OPTIONAL_SPACES && context.tokenString.equals("|") && !isBool) {
            throw new ParseError("The OR operator can only be used with logical expressions.", context);
        }
        while (tok == OPTIONAL_SPACES && context.tokenString.equals("|")) {
            context.next();
            if (parseLogicalFactor(context)) {
                context.prog.addCommand(-13);
                tok = context.look();
            } else {
                throw new ParseError("The OR operator can only be used with logical expressions.", context);
            }
        }
        return isBool;
    }

    public boolean parseLogicalFactor(ParserContext context) {
        int tok = context.look();
        int notCt = 0;
        while (tok == OPTIONAL_SPACES && context.tokenString.equals("~")) {
            context.next();
            tok = context.look();
            notCt += CASE_SENSITIVE;
        }
        boolean isBool = parseRelation(context);
        if (notCt <= 0 || isBool) {
            if (notCt % OPTIONAL_STARS == CASE_SENSITIVE) {
                context.prog.addCommand(-14);
            }
            return isBool;
        }
        throw new ParseError("The NOT operator can only be used with logical expressions.", context);
    }

    public boolean parseRelation(ParserContext context) {
        boolean isBool = parseExpression(context);
        if (context.look() != OPTIONAL_SPACES) {
            return isBool;
        }
        int rel = 0;
        if (context.tokenString.equals("=")) {
            rel = -6;
        } else if (context.tokenString.equals("<")) {
            rel = -8;
        } else if (context.tokenString.equals(">")) {
            rel = -9;
        } else if (context.tokenString.equals("<=")) {
            rel = -10;
        } else if (context.tokenString.equals(">=")) {
            rel = -11;
        } else if (context.tokenString.equals("<>")) {
            rel = -7;
        }
        if (rel == 0) {
            return isBool;
        }
        if (isBool) {
            throw new ParseError("A relational operator can only be used with numerical expressions.", context);
        }
        context.next();
        if (parseExpression(context)) {
            throw new ParseError("A relational operator can only be used with numerical expressions.", context);
        } else if (context.look() == OPTIONAL_SPACES && (context.tokenString.equals("=") || context.tokenString.equals("<") || context.tokenString.equals(">") || context.tokenString.equals("<=") || context.tokenString.equals(">=") || context.tokenString.equals("<>"))) {
            throw new ParseError("It is illegal to string together relations operators; use \"AND\" instead.", context);
        } else {
            context.prog.addCommand(rel);
            return true;
        }
    }

    public boolean parseExpression(ParserContext context) {
        boolean neg = false;
        if (context.look() == OPTIONAL_SPACES && (context.tokenString.equals("+") || context.tokenString.equals("-"))) {
            neg = context.tokenString.equals("-");
            context.next();
        }
        boolean isBool = parseTerm(context);
        if (neg) {
            if (isBool) {
                throw new ParseError("A unary + or - cannot be applied to a logical expression.", context);
            }
            context.prog.addCommand(-15);
        }
        int tok = context.look();
        if (tok == OPTIONAL_SPACES && ((context.tokenString.equals("+") || context.tokenString.equals("-")) && isBool)) {
            throw new ParseError("A + or - operator cannot be applied to logical operands.", context);
        }
        while (tok == OPTIONAL_SPACES && (context.tokenString.equals("+") || context.tokenString.equals("-"))) {
            context.next();
            int i = context.tokenString.equals("+") ? -1 : -2;
            if (parseTerm(context)) {
                throw new ParseError("A + or - operator cannot be applied to logical operands.", context);
            }
            context.prog.addCommand(i);
            tok = context.look();
        }
        return isBool;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean parseTerm(edu.hws.jcm.data.ParserContext r12) {
        /*
        r11 = this;
        r10 = 3;
        r9 = 2;
        r5 = 1;
        r6 = 0;
        r8 = 4;
        r0 = 0;
        r1 = r11.parsePrimary(r12);
        r3 = r12.look();
        r4 = r12.tokenString;
        if (r1 != 0) goto L_0x0053;
    L_0x0012:
        r7 = r11.options;
        r7 = r7 & 2;
        if (r7 == 0) goto L_0x0053;
    L_0x0018:
        if (r3 == r9) goto L_0x0036;
    L_0x001a:
        if (r3 == r10) goto L_0x0036;
    L_0x001c:
        if (r3 != r8) goto L_0x0053;
    L_0x001e:
        r7 = "(";
        r7 = r4.equals(r7);
        if (r7 != 0) goto L_0x0036;
    L_0x0026:
        r7 = "[";
        r7 = r4.equals(r7);
        if (r7 != 0) goto L_0x0036;
    L_0x002e:
        r7 = "{";
        r7 = r4.equals(r7);
        if (r7 == 0) goto L_0x0053;
    L_0x0036:
        r0 = r5;
    L_0x0037:
        if (r3 != r8) goto L_0x0087;
    L_0x0039:
        r7 = "*";
        r7 = r4.equals(r7);
        if (r7 != 0) goto L_0x0049;
    L_0x0041:
        r7 = "/";
        r7 = r4.equals(r7);
        if (r7 == 0) goto L_0x0087;
    L_0x0049:
        if (r1 == 0) goto L_0x0087;
    L_0x004b:
        r5 = new edu.hws.jcm.data.ParseError;
        r6 = "A * or / operator cannot be applied to logical operands.";
        r5.<init>(r6, r12);
        throw r5;
    L_0x0053:
        r0 = r6;
        goto L_0x0037;
    L_0x0055:
        r7 = r12.prog;
        r7.addCommand(r2);
        r3 = r12.look();
        r4 = r12.tokenString;
        if (r1 != 0) goto L_0x00bb;
    L_0x0062:
        r7 = r11.options;
        r7 = r7 & 2;
        if (r7 == 0) goto L_0x00bb;
    L_0x0068:
        if (r3 == r9) goto L_0x0086;
    L_0x006a:
        if (r3 == r10) goto L_0x0086;
    L_0x006c:
        if (r3 != r8) goto L_0x00bb;
    L_0x006e:
        r7 = "(";
        r7 = r4.equals(r7);
        if (r7 != 0) goto L_0x0086;
    L_0x0076:
        r7 = "[";
        r7 = r4.equals(r7);
        if (r7 != 0) goto L_0x0086;
    L_0x007e:
        r7 = "{";
        r7 = r4.equals(r7);
        if (r7 == 0) goto L_0x00bb;
    L_0x0086:
        r0 = r5;
    L_0x0087:
        if (r0 != 0) goto L_0x009b;
    L_0x0089:
        if (r3 != r8) goto L_0x00bd;
    L_0x008b:
        r7 = "*";
        r7 = r4.equals(r7);
        if (r7 != 0) goto L_0x009b;
    L_0x0093:
        r7 = "/";
        r7 = r4.equals(r7);
        if (r7 == 0) goto L_0x00bd;
    L_0x009b:
        if (r0 != 0) goto L_0x00a0;
    L_0x009d:
        r12.next();
    L_0x00a0:
        if (r0 != 0) goto L_0x00aa;
    L_0x00a2:
        r7 = "*";
        r7 = r4.equals(r7);
        if (r7 == 0) goto L_0x00b9;
    L_0x00aa:
        r2 = -3;
    L_0x00ab:
        r7 = r11.parsePrimary(r12);
        if (r7 == 0) goto L_0x0055;
    L_0x00b1:
        r5 = new edu.hws.jcm.data.ParseError;
        r6 = "A * or / operator cannot be applied to logical operands.";
        r5.<init>(r6, r12);
        throw r5;
    L_0x00b9:
        r2 = -4;
        goto L_0x00ab;
    L_0x00bb:
        r0 = r6;
        goto L_0x0087;
    L_0x00bd:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.hws.jcm.data.Parser.parseTerm(edu.hws.jcm.data.ParserContext):boolean");
    }

    public boolean parsePrimary(ParserContext context) {
        boolean isBool = parseFactor(context);
        if (context.look() == OPTIONAL_SPACES && context.tokenString.equals("^")) {
            if (isBool) {
                throw new ParseError("The exponentiation operator cannot be applied to logical operands.", context);
            }
            context.next();
            if (parsePrimary(context)) {
                throw new ParseError("The exponentiation operator cannot be applied to logical operands.", context);
            }
            context.prog.addCommand(-5);
        }
        return isBool;
    }

    public boolean parseFactor(ParserContext context) {
        boolean isBool = false;
        int tok = context.next();
        if (tok == OPTIONAL_STARS) {
            context.prog.addConstant(context.tokenValue);
        } else if (tok == 3) {
            parseWord(context);
        } else if (tok == CASE_SENSITIVE) {
            throw new ParseError("Data ended in the middle of an incomplete expression.", context);
        } else if (tok != OPTIONAL_SPACES) {
            throw new ParseError("Internal error:  Unknown token type.", context);
        } else if (context.tokenString.equals("(")) {
            isBool = parseGroup(Constants.LEFT_PAREN, Constants.RIGHT_PAREN, context);
        } else if (context.tokenString.equals("[") && (this.options & BRACKETS) != 0) {
            isBool = parseGroup('[', ']', context);
        } else if (context.tokenString.equals(VectorFormat.DEFAULT_PREFIX) && (this.options & BRACES) != 0) {
            isBool = parseGroup('{', '}', context);
        } else if (context.tokenString.equals(VectorFormat.DEFAULT_SUFFIX) && (this.options & BRACES) != 0) {
            throw new ParseError("Misplaced right brace with no matching left brace.", context);
        } else if (context.tokenString.equals("]") && (this.options & BRACKETS) != 0) {
            throw new ParseError("Misplaced right bracket with no matching left bracket.", context);
        } else if (context.tokenString.equals(")")) {
            throw new ParseError("Misplaced right parenthesis with no matching left parenthesis.", context);
        } else {
            throw new ParseError("Illegal or misplaced character \"" + context.tokenString.charAt(0) + "\"", context);
        }
        if ((this.options & FACTORIAL) != 0) {
            tok = context.look();
            while (tok == OPTIONAL_SPACES && context.tokenString.equals("!")) {
                if (isBool) {
                    throw new ParseError("The factorial operator cannot be applied to a logical value.", context);
                }
                context.next();
                context.prog.addCommand(-16);
                tok = context.look();
            }
        }
        return isBool;
    }

    private boolean parseGroup(char open, char close, ParserContext context) {
        boolean isBool = (this.options & BOOLEANS) == 0 ? parseExpression(context) : parseLogicalExpression(context);
        if (context.look() == OPTIONAL_SPACES && context.tokenString.equals(BuildConfig.FLAVOR + close)) {
            context.next();
            return isBool;
        }
        throw new ParseError("Missing \"" + close + "\" to match a previous \"" + open + "\".", context);
    }

    private void parseWord(ParserContext context) {
        if (context.tokenObject == null) {
            throw new ParseError("Unknown word \"" + context.tokenString + "\" encountered in an expression.", context);
        } else if ((context.tokenObject instanceof Variable) || (context.tokenObject instanceof Constant)) {
            context.prog.addCommandObject((ExpressionCommand) context.tokenObject);
        } else if (context.tokenObject instanceof StandardFunction) {
            StandardFunction f = context.tokenObject;
            if (context.look() == OPTIONAL_SPACES && (context.tokenString.equals("(") || ((context.tokenString.equals("[") && (this.options & BRACKETS) != 0) || (context.tokenString.equals(VectorFormat.DEFAULT_PREFIX) && (this.options & BRACES) != 0)))) {
                boolean isBool;
                context.next();
                if (context.tokenString.equals("(")) {
                    isBool = parseGroup(Constants.LEFT_PAREN, Constants.RIGHT_PAREN, context);
                } else if (context.tokenString.equals("[")) {
                    isBool = parseGroup('[', ']', context);
                } else {
                    isBool = parseGroup('{', '}', context);
                }
                if (isBool) {
                    throw new ParseError("The argument of a function must be a numerical expression.", context);
                }
            } else if ((this.options & OPTIONAL_PARENS) == 0) {
                throw new ParseError("Parentheses required around argument of standard function \"" + f.getName() + "\".", context);
            } else if (parseTerm(context)) {
                throw new ParseError("The argument of a function must be a numerical expression.", context);
            }
            context.prog.addCommand(f.getOpCode());
        } else if (context.tokenObject instanceof ParserExtension) {
            ((ParserExtension) context.tokenObject).doParse(this, context);
        } else if (context.tokenObject instanceof ExpressionCommand) {
            throw new ParseError("Unimplemented word \"" + context.tokenObject.getName() + "\" encountered in an expression.", context);
        } else {
            throw new ParseError("Unexpected word \"" + context.tokenObject.getName() + "\" encountered in an expression.", context);
        }
    }
}
