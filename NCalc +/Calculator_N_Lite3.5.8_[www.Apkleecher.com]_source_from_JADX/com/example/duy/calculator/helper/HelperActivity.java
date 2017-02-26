package com.example.duy.calculator.helper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.example.duy.calculator.AbstractAppCompatActivity;
import com.example.duy.calculator.R;
import io.github.kexanie.library.MathView;

public class HelperActivity extends AbstractAppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_help);
        setSupportActionBar((Toolbar) findViewById(com.google.android.gms.R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MathView) findViewById(R.id.webview)).setText(getString(R.string.help_main_calc));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
