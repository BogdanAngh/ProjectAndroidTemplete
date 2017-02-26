package org.matheclipse.core.expression;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.example.duy.calculator.math_eval.Constants;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import edu.jas.structure.ElemFactory;
import java.math.BigInteger;
import java.util.AbstractList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.math4.complex.Complex;
import org.matheclipse.core.basic.Config;
import org.matheclipse.core.builtin.function.LeafCount.LeafCountVisitor;
import org.matheclipse.core.convert.AST2Expr;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.WrongArgumentType;
import org.matheclipse.core.eval.util.AbstractAssumptions;
import org.matheclipse.core.form.output.OutputFormFactory;
import org.matheclipse.core.generic.Functors;
import org.matheclipse.core.generic.IsUnaryVariableOrPattern;
import org.matheclipse.core.generic.Predicates;
import org.matheclipse.core.generic.UnaryVariable2Slot;
import org.matheclipse.core.generic.interfaces.BiFunction;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IComplex;
import org.matheclipse.core.interfaces.IComplexNum;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IFraction;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.INum;
import org.matheclipse.core.interfaces.INumber;
import org.matheclipse.core.interfaces.IPattern;
import org.matheclipse.core.interfaces.IPatternObject;
import org.matheclipse.core.interfaces.IRational;
import org.matheclipse.core.interfaces.ISignedNumber;
import org.matheclipse.core.interfaces.IStringX;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.patternmatching.PatternMatcher;
import org.matheclipse.core.polynomials.ExprPolynomialRing;
import org.matheclipse.core.visit.IVisitor;
import org.matheclipse.core.visit.IVisitorBoolean;
import org.matheclipse.core.visit.IVisitorInt;
import org.matheclipse.core.visit.IVisitorLong;
import org.matheclipse.core.visit.VisitorReplaceAll;
import org.matheclipse.core.visit.VisitorReplacePart;
import org.matheclipse.core.visit.VisitorReplaceSlots;

public abstract class AbstractAST extends AbstractList<IExpr> implements IAST {
    private static final long serialVersionUID = -8682706994448890660L;
    protected int fEvalFlags;
    protected transient int hashValue;

    private static void internalFormOrderless(IAST ast, StringBuffer text, String sep, boolean symbolsAsFactoryMethod, int depth, boolean useOperators) {
        int i = 1;
        while (i < ast.size()) {
            if ((ast.get(i) instanceof IAST) && ast.head().equals(((IExpr) ast.get(i)).head())) {
                internalFormOrderless((IAST) ast.get(i), text, sep, symbolsAsFactoryMethod, depth, useOperators);
            } else {
                text.append(((IExpr) ast.get(i)).internalJavaString(symbolsAsFactoryMethod, depth + 1, useOperators));
            }
            if (i < ast.size() - 1) {
                text.append(sep);
            }
            i++;
        }
    }

    private static IExpr variables2Slots(IExpr expr, Predicate<IExpr> from, Function<IExpr, ? extends IExpr> to) {
        if (from.apply(expr)) {
            return (IExpr) to.apply(expr);
        }
        if (!expr.isAST()) {
            return expr;
        }
        IAST nestedList = (IAST) expr;
        IExpr temp = variables2Slots(nestedList.head(), from, to);
        if (temp == null) {
            return null;
        }
        IAST result = nestedList.apply(temp);
        int size = nestedList.size();
        for (int i = 1; i < size; i++) {
            temp = variables2Slots((IExpr) nestedList.get(i), from, to);
            if (temp == null) {
                return null;
            }
            result.set(i, temp);
        }
        return result;
    }

    public AbstractAST() {
        this.fEvalFlags = 0;
        this.hashValue = 0;
    }

    public IExpr abs() {
        throw new UnsupportedOperationException();
    }

    public final <T> T accept(IVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public final boolean accept(IVisitorBoolean visitor) {
        return visitor.visit(this);
    }

    public final int accept(IVisitorInt visitor) {
        return visitor.visit(this);
    }

    public long accept(IVisitorLong visitor) {
        return visitor.visit(this);
    }

    public IAST addAtClone(int position, IExpr expr) {
        IAST ast = clone();
        ast.add(position, expr);
        return ast;
    }

    public final void addEvalFlags(int i) {
        this.fEvalFlags |= i;
    }

    public IExpr and(IExpr that) {
        return F.And(this, that);
    }

    public IAST appendClone(IExpr expr) {
        IAST ast = clone();
        ast.add(expr);
        return ast;
    }

    public IAST apply(IExpr head) {
        return setAtClone(0, head);
    }

    public IExpr apply(IExpr... leaves) {
        IAST ast = F.ast(head());
        Collections.addAll(ast, leaves);
        return ast;
    }

    public final IAST apply(IExpr head, int start) {
        return apply(head, start, size());
    }

    public IAST apply(IExpr head, int start, int end) {
        IAST ast = F.ast(head);
        for (int i = start; i < end; i++) {
            ast.add(get(i));
        }
        return ast;
    }

    public IExpr apply(List<? extends IExpr> leaves) {
        IAST ast = F.ast(head());
        addAll(leaves);
        return ast;
    }

    public final ASTRange args() {
        return new ASTRange(this, 1);
    }

    public Object asType(Class<?> clazz) {
        IExpr temp;
        if (clazz.equals(Boolean.class)) {
            temp = F.eval(this);
            if (temp.equals(F.True)) {
                return Boolean.TRUE;
            }
            if (temp.equals(F.False)) {
                return Boolean.FALSE;
            }
        } else if (clazz.equals(Integer.class)) {
            if (F.eval(this).isSignedNumber()) {
                try {
                    return Integer.valueOf(((ISignedNumber) this).toInt());
                } catch (ArithmeticException e) {
                }
            }
        } else if (clazz.equals(BigInteger.class)) {
            temp = F.eval(this);
            if (temp instanceof IntegerSym) {
                return new BigInteger(((IntegerSym) temp).toByteArray());
            }
        } else if (clazz.equals(String.class)) {
            return toString();
        }
        throw new UnsupportedOperationException("AST.asType() - cast not supported.");
    }

    public IExpr copy() {
        return clone();
    }

    public IAST copyFrom(int index) {
        AST result = new AST((size() - index) + 1, false);
        result.add(head());
        result.addAll(this, index, size());
        return result;
    }

    public final IAST copyHead() {
        return AST.newInstance(head());
    }

    public final IAST copyUntil(int index) {
        return AST.newInstance(this, index);
    }

    public IAST clone() {
        AbstractAST ast = null;
        try {
            ast = (AbstractAST) super.clone();
            ast.fEvalFlags = 0;
            return ast;
        } catch (CloneNotSupportedException e) {
            return ast;
        }
    }

    public int compareTo(IExpr rhsExpr) {
        if (isAST(F.DirectedInfinity)) {
            if (rhsExpr.isAST(F.DirectedInfinity)) {
                return compareToAST(this, (IAST) rhsExpr);
            }
            return -1;
        } else if (rhsExpr.isAST(F.DirectedInfinity)) {
            return 1;
        } else {
            if (rhsExpr.isAST()) {
                if (isTimes()) {
                    return compareToTimes(this, (IAST) rhsExpr);
                }
                if (rhsExpr.isTimes()) {
                    return compareToTimes((IAST) rhsExpr, this) * -1;
                }
                return compareToAST(this, (IAST) rhsExpr);
            } else if (rhsExpr instanceof Symbol) {
                return ((Symbol) rhsExpr).compareTo(this) * -1;
            } else {
                int x = hierarchy();
                int y = rhsExpr.hierarchy();
                if (x >= y) {
                    return x == y ? 0 : 1;
                } else {
                    return -1;
                }
            }
        }
    }

    private static int compareToAST(IAST lhaAST, IAST rhsAST) {
        if (lhaAST.isPlusTimesPower()) {
            if (!rhsAST.isPlusTimesPower()) {
                return -1;
            }
        } else if (rhsAST.isPlusTimesPower()) {
            return 1;
        }
        int cp = lhaAST.head().compareTo(rhsAST.head());
        if (cp != 0) {
            return cp;
        }
        int commonArgSize = lhaAST.size() > rhsAST.size() ? rhsAST.size() : lhaAST.size();
        for (int i = 1; i < commonArgSize; i++) {
            cp = ((IExpr) lhaAST.get(i)).compareTo((IExpr) rhsAST.get(i));
            if (cp != 0) {
                return cp;
            }
        }
        return lhaAST.size() - rhsAST.size();
    }

    private static int compareToTimes(IAST lhsAST, IAST rhsAST) {
        int cp;
        if (rhsAST.isPower()) {
            IExpr lastTimes = (IExpr) lhsAST.get(lhsAST.size() - 1);
            if (!(lastTimes instanceof IAST)) {
                cp = lastTimes.compareTo(rhsAST.arg1());
                if (cp != 0) {
                    return cp;
                }
                return F.C1.compareTo(rhsAST.arg2());
            } else if (!lastTimes.isPower()) {
                return compareToAST(lhsAST, rhsAST);
            } else {
                cp = ((IAST) lastTimes).arg1().compareTo(rhsAST.arg1());
                if (cp != 0) {
                    return cp;
                }
                cp = ((IAST) lastTimes).arg2().compareTo(rhsAST.arg2());
                if (cp == 0) {
                    return 1;
                }
                return cp;
            }
        } else if (!rhsAST.isTimes()) {
            return compareToAST(lhsAST, rhsAST);
        } else {
            int commonArgCounter;
            int i0 = lhsAST.size();
            int i1 = rhsAST.size();
            if (i0 > i1) {
                commonArgCounter = i1;
            } else {
                commonArgCounter = i0;
            }
            do {
                commonArgCounter--;
                if (commonArgCounter <= 0) {
                    return lhsAST.size() - rhsAST.size();
                }
                i0--;
                i1--;
                cp = ((IExpr) lhsAST.get(i0)).compareTo((IExpr) rhsAST.get(i1));
            } while (cp == 0);
            return cp;
        }
    }

    public boolean contains(Object object) {
        int size = size();
        for (int i = 0; i < size; i++) {
            if (object.equals(get(i))) {
                return true;
            }
        }
        return false;
    }

    public final IExpr divide(IExpr that) {
        if (that.isNumber()) {
            return F.eval(F.Times(this, that.inverse()));
        }
        if (that.isOne()) {
            return this;
        }
        if (that.isMinusOne()) {
            return negate();
        }
        return F.eval(F.Times(this, F.Power(that, F.CN1)));
    }

    public final IExpr[] egcd(IExpr b) {
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractAST) || hashCode() != obj.hashCode()) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        IAST list = (IAST) obj;
        if (size() != list.size()) {
            return false;
        }
        int size = size();
        for (int i = 0; i < size; i++) {
            if (!get(i).equals(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    public final boolean equalsAt(int position, IExpr expr) {
        return get(position).equals(expr);
    }

    public final boolean equalsFromPosition(int from0, IAST f1, int from1) {
        if (size() - from0 != f1.size() - from1) {
            return false;
        }
        int j = from1;
        int i = from0;
        while (i < size() - 1) {
            int j2 = j + 1;
            if (!get(i + 1).equals(f1.get(j + 1))) {
                return false;
            }
            i++;
            j = j2;
        }
        return true;
    }

    public final double evalDouble() {
        ISignedNumber signedNumber = evalSignedNumber();
        if (signedNumber != null) {
            return signedNumber.doubleValue();
        }
        throw new WrongArgumentType(this, "Conversion into a double numeric value is not possible!");
    }

    public final Complex evalComplex() {
        INumber number = evalNumber();
        if (number != null) {
            return number.complexNumValue().complexValue();
        }
        throw new WrongArgumentType(this, "Conversion into a complex numeric value is not possible!");
    }

    public final INumber evalNumber() {
        if (isNumericFunction()) {
            IExpr result = F.evaln(this);
            if (result.isNumber()) {
                return (INumber) result;
            }
        }
        return null;
    }

    public final ISignedNumber evalSignedNumber() {
        if (isNumericFunction()) {
            IExpr result = F.evaln(this);
            if (result.isSignedNumber()) {
                return (ISignedNumber) result;
            }
        }
        return null;
    }

    public final IExpr evaluate(EvalEngine engine) {
        IExpr temp = engine.evalAST(this);
        if ((topHead().getAttributes() & StaticSettings.MAX_FILE_BUFFER_SIZE) == StaticSettings.MAX_FILE_BUFFER_SIZE && temp != null) {
            System.out.println(toString());
            System.out.println(" => " + temp.toString());
        }
        return temp;
    }

    public final ElemFactory<IExpr> factory() {
        throw new UnsupportedOperationException();
    }

    public final IAST[] filter(Function<IExpr, IExpr> function) {
        IAST[] result = new IAST[]{copyHead(), copyHead()};
        filter(result[0], result[1], (Function) function);
        return result;
    }

    public final IAST filter(IAST filterAST, IAST restAST, Function<IExpr, IExpr> function) {
        int size = size();
        for (int i = 1; i < size; i++) {
            IExpr expr = (IExpr) function.apply(get(i));
            if (expr != null) {
                filterAST.add(expr);
            } else {
                restAST.add(get(i));
            }
        }
        return filterAST;
    }

    public final IAST filter(IAST filterAST, IAST restAST, Predicate<IExpr> predicate) {
        int size = size();
        for (int i = 1; i < size; i++) {
            if (predicate.apply(get(i))) {
                filterAST.add(get(i));
            } else {
                restAST.add(get(i));
            }
        }
        return filterAST;
    }

    public final IAST filter(IAST filterAST, IExpr expr) {
        return filter(filterAST, Predicates.isTrue(expr));
    }

    public final IAST filter(IAST filterAST, Predicate<IExpr> predicate) {
        int size = size();
        for (int i = 1; i < size; i++) {
            if (predicate.apply(get(i))) {
                filterAST.add(get(i));
            }
        }
        return filterAST;
    }

    public final IAST filter(IAST filterAST, Predicate<IExpr> predicate, int maxMatches) {
        int count = 0;
        if (0 < maxMatches) {
            int size = size();
            for (int i = 1; i < size; i++) {
                if (predicate.apply(get(i))) {
                    count++;
                    if (count == maxMatches) {
                        filterAST.add(get(i));
                        break;
                    }
                    filterAST.add(get(i));
                }
            }
        }
        return filterAST;
    }

    public final IAST[] filter(Predicate<IExpr> predicate) {
        IAST[] result = new IAST[]{copyHead(), copyHead()};
        filter(result[0], result[1], (Predicate) predicate);
        return result;
    }

    public final int findFirstEquals(IExpr expr) {
        for (int i = 1; i < size(); i++) {
            if (equalsAt(i, expr)) {
                return i;
            }
        }
        return -1;
    }

    public final String fullFormString() {
        String sep = ", ";
        IExpr temp = head();
        StringBuffer text = new StringBuffer();
        if (temp == null) {
            text.append("<null-head>");
        } else {
            text.append(temp.fullFormString());
        }
        if (Config.PARSER_USE_LOWERCASE_SYMBOLS) {
            text.append(Constants.LEFT_PAREN);
        } else {
            text.append('[');
        }
        for (int i = 1; i < size(); i++) {
            if (get(i) == null) {
                text.append("<null-arg>");
            } else {
                text.append(get(i).fullFormString());
                if (i < size() - 1) {
                    text.append(", ");
                }
            }
        }
        if (Config.PARSER_USE_LOWERCASE_SYMBOLS) {
            text.append(Constants.RIGHT_PAREN);
        } else {
            text.append(']');
        }
        return text.toString();
    }

    public final IExpr gcd(IExpr that) {
        return equals(that) ? that : F.C1;
    }

    public IExpr get(int location) {
        throw new UnsupportedOperationException();
    }

    public final IAST getAST(int index) {
        try {
            return (IAST) get(index);
        } catch (ClassCastException e) {
            throw new WrongArgumentType(this, get(index), index);
        }
    }

    public final IExpr getAt(int index) {
        return get(index);
    }

    public final int getEvalFlags() {
        return this.fEvalFlags;
    }

    public final IInteger getInt(int index) {
        try {
            return (IInteger) get(index);
        } catch (ClassCastException e) {
            throw new WrongArgumentType(this, get(index), index);
        }
    }

    public final IAST getList(int index) {
        IExpr temp = get(index);
        if (temp.isList()) {
            return (IAST) temp;
        }
        throw new WrongArgumentType(this, temp, index);
    }

    public final INumber getNumber(int index) {
        try {
            return (INumber) get(index);
        } catch (ClassCastException e) {
            throw new WrongArgumentType(this, get(index), index);
        }
    }

    public final IExpr getOneIdentity(IExpr defaultValue) {
        if (size() > 2) {
            return this;
        }
        return size() == 2 ? arg1() : defaultValue;
    }

    public final IExpr getPart(int... positions) {
        IExpr expr = this;
        int size = positions.length;
        for (int i = 0; i < size && expr.isAST(); i++) {
            expr = (IExpr) ((IAST) expr).get(positions[i]);
            if (i == size - 1) {
                return expr;
            }
        }
        return null;
    }

    public final IExpr getPart(List<Integer> positions) {
        IExpr expr = this;
        int size = positions.size();
        for (int i = 0; i < size && expr.isAST(); i++) {
            expr = (IExpr) ((IAST) expr).get(((Integer) positions.get(i)).intValue());
            if (i == size - 1) {
                return expr;
            }
        }
        return null;
    }

    public int hashCode() {
        if (this.hashValue == 0) {
            this.hashValue = 17;
            int size = size();
            for (int i = 0; i < size; i++) {
                this.hashValue = (this.hashValue * 23) + get(i).hashCode();
            }
        }
        return this.hashValue;
    }

    public final int hierarchy() {
        return IExpr.ASTID;
    }

    public final int indexOf(Object object) {
        int size = size();
        for (int i = 0; i < size; i++) {
            if (object.equals(get(i))) {
                return i;
            }
        }
        return -1;
    }

    public final String internalFormString(boolean symbolsAsFactoryMethod, int depth) {
        return internalJavaString(symbolsAsFactoryMethod, depth, false);
    }

    public final String internalScalaString(boolean symbolsAsFactoryMethod, int depth) {
        return internalJavaString(symbolsAsFactoryMethod, depth, true);
    }

    public final String internalJavaString(boolean symbolsAsFactoryMethod, int depth, boolean useOperators) {
        String sep = ",";
        IExpr temp = head();
        if (temp.equals(F.Hold) && size() == 2) {
            return arg1().internalFormString(symbolsAsFactoryMethod, depth);
        }
        if (isInfinity()) {
            return "CInfinity";
        }
        if (isNegativeInfinity()) {
            return "CNInfinity";
        }
        if (isComplexInfinity()) {
            return "CComplexInfinity";
        }
        if (equals(F.Slot1)) {
            return "Slot1";
        }
        if (equals(F.Slot2)) {
            return "Slot2";
        }
        int i;
        if (isPower()) {
            IInteger i2;
            if (equalsAt(2, F.C1D2)) {
                if (arg1().isInteger()) {
                    i2 = (IInteger) arg1();
                    if (i2.equals(F.C2)) {
                        return "CSqrt2";
                    }
                    if (i2.equals(F.C3)) {
                        return "CSqrt3";
                    }
                    if (i2.equals(F.C5)) {
                        return "CSqrt5";
                    }
                    if (i2.equals(F.C6)) {
                        return "CSqrt6";
                    }
                    if (i2.equals(F.C7)) {
                        return "CSqrt7";
                    }
                    if (i2.equals(F.C10)) {
                        return "CSqrt10";
                    }
                }
                return "Sqrt(" + arg1().internalJavaString(symbolsAsFactoryMethod, depth + 1, useOperators) + ")";
            } else if (equalsAt(2, F.C2)) {
                return "Sqr(" + arg1().internalJavaString(symbolsAsFactoryMethod, depth + 1, useOperators) + ")";
            } else {
                if (equalsAt(2, F.CN1D2) && arg1().isInteger()) {
                    i2 = (IInteger) arg1();
                    if (i2.equals(F.C2)) {
                        return "C1DSqrt2";
                    }
                    if (i2.equals(F.C3)) {
                        return "C1DSqrt3";
                    }
                    if (i2.equals(F.C5)) {
                        return "C1DSqrt5";
                    }
                    if (i2.equals(F.C6)) {
                        return "C1DSqrt6";
                    }
                    if (i2.equals(F.C7)) {
                        return "C1DSqrt7";
                    }
                    if (i2.equals(F.C10)) {
                        return "C1DSqrt10";
                    }
                }
                if (arg2().isInteger()) {
                    try {
                        return "Power(" + arg1().internalJavaString(symbolsAsFactoryMethod, depth + 1, useOperators) + "," + Long.toString(((IInteger) arg2()).toLong()) + ")";
                    } catch (Exception e) {
                    }
                }
            }
        }
        StringBuffer text = new StringBuffer(size() * 10);
        if (temp.isSymbol()) {
            ISymbol sym = (ISymbol) temp;
            String name = null;
            if (Config.PARSER_USE_LOWERCASE_SYMBOLS) {
                name = sym.toString();
                if (name.length() > 0) {
                    name = name.toLowerCase(Locale.ENGLISH);
                }
                name = (String) AST2Expr.PREDEFINED_SYMBOLS_MAP.get(name);
            }
            if (name == null && !Character.isUpperCase(sym.toString().charAt(0))) {
                text.append("$(");
                for (i = 0; i < size(); i++) {
                    text.append(get(i).internalJavaString(symbolsAsFactoryMethod, depth + 1, useOperators));
                    if (i < size() - 1) {
                        text.append(",");
                    }
                }
                text.append(Constants.RIGHT_PAREN);
                return text.toString();
            }
        } else if (temp.isPattern() || temp.isAST()) {
            text.append("$(");
            for (i = 0; i < size(); i++) {
                text.append(get(i).internalJavaString(symbolsAsFactoryMethod, depth + 1, useOperators));
                if (i < size() - 1) {
                    text.append(",");
                }
            }
            text.append(Constants.RIGHT_PAREN);
            return text.toString();
        }
        if (isTimes() && size() == 3 && arg1().isMinusOne() && !arg2().isTimes()) {
            return "Negate(" + arg2().internalJavaString(symbolsAsFactoryMethod, depth + 1, useOperators) + ")";
        }
        if (useOperators && size() == 3) {
            IExpr arg1;
            IExpr arg2;
            if (isTimes()) {
                arg1 = arg1();
                arg2 = arg2();
                internalOperatorForm(arg1, arg1.isPlus(), symbolsAsFactoryMethod, depth, useOperators, text);
                text.append(Constants.MUL_UNICODE);
                internalOperatorForm(arg2, arg2.isPlus(), symbolsAsFactoryMethod, depth, useOperators, text);
                return text.toString();
            } else if (isPlus()) {
                arg1().internalJavaString(symbolsAsFactoryMethod, depth + 1, useOperators);
                text.append(Constants.PLUS_UNICODE);
                arg2().internalJavaString(symbolsAsFactoryMethod, depth + 1, useOperators);
                return text.toString();
            } else if (isPower()) {
                arg1 = arg1();
                arg2 = arg2();
                boolean isLowerPrecedence = arg1.isTimes() || arg1.isPlus();
                internalOperatorForm(arg1, isLowerPrecedence, symbolsAsFactoryMethod, depth, useOperators, text);
                text.append(Constants.POWER_UNICODE);
                isLowerPrecedence = arg2.isTimes() || arg2.isPlus();
                internalOperatorForm(arg2, isLowerPrecedence, symbolsAsFactoryMethod, depth, useOperators, text);
                return text.toString();
            }
        }
        text.append(temp.internalJavaString(false, 0, useOperators));
        text.append(Constants.LEFT_PAREN);
        if (isTimes() || isPlus()) {
            if (depth == 0 && isList()) {
                text.append('\n');
            }
            internalFormOrderless(this, text, ",", symbolsAsFactoryMethod, depth, useOperators);
            if (depth == 0 && isList()) {
                text.append('\n');
            }
        } else {
            if (depth == 0 && isList()) {
                text.append('\n');
            }
            for (i = 1; i < size(); i++) {
                text.append(get(i).internalJavaString(symbolsAsFactoryMethod, depth + 1, useOperators));
                if (i < size() - 1) {
                    text.append(",");
                    if (depth == 0 && isList()) {
                        text.append('\n');
                    }
                }
            }
            if (depth == 0 && isList()) {
                text.append('\n');
            }
        }
        text.append(Constants.RIGHT_PAREN);
        return text.toString();
    }

    private void internalOperatorForm(IExpr arg1, boolean isLowerPrecedence, boolean symbolsAsFactoryMethod, int depth, boolean useOperators, StringBuffer text) {
        if (isLowerPrecedence) {
            text.append("(");
        }
        text.append(arg1.internalJavaString(symbolsAsFactoryMethod, depth + 1, useOperators));
        if (isLowerPrecedence) {
            text.append(")");
        }
    }

    public IExpr inverse() {
        return F.eval(F.Power(this, F.CN1));
    }

    public boolean isAllExpanded() {
        if (isEvalFlagOff(AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD)) {
            return false;
        }
        return true;
    }

    public final boolean isAnd() {
        return isSameHeadSizeGE(F.And, 3);
    }

    public final boolean isArcCos() {
        return isSameHead(F.ArcCos, 2);
    }

    public final boolean isArcCosh() {
        return isSameHead(F.ArcCosh, 2);
    }

    public final boolean isArcSin() {
        return isSameHead(F.ArcSin, 2);
    }

    public final boolean isArcSinh() {
        return isSameHead(F.ArcSinh, 2);
    }

    public final boolean isArcTan() {
        return isSameHead(F.ArcTan, 2);
    }

    public final boolean isArcTanh() {
        return isSameHead(F.ArcTanh, 2);
    }

    public final boolean isAST() {
        return true;
    }

    public final boolean isAST(IExpr header) {
        return isSameHead(header);
    }

    public final boolean isAST(IExpr header, int length) {
        return isSameHead(header, length);
    }

    public boolean isAST(IExpr header, int length, IExpr... args) {
        if (!isSameHead(header, length)) {
            return false;
        }
        int i = 0;
        while (i < args.length) {
            if (args[i] != null && !get(i + 1).equals(args[i])) {
                return false;
            }
            i++;
        }
        return true;
    }

    public final boolean isAST(IExpr header, int minLength, int maxLength) {
        return isSameHead(header, minLength, maxLength);
    }

    public final boolean isAST(String symbol) {
        if (!Config.PARSER_USE_LOWERCASE_SYMBOLS) {
            return head().toString().equals(symbol);
        }
        String name = symbol;
        if (name.length() > 0) {
            name = symbol.toLowerCase(Locale.ENGLISH);
        }
        return head().toString().equals(name);
    }

    public final boolean isAST(String symbol, int length) {
        if (Config.PARSER_USE_LOWERCASE_SYMBOLS) {
            String name = symbol;
            if (name.length() > 0) {
                name = symbol.toLowerCase(Locale.ENGLISH);
            }
            if (size() == length && head().toString().equals(name)) {
                return true;
            }
            return false;
        } else if (size() == length && head().toString().equals(symbol)) {
            return true;
        } else {
            return false;
        }
    }

    public final boolean isASTSizeGE(IExpr header, int length) {
        return isSameHeadSizeGE(header, length);
    }

    public final boolean isAtom() {
        return false;
    }

    public boolean isBlank() {
        return false;
    }

    public final boolean isComplex() {
        return false;
    }

    public final boolean isComplexInfinity() {
        return isSameHead(F.DirectedInfinity, 1);
    }

    public final boolean isComplexNumeric() {
        return false;
    }

    public final boolean isCondition() {
        return isSameHead(F.Condition, 3);
    }

    public boolean isConstant() {
        return false;
    }

    public final boolean isCos() {
        return isSameHead(F.Cos, 2);
    }

    public final boolean isCosh() {
        return isSameHead(F.Cosh, 2);
    }

    public final IAST[] isDerivative() {
        if (head().isAST()) {
            IAST headAST = (IAST) head();
            if (headAST.isAST(F.Derivative, 2)) {
                IAST[] result = new IAST[3];
                result[0] = headAST;
                result[1] = this;
                return result;
            } else if (headAST.head().isAST(F.Derivative, 2)) {
                return new IAST[]{(IAST) headAST.head(), headAST, this};
            }
        }
        return null;
    }

    public final boolean isDirectedInfinity() {
        return isSameHead(F.DirectedInfinity, 1, 2);
    }

    public final boolean isE() {
        return false;
    }

    public final boolean isEmpty() {
        return size() == 0;
    }

    public final boolean isEvalFlagOff(int i) {
        return (this.fEvalFlags & i) == 0;
    }

    public final boolean isEvalFlagOn(int i) {
        return (this.fEvalFlags & i) == i;
    }

    public final boolean isExpanded() {
        if (isPlusTimesPower() && isEvalFlagOff(StaticSettings.MAX_FILE_BUFFER_SIZE)) {
            return false;
        }
        return true;
    }

    public final boolean isFalse() {
        return false;
    }

    public final boolean isFlatAST() {
        return (topHead().getAttributes() & 8) == 8;
    }

    public final boolean isFraction() {
        return false;
    }

    public final boolean isFree(IExpr pattern) {
        return isFree(pattern, true);
    }

    public final boolean isFree(IExpr pattern, boolean heads) {
        return !isMember(new PatternMatcher(pattern), heads);
    }

    public final boolean isFree(Predicate<IExpr> predicate, boolean heads) {
        return !isMember((Predicate) predicate, heads);
    }

    public final boolean isFreeAST(IExpr pattern) {
        return isFreeAST(new PatternMatcher(pattern));
    }

    public final boolean isFreeAST(Predicate<IExpr> predicate) {
        if (predicate.apply(head())) {
            return false;
        }
        int i = 1;
        while (i < size()) {
            if (get(i).isAST() && !get(i).isFreeAST((Predicate) predicate)) {
                return false;
            }
            i++;
        }
        return true;
    }

    public final boolean isFreeAt(int position, IExpr pattern) {
        return get(position).isFree(pattern, true);
    }

    public final boolean isFreeOfPatterns() {
        if ((getEvalFlags() & 8) == 8) {
            return true;
        }
        if ((getEvalFlags() & 7) != 0) {
            return false;
        }
        boolean isFreeOfPatterns = true;
        for (int i = 0; i < size(); i++) {
            IExpr temp = get(i);
            if (temp.isAST() && !temp.isFreeOfPatterns()) {
                isFreeOfPatterns = false;
                addEvalFlags(((IAST) temp).getEvalFlags() & 7);
            } else if (temp instanceof IPatternObject) {
                isFreeOfPatterns = false;
                addEvalFlags(getEvalFlags());
            }
        }
        if (!isFreeOfPatterns) {
            return isFreeOfPatterns;
        }
        addEvalFlags(8);
        return isFreeOfPatterns;
    }

    public final boolean isFunction() {
        return size() >= 2 && head().equals(F.Function);
    }

    public final boolean isGEOrdered(IExpr obj) {
        return compareTo(obj) >= 0;
    }

    public final boolean isGTOrdered(IExpr obj) {
        return compareTo(obj) > 0;
    }

    public final boolean isIndeterminate() {
        return false;
    }

    public final boolean isInfinity() {
        return equals(F.CInfinity);
    }

    public final boolean isInteger() {
        return false;
    }

    public final boolean isIntegerResult() {
        ISymbol symbol = topHead();
        if (symbol.equals(F.Floor) || symbol.equals(F.Ceiling) || symbol.equals(F.IntegerPart)) {
            return true;
        }
        if (isPower() && arg2().isInteger() && arg2().isPositive()) {
            if (arg1().isIntegerResult()) {
                return true;
            }
            return false;
        } else if (!isPlus() && !isTimes() && !symbol.equals(F.Binomial) && !symbol.equals(F.Factorial)) {
            return false;
        } else {
            for (int i = 1; i < size(); i++) {
                if (!get(i).isIntegerResult()) {
                    return false;
                }
            }
            return true;
        }
    }

    public final boolean isRationalResult() {
        ISymbol symbol = topHead();
        if (symbol.equals(F.Floor) || symbol.equals(F.Ceiling) || symbol.equals(F.IntegerPart)) {
            return true;
        }
        if (isPower() && arg2().isInteger() && arg2().isPositive()) {
            if (arg1().isRationalResult()) {
                return true;
            }
            return false;
        } else if (!isPlus() && !isTimes() && !symbol.equals(F.Binomial) && !symbol.equals(F.Factorial)) {
            return false;
        } else {
            for (int i = 1; i < size(); i++) {
                if (!get(i).isRationalResult()) {
                    return false;
                }
            }
            return true;
        }
    }

    public final boolean isLEOrdered(IExpr obj) {
        return compareTo(obj) <= 0;
    }

    public final boolean isList() {
        return isSameHeadSizeGE(F.List, 1);
    }

    public final boolean isListOfLists() {
        if (!head().equals(F.List)) {
            return false;
        }
        for (int i = 1; i < size(); i++) {
            if (!get(i).isList()) {
                return false;
            }
        }
        return true;
    }

    public final boolean isLog() {
        return isSameHead(F.Log, 2);
    }

    public final boolean isLTOrdered(IExpr obj) {
        return compareTo(obj) < 0;
    }

    public final int[] isMatrix() {
        if (isEvalFlagOn(32)) {
            return new int[]{size() - 1, ((IAST) arg1()).size() - 1};
        }
        if (head().equals(F.List)) {
            int[] dim = new int[]{size() - 1, 0};
            if (dim[0] > 0) {
                if (!arg1().isList()) {
                    return null;
                }
                dim[1] = ((IAST) arg1()).size() - 1;
                for (int i = 2; i < size(); i++) {
                    if (!get(i).isList()) {
                        return null;
                    }
                    if (dim[1] != ((IAST) get(i)).size() - 1) {
                        return null;
                    }
                }
                addEvalFlags(32);
                return dim;
            }
        }
        return null;
    }

    public final boolean isMember(IExpr pattern, boolean heads) {
        return isMember(new PatternMatcher(pattern), heads);
    }

    public final boolean isMember(Predicate<IExpr> predicate, boolean heads) {
        if (predicate.apply(this)) {
            return true;
        }
        int start = 1;
        if (heads) {
            start = 0;
        }
        for (int i = start; i < size(); i++) {
            if (get(i).isMember((Predicate) predicate, heads)) {
                return true;
            }
        }
        return false;
    }

    public final boolean isMinusOne() {
        return false;
    }

    public final boolean isModule() {
        return size() == 3 && head().equals(F.Module);
    }

    public final boolean isNegative() {
        if (isNumericFunction()) {
            IExpr result = F.evaln(this);
            if (result.isSignedNumber()) {
                return ((ISignedNumber) result).isNegative();
            }
        }
        return false;
    }

    public final boolean isNegativeInfinity() {
        return equals(F.CNInfinity);
    }

    public final boolean isNegativeResult() {
        int i;
        if (isPlus()) {
            i = 1;
            while (i < size()) {
                if (!get(i).isNegativeResult() && !AbstractAssumptions.assumeNegative(get(i))) {
                    return false;
                }
                i++;
            }
            return true;
        } else if (!isTimes()) {
            return false;
        } else {
            boolean flag = false;
            i = 1;
            while (i < size()) {
                if (!(get(i).isNonNegativeResult() || AbstractAssumptions.assumeNonNegative(get(i)))) {
                    if (get(i).isNegativeResult()) {
                        if (flag) {
                            flag = false;
                        } else {
                            flag = true;
                        }
                    } else if (!AbstractAssumptions.assumeNegative(get(i))) {
                        return false;
                    } else {
                        if (flag) {
                            flag = false;
                        } else {
                            flag = true;
                        }
                    }
                }
                i++;
            }
            return flag;
        }
    }

    public final boolean isNonNegativeResult() {
        if (topHead().equals(F.Abs)) {
            return true;
        }
        int i;
        if (isPlus()) {
            for (i = 1; i < size(); i++) {
                if (!get(i).isNonNegativeResult()) {
                    if (!AbstractAssumptions.assumeNonNegative(get(i))) {
                        return false;
                    }
                }
            }
            return true;
        } else if (!isTimes()) {
            return false;
        } else {
            boolean flag = true;
            i = 1;
            while (i < size()) {
                if (!(get(i).isNonNegativeResult() || AbstractAssumptions.assumeNonNegative(get(i)))) {
                    if (get(i).isNegativeResult()) {
                        if (flag) {
                            flag = false;
                        } else {
                            flag = true;
                        }
                    } else if (!AbstractAssumptions.assumeNegative(get(i))) {
                        return false;
                    } else {
                        if (flag) {
                            flag = false;
                        } else {
                            flag = true;
                        }
                    }
                }
                i++;
            }
            return flag;
        }
    }

    public final boolean isNot() {
        return size() == 2 && head().equals(F.Not);
    }

    public final boolean isNumber() {
        return false;
    }

    public final boolean isNumEqualInteger(IInteger ii) throws ArithmeticException {
        return false;
    }

    public final boolean isNumEqualRational(IRational value) throws ArithmeticException {
        return false;
    }

    public final boolean isNumeric() {
        return false;
    }

    public final boolean isNumericFunction() {
        if ((topHead().getAttributes() & IExpr.ASTID) != IExpr.ASTID) {
            return false;
        }
        for (int i = 1; i < size(); i++) {
            if (!get(i).isNumericFunction()) {
                return false;
            }
        }
        return true;
    }

    public final boolean isNumericMode() {
        if ((topHead().getAttributes() & IExpr.ASTID) == IExpr.ASTID) {
            for (int i = 1; i < size(); i++) {
                if (get(i).isNumericMode()) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean isNumIntValue() {
        return false;
    }

    public final boolean isOne() {
        return false;
    }

    public final boolean isONE() {
        return isOne();
    }

    public final boolean isOr() {
        return isSameHeadSizeGE(F.Or, 3);
    }

    public final boolean isOrderlessAST() {
        return (topHead().getAttributes() & 4) == 4;
    }

    public final boolean isPattern() {
        return false;
    }

    public final boolean isPatternDefault() {
        return false;
    }

    public final boolean isPatternExpr() {
        return (this.fEvalFlags & 7) != 0;
    }

    public final boolean isPatternSequence() {
        return false;
    }

    public final boolean isPi() {
        return false;
    }

    public final boolean isPlus() {
        return isSameHeadSizeGE(F.Plus, 3);
    }

    public boolean isPlusTimesPower() {
        return isPlus() || isTimes() || isPower();
    }

    public final boolean isPolynomial(IAST variables) {
        if (!isPlus() && !isTimes() && !isPower()) {
            return false;
        }
        return new ExprPolynomialRing(variables).isPolynomial(F.evalExpandAll(this));
    }

    public final boolean isPolynomial(ISymbol variable) {
        return isPolynomial(F.List(variable));
    }

    public final boolean isPolynomialOfMaxDegree(IAST variables, long maxDegree) {
        try {
            if (!isPlus() && !isTimes() && !isPower()) {
                return false;
            }
            if (new ExprPolynomialRing(variables).create(F.evalExpandAll(this)).degree() <= maxDegree) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public final boolean isPolynomialOfMaxDegree(ISymbol variable, long maxDegree) {
        return isPolynomialOfMaxDegree(F.List(variable), maxDegree);
    }

    public final boolean isPositive() {
        if (isNumericFunction()) {
            IExpr result = F.evaln(this);
            if (result.isSignedNumber()) {
                return ((ISignedNumber) result).isPositive();
            }
        }
        return false;
    }

    public final boolean isPositiveResult() {
        int i;
        if (isPlus()) {
            i = 1;
            while (i < size()) {
                if (!get(i).isPositiveResult() && !AbstractAssumptions.assumePositive(get(i))) {
                    return false;
                }
                i++;
            }
            return true;
        } else if (!isTimes()) {
            return false;
        } else {
            boolean flag = true;
            i = 1;
            while (i < size()) {
                if (!(get(i).isPositiveResult() || AbstractAssumptions.assumePositive(get(i)))) {
                    if (get(i).isNegativeResult()) {
                        if (flag) {
                            flag = false;
                        } else {
                            flag = true;
                        }
                    } else if (!AbstractAssumptions.assumeNegative(get(i))) {
                        return false;
                    } else {
                        if (flag) {
                            flag = false;
                        } else {
                            flag = true;
                        }
                    }
                }
                i++;
            }
            return flag;
        }
    }

    public final boolean isPower() {
        return isSameHead(F.Power, 3);
    }

    public final boolean isRational() {
        return false;
    }

    public final boolean isRationalValue(IRational value) {
        return false;
    }

    public final boolean isRealResult() {
        IExpr head = head();
        if (size() == 2 && F.Cos.equals(head) && F.Sin.equals(head)) {
            return arg1().isRealResult();
        }
        if (isPlus() || isTimes()) {
            for (int i = 1; i < size(); i++) {
                if (!get(i).isRealResult()) {
                    return false;
                }
            }
            return true;
        } else if (!isPower()) {
            return false;
        } else {
            if ((!arg2().isZero() || !arg1().isZero()) && arg1().isRealResult() && arg2().isRealResult()) {
                return true;
            }
            return false;
        }
    }

    public final boolean isRuleAST() {
        return size() == 3 && (head().equals(F.Rule) || head().equals(F.RuleDelayed));
    }

    public final boolean isSame(IExpr expression) {
        return equals(expression);
    }

    public final boolean isSame(IExpr expression, double epsilon) {
        return equals(expression);
    }

    public boolean isSameHead(IExpr head) {
        return head().equals(head);
    }

    public boolean isSameHead(IExpr head, int length) {
        return head().equals(head) && length == size();
    }

    public boolean isSameHead(IExpr head, int minLength, int maxLength) {
        int size = size();
        return head().equals(head) && minLength <= size && maxLength >= size;
    }

    public boolean isSameHeadSizeGE(IExpr head, int length) {
        return head().equals(head) && length <= size();
    }

    public final boolean isSequence() {
        return isSameHeadSizeGE(F.Sequence, 1);
    }

    public final boolean isSignedNumber() {
        return false;
    }

    public final boolean isSin() {
        return isSameHead(F.Sin, 2);
    }

    public final boolean isSinh() {
        return isSameHead(F.Sinh, 2);
    }

    public final boolean isSlot() {
        return isSameHead(F.Slot, 2) && arg1().isInteger();
    }

    public final boolean isSlotSequence() {
        return isSameHead(F.SlotSequence, 2) && arg1().isInteger();
    }

    public final boolean isSymbol() {
        return false;
    }

    public final boolean isTan() {
        return isSameHead(F.Tan, 2);
    }

    public final boolean isTanh() {
        return isSameHead(F.Tanh, 2);
    }

    public final boolean isTimes() {
        return isSameHeadSizeGE(F.Times, 3);
    }

    public final boolean isTrue() {
        return false;
    }

    public final boolean isUnit() {
        if (isZero()) {
            return false;
        }
        if (isOne()) {
            return true;
        }
        if (isNumber()) {
            return true;
        }
        if (F.eval(F.Times(this, F.Power(this, F.CN1))).isOne()) {
            return true;
        }
        return false;
    }

    public final boolean isValue() {
        EvalEngine engine = EvalEngine.get();
        IExpr symbol = topHead();
        IExpr result = engine.evalAttributes(symbol, this);
        if (result != null) {
            if (!result.isAST(symbol)) {
                return false;
            }
            if (engine.evalRules(symbol, (IAST) result) != null) {
                return true;
            }
            return false;
        } else if (engine.evalRules(symbol, this) == null) {
            return false;
        } else {
            return true;
        }
    }

    public final boolean isVariable() {
        return false;
    }

    public final int isVector() {
        if (isEvalFlagOn(64)) {
            return size() - 1;
        }
        if (!head().equals(F.List)) {
            return -1;
        }
        int dim = size() - 1;
        if (dim > 0) {
            if (arg1().isList()) {
                return -1;
            }
            for (int i = 2; i < size(); i++) {
                if (get(i).isList()) {
                    return -1;
                }
            }
        }
        addEvalFlags(64);
        return dim;
    }

    public final boolean isZero() {
        return false;
    }

    public final boolean isZERO() {
        return isZero();
    }

    public final Iterator<IExpr> iterator() {
        ASTIterator i = new ASTIterator();
        ASTIterator.access$002(i, this);
        ASTIterator.access$102(i, 1);
        ASTIterator.access$202(i, size());
        ASTIterator.access$302(i, 1);
        ASTIterator.access$402(i, 0);
        return i;
    }

    public final Iterator<IExpr> iterator0() {
        return super.iterator();
    }

    public final IExpr last() {
        return get(size() - 1);
    }

    public final int lastIndexOf(Object object) {
        for (int i = size() - 1; i >= 0; i--) {
            if (object.equals(get(i))) {
                return i;
            }
        }
        return -1;
    }

    public final long leafCount() {
        return accept(new LeafCountVisitor(0));
    }

    public final List<IExpr> leaves() {
        int sz = size();
        if (sz < 2) {
            return Collections.EMPTY_LIST;
        }
        return subList(1, sz);
    }

    public final IAST map(Function<IExpr, IExpr> function) {
        return map(clone(), (Function) function);
    }

    public final IAST map(IAST clonedResultAST, Function<IExpr, IExpr> function) {
        for (int i = 1; i < size(); i++) {
            IExpr temp = (IExpr) function.apply(get(i));
            if (temp != null) {
                clonedResultAST.set(i, temp);
            }
        }
        return clonedResultAST;
    }

    public final IAST map(IAST resultAST, IAST secondAST, BiFunction<IExpr, IExpr, IExpr> function) {
        for (int i = 1; i < size(); i++) {
            resultAST.add(function.apply(get(i), secondAST.get(i)));
        }
        return resultAST;
    }

    public final IAST map(IExpr head, Function<IExpr, IExpr> function) {
        return map(setAtClone(0, head), (Function) function);
    }

    public final IAST mapAt(IAST appendAST, IAST replacement, int position) {
        Function<IExpr, IExpr> function = Functors.replaceArg(replacement, position);
        for (int i = 1; i < size(); i++) {
            IExpr temp = (IExpr) function.apply(get(i));
            if (temp != null) {
                appendAST.add(temp);
            }
        }
        return appendAST;
    }

    public final IAST mapAt(IAST replacement, int position) {
        return map(Functors.replaceArg(replacement, position));
    }

    public final IExpr minus(IExpr that) {
        return subtract(that);
    }

    public final IExpr mod(IExpr that) {
        return F.Mod(this, that);
    }

    public final IExpr multiply(IExpr that) {
        return times(that);
    }

    public final IExpr negate() {
        if (isTimes()) {
            IExpr arg1 = arg1();
            if (arg1.isNumber()) {
                return setAtClone(1, (IExpr) ((INumber) arg1).negate());
            }
            IExpr timesAST = clone();
            timesAST.add(1, F.CN1);
            return timesAST;
        } else if (isNegativeInfinity()) {
            return F.CInfinity;
        } else {
            if (isInfinity()) {
                return F.CNInfinity;
            }
            return F.eval(F.Times(F.CN1, this));
        }
    }

    public final IExpr negative() {
        return opposite();
    }

    public final IExpr opposite() {
        return negate();
    }

    public final IExpr optional(IExpr that) {
        return that != null ? that : this;
    }

    public final IExpr or(IExpr that) {
        return F.Or(this, that);
    }

    public final int patternHashCode() {
        if (size() > 1) {
            int attr = topHead().getAttributes() & 12;
            if (attr != 0) {
                if (attr == 12) {
                    return head().hashCode() * 17;
                }
                if (attr != 8) {
                    return (head().hashCode() * 17) + size();
                }
                if (!(arg1() instanceof IAST)) {
                    return (head().hashCode() * 37) + arg1().hashCode();
                }
                return ((IAST) arg1()).head().hashCode() + (head().hashCode() * 31);
            } else if (!(arg1() instanceof IAST)) {
                return ((head().hashCode() * 37) + arg1().hashCode()) + size();
            } else {
                return (((IAST) arg1()).head().hashCode() + (head().hashCode() * 31)) + size();
            }
        } else if (size() == 1) {
            return head().hashCode() * 17;
        } else {
            return 41;
        }
    }

    public final IExpr plus(IExpr that) {
        return that.isZero() ? this : F.eval(F.Plus(this, that));
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
        return F.Power(this, that);
    }

    public final IExpr power(long n) {
        if (n == 0) {
            return F.C1;
        }
        return n != 1 ? F.Power(this, F.integer(n)) : this;
    }

    public final IAST prependClone(IExpr expr) {
        return addAtClone(1, expr);
    }

    public final ASTRange range() {
        return new ASTRange(this, 0, size());
    }

    public final ASTRange range(int start) {
        return new ASTRange(this, start, size());
    }

    public final ASTRange range(int start, int end) {
        return new ASTRange(this, start, end);
    }

    public final IExpr remainder(IExpr that) {
        if (equals(that)) {
            return F.C0;
        }
        return this;
    }

    public final IAST removeAtClone(int position) {
        IAST ast = clone();
        ast.remove(position);
        return ast;
    }

    public final IExpr replaceAll(Function<IExpr, IExpr> function) {
        return (IExpr) accept(new VisitorReplaceAll(function));
    }

    public final IExpr replaceAll(IAST astRules) {
        return (IExpr) accept(new VisitorReplaceAll(astRules));
    }

    public final IExpr replacePart(IAST astRules) {
        return (IExpr) accept(new VisitorReplacePart(astRules));
    }

    public final IExpr replaceRepeated(Function<IExpr, IExpr> function) {
        return ExprImpl.replaceRepeated(this, new VisitorReplaceAll(function));
    }

    public final IExpr replaceRepeated(IAST astRules) {
        return ExprImpl.replaceRepeated(this, new VisitorReplaceAll(astRules));
    }

    public final IExpr replaceSlots(IAST astSlots) {
        return (IExpr) accept(new VisitorReplaceSlots(astSlots));
    }

    public final IAST setAtClone(int position, IExpr expr) {
        IAST ast = clone();
        ast.set(position, expr);
        return ast;
    }

    public final void setEvalFlags(int i) {
        this.fEvalFlags = i;
    }

    @Deprecated
    public final int signum() {
        if (isTimes()) {
            IExpr temp = arg1();
            if (temp.isSignedNumber() && ((ISignedNumber) temp).isNegative()) {
                return -1;
            }
        }
        return 1;
    }

    public int size() {
        throw new UnsupportedOperationException();
    }

    public final IExpr subtract(IExpr that) {
        if (that.isZero()) {
            return this;
        }
        if (that.isNumber()) {
            return F.eval(F.Plus(this, (IExpr) that.negate()));
        }
        return F.eval(F.Plus(this, F.Times(F.CN1, that)));
    }

    public final IExpr sum(IExpr that) {
        return plus(that);
    }

    public final IExpr times(IExpr that) {
        if (that.isZero()) {
            return F.C0;
        }
        return F.eval(F.Times(this, that));
    }

    private final String toFullFormString() {
        StringBuffer text;
        String sep = ", ";
        IExpr temp = null;
        if (size() > 0) {
            temp = head();
        }
        if (temp == null) {
            text = new StringBuffer("<null-tag>");
        } else {
            text = new StringBuffer(temp.toString());
        }
        text.append('[');
        for (int i = 1; i < size(); i++) {
            AbstractAST o = get(i);
            text = text.append(o == this ? "(this AST)" : o.toString());
            if (i < size() - 1) {
                text.append(", ");
            }
        }
        text.append(']');
        return text.toString();
    }

    public final ISymbol topHead() {
        IExpr header = head();
        if (header instanceof ISymbol) {
            return (ISymbol) header;
        }
        if (header instanceof IAST) {
            return ((IAST) header).topHead();
        }
        if (header.isSignedNumber()) {
            if (header instanceof INum) {
                return F.RealHead;
            }
            if (header instanceof IInteger) {
                return F.IntegerHead;
            }
            if (header instanceof IFraction) {
                return F.Rational;
            }
        }
        if (header instanceof IComplex) {
            return F.Complex;
        }
        if (header instanceof IComplexNum) {
            return F.Complex;
        }
        if (header instanceof IPattern) {
            return F.PatternHead;
        }
        if (head() instanceof IStringX) {
            return F.StringHead;
        }
        return null;
    }

    public final String toScript() {
        return toString();
    }

    public final String toScriptFactory() {
        throw new UnsupportedOperationException();
    }

    public final String toString() {
        try {
            StringBuilder sb = new StringBuilder();
            OutputFormFactory.get(EvalEngine.get().isRelaxedSyntax()).convert(sb, this);
            return sb.toString();
        } catch (Exception e) {
            try {
                StringBuffer buf = new StringBuffer();
                if (size() > 0 && isList()) {
                    buf.append('{');
                    for (int i = 1; i < size(); i++) {
                        buf.append(get(i) == this ? "(this AST)" : String.valueOf(get(i)));
                        if (i < size() - 1) {
                            buf.append(", ");
                        }
                    }
                    buf.append('}');
                    return buf.toString();
                } else if (!isAST(F.Slot, 2) || !arg1().isSignedNumber()) {
                    return toFullFormString();
                } else {
                    try {
                        int slot = ((ISignedNumber) arg1()).toInt();
                        if (slot <= 0) {
                            return toFullFormString();
                        }
                        if (slot == 1) {
                            return "#";
                        }
                        return "#" + slot;
                    } catch (ArithmeticException e2) {
                        return toFullFormString();
                    }
                }
            } catch (NullPointerException e3) {
                System.out.println(fullFormString());
                throw e3;
            }
        }
    }

    public final IExpr variables2Slots(Map<IExpr, IExpr> map, List<IExpr> variableList) {
        return variables2Slots(this, new IsUnaryVariableOrPattern(), new UnaryVariable2Slot(map, variableList));
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
