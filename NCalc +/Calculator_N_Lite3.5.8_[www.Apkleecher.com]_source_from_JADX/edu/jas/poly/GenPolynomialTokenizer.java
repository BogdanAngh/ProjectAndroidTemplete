package edu.jas.poly;

import android.support.v4.view.MotionEventCompat;
import com.example.duy.calculator.math_eval.Constants;
import edu.hws.jcm.data.ExpressionProgram;
import edu.jas.arith.BigComplex;
import edu.jas.arith.BigDecimal;
import edu.jas.arith.BigInteger;
import edu.jas.arith.BigQuaternion;
import edu.jas.arith.BigRational;
import edu.jas.arith.ModInteger;
import edu.jas.arith.ModIntegerRing;
import edu.jas.arith.ModLongRing;
import edu.jas.structure.Power;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import io.github.kexanie.library.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.math4.random.ValueServer;
import org.apache.log4j.Logger;
import org.matheclipse.core.interfaces.IExpr;

public class GenPolynomialTokenizer {
    private static final Logger logger;
    private final boolean debug;
    private RingFactory fac;
    private int nvars;
    private coeffType parsedCoeff;
    private polyType parsedPoly;
    private GenPolynomialRing pfac;
    private final Reader reader;
    private GenSolvablePolynomialRing spfac;
    private RelationTable table;
    private final StreamTokenizer tok;
    private TermOrder tord;
    private String[] vars;

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$edu$jas$poly$GenPolynomialTokenizer$coeffType;

        static {
            $SwitchMap$edu$jas$poly$GenPolynomialTokenizer$coeffType = new int[coeffType.values().length];
            try {
                $SwitchMap$edu$jas$poly$GenPolynomialTokenizer$coeffType[coeffType.BigRat.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$edu$jas$poly$GenPolynomialTokenizer$coeffType[coeffType.BigInt.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$edu$jas$poly$GenPolynomialTokenizer$coeffType[coeffType.ModInt.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$edu$jas$poly$GenPolynomialTokenizer$coeffType[coeffType.BigC.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$edu$jas$poly$GenPolynomialTokenizer$coeffType[coeffType.BigQ.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$edu$jas$poly$GenPolynomialTokenizer$coeffType[coeffType.BigD.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$edu$jas$poly$GenPolynomialTokenizer$coeffType[coeffType.IntFunc.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    private enum coeffType {
        BigRat,
        BigInt,
        ModInt,
        BigC,
        BigQ,
        BigD,
        ANrat,
        ANmod,
        IntFunc
    }

    private enum polyType {
        PolBigRat,
        PolBigInt,
        PolModInt,
        PolBigC,
        PolBigD,
        PolBigQ,
        PolANrat,
        PolANmod,
        PolIntFunc
    }

    static {
        logger = Logger.getLogger(GenPolynomialTokenizer.class);
    }

    public GenPolynomialTokenizer() {
        this(new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF8"))));
    }

    public GenPolynomialTokenizer(GenPolynomialRing rf, Reader r) {
        this(r);
        if (rf != null) {
            if (rf instanceof GenSolvablePolynomialRing) {
                this.pfac = rf;
                this.spfac = (GenSolvablePolynomialRing) rf;
            } else {
                this.pfac = rf;
                this.spfac = null;
            }
            this.fac = rf.coFac;
            this.vars = rf.vars;
            if (this.vars != null) {
                this.nvars = this.vars.length;
            }
            this.tord = rf.tord;
            if (this.spfac != null) {
                this.table = this.spfac.table;
            } else {
                this.table = null;
            }
        }
    }

    public GenPolynomialTokenizer(Reader r) {
        this.debug = logger.isDebugEnabled();
        this.nvars = 1;
        this.parsedCoeff = coeffType.BigRat;
        this.parsedPoly = polyType.PolBigRat;
        this.vars = null;
        this.tord = new TermOrder();
        this.nvars = 1;
        this.fac = new BigRational(1);
        this.pfac = new GenPolynomialRing(this.fac, this.nvars, this.tord, this.vars);
        this.spfac = new GenSolvablePolynomialRing(this.fac, this.nvars, this.tord, this.vars);
        this.reader = r;
        this.tok = new StreamTokenizer(this.reader);
        this.tok.resetSyntax();
        this.tok.eolIsSignificant(false);
        this.tok.wordChars(48, 57);
        this.tok.wordChars(97, 122);
        this.tok.wordChars(65, 90);
        this.tok.wordChars(95, 95);
        this.tok.wordChars(47, 47);
        this.tok.wordChars(46, 46);
        this.tok.wordChars(160, MotionEventCompat.ACTION_MASK);
        this.tok.whitespaceChars(0, 32);
        this.tok.commentChar(35);
        this.tok.quoteChar(34);
        this.tok.quoteChar(39);
    }

    public void initFactory(RingFactory rf, coeffType ct) {
        this.fac = rf;
        this.parsedCoeff = ct;
        switch (1.$SwitchMap$edu$jas$poly$GenPolynomialTokenizer$coeffType[ct.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                this.pfac = new GenPolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolBigRat;
            case IExpr.DOUBLEID /*2*/:
                this.pfac = new GenPolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolBigInt;
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                this.pfac = new GenPolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolModInt;
            case IExpr.DOUBLECOMPLEXID /*4*/:
                this.pfac = new GenPolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolBigC;
            case ValueServer.CONSTANT_MODE /*5*/:
                this.pfac = new GenPolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolBigQ;
            case R.styleable.Toolbar_contentInsetEnd /*6*/:
                this.pfac = new GenPolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolBigD;
            case R.styleable.Toolbar_contentInsetLeft /*7*/:
                this.pfac = new GenPolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolIntFunc;
            default:
                this.pfac = new GenPolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolBigRat;
        }
    }

    public void initSolvableFactory(RingFactory rf, coeffType ct) {
        this.fac = rf;
        this.parsedCoeff = ct;
        switch (1.$SwitchMap$edu$jas$poly$GenPolynomialTokenizer$coeffType[ct.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                this.spfac = new GenSolvablePolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolBigRat;
            case IExpr.DOUBLEID /*2*/:
                this.spfac = new GenSolvablePolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolBigInt;
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                this.spfac = new GenSolvablePolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolModInt;
            case IExpr.DOUBLECOMPLEXID /*4*/:
                this.spfac = new GenSolvablePolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolBigC;
            case ValueServer.CONSTANT_MODE /*5*/:
                this.spfac = new GenSolvablePolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolBigQ;
            case R.styleable.Toolbar_contentInsetEnd /*6*/:
                this.spfac = new GenSolvablePolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolBigD;
            case R.styleable.Toolbar_contentInsetLeft /*7*/:
                this.spfac = new GenSolvablePolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolIntFunc;
            default:
                this.spfac = new GenSolvablePolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolBigRat;
        }
    }

    public GenPolynomial nextPolynomial() throws IOException {
        if (this.debug) {
            logger.debug("torder = " + this.tord);
        }
        GenPolynomial a = this.pfac.getZERO();
        GenPolynomial a1 = this.pfac.getONE();
        ExpVector leer = this.pfac.evzero;
        if (this.debug) {
            logger.debug("a = " + a);
            logger.debug("a1 = " + a1);
        }
        GenPolynomial b = a1;
        while (true) {
            int tt = this.tok.nextToken();
            logger.debug("while tt = " + this.tok);
            if (tt != -1) {
                switch (tt) {
                    case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_textAppearanceSmallPopupMenu /*41*/:
                    case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_dialogPreferredPadding /*44*/:
                        return a;
                    case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_textAppearancePopupMenuHeader /*42*/:
                    case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_dialogTheme /*43*/:
                        break;
                    case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_listDividerAlertDialog /*45*/:
                        b = b.negate();
                        break;
                }
                tt = this.tok.nextToken();
                if (tt != -1) {
                    RingElem r;
                    long ie;
                    switch (tt) {
                        case ExpressionProgram.TIMES /*-3*/:
                            if (this.tok.sval != null) {
                                if (this.tok.sval.length() != 0) {
                                    char first = this.tok.sval.charAt(0);
                                    if (digit(first) || first == '/' || first == '.') {
                                        StringBuffer df = new StringBuffer();
                                        df.append(this.tok.sval);
                                        if (this.tok.sval.length() > 1) {
                                            if (digit(this.tok.sval.charAt(1))) {
                                                if (first == '/') {
                                                    df.insert(0, "1");
                                                }
                                                if (first == '.') {
                                                    df.insert(0, Constants.ZERO);
                                                }
                                            }
                                        }
                                        if (this.tok.sval.charAt(this.tok.sval.length() - 1) == 'i') {
                                            tt = this.tok.nextToken();
                                            if (this.debug) {
                                                logger.debug("tt,im = " + this.tok);
                                            }
                                            if (this.tok.sval != null || tt == 45) {
                                                if (this.tok.sval != null) {
                                                    df.append(this.tok.sval);
                                                } else {
                                                    df.append("-");
                                                }
                                                if (tt == 45) {
                                                    tt = this.tok.nextToken();
                                                    if (this.tok.sval != null) {
                                                        if (digit(this.tok.sval.charAt(0))) {
                                                            df.append(this.tok.sval);
                                                        }
                                                    }
                                                    this.tok.pushBack();
                                                }
                                            } else {
                                                this.tok.pushBack();
                                            }
                                        }
                                        if (this.tok.nextToken() == 46) {
                                            tt = this.tok.nextToken();
                                            if (this.debug) {
                                                logger.debug("tt,dot = " + this.tok);
                                            }
                                            if (this.tok.sval != null) {
                                                df.append(".");
                                                df.append(this.tok.sval);
                                            } else {
                                                this.tok.pushBack();
                                                this.tok.pushBack();
                                            }
                                        } else {
                                            this.tok.pushBack();
                                        }
                                        try {
                                            r = (RingElem) this.fac.parse(df.toString());
                                            if (this.debug) {
                                                logger.debug("coeff " + r);
                                            }
                                            ie = nextExponent();
                                            if (this.debug) {
                                                logger.debug("ie " + ie);
                                            }
                                            r = Power.positivePower(r, ie);
                                            if (this.debug) {
                                                logger.debug("coeff^ie " + r);
                                            }
                                            b = b.multiply(r, leer);
                                            tt = this.tok.nextToken();
                                            if (this.debug) {
                                                logger.debug("tt,digit = " + this.tok);
                                            }
                                        } catch (Throwable re) {
                                            throw new InvalidExpressionException("not a number " + df, re);
                                        }
                                    }
                                    if (tt != -1) {
                                        if (this.tok.sval != null) {
                                            if (letter(this.tok.sval.charAt(0))) {
                                                int ix = leer.indexVar(this.tok.sval, this.vars);
                                                if (ix < 0) {
                                                    try {
                                                        r = (RingElem) this.fac.parse(this.tok.sval);
                                                        if (this.debug) {
                                                            logger.info("coeff " + r);
                                                        }
                                                        b = b.multiply(Power.positivePower(r, nextExponent()));
                                                    } catch (NumberFormatException e) {
                                                        throw new InvalidExpressionException("recursively unknown variable " + this.tok.sval);
                                                    }
                                                }
                                                ie = nextExponent();
                                                b = b.multiply(ExpVector.create(this.vars.length, ix, ie));
                                                tt = this.tok.nextToken();
                                                if (this.debug) {
                                                    logger.debug("tt,letter = " + this.tok);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                        case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_textAppearanceLargePopupMenu /*40*/:
                            RingElem c = nextPolynomial();
                            if (this.debug) {
                                logger.debug("factor " + c);
                            }
                            ie = nextExponent();
                            if (this.debug) {
                                logger.debug("ie " + ie);
                            }
                            GenPolynomial c2 = (GenPolynomial) Power.positivePower(c, ie);
                            if (this.debug) {
                                logger.debug("factor^ie " + c2);
                            }
                            b = b.multiply(c2);
                            tt = this.tok.nextToken();
                            if (this.debug) {
                                logger.debug("tt,digit = " + this.tok);
                                break;
                            }
                            break;
                        case 123:
                            StringBuffer rf = new StringBuffer();
                            int level = 0;
                            do {
                                tt = this.tok.nextToken();
                                if (tt == -1) {
                                    throw new InvalidExpressionException("mismatch of braces after " + a + ", error at " + b);
                                }
                                if (tt == 123) {
                                    level++;
                                }
                                if (tt == 125) {
                                    level--;
                                    if (level < 0) {
                                        continue;
                                    }
                                }
                                if (this.tok.sval != null) {
                                    if (rf.length() > 0) {
                                        if (rf.charAt(rf.length() - 1) != '.') {
                                            rf.append(" ");
                                        }
                                    }
                                    rf.append(this.tok.sval);
                                    continue;
                                } else {
                                    rf.append((char) tt);
                                    continue;
                                }
                            } while (level >= 0);
                            try {
                                r = (RingElem) this.fac.parse(rf.toString());
                                if (this.debug) {
                                    logger.debug("coeff " + r);
                                }
                                ie = nextExponent();
                                if (this.debug) {
                                    logger.debug("ie " + ie);
                                }
                                r = Power.positivePower(r, ie);
                                if (this.debug) {
                                    logger.debug("coeff^ie " + r);
                                }
                                b = b.multiply(r, leer);
                                tt = this.tok.nextToken();
                                if (this.debug) {
                                    logger.debug("tt,digit = " + this.tok);
                                    break;
                                }
                            } catch (Throwable re2) {
                                throw new InvalidExpressionException("not a number " + rf, re2);
                            }
                            break;
                        case 125:
                            throw new InvalidExpressionException("mismatch of braces after " + a + ", error at " + b);
                    }
                    if (tt != -1) {
                        this.tok.pushBack();
                        switch (tt) {
                            case R.styleable.SwitchCompat_switchMinWidth /*10*/:
                                tt = this.tok.nextToken();
                                if (!this.debug) {
                                    break;
                                }
                                logger.debug("tt,nl = " + tt);
                                break;
                            case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_textAppearanceSmallPopupMenu /*41*/:
                            case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_dialogTheme /*43*/:
                            case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_dialogPreferredPadding /*44*/:
                            case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_listDividerAlertDialog /*45*/:
                                logger.debug("b, = " + b);
                                a = a.sum(b);
                                b = a1;
                                break;
                            case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_textAppearancePopupMenuHeader /*42*/:
                                logger.debug("b, = " + b);
                                break;
                            default:
                                if (!this.debug) {
                                    break;
                                }
                                logger.debug("default: " + this.tok);
                                break;
                        }
                    }
                }
            }
            if (this.debug) {
                logger.debug("b = " + b);
            }
            a = a.sum(b);
            logger.debug("a = " + a);
            return a;
        }
    }

    public long nextExponent() throws IOException {
        int tt = this.tok.nextToken();
        if (tt == 94) {
            if (this.debug) {
                logger.debug("exponent ^");
            }
            tt = this.tok.nextToken();
            if (this.tok.sval != null && digit(this.tok.sval.charAt(0))) {
                return Long.parseLong(this.tok.sval);
            }
        }
        if (tt == 42) {
            if (this.tok.nextToken() == 42) {
                if (this.debug) {
                    logger.debug("exponent **");
                }
                tt = this.tok.nextToken();
                if (this.tok.sval != null && digit(this.tok.sval.charAt(0))) {
                    return Long.parseLong(this.tok.sval);
                }
            }
            this.tok.pushBack();
        }
        this.tok.pushBack();
        return 1;
    }

    public String nextComment() throws IOException {
        StringBuffer c = new StringBuffer();
        if (this.debug) {
            logger.debug("comment: " + this.tok);
        }
        int tt = this.tok.nextToken();
        if (this.debug) {
            logger.debug("comment: " + this.tok);
        }
        if (tt == 40) {
            tt = this.tok.nextToken();
            if (this.debug) {
                logger.debug("comment: " + this.tok);
            }
            if (tt == 42) {
                if (this.debug) {
                    logger.debug("comment: ");
                }
                while (true) {
                    if (this.tok.nextToken() == 42) {
                        if (this.tok.nextToken() == 41) {
                            return c.toString();
                        }
                        this.tok.pushBack();
                    }
                    c.append(this.tok.sval);
                }
            } else {
                this.tok.pushBack();
                if (this.debug) {
                    logger.debug("comment: " + this.tok);
                }
            }
        }
        this.tok.pushBack();
        if (this.debug) {
            logger.debug("comment: " + this.tok);
        }
        return c.toString();
    }

    public String[] nextVariableList() throws IOException {
        List<String> l = new ArrayList();
        int tt = this.tok.nextToken();
        if (tt == 40 || tt == 123) {
            logger.debug("variable list");
            tt = this.tok.nextToken();
            while (tt != -1 && tt != 41 && tt != 125) {
                if (tt == -3) {
                    l.add(this.tok.sval);
                }
                tt = this.tok.nextToken();
            }
        } else {
            this.tok.pushBack();
        }
        Object[] ol = l.toArray();
        String[] v = new String[ol.length];
        for (int i = 0; i < v.length; i++) {
            v[i] = (String) ol[i];
        }
        return v;
    }

    public RingFactory nextCoefficientRing() throws IOException {
        RingFactory ringFactory = null;
        coeffType ct = null;
        int tt = this.tok.nextToken();
        if (this.tok.sval != null) {
            if (this.tok.sval.equalsIgnoreCase("Q")) {
                ringFactory = new BigRational(0);
                ct = coeffType.BigRat;
            } else {
                if (this.tok.sval.equalsIgnoreCase("Rat")) {
                    ringFactory = new BigRational(0);
                    ct = coeffType.BigRat;
                } else {
                    if (this.tok.sval.equalsIgnoreCase("D")) {
                        ringFactory = new BigDecimal(0);
                        ct = coeffType.BigD;
                    } else {
                        if (this.tok.sval.equalsIgnoreCase("Z")) {
                            ringFactory = new BigInteger(0);
                            ct = coeffType.BigInt;
                        } else {
                            if (this.tok.sval.equalsIgnoreCase("Int")) {
                                ringFactory = new BigInteger(0);
                                ct = coeffType.BigInt;
                            } else {
                                if (this.tok.sval.equalsIgnoreCase("C")) {
                                    ringFactory = new BigComplex(0);
                                    ct = coeffType.BigC;
                                } else {
                                    if (this.tok.sval.equalsIgnoreCase("Complex")) {
                                        ringFactory = new BigComplex(0);
                                        ct = coeffType.BigC;
                                    } else {
                                        if (this.tok.sval.equalsIgnoreCase("Quat")) {
                                            ringFactory = new BigQuaternion(0);
                                            ct = coeffType.BigQ;
                                        } else {
                                            if (this.tok.sval.equalsIgnoreCase("Mod")) {
                                                tt = this.tok.nextToken();
                                                boolean openb = false;
                                                if (tt == 91) {
                                                    openb = true;
                                                    tt = this.tok.nextToken();
                                                }
                                                if (this.tok.sval != null) {
                                                    if (this.tok.sval.length() > 0) {
                                                        if (digit(this.tok.sval.charAt(0))) {
                                                            BigInteger mo = new BigInteger(this.tok.sval);
                                                            if (mo.compareTo(new BigInteger(ModLongRing.MAX_LONG)) < 0) {
                                                                ringFactory = new ModLongRing(mo.getVal());
                                                            } else {
                                                                ringFactory = new ModIntegerRing(mo.getVal());
                                                            }
                                                            ct = coeffType.ModInt;
                                                        } else {
                                                            this.tok.pushBack();
                                                        }
                                                        if (tt == 93 && openb) {
                                                            tt = this.tok.nextToken();
                                                        }
                                                    }
                                                }
                                                this.tok.pushBack();
                                                tt = this.tok.nextToken();
                                            } else {
                                                if (!this.tok.sval.equalsIgnoreCase("RatFunc")) {
                                                    if (!this.tok.sval.equalsIgnoreCase("ModFunc")) {
                                                        if (this.tok.sval.equalsIgnoreCase("IntFunc")) {
                                                            String[] rfv = nextVariableList();
                                                            int vr = rfv.length;
                                                            Object coeff = new GenPolynomialRing(new BigRational(), vr, new TermOrder(2), rfv);
                                                            ct = coeffType.IntFunc;
                                                        } else {
                                                            if (this.tok.sval.equalsIgnoreCase("AN")) {
                                                                if (this.tok.nextToken() == 91) {
                                                                    RingFactory tcfac;
                                                                    String[] anv;
                                                                    int vs;
                                                                    String[] ovars;
                                                                    GenPolynomialRing tpfac;
                                                                    RingFactory tfac;
                                                                    GenPolynomial<ModInteger> mod;
                                                                    int i;
                                                                    tt = this.tok.nextToken();
                                                                    RingFactory modIntegerRing = new ModIntegerRing("19");
                                                                    if (this.tok.sval != null) {
                                                                        if (this.tok.sval.length() > 0) {
                                                                            if (digit(this.tok.sval.charAt(0))) {
                                                                                modIntegerRing = new ModIntegerRing(this.tok.sval);
                                                                            } else {
                                                                                tcfac = new BigRational();
                                                                                this.tok.pushBack();
                                                                            }
                                                                            anv = nextVariableList();
                                                                            vs = anv.length;
                                                                            if (vs == 1) {
                                                                                throw new InvalidExpressionException("AlgebraicNumber only for univariate polynomials " + Arrays.toString(anv));
                                                                            }
                                                                            ovars = this.vars;
                                                                            this.vars = anv;
                                                                            tpfac = this.pfac;
                                                                            tfac = this.fac;
                                                                            this.fac = tcfac;
                                                                            if (tcfac instanceof ModIntegerRing) {
                                                                                this.pfac = new GenPolynomialRing(tcfac, vs, new TermOrder(), anv);
                                                                            } else {
                                                                                this.pfac = new GenPolynomialRing(tcfac, vs, new TermOrder(), anv);
                                                                            }
                                                                            if (this.debug) {
                                                                                logger.debug("pfac = " + this.pfac);
                                                                            }
                                                                            if (this.tok.nextToken() != 40) {
                                                                                mod = nextPolynomial();
                                                                                tt = this.tok.nextToken();
                                                                                i = this.tok.ttype;
                                                                                if (r0 != 41) {
                                                                                    this.tok.pushBack();
                                                                                }
                                                                            } else {
                                                                                this.tok.pushBack();
                                                                                mod = nextPolynomial();
                                                                            }
                                                                            if (this.debug) {
                                                                                logger.debug("mod = " + mod);
                                                                            }
                                                                            this.pfac = tpfac;
                                                                            this.fac = tfac;
                                                                            this.vars = ovars;
                                                                            if (tcfac instanceof ModIntegerRing) {
                                                                                ringFactory = new AlgebraicNumberRing(mod);
                                                                                ct = coeffType.ANrat;
                                                                            } else {
                                                                                ringFactory = new AlgebraicNumberRing(mod);
                                                                                ct = coeffType.ANmod;
                                                                            }
                                                                            if (this.debug) {
                                                                                logger.debug("coeff = " + ringFactory);
                                                                            }
                                                                            if (this.tok.nextToken() != 93) {
                                                                                this.tok.pushBack();
                                                                            }
                                                                        }
                                                                    }
                                                                    tcfac = new BigRational();
                                                                    this.tok.pushBack();
                                                                    anv = nextVariableList();
                                                                    vs = anv.length;
                                                                    if (vs == 1) {
                                                                        ovars = this.vars;
                                                                        this.vars = anv;
                                                                        tpfac = this.pfac;
                                                                        tfac = this.fac;
                                                                        this.fac = tcfac;
                                                                        if (tcfac instanceof ModIntegerRing) {
                                                                            this.pfac = new GenPolynomialRing(tcfac, vs, new TermOrder(), anv);
                                                                        } else {
                                                                            this.pfac = new GenPolynomialRing(tcfac, vs, new TermOrder(), anv);
                                                                        }
                                                                        if (this.debug) {
                                                                            logger.debug("pfac = " + this.pfac);
                                                                        }
                                                                        if (this.tok.nextToken() != 40) {
                                                                            this.tok.pushBack();
                                                                            mod = nextPolynomial();
                                                                        } else {
                                                                            mod = nextPolynomial();
                                                                            tt = this.tok.nextToken();
                                                                            i = this.tok.ttype;
                                                                            if (r0 != 41) {
                                                                                this.tok.pushBack();
                                                                            }
                                                                        }
                                                                        if (this.debug) {
                                                                            logger.debug("mod = " + mod);
                                                                        }
                                                                        this.pfac = tpfac;
                                                                        this.fac = tfac;
                                                                        this.vars = ovars;
                                                                        if (tcfac instanceof ModIntegerRing) {
                                                                            ringFactory = new AlgebraicNumberRing(mod);
                                                                            ct = coeffType.ANrat;
                                                                        } else {
                                                                            ringFactory = new AlgebraicNumberRing(mod);
                                                                            ct = coeffType.ANmod;
                                                                        }
                                                                        if (this.debug) {
                                                                            logger.debug("coeff = " + ringFactory);
                                                                        }
                                                                        if (this.tok.nextToken() != 93) {
                                                                            this.tok.pushBack();
                                                                        }
                                                                    } else {
                                                                        throw new InvalidExpressionException("AlgebraicNumber only for univariate polynomials " + Arrays.toString(anv));
                                                                    }
                                                                }
                                                                this.tok.pushBack();
                                                            }
                                                        }
                                                    }
                                                }
                                                throw new InvalidExpressionException("RatFunc and ModFunc can no more be read, see edu.jas.application.RingFactoryTokenizer.");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (ringFactory == null) {
            this.tok.pushBack();
            ringFactory = new BigRational();
            ct = coeffType.BigRat;
        }
        this.parsedCoeff = ct;
        return ringFactory;
    }

    public long[] nextWeightList() throws IOException {
        List<Long> l = new ArrayList();
        if (this.tok.nextToken() == 40) {
            logger.debug("weight list");
            int tt = this.tok.nextToken();
            while (tt != -1 && tt != 41) {
                if (this.tok.sval != null && digit(this.tok.sval.charAt(0))) {
                    l.add(Long.valueOf(Long.parseLong(this.tok.sval)));
                }
                tt = this.tok.nextToken();
            }
        } else {
            this.tok.pushBack();
        }
        Object[] ol = l.toArray();
        long[] w = new long[ol.length];
        for (int i = 0; i < w.length; i++) {
            w[i] = ((Long) ol[(ol.length - i) - 1]).longValue();
        }
        return w;
    }

    public long[][] nextWeightArray() throws IOException {
        List<long[]> l = new ArrayList();
        long[][] w = null;
        if (this.tok.nextToken() == 40) {
            logger.debug("weight array");
            int tt = this.tok.nextToken();
            while (tt != -1 && tt != 41) {
                if (tt != 40) {
                    if (this.tok.sval != null && digit(this.tok.sval.charAt(0))) {
                        this.tok.pushBack();
                        this.tok.pushBack();
                        l.add(nextWeightList());
                        break;
                    }
                }
                this.tok.pushBack();
                l.add(nextWeightList());
                tt = this.tok.nextToken();
            }
        } else {
            this.tok.pushBack();
        }
        Object[] ol = l.toArray();
        w = new long[ol.length][];
        for (int i = 0; i < w.length; i++) {
            w[i] = (long[]) ol[i];
        }
        return w;
    }

    public int nextSplitIndex() throws IOException {
        int e = -1;
        int e0 = -1;
        int tt = this.tok.nextToken();
        if (tt == 124) {
            if (this.debug) {
                logger.debug("split index");
            }
            if (this.tok.nextToken() == -1) {
                return -1;
            }
            if (this.tok.sval != null) {
                if (digit(this.tok.sval.charAt(0))) {
                    e = Integer.parseInt(this.tok.sval);
                }
                if (this.tok.nextToken() != 124) {
                    this.tok.pushBack();
                }
            }
        } else if (tt == 91) {
            if (this.debug) {
                logger.debug("split index");
            }
            if (this.tok.nextToken() == -1) {
                return -1;
            }
            if (this.tok.sval != null) {
                if (digit(this.tok.sval.charAt(0))) {
                    e0 = Integer.parseInt(this.tok.sval);
                }
                if (this.tok.nextToken() == 44) {
                    tt = this.tok.nextToken();
                    if (tt == -1) {
                        return e0;
                    }
                    if (this.tok.sval != null && digit(this.tok.sval.charAt(0))) {
                        e = Integer.parseInt(this.tok.sval);
                    }
                    if (tt != 93) {
                        this.tok.pushBack();
                    }
                }
            }
        } else {
            this.tok.pushBack();
        }
        return e;
    }

    public TermOrder nextTermOrder() throws IOException {
        int evord = 4;
        int tt = this.tok.nextToken();
        if (tt != -1) {
            if (tt != -3) {
                this.tok.pushBack();
            } else if (this.tok.sval != null) {
                if (this.tok.sval.equalsIgnoreCase("L")) {
                    evord = 2;
                } else if (this.tok.sval.equalsIgnoreCase("IL")) {
                    evord = 2;
                } else if (this.tok.sval.equalsIgnoreCase("INVLEX")) {
                    evord = 2;
                } else if (this.tok.sval.equalsIgnoreCase("LEX")) {
                    evord = 1;
                } else if (this.tok.sval.equalsIgnoreCase("G")) {
                    evord = 4;
                } else if (this.tok.sval.equalsIgnoreCase("IG")) {
                    evord = 4;
                } else if (this.tok.sval.equalsIgnoreCase("IGRLEX")) {
                    evord = 4;
                } else if (this.tok.sval.equalsIgnoreCase("GRLEX")) {
                    evord = 3;
                } else if (this.tok.sval.equalsIgnoreCase("W")) {
                    return new TermOrder(nextWeightArray());
                }
            }
        }
        int s = nextSplitIndex();
        if (s <= 0) {
            return new TermOrder(evord);
        }
        return new TermOrder(evord, evord, this.nvars, s);
    }

    public List<GenPolynomial> nextPolynomialList() throws IOException {
        List<GenPolynomial> L = new ArrayList();
        int tt = this.tok.nextToken();
        if (tt != -1 && tt == 40) {
            logger.debug("polynomial list");
            while (true) {
                tt = this.tok.nextToken();
                if (this.tok.ttype != 44) {
                    GenPolynomial a;
                    if (tt == 40) {
                        a = nextPolynomial();
                        tt = this.tok.nextToken();
                        if (this.tok.ttype != 41) {
                            this.tok.pushBack();
                        }
                    } else {
                        this.tok.pushBack();
                        a = nextPolynomial();
                    }
                    logger.info("next pol = " + a);
                    L.add(a);
                    if (this.tok.ttype == -1) {
                        break;
                    } else if (this.tok.ttype == 41) {
                        break;
                    }
                }
            }
        }
        return L;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<java.util.List<edu.jas.poly.GenPolynomial>> nextSubModuleList() throws java.io.IOException {
        /*
        r8 = this;
        r7 = 40;
        r6 = -1;
        r0 = new java.util.ArrayList;
        r0.<init>();
        r3 = r8.tok;
        r1 = r3.nextToken();
        if (r1 != r6) goto L_0x0011;
    L_0x0010:
        return r0;
    L_0x0011:
        if (r1 != r7) goto L_0x0010;
    L_0x0013:
        r3 = logger;
        r4 = "module list";
        r3.debug(r4);
        r2 = 0;
    L_0x001b:
        r3 = r8.tok;
        r1 = r3.nextToken();
        r3 = r8.tok;
        r3 = r3.ttype;
        r4 = 44;
        if (r3 == r4) goto L_0x001b;
    L_0x0029:
        r3 = r8.tok;
        r3 = r3.ttype;
        r4 = 41;
        if (r3 == r4) goto L_0x0010;
    L_0x0031:
        r3 = r8.tok;
        r3 = r3.ttype;
        if (r3 == r6) goto L_0x0010;
    L_0x0037:
        if (r1 != r7) goto L_0x001b;
    L_0x0039:
        r3 = r8.tok;
        r3.pushBack();
        r2 = r8.nextPolynomialList();
        r3 = logger;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "next vect = ";
        r4 = r4.append(r5);
        r4 = r4.append(r2);
        r4 = r4.toString();
        r3.info(r4);
        r0.add(r2);
        goto L_0x001b;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.poly.GenPolynomialTokenizer.nextSubModuleList():java.util.List<java.util.List<edu.jas.poly.GenPolynomial>>");
    }

    public void nextRelationTable() throws IOException {
        if (this.spfac != null) {
            RelationTable table = this.spfac.table;
            List<GenPolynomial> rels = null;
            int tt = this.tok.nextToken();
            if (this.debug) {
                logger.debug("start relation table: " + tt);
            }
            if (this.tok.sval != null && this.tok.sval.equalsIgnoreCase("RelationTable")) {
                rels = nextPolynomialList();
            }
            if (rels == null) {
                this.tok.pushBack();
                return;
            }
            Iterator<GenPolynomial> it = rels.iterator();
            while (it.hasNext()) {
                ExpVector e = ((GenPolynomial) it.next()).leadingExpVector();
                if (it.hasNext()) {
                    ExpVector f = ((GenPolynomial) it.next()).leadingExpVector();
                    if (it.hasNext()) {
                        table.update(e, f, new GenSolvablePolynomial(this.spfac, ((GenPolynomial) it.next()).val));
                    }
                }
            }
            if (this.debug) {
                logger.info("table = " + table);
            }
        }
    }

    public PolynomialList nextPolynomialSet() throws IOException {
        RingFactory coeff = nextCoefficientRing();
        logger.info("coeff = " + coeff.getClass().getSimpleName());
        this.vars = nextVariableList();
        logger.info("vars = " + Arrays.toString(this.vars));
        if (this.vars != null) {
            this.nvars = this.vars.length;
        }
        this.tord = nextTermOrder();
        logger.info("tord = " + this.tord);
        initFactory(coeff, this.parsedCoeff);
        List s = nextPolynomialList();
        logger.info("s = " + s);
        return new PolynomialList(this.pfac, s);
    }

    public ModuleList nextSubModuleSet() throws IOException {
        RingFactory coeff = nextCoefficientRing();
        logger.info("coeff = " + coeff.getClass().getSimpleName());
        this.vars = nextVariableList();
        logger.info("vars = " + Arrays.toString(this.vars));
        if (this.vars != null) {
            this.nvars = this.vars.length;
        }
        this.tord = nextTermOrder();
        logger.info("tord = " + this.tord);
        initFactory(coeff, this.parsedCoeff);
        List m = nextSubModuleList();
        logger.info("m = " + m);
        return new ModuleList(this.pfac, m);
    }

    public List<GenSolvablePolynomial> nextSolvablePolynomialList() throws IOException {
        List<GenPolynomial> s = nextPolynomialList();
        logger.info("s = " + s);
        List<GenSolvablePolynomial> sp = new ArrayList(s.size());
        for (GenPolynomial p : s) {
            sp.add(new GenSolvablePolynomial(this.spfac, p.val));
        }
        return sp;
    }

    public GenSolvablePolynomial nextSolvablePolynomial() throws IOException {
        GenPolynomial p = nextPolynomial();
        logger.info("p = " + p);
        return new GenSolvablePolynomial(this.spfac, p.val);
    }

    public PolynomialList nextSolvablePolynomialSet() throws IOException {
        RingFactory coeff = nextCoefficientRing();
        logger.info("coeff = " + coeff.getClass().getSimpleName());
        this.vars = nextVariableList();
        logger.info("vars = " + Arrays.toString(this.vars));
        if (this.vars != null) {
            this.nvars = this.vars.length;
        }
        this.tord = nextTermOrder();
        logger.info("tord = " + this.tord);
        initFactory(coeff, this.parsedCoeff);
        initSolvableFactory(coeff, this.parsedCoeff);
        nextRelationTable();
        if (logger.isInfoEnabled()) {
            logger.info("table = " + this.table);
        }
        List s = nextSolvablePolynomialList();
        logger.info("s = " + s);
        return new PolynomialList(this.spfac, s);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<java.util.List<edu.jas.poly.GenSolvablePolynomial>> nextSolvableSubModuleList() throws java.io.IOException {
        /*
        r8 = this;
        r7 = 40;
        r6 = -1;
        r0 = new java.util.ArrayList;
        r0.<init>();
        r3 = r8.tok;
        r1 = r3.nextToken();
        if (r1 != r6) goto L_0x0011;
    L_0x0010:
        return r0;
    L_0x0011:
        if (r1 != r7) goto L_0x0010;
    L_0x0013:
        r3 = logger;
        r4 = "module list";
        r3.debug(r4);
        r2 = 0;
    L_0x001b:
        r3 = r8.tok;
        r1 = r3.nextToken();
        r3 = r8.tok;
        r3 = r3.ttype;
        r4 = 44;
        if (r3 == r4) goto L_0x001b;
    L_0x0029:
        r3 = r8.tok;
        r3 = r3.ttype;
        r4 = 41;
        if (r3 == r4) goto L_0x0010;
    L_0x0031:
        r3 = r8.tok;
        r3 = r3.ttype;
        if (r3 == r6) goto L_0x0010;
    L_0x0037:
        if (r1 != r7) goto L_0x001b;
    L_0x0039:
        r3 = r8.tok;
        r3.pushBack();
        r2 = r8.nextSolvablePolynomialList();
        r3 = logger;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "next vect = ";
        r4 = r4.append(r5);
        r4 = r4.append(r2);
        r4 = r4.toString();
        r3.info(r4);
        r0.add(r2);
        goto L_0x001b;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.poly.GenPolynomialTokenizer.nextSolvableSubModuleList():java.util.List<java.util.List<edu.jas.poly.GenSolvablePolynomial>>");
    }

    public ModuleList nextSolvableSubModuleSet() throws IOException {
        RingFactory coeff = nextCoefficientRing();
        logger.info("coeff = " + coeff.getClass().getSimpleName());
        this.vars = nextVariableList();
        logger.info("vars = " + Arrays.toString(this.vars));
        if (this.vars != null) {
            this.nvars = this.vars.length;
        }
        this.tord = nextTermOrder();
        logger.info("tord = " + this.tord);
        initFactory(coeff, this.parsedCoeff);
        initSolvableFactory(coeff, this.parsedCoeff);
        nextRelationTable();
        if (logger.isInfoEnabled()) {
            logger.info("table = " + this.table);
        }
        List<List<GenSolvablePolynomial>> s = nextSolvableSubModuleList();
        logger.info("s = " + s);
        return new OrderedModuleList(this.spfac, s);
    }

    static boolean digit(char x) {
        return '0' <= x && x <= '9';
    }

    static boolean letter(char x) {
        return ('a' <= x && x <= 'z') || ('A' <= x && x <= 'Z');
    }

    public void nextComma() throws IOException {
        if (this.tok.ttype == 44) {
            int tt = this.tok.nextToken();
            if (this.debug) {
                logger.debug("after comma: " + tt);
            }
        }
    }

    public static String[] variableList(String s) {
        if (s == null) {
            return null;
        }
        String st = s.trim();
        if (st.length() == 0) {
            return new String[0];
        }
        if (st.charAt(0) == Constants.LEFT_PAREN) {
            st = st.substring(1);
        }
        if (st.charAt(st.length() - 1) == Constants.RIGHT_PAREN) {
            st = st.substring(0, st.length() - 1);
        }
        st = st.replaceAll(",", " ");
        List<String> sl = new ArrayList();
        Scanner sc = new Scanner(st);
        while (sc.hasNext()) {
            sl.add(sc.next());
        }
        sc.close();
        String[] vl = new String[sl.size()];
        int i = 0;
        for (String si : sl) {
            vl[i] = si;
            i++;
        }
        return vl;
    }

    public static String[] expressionVariables(String s) {
        if (s == null) {
            return null;
        }
        String st = s.trim();
        if (st.length() == 0) {
            return new String[0];
        }
        int i;
        st = st.replaceAll(",", " ").replaceAll("\\+", " ").replaceAll("-", " ").replaceAll("\\*", " ").replaceAll("/", " ").replaceAll("\\(", " ").replaceAll("\\)", " ").replaceAll("\\{", " ").replaceAll("\\}", " ").replaceAll("\\[", " ").replaceAll("\\]", " ").replaceAll("\\^", " ");
        Set<String> sl = new TreeSet();
        Scanner sc = new Scanner(st);
        while (sc.hasNext()) {
            String sn = sc.next();
            if (!(sn == null || sn.length() == 0)) {
                i = 0;
                while (digit(sn.charAt(i)) && i < sn.length() - 1) {
                    i++;
                }
                if (i > 0) {
                    sn = sn.substring(i, sn.length());
                }
                if (sn.length() != 0 && letter(sn.charAt(0))) {
                    sl.add(sn);
                }
            }
        }
        sc.close();
        String[] vl = new String[sl.size()];
        i = 0;
        for (String si : sl) {
            vl[i] = si;
            i++;
        }
        return vl;
    }
}
