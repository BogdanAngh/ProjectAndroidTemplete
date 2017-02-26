package com.example.duy.calculator;

import android.os.Bundle;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.view.View;
import android.view.View.OnClickListener;

@Deprecated
public class SolveDisEquationActivity extends AbstractSolve implements OnNavigationItemSelectedListener, OnClickListener {
    public int getIdStringHelp() {
        return R.string.help_expression;
    }

    public void showHelp() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.txtTitle.setText(R.string.input_disequation);
        this.btnSolve.setText(R.string.solve);
        this.mInputDisplay.setHint(null);
    }

    public void onHelpFunction(String s) {
    }

    public void onResult(String input) {
        if (input.contains("<") || input.contains(">") || input.contains(">=") || input.contains("<=")) {
            super.onResult(input);
        } else {
            this.mInputDisplay.setError(getString(R.string.error_not_disequation));
        }
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
}
