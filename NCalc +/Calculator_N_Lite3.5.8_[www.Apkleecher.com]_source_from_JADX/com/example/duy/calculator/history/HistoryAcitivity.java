package com.example.duy.calculator.history;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.example.duy.calculator.AbstractAppCompatActivity;
import com.example.duy.calculator.CalcApplication;
import com.example.duy.calculator.MainCalculatorActivity;
import com.example.duy.calculator.R;
import com.example.duy.calculator.history.HistoryAdapter.HistoryListener;
import com.example.duy.calculator.math_eval.Tokenizer;
import java.util.ArrayList;

public class HistoryAcitivity extends AbstractAppCompatActivity {
    private HistoryAdapter mHistoryAdapter;
    private RecyclerView mRecyclerView;

    class 1 implements HistoryListener {
        1() {
        }

        public void onItemClickListener(View view, ItemHistory itemHistory) {
            Intent intent = HistoryAcitivity.this.getIntent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(MainCalculatorActivity.DATA, itemHistory);
            intent.putExtra(MainCalculatorActivity.DATA, bundle);
            HistoryAcitivity.this.setResult(-1, intent);
            HistoryAcitivity.this.finish();
        }

        public void onItemLongClickListener(View view, ItemHistory itemHistory) {
            Toast.makeText(HistoryAcitivity.this, HistoryAcitivity.this.getString(R.string.copied) + " \n" + itemHistory.getMath(), 0).show();
        }
    }

    class 2 extends SimpleCallback {
        2(int x0, int x1) {
            super(x0, x1);
        }

        public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
            return false;
        }

        public void onSwiped(ViewHolder viewHolder, int direction) {
            HistoryAcitivity.this.mHistoryAdapter.removeHistory(viewHolder.getAdapterPosition());
        }
    }

    class 3 implements OnClickListener {
        3() {
        }

        public void onClick(View view) {
            HistoryAcitivity.this.database.clearHistory();
            HistoryAcitivity.this.mHistoryAdapter.clear();
            HistoryAcitivity.this.mHistoryAdapter.notifyDataSetChanged();
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_history);
        setSupportActionBar((Toolbar) findViewById(com.google.android.gms.R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tokenizer tokenizer = ((CalcApplication) getApplicationContext()).getTokenizer();
        this.mRecyclerView = (RecyclerView) findViewById(R.id.historyRecycler);
        this.mHistoryAdapter = new HistoryAdapter(this, tokenizer);
        this.mHistoryAdapter.setListener(new 1());
        ItemTouchHelper helper = new ItemTouchHelper(new 2(12, 12));
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.mRecyclerView.setAdapter(this.mHistoryAdapter);
        this.mRecyclerView.scrollToPosition(this.mHistoryAdapter.getItemCount() - 1);
        helper.attachToRecyclerView(this.mRecyclerView);
        findViewById(R.id.fabDeleteAll).setOnClickListener(new 3());
    }

    protected void onPause() {
        doSave();
        super.onPause();
    }

    private void doSave() {
        ArrayList histories = this.mHistoryAdapter.getItemHistories();
        this.database.clearHistory();
        this.database.saveHistory(histories);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 16908332) {
            doSave();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
