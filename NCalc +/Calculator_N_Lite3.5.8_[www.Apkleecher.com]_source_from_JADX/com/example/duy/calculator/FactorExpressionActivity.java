package com.example.duy.calculator;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetSequence.Listener;

public class FactorExpressionActivity extends AbstractSolve {
    private static final String STARTED;
    private boolean isDataNull;
    SharedPreferences preferences;

    class 1 implements Listener {
        final /* synthetic */ Editor val$editor;

        1(Editor editor) {
            this.val$editor = editor;
        }

        public void onSequenceFinish() {
            this.val$editor.putBoolean(FactorExpressionActivity.STARTED, true);
            this.val$editor.apply();
            FactorExpressionActivity.this.doEval();
        }

        public void onSequenceCanceled(TapTarget lastTarget) {
        }
    }

    public FactorExpressionActivity() {
        this.isDataNull = true;
    }

    static {
        STARTED = FactorExpressionActivity.class.getName() + "started";
    }

    public int getIdStringHelp() {
        return R.string.help_expression;
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.txtTitle.setText(R.string.factor_equation);
        this.btnSolve.setText(R.string.analyze);
        getIntentData();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!this.preferences.getBoolean(STARTED, false)) {
            if (this.isDataNull) {
                this.mInputDisplay.setText("x^16 - 1");
            }
            showHelp();
        }
    }

    public void showHelp() {
        Editor editor = this.preferences.edit();
        TapTarget target0 = TapTarget.forView(this.mInputDisplay, getString(R.string.input_expression), getString(R.string.input_analyze_here)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTarget target = TapTarget.forView(this.btnSolve, getString(R.string.factor_equation), getString(R.string.push_analyze_button)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTargetSequence sequence = new TapTargetSequence(this);
        sequence.targets(target0, target);
        sequence.listener(new 1(editor));
        sequence.start();
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

    private String getExpression(String command) {
        return "Factor(" + this.mEvaluator.cleanExpression(command) + ")";
    }

    private void doEval() {
        String inp = this.mInputDisplay.getCleanText();
        if (inp.isEmpty()) {
            this.mInputDisplay.requestFocus();
            this.mInputDisplay.setError(getString(R.string.input_expression));
            return;
        }
        onResult(this.mTokenizer.getNormalExpression(inp));
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

    public void onResult(String command) {
        super.onResult(getExpression(command));
    }

    public void onHelpFunction(String s) {
    }
}
