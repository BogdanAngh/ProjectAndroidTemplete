package com.example.duy.calculator.math_eval;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import com.example.duy.calculator.CalcApplication;
import com.example.duy.calculator.R;
import com.example.duy.calculator.math_eval.LogicEvaluator.EvaluateCallback;
import edu.jas.ps.UnivPowerSeriesRing;
import io.github.kexanie.library.BuildConfig;
import io.github.kexanie.library.MathView;
import org.apache.commons.math4.geometry.VectorFormat;

public class TaskSolve extends AsyncTask<Void, Void, String> {
    private final String TAG;
    Context activity;
    private StringBuilder equation;
    private String expr;
    private BigEvaluator mEvaluator;
    private MathView mathView;
    private ProgressBar progressBar;

    class 1 implements EvaluateCallback {
        final /* synthetic */ String[] val$res;

        1(String[] strArr) {
            this.val$res = strArr;
        }

        public void onEvaluate(String expr, String result, int errorResourceId) {
            this.val$res[0] = result;
        }
    }

    public TaskSolve(CalcApplication context, ProgressBar progressBar, MathView mathView, String expr) {
        this.TAG = TaskSolve.class.getName();
        this.equation = new StringBuilder();
        this.activity = context;
        this.mEvaluator = context.getEvaluator();
        expr = this.mEvaluator.getTokenizer().getNormalExpression(this.mEvaluator.cleanExpression(expr.toLowerCase()));
        this.mathView = mathView;
        this.expr = expr;
        this.progressBar = progressBar;
        this.progressBar.setVisibility(0);
        this.mathView = mathView;
    }

    protected String doInBackground(Void... voids) {
        if (this.expr.trim().isEmpty()) {
            return this.mEvaluator.getString(R.string.not_input_expression) + "</br>";
        }
        if (!this.expr.toLowerCase().contains(UnivPowerSeriesRing.DEFAULT_NAME)) {
            return this.mEvaluator.getString(R.string.not_params) + "</br>";
        }
        if (this.mEvaluator.isSyntaxError(this.expr)) {
            String er = this.mEvaluator.getError(this.expr);
            Log.d(this.TAG, "doInBackground: error " + er);
            return "$$" + er + "$$";
        }
        this.expr = this.mEvaluator.addDoubleEqual(this.expr);
        this.equation.append(Constants.SOLVE).append("({");
        this.equation.append(this.expr);
        this.equation.append(VectorFormat.DEFAULT_SUFFIX);
        this.equation.append(",");
        this.equation.append(VectorFormat.DEFAULT_PREFIX).append(UnivPowerSeriesRing.DEFAULT_NAME).append(VectorFormat.DEFAULT_SUFFIX);
        this.equation.append(")");
        String input = this.equation.toString();
        Log.d(this.TAG, input);
        String[] res = new String[]{BuildConfig.FLAVOR};
        this.mEvaluator.solveEquation(input, new 1(res));
        return res[0];
    }

    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
        this.mathView.setText(aVoid);
        this.progressBar.setVisibility(8);
    }
}
