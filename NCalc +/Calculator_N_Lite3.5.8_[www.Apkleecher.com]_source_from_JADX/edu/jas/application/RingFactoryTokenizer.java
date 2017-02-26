package edu.jas.application;

import android.support.v4.view.MotionEventCompat;
import com.example.duy.calculator.math_eval.Constants;
import edu.jas.arith.BigComplex;
import edu.jas.arith.BigDecimal;
import edu.jas.arith.BigInteger;
import edu.jas.arith.BigQuaternion;
import edu.jas.arith.BigRational;
import edu.jas.arith.ModInteger;
import edu.jas.arith.ModIntegerRing;
import edu.jas.arith.ModLongRing;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.GenPolynomialTokenizer;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.poly.GenSolvablePolynomialRing;
import edu.jas.poly.InvalidExpressionException;
import edu.jas.poly.RelationTable;
import edu.jas.poly.TermOrder;
import edu.jas.structure.RingFactory;
import edu.jas.ufd.QuotientRing;
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
import org.apache.commons.math4.random.ValueServer;
import org.apache.log4j.Logger;
import org.matheclipse.core.interfaces.IExpr;

public class RingFactoryTokenizer {
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
        static final /* synthetic */ int[] $SwitchMap$edu$jas$application$RingFactoryTokenizer$coeffType;

        static {
            $SwitchMap$edu$jas$application$RingFactoryTokenizer$coeffType = new int[coeffType.values().length];
            try {
                $SwitchMap$edu$jas$application$RingFactoryTokenizer$coeffType[coeffType.BigRat.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$edu$jas$application$RingFactoryTokenizer$coeffType[coeffType.BigInt.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$edu$jas$application$RingFactoryTokenizer$coeffType[coeffType.ModInt.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$edu$jas$application$RingFactoryTokenizer$coeffType[coeffType.BigC.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$edu$jas$application$RingFactoryTokenizer$coeffType[coeffType.BigQ.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$edu$jas$application$RingFactoryTokenizer$coeffType[coeffType.BigD.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$edu$jas$application$RingFactoryTokenizer$coeffType[coeffType.RatFunc.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$edu$jas$application$RingFactoryTokenizer$coeffType[coeffType.ModFunc.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$edu$jas$application$RingFactoryTokenizer$coeffType[coeffType.IntFunc.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
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
        RatFunc,
        ModFunc,
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
        PolRatFunc,
        PolModFunc,
        PolIntFunc
    }

    static {
        logger = Logger.getLogger(RingFactoryTokenizer.class);
    }

    public RingFactoryTokenizer() {
        this(new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF8"))));
    }

    public RingFactoryTokenizer(GenPolynomialRing rf, Reader r) {
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
            this.vars = rf.getVars();
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

    public RingFactoryTokenizer(Reader r) {
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
        switch (1.$SwitchMap$edu$jas$application$RingFactoryTokenizer$coeffType[ct.ordinal()]) {
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
                this.parsedPoly = polyType.PolRatFunc;
            case IExpr.INTEGERID /*8*/:
                this.pfac = new GenPolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolModFunc;
            case R.styleable.TextAppearance_textAllCaps /*9*/:
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
        switch (1.$SwitchMap$edu$jas$application$RingFactoryTokenizer$coeffType[ct.ordinal()]) {
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
                this.parsedPoly = polyType.PolRatFunc;
            case IExpr.INTEGERID /*8*/:
                this.spfac = new GenSolvablePolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolModFunc;
            case R.styleable.TextAppearance_textAllCaps /*9*/:
                this.spfac = new GenSolvablePolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolIntFunc;
            default:
                this.spfac = new GenSolvablePolynomialRing(this.fac, this.nvars, this.tord, this.vars);
                this.parsedPoly = polyType.PolBigRat;
        }
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
                                                String[] rfv;
                                                int vr;
                                                if (this.tok.sval.equalsIgnoreCase("RatFunc")) {
                                                    rfv = nextVariableList();
                                                    vr = rfv.length;
                                                    ringFactory = new QuotientRing(new GenPolynomialRing(new BigInteger(), vr, new TermOrder(2), rfv));
                                                    ct = coeffType.RatFunc;
                                                } else {
                                                    if (this.tok.sval.equalsIgnoreCase("ModFunc")) {
                                                        tt = this.tok.nextToken();
                                                        RingFactory mi = new ModIntegerRing("19");
                                                        if (this.tok.sval != null) {
                                                            if (this.tok.sval.length() > 0) {
                                                                if (digit(this.tok.sval.charAt(0))) {
                                                                    mi = new ModIntegerRing(this.tok.sval);
                                                                } else {
                                                                    this.tok.pushBack();
                                                                }
                                                                rfv = nextVariableList();
                                                                ringFactory = new QuotientRing(new GenPolynomialRing(mi, rfv.length, new TermOrder(2), rfv));
                                                                ct = coeffType.ModFunc;
                                                            }
                                                        }
                                                        this.tok.pushBack();
                                                        rfv = nextVariableList();
                                                        ringFactory = new QuotientRing(new GenPolynomialRing(mi, rfv.length, new TermOrder(2), rfv));
                                                        ct = coeffType.ModFunc;
                                                    } else {
                                                        if (this.tok.sval.equalsIgnoreCase("IntFunc")) {
                                                            rfv = nextVariableList();
                                                            vr = rfv.length;
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
                                                                            mod = new GenPolynomialTokenizer(this.pfac, this.reader).nextPolynomial();
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
                                                                        mod = new GenPolynomialTokenizer(this.pfac, this.reader).nextPolynomial();
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

    public void nextRelationTable() throws IOException {
        if (this.spfac != null) {
            RelationTable table = this.spfac.table;
            List<GenPolynomial> rels = null;
            int tt = this.tok.nextToken();
            if (this.debug) {
                logger.debug("start relation table: " + tt);
            }
            if (this.tok.sval != null && this.tok.sval.equalsIgnoreCase("RelationTable")) {
                rels = new GenPolynomialTokenizer(this.pfac, this.reader).nextPolynomialList();
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
                        GenPolynomial p = (GenPolynomial) it.next();
                        GenSolvablePolynomial sp = new GenSolvablePolynomial(this.spfac);
                        sp.doPutToMap(p.getMap());
                        table.update(e, f, sp);
                    }
                }
            }
            if (this.debug) {
                logger.info("table = " + table);
            }
        }
    }

    public GenPolynomialRing nextPolynomialRing() throws IOException {
        RingFactory coeff = nextCoefficientRing();
        logger.info("coeff = " + coeff);
        this.vars = nextVariableList();
        logger.info("vars = " + Arrays.toString(this.vars));
        if (this.vars != null) {
            this.nvars = this.vars.length;
        }
        this.tord = nextTermOrder();
        logger.info("tord = " + this.tord);
        initFactory(coeff, this.parsedCoeff);
        return this.pfac;
    }

    public GenSolvablePolynomialRing nextSolvablePolynomialRing() throws IOException {
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
            logger.info("table = " + this.table + ", tok = " + this.tok);
        }
        return this.spfac;
    }

    static boolean digit(char x) {
        return '0' <= x && x <= '9';
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
}
