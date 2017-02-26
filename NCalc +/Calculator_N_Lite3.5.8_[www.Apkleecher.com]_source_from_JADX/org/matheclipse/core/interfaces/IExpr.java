package org.matheclipse.core.interfaces;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import edu.jas.structure.GcdRingElem;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.commons.math4.complex.Complex;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.visit.IVisitor;
import org.matheclipse.core.visit.IVisitorBoolean;
import org.matheclipse.core.visit.IVisitorInt;
import org.matheclipse.core.visit.IVisitorLong;

public interface IExpr extends Comparable<IExpr>, GcdRingElem<IExpr>, Serializable {
    public static final int ASTID = 1024;
    public static final int BLANKID = 256;
    public static final int COMPLEXID = 32;
    public static final int DOUBLECOMPLEXID = 4;
    public static final int DOUBLEID = 2;
    public static final int FRACTIONID = 16;
    public static final int INTEGERID = 8;
    public static final int METHODSYMBOLID = 2048;
    public static final int PATTERNID = 512;
    public static final int STRINGID = 64;
    public static final int SYMBOLID = 128;

    IExpr $div(IExpr iExpr);

    IExpr $minus(IExpr iExpr);

    IExpr $plus(IExpr iExpr);

    IExpr $times(IExpr iExpr);

    IExpr $up(IExpr iExpr);

    int accept(IVisitorInt iVisitorInt);

    long accept(IVisitorLong iVisitorLong);

    <T> T accept(IVisitor<T> iVisitor);

    boolean accept(IVisitorBoolean iVisitorBoolean);

    IExpr and(IExpr iExpr);

    IExpr apply(List<? extends IExpr> list);

    IExpr apply(IExpr... iExprArr);

    Object asType(Class<?> cls);

    int compareTo(IExpr iExpr);

    IExpr dec();

    IExpr divide(IExpr iExpr);

    Complex evalComplex();

    double evalDouble();

    INumber evalNumber();

    ISignedNumber evalSignedNumber();

    IExpr evaluate(EvalEngine evalEngine);

    String fullFormString();

    IExpr getAt(int i);

    IExpr head();

    int hierarchy();

    IExpr inc();

    String internalFormString(boolean z, int i);

    String internalJavaString(boolean z, int i, boolean z2);

    String internalScalaString(boolean z, int i);

    IExpr inverse();

    boolean isAST();

    boolean isAST(String str);

    boolean isAST(String str, int i);

    boolean isAST(IExpr iExpr);

    boolean isAST(IExpr iExpr, int i);

    boolean isAST(IExpr iExpr, int i, int i2);

    boolean isAST(IExpr iExpr, int i, IExpr... iExprArr);

    boolean isASTSizeGE(IExpr iExpr, int i);

    boolean isAllExpanded();

    boolean isAnd();

    boolean isArcCos();

    boolean isArcCosh();

    boolean isArcSin();

    boolean isArcSinh();

    boolean isArcTan();

    boolean isArcTanh();

    boolean isAtom();

    boolean isBlank();

    boolean isComplex();

    boolean isComplexInfinity();

    boolean isComplexNumeric();

    boolean isCondition();

    boolean isConstant();

    boolean isCos();

    boolean isCosh();

    IAST[] isDerivative();

    boolean isDirectedInfinity();

    boolean isE();

    boolean isExpanded();

    boolean isFalse();

    boolean isFlatAST();

    boolean isFraction();

    boolean isFree(Predicate<IExpr> predicate, boolean z);

    boolean isFree(IExpr iExpr);

    boolean isFree(IExpr iExpr, boolean z);

    boolean isFreeAST(Predicate<IExpr> predicate);

    boolean isFreeAST(IExpr iExpr);

    boolean isFreeOfPatterns();

    boolean isFunction();

    boolean isGEOrdered(IExpr iExpr);

    boolean isGTOrdered(IExpr iExpr);

    boolean isIndeterminate();

    boolean isInfinity();

    boolean isInteger();

    boolean isIntegerResult();

    boolean isLEOrdered(IExpr iExpr);

    boolean isLTOrdered(IExpr iExpr);

    boolean isList();

    boolean isListOfLists();

    boolean isLog();

    int[] isMatrix();

    boolean isMember(Predicate<IExpr> predicate, boolean z);

    boolean isMember(IExpr iExpr, boolean z);

    boolean isMinusOne();

    boolean isModule();

    boolean isNegative();

    boolean isNegativeInfinity();

    boolean isNegativeResult();

    boolean isNonNegativeResult();

    boolean isNot();

    boolean isNumEqualInteger(IInteger iInteger) throws ArithmeticException;

    boolean isNumEqualRational(IRational iRational) throws ArithmeticException;

    boolean isNumIntValue();

    boolean isNumber();

    boolean isNumeric();

    boolean isNumericFunction();

    boolean isNumericMode();

    boolean isONE();

    boolean isOne();

    boolean isOr();

    boolean isOrderlessAST();

    boolean isPattern();

    boolean isPatternDefault();

    boolean isPatternExpr();

    boolean isPatternSequence();

    boolean isPi();

    boolean isPlus();

    boolean isPlusTimesPower();

    boolean isPolynomial(IAST iast);

    boolean isPolynomial(ISymbol iSymbol);

    boolean isPolynomialOfMaxDegree(ISymbol iSymbol, long j);

    boolean isPositive();

    boolean isPositiveResult();

    boolean isPower();

    boolean isRational();

    boolean isRationalResult();

    boolean isRationalValue(IRational iRational);

    boolean isRealResult();

    boolean isRuleAST();

    boolean isSame(IExpr iExpr);

    boolean isSame(IExpr iExpr, double d);

    boolean isSequence();

    boolean isSignedNumber();

    boolean isSin();

    boolean isSinh();

    boolean isSlot();

    boolean isSlotSequence();

    boolean isSymbol();

    boolean isTan();

    boolean isTanh();

    boolean isTimes();

    boolean isTrue();

    boolean isValue();

    boolean isVariable();

    int isVector();

    boolean isZERO();

    boolean isZero();

    long leafCount();

    List<IExpr> leaves();

    IExpr minus(IExpr iExpr);

    IExpr mod(IExpr iExpr);

    IExpr multiply(IExpr iExpr);

    IExpr negative();

    IExpr opposite();

    IExpr optional(IExpr iExpr);

    IExpr or(IExpr iExpr);

    IExpr plus(IExpr iExpr);

    IExpr power(long j);

    IExpr power(IExpr iExpr);

    @Nullable
    IExpr replaceAll(Function<IExpr, IExpr> function);

    @Nullable
    IExpr replaceAll(IAST iast);

    IExpr replacePart(IAST iast);

    IExpr replaceRepeated(Function<IExpr, IExpr> function);

    IExpr replaceRepeated(IAST iast);

    IExpr replaceSlots(IAST iast);

    @Deprecated
    int signum();

    IExpr times(IExpr iExpr);

    ISymbol topHead();

    IExpr variables2Slots(Map<IExpr, IExpr> map, List<IExpr> list);
}
