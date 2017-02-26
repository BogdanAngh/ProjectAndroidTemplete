package com.example.duy.calculator.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.duy.calculator.R;
import com.example.duy.calculator.item.Point;
import java.util.ArrayList;

public class PointAdapter extends Adapter<ViewHolder> {
    private final Context context;
    private ArrayList<Point> mPoints;

    class 1 implements TextWatcher {
        final /* synthetic */ ViewHolder val$holder;
        final /* synthetic */ int val$position;

        1(ViewHolder viewHolder, int i) {
            this.val$holder = viewHolder;
            this.val$position = i;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                if (this.val$holder.editTextX.getText().toString().isEmpty()) {
                    ((Point) PointAdapter.this.mPoints.get(this.val$position)).setX(0.0d);
                } else {
                    ((Point) PointAdapter.this.mPoints.get(this.val$position)).setX(Double.parseDouble(this.val$holder.editTextX.getText().toString()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void afterTextChanged(Editable editable) {
        }
    }

    class 2 implements TextWatcher {
        final /* synthetic */ ViewHolder val$holder;
        final /* synthetic */ int val$position;

        2(ViewHolder viewHolder, int i) {
            this.val$holder = viewHolder;
            this.val$position = i;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                if (this.val$holder.editTextY.getText().toString().isEmpty()) {
                    ((Point) PointAdapter.this.mPoints.get(this.val$position)).setY(0.0d);
                } else {
                    ((Point) PointAdapter.this.mPoints.get(this.val$position)).setY(Double.parseDouble(this.val$holder.editTextY.getText().toString()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void afterTextChanged(Editable editable) {
        }
    }

    class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        private Button btnRemove;
        private EditText editTextX;
        private EditText editTextY;

        public ViewHolder(View itemView) {
            super(itemView);
            this.editTextX = (EditText) itemView.findViewById(R.id.edit_x);
            this.editTextY = (EditText) itemView.findViewById(R.id.edit_y);
        }
    }

    public PointAdapter(Context context, ArrayList<Point> points) {
        this.mPoints = new ArrayList();
        this.mPoints = points;
        this.context = context;
    }

    public PointAdapter(Context context) {
        this.mPoints = new ArrayList();
        this.context = context;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_point, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.editTextX.addTextChangedListener(new 1(holder, position));
        holder.editTextY.addTextChangedListener(new 2(holder, position));
    }

    public void addPoint() {
        this.mPoints.add(new Point(0.0d, 0.0d));
        notifyItemInserted(this.mPoints.size() - 1);
    }

    public int getItemCount() {
        return this.mPoints.size();
    }

    public void remove(int position) {
        this.mPoints.remove(position);
        notifyItemRemoved(position);
    }

    public double[] getListPointX() {
        double[] x = new double[this.mPoints.size()];
        for (int i = 0; i < this.mPoints.size(); i++) {
            x[i] = ((Point) this.mPoints.get(i)).getX();
        }
        return x;
    }

    public double[] getListPointY() {
        double[] x = new double[this.mPoints.size()];
        for (int i = 0; i < this.mPoints.size(); i++) {
            x[i] = ((Point) this.mPoints.get(i)).getY();
        }
        return x;
    }
}
