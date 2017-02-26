package com.example.duy.calculator;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class TrigToExpActivity extends AbstractSolve implements OnClickListener {
    public int getIdStringHelp() {
        return R.string.help_expression;
    }

    public void showHelp() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.txtTitle.setText(R.string.input_expression);
        this.mInputDisplay.setHint(null);
        this.btnSolve.setText(R.string.tranmiss);
    }

    public void onResult(String input) {
        super.onResult("TrigToExp(" + input + ")");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_solve /*2131689652*/:
                onResult(this.mTokenizer.getNormalExpression(this.mInputDisplay.getCleanText()));
            case R.id.btn_clear /*2131689661*/:
                super.onClear();
            default:
        }
    }

    public void onHelpFunction(String s) {
    }
}
