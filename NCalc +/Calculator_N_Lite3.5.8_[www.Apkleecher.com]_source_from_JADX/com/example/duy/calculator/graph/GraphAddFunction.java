package com.example.duy.calculator.graph;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.example.duy.calculator.AbstractAppCompatActivity;
import com.example.duy.calculator.R;
import edu.jas.ps.UnivPowerSeriesRing;
import io.github.kexanie.library.BuildConfig;

public class GraphAddFunction extends AbstractAppCompatActivity {
    private Button btnSave;
    private EditText[] editFunctions;
    private SharedPreferences preferences;

    class 1 implements OnClickListener {
        1() {
        }

        public void onClick(View v) {
            int i;
            String[] f = new String[6];
            for (i = 0; i < 6; i++) {
                f[i] = GraphAddFunction.this.editFunctions[i].getText().toString();
            }
            for (i = 0; i < 6; i++) {
                if (!f[i].equals(BuildConfig.FLAVOR)) {
                    if (MathGraph.isValid(f[i], new String[]{UnivPowerSeriesRing.DEFAULT_NAME})) {
                    }
                }
            }
            GraphAddFunction.this.updateRect();
            GraphAddFunction.this.finish();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.graph_add_function);
        setSupportActionBar((Toolbar) findViewById(com.google.android.gms.R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.editFunctions = new EditText[6];
        this.editFunctions[0] = (EditText) findViewById(R.id.funText1);
        this.editFunctions[1] = (EditText) findViewById(R.id.funText2);
        this.editFunctions[2] = (EditText) findViewById(R.id.funText3);
        this.editFunctions[3] = (EditText) findViewById(R.id.funText4);
        this.editFunctions[4] = (EditText) findViewById(R.id.funText5);
        this.editFunctions[5] = (EditText) findViewById(R.id.funText6);
        this.btnSave = (Button) findViewById(R.id.update);
        this.btnSave.setOnClickListener(new 1());
        initData();
    }

    private void initData() {
        for (int i = 0; i < 6; i++) {
            String f = this.preferences.getString("f" + (i + 1), BuildConfig.FLAVOR);
            if (!f.isEmpty()) {
                this.editFunctions[i].setText(f);
            }
        }
    }

    public void updateRect() {
        Editor edit = this.preferences.edit();
        for (int i = 0; i < 6; i++) {
            edit.putString("f" + (i + 1), this.editFunctions[i].getText().toString());
        }
        edit.commit();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
