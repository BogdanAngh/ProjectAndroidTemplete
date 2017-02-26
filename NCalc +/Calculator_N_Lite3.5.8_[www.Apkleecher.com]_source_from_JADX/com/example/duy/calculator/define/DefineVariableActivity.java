package com.example.duy.calculator.define;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import com.example.duy.calculator.AbstractAppCompatActivity;
import com.example.duy.calculator.CalcApplication;
import com.example.duy.calculator.MainCalculatorActivity;
import com.example.duy.calculator.R;
import com.example.duy.calculator.math_eval.BigEvaluator;
import com.example.duy.calculator.math_eval.Tokenizer;
import io.github.kexanie.library.BuildConfig;
import java.util.ArrayList;
import java.util.Iterator;

public class DefineVariableActivity extends AbstractAppCompatActivity {
    String TAG;
    private VariableAdapter adapter;
    private BigEvaluator mEvaluator;
    private Tokenizer mTokenizer;

    class 1 extends SimpleCallback {
        1(int x0, int x1) {
            super(x0, x1);
        }

        public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
            return false;
        }

        public void onSwiped(ViewHolder viewHolder, int direction) {
            DefineVariableActivity.this.adapter.removeVar(viewHolder.getAdapterPosition());
        }
    }

    class 2 implements OnClickListener {
        2() {
        }

        public void onClick(View view) {
            DefineVariableActivity.this.doSave();
            DefineVariableActivity.this.finish();
        }
    }

    class 3 implements OnClickListener {
        3() {
        }

        public void onClick(View view) {
            DefineVariableActivity.this.adapter.add();
        }
    }

    public DefineVariableActivity() {
        this.TAG = DefineVariableActivity.class.getSimpleName();
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.define_variable_activity);
        setSupportActionBar((Toolbar) findViewById(com.google.android.gms.R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CalcApplication application = (CalcApplication) getApplication();
        this.mEvaluator = application.getEvaluator();
        this.mTokenizer = application.getTokenizer();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rc_var);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        this.adapter = new VariableAdapter(this);
        recyclerView.setAdapter(this.adapter);
        new ItemTouchHelper(new 1(12, 12)).attachToRecyclerView(recyclerView);
        findViewById(R.id.btn_save).setOnClickListener(new 2());
        findViewById(R.id.btn_add).setOnClickListener(new 3());
        Iterator it = getIntentData().iterator();
        while (it.hasNext()) {
            this.adapter.addVar(new VariableEntry((String) it.next(), BuildConfig.FLAVOR));
        }
    }

    private ArrayList<String> getIntentData() {
        try {
            String expr = getIntent().getStringExtra(MainCalculatorActivity.DATA);
            Log.d(this.TAG, "getIntentData: " + expr);
            ArrayList<String> variable = this.mEvaluator.getVariable(expr);
            Iterator it = variable.iterator();
            while (it.hasNext()) {
                Log.d(this.TAG, "getIntentData: " + ((String) it.next()));
            }
            return variable;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    public void onBackPressed() {
        doSave();
        super.onBackPressed();
    }

    private void doSave() {
        Iterator it = this.adapter.getEntries().iterator();
        while (it.hasNext()) {
            VariableEntry e = (VariableEntry) it.next();
            Log.d(this.TAG, "doSave: " + e.getName() + e.getValue());
            if (this.mEvaluator.isNumber(e.getValue()) && !e.getName().isEmpty()) {
                this.mEvaluator.define(e.getName(), e.getValue());
            }
        }
    }

    protected void onPause() {
        super.onPause();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
