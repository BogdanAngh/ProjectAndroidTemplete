package edu.jas.application;

import edu.jas.arith.Rational;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.Complex;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.GenPolynomialTokenizer;
import edu.jas.poly.TermOrder;
import edu.jas.root.ComplexAlgebraicRing;
import edu.jas.root.RealAlgebraicRing;
import edu.jas.root.RootUtil;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.ufd.QuotientRing;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;

public class ExtensionFieldBuilder implements Serializable {
    public final RingFactory factory;

    protected ExtensionFieldBuilder() {
        throw new IllegalArgumentException("do not use this constructor");
    }

    public ExtensionFieldBuilder(RingFactory base) {
        this.factory = base;
    }

    public RingFactory build() {
        return this.factory;
    }

    public static ExtensionFieldBuilder baseField(RingFactory base) {
        return new ExtensionFieldBuilder(base);
    }

    public ExtensionFieldBuilder transcendentExtension(String vars) {
        return new ExtensionFieldBuilder(new QuotientRing(new GenPolynomialRing(this.factory, GenPolynomialTokenizer.variableList(vars))));
    }

    public ExtensionFieldBuilder polynomialExtension(String vars) {
        return new ExtensionFieldBuilder(new GenPolynomialRing(this.factory, GenPolynomialTokenizer.variableList(vars)));
    }

    public ExtensionFieldBuilder algebraicExtension(String var, String expr) {
        String[] variables = GenPolynomialTokenizer.variableList(var);
        if (variables.length < 1) {
            variables = GenPolynomialTokenizer.expressionVariables(expr);
            if (variables.length < 1) {
                throw new IllegalArgumentException("no variables in '" + var + "' and '" + expr + "'");
            }
        }
        GenPolynomialRing pfac = new GenPolynomialRing(this.factory, variables);
        if (variables.length == 1) {
            return new ExtensionFieldBuilder(new AlgebraicNumberRing(pfac.parse(expr)));
        }
        try {
            Ideal agen = new Ideal(pfac, new GenPolynomialTokenizer(pfac, new StringReader(expr)).nextPolynomialList());
            if (agen.isONE()) {
                throw new IllegalArgumentException("ideal is 1: " + expr);
            } else if (agen.isZERO()) {
                return new ExtensionFieldBuilder(new QuotientRing(pfac));
            } else {
                return new ExtensionFieldBuilder(new ResidueRing(agen));
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public ExtensionFieldBuilder realAlgebraicExtension(String var, String expr, String root) {
        String[] variables = new String[]{var};
        if (((RingElem) this.factory.getONE()) instanceof Rational) {
            GenPolynomialRing pfac = new GenPolynomialRing(this.factory, new TermOrder(2), variables);
            return new ExtensionFieldBuilder(new RealAlgebraicRing(pfac.parse(expr), RootUtil.parseInterval(pfac.coFac, root)));
        }
        throw new IllegalArgumentException("base field not instance of Rational");
    }

    public ExtensionFieldBuilder complexAlgebraicExtension(String var, String expr, String root) {
        String[] variables = new String[]{var};
        if (((RingElem) this.factory.getONE()) instanceof Complex) {
            GenPolynomialRing pfac = new GenPolynomialRing(this.factory, variables);
            return new ExtensionFieldBuilder(new ComplexAlgebraicRing(pfac.parse(expr), RootUtil.parseRectangle(pfac.coFac, root)));
        }
        throw new IllegalArgumentException("base field not instance of Complex");
    }

    public String toString() {
        StringBuffer s = new StringBuffer(" ");
        s.append(this.factory.toString());
        s.append(" ");
        return s.toString();
    }

    public String toScript() {
        StringBuffer s = new StringBuffer(" ");
        s.append(this.factory.toScript());
        s.append(" ");
        return s.toString();
    }
}
