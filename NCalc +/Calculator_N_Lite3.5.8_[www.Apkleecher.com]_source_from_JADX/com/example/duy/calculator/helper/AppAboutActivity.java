package com.example.duy.calculator.helper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import com.example.duy.calculator.AbstractAppCompatActivity;
import com.example.duy.calculator.R;

public class AppAboutActivity extends AbstractAppCompatActivity {

    class 1 implements OnClickListener {
        1() {
        }

        public void onClick(View view) {
            AppAboutActivity.this.startActivity(new Intent(AppAboutActivity.this, LicenseActivity.class));
        }
    }

    class 2 implements OnClickListener {
        2() {
        }

        public void onClick(View view) {
            AppAboutActivity.this.rateApp();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_app_about);
        setSupportActionBar((Toolbar) findViewById(com.google.android.gms.R.id.toolbar));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.open_source).setOnClickListener(new 1());
        findViewById(R.id.rate).setOnClickListener(new 2());
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
