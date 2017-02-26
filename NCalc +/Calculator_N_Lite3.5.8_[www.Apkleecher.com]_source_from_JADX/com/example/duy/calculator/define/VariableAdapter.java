package com.example.duy.calculator.define;

import android.content.Context;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;
import com.example.duy.calculator.R;
import com.example.duy.calculator.data.Database;
import io.github.kexanie.library.BuildConfig;
import java.util.ArrayList;

public class VariableAdapter extends Adapter<ViewHolder> {
    private final String TAG;
    private Context context;
    private Database database;
    private ArrayList<VariableEntry> entries;
    private LayoutInflater inflater;

    class 1 implements OnFocusChangeListener {
        final /* synthetic */ VariableEntry val$entry;

        1(VariableEntry variableEntry) {
            this.val$entry = variableEntry;
        }

        public void onFocusChange(View view, boolean b) {
            this.val$entry.setName(((EditText) view).getText().toString());
        }
    }

    class 2 extends NumberKeyListener {
        2() {
        }

        protected char[] getAcceptedChars() {
            return "0123456789+-*/^.!".toCharArray();
        }

        public int getInputType() {
            return AccessibilityNodeInfoCompat.ACTION_COLLAPSE;
        }
    }

    class 3 implements OnFocusChangeListener {
        final /* synthetic */ VariableEntry val$entry;

        3(VariableEntry variableEntry) {
            this.val$entry = variableEntry;
        }

        public void onFocusChange(View view, boolean b) {
            if (!b) {
                this.val$entry.setValue(((EditText) view).getText().toString());
            }
        }
    }

    class 4 implements TextWatcher {
        final /* synthetic */ VariableEntry val$entry;

        4(VariableEntry variableEntry) {
            this.val$entry = variableEntry;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            this.val$entry.setValue(editable.toString());
        }
    }

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public EditText editValue;
        public EditText txtName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.editValue = (EditText) itemView.findViewById(R.id.edit_value);
            this.txtName = (EditText) itemView.findViewById(R.id.name);
        }
    }

    public VariableAdapter(Context context) {
        this.TAG = VariableAdapter.class.getSimpleName();
        this.entries = new ArrayList();
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.database = new Database(context);
    }

    public ArrayList<VariableEntry> getEntries() {
        return this.entries;
    }

    public void setEntries(ArrayList<VariableEntry> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(this.inflater.inflate(R.layout.variable_line, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(this.TAG, "onBindViewHolder: " + position);
        VariableEntry entry = (VariableEntry) this.entries.get(position);
        holder.txtName.setText(entry.getName());
        holder.txtName.setOnFocusChangeListener(new 1(entry));
        holder.editValue.setText(String.valueOf(entry.getValue()));
        holder.editValue.setKeyListener(new 2());
        holder.editValue.setOnFocusChangeListener(new 3(entry));
        holder.editValue.addTextChangedListener(new 4(entry));
        holder.editValue.requestFocus();
    }

    public int getItemCount() {
        return this.entries.size();
    }

    public void addVar(VariableEntry a) {
        this.entries.add(a);
        Log.d(this.TAG, "addVar: " + a.getName());
        notifyItemInserted(getItemCount() - 1);
    }

    public void removeVar(int adapterPosition) {
        this.database.removeVariable(((VariableEntry) this.entries.get(adapterPosition)).getName());
        this.entries.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
    }

    public void add() {
        this.entries.add(new VariableEntry(BuildConfig.FLAVOR, BuildConfig.FLAVOR));
        notifyItemInserted(getItemCount() - 1);
    }
}
