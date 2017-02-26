package com.example.duy.calculator;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import com.google.android.gms.R;
import io.github.kexanie.library.BuildConfig;
import io.github.kexanie.library.MathView;
import org.apache.commons.math4.fraction.Fraction;
import org.apache.commons.math4.geometry.VectorFormat;

@Deprecated
public class RealToFractionActivity extends AbstractCalculator implements TextWatcher, Calculator {
    private EditText editInput;
    private MathView txtResult;

    public void setTextDisplay(String text) {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_real_to_fraction);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);
        this.editInput = (EditText) findViewById(R.id.editInput);
        this.txtResult = (MathView) findViewById(R.id.txtResult);
        this.editInput.addTextChangedListener(this);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void insertText(String s) {
    }

    public void insertOperator(String s) {
    }

    public String getTextClean() {
        return null;
    }

    public void onHelpFunction(String s) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        onResult(null);
    }

    public void afterTextChanged(Editable editable) {
    }

    public void onResult(String result) {
        if (this.editInput.getText().toString().isEmpty()) {
            this.txtResult.setText(BuildConfig.FLAVOR);
            return;
        }
        try {
            Fraction fraction = new Fraction(Double.parseDouble(this.editInput.getText().toString()));
            this.txtResult.setText("$$\\frac{" + fraction.getNumerator() + VectorFormat.DEFAULT_SUFFIX + VectorFormat.DEFAULT_PREFIX + fraction.getDenominator() + VectorFormat.DEFAULT_SUFFIX + "$$");
        } catch (Exception e) {
            super.showDialog(getString(R.string.error), e.getMessage());
        }
    }

    public void onError(String errorResourceId) {
    }

    public void onDelete() {
    }

    public void onClear() {
    }

    public void onEqual() {
    }

    public void onInputVoice() {
    }
}
