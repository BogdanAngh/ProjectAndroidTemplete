package com.example.duy.calculator;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import com.getkeepsafe.taptargetview.R;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetSequence.Listener;

public class SolveEquationActivity extends AbstractSolve implements OnNavigationItemSelectedListener, OnClickListener {
    private static final String STARTED;
    private boolean isDataNull;
    private final OnKeyListener mFormulaOnKeyListener;
    SharedPreferences preferences;

    class 1 implements OnKeyListener {
        1() {
        }

        public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
            switch (keyCode) {
                case R.styleable.AppCompatTheme_textAppearanceSearchResultTitle /*66*/:
                case 160:
                    if (keyEvent.getAction() != 1) {
                        return true;
                    }
                    SolveEquationActivity.this.doEval();
                    return true;
                default:
                    return false;
            }
        }
    }

    class 2 implements Listener {
        final /* synthetic */ Editor val$editor;

        2(Editor editor) {
            this.val$editor = editor;
        }

        public void onSequenceFinish() {
            this.val$editor.putBoolean(SolveEquationActivity.STARTED, true);
            this.val$editor.apply();
            SolveEquationActivity.this.doEval();
        }

        public void onSequenceCanceled(TapTarget lastTarget) {
            SolveEquationActivity.this.doEval();
        }
    }

    public SolveEquationActivity() {
        this.mFormulaOnKeyListener = new 1();
        this.isDataNull = true;
    }

    static {
        STARTED = SolveEquationActivity.class.getName() + "started";
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.txtTitle.setText(R.string.input_equation);
        this.btnSolve.setText(R.string.solve);
        this.mInputDisplay.setOnKeyListener(this.mFormulaOnKeyListener);
        setStatus(1);
        getIntentData();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!this.preferences.getBoolean(STARTED, false)) {
            if (this.isDataNull) {
                this.mInputDisplay.setText("2x^2 + 3x + 1");
            }
            showHelp();
        }
    }

    public void showHelp() {
        Editor editor = this.preferences.edit();
        TapTarget target0 = TapTarget.forView(this.mInputDisplay, getString(R.string.input_equation), getString(R.string.input_equation_here)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTarget target = TapTarget.forView(this.btnSolve, getString(R.string.solve_equation), getString(R.string.push_solve_button)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTargetSequence sequence = new TapTargetSequence(this);
        sequence.targets(target0, target);
        sequence.listener(new 2(editor));
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
                if (!data.isEmpty()) {
                    onResult(data, false);
                }
            }
        }
    }

    public void onHelpFunction(String s) {
    }

    public void onResult(String input) {
        super.onResult(input);
    }

    public int getIdStringHelp() {
        return R.string.help_solve_equation;
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

    private void doEval() {
        String inp = this.mInputDisplay.getCleanText();
        if (inp.isEmpty()) {
            this.mInputDisplay.requestFocus();
            this.mInputDisplay.setError(getString(R.string.input_expression));
            return;
        }
        onResult(this.mEvaluator.cleanExpression(inp));
    }

    private String getExpression(String inp) {
        return inp;
    }
}
