package org.matheclipse.core.expression;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import edu.jas.structure.ElemFactory;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import org.apache.commons.math4.complex.Complex;
import org.matheclipse.core.basic.Config;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.IterationLimitExceeded;
import org.matheclipse.core.eval.exception.WrongArgumentType;
import org.matheclipse.core.eval.util.AbstractAssumptions;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IComplex;
import org.matheclipse.core.interfaces.IComplexNum;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IFraction;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.INum;
import org.matheclipse.core.interfaces.INumber;
import org.matheclipse.core.interfaces.IRational;
import org.matheclipse.core.interfaces.ISignedNumber;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.patternmatching.PatternMatcher;
import org.matheclipse.core.visit.IVisitor;
import org.matheclipse.core.visit.VisitorReplaceAll;
import org.matheclipse.core.visit.VisitorReplacePart;
import org.matheclipse.core.visit.VisitorReplaceSlots;

public abstract class ExprImpl implements IExpr, Serializable {
    private static final long serialVersionUID = -3346614106664542983L;

    public abstract ISymbol head();

    public static IExpr replaceRepeated(IExpr expr, VisitorReplaceAll visitor) {
        IExpr result = expr;
        IExpr temp = (IExpr) expr.accept((IVisitor) visitor);
        int iterationLimit = EvalEngine.get().getIterationLimit();
        int iterationCounter = 1;
        while (temp != null) {
            result = temp;
            temp = (IExpr) result.accept((IVisitor) visitor);
            if (iterationLimit >= 0) {
                iterationCounter++;
                if (iterationLimit <= iterationCounter) {
                    IterationLimitExceeded.throwIt((long) iterationCounter, result);
                }
            }
        }
        return result;
    }

    public IExpr abs() {
        if (this instanceof INumber) {
            return ((INumber) this).eabs();
        }
        throw new UnsupportedOperationException(toString());
    }

    public IExpr and(IExpr that) {
        return F.And(this, that);
    }

    public IExpr apply(IExpr... leaves) {
        IAST ast = F.ast(head());
        for (Object add : leaves) {
            ast.add(add);
        }
        return ast;
    }

    public IExpr apply(List<? extends IExpr> leaves) {
        IAST ast = F.ast(head());
        for (int i = 0; i < leaves.size(); i++) {
            ast.add(leaves.get(i));
        }
        return ast;
    }

    public Object asType(Class<?> clazz) {
        if (clazz.equals(Boolean.class)) {
            if (equals(F.True)) {
                return Boolean.TRUE;
            }
            if (equals(F.False)) {
                return Boolean.FALSE;
            }
        } else if (clazz.equals(Integer.class)) {
            if (isSignedNumber()) {
                try {
                    return Integer.valueOf(((ISignedNumber) this).toInt());
                } catch (ArithmeticException e) {
                }
            }
        } else if (clazz.equals(BigInteger.class)) {
            if (this instanceof IntegerSym) {
                return new BigInteger(((IntegerSym) this).toByteArray());
            }
        } else if (clazz.equals(String.class)) {
            return toString();
        }
        throw new UnsupportedOperationException("ExprImpl.asType() - cast not supported.");
    }

    public int compareTo(IExpr expr) {
        int x = hierarchy();
        int y = expr.hierarchy();
        if (x < y) {
            return -1;
        }
        return x == y ? 0 : 1;
    }

    public IExpr copy() {
        try {
            return (IExpr) clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public INumber conjugate() {
        if (isSignedNumber()) {
            return (INumber) this;
        }
        return null;
    }

    public IExpr divide(IExpr that) {
        if (!isNumber() || !that.isNumber()) {
            return F.eval(F.Times(this, F.Power(that, F.CN1)));
        }
        if (that.isOne()) {
            return this;
        }
        return times(that.inverse());
    }

    public IExpr[] egcd(IExpr b) {
        throw new UnsupportedOperationException(toString());
    }

    public double evalDouble() {
        if (isSignedNumber()) {
            return ((ISignedNumber) this).doubleValue();
        }
        throw new WrongArgumentType(this, "Conversion into a double numeric value is not possible!");
    }

    public Complex evalComplex() {
        if (isNumber()) {
            return ((INumber) this).complexNumValue().complexValue();
        }
        throw new WrongArgumentType(this, "Conversion into a complex numeric value is not possible!");
    }

    public INumber evalNumber() {
        if (isNumber()) {
            return (INumber) this;
        }
        return null;
    }

    public ISignedNumber evalSignedNumber() {
        if (isSignedNumber()) {
            return (ISignedNumber) this;
        }
        return null;
    }

    public IExpr evaluate(EvalEngine engine) {
        return null;
    }

    public ElemFactory<IExpr> factory() {
        throw new UnsupportedOperationException(toString());
    }

    public String fullFormString() {
        return toString();
    }

    public IExpr gcd(IExpr that) {
        throw new UnsupportedOperationException("gcd(" + toString() + ", " + that.toString() + ")");
    }

    public IExpr getAt(int index) {
        return F.Part(new IExpr[]{this, F.integer((long) index)});
    }

    public String internalFormString(boolean symbolsAsFactoryMethod, int depth) {
        return toString();
    }

    public String internalScalaString(boolean symbolsAsFactoryMethod, int depth) {
        return toString();
    }

    public String internalJavaString(boolean symbolsAsFactoryMethod, int depth, boolean useOperators) {
        return toString();
    }

    public IExpr inverse() {
        return power(F.CN1);
    }

    public final boolean isAnd() {
        return false;
    }

    public final boolean isArcCos() {
        return false;
    }

    public final boolean isArcCosh() {
        return false;
    }

    public final boolean isArcSin() {
        return false;
    }

    public final boolean isArcSinh() {
        return false;
    }

    public final boolean isArcTan() {
        return false;
    }

    public final boolean isArcTanh() {
        return false;
    }

    public final boolean isAST() {
        return false;
    }

    public final boolean isAST(IExpr header) {
        return false;
    }

    public final boolean isAST(IExpr header, int length) {
        return false;
    }

    public final boolean isAST(IExpr header, int minLength, int maxLength) {
        return false;
    }

    public final boolean isAST(IExpr header, int length, IExpr... args) {
        return false;
    }

    public final boolean isAST(String symbol) {
        return false;
    }

    public final boolean isAST(String symbol, int length) {
        return false;
    }

    public final boolean isASTSizeGE(IExpr header, int length) {
        return false;
    }

    public boolean isAtom() {
        return true;
    }

    public boolean isBlank() {
        return false;
    }

    public boolean isComplex() {
        return this instanceof IComplex;
    }

    public final boolean isComplexInfinity() {
        return false;
    }

    public final boolean isComplexNumeric() {
        return this instanceof IComplexNum;
    }

    public final boolean isCondition() {
        return false;
    }

    public boolean isConstant() {
        return false;
    }

    public final boolean isCos() {
        return false;
    }

    public final boolean isCosh() {
        return false;
    }

    public final IAST[] isDerivative() {
        return null;
    }

    public final boolean isDirectedInfinity() {
        return false;
    }

    public boolean isE() {
        return false;
    }

    public final boolean isExpanded() {
        return true;
    }

    public final boolean isAllExpanded() {
        return true;
    }

    public final boolean isPlusTimesPower() {
        return false;
    }

    public boolean isFalse() {
        return false;
    }

    public final boolean isFlatAST() {
        return false;
    }

    public final boolean isFraction() {
        return this instanceof IFraction;
    }

    public final boolean isFree(IExpr pattern) {
        return isFree(pattern, true);
    }

    public boolean isFree(IExpr pattern, boolean heads) {
        return !new PatternMatcher(pattern).apply(this);
    }

    public boolean isFree(Predicate<IExpr> predicate, boolean heads) {
        return !predicate.apply(this);
    }

    public final boolean isFreeAST(IExpr pattern) {
        return true;
    }

    public final boolean isFreeAST(Predicate<IExpr> predicate) {
        return true;
    }

    public boolean isFreeOfPatterns() {
        return true;
    }

    public final boolean isFunction() {
        return false;
    }

    public final boolean isGEOrdered(IExpr obj) {
        return compareTo(obj) >= 0;
    }

    public final boolean isGTOrdered(IExpr obj) {
        return compareTo(obj) > 0;
    }

    public boolean isIndeterminate() {
        return false;
    }

    public final boolean isInfinity() {
        return false;
    }

    public final boolean isInteger() {
        return this instanceof IInteger;
    }

    public final boolean isIntegerResult() {
        if (F.True.equals(AbstractAssumptions.assumeInteger(this))) {
            return true;
        }
        return this instanceof IInteger;
    }

    public final boolean isRationalResult() {
        if (F.True.equals(AbstractAssumptions.assumeRational(this))) {
            return true;
        }
        return this instanceof IRational;
    }

    public final boolean isRealResult() {
        if (F.True.equals(AbstractAssumptions.assumeReal(this))) {
            return true;
        }
        return this instanceof ISignedNumber;
    }

    public final boolean isNegativeResult() {
        return AbstractAssumptions.assumeNegative(this);
    }

    public final boolean isPositiveResult() {
        return AbstractAssumptions.assumePositive(this);
    }

    public final boolean isNonNegativeResult() {
        return AbstractAssumptions.assumeNonNegative(this);
    }

    public final boolean isLEOrdered(IExpr obj) {
        return compareTo(obj) <= 0;
    }

    public final boolean isList() {
        return false;
    }

    public final boolean isListOfLists() {
        return false;
    }

    public final boolean isLog() {
        return false;
    }

    public final boolean isLTOrdered(IExpr obj) {
        return compareTo(obj) < 0;
    }

    public final int[] isMatrix() {
        return null;
    }

    public final boolean isMember(IExpr pattern, boolean heads) {
        return isMember(new PatternMatcher(pattern), heads);
    }

    public final boolean isMember(Predicate<IExpr> predicate, boolean heads) {
        return predicate.apply(this);
    }

    public boolean isMinusOne() {
        return false;
    }

    public final boolean isModule() {
        return false;
    }

    public boolean isNegative() {
        return false;
    }

    public final boolean isNegativeInfinity() {
        return false;
    }

    public final boolean isNot() {
        return false;
    }

    public final boolean isNumber() {
        return this instanceof INumber;
    }

    public boolean isNumEqualInteger(IInteger value) throws ArithmeticException {
        return false;
    }

    public boolean isNumEqualRational(IRational value) throws ArithmeticException {
        return false;
    }

    public final boolean isNumeric() {
        return (this instanceof INum) || (this instanceof IComplexNum);
    }

    public final boolean isNumericFunction() {
        return isNumber() || isConstant();
    }

    public final boolean isNumericMode() {
        return isNumeric();
    }

    public boolean isNumIntValue() {
        return false;
    }

    public boolean isOne() {
        return false;
    }

    public boolean isONE() {
        return isOne();
    }

    public final boolean isOr() {
        return false;
    }

    public final boolean isOrderlessAST() {
        return false;
    }

    public boolean isPattern() {
        return false;
    }

    public boolean isPatternDefault() {
        return false;
    }

    public boolean isPatternExpr() {
        return false;
    }

    public boolean isPatternSequence() {
        return false;
    }

    public boolean isPi() {
        return false;
    }

    public final boolean isPlus() {
        return false;
    }

    public boolean isPolynomial(ISymbol variable) {
        return isNumber();
    }

    public boolean isPolynomial(IAST variables) {
        return isNumber();
    }

    public boolean isPolynomialOfMaxDegree(ISymbol variable, long maxDegree) {
        return isPolynomial(variable);
    }

    public boolean isPositive() {
        return false;
    }

    public final boolean isPower() {
        return false;
    }

    public final boolean isRational() {
        return this instanceof IRational;
    }

    public final boolean isRuleAST() {
        return false;
    }

    public final boolean isSame(IExpr expression) {
        return isSame(expression, Config.DOUBLE_EPSILON);
    }

    public boolean isSame(IExpr expression, double epsilon) {
        return equals(expression);
    }

    public final boolean isSequence() {
        return false;
    }

    public boolean isSignedNumber() {
        return this instanceof ISignedNumber;
    }

    public final boolean isSin() {
        return false;
    }

    public final boolean isSinh() {
        return false;
    }

    public final boolean isSlot() {
        return false;
    }

    public final boolean isSlotSequence() {
        return false;
    }

    public final boolean isSymbol() {
        return this instanceof ISymbol;
    }

    public boolean isRationalValue(IRational value) {
        return false;
    }

    public final boolean isTan() {
        return false;
    }

    public final boolean isTanh() {
        return false;
    }

    public final boolean isTimes() {
        return false;
    }

    public boolean isTrue() {
        return false;
    }

    public final boolean isUnit() {
        return true;
    }

    public boolean isValue() {
        return false;
    }

    public boolean isVariable() {
        return false;
    }

    public final int isVector() {
        return -1;
    }

    public boolean isZero() {
        return false;
    }

    public boolean isZERO() {
        return isZero();
    }

    public final long leafCount() {
        return isAtom() ? 1 : 0;
    }

    public final List<IExpr> leaves() {
        return null;
    }

    public final IExpr minus(IExpr that) {
        if (isNumber() && that.isNumber()) {
            return F.eval(F.Plus(this, ((INumber) that).opposite()));
        }
        if (that.isNumber()) {
            return F.eval(F.Plus(this, ((INumber) that).opposite()));
        }
        return F.eval(F.Plus(this, F.Times(F.CN1, that)));
    }

    public final IExpr mod(IExpr that) {
        return F.Mod(this, that);
    }

    public final IExpr multiply(IExpr that) {
        return times(that);
    }

    public IExpr negate() {
        return opposite();
    }

    public final IExpr negative() {
        return opposite();
    }

    public IExpr opposite() {
        return times(F.CN1);
    }

    public final IExpr optional(IExpr that) {
        return that != null ? that : this;
    }

    public final IExpr or(IExpr that) {
        return F.Or(this, that);
    }

    public IExpr plus(IExpr that) {
        return F.eval(F.Plus(this, that));
    }

    public final IExpr inc() {
        return plus(F.C1);
    }

    public final IExpr dec() {
        return plus(F.CN1);
    }

    public final IExpr power(IExpr that) {
        if (that.isZero()) {
            if (!isZero()) {
                return F.C1;
            }
        } else if (that.isOne()) {
            return this;
        }
        if (isNumber() && that.isNumber()) {
            return F.eval(F.Power(this, that));
        }
        return F.Power(this, that);
    }

    public final IExpr power(long n) {
        if (n == 0) {
            if (!isZero()) {
                return F.C1;
            }
        } else if (n == 1) {
            return this;
        } else {
            if (isNumber()) {
                long exp = n;
                if (n < 0) {
                    exp *= -1;
                }
                int b2pow = 0;
                while ((exp & 1) == 0) {
                    b2pow++;
                    exp >>= 1;
                }
                INumber r = (INumber) this;
                INumber x = r;
                while (true) {
                    exp >>= 1;
                    if (exp <= 0) {
                        break;
                    }
                    x = (INumber) x.multiply(x);
                    if ((exp & 1) != 0) {
                        r = (INumber) r.multiply(x);
                    }
                }
                int b2pow2 = b2pow;
                while (true) {
                    b2pow = b2pow2 - 1;
                    if (b2pow2 <= 0) {
                        break;
                    }
                    r = (INumber) r.multiply(r);
                    b2pow2 = b2pow;
                }
                return n < 0 ? r.inverse() : r;
            }
        }
        return F.Power(this, F.integer(n));
    }

    public IExpr remainder(IExpr that) {
        throw new UnsupportedOperationException(toString());
    }

    public final IExpr replaceAll(Function<IExpr, IExpr> function) {
        return (IExpr) accept((IVisitor) new VisitorReplaceAll(function));
    }

    public final IExpr replaceAll(IAST astRules) {
        return (IExpr) accept((IVisitor) new VisitorReplaceAll(astRules));
    }

    public final IExpr replacePart(IAST astRules) {
        return (IExpr) accept((IVisitor) new VisitorReplacePart(astRules));
    }

    public final IExpr replaceRepeated(Function<IExpr, IExpr> function) {
        return replaceRepeated(this, new VisitorReplaceAll(function));
    }

    public final IExpr replaceRepeated(IAST astRules) {
        return replaceRepeated(this, new VisitorReplaceAll(astRules));
    }

    public final IExpr replaceSlots(IAST astSlots) {
        return (IExpr) accept((IVisitor) new VisitorReplaceSlots(astSlots));
    }

    @Deprecated
    public final int signum() {
        if (isZERO()) {
            return 0;
        }
        if (isSignedNumber()) {
            return ((ISignedNumber) this).sign();
        }
        return 1;
    }

    public final IExpr subtract(IExpr that) {
        return plus((IExpr) that.negate());
    }

    public final IExpr sum(IExpr that) {
        return plus(that);
    }

    public IExpr times(IExpr that) {
        return F.eval(F.Times(this, that));
    }

    public final ISymbol topHead() {
        return head();
    }

    public final String toScript() {
        return toString();
    }

    public final String toScriptFactory() {
        throw new UnsupportedOperationException(toString());
    }

    public IExpr variables2Slots(Map<IExpr, IExpr> map, List<IExpr> list) {
        return this;
    }

    public final IExpr $div(IExpr that) {
        return divide(that);
    }

    public final IExpr $minus(IExpr that) {
        return minus(that);
    }

    public final IExpr $plus(IExpr that) {
        return plus(that);
    }

    public final IExpr $times(IExpr that) {
        return times(that);
    }

    public final IExpr $up(IExpr that) {
        return power(that);
    }
}
