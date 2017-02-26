package com.example.duy.calculator.history;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.duy.calculator.R;
import com.example.duy.calculator.data.Database;
import com.example.duy.calculator.math_eval.Tokenizer;
import java.util.ArrayList;

public class HistoryAdapter extends Adapter<ViewHolder> {
    private Context context;
    private Database database;
    private ArrayList<ItemHistory> itemHistories;
    private HistoryListener listener;
    private Tokenizer tokenizer;

    public interface HistoryListener {
        void onItemClickListener(View view, ItemHistory itemHistory);

        void onItemLongClickListener(View view, ItemHistory itemHistory);
    }

    class 1 implements OnClickListener {
        final /* synthetic */ ItemHistory val$itemHistory;

        1(ItemHistory itemHistory) {
            this.val$itemHistory = itemHistory;
        }

        public void onClick(View v) {
            if (HistoryAdapter.this.listener != null) {
                HistoryAdapter.this.listener.onItemClickListener(v, this.val$itemHistory);
            }
        }
    }

    class 2 implements OnLongClickListener {
        final /* synthetic */ ItemHistory val$itemHistory;

        2(ItemHistory itemHistory) {
            this.val$itemHistory = itemHistory;
        }

        public boolean onLongClick(View view) {
            if (HistoryAdapter.this.listener != null) {
                HistoryAdapter.this.listener.onItemLongClickListener(view, this.val$itemHistory);
            }
            return true;
        }
    }

    public class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        CardView cardView;
        TextView txtMath;
        TextView txtResult;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txtMath = (TextView) itemView.findViewById(R.id.txt_math);
            this.txtResult = (TextView) itemView.findViewById(R.id.txt_result);
            this.cardView = (CardView) itemView.findViewById(R.id.cardview);
        }
    }

    public ArrayList<ItemHistory> getItemHistories() {
        return this.itemHistories;
    }

    public void setItemHistories(ArrayList<ItemHistory> itemHistories) {
        this.itemHistories = itemHistories;
    }

    public HistoryAdapter(Context context, Tokenizer tokenizer) {
        this.itemHistories = new ArrayList();
        this.listener = null;
        this.context = context;
        this.database = new Database(context);
        this.itemHistories = this.database.getAllItemHistory();
        this.tokenizer = tokenizer;
    }

    public HistoryListener getListener() {
        return this.listener;
    }

    public void setListener(HistoryListener listener) {
        this.listener = listener;
    }

    public void addHistory(ItemHistory itemHistory) {
        this.itemHistories.add(itemHistory);
        this.database.removeItemHistory(itemHistory.getTime());
        notifyItemInserted(this.itemHistories.size() - 1);
    }

    public void removeHistory(ItemHistory itemHistory, int position) {
        this.itemHistories.remove(itemHistory);
        this.database.removeItemHistory(itemHistory.getTime());
        notifyItemRemoved(position);
    }

    public void removeHistory(int position) {
        this.itemHistories.remove(position);
        notifyItemRemoved(position);
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.history_entry, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemHistory itemHistory = (ItemHistory) this.itemHistories.get(position);
        holder.txtMath.setText(this.tokenizer.getLocalizedExpression(itemHistory.getMath()));
        holder.txtResult.setText(this.tokenizer.getLocalizedExpression(itemHistory.getResult()));
        holder.cardView.setOnClickListener(new 1(itemHistory));
        holder.cardView.setOnLongClickListener(new 2(itemHistory));
    }

    public int getItemCount() {
        return this.itemHistories.size();
    }

    public ItemHistory getItem(int index) {
        return (ItemHistory) this.itemHistories.get(index);
    }

    public void clear() {
        this.itemHistories.clear();
        notifyItemRangeRemoved(0, getItemCount());
    }
}
