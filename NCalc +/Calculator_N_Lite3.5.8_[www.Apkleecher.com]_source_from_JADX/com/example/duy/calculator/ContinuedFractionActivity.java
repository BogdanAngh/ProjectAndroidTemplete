package com.example.duy.calculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

@Deprecated
public class ContinuedFractionActivity extends AbstractSolve {
    public void onHelpFunction(String s) {
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mInputDisplay.setHint(null);
        this.txtTitle.setText(R.string.input_fraction);
        this.btnSolve.setText(R.string.analyze);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_solve /*2131689652*/:
                String inp = this.mTokenizer.getNormalExpression(this.mInputDisplay.getCleanText());
                Log.d(this.TAG, inp);
                onResult(inp);
            case R.id.btn_clear /*2131689661*/:
                super.onClear();
            default:
        }
    }

    public void onResult(String command) {
        super.onResult("ContinuedFraction(" + command + ")");
    }

    public int getIdStringHelp() {
        return R.string.help_expression;
    }

    public void showHelp() {
    }
}
