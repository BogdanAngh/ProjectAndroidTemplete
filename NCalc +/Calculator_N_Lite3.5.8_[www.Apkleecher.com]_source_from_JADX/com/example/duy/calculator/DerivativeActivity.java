package com.example.duy.calculator;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetSequence.Listener;
import java.util.ArrayList;

public class DerivativeActivity extends AbstractSolve {
    private static final String STARTED;
    private boolean isDataNull;
    private ArrayList<String> list;
    private SharedPreferences preferences;

    class 1 implements Listener {
        final /* synthetic */ Editor val$editor;

        1(Editor editor) {
            this.val$editor = editor;
        }

        public void onSequenceFinish() {
            this.val$editor.putBoolean(DerivativeActivity.STARTED, true);
            this.val$editor.apply();
            DerivativeActivity.this.prepareData();
        }

        public void onSequenceCanceled(TapTarget lastTarget) {
            DerivativeActivity.this.prepareData();
        }
    }

    public DerivativeActivity() {
        this.isDataNull = true;
        this.list = new ArrayList();
    }

    static {
        STARTED = DerivativeActivity.class.getName() + "started";
    }

    public void onHelpFunction(String s) {
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mInputDisplay.setHint(getString(R.string.enter_function));
        this.btnSolve.setText(R.string.derivative);
        for (int i = 1; i < 10; i++) {
            this.list.add(String.valueOf(i));
        }
        SpinnerAdapter adapter = new ArrayAdapter(this, 17367055, this.list);
        adapter.setDropDownViewResource(17367049);
        this.mSpinner.setVisibility(0);
        this.mSpinner.setAdapter(adapter);
        getIntentData();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (!this.preferences.getBoolean(STARTED, false)) {
            if (this.isDataNull) {
                this.mInputDisplay.setText("sqrt(x) + ln(x)");
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
                this.mInputDisplay.setText(data);
                data = getExpression(this.mTokenizer.getNormalExpression(data));
                this.isDataNull = false;
                onResult(data, false);
            }
        }
    }

    public void showHelp() {
        Editor editor = this.preferences.edit();
        TapTarget target0 = TapTarget.forView(this.mInputDisplay, getString(R.string.enter_function), getString(R.string.input_der_here)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTarget target1 = TapTarget.forView(this.mSpinner, getString(R.string.derivative_level), getString(R.string.der_level_desc)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTarget target = TapTarget.forView(this.btnSolve, getString(R.string.derivative), getString(R.string.push_der_button)).drawShadow(true).cancelable(true).targetCircleColor(R.color.colorAccent).transparentTarget(true).outerCircleColor(R.color.colorPrimary).dimColor(R.color.colorPrimaryDark);
        TapTargetSequence sequence = new TapTargetSequence(this);
        sequence.targets(target0, target1, target);
        sequence.listener(new 1(editor));
        sequence.start();
    }

    private void prepareData() {
        onResult(this.mTokenizer.getNormalExpression(this.mInputDisplay.getCleanText()));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_solve /*2131689652*/:
                prepareData();
            case R.id.btn_clear /*2131689661*/:
                super.onClear();
            default:
        }
    }

    public void onResult(String command) {
        String expression = getExpression(command);
        setStatus(0);
        super.onResult(expression);
    }

    private String getExpression(String command) {
        if (command.contains(",")) {
            return "D(" + command + ")";
        }
        return "D(" + command + ",{x," + ((String) this.list.get(this.mSpinner.getSelectedItemPosition())) + "})";
    }

    public int getIdStringHelp() {
        return R.string.help_derivative;
    }
}
