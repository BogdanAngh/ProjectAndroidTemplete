package com.example.duy.calculator;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.example.duy.calculator.math_eval.Constants;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetSequence.Listener;
import edu.jas.ps.UnivPowerSeriesRing;

public class LimitActivity extends AbstractSolve {
    private static final String STARTED;
    private boolean isDataNull;
    private SharedPreferences preferences;

    class 1 implements Listener {
        final /* synthetic */ Editor val$editor;

        1(Editor editor) {
            this.val$editor = editor;
        }

        public void onSequenceFinish() {
            this.val$editor.putBoolean(LimitActivity.STARTED, true);
            this.val$editor.apply();
            LimitActivity.this.doEval();
        }

        public void onSequenceCanceled(TapTarget lastTarget) {
        }
    }

    public LimitActivity() {
        this.isDataNull = true;
    }

    static {
        STARTED = LimitActivity.class.getName() + "started";
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mInputDisplay.setHint(R.string.input_expression);
        this.btnSolve.setText(R.string.eval);
        this.mLayoutLimit.setVisibility(0);
        this.editFrom.setVisibility(8);
        ((TextView) findViewById(R.id.txt_from)).setVisibility(8);
        ((TextView) findViewById(R.id.txt_to)).setText("x -> ");
        getIntentData();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!this.preferences.getBoolean(STARTED, false)) {
            if (this.isDataNull) {
                this.mInputDisplay.setText("1/x + 2");
                this.editTo.setText(Constants.INFINITY);
            }
            showHelp();
        }
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getBundleExtra(MainCalculatorActivity.DATA);
        if (bundle != null) {
            String data = bundle.getString(MainCalculatorActivity.DATA);
            if (data != null) {
                this.mInputDisplay.setText(data);
                data = getExpression(this.mTokenizer.getNormalExpression(data));
                this.isDataNull = false;
                onResult(data, false);
            }
        }
    }

    private String getExpression(String inp) {
        inp = this.mEvaluator.cleanExpression(inp);
        String params = UnivPowerSeriesRing.DEFAULT_NAME;
        String expression = ("Limit(" + inp + "," + params + "-> " + this.editTo.getText().toString() + ")").toLowerCase();
        Log.d(this.TAG, "getExpression: " + expression);
        return expression;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_solve /*2131689652*/:
                doEval();
            case R.id.btn_clear /*2131689661*/:
                super.onClear();
            default:
        }
    }

    public int getIdStringHelp() {
        return R.string.help_expression;
    }

    public void showHelp() {
        Editor editor = this.preferences.edit();
        TapTarget.forView(this.mInputDisplay, getString(R.string.input_expression)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTarget.forView(this.editTo, getString(R.string.input_limit)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTarget.forView(this.btnSolve, getString(R.string.eval)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTargetSequence sequence = new TapTargetSequence(this);
        sequence.targets(target0, target1, target2);
        sequence.listener(new 1(editor));
        sequence.start();
    }

    private void doEval() {
        String inp = this.mInputDisplay.getCleanText();
        if (inp.isEmpty()) {
            this.mInputDisplay.requestFocus();
            this.mInputDisplay.setError(getString(R.string.input_expression));
        } else if (this.editTo.getText().toString().isEmpty()) {
            this.editTo.requestFocus();
            this.editTo.setError(getString(R.string.error));
        } else {
            onResult(this.mEvaluator.cleanExpression(inp));
        }
    }

    public void onResult(String input) {
        super.onResult(getExpression(input));
    }

    public void onHelpFunction(String s) {
    }
}
