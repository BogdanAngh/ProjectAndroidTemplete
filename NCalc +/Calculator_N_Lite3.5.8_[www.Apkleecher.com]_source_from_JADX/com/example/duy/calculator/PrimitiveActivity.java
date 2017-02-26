package com.example.duy.calculator;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import com.example.duy.calculator.item_math_type.PrimitiveItem;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetSequence.Listener;
import edu.jas.ps.UnivPowerSeriesRing;

public class PrimitiveActivity extends AbstractSolve {
    private static final String STARTED;
    private boolean isDataNull;
    private SharedPreferences preferences;

    class 1 implements Listener {
        final /* synthetic */ Editor val$editor;

        1(Editor editor) {
            this.val$editor = editor;
        }

        public void onSequenceFinish() {
            this.val$editor.putBoolean(PrimitiveActivity.STARTED, true);
            this.val$editor.apply();
            PrimitiveActivity.this.doEval();
        }

        public void onSequenceCanceled(TapTarget lastTarget) {
        }
    }

    public PrimitiveActivity() {
        this.isDataNull = true;
    }

    static {
        STARTED = PrimitiveActivity.class.getName() + "started";
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mLayoutLimit.setVisibility(8);
        this.mInputDisplay.setHint(R.string.enter_function);
        this.btnSolve.setText(R.string.primitive);
        getIntentData();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!this.preferences.getBoolean(STARTED, false)) {
            if (this.isDataNull) {
                this.mInputDisplay.setText("x * sin(x)");
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
        return new PrimitiveItem(inp, UnivPowerSeriesRing.DEFAULT_NAME).getInput();
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
        TapTarget.forView(this.mInputDisplay, getString(R.string.enter_function), getString(R.string.input_primitive_here)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTarget.forView(this.btnSolve, getString(R.string.primitive), getString(R.string.push_button_primitive)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTargetSequence sequence = new TapTargetSequence(this);
        sequence.targets(target0, target3);
        sequence.listener(new 1(editor));
        sequence.start();
    }

    private void doEval() {
        String inp = this.mInputDisplay.getCleanText();
        if (inp.isEmpty()) {
            this.mInputDisplay.requestFocus();
            this.mInputDisplay.setError(getString(R.string.input_expression));
            return;
        }
        onResult(inp);
    }

    public void onResult(String input) {
        super.onResult(getExpression(input));
    }

    public void onHelpFunction(String s) {
    }
}
