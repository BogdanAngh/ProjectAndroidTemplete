package com.example.duy.calculator.math_eval;

import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;
import com.example.duy.calculator.R;
import com.example.duy.calculator.math_eval.LogicEvaluator.EvaluateCallback;
import io.github.kexanie.library.BuildConfig;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.commons.math4.geometry.VectorFormat;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.eval.TeXUtilities;
import org.matheclipse.core.interfaces.AbstractEvalStepListener;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.parser.client.SyntaxError;
import org.matheclipse.parser.client.math.MathException;

public class BigEvaluator extends LogicEvaluator {
    private static final String TAG;
    private boolean debug;
    private EvalEngine engine;
    private boolean isFraction;
    private boolean isUseDegree;
    private int numDecimal;
    private DecimalFormat numberFormat;
    private SharedPreferences sharedPreferences;
    private TeXUtilities teXUtilities;
    private ExprEvaluator util;

    class 1 implements Runnable {
        1() {
        }

        public void run() {
            BigEvaluator.this.printLog("test started");
            BigEvaluator.this.test();
        }
    }

    class 2 implements EvaluateCallback {
        final /* synthetic */ EvaluateCallback val$callback;
        final /* synthetic */ String val$input;

        2(String str, EvaluateCallback evaluateCallback) {
            this.val$input = str;
            this.val$callback = evaluateCallback;
        }

        public void onEvaluate(String expr, String result, int errorResourceId) {
            if (errorResourceId == 1) {
                this.val$callback.onEvaluate(expr, (((BigEvaluator.this.getTokenizer().getContext().getString(R.string.eval) + ":") + ("$$" + this.val$input + "$$") + "<hr>" + BigEvaluator.this.getStepByStep("Simplify(" + expr + ")")) + BigEvaluator.this.getTokenizer().getContext().getString(R.string.result) + ":") + BigEvaluator.this.convertToTex(result), errorResourceId);
                return;
            }
            this.val$callback.onEvaluate(expr, result, errorResourceId);
        }
    }

    class 3 extends AbstractEvalStepListener {
        final /* synthetic */ ArrayList val$arrayList;
        final /* synthetic */ boolean[] val$find;
        final /* synthetic */ String[] val$res;

        3(boolean[] zArr, String[] strArr, ArrayList arrayList) {
            this.val$find = zArr;
            this.val$res = strArr;
            this.val$arrayList = arrayList;
        }

        public void add(IExpr inputExpr, IExpr resultExpr, int recursionDepth, long iterationCounter, String hint) {
            BigEvaluator.this.printLog("test: Depth " + recursionDepth + " Iteration " + iterationCounter + ": " + inputExpr.toString() + " = " + resultExpr.toString() + " | " + hint);
            if (!this.val$find[0]) {
                if (inputExpr.toString().toLowerCase().contains("Simplify".toLowerCase()) || inputExpr.toString().toLowerCase().contains("ExpandAll".toLowerCase()) || inputExpr.toString().toLowerCase().contains("Together".toLowerCase()) || inputExpr.toString().toLowerCase().contains(Constants.FACTOR.toLowerCase()) || inputExpr.toString().toLowerCase().contains("Times".toLowerCase()) || inputExpr.toString().toLowerCase().contains("Apart".toLowerCase())) {
                    this.val$find[0] = true;
                    return;
                }
                if (recursionDepth <= 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    String[] strArr = this.val$res;
                    strArr[0] = stringBuilder.append(strArr[0]).append(BigEvaluator.this.convertToTex(resultExpr.toString())).append("<hr>").toString();
                    Log.d(BigEvaluator.TAG, "add: 3 " + BigEvaluator.this.convertToTex(resultExpr.toString()) + "<hr>");
                }
                this.val$arrayList.add(new ItemStep(inputExpr, resultExpr, recursionDepth, iterationCounter, hint));
            }
        }
    }

    class 4 implements EvaluateCallback {
        final /* synthetic */ EvaluateCallback val$callback;
        final /* synthetic */ boolean[] val$next;
        final /* synthetic */ String[] val$res;

        4(String[] strArr, boolean[] zArr, EvaluateCallback evaluateCallback) {
            this.val$res = strArr;
            this.val$next = zArr;
            this.val$callback = evaluateCallback;
        }

        public void onEvaluate(String expr0, String result0, int errorResourceId0) {
            if (errorResourceId0 == 1) {
                StringBuilder stringBuilder = new StringBuilder();
                String[] strArr = this.val$res;
                strArr[0] = stringBuilder.append(strArr[0]).append(BigEvaluator.this.convertToTex(result0)).toString();
                this.val$next[0] = true;
                this.val$res[1] = result0;
                return;
            }
            this.val$callback.onEvaluate(expr0, result0, -1);
        }
    }

    class 5 implements EvaluateCallback {
        final /* synthetic */ String[] val$res;

        5(String[] strArr) {
            this.val$res = strArr;
        }

        public void onEvaluate(String expr1, String result1, int errorResourceId1) {
            StringBuilder stringBuilder = new StringBuilder();
            String[] strArr = this.val$res;
            strArr[0] = stringBuilder.append(strArr[0]).append("<hr>").append(BigEvaluator.this.convertToTex(result1)).toString();
        }
    }

    class 6 implements EvaluateCallback {
        final /* synthetic */ EvaluateCallback val$callback;
        final /* synthetic */ String val$finalExpr;

        class 1 implements EvaluateCallback {
            final /* synthetic */ String val$finalRes;

            1(String str) {
                this.val$finalRes = str;
            }

            public void onEvaluate(String expr, String result, int errorResourceId) {
                6.this.val$callback.onEvaluate(expr, this.val$finalRes + "<hr>" + BigEvaluator.this.convertToTex(result), errorResourceId);
            }
        }

        6(String str, EvaluateCallback evaluateCallback) {
            this.val$finalExpr = str;
            this.val$callback = evaluateCallback;
        }

        public void onEvaluate(String expr0, String result0, int errorResourceId0) {
            if (errorResourceId0 == 1) {
                String finalRes = BigEvaluator.this.convertToTex(result0);
                BigEvaluator.this.setFraction(false);
                BigEvaluator.this.getResult(this.val$finalExpr, new 1(finalRes));
                return;
            }
            this.val$callback.onEvaluate(expr0, result0, -1);
        }
    }

    class 7 implements EvaluateCallback {
        final /* synthetic */ String[] val$res;

        7(String[] strArr) {
            this.val$res = strArr;
        }

        public void onEvaluate(String expr, String result, int errorResourceId) {
            this.val$res[0] = result;
        }
    }

    class 8 implements EvaluateCallback {
        8() {
        }

        public void onEvaluate(String expr, String result, int errorResourceId) {
            Log.d(BigEvaluator.TAG, "onEvaluate: " + expr + " = " + result);
        }
    }

    class 9 implements EvaluateCallback {
        final /* synthetic */ String[] val$res;

        9(String[] strArr) {
            this.val$res = strArr;
        }

        public void onEvaluate(String expr, String result, int errorResourceId) {
            if (errorResourceId == 1) {
                this.val$res[0] = BuildConfig.FLAVOR;
            } else {
                this.val$res[0] = result;
            }
        }
    }

    private class ItemStep {
        private String hint;
        private IExpr inputExpr;
        private long iterationCounter;
        private int recursionDepth;
        private IExpr resultExpr;

        public ItemStep(IExpr inputExpr, IExpr resultExpr, int recursionDepth, long iterationCounter, String hint) {
            this.inputExpr = inputExpr;
            this.resultExpr = resultExpr;
            this.recursionDepth = recursionDepth;
            this.iterationCounter = iterationCounter;
            this.hint = hint;
        }

        public IExpr getResultExpr() {
            return this.resultExpr;
        }

        public void setResultExpr(IExpr resultExpr) {
            this.resultExpr = resultExpr;
        }

        public int getRecursionDepth() {
            return this.recursionDepth;
        }

        public void setRecursionDepth(int recursionDepth) {
            this.recursionDepth = recursionDepth;
        }

        public long getIterationCounter() {
            return this.iterationCounter;
        }

        public void setIterationCounter(long iterationCounter) {
            this.iterationCounter = iterationCounter;
        }

        public String getHint() {
            return this.hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public IExpr getInputExpr() {
            return this.inputExpr;
        }

        public void setInputExpr(IExpr inputExpr) {
            this.inputExpr = inputExpr;
        }
    }

    static {
        TAG = BigEvaluator.class.getSimpleName();
    }

    public BigEvaluator(Tokenizer tokenizer) {
        super(tokenizer);
        this.numDecimal = 6;
        this.isUseDegree = false;
        this.isFraction = true;
        this.debug = false;
        this.numberFormat = new DecimalFormat("#.#########");
        this.util = new ExprEvaluator();
        this.engine = this.util.getEvalEngine();
        this.teXUtilities = new TeXUtilities(this.engine, true);
        addFunction();
        if (this.debug) {
            new Thread(new 1()).start();
        }
    }

    public BigEvaluator getEvaluator() {
        return this;
    }

    private void addFunction() {
        Log.d(TAG, "addFunction: ");
        try {
            this.util.evaluate("C(n_NumberQ, k_NumberQ):=(factorial(Ceiling(n)) / (factorial(Ceiling(k)) * factorial(Ceiling(n - k))))");
            this.util.evaluate("P(n_NumberQ, k_NumberQ):=(factorial(Ceiling(n)) / (factorial(Ceiling(n - k))))");
            this.util.evaluate("Cbrt(x_NumberQ):= x ^ (1/3)");
            this.util.evaluate("cbrt(x_NumberQ):= x ^ (1/3)");
            this.util.evaluate("Ceil(x_NumberQ):= Ceiling(x)");
            this.util.evaluate("ceil(x_NumberQ):= Ceiling(x)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void evaluate(String expr, EvaluateCallback callback) {
        getResult(expr.replace("ans", "($ans=" + this.util.getVariable("ans") + ")"), callback);
    }

    public String cleanExpression(String expr) {
        return FormatExpression.appendParenthesis(expr);
    }

    public ExprEvaluator getUtil() {
        return this.util;
    }

    public EvalEngine getEngine() {
        return this.engine;
    }

    public boolean isDebug() {
        return this.debug;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void getResult(java.lang.String r10, com.example.duy.calculator.math_eval.LogicEvaluator.EvaluateCallback r11) {
        /*
        r9 = this;
        r7 = 2;
        r8 = -1;
        r5 = "?";
        r6 = "";
        r10 = r10.replace(r5, r6);
        r5 = r9.getTokenizer();
        r10 = r5.getNormalExpression(r10);
        r10 = com.example.duy.calculator.math_eval.FormatExpression.appendParenthesis(r10);
        r5 = r10.isEmpty();
        if (r5 == 0) goto L_0x0021;
    L_0x001c:
        r5 = "";
        r11.onEvaluate(r10, r5, r7);
    L_0x0021:
        r5 = r9.isFraction;	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        if (r5 != 0) goto L_0x01bc;
    L_0x0025:
        r5 = r9.util;	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6 = new java.lang.StringBuilder;	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6.<init>();	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r7 = "N(";
        r6 = r6.append(r7);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6 = r6.append(r10);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r7 = ")";
        r6 = r6.append(r7);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6 = r6.toString();	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r3 = r5.evaluate(r6);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r5 = r9.debug;	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        if (r5 == 0) goto L_0x006a;
    L_0x0048:
        r5 = TAG;	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6 = new java.lang.StringBuilder;	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6.<init>();	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r7 = "evaluate: res ";
        r6 = r6.append(r7);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r7 = r3.toString();	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6 = r6.append(r7);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r7 = " real";
        r6 = r6.append(r7);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6 = r6.toString();	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        android.util.Log.d(r5, r6);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
    L_0x006a:
        r5 = r3.isNumeric();	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        if (r5 != 0) goto L_0x007c;
    L_0x0070:
        r5 = r3.isNumber();	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        if (r5 != 0) goto L_0x007c;
    L_0x0076:
        r5 = r3.isSignedNumber();	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        if (r5 == 0) goto L_0x014c;
    L_0x007c:
        r5 = r9.debug;	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        if (r5 == 0) goto L_0x0087;
    L_0x0080:
        r5 = TAG;	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6 = "getResult: is numberic";
        android.util.Log.d(r5, r6);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
    L_0x0087:
        r5 = TAG;	 Catch:{ Exception -> 0x00b0, SyntaxError -> 0x00bd, MathException -> 0x0176 }
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00b0, SyntaxError -> 0x00bd, MathException -> 0x0176 }
        r6.<init>();	 Catch:{ Exception -> 0x00b0, SyntaxError -> 0x00bd, MathException -> 0x0176 }
        r7 = "getResult: ";
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x00b0, SyntaxError -> 0x00bd, MathException -> 0x0176 }
        r7 = r3.toString();	 Catch:{ Exception -> 0x00b0, SyntaxError -> 0x00bd, MathException -> 0x0176 }
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x00b0, SyntaxError -> 0x00bd, MathException -> 0x0176 }
        r6 = r6.toString();	 Catch:{ Exception -> 0x00b0, SyntaxError -> 0x00bd, MathException -> 0x0176 }
        android.util.Log.d(r5, r6);	 Catch:{ Exception -> 0x00b0, SyntaxError -> 0x00bd, MathException -> 0x0176 }
        r5 = r3.toString();	 Catch:{ Exception -> 0x00b0, SyntaxError -> 0x00bd, MathException -> 0x0176 }
        r5 = r9.doFormatNumber(r5);	 Catch:{ Exception -> 0x00b0, SyntaxError -> 0x00bd, MathException -> 0x0176 }
        r6 = 1;
        r11.onEvaluate(r10, r5, r6);	 Catch:{ Exception -> 0x00b0, SyntaxError -> 0x00bd, MathException -> 0x0176 }
    L_0x00af:
        return;
    L_0x00b0:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r5 = r3.toString();	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6 = 1;
        r11.onEvaluate(r10, r5, r6);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        goto L_0x00af;
    L_0x00bd:
        r0 = move-exception;
        r5 = r9.debug;
        if (r5 == 0) goto L_0x00c5;
    L_0x00c2:
        r0.printStackTrace();
    L_0x00c5:
        r3 = r0.getCurrentLine();
        r2 = r0.getStartOffset();
        r5 = TAG;
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r7 = "getResult: index ";
        r6 = r6.append(r7);
        r6 = r6.append(r2);
        r7 = " line: ";
        r6 = r6.append(r7);
        r6 = r6.append(r3);
        r6 = r6.toString();
        android.util.Log.d(r5, r6);
        r5 = r3.length();	 Catch:{ Exception -> 0x0200 }
        if (r5 <= 0) goto L_0x01fc;
    L_0x00f5:
        r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0200 }
        r5.<init>();	 Catch:{ Exception -> 0x0200 }
        r6 = 0;
        r6 = r3.substring(r6, r2);	 Catch:{ Exception -> 0x0200 }
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x0200 }
        r6 = "?";
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x0200 }
        r6 = " ";
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x0200 }
        r6 = r3.length();	 Catch:{ Exception -> 0x0200 }
        r6 = r3.substring(r2, r6);	 Catch:{ Exception -> 0x0200 }
        r5 = r5.append(r6);	 Catch:{ Exception -> 0x0200 }
        r3 = r5.toString();	 Catch:{ Exception -> 0x0200 }
    L_0x011f:
        r5 = r9.isFraction;	 Catch:{ Exception -> 0x0200 }
        if (r5 != 0) goto L_0x012e;
    L_0x0123:
        r5 = 2;
        r6 = r3.length();	 Catch:{ Exception -> 0x0200 }
        r6 = r6 + -1;
        r3 = r3.substring(r5, r6);	 Catch:{ Exception -> 0x0200 }
    L_0x012e:
        r5 = TAG;	 Catch:{ Exception -> 0x0200 }
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0200 }
        r6.<init>();	 Catch:{ Exception -> 0x0200 }
        r7 = "getResult: ";
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x0200 }
        r6 = r6.append(r3);	 Catch:{ Exception -> 0x0200 }
        r6 = r6.toString();	 Catch:{ Exception -> 0x0200 }
        android.util.Log.d(r5, r6);	 Catch:{ Exception -> 0x0200 }
    L_0x0146:
        r5 = 3;
        r11.onEvaluate(r10, r3, r5);
        goto L_0x00af;
    L_0x014c:
        r5 = r9.debug;	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        if (r5 == 0) goto L_0x016c;
    L_0x0150:
        r5 = TAG;	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6 = new java.lang.StringBuilder;	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6.<init>();	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r7 = "getResult: ";
        r6 = r6.append(r7);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r7 = r3.toString();	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6 = r6.append(r7);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6 = r6.toString();	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        android.util.Log.d(r5, r6);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
    L_0x016c:
        r5 = r3.toString();	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6 = 1;
        r11.onEvaluate(r10, r5, r6);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        goto L_0x00af;
    L_0x0176:
        r0 = move-exception;
        r5 = r9.debug;
        if (r5 == 0) goto L_0x017e;
    L_0x017b:
        r0.printStackTrace();
    L_0x017e:
        r5 = new java.lang.StringBuilder;
        r5.<init>();
        r6 = "<h3>";
        r5 = r5.append(r6);
        r6 = 2131230953; // 0x7f0800e9 float:1.8077973E38 double:1.052967997E-314;
        r6 = r9.getString(r6);
        r5 = r5.append(r6);
        r6 = "</h3>";
        r5 = r5.append(r6);
        r6 = 2131231031; // 0x7f080137 float:1.8078132E38 double:1.052968036E-314;
        r6 = r9.getString(r6);
        r5 = r5.append(r6);
        r6 = "</br>";
        r5 = r5.append(r6);
        r6 = r0.getMessage();
        r5 = r5.append(r6);
        r4 = r5.toString();
        r11.onEvaluate(r10, r4, r8);
        goto L_0x00af;
    L_0x01bc:
        r5 = r9.util;	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r3 = r5.evaluate(r10);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r5 = r9.debug;	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        if (r5 == 0) goto L_0x01e8;
    L_0x01c6:
        r5 = TAG;	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6 = new java.lang.StringBuilder;	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6.<init>();	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r7 = "evaluate: res";
        r6 = r6.append(r7);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r7 = r3.toString();	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6 = r6.append(r7);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r7 = " fraction";
        r6 = r6.append(r7);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6 = r6.toString();	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        android.util.Log.d(r5, r6);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
    L_0x01e8:
        r5 = r3.toString();	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        r6 = 1;
        r11.onEvaluate(r10, r5, r6);	 Catch:{ SyntaxError -> 0x00bd, MathException -> 0x0176, Exception -> 0x01f2 }
        goto L_0x00af;
    L_0x01f2:
        r0 = move-exception;
        r5 = r0.getMessage();
        r11.onEvaluate(r10, r5, r8);
        goto L_0x00af;
    L_0x01fc:
        r3 = "";
        goto L_0x011f;
    L_0x0200:
        r1 = move-exception;
        r5 = r9.debug;
        if (r5 == 0) goto L_0x0146;
    L_0x0205:
        r1.printStackTrace();
        goto L_0x0146;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.duy.calculator.math_eval.BigEvaluator.getResult(java.lang.String, com.example.duy.calculator.math_eval.LogicEvaluator$EvaluateCallback):void");
    }

    public String getString(int error) {
        return this.mTokenizer.getContext().getString(error);
    }

    public String getResult(String expression) {
        expression = cleanExpression(expression);
        try {
            if (this.isFraction) {
                IExpr res = this.util.evaluate(expression);
                if (this.debug) {
                    Log.d(TAG, "evaluate: res" + res.toString());
                }
                return res.toString();
            }
            IExpr iExpr = this.util.evaluate("N(" + expression + ")");
            if (!iExpr.isNumeric() && !iExpr.isNumber() && !iExpr.isSignedNumber()) {
                return iExpr.toString();
            }
            try {
                return doFormatNumber(iExpr.toString());
            } catch (Exception e) {
                e.printStackTrace();
                return iExpr.toString();
            }
        } catch (Exception e2) {
            if (this.debug) {
                e2.printStackTrace();
            }
            return BuildConfig.FLAVOR;
        }
    }

    private String doFormatNumber(String res) {
        return this.numberFormat.format(Double.parseDouble(res)).replace(",", ".");
    }

    private void printLog(String fraction) {
        if (this.debug) {
            Log.d(TAG, fraction);
        }
    }

    private void printLog(SyntaxError e) {
        if (this.debug) {
            Log.e(TAG, "evaluate: " + e.getColumnIndex());
            Log.e(TAG, "evaluate: " + e.getCurrentLine());
            Log.e(TAG, "evaluate: " + e.getLength());
            Log.e(TAG, "evaluate: " + e.getStartOffset());
            Log.e(TAG, "evaluate: " + e.getError());
        }
    }

    public void getResultAsTex(String expression, EvaluateCallback callback) {
        expression = cleanExpression(expression);
        if (this.debug) {
            Log.d(TAG, "getResultAsTex: " + expression);
        }
        try {
            IExpr res;
            if (this.isFraction) {
                printLog("fraction");
                res = this.util.evaluate(expression);
            } else {
                printLog("not fraction");
                res = this.util.evaluate("N(" + expression + ")");
            }
            StringWriter writer = new StringWriter();
            this.teXUtilities.toTeX(res, writer);
            if (this.debug) {
                Log.d(TAG, "getResultAsTex: res" + writer.toString());
            }
            String s = writer.toString();
            callback.onEvaluate(expression, writer.toString(), 1);
        } catch (SyntaxError e) {
            if (this.debug) {
                e.printStackTrace();
            }
            printLog(e);
            callback.onEvaluate(expression, e.getMessage(), -1);
        } catch (MathException e2) {
            if (this.debug) {
                e2.printStackTrace();
            }
            callback.onEvaluate(expression, e2.getMessage(), -1);
        } catch (Exception e3) {
            callback.onEvaluate(expression, e3.getMessage(), -1);
        }
    }

    public boolean isFraction() {
        return this.isFraction;
    }

    public void setFraction(boolean fraction) {
        this.isFraction = fraction;
    }

    public void simplifyExpression(String input, EvaluateCallback callback) {
        getResult("Simplify(" + input + ")", new 2(input, callback));
    }

    private String getStepByStep(String s) {
        try {
            String[] res = new String[]{BuildConfig.FLAVOR};
            boolean[] find = new boolean[]{false};
            AbstractEvalStepListener listener = new 3(find, res, new ArrayList());
            EvalEngine engine = this.util.getEvalEngine();
            engine.setStepListener(listener);
            this.util.evaluate(s);
            engine.setTraceMode(false);
            printLog("getStepByStep: res = " + res[0]);
            return res[0];
        } catch (Exception e) {
            e.printStackTrace();
            return BuildConfig.FLAVOR;
        }
    }

    public void getDerivative(String expr, EvaluateCallback callback) {
        expr = cleanExpression(expr);
        String[] res = new String[]{BuildConfig.FLAVOR, BuildConfig.FLAVOR};
        boolean last = this.isFraction;
        boolean[] next = new boolean[]{false};
        setFraction(true);
        getResult(expr, new 4(res, next, callback));
        setFraction(false);
        if (next[0]) {
            getResult("Simplify(" + res[1] + ")", new 5(res));
        }
        callback.onEvaluate(expr, res[0], 1);
        setFraction(last);
    }

    public void solveEquation(String expr, EvaluateCallback callback) {
        expr = cleanExpression(expr);
        Log.d(TAG, "solveEquation: " + expr);
        boolean last = this.isFraction;
        setFraction(true);
        getResult(expr, new 6(expr, callback));
        setFraction(last);
        Log.d(TAG, "solveEquation: ok");
    }

    private String convertToReal(String result1) {
        try {
            boolean last = this.isFraction;
            setFraction(false);
            String[] res = new String[]{BuildConfig.FLAVOR};
            getResult(result1, new 7(res));
            setFraction(last);
            return res[0];
        } catch (Exception e) {
            return BuildConfig.FLAVOR;
        }
    }

    public String convertToTex(String expr) {
        expr = this.mTokenizer.getNormalExpression(cleanExpression(expr));
        StringWriter writer = new StringWriter();
        try {
            this.teXUtilities.toTeX(expr, writer);
            return "$$" + writer.toString() + "$$";
        } catch (Exception e) {
            return "$$" + expr + "$$";
        }
    }

    public ExprEvaluator getEvalUtilities() {
        return this.util;
    }

    public TeXUtilities getTeXUtilities() {
        return this.teXUtilities;
    }

    public void setTeXUtilities(TeXUtilities teXUtilities) {
        this.teXUtilities = teXUtilities;
    }

    public boolean isUseDegree() {
        return this.isUseDegree;
    }

    public void setUseDegree(boolean useDegree) {
        this.isUseDegree = useDegree;
    }

    public int getNumDecimal() {
        return this.numDecimal;
    }

    public void setNumDecimal(int numDecimal) {
        this.numDecimal = numDecimal;
    }

    public String addDoubleEqual(String inp) {
        if (inp.contains("==")) {
            return inp;
        }
        if (inp.contains("=")) {
            return inp.replace("=", "==");
        }
        return inp + "==0";
    }

    public void define(String var, double value) {
        try {
            this.util.defineVariable(var, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void define(String var, String value) {
        try {
            define(var, Double.valueOf(String.valueOf(this.util.evaluate("N(" + value + ")"))).doubleValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isNumber(String value) {
        try {
            return Boolean.valueOf(String.valueOf(this.util.evaluate("NumberQ(" + value + ")"))).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    public void test() {
        long start = SystemClock.currentThreadTimeMillis();
        for (int i = 0; i < 100; i++) {
            getResult(i + "+" + (i / 3) + " * " + "sqrt(" + i + ") * " + (i * 2), new 8());
        }
        long end = SystemClock.currentThreadTimeMillis();
    }

    public ArrayList<String> getVariable(String expr) {
        ArrayList<String> variables = new ArrayList();
        try {
            Collections.addAll(variables, getResult("Variables(" + expr + ")").replace(VectorFormat.DEFAULT_PREFIX, BuildConfig.FLAVOR).replace(VectorFormat.DEFAULT_SUFFIX, BuildConfig.FLAVOR).split(","));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return variables;
    }

    public boolean isSyntaxError(String expr) {
        try {
            this.util.evaluate(expr);
            return false;
        } catch (SyntaxError e) {
            return true;
        } catch (MathException e2) {
            return true;
        } catch (Exception e3) {
            return true;
        }
    }

    public String getError(String expr) {
        String[] res = new String[]{BuildConfig.FLAVOR};
        getResult(expr, new 9(res));
        return res[0];
    }
}
