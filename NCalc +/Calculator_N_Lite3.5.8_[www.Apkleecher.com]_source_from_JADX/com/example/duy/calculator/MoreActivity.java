package com.example.duy.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.google.android.gms.R;

public class MoreActivity extends AbstractAppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_more);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btnDiscrimation /*2131689599*/:
                intent = new Intent(this, DiscriminantActivity.class);
                break;
            case R.id.btnInterpolation /*2131689600*/:
                intent = new Intent(this, InterpolationActivity.class);
                break;
            case R.id.btnRealToFrac /*2131689602*/:
                intent = new Intent(this, RealToFractionActivity.class);
                break;
            case R.id.btnTrigExpand /*2131689604*/:
                intent = new Intent(this, TrigExpandActivity.class);
                break;
            case R.id.btnTrigToExp /*2131689605*/:
                intent = new Intent(this, TrigToExpActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
