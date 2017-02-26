package com.example.duy.calculator.converter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.duy.calculator.R;
import java.util.ArrayList;

public class UnitConverterCategoryAdapter extends Adapter<ViewHolder> {
    private ArrayList<Integer> arrImg;
    private String[] arrTitle;
    private Context context;
    private OnItemClickListener mListener;

    class 1 implements OnClickListener {
        final /* synthetic */ int val$position;

        1(int i) {
            this.val$position = i;
        }

        public void onClick(View v) {
            if (UnitConverterCategoryAdapter.this.mListener != null) {
                UnitConverterCategoryAdapter.this.mListener.onItemClick(this.val$position, UnitConverterCategoryAdapter.this.arrTitle[this.val$position]);
            }
        }
    }

    class 2 implements OnLongClickListener {
        2() {
        }

        public boolean onLongClick(View view) {
            if (UnitConverterCategoryAdapter.this.mListener != null) {
                UnitConverterCategoryAdapter.this.mListener.onItemLongClick();
            }
            return false;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int i, String str);

        void onItemLongClick();
    }

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.item_title);
            this.imageView = (ImageView) itemView.findViewById(R.id.item_img);
            this.cardView = (CardView) itemView.findViewById(R.id.cardview);
        }
    }

    public UnitConverterCategoryAdapter(String[] arrTitle, ArrayList<Integer> arrImg, Context context) {
        this.arrImg = new ArrayList();
        this.arrTitle = arrTitle;
        this.arrImg = arrImg;
        this.context = context;
    }

    public UnitConverterCategoryAdapter(ArrayList<Integer> arrImg, Context context) {
        this.arrImg = new ArrayList();
        this.arrImg = arrImg;
        this.context = context;
        this.arrTitle = context.getResources().getStringArray(R.array.unit);
    }

    public UnitConverterCategoryAdapter(Context context) {
        this.arrImg = new ArrayList();
        this.context = context;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_categoty_unit_converter, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(this.arrTitle[position]);
        if (position < this.arrImg.size()) {
            holder.imageView.setImageResource(((Integer) this.arrImg.get(position)).intValue());
        }
        holder.cardView.setOnClickListener(new 1(position));
        holder.cardView.setOnLongClickListener(new 2());
    }

    public int getItemCount() {
        return this.arrTitle.length;
    }

    public void setListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }
}
