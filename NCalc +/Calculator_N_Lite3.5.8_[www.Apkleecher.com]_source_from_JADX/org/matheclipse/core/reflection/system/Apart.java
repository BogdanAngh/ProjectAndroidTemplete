package org.matheclipse.core.reflection.system;

import edu.jas.arith.BigInteger;
import edu.jas.arith.BigRational;
import edu.jas.poly.GenPolynomial;
import edu.jas.ufd.FactorFactory;
import edu.jas.ufd.SquarefreeFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import org.matheclipse.core.convert.JASConvert;
import org.matheclipse.core.convert.VariablesSet;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.JASConversionException;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.ASTRange;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IFraction;
import org.matheclipse.core.interfaces.ISignedNumber;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.polynomials.IPartialFractionGenerator;
import org.matheclipse.core.polynomials.PartialFractionGenerator;

public class Apart extends AbstractFunctionEvaluator {
    public IExpr evaluate(IAST ast, EvalEngine engine) {
        IAST variableList;
        Validate.checkRange(ast, 2, 3);
        IExpr arg1 = ast.arg1();
        if (ast.size() == 3) {
            variableList = Validate.checkSymbolOrSymbolList(ast, 2);
        } else {
            VariablesSet eVar = new VariablesSet(arg1);
            if (!eVar.isSize(1)) {
                return null;
            }
            variableList = eVar.getVarList();
        }
        if (!arg1.isTimes() && !arg1.isPower()) {
            return arg1;
        }
        IExpr[] parts = getFractionalParts(arg1);
        if (parts != null) {
            IExpr result = partialFractionDecompositionRational(new PartialFractionGenerator(), parts, (ISymbol) variableList.arg1());
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    @Deprecated
    public static IAST partialFractionDecompositionInteger(IExpr[] parts, IAST variableList) {
        try {
            IExpr exprNumerator = F.evalExpandAll(parts[0]);
            IExpr exprDenominator = F.evalExpandAll(parts[1]);
            String[] varListStr = new String[]{variableList.arg1().toString()};
            JASConvert<BigInteger> jas = new JASConvert(new ASTRange(variableList, 1).toList(), BigInteger.ZERO);
            GenPolynomial<BigInteger> numerator = jas.expr2JAS(exprNumerator, false);
            SortedMap<GenPolynomial<BigInteger>, Long> sfactors = FactorFactory.getImplementation(BigInteger.ZERO).baseFactors(jas.expr2JAS(exprDenominator, false));
            List<GenPolynomial<BigInteger>> D = new ArrayList(sfactors.keySet());
            List<List<GenPolynomial<BigInteger>>> Ai = SquarefreeFactory.getImplementation(BigInteger.ZERO).basePartialFraction(numerator, sfactors);
            if (Ai.size() > 0) {
                IExpr temp;
                IAST Plus = F.Plus();
                if (!((GenPolynomial) ((List) Ai.get(0)).get(0)).isZERO()) {
                    temp = F.eval(jas.integerPoly2Expr((GenPolynomial) ((List) Ai.get(0)).get(0)));
                    if (temp.isAST()) {
                        ((IAST) temp).addEvalFlags(IExpr.SYMBOLID);
                    }
                    Plus.add(temp);
                }
                for (int i = 1; i < Ai.size(); i++) {
                    long j = 0;
                    for (GenPolynomial<BigInteger> genPolynomial : (List) Ai.get(i)) {
                        if (!genPolynomial.isZERO()) {
                            temp = F.eval(F.Times(jas.integerPoly2Expr(genPolynomial), F.Power(jas.integerPoly2Expr((GenPolynomial) D.get(i - 1)), F.integer(-1 * j))));
                            if (!temp.isZero()) {
                                if (temp.isAST()) {
                                    ((IAST) temp).addEvalFlags(IExpr.SYMBOLID);
                                }
                                Plus.add(temp);
                            }
                        }
                        j++;
                    }
                }
                return Plus;
            }
        } catch (JASConversionException e) {
        }
        return null;
    }

    public static IExpr partialFractionDecompositionRational(IPartialFractionGenerator pf, IExpr[] parts, ISymbol x) {
        try {
            IAST variableList = F.List(x);
            IExpr exprNumerator = F.evalExpandAll(parts[0]);
            IExpr exprDenominator = F.evalExpandAll(parts[1]);
            String[] varListStr = new String[]{variableList.arg1().toString()};
            JASConvert<BigRational> jas = new JASConvert(new ASTRange(variableList, 1).toList(), BigRational.ZERO);
            GenPolynomial<BigRational> numerator = jas.expr2JAS(exprNumerator, false);
            SortedMap<GenPolynomial<BigRational>, Long> sfactors = FactorFactory.getImplementation(BigRational.ZERO).baseFactors(jas.expr2JAS(exprDenominator, false));
            List<GenPolynomial<BigRational>> D = new ArrayList(sfactors.keySet());
            List<List<GenPolynomial<BigRational>>> Ai = SquarefreeFactory.getImplementation(BigRational.ZERO).basePartialFraction(numerator, sfactors);
            if (Ai.size() > 0) {
                pf.setJAS(jas);
                if (!((GenPolynomial) ((List) Ai.get(0)).get(0)).isZERO()) {
                    pf.addNonFractionalPart((GenPolynomial) ((List) Ai.get(0)).get(0));
                }
                for (int i = 1; i < Ai.size(); i++) {
                    int j = 0;
                    for (GenPolynomial<BigRational> genPolynomial : (List) Ai.get(i)) {
                        if (!genPolynomial.isZERO()) {
                            IPartialFractionGenerator iPartialFractionGenerator = pf;
                            iPartialFractionGenerator.addSinglePartialFraction(genPolynomial, (GenPolynomial) D.get(i - 1), j);
                        }
                        j++;
                    }
                }
                return pf.getResult();
            }
        } catch (JASConversionException e) {
        }
        return null;
    }

    public static IExpr[] getFractionalPartsRational(IExpr arg) {
        if (!arg.isFraction()) {
            return getFractionalParts(arg);
        }
        IFraction fr = (IFraction) arg;
        return new IExpr[]{fr.getNumerator(), fr.getDenominator()};
    }

    public static IExpr[] getFractionalParts(IExpr arg) {
        IExpr[] parts = null;
        if (arg.isTimes()) {
            parts = getFractionalPartsTimes((IAST) arg, false, true, true);
        } else if (arg.isPower()) {
            parts = new IExpr[2];
            IExpr denom = getFractionalPartsPower((IAST) arg);
            if (denom == null) {
                return null;
            }
            parts[0] = F.C1;
            parts[1] = denom;
        } else if (arg.isAST() && Denominator.getDenominatorForm((IAST) arg) != null) {
            return new IExpr[]{F.C1, Denominator.getDenominatorForm((IAST) arg)};
        }
        return parts;
    }

    public static IExpr[] getFractionalPartsTimes(IAST timesAST, boolean splitNumeratorOne, boolean splitFractionalNumbers, boolean useDenominatorForm) {
        IExpr[] result = new IExpr[3];
        result[2] = null;
        IAST numerator = F.Times();
        IAST denominator = F.Times();
        boolean evaled = false;
        boolean splitFractionEvaled = false;
        for (int i = 1; i < timesAST.size(); i++) {
            IExpr arg = (IExpr) timesAST.get(i);
            if (arg.isAST()) {
                IAST argAST = (IAST) arg;
                if (useDenominatorForm && argAST.size() == 2) {
                    IAST denomForm = Denominator.getDenominatorForm(argAST);
                    if (denomForm != null) {
                        denominator.add(denomForm);
                        evaled = true;
                    }
                    numerator.add(arg);
                } else {
                    if (arg.isPower()) {
                        IExpr denom = getFractionalPartsPower((IAST) arg);
                        if (denom != null) {
                            denominator.add(denom);
                            evaled = true;
                        }
                    }
                    numerator.add(arg);
                }
            } else {
                if (i == 1 && arg.isFraction()) {
                    IFraction fr;
                    if (splitNumeratorOne) {
                        fr = (IFraction) arg;
                        if (fr.getNumerator().isOne()) {
                            denominator.add(fr.getDenominator());
                            splitFractionEvaled = true;
                        } else if (fr.getNumerator().isMinusOne()) {
                            numerator.add(fr.getNumerator());
                            denominator.add(fr.getDenominator());
                            splitFractionEvaled = true;
                        } else {
                            result[2] = fr;
                        }
                    } else if (splitFractionalNumbers) {
                        fr = (IFraction) arg;
                        if (!fr.getNumerator().isOne()) {
                            numerator.add(fr.getNumerator());
                        }
                        denominator.add(fr.getDenominator());
                        evaled = true;
                    }
                }
                numerator.add(arg);
            }
        }
        if (evaled) {
            result[0] = numerator.getOneIdentity(F.C1);
            result[1] = denominator.getOneIdentity(F.C1);
            return result;
        }
        if (splitFractionEvaled) {
            result[0] = numerator.getOneIdentity(F.C1);
            if (!result[0].isTimes() && !result[0].isPlus()) {
                result[1] = denominator.getOneIdentity(F.C1);
                return result;
            } else if (result[0].isTimes() && ((IAST) result[0]).size() == 3 && ((IAST) result[0]).arg1().isMinusOne()) {
                result[1] = denominator.getOneIdentity(F.C1);
                return result;
            }
        }
        return null;
    }

    public static IExpr getFractionalPartsPower(IAST powerAST) {
        IExpr arg2 = powerAST.arg2();
        if (arg2.isSignedNumber()) {
            ISignedNumber sn = (ISignedNumber) arg2;
            if (sn.isMinusOne()) {
                return powerAST.arg1();
            }
            if (sn.isNegative()) {
                return F.Power(powerAST.arg1(), sn.negate());
            }
            if (sn.isInteger() && powerAST.arg1().isAST()) {
                IAST denomForm = Denominator.getDenominatorForm((IAST) powerAST.arg1());
                if (denomForm != null) {
                    return F.Power(denomForm, sn);
                }
            }
        }
        IExpr negExpr = AbstractFunctionEvaluator.getNormalizedNegativeExpression(arg2);
        if (negExpr != null) {
            return F.Power(powerAST.arg1(), negExpr);
        }
        return null;
    }
}
