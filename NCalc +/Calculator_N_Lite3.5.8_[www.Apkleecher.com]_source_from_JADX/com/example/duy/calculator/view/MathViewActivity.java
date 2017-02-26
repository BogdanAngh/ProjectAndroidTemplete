package com.example.duy.calculator.view;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.duy.calculator.R;
import io.github.kexanie.library.MathView;

public class MathViewActivity extends AppCompatActivity {
    public static final String DATA = "data_math_view";

    class 1 implements OnClickListener {
        1() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.math_view_layout);
        setSupportActionBar((Toolbar) findViewById(com.google.android.gms.R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent() != null) {
            Bundle bundle = getIntent().getBundleExtra(DATA);
            if (bundle != null) {
                try {
                    String res = bundle.getString(DATA);
                    MathView mathView = (MathView) findViewById(R.id.math_view);
                    mathView.getSettings().setSupportZoom(true);
                    mathView.getSettings().setJavaScriptEnabled(true);
                    mathView.getSettings().setBuiltInZoomControls(true);
                    mathView.getSettings().setDisplayZoomControls(true);
                    mathView.setText(res);
                    Toast.makeText(this, R.string.please_wait, 0).show();
                } catch (Exception e) {
                    new Builder(this).setTitle((int) R.string.error).setMessage(e.getMessage()).setPositiveButton((int) R.string.close, new 1()).create().show();
                }
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
