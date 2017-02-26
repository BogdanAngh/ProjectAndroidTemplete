package com.example.duy.calculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class TrigExpandActivity extends AbstractSolve {
    public int getIdStringHelp() {
        return R.string.help_expression;
    }

    public void showHelp() {
    }

    public void onHelpFunction(String s) {
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mInputDisplay.setHint(null);
        this.txtTitle.setText(R.string.input_expression);
        this.btnSolve.setText(R.string.analyze);
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

    public void onResult(String command) {
        super.onResult("TrigExpand(" + command + ")");
    }
}
