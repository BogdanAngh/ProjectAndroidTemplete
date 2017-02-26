package com.example.duy.calculator;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import com.example.duy.calculator.item_math_type.IntegrateItem;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetSequence.Listener;

public class IntegrateActivity extends AbstractSolve {
    private static final String STARTED;
    private boolean isDataNull;
    private SharedPreferences preferences;

    class 1 implements Listener {
        final /* synthetic */ Editor val$editor;

        1(Editor editor) {
            this.val$editor = editor;
        }

        public void onSequenceFinish() {
            this.val$editor.putBoolean(IntegrateActivity.STARTED, true);
            this.val$editor.apply();
            IntegrateActivity.this.doEval();
        }

        public void onSequenceCanceled(TapTarget lastTarget) {
        }
    }

    public IntegrateActivity() {
        this.isDataNull = true;
    }

    static {
        STARTED = IntegrateActivity.class.getName() + "started";
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mInputDisplay.setHint(R.string.enter_function);
        this.btnSolve.setText(R.string.integrate);
        this.mLayoutLimit.setVisibility(0);
        getIntentData();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!this.preferences.getBoolean(STARTED, false)) {
            if (this.isDataNull) {
                this.mInputDisplay.setText("sqrt(1 - x^2)/x^2");
                this.editFrom.setText("sqrt(2)/2");
                this.editTo.setText("1");
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
        return new IntegrateItem(inp, this.editFrom.getText().toString(), this.editTo.getText().toString()).getInput();
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
        TapTarget.forView(this.mInputDisplay, getString(R.string.enter_function), getString(R.string.input_integrate_here)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTarget target1 = TapTarget.forView(this.editFrom, getString(R.string.enter_from), getString(R.string.limit_from_desc)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTarget.forView(this.editTo, getString(R.string.enter_to), getString(R.string.limit_to_desc)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTarget.forView(this.btnSolve, getString(R.string.integrate), getString(R.string.push_integrate_button)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTargetSequence sequence = new TapTargetSequence(this);
        sequence.targets(target0, target1, target2, target3);
        sequence.listener(new 1(editor));
        sequence.start();
    }

    private void doEval() {
        String inp = this.mInputDisplay.getCleanText();
        if (inp.isEmpty()) {
            this.mInputDisplay.requestFocus();
            this.mInputDisplay.setError(getString(R.string.input_expression));
        } else if (this.editFrom.getText().toString().isEmpty()) {
            this.editFrom.requestFocus();
            this.editFrom.setError(getString(R.string.enter_limit));
        } else if (this.editTo.getText().toString().isEmpty()) {
            this.editTo.requestFocus();
            this.editTo.setError(getString(R.string.enter_limit));
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
