package com.example.duy.calculator;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetSequence.Listener;

public class SimplifyEquationActivity extends AbstractSolve {
    private static final String STARTED;
    private boolean isDataNull;
    SharedPreferences preferences;

    class 1 implements Listener {
        final /* synthetic */ Editor val$editor;

        1(Editor editor) {
            this.val$editor = editor;
        }

        public void onSequenceFinish() {
            this.val$editor.putBoolean(SimplifyEquationActivity.STARTED, true);
            this.val$editor.apply();
            SimplifyEquationActivity.this.doEval();
        }

        public void onSequenceCanceled(TapTarget lastTarget) {
            SimplifyEquationActivity.this.doEval();
        }
    }

    public SimplifyEquationActivity() {
        this.isDataNull = true;
    }

    static {
        STARTED = SimplifyEquationActivity.class.getName() + "started";
    }

    public int getIdStringHelp() {
        return R.string.help_expression;
    }

    public void onHelpFunction(String s) {
    }

    private void doEval() {
        String inp = this.mInputDisplay.getCleanText();
        if (inp.isEmpty()) {
            this.mInputDisplay.requestFocus();
            this.mInputDisplay.setError(getString(R.string.input_expression));
            return;
        }
        inp = this.mTokenizer.getNormalExpression(inp);
        Log.d(this.TAG, inp);
        onResult(inp);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.txtTitle.setText(R.string.input_expression);
        this.btnSolve.setText(R.string.simplify);
        setStatus(2);
        getIntentData();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!this.preferences.getBoolean(STARTED, false)) {
            if (this.isDataNull) {
                this.mInputDisplay.setText("a - b + 2a - b");
            }
            showHelp();
        }
    }

    public void showHelp() {
        Editor editor = this.preferences.edit();
        TapTarget target0 = TapTarget.forView(this.mInputDisplay, getString(R.string.input_expression), getString(R.string.input_simplify_here)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTarget target = TapTarget.forView(this.btnSolve, getString(R.string.simplify_equation), getString(R.string.push_simplify_button)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
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
                data = this.mTokenizer.getNormalExpression(data);
                this.isDataNull = false;
                onResult(data, false);
            }
        }
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
}
