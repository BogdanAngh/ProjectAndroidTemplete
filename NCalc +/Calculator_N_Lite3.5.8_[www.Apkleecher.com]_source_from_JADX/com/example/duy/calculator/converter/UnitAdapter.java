package com.example.duy.calculator.converter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.duy.calculator.R;
import java.util.ArrayList;

public class UnitAdapter extends Adapter<Holder> {
    final Context context;
    private ArrayList<ItemUnitConverter> mList;

    static class Holder extends ViewHolder {
        TextView txtResult;
        TextView txtUnit;

        public Holder(View itemView) {
            super(itemView);
            this.txtUnit = (TextView) itemView.findViewById(R.id.txtTitle);
            this.txtResult = (TextView) itemView.findViewById(R.id.txtResult);
        }
    }

    public UnitAdapter(Context context, ArrayList<ItemUnitConverter> mList) {
        this.mList = new ArrayList();
        this.context = context;
        this.mList = mList;
    }

    public void clear() {
        this.mList.clear();
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<ItemUnitConverter> list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(this.context).inflate(R.layout.item_result_unit, parent, false));
    }

    public void onBindViewHolder(Holder holder, int position) {
        ItemUnitConverter itemUnitConverter = (ItemUnitConverter) this.mList.get(position);
        holder.txtUnit.setText(itemUnitConverter.getTitle());
        holder.txtResult.setText(String.valueOf(itemUnitConverter.getRes()));
    }

    public int getItemCount() {
        return this.mList.size();
    }
}
